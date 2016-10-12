/**
 * 
 */
package com.hive.clientinterface.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.hive.clientinterface.service.IntegralInterfaceService;
import com.hive.clientinterface.service.IntendInterfaceService;
import com.hive.clientinterface.service.PrizeInterfaceService;
import com.hive.common.SystemCommon_Constant;
import com.hive.integralmanage.entity.Integral;
import com.hive.intendmanage.entity.Intend;
import com.hive.intendmanage.entity.IntendRelPrize;
import com.hive.intendmanage.model.IntendBean;
import com.hive.intendmanage.service.IntendRelPrizeService;
import com.hive.prizemanage.entity.PrizeInfo;
import com.hive.util.DateUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**  
 * Filename: IntendInterfaceController.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-4-4  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-4-4 上午10:13:11				yanghui 	1.0
 */
@Controller
@RequestMapping("interface/intend")
public class IntendInterfaceController  extends BaseController{
	
	@Resource
	private IntendInterfaceService intendInterfaceService;
	@Resource
	private IntendRelPrizeService intendRelPrizeService;
	@Resource
	private IntegralInterfaceService integralInterfaceService;
	@Resource
	private PrizeInterfaceService prizeInterfaceService;

	
	@RequestMapping(value="saveIntend",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveItend(@RequestParam(value="prizeorder") String prizeorder){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Gson gson = new Gson();
		IntendBean	bean = gson.fromJson(prizeorder,IntendBean.class);
	/*	IntendInterfaceThread intendThread = new IntendInterfaceThread(bean);
		Thread thread = new Thread(intendThread);
		thread.start();*/
		
		Integral integral = integralInterfaceService.getIntegralByUserId(bean.getApplyPersonId());
		Long currentValue = integral.getCurrentValue(); //该用户当前的积分
		Long usedIntegral = bean.getExcIntegral()*bean.getPrizeNum();  //本次消费的积分
		if(currentValue<usedIntegral){
			//说明当前的积分小于兑换奖品的积分
			resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_FAIL);
			resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "兑换失败，积分不足");
			resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, new Intend());
			return resultMap;
		}else{
			//自定义规则生产兑换订单号
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String ymd = sdf.format(new Date());
			String intendNo = ymd;
			//订单号 = 当前的日期（时分秒）+ 随机三为整数+当前用户的ID
			intendNo = intendNo + new Random().nextInt(1000)+String.valueOf(bean.getApplyPersonId());
			Intend intend = new Intend();
			intend.setIntendNo(intendNo); //订单号
			intend.setApplyTime(DateUtil.getTimestamp()); //订单申请时间
			intend.setIntendStatus(SystemCommon_Constant.INTEND_STATUS_1);  //订单状态
			intend.setConsignee(bean.getConsignee());  //收货人
			intend.setMobilePhone(bean.getMobilePhone());// 收货人电话
			intend.setAddress(bean.getAddress()); //收货人地址
			intend.setApplyPersonId(bean.getApplyPersonId()); // 订单申请人（客户端登录用户ID）
			intend.setRemark(bean.getRemark()); //订单备注
			
			intendInterfaceService.save(intend);
			
			//保存订单与奖品关联信息
			IntendRelPrize irp = new IntendRelPrize();
			irp.setPrizeId(bean.getPrizeId());
			irp.setPrizeNum(bean.getPrizeNum());
			irp.setPrizeCateId(bean.getPrizeCateId());
			irp.setExcIntegral(bean.getExcIntegral());
			irp.setIntendNo(intendNo);
			intendRelPrizeService.save(irp);
			
			//修改奖品兑换的数量
			PrizeInfo pinfo = prizeInterfaceService.get(bean.getPrizeId());
			Long excNum = pinfo.getExcNum();
			Long prizeNum = pinfo.getPrizeNum();
			excNum = excNum+bean.getPrizeNum();
			pinfo.setExcNum(excNum);
			pinfo.setPrizeNum(prizeNum-excNum); //剩余数量
			prizeInterfaceService.update(pinfo);
			
			//订单以及订单与奖品的关联信息都保存后，就需要修改该用户的积分信息
			Long usedValue = integral.getUsedValue();//该用户已经消费的积分
				//消费的积分为单个奖品需要的积分乘以奖品数量
			integral.setUsedValue(usedValue+usedIntegral);  //新的消费积分
			integral.setCurrentValue(currentValue-usedIntegral);  //新的当前积分
			integralInterfaceService.update(integral);
			
			resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
			resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "订单提交成功");
			resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, intend);
			
			return resultMap;
		}
	}
	
	
	/**
	 * 
	 * @Description: 
	 * @author yanghui 
	 * @Created 2014-4-8
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="IntendList",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> IntendList(RequestPage page,@RequestParam(value="userId",required=false) Long userId){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		DataGrid dataGrid = intendInterfaceService.getIntendList(page,userId);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_TOTAL, dataGrid.getTotal());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, dataGrid.getRows());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_PAGENO, page.getPage());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 订单明细
	 * @author yanghui 
	 * @Created 2014-4-8
	 * @param page
	 * @param id
	 * @return
	 */
	@RequestMapping(value="IntendDetail",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> IntendDetail(@RequestParam(value="id",required=false) Long id){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		IntendBean bean = intendInterfaceService.getIntendInfoAndPrizeInfo(id);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, bean);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		return resultMap;
	}
	
	
	/**
	 * 
	 * @Description: 取消订单
	 * @author yanghui 
	 * @Created 2014-4-8
	 * @param page
	 * @param id
	 * @return
	 */
	@RequestMapping(value="cancelIntend",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> cancelIntend(RequestPage page,@RequestParam(value="id",required=false) Long id){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Intend intend  = intendInterfaceService.get(id);
		intend.setIntendStatus(SystemCommon_Constant.INTEND_STATUS_6); //6-订单已取消
		intendInterfaceService.update(intend);
		
		//通过订单号关联到对应的奖品信息，然后恢复兑换的奖品数量
		IntendRelPrize irp = intendRelPrizeService.getByIntendNo(intend.getIntendNo());
		Long excIntegral = irp.getExcIntegral()*irp.getPrizeNum(); //兑换使用的积分
		Long prizeId = irp.getPrizeId();
		Long prizeNum = irp.getPrizeNum();
		PrizeInfo pinfo = prizeInterfaceService.get(prizeId);
		Long excNum = pinfo.getExcNum();
		pinfo.setExcNum(excNum-prizeNum);
		pinfo.setPrizeNum(pinfo.getPrizeNum()+excNum);
		prizeInterfaceService.update(pinfo);
		
		//修改该用户已经使用的积分
		Integral integral = integralInterfaceService.getIntegralByUserId(intend.getApplyPersonId());
		Long usedIntegral = integral.getUsedValue();
		integral.setUsedValue(usedIntegral-excIntegral);
		integral.setCurrentValue(integral.getCurrentValue()+excIntegral);
		integralInterfaceService.update(integral);
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, intend);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "订单取消成功");
		return resultMap;
	}

}
