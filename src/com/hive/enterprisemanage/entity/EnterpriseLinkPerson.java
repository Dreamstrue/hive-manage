package com.hive.enterprisemanage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
* Filename: EnterpriseLinkPerson.java  
* Description:  企业会员的联系人
* Copyright:Copyright (c)2014
* Company:  GuangFan 
* @author:  yanghui
* @version: 1.0  
* @Create:  2014-4-19  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2014-4-19 下午5:32:00				yanghui 	1.0
 */
@Entity
@Table(name="E_ENTERPRISE_LINKPERSON")
public class EnterpriseLinkPerson {
	
	private Long id;
	private Long memberId;
	private Long enterpriseId;
	private String linkPersonName;
	private String linkPhone;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "idGenerator", strategy = "native")
    @Column(name = "ID", unique = true, nullable = true)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="MEMBERID",nullable=false)
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	@Column(name="ENTERPRISEID",nullable=true)
	public Long getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	@Column(name="LINKPERSONNAME",nullable=false)
	public String getLinkPersonName() {
		return linkPersonName;
	}
	public void setLinkPersonName(String linkPersonName) {
		this.linkPersonName = linkPersonName;
	}
	@Column(name="LINKPHONE",nullable=false)
	public String getLinkPhone() {
		return linkPhone;
	}
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	
	
	
	
	
	

}
