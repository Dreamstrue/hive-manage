package com.hive.contentmanage.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.contentmanage.entity.IndustryStandard;
import com.hive.util.DataUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class IndustryStandardService extends BaseService<IndustryStandard>
{

  @Resource
  private BaseDao<IndustryStandard> industryStandardDao;

  protected BaseDao<IndustryStandard> getDao()
  {
    return this.industryStandardDao;
  }

  public List getListForIndex()
  {
    return getDao().find(" from " + getEntityName() + " where cvalid=" + "1" + " and cauditstatus=" + "1" + " order by dcreatetime asc", new Object[0]);
  }

  public DataGrid dataGrid(RequestPage page, String keys, String menuid) {
    StringBuffer hql = new StringBuffer();
    hql.append(" from " + getEntityName() + " where 1=1 ");

    StringBuffer counthql = new StringBuffer();
    counthql.append(" select count(*) from " + getEntityName() + " where 1=1 ");
    StringBuffer sb = new StringBuffer();
    if (!menuid.equals(""))
    {
      sb.append(" and nmenuid='" + menuid + "' ");
    }
    if (!DataUtil.isEmpty(keys)) {
      sb.append(" and title like '%" + keys + "%'");
    }
    sb.append(" and cvalid = '1'");
    sb.append(" order by nmenuid,dcreatetime desc");
    List list = getDao().find(page.getPage(), page.getRows(), hql.append(sb).toString(), new Object[0]);

    List newList = new ArrayList();
    for (int i = 0; i < list.size(); i++) {
      IndustryStandard inte = (IndustryStandard)list.get(i);
      String title = inte.getTitle();
      if (title.length() > Integer.valueOf("30").intValue()) {
        title = title.substring(0, 20) + "......";
      }
      inte.setTitle(title);
      newList.add(inte);
    }

    Long count = Long.valueOf(getDao().count(counthql.append(sb).toString(), new Object[0]).longValue());
    return new DataGrid(count.longValue(), newList);
  }

  public int getIndustryByName(String title)
  {
    int size = 0;
    List list = getIndustryListByName(title);
    if (list != null) {
      size = list.size();
    }
    return size;
  }

  private List<IndustryStandard> getIndustryListByName(String title) {
    return getDao().find(" from " + getEntityName() + " where title = ?", new Object[] { title });
  }

  public boolean getIndustryByNameAndId(Long id, String title)
  {
    boolean flag = true;
    List list = getDao().find("select id from " + getEntityName() + " where title = ?", new Object[] { title });
    if ((list.size() > 0) && 
      (!list.get(0).equals(id))) {
      flag = false;
    }

    return flag;
  }
}