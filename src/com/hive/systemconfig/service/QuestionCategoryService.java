/**
 * 
 */
package com.hive.systemconfig.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.systemconfig.entity.QuestionCategory;

import dk.dao.BaseDao;
import dk.service.BaseService;

/**  
 * Filename: QuestionCatagoryService.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  YangHui
 * @version: 1.0  
 * @Create:  2014-10-23  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-10-23 下午3:09:27				YangHui 	1.0
 */
@Service
public class QuestionCategoryService extends BaseService<QuestionCategory> {

	@Resource
	private BaseDao<QuestionCategory> questionCategoryDao;
	@Override
	protected BaseDao<QuestionCategory> getDao() {
		return questionCategoryDao;
	}
	
	/**
	 * 
	 * @Description:
	 * @author YangHui 
	 * @Created 2014-10-23
	 * @return
	 */
	public List getQuestionCateList() {
		String hql = " from "+getEntityName()+" where valid ='1' order by sort asc ";
		return getDao().find(hql, new Object[0]);
	}


	public int getSubCateCountByPid(Long id) {
		int size = 0;
	    List list = getSubDataByPid(id);
	    if (list != null) {
	      size = list.size();
	    }
	    return size;
	}

	private List getSubDataByPid(Long id) {
		return getDao().find("from "+getEntityName()+" where parentId=? and valid='1' ", new Object[]{id});
	}

}
