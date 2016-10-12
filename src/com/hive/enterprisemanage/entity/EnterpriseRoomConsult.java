package com.hive.enterprisemanage.entity;

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

import com.hive.common.SystemCommon_Constant;

import dk.util.JsonDateTimeSerializer;

/**
 * 
* Filename: EnterpriseRoomConsult.java  
* Description:   用户咨询表
* Copyright:Copyright (c)2014
* Company:  GuangFan 
* @author:  yanghui
* @version: 1.0  
* @Create:  2014-5-20  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2014-5-20 上午9:30:34				yanghui 	1.0
 */
@Entity
@Table(name="E_ENT_ROOM_CONSULT")
public class EnterpriseRoomConsult {
	
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 企业ID
	 */
	private Long enterpriseId;
	/**
	 * 咨询的问题ID
	 */
	private Long parentId = 0L;
	/**
	 * 登录用户ID
	 */
	private Long userId;
	
	/**
	 * 类别（1-用户咨询，2-企业回复）
	 */
	private String consultType;
	/**
	 * 咨询（回复）内容
	 */
	private String consultContent;
	/**
	 * 咨询（回复）时间
	 */
	private Date consultTime;
	/**
	 * 是否屏蔽
	 */
	private String shieldStatus = SystemCommon_Constant.REVIEW_SHIELD_STATUS_0;
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "idGenerator", strategy = "native")
    @Column(name = "ID", unique = true, nullable = true)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	@Column(name="ENTERPRISEID",nullable=false)
	public Long getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	@Column(name="PARENTID", nullable=true)
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	@Column(name="USERID", nullable=false)
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Column(name="CONSULTTYPE", nullable=false,length=1)
	public String getConsultType() {
		return consultType;
	}
	public void setConsultType(String consultType) {
		this.consultType = consultType;
	}
	
	@Column(name="CONSULTCONTENT", nullable=false,length=400)
	public String getConsultContent() {
		return consultContent;
	}
	public void setConsultContent(String consultContent) {
		this.consultContent = consultContent;
	}
	
	@Column(name="CONSULTTIME", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getConsultTime() {
		return consultTime;
	}
	public void setConsultTime(Date consultTime) {
		this.consultTime = consultTime;
	}
	
	@Column(name="SHIELDSTATUS", nullable=false,length=1)
	public String getShieldStatus() {
		return shieldStatus;
	}
	public void setShieldStatus(String shieldStatus) {
		this.shieldStatus = shieldStatus;
	}
	
	
	
	

}
