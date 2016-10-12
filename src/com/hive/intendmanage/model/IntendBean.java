/**
 * 
 */
package com.hive.intendmanage.model;

import com.hive.intendmanage.entity.Intend;

/**  
 * Filename: IntendBean.java  
 * Description:  
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-11  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-11 下午2:19:21				yanghui 	1.0
 */
public class IntendBean extends Intend{

	
	
	private Long prizeId;
	private String prizeName; //奖品名称
	private Long prizeNum; //奖品数量
	private Long excIntegral; //奖品对应的兑换积分
	private Long userId; 
	private String userName; //积分消费人
	private Long prizeCateId; //奖品类别
	
	public Long getPrizeId() {
		return prizeId;
	}
	public void setPrizeId(Long prizeId) {
		this.prizeId = prizeId;
	}
	public String getPrizeName() {
		return prizeName;
	}
	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	public Long getPrizeNum() {
		return prizeNum;
	}
	public void setPrizeNum(Long prizeNum) {
		this.prizeNum = prizeNum;
	}
	public Long getExcIntegral() {
		return excIntegral;
	}
	public void setExcIntegral(Long excIntegral) {
		this.excIntegral = excIntegral;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getPrizeCateId() {
		return prizeCateId;
	}
	public void setPrizeCateId(Long prizeCateId) {
		this.prizeCateId = prizeCateId;
	}
	
	
	
	
}
