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
 * Filename: EnterpriseCoordinate.java  
 * Description: 企业坐标实体类
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-5-16  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-5-16 下午2:39:34				yanghui 	1.0
 */
@Entity
@Table(name="E_ENTERPRISE_COORDINATE")
public class EnterpriseCoordinate {
	
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 企业ID
	 */
	private Long enterpriseId;
	/**
	 * 企业名称
	 */
	private String enterpriseName;
	/**
	 * 经度
	 */
	private String longitude;
	/**
	 * 纬度
	 */
	private String latitude;
	
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
	
	@Column(name="ENTID", nullable=false)
	public Long getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	
	@Column(name="ENTERNAME", nullable=false,length=200)
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	
	@Column(name="LONGITUDE", nullable=false,length=20)
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	@Column(name="LATITUDE", nullable=false,length=20)
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	
	
	
	

}
