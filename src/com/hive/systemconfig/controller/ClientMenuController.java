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
import com.hive.systemconfig.entity.ClientMenu;
import com.hive.systemconfig.service.ClientMenuService;
import com.hive.systemconfig.service.VersionCategoryService;

import dk.controller.BaseController;

/**  
 * Filename: ClientMenuController.java  
 * Description: 质询通客户端首页桌面图标菜单
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  YangHui
 * @version: 1.0  
 * @Create:  2014-8-29  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-8-29 下午4:11:51				YangHui 	1.0
 */
@Controller
@RequestMapping("clientMenu")
public class ClientMenuController extends BaseController {

	private static final String PREFIX = "systemconfig/clientMenu";
	
	@Resource
	private ClientMenuService clientMenuService;
	@Resource
	private VersionCategoryService versionCategoryService;
	
	
	@RequestMapping("manage")
	public String manage(){
		return PREFIX+"/manage";
	}
	
	
	
	@RequestMapping("list")
	@ResponseBody
	public List list(){
		List list = clientMenuService.getList();
		return list;
	}
	
	
	@RequestMapping("add")
	public String add(){
		return PREFIX+"/add";
	}
	
	
	@RequestMapping("insert")
	@ResponseBody
	public Map<String,Object> insert(ClientMenu cm){
		
		//校验不同版本的菜单名称不能重复
		boolean flag = clientMenuService.checkMenuNameByVersionType(cm.getMenuName(),cm.getVersionType());
		if(flag){
			if("1".equals(cm.getVersionType())){
				return error("企业版菜单【"+cm.getMenuName()+"】已存在！");
			}else{
				return error("大众版菜单【"+cm.getMenuName()+"】已存在！");
			}
		}
		
		clientMenuService.save(cm);
		//当新增成功后，修改客户端菜单的版本信息
		if("1".equals(cm.getVersionType())){
			versionCategoryService.updateVersionCategory(SystemCommon_Constant.V_CLIENT_MENU_1);
		}else{
			versionCategoryService.updateVersionCategory(SystemCommon_Constant.V_CLIENT_MENU_2);
		}
		return success("新增成功",cm);
	}
	
	
	
	@RequestMapping("edit")
	public String edit(Model model,@RequestParam(value="id") Long id){
		ClientMenu cm = clientMenuService.get(id);
		model.addAttribute("vo", cm);
		return PREFIX+"/edit";
	}
	
	@RequestMapping("update")
	@ResponseBody
	public Map<String,Object> update(ClientMenu cm){
		
		//校验不同版本的菜单名称不能重复
		/*boolean flag = clientMenuService.checkMenuNameByVersionType(cm.getMenuName(),cm.getVersionType());
		if(flag){
			if("1".equals(cm.getVersionType())){
				return error("企业版菜单【"+cm.getMenuName()+"】已存在！");
			}else{
				return error("大众版菜单【"+cm.getMenuName()+"】已存在！");
			}
		}*/
		clientMenuService.update(cm);
		if("1".equals(cm.getVersionType())){
			versionCategoryService.updateVersionCategory(SystemCommon_Constant.V_CLIENT_MENU_1);
		}else{
			versionCategoryService.updateVersionCategory(SystemCommon_Constant.V_CLIENT_MENU_2);
		}
		return success("修改成功",cm);
	}
	
	
	
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(@RequestParam(value="id") Long id){
		ClientMenu cm = clientMenuService.get(id);
		cm.setValid(SystemCommon_Constant.SIGN_YES_0);
		clientMenuService.update(cm);
		if("1".equals(cm.getVersionType())){
			versionCategoryService.updateVersionCategory(SystemCommon_Constant.V_CLIENT_MENU_1);
		}else{
			versionCategoryService.updateVersionCategory(SystemCommon_Constant.V_CLIENT_MENU_2);
		}
		return success("禁用成功",cm);
	}
	
	
	@RequestMapping("back")
	@ResponseBody
	public Map<String,Object> back(@RequestParam(value="id") Long id){
		ClientMenu cm = clientMenuService.get(id);
		cm.setValid(SystemCommon_Constant.SIGN_YES_1);
		clientMenuService.update(cm);
		if("1".equals(cm.getVersionType())){
			versionCategoryService.updateVersionCategory(SystemCommon_Constant.V_CLIENT_MENU_1);
		}else{
			versionCategoryService.updateVersionCategory(SystemCommon_Constant.V_CLIENT_MENU_2);
		}
		return success("恢复成功",cm);
	}
}
