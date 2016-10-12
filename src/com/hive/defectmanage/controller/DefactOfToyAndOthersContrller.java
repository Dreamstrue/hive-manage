package com.hive.defectmanage.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hive.common.SystemCommon_Constant;
import com.hive.defectmanage.entity.DefectRecord;
import com.hive.defectmanage.service.DefectRecordService;
import com.hive.integralmanage.entity.CashPrizeInfo;
import com.hive.tagManage.entity.BlackListBean;
import com.hive.tagManage.entity.SNBase;
import com.hive.tagManage.entity.SNBatch;
import com.hive.tagManage.model.SNBaseVo;
import com.hive.util.PropertiesFileUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;

@Controller
@RequestMapping("/defectOfToyAndOthers")
public class DefactOfToyAndOthersContrller extends BaseController {
	
	private Logger logger = Logger.getLogger(DefactOfToyAndOthersContrller.class);

	@Resource
	private DefectRecordService defectRecordService;
	
	//主要功能页面
	//包括：toy：儿童玩具    other：其他消费品
	private static final String INDEX_PAGE = "defectRecordManage/toyAndOther/index";
	
	//“温馨提示”页面
	//挂参数
	//参数：toy：儿童玩具    other：其他消费品
	private static final String REMINDER_PAGE = "defectRecordManage/toyAndOther/reminder";
	
	//“温馨提示”表单填写页面
	//挂参数
	//参数：toy：儿童玩具    other：其他消费品
	private static final String FORM_PAGE = "defectRecordManage/toyAndOther/form";
	
	//结束页面
	private static final String FINISH_PAGE = "defectRecordManage/toyAndOther/finish";
	//信息列表页面
	private static final String LIST_PAGE = "defectRecordManage/toyAndOther/manage";
	//信息详情页面
	private static final String DETAIL_PAGE = "defectRecordManage/toyAndOther/detail";
	/**
	 * 跳转至主功能页面
	 * 主功能包含：
	 * 1：缺陷产品信息采集
	 * 2：产品质量评价
	 */
	@RequestMapping("/toIndexPage") 
	public String toIndexPage(Model model,@RequestParam(value = "defectType",required=false) String defectType) {
		String butShowStr = "";
		String industryId = "";
		if("toy".equals(defectType)) {
			butShowStr = "儿童玩具";
			industryId = "215";
		} else if("other".equals(defectType)) {
			butShowStr = "其他消费品";
			industryId = "226";
		}
		
		PropertiesFileUtil propertiesFileUtil = new PropertiesFileUtil();
		//取得配置文件配置的上传路径
		String zxt_url = propertiesFileUtil.findValue("zxt_http_url");
		model.addAttribute("zxt_url", zxt_url);
		
		model.addAttribute("defectType",defectType);
		model.addAttribute("industryId",industryId);
		model.addAttribute("butShowStr",butShowStr);
		return INDEX_PAGE; 
	}
	
	/**
	 * 跳转至"温馨提示"页面
	 */
	@RequestMapping("/toReminderPage") 
	public String toReminderPage(Model model,@RequestParam(value = "defectType",required=false) String defectType) {
		String reminderStr = "";
		if("toy".equals(defectType)) {
			reminderStr = "儿童玩具";
		} else if("other".equals(defectType)) {
			reminderStr = "其他消费品";
		}
		model.addAttribute("defectType",defectType);
		model.addAttribute("reminderStr",reminderStr);
		return REMINDER_PAGE;
	}
	
	/**
	 * 跳转至表单填写页面
	 */
	@RequestMapping("/toFormPage") 
	public String toFormPage(Model model,@RequestParam(value = "defectType",required=false) String defectType) {
		String reminderStr = "";
		String peporttype = "";
		if("toy".equals(defectType)) {
			reminderStr = "儿童玩具";
			peporttype = "1"; 
		} else if("other".equals(defectType)) {
			reminderStr = "其他消费品";
			peporttype = "3"; 
		}
		model.addAttribute("peporttype",peporttype);
		model.addAttribute("titleShowStr",reminderStr);
		return FORM_PAGE;
	}
	
	/**
	 * 跳转至表单填写页面
	 */
	@RequestMapping(value = "/formSubmit", method = RequestMethod.POST)
	public ModelAndView formSubmit(DefectRecord defectRecord) {
		logger.info("进入添加方法！");
		defectRecord.setReportdate(new Date());
		Serializable resutl = defectRecordService.insert(defectRecord);
		if(resutl != null) {
			//return success("添加成功！", defectRecord);
			return new ModelAndView("redirect:/defectOfToyAndOthers/toFinishPage.action");
		} else {
			String defectType = "";
			if("3".equals(defectRecord.getPeporttype())) {
				defectType = "other";
			} 
			if("1".equals(defectRecord.getPeporttype())) {
				defectType = "toy";
			} 
			return new ModelAndView("redirect:/defectOfToyAndOthers/toFormPage.action?defectType="+defectType);
		}
		
	}
	
	/**
	 * 结束页面
	 * 说明：信息提交成功后显示的提示页面
	 * @return
	 */
	@RequestMapping("/toFinishPage") 
	public String toFinishPage() {
		return FINISH_PAGE; 
	}
	/**
	 * 功能描述：转到信息列表页面
	 * 创建时间:2015-12-18上午9:26:31
	 * 创建人: pengfei zhao
	 * @return
	 */
	@RequestMapping("/manage") 
	public String manage() {
		return LIST_PAGE; // /manage.jsp
	}
	/**
	 * 
	* @Title: queryList 
	* @Description: TODO(查询玩具和其他产品缺陷列表) 
	* @param @return    设定文件 
	* @return DataGrid    返回类型 
	* @throws
	 */
	@RequestMapping("/queryList.action")
	@ResponseBody
	public DataGrid queryList(@RequestParam("page") int page, @RequestParam("rows") int rows, 
			HttpServletRequest request){
		Map<String, Object> mapParam = new HashMap<String, Object>(); 
		mapParam.put("peporttype", request.getParameter("peporttype"));
		mapParam.put("reportusername", request.getParameter("reportusername"));
		mapParam.put("reportuserphone", request.getParameter("reportuserphone"));
		mapParam.put("prodName", request.getParameter("prodName"));
		DataGrid dg = defectRecordService.queryDefectList(page, rows, mapParam);
		return dg;
	}
	
	
	/**
	 * 查看缺陷详情信息
	 * @param model
	 * @param productId
	 * @return
	 */
	@RequestMapping("/viewDefectRecord.action") 
	public String viewDefectRecord(Model model, @RequestParam(value = "defectId") Long defectId) {
		DefectRecord defectRecord = null;
		try {
			 defectRecord = defectRecordService.get(defectId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("vo", defectRecord);
		return DETAIL_PAGE ;
	}
	/**
	 * 
	 * @Description: 删除缺陷信息
	 * @param id
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(@RequestParam(value="id") Long id){
		DefectRecord defectRecord = defectRecordService.get(id);
		defectRecordService.delete(defectRecord);
		return success("删除成功");
	}
	
}
