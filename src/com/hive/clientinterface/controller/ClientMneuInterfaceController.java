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

import com.hive.common.SystemCommon_Constant;
import com.hive.systemconfig.entity.ClientMenu;
import com.hive.systemconfig.entity.VersionCategory;
import com.hive.systemconfig.service.ClientMenuService;
import com.hive.systemconfig.service.VersionCategoryService;

import dk.controller.BaseController;

/**  
 * Filename: ClientMneuInterfaceController.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  YangHui
 * @version: 1.0  
 * @Create:  2014-9-3  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-9-3 上午9:25:10				YangHui 	1.0
 */
@Controller
@RequestMapping("interface/clientMenu")
public class ClientMneuInterfaceController extends BaseController {

	@Resource
	private ClientMenuService clientMenuService;
	@Resource
	private VersionCategoryService versionCategoryService;
	
	@RequestMapping(value="clientMenuList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> clientMenuList(@RequestParam(value="flag") String flag,@RequestParam(value="versionNo",required=false) int versionNo){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<ClientMenu> list = new ArrayList<ClientMenu>();
		VersionCategory v  = new VersionCategory();
		if("1".equals(flag)){
			//企业版
			v = versionCategoryService.getVersionByVersionCate(SystemCommon_Constant.V_CLIENT_MENU_1);
		}else if("2".equals(flag)){
			//大众版
			v = versionCategoryService.getVersionByVersionCate(SystemCommon_Constant.V_CLIENT_MENU_2);
		}
		if(versionNo==1){ //是第一次加载
			list = clientMenuService.getValidList(flag);
			resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
			resultMap.put(SystemCommon_Constant.RESULT_KEY_VERSION, v.getVersionNo());
		}else{ //不是第一次加载 
			if(v!=null){
				int currentVersionNo = v.getVersionNo();
				if(currentVersionNo != versionNo){ //版本号不相同，此时需要重新加载数据
					list = clientMenuService.getValidList(flag);
					resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
					resultMap.put(SystemCommon_Constant.RESULT_KEY_VERSION,v.getVersionNo());
				}else{
					resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_FAIL);
				}
			}
		}
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, list);
		return resultMap;
		
	}
	
	
	
	
	@RequestMapping(value="clientMenu",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> clientMenu(){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<ClientMenu> list = new ArrayList<ClientMenu>();
		list = clientMenuService.getList();
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, list);
		return resultMap;
		
	}
}
