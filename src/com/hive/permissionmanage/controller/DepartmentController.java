package com.hive.permissionmanage.controller;

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

import com.hive.common.SystemCommon_Constant;
import com.hive.permissionmanage.entity.Department;
import com.hive.permissionmanage.service.DepartmentService;
import com.hive.util.DateUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**
 * @description 部门管理
 * @author 燕珂
 * @createtime 2013-10-17 下午07:47:10
 */
@Controller
@RequestMapping("/department") 
public class DepartmentController extends BaseController {
	private static final String PREFIX = "permissionmanage/department";  // 页面目录（路径前缀）

	@Resource
	private DepartmentService departmentService;
	
	/**
	 * 跳转至列表页
	 */
	@RequestMapping("/manage") 
	public String manage() {
		return PREFIX + "/manage"; // 返回 menu/manage.jsp
	}

	/**
	 * 获取菜单树
	 */
	@RequestMapping("/treegrid")
	@ResponseBody  // 需要往页面回写东西时候要加上这个
	public List<Department> treegrid() {
		return departmentService.allDeparment();
	}
	
	@RequestMapping("/datagrid") 
	@ResponseBody
	public DataGrid datagrid(RequestPage page) {
		return departmentService.datagrid(page);
	}

	public DataGrid combogrid(RequestPage page)
	{
		return departmentService.datagrid(page);
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
	public Map<String, Object> insert(Department department, HttpSession session) {
		
		department.setNcreateid((Long) session.getAttribute("userId")); // 创建人
		department.setDcreatetime(DateUtil.getTimestamp()); // 创建时间
		department.setCvalid(SystemCommon_Constant.VALID_STATUS_1); // 是否有效
		
		departmentService.insert(department);
		return success("添加成功！", department);
	}
	
	/**
	 * 跳转至修改页
	 */
	@RequestMapping("edit")
	public String edit(Model model, @RequestParam(value = "departmentId") long departmentId) {
		model.addAttribute("vo", departmentService.get(departmentId));
		return PREFIX + "/edit";
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map update(Department department, HttpSession session) {
		
		Department departmentTemp = departmentService.get(department.getId());
		
		department.setNcreateid(departmentTemp.getNcreateid()); // 创建人
		department.setDcreatetime(departmentTemp.getDcreatetime()); // 创建时间
		department.setNmodifyid((Long)session.getAttribute("userId")); // 修改人
		department.setDmodifytime(DateUtil.getTimestamp());  // 修改时间
		department.setCvalid(departmentTemp.getCvalid()); // 是否有效
		
		departmentService.update(department);
		return success("修改成功！", department);
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Map delete(String ids)
	{
		if (StringUtils.isNotEmpty(ids)) {
			departmentService.logicDelete(ids); // 逻辑删除
			return success("删除成功！");
		} else {
			return error("请选择要删除的部门");
		}
	}
}
