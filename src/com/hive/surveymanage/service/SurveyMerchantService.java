/**
 * 
 */
package com.hive.surveymanage.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.surveymanage.entity.SurveyMerchant;

import dk.dao.BaseDao;
import dk.service.BaseService;

/**  
 * Filename: SurveyMerchantService.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  YangHui
 * @version: 1.0  
 * @Create:  2014-10-11  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-10-11 上午9:43:10				YangHui 	1.0
 */
@Service
public class SurveyMerchantService extends BaseService<SurveyMerchant> {

	
	@Resource
	private BaseDao<SurveyMerchant> surveyMerchantDao;
	@Override
	protected BaseDao<SurveyMerchant> getDao() {
		return surveyMerchantDao;
	}

}
