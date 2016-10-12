/**
 * 
 */
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
import com.hive.clientinterface.service.PrizeInterfaceService;
import com.hive.common.AnnexFileUpLoad;
import com.hive.common.SystemCommon_Constant;
import com.hive.prizemanage.entity.PrizeInfo;
import com.hive.util.DataUtil;
import com.hive.util.PropertiesFileUtil;

import dk.controller.BaseController;

/**  
 * Filename: PrizeInterfaceController.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-4-3  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-4-3 上午9:08:25				yanghui 	1.0
 */
@Controller
@RequestMapping("interface/prize")
public class PrizeInterfaceController extends BaseController {
	
	@Resource
	private PrizeInterfaceService prizeInterfaceService;
	@Resource
	private IntegralInterfaceService integralInterfaceService;
	
	
	@RequestMapping("prizeList")
	@ResponseBody
	public Map<String,Object> prizeList(@RequestParam(value="userId",required=false) Long userId){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<PrizeInfo> infoList = prizeInterfaceService.getPrizeList();
		if(!DataUtil.isNull(userId)){
			//获得当前的可用积分  
			Long currentVlaue = integralInterfaceService.getCurrentIntegralByUserId(userId);
			resultMap.put(SystemCommon_Constant.CURRENT_INTEGRAL, currentVlaue);
		}
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, infoList);
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 奖品明细信息
	 * @author yanghui 
	 * @Created 2014-4-8
	 * @param prizeId
	 * @return
	 */
	@RequestMapping("prizeDetail")
	@ResponseBody
	public Map<String,Object> prizeDetail(@RequestParam(value="id",required=false) Long prizeId){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		PrizeInfo info = prizeInterfaceService.get(prizeId);
		PropertiesFileUtil  propertiesFileUtil = new PropertiesFileUtil();
		String servicePath = propertiesFileUtil.findValue("zxt_manage_service_path");  //客户端通过网络形式访问图片，这里就把服务器发布的地址写入
		byte[] b = AnnexFileUpLoad.getContentFromFile(info.getPrizeExplain());
		info.setPrizeExplain(new String(b).replace("/zxt-manage", servicePath));
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, info);
		return resultMap;
	}
	
}
