package com.hive.surveymanage.controller;

import java.util.Date;
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
import com.hive.surveymanage.entity.SurveyCategory;
import com.hive.surveymanage.service.SurveyCategoryService;
import com.hive.systemconfig.service.VersionCategoryService;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**
 * @description 问卷调查类别控制类
 * @author 燕珂
 * @createtime 2014-3-6 上午11:03:04
 */
@Controller
@RequestMapping("/surveyCategoryManage")
public class SurveyCategoryController extends BaseController {

	private static final String PREFIX = "surveymanage/surveyCategoryManage";  // 页面目录（路径前缀）

	@Resource
	private SurveyCategoryService surveyCategoryService;
	@Resource
	private VersionCategoryService versionCategoryService;
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
		return surveyCategoryService.datagrid(page);
	}

	
	/**
	 * 跳转至添加页
	 */
	@RequestMapping("toAdd")
	public String toAdd() {
		return PREFIX + "/add";
	}

	/**
	 * 添加
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public Map<String, Object> insert(SurveyCategory surveyCategory, HttpSession session) {
		surveyCategory.setCreateid((Long) session.getAttribute("userId"));
		surveyCategory.setCreatetime(new Date());
		surveyCategory.setValid(SystemCommon_Constant.VALID_STATUS_1);
		surveyCategoryService.insert(surveyCategory);
		//当问卷类别新增后，修改相应的问卷类别版本信息，手机客户端会根据版本信息是否变化选择是否重新加载
		versionCategoryService.updateVersionCategory(SystemCommon_Constant.V_VOTE);
		return success("添加成功！", surveyCategory);
	}
	
	/**
	 * 跳转至修改页
	 */
	@RequestMapping("toEdit")
	public String toEdit(Model model, @RequestParam(value = "id") Long id) {
		model.addAttribute("vo", surveyCategoryService.get(id));
		return PREFIX + "/edit";
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map update(SurveyCategory surveyCategory, HttpSession session) {
		SurveyCategory surveyCategoryTemp = surveyCategoryService.get(surveyCategory.getId());
		
		surveyCategory.setCreateid(surveyCategoryTemp.getCreateid()); // 创建人
		surveyCategory.setCreatetime(surveyCategoryTemp.getCreatetime()); // 创建时间
		surveyCategory.setModifyid((Long)session.getAttribute("userId")); // 修改人
		surveyCategory.setModifytime(new Date());  // 修改时间
		surveyCategory.setValid(surveyCategoryTemp.getValid()); // 是否有效
		
		surveyCategoryService.update(surveyCategory);
		versionCategoryService.updateVersionCategory(SystemCommon_Constant.V_VOTE);
		return success("修改成功！", surveyCategory);
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
				surveyCategoryService.delete(Long.valueOf(ids.split(",")[i]));
			}
			versionCategoryService.updateVersionCategory(SystemCommon_Constant.V_VOTE);
			return success("删除成功！");
		} else
		{
			return error("请选择要删除的类别");
		}
	}
}
