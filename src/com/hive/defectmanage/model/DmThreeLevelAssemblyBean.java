package com.hive.defectmanage.model;
// default package

import com.hive.defectmanage.entity.DmThreeLevelAssembly;


/**
 * 
* Description: 三级分成代码表
* Copyright:Copyright (c)2014
 */
public class DmThreeLevelAssemblyBean extends DmThreeLevelAssembly {


     private String assemblyName;
     private String subAssemblyName;
     

	public String getAssemblyName() {
		return assemblyName;
	}

	public void setAssemblyName(String assemblyName) {
		this.assemblyName = assemblyName;
	}

	public String getSubAssemblyName() {
		return subAssemblyName;
	}

	public void setSubAssemblyName(String subAssemblyName) {
		this.subAssemblyName = subAssemblyName;
	}

}