/**
 * 
 */
package com.hive.enterprisemanage.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.enterprisemanage.entity.EnterpriseRoomShow;

import dk.dao.BaseDao;
import dk.service.BaseService;

/**  
 * Filename: EnterpriseRoomShowService.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-5-9  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-5-9 上午9:43:01				yanghui 	1.0
 */
@Service
public class EnterpriseRoomShowService extends BaseService<EnterpriseRoomShow> {

	
	@Resource
	private BaseDao<EnterpriseRoomShow> enterpriseRoomShowDao;
	
	@Override
	protected BaseDao<EnterpriseRoomShow> getDao() {
		return enterpriseRoomShowDao;
	}
	

}
