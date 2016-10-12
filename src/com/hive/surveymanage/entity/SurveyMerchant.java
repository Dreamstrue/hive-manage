package com.hive.surveymanage.entity;

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

/**
 * 
* Filename: SurveyMerchant.java  
* Description: 问卷相关商户类
* Copyright:Copyright (c)2014
* Company:  GuangFan 
* @author:  YangHui
* @version: 1.0  
* @Create:  2014-10-11  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2014-10-11 上午9:11:31				YangHui 	1.0
 */
@Entity
@Table(name="S_SURVEYMERCHANT")
public class SurveyMerchant {
	
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 问卷ID
	 */
	private Long surveyId;
	/**
	 * 用户ID(当匿名投票时存放一个负数，实名时就是当前登录用户的ID)
	 */
	private Long userId;
	/**
	 * 商户名称
	 */
	private String merchantName;
	/**
	 * 问卷类别
	 */
	private Long cateId;
	private Date createTime;
	
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
	@Column(name = "SURVEYID", nullable = false)
	public Long getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}
	@Column(name = "USERID", nullable = false)
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Column(name = "MERCHANTNAME", nullable = false)
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	@Column(name = "CATEID", nullable = false)
	public Long getCateId() {
		return cateId;
	}
	public void setCateId(Long cateId) {
		this.cateId = cateId;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME", nullable = false)
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	

}
