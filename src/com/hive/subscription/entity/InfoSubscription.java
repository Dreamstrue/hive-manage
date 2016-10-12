/**
 * 
 */
package com.hive.subscription.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import dk.util.JsonDateTimeSerializer;

/**  
 * Filename: InfoSubscription.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-2-27  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-2-27 上午11:35:38				yanghui 	1.0
 */
@Entity
@Table(name = "Info_subscription")
public class InfoSubscription {

	// Fields

	private Long id;
	private Long userId;
	private String userName;
	private Long infoCateId;
	private Date subTime;
	private Date unSubTime;
	private String unSubReason;
	private String subStatus;

	// Constructors

	/** default constructor */
	public InfoSubscription() {
	}

	

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

	@Column(name = "userId", nullable = false, precision = 22, scale = 0)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "infoCateId", nullable = false, precision = 22, scale = 0)
	public Long getInfoCateId() {
		return this.infoCateId;
	}

	public void setInfoCateId(Long infoCateId) {
		this.infoCateId = infoCateId;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "subTime", nullable = false)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getSubTime() {
		return this.subTime;
	}

	public void setSubTime(Date subTime) {
		this.subTime = subTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "unSubTime")
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getUnSubTime() {
		return this.unSubTime;
	}

	public void setUnSubTime(Date unSubTime) {
		this.unSubTime = unSubTime;
	}

	@Column(name = "unSubReason", length = 500)
	public String getUnSubReason() {
		return this.unSubReason;
	}

	public void setUnSubReason(String unSubReason) {
		this.unSubReason = unSubReason;
	}

	@Column(name = "subStatus", nullable = false, length = 1)
	public String getSubStatus() {
		return this.subStatus;
	}

	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}


	@Column(name = "userName", nullable = false, length = 20)
	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	
}
