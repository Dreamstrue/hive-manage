package com.hive.systemconfig.model;

import com.hive.systemconfig.entity.SysObjectParameconfig;

public class SysObjectParameBean extends SysObjectParameconfig {
	
	private String parameCode;
	private String parameName;
	private String isRadio;

	public String getParameCode() {
		return parameCode;
	}

	public void setParameCode(String parameCode) {
		this.parameCode = parameCode;
	}

	public String getParameName() {
		return parameName;
	}

	public void setParameName(String parameName) {
		this.parameName = parameName;
	}

	public String getIsRadio() {
		return isRadio;
	}

	public void setIsRadio(String isRadio) {
		this.isRadio = isRadio;
	}
	
	
	

}
