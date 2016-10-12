package com.hive.qrcode.controller;


import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hive.common.SystemCommon_Constant;
import com.hive.common.service.SequenceService;
import com.hive.qrcode.entity.QRCode;
import com.hive.qrcode.entity.QRCodeIssue;
import com.hive.qrcode.model.QRCodeIssueDetailVo;
import com.hive.qrcode.model.QRCodeIssueVo;
import com.hive.qrcode.model.QRCodeVo;
import com.hive.qrcode.service.QRCodeIssueDetailService;
import com.hive.qrcode.service.QRCodeIssueService;
import com.hive.qrcode.service.QRCodeService;
import com.hive.surveymanage.entity.IndustryEntity;
import com.hive.surveymanage.service.IndustryEntityService;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

@Controller
@RequestMapping("qrcodeIssue")
public class QRCodeIssueController extends BaseController {

	private static final String PREFIX = "qrcode/issue";

	@Resource
	private QRCodeIssueService qrcodeIssueService;
	@Resource
	private QRCodeIssueDetailService qrcodeIssueDetailService;
	@Resource
	private SequenceService sequenceService;
	@Resource
	private IndustryEntityService industryEntityService;
	@Resource
	private QRCodeService qrcodeService;

	/**
	 * 跳转到二维码发放管理页面
	 */
	@RequestMapping("toQrcodeIssueManage")
	public String toQrcodeIssueManage() {
		return PREFIX + "/qrcodeIssueManage";
	}
	/**
	 * 跳转到添加二维码发放页面
	 */
	@RequestMapping("toQrcodeIssueAdd")
	public String toQrcodeIssueAdd() {
		return PREFIX + "/qrcodeIssueAdd";
	}
	/**
	 * 跳转到手工录入页面
	 */
	@RequestMapping("toQrcodeIssueManual")
	public String toQrcodeIssueManual(Model model,@RequestParam(value = "issueId",required=true)  String issueId) {
		model.addAttribute("issueId", issueId);
		return PREFIX + "/qrcodeIssueDetailAdd_manual";
	}
	/**
	 * 跳转发放明细的二维码列表页面
	 */
	@RequestMapping("lookUpIssueDetailQrcodeList")
	public String lookUpIssueDetailQrcodeList(ModelAndView mav ,@RequestParam(value = "issueDetailId")  String issueDetailId) {
		mav.addObject("issueDetailId",issueDetailId);
		return PREFIX + "/tagList";
	}
	/**
	 * 跳转到添加二维码发放明细页面
	 */
	@RequestMapping("toQrcodeIssueDetailAdd")
	public String toQrcodeIssueDetailAdd() {
		return PREFIX + "/qrcodeIssueDetailAdd";
	}
	/**
	 * 添加二维码发放
	 */
	@RequestMapping("/qrcodeIssueAdd")
	@ResponseBody
	public Object qrcodeIssueAdd(QRCodeIssueVo qrcodeIssueVo,HttpServletRequest request) {
		QRCodeIssue ci = new QRCodeIssue();
		Long userId = (Long)request.getSession().getAttribute("userId");
		try {
			PropertyUtils.copyProperties(ci, qrcodeIssueVo);
			ci.setCreater(userId.toString());
			ci.setCreateTime(new Date());
			String number = sequenceService.createSequenceBySeqType(SystemCommon_Constant.SEQUENCE_EWMFP);
			ci.setNumber(number);
			qrcodeIssueService.save(ci);
		} catch (Exception e) {
			return error("添加二维码发放失败！");
		}
		
		return success("添加成功！");
	}
	/**
	 * 添加二维码绑定（临时解决方案）
	 */
	@RequestMapping("/qrcodeIssueTemporaryAdd")
	@ResponseBody
	public Object qrcodeIssueTemporaryAdd(QRCodeIssueVo qrcodeIssueVo,@RequestParam(value = "qrcodeId",required=true)  String qrcodeId,HttpServletRequest request) {
		//获取二维码信息
		QRCode qrc = qrcodeService.get(qrcodeId);
		if(qrc==null){
			return error("二维码信息有误！请核实！");
		}else{
			//校验是否已经绑定过
			if(qrc.getQrcodeStatus().equals(SystemCommon_Constant.QRCode_status_4)){
				return error("此二维码已经绑定过，请勿重复绑定！");
			}
		}
		QRCodeIssue ci = new QRCodeIssue();
		Long userId = (Long)request.getSession().getAttribute("userId");
		IndustryEntity  ie = null;
		if(StringUtils.isNotBlank(qrcodeIssueVo.getEntityId())){
			ie = industryEntityService.get(Long.parseLong(qrcodeIssueVo.getEntityId()));
		}else{
			return error("二维码信息有误！请核实！");
		}
		try {
			PropertyUtils.copyProperties(ci, qrcodeIssueVo);
			ci.setCreater(userId==null?"":userId.toString());
			ci.setCreateTime(new Date());
			ci.setEntityName(ie.getEntityName());
			ci.setLinkAddress(ie.getAddress());
			ci.setLinkPerson(ie.getLinkMan());
			ci.setLinkPhone(ie.getLinkPhone());
			String number = sequenceService.createSequenceBySeqType(SystemCommon_Constant.SEQUENCE_EWMFP);
			ci.setNumber(number);
			qrcodeIssueService.save(ci);
			qrcodeIssueVo.setId(ci.getId());
			/*
			 * 添加发放明细
			 */
			QRCodeVo qrcvo = new QRCodeVo();
			qrcvo.setId(qrc.getId());
			qrcvo.setIssueId(ci.getId());
			qrcvo.setQrcodeType(ci.getQrcodeType());
			qrcvo.setContent(ci.getQrcodeContent());
			qrcodeIssueService.qrcodeIssueDetailAdd_manual(qrcvo,userId);
			
			//执行发放操作
			if(qrcodeIssueService.issueQrcode(qrcodeIssueVo)){
				System.out.println("执行发放记录id为"+qrcodeIssueVo.getId()+"的发放操作！");
			}
		} catch (Throwable e) {
			return error("绑定失败！");
		}
		
		return success("绑定成功！！");
	}
	/**
	 * 手工录入发放二维码明细
	 */
	@RequestMapping("/qrcodeIssueAdd_manual")
	@ResponseBody
	public Object qrcodeIssueAdd_manual(QRCodeVo qrcodeVo,HttpServletRequest request) {
		Long userId = (Long)request.getSession().getAttribute("userId");
		try {
			qrcodeIssueService.qrcodeIssueDetailAdd_manual(qrcodeVo,userId);
		} catch (Exception e) {
			e.printStackTrace();
			return error("添加发放明细失败");
		}

		
		return success("添加成功！");
	}
	/**
	 * 添加二维码发放
	 */
	@RequestMapping("/qrcodeIssueDetailAdd")
	@ResponseBody
	public Object qrcodeIssueDetailAdd(QRCodeIssueDetailVo cidVo,HttpServletRequest request) {
		Long userId = (Long)request.getSession().getAttribute("userId");
		String ids = cidVo.getDetailQrcodeIds();
		String[] idsArr = ids.split(",");
		int count = idsArr.length;
		try {
			qrcodeIssueService.qrcodeIssueDetailAdd(cidVo,userId,count,idsArr);
		} catch (Exception e) {
			e.printStackTrace();
			return error("添加发放明细失败");
		}

		
		return success("添加成功！");
	}
	/**
	 * 执行二维码发放命令
	 */
	@RequestMapping("/issueQrcode")
	@ResponseBody
	public Object issueQrcode(QRCodeIssueVo cidVo,HttpServletRequest request) {
		try {
			boolean flag = qrcodeIssueService.issueQrcode(cidVo);
			if(!flag){
				return error("请先添加发放明细！");
			}
		} catch (Throwable e) {
			e.printStackTrace();
			return error("执行发放失败！请联系管理员处理！");
		}

		
		return success("发放成功！");
	}
	/**
	 * 二维码发放管理列表数据
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public DataGrid datagrid(RequestPage page, QRCodeIssueVo qrcodeIssueVo,HttpServletRequest request) {
		return qrcodeIssueService.datagrid(page, qrcodeIssueVo);
	}
	/**
	 * 二维码发放明细管理列表数据
	 */
	@RequestMapping("/datagridForDetail")
	@ResponseBody
	public DataGrid datagridForDetail(RequestPage page, QRCodeIssueDetailVo vo,HttpServletRequest request) {
		return qrcodeIssueDetailService.datagrid(page, vo);
	}
}
