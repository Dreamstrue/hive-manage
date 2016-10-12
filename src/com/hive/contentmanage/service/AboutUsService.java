package com.hive.contentmanage.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.contentmanage.entity.Aboutus;
import com.hive.util.DataUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class AboutUsService extends BaseService<Aboutus>
{

  @Resource
  private BaseDao<Aboutus> aboutusDao;

  protected BaseDao<Aboutus> getDao()
  {
    return this.aboutusDao;
  }
  public Aboutus getAboutUsInfoByMenuId(Long id) {
    Aboutus us = new Aboutus();
    List list = this.aboutusDao.find(" from " + getEntityName() + " where nmenuid=?", new Object[] { id });
    if (list.size() > 0) {
      us = (Aboutus)list.get(0);
    }
    return us;
  }

  public DataGrid dataGrid(RequestPage page, String keys, Long id) {
    StringBuffer hql = new StringBuffer();
    hql.append(" from " + getEntityName() + " where 1=1 ");

    StringBuffer counthql = new StringBuffer();
    counthql.append(" select count(*) from " + getEntityName() + " where 1=1 ");

    StringBuffer sb = new StringBuffer();
    if (!DataUtil.isEmpty(keys)) {
      sb.append(" and ctitle like '%" + keys + "%'");
    }
    sb.append(" and nmenuid='" + id + "'");
    sb.append(" and cvalid = '1'");
    sb.append(" order by dcreatetime desc");
    List list = getDao().find(page.getPage(), page.getRows(), hql.append(sb).toString(), new Object[0]);
    List newList = new ArrayList();
    for (int i = 0; i < list.size(); i++) {
      Aboutus caution = (Aboutus)list.get(i);
      String title = caution.getCtitle();
      if (title.length() > Integer.valueOf("30").intValue()) {
        title = title.substring(0, 20) + "......";
      }
      caution.setCtitle(title);
      newList.add(caution);
    }
    Long count = Long.valueOf(getDao().count(counthql.append(sb).toString(), new Object[0]).longValue());
    return new DataGrid(count.longValue(), newList);
  }
}