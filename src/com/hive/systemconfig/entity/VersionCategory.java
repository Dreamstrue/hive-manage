/**
 * 
 */
package com.hive.systemconfig.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.hive.common.SystemCommon_Constant;

/**  
 * Filename: VersionCategory.java  
 * Description:  版本类别表
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  YangHui
 * @version: 1.0  
 * @Create:  2014-9-1  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-9-1 上午9:23:56				YangHui 	1.0
 */
@Entity
@Table(name="VERSION_CATEGORY")
public class VersionCategory {
	
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 版本名称
	 */
	private String versionName;
	/**
	 * 版本号
	 */
	private int versionNo = new Integer(1);
	/**
	 * 版本类别
	 */
	private String versionCate;
	/**
	 * 是否可用
	 */
	private String valid = SystemCommon_Constant.VALID_STATUS_1;
	
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
	
	@Column(name="VERSIONNAME",nullable=false,length=100)
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	@Column(name="VERSIONNO",nullable=false,length=20)
	public int getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(int versionNo) {
		this.versionNo = versionNo;
	}
	@Column(name="VERSIONCATE",nullable=false,length=50)
	public String getVersionCate() {
		return versionCate;
	}
	public void setVersionCate(String versionCate) {
		this.versionCate = versionCate;
	}
	@Column(name="VALID",nullable=false,length=1)
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	
	
	

}
