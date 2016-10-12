package com.hive.discloseInfo.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Annex;
import com.hive.common.service.AnnexService;
import com.hive.discloseInfo.entity.DiscloseInfo;
import com.hive.discloseInfo.service.DiscloseService;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

@Controller
@RequestMapping("/discloseManage")
public class DiscloseController extends BaseController{
	private final String PREFIX="disclosemanage";
	
	@Resource
	private DiscloseService discloseService;
	@Resource
	private AnnexService annexService;
	
	@RequestMapping("/toDiscloseList")
	public String toDiscloseList(){
		return PREFIX + "/discloseList";
	}
	
	@RequestMapping("/listDisclose")
	@ResponseBody
	public DataGrid listSurvey(RequestPage page,String content,@RequestParam(value = "begintime", required = false)Date begintime,@RequestParam(value = "endtime", required = false)Date endtime){
		return discloseService.datagrid(page,content,begintime,endtime);
	}
	@RequestMapping("/checkDiscloseInfo")
	@ResponseBody
	public Object checkDiscloseInfo(HttpSession session, HttpServletRequest request){
		 discloseService.updateDiscloseInfo();
		 
		 return success("审核成功");
	}
	
	
	@RequestMapping("approve")
	public String approve(Model model,@RequestParam(value="id") Long id){
		DiscloseInfo info = discloseService.get(id);
		model.addAttribute("vo",info);
		List<Annex> list = annexService.getAnnexInfoByObjectId(id, "M_DISCLOSE");
		model.addAttribute("list",list);
		
		return PREFIX+"/edit";
	}
	
	
	@RequestMapping("/approveAction")
	@ResponseBody
	public Object approveAction(@RequestParam(value="id") Long id){
		DiscloseInfo info = discloseService.get(id);
		info.setAuditStatus(SystemCommon_Constant.AUDIT_STATUS_1);
		
		 discloseService.update(info);
		 
		 return success("审核成功");
	}
	
	
	
	
	
	@RequestMapping("/deleteDiscloseInfo")
	@ResponseBody
	public Map<String,Object>deleteDiscloseInfo(@RequestParam("discId") Long id){
		DiscloseInfo dis=discloseService.get(id);
		if(dis !=null){
			dis.setShieldStatus(SystemCommon_Constant.REVIEW_SHIELD_STATUS_1);
			discloseService.update(dis);
			return success("屏蔽成功");
		}else{
			return error("屏蔽失败");
		}
	}
	
	
	@RequestMapping("/backDiscloseInfo")
	@ResponseBody
	public Map<String,Object>backDiscloseInfo(@RequestParam("discId") Long id){
		DiscloseInfo dis=discloseService.get(id);
		if(dis !=null){
			dis.setShieldStatus(SystemCommon_Constant.REVIEW_SHIELD_STATUS_0);
			discloseService.update(dis);
			return success("恢复成功");
		}else{
			return error("恢复失败");
		}
	}
}
