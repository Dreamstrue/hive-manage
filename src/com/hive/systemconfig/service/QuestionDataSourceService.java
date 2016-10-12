package com.hive.systemconfig.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.systemconfig.entity.QuestionDataSource;

import dk.dao.BaseDao;
import dk.service.BaseService;

@Service
public class QuestionDataSourceService extends BaseService<QuestionDataSource> {

	@Resource
	private BaseDao<QuestionDataSource> questionDataSourceDao;
	@Override
	protected BaseDao<QuestionDataSource> getDao() {
		return questionDataSourceDao;
	}
	
	/**
	 * 
	 * @Description: 问卷问题数据源列表
	 * @author YangHui 
	 * @Created 2014-10-23
	 * @return
	 */
	public List allquestionData() {
		String hql = " from " + getEntityName()+" where valid='1' order by sort asc ";
		return getDao().find(hql, new Object[0]);
	}

	public int getSubDataCountByPid(Long id) {
		int size = 0;
	    List list = getSubDataByPid(id);
	    if (list != null) {
	      size = list.size();
	    }
	    return size;
	}
	
	  public List<QuestionDataSource> getSubDataByPid(Long id)
	  {
	    return getDao().find("from "+getEntityName()+" where parentId='" + id + "' and valid='" + "1" + "'", new Object[0]);
	  }

}
 