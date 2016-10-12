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
 * 菜单动作绑定实体
 */
@Entity
@Table(name = "P_MENUACTION")
public class MenuAction implements java.io.Serializable {

	// Fields

	private Long nmenuactionid;
	private Long nmenuid;
	private Long nactionid;

	// Constructors

	/** default constructor */
	public MenuAction() {
	}

	/** full constructor */
	public MenuAction(Long nmenuactionid, Long nmenuid,
			Long nactionid) {
		this.nmenuactionid = nmenuactionid;
		this.nmenuid = nmenuid;
		this.nactionid = nactionid;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "NMENUACTIONID", unique = true, nullable = true)
	public Long getNmenuactionid() {
		return this.nmenuactionid;
	}

	public void setNmenuactionid(Long nmenuactionid) {
		this.nmenuactionid = nmenuactionid;
	}

	@Column(name = "NMENUID", nullable = false, precision = 22, scale = 0)
	public Long getNmenuid() {
		return this.nmenuid;
	}

	public void setNmenuid(Long nmenuid) {
		this.nmenuid = nmenuid;
	}

	@Column(name = "NACTIONID", nullable = false, precision = 22, scale = 0)
	public Long getNactionid() {
		return this.nactionid;
	}

	public void setNactionid(Long nactionid) {
		this.nactionid = nactionid;
	}

}