/**
 * 
 */
package com.hive.subscription.model;

import java.util.Date;

/**  
 * Filename: SubscriptionSearchBean.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-2-27  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-2-27 上午11:47:56				yanghui 	1.0
 */
public class SubscriptionSearchBean {
	
	private String userName;
	private Long infoCateId;
	private Date subTime;
	private Date unSubTime;
	private Date beginDate;
	private Date endDate;
	
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getInfoCateId() {
		return infoCateId;
	}
	public void setInfoCateId(Long infoCateId) {
		this.infoCateId = infoCateId;
	}
	public Date getSubTime() {
		return subTime;
	}
	public void setSubTime(Date subTime) {
		this.subTime = subTime;
	}
	public Date getUnSubTime() {
		return unSubTime;
	}
	public void setUnSubTime(Date unSubTime) {
		this.unSubTime = unSubTime;
	}
	
	
	

}
