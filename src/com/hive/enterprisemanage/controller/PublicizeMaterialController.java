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
import com.hive.enterprisemanage.entity.EPublicizeMaterial;
import com.hive.enterprisemanage.service.PublicizeMaterialService;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：Ryu Zheng
 * <p/>创建日期：2013-10-31
 * <p/>创建时间：下午2:27:14
 * <p/>功能描述：企业宣传资料Controller
 * <p/>===========================================================
 */
@Controller
@RequestMapping("/enterpriseManage")
public class PublicizeMaterialController extends BaseController {

	/** 访问前缀：企业宣传资料信息 */
	private final String PREFIX_PUBLICIZE_MATERIAL = "enterprisemanage/publicizeMaterial";
	/** 表名：企业宣传资料 */
	private static final String OBJECT_TABLE_MATERIAL = "E_PUBLICIZEMATERIAL";
	/** 附件目录：企业宣传资料 */
	private static final String BUSINESS_DIR_MATERIAL = SystemCommon_Constant.ENT_QYXC_04;
	
	/** 企业宣传资料Service */
	@Resource
	private PublicizeMaterialService publicizeMaterialService;
	/** 附件操作Service */
	@Resource
	private AnnexService annexService;
	
	
	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出Task对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getPublicizeMaterial(@RequestParam(value = "npubmatid", defaultValue = "-1") Long npubmatid, Model model) {
		if (npubmatid !=null && npubmatid != -1) {
			model.addAttribute("publicizeMaterial", publicizeMaterialService.get(npubmatid));
		}
	}

	/**
	 * 功能描述：转到企业宣传资料列表页面 
	 * 创建时间:2013-10-30下午2:47:38
	 * 创建人: Ryu Zheng
	 * 
	 * @param entInfoId
	 * @param model
	 * @return
	 */
	@RequestMapping("/toPublicizeMaterialList")
	public String toPublicizeMaterialList(@RequestParam(value = "entInfoId", required = false) Long entInfoId, Model model) {
		model.addAttribute("entInfoId", entInfoId);
		return PREFIX_PUBLICIZE_MATERIAL + "/publicizeMaterialList";
	}
	
	/**
	 * 功能描述：企业宣传资料列表数据加载
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
	@RequestMapping("/listPublicizeMaterial")
	@ResponseBody
	public DataGrid listPublicizeMaterial(RequestPage page, @RequestParam(value = "entInfoId", required = false) Long entInfoId) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		return publicizeMaterialService.datagrid(page, entInfoId);
	}
	
	/**
	 * 功能描述：转到企业宣传资料添加页面
	 * 创建时间:2013-11-1下午3:37:06
	 * 创建人: Ryu Zheng
	 * 
	 * @param entInfoId
	 * @param model
	 * @return
	 */
	@RequestMapping("/toPublicizeMaterialAdd")
	public String toPublicizeMaterialAdd(@RequestParam(value = "entInfoId", required = false) Long entInfoId, Model model) {
		EPublicizeMaterial publicizeMaterial = new EPublicizeMaterial();
		publicizeMaterial.setNenterpriseid(entInfoId);
		
		model.addAttribute("publicizeMaterial", publicizeMaterial);
		return PREFIX_PUBLICIZE_MATERIAL + "/publicizeMaterialAdd";
	}

	/**
	 * 功能描述：保存企业宣传资料
	 * 创建时间:2013-10-30上午10:42:07
	 * 创建人: Ryu Zheng
	 * 
	 * @param annex
	 * @param qualification
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/savePublicizeMaterial")
	@ResponseBody
	public Map<String, Object> savePublicizeMaterial(
			@RequestParam(value = "annex", required = false) MultipartFile annex,
			EPublicizeMaterial publicizeMaterial, HttpSession session, Model model) {
		
		if(publicizeMaterial == null){
			return error("操作失败");
		}
		// 先将附件上传到服务器上，然后设置业务数据是有附件的，接下来保存获奖信息，最后将附件信息保存到数据库中
		if(annex != null && annex.getSize()>0){
			List<Annex> list = AnnexFileUpLoad.uploadImageFile(annex, session,0L,OBJECT_TABLE_MATERIAL,BUSINESS_DIR_MATERIAL,SystemCommon_Constant.ANNEXT_TYPE_IMAGE);
			publicizeMaterial.setChasannex(SystemCommon_Constant.SIGN_YES_1);
			// 设置创建人、创建时间、审核人、审核时间
			Long curUserId = (Long)session.getAttribute("userId");
			publicizeMaterial.setNcreateid(curUserId);
			publicizeMaterial.setNauditid(curUserId);
			publicizeMaterial.setDcreatetime(new Date());
			publicizeMaterial.setDaudittime(new Date());
			publicizeMaterialService.save(publicizeMaterial);
			annexService.saveAnnexList(list,String.valueOf(publicizeMaterial.getNpubmatid()));
		}else{
			// 设置创建人、创建时间、审核人、审核时间
			Long curUserId = (Long)session.getAttribute("userId");
			publicizeMaterial.setNcreateid(curUserId);
			publicizeMaterial.setNauditid(curUserId);
			publicizeMaterial.setDcreatetime(new Date());
			publicizeMaterial.setDaudittime(new Date());
			publicizeMaterialService.save(publicizeMaterial);
		}
	
		return success("操作成功", publicizeMaterial);
	}
	
	/**
	 * 功能描述：转到企业宣传资料编辑页面
	 * 创建时间:2013-10-29下午4:14:45
	 * 创建人: Ryu Zheng
	 * 
	 * @param publicizeMaterialId
	 * @param entInfoId
	 * @param model
	 * @return
	 */
	@RequestMapping("/toPublicizeMaterialEdit")
	public String toPublicizeMaterialEdit(
			@RequestParam(value = "publicizeMaterialId", required = false) Long publicizeMaterialId,
			@RequestParam(value = "entInfoId", required = false) Long entInfoId,
			Model model) {
		
		EPublicizeMaterial publicizeMaterial = null;
		boolean annexExist = false;
		// 当publicizeMaterialId不为空时，说明是编辑操作
		if(publicizeMaterialId != null){
			publicizeMaterial = publicizeMaterialService.get(publicizeMaterialId);
			//取得附件信息
			List<Annex> annexList = annexService.getAnnexInfoByObjectIdAndObjectType(publicizeMaterialId, "E_PUBLICIZEMATERIAL");
			if(annexList != null && annexList.size()>0){
				annexExist = true;
				model.addAttribute("annexFile", annexList.get(0));
			}
			
		}else{
			publicizeMaterial = new EPublicizeMaterial();
			publicizeMaterial.setNenterpriseid(entInfoId);
		}
		
		model.addAttribute("annexExist", annexExist);
		model.addAttribute("publicizeMaterial", publicizeMaterial);
		return PREFIX_PUBLICIZE_MATERIAL + "/publicizeMaterialEdit";
	}
	
	/**
	 * 功能描述：更新企业宣传资料
	 * 创建时间:2013-11-1上午10:55:39
	 * 创建人: Ryu Zheng
	 * 
	 * @param annex
	 * @param annexDelIds 用户编辑时删除的附件ID数组
	 * @param publicizeMaterial
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/updatePublicizeMaterial")
	@ResponseBody
	public Map<String, Object> updatePublicizeMaterial(
			@RequestParam(value = "annex", required = false) MultipartFile annex,
			@RequestParam(value = "annexDelIds", required = false) String annexDelIds,
			@ModelAttribute("publicizeMaterial") EPublicizeMaterial publicizeMaterial, HttpSession session, Model model) {
		
		if(publicizeMaterial == null){
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
			publicizeMaterial.setChasannex(SystemCommon_Constant.SIGN_YES_0);
		}else{
			List<Annex> list = AnnexFileUpLoad.uploadImageFile(annex, session,0L,OBJECT_TABLE_MATERIAL,BUSINESS_DIR_MATERIAL,SystemCommon_Constant.ANNEXT_TYPE_IMAGE);
			publicizeMaterial.setChasannex(SystemCommon_Constant.SIGN_YES_1);
			annexService.saveAnnexList(list,String.valueOf(publicizeMaterial.getNpubmatid()));
		}
		// 设置修改人、修改时间、审核人、审核时间
		Long curUserId = (Long)session.getAttribute("userId");
		publicizeMaterial.setNmodifyid(curUserId);
		publicizeMaterial.setNauditid(curUserId);
		publicizeMaterial.setDmodifytime(new Date());
		publicizeMaterial.setDaudittime(new Date());
		publicizeMaterialService.saveOrUpdate(publicizeMaterial);
		
		// 返回信息
		return success("操作成功", publicizeMaterial);
	}
	
	/**
	 * 功能描述：删除一条企业宣传资料信息
	 * 创建时间:2013-11-1下午3:36:33
	 * 创建人: Ryu Zheng
	 * 
	 * @param publicizeMaterialId
	 * @return
	 */
	@RequestMapping("/delPublicizeMaterial")
	@ResponseBody
	public Map<String, Object> delPublicizeMaterial(@RequestParam("publicizeMaterialId") Long publicizeMaterialId){
		EPublicizeMaterial publicizeMaterial = publicizeMaterialService.get(publicizeMaterialId);
		publicizeMaterial.setCvalid(SystemCommon_Constant.VALID_STATUS_0);
		publicizeMaterialService.update(publicizeMaterial);
		
		return success("删除成功");
	}
	
}
