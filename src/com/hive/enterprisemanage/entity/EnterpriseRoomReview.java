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
 * Filename: EnterpriseRoomReview.java  
 * Description:  企业动态评论表
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-5-12  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-5-12 上午9:40:49				yanghui 	1.0
 */
@Entity
@Table(name="E_ENT_ROOM_REVIEW")
public class EnterpriseRoomReview {
	
	/**
	 * 评论表主键
	 */
	private Long id;
	/**
	 * 动态表主键
	 */
	private Long dynamicId;
	/**
	 * 用户表主键（当前登录用户  也即评论人）
	 */
	private Long userId;
	/**
	 * 是否存在图片
	 */
	private String isPicture = SystemCommon_Constant.SIGN_YES_0;
	/**
	 * 评论内容
	 */
	private String review;
	/**
	 * 评论时间
	 */
	private Date createTime;
	/**
	 * 屏蔽状态
	 */
	private String shieldStatus = SystemCommon_Constant.SIGN_YES_0;
	
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
	
	@Column(name="DYNAMICID", nullable=false)
	public Long getDynamicId() {
		return dynamicId;
	}
	public void setDynamicId(Long dynamicId) {
		this.dynamicId = dynamicId;
	}
	
	@Column(name="USERID", nullable=false)
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Column(name="IS_PIC", nullable=false,length=1)
	public String getIsPicture() {
		return isPicture;
	}
	public void setIsPicture(String isPicture) {
		this.isPicture = isPicture;
	}
	
	@Column(name="REVIEW", nullable=false,length=400)
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
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
	
	@Column(name="SHIELDSTATUS", nullable=false,length=1)
	public String getShieldStatus() {
		return shieldStatus;
	}
	public void setShieldStatus(String shieldStatus) {
		this.shieldStatus = shieldStatus;
	}
	

}
