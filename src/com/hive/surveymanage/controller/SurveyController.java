package com.hive.surveymanage.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.WriterException;
import com.hive.common.AnnexFileUpLoad;
import com.hive.common.SystemCommon_Constant;
import com.hive.surveymanage.entity.IndustryEntity;
import com.hive.surveymanage.entity.Survey;
import com.hive.surveymanage.model.SurveySearchBean;
import com.hive.surveymanage.service.IndustryEntityService;
import com.hive.surveymanage.service.SurveyService;
import com.hive.systemconfig.entity.SysObjectParameconfig;
import com.hive.systemconfig.entity.SystemConfig;
import com.hive.systemconfig.model.SysObjectParameBean;
import com.hive.systemconfig.service.SysObjectParameconfigService;
import com.hive.systemconfig.service.SystemConfigService;
import com.hive.util.PropertiesFileUtil;
import com.hive.util.TwoDCodeImage;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**
 * @description 问卷调查控制类
 * @author 燕珂
 * @createtime 2014-3-11 上午11:18:43
 */
@Controller
@RequestMapping("/surveyManage")
public class SurveyController extends BaseController {
	
	private final String PREFIX="surveymanage/surveyManage";
	
	@Resource
	private SurveyService surveyService;
	
	@Resource
	private SystemConfigService systemConfigService;
	@Resource
	private SysObjectParameconfigService sysObjectParameconfigService;
	
	@Resource
	private IndustryEntityService industryEntityService;
	
	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出Task对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getSurvey(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id !=null && id != -1) {
			model.addAttribute("survey", surveyService.get(id));
		}
	}
	
	/**
	 * 功能描述：进入问卷信息列表页
	 * 创建时间:2013-11-14下午3:25:47
	 * 创建人: 燕珂
	 * 
	 * @return
	 */
	@RequestMapping("/toSurveyList")
	public String toSurveyList(Model model, HttpServletRequest request){
		//取得配置文件配置的上传路径
		PropertiesFileUtil propertiesFileUtil = new PropertiesFileUtil();
		String zxt_url = propertiesFileUtil.findValue("zxt_http_url");
		model.addAttribute("zxt_url", zxt_url);
		return PREFIX + "/surveyList";
	}
	
	/**
	 * 功能描述：问卷信息列表数据加载
	 * 创建时间:2013-11-26上午10:06:40
	 * 创建人: 燕珂
	 * 
	 * @param page
	 * @return
	 */
	@RequestMapping("/listSurvey")
	@ResponseBody
	public DataGrid listSurvey(RequestPage page, SurveySearchBean searchBean){
		return surveyService.datagrid(page, searchBean);
	}
	
	/**
	 * 功能描述：进入问卷添加页面
	 * 创建时间:2013-11-14下午3:53:05
	 * 创建人: 燕珂
	 * 
	 * @return
	 */
	@RequestMapping("/toSurveyAdd")
	public String toSurveyAdd(){
		return PREFIX + "/surveyAdd";
	}

	/**
	 * 功能描述：保存问卷
	 * 创建时间:2013-11-14下午4:14:16
	 * 创建人: 燕珂
	 * 
	 * @param survey
	 * @param session
	 * @return
	 */
	@RequestMapping("/saveSurvey")
	@ResponseBody
	public Map<String,Object> saveSurvey(Survey survey, HttpSession session, HttpServletRequest request){
		// 处理裁剪的图片(杨辉)
		AnnexFileUpLoad annexFileUpload = new AnnexFileUpLoad();
		String imgPath = survey.getPicturePath();
		if (StringUtils.isNotBlank(imgPath)) {
			String imgName = imgPath.substring(imgPath.lastIndexOf("/"));
			String ext = imgName.substring(imgName.indexOf("."));
			if(!".jpg".equals(ext)){
				return error("问卷图标请选择.jpg类型的图片");
			}
					
			String newImg = annexFileUpload.cutImage(request, survey.getPicturePath(),"");
			survey.setPicturePath(newImg);
		}
		
		survey.setParticipatenum(0); // 问卷参与人数初始化为0 
		Long curUserId = (Long)session.getAttribute("userId");
		survey.setCreateid(curUserId);
		survey.setCreatetime(new Date());
		survey.setAuditstatus(SystemCommon_Constant.AUDIT_STATUS_0);//初始化 0-未审核	
		survey.setValid(SystemCommon_Constant.VALID_STATUS_1); //初始化  1-可用
		
		surveyService.save(survey);
		return success("保存成功", survey);
	}
	
	/**
	 * 功能描述：进入问卷查看页面
	 * 创建时间:2013-11-14下午4:53:25
	 * 创建人: 燕珂
	 * 
	 * @return
	 */
	@RequestMapping("/toSurveyView")
	public String toSurveyView(@RequestParam("surveyId") Long surveyId, Model model){
		Survey survey = surveyService.get(surveyId);
		model.addAttribute("survey", survey);
		return PREFIX + "/surveyView";
	}
	
	/**
	 * 功能描述：进入问卷编辑页面
	 * 创建时间:2013-11-14下午4:53:25
	 * 创建人: 燕珂
	 * 
	 * @return
	 */
	@RequestMapping("/toSurveyEdit")
	public String toSurveyEdit(@RequestParam("surveyId") Long surveyId, Model model){
		Survey survey = surveyService.get(surveyId);
		model.addAttribute("survey", survey);
		return PREFIX + "/surveyEdit";
	}
	
	/**
	 * 功能描述：更新问卷
	 * 创建时间:2013-11-14下午5:09:52
	 * 创建人: 燕珂
	 * 
	 * @param survey
	 * @param session
	 * @return
	 */
	@RequestMapping("/updateSurvey")
	@ResponseBody
	public Map<String,Object> updateSurvey(@ModelAttribute("survey")Survey survey, HttpServletRequest request, HttpSession session){
		Survey surveyTemp = surveyService.get(survey.getId());
		
		// 场景：先打开某个问卷编辑界面，然后在列表页将其审核通过，再去编辑界面进行修改，这是不合逻辑的 bug
		if(SystemCommon_Constant.AUDIT_STATUS_1.equals(surveyTemp.getAuditstatus())){
			return error("该问卷已经审核通过，不能修改！");
		}
		
		AnnexFileUpLoad annexFileUpload = new AnnexFileUpLoad();
		String imgPath = survey.getPicturePath();
		if (StringUtils.isNotBlank(imgPath)) {
			String imgName = imgPath.substring(imgPath.lastIndexOf("/"));
			String ext = imgName.substring(imgName.indexOf("."));
			if(!".jpg".equals(ext)){
				return error("问卷图标请选择.jpg类型的图片");
			}
					
			String newImg = annexFileUpload.cutImage(request, survey.getPicturePath(), surveyTemp.getPicturePath());
			survey.setPicturePath(newImg);
		}
		
		survey.setCreateid(surveyTemp.getCreateid()); // 创建人
		survey.setCreatetime(surveyTemp.getCreatetime()); // 创建时间
		survey.setModifyid((Long)session.getAttribute("userId")); // 修改人
		survey.setModifytime(new Date());  // 修改时间
		survey.setValid(surveyTemp.getValid()); // 是否有效
		
		survey.setAuditstatus(SystemCommon_Constant.AUDIT_STATUS_0); // 修改后将审核状态改为“未审核”
		
		surveyService.saveOrUpdate(survey);
		return success("修改成功");
	}
	
	
	/**
	 * 功能描述：删除问卷
	 * 创建时间:2013-11-14下午3:55:39
	 * 创建人: 燕珂
	 * 
	 * @param survey
	 * @return
	 */
	@RequestMapping("/deleteSurvey")
	@ResponseBody
	public Map<String,Object> deleteSurvey(@RequestParam("surveyId") Long surveyId){
		Survey survey = surveyService.get(surveyId);
		if(survey != null){
			survey.setValid(SystemCommon_Constant.VALID_STATUS_0);
			surveyService.update(survey);
			return success("删除成功");
		}else{
			return error("删除失败");
		}
	}
	
	/**
	 * 
	 * @Description:设置问卷可以公示
	 * @author YangHui 
	 * @Created 2014-9-2
	 * @param surveyId
	 * @return
	 */
	@RequestMapping("/showSurvey")
	@ResponseBody
	public Map<String,Object> showSurvey(@RequestParam("surveyId") Long surveyId){
		Survey survey = surveyService.get(surveyId);
		if(survey != null){
			survey.setIsShow(SystemCommon_Constant.VALID_STATUS_1);
			surveyService.update(survey);
			return success("操作成功");
		}else{
			return error("操作失败");
		}
	}
	
	/**
	 * 
	 * @Description:屏蔽问卷公示
	 * @author YangHui 
	 * @Created 2014-9-2
	 * @param surveyId
	 * @return
	 */
	@RequestMapping("/shieldSurvey")
	@ResponseBody
	public Map<String,Object> shieldSurvey(@RequestParam("surveyId") Long surveyId){
		Survey survey = surveyService.get(surveyId);
		if(survey != null){
			survey.setIsShow(SystemCommon_Constant.VALID_STATUS_0);
			surveyService.update(survey);
			return success("操作成功");
		}else{
			return error("操作失败");
		}
	}
	
	/**
	 * 终止问卷
	 * @author 燕珂 
	 * @Created 2015-9-8
	 * @param surveyId
	 */
	@RequestMapping("/stopSurvey")
	@ResponseBody
	public Map<String,Object> stopSurvey(@RequestParam("surveyId") Long surveyId){
		Survey survey = surveyService.get(surveyId);
		if(survey != null){
			survey.setStatus(SystemCommon_Constant.SURVEY_STATUS_CLOSE);
			surveyService.update(survey);
			return success("操作成功");
		}else{
			return error("操作失败");
		}
	}
	
	/**
	 * 开启问卷
	 * @author 燕珂 
	 * @Created 2015-9-8
	 * @param surveyId
	 */
	@RequestMapping("/startSurvey")
	@ResponseBody
	public Map<String,Object> startSurvey(@RequestParam("surveyId") Long surveyId){
		Survey survey = surveyService.get(surveyId);
		if(survey != null){
			survey.setStatus(SystemCommon_Constant.SURVEY_STATUS_ON);
			surveyService.update(survey);
			return success("操作成功");
		}else{
			return error("操作失败");
		}
	}
	
	/**
	 * 延期问卷
	 * @author 燕珂 
	 * @Created 2015-9-8
	 * @param surveyId
	 */
	@RequestMapping("/delaySurvey")
	@ResponseBody
	public Map<String,Object> delaySurvey(@RequestParam("surveyId") Long surveyId, @RequestParam("endTimeStr") String endTimeStr){
		Survey survey = surveyService.get(surveyId);
		if(survey != null){
			try {
				survey.setEndtime(new SimpleDateFormat("yyyy-MM-dd").parse(endTimeStr));
				if (survey.getEndtime().getTime() < survey.getBegintime().getTime())
					return error("问卷结束时间必须大于开始时间！");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			surveyService.update(survey);
			return success("操作成功");
		}else{
			return error("操作失败");
		}
	}
	
	/**
	 * 审核问卷
	 * @param request
	 * @return
	 */
	@RequestMapping("/auditSurvey")
	@ResponseBody
	public Object checkQualityPromise(HttpSession session, HttpServletRequest request){
		String ids = request.getParameter("ids");
		String auditstatus = request.getParameter("auditstatus");
		String auditopinion = request.getParameter("auditopinion");
		surveyService.updateAuditSurvey(ids,(Long) session.getAttribute("userId"), auditstatus, auditopinion);
		return this.success("审核成功！");
	}
	
	/**
	 * 功能描述：进入问卷信息列表页
	 * 创建时间:2013-11-14下午3:25:47
	 * 创建人: 燕珂
	 * 
	 * @return
	 */
	@RequestMapping("/toSurveyCount")
	public String toSurveyCount(){
		return PREFIX + "/surveyCount";
	}
	
	/**
	 * 功能描述：进入问卷设置页
	 * 创建时间:2013-11-14下午3:25:47
	 * 创建人: 燕珂
	 * 
	 * @return
	 */
	@RequestMapping("/toSurveySet")
	public String toSurveySet(@RequestParam("surveyId") Long surveyId, Model model){
	//	Map<String, Long> objectSetMap = sysObjectParameconfigService.getObjectSetById("S_SURVEY", "问卷设置类", surveyId);
	//	model.addAttribute("objectSetMap", objectSetMap);
		List<SysObjectParameconfig> objectConfigList =sysObjectParameconfigService.getSysObjectParameconfigByObjecttype("S_SURVEY", "问卷设置类", surveyId);
		List<SystemConfig>  configList=systemConfigService.getSystemConfigListByParamCategory("问卷设置类");//getSystemConfigListByParamCategory
		
		List systemConfigIds = systemConfigService.getSystemConfigIdsByParamCategory("问卷设置类");
		List sysObjectParameIds = sysObjectParameconfigService.getSysObjectParameconfigIdsByObjecttype("S_SURVEY", "问卷设置类", surveyId);
		
		List sysList = new ArrayList(); //存放除了在配置表中存放以外的ID
		List objectList = new ArrayList(); //存放在配置表中存在的ID
		
		for(int i=0;i<systemConfigIds.size();i++){
			Long scid = (Long) systemConfigIds.get(i);
			if(sysObjectParameIds.contains(scid)){ //设置表中存在跟默认配置表一样的ID
				SysObjectParameconfig con = sysObjectParameconfigService.getSysObjectParame("S_SURVEY",surveyId,scid);
				SysObjectParameBean bean = new SysObjectParameBean();
				try {
					PropertyUtils.copyProperties(bean, con);
					SystemConfig c = systemConfigService.get(scid);
					bean.setParameCode(c.getParameCode());
					bean.setParameName(c.getParameName());
					bean.setIsRadio(c.getIsRadio());
					objectList.add(bean);
				} catch (Exception e) {
				}
			}else{
				sysList.add(systemConfigService.get(scid));
			}
			
		}

		
		model.addAttribute("configlist", sysList);
		model.addAttribute("objectConfiglist",objectList);
		return PREFIX + "/surveySet";
	}
	
	/**
	 * 设置问卷
	 * @param request
	 * @return
	 */
	@RequestMapping("/setSurvey")
	@ResponseBody
	public Object setSurvey(SystemConfig systemConfig, HttpServletRequest request, @RequestParam("surveyId") Long surveyId){
		// 先删除旧的
	//	systemConfigService.deleteOldSystemConfig("S_SURVEY", surveyId);
		sysObjectParameconfigService.deleteOldObjectConfig("S_SURVEY", surveyId);
		List<SystemConfig>  configList=systemConfigService.getSystemConfigListByParamCategory("问卷设置类");//getSystemConfigListByParamCategory
		
		for(SystemConfig c:configList){
			SysObjectParameconfig sysObjectParameconfig1 = new SysObjectParameconfig();
			sysObjectParameconfig1.setObjecttype("S_SURVEY");
			sysObjectParameconfig1.setObjectid(surveyId);
			sysObjectParameconfig1.setConfigid(c.getId());
			sysObjectParameconfig1.setCurrentValue(Long.parseLong(request.getParameter(c.getParameCode())));
			sysObjectParameconfigService.save(sysObjectParameconfig1);
		}
		/*
		String ipRepeat = request.getParameter("ipRepeat");
		String userRepeat = request.getParameter("userRepeat");
		String viewAfterVote = request.getParameter("viewAfterVote");
		
		systemConfig.setParameCategory("问卷设置类");
		
		systemConfig.setParameCode(SystemCommon_Constant.SURVEY_SET_CODE_IPREPEAT);
		systemConfig.setParameName("允许同一 IP 多次投票");
		systemConfig.setParameCurrentValue(Long.parseLong(ipRepeat));
		systemConfig.setValid(SystemCommon_Constant.VALID_STATUS_1);
		systemConfigService.save(systemConfig);
		SysObjectParameconfig sysObjectParameconfig = new SysObjectParameconfig();
		sysObjectParameconfig.setObjecttype("S_SURVEY");
		sysObjectParameconfig.setObjectid(surveyId);
		sysObjectParameconfig.setConfigid(systemConfig.getId());
		sysObjectParameconfigService.save(sysObjectParameconfig);
		
		systemConfig.setParameCode(SystemCommon_Constant.SURVEY_SET_CODE_USERREPEAT);
		systemConfig.setParameName("允许同一用户多次投票");
		systemConfig.setParameCurrentValue(Long.parseLong(userRepeat));
		systemConfig.setValid(SystemCommon_Constant.VALID_STATUS_1);
		systemConfigService.save(systemConfig);
		sysObjectParameconfig.setObjecttype("S_SURVEY");
		sysObjectParameconfig.setObjectid(surveyId);
		sysObjectParameconfig.setConfigid(systemConfig.getId());
		sysObjectParameconfigService.save(sysObjectParameconfig);
		
		systemConfig.setParameCode(SystemCommon_Constant.SURVEY_SET_CODE_VIEWAFTERVOTE);
		systemConfig.setParameName("允许投票后查看结果");
		systemConfig.setParameCurrentValue(Long.parseLong(viewAfterVote));
		systemConfig.setValid(SystemCommon_Constant.VALID_STATUS_1);
		systemConfigService.save(systemConfig);
		sysObjectParameconfig.setObjecttype("S_SURVEY");
		sysObjectParameconfig.setObjectid(surveyId);
		sysObjectParameconfig.setConfigid(systemConfig.getId());
		sysObjectParameconfigService.save(sysObjectParameconfig);
		*/
		return this.success("设置成功！");
	}
	
	/**
	 * 
	 * @Description: 生产二维码图片
	 * @author YangHui 
	 * @Created 2014-10-16
	 * @param id
	 * @return
	 * @throws WriterException
	 * @throws IOException
	 */
	@RequestMapping("createTwoCode")
	@ResponseBody
	public Map<String, Object> createTwoCode(HttpServletRequest request,@RequestParam(value="id") Long id) throws WriterException, IOException{
		
		//取得配置文件中服务部署的IP和端口
		PropertiesFileUtil pfu = new PropertiesFileUtil();
		String httpUrl = pfu.findValue("zxt_http_url");
		String text = httpUrl+"zxt-manage/interface/survey/sweepCode.action?id="+id+"&sign=survey";
		String imgName = "";
		Survey s = surveyService.get(id);
		if(s!=null){
			imgName = surveyService.get(id).getSubject();
		}
		String path = ""; 
		path = TwoDCodeImage.writeImage(text,imgName);
		if(!"".equals(path)){
			//二维码生成后，修改标志位状态
			s.setHasCode(SystemCommon_Constant.SIGN_YES_1);
			surveyService.update(s);
		}
		
		return this.success("二维码生成成功，位置：" + path,s);
	}
	/**
	 * 获取有效的问卷列表
	 * @param p
	 * @return
	 */
	@RequestMapping("findAllSurvey")
	@ResponseBody
	public List<Survey> findAllSurvey(@RequestParam(value="q",required=false) String q){
		return surveyService.findAllSurvey(q);
	}
	
	/**
	 * 根据实体ID 获取 行业类型ID 然后获取  行业类型对应的所有有效问卷
	 * yf 20160303 add
	 * @param industryEntityId
	 * @return
	 */
	@RequestMapping("/getSurveyByindustryEntity")
	@ResponseBody
	public List<Survey> getSurveyByindustryEntity(@RequestParam(value = "industryEntityId")  String industryEntityId){
		List<Survey> list = new ArrayList<Survey>();
		if(null != industryEntityId && !"".equals(industryEntityId)){
			IndustryEntity industryEntity = industryEntityService.get(Long.valueOf(industryEntityId));
			if(null != industryEntity){
				String industryid = industryEntity.getEntityType();//行业类型ID
				if(null != industryid && !"".equals(industryid)){
					list = surveyService.findAllSurveyByIndustryid(Long.valueOf(industryid));
				}
			}
		}
		return list;
	}
	
	/**
	 * 根据   行业类型ID 然后获取  行业类型对应的所有有效问卷
	 * yf 20160303 add
	 * @param surveyIndustryId
	 * @return
	 */
	@RequestMapping("/getSurveyBySurveyIndustry")
	@ResponseBody
	public List<Survey> getSurveyBySurveyIndustry(@RequestParam(value = "surveyIndustryId")  String surveyIndustryId){
		List<Survey> list = new ArrayList<Survey>();
		if(null != surveyIndustryId && !"".equals(surveyIndustryId)){
			list = surveyService.findAllSurveyByIndustryid(Long.valueOf(surveyIndustryId));
		}
		return list;
	}
}
