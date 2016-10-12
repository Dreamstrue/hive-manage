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
import com.hive.enterprisemanage.entity.EEnterpriseproduct;
import com.hive.enterprisemanage.service.EnterpriseProductService;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：Ryu Zheng
 * <p/>创建日期：2013-10-31
 * <p/>创建时间：下午2:23:53
 * <p/>功能描述：企业产品信息Controller
 * <p/>===========================================================
 */
@Controller
@RequestMapping("/enterpriseManage")
public class EnterpriseProductController extends BaseController {
	
	/** 访问前缀：企业产品信息 */
	private final String PREFIX_PRODUCT = "enterprisemanage/product";
	/** 表名：企业产品信息 */
	private static final String OBJECT_TABLE_PRODUCT = "E_ENTERPRISEPRODUCT";
	/** 附件目录：企业产品信息 */
	private static final String BUSINESS_DIR_PRODUCT = SystemCommon_Constant.ENT_QYCP_03;
	
	/** 企业产品信息Service */
	@Resource
	private EnterpriseProductService productService;
	/** 附件操作Service */
	@Resource
	private AnnexService annexService;
	
	
	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出Task对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getProduct(@RequestParam(value = "nentproid", defaultValue = "-1") Long nentproid, Model model) {
		if (nentproid !=null && nentproid != -1) {
			model.addAttribute("product", productService.get(nentproid));
		}
	}

	/**
	 * 功能描述：转到企业产品信息列表页面
	 * 创建时间:2013-10-30下午3:25:59
	 * 创建人: Ryu Zheng
	 * 
	 * @param entInfoId
	 * @param model
	 * @return
	 */
	@RequestMapping("/toProductList")
	public String toProductList(@RequestParam(value = "entInfoId", required = false) Long entInfoId, Model model){
		model.addAttribute("entInfoId", entInfoId);
		return PREFIX_PRODUCT+"/productList";
	}
	
	/**
	 * 功能描述：企业产品信息列表数据加载
	 * 创建时间:2013-10-30下午3:30:21
	 * 创建人: Ryu Zheng
	 * 
	 * @param page
	 * @param entInfoId
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping("/listProduct")
	@ResponseBody
	public DataGrid listProduct(RequestPage page, @RequestParam(value = "entInfoId", required = false) Long entInfoId) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		return productService.datagrid(page, entInfoId);
	}
	
	/**
	 * 功能描述：转到企业产品信息添加页面
	 * 创建时间:2013-11-1下午4:06:37
	 * 创建人: Ryu Zheng
	 * 
	 * @param entInfoId
	 * @param model
	 * @return
	 */
	@RequestMapping("/toProductAdd")
	public String toProductAdd(@RequestParam(value = "entInfoId", required = false) Long entInfoId, Model model) {
		EEnterpriseproduct product = new EEnterpriseproduct();
		product.setNenterpriseid(entInfoId);
		
		model.addAttribute("product", product);
		return PREFIX_PRODUCT + "/productAdd";
	}
	
	/**
	 * 功能描述：保存企业产品信息
	 * 创建时间:2013-10-30下午4:09:40
	 * 创建人: Ryu Zheng
	 * 
	 * @param annex
	 * @param product
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/saveProduct")
	@ResponseBody
	public Map<String, Object> saveProduct(
			@RequestParam(value = "annex", required = false) MultipartFile annex,
			EEnterpriseproduct product, HttpSession session,
			MultipartHttpServletRequest request, Model model) {
		
		if(product == null){
			return error("操作失败");
		}
		
		// 产品类别代码表的处理
		String proCatCode = StringUtils.EMPTY;
		String[] proCats = request.getParameterValues("proCat");
		for (int i = 0; i < proCats.length; i++) {
			if(!proCats[i].equals("")){
				if(i==0){
					proCatCode += proCats[i];
				}else{
					proCatCode += "_"+proCats[i];					
				}
			}
		}
		product.setCprocatcode(proCatCode);
		
		// 先将附件上传到服务器上，然后设置数据是有附件的，接下来保存数据信息，最后将附件信息保存到数据库中
		if(annex != null && annex.getSize()>0){
			List<Annex> list = AnnexFileUpLoad.uploadImageFile(annex, session,0L,OBJECT_TABLE_PRODUCT,BUSINESS_DIR_PRODUCT,SystemCommon_Constant.ANNEXT_TYPE_IMAGE);
			product.setChasannex(SystemCommon_Constant.SIGN_YES_1);
			// 设置创建人、创建时间、审核人、审核时间
			Long curUserId = (Long)session.getAttribute("userId");
			product.setNcreateid(curUserId);
			product.setNauditid(curUserId);
			product.setDcreatetime(new Date());
			product.setDaudittime(new Date());
			productService.save(product);
			annexService.saveAnnexList(list,String.valueOf(product.getNentproid()));
		}else{
			// 设置创建人、创建时间、审核人、审核时间
			Long curUserId = (Long)session.getAttribute("userId");
			product.setNcreateid(curUserId);
			product.setNauditid(curUserId);
			product.setDcreatetime(new Date());
			product.setDaudittime(new Date());
			productService.save(product);
		}
	
		return success("操作成功", product);
	}
	
	/**
	 * 功能描述：转到企业宣传资料编辑页面
	 * 创建时间:2013-10-29下午4:14:45
	 * 创建人: Ryu Zheng
	 * 
	 * @param productId
	 * @param entInfoId
	 * @param model
	 * @return
	 */
	@RequestMapping("/toProductEdit")
	public String toProductEdit(
			@RequestParam(value = "productId", required = false) Long productId,
			@RequestParam(value = "entInfoId", required = false) Long entInfoId,
			Model model) {
		
		EEnterpriseproduct product = null;
		boolean annexExist = false;
		// 当publicizeMaterialId不为空时，说明是编辑操作
		if(productId != null){
			product = productService.get(productId);
			//取得附件信息
			List<Annex> annexList = annexService.getAnnexInfoByObjectIdAndObjectType(productId, "E_ENTERPRISEPRODUCT");
			if(annexList != null && annexList.size()>0){
				annexExist = true;
				model.addAttribute("annexFile", annexList.get(0));
			}
			
		}else{
			product = new EEnterpriseproduct();
			product.setNenterpriseid(entInfoId);
		}
		
		model.addAttribute("annexExist", annexExist);
		model.addAttribute("product", product);
		return PREFIX_PRODUCT + "/productEdit";
	}
	
	/**
	 * 功能描述：更新企业宣传资料
	 * 创建时间:2013-11-1上午10:55:39
	 * 创建人: Ryu Zheng
	 * 
	 * @param annex
	 * @param annexDelIds 用户编辑时删除的附件ID数组
	 * @param product
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/updateProduct")
	@ResponseBody
	public Map<String, Object> updateProduct(
			@RequestParam(value = "annex", required = false) MultipartFile annex,
			@RequestParam(value = "annexDelIds", required = false) String annexDelIds,
			@ModelAttribute("product") EEnterpriseproduct product,
			HttpSession session, MultipartHttpServletRequest request,
			Model model) {
		
		if(product == null){
			return error("操作失败");
		}
		
		// 产品类别代码表的处理
		String proCatCode = StringUtils.EMPTY;
		String[] proCats = request.getParameterValues("proCat");
		for (int i = 0; i < proCats.length; i++) {
			if(!proCats[i].equals("")){
				if(i==0){
					proCatCode += proCats[i];
				}else{
					proCatCode += "_"+proCats[i];					
				}
			}
		}
		product.setCprocatcode(proCatCode);
		
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
			product.setChasannex(SystemCommon_Constant.SIGN_YES_0);
		}else{
			List<Annex> list = AnnexFileUpLoad.uploadImageFile(annex, session,0L,OBJECT_TABLE_PRODUCT,BUSINESS_DIR_PRODUCT,SystemCommon_Constant.ANNEXT_TYPE_IMAGE);
			product.setChasannex(SystemCommon_Constant.SIGN_YES_1);
			annexService.saveAnnexList(list,String.valueOf(product.getNentproid()));
		}
		// 设置修改人、修改时间、审核人、审核时间
		Long curUserId = (Long)session.getAttribute("userId");
		product.setNmodifyid(curUserId);
		product.setNauditid(curUserId);
		product.setDmodifytime(new Date());
		product.setDaudittime(new Date());
		productService.saveOrUpdate(product);
		
		// 返回信息
		return success("操作成功", product);
	}
	
	/**
	 * 功能描述：删除一条产品信息
	 * 创建时间:2013-10-30下午4:25:15
	 * 创建人: Ryu Zheng
	 * 
	 * @param productId
	 * @return
	 */
	@RequestMapping("/delProduct")
	@ResponseBody
	public Map<String, Object> delProduct(@RequestParam("productId") Long productId){
		EEnterpriseproduct product = productService.get(productId);
		product.setCvalid(SystemCommon_Constant.VALID_STATUS_0);
		productService.update(product);
		
		return success("删除成功");
	}
	
}
