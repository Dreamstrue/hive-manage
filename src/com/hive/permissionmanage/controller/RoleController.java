package com.hive.permissionmanage.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.permissionmanage.entity.Role;
import com.hive.permissionmanage.entity.RoleMenu;
import com.hive.permissionmanage.entity.RoleMenuAction;
import com.hive.permissionmanage.model.MenuBean;
import com.hive.permissionmanage.service.MenuService;
import com.hive.permissionmanage.service.RoleMenuActionService;
import com.hive.permissionmanage.service.RoleService;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**
 * @description 角色管理
 * @author 燕珂
 * @createtime 2013-10-17 下午07:47:10
 */
@Controller
@RequestMapping("/role") 
public class RoleController extends BaseController {
	private static final String PREFIX = "permissionmanage/role";  // 页面目录（路径前缀）

	@Resource
	private RoleService roleService;

	@Resource
	private MenuService menuService;
	
	@Resource
	private RoleMenuActionService roleMenuActionService;
	
	/**
	 * 跳转至列表页
	 */
	@RequestMapping("/manage") 
	public String manage() {
		return PREFIX + "/manage"; // 返回 menu/manage.jsp
	}
	
	/**
	 * 跳转至列表页
	 */
	@RequestMapping("/toRoleMenuActionBind") 
	public String toRoleMenuActionBind(HttpServletRequest request, String roleId) {
		request.setAttribute("roleId", roleId);
		Role role = roleService.get(Long.parseLong(roleId));
		if (role != null)
			request.setAttribute("roleName", role.getCrolename());
		return PREFIX + "/roleMenuActionBind"; // 返回 menu/manage.jsp
	}

	@RequestMapping("/datagrid") 
	@ResponseBody
	public DataGrid datagrid(RequestPage page) {
		return roleService.datagrid(page);
	}

	public DataGrid combogrid(RequestPage page)
	{
		return roleService.datagrid(page);
	}
	
	/**
	 * 跳转至添加页
	 */
	@RequestMapping("add")
	public String add() {
		return PREFIX + "/add";
	}

	/**
	 * 添加
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public Map<String, Object> insert(Role role) {
		roleService.insert(role);
		return success("添加成功！", role);
	}
	
	/**
	 * 跳转至修改页
	 */
	@RequestMapping("edit")
	public String edit(Model model, @RequestParam(value = "roleId") long roleId) {
		model.addAttribute("vo", roleService.get(roleId));
		return PREFIX + "/edit";
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map update(Role role) {
		roleService.update(role);
		return success("修改成功！", role);
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Map delete(String ids)
	{
		if (StringUtils.isNotEmpty(ids))
		{
			for (int i = 0; i < ids.split(",").length; i++) {
				roleService.delete(Long.valueOf(ids.split(",")[i]));
			}
			return success("删除成功！");
		} else
		{
			return error("请选择要删除的角色");
		}
	}
	
	/**
	 * 获取菜单树
	 */
	@RequestMapping("/menutreegrid")
	@ResponseBody  // 需要往页面回写东西时候要加上这个，否则会报 404 说找不到 datagrid.jsp
	public List<MenuBean> menutreegrid(@RequestParam(value = "roleId") long roleId) {
		return menuService.allMenuForBind(roleId);
	}
	
	@RequestMapping("/roleMenuActionBind")
	@ResponseBody
	public Map roleMenuActionBind(HttpServletRequest request) {
		long roleId = Long.parseLong(request.getParameter("roleId"));
		
		String menuViews = request.getParameterValues("menuViews")[0]; // 形如：menu_331_view,menu_143_view
		String[] menuViewArray = StringUtils.split(menuViews, ","); // 元素形如：menu_331_view
		List<RoleMenu> roleMenuList = new ArrayList<RoleMenu>();
		for (int i = 0; i < menuViewArray.length; i++) {
			String menuId = menuViewArray[i].substring(menuViewArray[i].indexOf("_") + 1, menuViewArray[i].lastIndexOf("_")); // 形如：331
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setNroleid(roleId);
			roleMenu.setNmenuid(Long.parseLong(menuId));
			
			roleMenuList.add(roleMenu);
		}
		
		String menuActionValus = request.getParameterValues("menuActionValus")[0]; // 形如：menu_331=6,menu_143=4
		String[] menuActionArray = StringUtils.split(menuActionValus, ","); // 元素形如：menu_331=6
		
		List<RoleMenuAction> roleMenuActionList = new ArrayList<RoleMenuAction>();
		for (int i = 0; i < menuActionArray.length; i++) {
			String menuId = menuActionArray[i].substring(menuActionArray[i].indexOf("_") + 1, menuActionArray[i].indexOf("=")); // 形如：331
			String actionValues = menuActionArray[i].substring(menuActionArray[i].indexOf("=") + 1); // 形如：6
			RoleMenuAction roleMenuAction = new RoleMenuAction();
			roleMenuAction.setNroleid(roleId);
			roleMenuAction.setNmenuid(Long.parseLong(menuId));
			roleMenuAction.setIactionvalues(Integer.parseInt(actionValues));
			
			roleMenuActionList.add(roleMenuAction);
		}
		
		if (roleMenuActionService.insertRoleMenuAction(roleId, roleMenuList, roleMenuActionList))
			return success("绑定成功");
		else
			return success("绑定失败");
	}
}
