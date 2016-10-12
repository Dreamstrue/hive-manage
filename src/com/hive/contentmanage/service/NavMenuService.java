/**
 * 
 */
package com.hive.contentmanage.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hive.contentmanage.entity.NavMenu;

import dk.dao.BaseDao;
import dk.service.BaseService;

/**  
 * Filename: NavMenuService.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-25  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-25 下午2:33:52				yanghui 	1.0
 */
@Service
public class NavMenuService extends BaseService<NavMenu>
{

  @Resource
  private BaseDao<NavMenu> navMenuDao;

  protected BaseDao<NavMenu> getDao()
  {
    return this.navMenuDao;
  }

  public List<NavMenu> getNavMenuListByName(String menuName)
  {
    return getDao().find(" from " + getEntityName() + " where text = ?", new Object[] { menuName });
  }

  public int getNavMenuByName(String menuName)
  {
    int size = 0;
    List list = getNavMenuListByName(menuName);
    if (list != null) {
      size = list.size();
    }
    return size;
  }

  public boolean getNavMenuByNameAndId(Long id, String name)
  {
    boolean flag = true;
    List list = getDao().find("select id from " + getEntityName() + " where text = ?", new Object[] { name });
    if ((list.size() > 0) && 
      (!list.get(0).equals(id))) {
      flag = false;
    }

    return flag;
  }

  public List<NavMenu> allNavMenu(String menuIds)
  {
    List list = new ArrayList();
    if (StringUtils.isNotBlank(menuIds))
      list = getDao().find(" from " + getEntityName() + ("all".equals(menuIds) ? " where 1=1" : new StringBuilder(" where id in (").append(menuIds).append(") ").toString()) + " and cvalid='" + "1" + "' order by sortOrder,valid", new Object[0]);
    return list;
  }

  public List<NavMenu> allNavMenu() {
    return getDao().find(" from " + getEntityName() + " where 1=1 and cvalid='" + "1" + "' order by sortOrder,valid", new Object[0]);
  }

  public List<NavMenu> allNavMenuIsValid()
  {
    return getDao().find(" from " + getEntityName() + " where valid = '" + "1" + "' order by sortOrder", new Object[0]);
  }

  public List<NavMenu> getNavMenuByPid(Long id)
  {
    return getDao().find("from NavMenu where pid='" + id + "' and valid='" + "1" + "'", new Object[0]);
  }

  public int getNavMenuCountByPid(Long id)
  {
    int size = 0;
    List list = getNavMenuByPid(id);
    if (list != null) {
      size = list.size();
    }
    return size;
  }

  public List<NavMenu> getNavListByPidAndUrl(Long pid)
  {
    return getDao().find(" from " + getEntityName() + " where pid=? and cvalid='" + "1" + "' and url is not null", new Object[] { pid });
  }
}
