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
import com.hive.systemconfig.service.QuestionDataSourceService;
import com.hive.util.DataUtil;

import dk.controller.BaseController;

/**  
 * Filename: QuestionDataSourceController.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  YangHui
 * @version: 1.0  
 * @Create:  2014-10-23  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-10-23 上午11:56:04				YangHui 	1.0
 */
@Controller
@RequestMapping("questionData")
public class QuestionDataSourceController extends BaseController {

	
	private static final String PREFIX = "systemconfig/questionData";
	
	@Resource
	private QuestionDataSourceService questionDataSourceService;
	@Resource
	private QuestionCategoryService questionCategoryService;
	
	@RequestMapping("manage")
	public String manage(){
		
		return PREFIX+"/manage";
	}
	
	@RequestMapping("allquestionData")
	@ResponseBody
	public List<QuestionDataSource> allquestionData(){
		List list = questionDataSourceService.allquestionData();
		return list;
	}
	
	@RequestMapping("add")
	public String add(){
		return PREFIX+"/add";
	}
	
	
	@RequestMapping("insert")
	@ResponseBody
	public Map<String,Object> insert(QuestionDataSource q){
		Long parentId = q.getParentId();
		if(parentId==null){
			q.setParentId(new Long(0));
		}
		questionDataSourceService.save(q);
		return success("添加成功",q);
	}
	
	
	@RequestMapping("edit")
	public String edit(Model model,@RequestParam(value="id") Long id){
		model.addAttribute("vo", questionDataSourceService.get(id));
		return PREFIX+"/edit";
	}
	
	
	
	@RequestMapping("update")
	@ResponseBody
	public Map<String,Object> update(QuestionDataSource q){
		Long parentId = q.getParentId();
		if(parentId==null){
			q.setParentId(new Long(0));
		}
		questionDataSourceService.update(q);
		return success("修改成功",q);
	}
	
	
	
	  @RequestMapping("delete")
	  @ResponseBody
	  public Map<String, Object> delete(@RequestParam("id") Long id)
	  {
	    if (!DataUtil.isNull(id))
	    {
	      int size = this.questionDataSourceService.getSubDataCountByPid(id);
	      if (size > 0) {
	        return error("存在下级节点，不允许删除");
	      }
	      QuestionDataSource q = (QuestionDataSource)this.questionDataSourceService.get(id);
	      q.setValid(SystemCommon_Constant.SIGN_YES_0);
	      this.questionDataSourceService.update(q);
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
