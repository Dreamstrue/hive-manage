package com.hive.util;

import com.hive.integralmanage.model.IntegralDetailBean;

/**
 *   为积分明细排列写的方法
* Filename: Comparator.java  
* Description:
* Copyright:Copyright (c)2014
* Company:  GuangFan 
* @author:  yanghui
* @version: 1.0  
* @Create:  2014-3-28  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2014-3-28 上午11:22:48				yanghui 	1.0
 */
public class Comparator implements java.util.Comparator<IntegralDetailBean> {

	public int compare(IntegralDetailBean bean1, IntegralDetailBean bean2) {
		int flag = 0;
		flag = bean2.getIntegralDate().compareTo(bean1.getIntegralDate());
		return flag;
	}

}
