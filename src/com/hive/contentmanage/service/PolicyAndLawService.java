package com.hive.contentmanage.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.contentmanage.entity.Policyandlaw;
import com.hive.util.DataUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class PolicyAndLawService extends BaseService<Policyandlaw>
{

  @Resource
  private BaseDao<Policyandlaw> policyAndLawDao;

  protected BaseDao<Policyandlaw> getDao()
  {
    return this.policyAndLawDao;
  }

  public List<Policyandlaw> allPolicyandlaw()
  {
    return getDao().find(" from " + getEntityName() + " order by dcreatetime asc", new Object[0]);
  }

  public List getListForIndex()
  {
    return getDao().find(" from " + getEntityName() + " where cvalid=" + "1" + " and cauditstatus=" + "1" + " order by dcreatetime asc", new Object[0]);
  }

  public int getPolicyandlawByName(String title)
  {
    int size = 0;
    List list = getPolicyandlawListByName(title);
    if (list != null) {
      size = list.size();
    }
    return size;
  }

  private List<Policyandlaw> getPolicyandlawListByName(String title)
  {
    return getDao().find(" from " + getEntityName() + " where clawname = ?", new Object[] { title });
  }

  public DataGrid dataGrid(RequestPage page, String keys, String menuid)
  {
    StringBuffer sql = new StringBuffer();
    sql.append(" from " + getEntityName() + " where 1=1 ");

    StringBuffer counthql = new StringBuffer();
    counthql.append(" select count(*) from " + getEntityName() + " where 1=1 ");

    StringBuffer sb = new StringBuffer();
    if (!menuid.equals(""))
    {
      sb.append(" and nmenuid='" + menuid + "' ");
    }
    if (!DataUtil.isEmpty(keys)) {
      sb.append(" and clawname like '%" + keys + "%'");
    }
    sb.append(" and cvalid = '1'");
    sb.append(" order by nmenuid,dcreatetime desc");
    List list = getDao().find(page.getPage(), page.getRows(), sql.append(sb).toString(), new Object[0]);

    List newList = new ArrayList();
    for (int i = 0; i < list.size(); i++) {
      Policyandlaw inte = (Policyandlaw)list.get(i);
      String title = inte.getClawname();
      if (title.length() > Integer.valueOf("30").intValue()) {
        title = title.substring(0, 20) + "......";
      }
      inte.setClawname(title);
      newList.add(inte);
    }

    Long count = Long.valueOf(getDao().count(counthql.append(sb).toString(), new Object[0]).longValue());
    return new DataGrid(count.longValue(), newList);
  }

  public boolean getPolicyandlawByNameAndId(Long id, String title)
  {
    boolean flag = true;
    List list = getDao().find("select id from " + getEntityName() + " where clawname = ?", new Object[] { title });
    if ((list.size() > 0) && 
      (!list.get(0).equals(id))) {
      flag = false;
    }

    return flag;
  }
}