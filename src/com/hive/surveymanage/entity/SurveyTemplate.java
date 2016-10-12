package com.hive.surveymanage.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * SSurveytemplate entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "S_SURVEYTEMPLATE", schema = "ZXT")
public class SurveyTemplate implements java.io.Serializable {

	// Fields

	private Long id;
	private String subject;
	private String indcatcode;
	private Long categoryid;
	private String description;
	private String tags;
	private String enddescription;
	private Long createid;
	private Date createtime;
	private Long modifyid;
	private Date modifytime;
	private Long auditid;
	private Date audittime;
	private String auditstatus;
	private String auditopinion;
	private String valid;

	// Constructors

	/** default constructor */
	public SurveyTemplate() {
	}

	/** minimal constructor */
	public SurveyTemplate(Long id, String subject, String indcatcode,
			Long categoryid, String enddescription, Long createid,
			Date createtime, String auditstatus, String valid) {
		this.id = id;
		this.subject = subject;
		this.indcatcode = indcatcode;
		this.categoryid = categoryid;
		this.enddescription = enddescription;
		this.createid = createid;
		this.createtime = createtime;
		this.auditstatus = auditstatus;
		this.valid = valid;
	}

	/** full constructor */
	public SurveyTemplate(Long id, String subject, String indcatcode,
			Long categoryid, String description, String tags,
			String enddescription, Long createid, Date createtime,
			Long modifyid, Date modifytime, Long auditid,
			Date audittime, String auditstatus, String auditopinion,
			String valid) {
		this.id = id;
		this.subject = subject;
		this.indcatcode = indcatcode;
		this.categoryid = categoryid;
		this.description = description;
		this.tags = tags;
		this.enddescription = enddescription;
		this.createid = createid;
		this.createtime = createtime;
		this.modifyid = modifyid;
		this.modifytime = modifytime;
		this.auditid = auditid;
		this.audittime = audittime;
		this.auditstatus = auditstatus;
		this.auditopinion = auditopinion;
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

	@Column(name = "SUBJECT", nullable = false, length = 100)
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Column(name = "INDCATCODE", nullable = false, length = 50)
	public String getIndcatcode() {
		return this.indcatcode;
	}

	public void setIndcatcode(String indcatcode) {
		this.indcatcode = indcatcode;
	}

	@Column(name = "CATEGORYID", nullable = false, precision = 22, scale = 0)
	public Long getCategoryid() {
		return this.categoryid;
	}

	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}

	@Column(name = "DESCRIPTION", length = 4000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "TAGS", length = 200)
	public String getTags() {
		return this.tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	@Column(name = "ENDDESCRIPTION", nullable = false, length = 4000)
	public String getEnddescription() {
		return this.enddescription;
	}

	public void setEnddescription(String enddescription) {
		this.enddescription = enddescription;
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

	@Column(name = "AUDITID", precision = 22, scale = 0)
	public Long getAuditid() {
		return this.auditid;
	}

	public void setAuditid(Long auditid) {
		this.auditid = auditid;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "AUDITTIME", length = 7)
	public Date getAudittime() {
		return this.audittime;
	}

	public void setAudittime(Date audittime) {
		this.audittime = audittime;
	}

	@Column(name = "AUDITSTATUS", nullable = false, length = 1)
	public String getAuditstatus() {
		return this.auditstatus;
	}

	public void setAuditstatus(String auditstatus) {
		this.auditstatus = auditstatus;
	}

	@Column(name = "AUDITOPINION", length = 400)
	public String getAuditopinion() {
		return this.auditopinion;
	}

	public void setAuditopinion(String auditopinion) {
		this.auditopinion = auditopinion;
	}

	@Column(name = "VALID", nullable = false, length = 1)
	public String getValid() {
		return this.valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

}