/**
 * 
 */
package com.hive.subscription.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.common.SystemCommon_Constant;
import com.hive.subscription.entity.InfoSubscription;
import com.hive.subscription.model.SubscriptionSearchBean;
import com.hive.subscription.service.InfoSubscriptionService;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**  
 * Filename: InfoSubscriptionController.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-2-27  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-2-27 上午11:40:29				yanghui 	1.0
 */
@Controller
@RequestMapping("subscription")
public class InfoSubscriptionController extends BaseController {

	private static final String PREFIX = "infosubscription";
	
	@Resource
	private InfoSubscriptionService subscriptionService;
	
	/**
	 * 
	 * @Description: 信息订阅管理页面
	 * @author yanghui 
	 * @Created 2014-2-27
	 * @return
	 */
	@RequestMapping("subManage")
	public String subManage(){
		return PREFIX+"/subscription";
	}
	
	/**
	 * 
	 * @Description: 信息订阅信息
	 * @author yanghui 
	 * @Created 2014-2-27
	 * @param page
	 * @param bean
	 * @return
	 */
	@RequestMapping("subDataGrid")
	@ResponseBody
	public DataGrid subDataGrid(RequestPage page,SubscriptionSearchBean bean){
		return subscriptionService.dataGrid(page,bean);
	}
	
	@RequestMapping("subDetail")
	public String subDetail(Model model,@RequestParam(value="userId") Long userId){
		model.addAttribute("userId", userId);
		return PREFIX+"/subscriptionDetail";
	}
	
	
	@RequestMapping("subdetailDataGrid")
	@ResponseBody
	public DataGrid subdetailDataGrid(RequestPage page,SubscriptionSearchBean bean,@RequestParam(value="userId") Long userId){
		return subscriptionService.detailDataGrid(page,bean,userId);
	}
	
	/**
	 * 
	 * @Description:  管理员帮助用户退订某类信息
	 * @author yanghui 
	 * @Created 2014-2-27
	 * @param userId
	 * @param cateId
	 * @return
	 */
	@RequestMapping("unsubInfo")
	@ResponseBody
	public Map<String,Object> unsubInfo(@RequestParam(value="ids") String ids){
		if(StringUtils.isNotEmpty(ids)){
			String[] str = ids.split(",");
			for(int i=0;i<str.length;i++){
				InfoSubscription info = subscriptionService.get(Long.valueOf(str[i]));
				if(info!=null){
					info.setSubStatus(SystemCommon_Constant.INFO_SIGN_NO);
				}
				subscriptionService.update(info);
			}
		}
		return success("退订成功");
	}
	
	
	/**
	 * 
	 * @Description: 信息退订管理页面
	 * @author yanghui 
	 * @Created 2014-2-27
	 * @return
	 */
	@RequestMapping("unsubManage")
	public String unsubManage(){
		return PREFIX+"/unsubscription";
	}
	
	
	@RequestMapping("unsubDataGrid")
	@ResponseBody
	public DataGrid unsubDataGrid(RequestPage page,SubscriptionSearchBean bean){
		return subscriptionService.unsubDataGrid(page,bean);
	}
	
	
	
	/**
	 * 
	 * @Description: 信息统计管理页面
	 * @author yanghui 
	 * @Created 2014-2-27
	 * @return
	 */
	@RequestMapping("statisticsManage")
	public String statisticsManage(){
		return PREFIX+"/statistics";
	}
	
	
	@RequestMapping("staDataGrid")
	@ResponseBody
	public DataGrid staDataGrid(RequestPage page,SubscriptionSearchBean bean){
		return subscriptionService.staDataGrid(page,bean);
	}
	
}
