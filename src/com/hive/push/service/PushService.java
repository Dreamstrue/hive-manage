package com.hive.push.service;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.push.entity.PushInfo;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

/**
 * @description 
 * @author 燕珂
 * @createtime 2015-8-13 下午07:43:14
 */
@Service
public class PushService  extends BaseService<PushInfo> {

	@Resource
	private BaseDao<PushInfo> pushDao;
	
	@Override
	protected BaseDao<PushInfo> getDao() {
		return pushDao;
	}
	
	public DataGrid datagrid(RequestPage page, String title, String objectType) {
		String hql = (new StringBuilder("from ")).append(getEntityName())
		.append(" where valid = '" + SystemCommon_Constant.VALID_STATUS_1 + "'")
		.append(StringUtils.isNotBlank(title) ? (" and title like '%" + title + "%'") : "")
		.append(StringUtils.isNotBlank(objectType) ? (" and objectType = '" + objectType + "'") : "")
		.append(" order by id desc").toString();
		if (!StringUtils.isEmpty(page.getSort()))
			hql = (new StringBuilder(String.valueOf(hql))).append(" , ").append(page.getSort()).append(" ").append(page.getOrder()).toString();
		String counthql = "select count(*) " + hql;
		long count = getDao().count(counthql, new Object[0]).longValue();
		java.util.List rolelist = getDao().find(page.getPage(), page.getRows(), hql, new Object[0]);
		return new DataGrid(count, rolelist);
	}
	
	/**
	 * 逻辑删除
	 */
	public void delete_logic(Long id) {
		pushDao.execute("UPDATE " + getEntityName() + " SET valid = ? WHERE id = " + id, SystemCommon_Constant.VALID_STATUS_0);
	}
}
