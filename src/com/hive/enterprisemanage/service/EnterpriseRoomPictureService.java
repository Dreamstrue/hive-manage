/**
 * 
 */
package com.hive.enterprisemanage.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.enterprisemanage.entity.EnterpriseRoomPicture;

import dk.dao.BaseDao;
import dk.service.BaseService;

/**  
 * Filename: EnterpriseRoomPictureService.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-5-9  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-5-9 上午9:44:32				yanghui 	1.0
 */
@Service
public class EnterpriseRoomPictureService extends	BaseService<EnterpriseRoomPicture> {

	@Resource
	private BaseDao<EnterpriseRoomPicture> enterpriseRoomPictureDao;
	@Override
	protected BaseDao<EnterpriseRoomPicture> getDao() {
		return enterpriseRoomPictureDao;
	}

}
