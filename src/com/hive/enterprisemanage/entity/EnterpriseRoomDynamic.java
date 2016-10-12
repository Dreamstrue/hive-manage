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
* Filename: EnterpriseRoomDynamic.java  
* Description: 企业动态表
* Copyright:Copyright (c)2014
* Company:  GuangFan 
* @author:  yanghui
* @version: 1.0  
* @Create:  2014-5-12  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2014-5-12 上午9:57:44				yanghui 	1.0
 */
@Entity
@Table(name="E_ENT_ROOM_DYNAMIC")
public class EnterpriseRoomDynamic {
	
	/**
	 * 企业动态表主键
	 */
	private Long id;
	/**
	 * 企业表主键
	 */
	private Long enterpriseId;
	/**
	 * 企业注册用户ID
	 */
	private Long enterUserId;
	/**
	 * 是否有图片
	 */
	private String isPicture = SystemCommon_Constant.SIGN_YES_0;
	/**
	 * 企业动态内容
	 */
	private String enterDynamic;
	/**
	 * 发布时间
	 */
	private Date createTime;
	/**
	 * 是否有附件
	 */
	private String hasannex = SystemCommon_Constant.SIGN_YES_0;
	/**
	 * 是否有效
	 */
	private String valid =SystemCommon_Constant.SIGN_YES_1;
	
	/**
	 * 发布范围（1-全部用户可见，2-关注用户可见）
	 */
	private String isSuerange = SystemCommon_Constant.SEND_RANG_1;
	/**
	 * 是否发送消息提醒（0-不发送，1-发送）
	 */
	private String isSendmsg = SystemCommon_Constant.SIGN_YES_0;
	
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
	
	
	@Column(name="ENTID", nullable=false)
	public Long getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	
	@Column(name="ENTUSERID", nullable=false)
	public Long getEnterUserId() {
		return enterUserId;
	}
	public void setEnterUserId(Long enterUserId) {
		this.enterUserId = enterUserId;
	}
	
	@Column(name="IS_PIC", nullable=false,length=1)
	public String getIsPicture() {
		return isPicture;
	}
	public void setIsPicture(String isPicture) {
		this.isPicture = isPicture;
	}
	
	@Column(name="ENT_DYNAMIC", nullable=false)
	public String getEnterDynamic() {
		return enterDynamic;
	}
	public void setEnterDynamic(String enterDynamic) {
		this.enterDynamic = enterDynamic;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATETIME",nullable=false)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	@Column(name="HASANNEX", nullable=false,length=1)
	public String getHasannex() {
		return hasannex;
	}
	public void setHasannex(String hasannex) {
		this.hasannex = hasannex;
	}
	
	@Column(name="VALID", nullable=false,length=1)
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	
	
	@Column(name="ISSUERANGE",nullable=false,length=1)
	public String getIsSuerange() {
		return isSuerange;
	}
	public void setIsSuerange(String isSuerange) {
		this.isSuerange = isSuerange;
	}
	
	@Column(name="ISSENDMSG",nullable=false,length=1)
	public String getIsSendmsg() {
		return isSendmsg;
	}
	public void setIsSendmsg(String isSendmsg) {
		this.isSendmsg = isSendmsg;
	}
	
}
