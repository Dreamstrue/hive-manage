/**
 * 
 */
package com.hive.intendmanage.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.common.SystemCommon_Constant;
import com.hive.integralmanage.entity.Integral;
import com.hive.integralmanage.service.IntegralService;
import com.hive.intendmanage.entity.Intend;
import com.hive.intendmanage.entity.IntendRelPrize;
import com.hive.intendmanage.model.IntendBean;
import com.hive.intendmanage.model.IntendSearchBean;
import com.hive.intendmanage.service.IntendRelPrizeService;
import com.hive.intendmanage.service.IntendService;
import com.hive.prizemanage.entity.PrizeInfo;
import com.hive.prizemanage.service.PrizeInfoService;
import com.hive.util.DateUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**  
 * Filename: IntendController.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-11  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-11 上午10:59:34				yanghui 	1.0
 */
@Controller
@RequestMapping("intend")
public class IntendController extends BaseController {
	
	private static final String PREFIX ="intendmanage";
	
	@Resource
	private IntendService intendService;
	@Resource
	private IntendRelPrizeService intendRelPrizeService;
	@Resource
	private IntegralService integralService;
	@Resource
	private PrizeInfoService prizeInfoService;
	
	@RequestMapping("manage")
	public String manage(){
		return PREFIX+"/intendManage";
	}
	
	@RequestMapping("dataGrid")
	@ResponseBody
	public DataGrid dataGrid(RequestPage page,IntendSearchBean bean){
		return intendService.DataGrid(page,bean);
	}
	
	
	/**
	 * 
	 * @Description: 对兑换申请产生的订单信息进行保存
	 * @author yanghui 
	 * @Created 2014-3-14
	 * @param bean
	 * @return
	 */
	@RequestMapping("saveIntend")
	@ResponseBody
	public Map<String,Object> saveIntend(IntendBean bean){
		Intend intend = new Intend();
		try {
			PropertyUtils.copyProperties(intend, bean);
			intend.setIntendStatus(SystemCommon_Constant.INTEND_STATUS_1); //1-订单已申请
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//先保存订单信息
		intendService.save(intend);
		//保存订单与奖品关联信息
		IntendRelPrize irp = new IntendRelPrize();
		irp.setPrizeId(bean.getPrizeId());
		irp.setPrizeNum(bean.getPrizeNum());
		irp.setPrizeCateId(bean.getPrizeCateId());
		irp.setExcIntegral(bean.getExcIntegral());
		intendRelPrizeService.save(irp);
		
		//订单以及订单与奖品的关联信息都保存后，就需要修改该用户的积分信息
		Integral integral = integralService.getIntegralByUserId(bean.getUserId());
		Long currentValue = integral.getCurrentValue(); //该用户当前的积分
		Long usedValue = integral.getUsedValue();//该用户已经消费的积分
			//消费的积分为单个奖品需要的积分乘以奖品数量
		Long usedIntegral = bean.getExcIntegral()*bean.getPrizeNum();  //本次消费的积分
		integral.setUsedValue(usedValue+usedIntegral);  //新的消费积分
		integral.setCurrentValue(currentValue-usedIntegral);  //新的当前积分
		integralService.update(integral);
		
		return success("订单已提交");
	}
	
	
	
	
	
	/**
	 * 
	 * @Description:  对已申请兑换的订单进行审核 （审核页面）
	 * @author yanghui 
	 * @Created 2014-3-13
	 * @param model
	 * @param id
	 * @param opType
	 * @return
	 */
	@RequestMapping("approve")
	public String approve(Model model,@RequestParam(value="id") Long id,@RequestParam(value="opType") String opType){
		IntendBean bean = intendService.getIntendInfoAndPrizeInfo(id);
		model.addAttribute("opType", opType);
		model.addAttribute("vo", bean);
		return PREFIX+"/intendEdit";
	}
	
	
	/**
	 * 
	 * @Description: 订单审核方法  
	 * @author yanghui 
	 * @Created 2014-3-14
	 * @param bean
	 * @param session
	 * @return
	 */
	@RequestMapping("approveAction")
	@ResponseBody
	public Map<String,Object> approveAction(IntendBean bean,HttpSession session){
		Intend intend = intendService.get(bean.getId());
		String msg = "";
		if(bean.getIntendStatus().equals(SystemCommon_Constant.INTEND_STATUS_2)){
			msg = "审核通过";
		}else if(bean.getIntendStatus().equals(SystemCommon_Constant.INTEND_STATUS_3)){
			msg = "审核未通过";
		}
		intend.setIntendStatus(bean.getIntendStatus());
		intend.setAuditOpinion(bean.getAuditOpinion());
		intend.setAuditTime(DateUtil.getTimestamp());
		intend.setAuditId((Long) session.getAttribute("userId"));
		intendService.update(intend);
		if(bean.getIntendStatus().equals(SystemCommon_Constant.INTEND_STATUS_3)){
			//审核不通过时，回退扣除的积分以及奖品的数量
			
			//通过订单号关联到对应的奖品信息，然后恢复兑换的奖品数量
			IntendRelPrize irp = intendRelPrizeService.getByIntendNo(intend.getIntendNo());
			Long excIntegral = irp.getExcIntegral()*irp.getPrizeNum(); //兑换使用的积分
			Long prizeId = irp.getPrizeId();
			Long prizeNum = irp.getPrizeNum();
			PrizeInfo pinfo = prizeInfoService.get(prizeId);
			Long excNum = pinfo.getExcNum();
			pinfo.setExcNum(excNum-prizeNum);
			pinfo.setPrizeNum(pinfo.getPrizeNum()+excNum);
			prizeInfoService.update(pinfo);
			
			//修改该用户已经使用的积分
			Integral integral = integralService.getIntegralByUserId(intend.getApplyPersonId());
			Long usedIntegral = integral.getUsedValue();
			integral.setUsedValue(usedIntegral-excIntegral);
			integral.setCurrentValue(integral.getCurrentValue()+excIntegral);
			integralService.update(integral);
			
		}
		
		return success(msg);
	}
	
	/**
	 * 
	 * @Description: 订单明细
	 * @author yanghui 
	 * @Created 2014-3-14
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("detail")
	public String detail(Model model,@RequestParam(value="id") Long id){
		IntendBean bean = intendService.getIntendInfoAndPrizeInfo(id);
		model.addAttribute("vo", bean);
		return PREFIX+"/intendDetail";
	}
	
	/**
	 * 
	 * @Description:  发货操作 
	 * @author yanghui 
	 * @Created 2014-3-22
	 * @param model
	 * @param id
	 * @param opType
	 * @return
	 */
	@RequestMapping("send")
	@ResponseBody
	public Map<String,Object> send(Model model,@RequestParam(value="id") Long id){
		Intend intend = intendService.get(id);
		intend.setIntendStatus(SystemCommon_Constant.INTEND_STATUS_4);
		intend.setSendTime(DateUtil.getTimestamp());
		intendService.update(intend);
		return success("发货成功");
	}
	
	
	/**
	 * 
	 * @Description:  确认收货操作
	 * @author yanghui 
	 * @Created 2014-3-22
	 * @param model
	 * @param id
	 * @param opType
	 * @return
	 */
	@RequestMapping("received")
	@ResponseBody
	public Map<String,Object> received(Model model,@RequestParam(value="id") Long id){
		Intend intend = intendService.get(id);
		intend.setIntendStatus(SystemCommon_Constant.INTEND_STATUS_5); //确认收货
		intend.setReceiveTime(DateUtil.getTimestamp());
		intendService.update(intend);
		return success("签收成功");
	}
	

}
