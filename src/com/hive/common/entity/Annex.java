package com.hive.common.entity;

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
 * 
* Filename: Annex.java  
* Description: 附件实体类
* Copyright:Copyright (c)2013
* Company:  GuangFan 
* @author:  yanghui
* @version: 1.0  
* @Create:  2013-10-18  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2013-10-18 下午3:02:51				yanghui 	1.0
 */
@Entity
@Table(name = "C_ANNEX")
public class Annex {

	// Fields

	private Long id;
	private String objectTable;
	private Long objectId;
	private String cannextype;
	private String cfilename;
	private String cfiletype;
	private Long nfilesize;
	private String bbinarydata;
	private String cfilepath;
	private Long idowncount;
	private Long ncreateid;
	private Date dcreatetime;
	private String cvalid;

	// Constructors

	/** default constructor */
	public Annex() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "NANNEXID", unique = true, nullable = true)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CRELATIONOBJECTTYPE", nullable = false, length = 50)
	public String getObjectTable() {
		return objectTable;
	}
	public void setObjectTable(String objectTable) {
		this.objectTable = objectTable;
	}

	
	@Column(name = "CRELATIONOBJECTID", nullable = true)
	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}



	@Column(name = "CANNEXTYPE", nullable = true, length = 100)
	public String getCannextype() {
		return this.cannextype;
	}

	public void setCannextype(String cannextype) {
		this.cannextype = cannextype;
	}

	@Column(name = "CFILENAME", nullable = false, length = 100)
	public String getCfilename() {
		return this.cfilename;
	}

	public void setCfilename(String cfilename) {
		this.cfilename = cfilename;
	}

	@Column(name = "CFILETYPE", nullable = false, length = 10)
	public String getCfiletype() {
		return this.cfiletype;
	}

	public void setCfiletype(String cfiletype) {
		this.cfiletype = cfiletype;
	}

	@Column(name = "NFILESIZE", nullable = false)
	public Long getNfilesize() {
		return this.nfilesize;
	}

	public void setNfilesize(Long nfilesize) {
		this.nfilesize = nfilesize;
	}

	@Column(name = "BBINARYDATA", length = 1)
	public String getBbinarydata() {
		return this.bbinarydata;
	}

	public void setBbinarydata(String bbinarydata) {
		this.bbinarydata = bbinarydata;
	}

	@Column(name = "CFILEPATH", nullable = false, length = 500)
	public String getCfilepath() {
		return this.cfilepath;
	}

	public void setCfilepath(String cfilepath) {
		this.cfilepath = cfilepath;
	}

	@Column(name = "IDOWNCOUNT", nullable = false)
	public Long getIdowncount() {
		return this.idowncount;
	}

	public void setIdowncount(Long idowncount) {
		this.idowncount = idowncount;
	}

	@Column(name = "NCREATEID", nullable = false)
	public Long getNcreateid() {
		return this.ncreateid;
	}

	public void setNcreateid(Long ncreateid) {
		this.ncreateid = ncreateid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DCREATETIME", nullable = false)
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDcreatetime() {
		return this.dcreatetime;
	}

	public void setDcreatetime(Date dcreatetime) {
		this.dcreatetime = dcreatetime;
	}

	@Column(name = "CVALID", nullable = false, length = 1)
	public String getCvalid() {
		return this.cvalid;
	}

	public void setCvalid(String cvalid) {
		this.cvalid = cvalid;
	}

}