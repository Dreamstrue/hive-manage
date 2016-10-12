package com.hive.tagManage.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

















import com.google.zxing.WriterException;
import com.hive.common.SystemCommon_Constant;
import com.hive.enterprisemanage.service.EnterpriseInfoService;
import com.hive.surveymanage.entity.Survey;
import com.hive.surveymanage.service.IndustryEntityService;
import com.hive.tagManage.entity.BlackListBean;
import com.hive.tagManage.entity.SNBase;
import com.hive.tagManage.entity.SNBatch;
import com.hive.tagManage.entity.SNBatchSurvey;
import com.hive.tagManage.model.SNBaseVo;
import com.hive.tagManage.service.TagAllocationService;
import com.hive.tagManage.service.TagSNBaseService;
import com.hive.tagManage.service.TagSNBatchService;
import com.hive.tagManage.service.TagSNBatchSurveyService;
import com.hive.util.EncoderHandler;
import com.hive.util.PropertiesFileUtil;
import com.hive.util.TwoDCodeImage;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;


/**
 * 
* @ClassName: SNBaseController 
* @Description: TODO(标签管理Controller) 
* @author lihao 
* @date 2015-4-2 下午03:24:19 
*
 */
@Controller
@RequestMapping("SNBaseController")
public class SNBaseController extends BaseController {
	
	private Logger logger = Logger.getLogger(SNBaseController.class);

	private static String PREFIX = "/tagManage/snBase";
	@Resource
	private TagSNBaseService snBaseService;
	@Resource
	private TagAllocationService allocationService;
	@Resource
	private TagSNBatchService tagSNBatchService;
	@Resource
	private IndustryEntityService industryEntityService;
	
	@Resource
	private TagSNBatchSurveyService tagSNBatchSurveyService;
	
	
	@RequestMapping("/toManagePage.action")
	public String toManagePage(HttpServletRequest request){
		return PREFIX + "/manage";
	}
	
	@RequestMapping("/toTagQueryPage.action")
	public String toTagQueryPage(HttpServletRequest request){
		return PREFIX + "/query";
	}
	
	/**
	 * 
	* @Title: queryList 
	* @Description: TODO(查询标签列表) 
	* @param @return    设定文件 
	* @return DataGrid    返回类型 
	* @throws
	 */
	@RequestMapping("/queryList")
	@ResponseBody
	public DataGrid queryList(@RequestParam("page") int page, @RequestParam("rows") int rows, 
			HttpServletRequest request){
		Map<String, Object> mapParam = new HashMap<String, Object>(); 
		mapParam.put("batch", request.getParameter("batch"));
		mapParam.put("sn", request.getParameter("sn"));
		mapParam.put("industryEntityId", request.getParameter("industryEntityId"));
		mapParam.put("blackList", request.getParameter("blackList"));
		mapParam.put("queryNum", request.getParameter("queryNum"));
		mapParam.put("status", request.getParameter("status"));
		
		List<BlackListBean> beanList = new ArrayList<BlackListBean>();
		List<SNBase> list = snBaseService.querySNBaseList(page, rows, mapParam);
		for(int i = 0; i<list.size(); i++){
			//拼装Bean
			BlackListBean blackListBean = new BlackListBean();
			try{
				PropertyUtils.copyProperties(blackListBean, list.get(i));
				SNBatch snBatch = tagSNBatchService.get(list.get(i).getSnBatchId());
				blackListBean.setStrentityName(snBatch.getEntityName());
				blackListBean.setStrBatch(snBatch.getBatch());
//				blackListBean.setIntRank(snBatch.getRank());
				String batchId = blackListBean.getSnBatchId();
//				String snBatch = tagSNBatchService.get(batchId).getBatch();
				blackListBean.setBqbh(snBatch.getBatch() + String.format("%07d", blackListBean.getSequenceNum()));
				blackListBean.setBlackList((blackListBean.getBlackList()==null)?"0":blackListBean.getBlackList());
				
				
				//查找问卷   20160304 yf add
				String surveyId = "";
				 String surveyTitle= "";
				List<SNBatchSurvey> res = tagSNBatchSurveyService.getSNBatchSurveyByBatch(list.get(i).getSnBatchId());
				if(null != res && res.size() >= 1){
					int i_ = 1;
					for(SNBatchSurvey sNBatchSurvey:res){
						surveyId += sNBatchSurvey.getSurveyId();
						if(i_ < res.size()){
							surveyId += ",";
						}
						surveyTitle += sNBatchSurvey.getSurveyTitle();
						if(i_ < res.size()){
							surveyTitle += ",";
						}
						i_++;
					}
				}
				blackListBean.setSurveyId(surveyId);
				blackListBean.setSurveyTitle(surveyTitle);
				
				beanList.add(blackListBean);
			}catch(Exception e){
				logger.info(e.getMessage());
			}
		}
		//总条数
		Long cnt = snBaseService.querySNBaseListCnt(mapParam);
		DataGrid dg = new DataGrid(cnt, beanList);
		return dg;
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
			SNBase snbase=null;
			if(id.contains(",")){
				snbase=snBaseService.get(id.split(",")[0]);
				snbase.setStatus("0");
				snbase.setId(id);
			}else{
				snbase=snBaseService.get(id);
			}
			model.addAttribute("vo",snbase);
		return PREFIX + "/edit";
		}
	}
	/**
	* @Title: markBlackList 
	* @Description: TODO(标记黑名单) 
	* @param @param id
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */ 
	@RequestMapping("/markBlackList.action")
	@ResponseBody
	public Object markBlackList(@RequestParam("id") String id){
		SNBase snBase = snBaseService.get(id);
		if(snBase.getBlackList().equals("1")){
			return this.error("该标签已经加入黑名单，请勿重复操作！");
		}
		snBase.setBlackList("1");
		snBaseService.update(snBase);
		return this.success("加入黑名单成功");
	}
	
	/**
	* @Title: quitBlackList 
	* @Description: TODO(取消黑名单) 
	* @param @param id
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	@RequestMapping("/quitBlackList.action")
	@ResponseBody
	public Object quitBlackList(@RequestParam("id") String id){
		SNBase snBase = snBaseService.get(id);
		if(snBase.getBlackList().equals("0")){
			return this.error("该标签尚未加入黑名单！");
		}
		snBase.setBlackList("0");
		snBaseService.update(snBase);
		return this.success("取消黑名单成功");
	}
	
	/**
	* @Title: markBlackInBatch 
	* @Description: TODO(批量加入黑名单) 
	* @param @param ids
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	@RequestMapping("/markBlackInBatch.action")
	@ResponseBody
	public Object markBlackInBatch(@RequestParam("ids") String ids){
		int len=0;
		String[] id = ids.split(",");
		len=id.length;
		for(int i = 0; i<id.length; i++){
			SNBase snBase = snBaseService.get(id[i]);
			if(snBase.getBlackList().equals("1")){
				len-=1;
			}else{
			snBase.setBlackList("1");
			snBaseService.update(snBase);
			}
		}
		String str="";
		if(len==0){
			str="您选中的"+id.length+"个标签已经在黑名单！";
		}else if(len==id.length){
			str="您选中的"+id.length+"个标签，加入黑名单成功！";
		}else{
			str="您选中的"+id.length+"个标签，添加成功"+len+"个，其他"+(id.length-len)+"已经在黑名单！";
		}
		return this.success(str);
	}
	
	
	/**
	 * 查看标签详情信息
	 * @param model
	 * @param productId
	 * @return
	 */
	@RequestMapping("/viewBaseInfo.action") 
	public String viewBaseInfo(Model model, @RequestParam(value = "baseId") String baseId) {
		SNBase  snbase = null;
		SNBaseVo vo =null;
		try {
			    snbase = snBaseService.get(baseId);
			    vo= new SNBaseVo();
				PropertyUtils.copyProperties(vo, snbase);
				SNBatch snbatch = tagSNBatchService.get(snbase.getSnBatchId());
				vo.setEntityName(snbatch.getEntityName());
				vo.setBatch(snbatch.getBatch());
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("vo", vo);
		return PREFIX + "/detail";
	}
	
	
	/**
	 * 修改标签状态
	 * @author 赵鹏飞
	 */
	@RequestMapping("/editSnBase")
	@ResponseBody
	public Map<String, Object> editSnBase(HttpServletRequest request, HttpSession session) {
		int len=0;
		try {
		String ids=request.getParameter("id");
		String[] id = ids.split(",");
		len=id.length;
		for(int i = 0; i<id.length; i++){
			

			SNBase snBase = new SNBase();

			snBase=snBaseService.get(id[i]);
			snBase.setId(id[i]);
			snBase.setStatus(request.getParameter("status"));//申请单状态
			snBaseService.update(snBase);
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
	@RequestMapping("/viewQrcode.action") 
	public void viewQrcode(@RequestParam(value = "id") String id,@RequestParam(value = "queryPath") String queryPath,HttpServletRequest requset, HttpServletResponse response) {
	        EncoderHandler encoder = new EncoderHandler();  
	        if(!queryPath.contains("&SNId")){
	        	queryPath=queryPath+"&SNId="+id;
	        }
	        encoder.encoderQRCoder(queryPath, response);  
	}
	
	
	/**
	 * 
	 * @Description: 生产二维码图片
	 * @return
	 * @throws WriterException
	 * @throws IOException
	 */
	@RequestMapping("createCode.action")
	public Map<String, Object> createTwoCode(@RequestParam(value = "id", required = false) Long id,@RequestParam(value = "queryPath", required = false) String queryPath ) throws WriterException, IOException{
		String text = queryPath;
		String imgName = "";
		SNBase s = snBaseService.get(id);
		if(s!=null){
			imgName = s.getSn();
		}
		String path = ""; 
		path = TwoDCodeImage.writeImage(text,imgName);
		return this.success("二维码生成成功，位置：" + path,s);
	}
}
