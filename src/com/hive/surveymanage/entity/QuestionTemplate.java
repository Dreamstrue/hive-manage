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
 * SQuestiontemplate entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "S_QUESTIONTEMPLATE", schema = "ZXT")
public class QuestionTemplate implements java.io.Serializable {

	// Fields

	private Long id;
	private Long surveyid;
	private String description;
	private String question;
	private String questiontype;
	private Long sort;
	private Long createid;
	private Date createtime;
	private Long modifyid;
	private Date modifytime;
	private String valid;

	// Constructors

	/** default constructor */
	public QuestionTemplate() {
	}

	/** minimal constructor */
	public QuestionTemplate(Long id, Long surveyid,
			String question, String questiontype, Long sort,
			Long createid, Date createtime, String valid) {
		this.id = id;
		this.surveyid = surveyid;
		this.question = question;
		this.questiontype = questiontype;
		this.sort = sort;
		this.createid = createid;
		this.createtime = createtime;
		this.valid = valid;
	}

	/** full constructor */
	public QuestionTemplate(Long id, Long surveyid,
			String description, String question, String questiontype,
			Long sort, Long createid, Date createtime,
			Long modifyid, Date modifytime, String valid) {
		this.id = id;
		this.surveyid = surveyid;
		this.description = description;
		this.question = question;
		this.questiontype = questiontype;
		this.sort = sort;
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

	@Column(name = "DESCRIPTION", length = 4000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "QUESTION", nullable = false, length = 1000)
	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	@Column(name = "QUESTIONTYPE", nullable = false, length = 1)
	public String getQuestiontype() {
		return this.questiontype;
	}

	public void setQuestiontype(String questiontype) {
		this.questiontype = questiontype;
	}

	@Column(name = "SORT", nullable = false, precision = 22, scale = 0)
	public Long getSort() {
		return this.sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
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