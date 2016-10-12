/**
 * 
 */
package com.hive.clientinterface.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.clientinterface.service.InfoCategoryInterfaceService;
import com.hive.common.SystemCommon_Constant;
import com.hive.systemconfig.entity.VersionCategory;
import com.hive.systemconfig.service.ClientMenuService;
import com.hive.systemconfig.service.VersionCategoryService;

import dk.controller.BaseController;

/**  
 * Filename: VersionInterfaceController.java  
 * Description: 版本信息控制类
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  YangHui
 * @version: 1.0  
 * @Create:  2014-9-4  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-9-4 下午3:19:52				YangHui 	1.0
 */
@Controller
@RequestMapping("interface/version")
public class VersionInterfaceController extends BaseController {
	
	@Resource
	private VersionCategoryService versionCategoryService;
	@Resource
	private ClientMenuService clientMenuService;
	@Resource
	private InfoCategoryInterfaceService infoCategoryInterfaceService;
	/**
	 * 
	 * @Description: 取得不同版本类别的版本号，返回给客户端进行比较，是否需要更新操作
	 * @author YangHui 
	 * @Created 2014-9-4
	 * @param versionCate
	 * @return
	 */
	@RequestMapping(value="checkVersion",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> checkVersion(@RequestParam(value="versionCate") String versionCate){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		VersionCategory v = versionCategoryService.getVersionByVersionCate(versionCate);
		if(v!=null){
			resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, v.getVersionNo());
		}else{
			resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, new Integer(1));
		}
		return resultMap;
	}
	
	
	
	/**
	 * 
	 * @Description: 返回需要更新的数据列表
	 * @author YangHui 
	 * @Created 2014-9-4
	 * @param versionCate
	 * @return
	 */
	@RequestMapping(value="getCateList",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getCateList(@RequestParam(value="versionCate") String versionCate){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List list = new ArrayList();
		if(SystemCommon_Constant.V_CLIENT_MENU_1.equals(versionCate)){ //客户端菜单-企业版
			list = clientMenuService.getValidList(SystemCommon_Constant.CLIENT_VERSION_TYPE_1);
		}else if(SystemCommon_Constant.V_CLIENT_MENU_2.equals(versionCate)){ //客户端菜单-大众版
			list = clientMenuService.getValidList(SystemCommon_Constant.CLIENT_VERSION_TYPE_2);
		}else if(SystemCommon_Constant.V_VOTE.equals(versionCate)){ //质量评价-问卷类别
			list = infoCategoryInterfaceService.getSurveyCategoryList();
		}else if(SystemCommon_Constant.V_INFOCATE.equals(versionCate)){ //信息类别
			list = infoCategoryInterfaceService.getAllInfoCategory();
		}else if(SystemCommon_Constant.V_VOTE_INDUSTRY.equals(versionCate)){ //问卷行业类别
			list = infoCategoryInterfaceService.getSurveyIndustryList();
		}
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, list);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		return resultMap;
	}

}
