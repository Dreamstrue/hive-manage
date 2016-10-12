package com.hive.votemanage.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.votemanage.entity.SurveyAnswer;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.service.BaseService;

@Service
public class SurveyAnswerService extends BaseService<SurveyAnswer>
{

  @Resource
  private BaseDao<SurveyAnswer> surveyAnswerDao;

  protected BaseDao<SurveyAnswer> getDao()
  {
    return this.surveyAnswerDao;
  }

  public DataGrid answerDatagridOf(Long surveryInfoId)
  {
    StringBuilder hql = new StringBuilder();
    hql.append("FROM ").append(getEntityName()).append(" WHERE valid = '")
      .append("1").append("' ");

    List answerList = getDao().find(hql.toString(), new Object[0]);
    return new DataGrid(answerList.size(), answerList);
  }
}