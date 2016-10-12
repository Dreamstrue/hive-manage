package com.hive.evaluationManage.model;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Entity;

@Entity
@SuppressWarnings("serial")
public class EvaluationVo implements java.io.Serializable {

	private BigInteger id;
	private BigInteger surveyId;
	private String surveyName;
	private Date surveyBeginTime;
	private Date surveyEndTime;
	private String industryName;
	private String industryId;
	private String entityName;
	private String voteUserName;//评价人姓名
	private String objectType;
	private BigInteger surveyPartakeUserId;//评价人id
	private String idCard; //评价人身份证号
	private String mobilePhone;//评价人手机号
	private String snCode;//sn码
	private Date createTime;
	
	
	private String beginTime;//条件查询，评论起始时间参数
	private String endTime;//条件查询，评论结束时间参数
	private String validFlag;//所有能能够抽奖有效评价表示
	
	private String getPrizeFlag;//是否领奖
	private String winPrizeFlag;//是否中奖
	private String winPrizeId;//中奖信息id
	
	public EvaluationVo(){
		
	}





	public EvaluationVo(BigInteger surveyId, String surveyName,
			Date surveyBeginTime, Date surveyEndTime, String industryName,
			String industryId, String entityName, String voteUserName,
			String objectType, BigInteger surveyPartakeUserId, String idCard,
			String mobilePhone, String snCode, Date createTime) {
		super();
		this.surveyId = surveyId;
		this.surveyName = surveyName;
		this.surveyBeginTime = surveyBeginTime;
		this.surveyEndTime = surveyEndTime;
		this.industryName = industryName;
		this.industryId = industryId;
		this.entityName = entityName;
		this.voteUserName = voteUserName;
		this.objectType = objectType;
		this.surveyPartakeUserId = surveyPartakeUserId;
		this.idCard = idCard;
		this.mobilePhone = mobilePhone;
		this.snCode = snCode;
		this.createTime = createTime;
	}





	public Date getCreateTime() {
		return createTime;
	}





	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}





	public BigInteger getSurveyId() {
		return surveyId;
	}


	public void setSurveyId(BigInteger surveyId) {
		this.surveyId = surveyId;
	}


	public BigInteger getSurveyPartakeUserId() {
		return surveyPartakeUserId;
	}


	public void setSurveyPartakeUserId(BigInteger surveyPartakeUserId) {
		this.surveyPartakeUserId = surveyPartakeUserId;
	}


	public String getSnCode() {
		return snCode;
	}

	public void setSnCode(String snCode) {
		this.snCode = snCode;
	}

	public String getIndustryId() {
		return industryId;
	}
	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}
	public String getSurveyName() {
		return surveyName;
	}
	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}
	public Date getSurveyBeginTime() {
		return surveyBeginTime;
	}
	public void setSurveyBeginTime(Date surveyBeginTime) {
		this.surveyBeginTime = surveyBeginTime;
	}
	public Date getSurveyEndTime() {
		return surveyEndTime;
	}
	public void setSurveyEndTime(Date surveyEndTime) {
		this.surveyEndTime = surveyEndTime;
	}
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getVoteUserName() {
		return voteUserName;
	}
	public void setVoteUserName(String voteUserName) {
		this.voteUserName = voteUserName;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}


	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}





	public String getValidFlag() {
		return validFlag;
	}





	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}





	public String getGetPrizeFlag() {
		return getPrizeFlag;
	}





	public void setGetPrizeFlag(String getPrizeFlag) {
		this.getPrizeFlag = getPrizeFlag;
	}





	public String getWinPrizeFlag() {
		return winPrizeFlag;
	}





	public void setWinPrizeFlag(String winPrizeFlag) {
		this.winPrizeFlag = winPrizeFlag;
	}





	public String getWinPrizeId() {
		return winPrizeId;
	}





	public void setWinPrizeId(String winPrizeId) {
		this.winPrizeId = winPrizeId;
	}





	public BigInteger getId() {
		return id;
	}





	public void setId(BigInteger id) {
		this.id = id;
	}

	
	
}
