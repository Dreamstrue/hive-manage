package com.hive.infomanage.controller;

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
import com.hive.infomanage.entity.InfoNotice;
import com.hive.infomanage.model.InfoSearchBean;
import com.hive.infomanage.model.NoticeSubBean;
import com.hive.infomanage.service.InfoNoticeService;
import com.hive.infomanage.service.NoticeReceiveService;
import com.hive.push.messagepush.ZxtMessagePush;
import com.hive.util.DateUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

@Controller
@RequestMapping("notice")
public class InfoNoticeController extends BaseController {

	private static final String PREFIX = "infomanage/notice";
	private static final String OBJECT_TABLE = "INFO_NOTICE";
	private static final String BUSINESS_DIR = SystemCommon_Constant.XXGG_01;
	
	@Resource
	private InfoNoticeService infoNoticeService;
	@Resource
	private AnnexService annexService;
	@Resource
	private NoticeReceiveService noticeReceiveService;
	
	@RequestMapping("manage")
	public String manage(){
		return PREFIX+"/noticeManage";
	}
	
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(
			RequestPage page,InfoSearchBean bean) {
		return infoNoticeService.dataGrid(page, bean);
	}
	
	
	
	@RequestMapping("add")
	public String add(){
		return PREFIX+"/noticeAdd";
	}
	
	/**
	 * 
	 * @Description:
	 * @author yanghui 
	 * @Created 2014-2-25
	 * @param notice  新增的公告信息
	 * @param session 
	 * @param idString  新增的公告信息对应的附件上传成功后的附件ID值
	 * @return
	 */
	@RequestMapping("insert")
	@ResponseBody
	public Map<String,Object> insert(InfoNotice notice,HttpSession session,@RequestParam(value = "idString", required = false) String idString, @RequestParam(value = "annexFile", required = false) MultipartFile annexFile){
		String title = notice.getTitle();
		boolean existFlag = infoNoticeService.isExistInfoNoticeByTitle(title);
		if(existFlag){
			return error("新增公告标题已存在");
		}
		
		String filePath = AnnexFileUpLoad.writeContentToFile(notice.getContent(),BUSINESS_DIR,"","");
		notice.setContent(filePath); //把内容保存到文件后的文件路径保存到内容字段里，方便以后从文件中读取字节流
		
		notice.setCreateId((Long) session.getAttribute("userId"));
		notice.setCreateTime(DateUtil.getCurrentTime());
		notice.setValid(SystemCommon_Constant.VALID_STATUS_1); //初始化  1-可用
		notice.setAuditStatus(SystemCommon_Constant.AUDIT_STATUS_0);//初始化 0-未审核		
		infoNoticeService.save(notice);
		
		//公告信息保存成功后，修改对应的附件信息的创建人和对象ID值（即该公告信息的ID值）
		Long createId = notice.getCreateId();
		annexService.updateAnnex(createId,notice.getId(),idString);
		
		if(notice.getHasannex().equals("1")){
			if (annexFile.getSize() > 0L) {
				List list1 = AnnexFileUpLoad.uploadImageFile(annexFile, session,
						notice.getId(), OBJECT_TABLE, BUSINESS_DIR, SystemCommon_Constant.ANNEXT_TYPE_TZFJ);
				this.annexService.saveAnnexList(list1,
						String.valueOf(notice.getId()));
			} 
		}
		
		//新的通知公告添加成功后，推送给用户
		ZxtMessagePush push = new ZxtMessagePush();
		push.messagePush(title, "您有一条新的通知公告信息", notice.getId());
		return success("保存成功",notice);
	}
	
	
	@RequestMapping("edit")
	public String edit(Model model,@RequestParam(value="id") Long id,@RequestParam(value = "opType") String opType){
		InfoNotice  n = infoNoticeService.get(id);
		byte[] b = AnnexFileUpLoad.getContentFromFile(n.getContent());
		n.setContent(new String(b));
		model.addAttribute("vo", n);
		model.addAttribute("opType", opType);
		//取得该记录对应的附件信息
		List<Annex> list = annexService.getAnnexInfoByObjectId(id,OBJECT_TABLE);
		model.addAttribute("annex", list);
		return PREFIX+"/noticeEdit";
	}
	
	
	@RequestMapping("update")
	@ResponseBody
	public Map<String,Object> update(NoticeSubBean bean,HttpSession session,@RequestParam(value = "ids", required = false,defaultValue="") String ids,@RequestParam(value = "idString", required = false) String idString,@RequestParam(value = "fileExist", required = false) String fileExist, @RequestParam(value = "files", required = false) MultipartFile files){
		InfoNotice oldNotice = infoNoticeService.get(bean.getId());//通过ID得到数据库中原来的记录信息
		//该记录信息下存在的所有可用的附件
		int size = annexService.getAllValidAnnexSize(bean.getId(), "");
		String hasannex = bean.getHasannex();
		boolean flag = AnnexFileUpLoad.checkHasAnnex(hasannex, idString,size);//当flag = false,说明并没有附件
		if(!flag){
			bean.setHasannex(SystemCommon_Constant.SIGN_YES_0);
		}
		//取得新增时保存的文件路径（注意：路径保存在内容字段里）
		String oldPath = oldNotice.getContent();
		String filePath = AnnexFileUpLoad.writeContentToFile(bean.getContent(), BUSINESS_DIR, oldPath,"");
		
		try {
			PropertyUtils.copyProperties(oldNotice, bean);
			oldNotice.setContent(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		oldNotice.setModifyId((Long) session.getAttribute("userId"));
		oldNotice.setModifyTime(DateUtil.getTimestamp());
		oldNotice.setAuditStatus(SystemCommon_Constant.AUDIT_STATUS_0);
		//当选择不存在附件时，把原来的所有的有效附件全部逻辑删除
		if(bean.getHasannex().equals(SystemCommon_Constant.SIGN_YES_0)){
			annexService.setAnnexIsValidByObjectId(bean.getId(), "");
		}else{
			if(!ids.equals("")){
				String[] arr = ids.split(",");
			//这里判断删除的附件数量等于该记录下的所有有效附件数量时,把记录的是否存在附件修改为否
				/*if(arr.length==size && files==null){*/
				if(arr.length==size && StringUtils.isEmpty(idString)){ 	
					bean.setHasannex(SystemCommon_Constant.SIGN_YES_0);
				}
				for(int i=0;i<arr.length;i++){
					annexService.updateAnnexIsValidById(Long.valueOf(arr[i]),"");
				}
			}
		}
		
//		 附件处理
		if (hasannex.equals("1") && files.getSize() > 0L) {
			if (files.getSize() > 0L) {
				if (fileExist.equals("no") && StringUtils.isNotBlank(ids)) {
					Annex nex = (Annex) this.annexService.get(Long.parseLong(ids));
					if (nex!=null) {
						nex.setCvalid("0");
						this.annexService.update(nex);
					}
				}
				List list = AnnexFileUpLoad.uploadImageFile(files, session,
						bean.getId(), OBJECT_TABLE, BUSINESS_DIR,
						SystemCommon_Constant.ANNEXT_TYPE_ZXFJ);
				this.annexService.saveAnnexList(list,
						String.valueOf(bean.getId()));
				oldNotice.setHasannex("1");
			} 
		} else {
			if (fileExist.equals("no") && StringUtils.isNotBlank(ids)) {
				Annex nex = (Annex) this.annexService.get(Long.parseLong(ids));
				if (nex!=null) {
					nex.setCvalid("0");
					this.annexService.update(nex);
				}
			}
			oldNotice.setHasannex("0");
		}
		
		infoNoticeService.update(oldNotice);
		Long createId = (Long) session.getAttribute("userId");
		annexService.updateAnnex(createId,bean.getId(),idString);
		
		
		return success("修改成功",oldNotice);
	}
	
	
	@RequestMapping("approve")
	public String approve(Model model,@RequestParam(value="id") Long id,@RequestParam(value = "opType") String opType){
		InfoNotice  n = infoNoticeService.get(id);
		byte[] b = AnnexFileUpLoad.getContentFromFile(n.getContent());
		n.setContent(new String(b));
		model.addAttribute("vo", n);
		model.addAttribute("opType", opType);
		//取得该记录对应的附件信息
		List<Annex> list = annexService.getAnnexInfoByObjectId(id,OBJECT_TABLE);
		model.addAttribute("annex", list);
		return PREFIX+"/noticeEdit";
	}
	
	
	
	@RequestMapping("approveAction")
	@ResponseBody
	public Map<String,Object> approveAction(NoticeSubBean bean,HttpSession session,@RequestParam(value = "ids", required = false,defaultValue="") String ids,@RequestParam(value = "idString", required = false) String idString){
		InfoNotice oldNotice = infoNoticeService.get(bean.getId());//通过ID得到数据库中原来的记录信息
		//该记录信息下存在的所有可用的附件
		int size = annexService.getAllValidAnnexSize(bean.getId(), "");
		String hasannex = bean.getHasannex();
		boolean flag = AnnexFileUpLoad.checkHasAnnex(hasannex, idString,size);//当flag = false,说明并没有附件
		if(!flag){
			bean.setHasannex(SystemCommon_Constant.SIGN_YES_0);
		}
		//取得新增时保存的文件路径（注意：路径保存在内容字段里）
		String oldPath = oldNotice.getContent();
		String filePath = AnnexFileUpLoad.writeContentToFile(bean.getContent(), BUSINESS_DIR, oldPath,"");
		
		try {
			PropertyUtils.copyProperties(oldNotice, bean);
			oldNotice.setContent(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		oldNotice.setAuditId((Long) session.getAttribute("userId"));
		oldNotice.setAuditTime(DateUtil.getTimestamp());
		oldNotice.setAuditStatus(bean.getAuditStatus());
		String msg = "";
		if(SystemCommon_Constant.AUDIT_STATUS_1.equals(bean.getAuditStatus())){
			msg = "审核通过";
		}else{
			msg ="审核不通过";
		}
		//当选择不存在附件时，把原来的所有的有效附件全部逻辑删除
		if(bean.getHasannex().equals(SystemCommon_Constant.SIGN_YES_0)){
			annexService.setAnnexIsValidByObjectId(bean.getId(), "");
		}else{
			if(!ids.equals("")){
				String[] arr = ids.split(",");
			//这里判断删除的附件数量等于该记录下的所有有效附件数量时,把记录的是否存在附件修改为否
				/*if(arr.length==size && files==null){*/
				if(arr.length==size && StringUtils.isEmpty(idString)){ 	
					bean.setHasannex(SystemCommon_Constant.SIGN_YES_0);
				}
				for(int i=0;i<arr.length;i++){
					annexService.updateAnnexIsValidById(Long.valueOf(arr[i]),"");
				}
			}
		}
		infoNoticeService.update(oldNotice);
		Long createId = (Long) session.getAttribute("userId");
		annexService.updateAnnex(createId,bean.getId(),idString);
		
		//推送给用户
		if(SystemCommon_Constant.AUDIT_STATUS_1.equals(bean.getAuditStatus())){
			if("1".equals(oldNotice.getIsPush())){
				
				ZxtMessagePush push = new ZxtMessagePush();
				push.messagePush(oldNotice.getTitle(), oldNotice.getTitle(), oldNotice.getId());
			}
		}
		
		
		return success(msg,oldNotice);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam(value = "id") Long id) {
			InfoNotice inte = infoNoticeService.get(id);
			inte.setValid(SystemCommon_Constant.VALID_STATUS_0);
			infoNoticeService.update(inte);
			return success("删除成功！");
		
	}
	
	
	@RequestMapping("detail")
	public String detail(Model model,@RequestParam(value="id") Long id){
		InfoNotice  n = infoNoticeService.get(id);
		byte[] b = AnnexFileUpLoad.getContentFromFile(n.getContent());
		n.setContent(new String(b));
		model.addAttribute("vo", n);
		//取得该记录对应的附件信息
		List<Annex> list = annexService.getAnnexInfoByObjectId(id,OBJECT_TABLE);
		model.addAttribute("annex", list);
		return PREFIX+"/noticeDetail";
	}
	
	@RequestMapping("/uploadFiles")
	public void uploadFiles(@RequestParam(value = "file", required = false) MultipartFile[] files,HttpServletRequest request,HttpServletResponse response){
		List list = new ArrayList();
		Long objectId = new Long(0);  //由于在新增一条信息时，该信息在尚未保存之前附件上传就已经成功，这里暂时设定附件对应的对象ID为0,在信息保存后产生ID后再修改附件信息
		HttpSession session = request.getSession();
//		session.setAttribute("userId", new Long(0));  //由于在新增一条信息时，该信息在尚未保存之前附件上传就已经成功，这里暂时设定附件对应的创建者ID为0，信息保存后再修改附件信息
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
	
	/**
	 * 
	 * @Description:   针对每条通知公告查询已阅记录
	 * @author yanghui 
	 * @Created 2014-3-10
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("readDetail")
	public String readDetail(Model model,@RequestParam(value="id") Long id){
		model.addAttribute("id", id);
		return PREFIX+"/noticeReadSearch";
	}
	
	
	@RequestMapping("readDetailDataGrid")
	@ResponseBody
	public DataGrid readDetailDataGrid(RequestPage page,@RequestParam(value="id") Long id){
		return noticeReceiveService.readDetailDataGrid(page,id);
	}
}
