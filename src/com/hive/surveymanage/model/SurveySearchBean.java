package com.hive.surveymanage.model;

import java.util.Date;

/**
 * @description 用于组装问卷搜索条件的 bean
 * @author 燕珂
 * @createtime 2014-4-10 下午06:50:24
 */
public class SurveySearchBean {
	private String subject;
	private String auditstatus;
	private String createName;
	private Date begintime;
	private Date endtime;
	
	public String getAuditstatus() {
		return auditstatus;
	}
	public void setAuditstatus(String auditstatus) {
		this.auditstatus = auditstatus;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public Date getBegintime() {
		return begintime;
	}
	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
}
