/**
 * 
 */
package com.hive.clientinterface.thread;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

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

/**  
 * Filename: IntendInterfaceThread.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-4-8  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-4-8 上午9:44:48				yanghui 	1.0
 */
public class IntendInterfaceThread implements Runnable {

	@Resource
	private IntendInterfaceService intendInterfaceService;
	@Resource
	private IntendRelPrizeService intendRelPrizeService;
	@Resource
	private IntegralInterfaceService integralInterfaceService;
	@Resource
	private PrizeInterfaceService prizeInterfaceService;
	
	private IntendBean bean;
	
	public IntendInterfaceThread(IntendBean bean){
		this.bean = bean;
	}
	
	
	public void run() {
		
		//自定义规则生产兑换订单号
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String ymd = sdf.format(new Date());
		String intendNo = ymd;
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
		Integral integral = integralInterfaceService.getIntegralByUserId(bean.getApplyPersonId());
		Long currentValue = integral.getCurrentValue(); //该用户当前的积分
		Long usedValue = integral.getUsedValue();//该用户已经消费的积分
			//消费的积分为单个奖品需要的积分乘以奖品数量
		Long usedIntegral = bean.getExcIntegral()*bean.getPrizeNum();  //本次消费的积分
		integral.setUsedValue(usedValue+usedIntegral);  //新的消费积分
		integral.setCurrentValue(currentValue-usedIntegral);  //新的当前积分
		integralInterfaceService.update(integral);

	}

}
