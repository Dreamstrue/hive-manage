package com.hive.surveymanage.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.surveymanage.entity.SurveyKeyWords;

import dk.dao.BaseDao;
import dk.service.BaseService;

@Service
public class SurveyKeyWordsService extends BaseService<SurveyKeyWords> {

	@Resource
	private BaseDao<SurveyKeyWords> surveyKeyWordsDao;
	@Override
	protected BaseDao<SurveyKeyWords> getDao() {
		return surveyKeyWordsDao;
	}
	
	
	/**
	 * 
	 * @Description: 通过问卷的行业类别查找对应的细化类关键字
	 * @author YangHui 
	 * @Created 2014-10-16
	 * @param key 关键字
	 * @return
	 */
	public List getKeyWordsListByKey(String key) {
		
		String hql = "select keywords from "+getEntityName()+" where industryName =? ";
		List list = getDao().find(hql, new Object[]{key});
		return list;
	}

	
	
}
