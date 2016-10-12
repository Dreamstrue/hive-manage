package com.hive.votemanage.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.votemanage.entity.SurveyInfo;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class SurveyInfoService extends BaseService<SurveyInfo>
{

  @Resource
  private BaseDao<SurveyInfo> surveyInfoDao;

  protected BaseDao<SurveyInfo> getDao()
  {
    return this.surveyInfoDao;
  }

  public DataGrid datagrid(RequestPage page)
  {
    StringBuilder hql = new StringBuilder();
    hql.append("FROM ").append(getEntityName()).append(" WHERE valid = '")
      .append("1").append("' ")
      .append(" ORDER BY createTime DESC ");

    StringBuilder countHql = new StringBuilder();
    countHql.append("select count(*) FROM ").append(getEntityName()).append(" WHERE valid = '")
      .append("1").append("' ")
      .append(" ORDER BY createTime DESC ");

    long count = getDao().count(countHql.toString(), new Object[0]).longValue();
    List surveyInfoList = getDao().find(page.getPage(), page.getRows(), hql.toString(), new Object[0]);

    return new DataGrid(count, surveyInfoList);
  }
}