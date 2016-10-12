/**
 * 
 */
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
 * Filename: EnterpriseRoomAttention.java  
 * Description:  空间关注 用户表
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-5-14  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-5-14 下午4:00:21				yanghui 	1.0
 */
@Entity
@Table(name="E_ENT_ROOM_ATTENTION")
public class EnterpriseRoomAttention {

	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 关注的企业ID
	 */
	private Long enterpriseId;
	/**
	 * 	用户ID
	 */
	private Long userId;
	/**
	 * 关注时间
	 */
	private Date attentionTime;
	/**
	 * 是否被屏蔽 0-未屏蔽 1-屏蔽 
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
	@Column(name="PARENTID", nullable=false)
	public Long getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	
	@Column(name="USERID", nullable=false)
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ATTENTIONTIME",nullable=false)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getAttentionTime() {
		return attentionTime;
	}
	public void setAttentionTime(Date attentionTime) {
		this.attentionTime = attentionTime;
	}
	public String getShieldStatus() {
		return shieldStatus;
	}
	public void setShieldStatus(String shieldStatus) {
		this.shieldStatus = shieldStatus;
	}
	
	
	
}
