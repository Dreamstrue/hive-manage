/**
 * 
 */
package com.hive.intendmanage.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.intendmanage.entity.IntendRelPrize;

import dk.dao.BaseDao;
import dk.service.BaseService;

/**  
 * Filename: IntendRelPrizeService.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-11  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-11 上午11:01:25				yanghui 	1.0
 */
@Service
public class IntendRelPrizeService extends BaseService<IntendRelPrize> {

	@Resource
	private BaseDao<IntendRelPrize> intendRelPrizeDao;
	@Override
	protected BaseDao<IntendRelPrize> getDao() {
		return intendRelPrizeDao;
	}
	public IntendRelPrize getByIntendNo(String intendNo) {
		List list  = getDao().find(" from "+getEntityName()+" where intendNo=?", intendNo);
		IntendRelPrize irp  = (IntendRelPrize)list.get(0) ;
		return irp;
	}

}
