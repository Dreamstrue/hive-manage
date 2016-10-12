package com.hive.tagManage.model;

import java.util.Date;

import com.hive.tagManage.entity.SNBase;


public class SNBaseVo extends SNBase {
	private String batch;
	private String entityName;
	
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	

}