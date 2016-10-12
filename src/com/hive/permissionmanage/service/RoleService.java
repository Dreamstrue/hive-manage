package com.hive.permissionmanage.service;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hive.permissionmanage.entity.Role;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

/**
 * @description 
 * @author 燕珂
 * @createtime 2013-10-18 下午01:45:48
 */
@Service
public class RoleService extends BaseService<Role> {


	@Resource
	private BaseDao<Role> roleDao;

	@Override
	protected BaseDao<Role> getDao() {
		return roleDao;
	}
	
	public DataGrid datagrid(RequestPage page)
	{
		String hql = (new StringBuilder("from ")).append(getEntityName()).toString();
		if (!StringUtils.isEmpty(page.getSort()))
			hql = (new StringBuilder(String.valueOf(hql))).append(" order by ").append(page.getSort()).append(" ").append(page.getOrder()).toString();
		String counthql = (new StringBuilder("select count(*) from ")).append(getEntityName()).toString();
		long count = getDao().count(counthql, new Object[0]).longValue();
		java.util.List rolelist = getDao().find(page.getPage(), page.getRows(), hql, new Object[0]);
		return new DataGrid(count, rolelist);
	}

}
