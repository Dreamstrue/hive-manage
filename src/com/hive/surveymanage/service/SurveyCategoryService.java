package com.hive.surveymanage.service;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hive.surveymanage.entity.SurveyCategory;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

/**
 * @description 
 * @author 燕珂
 * @createtime 2014-3-10 下午04:47:36
 */
@Service
public class SurveyCategoryService extends BaseService<SurveyCategory> {
	@Resource
	private BaseDao<SurveyCategory> actionDao;

	@Override
	protected BaseDao<SurveyCategory> getDao() {
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
}
