package com.hive.tagManage.entity;

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
@Entity
@Table(name = "TAG_SNBATCHSURVEY")
public class SNBatchSurvey {
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid") // 通过注解方式生成一个generator
	@GeneratedValue(generator = "idGenerator") // 使用generator
	private String id;
	
	/**
	 * 对应批次标识ID
	 */
	@Column(name = "SNBATCHID", length = 32)
	private String snBatchId;
	
	
	/**
	 * 对应的问卷ID
	 */
	@Column(name = "SURVEYID", length = 32)
	private String surveyId;
	
	
	
	/**
	 * 对应的问卷标题
	 */
	@Column(name = "SURVEYTITLE", length = 1000)
	private String surveyTitle;
	
	
	
	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	@Column(name ="CREATETIME",nullable=true, length = 17)
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
