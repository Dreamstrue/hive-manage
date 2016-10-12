package com.hive.common.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.common.entity.CompanyTypeCode;
import com.hive.common.service.CompanyTypeCodeService;

import dk.controller.BaseController;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：Ryu Zheng
 * <p/>创建日期：2013-11-3
 * <p/>创建时间：下午2:54:53
 * <p/>功能描述：登记注册类型代码表Controller
 * <p/>===========================================================
 */
@Controller
@RequestMapping("/companyTypeCode")
public class CompanyTypeCodeController extends BaseController {
	
	/** 登记注册类型代码表Service */
	@Resource
	private CompanyTypeCodeService companyTypeCodeService;
	
	
	/**
	 * 功能描述：加载登记注册类型代码表数据
	 * 创建时间:2013-11-5下午4:41:54
	 * 创建人: Ryu Zheng
	 * 
	 * @param parentCode 父分类代码
	 * @return
	 */
	@RequestMapping("/listCompanyType")
	@ResponseBody
	public List<CompanyTypeCode> listCompanyType(@RequestParam(value = "parentCode", required = false, defaultValue = "") String parentCode){
		return companyTypeCodeService.getCompanyType(parentCode);
	}
	
}
