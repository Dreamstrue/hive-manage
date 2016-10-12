package com.hive.enterprisemanage.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hive.common.AnnexFileUpLoad;
import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Annex;
import com.hive.common.service.AnnexService;
import com.hive.enterprisemanage.entity.EQualification;
import com.hive.enterprisemanage.service.QualificationService;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：Ryu Zheng
 * <p/>创建日期：2013-10-31
 * <p/>创建时间：下午2:30:11
 * <p/>功能描述：企业资质信息Controller
 * <p/>===========================================================
 */
@Controller
@RequestMapping("/enterpriseManage")
public class QualificationController extends BaseController {
	
	/** 访问前缀：企业资质信息 */
	private final String PREFIX_QUALIFICATION = "enterprisemanage/qualification";
	/** 表名：企业资质信息 */
	private static final String OBJECT_TABLE_QUALIFICATION = "E_QUALIFICATION";
	/** 附件目录：企业资质信息 */
	private static final String BUSINESS_DIR_QUALIFICATION = SystemCommon_Constant.ENT_QYZZ_02;
	
	/** 企业资质信息Service */
	@Resource
	private QualificationService qualificationService;
	/** 附件操作Service */
	@Resource
	private AnnexService annexService;
	
	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出Task对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getQualification(@RequestParam(value = "nquaid", defaultValue = "-1") Long nquaid, Model model) {
		if (nquaid !=null && nquaid != -1) {
			model.addAttribute("qualification", qualificationService.get(nquaid));
		}
	}
	
	
	/**
	 * 功能描述：转到企业资质信息列表页面
	 * 创建时间:2013-10-24下午5:22:57
	 * 创建人: Ryu Zheng
	 * 
	 * @return
	 */
	@RequestMapping("/toQualificationList")
	public String toQualificationList(@RequestParam(value = "entInfoId", required = false) Long entInfoId, Model model){
		model.addAttribute("entInfoId", entInfoId);
		return PREFIX_QUALIFICATION+"/qualificationList";
	}
	
	/**
	 * 功能描述：企业资质信息列表数据加载
	 * 创建时间:2013-10-30上午10:19:12
	 * 创建人: Ryu Zheng
	 * 
	 * @param page
	 * @param entInfoId
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping("/listQualification")
	@ResponseBody
	public DataGrid listQualification(RequestPage page, @RequestParam(value = "entInfoId", required = false) Long entInfoId) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		return qualificationService.datagrid(page, entInfoId);
	}
	
	/**
	 * 功能描述：转到企业资质信息添加页面
	 * 创建时间:2013-10-29上午9:47:48
	 * 创建人: Ryu Zheng
	 * 
	 * @param entInfoId
	 * @param model
	 * @return
	 */
	@RequestMapping("/toQualificationAdd")
	public String toQualificationAdd(@RequestParam(value = "entInfoId", required = false) Long entInfoId, Model model) {
		EQualification qualification = new EQualification();
		qualification.setNenterpriseid(entInfoId);
		
		model.addAttribute("qualification", qualification);
		return PREFIX_QUALIFICATION + "/qualificationAdd";
	}
	
	/**
	 * 功能描述：保存企业资质信息
	 * 创建时间:2013-10-30上午10:42:07
	 * 创建人: Ryu Zheng
	 * 
	 * @param annex
	 * @param qualification
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/saveQualification")
	@ResponseBody
	public Map<String, Object> saveQualification(
			@RequestParam(value = "annex", required = false) MultipartFile annex,
			EQualification qualification, HttpSession session, Model model) {
		
		if(qualification == null){
			return error("操作失败");
		}
		// 先将附件上传到服务器上，然后设置资质信息记录是有附件的，接下来保存数据信息，最后将附件信息保存到数据库中
		if(annex != null && annex.getSize()>0){
			List<Annex> list = AnnexFileUpLoad.uploadImageFile(annex, session,0L,OBJECT_TABLE_QUALIFICATION,BUSINESS_DIR_QUALIFICATION,SystemCommon_Constant.ANNEXT_TYPE_IMAGE);
			qualification.setChasannex(SystemCommon_Constant.SIGN_YES_1);
			// 设置创建人、创建时间、审核人、审核时间
			Long curUserId = (Long)session.getAttribute("userId");
			qualification.setNcreateid(curUserId );
			qualification.setNauditid(curUserId );
			qualification.setDcreatetime(new Date());
			qualification.setDaudittime(new Date());
			qualificationService.save(qualification);
			annexService.saveAnnexList(list,String.valueOf(qualification.getNquaid()));
		}else{
			// 设置创建人、创建时间、审核人、审核时间
			Long curUserId = (Long)session.getAttribute("userId");
			qualification.setNcreateid(curUserId );
			qualification.setNauditid(curUserId );
			qualification.setDcreatetime(new Date());
			qualification.setDaudittime(new Date());
			qualificationService.save(qualification);
		}
	
		return success("操作成功", qualification);
	}
	
	/**
	 * 功能描述：转到企业资质信息编辑页面
	 * 创建时间:2013-10-29下午4:14:45
	 * 创建人: Ryu Zheng
	 * 
	 * @param qualificationId
	 * @param entInfoId
	 * @param model
	 * @return
	 */
	@RequestMapping("/toQualificationEdit")
	public String toQualificationEdit(
			@RequestParam(value = "qualificationId", required = false) Long qualificationId,
			@RequestParam(value = "entInfoId", required = false) Long entInfoId,
			Model model) {
		
		EQualification qualification = null;
		boolean annexExist = false;
		// 当qualificationId不为空时，说明是编辑操作
		if(qualificationId != null){
			qualification = qualificationService.get(qualificationId);
			//取得附件信息
			List<Annex> annexList = annexService.getAnnexInfoByObjectIdAndObjectType(qualificationId, OBJECT_TABLE_QUALIFICATION);
			if(annexList != null && annexList.size()>0){
				annexExist = true;
				model.addAttribute("annexFile", annexList.get(0));
			}
			
		}else{
			qualification = new EQualification();
			qualification.setNenterpriseid(entInfoId);
		}
		
		model.addAttribute("annexExist", annexExist);
		model.addAttribute("qualification", qualification);
		return PREFIX_QUALIFICATION + "/qualificationEdit";
	}
	
	/**
	 * 功能描述：更新企业资质信息
	 * 创建时间:2013-11-1上午10:55:39
	 * 创建人: Ryu Zheng
	 * 
	 * @param annex
	 * @param annexDelIds 用户编辑时删除的附件ID数组
	 * @param qualification
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/updateQualification")
	@ResponseBody
	public Map<String, Object> updateQualification(
			@RequestParam(value = "annex", required = false) MultipartFile annex,
			@RequestParam(value = "annexDelIds", required = false) String annexDelIds,
			@ModelAttribute("qualification") EQualification qualification, HttpSession session, Model model) {
		
		if(qualification == null){
			return error("操作失败");
		}
		
		/*
		 * 一、如果有要删除的附件，那么就先把要删除的附件删除，然后根据有没有新附件又分两种情况:
		 * 		1，annex为空，就标记一下就可以了；
		 * 		2，annex不空，就将新附件进行上传并保存；
		 * 二、若没有要删除的附件，然后根据有没有新附件又分两种情况:
		 * 		1，annex为空，就标记一下就可以了；
		 * 		2，annex不空，就将新附件进行上传并保存；
		 * 
		 * 最后就是直接设置一下修改人，修改日期信息保存修改即可；
		 * */
		if(StringUtils.isNotBlank(annexDelIds)){
			String annexDelIdArray[] = annexDelIds.split(",");
			if(annexDelIdArray != null && annexDelIdArray.length>0){
				for(String annexDelId : annexDelIdArray){
					annexService.deleteAnnexById(Long.valueOf(annexDelId));
				}
				
			}
		}
		// 根据是否有附件来进行相应的操作
		if(annex == null || annex.getSize()<=0){
			qualification.setChasannex(SystemCommon_Constant.SIGN_YES_0);
		}else{
			List<Annex> list = AnnexFileUpLoad.uploadImageFile(annex, session,0L,OBJECT_TABLE_QUALIFICATION,BUSINESS_DIR_QUALIFICATION,SystemCommon_Constant.ANNEXT_TYPE_IMAGE);
			qualification.setChasannex(SystemCommon_Constant.SIGN_YES_1);
			annexService.saveAnnexList(list,String.valueOf(qualification.getNquaid()));
		}
		// 设置修改人、修改时间、审核人、审核时间
		Long curUserId = (Long)session.getAttribute("userId");
		qualification.setNmodifyid(curUserId);
		qualification.setNauditid(curUserId);
		qualification.setDmodifytime(new Date());
		qualification.setDaudittime(new Date());
		qualificationService.saveOrUpdate(qualification);
		
		// 返回信息
		return success("操作成功", qualification);
	}
	
	/**
	 * 功能描述：删除一条资质信息
	 * 创建时间:2013-10-28下午3:47:39
	 * 创建人: Ryu Zheng
	 * 
	 * @param qualificationId
	 * @return
	 */
	@RequestMapping("/delQualification")
	@ResponseBody
	public Map<String, Object> delQualification(@RequestParam("qualificationId") Long qualificationId){
		EQualification qualification = qualificationService.get(qualificationId);
		qualification.setCvalid(SystemCommon_Constant.VALID_STATUS_0);
		qualificationService.update(qualification);
		
		return success("删除成功");
	}

}
