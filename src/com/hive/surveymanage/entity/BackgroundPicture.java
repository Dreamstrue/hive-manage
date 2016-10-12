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
 * SBackgroundpicture entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "S_BACKGROUNDPICTURE", schema = "ZXT")
public class BackgroundPicture implements java.io.Serializable {

	// Fields

	private Long id;
	private String backname;
	private String thumbpicpath;
	private String originalpicpath;
	private Long createid;
	private Date createtime;
	private Long modifyid;
	private Date modifytime;
	private String valid;

	// Constructors

	/** default constructor */
	public BackgroundPicture() {
	}

	/** minimal constructor */
	public BackgroundPicture(Long id, String backname,
			String thumbpicpath, String originalpicpath, Long createid,
			Date createtime, String valid) {
		this.id = id;
		this.backname = backname;
		this.thumbpicpath = thumbpicpath;
		this.originalpicpath = originalpicpath;
		this.createid = createid;
		this.createtime = createtime;
		this.valid = valid;
	}

	/** full constructor */
	public BackgroundPicture(Long id, String backname,
			String thumbpicpath, String originalpicpath, Long createid,
			Date createtime, Long modifyid, Date modifytime, String valid) {
		this.id = id;
		this.backname = backname;
		this.thumbpicpath = thumbpicpath;
		this.originalpicpath = originalpicpath;
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

	@Column(name = "BACKNAME", nullable = false, length = 100)
	public String getBackname() {
		return this.backname;
	}

	public void setBackname(String backname) {
		this.backname = backname;
	}

	@Column(name = "THUMBPICPATH", nullable = false, length = 500)
	public String getThumbpicpath() {
		return this.thumbpicpath;
	}

	public void setThumbpicpath(String thumbpicpath) {
		this.thumbpicpath = thumbpicpath;
	}

	@Column(name = "ORIGINALPICPATH", nullable = false, length = 500)
	public String getOriginalpicpath() {
		return this.originalpicpath;
	}

	public void setOriginalpicpath(String originalpicpath) {
		this.originalpicpath = originalpicpath;
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