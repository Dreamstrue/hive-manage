package com.hive.contentmanage.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.contentmanage.entity.SpecialTopicContent;
import com.hive.contentmanage.entity.SpecialTopicInfo;
import com.hive.contentmanage.model.SpecialContentInfoBean;
import com.hive.util.DataUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class SpecialTopicContentService extends BaseService<SpecialTopicContent>
{

  @Resource
  private BaseDao<SpecialTopicContent> specialTopicContentDao;

  @Resource
  private SpecialTopicInfoService infoService;

  protected BaseDao<SpecialTopicContent> getDao()
  {
    return this.specialTopicContentDao;
  }

  public DataGrid dataGrid(RequestPage page, String keys, String id, String pid)
  {
    List newList = new ArrayList();
    StringBuffer hql = new StringBuffer();
    StringBuffer counthql = new StringBuffer();
    StringBuffer sb = new StringBuffer();

    hql.append(" from " + getEntityName() + " where 1=1");
    counthql.append(" select count(*) from " + getEntityName() + " where 1=1 ");

    Long count = new Long(0L);
    if (pid.equals("0"))
    {
      hql.append(" and pid in ( select id from SpecialTopicInfo where pid in (" + id + "))");
      counthql.append(" and  pid in ( select id from SpecialTopicInfo where pid in (" + id + "))");
    }
    else if ((!pid.equals("")) && (!pid.equals("0")))
    {
      hql.append(" and pid = '" + id + "'");
      counthql.append(" and pid = '" + id + "'");
    }

    if (!DataUtil.isEmpty(keys)) {
      sb.append(" and title like '%" + keys + "%'");
    }
    sb.append(" and cvalid = '1'");
    sb.append(" order by dcreatetime desc,cauditstatus asc");

    newList = commonList(newList, page, hql.append(sb));
    count = getDao().count(counthql.append(sb).toString(), new Object[0]).longValue();
    return new DataGrid(count.longValue(), newList);
  }

  private List<SpecialContentInfoBean> commonList(List<SpecialContentInfoBean> newList, RequestPage page, StringBuffer hql)
  {
    List list = getDao().find(page.getPage(), page.getRows(), hql.toString(), new Object[0]);

    for (int i = 0; i < list.size(); i++) {
      SpecialContentInfoBean bean = new SpecialContentInfoBean();
      SpecialTopicContent inte = (SpecialTopicContent)list.get(i);
      bean.setId(inte.getId());
      bean.setPid(inte.getPid());
      bean.setDcreatetime(inte.getDcreatetime());
      bean.setCauditstatus(inte.getCauditstatus());
      bean.setCvalid(inte.getCvalid());

      SpecialTopicInfo info = (SpecialTopicInfo)this.infoService.get(inte.getPid());
      String navName = info.getText();
      bean.setSpecialNavName(navName);

      SpecialTopicInfo info1 = (SpecialTopicInfo)this.infoService.get(info.getPid());
      String infoName = info1.getText();
      bean.setSpecialInfoName(infoName);

      String title = inte.getTitle();
      if (title.length() > Integer.valueOf("30").intValue()) {
        title = title.substring(0, 20) + "......";
      }
      bean.setTitle(title);
      newList.add(bean);
    }

    return newList;
  }

  public List<SpecialTopicContent> getContentListByPid(Long id)
  {
    return getDao().find(" from " + getEntityName() + " where pid=? and cvalid='" + "1" + "'", new Object[] { id });
  }

  public int getContentByName(String title)
  {
    int size = 0;
    List list = getContentListByName(title);
    if (list != null) {
      size = list.size();
    }
    return size;
  }

  public List<SpecialTopicContent> getContentListByName(String title) {
    return getDao().find(" from " + getEntityName() + " where title = ?", new Object[] { title });
  }
}