package com.hive.push.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.hive.push.entity.PushInfo;
import com.hive.push.service.PushService;
import com.hive.util.DateUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**
 * @description 
 * @author 燕珂
 * @createtime 2015-8-13 下午07:42:13
 */@Controller
 @RequestMapping("pushinfo")
 public class PushController extends BaseController {
 	private static final String PREFIX = "pushinfo";
 	
 	@Resource
 	private PushService pushService;
 	
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
	public DataGrid datagrid(RequestPage page, @RequestParam(value = "title", required = false) String title, @RequestParam(value = "objectType", required = false) String objectType) {
		return pushService.datagrid(page, title, objectType);
	}

	/**
	 * 跳转至添加页
	 */
	@RequestMapping("add")
	public String add(Model model, @RequestParam(value = "objectType") String objectType, @RequestParam(value = "objectId") Long objectId) {
		model.addAttribute("objectType", objectType);
		model.addAttribute("objectId", objectId);
		return PREFIX + "/add";
	}

	/**
	 * 添加
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public Map<String, Object> insert(PushInfo pushInfo, HttpSession session, @RequestParam(value = "objectType") String objectType, @RequestParam(value = "objectId") Long objectId, @RequestParam(value = "pushTimeStr", required = false) String pushTimeStr){
		if (StringUtils.isNotBlank(pushTimeStr) && "1".equals(pushInfo.getIsTime())) {
			try {
				pushInfo.setPushTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(pushTimeStr));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		pushInfo.setCreateId((Long) session.getAttribute("userId"));
		pushInfo.setCreateTime(DateUtil.getCurrentTime());
		pushInfo.setValid(SystemCommon_Constant.VALID_STATUS_1); //初始化  1-可用
		pushService.save(pushInfo);
		return success("添加成功！", pushInfo);
	}
	
	/**
	 * 跳转至修改页
	 */
	@RequestMapping("edit")
	public String edit(Model model, @RequestParam(value = "id") Long id) {
		model.addAttribute("vo", pushService.get(id));
		model.addAttribute("createTimeStr", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(pushService.get(id).getCreateTime()));
		return PREFIX + "/edit";
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map update(PushInfo pushInfo, @RequestParam(value = "createTimeStr") String createTimeStr, @RequestParam(value = "pushTimeStr", required = false) String pushTimeStr) {
		try {
			pushInfo.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createTimeStr));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (StringUtils.isNotBlank(pushTimeStr) && "1".equals(pushInfo.getIsTime())) {
			try {
				pushInfo.setPushTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(pushTimeStr));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		pushService.update(pushInfo);
		return success("修改成功！", pushInfo);
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Map delete(Long id) {
		//pushService.delete(id); // 物理删除
		if (id != null) {
			pushService.delete_logic(id); // 逻辑删除
			return success("删除成功！");
		} else {
			return error("请选择要删除的记录！");
		}
	}

}
