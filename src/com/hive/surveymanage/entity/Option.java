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
 * SOption entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "S_OPTION", schema = "ZXT")
public class Option implements java.io.Serializable {

	// Fields

	private Long id;
	private Long questionid;
	private String optionText; //  OPTION 是 Oracle 关键词，所以此处用 optionText 而非 option
	private int sort;
	private Long selectnum;
	private String requireinput;
	
	private String optiontype; // 选项类型（对于组合选择题，左侧的选项存1，上面的选项存2）
	/**
	 * 问题重现：这两个字段是后来开发过程中新增的，然后手动在数据库加了这俩字段，原来已经存在的记录的这俩字段值就是空的。
	 * 出现问题：页面读取 optionList 的时候会报错：java.lang.IllegalArgumentException，实际上就是无法将空值转换成 int 类型，如果定义为 Integer 类型就不会出这个问题了。
	 * 解决办法：要么改字段类型，要么给已经存在的记录的该字段统一赋个值。
	 */
	private int score; // 选项分值（适用于打分题） 
	
	private Long createid;
	private Date createtime;
	private Long modifyid;
	private Date modifytime;
	private String valid;

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

	@Column(name = "OPTIONTEXT", nullable = false, length = 500)
	public String getOptionText() {
		return this.optionText;
	}

	@Column(name = "OPTIONTYPE", length = 8)
	public String getOptiontype() {
		return optiontype;
	}

	public void setOptiontype(String optiontype) {
		this.optiontype = optiontype;
	}

	@Column(name = "SCORE", precision = 22, scale = 0)
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}

	@Column(name = "SORT", nullable = false, precision = 22, scale = 0)
	public int getSort() {
		return this.sort;
	}

	public void setSort(int sort) {
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

}