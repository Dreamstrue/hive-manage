package com.hive.permissionmanage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Action entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "P_ACTION")
public class Action implements java.io.Serializable {

	// Fields

	private Long nactionid;
	private String cactionname;
	private String cactionstr;
	private int iactionvalue;
	private String cactiondescription;

	// Constructors

	/** default constructor */
	public Action() {
	}

	/** minimal constructor */
	public Action(Long nactionid, String cactionname, String cactionstr,
			int iactionvalue) {
		this.nactionid = nactionid;
		this.cactionname = cactionname;
		this.cactionstr = cactionstr;
		this.iactionvalue = iactionvalue;
	}

	/** full constructor */
	public Action(Long nactionid, String cactionname, String cactionstr,
			int iactionvalue, String cactiondescription) {
		this.nactionid = nactionid;
		this.cactionname = cactionname;
		this.cactionstr = cactionstr;
		this.iactionvalue = iactionvalue;
		this.cactiondescription = cactiondescription;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "NACTIONID", unique = true, nullable = true)
	public Long getNactionid() {
		return this.nactionid;
	}

	public void setNactionid(Long nactionid) {
		this.nactionid = nactionid;
	}

	@Column(name = "CACTIONNAME", nullable = false, length = 50)
	public String getCactionname() {
		return this.cactionname;
	}

	public void setCactionname(String cactionname) {
		this.cactionname = cactionname;
	}

	@Column(name = "CACTIONSTR", nullable = false, length = 200)
	public String getCactionstr() {
		return this.cactionstr;
	}

	public void setCactionstr(String cactionstr) {
		this.cactionstr = cactionstr;
	}

	@Column(name = "IACTIONVALUE", nullable = false, precision = 22, scale = 0)
	public int getIactionvalue() {
		return this.iactionvalue;
	}

	public void setIactionvalue(int iactionvalue) {
		this.iactionvalue = iactionvalue;
	}

	@Column(name = "CACTIONDESCRIPTION", length = 200)
	public String getCactiondescription() {
		return this.cactiondescription;
	}

	public void setCactiondescription(String cactiondescription) {
		this.cactiondescription = cactiondescription;
	}

}