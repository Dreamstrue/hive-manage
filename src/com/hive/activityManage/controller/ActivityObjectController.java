package com.hive.activityManage.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.activityManage.entity.ActionObject;
import com.hive.activityManage.entity.Activity;
import com.hive.activityManage.entity.ActivityObjectLink;
import com.hive.activityManage.entity.ActivityPrizeLink;
import com.hive.activityManage.entity.AwardActivity;
import com.hive.activityManage.model.ActivityPrizeLinkVo;
import com.hive.activityManage.model.ActivityVo;
import com.hive.activityManage.service.ActionObjectService;
import com.hive.activityManage.service.ActivityObjectLinkService;
import com.hive.activityManage.service.ActivityPrizeLinkService;
import com.hive.activityManage.service.ActivityService;
import com.hive.activityManage.service.AwardActivityService;
import com.hive.common.SystemCommon_Constant;
import com.hive.prizemanage.service.PrizeInfoService;
import com.hive.util.DateUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**
 * @description 活动作用对象功能类
 * @author yyf
 * @createtime 2016-1-14
 */
@Controller
@RequestMapping("/activityObject")
public class ActivityObjectController extends BaseController {
	
	private final String PREFIX="activityObjectManage";
	
	@Resource
	private ActivityService activityService;
	@Resource
	private AwardActivityService awardActivityService;
	@Resource
	private ActivityPrizeLinkService activityPrizeLinkService;
	@Resource
	private ActionObjectService actionObjectService;
	@Resource
	private ActivityObjectLinkService activityObjectLinkService;
	/**
	 * 功能描述：跳转到作用对象管理页面
	 * 创建时间:2016-6-6
	 * 创建人: yyf
	 * 
	 * @return
	 */
	@RequestMapping("/toActivityObjectManage")
	public String toEvaluation(Model model, HttpServletRequest request){
		return PREFIX+"/manage";
	}
	/**
	 * 功能描述：跳转新增作用对象
	 * 创建时间:2016-6-15
	 * 创建人: yyf
	 * 
	 * @return
	 */
	@RequestMapping("/toActionObjectAdd")
	public String toEvaluationDetail(Model model,@RequestParam(value="activityId",required=true) Long activityId){
		Activity act = activityService.get(activityId);
		model.addAttribute("activityVo", act);
		return PREFIX+"/actionObjectAdd";
	}
	/**
	 * 功能描述：保存作用对象
	 */
	@RequestMapping("/actionObjectAdd")
	@ResponseBody
	public Map<String, Object> actionObjectAdd(ActionObject vo,HttpServletRequest request,@RequestParam(value="activityId",required=true) Long activityId) {
		//校验当前vo是否已经存在
		ActionObject ao = actionObjectService.findByLinkIdAndactionObjectType(vo.getActionObjectType(),vo.getLinkId());
		Long actionObjectId = null;
		if(ao==null){
			actionObjectService.save(vo);
			actionObjectId = vo.getId();
		}else{
			actionObjectId = ao.getId();
		}
		//校验当前作用对象是否有正在进行的活动
		ActivityObjectLink aol = activityObjectLinkService.findByAnctionObjectId(actionObjectId);
		if(aol.getId()!=null){
			return error("正在活动中，无法参与多个活动");
		}else{
			//保存作用对象和活动的中间表数据
			aol.setActivityId(activityId);
			aol.setActionObjectId(actionObjectId);
			aol.setIsValid("1");
			activityObjectLinkService.save(aol);
		}
		return success("保存成功");
	}
	/**
	 * 功能描述：终止此作用对象的活动
	 */
	@RequestMapping("/endActionObject")
	@ResponseBody
	public Map<String, Object> endActionObject(@RequestParam(value="actionObjectId",required=true) Long actionObjectId,HttpServletRequest request) {
		
		ActivityObjectLink activityObjectLink=activityObjectLinkService.findByAnctionObjectId(actionObjectId);
		
		if(activityObjectLink!=null){
			activityObjectLink.setIsValid("0");
			activityObjectLinkService.update(activityObjectLink);
			return success("已终止！");
		}else{
			return success("终止失败！");
		}
	}
	/**
	 * 功能描述：暂停此作用对象的活动
	 */
	@RequestMapping("/stopActionObject")
	@ResponseBody
	public Map<String, Object> stopActionObject(@RequestParam(value="actionObjectId",required=true) Long actionObjectId,HttpServletRequest request) {
		
		ActivityObjectLink activityObjectLink=activityObjectLinkService.findByAnctionObjectId(actionObjectId);
		
		if(activityObjectLink!=null){
			activityObjectLink.setIsValid("2");
			activityObjectLinkService.update(activityObjectLink);
			return success("已暂停！");
		}else{
			return success("暂停失败！");
		}
	}
	/**
	 * 功能描述：重新启动此作用对象的活动
	 */
	@RequestMapping("/startActionObject")
	@ResponseBody
	public Map<String, Object> startActionObject(@RequestParam(value="actionObjectId",required=true) Long actionObjectId,HttpServletRequest request) {
		
		ActivityObjectLink activityObjectLink=activityObjectLinkService.findByAnctionObjectId(actionObjectId);
		
		if(activityObjectLink!=null){
			activityObjectLink.setIsValid("1");
			activityObjectLinkService.update(activityObjectLink);
			return success("已重新启动！");
		}else{
			return success("启动失败！");
		}
	}
	/**
	 * 功能描述：获取活动分配的所有作用对象
	 * 创建时间:2016-06-03
	 * 创建人: yyf
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/datagridForActionObject.action")
	@ResponseBody
	public DataGrid datagridForActionObject(RequestPage page,HttpServletRequest request,@RequestParam(value="activityId",required=true) Long activityId){
		DataGrid resultdata = actionObjectService.dataGrid(page,activityId);
		return resultdata;
	}
}
