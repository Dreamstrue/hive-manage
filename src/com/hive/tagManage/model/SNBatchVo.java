package com.hive.tagManage.model;

import java.util.Date;


public class SNBatchVo {
	

	private String id;	//标识编号
	
	/**
	 * 批次名称
	 */
	private String batch;
	
	/**
	 * 数量
	 */
	private Integer amount;
	
	/**
	 * 状态
	 * 0:未印刷   1:印刷中   2：已印刷
	 */
	private Integer status;
	
	/**
	 * 创建者
	 */
	private String creater;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 创建者姓名
	 */
	private String createrName;
	
	/**
	 * 单位信息（印刷的标签的单位信息）
	 */
	private String industryEntityId;
	
	private String entityName;

	/**
	 * 可用数量（没有分配的数量）
	 */
	private Long validAmount;
	
	
	/**
	 * 对应的问卷ID
	 */
	private String surveyId;
	
	/**
	 * 对应的问卷标题
	 */
	private String surveyTitle;
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public Long getValidAmount() {
		return validAmount;
	}

	public void setValidAmount(Long validAmount) {
		this.validAmount = validAmount;
	}

	public String getIndustryEntityId() {
		return industryEntityId;
	}

	public void setIndustryEntityId(String industryEntityId) {
		this.industryEntityId = industryEntityId;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}

	public String getSurveyTitle() {
		return surveyTitle;
	}

	public void setSurveyTitle(String surveyTitle) {
		this.surveyTitle = surveyTitle;
	}
	
}
