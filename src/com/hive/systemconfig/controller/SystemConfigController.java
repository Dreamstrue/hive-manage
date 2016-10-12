/**
 * 
 */
package com.hive.systemconfig.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.common.SystemCommon_Constant;
import com.hive.systemconfig.entity.SystemConfig;
import com.hive.systemconfig.service.SystemConfigService;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**  
 * Filename: SystemConfigController.java  
 * Description:  系统参数配置
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-31  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-31 下午2:24:38				yanghui 	1.0
 */
@Controller
@RequestMapping("sysconfig")
public class SystemConfigController extends BaseController {
	
	private static final String PREFIX = "systemconfig/systemconfig";
	
	@Resource
	private SystemConfigService systemConfigService;
	
	
	@RequestMapping("manage")
	public String manage(){
		return PREFIX+"/sysconfigManage";
	}
	
	@RequestMapping("dataGrid")
	@ResponseBody
	public DataGrid dataGrid(RequestPage page){
		return systemConfigService.dataGrid(page);
	}
	
	@RequestMapping("add")
	public String add(){
		return PREFIX+"/sysconfigAdd";
	}
	
	
	@RequestMapping("insert")
	@ResponseBody
	public Map<String,Object> insert(SystemConfig config){
		//新增判断参数名称和参数代码 不能重复
		String parameCode = config.getParameCode();
		String parameName = config.getParameName();
		boolean flag = false;
		flag = systemConfigService.isExist(parameCode,"parameCode");
		if(flag){
			return error("参数代码【"+parameCode+"】已存在");
		}
		flag = systemConfigService.isExist(parameName,"parameName");
		if(flag){
			return error("参数名称【"+parameName+"】已存在");
		}
				
		config.setValid(SystemCommon_Constant.VALID_STATUS_1);
		systemConfigService.save(config);
		return success("添加成功",config);
	}
	
	
	@RequestMapping("edit")
	public String edit(Model model,@RequestParam(value="id") Long id){
		SystemConfig config = systemConfigService.get(id);
		model.addAttribute("vo", config);
		return PREFIX+"/sysconfigEdit";
	}
	
	@RequestMapping("update")
	@ResponseBody
	public Map<String,Object> update(SystemConfig config){
		//修改的时候判断参数名称不能重复
		boolean flag = false;
		flag = systemConfigService.isExistWhenUpdate(config.getParameName(),config.getId());
		if(flag){
			return error("参数名称【"+config.getParameName()+"】已存在");
		}
		systemConfigService.update(config);
		return success("修改成功",config);
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(@RequestParam(value="id") Long id){
		SystemConfig config = systemConfigService.get(id);
		config.setValid(SystemCommon_Constant.VALID_STATUS_0);
		systemConfigService.update(config);
		return success("删除成功");
		
	}

}
