package com.hive.common.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.common.entity.District;
import com.hive.common.service.DistrictService;

import dk.controller.BaseController;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：Ryu Zheng
 * <p/>创建日期：2013-11-3
 * <p/>创建时间：下午2:54:53
 * <p/>功能描述：县区代码表Controller
 * <p/>===========================================================
 */
@Controller
@RequestMapping("/district")
public class DistrictController extends BaseController {
	
	/** 县区代码表Service */
	@Resource
	private DistrictService districtService;
	
	
	/**
	 * 功能描述：加载指定市区下的县区数据
	 * 创建时间:2013-11-4下午5:55:20
	 * 创建人: Ryu Zheng
	 * 
	 * @param cityCode 市区代码
	 * @return
	 */
	@RequestMapping("/listDistrict")
	@ResponseBody
	public List<District> listDistrict(@RequestParam(value = "cityCode", required = false, defaultValue = "") String cityCode){
		return districtService.getDistrictsOfCity(cityCode);
	}
	
}
