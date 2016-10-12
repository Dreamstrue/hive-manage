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
 * Filename: EnterpriseRoomReply.java  
 * Description: 评论回复表
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-5-12  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-5-12 上午9:50:53				yanghui 	1.0
 */
@Entity
@Table(name="E_ENT_ROOM_REPLY")
public class EnterpriseRoomReply {
	
	/**
	 * 回复表主键
	 */
	private Long id;
	/**
	 * 评论表主键
	 */
	private Long reviewId;
	/**
	 * 用户表主键（评论人）
	 */
	private Long userId;
	/**
	 * 回复评论的用户主键（回复人）
	 */
	private Long replyUserId;
	/**
	 * 是否存在图片
	 */
	private String isPicture = SystemCommon_Constant.SIGN_YES_0;
	/**
	 * 回复内容
	 */
	private String reply;
	/**
	 * 回复时间
	 */
	private Date createTime;
	/**
	 * 屏蔽状态
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
	
	@Column(name="REVIEWID", nullable=false,length=1)
	public Long getReviewId() {
		return reviewId;
	}
	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}
	
	@Column(name="USERID", nullable=false,length=1)
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Column(name="REPLYUSERID", nullable=false,length=1)
	public Long getReplyUserId() {
		return replyUserId;
	}
	public void setReplyUserId(Long replyUserId) {
		this.replyUserId = replyUserId;
	}
	
	@Column(name="IS_PIC", nullable=false,length=1)
	public String getIsPicture() {
		return isPicture;
	}
	public void setIsPicture(String isPicture) {
		this.isPicture = isPicture;
	}
	
	@Column(name="REPLY", nullable=false,length=400)
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
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
