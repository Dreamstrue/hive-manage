package com.hive.enterprisemanage.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.zxing.WriterException;
import com.hive.common.AnnexFileUpLoad;
import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Annex;
import com.hive.common.service.AnnexService;
import com.hive.enterprisemanage.entity.EEnterpriseinfo;
import com.hive.enterprisemanage.service.EnterpriseInfoService;
import com.hive.surveymanage.entity.Survey;
import com.hive.util.PropertiesFileUtil;
import com.hive.util.TwoDCodeImage;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：Ryu Zheng
 * <p/>创建日期：2013-10-23
 * <p/>创建时间：上午9:34:49
 * <p/>功能描述：企业信息管理Controller
 * <p/>===========================================================
 */
@Controller
@RequestMapping("/enterpriseManage")
public class EnterpriseInfoController extends BaseController {
	
	/** 访问前缀：企业登记信息 */
	private final String PREFIX_ENTERPRISE_INFO = "enterprisemanage/enterpriseInfo";
	/** 表名：企业登记信息 */
	private static final String OBJECT_TABLE_ENTERPRISE = "E_ENTERPRISEINFO";
	/** 附件目录：企业登记信息 */
	private static final String BUSINESS_DIR_ENTERPRISE = SystemCommon_Constant.ENT_QYDJ_01;
	
	/** 企业基本信息Service */
	@Resource
	private EnterpriseInfoService entInfoService;
	/** 附件操作Service */
	@Resource
	private AnnexService annexService;
	
	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出Task对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getEntInfo(@RequestParam(value = "nenterpriseid", defaultValue = "-1") Long nenterpriseid, Model model) {
		if (nenterpriseid !=null && nenterpriseid != -1) {
			model.addAttribute("enterpriseInfo", entInfoService.get(nenterpriseid));
		}
	}
	
	/**
	 * 功能描述：转到企业登记信息列表页面
	 * 创建时间:2013-10-23上午9:26:31
	 * 创建人: Ryu Zheng
	 * 
	 * @return
	 */
	@RequestMapping("/toEntInfoList")
	public String toEntInfoList(){
		return PREFIX_ENTERPRISE_INFO+"/entInfoList";
	}
	
	/**
	 * 功能描述：
	 * 创建时间:2013-11-3下午3:48:48
	 * 创建人: Ryu Zheng
	 * 
	 * @param page
	 * @param entName 企业名称
	 * @param orgCode 组织机构代码
	 * @return
	 */
	/**
	 * 功能描述：企业登记信息列表数据加载
	 * 创建时间:2013-11-6上午10:22:29
	 * 创建人: Ryu Zheng
	 * 
	 * @param page
	 * @param entName 企业名称
	 * @param orgCode 组织机构代码
	 * @param province 省份代码
	 * @param city 市区代码
	 * @param district 县区代码
	 * @param regTypes 登记注册类型
	 * @param indCategorys 行业分类
	 * @param legal 法人代表
	 * @param contractAddress 通讯地址
	 * @param request
	 * @return
	 */
	@RequestMapping("/listEntInfo")
	@ResponseBody
	public DataGrid listEntInfo(
			RequestPage page,
			@RequestParam(value = "entName", required = false, defaultValue = "") String entName,
			@RequestParam(value = "orgCode", required = false, defaultValue = "") String orgCode,
			@RequestParam(value = "cprovincecode", required = false, defaultValue = "") String province,
			@RequestParam(value = "ccitycode", required = false, defaultValue = "") String city,
			@RequestParam(value = "cdistrictcode", required = false, defaultValue = "") String district,
			@RequestParam(value = "regType", required = false) String regTypes,
			@RequestParam(value = "cindcatcode", required = false) String cindcatcode,
			@RequestParam(value = "legal", required = false, defaultValue = "") String legal,
			@RequestParam(value = "contractAddress", required = false, defaultValue = "") String contractAddress,
			HttpServletRequest request) {
		
		String comTypCode = StringUtils.EMPTY;
		String indCatCode = StringUtils.isBlank(cindcatcode) ? StringUtils.EMPTY : cindcatcode;
		
		// 如果不是高级查询，则把除了"企业名称"和"组织机构代码"之外的查询参数全部设置为空即可
		String advSearToggle = (String)request.getParameter("advSearToggle");
		if(StringUtils.isBlank(advSearToggle)){
			province = StringUtils.EMPTY;
			city = StringUtils.EMPTY;
			district = StringUtils.EMPTY;
			comTypCode = StringUtils.EMPTY;
			indCatCode = StringUtils.EMPTY;
			legal = StringUtils.EMPTY;
			contractAddress = StringUtils.EMPTY;
			
			return entInfoService.datagrid(page, entName, orgCode, province,city,district, comTypCode,indCatCode, legal, contractAddress);
		}
		
		// 如果是高级查询的话，接下来才会处理其他高级查询项
		
		// 登记注册类型分类 代码的处理
		if(StringUtils.isNotBlank(regTypes)){
			String[] regTypeArray = regTypes.split(",");
			for (int i = 0; i < regTypeArray.length; i++) {
				if(!regTypeArray[i].equals("")){
					if(i==0){
						comTypCode += regTypeArray[i];
					}else{
						comTypCode += "_"+regTypeArray[i];					
					}
				}
			}
		}
		
		/* 国民经济行业分类 代码的处理
		if(StringUtils.isNotBlank(indCategorys)){
			String[] indCategoryArray = indCategorys.split(",");
			for (int i = 0; i < indCategoryArray.length; i++) {
				if(!indCategoryArray[i].equals("")){
					if(i==0){
						indCatCode += indCategoryArray[i];
					}else{
						indCatCode += "_"+indCategoryArray[i];					
					}
				}
			}
		}
		*/
		
		return entInfoService.datagrid(page, entName, orgCode, province,city,district, comTypCode,indCatCode, legal, contractAddress);
	}
	
	/**
	 * 功能描述：转到企业登记信息添加页面
	 * 创建时间:2013-10-28下午3:08:35
	 * 创建人: Ryu Zheng
	 * 
	 * @param entInfoId
	 * @param model
	 * @return
	 */
	@RequestMapping("/toEntInfoAdd")
	public String toEntInfoAdd(Model model){
		EEnterpriseinfo enterpriseInfo = new EEnterpriseinfo();;
		
		model.addAttribute("enterpriseInfo", enterpriseInfo);
		return PREFIX_ENTERPRISE_INFO+"/entInfoAdd";
	}
	
	/**
	 * 功能描述： 添加企业时校验指定的"组织机构代码"是否已存在<br>
	 * 创建时间:2013-12-17下午4:26:57<br>
	 * 创建人: Ryu Zheng<br>
	 * 
	 * @param orgCode
	 * @return
	 */
	@RequestMapping("/existOfOrgCode")
	@ResponseBody
	public boolean existOfOrgCode(@RequestParam(required=true, value="orgCode") String orgCode){
		List<EEnterpriseinfo> entInfoList = entInfoService.checkByOrganizationCode(orgCode);
		if(entInfoList != null && entInfoList.size()>0){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 功能描述：保存企业登记信息 
	 * 创建时间:2013-11-30下午4:01:45 
	 * 创建人: Ryu Zheng
	 * 
	 * @param businessLicencePic
	 *            营业执照图片
	 * @param orgCodePic
	 *            组织机构代码证图片
	 * @param entLogoPic
	 *            企业Logo图片
	 * @param entInfo
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/saveEntInfo")
	@ResponseBody
	public Map<String, Object> saveEntInfo(
			@RequestParam(value = "businessLicencePic", required = true) MultipartFile businessLicencePic,
			@RequestParam(value = "orgCodePic", required = true) MultipartFile orgCodePic,
			@RequestParam(value = "entLogoPic", required = true) MultipartFile entLogoPic,
			EEnterpriseinfo entInfo, HttpSession session,
			MultipartHttpServletRequest request, Model model) {
		
		if(entInfo == null){
			return error("操作失败");
		}
		
		/** 国民经济行业分类 代码的处理
		String indCategoryCode = StringUtils.EMPTY;
		String[] indCategorys = request.getParameterValues("indCategory");
		for (int i = 0; i < indCategorys.length; i++) {
			if(!indCategorys[i].equals("")){
				if(i==0){
					indCategoryCode += indCategorys[i];
				}else{
					indCategoryCode += "_"+indCategorys[i];					
				}
			}
		}
		entInfo.setCindcatcode(indCategoryCode);
		*/
		
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
		entInfo.setCcomtypcode(companyTypeCode);
		
		// 图片附件列表
		List<Annex> annexList = new ArrayList<Annex>();
		
		// 将"营业执照"图片上传到服务器上
		List<Annex> businessLicencePicList = AnnexFileUpLoad.uploadImageFile(
				businessLicencePic, session, 0L, OBJECT_TABLE_ENTERPRISE,
				BUSINESS_DIR_ENTERPRISE,
				SystemCommon_Constant.ANNEXT_TYPE_BUSINESS_LICENCE);
		annexList.addAll(businessLicencePicList);
		
		// 将"组织机构代码证"图片上传到服务器上
		List<Annex> orgCodePicList = AnnexFileUpLoad.uploadImageFile(
				orgCodePic, session, 0L, OBJECT_TABLE_ENTERPRISE,
				BUSINESS_DIR_ENTERPRISE,
				SystemCommon_Constant.ANNEXT_TYPE_ORG_CODE);
		annexList.addAll(orgCodePicList);
		
		// 将"企业Logo"图片上传到服务器上
		List<Annex> entLogoPicList = AnnexFileUpLoad.uploadImageFile(
				entLogoPic, session, 0L, OBJECT_TABLE_ENTERPRISE,
				BUSINESS_DIR_ENTERPRISE,
				SystemCommon_Constant.ANNEXT_TYPE_ENT_LOGO);
		annexList.addAll(entLogoPicList);
		
		// 设置附件标识
		entInfo.setChasannex(SystemCommon_Constant.SIGN_YES_1);
		// 设置创建人、创建时间、审核人、审核时间
		Long curUserId = (Long)session.getAttribute("userId");
		entInfo.setNcreateid(curUserId);
		entInfo.setNauditid(curUserId);
		entInfo.setDcreatetime(new Date());
		entInfo.setDaudittime(new Date());
		entInfoService.save(entInfo);
		annexService.saveAnnexList(annexList, String.valueOf(entInfo.getNenterpriseid()));
		
		return success("操作成功", entInfo);
	}
	
	/**
	 * 功能描述：转到企业登记信息编辑页面
	 * 功能描述：
	 * 创建时间:2013-10-28下午3:08:35
	 * 创建人: Ryu Zheng
	 * 
	 * @param entInfoId
	 * @param model
	 * @return
	 */
	@RequestMapping("/toEntInfoEdit")
	public String toEntInfoEdit(@RequestParam(value = "entInfoId", required = false) Long entInfoId, Model model){
		EEnterpriseinfo enterpriseInfo = null;
		
		boolean annexExist = false;
		// 当entInfoId不为空时，说明是编辑操作
		if(entInfoId != null){
			enterpriseInfo = entInfoService.get(entInfoId);
			//取得附件信息
			List<Annex> annexList = annexService.getAnnexInfoByObjectIdAndAnnexType(entInfoId);
			if(annexList != null && annexList.size()>0){
				annexExist = true;
				model.addAttribute("annexList", annexList);
			}
			
		}else{
			enterpriseInfo = new EEnterpriseinfo();
			enterpriseInfo.setNenterpriseid(entInfoId);
		}
		
		model.addAttribute("annexExist", annexExist);
		model.addAttribute("enterpriseInfo", enterpriseInfo);
		return PREFIX_ENTERPRISE_INFO+"/entInfoEdit";
	}
	
	/**
	 * 功能描述：获取某企业的附件列表数据
	 * 创建时间:2013-12-4上午9:44:32
	 * 创建人: Ryu Zheng
	 * 
	 * @param entInfoId
	 * @return
	 */
	@RequestMapping("/getAnnexsOf")
	@ResponseBody
	public Map<String, Object> getAnnexsOf(@RequestParam(value = "entInfoId", required = false) Long entInfoId){
		//取得附件信息
		List<Annex> annexList = annexService.getAnnexListByObjectId(entInfoId,OBJECT_TABLE_ENTERPRISE);
		return success("操作成功", annexList);
	}
	
	/**
	 * 功能描述：更新企业登记信息 创建时间:2013-12-2下午2:04:20 创建人: Ryu Zheng
	 * 
	 * @param annexDelIds
	 *            用户编辑时删除的附件ID数组
	 * @param businessLicencePic
	 *            营业执照图片
	 * @param orgCodePic
	 *            组织机构代码证图片
	 * @param entLogoPic
	 *            企业Logo图片
	 * @param enterpriseInfo
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/updateEntInfo")
	@ResponseBody
	public Map<String, Object> updateEntInfo(
			@RequestParam(value = "annexDelIds", required = false) String annexDelIds,
			@RequestParam(value = "businessLicencePic", required = false) MultipartFile businessLicencePic,
			@RequestParam(value = "orgCodePic", required = false) MultipartFile orgCodePic,
			@RequestParam(value = "entLogoPic", required = false) MultipartFile entLogoPic,
			@ModelAttribute("enterpriseInfo") EEnterpriseinfo enterpriseInfo,
			HttpSession session, MultipartHttpServletRequest request,
			Model model) {
		
		if(enterpriseInfo == null){
			return error("操作失败");
		}
		
		/** 国民经济行业分类 代码的处理
		String indCategoryCode = StringUtils.EMPTY;
		String[] indCategorys = request.getParameterValues("indCategory");
		for (int i = 0; i < indCategorys.length; i++) {
			if(!indCategorys[i].equals("")){
				if(i==0){
					indCategoryCode += indCategorys[i];
				}else{
					indCategoryCode += "_"+indCategorys[i];					
				}
			}
		}
		enterpriseInfo.setCindcatcode(indCategoryCode);
		*/
		
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
		enterpriseInfo.setCcomtypcode(companyTypeCode);
		
		/*
		 * 如果有要删除的附件，那么就先把要删除的附件删除，然后判断各个图片附件依次进行操作
		 * */
		if(StringUtils.isNotBlank(annexDelIds)){
			String annexDelIdArray[] = annexDelIds.split(",");
			if(annexDelIdArray != null && annexDelIdArray.length>0){
				for(String annexDelId : annexDelIdArray){
					annexService.deleteAnnexById(Long.valueOf(annexDelId));
				}
				
			}
		}
		
		// *****************************************************
		// 图片附件的处理
		// *****************************************************
		List<Annex> annexList = new ArrayList<Annex>();
		
		// "营业执照"图片
		if(!businessLicencePic.isEmpty()){
			// 将"营业执照"图片上传到服务器上
			List<Annex> businessLicencePicList = AnnexFileUpLoad.uploadImageFile(
					businessLicencePic, session, 0L, OBJECT_TABLE_ENTERPRISE,
					BUSINESS_DIR_ENTERPRISE,
					SystemCommon_Constant.ANNEXT_TYPE_BUSINESS_LICENCE);
			annexList.addAll(businessLicencePicList);
		}
		// "组织机构代码证"图片
		if(!orgCodePic.isEmpty()){
			// 将"组织机构代码证"图片上传到服务器上
			List<Annex> orgCodePicList = AnnexFileUpLoad.uploadImageFile(
					orgCodePic, session, 0L, OBJECT_TABLE_ENTERPRISE,
					BUSINESS_DIR_ENTERPRISE,
					SystemCommon_Constant.ANNEXT_TYPE_ORG_CODE);
			annexList.addAll(orgCodePicList);
		}
		// "企业Logo"图片
		if(!entLogoPic.isEmpty()){
			// 将"企业Logo"图片上传到服务器上
			List<Annex> entLogoPicList = AnnexFileUpLoad.uploadImageFile(
					entLogoPic, session, 0L, OBJECT_TABLE_ENTERPRISE,
					BUSINESS_DIR_ENTERPRISE,
					SystemCommon_Constant.ANNEXT_TYPE_ENT_LOGO);
			annexList.addAll(entLogoPicList);
		}
		
		// 设置附件标识
		if(businessLicencePic.isEmpty()&&orgCodePic.isEmpty()&&entLogoPic.isEmpty()){
			enterpriseInfo.setChasannex(SystemCommon_Constant.SIGN_YES_0);
		}else{
			enterpriseInfo.setChasannex(SystemCommon_Constant.SIGN_YES_1);
		}
		// 保存图片附件列表
		annexService.saveAnnexList(annexList, String.valueOf(enterpriseInfo.getNenterpriseid()));
		// 设置修改人、修改时间、审核人、审核时间
		Long curUserId = (Long)session.getAttribute("userId");
		enterpriseInfo.setNmodifyid(curUserId);
		enterpriseInfo.setNauditid(curUserId);
		enterpriseInfo.setDmodifytime(new Date());
		enterpriseInfo.setDaudittime(new Date());
		entInfoService.saveOrUpdate(enterpriseInfo);
		
		// 返回信息
		return success("操作成功", enterpriseInfo);
	}
	
	
	/**
	 * 功能描述：删除企业登记信息
	 * 创建时间:2013-10-23上午9:31:00
	 * 创建人: Ryu Zheng
	 * 
	 * @param model
	 * @param entInfoId
	 * @return
	 */
	@RequestMapping("/delEntInfo")
	@ResponseBody
	public Map<String, Object> delEntInfo(Model model,@RequestParam("entInfoId") Long entInfoId){
		EEnterpriseinfo entInfo = entInfoService.get(entInfoId);
		if(entInfo == null){
			return error("根据企业ID没有查到相应的企业数据");
		}
		
		entInfoService.delEntInfo(entInfo);
		return success("删除成功");
	}
	
	
	/**
	 * ajax验证企业名称是否存在
	 * @param name
	 * @return
	 */
	@RequestMapping("/checkEntName")
	@ResponseBody
	public String checkEntName(@RequestParam("name") String name){
        try {
        	List<EEnterpriseinfo> entList = entInfoService.checkByName(name);
        	if (!entList.isEmpty()&&entList.size()>0) {
        		return "exist";
        	}else {
        		return "unexist";
        		}
        	} catch (Exception e) {
        		e.printStackTrace();   
        		return "验证失败";
        	} 		
	}

	/**
	 * ajax验证组织机构代码是否存在
	 * @param name
	 * @return
	 */
	@RequestMapping("/checkOrganizationCode")
	@ResponseBody
	public String checkOrganizationCode(@RequestParam("name") String name){
        try {
        	List<EEnterpriseinfo> entList = entInfoService.checkByOrganizationCode(name);
        	if (!entList.isEmpty()&&entList.size()>0) {
        		return "exist";
        	}else {
        		return "unexist";
        		}
        	} catch (Exception e) {
        		e.printStackTrace();   
        		return "验证失败";
        	} 		
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

		EEnterpriseinfo enterpriseInfo = entInfoService.get(id);;
		if(enterpriseInfo!=null){
			imgName = enterpriseInfo.getCenterprisename();
		}
		String path = ""; 
		path = TwoDCodeImage.writeImage(text,imgName);
		
		return this.success("二维码生成成功！", enterpriseInfo);
	}
}