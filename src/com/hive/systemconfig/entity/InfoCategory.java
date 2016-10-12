package com.hive.systemconfig.entity;

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

import dk.util.JsonDateTimeSerializer;

/**
 * InfoCategory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Info_Category")
public class InfoCategory implements java.io.Serializable {

	// Fields

	private Long id;
	private Long parentId;
	private String text;
	private String remark;
	private Long createId;
	private Date createTime;
	private Long modifyId;
	private Date modifyTime;
	private Long auditId;
	private Date auditTime;
	private String auditStatus;
	private String auditOpinion;
	private String valid;
	/*项目中如政策法规、行业标准等内容管理属于同一数据表，只是通过信息类别区分，
	 * 而有些内容管理如新闻中心下的通知公告表结构有不同，所以为了防止在信息类别下拉表中出现该子类别，
	 * 设定是否展示，0-不展示；1-展示*/
	private String isShow; 
	/**
	 * 客户端显示   控制是否在客户端导航中出现
	 */
	private String clientShow;
	// Constructors

	/** default constructor */
	public InfoCategory() {
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

	@Column(name = "parentId", nullable = false)
	public Long getParentId() {
		return parentId;
	}



	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}



	@Column(name = "infoCateName", nullable = false, length = 100)
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "remark", length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "createId", nullable = false, precision = 22, scale = 0)
	public Long getCreateId() {
		return this.createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createTime", nullable = false)
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "modifyId", precision = 22, scale = 0)
	public Long getModifyId() {
		return this.modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modifyTime")
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "auditId", precision = 22, scale = 0)
	public Long getAuditId() {
		return this.auditId;
	}

	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "auditTime")
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getAuditTime() {
		return this.auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	@Column(name = "auditStatus", nullable = false, length = 1)
	public String getAuditStatus() {
		return this.auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	@Column(name = "auditOpinion", length = 200)
	public String getAuditOpinion() {
		return this.auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	@Column(name = "valid", nullable = false, length = 1)
	public String getValid() {
		return this.valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}


	@Column(name = "ISSHOW", nullable = true, length = 1)
	public String getIsShow() {
		return isShow;
	}



	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}


	@Column(name = "CLIENTSHOW", nullable = true, length = 1)
	public String getClientShow() {
		return clientShow;
	}



	public void setClientShow(String clientShow) {
		this.clientShow = clientShow;
	}
	
	
	
	

}