package com.hive.qrcode.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Sequence;
import com.hive.common.service.SequenceService;
import com.hive.qrcode.ViewExcelOfQRCode;
import com.hive.qrcode.entity.QRCode;
import com.hive.qrcode.entity.QRCodeBatch;
import com.hive.qrcode.model.QRCodeBatchVo;
import com.hive.qrcode.service.QRCodeBatchService;
import com.hive.qrcode.service.QRCodeService;
import com.hive.tagManage.entity.SNBase;
import com.hive.tagManage.service.TagSNBaseService;
import com.hive.tagManage.service.ViewExcelOfSN;
import com.hive.util.PropertiesFileUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

@Controller
@RequestMapping("qrcodeBatch")
public class QRCodeBatchController extends BaseController {

	private static final String PREFIX = "qrcode/batch";

	@Resource
	private QRCodeBatchService qrcodeBatchService;
	@Resource
	private QRCodeService qrcodeService;
	
	@Resource
	private SequenceService sequenceService;
	@Resource
	private TagSNBaseService tagSNBaseService;
	
	
	/**
	 * 跳转到二维码批次管理页面
	 */
	@RequestMapping("toQrcodeBatchManage")
	public String manage() {
		return PREFIX + "/qrcodeBatchManage";
	}
	
	/**
	 * 二维码批次管理列表数据
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public DataGrid datagrid(RequestPage page, QRCodeBatchVo qrcodeBatchVo,HttpServletRequest request) {
		return qrcodeBatchService.datagrid(page, qrcodeBatchVo);
	}
	/**
	 * 跳转到添加二维码批次页面
	 */
	@RequestMapping("toQrcodeBatchAdd")
	public String toQrcodeBatchAdd(Model model,@RequestParam(value="seqType",required=true) String seqType) {
		String batchNum = sequenceService.createSequenceBySeqType(seqType);
		if(batchNum==null){
			throw new RuntimeException("不存在seq_type类型为"+seqType+"的序列号，请核实！");
		}
		model.addAttribute("batchNum", batchNum);
		return PREFIX + "/qrcodeBatchAdd";
	}
	/**
	 * 添加二维码批次
	 */
	@RequestMapping("qrcodeBatchAdd")
	@ResponseBody
	public Object qrcodeBatchAdd(QRCodeBatchVo batchVo,HttpServletRequest request) {
		HttpSession session = request.getSession();
		Long userId = (Long)session.getAttribute("userId");
		synchronized(this) {
			try {
				
				QRCodeBatch qrcodeBatch = new QRCodeBatch();
				PropertyUtils.copyProperties(qrcodeBatch, batchVo);
				if(StringUtils.isNotBlank(String.valueOf(userId))){
					qrcodeBatch.setCreater(String.valueOf(userId));
				}else{
					return error("您不能进行此项操作，请联系质保通管理员处理！");
				}
				qrcodeBatch.setCreateTime(new Date());
				qrcodeBatch.setValidAmount(0);//初始有效数量为0，当批次处于已印刷状态时，才不为0
				qrcodeBatchService.save(qrcodeBatch);
				Integer count = qrcodeBatch.getAmount();
				String digitNumStr = batchVo.getDigitNumber();
				int digitNumLen = digitNumStr.length();
				//创建二维码
				for (int i = 0; i < count; i++) {
					Integer digitNum = Integer.parseInt(digitNumStr);
					digitNumStr = digitNumStr.replace(digitNum+"", i+1+"");
					digitNumStr = digitNumStr.substring(digitNumStr.length()-digitNumLen);
					QRCode cre = new QRCode();
					Long sn = tagSNBaseService.getPK(i);//20190929 yyf edit 加上i
					cre.setSn(sn.toString());
					cre.setQrcodeNumber(batchVo.getBatchNumber()+digitNumStr);
					cre.setQrcodeType("");
					cre.setContent("");
					cre.setQrcodeBatchId(qrcodeBatch.getId());
					cre.setQrcodeStatus(batchVo.getStatus().toString());
					qrcodeService.save(cre);
					PropertiesFileUtil propertiesFileUtil = new PropertiesFileUtil();
					String QRCODE_PATH_ = propertiesFileUtil.findValue("QRCODE_DISPATCHER_PATH");
					cre.setQrcodeValue(QRCODE_PATH_+cre.getId());
					qrcodeService.update(cre);
				}
				return success("创建批次成功！");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			return error("保存二维码批次信息异常！");
		}
	}
	/**
	 * 添加二维码批次
	 */
	@RequestMapping("qrcodeBatchNumber")
	@ResponseBody
	public Object qrcodeBatchNumber(@RequestParam(value="seqCode",required=true) String seqCode) {
		if(StringUtils.isNotBlank(seqCode)){
			Sequence seq = sequenceService.findSequenceBySeqCode(seqCode);
			if(seq==null){
				throw new RuntimeException("seq_code为"+seqCode+"的序列号不存在或数据冲突，请核实！");
			}else{
				String batchNum = sequenceService.createSequenceBySeqType(seq.getSeqType());
				QRCodeBatchVo cb = new QRCodeBatchVo();
				cb.setBatchNumber(batchNum);
				cb.setDigitNumber(seq.getDigitNumber());
				return success("", cb);
			}
		}else{
			throw new RuntimeException("数据有误，请核实！");
		}
	}
	/**
	 * 跳转查看二维码列表页面
	 */
	@RequestMapping("lookUpList")
	public String lookUpList(ModelAndView mav ,@RequestParam(value = "batchId")  String batchId,@RequestParam(value = "qrcodeType")  String qrcodeType) {
		mav.addObject("batchId",batchId);
		mav.addObject("qrcodeType",qrcodeType);
		return PREFIX + "/tagList";
	}
	/**
	 * 修改二维码印刷状态
	 */
	@RequestMapping("/toEditStatus")
	public String toEditStatus(Model model,@RequestParam(value = "id") String id) {
		synchronized(this) {
			model.addAttribute("vo",qrcodeBatchService.get(id));
			return PREFIX + "/editStatus";
		}
	}
	/**
	 * 修改二维码批次印刷状态
	 */
	@RequestMapping("/editStatus")
	@ResponseBody
	public Object editStatus(HttpServletRequest request,QRCodeBatchVo qrcodeBatchVo) {
		try {
				Integer status = qrcodeBatchVo.getStatus();
				QRCodeBatch cb=qrcodeBatchService.get(qrcodeBatchVo.getId());
				if(status!=null){
					cb.setStatus(status);//更改状态为
					List<QRCode> list = new ArrayList<QRCode>();
					if(status==0){
						//当修改批次状态为未生效时，获取空闲状态的二维码
						list = qrcodeService.findQrcodeByBatchIdAndStatus(qrcodeBatchVo.getId(),SystemCommon_Constant.QRCode_status_2);
						//修改批次有效数量
						cb.setValidAmount(cb.getValidAmount()-list.size());
					}else if(status==2){
						//当修改批次状态为占用时，获取所有的未生效的二维码
					    list = qrcodeService.findQrcodeByBatchIdAndStatus(qrcodeBatchVo.getId(),SystemCommon_Constant.QRCode_status_0);
						//修改批次有效数量
						cb.setValidAmount(cb.getValidAmount()+list.size());
					}else{
						return error("状态数据异常！");
					}
					qrcodeBatchService.update(cb);
					//更新二维码状态
					for(QRCode qrc : list){
						qrc.setQrcodeStatus(status.toString());
						qrcodeService.update(qrc);
					}
				}else{
					return error("状态数据异常！");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return error("修改失败！");
				
			}
			return success("修改成功！");
	}
	/** 
	 * 导出Excel 
	 * @param model 
	 * @param projectId 
	 * @param request 
	 * @return 
	 * @throws UnsupportedEncodingException 
	 */  
	@RequestMapping(value="/downLoadExcel",method=RequestMethod.GET)  
	public ModelAndView toDcExcel(Model model,@RequestParam(value = "batchId") String batchId) throws UnsupportedEncodingException{
		QRCodeBatch  qrcb = qrcodeBatchService.get(batchId);
		String batchNum = qrcb.getBatchNumber();
		List<QRCode> qrcList = qrcodeService.findQrcodeByBatchIdAndStatus(batchId, SystemCommon_Constant.ACTIVITY_STATUS_2);
		Map<String,Object> map = new HashMap<String,Object>();    
		map.put("qrcList", qrcList);  
		map.put("batch", batchNum);
		map.put("count", qrcList.size());
		ViewExcelOfQRCode viewExcel = new ViewExcelOfQRCode();    
		return new ModelAndView(viewExcel, map);   
		
	} 
}
