package com.hive.integralmanage.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import dk.util.JsonDateTimeSerializer;

public class IntegralDetailBean {
	
	private Long id;
	private Date integralDate;  //积分日期 （获取日期，消费日期）
	private String integralType; //类型（获取、消费）
	private Long integralValue;  //获得的积分值，消费时默认为0
	private String remark; //说明
	private String summary ;// 详情
	private Long sourceId;
	private Long integralCateId; //积分类别Id
	private String intendNo; //订单号
    private String entityCategory;//实体类型（中石油、中石化）
	private String industryEntity;//实体信息
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getIntegralDate() {
		return integralDate;
	}
	public void setIntegralDate(Date integralDate) {
		this.integralDate = integralDate;
	}
	public String getIntegralType() {
		return integralType;
	}
	public void setIntegralType(String integralType) {
		this.integralType = integralType;
	}
	public Long getIntegralValue() {
		return integralValue;
	}
	public void setIntegralValue(Long integralValue) {
		this.integralValue = integralValue;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Long getSourceId() {
		return sourceId;
	}
	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}
	public Long getIntegralCateId() {
		return integralCateId;
	}
	public void setIntegralCateId(Long integralCateId) {
		this.integralCateId = integralCateId;
	}
	public String getIntendNo() {
		return intendNo;
	}
	public void setIntendNo(String intendNo) {
		this.intendNo = intendNo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEntityCategory() {
		return entityCategory;
	}
	public void setEntityCategory(String entityCategory) {
		this.entityCategory = entityCategory;
	}
	public String getIndustryEntity() {
		return industryEntity;
	}
	public void setIndustryEntity(String industryEntity) {
		this.industryEntity = industryEntity;
	}
}
