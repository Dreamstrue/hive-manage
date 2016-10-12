/**
 * 
 */
package com.hive.enterprisemanage.model;

import java.util.List;

import com.hive.enterprisemanage.entity.EnterpriseCustomerGroup;

/**  
 * Filename: EnterpriseCustomerGroupBean.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  YangHui
 * @version: 1.0  
 * @Create:  2014-6-3  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-6-3 上午9:59:16				YangHui 	1.0
 */
public class EnterpriseCustomerGroupBean extends EnterpriseCustomerGroup {
	
	public List<EnterpriseCostomer> list;

	public List<EnterpriseCostomer> getList() {
		return list;
	}

	public void setList(List<EnterpriseCostomer> list) {
		this.list = list;
	}

	
	

}
