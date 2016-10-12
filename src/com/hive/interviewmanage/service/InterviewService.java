package com.hive.interviewmanage.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.interviewmanage.entity.Interview;
import com.hive.util.DataUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class InterviewService extends BaseService<Interview>
{

  @Resource
  private BaseDao<Interview> interviewDao;

  protected BaseDao<Interview> getDao()
  {
    return this.interviewDao;
  }

  public DataGrid dataGrid(RequestPage page, String keys) {
    StringBuffer hql = new StringBuffer();
    hql.append(" from " + getEntityName() + " where 1=1 ");

    StringBuffer counthql = new StringBuffer();
    counthql.append(" select count(*) from " + getEntityName() + " where 1=1 ");
    StringBuffer sb = new StringBuffer();
    if (!DataUtil.isEmpty(keys)) {
      sb.append(" and csubject like '%" + keys + "%'");
    }
    sb.append(" and cvalid = '1'");
    sb.append(" order by dcreatetime desc");
    List list = getDao().find(page.getPage(), page.getRows(), hql.append(sb).toString(), new Object[0]);
    List newList = new ArrayList();
    for (int i = 0; i < list.size(); i++) {
      Interview inte = (Interview)list.get(i);
      String subject = inte.getCsubject();
      if (subject.length() > Integer.valueOf("30").intValue()) {
        subject = subject.substring(0, 20) + "......";
      }
      inte.setCsubject(subject);
      newList.add(inte);
    }

    Long count = Long.valueOf(getDao().count(counthql.append(sb).toString(), new Object[0]).longValue());
    return new DataGrid(count.longValue(), newList);
  }

  public int getInterviewByName(String subject) {
    int size = 0;
    List list = getDao().find(" from " + getEntityName() + " where csubject = ?", new Object[] { subject });
    if (list != null) {
      size = list.size();
    }
    return size;
  }

  public void logicDelete(String ids)
  {
    for (int i = 0; i < ids.split(",").length; i++)
      this.interviewDao.execute("UPDATE " + getEntityName() + " SET cvalid = ? WHERE nintonlid IN (" + ids + ")", new Object[] { "0" });
  }

  public boolean auditInterview(long interviewId, boolean pass, String auditOpinion, long auditId)
  {
    try
    {
      this.interviewDao.execute("UPDATE " + getEntityName() + " SET cauditstatus = ? , cauditopinion = ?, nauditid = ?, daudittime = sysdate WHERE nintonlid = ?", new Object[] { pass ? "1" : "2", auditOpinion, Long.valueOf(auditId), Long.valueOf(interviewId) });
      return true; } catch (Exception e) {
    }
    return false;
  }
}