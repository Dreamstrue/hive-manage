/**
 * 
 */
package com.hive.integralmanage.entity;

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

import dk.util.JsonDateSerializer;
import dk.util.JsonDateTimeSerializer;

/**  
 * Filename: IntegralSub.java  
 * Description:  积分子表 实体类
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-11  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-11 上午9:25:14				yanghui 	1.0
 */
@Entity
@Table(name = "M_integral_sub")
public class IntegralSub {


	private Long id;
	private Long userId;
	/**
	 * 基本积分
	 */
	private Long basicValue; //基本积分
	/**
	 * 奖励积分
	 */
	private Long rewardValue; //奖励积分
	/**
	 * 本次获得的总积分
	 */
	private Long totalValue; //本次获得总积分
	/**
	 * 积分来源  （比如评论、分享新闻资讯；参与问卷投票获得积分的来源，存值为他们的ID值）
	 */
	private Long integralSource; //
	/**
	 * 积分类别
	 */
	private Long inteCateId; //
	/**
	 * 积分获得日期
	 */
	private Date gainDate; //
	/**
	 * 积分失效日期  （通过得到日期和系统定义的积分有效期关系得到 失效日期）
	 */
	private Date invalidDate; //
	/**
	 * 是否有效
	 */
	private String valid;
	/**
	 * 积分来源的标题
	 */
	private String sourceTitle;

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

	@Column(name = "userId", nullable = false)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "basicValue", nullable = false)
	public Long getBasicValue() {
		return this.basicValue;
	}

	public void setBasicValue(Long basicValue) {
		this.basicValue = basicValue;
	}

	@Column(name = "rewardValue", nullable = false)
	public Long getRewardValue() {
		return this.rewardValue;
	}

	public void setRewardValue(Long rewardValue) {
		this.rewardValue = rewardValue;
	}

	@Column(name = "totalValue", nullable = false)
	public Long getTotalValue() {
		return this.totalValue;
	}

	public void setTotalValue(Long totalValue) {
		this.totalValue = totalValue;
	}

	@Column(name = "integralSource", nullable = false)
	public Long getIntegralSource() {
		return this.integralSource;
	}

	public void setIntegralSource(Long integralSource) {
		this.integralSource = integralSource;
	}

	@Column(name = "inteCateId", nullable = false)
	public Long getInteCateId() {
		return this.inteCateId;
	}

	public void setInteCateId(Long inteCateId) {
		this.inteCateId = inteCateId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gainDate", nullable = false)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getGainDate() {
		return this.gainDate;
	}

	public void setGainDate(Date gainDate) {
		this.gainDate = gainDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "invalidDate")
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getInvalidDate() {
		return this.invalidDate;
	}

	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	@Column(name = "valid", nullable = false, length = 1)
	public String getValid() {
		return this.valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	@Column(name="SOURCETITLE",length=500)
	public String getSourceTitle() {
		return sourceTitle;
	}

	public void setSourceTitle(String sourceTitle) {
		this.sourceTitle = sourceTitle;
	}

	
	
	

}
