package com.hive.activityManage.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * 作用对象的活动表（活动对象关联中间表）
 */
@Entity
@Table(name = "A_Activity_Object_Link", schema = "ZXT")
public class ActivityObjectLink implements java.io.Serializable {

	// Fields

	private Long id;//中间表id
	private Long activityId;//活动实例id
	private Long actionObjectId;//作用对象id
	private String isValid;//0无效1有效2暂停
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
	
	@Column(name = "ACTIVITYID", nullable = false, precision = 22, scale = 0)
	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	@Column(name = "ACTIONOBJECTID", nullable = false, precision = 22, scale = 0)
	public Long getActionObjectId() {
		return actionObjectId;
	}

	public void setActionObjectId(Long actionObjectId) {
		this.actionObjectId = actionObjectId;
	}
	@Column(name = "ISVALID", length=10)
	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
}