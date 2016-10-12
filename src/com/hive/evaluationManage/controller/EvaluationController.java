package com.hive.evaluationManage.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.evaluationManage.model.EvaluationVo;
import com.hive.evaluationManage.service.EvaluationService;
import com.hive.surveymanage.entity.IndustryEntity;
import com.hive.surveymanage.service.CObjectService;
import com.hive.surveymanage.service.IndustryEntityService;
import com.hive.util.PropertiesFileUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**
 * @description 评价控制类
 * @author yyf
 * @createtime 2016-1-13
 */
@Controller
@RequestMapping("/evaluationManage")
public class EvaluationController extends BaseController {
	
	private final String PREFIX="evaluationmanage";
	
	@Resource
	private EvaluationService evaluationService;
	@Resource
	private IndustryEntityService industryEntityService;
	/**
	 * 功能描述：获取评论列表
	 * 创建时间:2016-1-13
	 * 创建人: yyf
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/datagrid.action")
	@ResponseBody
	public DataGrid datagrid(RequestPage page,HttpServletRequest request,EvaluationVo evaluationVo){
		DataGrid resultdata = evaluationService.datagrid(page,evaluationVo);
		return resultdata;
	}
	
	/**
	 * 功能描述：转到评论列表页面
	 * 创建时间:2016-1-13
	 * 创建人: yyf
	 * 
	 * @return
	 */
	@RequestMapping("/toEvaluationManage")
	public String toEvaluation(Model model, HttpServletRequest request){
		//取得配置文件配置的上传路径
		PropertiesFileUtil propertiesFileUtil = new PropertiesFileUtil();
		String zxt_url = propertiesFileUtil.findValue("zxt_http_url");
		model.addAttribute("zxt_url", zxt_url);
		return PREFIX+"/evaluationList";
	}
	/**
	 * 功能描述：转到评论列表页面
	 * 创建时间:2016-1-13
	 * 创建人: yyf
	 * 
	 * @return
	 */
	@RequestMapping("/toEvaluationListForGsims")
	public String toEvaluationListForGsims(Model model,@RequestParam(value="objectId",required=true) Long objectId
			,@RequestParam(value="objectType",required=true) String objectType){
		List<IndustryEntity>  list = industryEntityService.findEntityByOutSysObjectId(objectId,objectType);
		String entityName = "";
		if(list.size()>0){
			IndustryEntity industryEntity =list.get(0);
			entityName = industryEntity.getEntityName();
		}		
		PropertiesFileUtil propertiesFileUtil = new PropertiesFileUtil();
		//取得配置文件配置的上传路径
		String zxt_url = propertiesFileUtil.findValue("zxt_http_url");
		model.addAttribute("zxt_url", zxt_url);
		if(StringUtils.isNotBlank(entityName)){
			model.addAttribute("entityName", entityName);//加油站的企业实体id
			return PREFIX+"/evaluationListForGsims";
		}else{
			return PREFIX+"/evaluationError";
		}
		
	}
	/**
	 * 功能描述：转到问卷详情页面
	 * 创建时间:2016-1-18
	 * 创建人: yyf
	 * 
	 * @return
	 */
	@RequestMapping("/toEvaluationDetail")
	public String toEvaluationDetail(){
		return PREFIX+"/evaluationDetail";
	}
	
	/**
	 * 功能描述：为质量评价微信公众好提供的评价查询入口
	 * 创建时间:2016-3-21
	 * 创建人: yyf
	 * 
	 * @return
	 */
	@RequestMapping("/toQueryEvaluation")
	public String toQueryEvaluation(Model model){
		PropertiesFileUtil propertiesFileUtil = new PropertiesFileUtil();
		String zxt_http_url = propertiesFileUtil.findValue("zxt_http_url");
		if(!StringUtils.isNotBlank(zxt_http_url))
			zxt_http_url = "http://localhost:80/zxt";
		model.addAttribute("zxt_http_url", zxt_http_url);
		return PREFIX+"/evaluationQuery";
	}
	/**
	 * 功能描述：加油站兑奖记录评价
	 * 创建时间:20160415
	 * 创建人: yyf
	 * 
	 * @return
	 */
	@RequestMapping("/toQueryEvaluationForApp")
	public String toQueryEvaluationForWeChat(Model model){
		PropertiesFileUtil propertiesFileUtil = new PropertiesFileUtil();
		String zxt_http_url = propertiesFileUtil.findValue("zxt_http_url");
		if(!StringUtils.isNotBlank(zxt_http_url))
			zxt_http_url = "http://localhost:80/zxt";
		model.addAttribute("zxt_http_url", zxt_http_url);
		return PREFIX+"/evaluationQueryForApp";
	}
	
//	public List findSurveyDetailList(){
//		evaluationService.findSurveyDetailList();
//		return null;
//	}
	
	
	/**
	  * 方法名称：toEvaluation
	  * 功能描述：通过汇总页面进入评价列表
	  * 创建时间:2016年6月2日下午3:28:15
	  * 创建人: pengfei Zhao
	  * @return String
	 */
	@RequestMapping("/toEvaluationByChart")
	public String toEvaluationByChart(Model model, HttpServletRequest request){
		//取得配置文件配置的上传路径
		PropertiesFileUtil propertiesFileUtil = new PropertiesFileUtil();
		String zxt_url = propertiesFileUtil.findValue("zxt_http_url");
		model.addAttribute("zxt_url", zxt_url);
		String countTime=request.getParameter("countTime");
		String isMember=request.getParameter("isMember");//0全部 1会员 2匿名
		String entityId=request.getParameter("entityId");//实体
		String surveyId=request.getParameter("surveyId");//问卷
		model.addAttribute("countTime", countTime);
		model.addAttribute("isMember", isMember);
		model.addAttribute("entityId", entityId);
		model.addAttribute("surveyId", surveyId);
		return PREFIX+"/evaluationByChartList";
	}
	
	/**
	  * 方法名称：toEvaluation
	  * 功能描述：通过汇总统计页面进入评价列表
	  * 创建时间:2016年6月2日下午3:28:15
	  * 创建人: pengfei Zhao
	  * @return String
	 */
	@RequestMapping("/datagridForchart.action")
	@ResponseBody
	public DataGrid datagridForchart(RequestPage page,HttpServletRequest request){
		String countTime=request.getParameter("countTime");
		String isMember=request.getParameter("isMember");//0全部 1会员 2匿名
		String entityId=request.getParameter("entityId");//实体
		String surveyId=request.getParameter("surveyId");//问卷
		DataGrid resultdata = evaluationService.datagridforChart(page,countTime,isMember,entityId,surveyId);
		return resultdata;
	}
	
	/**
	  * 方法名称：toEvaluation
	  * 功能描述：各个实体的评价数据统计
	  * 创建时间:2016年6月2日下午3:28:15
	  * 创建人: pengfei Zhao
	  * @return String
	 */
	@RequestMapping("/toEvaluationCharsForEntity")
	public String toEvaluationCharsForEntity(Model model, HttpServletRequest request,@RequestParam(value="gasIdList",required=false) String gasIdList,@RequestParam(value="otherSystemMark",required=false) String otherSystemMark){
		//取得配置文件配置的上传路径
		PropertiesFileUtil propertiesFileUtil = new PropertiesFileUtil();
		String zxt_url = propertiesFileUtil.findValue("zxt_http_url");
		model.addAttribute("zxt_url", zxt_url);
		model.addAttribute("gasIdList", gasIdList);
		model.addAttribute("otherSystemMark", otherSystemMark);
		return PREFIX+"/evaluationChartForSurveyEntity";
	}
	
	
}
