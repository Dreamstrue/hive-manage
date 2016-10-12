package com.hive.permissionmanage.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.permissionmanage.entity.Action;
import com.hive.permissionmanage.service.ActionService;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**
 * @description 动作管理
 * @author 燕珂
 * @createtime 2013-10-22 上午10:59:06
 */
@Controller
@RequestMapping("/action")
public class ActionController extends BaseController {

	private static final String PREFIX = "permissionmanage/action";  // 页面目录（路径前缀）

	@Resource
	private ActionService actionService;
	
	/**
	 * 跳转至列表页
	 */
	@RequestMapping("/manage") 
	public String manage() {
		return PREFIX + "/manage"; // 返回 menu/manage.jsp
	}
	
	@RequestMapping("/datagrid") 
	@ResponseBody
	public DataGrid datagrid(RequestPage page) {
		return actionService.datagrid(page);
	}

	public DataGrid combogrid(RequestPage page)
	{
		return actionService.datagrid(page);
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
	public Map<String, Object> insert(Action action) {
		action.setIactionvalue(actionService.getMaxActionValue());
		actionService.insert(action);
		return success("添加成功！", action);
	}
	
	/**
	 * 跳转至修改页
	 */
	@RequestMapping("edit")
	public String edit(Model model, @RequestParam(value = "actionId") long actionId) {
		model.addAttribute("vo", actionService.get(actionId));
		return PREFIX + "/edit";
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map update(Action action) {
		actionService.update(action);
		return success("修改成功！", action);
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
				actionService.delete(Long.valueOf(ids.split(",")[i]));
			}
			return success("删除成功！");
		} else
		{
			return error("请选择要删除的角色");
		}
	}

}
