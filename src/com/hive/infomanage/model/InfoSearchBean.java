package com.hive.infomanage.model;

import java.util.Date;

public class InfoSearchBean {

	private String title;
	private String auditStatus;
	private Long infoCateId;
	private Date createTime;
	private Date beginDate;
	private Date endDate;
	private String sendObject;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public Long getInfoCateId() {
		return infoCateId;
	}
	public void setInfoCateId(Long infoCateId) {
		this.infoCateId = infoCateId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
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
	public String getSendObject() {
		return sendObject;
	}
	public void setSendObject(String sendObject) {
		this.sendObject = sendObject;
	}
	
	
	
	
	
}
