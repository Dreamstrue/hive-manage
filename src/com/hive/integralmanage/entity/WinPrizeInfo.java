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

import dk.util.JsonDateTimeSerializer;

/**
 * 中奖信息
 * @author yf 20160329 add
 *
 */
@Entity
@Table(name = "M_WINPRIZEINFO")
public class WinPrizeInfo {

	private Long id;
	
	private String prizeName;  //奖品名称
	
	private String prizeSupplier;//奖品提供方    中石化   中石油   大桥石化   其它
	
	private Long prizeNum;   //奖品数量
	
	private String prizeUser; //领奖人
	
	private String prizePhone; //领奖人电话
	
	private String prizePlace;//领奖地点
	
	private String snNum; //中奖SN码
	
	private String snBaseId;//标签id
	
	private String surveyEvaluationId;//对应的评价记录id
	
	private String cashPrizeInfoId;//对应的兑奖记录Id
	
	private String isCash;//是否兑奖  0 1
	
	private String winPrizeInfoType;//中奖信息类型 0 自己选择   1 抽奖
	
	private Date createTime;//获取时间
	
	private Long createId;//创建人ID
	
	private String createName;//创建人
	
	private String createOrgId;//创建部门ID
	
	private String createOrgName;//创建部门Name
	
	private Long modifyId;//更新人ID
	
	private Date modifyTime;//更新时间

	private String valid;//是否有效

	private String remark;//备注
	
	private Date endDate;//截止时间
	
	private String activityId;//活动id 20160620 yyf add
	
	private String activityType;//活动类型 20160620 yyf add
	
	private String winPrizeFlag;//是否中奖，0未中奖，1表示中奖    20160620 yyf add
	
	
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
	@Column(name = "SNNUM", nullable = false, length = 100)
	public String getSnNum() {
		return snNum;
	}

	public void setSnNum(String snNum) {
		this.snNum = snNum;
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

	@Column(name = "PRIZESUPPLIER",  length = 50)
	public String getPrizeSupplier() {
		return prizeSupplier;
	}

	public void setPrizeSupplier(String prizeSupplier) {
		this.prizeSupplier = prizeSupplier;
	}

	@Column(name = "SNBASEID",  length = 100)
	public String getSnBaseId() {
		return snBaseId;
	}

	public void setSnBaseId(String snBaseId) {
		this.snBaseId = snBaseId;
	}

	@Column(name = "SURVEYEVALUATIONID",  length = 50)
	public String getSurveyEvaluationId() {
		return surveyEvaluationId;
	}

	public void setSurveyEvaluationId(String surveyEvaluationId) {
		this.surveyEvaluationId = surveyEvaluationId;
	}

	@Column(name = "CASHPRIZEINFOID",  length = 50)
	public String getCashPrizeInfoId() {
		return cashPrizeInfoId;
	}

	public void setCashPrizeInfoId(String cashPrizeInfoId) {
		this.cashPrizeInfoId = cashPrizeInfoId;
	}

	@Column(name = "ISCASH",  length = 50)
	public String getIsCash() {
		return isCash;
	}

	public void setIsCash(String isCash) {
		this.isCash = isCash;
	}

	@Column(name = "WINPRIZEINFOTYPE",  length = 50)
	public String getWinPrizeInfoType() {
		return winPrizeInfoType;
	}

	public void setWinPrizeInfoType(String winPrizeInfoType) {
		this.winPrizeInfoType = winPrizeInfoType;
	}
	@Column(name = "ACTIVITYID",  length = 32)
	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	@Column(name = "ACTIVITYTYPE",  length = 10)
	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENDDATE")
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "WINPRIZEFLAG",  length = 2)
	public String getWinPrizeFlag() {
		return winPrizeFlag;
	}
	public void setWinPrizeFlag(String winPrizeFlag) {
		this.winPrizeFlag = winPrizeFlag;
	}
	

}
