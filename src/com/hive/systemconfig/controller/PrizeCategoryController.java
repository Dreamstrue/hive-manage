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
import com.hive.systemconfig.entity.PrizeCategory;
import com.hive.systemconfig.service.PrizeCategoryService;
import com.hive.util.DateUtil;

import org.apache.commons.beanutils.PropertyUtils;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;
@Controller
@RequestMapping("prizeCate")
public class PrizeCategoryController extends BaseController {

	private static final String PREFIX = "systemconfig/prizecategory";
	
	@Resource
	private PrizeCategoryService prizeCategoryService;
	
	
	
	@RequestMapping("manage")
	public String manage(){
		return PREFIX+"/prizecateManage";
	}
	
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(RequestPage page,@RequestParam(value = "keys", required = false, defaultValue = "") String keys) {
		return prizeCategoryService.dataGrid(page, keys);
	}
	
	
	@RequestMapping("/treegrid")
	@ResponseBody
	public List<PrizeCategory> treegrid() {
		return prizeCategoryService.treegrid();
	}
	
	
	@RequestMapping("add")
	public String add(){
		return PREFIX+"/prizecateAdd";
	}
	
	@RequestMapping("insert")
	@ResponseBody
	public Map<String,Object> insert(PrizeCategory prize,HttpSession session){
		//判断添加的信息分类名称不可以重复
		String cateName = prize.getText();
		boolean existFlag = prizeCategoryService.isExistInfoCategoryByName(cateName);
		if(existFlag){
			return error("类别名称【"+cateName+"】已存在");
		}
		prize.setCreateId((Long) session.getAttribute("userId"));
		prize.setCreateTime(DateUtil.getCurrentTime());
		prize.setValid(SystemCommon_Constant.VALID_STATUS_1); //初始化  1-可用
		prize.setAuditStatus(SystemCommon_Constant.AUDIT_STATUS_0);//初始化 0-未审核
		
		prizeCategoryService.save(prize);
		
		return success("保存成功",prize);
	}
	
	
	@RequestMapping("edit")
	public String edit(Model model,@RequestParam(value="id") Long id){
		PrizeCategory i = prizeCategoryService.get(id);
		model.addAttribute("vo", i);
		return PREFIX+"/prizecateEdit";
	}
	
	@RequestMapping("update")
	@ResponseBody
	public Map<String,Object> update(PrizeCategory prize,HttpSession session){
		//判断修改的信息分类名称不可以重复
		String cateName = prize.getText();
		boolean existFlag = prizeCategoryService.isExistInfoCategoryByNameAndId(cateName,prize.getId());
		if(existFlag){
			return error("类别名称【"+cateName+"】已存在");
		}
		//原来的记录信息
		PrizeCategory oldinfo = prizeCategoryService.get(prize.getId());
		PrizeCategory newinfo = new PrizeCategory();
		try {
			PropertyUtils.copyProperties(newinfo, oldinfo);
			newinfo.setText(prize.getText());
			newinfo.setRemark(prize.getRemark());
			newinfo.setParentId(prize.getParentId());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		newinfo.setModifyId((Long) session.getAttribute("userId"));
		newinfo.setModifyTime(DateUtil.getCurrentTime());
		prizeCategoryService.update(newinfo);
		
		return success("修改成功",newinfo);
	}

	
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(@RequestParam(value="id") Long id){
		
		boolean flag = prizeCategoryService.isExistChildren(id);
		if(flag)return error("存在下级节点，不允许删除");
		PrizeCategory i = prizeCategoryService.get(id);
		i.setValid(SystemCommon_Constant.VALID_STATUS_0);
		prizeCategoryService.update(i);
		
		return success("删除成功",i);
	}
	
	@RequestMapping("allPrizeCate")
	@ResponseBody
	public List<PrizeCategory> allPrizeCate(){
		List<PrizeCategory> list  = prizeCategoryService.findPrizeCateValid();
		return list;
	}
	
	
	/**
	 * 
	 * @Description: 其他模块中需要用到的树形下拉列表（只是列表中少了根节点）
	 * @author yanghui 
	 * @Created 2014-3-6
	 * @return
	 */
	@RequestMapping("allPrizeTree")
	@ResponseBody
	public List<PrizeCategory> allPrizeTree(){
		List<PrizeCategory> list  = prizeCategoryService.findPrizeCateValidNotRoot();
		return list;
	}
}
