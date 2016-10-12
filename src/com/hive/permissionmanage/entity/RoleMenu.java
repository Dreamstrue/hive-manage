package com.hive.permissionmanage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * PRolemenu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "P_ROLEMENU")
public class RoleMenu implements java.io.Serializable {

	// Fields

	private Long nrolemenuid;
	private Long nroleid;
	private Long nmenuid;

	// Constructors

	/** default constructor */
	public RoleMenu() {
	}

	/** full constructor */
	public RoleMenu(Long nrolemenuid, Long nroleid,
			Long nmenuid) {
		this.nrolemenuid = nrolemenuid;
		this.nroleid = nroleid;
		this.nmenuid = nmenuid;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "NROLEMENUID", unique = true, nullable = true)
	public Long getNrolemenuid() {
		return this.nrolemenuid;
	}

	public void setNrolemenuid(Long nrolemenuid) {
		this.nrolemenuid = nrolemenuid;
	}

	@Column(name = "NROLEID", nullable = false, precision = 22, scale = 0)
	public Long getNroleid() {
		return this.nroleid;
	}

	public void setNroleid(Long nroleid) {
		this.nroleid = nroleid;
	}

	@Column(name = "NMENUID", nullable = false, precision = 22, scale = 0)
	public Long getNmenuid() {
		return this.nmenuid;
	}

	public void setNmenuid(Long nmenuid) {
		this.nmenuid = nmenuid;
	}

}