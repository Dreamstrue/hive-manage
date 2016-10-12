/**
 * 
 */
package com.hive.clientinterface.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.hive.clientinterface.service.EnterpriseInterfaceService;
import com.hive.common.AnnexFileUpLoad;
import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Annex;
import com.hive.common.service.AnnexService;
import com.hive.enterprisemanage.entity.EEnterpriseinfo;
import com.hive.enterprisemanage.entity.EEnterpriseproduct;
import com.hive.enterprisemanage.entity.EnterpriseCustomerGroup;
import com.hive.enterprisemanage.entity.EnterpriseRoomAttention;
import com.hive.enterprisemanage.entity.EnterpriseRoomConsult;
import com.hive.enterprisemanage.entity.EnterpriseRoomDiscount;
import com.hive.enterprisemanage.entity.EnterpriseRoomDynamic;
import com.hive.enterprisemanage.entity.EnterpriseRoomPicture;
import com.hive.enterprisemanage.entity.EnterpriseRoomProduct;
import com.hive.enterprisemanage.entity.EnterpriseRoomReply;
import com.hive.enterprisemanage.entity.EnterpriseRoomReview;
import com.hive.enterprisemanage.entity.EnterpriseRoomShow;
import com.hive.enterprisemanage.model.EnterpriseProductBean;
import com.hive.enterprisemanage.model.EnterpriseSearchBean;
import com.hive.enterprisemanage.model.EnterpriseShowBean;
import com.hive.enterprisemanage.service.EnterpriseInfoService;
import com.hive.membermanage.entity.MMember;
import com.hive.util.DataUtil;
import com.hive.util.DateUtil;
import com.hive.util.FileUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**  
 * Filename: EnterpriseInterfaceController.java  
 * Description:  客户端使用的企业空间接口
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-5-9  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-5-9 上午9:47:56				yanghui 	1.0
 */
@Controller
@RequestMapping("interface/enterQzone")
public class EnterpriseInterfaceController extends BaseController {

	/**
	 * 企业空间service
	 */
	@Resource
	private EnterpriseInterfaceService enterpriseInterfaceService;
	/**
	 * 	企业信息service
	 */
	@Resource
	private EnterpriseInfoService enterpriseInfoService;
	/**
	 * 附件表service
	 */
	@Resource
	private AnnexService annexService;
	/**
	 * 
	 * @Description:  企业秀展示
	 * @author yanghui 
	 * @Created 2014-5-9
	 * @param enterId
	 * @return
	 */
	@RequestMapping(value="enterShow", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> enterShow(@RequestParam(value="enterId") Long enterId,@RequestParam(value="userId",required=false) Long userId){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		EnterpriseShowBean bean = new EnterpriseShowBean();
		
		if(userId!=0L){
			//说明用户登录了，判断是否已关注该企业
			boolean flag = enterpriseInterfaceService.checkAttenStatus(enterId,userId);
			if(flag){
				bean.setAttenStatus(SystemCommon_Constant.SIGN_YES_1);
			}
		}
		
		//企业信息
		EEnterpriseinfo  einfo = null;
		einfo = enterpriseInfoService.get(enterId);
		if(einfo!=null){
			//企业名称
			String enterName = einfo.getCenterprisename();
			//取得企业LOGO（附件表中）
			List<Annex> alist = annexService.getAnnexInfoByObjectIdAndAnnexType(enterId, "ENT_LOGO");
			Annex annex = null;
			String logoPath = "";  //企业LOGO图片路径
			if(alist!=null && alist.size()>0){
				annex = alist.get(0);   
				logoPath = annex.getCfilepath();
			}
			bean.setEnterpriseName(enterName);
			bean.setLogoPath(logoPath);
			 
			//企业秀表信息
			EnterpriseRoomShow entershow = new EnterpriseRoomShow();
			entershow = enterpriseInterfaceService.getEnterpriseRoomShowInfo(enterId);
			if(entershow!=null){
				//取得企业的空间图片
				if(SystemCommon_Constant.SIGN_YES_1.equals(entershow.getIsPicture())){
					List<EnterpriseRoomPicture> plist = enterpriseInterfaceService.getEnterpriseRoomPictureList(entershow.getId());
					if(plist!=null && plist.size()>0){
						bean.setPicList(plist);
					}
				}
				
				bean.setId(entershow.getId());
				bean.setEnterpriseId(entershow.getEnterpriseId());
				//由于企业秀中的企业说明内容存放在磁盘的文件里，这里需要从磁盘里读出文件内容
				byte[] b = AnnexFileUpLoad.getContentFromFile(entershow.getEnterpriseInfo());
				bean.setEnterpriseInfo(new String(b));
		//		bean.setEnterpriseInfo(entershow.getEnterpriseInfo());
				bean.setCreateTime(entershow.getCreateTime());
				bean.setValid(entershow.getValid());
				bean.setHasannex(entershow.getHasannex());
				bean.setIsPicture(entershow.getIsPicture());
				bean.setEnterUserId(entershow.getEnterUserId());
				
				if(SystemCommon_Constant.SIGN_YES_1.equals(entershow.getHasannex())){
					//存在附件
					List<Annex> list = annexService.getAnnexInfoByObjectId(enterId, "E_ENT_ROOM_SHOW");
					if(DataUtil.listIsNotNull(list)){
						bean.setAnnexList(list);
					}
				}
				resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
			}else{
				resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_OTHER);
			}
			
			//判断该企业是否存在新的咨询信息未回复
			boolean flag = enterpriseInterfaceService.checkEnterpriseExistNewConsult(enterId);
			if(flag){
				bean.setIsExistNewConsult(SystemCommon_Constant.SIGN_YES_1);
			}else bean.setIsExistNewConsult(SystemCommon_Constant.SIGN_YES_0);
			
			
			resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
			resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, bean);
		}else{
			resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_FAIL);
			resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "企业信息不存在");
		}
		return resultMap;
	}
	
	
	/**
	 * 
	 * @Description: 编辑企业秀信息
	 * @author YangHui 
	 * @Created 2014-6-5
	 * @param parame
	 * @param files
	 * @return
	 */
	@RequestMapping(value="editEnterShow",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> editEnterShow(HttpServletRequest  request,@RequestParam(value="parame") String parame,@RequestParam(value="count",required=false) int count,@RequestParam(value="delPicId",required=false,defaultValue="") String ids){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<MultipartFile> list = new ArrayList<MultipartFile>();
		if(count>0){
			MultipartHttpServletRequest mr = (MultipartHttpServletRequest)request;
			for(int i=0;i<count;i++){
				list.add((MultipartFile)mr.getFile("picFile"+i));
			}
		}
		Gson gson = new Gson();
		EnterpriseRoomShow show = gson.fromJson(parame, EnterpriseRoomShow.class);
		show.setCreateTime(DateUtil.getTimestamp());
		
		//把企业秀编辑内容保存到文件里
		String content = show.getEnterpriseInfo();
		String filePath = AnnexFileUpLoad.writeContentToFile(content, "enterShow", "","");
		show.setEnterpriseInfo(filePath);
		//修改企业秀描述信息
		enterpriseInterfaceService.saveOrUpdateEnterPriseRoomShow(show);
		
		
		
		//保存企业秀的图片信息
		List<EnterpriseRoomPicture> picList = AnnexFileUpLoad.uploadEnterpriseRoomPicture(show.getId(),list);
		
		enterpriseInterfaceService.saveOrUpdateEnterpriseRoomPictureList(picList,ids);
		
		return resultMap;
	}
	
	
	/**
	 * 
	 * @Description: 企业动态（企业）列表
	 * @author yanghui 
	 * @Created 2014-5-12
	 * @param page
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="enterList", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> enterList(RequestPage page,@RequestParam(value="userId",required=false) Long userId){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		DataGrid dataGrid = enterpriseInterfaceService.getEnterpriseAndDynamic(page,userId);
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_TOTAL, dataGrid.getTotal());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, dataGrid.getRows());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_PAGENO, page.getPage());
		
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 新增企业动态发布
	 * @author YangHui 
	 * @Created 2014-6-16
	 * @param parame
	 * @param files
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="editEnterDynamic",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> editEnterDynamic(HttpServletRequest request,@RequestParam(value="count",required=false) int count,@RequestParam(value="parame") String parame){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		//处理上传的图片
		List<MultipartFile> list = new ArrayList<MultipartFile>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap(); 
		if(fileMap.size()>0){
			for(Map.Entry<String,MultipartFile> entity:fileMap.entrySet()){
				MultipartFile file = entity.getValue();
				list.add(file);
			}
		}
		/*if(count>0){
			MultipartHttpServletRequest mr = (MultipartHttpServletRequest)request; 
			for(int i=0;i<count;i++){
				list.add((MultipartFile)mr.getFile("picFile"+i));
			}
		}*/
		
		
		
		Gson gson = new Gson();
		EnterpriseRoomDynamic bean = gson.fromJson(parame, EnterpriseRoomDynamic.class);
		bean.setCreateTime(DateUtil.getTimestamp());
		if(DataUtil.listIsNotNull(list)){
			bean.setIsPicture(SystemCommon_Constant.SIGN_YES_1);
		}
		enterpriseInterfaceService.saveEnterpriseRoomDynamic(bean);
		//保存企业动态的图片信息
		List<EnterpriseRoomPicture> picList = AnnexFileUpLoad.uploadEnterpriseRoomPicture(bean.getId(),list);
		
		enterpriseInterfaceService.saveOrUpdateEnterpriseRoomPictureList(picList,"");
		
		return resultMap;
	}
	
	
	/**
	 * 
	 * @Description: 企业发布的动态列表以及动态的评论与回复
	 * @author yanghui 
	 * @Created 2014-5-12
	 * @param page
	 * @param enterpriseId
	 * @return
	 */
	@RequestMapping(value="dynamicList", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> dynamicList(RequestPage page,@RequestParam(value="enterpriseId",required=false) Long enterpriseId){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		DataGrid dataGrid = enterpriseInterfaceService.getDynamicList(page,enterpriseId);
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_TOTAL, dataGrid.getTotal());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, dataGrid.getRows());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_PAGENO, page.getPage());
		
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 保存评论信息
	 * @author yanghui 
	 * @Created 2014-5-13
	 * @param prizeorder
	 * @return
	 */
	@RequestMapping(value="addReview", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addReview(@RequestParam(value="review") String prizeorder){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Gson gson = new Gson();
		EnterpriseRoomReview review  = gson.fromJson(prizeorder,EnterpriseRoomReview.class);
		review.setCreateTime(DateUtil.getTimestamp());
		review.setShieldStatus(SystemCommon_Constant.REVIEW_SHIELD_STATUS_0);
		review.setIsPicture(SystemCommon_Constant.SIGN_YES_0);
		
		enterpriseInterfaceService.saveReview(review);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, review);
		
		return resultMap;
	}
	
	
	@RequestMapping(value="addReply", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addReply(@RequestParam(value="reply") String prizeorder){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Gson gson = new Gson();
		EnterpriseRoomReply reply  = gson.fromJson(prizeorder,EnterpriseRoomReply.class);
		reply.setCreateTime(DateUtil.getTimestamp());
		reply.setShieldStatus(SystemCommon_Constant.REVIEW_SHIELD_STATUS_0);
		reply.setIsPicture(SystemCommon_Constant.SIGN_YES_0);
		
		enterpriseInterfaceService.saveReply(reply);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, reply);
		
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 企业产品发布   新增产品发布    产品列表（企业存在，但没有被发布的）
	 * @author YangHui 
	 * @Created 2014-6-12
	 * @param enterpriseId
	 * @return
	 */
	@RequestMapping(value="queryProductList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> queryPorductList(@RequestParam(value="enterpriseId") Long enterpriseId){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		List<EEnterpriseproduct> list = enterpriseInterfaceService.getProductListNotInRoom(enterpriseId);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, list);
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 企业新增产品展示信息
	 * @author YangHui 
	 * @Created 2014-6-13
	 * @param parame
	 * @param files 上传的产品图片
	 * @return
	 */
	@RequestMapping(value="saveEnterRoomProduct",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveEnterRoomProduct(HttpServletRequest request,@RequestParam(value="enterpriseProduct") String parame,@RequestParam(value="count",required=false) int count){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		List<MultipartFile> list = new ArrayList<MultipartFile>();
		if(count>0){
			MultipartHttpServletRequest mr = (MultipartHttpServletRequest)request;
			for(int i=0;i<count;i++){
				list.add((MultipartFile)mr.getFile("picFile"+i));
			}
		}
		
		
		Gson gson = new Gson();
		EnterpriseRoomProduct pro = gson.fromJson(parame, EnterpriseRoomProduct.class);
		pro.setCreateTime(DateUtil.getTimestamp());
		if(DataUtil.listIsNotNull(list)){
			pro.setIsPicture(SystemCommon_Constant.SIGN_YES_1);
		}
		enterpriseInterfaceService.saveEnterpriseRoomProduct(pro);
		if(DataUtil.listIsNotNull(list)){
			//保存图片信息
			List<EnterpriseRoomPicture> picList = AnnexFileUpLoad.uploadEnterpriseRoomPicture(pro.getId(),list);
			enterpriseInterfaceService.saveOrUpdateEnterpriseRoomPictureList(picList,"");
		}
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, pro);
		return resultMap;
	}
	
	
	/**
	 * 
	 * @Description:企业修改产品展示信息
	 * @author YangHui 
	 * @Created 2014-6-18
	 * @param parame
	 * @param files
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="editEnterRoomProduct",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> editEnterRoomProduct(HttpServletRequest request,@RequestParam(value="enterpriseProduct") String parame,@RequestParam(value="count",required=false) int count,@RequestParam(value="delPicId",required=false,defaultValue="") String ids){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		List<MultipartFile> list = new ArrayList<MultipartFile>();
		if(count>0){
			MultipartHttpServletRequest mr = (MultipartHttpServletRequest)request;
			for(int i=0;i<count;i++){
				list.add((MultipartFile)mr.getFile("picFile"+i));
			}
		}
		
		Gson gson = new Gson();
		EnterpriseRoomProduct pro = gson.fromJson(parame, EnterpriseRoomProduct.class);
		if(DataUtil.listIsNotNull(list)){
			pro.setIsPicture(SystemCommon_Constant.SIGN_YES_1);
		}
		enterpriseInterfaceService.updateEnterpriseRoomProduct(pro);
		if(DataUtil.listIsNotNull(list)){
			//保存图片信息
			List<EnterpriseRoomPicture> picList = AnnexFileUpLoad.uploadEnterpriseRoomPicture(pro.getId(),list);
			enterpriseInterfaceService.saveOrUpdateEnterpriseRoomPictureList(picList,ids);
		}
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, pro);
		return resultMap;
	}
	
	 
	/**
	 * 
	 * @Description:  产品列表
	 * @author yanghui 
	 * @Created 2014-5-13
	 * @param page
	 * @param enterpriseId
	 * @return
	 */
	@RequestMapping(value="productList", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> productList(RequestPage page,@RequestParam(value="enterpriseId",required=false) Long enterpriseId){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		DataGrid dataGrid = enterpriseInterfaceService.getProductList(page,enterpriseId);
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_TOTAL, dataGrid.getTotal());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, dataGrid.getRows());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_PAGENO, page.getPage());
		
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 产品详情
	 * @author yanghui 
	 * @Created 2014-5-13
	 * @param id
	 * @return
	 */
	@RequestMapping(value="productDetail",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> productDetail(@RequestParam(value="id") Long id){
		
		EnterpriseProductBean bean = enterpriseInterfaceService.findProductDetail(id);
		
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, bean);
		return resultMap;
	}
	
	
	/**
	 * 
	 * @Description:  优惠打折列表
	 * @author yanghui 
	 * @Created 2014-5-13
	 * @param page
	 * @param enterpriseId
	 * @return
	 */
	@RequestMapping(value="discountList", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> discountList(RequestPage page,@RequestParam(value="enterpriseId",required=false) Long enterpriseId,@RequestParam(value="searchMerchant",required=false) String parame){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Gson gson = new Gson();
//		String consult="{\"mNum\":5000,\"longitude\":\"113.583196\",\"latitude\":\"34.812821\",\"productCategoryId\":8,\"sortType\":\"1\"}";
		EnterpriseSearchBean bean  = gson.fromJson(parame,EnterpriseSearchBean.class);
		
		DataGrid dataGrid = enterpriseInterfaceService.getDiscountList(page,enterpriseId,bean);
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_TOTAL, dataGrid.getTotal());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, dataGrid.getRows());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_PAGENO, page.getPage());
		
		return resultMap;
	}
	
	
	/**
	 * 
	 * @Description: 企业发布产品打折优惠信息时需要的产品列表（企业存在，但没有被发布为优惠打折的）
	 * @author YangHui 
	 * @Created 2014-6-12
	 * @param enterpriseId
	 * @return
	 */
	@RequestMapping(value="queryDiscountProductList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> queryDiscountProductList(@RequestParam(value="enterpriseId") Long enterpriseId){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		List<EEnterpriseproduct> list = enterpriseInterfaceService.getProductListNotInRoomDiscount(enterpriseId);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, list);
		return resultMap;
	}
	
	
	/**
	 * 
	 * @Description: 保存新发布的产品打折优惠信息
	 * @author YangHui 
	 * @Created 2014-6-13
	 * @param parame
	 * @param files
	 * @return
	 */
	@RequestMapping(value="saveEnterRoomDiscount",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveEnterRoomDiscount(HttpServletRequest request,@RequestParam(value="enterpriseProduct") String parame,@RequestParam(value="count",required=false) int count){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		List<MultipartFile> list = new ArrayList<MultipartFile>();
		if(count>0){
			MultipartHttpServletRequest mr = (MultipartHttpServletRequest)request;
			for(int i=0;i<count;i++){
				list.add((MultipartFile)mr.getFile("picFile"+i));
			}
		}
		Gson gson = new Gson();
		EnterpriseRoomDiscount pro = gson.fromJson(parame, EnterpriseRoomDiscount.class);
		pro.setCreateTime(DateUtil.getTimestamp());
		if(DataUtil.listIsNotNull(list)){
			pro.setIsPicture(SystemCommon_Constant.SIGN_YES_1);
		}
		enterpriseInterfaceService.saveEnterpriseRoomDiscount(pro);
		if(DataUtil.listIsNotNull(list)){
			//保存图片信息
			List<EnterpriseRoomPicture> picList = AnnexFileUpLoad.uploadEnterpriseRoomPicture(pro.getId(),list);
			enterpriseInterfaceService.saveOrUpdateEnterpriseRoomPictureList(picList,"");
		}
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, pro);
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 修改产品打折优惠信息
	 * @author YangHui 
	 * @Created 2014-6-18
	 * @param parame
	 * @param files
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="editEnterRoomDiscount",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> editEnterRoomDiscount(HttpServletRequest request,@RequestParam(value="enterpriseProduct") String parame,@RequestParam(value="count",required=false) int count,@RequestParam(value="delPicId",required=false,defaultValue="") String ids){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		List<MultipartFile> list = new ArrayList<MultipartFile>();
		if(count>0){
			MultipartHttpServletRequest mr = (MultipartHttpServletRequest)request;
			for(int i=0;i<count;i++){
				list.add((MultipartFile)mr.getFile("picFile"+i));
			}
		}
		
		Gson gson = new Gson();
 		EnterpriseRoomDiscount pro = gson.fromJson(parame, EnterpriseRoomDiscount.class);
		if(DataUtil.listIsNotNull(list)){
			pro.setIsPicture(SystemCommon_Constant.SIGN_YES_1);
		}
		enterpriseInterfaceService.updateEnterpriseRoomDiscount(pro); 
		if(DataUtil.listIsNotNull(list)){
			//保存图片信息
			List<EnterpriseRoomPicture> picList = AnnexFileUpLoad.uploadEnterpriseRoomPicture(pro.getId(),list);
			enterpriseInterfaceService.saveOrUpdateEnterpriseRoomPictureList(picList,ids);
		}
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, pro);
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 优惠打折详情
	 * @author yanghui 
	 * @Created 2014-5-13
	 * @param id
	 * @return
	 */
	@RequestMapping(value="discountDetail",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> discountDetail(@RequestParam(value="id") Long id){
		
		EnterpriseProductBean bean = enterpriseInterfaceService.findDiscountDetail(id);
		
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, bean);
		return resultMap;
	}
	
	
	
	/**
	 * 
	 * @Description:  寻找商家 
	 * @author yanghui 
	 * @Created 2014-5-14
	 * @param mNum  距离
	 * @param longitude  经度
	 * @param latitude  纬度
	 * @param productCategoryId 产品类别ID 
	 * @param sortType  排序
	 * @return
	 */
	@RequestMapping(value="searchEnter",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> searchEnterprise(RequestPage page,@RequestParam(value="searchMerchant") String parame){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		Gson gson = new Gson();
	//	String consult="{\"mNum\":5000,\"longitude\":\"113.583196\",\"latitude\":\"34.812821\",\"productCategoryId\":7,\"sortType\":\"1\"}";
		EnterpriseSearchBean bean  = gson.fromJson(parame,EnterpriseSearchBean.class);
		
		
		DataGrid dataGrid = enterpriseInterfaceService.getEnterpriseInfo(page,bean);
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_TOTAL, dataGrid.getTotal());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, dataGrid.getRows());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_PAGENO, page.getPage());
		
		
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 根据商家姓名模糊查询 
	 * @author YangHui 
	 * @Created 2014-6-16
	 * @param page
	 * @param parame
	 * @return
	 */
	@RequestMapping(value="searchEnterByName",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> searchEnterByName(RequestPage page,@RequestParam(value="searchMerchant") String parame){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		Gson gson = new Gson();
		EnterpriseSearchBean bean  = gson.fromJson(parame,EnterpriseSearchBean.class);
		DataGrid dataGrid = enterpriseInterfaceService.getEnterpriseInfoByName(page,bean);
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_TOTAL, dataGrid.getTotal());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, dataGrid.getRows());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_PAGENO, page.getPage());
		
		
		return resultMap;
	}
	
	
	/**
	 * 
	 * @Description: 添加关注
	 * @author yanghui 
	 * @Created 2014-5-14
	 * @return
	 */
	@RequestMapping(value="addAttention",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addAttention(EnterpriseRoomAttention atten){
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		atten.setAttentionTime(DateUtil.getTimestamp()); 
		enterpriseInterfaceService.saveAttention(atten);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, atten);
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 取消关注
	 * @author yanghui 
	 * @Created 2014-5-14
	 * @return
	 */
	@RequestMapping(value="delAttention",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteAttention(EnterpriseRoomAttention atten){
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		atten = enterpriseInterfaceService.getAttenByEnterIdAndUserId(atten.getEnterpriseId(),atten.getUserId());
		if(!DataUtil.isNull(atten.getId())){
			enterpriseInterfaceService.deleteAtten(atten);
		}
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 用户咨询
	 * @author yanghui 
	 * @Created 2014-5-20
	 * @param atten
	 * @return
	 */
	@RequestMapping(value="consult",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> userConsult(@RequestParam(value="consult") String parame){
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Gson gson = new Gson();
		EnterpriseRoomConsult consult = gson.fromJson(parame,EnterpriseRoomConsult.class);

		consult.setConsultTime(DateUtil.getTimestamp());
		consult.setShieldStatus(SystemCommon_Constant.REVIEW_SHIELD_STATUS_0); // 默认 0-未屏蔽
		enterpriseInterfaceService.saveConsult(consult);
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, consult);
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 针对企业的咨询和回复列表
	 * @author yanghui 
	 * @Created 2014-5-26
	 * @param enterId
	 * @return
	 */
	@RequestMapping(value="consultList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> consultList(RequestPage page,@RequestParam(value="enterpriseId") Long enterId){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		DataGrid dataGrid = enterpriseInterfaceService.getConsultList(page,enterId);
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_TOTAL, dataGrid.getTotal());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, dataGrid.getRows());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_PAGENO, page.getPage());
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 企业客户管理（关注该企业的客户）
	 * @author yanghui 
	 * @Created 2014-5-28
	 * @param page
	 * @param enterId
	 * @return
	 */
	@RequestMapping(value="customerManage",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> costomerManage(RequestPage page,@RequestParam(value="enterpriseId") Long enterId){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		DataGrid dataGrid = enterpriseInterfaceService.getCustomerList(page,enterId);
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_TOTAL, dataGrid.getTotal());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, dataGrid.getRows());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_PAGENO, page.getPage());
		return resultMap;
	}
	
	
	/**
	 * 
	 * @Description: 企业屏蔽用户
	 * @author yanghui 
	 * @Created 2014-5-29
	 * @param enterId
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="shieldCustomer",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> shieldCustomer(@RequestParam(value="enterpriseId") Long enterId,@RequestParam(value="userId") Long userId,@RequestParam(value="type") String type){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		enterpriseInterfaceService.updateEnterpriseCustomer(enterId,userId,type);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 企业的分组列表
	 * @author yanghui 
	 * @Created 2014-5-29
	 * @param enterId
	 * @param type （如果type=1,查询分组及组别下的用户;type=0或者type=null，只查询分组信息）
	 * @return
	 */
	@RequestMapping(value="groupList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> groupList(RequestPage page,@RequestParam(value="enterpriseId") Long enterId,@RequestParam(value="type",required=false) String type){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		DataGrid dataGrid = enterpriseInterfaceService.getGroupListByEnterId(page,enterId,type);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_TOTAL, dataGrid.getTotal());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, dataGrid.getRows());
		resultMap.put(SystemCommon_Constant.RESULT_KEY_PAGENO, page.getPage());
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 企业新增分组
	 * @author yanghui 
	 * @Created 2014-5-29
	 * @param parame
	 * @return
	 */
	@RequestMapping(value="addGroup",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addGroup(@RequestParam(value="parame") String parame){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Gson gson = new Gson();
		EnterpriseCustomerGroup g = gson.fromJson(parame, EnterpriseCustomerGroup.class);
		
		enterpriseInterfaceService.saveOrUpdateCustomerGroup(g,"add");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, g);
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 企业修改分组
	 * @author yanghui 
	 * @Created 2014-5-29
	 * @param parame
	 * @return
	 */
	@RequestMapping(value="updateGroup",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateGroup(@RequestParam(value="parame") String parame){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Gson gson = new Gson();
		EnterpriseCustomerGroup g = gson.fromJson(parame, EnterpriseCustomerGroup.class);
		
		enterpriseInterfaceService.saveOrUpdateCustomerGroup(g,"update");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, g);
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 企业删除分组
	 * @author yanghui 
	 * @Created 2014-5-29
	 * @param parame
	 * @return
	 */
	@RequestMapping(value="deleteGroup",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> deleteGroup(@RequestParam(value="id") Long id){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		enterpriseInterfaceService.deleteCustomerGroup(id);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 把用户移动到另一个分组
	 * @author YangHui 
	 * @Created 2014-6-3
	 * @param userId
	 * @param groupId
	 * @return
	 */
	@RequestMapping(value="moveGroup",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> moveGroup(@RequestParam(value="userId") Long userId,@RequestParam(value="newGroupId") Long newGroupId,@RequestParam(value="oldGroupId",required=false) Long oldGroupId){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		enterpriseInterfaceService.updateCustomerGroup(userId,newGroupId,oldGroupId);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		return resultMap;
	}
	
	
	
	//------------------企业多用户管理接口   2014-08-14   开始--------------------------------------------------
	
	/**
	 * 
	 * @Description: 企业多用户列表
	 * @author YangHui 
	 * @Created 2014-8-14
	 * @param enterpriseId
	 * @return
	 */
	@RequestMapping(value="userList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> userList(@RequestParam(value="enterpriseId") Long enterpriseId){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		List<MMember> mlist = enterpriseInterfaceService.getUserListByEnterpriseId(enterpriseId);
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA,mlist);
		return resultMap;
	}
	
	
	/**
	 * 
	 * @Description: 管理员对其他用户的禁用和启用
	 * @author YangHui 
	 * @Created 2014-8-14
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="isValid",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> isValid(@RequestParam(value="userId") Long userId,@RequestParam(value="type") String type){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		enterpriseInterfaceService.updateMemberIsValid(userId,type);
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		return resultMap;
	}
	
	
	/**
	 * 
	 * @Description:管理员角色转让
	 * @author YangHui 
	 * @Created 2014-8-14
	 * @param userId 转让的用户
	 * @param transUserId 被转让用户
	 * @return
	 */
	@RequestMapping(value="transfer",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> transfer(@RequestParam(value="userId") Long userId,@RequestParam(value="transUserId") Long transUserId){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		enterpriseInterfaceService.updateMemberIsManager(userId,transUserId);
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 企业下的管理员用户审核非管理员用户
	 * @author YangHui 
	 * @Created 2014-8-18
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="approve",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> approve(@RequestParam(value="userId") Long userId){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		enterpriseInterfaceService.updateMemberStatus(userId);
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		return resultMap;
	}
	
	
	
	
	//------------------企业多用户管理接口      2014-08-14   结束--------------------------------------------------
	
	
}
