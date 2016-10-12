/**
 * 
 */
package com.hive.systemconfig.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.systemconfig.entity.VersionCategory;

import dk.dao.BaseDao;
import dk.service.BaseService;

/**  
 * Filename: VersionCategoryService.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  YangHui
 * @version: 1.0  
 * @Create:  2014-9-1  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-9-1 上午9:45:08				YangHui 	1.0
 */
@Service
public class VersionCategoryService extends BaseService<VersionCategory> {

	
	@Resource
	private BaseDao<VersionCategory> versionCategoryDao;
	@Override
	protected BaseDao<VersionCategory> getDao() {
		return versionCategoryDao;
	}
	public List getList() {
		String hql  =" from "+getEntityName();
		List<VersionCategory> list = getDao().find(hql, new Object[0]);
		return list;
	}
	
	
	public void updateVersionCategory(String versionCate){
		String hql = " from "+getEntityName()+" where versionCate=? ";
		VersionCategory v = new VersionCategory();
		List list = getDao().find(hql, new Object[]{versionCate});
		if(list!=null && list.size()>0){
			v = (VersionCategory) list.get(0);
			v.setVersionNo(v.getVersionNo()+1);
			update(v);
		}
	}
	
	public VersionCategory getVersionByVersionCate(String vcate){
		String hql = " from "+getEntityName()+" where versionCate=? ";
		VersionCategory v = null;
		List list = getDao().find(hql, new Object[]{vcate});
		if(list!=null && list.size()>0){
			v = (VersionCategory) list.get(0);
		}
		return v;
	}
	

}
