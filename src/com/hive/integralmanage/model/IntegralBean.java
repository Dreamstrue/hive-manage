/**
 * 
 */
package com.hive.integralmanage.model;

import java.util.Date;

/**  
 * Filename: IntegralBean.java  
 * Description:  积分查询对应的Bean 
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-11  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-11 上午10:36:59				yanghui 	1.0
 */
public class IntegralBean {
	
	private Long totalIntegral ;// 总积分
	private Long currentIntegral; //当前可用积分
	private Long usedIntegral; //已消费积分
	
	private String userName; //用户名称 
	private String chineseName; //用户姓名
	private Long userId; //用户ID
	private Date gainDate; //积分获得日期
	
	private String mobilePhone;
	private String idCard;
	private String anonymousFlag;
	
	public Long getTotalIntegral() {
		return totalIntegral;
	}
	public void setTotalIntegral(Long totalIntegral) {
		this.totalIntegral = totalIntegral;
	}
	public Long getCurrentIntegral() {
		return currentIntegral;
	}
	public void setCurrentIntegral(Long currentIntegral) {
		this.currentIntegral = currentIntegral;
	}
	public Long getUsedIntegral() {
		return usedIntegral;
	}
	public void setUsedIntegral(Long usedIntegral) {
		this.usedIntegral = usedIntegral;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getGainDate() {
		return gainDate;
	}
	public void setGainDate(Date gainDate) {
		this.gainDate = gainDate;
	}
	
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getAnonymousFlag() {
		return anonymousFlag;
	}
	public void setAnonymousFlag(String anonymousFlag) {
		this.anonymousFlag = anonymousFlag;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	
	
}
