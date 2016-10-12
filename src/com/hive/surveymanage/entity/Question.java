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

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import dk.util.JsonDateSerializer;

/**
 * SQuestion entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "S_QUESTION", schema = "ZXT")
public class Question implements java.io.Serializable {

	// Fields

	private Long id;
	private Long surveyid;
	private String description;
	private String question;
	private String questiontype;
	private int sort;
	private String required; // 是否必填
	private Integer selectmin; // 最少选择几项
	private Integer selectmax; // 最多选择几项
	private Long createid;
	private Date createtime;
	private Long modifyid;
	private Date modifytime;
	private String valid;
	
	/**
	 * 是否需要手动选择
	 */
	private String isSelect;
	/**
	 * 当设置为需要手动选择时，选择的数据源类型
	 */
	private Long questionCateId;
	// Constructors

	/** default constructor */
	public Question() {
	}

	/** minimal constructor */
	public Question(Long id, Long surveyid, String question,
			String questiontype, int sort, Long createid,
			Date createtime, String valid) {
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
	public Question(Long id, Long surveyid, String description,
			String question, String questiontype, int sort,
			Long createid, Date createtime, Long modifyid,
			Date modifytime, String valid) {
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

	@Column(name = "QUESTIONTYPE", nullable = false, length = 2)
	public String getQuestiontype() {
		return this.questiontype;
	}

	public void setQuestiontype(String questiontype) {
		this.questiontype = questiontype;
	}

	@Column(name = "SORT", nullable = false, precision = 22, scale = 0)
	public int getSort() {
		return this.sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	@Column(name = "CREATEID", nullable = false, precision = 22, scale = 0)
	public Long getCreateid() {
		return this.createid;
	}

	public void setCreateid(Long createid) {
		this.createid = createid;
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

	@Column(name = "MODIFYID", precision = 22, scale = 0)
	public Long getModifyid() {
		return this.modifyid;
	}

	public void setModifyid(Long modifyid) {
		this.modifyid = modifyid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFYTIME", length = 7)
	@JsonSerialize(using=JsonDateSerializer.class)
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

	@Column(name = "REQUIRED", nullable = false, length = 1)
	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	@Column(name = "SELECTMAX", precision = 22, scale = 0)
	public Integer getSelectmax() {
		return selectmax;
	}

	public void setSelectmax(Integer selectmax) {
		this.selectmax = selectmax;
	}

	@Column(name = "SELECTMIN", precision = 22, scale = 0)
	public Integer getSelectmin() {
		return selectmin;
	}

	public void setSelectmin(Integer selectmin) {
		this.selectmin = selectmin;
	}

	@Column(name = "ISSELECT",length=1)
	public String getIsSelect() {
		return isSelect;
	}

	public void setIsSelect(String isSelect) {
		this.isSelect = isSelect;
	}

	@Column(name = "QUESTIONCATEID")
	public Long getQuestionCateId() {
		return questionCateId;
	}

	public void setQuestionCateId(Long questionCateId) {
		this.questionCateId = questionCateId;
	}

	
	
}