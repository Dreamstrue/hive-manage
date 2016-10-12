package com.hive.permissionmanage.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hive.permissionmanage.entity.Action;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

/**
 * @description 
 * @author 燕珂
 * @createtime 2013-10-22 上午11:07:27
 */
@Service
public class ActionService extends BaseService<Action> {

	@Resource
	private BaseDao<Action> actionDao;

	@Override
	protected BaseDao<Action> getDao() {
		return actionDao;
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
	
	public int getMaxActionValue() {
		int maxActionValue = 0;
		List list = getDao().find("SELECT nvl(max(iactionvalue), 0) FROM " + getEntityName()); // 没有记录时，max() 得到为空
		maxActionValue = (Integer)list.get(0);
		if (maxActionValue == 0) // 第一条记录动作值为1，后面的每次扩大2倍
			maxActionValue = 1;
		else
			maxActionValue = 2 * maxActionValue;
		return maxActionValue;
	}
	
	public List<Action> getActionList() {
		return getDao().find(" from "+getEntityName() + " order by iactionvalue asc");
	}
}
