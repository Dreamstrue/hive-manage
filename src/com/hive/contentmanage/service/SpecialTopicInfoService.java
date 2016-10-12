package com.hive.contentmanage.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.contentmanage.entity.SpecialTopicInfo;
import com.hive.util.DataUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class SpecialTopicInfoService extends BaseService<SpecialTopicInfo>
{

  @Resource
  private BaseDao<SpecialTopicInfo> specialTopicInfoDao;

  protected BaseDao<SpecialTopicInfo> getDao()
  {
    return this.specialTopicInfoDao;
  }

  public List<SpecialTopicInfo> allSpecialInfos()
  {
    List<SpecialTopicInfo> list = getDao().find(" from " + getEntityName() + " where cvalid='" + "1" + "' order by dcreatetime desc", new Object[0]);
    List newList = new ArrayList();
    for (SpecialTopicInfo info : list) {
      if (info != null) {
        info.getAttributes().put("pid", info.getPid());
        newList.add(info);
      }
    }
    return newList;
  }

  public List<SpecialTopicInfo> specialList()
  {
    return getDao().find(" from " + getEntityName() + " where pid = 1", new Object[0]);
  }

  public DataGrid DataGrid(RequestPage page, String keys, String pid)
  {
    StringBuffer hql = new StringBuffer();
    hql.append(" from " + getEntityName() + " where 1=1 ");

    StringBuffer counthql = new StringBuffer();
    counthql.append(" select count(*) from " + getEntityName() + " where 1=1 ");

    StringBuffer sb = new StringBuffer();
    if (!DataUtil.isEmpty(keys)) {
      sb.append(" and text like '%" + keys + "%'");
    }
    sb.append(" and cvalid = '1'");
    sb.append(" and pid = '" + pid + "' order by dcreatetime desc");
    List list = getDao().find(page.getPage(), page.getRows(), hql.append(sb).toString(), new Object[0]);

    Long count = Long.valueOf(getDao().count(counthql.append(sb).toString(), new Object[0]).longValue());
    return new DataGrid(count.longValue(), list);
  }

  public List<SpecialTopicInfo> getSpecialInfoByPid(Long id)
  {
    return getDao().find(" from " + getEntityName() + " where pid = ?", new Object[] { id });
  }

  public List<SpecialTopicInfo> getSpecialInfoByPidIsValid(Long id)
  {
    return getDao().find(" from " + getEntityName() + " where pid = ? and cvalid='" + "1" + "'", new Object[] { id });
  }
}