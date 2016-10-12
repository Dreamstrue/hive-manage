package com.hive.qrcode.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.common.SystemCommon_Constant;
import com.hive.permissionmanage.entity.User;
import com.hive.permissionmanage.service.UserService;
import com.hive.qrcode.entity.QRCode;
import com.hive.qrcode.entity.QRCodeBatch;
import com.hive.qrcode.entity.QRCodeIssueDetail;
import com.hive.qrcode.model.QRCodeBatchVo;
import com.hive.qrcode.model.QRCodeIssueDetailVo;
import com.hive.qrcode.model.QRCodeVo;
import com.hive.qrcode.service.QRCodeBatchService;
import com.hive.qrcode.service.QRCodeIssueDetailService;
import com.hive.qrcode.service.QRCodeService;
import com.hive.surveymanage.service.IndustryEntityService;
import com.hive.util.PropertiesFileUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

@Controller
@RequestMapping("qrcode")
public class QRCodeController extends BaseController {

	private static final String PREFIX = "qrcode";

	@Resource
	private QRCodeService qrcodeService;
	@Resource
	private QRCodeIssueDetailService qrcodeIssueDetailService;
	@Resource
	private QRCodeBatchService qrcodeBatchService;
	@Resource
	private UserService userService;
	@Resource
	private IndustryEntityService industryEntityService;
	/**
	 * 某批次下的所有二维码
	 */
	@RequestMapping("/findQrcodeGrid")
	@ResponseBody
	public DataGrid findQrcodeGrid(RequestPage page,HttpServletRequest request) {
		String batchId = request.getParameter("batchId");
		QRCodeVo qrcodeVo = new QRCodeVo();
		qrcodeVo.setQrcodeBatchId(batchId);
		return qrcodeService.datagrid(page,qrcodeVo);
	}
	/**
	 * 跳转到二维码管理页面
	 */
	@RequestMapping("toQrcodeManage")
	public String toQrcodeManage() {
		return PREFIX + "/qrcodeManage";
	}
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
	 * 二维码管理列表数据
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public DataGrid datagrid(RequestPage page, QRCodeVo qrcodeVo,HttpServletRequest request) {
		return qrcodeService.datagrid(page, qrcodeVo);
	}
	/**
	 * 加载发放的二维码列表数据
	 */
	@RequestMapping("/datagridForIssue")
	@ResponseBody
	public DataGrid datagridForIssue(RequestPage page, QRCodeIssueDetailVo vo,HttpServletRequest request) {
		String pid = vo.getPid();
		QRCodeVo qrcodeVo = new QRCodeVo();
		if(StringUtils.isNotBlank(pid)){
			//获取到所有的发放明细
			List<QRCodeIssueDetail>  list = qrcodeIssueDetailService.findIssueDetailByPid(pid);
			String ids="";
			for(QRCodeIssueDetail cid : list){
				ids = ids+"'"+cid.getId()+"',";
			}
			if(ids.length()>0){
				ids = ids.substring(0, ids.length()-1);
			}
			if(StringUtils.isNotBlank(ids)){
				//获取ids下的所有的二维码
				qrcodeVo.setIssueDetailIds(ids);
				return qrcodeService.datagrid(page, qrcodeVo);
			}
		}
		return new DataGrid(0, new ArrayList<QRCodeVo>());
	}
	/**
	 * 更新二维码数据
	 */
	@RequestMapping("/updateQrcode")
	@ResponseBody
	public Object updateQrcode(QRCodeVo qrcodeVo,HttpServletRequest request) {
		String id = qrcodeVo.getId();
		if(StringUtils.isNotBlank(id)){
			QRCode cr = qrcodeService.get(id);
			String detailId = cr.getIssueDetailId();
			cr.setIssueDetailId("");
			cr.setQrcodeType("");
			cr.setContent("");
			cr.setEntityId("");
			cr.setQrcodeStatus(SystemCommon_Constant.QRCode_status_2);
			qrcodeService.update(cr);
			//修改发放明细的数量
			QRCodeIssueDetail cid = qrcodeIssueDetailService.get(detailId);
			cid.setAmount(cid.getAmount()-1);
			qrcodeIssueDetailService.update(cid);
			//更新批次的有效数量
			QRCodeBatch cb = qrcodeBatchService.findBatchByBatchNumber(cid.getBatchNumber());
			cb.setValidAmount(cb.getValidAmount()+1);
			qrcodeBatchService.update(cb);
			return success("删除成功！");
		}else{
			return error("参数异常，请联系管理员处理！");
		}
	}
	/**
	 * 检验二维码数据
	 */
	@RequestMapping("/checkQrcode")
	@ResponseBody
	public Object checkQrcode(QRCodeVo qrcodeVo,HttpServletRequest request) {
		try {
			//获取二维码编号
			QRCodeVo vo = new QRCodeVo();
			String num = qrcodeVo.getQrcodeNumber();
			if(StringUtils.isNotBlank(num)){
				QRCode cre = qrcodeService.findQRCodeByNum(num,SystemCommon_Constant.QRCode_status_2);
				if(cre==null){
					return error("此二维码不存在或已经发放，请核对后重新录入！");
				}else{
					PropertyUtils.copyProperties(vo, cre);
					if(StringUtils.isNotBlank(cre.getQrcodeBatchId())){
						QRCodeBatch cb = qrcodeBatchService.get(cre.getQrcodeBatchId());
						QRCodeBatchVo cbv = new QRCodeBatchVo();
						PropertyUtils.copyProperties(cbv, cb);
						vo.setQrcodeBatchVo(cbv);
					}
					vo.setQrcodeStatus("已印刷");
					
				}
					
					return success("",vo);
			}else{
				return error("此二维码不存在，请核对后重新录入！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return error("操作异常，请联系管理员处理！");
		}
	}
	/**
	 * 查看二维码详情信息
	 * @param model
	 * @param qrcodeId
	 * @return
	 */
	@RequestMapping("/viewQrcodeInfo") 
	public String viewQrcodeInfo(Model model, @RequestParam(value = "qrcodeId", required = true) String id) {
		QRCodeVo vo = new QRCodeVo();
		QRCode qrc = qrcodeService.get(id);
		try {
			PropertyUtils.copyProperties(vo, qrc);
			QRCodeBatch batch = qrcodeBatchService.get(qrc.getQrcodeBatchId());
			QRCodeBatchVo batchVo = new QRCodeBatchVo();
			PropertyUtils.copyProperties(batchVo, batch);
			User user = userService.get(Long.parseLong(batch.getCreater()));
			batchVo.setCreaterName(user.getFullname());
			if(StringUtils.isNotBlank(qrc.getEntityId()))
			vo.setIndustryEntity(industryEntityService.get(Long.parseLong(qrc.getEntityId())));
//			if(StringUtils.isNotBlank(qrc.getSnbaseId()))
//			vo.setSnBaseInfo(snBaseService.get(qrc.getSnbaseId()));
			vo.setQrcodeBatchVo(batchVo);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		model.addAttribute("vo", vo);
		
		return PREFIX + "/viewQrcode";
	}
	/**
	 * 跳转修改二维码content内容页面
	 * @param model
	 * @param qrcodeId
	 * @return
	 */
	@RequestMapping("/toEditQrcodeContent") 
	public String editQrcodeContent(Model model, @RequestParam(value = "qrcodeId", required = true) String id) {
		QRCodeVo vo = new QRCodeVo();
		QRCode qrc = qrcodeService.get(id);
		try {
			PropertyUtils.copyProperties(vo, qrc);
			QRCodeBatch batch = qrcodeBatchService.get(qrc.getQrcodeBatchId());
			QRCodeBatchVo batchVo = new QRCodeBatchVo();
			PropertyUtils.copyProperties(batchVo, batch);
			User user = userService.get(Long.parseLong(batch.getCreater()));
			batchVo.setCreaterName(user.getFullname());
			if(StringUtils.isNotBlank(qrc.getEntityId()))
				vo.setIndustryEntity(industryEntityService.get(Long.parseLong(qrc.getEntityId())));
//			if(StringUtils.isNotBlank(qrc.getSnbaseId()))
//			vo.setSnBaseInfo(snBaseService.get(qrc.getSnbaseId()));
			vo.setQrcodeBatchVo(batchVo);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		model.addAttribute("vo", vo);
		
		return PREFIX + "/editQrcodeContent";
	}
	/**
	 * 修改二维码content内容
	 */
	@RequestMapping("/editQrcodeContent")
	@ResponseBody
	public Object editQrcodeContent(QRCodeVo qrcodeVo,HttpServletRequest request) {
		try {
			if(StringUtils.isNotBlank(qrcodeVo.getId())){
				QRCode cre = qrcodeService.get(qrcodeVo.getId());
				if(cre==null){
					return error("此二维码不存在，请核对后重新录入！");
				}else{
					cre.setContent(qrcodeVo.getContent());
					qrcodeService.update(cre);
					return success("修改成功！");
				}
			}else{
				return error("此二维码不存在，请核对后重新录入！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return error("操作异常，请联系管理员处理！");
		}
	}
	/**
	 * 跳转到二维码调度页面
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/qrcodeDispatcher") 
	public String qrcodeDispatcher(Model model, @RequestParam(value = "id", required = true) String id) {
		QRCodeVo vo = new QRCodeVo();
		vo.setShowSimpleQrcodeUrl("/qrcode/toShowSimpleQrcodeInfo.action?qrcodeId="+id);
		QRCode qrc = qrcodeService.get(id);
		try {
			PropertyUtils.copyProperties(vo, qrc);
			QRCodeBatch batch = qrcodeBatchService.get(qrc.getQrcodeBatchId());
			QRCodeBatchVo batchVo = new QRCodeBatchVo();
			PropertyUtils.copyProperties(batchVo, batch);
			User user = userService.get(Long.parseLong(batch.getCreater()));
			batchVo.setCreaterName(user.getFullname());
			if(StringUtils.isNotBlank(qrc.getEntityId()))
				vo.setIndustryEntity(industryEntityService.get(Long.parseLong(qrc.getEntityId())));
//			if(StringUtils.isNotBlank(qrc.getSnbaseId()))
//			vo.setSnBaseInfo(snBaseService.get(qrc.getSnbaseId()));
			vo.setQrcodeBatchVo(batchVo);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		model.addAttribute("vo", vo);
		if(StringUtils.isBlank(qrc.getQrcodeType())&&qrc.getQrcodeType()!="1"){
			return PREFIX + "/showSimpleQrcode";
		}else{
			return PREFIX + "/qrcodeDispatcher";
		}
	}
	/**
	 * 跳转到二维码信息的页面（在二维码没有绑定企业之前的页面显示）
	 * @param model
	 * @param qrcodeId
	 * @return
	 */
	@RequestMapping("/toShowSimpleQrcodeInfo") 
	public String toShowSimpleQrcodeInfo(Model model, @RequestParam(value = "qrcodeId", required = true) String id) {
		QRCodeVo vo = new QRCodeVo();
		QRCode qrc = qrcodeService.get(id);
		try {
			PropertyUtils.copyProperties(vo, qrc);
			QRCodeBatch batch = qrcodeBatchService.get(qrc.getQrcodeBatchId());
			QRCodeBatchVo batchVo = new QRCodeBatchVo();
			PropertyUtils.copyProperties(batchVo, batch);
			User user = userService.get(Long.parseLong(batch.getCreater()));
			batchVo.setCreaterName(user.getFullname());
			if(StringUtils.isNotBlank(qrc.getEntityId()))
			vo.setIndustryEntity(industryEntityService.get(Long.parseLong(qrc.getEntityId())));
//			if(StringUtils.isNotBlank(qrc.getSnbaseId()))
//			vo.setSnBaseInfo(snBaseService.get(qrc.getSnbaseId()));
			vo.setQrcodeBatchVo(batchVo);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		model.addAttribute("vo", vo);
		
		return PREFIX + "/showSimpleQrcode";
	}
	/**
	 * 跳转到二维码绑定的页面（二维码绑定的临时解决方案）
	 * yyf 20160823 add
	 * @param model
	 * @param qrcodeId
	 * @return
	 */
	@RequestMapping("/toBindQrcode") 
	public String toBindQrcode(Model model, @RequestParam(value = "qrcodeId", required = true) String id) {
		QRCodeVo vo = new QRCodeVo();
		QRCode qrc = qrcodeService.get(id);
		try {
			PropertyUtils.copyProperties(vo, qrc);
			QRCodeBatch batch = qrcodeBatchService.get(qrc.getQrcodeBatchId());
			QRCodeBatchVo batchVo = new QRCodeBatchVo();
			PropertyUtils.copyProperties(batchVo, batch);
			User user = userService.get(Long.parseLong(batch.getCreater()));
			batchVo.setCreaterName(user.getFullname());
			if(StringUtils.isNotBlank(qrc.getEntityId()))
				vo.setIndustryEntity(industryEntityService.get(Long.parseLong(qrc.getEntityId())));
//			if(StringUtils.isNotBlank(qrc.getSnbaseId()))
//			vo.setSnBaseInfo(snBaseService.get(qrc.getSnbaseId()));
			vo.setQrcodeBatchVo(batchVo);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		model.addAttribute("vo", vo);
		
		return PREFIX + "/bindQrcode";
	}
	/**
	 * 20160823 yyf add
	 * 临时二维码绑定解决方案中的密码校验
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkPassword")
	@ResponseBody
	public Object checkPassword(@RequestParam(value = "password", required = true) String password, HttpServletRequest request){
		
		PropertiesFileUtil pfu = new PropertiesFileUtil();
		String pw = pfu.findValue("qrcode_bind_password");
		if(pw.equals(password)){
			return success("校验通过！",password);
		}else{
			return error("您输入的密码有误，请核对后重新输入！");
		}
	}
}
