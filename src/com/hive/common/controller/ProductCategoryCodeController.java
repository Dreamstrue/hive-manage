package com.hive.common.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.common.entity.ProductCategoryCode;
import com.hive.common.service.ProductCategoryCodeService;

import dk.controller.BaseController;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：Ryu Zheng
 * <p/>创建日期：2013-11-3
 * <p/>创建时间：下午2:54:53
 * <p/>功能描述：产品类别代码表Controller
 * <p/>===========================================================
 */
@Controller
@RequestMapping("/productCategoryCode")
public class ProductCategoryCodeController extends BaseController {
	
	/** 产品类别代码表Service */
	@Resource
	private ProductCategoryCodeService productCategoryCodeService;
	
	
	/**
	 * 功能描述：加载产品类别代码表数据
	 * 创建时间:2013-11-5下午6:02:29
	 * 创建人: Ryu Zheng
	 * 
	 * @param parentCode 父分类代码
	 * @return
	 */
	@RequestMapping("/listProductCategory")
	@ResponseBody
	public List<ProductCategoryCode> listProductCategory(@RequestParam(value = "parentCode", required = false, defaultValue = "") String parentCode){
		return productCategoryCodeService.getProductCategory(parentCode);
	}
	
}
