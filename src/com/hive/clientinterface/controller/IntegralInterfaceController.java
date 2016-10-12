package com.hive.clientinterface.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.clientinterface.service.IntegralInterfaceService;
import com.hive.common.SystemCommon_Constant;
import com.hive.integralmanage.entity.Integral;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;
/**
 * 
* Filename: IntegralInterfaceController.java  
* Description: 积分管理  客户端接口
* Copyright:Copyright (c)2014
* Company:  GuangFan 
* @author:  yanghui
* @version: 1.0  
* @Create:  2014-3-28  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2014-3-28 上午10:33:03				yanghui 	1.0
 */
@Controller
@RequestMapping("interface/integral")
public class IntegralInterfaceController extends BaseController {

	
	@Resource
	private IntegralInterfaceService integralInterfaceService;
	
	
	/**
	 * 
	 * @Description: 用户积分明细
	 * @author yanghui 
	 * @Created 2014-3-28
	 * @param page
	 * @param userId
	 * @return
	 */
	@RequestMapping("integralDetail")
	@ResponseBody
	public Map<String,Object> integralDetail(RequestPage page,@RequestParam(value="userId") Long userId){
		DataGrid dataGrid = integralInterfaceService.integralDetailDataGrid(page, userId);
		//得到该用户当前可用积分
		List integarList = integralInterfaceService.getIntegralList(userId);
		Long currentValue = new Long(0);
		if(integarList.size()>0){
			currentValue = ((Integral)integarList.get(0)).getCurrentValue();
		}
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_TOTAL, dataGrid.getTotal());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, dataGrid.getRows());
		resultMap.put("currentValue", currentValue);
		return resultMap;
	}
	
}
