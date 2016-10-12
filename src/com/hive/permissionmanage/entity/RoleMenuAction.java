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
 * PRolemenuaction entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "P_ROLEMENUACTION")
public class RoleMenuAction implements java.io.Serializable {

	// Fields

	private Long nrolemenuid;
	private Long nroleid;
	private Long nmenuid;
	private Integer iactionvalues;

	// Constructors

	/** default constructor */
	public RoleMenuAction() {
	}

	/** full constructor */
	public RoleMenuAction(Long nrolemenuid, Long nroleid,
			Long nmenuid, Integer iactionvalues) {
		this.nrolemenuid = nrolemenuid;
		this.nroleid = nroleid;
		this.nmenuid = nmenuid;
		this.iactionvalues = iactionvalues;
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

	@Column(name = "IACTIONVALUES", nullable = false, precision = 22, scale = 0)
	public Integer getIactionvalues() {
		return this.iactionvalues;
	}

	public void setIactionvalues(Integer iactionvalues) {
		this.iactionvalues = iactionvalues;
	}

}