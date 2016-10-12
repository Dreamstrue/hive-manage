package com.hive.evaluationManage.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import dk.util.JsonDateTimeSerializer;

/**
 * SVoterecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "S_SURVEYEVALUATION", schema = "ZXT")
public class SurveyEvaluation implements java.io.Serializable {

	// Fields

	private Long id;//评价id
	private String surveyid;//问卷id
	private String industryEntityId;//行业实体id
	private String snBaseId;//标签id，每一个标签只能评价一次，所以标签和评论是1对1关系
	private String surveyPartakeUserId;//评价人id
	private Date createTime;//评价时间
	private String isValid;

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "ID", unique = true, nullable = true)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "SURVEYID", nullable = false, length = 100)
	public String getSurveyid() {
		return this.surveyid;
	}

	public void setSurveyid(String surveyid) {
		this.surveyid = surveyid;
	}

	@Column(name = "INDUSTRYENTITYID", length = 100)
	public String getIndustryEntityId() {
		return industryEntityId;
	}

	public void setIndustryEntityId(String industryEntityId) {
		this.industryEntityId = industryEntityId;
	}

	@Column(name = "SNBASEID", length = 100)
	public String getSnBaseId() {
		return snBaseId;
	}
	
	public void setSnBaseId(String snBaseId) {
		this.snBaseId = snBaseId;
	}
	@Column(name = "SURVEYPARTAKEUSERID", length=100)
	public String getSurveyPartakeUserId() {
		return surveyPartakeUserId;
	}

	public void setSurveyPartakeUserId(String surveyPartakeUserId) {
		this.surveyPartakeUserId = surveyPartakeUserId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME", nullable = false, length = 7)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "ISVALID", length=10)
	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	
}