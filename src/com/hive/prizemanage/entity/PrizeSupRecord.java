package com.hive.prizemanage.entity;

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
 * 
* Filename: PrizeSupRecord.java  
* Description:  奖品补货记录表实体
* Copyright:Copyright (c)2014
* Company:  GuangFan 
* @author:  yanghui
* @version: 1.0  
* @Create:  2014-3-6  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2014-3-6 下午4:49:06				yanghui 	1.0
 */
@Entity
@Table(name = "M_PRIZESUPRECORD")
public class PrizeSupRecord {

	// Fields

	private Long id;
	private Long prizeId;  //奖品ID
	private Long createId;  //操作人
	private Date createTime; //新增时间
	private Long prizeNum;  //新增奖品数量
	private Long modifyId;
	private Date modifyTime;
	private String valid;

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

	@Column(name = "prizeId", nullable = false)
	public Long getPrizeId() {
		return this.prizeId;
	}

	public void setPrizeId(Long prizeId) {
		this.prizeId = prizeId;
	}

	@Column(name = "createId", nullable = false)
	public Long getCreateId() {
		return this.createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createTime", nullable = false)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "prizeNum", nullable = false)
	public Long getPrizeNum() {
		return this.prizeNum;
	}

	public void setPrizeNum(Long prizeNum) {
		this.prizeNum = prizeNum;
	}

	@Column(name = "modifyId")
	public Long getModifyId() {
		return this.modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modifyTime")
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "valid", nullable = false, length = 1)
	public String getValid() {
		return this.valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

}
