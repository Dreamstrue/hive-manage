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
 * STemplateoption entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "S_OPTIONTEMPLATE", schema = "ZXT")
public class OptionTemplate implements java.io.Serializable {

	// Fields

	private Long id;
	private Long questionid;
	private String optionText; //  OPTION 是 Oracle 关键词，所以此处用 optionText 而非 option
	private Long sort;
	private Long selectnum;
	private String requireinput;
	private Long createid;
	private Date createtime;
	private Long modifyid;
	private Date modifytime;
	private String calid;

	// Constructors

	/** default constructor */
	public OptionTemplate() {
	}

	/** minimal constructor */
	public OptionTemplate(Long id, Long questionid, String optionText,
			Long sort, String requireinput, Long createid,
			Date createtime, String calid) {
		this.id = id;
		this.questionid = questionid;
		this.optionText = optionText;
		this.sort = sort;
		this.requireinput = requireinput;
		this.createid = createid;
		this.createtime = createtime;
		this.calid = calid;
	}

	/** full constructor */
	public OptionTemplate(Long id, Long questionid, String optionText,
			Long sort, Long selectnum, String requireinput,
			Long createid, Date createtime, Long modifyid,
			Date modifytime, String calid) {
		this.id = id;
		this.questionid = questionid;
		this.optionText = optionText;
		this.sort = sort;
		this.selectnum = selectnum;
		this.requireinput = requireinput;
		this.createid = createid;
		this.createtime = createtime;
		this.modifyid = modifyid;
		this.modifytime = modifytime;
		this.calid = calid;
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

	@Column(name = "QUESTIONID", nullable = false, precision = 22, scale = 0)
	public Long getQuestionid() {
		return this.questionid;
	}

	public void setQuestionid(Long questionid) {
		this.questionid = questionid;
	}

	@Column(name = "optionText", nullable = false, length = 500)
	public String getOptionText() {
		return this.optionText;
	}

	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}

	@Column(name = "SORT", nullable = false, precision = 22, scale = 0)
	public Long getSort() {
		return this.sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	@Column(name = "SELECTNUM", precision = 22, scale = 0)
	public Long getSelectnum() {
		return this.selectnum;
	}

	public void setSelectnum(Long selectnum) {
		this.selectnum = selectnum;
	}

	@Column(name = "REQUIREINPUT", nullable = false, length = 1)
	public String getRequireinput() {
		return this.requireinput;
	}

	public void setRequireinput(String requireinput) {
		this.requireinput = requireinput;
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

	@Column(name = "CALID", nullable = false, length = 1)
	public String getCalid() {
		return this.calid;
	}

	public void setCalid(String calid) {
		this.calid = calid;
	}

}