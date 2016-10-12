package com.hive.activityManage.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.common.util.Hash;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hive.activityManage.entity.Activity;
import com.hive.activityManage.entity.ActivityObjectLink;
import com.hive.activityManage.entity.ActivityPrizeLink;
import com.hive.activityManage.entity.AwardActivity;
import com.hive.activityManage.entity.RewardActivity;
import com.hive.activityManage.entity.RewardPrizeLink;
import com.hive.activityManage.model.ActivityPrizeLinkVo;
import com.hive.activityManage.model.ActivityVo;
import com.hive.activityManage.model.RewardPrizeLinkVo;
import com.hive.activityManage.service.ActivityObjectLinkService;
import com.hive.activityManage.service.ActivityPrizeLinkService;
import com.hive.activityManage.service.ActivityService;
import com.hive.activityManage.service.AwardActivityService;
import com.hive.activityManage.service.RewardActivityService;
import com.hive.activityManage.service.RewardPrizeLinkService;
import com.hive.common.AnnexFileUpLoad;
import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Annex;
import com.hive.enterprisemanage.entity.EAwardenterprise;
import com.hive.evaluationManage.entity.SurveyEvaluation;
import com.hive.evaluationManage.entity.SurveyPartakeUser;
import com.hive.evaluationManage.model.EvaluationVo;
import com.hive.evaluationManage.service.EvaluationService;
import com.hive.evaluationManage.service.SurveyPartakeUserService;
import com.hive.integralmanage.entity.WinPrizeInfo;
import com.hive.integralmanage.service.WinPrizeInfoService;
import com.hive.membermanage.entity.MMember;
import com.hive.membermanage.service.MembermanageService;
import com.hive.prizemanage.entity.PrizeInfo;
import com.hive.prizemanage.service.PrizeInfoService;
import com.hive.surveymanage.entity.IndustryEntity;
import com.hive.surveymanage.service.EntityCategoryService;
import com.hive.surveymanage.service.IndustryEntityService;
import com.hive.tagManage.entity.SNBase;
import com.hive.tagManage.service.TagSNBaseService;
import com.hive.util.DateUtil;
import com.hive.util.PropertiesFileUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.util.MD5;

/**
 * @description 活动
 * @author yyf
 * @createtime 2016-1-13
 */
@Controller
@RequestMapping("/activityManage")
public class ActivityController extends BaseController {
	
	private final String PREFIX="activityManage";
	
	@Resource
	private ActivityService activityService;
	@Resource
	private AwardActivityService awardActivityService;
	@Resource
	private RewardActivityService rewardActivityService;
	@Resource
	private ActivityPrizeLinkService activityPrizeLinkService;
	@Resource
	private RewardPrizeLinkService rewardPrizeLinkService;
	@Resource
	private PrizeInfoService prizeInfoService;
	@Resource
	private SurveyPartakeUserService surveyPartakeUserService;
	@Resource
	private EntityCategoryService entityCategoryService;
	@Resource
	private WinPrizeInfoService winPrizeInfoService;
	@Resource
	private TagSNBaseService tagSNBaseService;
	@Resource
	private ActivityObjectLinkService activityObjectLinkService;
	@Resource
	private MembermanageService membermanageService;
	@Resource
	private EvaluationService evaluationService;
	/**
	 * 功能描述：跳转到活动管理页面
	 * 创建时间:2016-6-6
	 * 创建人: yyf
	 * 
	 * @return
	 */
	@RequestMapping("/toActivityManage")
	public String toEvaluation(Model model, HttpServletRequest request){
		return PREFIX+"/manage";
	}
	/**
	 * 功能描述：获取活动列表
	 * 创建时间:2016-06-03
	 * 创建人: yyf
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/datagrid.action")
	@ResponseBody
	public DataGrid datagrid(RequestPage page,HttpServletRequest request,ActivityVo vo){
		DataGrid resultdata = activityService.dataGrid(page,vo);
		return resultdata;
	}
	/**
	 * 功能描述：获取已经启动活动列表数据用于分配给作用对象
	 * 创建时间:2016-06-15
	 * 创建人: yyf
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/dataGridStartedActivity.action")
	@ResponseBody
	public DataGrid dataGridStartedActivity(RequestPage page,HttpServletRequest request,ActivityVo vo){
		DataGrid resultdata = activityService.dataGridStartedActivity(page,vo);
		return resultdata;
	}
	/**
	 * 功能描述：获取指定活动的奖品列表
	 * 创建时间:2016-06-13
	 * 创建人: yyf
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/datagridForAward.action")
	@ResponseBody
	public DataGrid datagridForAward(RequestPage page,HttpServletRequest request,@RequestParam(value="awardActivityId",required=true) Long awardActivityId){
		DataGrid resultdata = activityPrizeLinkService.dataGrid(page,awardActivityId);
		return resultdata;
	}
	/**
	 * 功能描述：获取指定活动的奖品列表
	 * 创建时间:2016-06-13
	 * 创建人: yyf
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/datagridForReward.action")
	@ResponseBody
	public DataGrid datagridForReward(RequestPage page,HttpServletRequest request,@RequestParam(value="activityId",required=true) Long activityId){
		DataGrid resultdata = rewardPrizeLinkService.dataGrid(page,activityId);
		return resultdata;
	}
	/**
	 * 功能描述：跳转新增抽奖活动页面
	 * 创建时间:2016-6-6
	 * 创建人: yyf
	 * 
	 * @return
	 */
	@RequestMapping("/toAwardActivityAdd")
	public String toAwardActivityAdd(Model model){
		String orderNum = DateUtil.format(new Date(), "yyyyMMdd");
		Activity act = activityService.findNewestActivity();
		if(act!=null){
			String orderNum_ = act.getOrderNum();
			if(orderNum.equals(orderNum_.substring(0, orderNum_.length()-2))){
				String num = orderNum_.substring(orderNum_.length()-2,orderNum_.length());
				if(num.equals("99")){
					throw new RuntimeException("今天活动序号已经超出上限！");
				}else{
					int n = Integer.parseInt(num)+1;
					if(n<10){
						orderNum = orderNum +"0"+n;
					}else{
						orderNum = orderNum + n;
					}
				}
			}else{
				orderNum = orderNum +"01";
			}
		}else{
			orderNum = orderNum +"01";
		}
		model.addAttribute("orderNum", orderNum);
		return PREFIX+"/awardActivityAdd";
	}
	/**
	 * 功能描述：跳转新增奖励活动页面
	 * 创建时间:2016-6-6
	 * 创建人: yyf
	 * 
	 * @return
	 */
	@RequestMapping("/toRewardActivityAdd")
	public String toRewardActivityAdd(Model model){
		String orderNum = DateUtil.format(new Date(), "yyyyMMdd");
		Activity act = activityService.findNewestActivity();
		if(act!=null){
			String orderNum_ = act.getOrderNum();
			if(orderNum.equals(orderNum_.substring(0, orderNum_.length()-2))){
				String num = orderNum_.substring(orderNum_.length()-2,orderNum_.length());
				if(num.equals("99")){
					throw new RuntimeException("今天活动序号已经超出上限！");
				}else{
					int n = Integer.parseInt(num)+1;
					if(n<10){
						orderNum = orderNum +"0"+n;
					}else{
						orderNum = orderNum + n;
					}
				}
			}else{
				orderNum = orderNum +"01";
			}
		}else{
			orderNum = orderNum +"01";
		}
		model.addAttribute("orderNum", orderNum);
		return PREFIX+"/rewardActivityAdd";
	}
	/**
	 * 功能描述：跳转活动详情页面
	 * 创建时间:2016-6-13
	 * 创建人: yyf
	 * 
	 * @return
	 */
	@RequestMapping("/toActivityView")
	public String toActivityView(Model model,@RequestParam(value="activityId",required=true) Long activityId,
			@RequestParam(value="activityType",required=true) String activityType){
		Activity act = activityService.get(activityId);
		ActivityVo vo = new ActivityVo();
		try {
			PropertyUtils.copyProperties(vo, act);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		if(activityType.equals(SystemCommon_Constant.ACTIVITY_TYPE_1)){
			AwardActivity aa = awardActivityService.findAwardActivityIdByPid(activityId);
			if(aa!=null){
				model.addAttribute("awardActivityId", aa.getId());
			}
			vo.setOldContent(vo.getContent());
			byte[] b = AnnexFileUpLoad.getContentFromFile(vo.getContent());
			vo.setContent(new String(b));
			model.addAttribute("vo", vo);
			return PREFIX+"/awardActivityView";
		}else if(activityType.equals(SystemCommon_Constant.ACTIVITY_TYPE_2)){
			RewardActivity aa = rewardActivityService.findRewardActivityIdByPid(activityId);
			if(aa!=null){
				model.addAttribute("rewardActivityId", aa.getId());
			}
			model.addAttribute("vo", vo);
			return PREFIX+"/rewardActivityView";
		}else{
			return PREFIX+"/activityView";
		}
	}
	/**
	 * 功能描述：跳转活动信息修改页面
	 * 创建时间:2016-6-13
	 * 创建人: yyf
	 * 
	 * @return
	 */
	@RequestMapping("/toActivityEdit")
	public String toActivityEdit(Model model,@RequestParam(value="activityId",required=true) Long activityId,
			@RequestParam(value="activityType",required=true) String activityType){
		Activity act = activityService.get(activityId);
		ActivityVo vo = new ActivityVo();
		try {
			PropertyUtils.copyProperties(vo, act);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		if(activityType.equals(SystemCommon_Constant.ACTIVITY_TYPE_1)){
			AwardActivity aa = awardActivityService.findAwardActivityIdByPid(activityId);
			if(aa!=null){
				model.addAttribute("awardActivityId", aa.getId());
			}
			vo.setOldContent(vo.getContent());
			byte[] b = AnnexFileUpLoad.getContentFromFile(vo.getContent());
			vo.setContent(new String(b));
			model.addAttribute("vo", vo);
			return PREFIX+"/awardActivityEdit";
		}else if(activityType.equals(SystemCommon_Constant.ACTIVITY_TYPE_2)){
			RewardActivity aa = rewardActivityService.findRewardActivityIdByPid(activityId);
			if(aa!=null){
				model.addAttribute("rewardActivityId", aa.getId());
			}
			model.addAttribute("vo", vo);
			return PREFIX+"/rewardActivityEdit";
		}else{
			return PREFIX+"/activityEdit";
		}
	}
	/**
	 * 功能描述：保存或修改活动信息
	 */
	@RequestMapping("/addOrEditActivityInfo")
	@ResponseBody
	public Map<String, Object> addOrEditActivityInfo(ActivityVo vo,HttpServletRequest request) {
		Long id = vo.getId();
		Activity act = new Activity();
		act.setId(vo.getId());
		act.setActivityType(vo.getActivityType());
		act.setBeginTime(DateUtil.format(vo.getBeginTimeStr(), "yyyy-MM-dd HH:mm:ss"));
		act.setEndTime(DateUtil.format(vo.getEndTimeStr(), "yyyy-MM-dd HH:mm:ss"));
		if(StringUtils.isNotBlank(vo.getCreateTimeStr())){
			act.setCreateTime(DateUtil.format(vo.getCreateTimeStr(), "yyyy-MM-dd HH:mm:ss"));
		}
		act.setIsValid(vo.getIsValid());
		act.setJoinTimes(vo.getJoinTimes());
		act.setJoinTimesPeriod(vo.getJoinTimesPeriod());
		act.setOrderNum(vo.getOrderNum());
		act.setStatus(vo.getStatus());
		act.setTheme(vo.getTheme());
		//获取活动详情内容信息   不为空的时候上传   20160629 yf update
		if(null != vo.getContent() && !"".equals(vo.getContent())){
			String content = AnnexFileUpLoad.writeActivityContentToFile(vo.getContent(),SystemCommon_Constant.ACTIVITY_CONTENT,vo.getOldContent(),"");
			act.setContent(content);
		}
		
		if(id!=null){
			activityService.update(act);
			return success("修改活动信息成功");
		}else{
			act.setCreateTime(new Date());
			activityService.save(act);
			ActivityVo avo = new ActivityVo();
			avo.setId(act.getId());
			if(act.getActivityType().equals(SystemCommon_Constant.ACTIVITY_TYPE_1)){
				AwardActivity aa = new AwardActivity();
				aa.setPid(act.getId());
				awardActivityService.save(aa);
				avo.setSonId(aa.getId());
			}else if(act.getActivityType().equals(SystemCommon_Constant.ACTIVITY_TYPE_2)){
				RewardActivity aa = new RewardActivity();
				aa.setPid(act.getId());
				rewardActivityService.save(aa);
				avo.setSonId(aa.getId());
			}
			return success("保存活动信息成功！",avo);
		}
	}
	/**
	 * 功能描述：保存或修改奖品
	 */
	@RequestMapping("/addOrEditAward")
	@ResponseBody
	public Map<String, Object> addOrEditAward(ActivityPrizeLinkVo vo,HttpServletRequest request) {
		Long id = vo.getId();
		ActivityPrizeLink act = new ActivityPrizeLink();
		try {
			PropertyUtils.copyProperties(act, vo);
			if(act.getPrizeId()==null){
				act.setPrizeCount(99999);
				act.setRemainCount(99999);
			}
			if(id!=null){
				activityPrizeLinkService.update(act);
			}else{
				activityPrizeLinkService.save(act);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return success("操作成功");
	}
	/**
	 * 功能描述：保存或修改奖品
	 */
	@RequestMapping("/addOrEditReward")
	@ResponseBody
	public Map<String, Object> addOrEditReward(RewardPrizeLinkVo vo,HttpServletRequest request) {
		Long id = vo.getId();
		RewardPrizeLink act = new RewardPrizeLink();
		try {
			PropertyUtils.copyProperties(act, vo);
			if(id!=null){
				rewardPrizeLinkService.update(act);
			}else{
				rewardPrizeLinkService.save(act);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return success("操作成功");
	}
	/**
	 * 功能描述：删除奖品
	 */
	@RequestMapping("/deleteAward")
	@ResponseBody
	public Map<String, Object> deleteAward(@RequestParam(value="awardId",required=true) Long awardId,@RequestParam(value="awardActivityId",required=true) Long awardActivityId,HttpServletRequest request) {
		ActivityPrizeLink  apl = activityPrizeLinkService.get(awardId);
		Integer level = apl.getPrizeLevel();
		List<ActivityPrizeLink> list = activityPrizeLinkService.findAwardByGreaterThanPrizeLevel(level,awardActivityId);
		for(ActivityPrizeLink aplk : list){
			aplk.setPrizeLevel(level++);
			activityPrizeLinkService.update(aplk);
		}
		activityPrizeLinkService.delete(awardId);
		return success("删除成功！");
	}
	/**
	 * 功能描述：删除奖品
	 */
	@RequestMapping("/deleteReward")
	@ResponseBody
	public Map<String, Object> deleteReward(@RequestParam(value="awardId",required=true) Long awardId,@RequestParam(value="activityId",required=true) Long activityId,HttpServletRequest request) {
		RewardPrizeLink  apl = rewardPrizeLinkService.get(awardId);
		Integer level = apl.getPrizeNum();
		List<RewardPrizeLink> list = rewardPrizeLinkService.findRewardByGreaterThanPrizeNum(level,activityId);
		for(RewardPrizeLink aplk : list){
			aplk.setPrizeNum(level++);
			rewardPrizeLinkService.update(aplk);
		}
		rewardPrizeLinkService.delete(awardId);
		return success("删除成功！");
	}
	/**
	 * 功能描述：删除活动
	 */
	@RequestMapping("/deleteActivity")
	@ResponseBody
	public Map<String, Object> deleteActivity(@RequestParam(value="activityId",required=true) Long activityId,HttpServletRequest request) {
		Activity  act = activityService.get(activityId);
		if(act!=null){
			act.setIsValid("0");
			activityService.update(act);
			//获取所有关联此活动的作用对象中间表数据
			List<ActivityObjectLink> linkList = activityObjectLinkService.findByActivityId(act.getId());
			for(ActivityObjectLink aol : linkList){
				aol.setIsValid("0");
				//更新关联此活动的数据
				activityObjectLinkService.update(aol);
			}
			return success("删除成功！");
		}else{
			return success("删除失败！");
		}
	}
	/**
	 * 功能描述：获取奖品序号
	 */
	@RequestMapping("/obtainOrderNum")
	@ResponseBody
	public Map<String, Object> obtainOrderNum(@RequestParam(value="awardActivityId",required=true) Long awardActivityId,
			@RequestParam(value="anwserUserId",required=true) String anwserUserId,
			@RequestParam(value="surveyEvaluationId",required=true) String surveyEvaluationId,
			@RequestParam(value="obtainOrderNumFlag",required=true) String obtainOrderNumFlag,HttpServletRequest request) {
		//获取活动奖品
		List<ActivityPrizeLink> list = activityPrizeLinkService.findAwardByActivityId(awardActivityId);
		//获取抽奖活动信息
		AwardActivity awardActivity = awardActivityService.get(awardActivityId);
		//空奖
		Map<String, Object> map = obainOrderNum(list);
		Integer result = (Integer)map.get("result");
		ActivityPrizeLink nullapl = (ActivityPrizeLink)map.get("nullapl");
		if(obtainOrderNumFlag.equals("debug")){
			return success("获取奖品序号成功！",result);
		}else{
			//校验活动是否开始
			Long hasBeginTime = activityService.checkActivityBeginTime(awardActivityId);
			if(hasBeginTime==null){
				return error("活动数据异常！请联系质讯通管理员处理！");
			}else if(hasBeginTime<0){
				hasBeginTime = -hasBeginTime;
				String remainTime = DateUtil.formatTime(hasBeginTime);
				return error("抱歉！活动还没开始,请您耐心等候！预计"+remainTime+"后活动开始！");
			}
			//首先校验此评论是否参与过抽奖
			boolean winFlag = winPrizeInfoService.checkWinPrizeByEvaluationId(surveyEvaluationId);
			if(!winFlag){
				//校验当前人是否能进行抽奖
				String flag = checkWinPrizeTimes(anwserUserId,awardActivityId);
				if(flag.equals("yes")){
					if(result!=0){
						/*
						 * 保存中奖记录
						 */
						if(StringUtils.isNotBlank(anwserUserId)){
							//获取中奖人信息
							SurveyPartakeUser  surveyUser = surveyPartakeUserService.get(Long.parseLong(anwserUserId));
							//根据活动id和中奖序号获取奖品信息
							PrizeInfo prizeInfo = prizeInfoService.findPrizeInfoByPrizeLevelAndActivityId(result,awardActivityId);
							WinPrizeInfo winprize=new WinPrizeInfo();
							winprize.setActivityId(awardActivity.getPid().toString());
							winprize.setActivityType(SystemCommon_Constant.ACTIVITY_TYPE_1);
							winprize.setPrizeNum(1L);
							winprize.setPrizeUser(surveyUser.getUsername());
							winprize.setPrizePhone(surveyUser.getPhone());
							winprize.setIsCash("0");
							System.out.println("评论记录id为="+surveyEvaluationId);
							winprize.setSurveyEvaluationId(surveyEvaluationId);
							winprize.setSnNum("");
							winprize.setSnBaseId("");
							//获取sn
							if(StringUtils.isNotBlank(surveyEvaluationId)){
								SurveyEvaluation se=evaluationService.get(Long.parseLong(surveyEvaluationId));
								if(se!=null&&StringUtils.isNotBlank(se.getSnBaseId())){
									SNBase snb=tagSNBaseService.get(se.getSnBaseId());
									if(snb!=null){
										winprize.setSnNum(snb.getSn());
										winprize.setSnBaseId(snb.getId());
									}
								}
							}
							winprize.setValid("1");
							//根据手机号获取会员信息
							String phone=surveyUser.getPhone();
							String userName=surveyUser.getUsername();
							MMember mm = membermanageService.findByPhone(phone,userName);
							System.out.println("获取会员信息的的参数：phone="+phone+"  userName="+userName);
							if(mm!=null){
								winprize.setCreateId(mm.getNmemberid());
								winprize.setCreateName(mm.getChinesename());
							}else{
								winprize.setCreateId(-1l);
								winprize.setCreateName("");
							}
							winprize.setCreateTime(new Date());
							winprize.setWinPrizeInfoType("1");//1表示抽奖
							/*
							 * 修改奖品数量  TODO
							 */
							boolean reFlag = modifyRemainCount(result,awardActivityId);
							if(prizeInfo!=null&&reFlag){
								//获取奖品来源
								if(StringUtils.isNotBlank(prizeInfo.getEntityCategory())){
									String entityCategoryName=entityCategoryService.getNamebyId(prizeInfo.getEntityCategory());
									winprize.setPrizeSupplier(entityCategoryName);
									winprize.setPrizePlace(entityCategoryName+"加油站");
								}
								winprize.setWinPrizeFlag("1");//中奖
								winprize.setPrizeName(prizeInfo.getPrizeName());
								winPrizeInfoService.save(winprize);
							}else{
								if(nullapl.getId()==null){
									System.out.println("竟然没有设置空奖！！！！说了按要求填写活动内容！！！就是不听话啊！根据活动id（activityId="+awardActivityId+"）和奖品序号（prizeLevel="+result+"）从findPrizeInfoByPrizeLevelAndActivityId方法中获取奖品信息！");
									return error("系统异常，请联系质讯通管理员处理！");//如果没有空奖
								}else{
									winprize.setPrizeSupplier("");
									winprize.setPrizePlace("");
									winprize.setWinPrizeFlag("0");//未中奖
									winprize.setPrizeName("");
									winPrizeInfoService.save(winprize);
									return success("获取奖品不存在，返回空奖！",nullapl.getPrizeLevel());//如果无法查找到奖品信息就返回空奖
								}
							}
						}
						return success("获取奖品序号成功！",result);
					}else{
						return error("系统异常，请联系质讯通管理员处理！");
					}
				}else if(flag.equals("no1")){
					return error("您的抽奖次数已经达到上限！");
				}else if(flag.equals("no2")){
					return error("抱歉，活动已结束！");
				}else if(flag.startsWith("no3")){
					String re = flag.substring(3, flag.length());
					re = DateUtil.formatTime(Long.parseLong(re));
					return error("您近期已经参与过抽奖，请您耐心等候，预计"+re+"后可再次参与抽奖！");
				}else{
					return error("不可能出现的错误！");
				}
			}else{
				return error("抱歉，当前评论已经参与过抽奖！");
			}
		}

	}
	/**
	 * 修改奖品数量
	 * 并发处理
	 * @param result
	 * @param awardActivityId
	 * @return
	 */
	private synchronized boolean modifyRemainCount(Integer result, Long awardActivityId) {
		boolean flag = true;
		//根据活动id和奖品序号获取活动奖品数据
		ActivityPrizeLink apl = activityPrizeLinkService.findActivityPrizeByPrizeLevelAndId(result,awardActivityId);
		if(apl.getRemainCount()>0){
			apl.setRemainCount(apl.getRemainCount()-1);
			activityPrizeLinkService.update(apl);
		}else{
			flag = false;
		}
		return flag;
	}
	/**
	 * 功能描述：启动活动
	 */
	@RequestMapping("/startActivity")
	@ResponseBody
	public Map<String, Object> startActivity(@RequestParam(value="activityId",required=true) Long activityId,
			@RequestParam(value="status",required=true) String status,HttpServletRequest request) {
		Activity  act = activityService.get(activityId);
		String activityType = "";
		int count = 0;
		boolean flag = false;//是否存在空奖
		if(act!=null){
			activityType = act.getActivityType();
			if(activityType.equals(SystemCommon_Constant.ACTIVITY_TYPE_1)){
				AwardActivity  aa = awardActivityService.findAwardActivityIdByPid(activityId);
				if(aa!=null){
					List<ActivityPrizeLink>  list = activityPrizeLinkService.findAwardByActivityId(aa.getId());
					count = list.size();
					for(ActivityPrizeLink apl : list){
						if(apl.getPrizeId()==null){
							flag = true;
						}
					}
				}
			}else if(activityType.equals(SystemCommon_Constant.ACTIVITY_TYPE_2)){
				RewardActivity  aa = rewardActivityService.findRewardActivityIdByPid(activityId);
				if(aa!=null){
					List<RewardPrizeLink>  list = rewardPrizeLinkService.findRewardByActivityId(aa.getId());
					count = list.size();
					flag = true;
				}
			}
			if(count>0&&flag){
				if(StringUtils.isNotBlank(status)&&status.equals(SystemCommon_Constant.ACTIVITY_STATUS_3)){
					//如果之前是暂停状态那么将暂停状态修改为启动状态，并将其下分配的活动对象的状态也改为正常状态
					act.setStatus(SystemCommon_Constant.ACTIVITY_STATUS_1);
					activityService.update(act);
					//获取所有参与此活动的对象
					List<ActivityObjectLink>  list = activityObjectLinkService.findByActivityId(act.getId());
					for(ActivityObjectLink aol : list){
						aol.setIsValid("1");
						activityObjectLinkService.update(aol);
					}
					return success("启动成功！");
				}else{
					act.setStatus(SystemCommon_Constant.ACTIVITY_STATUS_1);
					activityService.update(act);
					return success("启动成功！");
				}
			}else{
				return success("启动失败，请在活动中按要求添加奖品信息！");
			}
		}else{
			return success("启动失败！此活动已经不存在！");
		}
	}
	/**
	 * 功能描述：终止活动
	 */
	@RequestMapping("/endActivity")
	@ResponseBody
	public Map<String, Object> endActivity(@RequestParam(value="activityId",required=true) Long activityId,HttpServletRequest request) {
		
		Activity  act = activityService.get(activityId);
		if(act!=null){
			act.setStatus(SystemCommon_Constant.ACTIVITY_STATUS_2);
			activityService.update(act);
			//获取所有参与此活动的对象
			List<ActivityObjectLink>  list = activityObjectLinkService.findByActivityId(act.getId());
			for(ActivityObjectLink aol : list){
				aol.setIsValid("0");
				activityObjectLinkService.update(aol);
			}
			return success("活动已终止！");
		}else{
			return success("终止失败！此活动已经不存在！");
		}
	}
	/**
	 * 功能描述：暂停活动
	 */
	@RequestMapping("/stopActivity")
	@ResponseBody
	public Map<String, Object> stopActivity(@RequestParam(value="activityId",required=true) Long activityId,HttpServletRequest request) {
		
		Activity  act = activityService.get(activityId);
		if(act!=null){
			act.setStatus(SystemCommon_Constant.ACTIVITY_STATUS_3);
			activityService.update(act);
			//获取所有参与此活动的对象
			List<ActivityObjectLink>  list = activityObjectLinkService.findByActivityId(act.getId());
			for(ActivityObjectLink aol : list){
				aol.setIsValid("2");
				activityObjectLinkService.update(aol);
			}
			return success("活动已暂停！");
		}else{
			return success("暂停失败！此活动已经不存在！");
		}
	}
	/**
	 * 功能描述：跳转抽奖大转盘页面
	 * 创建时间:2016-6-12
	 * 创建人: yyf
	 * 
	 * @return
	 */
	@RequestMapping("/toWheelSurf")
	public String toWheelSurf(Model model,@RequestParam(value="activityId",required=true) Long activityId
			,@RequestParam(value="anwserUserId",required=false) String anwserUserId
			,@RequestParam(value="timestamp",required=false) String timestamp
			,@RequestParam(value="key",required=false) String key
			,@RequestParam(value="surveyEvaluationId",required=false) String surveyEvaluationId){
		Activity activity =activityService.get(activityId);
		//活动详情信息
		if(activity!=null){
			byte[] b = AnnexFileUpLoad.getContentFromFile(activity.getContent());
			model.addAttribute("activityDetail", new String(b));
		}
		//根据活动id获取抽奖活动id
		AwardActivity awardActivity =awardActivityService.findAwardActivityIdByPid(activityId);
		Long awardActivityId = awardActivity.getId();
		//获取活动奖品
		List<ActivityPrizeLink> list = activityPrizeLinkService.findAwardByActivityId(awardActivityId);
		String isWinPrize = "";
		String pname = "";
		String cvalue="";
		for (int i = 0; i < list.size(); i++) {
			if(i==0){
				pname = pname + list.get(i).getPrizeName();
				if(list.get(i).getPrizeId()!=null){
					isWinPrize = isWinPrize + "win";
				}else{
					isWinPrize = isWinPrize + "lose";
				}
			}else{
				pname =  pname +","+ list.get(i).getPrizeName();
				if(list.get(i).getPrizeId()!=null){
					isWinPrize = isWinPrize +"," + "win";
				}else{
					isWinPrize = isWinPrize +"," + "lose";
				}
			}
			if(i==0){
				cvalue = cvalue + "#FFF4D6";
			}else if(i%2==0){
				cvalue = cvalue + ",#FFF4D6";
			}else{
				cvalue = cvalue + ",#FFFFFF";
			}
		}
//		String pname = "一等奖,二等奖,三等奖,四等奖,五等奖,六等奖,七等奖 ,八等奖,九等奖,谢谢参与";
//		String cvalue="#FFF4D6,FFFFFF,#FFF4D6,#FFFFFF,#FFF4D6,#FFFFFF,#FFF4D6,#FFFFFF,#FFF4D6,#FFFFFF";
		model.addAttribute("isWinPrize", isWinPrize);//用来存放是否是空奖
		model.addAttribute("pname", pname);
		model.addAttribute("cvalue", cvalue);
		model.addAttribute("awardActivityId", awardActivityId);
		model.addAttribute("surveyEvaluationId", surveyEvaluationId);
		//安全校验
		PropertiesFileUtil pfu = new PropertiesFileUtil();
		String timestamp_ ="";
		if(StringUtils.isNotBlank(timestamp)){
			timestamp_ = timestamp.substring(0, 10);
		}
		String token = pfu.findValue("activity_token");
		String key_ = timestamp_+token+anwserUserId;
		key_ = MD5.getMD5Code(key_).toUpperCase();
		if(key_.equals(key)){
			model.addAttribute("anwserUserId", anwserUserId);
			model.addAttribute("obtainOrderNumFlag", "run");
			return PREFIX+"/wheelSurf";
		}else{
			 if(StringUtils.isNotBlank(anwserUserId)&&StringUtils.isNotBlank(timestamp)&&StringUtils.isNotBlank(key)){
				 return PREFIX+"/activityError";//跳转到错误页面，提示您没有抽奖权限
			 }else{
			     model.addAttribute("obtainOrderNumFlag", "debug");//预览测试中奖
				 return PREFIX+"/wheelSurf";
			 }
		}
	}
	/**
	 * 校验当前人是否抽奖的权限
	 * @return
	 */
	private String checkWinPrizeTimes(String anwserUserId,Long awardActivityId){
		String flag = "";
		//获取当前活动参数信息
		AwardActivity awardActivity = awardActivityService.get(awardActivityId);
		
		Activity act = activityService.get(awardActivity.getPid());
		//获取活动可参与次数
		Integer times = act.getJoinTimes();
		//校验当前人是否能进行抽奖
		List<WinPrizeInfo> wlist = winPrizeInfoService.findWinPrizeInfoByPhoneAndActivityId(anwserUserId,act.getId());
		int count = wlist.size();
		if(count<times){
			//获取抽奖间隔
			String timesPeriod = act.getJoinTimesPeriod();
			if(StringUtils.isNotBlank(timesPeriod)){
				//转化为毫秒
				Long period = Long.parseLong(timesPeriod)*24*3600000;
				//活动开始时间
				Long beginTime = act.getBeginTime().getTime();
				Long endTime = act.getEndTime().getTime();
				//当前时间
				Long nowTime = new Date().getTime();
				Long begin = nowTime - (beginTime+count*period);
				Long end = nowTime - endTime;
				if(end>0){
					flag = "no2";
				}else{
					if(begin>0){
						flag = "yes";
					}else{
						Long sp = -begin;
						flag = "no3"+sp;
					}
				}
			}else{
				flag = "yes";
			}
		}else{
			//本次活动此人抽奖次数大于等于本活动的可参与次数,不能再进行抽奖
			flag = "no1";
		}
		return flag;
	}

	/**
	 * 获取一个中奖号
	 * @param list
	 * @param nullapl
	 * @return
	 */
	private Map<String,Object> obainOrderNum(List<ActivityPrizeLink> list){
		//随机数的基数
		Integer sum = 0;
		//空奖品实例
		ActivityPrizeLink nullapl = new ActivityPrizeLink();
		//中奖概率区间
		List<Integer> levelList = new ArrayList<Integer>();
		Map<Integer, String> map = new HashMap<Integer, String>();
		for (int i = 0; i < list.size(); i++) {
			// TODO 判断当前奖品数量是否充足 
			Integer count = list.get(i).getRemainCount();//奖品剩余数量
//			if(count>0){20160628 yyf add不再校验奖品数量 都参与抽奖，抽中奖品后校验奖品剩余数量不足的话，跳到空奖上
				Integer pp = list.get(i).getPrizeProbability();//中奖概率
				Integer level = list.get(i).getPrizeLevel();//奖品序号
				if(pp>0){
					levelList.add(level);
					map.put(level, sum+1+"-"+(sum+pp));
					sum = sum + pp;
				}
//			}
			//初始化空奖实例
			if(list.get(i).getPrizeId()==null&&StringUtils.isBlank(list.get(i).getPrizeInfoName())){
				nullapl = list.get(i);
			}
			
		}
		//中奖序号
		Integer result = 0 ;
		Random rd = new Random();
		if(sum>0){
			int rdNum = rd.nextInt(sum)+1;
			for(Integer in : levelList){
				String pstr = map.get(in);
				String[] parr = pstr.split("-");
				Integer begin = Integer.valueOf(parr[0]);
				Integer end = Integer.valueOf(parr[1]);
				if(rdNum>=begin&&rdNum<=end){
					result = in;
					break;
				}
			}
		}
		Map<String,Object> rmap = new HashMap<String, Object>();
		rmap.put("result", result);
		rmap.put("nullapl", nullapl);
		return rmap;
	}
	public static void main(String[] args) {
		System.out.println(Long.parseLong(""));
		
	}
}
