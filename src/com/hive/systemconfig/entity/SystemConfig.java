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
 * 
* Filename: SystemConfig.java  
* Description: 系统参数配置 实体类
* Copyright:Copyright (c)2014
* Company:  GuangFan 
* @author:  yanghui
* @version: 1.0  
* @Create:  2014-3-31  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2014-3-31 下午1:46:35				yanghui 	1.0
 */
@Entity
@Table(name="SYS_PARAMECONFIG")
public class SystemConfig {
	
	private Long id;
	private String parameCategory;//参数类别
	private String parameCode;//参数代码
	private String parameName;//参数名称
	private Long parameDefaultValue;//参数默认值
	private Long parameCurrentValue;//参数当前值
	private String valid;
	private String isRadio; 
	public String getIsRadio() {
		return isRadio;
	}
	public void setIsRadio(String isRadio) {
		this.isRadio = isRadio;
	}

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
	
	@Column(name="PARAMECATEGORY",nullable=false,length = 50)
	public String getParameCategory() {
		return parameCategory;
	}
	public void setParameCategory(String parameCategory) {
		this.parameCategory = parameCategory;
	}
	
	@Column(name="PARAMECODE",nullable=false,length = 20)
	public String getParameCode() {
		return parameCode;
	}
	public void setParameCode(String parameCode) {
		this.parameCode = parameCode;
	}
	
	@Column(name="PARAMENAME",nullable=false,length = 50)
	public String getParameName() {
		return parameName;
	}
	public void setParameName(String parameName) {
		this.parameName = parameName;
	}
	
	@Column(name="PARAMEDEFAULTVALUE",nullable=true)
	public Long getParameDefaultValue() {
		return parameDefaultValue;
	}
	public void setParameDefaultValue(Long parameDefaultValue) {
		this.parameDefaultValue = parameDefaultValue;
	}
	
	@Column(name="PARAMECURRENTVALUE",nullable=true)
	public Long getParameCurrentValue() {
		return parameCurrentValue;
	}
	public void setParameCurrentValue(Long parameCurrentValue) {
		this.parameCurrentValue = parameCurrentValue;
	}
	
	@Column(name = "valid", nullable = false, length = 1)
	public String getValid() {
		return this.valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}
	
	
	
	
	
}
