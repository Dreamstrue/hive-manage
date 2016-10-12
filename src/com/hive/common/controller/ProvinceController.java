package com.hive.common.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.common.entity.Province;
import com.hive.common.service.ProvinceService;

import dk.controller.BaseController;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：Ryu Zheng
 * <p/>创建日期：2013-11-3
 * <p/>创建时间：下午2:54:53
 * <p/>功能描述：省份代码表Controller
 * <p/>===========================================================
 */
@Controller
@RequestMapping("/province")
public class ProvinceController extends BaseController {
	
	/** 省份代码表Service */
	@Resource
	private ProvinceService provinceService;
	
	
	/**
	 * 功能描述： 省份代码表数据加载
	 * 创建时间:2013-10-28下午3:25:02
	 * 创建人: Ryu Zheng
	 * 
	 * @param page
	 * @param entInfoId
	 * @return
	 */
	@RequestMapping("/listAll")
	@ResponseBody
	public List<Province> listAll(){
		return provinceService.getProvince();
	}
	
}
