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
 * PRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "P_ROLE")
public class Role implements java.io.Serializable {

	// Fields

	private Long nroleid;
	private String crolename;
	private String croledescription;

	// Constructors

	/** default constructor */
	public Role() {
	}

	/** minimal constructor */
	public Role(Long nroleid, String crolename) {
		this.nroleid = nroleid;
		this.crolename = crolename;
	}

	/** full constructor */
	public Role(Long nroleid, String crolename, String croledescription) {
		this.nroleid = nroleid;
		this.crolename = crolename;
		this.croledescription = croledescription;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "NROLEID", unique = true, nullable = true)
	public Long getNroleid() {
		return this.nroleid;
	}

	public void setNroleid(Long nroleid) {
		this.nroleid = nroleid;
	}

	@Column(name = "CROLENAME", nullable = false, length = 50)
	public String getCrolename() {
		return this.crolename;
	}

	public void setCrolename(String crolename) {
		this.crolename = crolename;
	}

	@Column(name = "CROLEDESCRIPTION", length = 200)
	public String getCroledescription() {
		return this.croledescription;
	}

	public void setCroledescription(String croledescription) {
		this.croledescription = croledescription;
	}

}