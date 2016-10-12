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
import com.hive.enterprisemanage.entity.EAwardenterprise;
import com.hive.enterprisemanage.service.AwardEnterpriseService;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：Ryu Zheng
 * <p/>创建日期：2013-10-31
 * <p/>创建时间：下午2:15:43
 * <p/>功能描述：企业获奖信息Controller
 * <p/>===========================================================
 */
@Controller
@RequestMapping("/enterpriseManage")
public class AwardEnterpriseController extends BaseController {
	
	/** 访问前缀：企业获奖信息 */
	private final String PREFIX_AWARD = "enterprisemanage/award";
	/** 表名：企业获奖信息 */
	private static final String OBJECT_TABLE_AWARD = "E_AWARDENTERPRISE";
	/** 附件目录：企业获奖信息 */
	private static final String BUSINESS_DIR_AWARD = SystemCommon_Constant.ENT_QYHJ_06;
	
	/** 企业获奖信息Service */
	@Resource
	private AwardEnterpriseService awardEnterpriseService;
	/** 附件操作Service */
	@Resource
	private AnnexService annexService;
	
	
	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出Task对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getAward(@RequestParam(value = "nawaentid", defaultValue = "-1") Long nawaentid, Model model) {
		if (nawaentid !=null && nawaentid != -1) {
			model.addAttribute("award", awardEnterpriseService.get(nawaentid));
		}
	}
	
	/**
	 * 功能描述：转到企业获奖信息列表页面
	 * 创建时间:2013-10-25下午2:26:29
	 * 创建人: Ryu Zheng
	 * 
	 * @return
	 */
	@RequestMapping("/toAwardList")
	public String toAwardList(@RequestParam(value = "entInfoId", required = false) Long entInfoId, Model model) {
		model.addAttribute("entInfoId", entInfoId);
		return PREFIX_AWARD + "/awardList";
	}
	
	/**
	 * 功能描述：企业获奖信息列表数据加载
	 * 创建时间:2013-10-28下午3:25:02
	 * 创建人: Ryu Zheng
	 * 
	 * @param page
	 * @param entInfoId
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping("/listAward")
	@ResponseBody
	public DataGrid listAward(RequestPage page, @RequestParam(value = "entInfoId", required = false) Long entInfoId) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		return awardEnterpriseService.datagrid(page, entInfoId);
	}
	
	/**
	 * 功能描述：转到企业获奖信息添加页面
	 * 创建时间:2013-10-29上午9:47:48
	 * 创建人: Ryu Zheng
	 * 
	 * @param awardId
	 * @param entInfoId
	 * @param model
	 * @return
	 */
	@RequestMapping("/toAwardAdd")
	public String toAwardAdd(@RequestParam(value = "entInfoId", required = false) Long entInfoId, Model model) {
		EAwardenterprise awardEnterprise = new EAwardenterprise();
		awardEnterprise.setNenterpriseid(entInfoId);
		
		model.addAttribute("awardEnterprise", awardEnterprise);
		return PREFIX_AWARD + "/awardAdd";
	}
	
	/**
	 * 功能描述：保存企业获奖信息
	 * 创建时间:2013-10-29下午5:33:14
	 * 创建人: Ryu Zheng
	 * 
	 * @param annex
	 * @param awardEnterprise
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/saveAward")
	@ResponseBody
	public Map<String, Object> saveAward(
			@RequestParam(value = "annex", required = false) MultipartFile annex,
			EAwardenterprise awardEnterprise, HttpSession session, Model model) {
		
		if(awardEnterprise == null){
			return error("操作失败");
		}
		
		
		// 先将附件上传到服务器上，然后设置获奖信息记录是有附件的，接下来保存获奖信息，最后将附件信息保存到数据库中
		if(annex != null && annex.getSize()>0){
			List<Annex> list = AnnexFileUpLoad.uploadImageFile(annex, session,0L,OBJECT_TABLE_AWARD,BUSINESS_DIR_AWARD,SystemCommon_Constant.ANNEXT_TYPE_IMAGE);
			awardEnterprise.setChasannex(SystemCommon_Constant.SIGN_YES_1);
			// 设置创建人、创建时间、审核人、审核时间
			Long curUserId = (Long)session.getAttribute("userId");
			awardEnterprise.setNcreateid(curUserId);
			awardEnterprise.setNauditid(curUserId);
			awardEnterprise.setDcreatetime(new Date());
			awardEnterprise.setDaudittime(new Date());
			awardEnterpriseService.save(awardEnterprise);
			annexService.saveAnnexList(list,String.valueOf(awardEnterprise.getNawaentid()));
		}else{
			// 设置创建人、创建时间、审核人、审核时间
			Long curUserId = (Long)session.getAttribute("userId");
			awardEnterprise.setNcreateid(curUserId);
			awardEnterprise.setNauditid(curUserId);
			awardEnterprise.setDcreatetime(new Date());
			awardEnterprise.setDaudittime(new Date());
			awardEnterpriseService.save(awardEnterprise);
		}
	
		return success("操作成功", awardEnterprise);
	}
	
	
	/**
	 * 功能描述：转到企业获奖信息编辑页面
	 * 创建时间:2013-10-29下午4:14:45
	 * 创建人: Ryu Zheng
	 * 
	 * @param awardId
	 * @param entInfoId
	 * @param model
	 * @return
	 */
	@RequestMapping("/toAwardEdit")
	public String toAwardEdit(
			@RequestParam(value = "awardId", required = false) Long awardId,
			@RequestParam(value = "entInfoId", required = false) Long entInfoId,
			Model model) {
		
		EAwardenterprise awardEnterprise = null;
		boolean annexExist = false;
		// 当awardId不为空时，说明是编辑操作
		if(awardId != null){
			awardEnterprise = awardEnterpriseService.get(awardId);
			//取得附件信息
			List<Annex> annexList = annexService.getAnnexInfoByObjectIdAndObjectType(awardId, OBJECT_TABLE_AWARD);
			if(annexList != null && annexList.size()>0){
				annexExist = true;
				model.addAttribute("annexFile", annexList.get(0));
			}
			
		}else{
			awardEnterprise = new EAwardenterprise();
			awardEnterprise.setNenterpriseid(entInfoId);
		}
		
		model.addAttribute("annexExist", annexExist);
		model.addAttribute("awardEnterprise", awardEnterprise);
		return PREFIX_AWARD + "/awardEdit";
	}
	
	/**
	 * 功能描述：更新企业获奖信息
	 * 创建时间:2013-11-1上午10:55:39
	 * 创建人: Ryu Zheng
	 * 
	 * @param annex
	 * @param annexDelIds 用户编辑时删除的附件ID数组
	 * @param awardEnterprise
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/updateAward")
	@ResponseBody
	public Map<String, Object> updateAward(
			@RequestParam(value = "annex", required = false) MultipartFile annex,
			@RequestParam(value = "annexDelIds", required = false) String annexDelIds,
			@ModelAttribute("award") EAwardenterprise awardEnterprise, HttpSession session, Model model) {
		
		if(awardEnterprise == null){
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
			awardEnterprise.setChasannex(SystemCommon_Constant.SIGN_YES_0);
		}else{
			List<Annex> list = AnnexFileUpLoad.uploadImageFile(annex, session,0L,OBJECT_TABLE_AWARD,BUSINESS_DIR_AWARD,SystemCommon_Constant.ANNEXT_TYPE_IMAGE);
			awardEnterprise.setChasannex(SystemCommon_Constant.SIGN_YES_1);
			annexService.saveAnnexList(list,String.valueOf(awardEnterprise.getNawaentid()));
		}
		// 设置修改人、修改时间、审核人、审核时间
		Long curUserId = (Long)session.getAttribute("userId");
		awardEnterprise.setNmodifyid(curUserId);
		awardEnterprise.setNauditid(curUserId);
		awardEnterprise.setDmodifytime(new Date());
		awardEnterprise.setDaudittime(new Date());
		awardEnterpriseService.saveOrUpdate(awardEnterprise);
		
		// 返回信息
		return success("操作成功", awardEnterprise);
	}
	
	/**
	 * 功能描述：删除一条获奖信息
	 * 创建时间:2013-10-28下午3:47:39
	 * 创建人: Ryu Zheng
	 * 
	 * @param awardId
	 * @return
	 */
	@RequestMapping("/delAward")
	@ResponseBody
	public Map<String, Object> delAward(@RequestParam("awardId") Long awardId){
		EAwardenterprise award = awardEnterpriseService.get(awardId);
		award.setCvalid(SystemCommon_Constant.VALID_STATUS_0);
		awardEnterpriseService.update(award);
		return success("删除成功");
	}

}
