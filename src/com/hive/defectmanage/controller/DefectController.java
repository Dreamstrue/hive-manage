package com.hive.defectmanage.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dk.controller.BaseController;

/**
 * @description 缺陷采集
 * @author yyf
 * @date 2016-01-13
 */
@Controller
@RequestMapping("/defect")
public class DefectController extends BaseController{
	private final String PREFIX = "defectRecordManage/";
	
	/**
	 * 选择进入缺陷采集初始页面
	 */
	@RequestMapping("/toDefectIndex")
	public String toDefectIndex(HttpServletRequest request) {
		return PREFIX + "defectIndex";
	}
	
}
