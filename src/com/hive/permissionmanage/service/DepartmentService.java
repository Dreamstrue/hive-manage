package com.hive.permissionmanage.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.permissionmanage.entity.Department;

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
public class DepartmentService extends BaseService<Department> {


	@Resource
	private BaseDao<Department> departmentDao;

	@Override
	protected BaseDao<Department> getDao() {
		return departmentDao;
	}
	
	public List<Department> allDeparment() {
		return getDao().find(
				" from " + getEntityName() + " where cvalid = " + SystemCommon_Constant.VALID_STATUS_1 + " order by ndepartmentid asc");
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
	
	/**
	 * 逻辑删除
	 */
	public void logicDelete(String ids) {
		for (int i = 0; i < ids.split(",").length; i++) {
			departmentDao.execute("UPDATE " + getEntityName() + " SET cvalid = ? WHERE ndepartmentid IN (" + ids + ")", SystemCommon_Constant.VALID_STATUS_0);
		}
	}

}
