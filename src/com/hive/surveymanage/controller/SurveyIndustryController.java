package com.hive.surveymanage.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.common.SystemCommon_Constant;
import com.hive.surveymanage.entity.IndustryEntity;
import com.hive.surveymanage.entity.IndustryEntitySurvey;
import com.hive.surveymanage.entity.Survey;
import com.hive.surveymanage.entity.SurveyIndustry;
import com.hive.surveymanage.entity.SurveyIndustrySurvey;
import com.hive.surveymanage.model.IndustryEntityBean;
import com.hive.surveymanage.service.SurveyIndustryService;
import com.hive.surveymanage.service.SurveyIndustrySurveyService;
import com.hive.surveymanage.service.SurveyService;
import com.hive.systemconfig.service.VersionCategoryService;
import com.hive.util.DataUtil;
import com.hive.util.EncoderHandler;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**
 * @description 问卷所属行业管理控制类
 * @author 燕珂
 * @createtime 2014-3-15 下午03:55:13
 */
@Controller
@RequestMapping("/surveyIndustryManage")
public class SurveyIndustryController extends BaseController {

	private static final String PREFIX = "surveymanage/surveyIndustryManage";  // 页面目录（路径前缀）

	@Resource
	private SurveyIndustryService surveyIndustryService;
	@Resource
	private VersionCategoryService versionCategoryService;
	
	@Resource
	private SurveyService surveyService;
	@Resource
	private SurveyIndustrySurveyService surveyIndustrySurveyService;
	
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
	public List<SurveyIndustry> treegrid() {
		return surveyIndustryService.allSurveyIndustry();
	}
	
	@RequestMapping("/datagrid") 
	@ResponseBody
	public DataGrid datagrid(RequestPage page) {
		return surveyIndustryService.datagrid(page);
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
	public Map<String, Object> insert(SurveyIndustry surveyIndustry, HttpSession session) {
		if (surveyIndustry.getPid() == null)
			surveyIndustry.setPid(0L); // 根节点的父 id 设为0
		
		surveyIndustry.setCreateid((Long) session.getAttribute("userId")); // 创建人
		surveyIndustry.setCreatetime(new Date()); // 创建时间
		surveyIndustry.setValid(SystemCommon_Constant.VALID_STATUS_1); // 是否有效
		
		surveyIndustryService.insert(surveyIndustry);
		
		versionCategoryService.updateVersionCategory(SystemCommon_Constant.V_VOTE_INDUSTRY);
		return success("添加成功！", surveyIndustry);
	}
	
	/**
	 * 跳转至修改页
	 */
	@RequestMapping("edit")
	public String edit(Model model, @RequestParam(value = "surveyIndustryId") long surveyIndustryId) {
		model.addAttribute("vo", surveyIndustryService.get(surveyIndustryId));
		return PREFIX + "/edit";
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map update(SurveyIndustry surveyIndustry, HttpSession session) {
		
		SurveyIndustry surveyIndustryTemp = surveyIndustryService.get(surveyIndustry.getId());
		
		surveyIndustry.setCreateid(surveyIndustryTemp.getCreateid()); // 创建人
		surveyIndustry.setCreatetime(surveyIndustryTemp.getCreatetime()); // 创建时间
		surveyIndustry.setModifyid((Long)session.getAttribute("userId")); // 修改人
		surveyIndustry.setModifytime(new Date());  // 修改时间
		surveyIndustry.setValid(surveyIndustryTemp.getValid()); // 是否有效
		
		surveyIndustryService.update(surveyIndustry);
		versionCategoryService.updateVersionCategory(SystemCommon_Constant.V_VOTE_INDUSTRY);
		return success("修改成功！", surveyIndustry);
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Map delete(@RequestParam(value="id") Long id)
	{
		if (!DataUtil.isNull(id)) {
			boolean flag = surveyIndustryService.checkChild(id);
			if(flag){
				surveyIndustryService.delete_logic(id); // 逻辑删除
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
	public List<SurveyIndustry> getParentIndustryList(){
		List list = surveyIndustryService.getParentIndustryList();
		return list;
		
	}
	/**
	 * 获取所有的行业类别
	 * yyf 20160515 add
	 * @return
	 */
	@RequestMapping("getAllIndustryList")
	@ResponseBody
	public List<SurveyIndustry> getAllIndustryList(){
		List list = surveyIndustryService.getAllIndustryList();
		return list;
		
	}
	
	
	/**
	 * 绑定问卷信息
	 * @author 
	 * @param model
	 * @param productId
	 * @return
	 * 20160321  yf 迁移
	 */
	@RequestMapping("/bindingInfo.action") 
	public String bindingInfo(Model model, @RequestParam(value = "surveyIndustryId") String surveyIndustryId) {
		
		SurveyIndustry surveyIndustry = new SurveyIndustry();
		if(null != surveyIndustryId && !"".equals(surveyIndustryId) ){
			surveyIndustry = surveyIndustryService.get(Long.valueOf(surveyIndustryId));
		}
		model.addAttribute("surveyIndustry", surveyIndustry);
		return PREFIX + "/binding";
	}
	/**
	 * 绑定问卷信息
	 * @author
	 * 20160321  yf 迁移
	 */
	@RequestMapping("/bindingSurveyInfo")
	@ResponseBody
	public Map<String, Object> bindingSurveyInfo(HttpServletRequest request, HttpSession session) {
	try {
		String surveyIndustryId = request.getParameter("id");
		String surveyId = request.getParameter("surveyId");
		
		SurveyIndustry surveyIndustry = new SurveyIndustry();
		if(null != surveyIndustryId && !"".equals(surveyIndustryId) ){
			surveyIndustry = surveyIndustryService.get(Long.valueOf(surveyIndustryId));
			if(null != surveyId && !"".equals(surveyId) ){
				Survey survey = surveyService.get(Long.valueOf(surveyId));
				surveyIndustry.setSurveyId(Long.valueOf(surveyId));
				surveyIndustry.setSurveyTitle(survey.getSubject());
				surveyIndustryService.update(surveyIndustry);
			}else{
				surveyIndustry.setSurveyId(null);
				surveyIndustry.setSurveyTitle("没有绑定问卷");
				surveyIndustryService.update(surveyIndustry);
			}
			if(null != surveyId && !"".equals(surveyId) ){
				List<SurveyIndustrySurvey> returnList=surveyIndustrySurveyService.exitEntityByIndustryandsurveyId(surveyIndustryId, surveyId);
				if(returnList.size()>0){
					SurveyIndustrySurvey surveyIndustrySurve=returnList.get(0);
					surveyIndustrySurve.setSurveyId(surveyId);
					surveyIndustrySurve.setCreateTime(new Date());
					surveyIndustrySurve.setSurveyTitle(surveyService.get(Long.valueOf(surveyId)).getSubject());
					surveyIndustrySurveyService.update(surveyIndustrySurve);
				}else{
					SurveyIndustrySurvey surveyIndustrySurvey = new SurveyIndustrySurvey();
					surveyIndustrySurvey.setSurveyIndustryId(surveyIndustryId);
					surveyIndustrySurvey.setSurveyId(surveyId);
					surveyIndustrySurvey.setCreateTime(new Date());
					surveyIndustrySurvey.setSurveyTitle(surveyService.get(Long.valueOf(surveyId)).getSubject());
					surveyIndustrySurveyService.save(surveyIndustrySurvey);
				}
				}
			
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success("修改成功！", "");
	}
	
	/**
	 * 查看标签详情信息
	 * @param model
	 * @param productId
	 * @return
	 */
	@RequestMapping("/viewBarcodeInfo.action") 
	public String viewBarcodeInfo(Model model, @RequestParam(value = "surveyIndustryId") String surveyIndustryId) {
		SurveyIndustry surveyIndustry = new SurveyIndustry();
		try {
			if(null != surveyIndustryId && !"".equals(surveyIndustryId) ){
				surveyIndustry = surveyIndustryService.get(Long.valueOf(surveyIndustryId));
			}
			String queryPath = SystemCommon_Constant.QRCODE_PATH_surveyIndustry + surveyIndustryId;
			
			model.addAttribute("surveyIndustry", surveyIndustry);
			model.addAttribute("queryPath", queryPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return PREFIX + "/viewBarcode";
	}
	
	/**
	  * 方法名称：viewQrcode
	  * 功能描述：查看二维码
	  * 创建时间:2016年1月25日下午5:40:08
	  * 创建人: pengfei Zhao
	  * @param @param content
	  * @param @param requset
	  * @param @param response 
	  * @return void
*/
	@RequestMapping("/viewBarcodeImg.action") 
	public void viewQrcode(@RequestParam(value = "id") String id,@RequestParam(value = "queryPath") String queryPath,HttpServletRequest requset, HttpServletResponse response) {
	        EncoderHandler encoder = new EncoderHandler();  
	        encoder.encoderQRCoder(queryPath, response);  
	}

}
