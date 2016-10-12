/**
 * 
 */
package com.hive.contentmanage.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.contentmanage.entity.NavMenu;
import com.hive.contentmanage.service.NavMenuService;
import com.hive.permissionmanage.entity.Menu;
import com.hive.permissionmanage.service.MenuService;
import com.hive.util.DataUtil;
import com.hive.util.DateUtil;

import dk.controller.BaseController;

/**  
 * Filename: NavMenuController.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-25  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-25 下午2:32:23				yanghui 	1.0
 */
@Controller
@RequestMapping("navMenu")
public class NavMenuController extends BaseController
{
  private static final String PREFIX = "navmenu";

  @Resource
  private NavMenuService navMenuService;

  @Resource
  private MenuService menuService;

  @RequestMapping("manage")
  public String manage()
  {
    return PREFIX+"/menuManage";
  }

  @RequestMapping("allNavMenu")
  @ResponseBody
  public List<NavMenu> allNavMenu(String menuIds) {
    List list = this.navMenuService.allNavMenu("all");
    return list;
  }

  @RequestMapping("allNavMenuIsValid")
  @ResponseBody
  public List<NavMenu> allNavMenuIsValid()
  {
    List list = this.navMenuService.allNavMenuIsValid();
    return list;
  }

  @RequestMapping("subNavMenu")
  @ResponseBody
  public List<NavMenu> subNavMenu(@RequestParam("id") Long id) {
    List list = this.navMenuService.getNavMenuByPid(id);
    return list;
  }

  @RequestMapping("add")
  public String add() {
    return PREFIX+"/menuAdd";
  }

  @RequestMapping("insert")
  @ResponseBody
  public Map<String, Object> insert(NavMenu menu, HttpSession session) {
    String menuName = menu.getText();
    int size = this.navMenuService.getNavMenuByName(menuName);
    if (size > 0) {
      return error("菜单【" + menuName + "】已存在");
    }

    menu.setCreateUserId((Long)session.getAttribute("userId"));
    Timestamp stamp = DateUtil.getTimestamp();
    String dateStr = DateUtil.Time14ToString(stamp);
    menu.setCreateTime(DateUtil.format(dateStr));

    if (StringUtils.isEmpty(menu.getUrl()))
      menu.setLeaf("1");
    else {
      menu.setLeaf("0");
    }
    menu.setValid("1");

    this.navMenuService.save(menu);

    Long pid = menu.getPid();
    NavMenu m = (NavMenu)this.navMenuService.get(pid);
    if (m != null) {
      if ((StringUtils.isNotEmpty(menu.getUrl())) && 
        (StringUtils.isEmpty(m.getUrl()))) {
        m.setLeaf("0");
      }

      this.navMenuService.update(m);
    }
    return success("添加成功", menu);
  }

  @RequestMapping("edit")
  public String edit(Model model, @RequestParam("id") Long id)
  {
    model.addAttribute("vo", this.navMenuService.get(id));
    return PREFIX+"/menuEdit";
  }
  @RequestMapping("update")
  @ResponseBody
  public Map<String, Object> update(NavMenu menu, HttpSession session) {
    NavMenu nav = (NavMenu)this.navMenuService.get(menu.getId());
    Long oldPid = nav.getPid();
    Long newPid = menu.getPid();

    int newsize = this.navMenuService.getNavMenuCountByPid(newPid);

    Long id = menu.getId();
    String menuName = menu.getText();

    boolean flag = this.navMenuService.getNavMenuByNameAndId(id, menuName);
    if (!flag) {
      return error("菜单名称【" + menuName + "】已存在");
    }
    nav.setText(menuName);
    nav.setMenuHref(menu.getMenuHref());
    nav.setUrl(menu.getUrl());
    nav.setPid(menu.getPid());
    nav.setSortOrder(menu.getSortOrder());
    nav.setConentity(menu.getConentity());

    nav.setModifUserId((Long)session.getAttribute("userId"));
    nav.setModifyTime(DateUtil.getTimestamp());

    int count = this.navMenuService.getNavMenuCountByPid(id);
    if (count > 0) {
      List navList = this.navMenuService.getNavListByPidAndUrl(id);
      if (navList.size() > 0)
      {
        if (StringUtils.isEmpty(nav.getUrl())) {
          nav.setLeaf("0");
        }
      }
    }
    this.navMenuService.update(nav);

    if (oldPid.longValue() != newPid.longValue()) {
      NavMenu m = (NavMenu)this.navMenuService.get(oldPid);
      if (m != null)
      {
        int size = this.navMenuService.getNavMenuCountByPid(oldPid);
        if (size == 0)
        {
          if (StringUtils.isEmpty(m.getUrl()))
            m.setLeaf("1");
        }
        else {
          List navList = this.navMenuService.getNavListByPidAndUrl(oldPid);
          if ((navList.size() == 0) && 
            (StringUtils.isEmpty(m.getUrl()))) {
            m.setLeaf("1");
          }
        }

        this.navMenuService.update(m);
      }

      NavMenu n = (NavMenu)this.navMenuService.get(newPid);
      if (n != null) {
        if (newsize == 0)
        {
          if ((StringUtils.isNotEmpty(nav.getUrl())) && 
            (StringUtils.isEmpty(n.getUrl()))) {
            n.setLeaf("0");
          }
        }
        else
        {
          List navList = this.navMenuService.getNavListByPidAndUrl(newPid);
          if ((navList.size() > 0) && 
            (StringUtils.isEmpty(n.getUrl()))) {
            n.setLeaf("0");
          }
        }

        this.navMenuService.update(n);
      }
    }
    else {
      NavMenu m = (NavMenu)this.navMenuService.get(oldPid);
      if (m != null) {
        List navList = this.navMenuService.getNavListByPidAndUrl(oldPid);
        if ((navList.size() == 0) && 
          (StringUtils.isEmpty(m.getUrl()))) {
          m.setLeaf("1");
        }

        this.navMenuService.update(m);
      }
    }
    return success("修改成功");
  }

  @RequestMapping("delete")
  @ResponseBody
  public Map<String, Object> delete(@RequestParam("id") Long id)
  {
    if (!DataUtil.isNull(id))
    {
      int size = this.navMenuService.getNavMenuCountByPid(id);
      if (size > 0) {
        return error("存在下级菜单，不允许删除");
      }
      NavMenu menu = (NavMenu)this.navMenuService.get(id);
      menu.setValid("0");
      this.navMenuService.update(menu);

      Long pid = menu.getPid();
      int size1 = this.navMenuService.getNavMenuCountByPid(id);
      if (size1 == 0) {
        NavMenu m = (NavMenu)this.navMenuService.get(pid);
        if (m != null) {
          m.setLeaf("1");
          this.navMenuService.update(m);
        }
      }

      return success("删除成功！");
    }return error("请选择需要删除的对象");
  }

  @RequestMapping("/deleteAll")
  @ResponseBody
  public Map<String, Object> deleteAll(@RequestParam("id") Long id, HttpSession session)
  {
    String type = "yes";
    if (!DataUtil.isNull(id)) {
      commonMethod(id, type, session);
      return success("设置成功");
    }return success("请选择需要删除的对象");
  }

  @RequestMapping("/backAll")
  @ResponseBody
  public Map<String, Object> backAll(@RequestParam("id") Long id, HttpSession session)
  {
    String type = "no";
    if (!DataUtil.isNull(id)) {
      commonMethod(id, type, session);
      return success("设置成功");
    }return success("请选择需要删除的对象");
  }

  public void commonMethod(Long id, String type, HttpSession session)
  {
    NavMenu menu = (NavMenu)this.navMenuService.get(id);
    if (type.equals("yes"))
    {
      menu.setValid("0");
    }
    if (type.equals("no"))
    {
      menu.setValid("1");
    }

    menu.setModifUserId((Long)session.getAttribute("userId"));
    menu.setModifyTime(new Date());
    this.navMenuService.update(menu);

    Long pid = menu.getPid();
    NavMenu m = (NavMenu)this.navMenuService.get(pid);
    if (m != null) {
      int size1 = this.navMenuService.getNavMenuCountByPid(pid);
      if (size1 == 0)
      {
        if (StringUtils.isEmpty(m.getUrl()))
          m.setLeaf("1");
      }
      else
      {
        List navList = this.navMenuService.getNavListByPidAndUrl(pid);
        if (navList.size() > 0) {
          if (StringUtils.isEmpty(m.getUrl())) {
            m.setLeaf("0");
          }
        }
        else if (StringUtils.isEmpty(m.getUrl())) {
          m.setLeaf("1");
        }
      }

      this.navMenuService.update(m);
    }

    List list = this.navMenuService.getNavMenuByPid(id);
    if (list.size() > 0)
      for (Iterator it = list.iterator(); it.hasNext(); ) {
        NavMenu nav = (NavMenu)it.next();
        if (type.equals("yes"))
        {
          nav.setValid("0");
        }
        if (type.equals("no"))
        {
          nav.setValid("1");
        }
        nav.setModifUserId((Long)session.getAttribute("userId"));
        nav.setModifyTime(new Date());
        this.navMenuService.update(nav);

        if (type.equals("yes"))
          deleteAll(nav.getId(), session);
        else backAll(nav.getId(), session);
      }
  }

  @RequestMapping("audit")
  public String audit(Model model, @RequestParam("id") Long id)
  {
    model.addAttribute("vo", this.navMenuService.get(id));
    return PREFIX+"/menuAudit";
  }

  @RequestMapping("/approve")
  @ResponseBody
  public Map<String, Object> approve(NavMenu menu, @RequestParam("type") String type, HttpSession session)
  {
    if (type.equals("yes"))
    {
      menu.setAuditStatus("1");
      menu.setValid("1");
    }
    else if (type.equals("no"))
    {
      menu.setAuditStatus("2");
      menu.setValid("0");
    }
    menu.setAuditUserId((Long)session.getAttribute("userId"));
    menu.setAuditTime(new Date());
    this.navMenuService.update(menu);
    return success("审核结束！");
  }

  @RequestMapping("/myNavMenu")
  @ResponseBody
  public List<Menu> myNavMenu()
  {
    List<Menu> list = new ArrayList<Menu>();

    Long menuId = new Long(83L);
    list = this.menuService.allMenuByMenuId(menuId);
    List<NavMenu> navList = this.navMenuService.allNavMenu();
    List myMenuList = new ArrayList();
    for (Menu menu : list) {
      if (menu != null) {
        myMenuList.add(menu);
      }
    }

    for (NavMenu nav : navList) {
      if (nav != null) {
        myMenuList.add(nav);
      }
    }
    return myMenuList;
  }
}