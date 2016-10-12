/**
 * 
 */
package com.hive.prizemanage.entity;

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

import dk.util.JsonDateSerializer;
import dk.util.JsonDateTimeSerializer;

/**  
 * Filename: PrizeInfo.java  
 * Description: 奖品表实体类
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-6  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-6 下午4:14:27				yanghui 	1.0
 */
@Entity
@Table(name = "M_PRIZEINFO")
public class PrizeInfo {

	// Fields

	private Long id;
	private String prizeName;  //奖品名称
	private Long prizeCateId;  //奖品类别
	private String picturePath; //奖品图片路径
	private String pictureName; //图片名称
	private Long excIntegral; //兑换奖品所需积分
	private String prizeExplain; //奖品说明
	private Long createId; 
	private Date createTime;
	private Long modifyId;
	private Date modifyTime;
	private Long auditId;
	private Date auditTime;
	private String auditStatus; //审核状态
	private String auditOpinion; //审核意见
	private String valid;
	private Long prizeNum;   //奖品数量
	private Long excNum; //已兑换数量
	private Date validDate; //奖品有效期（下线时间）
	private String entityCategory;//实体类型（中石油、中石化）
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

	@Column(name = "prizeName", nullable = false, length = 100)
	public String getPrizeName() {
		return this.prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	@Column(name = "prizeCateId", nullable = false)
	public Long getPrizeCateId() {
		return this.prizeCateId;
	}

	public void setPrizeCateId(Long prizeCateId) {
		this.prizeCateId = prizeCateId;
	}

	@Column(name = "picturePath", nullable = false, length = 500)
	public String getPicturePath() {
		return this.picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	
	
	@Column(name = "pictureName", nullable = true, length = 20)
	public String getPictureName() {
		return pictureName;
	}

	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}

	@Column(name = "excIntegral", nullable = false)
	public Long getExcIntegral() {
		return this.excIntegral;
	}

	public void setExcIntegral(Long excIntegral) {
		this.excIntegral = excIntegral;
	}

	@Column(name = "prizeExplain", nullable = false, length = 1000)
	public String getPrizeExplain() {
		return this.prizeExplain;
	}

	public void setPrizeExplain(String prizeExplain) {
		this.prizeExplain = prizeExplain;
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

	@Column(name = "auditStatus", nullable = false, length = 1)
	public String getAuditStatus() {
		return this.auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	@Column(name = "auditOpinion", length = 200)
	public String getAuditOpinion() {
		return this.auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	@Column(name = "valid", nullable = false, length = 1)
	public String getValid() {
		return this.valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	@Column(name = "prizeNum", nullable = false)
	public Long getPrizeNum() {
		return this.prizeNum;
	}

	public void setPrizeNum(Long prizeNum) {
		this.prizeNum = prizeNum;
	}
	
	
	@Column(name = "excNum", nullable = true)
	public Long getExcNum() {
		return excNum;
	}

	public void setExcNum(Long excNum) {
		this.excNum = excNum;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "validDate")
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getValidDate() {
		return this.validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}
	
	@Column(name = "ENTITYCATEGORY", length = 10)
	public String getEntityCategory() {
		return entityCategory;
	}
	public void setEntityCategory(String entityCategory) {
		this.entityCategory = entityCategory;
	}
}
