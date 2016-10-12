/**
 * 
 */
package com.hive.clientinterface.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hive.clientinterface.service.DefectInterfaceService;
import com.hive.common.AnnexFileUpLoad;
import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Annex;
import com.hive.common.service.AnnexService;
import com.hive.contentmanage.entity.CommonContent;
import com.hive.defectmanage.entity.DefectRecord;
import com.hive.defectmanage.entity.DefectRecordCar;
import com.hive.defectmanage.entity.DefectRecordCarContacts;
import com.hive.defectmanage.entity.DefectRecordCarDefect;
import com.hive.defectmanage.entity.DefectRecordCarInfo;
import com.hive.defectmanage.entity.DmAssembly;
import com.hive.defectmanage.entity.DmCarBrand;
import com.hive.defectmanage.entity.DmCarBrandSeries;
import com.hive.defectmanage.entity.DmCarBrandSeriesModel;
import com.hive.defectmanage.entity.DmCarBrandSeriesModelDetail;
import com.hive.defectmanage.entity.DmCertificateStype;
import com.hive.defectmanage.entity.DmGearbox;
import com.hive.defectmanage.entity.DmSubAssembly;
import com.hive.defectmanage.entity.DmThreeLevelAssembly;
import com.hive.defectmanage.entity.ProductKeepRecord;
import com.hive.defectmanage.model.DefectRecordCardBean;
import com.hive.enterprisemanage.entity.EEnterpriseinfo;
import com.hive.enterprisemanage.service.EnterpriseInfoService;
import com.hive.membermanage.entity.MMember;
import com.hive.membermanage.service.MembermanageService;
import com.hive.util.DataUtil;
import com.hive.util.DateUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**  
 * Filename: DefectInterfaceController.java  
 * Description: 缺陷采集接口
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  YangHui
 * @version: 1.0  
 * @Create:  2014-6-3  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-6-3 下午3:02:43				YangHui 	1.0
 */
@Controller
@RequestMapping("interface/defect")
public class DefectInterfaceController extends BaseController {

	
	
	@Resource
	private DefectInterfaceService defectInterfaceService;
	@Resource
	private MembermanageService membermanageService;
	@Resource 
	private EnterpriseInfoService enterpriseInfoService;
	@Resource
	private AnnexService annexService;
	/**
	 * 
	 * @Description: 证件类别表
	 * @author YangHui 
	 * @Created 2014-6-3
	 * @return
	 */
	@RequestMapping(value="certType", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> certType(){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<DmCertificateStype> list = defectInterfaceService.getCertificatesTypeList();
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, list);
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 汽车品牌列表
	 * @author YangHui 
	 * @Created 2014-6-4
	 * @return
	 */
	@RequestMapping(value="carBrandList", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> carBrandList(){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<DmCarBrand> list = defectInterfaceService.getCarBrandList("");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, list);
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 汽车品牌列表（根据名字模糊搜索）
	 * @author 燕珂 
	 * @Created 2015-11-11
	 * @return
	 */
	@RequestMapping(value="carBrandList2", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> carBrandList2(@RequestParam(value="brandName") String brandName){
		
//		try {
//			//System.out.println("转码前：" + brandName);
//			brandName = new String(brandName.getBytes("ISO-8859-1"), "UTF8");
//			//System.out.println("转码后：" + brandName);
//		} catch (UnsupportedEncodingException  e) {
//			System.out.println("转码失败！");
//			e.printStackTrace();
//		}
		
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<DmCarBrand> list = defectInterfaceService.getCarBrandList(brandName);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, list);
		return resultMap;
	}

	
	/**
	 * 
	 * @Description: 汽车品牌车型系列列表
	 * @author YangHui 
	 * @Created 2014-6-4
	 * @return
	 */
	@RequestMapping(value="carBrandSeriesList", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> carBrandSeriesList(@RequestParam(value="brandId") Long brandId){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<DmCarBrandSeries> list = defectInterfaceService.getCarBrandSeriesList(brandId);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, list);
		return resultMap;
	}
	
	
	/**
	 * 
	 * @Description: 车型名称列表
	 * @author YangHui 
	 * @Created 2014-6-4
	 * @return
	 */
	@RequestMapping(value="carBrandSeriesModelList", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> carBrandSeriesModelList(@RequestParam(value="brandId") Long brandId,@RequestParam(value="seriesId") Long seriesId){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<DmCarBrandSeriesModel> list = defectInterfaceService.getCarBrandSeriesModelList(brandId,seriesId);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, list);
		return resultMap;
	}
	
	

	/**
	 * 
	 * @Description: 车型具体型号列表
	 * @author YangHui 
	 * @Created 2014-6-4
	 * @return
	 */
	@RequestMapping(value="carBrandSeriesModelDetailList", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> carBrandSeriesModelDetailList(@RequestParam(value="brandId") Long brandId,@RequestParam(value="seriesId") Long seriesId,@RequestParam(value="modelId") Long modelId){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<DmCarBrandSeriesModelDetail> list = defectInterfaceService.getCarBrandSeriesModelDetailList(brandId,seriesId,modelId);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, list);
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 变速器类型列表
	 * @author YangHui 
	 * @Created 2014-6-4
	 * @return
	 */
	@RequestMapping(value="gearBoxList", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> gearBoxList(){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<DmGearbox> list = defectInterfaceService.getGearBoxList();
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, list);
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 总成代码表
	 * @author YangHui 
	 * @Created 2014-6-4
	 * @return
	 */
	@RequestMapping(value="assemblyList", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> assemblyList(){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<DmAssembly> list = defectInterfaceService.getAssemblyList();
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, list);
		return resultMap;
	}
	/**
	 * 
	 * @Description:分总成代码表
	 * @author YangHui 
	 * @Created 2014-6-4
	 * @return
	 */
	@RequestMapping(value="subAssemblyList", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> subAssemblyList(@RequestParam(value="assemblyId") Long assemblyId){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<DmSubAssembly> list = defectInterfaceService.getSubAssemblyList(assemblyId);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, list);
		return resultMap;
	}
	
	/**
	 * 
	 * @Description:三级总成代码表
	 * @author YangHui 
	 * @Created 2014-6-4
	 * @return
	 */
	@RequestMapping(value="threeAssemblyList", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> threeAssemblyList(@RequestParam(value="assemblyId") Long assemblyId,@RequestParam(value="subAssemblyId") Long subAssemblyId){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<DmThreeLevelAssembly> list = defectInterfaceService.getThreeAssemblyList(assemblyId,subAssemblyId);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, list);
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 儿童玩具或其他类别的缺陷采集
	 * @author YangHui 
	 * @Created 2014-6-3
	 * @param prizeorder
	 * @return
	 */
	@RequestMapping(value="saveDefectRecord", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveDefectRecord(@RequestParam(value="parame") String prizeorder){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Gson gson = new Gson();
		DefectRecord dr = null;
		try {
			
		//	String consult="{\"buyDate\":5000}";
			dr  = gson.fromJson(prizeorder,DefectRecord.class);
			dr.setReportdate(DateUtil.getTimestamp());
			defectInterfaceService.saveDefectRecord(dr);
			
			resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
			resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
			resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, dr);
		} catch (Exception e) {
			resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_FAIL);
			resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "服务器错误");
		}
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 保存汽车缺陷采集---联系人信息
	 * @author YangHui 
	 * @Created 2014-6-3
	 * @param prizeorder
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="saveDrCarContact", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveDrCarContact(@RequestParam(value="parame") String prizeorder,@RequestParam(value="reportuserid",required=false) Long userId){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		DefectRecordCar car = new DefectRecordCar();
		car.setReportdate(DateUtil.getTimestamp());
		car.setReportuserid(userId);
		car.setIsValid(SystemCommon_Constant.VALID_STATUS_0);   //默认值为0
		defectInterfaceService.saveDefectRecordCar(car);
		Long defectId = car.getId();
		
		Gson gson = new Gson();
		try {
			
		} catch (Exception e) {
		}
		DefectRecordCarContacts carContact = gson.fromJson(prizeorder,DefectRecordCarContacts.class);
		carContact.setDefectId(defectId);
		defectInterfaceService.saveDefectRecordCarConstact(carContact);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, carContact);
		return resultMap;
	}
	
	
	/**
	 * 
	 * @Description: 保存汽车类缺陷采集---车辆信息
	 * @author YangHui 
	 * @Created 2014-6-4
	 * @param prizeorder
	 * @return
	 */
	@RequestMapping(value="saveDrCarInfo", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveDrCarInfo(@RequestParam(value="parame") String prizeorder){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		Gson gson = new Gson();
		DefectRecordCarInfo carinfo = gson.fromJson(prizeorder,DefectRecordCarInfo.class);
		
		defectInterfaceService.saveDefectRecordCarInfo(carinfo);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, carinfo);
		return resultMap;
	}
	
	
	/**
	 * 
	 * @Description: 保存汽车类缺陷采集---缺陷描述信息
	 * @author YangHui 
	 * @Created 2014-6-4
	 * @param prizeorder
	 * @return
	 */
	@RequestMapping(value="saveDrCarDefect",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveDrCarDefect(@RequestParam(value="parame") String prizeorder){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		Gson gson = new Gson();
		DefectRecordCarDefect carDefect = gson.fromJson(prizeorder,DefectRecordCarDefect.class);
		defectInterfaceService.saveDrCarDefect(carDefect);
		//当汽车类缺陷采集---联系人信息、车辆信息、缺陷描述信息都保存后，修改汽车缺陷表的记录为有效状态
		Long defectId = carDefect.getDefectId();
		DefectRecordCar car = defectInterfaceService.getDefectRecordCar(defectId);
		if(car!=null){
			car.setIsValid(SystemCommon_Constant.VALID_STATUS_1);
			defectInterfaceService.updateDefectRecordCar(car);
		}
		
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, carDefect);
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 汽车类缺陷采集所有信息（联系人、汽车、缺陷描述等）一起保存
	 * @author YangHui 
	 * @Created 2014-6-10
	 * @param prizeorder
	 * @return
	 */
	@RequestMapping(value="saveDrCardAllInfo",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveDrCardAllInfo(HttpServletRequest request,@RequestParam(value="parame") String prizeorder,@RequestParam(value="fileCount",required=false) int count ){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		List<MultipartFile> list = new ArrayList<MultipartFile>();
		if(count>0){
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
			for(int i=0;i<count;i++){
				list.add((MultipartFile)multipartRequest.getFile("file"+i));
			}
		}
		
		Gson gson = new Gson();
		try {
			DefectRecordCardBean bean = gson.fromJson(prizeorder, DefectRecordCardBean.class);
			
			//1.保存汽车类产品缺陷记录表
			DefectRecordCar dr = new DefectRecordCar();
			dr.setReportuserid(bean.getReportuserid());
			dr.setIsValid(SystemCommon_Constant.VALID_STATUS_1);
			dr.setReportdate(DateUtil.getTimestamp());
			defectInterfaceService.saveDefectRecordCar(dr);
			
			Long defectId = dr.getId();
			
			//2.保存汽车信息
			DefectRecordCarInfo info = new DefectRecordCarInfo();
			PropertyUtils.copyProperties(info, bean);
			info.setDefectId(defectId);
			defectInterfaceService.saveDefectRecordCarInfo(info);
			
			//3.保存联系人信息
			DefectRecordCarContacts contact = new DefectRecordCarContacts();
			PropertyUtils.copyProperties(contact, bean);
			contact.setDefectId(defectId);
			defectInterfaceService.saveDefectRecordCarConstact(contact);
			
			//4.保存缺陷描述信息
			DefectRecordCarDefect defect = new DefectRecordCarDefect();
			PropertyUtils.copyProperties(defect, bean);
			defect.setDefectId(defectId);
			defectInterfaceService.saveDrCarDefect(defect);
			
			
			
			//5.保存附件
			if(DataUtil.listIsNotNull(list)){
				HttpSession session = null;
				List<Annex> alist = AnnexFileUpLoad.uploadFile(list, session, defectId, "DEFECT_RECORD_CAR", "defectInfo", "");
				annexService.saveAnnexList(alist, "");
			}
			
			resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
			resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
			resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, dr);
		} catch (Exception e) {
			
			resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_FAIL);
			resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "服务器错误");
		}
		
		return resultMap;
	}
	
	
	/**
	 * 
	 * @Description: 产品缺陷采集信息查询
	 * @author YangHui 
	 * @Created 2014-6-11
	 * @param peporttype 采集类型  （1-儿童玩具类；2-汽车类；3-其他类）
	 * @param proName   产品名称
	 * @param producerName  producerName
	 * @return
	 */
	@RequestMapping(value="queryDefectInfo",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryDefectInfo(RequestPage page,@RequestParam(value="peporttype") String peporttype,@RequestParam(value="keyword",required=false,defaultValue="") String keyword){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		DataGrid dataGrid = defectInterfaceService.getDefectRecordInfos(page,peporttype,keyword);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_TOTAL, dataGrid.getTotal());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, dataGrid.getRows());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_PAGENO, page.getPage());
		
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 查看缺陷记录的详细信息
	 * @author YangHui 
	 * @Created 2014-6-12
	 * @param peporttype
	 * @param id
	 * @return
	 */
	@RequestMapping(value="queryDefectDetail",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> queryDefectDetail(@RequestParam(value="peporttype") String peporttype,@RequestParam(value="id") Long id){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Object obj = defectInterfaceService.getDefectRecordDetail(peporttype,id);
		if("2".equals(peporttype)){
			DefectRecordCardBean bean = (DefectRecordCardBean) obj;
			resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, bean);
		}else{
			DefectRecord record = (DefectRecord) obj;
			resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, record);
		}
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		
		return resultMap;
	}
	
	
	/**
	 * 
	 * @Description: 企业进行备案时查询的企业基本信息
	 * @author YangHui 
	 * @Created 2014-6-5
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="queryEnterInfo",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object>  queryEnterInfo(@RequestParam(value="userId") Long userId){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		//通过登录的用户ID查询企业的信息
		MMember mber = membermanageService.get(userId);
		if("0".equals(mber.getCmembertype())){
			resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_FAIL);
			resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "该用户不是企业用户");
		}else{
			if(mber.getNenterpriseid()==null){
				resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_FAIL);
				resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "无对应的企业信息");
			}else{
				EEnterpriseinfo einfo = enterpriseInfoService.get(mber.getNenterpriseid());
				resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
				resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
				resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, einfo);
			}
		}
		return resultMap;
	}
	
	
	/**
	 * 
	 * @Description: 保存企业备案信息
	 * @author YangHui 
	 * @Created 2014-6-5
	 * @param parame
	 * @return
	 */
	@RequestMapping(value="saveKeepRecord",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>  saveKeepRecord(@RequestParam(value="parame") String parame){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Gson gson = new Gson();
		ProductKeepRecord record = gson.fromJson(parame, ProductKeepRecord.class);
		record.setKeepRecordDate(DateUtil.getTimestamp()); //备案时间
		defectInterfaceService.saveProductKeepRecord(record);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, record);
		return resultMap;
	}
	
	
	@RequestMapping(value="queryKeepRecord",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> queryKeepRecord(RequestPage page,@RequestParam(value="userId") Long userId){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		DataGrid dataGrid = defectInterfaceService.getProductKeepRecord(page,userId);
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_TOTAL, dataGrid.getTotal());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, dataGrid.getRows());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_PAGENO, page.getPage());
		
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 召回公告列表
	 * @author YangHui 
	 * @Created 2014-6-17
	 * @param page
	 * @return
	 */
	@RequestMapping(value="recallNoticeList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> recallNoticeList(RequestPage page){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		DataGrid dataGrid = defectInterfaceService.getRecallNoticeList(page);
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_TOTAL, dataGrid.getTotal());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, dataGrid.getRows());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_PAGENO, page.getPage());
		
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 召回公告明细
	 * @author YangHui 
	 * @Created 2014-6-17
	 * @param page
	 * @param id
	 * @return
	 */
	@RequestMapping(value="recallNoticeDetail",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> recallNoticeDetail(@RequestParam(value="id") Long id){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		CommonContent cc = defectInterfaceService.getCommonContentById(id);
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, cc);
		
		return resultMap;
	}
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args){
		String consult="{\"buyDate\":2014-06-06 10:20:12}";
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		DefectRecord dr  = gson.fromJson(consult,DefectRecord.class);
		System.out.println(dr);
	}
}
