package com.hive.infomanage.model;

import java.util.Date;

public class NoticeReadBean {
	
	private Long id; //通知公告签收编号
    private Long noticeId; //通知公告编号
    private Date receiveDate;//签收时间（阅读时间）
    private Long userId; //签收人编号（登录用户的ID值）
    private String userName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}
	public Date getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
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
    
    
    

}
