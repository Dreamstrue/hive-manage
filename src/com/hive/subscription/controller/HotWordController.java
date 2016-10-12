package com.hive.subscription.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.common.SystemCommon_Constant;
import com.hive.subscription.entity.HotWord;
import com.hive.subscription.service.HotWordService;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**
 * @description 热门推荐（关键词）控制类
 * @author 燕珂
 * @createtime 2015-8-4 下午10:42:40
 */
@Controller
@RequestMapping("hotWord")
public class HotWordController extends BaseController {
private static final String PREFIX = "hotword";
	
	@Resource
	private HotWordService hotWordService;
	
	/**
	 * 跳转至列表页
	 */
	@RequestMapping("/manage") 
	public String manage() {
		return PREFIX + "/manage"; // 返回 menu/manage.jsp
	}
	
	/**
	 * 列表数据
	 */
	@RequestMapping("/datagrid") 
	@ResponseBody
	public DataGrid datagrid(RequestPage page) {
		return hotWordService.datagrid(page);
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
	public Map<String, Object> insert(HotWord hotWord) {
		hotWord.setValid(SystemCommon_Constant.VALID_STATUS_1); //初始化  1-可用
		hotWordService.save(hotWord);
		return success("添加成功！", hotWord);
	}
	
	/**
	 * 跳转至修改页
	 */
	@RequestMapping("edit")
	public String edit(Model model, @RequestParam(value = "id") Long id) {
		model.addAttribute("vo", hotWordService.get(id));
		return PREFIX + "/edit";
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map update(HotWord hotWord) {
		hotWordService.update(hotWord);
		return success("修改成功！", hotWord);
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Map delete(String ids) {
		if (StringUtils.isNotEmpty(ids)) {
			for (int i = 0; i < ids.split(",").length; i++) {
				hotWordService.delete(Long.valueOf(ids.split(",")[i]));
			}
			return success("删除成功！");
		} else {
			return error("请选择要删除的数据！");
		}
	}
}
