package com.hive.permissionmanage.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.common.SystemCommon_Constant;
import com.hive.permissionmanage.entity.User;
import com.hive.permissionmanage.service.UserRoleService;
import com.hive.permissionmanage.service.UserService;
import com.hive.util.MD5EncrpytUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**
 * @description 用户管理
 * @author 燕珂
 * @createtime 2013-10-29 下午01:55:41
 */
@Controller
@RequestMapping("/user") 
public class UserController extends BaseController {
	private Logger logger = Logger.getLogger(UserController.class);
	
	private static final String PREFIX = "permissionmanage/user";  // 页面目录（路径前缀）

	@Resource
	private UserService userService;
	
	@Resource
	private UserRoleService userRoleService;
	
	/**
	 * 跳转至列表页
	 */
	@RequestMapping("/manage") 
	public String manage() {
		return PREFIX + "/manage"; // 返回 menu/manage.jsp
	}
	
	@RequestMapping("/datagrid") 
	@ResponseBody 
	public DataGrid datagrid(RequestPage page, String keys)
	{
		return userService.datagrid(page, keys);
	}

	@RequestMapping("/add") 
	public String add()
	{
		return PREFIX + "/add";
	}

	@RequestMapping("/insert") 
	@ResponseBody
	public Map insert(User user)
	{
		String mdPass = MD5EncrpytUtil.md5Encrypt(user.getPassword(), "md5"); // md5 加密
		user.setPassword(mdPass);
		user.setValid(SystemCommon_Constant.VALID_STATUS_1); // 是否有效
		userService.insert(user);
		return success("添加成功！", user);
	}

	@RequestMapping("/delete") 
	@ResponseBody
	public Map delete(String ids)
	{
		if (StringUtils.isNotEmpty(ids)) {
			userService.logicDelete(ids); // 逻辑删除
			return success("删除成功！");
		} else {
			return error("请选择要删除的用户");
		}
	}

	@RequestMapping("/editPassword") 
	public String editPassword(Model model, @RequestParam(value = "id") long id)
	{
		model.addAttribute("vo", userService.get(id));
		return PREFIX + "/editPassword";
	}

	@RequestMapping("/updatePassword") 
	@ResponseBody
	public Map updatePassword(@RequestParam(value = "id") long id, String password)
	{
		userService.updatePassword(id, password);
		return success("密码修改成功！");
	}

	@RequestMapping("/edit") 
	public String edit(Model model, @RequestParam(value = "userId") long userId) {
		model.addAttribute("vo", userService.get(userId));
		return PREFIX + "/edit";
	}

	@RequestMapping("/update") 
	@ResponseBody
	public Map update(User user)
	{
		User userTemp = userService.get(user.getId());
		
		user.setValid(userTemp.getValid()); // 是否有效
		userService.update(user);
		return success("用户信息修改成功！", user);
	}
	
	@RequestMapping("/toUserRoleBind") 
	public String toUserRoleBind(Model model, @RequestParam(value = "userId") long userId, HttpServletRequest request)
	{
		model.addAttribute("vo", userService.get(userId));
		return PREFIX + "/userRoleBind";
	}
	
	@RequestMapping("/userRoleBind") 
	@ResponseBody
	// 参数后面加个 required = false 表示此参数不是必须的，前台可以不传，如果不加的话默认是必须传值的，否则前台请求不传此参数时，会报 400 bad request。此处还可以给参数赋默认值：defaultValue =
	public Map userRoleBind(@RequestParam(value = "userId") long userId, @RequestParam(value = "roleIds", required = false) String[] roleIds)
	{
		if (userRoleService.insertUserRole(userId, roleIds))
			return success("绑定成功");
		else
			return error("绑定失败");
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/getRoleIds")
	@ResponseBody
	public Map getRoleIds(Long id) {
		String roleIds = userRoleService.getRoleIdsByUserId(id);
		return success(roleIds);
	}

}
