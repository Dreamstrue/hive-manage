/**
 * 
 */
package com.hive.systemconfig.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.systemconfig.entity.SentimentKeyWord;

import dk.dao.BaseDao;
import dk.service.BaseService;

/**  
 * Filename: SentimentKeyWordService.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  YangHui
 * @version: 1.0  
 * @Create:  2014-6-17  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-6-17 下午4:30:44				YangHui 	1.0
 */
@Service
public class SentimentKeyWordService extends BaseService<SentimentKeyWord> {

	
	@Resource
	private BaseDao<SentimentKeyWord> sentimentKeyWordDao;
	@Override
	protected BaseDao<SentimentKeyWord> getDao() {
		return sentimentKeyWordDao;
	}
	
	

}
