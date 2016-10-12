package com.hive.clientinterface.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.clientinterface.service.InfoCategoryInterfaceService;
import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.City;
import com.hive.common.entity.District;
import com.hive.common.entity.Province;
import com.hive.enterprisemanage.model.ProductCategoryBean;
import com.hive.surveymanage.entity.SurveyCategory;
import com.hive.systemconfig.entity.InfoCategory;
import com.hive.systemconfig.entity.VersionCategory;
import com.hive.systemconfig.service.VersionCategoryService;
import com.hive.util.DataUtil;

import dk.controller.BaseController;
@Controller
@RequestMapping("interface/infoCate")
public class InfoCategoryInterfaceController extends BaseController {

	
	@Resource
	private InfoCategoryInterfaceService infoCategoryInterfaceService;
	@Resource
	private VersionCategoryService versionCategoryService;
	
	@RequestMapping(value="infoCateList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> infoCateList(){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<InfoCategory> list = infoCategoryInterfaceService.getAllInfoCategory();
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, list);
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 加载信息分类列表
	 * @author yanghui 
	 * @Created 2014-5-6
	 * @return
	 */
	@RequestMapping(value="cateList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> cateList(@RequestParam(value="pid") Long pid){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<InfoCategory> list = new ArrayList<InfoCategory>();
		list = infoCategoryInterfaceService.getCateList(pid);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, list);
		return resultMap;
	}
	
	
	/**
	 * 
	 * @Description: 加载产品类别分类列表
	 * @author yanghui 
	 * @Created 2014-5-14
	 * @param pid
	 * @return
	 */
	@RequestMapping(value="proCateList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> proCateList(){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<ProductCategoryBean> list = infoCategoryInterfaceService.getProductCategoryList();
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, list);
		return resultMap;
	}
	
	/**
	 * 
	 * @Description:  省份代码表
	 * @author YangHui 
	 * @Created 2014-6-3
	 * @return
	 */
	@RequestMapping(value="provinceList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> provinceList(){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<Province> list = infoCategoryInterfaceService.getProvinceList();
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, list);
		return resultMap;
	}
	
	
	/**
	 * 
	 * @Description:  城市代码表
	 * @author YangHui 
	 * @Created 2014-6-3
	 * @return
	 */
	@RequestMapping(value="cityList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> cityList(@RequestParam(value="provincecode") String pcode){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<City> list = infoCategoryInterfaceService.getCityList(pcode);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, list);
		return resultMap;
	}
	
	
	
	/**
	 * 
	 * @Description:  县区代码表
	 * @author YangHui 
	 * @Created 2014-6-3
	 * @return
	 */
	@RequestMapping(value="districtList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> districtList(@RequestParam(value="citycode") String ccode){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<District> list = infoCategoryInterfaceService.getDistrictList(ccode);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, list);
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 加载问卷类别列表
	 * @author YangHui 
	 * @Created 2014-9-4
	 * @param ccode
	 * @return
	 */
	@RequestMapping(value="surveyCateList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> surveyCateList(){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<SurveyCategory> list = infoCategoryInterfaceService.getSurveyCategoryList();
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, list);
		return resultMap;
	}
	
	
}
