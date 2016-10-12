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
import com.hive.systemconfig.entity.QuestionCategory;
import com.hive.systemconfig.entity.QuestionDataSource;
import com.hive.systemconfig.service.QuestionCategoryService;
import com.hive.util.DataUtil;

import dk.controller.BaseController;

/**  
 * Filename: QuestionCategoryController.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  YangHui
 * @version: 1.0  
 * @Create:  2014-10-24  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-10-24 下午4:04:09				YangHui 	1.0
 */
@Controller
@RequestMapping("questionCate")
public class QuestionCategoryController extends BaseController {

	
	private static final String PREFIX = "systemconfig/questionCate";
	

	@Resource
	private QuestionCategoryService questionCategoryService;
	
	@RequestMapping("manage")
	public String manage(){
		
		return PREFIX+"/manage";
	}
	
	@RequestMapping("allquestionCate")
	@ResponseBody
	public List<QuestionDataSource> allquestionCate(){
		List list = questionCategoryService.getQuestionCateList();
		return list;
	}
	
	@RequestMapping("add")
	public String add(){
		return PREFIX+"/add";
	}
	
	
	@RequestMapping("insert")
	@ResponseBody
	public Map<String,Object> insert(QuestionCategory q){
		Long parentId = q.getParentId();
		if(parentId==null){
			q.setParentId(new Long(0));
		}
		questionCategoryService.save(q);
		return success("添加成功",q);
	}
	
	
	@RequestMapping("edit")
	public String edit(Model model,@RequestParam(value="id") Long id){
		model.addAttribute("vo", questionCategoryService.get(id));
		return PREFIX+"/edit";
	}
	
	
	
	@RequestMapping("update")
	@ResponseBody
	public Map<String,Object> update(QuestionCategory q){
		Long parentId = q.getParentId();
		if(parentId==null){
			q.setParentId(new Long(0));
		}
		questionCategoryService.update(q);
		return success("修改成功",q);
	}
	
	
	
	  @RequestMapping("delete")
	  @ResponseBody
	  public Map<String, Object> delete(@RequestParam("id") Long id)
	  {
	    if (!DataUtil.isNull(id))
	    {
	      int size = this.questionCategoryService.getSubCateCountByPid(id);
	      if (size > 0) {
	        return error("存在下级节点，不允许删除");
	      }
	      QuestionCategory q = (QuestionCategory)this.questionCategoryService.get(id);
	      q.setValid(SystemCommon_Constant.VALID_STATUS_0);
	      this.questionCategoryService.update(q);
	      return success("删除成功！");
	    }return error("请选择需要删除的对象");
	  }
	
	  
	  @RequestMapping("questionCateList")
	  @ResponseBody
	  public List<QuestionCategory> questionCateList(){
		  List list = questionCategoryService.getQuestionCateList();
		  return list;
		  
	  }


}
