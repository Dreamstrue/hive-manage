/**
 * 
 */
package com.hive.integralmanage.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hive.common.AnnexFileUpLoad;
import com.hive.common.SystemCommon_Constant;
import com.hive.evaluationManage.entity.SurveyEvaluation;
import com.hive.evaluationManage.service.EvaluationService;
import com.hive.integralmanage.entity.CashPrizeInfo;
import com.hive.integralmanage.entity.WinPrizeInfo;
import com.hive.integralmanage.service.CashPrizeInfoService;
import com.hive.integralmanage.service.WinPrizeInfoService;
import com.hive.permissionmanage.entity.User;
import com.hive.permissionmanage.service.DepartmentService;
import com.hive.prizemanage.entity.PrizeInfo;
import com.hive.prizemanage.entity.PrizeSupRecord;
import com.hive.prizemanage.model.PrizeInfoBean;
import com.hive.prizemanage.model.PrizeInfoSearchBean;
import com.hive.prizemanage.service.PrizeInfoService;
import com.hive.prizemanage.service.PrizeSupRecordService;
import com.hive.surveymanage.entity.IndustryEntity;
import com.hive.surveymanage.service.IndustryEntityService;
import com.hive.tagManage.entity.SNBase;
import com.hive.tagManage.entity.SNBatch;
import com.hive.tagManage.service.TagSNBaseService;
import com.hive.tagManage.service.TagSNBatchService;
import com.hive.util.DateUtil;
import com.hive.util.PropertiesFileUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**  
 * Filename: PrizeInfoController.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-6  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-6 下午4:15:27				yanghui 	1.0
 */
@Controller
@RequestMapping("cashPrizeInfo")
public class CashPrizeInfoController extends BaseController {

	
	private static final String PREFIX = "prizecashmanage";
	private static final String OBJECT_TABLE = "M_PRIZEINFO";
	private static final String BUSINESS_DIR = SystemCommon_Constant.JPMS_05;
	
	@Resource
	private CashPrizeInfoService cashprizeInfoService;
	
	@Resource
	private PrizeSupRecordService prizeSupRecordService;
	@Resource
	private TagSNBaseService snBaseServise;
	@Resource
	private TagSNBatchService tagSNBatchService;
	@Resource
	private DepartmentService departmentService;
	@Resource
	private WinPrizeInfoService winPrizeInfoService;
	@Resource
	private IndustryEntityService industryEntityService;
	@Resource
	private EvaluationService evaluationService;
	@RequestMapping("manage")
	public String manage(Model model, HttpServletRequest request){
		String createId = request.getParameter("createId");
		String createName = request.getParameter("createName");
		String createOrgId = request.getParameter("createOrgId");
		String createOrgName = request.getParameter("createOrgName");
		model.addAttribute("createId", createId);
		model.addAttribute("createName", createName);
		model.addAttribute("createOrgId", createOrgId);
		model.addAttribute("createOrgName", createOrgName);
		return PREFIX+"/cashPrizeManage";
	}
	
	@RequestMapping("dataGrid")
	@ResponseBody
	public DataGrid dataGrid(RequestPage page,CashPrizeInfo cashPrinfo){
		return cashprizeInfoService.dataGrid(page,cashPrinfo);
	}
	
	
	@RequestMapping("add")
	public String add(Model model, HttpServletRequest request){
		String createId = request.getParameter("createId");
		String createName = request.getParameter("createName");
		String createOrgId = request.getParameter("createOrgId");
		String createOrgName = request.getParameter("createOrgName");
		
		HttpSession session = request.getSession();
		if(null != session.getAttribute("userId")){
			User user = (User)session.getAttribute("user");
			createId = user.getId().toString();
			createName = user.getFullname();
			createOrgId = user.getDepartmentId().toString();
			createOrgName = "质讯通";
		}
		
		model.addAttribute("createId", createId);
		model.addAttribute("createName", createName);
		model.addAttribute("createOrgId", createOrgId);
		model.addAttribute("createOrgName", createOrgName);
		
		return PREFIX+"/cashPrizeAdd";
	}
	
	@RequestMapping("insert")
	@ResponseBody
	public Map<String,Object> insert(CashPrizeInfo info,HttpServletRequest request){
		if(StringUtils.isNotBlank(info.getDj_password())){
			String prizeSN = info.getPrizeSN();
			SNBase snbase = snBaseServise.queryBySN(prizeSN);
			if(snbase==null){
				return error("该SN编号无效");
			}
			boolean flag = cashprizeInfoService.isExistPrizeSN(prizeSN);//TODO 要满足
			if(flag){
				return error("奖品已领取");
			}
			//yyf 20160629 add 增加中奖校验
			WinPrizeInfo winPrizeInfo = winPrizeInfoService.findWinPrizeInfoBySn(prizeSN);
			if(winPrizeInfo.getId()==null){
				return error("此SN码未中奖！");
			}
			//查询中奖信息
			SNBatch sb = tagSNBatchService.get(snbase.getSnBatchId());
			IndustryEntity  ie = industryEntityService.get(Long.valueOf(sb.getIndustryEntityId()));
			info.setCreateId(ie.getId());
			info.setCreateName(ie.getEntityName());
			info.setCreateOrgId(ie.getId().toString());
			info.setCreateOrgName(ie.getEntityName());
			info.setCreateTime(DateUtil.getTimestamp());
			info.setPrizeName(winPrizeInfo.getPrizeName());
			info.setPrizeNum(winPrizeInfo.getPrizeNum());
			info.setPrizePhone(winPrizeInfo.getPrizePhone());
			info.setPrizePlace(winPrizeInfo.getPrizePlace());
			info.setPrizeUser(winPrizeInfo.getPrizeUser());
			info.setRemark(winPrizeInfo.getRemark()==null?"":winPrizeInfo.getRemark());
			info.setValid(SystemCommon_Constant.VALID_STATUS_1); // 默认 1-可用
			info.setWinPrizeInfoId(winPrizeInfo==null?"":winPrizeInfo.getId().toString());
			
			cashprizeInfoService.save(info);
			//修改中奖信息
			winPrizeInfo.setIsCash("1");
			winPrizeInfo.setCashPrizeInfoId(info.getId().toString());
			winPrizeInfoService.update(winPrizeInfo);
			return success("兑奖成功！",info);
		}else{
			String prizeSN = info.getPrizeSN();
			SNBase snbase = snBaseServise.queryBySN(prizeSN);
			if(snbase==null){
				return error("该SN编号无效");
			}
			//判断是否有中奖信息
			//yyf 20160629 add 增加中奖校验
			WinPrizeInfo winPrizeInfo = winPrizeInfoService.findWinPrizeInfoBySn(prizeSN);
			if(winPrizeInfo.getId()==null){
				return error("此SN码未中奖！");
			}else{
				info.setWinPrizeInfoId(winPrizeInfo.getId().toString());
			}
			//判断是否兑过奖
			boolean flag = cashprizeInfoService.isExistPrizeSN(prizeSN);
			if(flag){
				return error("奖品已领取");
			}
			HttpSession session = request.getSession();
			//info.setCreateId((Long) session.getAttribute("userId"));
			info.setCreateTime(DateUtil.getTimestamp());
			info.setValid(SystemCommon_Constant.VALID_STATUS_1); // 默认 1-可用
			
			cashprizeInfoService.save(info);
			//修改中奖信息
			if(winPrizeInfo!=null){
				winPrizeInfo.setIsCash("1");
				winPrizeInfo.setCashPrizeInfoId(info.getId().toString());
				winPrizeInfoService.update(winPrizeInfo);
			}
			return success("保存成功",info);
		}
	}
	/**
	 * 根据中奖信息进行兑奖
	 * 20160629 yyf add
	 * @param info
	 * @param request
	 * @return
	 */
	@RequestMapping("addCashPrizeInfo")
	@ResponseBody
	public Map<String,Object> addCashPrizeInfo(CashPrizeInfo info,HttpServletRequest request){
			WinPrizeInfo winPrizeInfo = new WinPrizeInfo();
			IndustryEntity  ie = new IndustryEntity();
			String winPrizeId = info.getWinPrizeInfoId();
			winPrizeInfo = winPrizeInfoService.get(Long.parseLong(winPrizeId));
			if(winPrizeInfo!=null){
				SurveyEvaluation se = evaluationService.get(Long.parseLong(winPrizeInfo.getSurveyEvaluationId()));
				if(se!=null){
					ie =	industryEntityService.get(Long.parseLong(se.getIndustryEntityId()));
				}
			}
				
			info.setCreateId(ie.getId());
			info.setCreateName(ie.getEntityName());
			info.setCreateOrgId(ie.getId().toString());
			info.setCreateOrgName(ie.getEntityName());
			info.setCreateTime(DateUtil.getTimestamp());
			info.setPrizeName(winPrizeInfo.getPrizeName());
			info.setPrizeNum(winPrizeInfo.getPrizeNum());
			info.setPrizePhone(winPrizeInfo.getPrizePhone());
			info.setPrizePlace(winPrizeInfo.getPrizePlace());
			info.setPrizeUser(winPrizeInfo.getPrizeUser());
			info.setRemark(winPrizeInfo.getRemark()==null?"":winPrizeInfo.getRemark());
			info.setValid(SystemCommon_Constant.VALID_STATUS_1); // 默认 1-可用
			info.setWinPrizeInfoId(winPrizeInfo==null?"":winPrizeInfo.getId().toString());
			
			cashprizeInfoService.save(info);
			//修改中奖信息
			winPrizeInfo.setIsCash("1");
			winPrizeInfo.setCashPrizeInfoId(info.getId().toString());
			winPrizeInfoService.update(winPrizeInfo);
			return success("兑奖成功！",info);
	}
	
	@RequestMapping("edit")
	public String edit(Model model,@RequestParam(value="id") Long id){
		CashPrizeInfo info = cashprizeInfoService.get(id);
		model.addAttribute("vo", info);
		return PREFIX+"/cashPrizeEdit";
	}
	
	
	@RequestMapping("update")
	@ResponseBody
	public Map<String,Object> update(CashPrizeInfo bean,HttpServletRequest request){
		CashPrizeInfo oldInfo = cashprizeInfoService.get(bean.getId()); //原始记录信息 
		
		try {
			PropertyUtils.copyProperties(oldInfo, bean);
			HttpSession session = request.getSession();
			oldInfo.setModifyId((Long) session.getAttribute("userId"));
			oldInfo.setModifyTime(DateUtil.getTimestamp());
			cashprizeInfoService.update(oldInfo);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("属性拷贝错误");
		} 
		return success("修改成功",oldInfo);
	}
	
	/**
	 * 
	 * @Description: 删除奖品信息
	 * @author yanghui 
	 * @Created 2014-3-24
	 * @param id
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(@RequestParam(value="id") Long id){
		
		//首先删除选择的奖品信息（非物理删除） 修改是否可用字段valid为0
		CashPrizeInfo pinfo = cashprizeInfoService.get(id);
		pinfo.setValid(SystemCommon_Constant.VALID_STATUS_0); 
		cashprizeInfoService.update(pinfo);
		//删除对应的补货 记录
		prizeSupRecordService.updatePrizeSubRecord(id);
		return success("删除成功");
	}
	
	
	
}
