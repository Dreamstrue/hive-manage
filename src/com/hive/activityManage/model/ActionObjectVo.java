package com.hive.activityManage.model;


/**
 * 作用对象vo
 */
public class ActionObjectVo{

	// Fields

	private Long id;//作用对象实例id
	private String actionObjectType;//作用对象类型  1.行业实体，2.标签批次,3.行业类别
	private String linkId ;//关联id 比如行业类别问卷中间表id（s_surveyindustrysurvey），行业实体问卷中间表id（s_industryentitysurvey），标签批次问卷中间表id（tag_snbatchsurvey）
	private String objectId;//对象id  比如行业id，行业实体id
	private String objectName;//对象名称
	private String actionObjectName;//作用对象名称
	private String activityFormId;//活动形式id，即问卷id
	private String activityFormName;//活动形式的名称，即问卷的主题
	
	private String aostatus;//活动作用对象状态
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActionObjectType() {
		return actionObjectType;
	}
	
	public void setActionObjectType(String actionObjectType) {
		this.actionObjectType = actionObjectType;
	}

	public String getLinkId() {
		return linkId;
	}
	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}
	public String getObjectId() {
		return objectId;
	}
	
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public String getActivityFormId() {
		return activityFormId;
	}

	public void setActivityFormId(String activityFormId) {
		this.activityFormId = activityFormId;
	}

	public String getActivityFormName() {
		return activityFormName;
	}

	public void setActivityFormName(String activityFormName) {
		this.activityFormName = activityFormName;
	}

	public String getActionObjectName() {
		return actionObjectName;
	}

	public void setActionObjectName(String actionObjectName) {
		this.actionObjectName = actionObjectName;
	}

	public String getAostatus() {
		return aostatus;
	}

	public void setAostatus(String aostatus) {
		this.aostatus = aostatus;
	}
	
}