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

import dk.util.JsonDateTimeSerializer;

/**
 * SVoterecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "S_VOTERECORD", schema = "ZXT")
public class VoteRecord implements java.io.Serializable {

	// Fields

	private Long id;
	private Long surveyid;
	private Long questionid;
	private String questiontype;
	private Long option1id;
	private Long option2id;
	private String answercontent;
	private Long userid;
	private Date votetime;
	private String ipaddress;
	private String batchNumber;// 20160727 YYF ADD 注释：此字段是一个随机的uuid，它唯一确定一条问卷记录

	private String relationObjectType; // 当前问卷针对主体类型（第一次改版：支撑查看针对某一个企业的统计结果）
	private Long relationObjectId; // 当前问卷针对主体 id
	
	private Long cObjectId; // 存放 C_OBJECT表的 id（第二次改版：支撑实体信息存在外部系统）
	// Constructors
	private Long surveyPartakeUserId; //评论用户列表Id
	

	/** default constructor */
	public VoteRecord() {
	}

	/** minimal constructor */
	public VoteRecord(Long id, Long surveyid,
			Long questionid, String questiontype, Long option1id,
			Date votetime) {
		this.id = id;
		this.surveyid = surveyid;
		this.questionid = questionid;
		this.questiontype = questiontype;
		this.option1id = option1id;
		this.votetime = votetime;
	}

	/** full constructor */
	public VoteRecord(Long id, Long surveyid,
			Long questionid, String questiontype, Long option1id,
			Long option2id, String answercontent, Long userid,
			Date votetime) {
		this.id = id;
		this.surveyid = surveyid;
		this.questionid = questionid;
		this.questiontype = questiontype;
		this.option1id = option1id;
		this.option2id = option2id;
		this.answercontent = answercontent;
		this.userid = userid;
		this.votetime = votetime;
	}

	// Property accessors
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

	@Column(name = "SURVEYID", nullable = false, precision = 22, scale = 0)
	public Long getSurveyid() {
		return this.surveyid;
	}

	public void setSurveyid(Long surveyid) {
		this.surveyid = surveyid;
	}

	@Column(name = "QUESTIONID", nullable = false, precision = 22, scale = 0)
	public Long getQuestionid() {
		return this.questionid;
	}

	public void setQuestionid(Long questionid) {
		this.questionid = questionid;
	}

	@Column(name = "QUESTIONTYPE", nullable = false, length = 8)
	public String getQuestiontype() {
		return this.questiontype;
	}

	public void setQuestiontype(String questiontype) {
		this.questiontype = questiontype;
	}

	@Column(name = "OPTION1ID", precision = 22, scale = 0)
	public Long getOption1id() {
		return this.option1id;
	}

	public void setOption1id(Long option1id) {
		this.option1id = option1id;
	}

	@Column(name = "OPTION2ID", precision = 22, scale = 0)
	public Long getOption2id() {
		return this.option2id;
	}

	public void setOption2id(Long option2id) {
		this.option2id = option2id;
	}

	@Column(name = "ANSWERCONTENT", length = 4000)
	public String getAnswercontent() {
		return this.answercontent;
	}

	public void setAnswercontent(String answercontent) {
		this.answercontent = answercontent;
	}
	
	@Column(name = "RELATIONOBJECTTYPE", length = 100)
	public String getRelationObjectType() {
		return this.relationObjectType;
	}

	public void setRelationObjectType(String relationObjectType) {
		this.relationObjectType = relationObjectType;
	}
	
	@Column(name = "RELATIONOBJECTID", precision = 22, scale = 0)
	public Long getRelationObjectId() {
		return this.relationObjectId;
	}

	public void setRelationObjectId(Long relationObjectId) {
		this.relationObjectId = relationObjectId;
	}

	@Column(name = "USERID", precision = 22, scale = 0)
	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "VOTETIME", nullable = false, length = 7)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getVotetime() {
		return this.votetime;
	}

	public void setVotetime(Date votetime) {
		this.votetime = votetime;
	}
	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	@Column(name = "COBJECTID", precision = 22, scale = 0)
	public Long getcObjectId() {
		return cObjectId;
	}

	public void setcObjectId(Long cObjectId) {
		this.cObjectId = cObjectId;
	}
	@Column(name = "SURVEYPARTAKEUSERID", precision = 22, scale = 0)
	public Long getSurveyPartakeUserId() {
		return surveyPartakeUserId;
	}

	public void setSurveyPartakeUserId(Long surveyPartakeUserId) {
		this.surveyPartakeUserId = surveyPartakeUserId;
	}
	
}