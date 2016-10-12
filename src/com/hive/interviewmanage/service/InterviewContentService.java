package com.hive.interviewmanage.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.interviewmanage.entity.InterviewContent;
import com.hive.util.DataUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class InterviewContentService extends BaseService<InterviewContent>
{

  @Resource
  private BaseDao<InterviewContent> interviewContentDao;

  protected BaseDao<InterviewContent> getDao()
  {
    return this.interviewContentDao;
  }

  public DataGrid dataGrid(RequestPage page, String keys, long interviewId) {
    StringBuffer hql = new StringBuffer();
    hql.append(" from " + getEntityName() + " where 1=1 ");

    StringBuffer counthql = new StringBuffer();
    counthql.append(" select count(*) from " + getEntityName() + " where 1=1 ");
    StringBuffer sb = new StringBuffer();
    if (!DataUtil.isEmpty(keys)) {
      sb.append(" and cdialoguecontent like '%" + keys + "%'");
    }
    sb.append(" and nintonlid = " + interviewId);
    sb.append(" and cvalid = '1'");
    sb.append(" order by dcreatetime desc");
    List list = getDao().find(page.getPage(), page.getRows(), hql.append(sb).toString(), new Object[0]);

    Long count = Long.valueOf(getDao().count(counthql.append(sb).toString(), new Object[0]).longValue());
    return new DataGrid(count.longValue(), list);
  }

  public String getLiveContent(long interviewId) {
    StringBuffer liveContentSb = new StringBuffer();
    String hql = " from " + getEntityName() + " where nintonlid = " + interviewId + "order by dcreatetime asc";
    List list = getDao().find(hql, new Object[0]);
    for (int i = 0; i < list.size(); i++) {
      InterviewContent interviewContent = (InterviewContent)list.get(i);
      liveContentSb.append(interviewContent.getCdialoguecontent()).append("<br/>");
    }
    return liveContentSb.toString();
  }

  public void logicDelete(String ids)
  {
    for (int i = 0; i < ids.split(",").length; i++)
      this.interviewContentDao.execute("UPDATE " + getEntityName() + " SET cvalid = ? WHERE nintconid IN (" + ids + ")", new Object[] { "0" });
  }
}