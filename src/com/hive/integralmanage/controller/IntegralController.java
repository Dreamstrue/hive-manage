/**
 * 
 */
package com.hive.integralmanage.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.common.AnnexFileUpLoad;
import com.hive.common.SystemCommon_Constant;
import com.hive.integralmanage.entity.Integral;
import com.hive.integralmanage.entity.IntegralOil;
import com.hive.integralmanage.entity.IntegralSub;
import com.hive.integralmanage.service.IntegralOilService;
import com.hive.integralmanage.service.IntegralService;
import com.hive.integralmanage.service.IntegralSubService;
import com.hive.intendmanage.entity.Intend;
import com.hive.intendmanage.entity.IntendRelPrize;
import com.hive.intendmanage.model.IntendBean;
import com.hive.intendmanage.service.IntendRelPrizeService;
import com.hive.intendmanage.service.IntendService;
import com.hive.membermanage.entity.MMember;
import com.hive.prizemanage.entity.PrizeInfo;
import com.hive.prizemanage.service.PrizeInfoService;
import com.hive.util.DateUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**  
 * Filename: IntegralController.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-11  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-11 上午9:32:47				yanghui 	1.0
 */
@Controller
@RequestMapping("integral")
public class IntegralController extends BaseController {

	private static final String PREFIX = "integralmanage";
	
	@Resource
	private IntegralService integralService;
	@Resource
	private IntegralOilService integralOilService;
	@Resource
	private IntegralSubService integralSubService;
	@Resource
	private IntendService intendService; //订单service
	@Resource
	private PrizeInfoService prizeInfoService;
	@Resource
	private IntendRelPrizeService intendRelPrizeService;
	/**
	 * 
	 * @Description: 积分管理页面
	 * @author yanghui 
	 * @Created 2014-3-11
	 * @return
	 */
	@RequestMapping("manage")
	public String manage(){
		return PREFIX+"/integralManage";
	}
	/**
	 * 
	 * @Description: 加油站积分管理页面
	 * @author yanghui 
	 * @Created 2014-3-11
	 * @return
	 */
	@RequestMapping("oilmanage")
	public String oilmanage(){
		return PREFIX+"/integralOilManage";
	}
	/**
	 * 
	 * @Description: 积分管理统计方法
	 * @author yanghui 
	 * @Created 2014-3-11
	 * @param page
	 * @param userName
	 * @return
	 */
	@RequestMapping("integralDataGrid")
	@ResponseBody
	public DataGrid integralDataGrid(RequestPage page,MMember mmember){
		return integralService.integralDataGrid(page,mmember);
	}
	
	/**
	 * 
	 * @Description: 已消费积分统计页面
	 * @author yanghui 
	 * @Created 2014-3-11
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping("usedDetail")
	public String usedDetail(Model model,@RequestParam(value="userId") Long userId){
		model.addAttribute("userId", userId);
		return PREFIX+"/usedIntegralManage";
	}
	
	/**
	 * 
	 * @Description: 已消费积分统计方法
	 * @author yanghui 
	 * @Created 2014-3-11
	 * @param page
	 * @param userId
	 * @return
	 */
	@RequestMapping("usedIntegralDataGrid")
	@ResponseBody
	public DataGrid usedIntegralDataGrid(RequestPage page,@RequestParam(value="userId") Long userId){
		return integralService.usedIntegralDataGrid(page,userId);
	}
	/**
	 * 
	 * @Description: 加油站积分管理统计方法
	 * @author zhaopf
	 * @param page
	 * @param userName
	 * @return
	 */
	@RequestMapping("integralOilDataGrid")
	@ResponseBody
	public DataGrid integralOilDataGrid(RequestPage page,MMember mmember){
		return integralOilService.integralOilDataGrid(page,mmember);
	}
	
	/**
	 * 
	 * @Description: 添加积分总表记录
	 * @author yanghui 
	 * @Created 2014-3-11
	 * @param integral
	 * @return
	 */
	@RequestMapping("insertIntegral")
	@ResponseBody
	public Map<String,Object> insertIntegral(Integral integral){
		 
		integralService.save(integral);
		return success("添加成功");
	}
	
	/**
	 * 
	 * @Description: 修改积分总表记录
	 * @author yanghui 
	 * @Created 2014-3-11
	 * @param integral
	 * @return
	 */
	@RequestMapping("updateIntegral")
	@ResponseBody
	public Map<String,Object> updateIntegral(Integral integral){
		 
		integralService.update(integral);
		return success("添加成功");
	}
	
	/**
	 * 
	 * @Description:  添加积分子表（明细）记录
	 * @author yanghui 
	 * @Created 2014-3-11
	 * @param sub
	 * @return
	 */
	@RequestMapping("insertIntegralSub")
	@ResponseBody
	public Map<String,Object> insertIntegralSub(IntegralSub sub){
		
		integralSubService.save(sub);
		//积分明细表保存后，需要修改该用户对应的总表信息
		Integral integral = integralService.getIntegralByUserId(sub.getUserId());
		if(integral==null){
			//说明是该用户第一次获得积分
			integral.setUserId(sub.getUserId());
			integral.setCurrentValue(sub.getTotalValue());
			integral.setUsedValue(new Long(0));
			integralService.save(integral);
		}else{
			Long currentValue = integral.getCurrentValue();  //原来的当前积分  
			currentValue = currentValue+ sub.getTotalValue();  //本次获得的总积分+原来的当前积分 = 现在的当前积分
			integral.setCurrentValue(currentValue);
			integralService.update(integral);
		}
		return success("添加成功");
	}
	
	
	/**
	 * 
	 * @Description: 积分明细页面（包括获取与消费记录）
	 * @author yanghui 
	 * @Created 2014-3-27
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping("integralDetail")
	public String integralDetail(Model model,@RequestParam(value="userId") Long userId){
		model.addAttribute("userId", userId);
		return PREFIX+"/integralDetail";
	}
	/**
	 * 
	 * @Description: 积分明细列表
	 * @author yanghui 
	 * @Created 2014-3-27
	 * @param page
	 * @param userId
	 * @return
	 */
	@RequestMapping("integralDetailDataGrid")
	@ResponseBody
	public DataGrid integralDetailDataGrid(RequestPage page,@RequestParam(value="userId") Long userId){
		return integralService.integralDetailDataGrid(page,userId);
	}
	
}
