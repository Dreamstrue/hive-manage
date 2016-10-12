/**
 * 
 */
package com.hive.systemconfig.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hive.systemconfig.service.MemberGradeService;

import dk.controller.BaseController;

/**  
 * Filename: MemberGradeController.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-31  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-31 下午5:34:34				yanghui 	1.0
 */
@Controller
@RequestMapping("memberGrade")
public class MemberGradeController extends BaseController {

	
	@Resource
	private MemberGradeService memberGradeService;
}
