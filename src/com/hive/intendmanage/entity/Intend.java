/**
 * 
 */
package com.hive.intendmanage.entity;

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
 * Filename: Intend.java  
 * Description: 订单表 实体类
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-11  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-11 上午10:48:12				yanghui 	1.0
 */
@Entity
@Table(name = "M_INTEND")
public class Intend {

	// Fields

	private Long id;
	private String intendNo;  //订单号
	private String consignee; //收货人 
	private String mobilePhone; //收货人电话
	private String address; //收货地址 
	private Date applyTime; //订单申请时间
	private Long applyPersonId;  //申请人ID
	private Long auditId; //订单审核人ID
	private Date auditTime; //审核时间 
	private String auditOpinion;  //审核意见
	private Date sendTime; // 发货时间
	private Date receiveTime; //收货时间
	private String intendStatus;  //订单状态 
	private String remark; //备注

	
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

	@Column(name = "intendNo", nullable = false, length = 30)
	public String getIntendNo() {
		return this.intendNo;
	}

	public void setIntendNo(String intendNo) {
		this.intendNo = intendNo;
	}

	@Column(name = "consignee", nullable = false, length = 10)
	public String getConsignee() {
		return this.consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	@Column(name = "mobilePhone", nullable = false, length = 11)
	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Column(name = "address", nullable = false, length = 500)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "applyTime", nullable = false)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getApplyTime() {
		return this.applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	@Column(name = "applyPersonId", nullable = false)
	public Long getApplyPersonId() {
		return this.applyPersonId;
	}

	public void setApplyPersonId(Long applyPersonId) {
		this.applyPersonId = applyPersonId;
	}

	@Column(name = "auditId")
	public Long getAuditId() {
		return this.auditId;
	}

	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "auditTime")
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getAuditTime() {
		return this.auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	@Column(name = "auditOpinion", length = 200)
	public String getAuditOpinion() {
		return this.auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "sendTime")
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "receiveTime")
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getReceiveTime() {
		return this.receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	@Column(name = "intendStatus", nullable = false, length = 1)
	public String getIntendStatus() {
		return this.intendStatus;
	}

	public void setIntendStatus(String intendStatus) {
		this.intendStatus = intendStatus;
	}

	@Column(name = "remark", length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
