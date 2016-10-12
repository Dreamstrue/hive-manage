/**
 * 
 */
package com.hive.systemconfig.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.common.SystemCommon_Constant;
import com.hive.systemconfig.entity.IntegralCategory;
import com.hive.systemconfig.service.IntegralCategoryService;
import com.hive.util.DateUtil;

import org.apache.commons.beanutils.PropertyUtils;

import dk.controller.BaseController;

/**  
 * Filename: IntegralCategoryController.java  
 * Description: 积分分类管理
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-6  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-6 下午1:55:29				yanghui 	1.0
 */
@Controller
@RequestMapping("inteCate")
public class IntegralCategoryController extends BaseController {

	private static final String PREFIX = "systemconfig/integralcategory";
	
	@Resource
	private IntegralCategoryService integralCategoryService;
	
	
	@RequestMapping("manage")
	public String manage(){
		return PREFIX+"/integralCateManage";
	}
	
	
	
	@RequestMapping("/treegrid")
	@ResponseBody
	public List<IntegralCategory> treegrid() {
		return integralCategoryService.treegrid();
	}
	
	@RequestMapping("add")
	public String add(){
		return PREFIX+"/integralCateAdd";
	}
	
	@RequestMapping("insert")
	@ResponseBody
	public Map<String,Object> insert(IntegralCategory integral,HttpSession session){
		//判断添加的积分分类名称不可以重复
		String cateName = integral.getText();
		boolean existFlag = integralCategoryService.isExistIntegralCategoryByName(cateName);
		if(existFlag){
			return error("类别名称【"+cateName+"】已存在");
		}
		integral.setCreateTime(DateUtil.getCurrentTime());
		integral.setValid(SystemCommon_Constant.VALID_STATUS_1); //初始化  1-可用
		
		integralCategoryService.save(integral);
		
		return success("保存成功",integral);
	}
	
	
	@RequestMapping("edit")
	public String edit(Model model,@RequestParam(value="id") Long id){
		IntegralCategory i = integralCategoryService.get(id);
		model.addAttribute("vo", i);
		return PREFIX+"/integralCateEdit";
	}
	
	@RequestMapping("update")
	@ResponseBody
	public Map<String,Object> update(IntegralCategory integral,HttpSession session){
		//判断修改的积分分类名称不可以重复
		String cateName = integral.getText();
		boolean existFlag = integralCategoryService.isExistIntegralCategoryByNameAndId(cateName,integral.getId());
		if(existFlag){
			return error("类别名称【"+cateName+"】已存在");
		}
		//原来的记录信息
		IntegralCategory oldinfo = integralCategoryService.get(integral.getId());
		IntegralCategory newinfo = new IntegralCategory();
		try {
			PropertyUtils.copyProperties(newinfo, oldinfo);
			newinfo.setText(integral.getText());
			newinfo.setRemark(integral.getRemark());
			newinfo.setParentId(integral.getParentId());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		integralCategoryService.update(newinfo);
		
		return success("修改成功",newinfo);
	}

	
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(@RequestParam(value="id") Long id){
		
		IntegralCategory i = integralCategoryService.get(id);
		//判断删除节点是否存在孩子节点，如果存在，则不允许删除父节点
		boolean flag = integralCategoryService.isExistChildren(id);
		if(flag){
			return error("存在下级节点，不允许删除");
		}
		i.setValid(SystemCommon_Constant.VALID_STATUS_0);
		integralCategoryService.update(i);
		
		return success("删除成功",i);
	}

	/**
	 * 
	 * @Description: 积分分类下拉列表
	 * @author yanghui 
	 * @Created 2014-2-25
	 * @return
	 */
	@RequestMapping("allInteCate")
	@ResponseBody
	public List<IntegralCategory> allInteCate(){
		List<IntegralCategory> list  = integralCategoryService.findIntegralCategoryValid();
		return list;
	}
	
	
}
