package com.hive.contentmanage.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.contentmanage.entity.Emagazine;
import com.hive.util.DataUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class EmagazineService extends BaseService<Emagazine>
{

  @Resource
  private BaseDao<Emagazine> magazineDao;

  protected BaseDao<Emagazine> getDao()
  {
    return this.magazineDao;
  }

  public DataGrid dataGrid(RequestPage page, String s, Long id)
  {
    StringBuffer sb = new StringBuffer();
    sb.append(" from " + getEntityName() + " where 1=1 ");
    if (!DataUtil.isEmpty(s)) {
      sb.append(" and title like '%" + s + "%'");
    }

    sb.append(" and cvalid='1'");
    sb.append(" and nmenuid ='" + id + "'");
    sb.append(" order by dcreatetime desc");

    List list = getDao().find(page.getPage(), page.getRows(), sb.toString(), new Object[0]);

    StringBuffer hql = new StringBuffer();
    hql.append(" select count(*) ").append(sb);
    Long count = Long.valueOf(getDao().count(hql.toString(), new Object[0]).longValue());
    return new DataGrid(count.longValue(), list);
  }

  public int gettitleByName(String title)
  {
    int size = 0;
    List list = getEmagazineListByName(title);
    if (list != null) {
      size = list.size();
    }
    return size;
  }

  public List<Emagazine> getEmagazineListByName(String title) {
    return getDao().find(" from " + getEntityName() + " where title = ?", new Object[] { title });
  }
}