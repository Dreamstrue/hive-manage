/**
 * 
 */
package com.hive.systemconfig.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.common.SystemCommon_Constant;
import com.hive.systemconfig.entity.VersionCategory;
import com.hive.systemconfig.service.VersionCategoryService;

import dk.controller.BaseController;

/**  
 * Filename: VersionCategoryController.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  YangHui
 * @version: 1.0  
 * @Create:  2014-9-1  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-9-1 上午9:43:19				YangHui 	1.0
 */
@Controller
@RequestMapping("versionCate")
public class VersionCategoryController extends BaseController {

	private static final String PREFIX = "systemconfig/versioncategory";
	@Resource
	private VersionCategoryService versionCategoryService;
	 
	
	@RequestMapping("manage")
	public String manage(){
		return PREFIX+"/manage";
	}
	
	@RequestMapping("list")
	@ResponseBody
	public List list(){
		List list = versionCategoryService.getList();
		return list;
	}
	

	@RequestMapping("add")
	public String add(){
		return PREFIX+"/add";
	}
	
	@RequestMapping("insert")
	@ResponseBody
	public Map<String,Object> insert(VersionCategory ver){
		versionCategoryService.save(ver);
		return success("添加成功", ver);
	}
	

	@RequestMapping("edit")
	public String edit(Model model,@RequestParam(value="id") Long id){
		model.addAttribute("vo", versionCategoryService.get(id));
		return PREFIX+"/edit";
	}
	
	@RequestMapping("update")
	@ResponseBody
	public Map<String,Object> update(VersionCategory ver){
		versionCategoryService.update(ver);
		return success("修改成功", ver);
	}
	
	
	
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(@RequestParam(value="id") Long id){
		VersionCategory cm = versionCategoryService.get(id);
		cm.setValid(SystemCommon_Constant.SIGN_YES_0);
		versionCategoryService.update(cm);
		return success("禁用成功",cm);
	}
	
	
	@RequestMapping("back")
	@ResponseBody
	public Map<String,Object> back(@RequestParam(value="id") Long id){
		VersionCategory cm = versionCategoryService.get(id);
		cm.setValid(SystemCommon_Constant.SIGN_YES_1);
		versionCategoryService.update(cm);
		return success("恢复成功",cm);
	}
}
