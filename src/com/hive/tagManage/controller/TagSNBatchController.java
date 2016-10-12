package com.hive.tagManage.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hive.common.SystemCommon_Constant;
import com.hive.surveymanage.entity.IndustryEntity;
import com.hive.surveymanage.entity.IndustryEntitySurvey;
import com.hive.surveymanage.entity.Survey;
import com.hive.surveymanage.service.IndustryEntityService;
import com.hive.surveymanage.service.SurveyService;
import com.hive.tagManage.entity.SNBase;
import com.hive.tagManage.entity.SNBatch;
import com.hive.tagManage.entity.SNBatchCode;
import com.hive.tagManage.entity.SNBatchSurvey;
import com.hive.tagManage.model.ZipOfSN;
import com.hive.tagManage.service.TagAllocationService;
import com.hive.tagManage.service.TagBatchCodeService;
import com.hive.tagManage.service.TagSNBaseService;
import com.hive.tagManage.service.TagSNBatchService;
import com.hive.tagManage.service.TagSNBatchSurveyService;
import com.hive.tagManage.service.ViewExcelOfSN;
import com.hive.util.DateUtil;
import com.hive.util.IdFactory;
import com.hive.util.PropertiesFileUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

@Controller
@RequestMapping("tagSNBatchController")
public class TagSNBatchController extends BaseController {

	private static final String PREFIX = "tagManage/snBatch";

	@Resource
	private TagSNBatchService tagSNBatchService;

	@Resource
	private IndustryEntityService industryEntityService;
	@Resource
	private TagSNBaseService tagSNBaseService;

	@Resource
	private TagBatchCodeService tagBatchCodeService;
	
	@Resource
	private TagAllocationService tagAllocationService;
	
	@Resource
	private TagSNBatchSurveyService tagSNBatchSurveyService;
	
	@Resource
	private SurveyService surveyService;
	
	
//	
	/**
	 * sn代码库列表页面
	 */
	@RequestMapping("snBatchManage")
	public String manage() {
		return PREFIX + "/manage";
	}
	
	/**
	 * SNBatch 显示列表
	 * @Title: datagrid   
	 * @param @param page
	 * @param @param snBase
	 * @param @param request
	 * @param @return    设定文件  
	 * @return DataGrid    返回类型  
	 * @throws  
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public DataGrid datagrid(RequestPage page, SNBatch snBatch,HttpServletRequest request) {
		return tagSNBatchService.datagrid(page, snBatch);
	}
	
	/**
	 * 生成sn序列号的页面
	 */
	@RequestMapping("/add")
	public String add(Model model) {
		synchronized(this) {
		String nyr = DateUtil.dateToNumber(new Date()).substring(2,8);//当前年2位（XX年xx月xx日）
		/*SNBatchCode bc = tagBatchCodeService.getBatchBybfirst(nyr);
		if(bc != null) {
			bc.setBatchLast((Integer.parseInt(bc.getBatchLast())+1)+"");
		} else {
			bc = new SNBatchCode();
			bc.setBatchFirst(nyr);
			bc.setBatchLast("1");
		}*/
		model.addAttribute("nyr", nyr);
		//model.addAttribute("batchCode",bc.getBatchFirst()+"*" );
		//model.addAttribute(bc);
		return PREFIX + "/add";
		}
	}
	/**
	 * 生成后处理的sn序列号的页面
	 */
	@RequestMapping("/after_add")
	public String after_add(Model model) {
		synchronized(this) {
			String nyr = DateUtil.dateToNumber(new Date()).substring(2,8);//当前年2位（XX年xx月xx日）
			model.addAttribute("nyr", nyr);
			return PREFIX + "/after_add";
		}
	}
	/**
	 * 跳转后处理绑定问卷和实体
	 * 20160920 yyf add
	 */
	@RequestMapping("/toBindSurveyAndEntity")
	public String toBindSurveyAndEntity(Model model,@RequestParam(value = "id") String id) {
		synchronized(this) {
			SNBatch  snBatch  = tagSNBatchService.get(id);
			model.addAttribute("vo", snBatch);
			return PREFIX + "/bindSurveyAndEntity";
		}
	}
	/**
	 *绑定实体和问卷后处理
	 *20160920 yyf add
	 */
	@RequestMapping("/bindSurveyAndEntity")
	@ResponseBody
	public Object bindSurveyAndEntity(Model model,
			@RequestParam(value = "id") String id,
			@RequestParam(value = "industryEntityId") String industryEntityId,
			@RequestParam(value = "subjectId") String subjectId) {
		synchronized(this) {
			String entityName= "";
			if(StringUtils.isNotBlank(industryEntityId)){
				IndustryEntity industryEntity=industryEntityService.get(Long.valueOf(industryEntityId));
				entityName=industryEntity.getEntityName();
			}else{
				industryEntityId = "";
				entityName="通用型";
			}
			SNBatch  snBatch  = tagSNBatchService.get(id);
			snBatch.setIndustryEntityId(industryEntityId);
			snBatch.setEntityName(entityName);
			tagSNBatchService.update(snBatch);
			SNBatchSurvey sNBatchSurvey = new SNBatchSurvey();
			sNBatchSurvey.setSnBatchId(snBatch.getId());
			sNBatchSurvey.setSurveyId(subjectId);
			sNBatchSurvey.setCreateTime(new Date());
			sNBatchSurvey.setSurveyTitle(surveyService.get(Long.valueOf(subjectId)).getSubject());
			tagSNBatchSurveyService.save(sNBatchSurvey);
			return success("绑定成功！");
		}
	}
	/**
	  * @方法描述: TODO(修改用户申请单状态) 
	  * @创建人：zhaopengfei
	  * @创建时间：2015-8-5 下午06:15:24   
	  * @修改人：zhaopengfei
	  * @throws
	 */
	@RequestMapping("/edit")
	public String edit(Model model,
			@RequestParam(value = "id") String id) {
		synchronized(this) {
			
			model.addAttribute("vo",tagSNBatchService.get(id));
		//String nyr = DateUtil.dateToNumber(new Date()).substring(2,8);//当前年2位（XX年xx月xx日）
		//model.addAttribute("nyr", nyr);
		return PREFIX + "/edit";
		}
	}
	/**
	 * 生成每批次的SN
	 * @Title: insert   
	 * @param @param snBase
	 * @param @param session
	 * @param @return    设定文件  
	 * @return Map    返回类型  
	 * @throws  
	 */
	@RequestMapping("/createSN.action")
	@ResponseBody
	public  Object createSN(HttpSession session,HttpServletRequest request,Model model) {
		synchronized(this) {
			String curUserId = session.getAttribute("userId").toString();	//当前登录ID
			String numbers = request.getParameter("nNumber");	//申请数量
//			Integer status = Integer.parseInt(request.getParameter("status"));	//状态
			String nyr = request.getParameter("nyr");	//年月日
			String industryEntityId= request.getParameter("industryEntityId");//适用实体
			String subjectId= request.getParameter("subjectId");//问卷ID
			//先判断是否为0 或者为空
			if(numbers==null||numbers.equals("")||industryEntityId==null||industryEntityId.equals("")){
				return error("生产失败！请填写补全申请数据。");
			}
			if(numbers.equals("0")){
				return error("生产失败！生成数量不能为0。");
			}
			//判断在标签批次戳里有无记录（Tag_BatchCode）
			SNBatchCode snBaseCode = tagBatchCodeService.getBatchBybfirst(nyr);
			if(snBaseCode != null) {
				snBaseCode.setBatchLast((Integer.parseInt(snBaseCode.getBatchLast())+1)+"");
			} else {
				snBaseCode = new SNBatchCode();
				snBaseCode.setBatchFirst(nyr);
				snBaseCode.setBatchLast("1");
			}
			String batchCode = snBaseCode.getBatchFirst()+(snBaseCode.getBatchLast()+"");
			
			String entityName="";
			if(StringUtils.isNotBlank(industryEntityId)){
				IndustryEntity industryEntity=industryEntityService.get(Long.valueOf(industryEntityId));
				entityName=industryEntity.getEntityName();
//				enterpriseName
			}else{
				entityName="通用型";
			}
			
			/***** 组装批次信息  START *****/
			SNBatch snBatch = new SNBatch();
			snBatch.setBatch(batchCode);
			snBatch.setAmount(Integer.parseInt(numbers));
			snBatch.setValidAmount(Long.valueOf(numbers));
			snBatch.setCreater(curUserId);
			snBatch.setCreateTime(new Date());
			snBatch.setStatus(0);
			snBatch.setEntityName(entityName);
			snBatch.setIndustryEntityId(industryEntityId);
			/***** 组装批次信息  END *****/
			
			/***** 组装SN信息  START *****/
			List<Object[]> snbaseList = new ArrayList<Object[]>();
			int num=Integer.parseInt(numbers);
			long psn=0;
			String queryPath = SystemCommon_Constant.QRCODE_PATH;
			queryPath+=industryEntityId+"&SNId=";
			for(int i = 0; i <num; i ++) {
				long sn=0;
				String id = IdFactory.getUuid();
			    sn = tagSNBaseService.getPK(i);
				snbaseList.add(new Object[]{id, "", sn, null, i, 1, queryPath+id});
				
				//SystemCommon_Constant.QRCODE_PATH
			}
			/***** 组装SN信息  END *****/
			
			int result = tagSNBaseService.insertSN(snbaseList,snBaseCode,snBatch);
				
			if(result > 0) {
				List<SNBase> list = tagAllocationService.findAll();
				
				
				//保存   批次问卷对应信息   yf 20160303 add
				if(null != snBatch.getId() && !"".equals(snBatch.getId()) &&
				   null != subjectId && !"".equals(subjectId)){
					SNBatchSurvey sNBatchSurvey = new SNBatchSurvey();
					sNBatchSurvey.setSnBatchId(snBatch.getId());
					sNBatchSurvey.setSurveyId(subjectId);
					sNBatchSurvey.setCreateTime(new Date());
					sNBatchSurvey.setSurveyTitle(surveyService.get(Long.valueOf(subjectId)).getSubject());
					tagSNBatchSurveyService.save(sNBatchSurvey);
				}
				
				
				return success("生产成功！",list.get(0));
			} else {
				return error("生产失败！");
			}
		}
	}
	
	/**
	 * 后处理生成标签 
	 * @throws  
	 */
	@RequestMapping("/createSNAfter.action")
	@ResponseBody
	public  Object createSNAfter(HttpSession session,HttpServletRequest request,Model model) {
		String curUserId = session.getAttribute("userId").toString();	//当前登录ID
		String numbers = request.getParameter("nNumber");	//申请数量
		Integer batchCount = Integer.parseInt(request.getParameter("batchCount"));	//状态
		String nyr = request.getParameter("nyr");	//年月日
		if(StringUtils.isBlank(numbers)||numbers.equals("0")){
			throw new RuntimeException("生成数量需大于0");
		}
		//先判断是否超出数量上限
		Integer sum = batchCount*Integer.parseInt(numbers);
		if(sum>100000){
			return success("生产失败！因生成总数量大于100000！");
		}
		if(numbers==null||numbers.equals("")){
			return success("生产失败！请填写补全申请数据。");
		}
		try {
			tagSNBatchService.createSNAfter(curUserId,numbers,batchCount,nyr);
		} catch (Throwable e) {
			return success("生产失败！");
		}
		return success("生产成功！");
	}
	/** 
	 * 导出Excel的zip 
	 * @param model 
	 * @param projectId 
	 * @param request 
	 * @return 
	 * @throws Exception 
	 */  
	@RequestMapping(value="/dcExcelZip",method=RequestMethod.GET)  
	public ModelAndView toDcExcelZip(ModelMap model, HttpServletRequest request) throws Exception{
		PropertiesFileUtil propertiesFileUtil = new PropertiesFileUtil();
		// 取得配置文件配置的上传路径
		String path = propertiesFileUtil.findValue("uploadPath")+File.separator+"snBatch";
		ZipOfSN zos = new ZipOfSN();
		String zipFileName = "质讯通标签批次"+DateUtil.format(new Date(), "yyyyMMdd");
		String ids = request.getParameter("ids");
		String[] idsArr = ids.split(",");
		for(String id : idsArr){
			SNBatch  bat = tagSNBatchService.get(id);
			List<SNBase> snList = tagSNBaseService.getSNByBatch(bat.getId());
			List list = new ArrayList();  //测试数据没有用到  
			Map smap = new HashMap();
			smap.put("sourceDirectory", path+File.separator+"excel");  
			smap.put("zipFileName", zipFileName);
			smap.put("snList", snList);  
			smap.put("batch", bat.getBatch());
			smap.put("count", snList.size());
			smap.put("entityName", bat.getEntityName());
			zos.buildExcelDocument(smap);
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("sourceDirectory", path);
		map.put("zipFileName", zipFileName);
		return new ModelAndView(zos, map);
		
	}  
	/**
	 * sn代码库列表页面
	 */
	@RequestMapping("lookListOfSNBatchId")
	public String lookListOfSNBatchId(ModelAndView mav ,@RequestParam(value = "snBatchId")  String snBatchId) {
		mav.addObject("snBatchId",snBatchId);
		return PREFIX + "/tagList";
	}
	
	
	/**
	 * 某批次下的所有SN
	 * @Title: getAllotedSNDataGrid   
	 * @param @param page
	 * @param @param request
	 * @param @return    设定文件  
	 * @return DataGrid    返回类型  
	 * @throws  
	 */
	@RequestMapping("/getSNGridBySNBatch")
	@ResponseBody
	public DataGrid getSNGridBySNBatch(RequestPage page,HttpServletRequest request) {
		String snBatchId = request.getParameter("snBatchId");
		return tagSNBaseService.getSNGridBySNBatch(page,snBatchId);
	}
	/**
	 * 某批次下的所有SN
	 * @Title: getAllotedSNDataGrid   
	 * @param @param page
	 * @param @param request
	 * @param @return    设定文件  
	 * @return DataGrid    返回类型  
	 * @throws  
	 */
	@RequestMapping("/getSNGridByBatch")
	@ResponseBody
	public DataGrid getSNGridByBatch(RequestPage page,HttpServletRequest request) {
		String snBatch = request.getParameter("snBatch");
		String snBatchId=tagSNBatchService.getEntityBybatch(snBatch).getId();
		return tagSNBaseService.getSNGridBySNBatch(page,snBatchId);
	}
	/** 
	 * 导出Excel 
	 * @param model 
	 * @param projectId 
	 * @param request 
	 * @return 
	 * @throws UnsupportedEncodingException 
	 */  
	@RequestMapping(value="/dcExcel",method=RequestMethod.GET)  
	public ModelAndView toDcExcel(ModelMap model, HttpServletRequest request) throws UnsupportedEncodingException{  
		String batch = request.getParameter("batch");
//		String rank = request.getParameter("rank");
		String snBatchId = request.getParameter("snBatchId");
		String entityName= java.net.URLDecoder.decode(request.getParameter("entityName"), "UTF-8");
		List<SNBase> snList = tagSNBaseService.getSNByBatch(snBatchId);
		List list = new ArrayList();  //测试数据没有用到  
		Map map = new HashMap();    
		map.put("snList", snList);  
		map.put("batch", batch);
		map.put("count", snList.size());
//		map.put("rank", rank);
		map.put("entityName", entityName);
		ViewExcelOfSN viewExcel = new ViewExcelOfSN();    
		return new ModelAndView(viewExcel, map);   
		
	}  
	
	@RequestMapping("/getEntityBybatch.action")
	@ResponseBody
	public SNBatch getEntityBybatch(RequestPage page,HttpServletRequest request){
		String batch = request.getParameter("batch");
		return tagSNBatchService.getEntityBybatch(batch);
	}
	
	/**
	 * 修改用户单据印刷状态
	 * @author 赵鹏飞
	 */
	@RequestMapping("/editSnBatch")
	@ResponseBody
	public Map<String, Object> editSnBatch(HttpServletRequest request, HttpSession session) {
		SNBatch snBatch = new SNBatch();
	try {
		snBatch=tagSNBatchService.get(request.getParameter("id"));
		snBatch.setId(request.getParameter("id"));
		snBatch.setStatus(Integer.parseInt(request.getParameter("status")));//申请单状态
		tagSNBatchService.update(snBatch);
		
		//批量修改  标签批次状态   yf 20150921 add
		String snBaseSTATUS = "0";
		String snBatchStatus = request.getParameter("status");//标签 批次状态
		if(null != snBatchStatus && !"".equals(snBatchStatus)){
			if("0".equals(snBatchStatus)){//未印刷
				snBaseSTATUS = "0";//未印刷
			}else if("1".equals(snBatchStatus)){//印刷中
				snBaseSTATUS = "5";//印刷中
			}else if("2".equals(snBatchStatus)){//已印刷
				snBaseSTATUS = "6";//已印刷
			}
		}
		tagSNBaseService.updateSNStaByBatch(snBatch.getBatch(), snBaseSTATUS);//0.未印刷，5   印刷中   6    已印刷    1.入库(已经印刷入库)，2.出库(厂家已经提货)，3.绑定产品(厂家已经将标签绑定到产品上)，4.作废(印刷失败的标签，此状态保留使用)  
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success("修改成功！", "");
	}
	/**
	 * 根据行业实体id获取其所有绑定过问卷的批次
	 * @author yyf 20160616
	 */
	@RequestMapping("/getAllBindedSurveyBatchByIndustryEntityId")
	@ResponseBody
	public List<SNBatch>  getAllBindedSurveyBatchByIndustryEntityId(HttpServletRequest request,@RequestParam(value="industryEntityId") Long industryEntityId) {
		return tagSNBatchService.getAllBindedSurveyBatchByIndustryEntityId(industryEntityId);
				
	}
	/**
	 * 根据批次id获取其绑定的问卷信息
	 * @author yyf 20160616
	 */
	@RequestMapping("/getBindedSurveyByBatchId")
	@ResponseBody
	public List<Survey>  getBindedSurveyByBatchId(HttpServletRequest request,@RequestParam(value="batchId") String batchId) {
		List<SNBatchSurvey> list = tagSNBatchService.getBindedSurveyByBatchId(batchId);
		List<Survey> rlist = new ArrayList<Survey>();
		if(list.size()==1){
			rlist = surveyService.findSurveyById(Long.parseLong(list.get(0).getSurveyId()));
		}
		return rlist;
		
	}
	/**
	 * 
	 * @Description: 获取linkId
	 * @author yyf
	 * @Created 20160616
	 */
	@RequestMapping("getLinkIdByParam")
	@ResponseBody
	public SNBatchSurvey getLinkIdByParam(@RequestParam(value="batchId") String batchId,@RequestParam(value="surveyId") String surveyId) {
		SNBatchSurvey ies = tagSNBatchSurveyService.getLinkIdByParam(batchId,surveyId);
		if(ies!=null){
			return ies;
		}else{
			throw new RuntimeException("批次问卷表数据异常");
		}
		
	}	
	
}
