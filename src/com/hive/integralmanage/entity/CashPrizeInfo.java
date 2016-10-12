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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import dk.util.JsonDateSerializer;
import dk.util.JsonDateTimeSerializer;

/**  
 * Filename: PrizeInfo.java  
 * Description: 奖品领取实体类
 * 兑奖记录
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 */
@Entity
@Table(name = "M_CASHPRIZEINFO")
public class CashPrizeInfo {

	// Fields

	private Long id;
	private String prizeName;  //奖品名称
	private String prizeUser; //领奖人
	private String prizePhone; //领奖人电话
	private String prizeSN; //中奖SN码
	private Long createId; 
	private Date createTime;
	private Long modifyId;
	private Date modifyTime;
	private String valid;
	private Long prizeNum;   //奖品数量
	private String remark;
	
	private String createName;//添加人姓名  20160322 yf add
	private String createOrgId;//添加人 部门ID  20160322 yf add
	private String createOrgName;//添加人 部门名称  20160322 yf add
	private String prizePlace;//领奖地点  20160322 yf add
	
	private String winPrizeInfoId;//对应中奖信息ID
	
	private String dj_password;//临时解决方案中使用的参数
	
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

	@Column(name = "PRIZENAME", nullable = false, length = 100)
	public String getPrizeName() {
		return this.prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	@Column(name = "PRIZEUSER", nullable = false, length = 100)
	public String getPrizeUser() {
		return prizeUser;
	}

	public void setPrizeUser(String prizeUser) {
		this.prizeUser = prizeUser;
	}
	@Column(name = "PRIZEPHONE", nullable = false, length = 100)
	public String getPrizePhone() {
		return prizePhone;
	}

	public void setPrizePhone(String prizePhone) {
		this.prizePhone = prizePhone;
	}
	@Column(name = "PRIZESN", nullable = false, length = 100)
	public String getPrizeSN() {
		return prizeSN;
	}

	public void setPrizeSN(String prizeSN) {
		this.prizeSN = prizeSN;
	}

	@Column(name = "CREATEID", nullable = false)
	public Long getCreateId() {
		return this.createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME", nullable = false)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
	@Column(name = "VALID", nullable = false, length = 1)
	public String getValid() {
		return this.valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	@Column(name = "PRIZENUM", nullable = false)
	public Long getPrizeNum() {
		return this.prizeNum;
	}

	public void setPrizeNum(Long prizeNum) {
		this.prizeNum = prizeNum;
	}
	
	@Column(name = "REMARK",  length = 100)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	@Column(name = "CREATENAME",  length = 50)
	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Column(name = "CREATEORGID",  length = 50)
	public String getCreateOrgId() {
		return createOrgId;
	}

	public void setCreateOrgId(String createOrgId) {
		this.createOrgId = createOrgId;
	}

	@Column(name = "CREATEORGNAME",  length = 500)
	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	@Column(name = "PRIZEPLACE",  length = 1000)
	public String getPrizePlace() {
		return prizePlace;
	}

	public void setPrizePlace(String prizePlace) {
		this.prizePlace = prizePlace;
	}

	@Column(name = "WINPRIZEINFOID",  length = 100)
	public String getWinPrizeInfoId() {
		return winPrizeInfoId;
	}

	public void setWinPrizeInfoId(String winPrizeInfoId) {
		this.winPrizeInfoId = winPrizeInfoId;
	}

	public String getDj_password() {
		return dj_password;
	}

	public void setDj_password(String dj_password) {
		this.dj_password = dj_password;
	}
	
	

}
