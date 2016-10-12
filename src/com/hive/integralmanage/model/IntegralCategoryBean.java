package com.hive.integralmanage.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import dk.util.JsonDateTimeSerializer;

public class IntegralCategoryBean {
	
	private Long id;
	private Long totalIntegral ;// 总积分
    private String entityCategory;//实体类型（中石油、中石化）
    private String entityCategoryName;//实体类型（中石油、中石化）
	private String counts;//参与次数
	private Long currentIntegral; //当前可用积分
	private Long usedIntegral; //已消费积分
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
	public Long getTotalIntegral() {
		return totalIntegral;
	}
	public void setTotalIntegral(Long totalIntegral) {
		this.totalIntegral = totalIntegral;
	}
	public String getCounts() {
		return counts;
	}
	public void setCounts(String counts) {
		this.counts = counts;
	}
	public Long getCurrentIntegral() {
		return currentIntegral;
	}
	public void setCurrentIntegral(Long currentIntegral) {
		this.currentIntegral = currentIntegral;
	}
	public Long getUsedIntegral() {
		return usedIntegral;
	}
	public void setUsedIntegral(Long usedIntegral) {
		this.usedIntegral = usedIntegral;
	}
	public String getEntityCategoryName() {
		return entityCategoryName;
	}
	public void setEntityCategoryName(String entityCategoryName) {
		this.entityCategoryName = entityCategoryName;
	}

	
}
