package com.hive.infomanage.controller;

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
import com.hive.infomanage.entity.InfoNews;
import com.hive.infomanage.model.InfoSearchBean;
import com.hive.infomanage.model.NewsSubBean;
import com.hive.infomanage.service.InfoNewsService;
import com.hive.infomanage.service.NewsCommentService;
import com.hive.util.DataUtil;
import com.hive.util.DateUtil;
import com.hive.util.PropertiesFileUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

@Controller
@RequestMapping("news")
public class InfoNewsController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(InfoNewsController.class);

	private static final String PREFIX = "infomanage/news";
	private static final String OBJECT_TABLE = "INFO_NEWS";
	private static final String BUSINESS_DIR = SystemCommon_Constant.CXZX_04;
	
	@Resource
	private InfoNewsService infoNewsService;
	@Resource
	private NewsCommentService newsCommentService;
	@Resource
	private AnnexService annexService;
	
	@RequestMapping("manage")
	public String manage(){
		return PREFIX+"/newsManage";
	}
	
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(
			RequestPage page,InfoSearchBean bean) {
		return infoNewsService.dataGrid(page, bean);
	}
	
	@RequestMapping("add")
	public String add(){
		return PREFIX+"/newsAdd";
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
	public Map<String,Object> insert(InfoNews news,HttpServletRequest request,@RequestParam(value = "idString", required = false) String idString, @RequestParam(value = "annexFile", required = false) MultipartFile annexFile){
		//处理裁剪的图片
		AnnexFileUpLoad annexFileUpload = new AnnexFileUpLoad();
		String newImg = annexFileUpload.cutImage(request, news.getPicturePath(),"");
		news.setPicturePath(newImg);
	    
	    
		String title = news.getTitle();
		boolean existFlag = infoNewsService.isExistInfoNewsByTitle(title);
		if(existFlag){
			return error("新增公告标题已存在");
		}
		
		String filePath = AnnexFileUpLoad.writeContentToFile(news.getContent(),BUSINESS_DIR,"","");
		news.setContent(filePath); //把内容保存到文件后的文件路径保存到内容字段里，方便以后从文件中读取字节流
		HttpSession session = request.getSession();
		news.setCreateId((Long) session.getAttribute("userId"));
		news.setCreateTime(DateUtil.getCurrentTime());
		news.setValid(SystemCommon_Constant.VALID_STATUS_1); //初始化  1-可用
		news.setAuditStatus(SystemCommon_Constant.AUDIT_STATUS_0);//初始化 0-未审核		
		//新建一条新闻资讯信息时，评论次数默认为0
		news.setCommentNum(0);
		news.setShareNum(0); //新增时默认分享次数为0
		
		//系统设置变量    由于这两个内容是从系统设置表中读取的，目前暂时默认
		news.setShareIntegral(new Long(200));  //单次分享积分 
		news.setCommentIntegral(new Long(10)); //单次评论积分
		infoNewsService.save(news);
		
		//公告信息保存成功后，修改对应的附件信息的创建人和对象ID值（即该公告信息的ID值）
		Long createId = news.getCreateId();
		annexService.updateAnnex(createId,news.getId(),idString);
		
		if(news.getHasannex().equals("1")){
			if (annexFile.getSize() > 0L) {
				List list1 = AnnexFileUpLoad.uploadImageFile(annexFile, session,
						news.getId(), OBJECT_TABLE, BUSINESS_DIR, SystemCommon_Constant.ANNEXT_TYPE_ZXFJ);
				this.annexService.saveAnnexList(list1,
						String.valueOf(news.getId()));
			} 
		}
		
		return success("保存成功",news);
	}
	
	
	@RequestMapping("edit")
	public String edit(Model model,@RequestParam(value="id") Long id,@RequestParam(value = "opType") String opType){
		InfoNews  n = infoNewsService.get(id);
		byte[] b = AnnexFileUpLoad.getContentFromFile(n.getContent());
		n.setContent(new String(b));
		model.addAttribute("vo", n);
		model.addAttribute("opType", opType);
		//取得该记录对应的附件信息
		List<Annex> imgList = annexService.getAnnexInfoByObjectId(id,OBJECT_TABLE);
		model.addAttribute("annex", imgList);
		
		List list = this.annexService.getAnnexInfoByObjectIdToPictureNew1(id,SystemCommon_Constant.ANNEXT_TYPE_ZXFJ);
		model.addAttribute("annex", list);
	    try {
	    	  model.addAttribute("ids", list.get(0));
		} finally{
			return PREFIX+"/newsEdit";
		}
	}
	
	
	@RequestMapping("update")
	@ResponseBody
	public Map<String,Object> update(NewsSubBean bean,HttpServletRequest request,@RequestParam(value = "ids", required = false,defaultValue="") String ids,@RequestParam(value = "idString", required = false) String idString,@RequestParam(value = "fileExist", required = false) String fileExist, @RequestParam(value = "files", required = false) MultipartFile files){
		InfoNews oldNews = infoNewsService.get(bean.getId());//通过ID得到数据库中原来的记录信息
		String oldPicturePath = oldNews.getPicturePath(); 
		//该记录信息下存在的所有可用的附件
		int size = annexService.getAllValidAnnexSize(bean.getId(), "");
		String hasannex = bean.getHasannex();
		boolean flag = AnnexFileUpLoad.checkHasAnnex(hasannex, idString,size);//当flag = false,说明并没有附件
		if(!flag){
			bean.setHasannex(SystemCommon_Constant.SIGN_YES_0);
		}
		//取得新增时保存的文件路径（注意：路径保存在内容字段里）
		String oldPath = oldNews.getContent();
		String filePath = AnnexFileUpLoad.writeContentToFile(bean.getContent(), BUSINESS_DIR, oldPath,"");
		
		try {
			PropertyUtils.copyProperties(oldNews, bean);
			oldNews.setContent(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//处理修改时是否上传图片剪裁
		AnnexFileUpLoad annexFileUpload = new AnnexFileUpLoad();
		String newImg = annexFileUpload.cutImage(request, bean.getPicturePath(), oldPicturePath);
		if(!StringUtils.isEmpty(newImg)){
			oldNews.setPicturePath(newImg);
		}
		
		
		HttpSession session = request.getSession();
		oldNews.setModifyId((Long) session.getAttribute("userId"));
		oldNews.setModifyTime(DateUtil.getTimestamp());
		oldNews.setAuditStatus(SystemCommon_Constant.AUDIT_STATUS_0);
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
				oldNews.setHasannex("1");
			} 
		} else {
			if (fileExist.equals("no") && StringUtils.isNotBlank(ids)) {
				Annex nex = (Annex) this.annexService.get(Long.parseLong(ids));
				if (nex!=null) {
					nex.setCvalid("0");
					this.annexService.update(nex);
				}
			}
			oldNews.setHasannex("0");
		}
		
		infoNewsService.update(oldNews);
		Long createId = (Long) session.getAttribute("userId");
		annexService.updateAnnex(createId,bean.getId(),idString);
		
		
		return success("修改成功",oldNews);
	}
	
	
	
	@RequestMapping("approve")
	public String approve(Model model,@RequestParam(value="id") Long id,@RequestParam(value = "opType") String opType){
		InfoNews  n = infoNewsService.get(id);
		byte[] b = AnnexFileUpLoad.getContentFromFile(n.getContent());
		n.setContent(new String(b));
		model.addAttribute("vo", n);
		model.addAttribute("opType", opType);
		//取得该记录对应的附件信息
		List<Annex> list = annexService.getAnnexInfoByObjectId(id,OBJECT_TABLE);
		model.addAttribute("annex", list);
		return PREFIX+"/newsEdit";
	}
	
	
	
	@RequestMapping("approveAction")
	@ResponseBody
	public Map<String,Object> approveAction(NewsSubBean bean,HttpSession session,@RequestParam(value = "ids", required = false,defaultValue="") String ids,@RequestParam(value = "idString", required = false) String idString){
		InfoNews oldNews = infoNewsService.get(bean.getId());//通过ID得到数据库中原来的记录信息
		//该记录信息下存在的所有可用的附件
		int size = annexService.getAllValidAnnexSize(bean.getId(), "");
		String hasannex = bean.getHasannex();
		boolean flag = AnnexFileUpLoad.checkHasAnnex(hasannex, idString,size);//当flag = false,说明并没有附件
		if(!flag){
			bean.setHasannex(SystemCommon_Constant.SIGN_YES_0);
		}
		//取得新增时保存的文件路径（注意：路径保存在内容字段里）
		String oldPath = oldNews.getContent();
		// 审核时候最后参数传 "approve" 则内容中会多加一些 html 标签，导致前台详情页错乱，暂时不传 燕珂 2015-09-11
		String filePath = AnnexFileUpLoad.writeContentToFile(bean.getContent(), BUSINESS_DIR, oldPath,"");
		
		try {
			PropertyUtils.copyProperties(oldNews, bean);
			oldNews.setContent(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		oldNews.setAuditId((Long) session.getAttribute("userId"));
		oldNews.setAuditTime(DateUtil.getTimestamp());
		oldNews.setAuditStatus(bean.getAuditStatus());
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
		infoNewsService.update(oldNews);
		Long createId = (Long) session.getAttribute("userId");
		annexService.updateAnnex(createId,bean.getId(),idString);
		
		return success(msg,oldNews);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam(value = "id") Long id) {
			InfoNews inte = infoNewsService.get(id);
			inte.setValid(SystemCommon_Constant.VALID_STATUS_0);
			infoNewsService.update(inte);
			return success("删除成功！");
		
	}
	
	
	@RequestMapping("detail")
	public String detail(Model model,@RequestParam(value="id") Long id){
		InfoNews  n = infoNewsService.get(id);
		byte[] b = AnnexFileUpLoad.getContentFromFile(n.getContent());
		n.setContent(new String(b));
		model.addAttribute("vo", n);
		//取得该记录对应的附件信息
		List<Annex> list = annexService.getAnnexInfoByObjectId(id,OBJECT_TABLE);
		model.addAttribute("annex", list);
		return PREFIX+"/newsDetail";
	}
	
	
	
	
	//新闻资讯  按信息分类   统计页面
	@RequestMapping("categoryStatistics")
	public String categoryStatistics(){
		return PREFIX+"/categoryStatistics";
	}
	
	/**
	 * 
	 * @Description:  按信息类别统计
	 * @author yanghui 
	 * @Created 2014-3-20
	 * @param page
	 * @param bean
	 * @return
	 */
	@RequestMapping("categoryDataGrid")
	@ResponseBody
	public DataGrid categoryDataGrid(RequestPage page,InfoSearchBean bean){
		return  infoNewsService.categoryDataGrid(page,bean);
	}
	
	/**
	 * 
	 * @Description:  选中某一类别后下的所有新闻资讯
	 * @author yanghui 
	 * @Created 2014-3-20
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("newsStatistics")
	public String newsStatistics(Model model,@RequestParam(value="infoCateId") Long id){
		model.addAttribute("infoCateId", id);
		return PREFIX+"/newsStatistics";
	}
	
	@RequestMapping("statisticsDataGrid")
	@ResponseBody
	public DataGrid statisticsDataGrid(RequestPage page,InfoSearchBean bean){
		return  infoNewsService.statisticsDataGrid(page,bean);
	}
	
	
	
	
	/**
	 * 
	 * @Description: 资讯评论统计页面
	 * @author yanghui 
	 * @Created 2014-3-20
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("commentDetail")
	public String commentDetail(Model model,@RequestParam(value="id") Long id){
		model.addAttribute("newsId", id);
		return PREFIX+"/newsComment";
	}
	
	/**
	 * 
	 * @Description: 资讯评论列表
	 * @author yanghui 
	 * @Created 2014-3-20
	 * @param page
	 * @param bean
	 * @param newsId
	 * @return
	 */
	@RequestMapping("commentDataGrid")
	@ResponseBody
	public DataGrid commentDataGrid(RequestPage page,InfoSearchBean bean,@RequestParam(value="newsId") Long newsId){
		return newsCommentService.commentDataGrid(page,bean,newsId);
	}
	
	/**
	 * 
	 * @Description: 资讯分享统计页面
	 * @author yanghui 
	 * @Created 2014-3-20
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("shareDetail")
	public String shareDetail(Model model,@RequestParam(value="id") Long id){
		model.addAttribute("newsId", id);
		return PREFIX+"/newsShare";
	}
	
	@RequestMapping("shareDataGrid")
	@ResponseBody
	public DataGrid shareDataGrid(RequestPage page,InfoSearchBean bean,@RequestParam(value="newsId") Long newsId){
		
		return infoNewsService.shareDataGrid(page,bean,newsId);
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
	
	@RequestMapping("loadPic")
	public void loadPic(@RequestParam(value="picPath") String picPath,HttpServletRequest request, HttpServletResponse response){
	//    String picPath = request.getParameter("picPath");
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
