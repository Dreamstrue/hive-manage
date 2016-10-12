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
 * SSurveyindustry entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "S_SURVEYINDUSTRY", schema = "ZXT")
public class SurveyIndustry implements java.io.Serializable {

	// Fields

	private Long id;
	private String text; // 凡是树形结构，节点名字都得叫 text，否则展示不出来\
	private String objectType; // 分配的的类别如（jiayouzhan、dianti）15/12/25pfzhao
	private Long pid;
	private Integer sort;
	private Long createid;
	private Date createtime;
	private Long modifyid;
	private Date modifytime;
	private String valid;
	
	private Long surveyId;//绑定的问卷ID  yf 20160321 add
	private String surveyTitle;//绑定的问卷title  yf 20160321 add

	// Constructors

	/** default constructor */
	public SurveyIndustry() {
	}

	/** minimal constructor */
	public SurveyIndustry(Long id, String industryname,
			Long pid, Long createid, Date createtime, String valid) {
		this.id = id;
		this.text = industryname;
		this.pid = pid;
		this.createid = createid;
		this.createtime = createtime;
		this.valid = valid;
	}

	/** full constructor */
	public SurveyIndustry(Long id, String industryname,
			Long pid, Long createid, Date createtime,
			Long modifyid, Date modifytime, String valid) {
		this.id = id;
		this.text = industryname;
		this.pid = pid;
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

	@Column(name = "INDUSTRYNAME", nullable = false, length = 100)
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "PID", nullable = false, precision = 22, scale = 0)
	public Long getPid() {
		return this.pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
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
	@Column(name = "MODIFYTIME", nullable = false, length = 7)
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

	@Column(name = "SORT", length = 38, nullable = false)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	@Column(name = "OBJECTTYPE", nullable = false, length = 50)
	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	
	 @Column(name = "SURVEYID",length = 100)
	public Long getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

	@Column(name = "SURVEYTITLE",length = 1000)
	public String getSurveyTitle() {
		return surveyTitle;
	}

	public void setSurveyTitle(String surveyTitle) {
		this.surveyTitle = surveyTitle;
	}
	
	

}