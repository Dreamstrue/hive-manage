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
 * PUserrole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "P_USERROLE")
public class UserRole implements java.io.Serializable {

	// Fields

	private Long nuserroleid;
	private Long nuserid;
	private Long nroleid;

	// Constructors

	/** default constructor */
	public UserRole() {
	}

	/** full constructor */
	public UserRole(Long nuserroleid, Long nuserid,
			Long nroleid) {
		this.nuserroleid = nuserroleid;
		this.nuserid = nuserid;
		this.nroleid = nroleid;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "NUSERROLEID", unique = true, nullable = true)
	public Long getNuserroleid() {
		return this.nuserroleid;
	}

	public void setNuserroleid(Long nuserroleid) {
		this.nuserroleid = nuserroleid;
	}

	@Column(name = "NUSERID", nullable = false, precision = 22, scale = 0)
	public Long getNuserid() {
		return this.nuserid;
	}

	public void setNuserid(Long nuserid) {
		this.nuserid = nuserid;
	}

	@Column(name = "NROLEID", nullable = false, precision = 22, scale = 0)
	public Long getNroleid() {
		return this.nroleid;
	}

	public void setNroleid(Long nroleid) {
		this.nroleid = nroleid;
	}

}