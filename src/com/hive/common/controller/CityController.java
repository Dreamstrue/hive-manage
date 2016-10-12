package com.hive.common.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.common.entity.City;
import com.hive.common.service.CityService;

import dk.controller.BaseController;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：Ryu Zheng
 * <p/>创建日期：2013-11-3
 * <p/>创建时间：下午2:54:53
 * <p/>功能描述：地市代码表Controller
 * <p/>===========================================================
 */
@Controller
@RequestMapping("/city")
public class CityController extends BaseController {
	
	/** 地市代码表Service */
	@Resource
	private CityService cityService;
	
	
	/**
	 * 功能描述：加载指定省份下的地市数据
	 * 创建时间:2013-11-4下午5:32:25
	 * 创建人: Ryu Zheng
	 * 
	 * @param proCode 省份代码
	 * @return
	 */
	@RequestMapping("/listCity")
	@ResponseBody
	public List<City> listCity(@RequestParam(value = "proCode", required = false, defaultValue = "") String proCode){
		return cityService.getCitysOfProvince(proCode);
	}
	
}
