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
import com.hive.common.AnnexFileUpLoad;
import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Annex;
import com.hive.common.service.AnnexService;
import com.hive.complain.entity.ComplainInfo;
import com.hive.complain.entity.ComplainPerson;
import com.hive.complain.model.ComplainBean;
import com.hive.complain.service.ComplainInfoService;
import com.hive.complain.service.ComplainPersonService;
import com.hive.membermanage.entity.MMember;
import com.hive.membermanage.service.MembermanageService;
import com.hive.util.DataUtil;
import com.hive.util.DateUtil;

import dk.controller.BaseController;
/**
 * 
* Filename: ComplainInterfaceController.java  
* Description: 投诉系统接口
* Copyright:Copyright (c)2014
* Company:  GuangFan 
* @author:  YangHui
* @version: 1.0  
* @Create:  2014-6-30  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2014-6-30 下午3:48:36				YangHui 	1.0
 */
@Controller
@RequestMapping("interface/complain")
public class ComplainInterfaceController extends BaseController {
	
	@Resource
	private ComplainPersonService complainPersonService;
	@Resource
	private ComplainInfoService complainInfoService;
	@Resource
	private MembermanageService memberManageService;
	@Resource
	private AnnexService annexService;
	
	
	
	private static final String TABLE_NAME = SystemCommon_Constant.COMPLAIN_INFO;
	private static final String BUSSINESS_DIR ="complain";
	/**
	 * 
	 * @Description: 保存投诉信息（实名和匿名投诉）
	 * @author YangHui 
	 * @Created 2014-6-30
	 * @param parame
	 * @return
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveComplain(HttpServletRequest request,@RequestParam(value="parame",required=false) String parame,@RequestParam(value="fileCount",required=false) int count ){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		List<MultipartFile> list = new ArrayList<MultipartFile>();
		if(count>0){
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
			for(int i=1;i<count;i++){
				list.add((MultipartFile)multipartRequest.getFile("file"+i));
			}
		}
		
		Gson gson = new Gson();
		ComplainBean bean = gson.fromJson(parame, ComplainBean.class);
		
		ComplainInfo info = new ComplainInfo();
		if(!DataUtil.isNull(bean.getUserId())){
			//实名投诉
			try {
				ComplainPerson person = new ComplainPerson();
				
				PropertyUtils.copyProperties(person, bean);
				PropertyUtils.copyProperties(info, bean);
				
				
				info.setComDate(DateUtil.getTimestamp());
				complainInfoService.save(info);
				
				
				person.setComplainId(info.getId());
				complainPersonService.save(person);
				
				
			} catch (Exception e) {
			}
			
			
		}else{
			//匿名投诉
			try {
				PropertyUtils.copyProperties(info, bean);
				String searchCode = DataUtil.getCharAndNumr(6); //随机查询码
				searchCode = checkSearchCode(searchCode);
				info.setSearchCode(searchCode);
				info.setComDate(DateUtil.getTimestamp());
				complainInfoService.save(info);		
			} catch (Exception e) {
			}
		}
		
		
		//保存上传的图片信息
		if(DataUtil.listIsNotNull(list)){
			HttpSession session = null;
			List<Annex> alist = AnnexFileUpLoad.uploadFile(list, session, info.getId(), TABLE_NAME, BUSSINESS_DIR, "");
			annexService.saveAnnexList(alist, "");
		}
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, info);
		return resultMap;
	}
	
	
	/**
	 * 
	 * @Description: 校验查询码是否存在
	 * @author YangHui 
	 * @Created 2014-6-30
	 * @param searchCode
	 * @return
	 */
	public String checkSearchCode(String searchCode){
		boolean flag = false;
		flag = complainInfoService.checkSearchCode(searchCode);
		if(flag){
			searchCode = DataUtil.getCharAndNumr(6);
			checkSearchCode(searchCode);
		}
		return searchCode;
	}
	
	/**
	 * 
	 * @Description: 匿名投诉查询 （匿名根据查询码查询，实名根据用户ID查询）
	 * @author YangHui 
	 * @Created 2014-6-30
	 * @param queryCode
	 * @param complainId  投诉记录的ID
	 * @return
	 */
	@RequestMapping(value="query",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> query(@RequestParam(value="code",required=false) String searchCode,@RequestParam(value="complainId",required=false) Long complainId){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		ComplainBean bean = new ComplainBean();
		ComplainInfo info   = new  ComplainInfo();
		if(!DataUtil.isEmpty(searchCode)){
			info = complainInfoService.queryBySearchCode(searchCode);
		}else if(!DataUtil.isNull(complainId)){
			info = complainInfoService.get(complainId);
			ComplainPerson person = complainPersonService.getPersonByComplainId(complainId);
			try {
				PropertyUtils.copyProperties(bean, person);
			} catch (Exception e) {
			}
		}
		if(info!=null){
			try {
				PropertyUtils.copyProperties(bean, info);
			} catch (Exception e) {
			}
		}

		
		//取得附件列表
		List<Annex> list = annexService.getAnnexListByObjectId(bean.getId(), TABLE_NAME);
		bean.setAnnexList(list);
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, bean);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		return resultMap;
	}
	
	/**
	 * 
	 * @Description:实名投诉查询 （匿名根据查询码查询，实名根据用户ID查询）
	 * @author YangHui 
	 * @Created 2014-6-30
	 * @param parame
	 * @return
	 */
	@RequestMapping(value="queryList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> queryList(@RequestParam(value="userId") Long userId){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<ComplainInfo> list = complainInfoService.getComplainInfoListByUserId(userId);
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, list);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, ""); 
		return resultMap;
	}
	
	
	/**
	 * 
	 * @Description: 填写投诉信息时，用户登录后，自动填充个人信息
	 * @author YangHui 
	 * @Created 2014-6-30
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="queryMemberInfo",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> queryMemberInfo(@RequestParam(value="userId") Long userId){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		MMember m = memberManageService.get(userId);
		ComplainPerson person = new ComplainPerson();
		person.setName(m.getChinesename());//姓名
		person.setSex(m.getCsex());//性别
		person.setCardNo(m.getCardno());//身份证号
		person.setOccupation(m.getOccupation());//职业
		person.setAddress(m.getCaddress());//地址
		person.setEmail(m.getCemail());//邮箱
		person.setPostCode(m.getZipcode());//邮编
		person.setPhone(m.getCmobilephone());//电话
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, person);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		return resultMap;
	}
	
	
}
