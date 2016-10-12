/**
 * 
 */
package com.hive.surveymanage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hive.surveymanage.service.SurveyMerchantService;

import dk.controller.BaseController;

/**  
 * Filename: SurveyMerchantController.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  YangHui
 * @version: 1.0  
 * @Create:  2014-10-11  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-10-11 上午9:44:27				YangHui 	1.0
 */
@Controller
@RequestMapping("merchant")
public class SurveyMerchantController extends BaseController {

	@Resource
	private SurveyMerchantService surveyMerchantService;
	
	
}
