package com.hive.systemconfig.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * SysObjectParameconfig entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_OBJECT_PARAMECONFIG", schema = "ZXT")
public class SysObjectParameconfig implements java.io.Serializable {

	// Fields

	private Long id;
	private String objecttype;
	private Long objectid;
	private Long configid;
	/**
	 * 当前值
	 */
	private Long currentValue;
	// Constructors

	/** default constructor */
	public SysObjectParameconfig() {
	}

	/** full constructor */
	public SysObjectParameconfig(Long id, String objecttype,
			Long objectid, Long configid) {
		this.id = id;
		this.objecttype = objecttype;
		this.objectid = objectid;
		this.configid = configid;
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

	@Column(name = "OBJECTTYPE", nullable = false, length = 100)
	public String getObjecttype() {
		return this.objecttype;
	}

	public void setObjecttype(String objecttype) {
		this.objecttype = objecttype;
	}

	@Column(name = "OBJECTID", nullable = false, precision = 22, scale = 0)
	public Long getObjectid() {
		return this.objectid;
	}

	public void setObjectid(Long objectid) {
		this.objectid = objectid;
	}

	@Column(name = "CONFIGID", nullable = false, precision = 22, scale = 0)
	public Long getConfigid() {
		return this.configid;
	}

	public void setConfigid(Long configid) {
		this.configid = configid;
	}

	@Column(name = "CURRENTVALUE", nullable = true, precision = 22, scale = 0)
	public Long getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(Long currentValue) {
		this.currentValue = currentValue;
	}

	
	
}