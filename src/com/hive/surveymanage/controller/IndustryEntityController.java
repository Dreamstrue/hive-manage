package com.hive.surveymanage.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.zxing.WriterException;
import com.hive.common.AnnexFileUpLoad;
import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Annex;
import com.hive.enterprisemanage.entity.EEnterpriseinfo;
import com.hive.enterprisemanage.service.EnterpriseInfoService;
import com.hive.membermanage.entity.MMember;
import com.hive.membermanage.service.MembermanageService;
import com.hive.surveymanage.entity.CObject;
import com.hive.surveymanage.entity.IndustryEntity;
import com.hive.surveymanage.entity.IndustryEntitySurvey;
import com.hive.surveymanage.entity.Survey;
import com.hive.surveymanage.entity.SurveyIndustry;
import com.hive.surveymanage.model.IndustryEntityBean;
import com.hive.surveymanage.service.CObjectService;
import com.hive.surveymanage.service.IndustryEntityService;
import com.hive.surveymanage.service.IndustryEntitySurveyService;
import com.hive.surveymanage.service.SurveyIndustryService;
import com.hive.surveymanage.service.SurveyService;
import com.hive.surveymanage.service.ViewExcelOfEntity;
import com.hive.systemconfig.service.VersionCategoryService;
import com.hive.tagManage.entity.SNBase;
import com.hive.tagManage.entity.SNBatch;
import com.hive.tagManage.entity.SNBatchSurvey;
import com.hive.tagManage.model.SNBaseVo;
import com.hive.tagManage.service.ViewExcelOfSN;
import com.hive.util.DataUtil;
import com.hive.util.DateUtil;
import com.hive.util.EncoderHandler;
import com.hive.util.PropertiesFileUtil;
import com.hive.util.TwoDCodeImage;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.util.MD5;

/**
 * @description 行业实体管理控制类
 * @author zhaopengfei
 * @createtime 2015-12-15 下午03:55:13
 */
@Controller
@RequestMapping("/industryEntityManage")
public class IndustryEntityController extends BaseController {

	private static final String PREFIX = "surveymanage/industryEntityManage";  // 页面目录（路径前缀）

	@Resource
	private IndustryEntityService industryEntityService;
	@Resource
	private VersionCategoryService versionCategoryService;
	@Resource
	private SurveyIndustryService surveryIndustryService;
	@Resource
	private CObjectService  cObjectService;
	@Resource
	private MembermanageService membermanageService;
	/** 企业基本信息Service */
	@Resource
	private EnterpriseInfoService entInfoService;
	@Resource
	private IndustryEntitySurveyService industryEntitySurveyService;
	@Resource
	private SurveyService surveyService;
	/**
	 * 功能描述：转到信息列表页面
	 * 创建时间:2015-12-18上午9:26:31
	 * 创建人: pengfei zhao
	 * @return
	 */
	@RequestMapping("/manage") 
	public String manage(Model model, HttpServletRequest request) {
		//取得配置文件配置的上传路径
		PropertiesFileUtil propertiesFileUtil = new PropertiesFileUtil();
		String zxt_url = propertiesFileUtil.findValue("zxt_http_url");
		model.addAttribute("zxt_url", zxt_url);
		return PREFIX + "/manage"; // /manage.jsp
	}
	
	/**
	 * 功能描述：
	 * 创建时间:2013-11-3下午3:48:48
	 * 创建人: Ryu Zheng
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/datagrid.action")
	@ResponseBody
	public DataGrid datagrid(
			RequestPage page,
			@RequestParam(value = "entityName", required = false,defaultValue="") String entityName,
			@RequestParam(value = "entityType", required = false) String entityType,
			@RequestParam(value = "entityCategory", required = false) String entityCategory,
			@RequestParam(value = "cauditstatus", required = false) String cauditstatus,
			HttpServletRequest request) throws UnsupportedEncodingException {
		return industryEntityService.datagrid(page, entityName, entityType,entityCategory, cauditstatus);
	}
	
	/**
	 * 功能描述：转到行业实体添加页面
	 * 创建时间:2013-10-28下午3:08:35
	 * 创建人: pengfei Zhao
	 * @param model
	 * @return
	 */
	@RequestMapping("add")
	public String add(Model model) {
		IndustryEntity industryEntity=new IndustryEntity();
		model.addAttribute("industryEntity", industryEntity);
		return PREFIX + "/add";
	}
	/**
	 * 公用查询行业实体页面
	 */
	@RequestMapping("queryIndustryEntity")
	public String queryIndustryEntity(Model model) {
		return PREFIX + "/query";
	}
	
	
	/**
	 * 功能描述：保存实体信息 
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/saveindusEntity")
	@ResponseBody
	public Map<String, Object> saveindusEntity(
			IndustryEntity induEntity, HttpSession session,
			MultipartHttpServletRequest request, Model model) {
		if(induEntity == null){
			return error("操作失败");
		}
		String entityName=induEntity.getEntityName();
		boolean isok=industryEntityService.checkEntity(entityName);
		if(isok==true){
			return error("实体名称已存在");
		}
		// 登记注册类型分类 代码的处理
		String companyTypeCode = StringUtils.EMPTY;
		String[] regTypes = request.getParameterValues("regType");
		for (int i = 0; i < regTypes.length; i++) {
			if(!regTypes[i].equals("")){
				if(i==0){
					companyTypeCode += regTypes[i];
				}else{
					companyTypeCode += "_"+regTypes[i];					
				}
			}
		}
		
		//创建一个关联的ID
		Long cObjectId = null;
		CObject cObject = new CObject();
		cObject.setObjectType(surveryIndustryService.getobjectTypebyId(induEntity.getEntityType()));
		cObject.setObjectId(induEntity.getOtherId());
		cObject.setObjectName(entityName);
		cObjectId = cObjectService.saveCobject(cObject);
		
		
		induEntity.setCcomtypcode(companyTypeCode);
		// 设置创建人、创建时间、审核人、审核时间
		Long curUserId = (Long)session.getAttribute("userId");
		induEntity.setCreateid(curUserId);
		induEntity.setCreatetime(new Date());
		induEntity.setCauditstatus("0");
		induEntity.setValid("1");
		induEntity.setObjectId(cObjectId.toString());
		industryEntityService.save(induEntity);
		
		return success("操作成功", induEntity);
	}
	
	/**
	  * 方法名称：toEntInfoEdit
	  * 功能描述：转到实体信息编辑页面
	  * 创建时间:2015年12月21日下午4:15:01
	  * 创建人: pengfei Zhao
	  * @param @param entInfoId
	  * @param @param model
	  * @param @return 
	  * @return String
	 */
	@RequestMapping("/toEntityInfoEdit")
	public String toEntityInfoEdit(@RequestParam(value = "entInfoId", required = false) Long entInfoId, Model model){
		IndustryEntity induEntity = null;
		
		// 当entInfoId不为空时，说明是编辑操作
		if(entInfoId != null){
			induEntity = industryEntityService.get(entInfoId);
			CObject object = cObjectService.get(Long.valueOf(induEntity.getObjectId()));
			induEntity.setOtherId(object.getObjectId());
		}else{
			induEntity = new IndustryEntity();
			induEntity.setId(entInfoId);
		}
		
		model.addAttribute("induEntity", induEntity);
		return PREFIX+"/edit";
	} 
    /**
      * 方法名称：updateEntInfo
      * 功能描述：更新实体信息
      * 创建时间:2015年12月21日下午4:44:30
      * 创建人: pengfei Zhao
      * @param @param session
      * @param @param request
      * @param @param model
      * @param @return 
      * @return Map<String,Object>
     */
	@RequestMapping("/updateEntityInfo")
	@ResponseBody
	public Map<String, Object> updateEntityInfo(
			@ModelAttribute("induEntity") IndustryEntity induEntity,
			HttpSession session, MultipartHttpServletRequest request,
			Model model) {
		
		if(induEntity == null){
			return error("操作失败");
		}
		// 登记注册类型分类 代码的处理
		String companyTypeCode = StringUtils.EMPTY;
		String[] regTypes = request.getParameterValues("regType");
		for (int i = 0; i < regTypes.length; i++) {
			if(!regTypes[i].equals("")){
				if(i==0){
					companyTypeCode += regTypes[i];
				}else{
					companyTypeCode += "_"+regTypes[i];					
				}
			}
		}
		induEntity.setCcomtypcode(companyTypeCode);
		
		// 设置创建人、创建时间、审核人、审核时间
		Long curUserId = (Long)session.getAttribute("userId");
		induEntity.setModifyid(curUserId);
		induEntity.setModifytime(new Date());
		induEntity.setValid("1");
		industryEntityService.saveOrUpdate(induEntity);
		// 返回信息
		return success("操作成功", induEntity);
	}
	
/**
  * 方法名称：delLogicInfo
  * 功能描述：逻辑删除实体信息
  * 创建时间:2015年12月23日下午5:33:48
  * 创建人: pengfei Zhao
  * @param @param model
  * @param @param entInfoId
  * @param @return 
  * @return Map<String,Object>
 */
	@RequestMapping("/delLogicInfo")
	@ResponseBody
	public Map<String, Object> delLogicInfo(Model model,@RequestParam("entInfoId") Long entInfoId){
		IndustryEntity induEntity = industryEntityService.get(entInfoId);
		if(induEntity == null){
			return error("删除失败");
		}
		industryEntityService.delEntInfo(induEntity);
		return success("删除成功");
	}
	
	
	@RequestMapping("/delInfo")
	@ResponseBody
	public Map<String, Object> delInfo(Model model,@RequestParam("entInfoId") Long entInfoId){
		IndustryEntity induEntity = industryEntityService.get(entInfoId);
		if(induEntity == null){
			return error("删除失败");
		}
		industryEntityService.delete(entInfoId);
		return success("删除成功");
	}
	
	/**
	  * 方法名称：checkEntity
	  * 功能描述：实体信息审核操作
	  * 创建时间:2015年12月21日下午5:13:39
	  * 创建人: pengfei Zhao
	  * @param @param request
	  * @param @return 
	  * @return Object
	 */
    @RequestMapping({"/checkEntity.action"})
    @ResponseBody
    public Object checkEntity(HttpServletRequest request)
    {
      String ids = request.getParameter("ids");
      String status = request.getParameter("checktype");
      industryEntityService.checkEntity(ids, status);
      return success("审核成功！");
    }
    /**
     * 方法名称：creatLoginUser
     * 功能描述：创建用户
     * 创建人: pengfei Zhao
    */
	@RequestMapping("/creatUser.action") 
	public String creatUser(Model model, @RequestParam(value = "entityId") Long entityId) {
		IndustryEntity induEntity = null;
		induEntity=industryEntityService.get(entityId);
		model.addAttribute("vo", induEntity);
		return PREFIX + "/creatUser";
	}
    /**
      * 方法名称：creatLoginUser
      * 功能描述：创建用户
      * 创建时间:2016年4月15日下午4:41:33
      * 创建人: pengfei Zhao
      * @return Map<String,Object>
     */
    @RequestMapping("/creatLoginUser")
	@ResponseBody
	public Map<String, Object> creatLoginUser(HttpServletRequest request){
    	String entInfoId=request.getParameter("id");
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		IndustryEntity induEntity = industryEntityService.get(Long.valueOf(entInfoId));
		MMember menb=membermanageService.getByuserName(username);
		if(induEntity != null&&menb==null){
			menb=new MMember();
			menb.setCusername(username);
			String encryptedPassword = MD5.getMD5Code(password);
			menb.setCpassword(encryptedPassword);
			menb.setDcreatetime(DateUtil.getTimestamp());
			//menb.setChinesename(induEntity.getLinkMan());
			menb.setChinesename(induEntity.getEntityName());//中文名用 实体名字  20160712 yf update
			menb.setCnickname(induEntity.getEntityName());
			menb.setCaddress(induEntity.getAddress());
			menb.setCvalid(SystemCommon_Constant.VALID_STATUS_1); //默认
			menb.setCmemberlevel(SystemCommon_Constant.MEMBER_LEVEL_1);//默认为1级
			menb.setCprovincecode(induEntity.getCprovincecode());
			menb.setCcitycode(induEntity.getCcitycode());
			menb.setCdistrictcode(induEntity.getCdistrictcode());
			menb.setCmobilephone(induEntity.getLinkPhone());
			menb.setCmembertype("1");
			menb.setCmemberstatus("0");
			menb.setEnterpriseName(induEntity.getEntityName());
			menb.setIndustryEntityId(induEntity.getId());
			//创建企业信息
			EEnterpriseinfo enterprise = entInfoService.getEnterpriseInfoByName(induEntity.getEntityName());
			if(enterprise==null){//判断是否存在
			enterprise=new EEnterpriseinfo();
			enterprise.setCenterprisename(induEntity.getEntityName());
			enterprise.setDestablishdate(induEntity.getFoundtime());
			enterprise.setCregaddress(induEntity.getAddress());
			enterprise.setCprovincecode(induEntity.getCprovincecode());
			enterprise.setCcitycode(induEntity.getCcitycode());
			enterprise.setCdistrictcode(induEntity.getCdistrictcode());
			enterprise.setCindcatcode(induEntity.getEntityType());
			enterprise.setCcomtypcode(induEntity.getCcomtypcode());
			enterprise.setCbusinesslicenseno(induEntity.getCbusinesslicenseno());
			enterprise.setCbusiness(induEntity.getCbusiness());
			enterprise.setCsummary(induEntity.getCsummary());
			enterprise.setCcontractperson(induEntity.getLinkMan());
			enterprise.setCcontractpersonphone(induEntity.getLinkPhone());
			enterprise.setCcontractaddress(induEntity.getAddress());
			entInfoService.save(enterprise);
			}
			menb.setNenterpriseid(enterprise.getNenterpriseid());
		}else{
			return error("用户名已存在!");
		}
		membermanageService.save(menb);
		induEntity.setCreatUserstatus("1");
		industryEntityService.update(induEntity);
		return success("创建用户【"+induEntity.getEntityName()+"】成功,请在企业信息管理中完善企业信息");
	}
	
	@RequestMapping("parentIndustryList")
	@ResponseBody
	public List<SurveyIndustry> getParentIndustryList(){
		List list = industryEntityService.getParentIndustryList();
		return list;
		
	}
	/**
	 * @Title: allindustryEntityInfo   
	 * @Description: 获取所有在册的企业   
	 * @param @return    设定文件  
	 * @return List<EEnterpriseinfo>    返回类型  
	 * @throws  
	 */
	@RequestMapping("/allindustryEntityInfo.action")
	@ResponseBody
	public List<IndustryEntity> allindustryEntityInfo(){
		return industryEntityService.getIndustryEntityInfo();
	}
	/**
	 * @Title: findAllBindedServeyEntity   
	 * @Description: 获取所有绑定过问卷的行业实体 
	 * 20160616 yyf add
	 * @throws  
	 */
	@RequestMapping("/findAllBindedSurveyEntity.action")
	@ResponseBody
	public List<IndustryEntity> findAllBindedSurveyEntity(){
		return industryEntityService.findAllBindedSurveyEntity();
	}
	
	
	/**
	 * 根据外系统传过来的Id获取相关的实体列表
	 * zpf 20160624 add
	 * @return
	 */
	@RequestMapping("getAllIndustryEntityListForOther")
	@ResponseBody
	public List<IndustryEntity> getAllIndustryEntityListForOther(HttpServletRequest request,@RequestParam(value="gasIdList",required=true) String gasIdList){
		List<IndustryEntity> list=null;  
		IndustryEntity ins=new IndustryEntity();
		ins.setEntityName("全部");
		ins.setId(1L);
		if(gasIdList!=null&&!gasIdList.equals("")){
			list=industryEntityService.getAllIndustryEntityListForOther(gasIdList);
		    }
		list.add(ins);
		return list;
		
	}
	/**
	 * 获取本系统所有的有效实体对象
	 * @return
	 */
	@RequestMapping("getAllIndustryEntityList")
	@ResponseBody
	public List<IndustryEntity> getAllIndustryEntityList(@RequestParam(value = "q", required = false) String entityName){
		List<IndustryEntity> list = industryEntityService.getAllIndustryEntityList(entityName);
		return list;
		
	}
	/**
	 * 
	 * @Description: 生产二维码图片
	 * @author YangHui 
	 * @Created 2014-10-16
	 * @param id
	 * @return
	 * @throws WriterException
	 * @throws IOException
	 */
	@RequestMapping("createTwoCode")
	@ResponseBody
	public Map<String, Object> createTwoCode(@RequestParam(value="id") Long id) throws WriterException, IOException{
		
		//取得配置文件中服务部署的IP和端口
		PropertiesFileUtil pfu = new PropertiesFileUtil();
		String httpUrl = pfu.findValue("zxt_http_url");
		
		String text = httpUrl+"zxt-manage/interface/survey/sweepCode.action?id="+id+"&sign=enterprise";
		String imgName = "";

		IndustryEntity induEntity = industryEntityService.get(id);;
		if(induEntity!=null){
			imgName = induEntity.getEntityName();
		}
		String path = ""; 
		path = TwoDCodeImage.writeImage(text,imgName);
		
		return this.success("二维码生成成功！", induEntity);
	}
	
	/**
	 * 查看标签详情信息
	 * @param model
	 * @param productId
	 * @return
	 */
	@RequestMapping("/viewBarcodeInfo.action") 
	public String viewBarcodeInfo(Model model, @RequestParam(value = "entityId") Long entityId) {
		IndustryEntity induEntity = null;
		IndustryEntityBean vo =null;
		try {
			induEntity=industryEntityService.get(entityId);
			String queryPath = SystemCommon_Constant.QRCODE_PATH;
			vo= new IndustryEntityBean();
			PropertyUtils.copyProperties(vo, induEntity);
			vo.setQueryPath(queryPath+entityId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("vo", vo);
		return PREFIX + "/viewBarcode";
	}
	

	/**
	 * 绑定问卷信息
	 * @param model
	 * @param productId
	 * @return
	 */
	@RequestMapping("/bindingInfo.action") 
	public String bindingInfo(Model model, @RequestParam(value = "entityId") Long entityId) {
		
		IndustryEntity induEntity = null;
		induEntity=industryEntityService.get(entityId);
		model.addAttribute("vo", induEntity);
		return PREFIX + "/binding";
	}
	/**
	 * 绑定问卷信息
	 * @author 赵鹏飞
	 */
	@RequestMapping("/bindingSurveyInfo")
	@ResponseBody
	public Map<String, Object> bindingSurveyInfo(HttpServletRequest request, HttpSession session) {
	try {
		String entityId=request.getParameter("id");
		String surveyId=request.getParameter("surveyId");
		IndustryEntity induEntity = null;
		induEntity=industryEntityService.get(Long.valueOf(entityId));
		if(surveyId!=null&&!surveyId.equals("")){
			induEntity.setSurveyId(Long.valueOf(surveyId));
		}else{
			induEntity.setSurveyId(null);
		}
		industryEntityService.saveOrUpdate(induEntity);
		if(surveyId!=null&&!surveyId.equals("")){
			List<IndustryEntitySurvey> returnList=industryEntitySurveyService.exitEntityByIndustryandEntityId(entityId, surveyId);
			if(returnList.size()>0){
				IndustryEntitySurvey industryEntitySurvey=returnList.get(0);
				industryEntitySurvey.setSurveyId(surveyId);
				industryEntitySurvey.setCreateTime(new Date());
				industryEntitySurvey.setSurveyTitle(surveyService.get(Long.valueOf(surveyId)).getSubject());
				industryEntitySurveyService.update(industryEntitySurvey);
			}else{
				IndustryEntitySurvey industryEntitySurvey = new IndustryEntitySurvey();
				industryEntitySurvey.setIndustryEntityId(entityId);
				industryEntitySurvey.setSurveyId(surveyId);
				industryEntitySurvey.setCreateTime(new Date());
				industryEntitySurvey.setSurveyTitle(surveyService.get(Long.valueOf(surveyId)).getSubject());
				industryEntitySurveyService.saveOrUpdate(industryEntitySurvey);
			}
		}else{
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success("修改成功！", "");
	}
	
	
	/**
	  * 方法名称：viewQrcode
	  * 功能描述：查看二维码
	  * 创建时间:2016年1月25日下午5:40:08
	  * 创建人: pengfei Zhao
	  * @param @param content
	  * @param @param requset
	  * @param @param response 
	  * @return void
 */
	@RequestMapping("/viewBarcodeImg.action") 
	public void viewQrcode(@RequestParam(value = "id") String id,@RequestParam(value = "queryPath") String queryPath,HttpServletRequest requset, HttpServletResponse response) {
	        EncoderHandler encoder = new EncoderHandler();  
	        encoder.encoderQRCoder(queryPath, response);  
	}
	/**
	 * 
	 * @Description: 获取
	 * @author yyf
	 * @Created 20160615
	 */
	@RequestMapping("findSurveyListById")
	@ResponseBody
	public List<Survey> findSurveyListById(@RequestParam(value="objectId") Long objectId) {
		
		IndustryEntity ie = industryEntityService.get(objectId);
		List<Survey> list = new ArrayList<Survey>();
		Long surId = ie.getSurveyId();
		if(surId!=null&&surId>0){
			list = surveyService.findSurveyById(surId);
		}
		return list;
	}
	/**
	 * 
	 * @Description: 获取linkId
	 * @author yyf
	 * @Created 20160615
	 */
	@RequestMapping("getLinkIdByParam")
	@ResponseBody
	public IndustryEntitySurvey getLinkIdByParam(@RequestParam(value="objectId") Long objectId,@RequestParam(value="surveyId") Long surveyId) {
		IndustryEntitySurvey ies = industryEntitySurveyService.getLinkIdByParam(objectId,surveyId);
		if(ies!=null){
			return ies;
		}else{
			throw new RuntimeException("行业实体问卷表数据异常");
		}
	}
	/**
	 * 
	 * @Description: 获取IndustryEntity
	 * @author yyf
	 * @Created 20160815
	 */
	@RequestMapping("getIndustryEntityById")
	@ResponseBody
	public IndustryEntity getIndustryEntityById(@RequestParam(value="id") Long id) {
		IndustryEntity ie = industryEntityService.get(id);
		if(ie!=null){
			return ie;
		}else{
			throw new RuntimeException("行业实体数据异常");
		}
		
}

	
	
	/** 
	 * 导出Excel 
	 * @param model 
	 * @param projectId 
	 * @param request 
	 * @return 
	 * @throws UnsupportedEncodingException 
	 */  
	@RequestMapping(value="/entitySaveExcel",method=RequestMethod.GET) 
	public ModelAndView entitySaveExcel(@RequestParam("ids") String ids){
		int len=0;
		String[] id = ids.split(",");
		len=id.length;
		List<IndustryEntityBean> Maplist = new ArrayList<IndustryEntityBean>(); 
		for(int i = 0; i<id.length; i++){
			IndustryEntity ie = industryEntityService.get(Long.valueOf(id[i]));
			IndustryEntityBean ieb=industryEntityService.getIndstryEntiyBeanForEntity(ie);
			Maplist.add(ieb);
		}
		Map map = new HashMap(); 
		map.put("Maplist", Maplist);
		ViewExcelOfEntity viewExcel = new ViewExcelOfEntity();    
		return new ModelAndView(viewExcel, map);   
	}
}
