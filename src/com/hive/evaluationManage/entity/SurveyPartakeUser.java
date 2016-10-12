package com.hive.evaluationManage.entity;

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

/**
 * SSurvey entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "S_SURVEYPARTAKEUSER", schema = "ZXT")
public class SurveyPartakeUser implements java.io.Serializable {

	/**
	 * 编号
	 */
	private Long id;

	/**
	 * 所属行业分类
	 */
	private Long industryid;
	/**
	 * 油卡号
	 */
	private String oilcardNo;
	/**
	 * 姓名
	 */
	private String username;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 身份证号
	 */
	private String IDCard;
	
	/**
	 * 加油日期
	 */
	private Date oilDate;
	/**
	 * 加油站名称
	 */
	private String oilStationName;
	
	/**
	 * 创建时间
	 */
	private Date createtime;
	/**
	 * 是否有效
	 */
	private String valid;
	/**
	 * 问卷ID
	 */
	private String surveyId;
	/**
	 * 实体ID
	 */
	private String objectId;
	
	
	/** default constructor */
	public SurveyPartakeUser() {
	}
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
	@Column(name = "INDUSTRYID", nullable = false, length = 50)
	public Long getIndustryid() {
		return this.industryid;
	}

	public void setIndustryid(Long industryid) {
		this.industryid = industryid;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME", nullable = false, length = 7)
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	@Column(name = "VALID", nullable = false, length = 1)
	public String getValid() {
		return this.valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}
	@Column(name = "OILCARDNO", length = 30)
	public String getOilcardNo() {
		return oilcardNo;
	}
	public void setOilcardNo(String oilcardNo) {
		this.oilcardNo = oilcardNo;
	}
	@Column(name = "USERNAME", length = 30)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Column(name = "PHONE", length = 30)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name = "IDCARD", length = 30)
	public String getIDCard() {
		return IDCard;
	}
	public void setIDCard(String iDCard) {
		IDCard = iDCard;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "OILDATE", length = 7)
	public Date getOilDate() {
		return oilDate;
	}
	public void setOilDate(Date oilDate) {
		this.oilDate = oilDate;
	}
	@Column(name = "OILSTATIONNAME", length = 30)
	public String getOilStationName() {
		return oilStationName;
	}
	public void setOilStationName(String oilStationName) {
		this.oilStationName = oilStationName;
	}
	@Column(name = "SURVEYID", length = 30)
	public String getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}
	@Column(name = "OBJECTID", length = 30)
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
}