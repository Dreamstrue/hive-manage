package com.hive.permissionmanage.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.common.SystemCommon_Constant;
import com.hive.contentmanage.entity.NavMenu;
import com.hive.contentmanage.service.NavMenuService;
import com.hive.permissionmanage.entity.Menu;
import com.hive.permissionmanage.entity.User;
import com.hive.permissionmanage.service.MenuActionService;
import com.hive.permissionmanage.service.MenuService;
import com.hive.permissionmanage.service.UserService;

import dk.controller.BaseController;

/**
 * 菜单管理
 * @author 燕珂
 */
@Controller
@RequestMapping("/menu")  // 接收形如 ${appPath}/menu/**.action 这样的请求
public class MenuController extends BaseController {

	private static final String PREFIX = "permissionmanage/menu";  // 页面目录（路径前缀）

	@Resource
	private MenuService menuService;
	@Resource
	private NavMenuService navMenuService;
	@Resource
	private MenuActionService menuActionService;
	@Resource
	private UserService userService;

	@RequestMapping("/myMenu")
	@ResponseBody
	public List<Menu> myMenu(HttpSession session) {
		long loginUserId = (Long) session.getAttribute("userId");
		User loginUser = userService.get(loginUserId);
		String canViewMenuIds = "";
		if ("1".equals(loginUser.getAdmin())) {
			canViewMenuIds = "all"; // 全部
		} else {
			canViewMenuIds = userService.getMenuIdsByUserId(loginUserId);
		}
		List<Menu> list = new ArrayList<Menu>();
		list = this.menuService.allMenu(canViewMenuIds); // 可访问菜单权限控制
		List<NavMenu> navList = navMenuService.allNavMenu(canViewMenuIds);
		Map<String,String> uncheckMember=menuService.getUncheckNumber();
		List myMenuList = new ArrayList();
	    for (Menu menu : list) {
		      if (menu != null) {
		        menu.getAttributes().put("url", menu.getUrl());
		        /** add by yanghui  2013-11-01  主要是为了把每个菜单节点的父阶段ID传到前端    begin */
		        menu.getAttributes().put("pid", menu.getPid());
		        /** add by yanghui  2013-11-01  主要是为了把每个菜单节点的父阶段ID传到前端    end */
		        myMenuList.add(menu);
		        // add by baibing 2013-12-16 为审核链接的标题添加未审核数量
		        for (Map.Entry<String, String> entry : uncheckMember.entrySet()) {
					if (!"0".equals(entry.getValue())
							&& entry.getKey().equals(menu.getText())) {
						menu.setText(menu.getText() + "(" + entry.getValue()
								+ ")");
					}
				}
		      }
		    }
	    	// 把栏目列表“嫁接”到菜单树中（由于这些栏目创建时选的父菜单就是内容管理，所以嫁接过来就直接能正常显示到内容管理菜单下面）
		    for (NavMenu nav : navList) {
			      if (nav != null) {
			    	  nav.getAttributes().put("url", nav.getUrl());
			    	  //** add by yanghui  2013-11-01  主要是为了把每个菜单节点的父阶段ID传到前端    begin *//*
			    	  nav.getAttributes().put("pid", nav.getPid());
			    	  //** add by yanghui  2013-11-01  主要是为了把每个菜单节点的父阶段ID传到前端    end *//*
			       
			    	  // 有些菜单只是前台网站用的，后台并不需要提供管理页面（这些菜单没有配置后台地址），那么就不把这些菜单（栏目）显示到内容管理下面了
			  //  	  if (nav.getUrl() != null || (nav.getUrl() == null && SystemCommon_Constant.LEAF_NO.equals(nav.getLeaf()))) // 文件夹的 url 也为 null，但是要显示（通过是否叶子节点区分）
			    	  if(SystemCommon_Constant.SIGN_YES_1.equals(nav.getIsShow())){ //判断是否需要显示在菜单内容管理下面，1需要显示，0不需要
			    		  myMenuList.add(nav);
			    	  }
			    	  
			      }
			    }
		    return myMenuList;
	}

	/**
	 * 跳转至列表页
	 */
	@RequestMapping("/manage") // 接收 ${appPath}/menu/manage.action 请求
	public String manage() {
		return PREFIX + "/manage"; // 返回 menu/manage.jsp
	}

	/**
	 * 获取菜单树
	 */
	@RequestMapping("/treegrid")
	@ResponseBody  // 需要往页面回写东西时候要加上这个，否则会报 404 说找不到 treegrid.jsp（就是当前方法名 + .jsp）
	public List<Menu> treegrid() {
		return menuService.allMenu("all");
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
	public Map<String, Object> insert(Menu menu) {
		// 首先判断添加的是否为重复的菜单
		String menuName = menu.getText();
		int size = menuService.getMenuByName(menuName);
		if (size > 0) {
			return error("菜单【" + menuName + "】已存在");
		}
		menuService.insert(menu);
		return success("添加成功", menu);
	}

	/**
	 * 跳转至修改页
	 */
	@RequestMapping("edit")
	public String edit(Model model, @RequestParam(value = "id") long menuId) { // 把请求中的参数 id 的值转成 long 类型赋给 变量 menuId
		model.addAttribute("vo", menuService.get(menuId));
		return PREFIX + "/edit";
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map<String, Object> update(Menu menu) {
		//首先判断添加的是否为重复的菜单
		String menuName = menu.getText();
		//判断是否重名
		boolean flag = menuService.getMenuByNameAndId(menu.getId(), menuName);
		if (!flag) {
			return error("菜单名称【" + menuName + "】已存在");
		}
		menuService.update(menu);
		return success("修改成功");

	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Map del(Long id) {
		if (id != null && id != 0) {
			menuService.delete(id);
			return success("删除成功！");
		} else {
			return error("请选择要删除的项");
		}
	}
	
	@RequestMapping("/toMenuActionBind") 
	public String toMenuActionBind(Model model, @RequestParam(value = "menuId") long menuId, HttpServletRequest request)
	{
		model.addAttribute("vo", menuService.get(menuId));
		request.setAttribute("actionIds", "199,100");
		return PREFIX + "/menuActionBind";
	}
	
	@RequestMapping("/menuActionBind") 
	@ResponseBody
	// 参数后面加个 required = false 表示此参数不是必须的，前台可以不传，如果不加的话默认是必须传值的，否则前台请求不传此参数时，会报 400 bad request。此处还可以给参数赋默认值：defaultValue =
	public Map menuActionBind(@RequestParam(value = "menuId") long menuId, @RequestParam(value = "actionIds", required = false) String[] actionIds)
	{
		if (menuActionService.insertMenuAction(menuId, actionIds))
			return success("绑定成功");
		else
			return success("绑定失败");
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/getActionIds")
	@ResponseBody
	public Map getActionIds(Long id) {
		String actionIds = menuActionService.getActionIdsByMenuId(id);
		return success(actionIds);
	}
}
