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

import org.hibernate.annotations.GenericGenerator;

/**
 * SSurveyset entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "S_SURVEYSET", schema = "ZXT")
public class SurveySet implements java.io.Serializable {

	// Fields

	private Long id;
	private Long surveyid;
	private String allowanonymous;
	private String allowrepeatip;
	private Long backpicid;
	private Long createid;
	private Date createtime;
	private Long modifyid;
	private Date modifytime;
	private String valid;

	// Constructors

	/** default constructor */
	public SurveySet() {
	}

	/** minimal constructor */
	public SurveySet(Long id, Long surveyid,
			String allowanonymous, String allowrepeatip, Long createid,
			Date createtime, String valid) {
		this.id = id;
		this.surveyid = surveyid;
		this.allowanonymous = allowanonymous;
		this.allowrepeatip = allowrepeatip;
		this.createid = createid;
		this.createtime = createtime;
		this.valid = valid;
	}

	/** full constructor */
	public SurveySet(Long id, Long surveyid,
			String allowanonymous, String allowrepeatip, Long backpicid,
			Long createid, Date createtime, Long modifyid,
			Date modifytime, String valid) {
		this.id = id;
		this.surveyid = surveyid;
		this.allowanonymous = allowanonymous;
		this.allowrepeatip = allowrepeatip;
		this.backpicid = backpicid;
		this.createid = createid;
		this.createtime = createtime;
		this.modifyid = modifyid;
		this.modifytime = modifytime;
		this.valid = valid;
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

	@Column(name = "ALLOWANONYMOUS", nullable = false, length = 1)
	public String getAllowanonymous() {
		return this.allowanonymous;
	}

	public void setAllowanonymous(String allowanonymous) {
		this.allowanonymous = allowanonymous;
	}

	@Column(name = "ALLOWREPEATIP", nullable = false, length = 1)
	public String getAllowrepeatip() {
		return this.allowrepeatip;
	}

	public void setAllowrepeatip(String allowrepeatip) {
		this.allowrepeatip = allowrepeatip;
	}

	@Column(name = "BACKPICID", precision = 22, scale = 0)
	public Long getBackpicid() {
		return this.backpicid;
	}

	public void setBackpicid(Long backpicid) {
		this.backpicid = backpicid;
	}

	@Column(name = "CREATEID", nullable = false, precision = 22, scale = 0)
	public Long getCreateid() {
		return this.createid;
	}

	public void setCreateid(Long createid) {
		this.createid = createid;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATETIME", nullable = false, length = 7)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Column(name = "MODIFYID", precision = 22, scale = 0)
	public Long getModifyid() {
		return this.modifyid;
	}

	public void setModifyid(Long modifyid) {
		this.modifyid = modifyid;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "MODIFYTIME", length = 7)
	public Date getModifytime() {
		return this.modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	@Column(name = "VALID", nullable = false, length = 1)
	public String getValid() {
		return this.valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

}