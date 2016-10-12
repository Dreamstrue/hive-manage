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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hive.common.AnnexFileUpLoad;
import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Annex;
import com.hive.common.service.AnnexService;
import com.hive.enterprisemanage.entity.EQualitypromise;
import com.hive.enterprisemanage.service.QualityPromiseService;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：Ryu Zheng
 * <p/>创建日期：2013-10-31
 * <p/>创建时间：下午2:32:42
 * <p/>功能描述：企业质量承诺书Controller
 * <p/>===========================================================
 */
@Controller
@RequestMapping("/enterpriseManage")
public class QualityPromiseController extends BaseController {
	
	/** 访问前缀：企业质量承诺书 */
	private final String PREFIX_QUALITY_PROMISE = "enterprisemanage/qualityPromise";
	/** 表名：企业质量承诺书 */
	private static final String OBJECT_TABLE_QUALITY = "E_QUALITYPROMISE";
	/** 附件目录：企业质量承诺书 */
	private static final String BUSINESS_DIR_QUALITY = SystemCommon_Constant.ENT_QYCN_05;
	
	/** 企业质量承诺书Service */
	@Resource
	private QualityPromiseService qualityPromiseService;
	/** 附件操作Service */
	@Resource
	private AnnexService annexService;
	
	
	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出Task对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getQualityPromise(@RequestParam(value = "nquaproid", defaultValue = "-1") Long nquaproid, Model model) {
		if (nquaproid != null && nquaproid != -1) {
			model.addAttribute("qualityPromise", qualityPromiseService.get(nquaproid));
		}
	}
	
	
	/**
	 * 功能描述：转到企业质量承诺书列表页面
	 * 创建时间:2013-10-30下午1:47:40
	 * 创建人: Ryu Zheng
	 * 
	 * @param entInfoId
	 * @param model
	 * @return
	 */
	@RequestMapping("/toQualityPromiseList")
	public String toQualityPromiseList(@RequestParam(value = "entInfoId", required = false) Long entInfoId, Model model) {
		model.addAttribute("entInfoId", entInfoId);
		return PREFIX_QUALITY_PROMISE + "/qualityPromiseList";
	}
	
	/**
	 * 功能描述：企业质量承诺书列表数据加载
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
	@RequestMapping("/listQualityPromise")
	@ResponseBody
	public DataGrid listQualityPromise(RequestPage page, @RequestParam(value = "entInfoId", required = false) Long entInfoId) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		return qualityPromiseService.datagrid(page, entInfoId);
	}
	
	/**
	 * 功能描述：转到企业质量承诺书添加页面
	 * 创建时间:2013-10-30下午2:11:11
	 * 创建人: Ryu Zheng
	 * 
	 * @param entInfoId
	 * @param model
	 * @return
	 */
	@RequestMapping("/toQualityPromiseAdd")
	public String toQualityPromiseAdd(@RequestParam(value = "entInfoId", required = false) Long entInfoId, Model model) {
		EQualitypromise qualityPromise = new EQualitypromise();
		qualityPromise.setNenterpriseid(entInfoId);
		
		model.addAttribute("qualityPromise", qualityPromise);
		return PREFIX_QUALITY_PROMISE + "/qualityPromiseAdd";
	}

	/**
	 * 功能描述：保存企业质量承诺书
	 * 创建时间:2013-10-30下午2:15:34
	 * 创建人: Ryu Zheng
	 * 
	 * @param annex
	 * @param qualityPromise
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/saveQualityPromise")
	@ResponseBody
	public Map<String, Object> saveQualityPromise(
			@RequestParam(value = "annex", required = false) MultipartFile annex,
			EQualitypromise qualityPromise, HttpSession session,
			MultipartHttpServletRequest request, Model model) {
		
		if(qualityPromise == null){
			return error("操作失败");
		}
		
		// 行业分类 代码的处理
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
		qualityPromise.setCindcatcode(indCategoryCode);
		
		// 先将附件上传到服务器上，然后设置资质信息记录是有附件的，接下来保存获奖信息，最后将附件信息保存到数据库中
		if(annex != null && annex.getSize()>0){
			List<Annex> list = AnnexFileUpLoad.uploadImageFile(annex, session,0L,OBJECT_TABLE_QUALITY, BUSINESS_DIR_QUALITY, SystemCommon_Constant.ANNEXT_TYPE_IMAGE);
			qualityPromise.setChasannex(SystemCommon_Constant.SIGN_YES_1);
			// 设置创建人、创建时间、审核人、审核时间
			Long curUserId = (Long)session.getAttribute("userId");
			qualityPromise.setNcreateid(curUserId);
			qualityPromise.setNauditid(curUserId);
			qualityPromise.setDcreatetime(new Date());
			qualityPromise.setDaudittime(new Date());
			qualityPromiseService.save(qualityPromise);
			annexService.saveAnnexList(list,String.valueOf(qualityPromise.getNquaproid()));
		}else{
			// 设置创建人、创建时间、审核人、审核时间
			Long curUserId = (Long)session.getAttribute("userId");
			qualityPromise.setNcreateid(curUserId);
			qualityPromise.setNauditid(curUserId);
			qualityPromise.setDcreatetime(new Date());
			qualityPromise.setDaudittime(new Date());
			qualityPromiseService.save(qualityPromise);
		}
	
		return success("操作成功", qualityPromise);
	}
	
	/**
	 * 功能描述：转到企业质量承诺书编辑页面
	 * 创建时间:2013-11-1上午9:57:54
	 * 创建人: Ryu Zheng
	 * 
	 * @param qualityPromiseId
	 * @param entInfoId
	 * @param model
	 * @return
	 */
	@RequestMapping("/toQualityPromiseEdit")
	public String toQualityPromiseEdit(
			@RequestParam(value = "qualityPromiseId", required = false) Long qualityPromiseId,
			@RequestParam(value = "entInfoId", required = false) Long entInfoId,
			Model model) {
		
		EQualitypromise qualityPromise = null;
		boolean annexExist = false;
		// 当qualityPromiseId不为空时，说明是编辑操作
		if(qualityPromiseId != null){
			qualityPromise = qualityPromiseService.get(qualityPromiseId);
			//取得附件信息
			List<Annex> annexList = annexService.getAnnexInfoByObjectIdAndObjectType(qualityPromiseId, OBJECT_TABLE_QUALITY);
			if(annexList != null && annexList.size()>0){
				annexExist = true;
				model.addAttribute("annexFile", annexList.get(0));
			}
			
		}else{
			qualityPromise = new EQualitypromise();
			qualityPromise.setNenterpriseid(entInfoId);
		}
		
		model.addAttribute("annexExist", annexExist);
		model.addAttribute("qualityPromise", qualityPromise);
		return PREFIX_QUALITY_PROMISE + "/qualityPromiseEdit";
	}
	
	/**
	 * 功能描述：更新企业质量承诺书
	 * 创建时间:2013-11-1上午10:55:39
	 * 创建人: Ryu Zheng
	 * 
	 * @param annex
	 * @param annexDelIds 用户编辑时删除的附件ID数组
	 * @param qualityPromise
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/updateQualityPromise")
	@ResponseBody
	public Map<String, Object> updateQualityPromise(
			@RequestParam(value = "annex", required = false) MultipartFile annex,
			@RequestParam(value = "annexDelIds", required = false) String annexDelIds,
			@ModelAttribute("qualityPromise") EQualitypromise qualityPromise,
			HttpSession session, MultipartHttpServletRequest request,
			Model model) {
		
		if(qualityPromise == null){
			return error("操作失败");
		}
		
		// 行业分类 代码的处理
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
		qualityPromise.setCindcatcode(indCategoryCode);
		
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
			qualityPromise.setChasannex(SystemCommon_Constant.SIGN_YES_0);
		}else{
			List<Annex> list = AnnexFileUpLoad.uploadImageFile(annex, session,0L,OBJECT_TABLE_QUALITY,BUSINESS_DIR_QUALITY,SystemCommon_Constant.ANNEXT_TYPE_IMAGE);
			qualityPromise.setChasannex(SystemCommon_Constant.SIGN_YES_1);
			annexService.saveAnnexList(list,String.valueOf(qualityPromise.getNquaproid()));
		}
		// 设置修改人、修改时间、审核人、审核时间
		Long curUserId = (Long)session.getAttribute("userId");
		qualityPromise.setNmodifyid(curUserId);
		qualityPromise.setNauditid(curUserId);
		qualityPromise.setDmodifytime(new Date());
		qualityPromise.setDaudittime(new Date());
		qualityPromiseService.saveOrUpdate(qualityPromise);
		
		// 返回信息
		return success("操作成功", qualityPromise);
	}
	
	/**
	 * 功能描述：删除一条企业质量承诺书信息
	 * 创建时间:2013-10-28下午3:47:39
	 * 创建人: Ryu Zheng
	 * 
	 * @param qualityPromiseId
	 * @return
	 */
	@RequestMapping("/delQualityPromise")
	@ResponseBody
	public Map<String, Object> delQualityPromise(@RequestParam("qualityPromiseId") Long qualityPromiseId){
		EQualitypromise qualityPromise = qualityPromiseService.get(qualityPromiseId);
		qualityPromise.setCvalid(SystemCommon_Constant.VALID_STATUS_0);
		qualityPromiseService.update(qualityPromise);
		
		return success("删除成功");
	}
	
}
