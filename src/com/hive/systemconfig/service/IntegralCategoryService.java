package com.hive.systemconfig.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.systemconfig.entity.IntegralCategory;

import dk.dao.BaseDao;
import dk.service.BaseService;
@Service
public class IntegralCategoryService extends BaseService<IntegralCategory> {

	@Resource
	private BaseDao<IntegralCategory> integralCategoryDao;
	@Override
	protected BaseDao<IntegralCategory> getDao() {
		return integralCategoryDao;
	}
	public List<IntegralCategory> treegrid() {
		return findIntegralCategoryValid();
	}
	public boolean isExistIntegralCategoryByName(String cateName) {
		boolean flag = false;
		List<IntegralCategory> list = getListByIntegralCateName(cateName);
		if(list.size()>0){
				flag = true;
		}
		return flag;
	}
	private List<IntegralCategory> getListByIntegralCateName(String cateName) {
		return getDao().find(" from "+ getEntityName() + " where text=? and valid = ?", new Object[]{cateName,SystemCommon_Constant.VALID_STATUS_1});
	}
	public boolean isExistIntegralCategoryByNameAndId(String cateName, Long id) {
		boolean flag = false;
		List<IntegralCategory> list = getListByIntegralCateName(cateName);
		if(list.size()>0){
			IntegralCategory i = (IntegralCategory) list.get(0);
			if(!i.getId().equals(id)){
				flag = true;
			}
		}
		return flag;
	}
	public boolean isExistChildren(Long id) {
		boolean flag = false;
		List<IntegralCategory> list = getDao().find(" from "+ getEntityName() + " where parentId = ? and valid = ?",new Object[]{id,SystemCommon_Constant.VALID_STATUS_1});
		if(list.size()>0){
			flag = true;
		}
		return flag;
	}
	public List<IntegralCategory> findIntegralCategoryValid() {
		return getDao().find(" from "+getEntityName()+" where valid=? order by createTime desc ", SystemCommon_Constant.VALID_STATUS_1);
	}

}
