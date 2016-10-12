package com.hive.enterprisemanage.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hive.common.AnnexFileUpLoad;
import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Annex;
import com.hive.common.service.AnnexService;
import com.hive.enterprisemanage.entity.MTradeinfo;
import com.hive.enterprisemanage.service.TradeInfoService;
import com.hive.util.DateUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;
/**
 * 
* Filename: MTradeInfoController.java  
* Description:  供求关系控制类
* Copyright:Copyright (c)2013
* Company:  GuangFan 
* @author:  yanghui
* @version: 1.0  
* @Create:  2013-10-30  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2013-10-30 下午3:00:14				yanghui 	1.0
 */
@Controller
@RequestMapping("tradeInfo")
public class TradeInfoController extends BaseController {
	
	public static final String PREFIX = "enterprisemanage/tradeinfo";
	public static final String OBJECT_TABLE = "M_TRADEINFO";
	public static final String BUSINESS_DIR = SystemCommon_Constant.ENT_CPGQ_07;
	
	@Resource
	private TradeInfoService tradeInfoService;
	@Resource
	private AnnexService annexService;
	
	
	
	@RequestMapping("/manage")
	public String manage(){
		return PREFIX+"/manage";
	}
	
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(RequestPage page,@RequestParam(value="keys",required=false,defaultValue="")String keys,HttpServletRequest request){
		String status = request.getParameter("status");
		status=StringUtils.isBlank(status)?"0":status;
		return tradeInfoService.dataGrid(page,keys,status);
	}
	
	@RequestMapping("/add")
	public String add(){
		return PREFIX+"/add";
	}
	
	
	
	@RequestMapping("/insert")
	@ResponseBody
	public Map<String, Object> insert(MTradeinfo info, HttpSession session,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		info.setNcreateid(Long.valueOf((String) session.getAttribute("userId")));
		info.setDcreatetime(DateUtil.getTimestamp());
		info.setNmemberid(Long.valueOf((String) session.getAttribute("userId")));//企业ID
		info.setCauditstatus(SystemCommon_Constant.AUDIT_STATUS_0);
		info.setCvalid(SystemCommon_Constant.VALID_STATUS_1);
		
		tradeInfoService.save(info);
		
		List<Annex> nexlist = AnnexFileUpLoad.uploadFile(files, session, info.getId(), OBJECT_TABLE,BUSINESS_DIR, null);
		annexService.saveAnnexList(nexlist,null);
		
		
		return success("添加成功",info);
	}
	
	@RequestMapping("/approve")
	public String approve(Model model,@RequestParam(value="id") Long id){
		model.addAttribute("vo", tradeInfoService.get(id));
		//取得该记录对应的附件信息
		List<Annex> list = annexService.getAnnexInfoByObjectId(id,OBJECT_TABLE);
		model.addAttribute("annex", list);
		model.addAttribute("type", "approve");
		return PREFIX+"/edit";
	}
	
	
	@RequestMapping("/approveAction")
	@ResponseBody
	public Map<String,Object> approveAction(HttpSession session,@RequestParam(value="id") Long id,@RequestParam(value="cauditopinion",required=false,defaultValue="") String cauditopinion,@RequestParam(value="type") String type){
		MTradeinfo info = tradeInfoService.get(id);
		info.setNauditid((Long)session.getAttribute("userId"));
		info.setDaudittime(DateUtil.getTimestamp());
		
		String msg = "";
		if(type.equals("success")){
			info.setCauditstatus(SystemCommon_Constant.AUDIT_STATUS_1);
			msg = "审核通过";
		}else {
			info.setCauditstatus(SystemCommon_Constant.AUDIT_STATUS_2);
			msg ="审核不通过";
		}
		
		info.setCauditopinion(cauditopinion);
		
		tradeInfoService.update(info);
		
		return success(msg,info);
	}
	
	
	
	
	@RequestMapping("/detail")
	public String detail(Model model,@RequestParam(value="id") Long id){
		model.addAttribute("vo", tradeInfoService.get(id));
		//取得该记录对应的附件信息
		List<Annex> list = annexService.getAnnexInfoByObjectId(id,OBJECT_TABLE);
		model.addAttribute("annex", list);
		model.addAttribute("type", "detail");
		return PREFIX+"/edit";
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam(value = "id") String id) {
		if (StringUtils.isNotEmpty(id)) {
			MTradeinfo inte = tradeInfoService.get(Long.valueOf(id));
			inte.setCvalid(SystemCommon_Constant.VALID_STATUS_0);
			tradeInfoService.update(inte);
			return success("删除成功！");
		} else
			return error("请选择需要删除的对象");
	}
	

}
