package com.hive.defectmanage.model;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.hive.common.SystemCommon_Constant;
import com.hive.defectmanage.entity.DmSubAssembly;


/**
 * 
* Description: 分总成代码表
* Copyright:Copyright (c)2014
 */
public class DmSubAssemblyBean extends DmSubAssembly {


     private String assemblyName;

	public String getAssemblyName() {
		return assemblyName;
	}

	public void setAssemblyName(String assemblyName) {
		this.assemblyName = assemblyName;
	}

}