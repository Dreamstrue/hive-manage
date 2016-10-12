package com.hive.surveymanage.entity;

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
 * 行业实体对应的问卷
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：Pengfei Zhao
 * <p/>创建日期：2016年6月6日
 * <p/>创建时间：下午2:15:04
 * <p/>功能描述：[]Service
 * <p/>===========================================================
 */
@Entity
@Table(name = "S_SURVEYINDUSTRYSURVEY")
public class SurveyIndustrySurvey {
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid") // 通过注解方式生成一个generator
	@GeneratedValue(generator = "idGenerator") // 使用generator
	private String id;
	
	/**
	 * 对应行业实体标识ID
	 */
	@Column(name = "SURVEYINDUSTRYID", length = 32)
	private String surveyIndustryId;
	
	
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

	public String getSurveyIndustryId() {
		return surveyIndustryId;
	}

	public void setSurveyIndustryId(String surveyIndustryId) {
		this.surveyIndustryId = surveyIndustryId;
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
