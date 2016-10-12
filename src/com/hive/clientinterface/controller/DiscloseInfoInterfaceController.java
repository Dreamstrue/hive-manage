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

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.hive.clientinterface.service.DiscloseInfoInterfaceService;
import com.hive.common.AnnexFileUpLoad;
import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Annex;
import com.hive.common.service.AnnexService;
import com.hive.discloseInfo.entity.DiscloseInfo;
import com.hive.discloseInfo.entity.DiscloseInfoReply;
import com.hive.discloseInfo.model.DiscloseInfoBean;
import com.hive.discloseInfo.model.DiscloseInfoReplyBean;
import com.hive.util.DataUtil;
import com.hive.util.DateUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**  
 * Filename: DiscloseInfoController.java  
 * Description:  爆料信息 客户端接口
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  YangHui
 * @version: 1.0  
 * @Create:  2014-7-11  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-7-11 上午10:36:57				YangHui 	1.0
 */
@Controller
@RequestMapping("interface/disclose")
public class DiscloseInfoInterfaceController extends BaseController {
	
	/**
	 * 爆料或评论存在图片的目录
	 */
	private static final String BUSINESS_DIR = "disclose";
	/**
	 * 图片存在到附件表中对应的表名
	 */
	private static final String TABLE_NAME = "M_DISCLOSE";
	private static final String TABLE_NAME_REPLY = "M_DISCLOSE_REPLY";
	
	
	@Resource
	private DiscloseInfoInterfaceService discloseInfoInterfaceService;
	
	@Resource
	private AnnexService annexService;
	
	/**
	 * 
	 * @Description: 爆料列表
	 * @author YangHui 
	 * @Created 2014-7-11
	 * @param page
	 * @return
	 */
	@RequestMapping(value="queryList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> queryList(RequestPage page){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		DataGrid dataGrid = discloseInfoInterfaceService.getDiscloseInfoList(page);
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_TOTAL, dataGrid.getTotal());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, dataGrid.getRows());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_PAGENO, page.getPage());
		return resultMap;
	}
	
	
	
	/**
	 * 
	 * @Description: 新增爆料信息
	 * @author YangHui 
	 * @Created 2014-7-11
	 * @param page
	 * @return
	 */
	@RequestMapping(value="saveInfo",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveDiscloseInfo(HttpServletRequest request,@RequestParam(value="parame") String parame,@RequestParam(value="fileCount",required=false) int count){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		Gson gson = new Gson();
		DiscloseInfo info = gson.fromJson(parame, DiscloseInfo.class);
		//爆料时间
		info.setCreateTime(DateUtil.getCurrentTime());
		
		List<MultipartFile> list = new ArrayList<MultipartFile>();
		if(count>0){
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap(); 
			if(fileMap.size()>0){
				info.setIsPic(SystemCommon_Constant.SIGN_YES_1); //存在图片
				for(Map.Entry<String,MultipartFile> entity:fileMap.entrySet()){
					MultipartFile file = entity.getValue();
					list.add(file);
				}
			}
		}else{
			info.setIsPic(SystemCommon_Constant.SIGN_YES_0);
		}
		
		
		/*if(count>0){
			info.setIsPic(SystemCommon_Constant.SIGN_YES_1); //存在图片
			for(int i=0;i<count;i++){
				list.add((MultipartFile)multipartRequest.getFile("file"+i));
			}
		}else info.setIsPic(SystemCommon_Constant.SIGN_YES_0);*/
		
		discloseInfoInterfaceService.saveDiscloseInfo(info);
		
		if(count>0){
			HttpSession session = null;
			List<Annex> alist = AnnexFileUpLoad.uploadFile(list, session, info.getId(), TABLE_NAME, BUSINESS_DIR, "");
			annexService.saveAnnexList(alist, "");
		}
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, info);
		return resultMap;
	}
	
	
	
	/**
	 * 
	 * @Description: 新增爆料评论信息
	 * @author YangHui 
	 * @Created 2014-7-11
	 * @param page
	 * @return
	 */
	@RequestMapping(value="saveReply",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveReply(HttpServletRequest request,@RequestParam(value="parame") String parame,@RequestParam(value="discloseId") Long discloseId,@RequestParam(value="userId",required=false) Long replyUserId){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		Gson gson = new Gson();
		DiscloseInfoReply reply = new DiscloseInfoReply();
		//爆料时间
		reply.setReplyTime(DateUtil.getCurrentTime());
		reply.setContent(parame);
		reply.setDiscloseId(discloseId);  
		reply.setReplyUserId(replyUserId);
		
		reply.setIsPic(SystemCommon_Constant.SIGN_YES_0);
		
		discloseInfoInterfaceService.saveDiscloseReply(reply);
		
		DiscloseInfoReplyBean bean = new DiscloseInfoReplyBean();
		try {
			PropertyUtils.copyProperties(bean, reply);
			bean.setReplyUserName(discloseInfoInterfaceService.getUserName(replyUserId));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, bean);
		return resultMap;
	}
	
	
	/**
	 * 
	 * @Description: 爆料信息明细
	 * @author YangHui 
	 * @Created 2014-7-11
	 * @param id
	 * @return
	 */
	@RequestMapping(value="queryInfo",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> queryInfo(@RequestParam(value="id") Long id){
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		DiscloseInfo info = new DiscloseInfo();
		info = discloseInfoInterfaceService.get(id);
		DiscloseInfoBean bean  = new DiscloseInfoBean();
		if(info!=null){
			try {
				PropertyUtils.copyProperties(bean, info); //爆料信息
				//爆料的图片信息
				List<Annex> alist = annexService.getAnnexInfoByObjectId(id, TABLE_NAME);
				bean.setAnnexList(alist);
				
				//评论数量
				bean.setReplyNum(discloseInfoInterfaceService.getReplyNumByDiscloseId(info.getId()));
				/*//评论列表
				List<DiscloseInfoReplyBean> replyList = discloseInfoInterfaceService.getReplyList(id,TABLE_NAME_REPLY);
				bean.setReplyList(replyList);*/
				
				bean.setContent(DataUtil.getText(bean.getContent()));
				
				
				resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
				resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
				resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, bean);
			} catch (Exception e) {
				resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_FAIL);
				resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "服务器内部错误");
			}
		}else{
			resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_FAIL);
			resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "爆料信息不存在");
		}
		
		return resultMap;
	}
	
	
	/**
	 * 
	 * @Description: 查询爆料信息的评论列表
	 * @author YangHui 
	 * @Created 2014-7-14
	 * @param id
	 * @return
	 */
	@RequestMapping(value="queryReplyList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> queryReplyList(RequestPage page,@RequestParam(value="id") Long id){
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		DataGrid dataGrid = discloseInfoInterfaceService.getReplyInfoList(page,id,TABLE_NAME_REPLY);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_TOTAL, dataGrid.getTotal());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, dataGrid.getRows());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_PAGENO, page.getPage());
		return resultMap;
	}
	
}
