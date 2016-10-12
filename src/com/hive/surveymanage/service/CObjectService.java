package com.hive.surveymanage.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;



import com.hive.surveymanage.entity.CObject;

import dk.dao.BaseDao;
import dk.service.BaseService;
@Service
public class CObjectService extends BaseService<CObject>{
	
	@Resource
	private BaseDao<CObject> cobjectDao; 
	
	@Resource
	private SessionFactory sessionFactory;
	
	@Override
	protected BaseDao<CObject> getDao() {
		return cobjectDao;
	}
	
	/**
	 * 如果此对象尚未在质讯通这边存储则进行保存，已经有的直接返回 id
	 * @param cObject
	 * @return
	 */
	public Long saveCobject(CObject cObject) {
		List<CObject> list = getDao().find("from " + getEntityName() + " where objectType = '" + cObject.getObjectType() + "' and objectName = '" + cObject.getObjectName()+"'");
		if (list != null && list.size() > 0)
			return list.get(0).getId();
		else {
			cobjectDao.save(cObject);
			return cObject.getId();
		}
	}
}
