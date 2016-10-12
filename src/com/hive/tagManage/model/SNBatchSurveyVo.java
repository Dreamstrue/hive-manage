package com.hive.tagManage.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 标签批次对应的问卷
 * 项目名称：zbt-manage    
 * 类名称：SNBatchSurvey    
 * 类描述：标签批次对应的问卷
 * 创建人：yf
 * 创建时间：2016-03-03
 * 修改备注：    
 * @version     
 */

public class SNBatchSurveyVo {
	

	private String id;
	
	/**
	 * 对应批次标识ID
	 */
	private String snBatchId;
	
	
	/**
	 * 对应的问卷ID
	 */
	private String surveyId;
	
	/**
	 * 对应的问卷标题
	 */
	private String surveyTitle;
	
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSnBatchId() {
		return snBatchId;
	}

	public void setSnBatchId(String snBatchId) {
		this.snBatchId = snBatchId;
	}

	public String getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSurveyTitle() {
		return surveyTitle;
	}

	public void setSurveyTitle(String surveyTitle) {
		this.surveyTitle = surveyTitle;
	}
	
}
