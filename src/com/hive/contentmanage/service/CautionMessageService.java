package com.hive.contentmanage.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.contentmanage.entity.CautionMessage;
import com.hive.util.DataUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class CautionMessageService extends BaseService<CautionMessage>
{

  @Resource
  private BaseDao<CautionMessage> cautionMessageDao;

  protected BaseDao<CautionMessage> getDao()
  {
    return this.cautionMessageDao;
  }

  public List getListForIndex()
  {
    return getDao().find(" from " + getEntityName() + " where cvalid=" + "1" + " and cauditstatus=" + "1" + " order by dcreatetime asc", new Object[0]);
  }

  public DataGrid dataGrid(RequestPage page, String keys, Long id) {
    StringBuffer hql = new StringBuffer();
    hql.append(" from " + getEntityName() + " where 1=1 ");

    StringBuffer counthql = new StringBuffer();
    counthql.append(" select count(*) from " + getEntityName() + " where 1=1 ");

    StringBuffer sb = new StringBuffer();
    if (!DataUtil.isEmpty(keys)) {
      sb.append(" and title like '%" + keys + "%'");
    }
    sb.append(" and nmenuid='" + id + "'");
    sb.append(" and cvalid = '1'");
    sb.append(" order by dcreatetime desc");
    List list = getDao().find(page.getPage(), page.getRows(), hql.append(sb).toString(), new Object[0]);
    List newList = new ArrayList();
    for (int i = 0; i < list.size(); i++) {
      CautionMessage caution = (CautionMessage)list.get(i);
      String title = caution.getTitle();
      if (title.length() > Integer.valueOf("30").intValue()) {
        title = title.substring(0, 20) + "......";
      }
      caution.setTitle(title);
      newList.add(caution);
    }
    Long count = Long.valueOf(getDao().count(counthql.append(sb).toString(), new Object[0]).longValue());
    return new DataGrid(count.longValue(), newList);
  }
  public int getMessageByName(String title) {
    int size = 0;
    List list = getReportListByName(title);
    if (list != null) {
      size = list.size();
    }
    return size;
  }

  public List<CautionMessage> getReportListByName(String title)
  {
    return getDao().find(" from " + getEntityName() + " where title = ?", new Object[] { title });
  }
}