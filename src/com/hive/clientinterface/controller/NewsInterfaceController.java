/**
 * 
 */
package com.hive.clientinterface.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.hive.clientinterface.service.IntegralInterfaceService;
import com.hive.clientinterface.service.NewsInterfaceService;
import com.hive.clientmanage.service.ClientUserService;
import com.hive.common.AnnexFileUpLoad;
import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Annex;
import com.hive.common.service.AnnexService;
import com.hive.contentmanage.entity.CommonContent;
import com.hive.contentmanage.entity.Picturenews;
import com.hive.contentmanage.service.CommonContentService;
import com.hive.contentmanage.service.PictureNewsService;
import com.hive.infomanage.entity.InfoNotice;
import com.hive.infomanage.entity.NewsComment;
import com.hive.infomanage.model.NewsCommentBean;
import com.hive.infomanage.service.InfoNoticeService;
import com.hive.infomanage.service.NewsCommentService;
import com.hive.integralmanage.entity.Integral;
import com.hive.membermanage.entity.MMember;
import com.hive.membermanage.service.MembermanageService;
import com.hive.util.DataUtil;
import com.hive.util.DateUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**  
 * Filename: NewsInterfaceController.java  
 * Description:   新闻资讯 客户端接口 
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-25  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-25 上午11:05:16				yanghui 	1.0
 */
@Controller
@RequestMapping("interface/news")
public class NewsInterfaceController extends BaseController {
	private static final String OBJECT_TABLE = "F_COMMONCONTENT";
	private static final String OBJECT_TABLE_PICTURE = "F_PICTURENEWS";
	
	
	/**
	 * 咨询动态评论上传图片保存附件时所属的对象表名
	 */
	public static final String COMMENT_TABLE_NAME = "INFO_NEWS_COMMENT";
	/**
	 * 咨询动态评论上传图片在磁盘中存放的目录名
	 */
	public static final String COMMENT_BUSINESS_DIR = "comment";
	
	@Resource
	private NewsInterfaceService newsInterfaceService;
	@Resource
	private NewsCommentService newsCommentService;
	@Resource
	private AnnexService annexService;
	@Resource
	private IntegralInterfaceService integralInterfaceService;
	@Resource
	private ClientUserService clientUserService;
	@Resource
	private InfoNoticeService infoNoticeService;
	@Resource
	private PictureNewsService pictureNewsService;
	@Resource
	private MembermanageService membermanageService;
	@Resource
	private CommonContentService commonContentService;
	/**
	 * 
	 * @Description: 新闻资讯客户端列表
	 * @author yanghui 
	 * @Created 2014-3-25
	 * @param page
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="newsList", method=RequestMethod.GET)
	@ResponseBody

	public Map<String,Object> newsList(RequestPage page,@RequestParam(value="infoCateId",required=false) Long infoCateId){
		DataGrid dataGrid = newsInterfaceService.dataGrid(page,infoCateId);
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_TOTAL, dataGrid.getTotal());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, dataGrid.getRows());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_PAGENO, page.getPage());
		return resultMap;
	}
	
	/**
	 * 
	 * @Description:  查看新闻资讯明细
	 * @author yanghui 
	 * @Created 2014-3-25
	 * @param id
	 * @return
	 */
	@RequestMapping(value="newsDetail",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> newsDetail(@RequestParam(value="id") Long id){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		CommonContent cc = null;
		Picturenews p = null;
		List annexList = null;
		if(id!=null && id!=0){
			//由于消费资讯中存在图片新闻类别，该类别不属于通用内容管理部分，所以需要特殊处理
			cc =  newsInterfaceService.get(id);
			if(cc==null){
				//说明从通用内容管理表中没有查询到数据，那么就从图片新闻表里查询
				p = pictureNewsService.get(id);
				byte[] b = AnnexFileUpLoad.getContentFromFile(p.getContent());
				p.setContent(new String(b));
				
				annexList = annexService.getAnnexListByObjectId(id,OBJECT_TABLE_PICTURE);
				
				resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, p);
			}else{
				byte[] b = AnnexFileUpLoad.getContentFromFile(cc.getContent());
				cc.setContent(new String(b));
				annexList = annexService.getAnnexListByObjectId(id,OBJECT_TABLE);
				
				resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, cc);
			}
		}
		//判断该明细是否存在附件
		if(annexList!=null && annexList.size()>0){
			resultMap.put("annexList", annexList);
		}
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		return resultMap;
	}
	
	
	/**
	 * 
	 * @Description:  获取新闻资讯下的所有评论内容
	 * @author yanghui 
	 * @Created 2014-3-25
	 * @param newsId
	 * @return
	 */
	@RequestMapping(value="newsCommentList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object>newsComment(RequestPage page,@RequestParam(value="id") Long newsId){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//获取新闻资讯下的评论列表
		DataGrid dataGrid = newsCommentService.getCommentContentByNewsId(page,newsId);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_TOTAL, dataGrid.getTotal());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, dataGrid.getRows());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_PAGENO, page.getPage());
		return resultMap;
	}
	
	/**
	 * 
	 * @Description:  保存对新闻资讯的评论内容
	 * @author yanghui 
	 * @Created 2014-3-31
	 * @param bean
	 * @return
	 */
	@RequestMapping(value="saveComment",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveComment(HttpServletRequest request,NewsCommentBean bean,@RequestParam(value="count",required=false) int count){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<MultipartFile> list = new ArrayList<MultipartFile>();
		if(count>0){
			MultipartHttpServletRequest mr = (MultipartHttpServletRequest)request;
			for(int i=0;i<count;i++){
				list.add((MultipartFile)mr.getFile("file"+i));
			}
		}
		
		
		Gson gson = new Gson();
	//	NewsCommentBean bean = gson.fromJson(parame, NewsCommentBean.class);
		
		
		//保存新的评论内容
		NewsComment nc = new NewsComment();
		nc.setCommentDate(DateUtil.getTimestamp());
		nc.setContent(bean.getContent());
		nc.setUserId(bean.getUserId());
		nc.setNewsId(bean.getNewsId());
	//	nc.setEntityName(bean.getEntityName());
		newsCommentService.save(nc);
		
		
		//保存评论信息图片
		if(DataUtil.listIsNotNull(list)){
			//保存图片信息
			HttpSession session = null;
			List<Annex> alist = AnnexFileUpLoad.uploadFile(list, session, nc.getId(), COMMENT_TABLE_NAME, COMMENT_BUSINESS_DIR, "");
			annexService.saveAnnexList(alist, "");
			bean.setAnnexList(alist);
		}
		
		
		MMember m = membermanageService.get(bean.getUserId());
		if("1".equals(m.getCmembertype())){
			bean.setUserName(m.getConciseName());
		}else{
			bean.setUserName(m.getCnickname());
		}
		bean.setCommentDate(DateUtil.getTimestamp());
		bean.setId(nc.getId());
		bean.setHeadPicPath(m.getCavatarpath());
		//评论信息保存后，修改新闻资讯的评论数量,针对这条别评论的新闻，不知道属于图片新闻，通知公告还是通用内容管理，这里需要一一判断处理
		Long newsId = bean.getNewsId();
		Object o = null ;
		o = newsInterfaceService.getObjectById("F_COMMONCONTENT",newsId);
		if(o==null){
			o = newsInterfaceService.getObjectById("F_PICTURENEWS",newsId);
			if(o==null){
				o = newsInterfaceService.getObjectById("INFO_NOTICE",newsId);
				if(o!=null){
					InfoNotice i = (InfoNotice)o;
					i.setCommentNum(i.getCommentNum()+1);
					infoNoticeService.update(i);
				}
			}else{
				Picturenews i = (Picturenews)o;
				i.setCommentNum(i.getCommentNum()+1);
				pictureNewsService.update(i);
			}
		}else{
			CommonContent info = (CommonContent)o;
			info.setCommentNum(info.getCommentNum()+1);
			newsInterfaceService.update(info);
		}
		/*if("INFO_NOTICE".equals(bean.getEntityName())){
			//通知公告评论
			InfoNotice i = infoNoticeService.get(newsId);
			i.setCommentNum(i.getCommentNum()+1);
			infoNoticeService.update(i);
		}else if("F_PICTURENEWS".equals(bean.getEntityName())){
			//通知公告评论
			Picturenews i = pictureNewsService.get(newsId);
			i.setCommentNum(i.getCommentNum()+1);
			pictureNewsService.update(i);
		}else if("F_COMMONCONTENT".equals(bean.getEntityName()) || "".equals(bean.getEntityName())){
			CommonContent info = newsInterfaceService.get(newsId);
			info.setCommentNum(info.getCommentNum()+1);
			newsInterfaceService.update(info);
		}*/
		//修改用户的积分以及积分记录表
		Integral integral = integralInterfaceService.updateUserIntegral(bean,SystemCommon_Constant.INTEGRAL_CATEGORY_COMMENT);
		//根据该用户的总积分（当前积分+已消费的积分）判断用户的会员等级
		clientUserService.updateUserGradeByTotalIntegral(bean,integral);
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, bean);
		return resultMap;
	}
	
	 
	
	@RequestMapping("updatePath")
	@ResponseBody
	public Map<String,Object> updatePath(){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<CommonContent> list = commonContentService.getCommonList();
		for (int i = 0; i < list.size(); i++) {

			CommonContent cc = list.get(i);
			String oldPath = cc.getContent();

			byte[] b = AnnexFileUpLoad.getContentFromFile(oldPath);
			cc.setContent(new String(b));

			// 取得新增时保存的文件路径（注意：路径保存在内容字段里）
			String filePath = AnnexFileUpLoad.writeContentToFile(
					cc.getContent(), "COMMONCONTENT", oldPath,"approve");

			cc.setContent(filePath);
			commonContentService.update(cc);

		}
		return resultMap;
	}
	
	
	@RequestMapping("updatePicture")
	@ResponseBody
	public Map<String,Object> updatePicture(){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Picturenews> list = pictureNewsService.getCommonList();
		for (int i = 0; i < list.size(); i++) {

			Picturenews cc = list.get(i);
			String oldPath = cc.getContent();

			byte[] b = AnnexFileUpLoad.getContentFromFile(oldPath);
			cc.setContent(new String(b));

			// 取得新增时保存的文件路径（注意：路径保存在内容字段里）
			String filePath = AnnexFileUpLoad.writeContentToFile(
					cc.getContent(), "COMMONCONTENT", oldPath,"approve");
			cc.setContent(filePath);
			pictureNewsService.update(cc);

		}
		return resultMap;
	}

}
