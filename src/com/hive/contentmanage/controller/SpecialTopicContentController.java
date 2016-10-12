package com.hive.contentmanage.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hive.common.AnnexFileUpLoad;
import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Annex;
import com.hive.common.service.AnnexService;
import com.hive.contentmanage.entity.SpecialTopicContent;
import com.hive.contentmanage.model.SpecialContentBean;
import com.hive.contentmanage.service.SpecialTopicContentService;
import com.hive.util.DateUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

@Controller
@RequestMapping("specialContent")
public class SpecialTopicContentController extends BaseController {
	
	public static final String PREFIX = "contentmanage/specialTopic";
	private static final String OBJECT_TABLE = "F_SPECIALTOPICCONTENT";
	private static final String BUSINESS_DIR = SystemCommon_Constant.RDZT_08;
	
	
	@Resource
	private SpecialTopicContentService contentService;
	@Resource
	private AnnexService annexService;
	
	
	@RequestMapping("/manage")
	public String manage(){
		return PREFIX+"/specialContentManage";
	}
	
	
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(
			RequestPage page,
			@RequestParam(value = "keys", required = false, defaultValue = "") String keys,@RequestParam(value = "id", required = false, defaultValue = "") String id,@RequestParam(value = "pid", required = false, defaultValue = "") String pid) {
		return contentService.dataGrid(page, keys,id,pid);
	}
	
	@RequestMapping("/add")
	public String add(){
		return PREFIX+"/specialContentAdd";
	}
	
	
	@RequestMapping("/insert")
	@ResponseBody
	public Map<String, Object> insert(SpecialTopicContent content, HttpSession session,
			@RequestParam(value = "file", required = false) MultipartFile[] files,@RequestParam(value = "idString", required = false) String idString) {

		String title = content.getTitle();
		int size = contentService.getContentByName(title);
		if(size>0){
			return error("标题【"+title+"】已存在");
		}
		
		String filePath = AnnexFileUpLoad.writeContentToFile(content.getContent(),BUSINESS_DIR,"","");
		content.setContent(filePath); //把内容保存到文件后的文件路径保存到内容字段里，方便以后从文件中读取字节流

		// 创建人
		content.setNcreateid((Long) session
				.getAttribute("userId"));
		content.setDcreatetime(DateUtil.getTimestamp());// 创建时间

		content.setCauditstatus(SystemCommon_Constant.AUDIT_STATUS_0);
		content.setCvalid(SystemCommon_Constant.VALID_STATUS_1);
		contentService.save(content);

		// 上传附件

		Long createId = (Long) session.getAttribute("userId");
		annexService.updateAnnex(createId,content.getId(),idString);
//		List<Annex> nexlist = AnnexFileUpLoad.uploadFile(files, session, content.getId(), OBJECT_TABLE,BUSINESS_DIR, null);
//		annexService.saveAnnexList(nexlist,null);
		
		return success("保存成功", content);
	}
	
	
	@RequestMapping("/edit")
	public String edit(Model model, @RequestParam(value = "id") Long id,
			@RequestParam(value = "opType") String opType) {
		SpecialTopicContent in = contentService.get(id);
		byte[] bt = AnnexFileUpLoad.getContentFromFile(in.getContent());
		in.setContent(new String(bt));
		model.addAttribute("vo", in);
		model.addAttribute("opType", opType);
		//取得该记录对应的附件信息
		List<Annex> list = annexService.getAnnexInfoByObjectId(id,OBJECT_TABLE);
		model.addAttribute("annex", list);
		return PREFIX + "/specialContentEdit";
	}

	
	@RequestMapping("/update")
	@ResponseBody
	public Map<String, Object> update(SpecialContentBean bean, HttpServletRequest request,
			@RequestParam(value = "file", required = false) MultipartFile[] files,@RequestParam(value = "ids", required = false,defaultValue="") String ids,@RequestParam(value = "idString", required = false) String idString) {
		
		//这里实现一个方法，当选择存在附件，而实际上并没有上传附件时，默认把是否存在附件修改为否；
		int size = annexService.getAllValidAnnexSize(bean.getId(),"");
		String chasannex = bean.getChasannex();
	//	boolean flag = AnnexFileUpLoad.checkHasAnnex(chasannex, files,size);//当flag = false,说明并没有附件
		boolean flag = AnnexFileUpLoad.checkHasAnnex(chasannex, idString,size);//当flag1 = false,说明并没有附件----修改过后的方法
		if(!flag){
			bean.setChasannex(SystemCommon_Constant.SIGN_YES_0);
		}
		
		SpecialTopicContent content = contentService.get(bean.getId());
		
		//取得新增时保存的文件路径(注意：路径保存在内容字段里面)
		String oldPath = content.getContent();
		String filePath = AnnexFileUpLoad.writeContentToFile(bean.getContent(),BUSINESS_DIR,oldPath,"");
		try {
			PropertyUtils.copyProperties(content, bean);
			content.setContent(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}

		

		// 修改人
		HttpSession session = request.getSession();
		content.setNmodifyid((Long) session
				.getAttribute("userId"));
		content.setDmodifytime(DateUtil.getTimestamp());// 修改时间
		
		content.setCauditstatus(SystemCommon_Constant.AUDIT_STATUS_0);
		
		//当选择不存在附件时，把原来所有的有效附件全部删除(逻辑删除)
				if(bean.getChasannex().equals(SystemCommon_Constant.SIGN_YES_0)){
					annexService.setAnnexIsValidByObjectId(bean.getId(),"");
				}
				if(!ids.equals("")){
					String[] arr = ids.split(",");
				//这里判断删除的附件数量等于该记录下的所有有效附件数量时,把记录的是否存在附件修改为否
				//	if(arr.length==size && files==null){
					if(arr.length==size && StringUtils.isEmpty(idString)){     //新修改的内容 2014-01-07
						content.setChasannex(SystemCommon_Constant.SIGN_YES_0);
					}else{
						
					}
					for(int i=0;i<arr.length;i++){
						annexService.updateAnnexIsValidById(Long.valueOf(arr[i]),"");
					}
				}
				
				Long createId = (Long) session.getAttribute("userId");
				annexService.updateAnnex(createId,bean.getId(),idString);		
//				List<Annex> nexlist = AnnexFileUpLoad.uploadFile(files, session, bean.getId(),OBJECT_TABLE,BUSINESS_DIR, null);
//				annexService.saveAnnexList(nexlist,null);
		contentService.update(content);
		
		return success("修改成功", content);
	}
	
	@RequestMapping("detail")
	public String detail(Model model,@RequestParam(value="id") Long id){
		SpecialTopicContent in = contentService.get(id);
		byte[] bt = AnnexFileUpLoad.getContentFromFile(in.getContent());
		in.setContent(new String(bt));
		model.addAttribute("vo", in);
		//取得该记录对应的附件信息
		List<Annex> list = annexService.getAnnexInfoByObjectId(id,OBJECT_TABLE);
		model.addAttribute("annex", list);
		return PREFIX+"/specialContentDetails";
	}
	
	@RequestMapping("/successAction")
	@ResponseBody
	public Map<String, Object> successAction(SpecialContentBean bean,
			HttpSession session,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		SpecialTopicContent content = contentService.get(bean.getId());
		try {
			PropertyUtils.copyProperties(content, bean);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 修改人
		// content.setNmodifyid(Long.valueOf((String)
		// session.getAttribute("userId")));
		// content.setDmodifytime(DateUtil.getTimestamp());//修改时间

		content.setNauditid((Long) session
				.getAttribute("userId"));
		content.setDaudittime(DateUtil.getTimestamp());

		content.setCauditstatus(SystemCommon_Constant.AUDIT_STATUS_1);
		content.setCauditopinion(bean.getCauditopinion());
		contentService.update(content);
		return success("审核完成", content);
	}

	// 审核不通过
	@RequestMapping("/errorAction")
	@ResponseBody
	public Map<String, Object> errorAction(HttpSession session,
			@RequestParam(value = "id", required = false) int id,@RequestParam(value = "cauditopinion", required = false) String auditOpinion) {
		SpecialTopicContent content = contentService.get(id);
		content.setNauditid((Long) session
				.getAttribute("userId"));
		content.setDaudittime(DateUtil.getTimestamp());

		content.setCauditopinion(auditOpinion);
		content.setCauditstatus(SystemCommon_Constant.AUDIT_STATUS_2);

		contentService.update(content);
		return success("审核完成", content);

	}
	
	@RequestMapping("/approveAction")
	@ResponseBody
	public Map<String, Object> approveAction(SpecialContentBean bean, HttpServletRequest request,
			@RequestParam(value = "file", required = false) MultipartFile[] files,@RequestParam(value = "ids", required = false,defaultValue="") String ids,@RequestParam(value = "type", required = false,defaultValue="") String type,@RequestParam(value = "idString", required = false) String idString) {
		
		//这里实现一个方法，当选择存在附件，而实际上并没有上传附件时，默认把是否存在附件修改为否；
		int size = annexService.getAllValidAnnexSize(bean.getId(),"");
		String chasannex = bean.getChasannex();
	//	boolean flag = AnnexFileUpLoad.checkHasAnnex(chasannex, files,size);//当flag = false,说明并没有附件
		boolean flag = AnnexFileUpLoad.checkHasAnnex(chasannex, idString,size);//当flag1 = false,说明并没有附件----修改过后的方法
		if(!flag){
			bean.setChasannex(SystemCommon_Constant.SIGN_YES_0);
		}
		
		SpecialTopicContent content = contentService.get(bean.getId());
		//取得新增时保存的文件路径(注意：路径保存在内容字段里面)
		String oldPath = content.getContent();
		String filePath = AnnexFileUpLoad.writeContentToFile(bean.getContent(),BUSINESS_DIR,oldPath,"approve");
		try {
			PropertyUtils.copyProperties(content, bean);
			content.setContent(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}

		

		// 修改人
		HttpSession session = request.getSession();
		content.setNauditid((Long) session
				.getAttribute("userId"));
		content.setDaudittime(DateUtil.getTimestamp());// 修改时间
		String msg = "";
		if(bean.getCauditstatus().equals(SystemCommon_Constant.AUDIT_STATUS_1)){
			msg = "审核通过";
		}else if(bean.getCauditstatus().equals(SystemCommon_Constant.AUDIT_STATUS_2)){
			msg ="审核不通过";
		}
		content.setCauditstatus(bean.getCauditstatus());
		
		//当选择不存在附件时，把原来所有的有效附件全部删除(逻辑删除)
				if(bean.getChasannex().equals(SystemCommon_Constant.SIGN_YES_0)){
					annexService.setAnnexIsValidByObjectId(bean.getId(),"");
				}
				if(!ids.equals("")){
					String[] arr = ids.split(",");
				//这里判断删除的附件数量等于该记录下的所有有效附件数量时,把记录的是否存在附件修改为否
				//	if(arr.length==size && files==null){
					if(arr.length==size && StringUtils.isEmpty(idString)){     //新修改的内容 2014-01-07
						content.setChasannex(SystemCommon_Constant.SIGN_YES_0);
					}else{
						
					}
					for(int i=0;i<arr.length;i++){
						annexService.updateAnnexIsValidById(Long.valueOf(arr[i]),"");
					}
				}
				Long createId = (Long) session.getAttribute("userId");
				annexService.updateAnnex(createId,bean.getId(),idString);
//				List<Annex> nexlist = AnnexFileUpLoad.uploadFile(files, session, bean.getId(),OBJECT_TABLE,BUSINESS_DIR, null);
//				annexService.saveAnnexList(nexlist,null);
		contentService.update(content);
		
		return success(msg, content);
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam(value = "id") String id) {
		if (StringUtils.isNotEmpty(id)) {
			SpecialTopicContent inte = contentService.get(Long.valueOf(id));
			inte.setCvalid(SystemCommon_Constant.VALID_STATUS_0);
			contentService.update(inte);
			return success("删除成功！");
		} else
			return error("请选择需要删除的对象");
	}

	@RequestMapping("/back")
	@ResponseBody
	public Map<String, Object> back(@RequestParam(value = "id") String id) {
		if (StringUtils.isNotEmpty(id)) {
			SpecialTopicContent inte = contentService.get(Long.valueOf(id));
			inte.setCvalid(SystemCommon_Constant.VALID_STATUS_1);
			contentService.update(inte);
			return success("恢复成功！");
		} else
			return error("请选择需要删除的对象");
	}
	
	@RequestMapping("/uploadFiles")
	public void uploadFiles(@RequestParam(value = "file", required = false) MultipartFile[] files,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		List list = new ArrayList();
		Long objectId = new Long(0);  //由于在新增一条信息时，该信息在尚未保存之前附件上传就已经成功，这里暂时设定附件对应的对象ID为1,在信息保存后产生ID后再修改附件信息
		session.setAttribute("userId", new Long(0));  //由于在新增一条信息时，该信息在尚未保存之前附件上传就已经成功，这里暂时设定附件对应的创建者ID为0，信息保存后再修改附件信息
		List<Annex> nexlist = AnnexFileUpLoad.uploadFile(files, session, objectId, OBJECT_TABLE,BUSINESS_DIR, null);
		String idString = annexService.saveAnnexListTwo(nexlist,null); //返回所有附件保存后的ID值
		list = AnnexFileUpLoad.commonUploadFiles(nexlist, idString);
		String  jsonArray = JSONArray.toJSONString(list);
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.print(jsonArray);
	}
}
