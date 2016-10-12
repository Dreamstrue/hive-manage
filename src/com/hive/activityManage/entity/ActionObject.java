package com.hive.activityManage.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;


/**
 * 作用对象表
 */
@Entity
@Table(name = "A_ActionObject", schema = "ZXT")
public class ActionObject implements java.io.Serializable {

	// Fields

	private Long id;//作用对象实例id
	private String actionObjectType;//作用对象类型  1.行业实体，2.标签批次,3.行业类别
	private String linkId ;//关联id 比如行业类别问卷中间表id（s_surveyindustrysurvey），行业实体问卷中间表id（s_industryentitysurvey），标签批次问卷中间表id（tag_snbatchsurvey）
	private String objectId;//对象id  比如行业id，行业实体id
	private String objectName;//对象名称
	private String activityFormId;//活动形式id，即问卷id
	private String activityFormName;//活动形式的名称，即问卷的主题
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

	@Column(name = "ACTIONOBJECTTYPE", nullable = false, length = 10)
	public String getActionObjectType() {
		return actionObjectType;
	}
	
	public void setActionObjectType(String actionObjectType) {
		this.actionObjectType = actionObjectType;
	}

	@Column(name = "LINKID", nullable = false, length = 32)
	public String getLinkId() {
		return linkId;
	}
	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}
	@Column(name = "OBJECTID", nullable = false, length = 32)
	public String getObjectId() {
		return objectId;
	}
	
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	@Column(name = "OBJECTNAME", nullable = false, length = 100)
	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	@Column(name = "ACTIVITYFORMID", nullable = false, length = 32)
	public String getActivityFormId() {
		return activityFormId;
	}

	public void setActivityFormId(String activityFormId) {
		this.activityFormId = activityFormId;
	}

	@Column(name = "ACTIVITYFORMNAME", nullable = false, length = 100)
	public String getActivityFormName() {
		return activityFormName;
	}

	public void setActivityFormName(String activityFormName) {
		this.activityFormName = activityFormName;
	}
	
}