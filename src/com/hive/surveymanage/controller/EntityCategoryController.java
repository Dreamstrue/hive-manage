package com.hive.surveymanage.controller;

import java.util.Date;
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
import com.hive.surveymanage.entity.EntityCategory;
import com.hive.surveymanage.entity.IndustryEntity;
import com.hive.surveymanage.service.EntityCategoryService;
import com.hive.systemconfig.service.VersionCategoryService;
import com.hive.util.DataUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**
 * @description 实体类别管理控制类
 * @author 赵鹏飞
 * @createtime 2016-2-18 下午03:55:13
 */
@Controller
@RequestMapping("/entityCategoryManage")
public class EntityCategoryController extends BaseController {

	private static final String PREFIX = "surveymanage/entityCategoryManage";  // 页面目录（路径前缀）

	@Resource
	private EntityCategoryService entityCategoryService;
	@Resource
	private VersionCategoryService versionCategoryService;
	
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
	public List<EntityCategory> treegrid() {
		return entityCategoryService.allentityCategory();
	}
	
	@RequestMapping("/datagrid") 
	@ResponseBody
	public DataGrid datagrid(RequestPage page) {
		return entityCategoryService.datagrid(page);
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
	public Map<String, Object> insert(EntityCategory entityCategory, HttpSession session) {
		if (entityCategory.getPid() == null)
			entityCategory.setPid(0L); // 根节点的父 id 设为0
		
		entityCategory.setCreateid((Long) session.getAttribute("userId")); // 创建人
		entityCategory.setCreatetime(new Date()); // 创建时间
		entityCategory.setValid(SystemCommon_Constant.VALID_STATUS_1); // 是否有效
		
		entityCategoryService.insert(entityCategory);
		
		versionCategoryService.updateVersionCategory(SystemCommon_Constant.V_VOTE_INDUSTRY);
		return success("添加成功！", entityCategory);
	}
	
	/**
	 * 跳转至修改页
	 */
	@RequestMapping("edit")
	public String edit(Model model, @RequestParam(value = "entityCategoryId") long entityCategoryId) {
		model.addAttribute("vo", entityCategoryService.get(entityCategoryId));
		return PREFIX + "/edit";
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map update(EntityCategory entityCategory, HttpSession session) {
		
		EntityCategory entityCategoryTemp = entityCategoryService.get(entityCategory.getId());
		
		entityCategory.setCreateid(entityCategoryTemp.getCreateid()); // 创建人
		entityCategory.setCreatetime(entityCategoryTemp.getCreatetime()); // 创建时间
		entityCategory.setModifyid((Long)session.getAttribute("userId")); // 修改人
		entityCategory.setModifytime(new Date());  // 修改时间
		entityCategory.setValid(entityCategoryTemp.getValid()); // 是否有效
		
		entityCategoryService.update(entityCategory);
		versionCategoryService.updateVersionCategory(SystemCommon_Constant.V_VOTE_INDUSTRY);
		return success("修改成功！", entityCategory);
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Map delete(@RequestParam(value="id") Long id)
	{
		if (!DataUtil.isNull(id)) {
			boolean flag = entityCategoryService.checkChild(id);
			if(flag){
				entityCategoryService.delete_logic(id); // 逻辑删除
				versionCategoryService.updateVersionCategory(SystemCommon_Constant.V_VOTE_INDUSTRY);
				return success("删除成功！");
			}else{
				return error("存在下级行业，不允许删除！");
			}
		} else {
			return error("请选择要删除的行业");
		}
	}
	
	
	@RequestMapping("parentIndustryList")
	@ResponseBody
	public List<EntityCategory> getParentIndustryList(){
		List list = entityCategoryService.getParentIndustryList();
		return list;
		
	}
	/**
	 * @Title: allCategoryEntityInfo   
	 * @Description: 获取所有实体类别   
	 * @param @return    设定文件  
	 * @throws  
	 */
	@RequestMapping("/allCategoryEntityInfo.action")
	@ResponseBody
	public List<EntityCategory> allCategoryEntityInfo(){
		return entityCategoryService.getCategoryEntityInfo();
	}
}
