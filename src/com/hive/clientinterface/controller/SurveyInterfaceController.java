package com.hive.clientinterface.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.clientinterface.service.SurveyInterfaceService;
import com.hive.common.SystemCommon_Constant;
import com.hive.surveymanage.model.SurveyVo;
import com.hive.surveymanage.service.SurveyKeyWordsService;
import com.hive.util.PropertiesFileUtil;
import com.hive.util.XMLParse;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**
 * @description 为 Android 客户端提供问卷列表数据接口
 * @author 燕珂
 * @createtime 2014-4-8 上午10:20:06
 */
@Controller
@RequestMapping("interface/survey")
public class SurveyInterfaceController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(SurveyInterfaceController.class);

	@Resource
	private SurveyInterfaceService surveyInterfaceService;
	
	@Resource
	private SurveyKeyWordsService surveyKeyWordsService;
	
	/**
	 * 
	 * @Description: 调查问卷列表
	 * @author yanghui 
	 * @Created 2014-3-25
	 * @param page
	 * @param userId
	 * @param cateId  问卷行业类别ID
	 * @return
	 */
	@RequestMapping(value="surveyList", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> surveyList(RequestPage page,@RequestParam(value="cateId") Long cateId){
		DataGrid dataGrid = surveyInterfaceService.dataGrid(page,cateId,"all");
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_TOTAL, dataGrid.getTotal());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, dataGrid.getRows());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_PAGENO, page.getPage());
		return resultMap;
	}
	
	
	/**
	 * 
	 * @Description: 公示问卷列表
	 * @author YangHui 
	 * @Created 2014-9-3
	 * @param page
	 * @param cateId  问卷行业类别ID
	 * @param session
	 * @return
	 */
	@RequestMapping(value="surveyShowList", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> surveyShowList(RequestPage page,@RequestParam(value="cateId") Long cateId){
		DataGrid dataGrid = surveyInterfaceService.dataGrid(page,cateId,"show");
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_TOTAL, dataGrid.getTotal());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, dataGrid.getRows());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_PAGENO, page.getPage());
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 通过扫描二维码信息进行投票接口
	 * @author YangHui 
	 * @Created 2014-10-15
	 * @param id
	 * @return
	 */
	@RequestMapping(value="surveyInfo", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> surveyInfo(@RequestParam(value="id") Long id){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		SurveyVo vo = surveyInterfaceService.getSurveyInfoById(id);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, vo);
		return resultMap;
	}
	
	
	
	/**
	 * 
	 * @Description: 查找行业对应的关键字
	 * @author YangHui 
	 * @Created 2014-10-16
	 * @param key
	 * @return
	 */
	@RequestMapping(value="keywords", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> keywords(@RequestParam(value="industryName") String key){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		try {
			key = new String(key.getBytes("ISO-8859-1"), "UTF-8");
			key = URLDecoder.decode(key, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		List list = surveyKeyWordsService.getKeyWordsListByKey(key);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, list); 
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 扫描二维码（如微信，浏览器，质讯通等不同软件）扫描结果操作判断
	 * @author YangHui 
	 * @Created 2014-10-16
	 * @param key
	 * @return
	 */
	@RequestMapping(value="sweepCode", method=RequestMethod.GET)
	public String sweepCode(Model model,@RequestParam(value="id",required=false) Long id,@RequestParam(value="sign",required=false) String sign){
		String address = "";
		String entAddress = "";
		PropertiesFileUtil pfu = new PropertiesFileUtil();
		//由于质讯通客户端apk与版本信息文件都存放在诚信网项目根目录下，这里取得根目录的路径配置
		String rootPath = pfu.findValue("zxt_service_path");
		
		//质讯通个人版配置文件路径
		String xmlFile_per = rootPath+"updateinfo.xml";
		//String xmlFile_per = "http://www.zhixt.cn/updateinfo.xml";
		//质讯通企业版配置文件路径
		String xmlFile_ent = rootPath+"updateinfoEnt.xml";
		//String xmlFile_ent = "http://www.zhixt.cn/updateinfoEnt.xml";
		//说明除质讯通扫描以外的其他（如微信，浏览器等扫描操作）
		XMLParse xmlParse  =  new XMLParse();
		address = xmlParse.xmlParse(xmlFile_per); 
		entAddress = xmlParse.xmlParse(xmlFile_ent);
		
		logger.debug(address+"<--------->"+entAddress);
		model.addAttribute("address",address);
		model.addAttribute("entAddress",entAddress);
		return "surveymanage/download";
	}
	
}
