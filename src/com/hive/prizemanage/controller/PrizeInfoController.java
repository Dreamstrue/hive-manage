/**
 * 
 */
package com.hive.prizemanage.controller;

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
import com.hive.prizemanage.entity.PrizeInfo;
import com.hive.prizemanage.entity.PrizeSupRecord;
import com.hive.prizemanage.model.PrizeInfoBean;
import com.hive.prizemanage.model.PrizeInfoSearchBean;
import com.hive.prizemanage.service.PrizeInfoService;
import com.hive.prizemanage.service.PrizeSupRecordService;
import com.hive.util.DateUtil;

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
@RequestMapping("prizeInfo")
public class PrizeInfoController extends BaseController {

	
	private static final String PREFIX = "prizemanage";
	private static final String OBJECT_TABLE = "M_PRIZEINFO";
	private static final String BUSINESS_DIR = SystemCommon_Constant.JPMS_05;
	
	@Resource
	private PrizeInfoService prizeInfoService;
	
	@Resource
	private PrizeSupRecordService prizeSupRecordService;
	
	@RequestMapping("manage")
	public String manage(){
		return PREFIX+"/prizeManage";
	}
	
	@RequestMapping("dataGrid")
	@ResponseBody
	public DataGrid dataGrid(RequestPage page,PrizeInfoSearchBean bean){
		return prizeInfoService.dataGrid(page,bean);
	}
	
	
	@RequestMapping("add")
	public String add(){
		return PREFIX+"/prizeAdd";
	}
	
	@RequestMapping("insert")
	@ResponseBody
	public Map<String,Object> insert(PrizeInfo info,HttpServletRequest request,@RequestParam(value="file",required=false) MultipartFile file){
		//处理裁剪的图片
		AnnexFileUpLoad annexFileUpload = new AnnexFileUpLoad();
		//新增图片的处理时第三个参数不需要
		String newImg = annexFileUpload.cutImage(request, info.getPicturePath(),"");
		info.setPicturePath(newImg);
		
		String prizeName = info.getPrizeName();
		boolean flag = prizeInfoService.isExistPrizeName(prizeName);
		if(flag){
			return error("奖品名称已存在");
		}
		String filePath = AnnexFileUpLoad.writeContentToFile(info.getPrizeExplain(),BUSINESS_DIR,"","");
		info.setPrizeExplain(filePath); //把内容保存到文件后的文件路径保存到内容字段里，方便以后从文件中读取字节流
		info.setExcNum(new Long(0));//新增奖品时 默认兑换数量为0
		HttpSession session = request.getSession();
		info.setCreateId((Long) session.getAttribute("userId"));
		info.setCreateTime(DateUtil.getTimestamp());
		info.setAuditStatus(SystemCommon_Constant.AUDIT_STATUS_1);// 默认 1-审核通过
		info.setValid(SystemCommon_Constant.VALID_STATUS_1); // 默认 1-可用
		
		prizeInfoService.save(info);
/*		if(file.getSize()>0){
			List<Annex> list = AnnexFileUpLoad.uploadImageFile(file,session,new Long(0),"","",SystemCommon_Constant.ANNEXT_TYPE_IMAGE);
			String picpath = list.get(0).getCfilepath();
			String picName = list.get(0).getCfilename();
			info.setPictureName(picName);
			info.setPicturePath(picpath);
		}else return error("请选择奖品图片");*/
		
		return success("保存成功",info);
	}
	
	
	@RequestMapping("edit")
	public String edit(Model model,@RequestParam(value="id") Long id){
		PrizeInfo info = prizeInfoService.get(id);
		byte[] b = AnnexFileUpLoad.getContentFromFile(info.getPrizeExplain());
		info.setPrizeExplain(new String(b));
		model.addAttribute("vo", info);
		return PREFIX+"/prizeEdit";
	}
	
	
	@RequestMapping("update")
	@ResponseBody
	public Map<String,Object> update(PrizeInfoBean bean,HttpServletRequest request,@RequestParam(value="file",required=false) MultipartFile file){
		PrizeInfo oldInfo = prizeInfoService.get(bean.getId()); //原始记录信息 
		
		String oldPath = oldInfo.getPrizeExplain(); //修改前的内容文件地址
		String filePath = AnnexFileUpLoad.writeContentToFile(bean.getPrizeExplain(), BUSINESS_DIR, oldPath,"approve"); //修改后新的内容文件地址
		
		String oldPicturePath= oldInfo.getPicturePath();
		try {
			PropertyUtils.copyProperties(oldInfo, bean);
			oldInfo.setPrizeExplain(filePath);
			//处理修改时是否上传图片剪裁
			AnnexFileUpLoad annexFileUpload = new AnnexFileUpLoad();
			String newImg = annexFileUpload.cutImage(request, bean.getPicturePath(), oldPicturePath);
			if(!StringUtils.isEmpty(newImg)){ //不是空，说明修改时新传了图片
				oldInfo.setPicturePath(newImg);
			}
			
			
			
			HttpSession session = request.getSession();
			oldInfo.setModifyId((Long) session.getAttribute("userId"));
			oldInfo.setModifyTime(DateUtil.getTimestamp());
			
			/*if("no".equals(bean.getImageExist())){
				//说明图片被删除了
				if(file.getSize()>0){
					List<Annex> list = AnnexFileUpLoad.uploadImageFile(file,session,new Long(0),"","",SystemCommon_Constant.ANNEXT_TYPE_IMAGE);
					String picpath = list.get(0).getCfilepath();
					String picName = list.get(0).getCfilename();
					oldInfo.setPictureName(picName);
					oldInfo.setPicturePath(picpath);
					
					//由于修改了图片，就需要把原来的图片物理删除
					String oldpath = bean.getPicturePath();
					AnnexFileUpLoad.deleteFile(oldpath);
				}else return error("请选择奖品图片");
			}*/
			prizeInfoService.update(oldInfo);
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
		PrizeInfo pinfo = prizeInfoService.get(id);
		pinfo.setValid(SystemCommon_Constant.VALID_STATUS_0); 
		prizeInfoService.update(pinfo);
		//删除对应的补货 记录
		prizeSupRecordService.updatePrizeSubRecord(id);
		return success("删除成功");
	}
	/**
	 * 
	 * @Description: 奖品补货记录列表页面
	 * @author yanghui 
	 * @Created 2014-3-13
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("subRecord")
	public String subRecord(Model model,@RequestParam(value="id") Long id){
		model.addAttribute("prizeId", id);
		return PREFIX+"/prizeNumberManage";
	}
	
	
	@RequestMapping("recordDataGrid")
	@ResponseBody
	public DataGrid recordDataGrid(RequestPage page,PrizeInfoSearchBean bean){
		return prizeSupRecordService.recordDataGrid(page,bean);
	}
	
	@RequestMapping("subRecordAdd")
	public String subRecordAdd(Model model,@RequestParam(value="prizeId") Long prizeId){
		model.addAttribute("prizeId", prizeId);
		model.addAttribute("prizeName", prizeInfoService.get(prizeId).getPrizeName());
		return PREFIX+"/prizeNumberAdd";
	}
	
	@RequestMapping("subRecordInsert")
	@ResponseBody
	public Map<String,Object> subRecordInsert(PrizeSupRecord record,HttpSession session){
		record.setCreateTime(DateUtil.getTimestamp());
		record.setCreateId((Long) session.getAttribute("userId"));
		record.setValid(SystemCommon_Constant.VALID_STATUS_1);
		prizeSupRecordService.save(record);
		//待新增数量后，要修改奖品表中的奖品数量
		PrizeInfo info = prizeInfoService.get(record.getPrizeId());
		Long prizeNum = info.getPrizeNum()+record.getPrizeNum();
		info.setPrizeNum(prizeNum);
		prizeInfoService.update(info);
		
		return success("添加成功");
	}
	
	/**
	 * 
	 * @Description: 奖品库存查询
	 * @author yanghui 
	 * @Created 2014-3-17
	 * @return
	 */
	@RequestMapping("surplus")
	public String surplus(){
		return PREFIX+"/prizeSurplus";
	}
	
	@RequestMapping("excDetail")
	public String excDetail(Model model,@RequestParam(value="id") Long id){
		model.addAttribute("prizeId", id);
		return PREFIX+"/prizeExcDetail";
	}
	
	/**
	 * 
	 * @Description: 某种奖品已兑换的明细
	 * @author yanghui 
	 * @Created 2014-3-17
	 * @param page
	 * @param bean
	 * @return
	 */
	@RequestMapping("excDetailDataGrid")
	@ResponseBody
	public DataGrid excDetailDataGrid(RequestPage page,PrizeInfoSearchBean bean){
		return prizeInfoService.excDetailDataGrid(page,bean);
	}
	
	
	@RequestMapping("exchange")
	public String exchange(){
		return PREFIX+"/prizeExchange";
	}
	
	@RequestMapping("/allPrizeInfo.action")
	@ResponseBody
	public List<PrizeInfo> allPrizeInfo(){
		Long prizeCateId=SystemCommon_Constant.PRIZE_CATEID;
		return prizeInfoService.getAllPrizeInfo(prizeCateId);
	}
	/**
	 * 查询所有的奖品信息
	 * 20160608 yyf add
	 * @return
	 */
	@RequestMapping("/findAllPrizeInfo.action")
	@ResponseBody
	public List<PrizeInfo> findAllPrizeInfo(){
		return prizeInfoService.getAllPrizeInfo(null);
	}
	
	@RequestMapping("/getPrizeInfoById.action")
	@ResponseBody
	public PrizeInfo getPrizeInfoById(@RequestParam(value="id") Long id,HttpServletRequest request){
		  PrizeInfo pf=prizeInfoService.get(id);
		  String prizeExplain = pf.getPrizeExplain();
			byte[] b = AnnexFileUpLoad.getContentFromFile(prizeExplain);		
			pf.setPrizeExplain((new String(b)).replace("/zxt-manage", request.getContextPath()));
		  return pf;
	}
}
