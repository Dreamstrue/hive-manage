/**
 * 
 */
package com.hive.integralmanage.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.crypto.RuntimeCryptoException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.common.AnnexFileUpLoad;
import com.hive.common.SystemCommon_Constant;
import com.hive.evaluationManage.model.EvaluationVo;
import com.hive.evaluationManage.service.EvaluationService;
import com.hive.integralmanage.entity.CashPrizeInfo;
import com.hive.integralmanage.entity.Integral;
import com.hive.integralmanage.entity.IntegralOil;
import com.hive.integralmanage.entity.IntegralOilSub;
import com.hive.integralmanage.entity.IntegralSub;
import com.hive.integralmanage.entity.WinPrizeInfo;
import com.hive.integralmanage.model.IntegralCategoryBean;
import com.hive.integralmanage.service.CashPrizeInfoService;
import com.hive.integralmanage.service.IntegralOilService;
import com.hive.integralmanage.service.IntegralOilSubService;
import com.hive.integralmanage.service.IntegralService;
import com.hive.integralmanage.service.WinPrizeInfoService;
import com.hive.intendmanage.entity.Intend;
import com.hive.intendmanage.entity.IntendRelPrize;
import com.hive.intendmanage.model.IntendBean;
import com.hive.intendmanage.service.IntendRelPrizeService;
import com.hive.intendmanage.service.IntendService;
import com.hive.membermanage.entity.MMember;
import com.hive.permissionmanage.entity.User;
import com.hive.permissionmanage.service.DepartmentService;
import com.hive.prizemanage.entity.PrizeInfo;
import com.hive.prizemanage.service.PrizeInfoService;
import com.hive.tagManage.entity.SNBase;
import com.hive.tagManage.service.TagSNBaseService;
import com.hive.util.DateUtil;
import com.hive.util.PropertiesFileUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**  
 * Filename: IntegralController.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-11  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-11 上午9:32:47				yanghui 	1.0
 */
@Controller
@RequestMapping("integralOil")
public class IntegralOilController extends BaseController {

	private static final String PREFIX = "integralOilmanage";
	
	@Resource
	private IntegralService integralService;
	@Resource
	private IntegralOilService integralOilService;
	@Resource
	private IntegralOilSubService integralOilSubService;
	@Resource
	private IntendService intendService; //订单service
	@Resource
	private PrizeInfoService prizeInfoService;
	@Resource
	private IntendRelPrizeService intendRelPrizeService;
	@Resource
	private TagSNBaseService snBaseService;
	@Resource
	private CashPrizeInfoService cashprizeInfoService;
	@Resource
	private EvaluationService evaluationService;
	
	@Resource
	private WinPrizeInfoService winPrizeInfoService;
	
	@Resource
	private DepartmentService departmentService;
	
	/**
	 * 
	 * @Description: 加油站积分管理页面
	 * @author yanghui 
	 * @Created 2014-3-11
	 * @return
	 */
	@RequestMapping("oilmanage")
	public String oilmanage(){
		return PREFIX+"/integralOilManage";
	}
	
	
	/**
	 * 
	 * @Description: 已消费积分统计页面
	 * @author yanghui 
	 * @Created 2014-3-11
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping("usedDetail")
	public String usedDetail(Model model,@RequestParam(value="userId") Long userId){
		model.addAttribute("userId", userId);
		return PREFIX+"/usedIntegralManage";
	}
	
	/**
	 * 
	 * @Description: 已消费积分统计方法
	 * @author yanghui 
	 * @Created 2014-3-11
	 * @param page
	 * @param userId
	 * @return
	 */
	@RequestMapping("usedIntegralDataGrid")
	@ResponseBody
	public DataGrid usedIntegralDataGrid(RequestPage page,@RequestParam(value="userId") Long userId){
		return integralOilService.usedIntegralDataGrid(page,userId);
	}
	/**
	 * 
	 * @Description: 加油站积分管理统计方法
	 * @author zhaopf
	 * @param page
	 * @param userName
	 * @return
	 */
	@RequestMapping("integralOilDataGrid")
	@ResponseBody
	public DataGrid integralOilDataGrid(RequestPage page,MMember mmember){
		return integralOilService.integralOilDataGrid(page,mmember);
	}
	
	/**
	 * 
	 * @Description: 添加积分总表记录
	 * @author yanghui 
	 * @Created 2014-3-11
	 * @param integral
	 * @return
	 */
	@RequestMapping("insertIntegral")
	@ResponseBody
	public Map<String,Object> insertIntegral(IntegralOil integral){
		 
		integralOilService.save(integral);
		return success("添加成功");
	}
	
	/**
	 * 
	 * @Description: 修改积分总表记录
	 * @author yanghui 
	 * @Created 2014-3-11
	 * @param integral
	 * @return
	 */
	@RequestMapping("updateIntegral")
	@ResponseBody
	public Map<String,Object> updateIntegral(IntegralOil integral){
		 
		integralOilService.update(integral);
		return success("添加成功");
	}
	
	/**
	 * 
	 * @Description:  添加积分子表（明细）记录
	 * @author yanghui 
	 * @Created 2014-3-11
	 * @param sub
	 * @return
	 */
	@RequestMapping("insertIntegralSub")
	@ResponseBody
	public Map<String,Object> insertIntegralSub(IntegralOilSub sub){
		
		integralOilSubService.save(sub);
		//积分明细表保存后，需要修改该用户对应的总表信息
		IntegralOil integral = integralOilService.getIntegralOilByUserId(sub.getUserId());
		if(integral==null){
			//说明是该用户第一次获得积分
			integral.setUserId(sub.getUserId());
			integral.setCurrentValue(sub.getBasicValue());
			integral.setUsedValue(new Long(0));
			integralOilService.save(integral);
		}else{
			Long currentValue = integral.getCurrentValue();  //原来的当前积分  
			currentValue = currentValue+ sub.getBasicValue();  //本次获得的总积分+原来的当前积分 = 现在的当前积分
			integral.setCurrentValue(currentValue);
			integralOilService.update(integral);
		}
		return success("添加成功");
	}
	
	
	/**
	 * 
	 * @Description: 积分明细页面（包括获取与消费记录）
	 * @author yanghui 
	 * @Created 2014-3-27
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping("integralDetail")
	public String integralDetail(Model model,@RequestParam(value="userId") Long userId){
		model.addAttribute("userId", userId);
		return PREFIX+"/integralOilDetail";
	}
	@RequestMapping("integralCateDetail")
	public String integralCateDetail(Model model,@RequestParam(value="userId") Long userId,@RequestParam(value="entityCategory") Long entityCategory){
		model.addAttribute("userId", userId);
		model.addAttribute("entityCategory", entityCategory);
		return PREFIX+"/integralOilCateDetail";
	}
	/**
	 * 
	 * @Description: 积分明细列表
	 * @author yanghui 
	 * @Created 2014-3-27
	 * @param page
	 * @param userId
	 * @return
	 */
	@RequestMapping("integralDetailDataGrid")
	@ResponseBody
	public DataGrid integralDetailDataGrid(RequestPage page,@RequestParam(value="userId") Long userId){
		return integralOilService.integralDetailDataGrid(page,userId);
	}
	/**
	 * 
	 * @Description: 某个类别下的积分明细
	 * @author yanghui 
	 * @Created 2014-3-27
	 * @param page
	 * @param userId
	 * @return
	 */
	@RequestMapping("integralDetailCateDataGrid")
	@ResponseBody
	public DataGrid integralDetailCateDataGrid(RequestPage page,@RequestParam(value="userId") Long userId,@RequestParam(value="entityCategory") Long entityCategory){
		return integralOilService.integralDetailCateDataGrid(page,userId,entityCategory);
	}
	
	/**
	 * 
	 * @Description: 加油站积分兑换添加页面
	 * @author zhaopf
	 * @Created 2016-02-16
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping("integralOilExchange")
	public String integralOilExchange(Model model,@RequestParam(value="userId") Long userId,HttpSession session){
		model.addAttribute("userId", userId);
		session.setAttribute("userId", userId);
		IntegralOil integralOil =  integralOilService.getIntegralOilByUserId(userId);
		if(integralOil!=null){
			model.addAttribute("currentIntegral", integralOil.getCurrentValue());
			model.addAttribute("usedIntegral", integralOil.getUsedValue());
		}else{
			model.addAttribute("currentIntegral", 0);
			model.addAttribute("usedIntegral", 0);
		}
		return PREFIX+"/integralOilExchange";
	}

	/**
	 * 
	 * @Description: 加油站积分兑换保存数据
	 * @author zhaopf
	 * @Created 2016-02-16
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping("saveIntend")
	@ResponseBody
	public Map<String,Object> saveIntend(IntendBean bean,HttpSession session){
		Long userId = (Long) session.getAttribute("userId"); //当前登录的会员
		IntegralOil integralOil = integralOilService.getIntegralOilByUserId(userId);
		Long currentValue = integralOil.getCurrentValue(); //该用户当前的积分
		Long usedIntegral = bean.getExcIntegral()*bean.getPrizeNum();  //本次消费的积分

		//自定义规则生产兑换订单号
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String ymd = sdf.format(new Date());
		String intendNo = ymd;
		//订单号 = 当前的日期（时分秒）+ 随机三为整数+当前用户的ID
		intendNo = intendNo + new Random().nextInt(1000)+String.valueOf(userId);
		Intend intend = new Intend();
		intend.setIntendNo(intendNo); //订单号
		intend.setApplyTime(DateUtil.getTimestamp()); //订单申请时间
		intend.setIntendStatus(SystemCommon_Constant.INTEND_STATUS_1);  //订单状态
		intend.setConsignee(bean.getConsignee());  //收货人
		intend.setMobilePhone(bean.getMobilePhone());// 收货人电话
		intend.setAddress(bean.getAddress()); //收货人地址
		intend.setApplyPersonId(userId); // 订单申请人（客户端登录用户ID）
		intend.setRemark(bean.getRemark()); //订单备注
		
		intendService.save(intend);
		
		//保存订单与奖品关联信息
		IntendRelPrize irp = new IntendRelPrize();
		irp.setPrizeId(bean.getPrizeId());
		irp.setPrizeNum(bean.getPrizeNum());
		irp.setPrizeCateId(bean.getPrizeCateId());
		irp.setExcIntegral(bean.getExcIntegral());
		irp.setIntendNo(intendNo);
		intendRelPrizeService.save(irp);
		
		//修改奖品兑换的数量
		PrizeInfo pinfo = prizeInfoService.get(bean.getPrizeId());
		Long excNum = pinfo.getExcNum();
		Long prizeNum = pinfo.getPrizeNum();
		excNum = excNum+bean.getPrizeNum();
		pinfo.setExcNum(excNum);
		pinfo.setPrizeNum(prizeNum-excNum); //剩余数量
		prizeInfoService.update(pinfo);
		
		//订单以及订单与奖品的关联信息都保存后，就需要修改该用户的积分信息
		Long usedValue = integralOil.getUsedValue();//该用户已经消费的积分
			//消费的积分为单个奖品需要的积分乘以奖品数量
		integralOil.setUsedValue(usedValue+usedIntegral);  //新的消费积分
		integralOil.setCurrentValue(currentValue-usedIntegral);  //新的当前积分
		integralOilService.update(integralOil);
	
		return success("订单已提交成功");
	}
	
	
	/**
	 * 
	 * @Description: 积分分类(中石油，中石化)
	 * @author yanghui 
	 * @Created 2014-3-27
	 * @param page
	 * @param userId
	 * @return
	 */
	@RequestMapping("integralCategory")
	@ResponseBody
	public List<IntegralCategoryBean> integralCategory(RequestPage page,@RequestParam(value="userId") Long userId){
		return integralOilService.integralCategory(page,userId);
	}
	
	
	/**
	 * 
	 * @Description: 中奖查询页面
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping("exchangeQuery")
	public String exchangeQuery(){
		return PREFIX+"/exchangeQuery";
	}
	/**
	 * 
	 * @Description: 为微信提供的入口中奖详情页面
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping("exchangeQueryForWeChat")
	public String exchangeQueryForWeChat(Model model,@RequestParam(value="sn") String sn,@RequestParam(value="phone") String phone){
		model.addAttribute("sn", sn);
		model.addAttribute("phone", phone);
		return PREFIX+"/exchangeQueryForWeChat";
	}
	/**
	 * 
	 * @Description: 为微信提供的入口中奖详情页面(新)
	 *  20160628 yyf add
	 * @return
	 */
	@RequestMapping("winPrizeInfo")
	public String winPrizeInfo(Model model,@RequestParam(value="winPrizeId") String winPrizeId){
		model.addAttribute("winPrizeId", winPrizeId);
		return PREFIX+"/winPrizeInfo";
	}
	
	/**
	 * 
	 * @Description: 中奖信息查询（调用免费宝接口）
	 * @author zhaopf
	 * @Created 2016-02-16
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping("/queryExchange")
	@ResponseBody
	public Map<String,String> queryExchange(@RequestParam(value="sn") String sn,@RequestParam(value="phone") String phone, HttpServletRequest request){
		Map<String,String> map = new HashMap<String, String>();//YYF
		List<Object> list = new ArrayList<Object>();
		String path="http://www.baidu.com";
	   try {
			SNBase snBase = snBaseService.queryBySN(sn);
			if(snBase!=null){
				EvaluationVo evalVo=evaluationService.getEvalVobySn(sn, phone);
				if(evalVo!=null){
					//根据手机号    Sn码 查询中奖纪录  yf 20160329 add
					WinPrizeInfo winPrizeInfo = winPrizeInfoService.getWinPrizeInfoBySnVsPhone(sn, phone);
					map.put("prizeName", null!= winPrizeInfo.getPrizeName()?winPrizeInfo.getPrizeName():"暂无");//奖品名称
					map.put("prizeNum", null != winPrizeInfo.getPrizeNum()?winPrizeInfo.getPrizeNum().toString():"暂无");//奖品数量
					map.put("prizeUser", null != winPrizeInfo.getPrizeUser()?winPrizeInfo.getPrizeUser():"暂无");//中奖人姓名
					map.put("prizePhone", null != winPrizeInfo.getPrizePhone()?winPrizeInfo.getPrizePhone():"暂无");//中奖人电话
					map.put("prizeAddress", null != winPrizeInfo.getPrizePlace()?winPrizeInfo.getPrizePlace():"暂无");//领奖地点
					map.put("prizeSupplier", null != winPrizeInfo.getPrizeSupplier()?winPrizeInfo.getPrizeSupplier():"暂无");//奖品提供方
					map.put("isCash","暂无");
					List<CashPrizeInfo> cashPrizeInfos = cashprizeInfoService.queryBySN(sn);
					if(null != cashPrizeInfos && cashPrizeInfos.size()>0){
						map.put("istrue", "yes");
						map.put("isCash","已经兑奖");
					}else{
						map.put("istrue", "no");
						map.put("isCash","没有兑奖");
					}
					
					map.put("surveyName", evalVo.getSurveyName());
					map.put("industryName", evalVo.getIndustryName());
					map.put("entityName", evalVo.getEntityName());
					map.put("voteUserName", evalVo.getVoteUserName());
					map.put("idCard", evalVo.getIdCard());
					map.put("mobilePhone", evalVo.getMobilePhone());
					map.put("createTime", evalVo.getCreateTime().toString().substring(0, 19));
				}else{
					return null;
				}
				
					//***********************************************************************
				}else{
					return null;
				}
				
//				list.add(enterpriseInfo);
//				String notesInfo=productInfo.getCNotesInfo();
//				if(notesInfo.contains("\r\n")){
//					String[] notes = notesInfo.split("\r\n");
//					for(int i=0;i<notes.length;i++)
//						list.add(notes[i]);
//				}else{
//					list.add(productInfo.getCNotesInfo());//产品备注信息
//				}
		} catch (Exception e) {
			System.out.println("真伪查询----查询sn码时出现异常！异常如下：");
			System.out.println(e.getMessage());
			return null;//抛异常时，返回null，前台会提示用户，无此产品信息
		}
			return map;
	}
	/**
	 * 
	 * @Description: 中奖信息查询（新）
	 * @author yyf
	 * @Created 2016-06-28
	 * @return
	 */
	@RequestMapping("/queryWinPrizeInfo")
	@ResponseBody
	public Map<String,String> queryWinPrizeInfo(@RequestParam(value="winPrizeId") String winPrizeId,HttpServletRequest request){
		Map<String,String> map = new HashMap<String, String>();//YYF
		try {
				EvaluationVo evalVo=evaluationService.findByWinPrizeId(winPrizeId);
				if(evalVo!=null){
					//根据手机号    Sn码 查询中奖纪录  yf 20160329 add
					WinPrizeInfo winPrizeInfo = winPrizeInfoService.get(Long.parseLong(winPrizeId));
					map.put("prizeName", null!= winPrizeInfo.getPrizeName()?winPrizeInfo.getPrizeName():"暂无");//奖品名称
					map.put("prizeNum", null != winPrizeInfo.getPrizeNum()?winPrizeInfo.getPrizeNum().toString():"暂无");//奖品数量
					map.put("prizeUser", null != winPrizeInfo.getPrizeUser()?winPrizeInfo.getPrizeUser():"暂无");//中奖人姓名
					map.put("prizePhone", null != winPrizeInfo.getPrizePhone()?winPrizeInfo.getPrizePhone():"暂无");//中奖人电话
					map.put("prizeAddress", null != winPrizeInfo.getPrizePlace()?winPrizeInfo.getPrizePlace():"暂无");//领奖地点
					map.put("prizeSupplier", null != winPrizeInfo.getPrizeSupplier()?winPrizeInfo.getPrizeSupplier():"暂无");//奖品提供方
					map.put("isCash",evalVo.getGetPrizeFlag());
					if(StringUtils.isNotBlank(evalVo.getGetPrizeFlag())&&evalVo.getGetPrizeFlag().equals("已领")){
						map.put("istrue", "yes");
					}else{
						map.put("istrue", "no");
					}
					
					map.put("surveyName", evalVo.getSurveyName());
					map.put("industryName", evalVo.getIndustryName());
					map.put("entityName", evalVo.getEntityName());
					map.put("voteUserName", evalVo.getVoteUserName());
					map.put("idCard", evalVo.getIdCard());
					map.put("mobilePhone", evalVo.getMobilePhone());
					map.put("createTime", evalVo.getCreateTime().toString().substring(0, 19));
				}else{
					return null;
				}
		} catch (Exception e) {
			System.out.println("获取信息异常！#######################################################"+e.getMessage());
			return null;//抛异常时，返回null，前台会提示用户，无此产品信息
		}
		return map;
	}
	/**
	  * 方法名称：cashPrizeRecord
	  * 功能描述：查询兑奖记录
	  * 创建时间:2016年2月23日下午2:55:47
	  * 创建人: pengfei Zhao
	  * @param @param SNParam
	  * @param @return 
	  * @return List<CashPrizeInfo>
	 */
	@RequestMapping("/cashPrizeRecord.action")
	@ResponseBody
	public List<CashPrizeInfo> cashPrizeRecord(@RequestParam("SNParam") String SNParam){
			List<CashPrizeInfo> list = cashprizeInfoService.queryBySN(SNParam);
			return list;
	}
	/**
	 * 方法名称：queryPrizeRecord
	 * 功能描述：查询兑奖记录
	 * 创建时间:20160629
	 * 创建人: yyf
	 * @param @param winPrizeId
	 * @param @return 
	 * @return List<CashPrizeInfo>
	 */
	@RequestMapping("/queryPrizeRecord.action")
	@ResponseBody
	public List<CashPrizeInfo> queryPrizeRecord(@RequestParam("winPrizeId") String winPrizeId){
		List<CashPrizeInfo> list = cashprizeInfoService.queryByWinPrizeId(winPrizeId);
		return list;
	}
	
	/**
	  * 方法名称：cashPrizeOneRecord
	  * 功能描述：查询一条兑奖记录
	  * 创建人: 20160414 yyf add
	  * @param @param SNParam
	  * @param @return 
	  * @return CashPrizeInfo
	 */
	@RequestMapping("/cashPrizeOneRecord.action")
	@ResponseBody
	public CashPrizeInfo cashPrizeOneRecord(@RequestParam("SNParam") String SNParam,
			@RequestParam(value="createId",required=false) Long createId,
			@RequestParam(value="createName",required=false) String createName,
			@RequestParam(value="createDeptId",required=false) String createDeptId,
			@RequestParam(value="createDeptName",required=false) String createDeptName,
			HttpServletRequest request){
			User user = (User)request.getSession().getAttribute("user");
			List<CashPrizeInfo> list = cashprizeInfoService.queryBySN(SNParam);
			CashPrizeInfo cpi =new CashPrizeInfo();
			if(list.size()!=0){
				 cpi = list.get(0);
			}else{
				WinPrizeInfo winPrizeInfo = winPrizeInfoService.getWinPrizeInfoBySnVsPhone(SNParam, "");
				//TODO ADD YYF 以下是模拟测试数据
				cpi.setCreateId(createId);
				cpi.setCreateName(createName);
				cpi.setCreateOrgId(createDeptId);
				cpi.setCreateOrgName(createDeptName);
				cpi.setPrizeSN(SNParam);
				cpi.setPrizePhone(winPrizeInfo.getPrizePhone());
				cpi.setPrizeUser(winPrizeInfo.getPrizeUser());
			}
			return cpi;
	}
	
	
	public static String loadJSON (String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL urlnet = new URL(url);
            URLConnection urlConect = urlnet.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConect.getInputStream(),"utf-8"));
            String inputLine = null;
            while ( (inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return json.toString();
    }
	/**
	 * 20160415 yyf add  兑奖临时解决方案
	 */
	@RequestMapping("temporary")
	public String temporary(Model model,@RequestParam(value="sn") String sn,@RequestParam(value="pswd") String pswd){
		boolean flag = cashprizeInfoService.isExistPrizeSN(sn);
		if(flag){
			model.addAttribute("isExistPrizeSN", "true");
		}
		model.addAttribute("sn", sn);
		model.addAttribute("pswd", pswd);
		return PREFIX+"/temporary";
	}
	/**
	 * 20160415 yyf add  兑奖或兑奖详情页面入口
	 */
	@RequestMapping("gasStationExpiryQueryForApp")
	public String gasStationExpiryQueryForWeChat(Model model,@RequestParam(value="sn") String sn,
			@RequestParam(value="createId") String createId,
			@RequestParam(value="createName") String createName,
			@RequestParam(value="createDeptId") String createDeptId,
			@RequestParam(value="createDeptName") String createDeptName){
		//从手机端获取的sn实际是snid
		SNBase sbase = snBaseService.get(sn);
		boolean flag = cashprizeInfoService.isExistPrizeSN(sbase.getSn());
		if(flag){
			model.addAttribute("isExistPrizeSN", "true");
		}
		
		model.addAttribute("createId", createId);
		model.addAttribute("createName", createName);
		model.addAttribute("createDeptId", createDeptId);
		model.addAttribute("createDeptName", createDeptName);
		model.addAttribute("sn", sbase.getSn());
		return PREFIX+"/gasStationExpiryQueryForApp";
	}

}
