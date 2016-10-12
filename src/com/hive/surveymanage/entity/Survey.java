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

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import com.hive.common.SystemCommon_Constant;

import dk.util.JsonDateSerializer;

/**
 * SSurvey entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "S_SURVEY", schema = "ZXT")
public class Survey implements java.io.Serializable {

	/**
	 * 编号
	 */
	private Long id;
	/**
	 * 标题
	 */
	private String subject;
	/**
	 * 所属行业分类
	 */
	private Long industryid;
	/**
	 * 所属类别
	 */
	private Long categoryid;
	/**
	 * 说明
	 */
	private String description = StringUtils.EMPTY; // 初始化为空，这样转成的 json 对应节点值为 ""，而非 null 
	/**
	 * 标签
	 */
	private String tags;
	/**
	 * 数量限制
	 */
	private Integer numlimit; // 如果设置为 int 类型，当表单中此文本框未填值时候，就会报错：java.lang.NumberFormatException: For input string: "" --> 不能讲空字符串转换成整数
	/**
	 * 开始时间
	 */
	private Date begintime;
	/**
	 * 结束时间
	 */
	private Date endtime;
	/**
	 * 状态：1、投票进行中 2、已关闭（审核通过后才有值）
	 */
	private String status;
	/**
	 * 奖励积分
	 */
	private Integer integral;
	/**
	 * 参与人数
	 */
	private int participatenum;
	/**
	 * 结束语
	 */
	private String enddescription;
	/**
	 * 创建人
	 */
	private Long createid;
	/**
	 * 创建时间
	 */
	private Date createtime;
	/**
	 * 修改人
	 */
	private Long modifyid;
	/**
	 * 修改时间
	 */
	private Date modifytime;
	/**
	 * 审核人
	 */
	private Long auditid;
	/**
	 * 审核时间
	 */
	private Date audittime;
	/**
	 * 审核状态
	 */
	private String auditstatus;
	/**
	 * 审核意见
	 */
	private String auditopinion;
	/**
	 * 是否有效
	 */
	private String valid;
	
	/**图标路径*/
	private String picturePath; 
	/**
	 * 是否允许公示
	 */
	private String isShow = SystemCommon_Constant.SIGN_YES_0; 
	// Constructors
	/**
	 * 是否已生产二维码图片 默认 0-否，1-是。
	 */
	private String hasCode = SystemCommon_Constant.SIGN_YES_0;
	
	/** default constructor */
	public Survey() {
	}

	/** minimal constructor */
	public Survey(Long id, String subject, Long industryid,
			Long categoryid, int participatenum,
			Long createid, Date createtime, String auditstatus,
			String valid) {
		this.id = id;
		this.subject = subject;
		this.industryid = industryid;
		this.categoryid = categoryid;
		this.participatenum = participatenum;
		this.createid = createid;
		this.createtime = createtime;
		this.auditstatus = auditstatus;
		this.valid = valid;
	}

	/** full constructor */
	public Survey(Long id, String subject, Long industryid,
			Long categoryid, String description, String tags,
			Integer numlimit, Date begintime, Date endtime, String status,
			Integer integral, int participatenum,
			String enddescription, Long createid, Date createtime,
			Long modifyid, Date modifytime, Long auditid,
			Date audittime, String auditstatus, String auditopinion,
			String valid) {
		this.id = id;
		this.subject = subject;
		this.industryid = industryid;
		this.categoryid = categoryid;
		this.description = description;
		this.tags = tags;
		this.numlimit = numlimit;
		this.begintime = begintime;
		this.endtime = endtime;
		this.status = status;
		this.integral = integral;
		this.participatenum = participatenum;
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

	@Column(name = "SUBJECT", nullable = false, length = 200)
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Column(name = "INDUSTRYID", nullable = false, length = 50)
	public Long getIndustryid() {
		return this.industryid;
	}

	public void setIndustryid(Long industryid) {
		this.industryid = industryid;
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

	@Column(name = "NUMLIMIT", precision = 22, scale = 0)
	public Integer getNumlimit() {
		return this.numlimit;
	}

	public void setNumlimit(Integer numlimit) {
		this.numlimit = numlimit;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "BEGINTIME", length = 7)
	public Date getBegintime() {
		return this.begintime;
	}

	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ENDTIME", length = 7)
	public Date getEndtime() {
		return this.endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	@Column(name = "STATUS", length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "INTEGRAL", precision = 22, scale = 0)
	public Integer getIntegral() {
		return this.integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	@Column(name = "PARTICIPATENUM", nullable = false, precision = 22, scale = 0)
	public int getParticipatenum() {
		return this.participatenum;
	}

	public void setParticipatenum(int participatenum) {
		this.participatenum = participatenum;
	}

	@Column(name = "ENDDESCRIPTION", length = 4000)
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

	@Column(name = "AUDITID", precision = 22, scale = 0)
	public Long getAuditid() {
		return this.auditid;
	}

	public void setAuditid(Long auditid) {
		this.auditid = auditid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AUDITTIME", length = 7)
	@JsonSerialize(using=JsonDateSerializer.class)
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
	
	@Column(name = "PICTUREPATH", length = 200,nullable=true)
	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	@Column(name = "ISSHOW", length = 1,nullable=false)
	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	@Column(name = "HASCODE", length = 1,nullable=false)
	public String getHasCode() {
		return hasCode;
	}

	public void setHasCode(String hasCode) {
		this.hasCode = hasCode;
	}
	
	
	

}