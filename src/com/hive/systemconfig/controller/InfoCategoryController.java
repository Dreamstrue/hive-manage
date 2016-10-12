package com.hive.systemconfig.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.common.SystemCommon_Constant;
import com.hive.systemconfig.entity.InfoCategory;
import com.hive.systemconfig.service.InfoCategoryService;
import com.hive.systemconfig.service.VersionCategoryService;
import com.hive.util.DateUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;
@Controller
@RequestMapping("infoCate")
public class InfoCategoryController extends BaseController {
	
	private static final String PREFIX = "systemconfig/infocategory";
	
	@Resource
	private InfoCategoryService infoCategoryService;
	
	@Resource
	private VersionCategoryService versionCategoryService;
	
	
	@RequestMapping("manage")
	public String manage(){
		return PREFIX+"/infoManage";
	}
	
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(RequestPage page,@RequestParam(value = "keys", required = false, defaultValue = "") String keys) {
		return infoCategoryService.dataGrid(page, keys);
	}
	
	
	@RequestMapping("/treegrid")
	@ResponseBody
	public List<InfoCategory> treegrid() {
		return infoCategoryService.treegrid();
	}
	
	@RequestMapping("add")
	public String add(){
		return PREFIX+"/infoAdd";
	}
	
	@RequestMapping("insert")
	@ResponseBody
	public Map<String,Object> insert(InfoCategory info,HttpSession session){
		//判断添加的信息分类名称不可以重复
		String cateName = info.getText();
		boolean existFlag = infoCategoryService.isExistInfoCategoryByName(cateName);
		if(existFlag){
			return error("类别名称【"+cateName+"】已存在");
		}
		info.setCreateId((Long) session.getAttribute("userId"));
		info.setCreateTime(DateUtil.getCurrentTime());
		info.setValid(SystemCommon_Constant.VALID_STATUS_1); //初始化  1-可用
		info.setAuditStatus(SystemCommon_Constant.AUDIT_STATUS_0);//初始化 0-未审核
		infoCategoryService.save(info);
		
		//当信息类别新增成功后，修改信息类别的版本信息
		versionCategoryService.updateVersionCategory(SystemCommon_Constant.V_INFOCATE);
		return success("保存成功",info);
	}
	
	
	@RequestMapping("edit")
	public String edit(Model model,@RequestParam(value="id") Long id){
		InfoCategory i = infoCategoryService.get(id);
		model.addAttribute("vo", i);
		return PREFIX+"/infoEdit";
	}
	
	@RequestMapping("update")
	@ResponseBody
	public Map<String,Object> update(InfoCategory info,HttpSession session){
		//判断修改的信息分类名称不可以重复
		String cateName = info.getText();
		boolean existFlag = infoCategoryService.isExistInfoCategoryByNameAndId(cateName,info.getId());
		if(existFlag){
			return error("类别名称【"+cateName+"】已存在");
		}
		//原来的记录信息
		InfoCategory oldinfo = infoCategoryService.get(info.getId());
		InfoCategory newinfo = new InfoCategory();
		try {
			PropertyUtils.copyProperties(newinfo, oldinfo);
			newinfo.setText(info.getText());
			newinfo.setRemark(info.getRemark());
			newinfo.setParentId(info.getParentId());
			newinfo.setIsShow(info.getIsShow());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		//当父级别修改为不展示时，这它的孩子节点也要全部修改为不显示
		if(info.getIsShow().equals(SystemCommon_Constant.IS_SHOW_0)){
			List list = infoCategoryService.getChildByParentId(info.getId());
			for(int i=0;i<list.size();i++){
				InfoCategory icy = (InfoCategory) list.get(i);
				icy.setIsShow(SystemCommon_Constant.IS_SHOW_0);
				infoCategoryService.update(icy);
			}
		}
		
		//当节点为孩子节点时，如果要修改为展示，此时父节点也要被修改为可展示
		if(SystemCommon_Constant.IS_SHOW_1.equals(info.getIsShow())){
			Long parentId = oldinfo.getParentId();
			InfoCategory i = infoCategoryService.get(parentId);
			i.setIsShow(SystemCommon_Constant.IS_SHOW_1);
			infoCategoryService.update(i);
		}
		
		
		
		//当客户端显示    父级别修改为不展示时，这它的孩子节点也要全部修改为不显示
		if(info.getClientShow().equals(SystemCommon_Constant.IS_SHOW_0)){
			List list = infoCategoryService.getChildByParentId(info.getId());
			for(int i=0;i<list.size();i++){
				InfoCategory icy = (InfoCategory) list.get(i);
				icy.setClientShow(SystemCommon_Constant.IS_SHOW_0);
				infoCategoryService.update(icy);
			}
		}
		
		//当节点为孩子节点时，如果要修改为展示，此时父节点也要被修改为可展示
		if(SystemCommon_Constant.IS_SHOW_1.equals(info.getClientShow())){
			Long parentId = oldinfo.getParentId();
			InfoCategory i = infoCategoryService.get(parentId);
			i.setClientShow(SystemCommon_Constant.IS_SHOW_1);
			infoCategoryService.update(i);
		}
		
		newinfo.setModifyId((Long) session.getAttribute("userId"));
		newinfo.setModifyTime(DateUtil.getCurrentTime());
		infoCategoryService.update(newinfo);
		
		versionCategoryService.updateVersionCategory(SystemCommon_Constant.V_INFOCATE);
		return success("修改成功",newinfo);
	}

	
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(@RequestParam(value="id") Long id){
		
		InfoCategory i = infoCategoryService.get(id);
		//判断删除节点是否存在孩子节点，如果存在，则不允许删除父节点
		boolean flag = infoCategoryService.isExistChildren(id);
		if(flag){
			return error("存在下级节点，不允许删除");
		}
		i.setValid(SystemCommon_Constant.VALID_STATUS_0);
		infoCategoryService.update(i);
		versionCategoryService.updateVersionCategory(SystemCommon_Constant.V_INFOCATE);
		return success("删除成功",i);
	}

	/**
	 * 
	 * @Description: 信息分类添加修改时的树形下拉列表
	 * @author yanghui 
	 * @Created 2014-2-25
	 * @return
	 */
	@RequestMapping("allInfoCate")
	@ResponseBody
	public List<InfoCategory> allInfoCate(){
		List<InfoCategory> list  = infoCategoryService.findInfoCateValid();
		return list;
	}
	
	/**
	 * 
	 * @Description: 其他模块中需要用到的树形下拉列表（只是列表中少了根节点）
	 * @author yanghui 
	 * @Created 2014-3-6
	 * @return
	 */
	@RequestMapping("allInfoTree")
	@ResponseBody
	public List<InfoCategory> allInfoTree(){
		List<InfoCategory> list  = infoCategoryService.findInfoCateValidNotRoot();
		return list;
	}
	
}
