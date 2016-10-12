/**
 * 
 */
package com.hive.contentmanage.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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
import com.hive.contentmanage.entity.CommonContent;
import com.hive.contentmanage.model.CommonContentUpdateBean;
import com.hive.contentmanage.service.CommonContentService;
import com.hive.infomanage.model.InfoSearchBean;
import com.hive.push.messagepush.ZxtMessagePush;
import com.hive.util.DataUtil;
import com.hive.util.DateUtil;
import com.hive.util.PropertiesFileUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**  
 * Filename: CommonContentController.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-4-10  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-4-10 上午11:03:10				yanghui 	1.0
 */
@Controller
@RequestMapping("commonContent")
public class CommonContentController extends BaseController {
	
	private static final String PREFIX = "contentmanage";
	private static final String OBJECT_TABLE = "F_COMMONCONTENT";
	private static final String BUSINESS_DIR = SystemCommon_Constant.COMMON_CONTENT;
	private static final Logger logger = Logger.getLogger(CommonContentController.class);
	@Resource
	private CommonContentService commonContentService;
	@Resource
	private AnnexService annexService;
	
	@RequestMapping("manage")
	public String manage(){
		return PREFIX+"/contentManage";
	}
	
	
	@RequestMapping("dataGrid")
	@ResponseBody
	public DataGrid dataGrid(RequestPage page,InfoSearchBean bean){
		return commonContentService.dataGrid(page,bean);
	}
	
	@RequestMapping("add")
	public String add(){
		return PREFIX+"/contentAdd";
	}

	@RequestMapping("insert")
	@ResponseBody
	public Map<String,Object> insert(CommonContent commonContent,HttpServletRequest request,@RequestParam(value = "idString", required = false) String idString,@RequestParam(value="pid",required=false) Long pid, @RequestParam(value = "annexFile", required = false) MultipartFile annexFile){
		
		//如新闻资讯模块需要上传图标在客户端显示时使用，这里先处理裁剪的图片
		AnnexFileUpLoad annexFileUpload = new AnnexFileUpLoad();
		String newImg = annexFileUpload.cutImage(request, commonContent.getPicturePath(),"");
		commonContent.setPicturePath(newImg);
		
		String title = commonContent.getTitle();
		boolean existFlag = commonContentService.isExistTitle(title);
		if(existFlag){
			return error("标题已存在");
		}
		
		String filePath = AnnexFileUpLoad.writeContentToFile(commonContent.getContent(),BUSINESS_DIR,"","");
		commonContent.setContent(filePath); //把内容保存到文件后的文件路径保存到内容字段里，方便以后从文件中读取字节流
		HttpSession session = request.getSession();
		commonContent.setCreateId((Long) session.getAttribute("userId"));
		commonContent.setCreateTime(DateUtil.getCurrentTime());
		commonContent.setValid(SystemCommon_Constant.VALID_STATUS_1); //初始化  1-可用
		commonContent.setAuditStatus(SystemCommon_Constant.AUDIT_STATUS_0);//初始化 0-未审核	
		commonContent.setIdowncount(0L); //默认设置下载次数为0
		//下面两个字段内容不是所有模块都需要用到，暂且新增时默认都设置为0
		commonContent.setCommentNum(0); //评论次数
		commonContent.setShareNum(0); //分享次数为
		
		if(!String.valueOf(pid).equals("42")){
			//当选择的类别的父级节点不是消费资讯（id=42）的时候，是否为头条设置为空
			commonContent.setIsTop("");
		}
		commonContentService.save(commonContent);
		
		//公告信息保存成功后，修改对应的附件信息的创建人和对象ID值（即该公告信息的ID值）
		Long createId = commonContent.getCreateId();
		annexService.updateAnnex(createId,commonContent.getId(),idString);
		
		if(commonContent.getHasannex().equals("1")){
			if (annexFile.getSize() > 0L) {
				List list1 = AnnexFileUpLoad.uploadImageFile(annexFile, session,
						commonContent.getId(), OBJECT_TABLE, BUSINESS_DIR, SystemCommon_Constant.ANNEXT_TYPE_ZXFJ);
				this.annexService.saveAnnexList(list1,
						String.valueOf(commonContent.getId()));
			} 
		}
		
		return success("保存成功",commonContent);
	}
	
	
	@RequestMapping("edit")
	public String edit(Model model,@RequestParam(value="id") Long id,@RequestParam(value = "opType") String opType){
		CommonContent cc = commonContentService.get(id);
		byte[] b = AnnexFileUpLoad.getContentFromFile(cc.getContent());
		cc.setContent(new String(b));
		model.addAttribute("vo", cc);
		model.addAttribute("opType", opType);
		//取得附件列表信息
		List<Annex> list = annexService.getAnnexInfoByObjectId(id,OBJECT_TABLE);
		model.addAttribute("annex", list);
		return PREFIX+"/contentEdit";
	}
	
	@RequestMapping("update")
	@ResponseBody
	public Map<String,Object> update(CommonContentUpdateBean bean,HttpServletRequest request,@RequestParam(value = "ids", required = false,defaultValue="") String ids,@RequestParam(value = "idString", required = false) String idString,@RequestParam(value="pid",required=false) Long pid,@RequestParam(value = "fileExist", required = false) String fileExist, @RequestParam(value = "files", required = false) MultipartFile files){
		CommonContent common = commonContentService.get(bean.getId());//通过ID得到数据库中原来的记录信息
		String oldPicturePath = common.getPicturePath(); 
		//该记录信息下存在的所有可用的附件
		int size = annexService.getAllValidAnnexSize(bean.getId(), "");
		String hasannex = bean.getHasannex();
		boolean flag = AnnexFileUpLoad.checkHasAnnex(hasannex, idString,size);//当flag = false,说明并没有附件
		if(!flag){
			bean.setHasannex(SystemCommon_Constant.SIGN_YES_0);
		}
		//取得新增时保存的文件路径（注意：路径保存在内容字段里）
		String oldPath = common.getContent();
		String filePath = AnnexFileUpLoad.writeContentToFile(bean.getContent(), BUSINESS_DIR, oldPath,"");
		
		try {
			PropertyUtils.copyProperties(common, bean);
			common.setContent(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//处理修改时是否上传图片剪裁
		AnnexFileUpLoad annexFileUpload = new AnnexFileUpLoad();
		String newImg = annexFileUpload.cutImage(request, bean.getPicturePath(), oldPicturePath);
		if(!StringUtils.isEmpty(newImg)){
			common.setPicturePath(newImg);
		}
		
		HttpSession session = request.getSession();
		common.setModifyId((Long) session.getAttribute("userId"));
		common.setModifyTime(DateUtil.getTimestamp());
		common.setAuditStatus(SystemCommon_Constant.AUDIT_STATUS_0);
		//当选择不存在附件时，把原来的所有的有效附件全部逻辑删除
		if(bean.getHasannex().equals(SystemCommon_Constant.SIGN_YES_0)){
			annexService.setAnnexIsValidByObjectId(bean.getId(), "");
		}else{
			if(!ids.equals("")){
				String[] arr = ids.split(",");
			//这里判断删除的附件数量等于该记录下的所有有效附件数量时,把记录的是否存在附件修改为否
				if(arr.length==size && StringUtils.isEmpty(idString)){ 	
					bean.setHasannex(SystemCommon_Constant.SIGN_YES_0);
				}
				for(int i=0;i<arr.length;i++){
					annexService.updateAnnexIsValidById(Long.valueOf(arr[i]),"");
				}
			}
		}
		
		if(!String.valueOf(pid).equals("42") && null!=pid){
			//当选择的类别的父级节点不是消费资讯（id=42）的时候，是否为头条设置为空
			common.setIsTop("");
		}
		
		
		// 附件处理
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
				common.setHasannex("1");
			} 
		} else {
			if (fileExist.equals("no") && StringUtils.isNotBlank(ids)) {
				Annex nex = (Annex) this.annexService.get(Long.parseLong(ids));
				if (nex!=null) {
					nex.setCvalid("0");
					this.annexService.update(nex);
				}
			}
			common.setHasannex("0");
		}
		
		commonContentService.update(common);
		Long createId = (Long) session.getAttribute("userId");
		annexService.updateAnnex(createId,bean.getId(),idString);
		
		
		return success("修改成功",common);
	}
	
	
	@RequestMapping("approve")
	public String approve(Model model,@RequestParam(value="id") Long id,@RequestParam(value = "opType") String opType){
		CommonContent  n = commonContentService.get(id);
		byte[] b = AnnexFileUpLoad.getContentFromFile(n.getContent());
		n.setContent(new String(b));
		model.addAttribute("vo", n);
		model.addAttribute("opType", opType);
		//取得该记录对应的附件信息
		List<Annex> list = annexService.getAnnexInfoByObjectId(id,OBJECT_TABLE);
		model.addAttribute("annex", list);
		return PREFIX+"/contentEdit";
	}
	
	
	
	@RequestMapping("approveAction")
	@ResponseBody
	public Map<String,Object> approveAction(CommonContentUpdateBean bean,HttpSession session,@RequestParam(value = "ids", required = false,defaultValue="") String ids,@RequestParam(value = "idString", required = false) String idString,HttpServletRequest request,@RequestParam(value="pid",required=false) Long pid){
		CommonContent common = commonContentService.get(bean.getId());//通过ID得到数据库中原来的记录信息
		String oldPicturePath= common.getPicturePath();
		//该记录信息下存在的所有可用的附件
		int size = annexService.getAllValidAnnexSize(bean.getId(), "");
		String hasannex = bean.getHasannex();
		boolean flag = AnnexFileUpLoad.checkHasAnnex(hasannex, idString,size);//当flag = false,说明并没有附件
		if(!flag){
			bean.setHasannex(SystemCommon_Constant.SIGN_YES_0);
		}
		//取得新增时保存的文件路径（注意：路径保存在内容字段里）
		String oldPath = common.getContent();
		// 审核时候最后参数传 "approve" 则内容中会多加一些 html 标签，导致前台详情页错乱，暂时不传 燕珂 2015-09-11
		String filePath = AnnexFileUpLoad.writeContentToFile(bean.getContent(), BUSINESS_DIR, oldPath,"");
		
		try {
			PropertyUtils.copyProperties(common, bean);
			common.setContent(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//处理修改时是否上传图片剪裁
		AnnexFileUpLoad annexFileUpload = new AnnexFileUpLoad();
		String newImg = annexFileUpload.cutImage(request, bean.getPicturePath(), oldPicturePath);
		if(!StringUtils.isEmpty(newImg)){
			common.setPicturePath(newImg);
		}
		
		common.setAuditId((Long) session.getAttribute("userId"));
		common.setAuditTime(DateUtil.getTimestamp());
		common.setAuditStatus(bean.getAuditStatus());
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
				if(arr.length==size && StringUtils.isEmpty(idString)){ 	
					bean.setHasannex(SystemCommon_Constant.SIGN_YES_0);
				}
				for(int i=0;i<arr.length;i++){
					annexService.updateAnnexIsValidById(Long.valueOf(arr[i]),"");
				}
			}
		}

		String str = "";
		if(pid!=null){
			if(!String.valueOf(pid).equals("42")){
				//当选择的类别的父级节点不是消费资讯（id=42）的时候，是否为头条设置为空
				common.setIsTop("");
			}
			str = String.valueOf(pid);
		}
		
		commonContentService.update(common);
		Long createId = (Long) session.getAttribute("userId");
		annexService.updateAnnex(createId,bean.getId(),idString);
		
		
		if(SystemCommon_Constant.AUDIT_STATUS_1.equals(bean.getAuditStatus())){
			
			//新的资讯审核通过后，推送给用户
			/**
			 *  由于项目业务中针对消费资讯、红榜单、黑榜单、质量强市进行消息推送
			 *  而这四项属于公共内容管理，需要通过所属的信息类别进行判断（信息类别表字段的ID不要随意改动）
			 */
			if(SystemCommon_Constant.PUSH_MESSAGE_XFZX.equals(str) || SystemCommon_Constant.PUSH_MESSAGE_RED.equals(str) || SystemCommon_Constant.PUSH_MESSAGE_BLACK.equals(str) || SystemCommon_Constant.PUSH_MESSAGE_ZLQS.equals(str)){
				if("1".equals(common.getIsPush())){
					ZxtMessagePush push = new ZxtMessagePush();
					push.messagePush(common.getTitle(), common.getSummary(), common.getId());
				}
			}
		}
		return success(msg,common);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam(value = "id") Long id) {
			CommonContent inte = commonContentService.get(id);
			inte.setValid(SystemCommon_Constant.VALID_STATUS_0);
			commonContentService.update(inte);
			return success("删除成功！");
		
	}
	
	
	@RequestMapping("detail")
	public String detail(Model model,@RequestParam(value="id") Long id){
		CommonContent  n = commonContentService.get(id);
		byte[] b = AnnexFileUpLoad.getContentFromFile(n.getContent());
		n.setContent(new String(b));
		model.addAttribute("vo", n);
		//取得该记录对应的附件信息
		List<Annex> list = annexService.getAnnexInfoByObjectId(id,OBJECT_TABLE);
		model.addAttribute("annex", list);
		return PREFIX+"/contentDetail";
	}
	
	
	@RequestMapping("/uploadFiles")
	public void uploadFiles(@RequestParam(value = "file", required = false) MultipartFile[] files,HttpServletRequest request,HttpServletResponse response){
		List list = new ArrayList();
		Long objectId = new Long(0);  //由于在新增一条信息时，该信息在尚未保存之前附件上传就已经成功，这里暂时设定附件对应的对象ID为0,在信息保存后产生ID后再修改附件信息
		HttpSession session = request.getSession();
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
	
	
	@RequestMapping("loadPic")
	public void loadPic(@RequestParam(value="picPath") String picPath,HttpServletRequest request, HttpServletResponse response){
	    try
	    {
	      String temp_picPath = new String(picPath.getBytes("ISO-8859-1"), "UTF-8");
	      picPath = URLDecoder.decode(temp_picPath, "UTF-8");
	    } catch (UnsupportedEncodingException e) {
	      e.printStackTrace();
	    }
	    ServletOutputStream os = null;
	    FileInputStream fis = null;
	    response.setHeader("Pragma", "no-cache");
	    response.setHeader("Cache-Control", "no-cache");
	    response.setDateHeader("Expires", 0L);
	    response.setContentType("image/jpeg");
	    
	    
	    String uploadDirAbsolutePath = request.getSession().getServletContext().getRealPath("/");
	    PropertiesFileUtil pfu = new PropertiesFileUtil();
	    String filePath = pfu.findValue("uploadPath");

	    if (!DataUtil.isEmpty(picPath))
	    {
	      File photoFile = new File(uploadDirAbsolutePath + picPath);
	      try {
	        if ((photoFile != null) && (photoFile.exists())) {
	          os = response.getOutputStream();
	          fis = new FileInputStream(photoFile);
	          byte[] buffer = new byte[1024];
	          int b;
	          while ((b = fis.read(buffer)) != -1)
	          {
	            os.write(buffer, 0, b);
	          }
	          os.flush();
	          os.close();
	          fis.close();
	        }
	      } catch (IOException e) {
	        logger.error("读取图片出错！", e);
	      }
	    }
	  }
}
