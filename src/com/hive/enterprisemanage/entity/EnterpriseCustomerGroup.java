/**
 * 
 */
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
 * Filename: EnterpriseCustomerGroup.java  
 * Description: 企业用户组别信息
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-5-29  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-5-29 下午4:25:34				yanghui 	1.0
 */
@Entity
@Table(name="E_ENT_CUSTOMERMANAGE_GROUP")
public class EnterpriseCustomerGroup {
	
	
	private Long id;
	/**
	 * 企业ID
	 */
	private Long enterpriseId;
	/**
	 * 分组名称
	 */
	private String groupName;
	/**
	 * 分组备注
	 */
	private String remark;
	
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
	
	@Column(name="ENTERPRISEID", nullable=false)
	public Long getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	
	@Column(name="GROUPNAME", nullable=false,length=50)
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	@Column(name="REMARK")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	

}
