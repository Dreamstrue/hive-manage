package com.hive.enterprisemanage.entity;

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
 * 
* Filename: MTradeinfo.java  
* Description:  企业供求信息表
* Copyright:Copyright (c)2013
* Company:  GuangFan 
* @author:  yanghui
* @version: 1.0  
* @Create:  2013-10-30  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2013-10-30 下午2:54:51				yanghui 	1.0
 */
@Entity
@Table(name = "M_TRADEINFO")
public class MTradeinfo implements java.io.Serializable {

	// Fields

	private Long id;
	private Long nmemberid;
	private String ctitle;
	private String cproductname;
	private String cproductnum;
	private String cprice;
	private String cbs;
	private String ccontactperson;
	private String ccontactphone;
	private Date dvalidbegin;
	private Date dvalidend;
	private String chasannex;
	private Long ncreateid;
	private Date dcreatetime;
	private Long nmodifyid;
	private Date dmodifytime;
	private Long nauditid;
	private Date daudittime;
	private String cauditstatus;
	private String cvalid;
	private String cauditopinion;
	private String unit;//单位

	// Constructors

	

	// Property accessors
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "idGenerator", strategy = "native")
    @Column(name = "NTRADEINFOID", unique = true, nullable = true)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NMEMBERID", nullable = false)
	public Long getNmemberid() {
		return this.nmemberid;
	}

	public void setNmemberid(Long nmemberid) {
		this.nmemberid = nmemberid;
	}

	@Column(name = "CTITLE", nullable = false, length = 100)
	public String getCtitle() {
		return this.ctitle;
	}

	public void setCtitle(String ctitle) {
		this.ctitle = ctitle;
	}

	@Column(name = "CPRODUCTNAME", nullable = false, length = 100)
	public String getCproductname() {
		return this.cproductname;
	}

	public void setCproductname(String cproductname) {
		this.cproductname = cproductname;
	}

	@Column(name = "CPRODUCTNUM", nullable = false, length = 100)
	public String getCproductnum() {
		return this.cproductnum;
	}

	public void setCproductnum(String cproductnum) {
		this.cproductnum = cproductnum;
	}

	@Column(name = "CPRICE", nullable = false, length = 200)
	public String getCprice() {
		return this.cprice;
	}

	public void setCprice(String cprice) {
		this.cprice = cprice;
	}

	@Column(name = "CBS", nullable = false, length = 1)
	public String getCbs() {
		return this.cbs;
	}

	public void setCbs(String cbs) {
		this.cbs = cbs;
	}

	@Column(name = "CCONTACTPERSON", nullable = false, length = 20)
	public String getCcontactperson() {
		return this.ccontactperson;
	}

	public void setCcontactperson(String ccontactperson) {
		this.ccontactperson = ccontactperson;
	}

	@Column(name = "CCONTACTPHONE", nullable = false, length = 50)
	public String getCcontactphone() {
		return this.ccontactphone;
	}

	public void setCcontactphone(String ccontactphone) {
		this.ccontactphone = ccontactphone;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DVALIDBEGIN", nullable = false)
	public Date getDvalidbegin() {
		return this.dvalidbegin;
	}

	public void setDvalidbegin(Date dvalidbegin) {
		this.dvalidbegin = dvalidbegin;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DVALIDEND", nullable = false)
	public Date getDvalidend() {
		return this.dvalidend;
	}

	public void setDvalidend(Date dvalidend) {
		this.dvalidend = dvalidend;
	}

	@Column(name = "CHASANNEX", nullable = false, length = 1)
	public String getChasannex() {
		return this.chasannex;
	}

	public void setChasannex(String chasannex) {
		this.chasannex = chasannex;
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
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getDcreatetime() {
		return this.dcreatetime;
	}

	public void setDcreatetime(Date dcreatetime) {
		this.dcreatetime = dcreatetime;
	}

	@Column(name = "NMODIFYID")
	public Long getNmodifyid() {
		return this.nmodifyid;
	}

	public void setNmodifyid(Long nmodifyid) {
		this.nmodifyid = nmodifyid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DMODIFYTIME")
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getDmodifytime() {
		return this.dmodifytime;
	}

	public void setDmodifytime(Date dmodifytime) {
		this.dmodifytime = dmodifytime;
	}

	@Column(name = "NAUDITID")
	public Long getNauditid() {
		return this.nauditid;
	}

	public void setNauditid(Long nauditid) {
		this.nauditid = nauditid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DAUDITTIME")
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getDaudittime() {
		return this.daudittime;
	}

	public void setDaudittime(Date daudittime) {
		this.daudittime = daudittime;
	}

	@Column(name = "CAUDITSTATUS", nullable = false, length = 1)
	public String getCauditstatus() {
		return this.cauditstatus;
	}

	public void setCauditstatus(String cauditstatus) {
		this.cauditstatus = cauditstatus;
	}

	@Column(name = "CVALID", nullable = false, length = 1)
	public String getCvalid() {
		return this.cvalid;
	}

	public void setCvalid(String cvalid) {
		this.cvalid = cvalid;
	}

	@Column(name = "CAUDITOPINION", length = 400)
	public String getCauditopinion() {
		return this.cauditopinion;
	}

	public void setCauditopinion(String cauditopinion) {
		this.cauditopinion = cauditopinion;
	}

	@Column(name = "UNIT", length = 20,nullable=false)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}