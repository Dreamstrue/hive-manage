package com.hive.defectmanage.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hive.clientinterface.service.DefectInterfaceService;
import com.hive.common.AnnexFileUpLoad;
import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Annex;
import com.hive.common.service.AnnexService;
import com.hive.defectmanage.entity.DefectRecord;
import com.hive.defectmanage.entity.DefectRecordCarContacts;
import com.hive.defectmanage.entity.DefectRecordCarDefect;
import com.hive.defectmanage.entity.DefectRecordCarInfo;
import com.hive.defectmanage.entity.DmAssembly;
import com.hive.defectmanage.entity.DmCarBrand;
import com.hive.defectmanage.entity.DmCarBrandSeries;
import com.hive.defectmanage.entity.DmCarBrandSeriesModel;
import com.hive.defectmanage.entity.DmCarBrandSeriesModelDetail;
import com.hive.defectmanage.entity.DmSubAssembly;
import com.hive.defectmanage.entity.DmThreeLevelAssembly;
import com.hive.defectmanage.model.DefectRecordCardBean;
import com.hive.defectmanage.service.DefectRecordCarService;
import com.hive.defectmanage.service.DefectRecordService;
import com.hive.surveymanage.entity.SurveyCategory;
import com.hive.util.PropertiesFileUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

/**
 * @description 缺陷采集-汽车
 * @author 燕珂
 * @date 2015-11-10
 */
@Controller
@RequestMapping("/defectCar")
public class DefectCarController extends BaseController{
	private final String PREFIX = "defectRecordManage/car";
	
	@Resource
	private DefectInterfaceService defectInterfaceService;
	@Resource
	private DefectRecordCarService defectRecordCarService;
	@Resource
	private AnnexService annexService;
	
	/**
	 * 选择进入缺陷采集还是评价页面
	 */
	@RequestMapping("/toDefectCarIndex")
	public String toDefectCarIndex(HttpServletRequest request) {
		PropertiesFileUtil propertiesFileUtil = new PropertiesFileUtil();
		//取得配置文件配置的上传路径
		String zxt_url = propertiesFileUtil.findValue("zxt_http_url");
		request.setAttribute("zxt_url", zxt_url);
		return PREFIX + "/defectCarIndex";
	}
	
	/**
	 * 提示页面
	 */
	@RequestMapping("/toDefectCarTips")
	public String toDefectCarTips(){
		return PREFIX + "/defectCarTips";
	}
	
	/**
	 * 车辆信息页面
	 */
	@RequestMapping("/toDefectCarInfo")
	public String toDefectCarInfo(){
		return PREFIX + "/defectCarInfo";
	}
	
	/**
	 * 暂时把车辆信息保存到 session 中
	 */
	@RequestMapping("/saveDefectCarInfo")
	public String saveDefectCarInfo(DefectRecordCarInfo defectCarInfo, HttpSession session, HttpServletRequest request) {
		session.setAttribute("defectCarInfo", defectCarInfo);
		return "redirect:/defectCar/toDefectCarDescription.action";
	}
	
	/**
	 * 缺陷描述信息
	 */
	@RequestMapping("/toDefectCarDescription")
	public String toDefectCarDescription(HttpSession session){
		return PREFIX + "/defectCarDescription";
	}
	
	/**
	 * 暂时把缺陷描述信息保存到 session 中
	 */
	@RequestMapping("/saveDefectCarDescription")
	public String saveDefectCarDescription(DefectRecordCarDefect defectCarDescription, @RequestParam(value = "File", required = false) MultipartFile[] files, HttpSession session, HttpServletRequest request) {
		session.setAttribute("defectCarDescription", defectCarDescription);
		//session.setAttribute("files", files); // 这个不能放到 session中往下传，到后面的方法中，文件就丢失了，只剩下第一个文件
		
		List<Annex> annexlist = AnnexFileUpLoad.uploadFile(files, session, 0L, "DEFECT_RECORD_CAR", "DEFECT", "DEFECT_FILE"); // 本来是想把上传和保存放到同一个事务中的，只能在这儿传了（可能会产生垃圾附件）
		session.setAttribute("annexlist", annexlist);
		return "redirect:/defectCar/toDefectCarContract.action";
	}
	
	/**
	 * 联系人信息
	 */
	@RequestMapping("/toDefectCarContract")
	public String toDefectCarContract(){
		return PREFIX + "/defectCarContract";
	}
	
	/**
	 * 全部信息保存入数据库
	 */
	@RequestMapping("/saveDefectCarAllInfo")
	public String saveDefectCarContract(DefectRecordCarContacts defectCarContract, HttpSession session, HttpServletRequest request) {
		DefectRecordCarInfo defectCarInfo = (DefectRecordCarInfo)session.getAttribute("defectCarInfo");
		DefectRecordCarDefect defectCarDescription = (DefectRecordCarDefect)session.getAttribute("defectCarDescription");
		List<Annex> annexlist = (List<Annex>)session.getAttribute("annexlist");
		
		boolean saveResult = defectInterfaceService.saveDefectCarAllInfo(defectCarInfo, defectCarDescription, defectCarContract, annexlist);
		
		if (saveResult)
			return PREFIX + "/defectCarSuccess";
		else
			return PREFIX + "/defectCarFailure";
	}
	
	
	/**
	 * 功能描述：转到信息列表页面
	 * 创建时间:2015-12-18上午9:26:31
	 * 创建人: pengfei zhao
	 * @return
	 */
	@RequestMapping("/manage") 
	public String manage() {
		return PREFIX+"/manage"; // /
	}
	/**
	  * 方法名称：getCarBrandList
	  * 功能描述：获取所有品牌列表
	  * 创建时间:2016年3月2日下午3:07:42
	  * 创建人: pengfei Zhao
	  * @param @return 
	  * @return List<DmCarBrand>
	 */
	@RequestMapping("/getCarBrandList.action")
	@ResponseBody
	public List<DmCarBrand> getCarBrandList() {
		return defectRecordCarService.getCarBrandList();
	}
	/**
	  * 方法名称：getCarBrandModelList
	  * 功能描述：获取所有品牌名称列表
	  * 创建时间:2016年3月2日下午3:07:42
	  * 创建人: pengfei Zhao
	  * @param @return 
	  * @return List<DmCarBrand>
	 */
	@RequestMapping("/getCarBrandModelList.action")
	@ResponseBody
	public List<DmCarBrandSeriesModel> getCarBrandModelList(@RequestParam(value = "brandid") Long brandid,@RequestParam(value = "seriesid") Long seriesid) {
		return defectRecordCarService.getCarBrandSeriesModelList(brandid,seriesid);
	}
	
	/**
	  * 方法名称：getCarBrandList
	  * 功能描述：获取所有品牌系列列表
	  * 创建时间:2016年3月2日下午3:07:42
	  * 创建人: pengfei Zhao
	  * @param @return 
	  * @return List<DmCarBrand>
	 */
	@RequestMapping("/getCarBrandSeriesList.action")
	@ResponseBody
	public List<DmCarBrandSeries> getCarBrandSeriesList(@RequestParam(value = "brandid") Long brandid) {
		return defectRecordCarService.getCarBrandSeriesListForId(brandid);
	}
	
	/**
	  * 方法名称：getAssemblyList
	  * 功能描述：获取所有总成列表
	  * 创建时间:2016年3月2日下午3:07:42
	  * 创建人: pengfei Zhao
	  * @param @return 
	  * @return List<DmCarBrand>
	 */
	@RequestMapping("/getAssemblyList.action")
	@ResponseBody
	public List<DmAssembly> getAssemblyList() {
		return defectRecordCarService.getAssemblyList();
	}
	
	/**
	  * 方法名称：getSubAssemblyList
	  * 功能描述：获取所有分总成列表
	  * 创建时间:2016年3月2日下午3:07:42
	  * 创建人: pengfei Zhao
	  * @param @return 
	  * @return List<DmCarBrand>
	 */
	@RequestMapping("/getSubAssemblyList.action")
	@ResponseBody
	public List<DmSubAssembly> getSubAssemblyList(@RequestParam(value = "assemblyId") Long assemblyId) {
		return defectRecordCarService.getSubAssemblyList(assemblyId);
	}
	
	/**
	 * 
	* @Title: queryList 
	* @Description: TODO(查询玩具和其他产品缺陷列表) 
	* @param @return    设定文件 
	* @return DataGrid    返回类型 
	* @throws
	 */
	@RequestMapping("/queryList.action")
	@ResponseBody
	public DataGrid queryList(@RequestParam("page") int page, @RequestParam("rows") int rows, 
			HttpServletRequest request){
		Map<String, Object> mapParam = new HashMap<String, Object>(); 
		mapParam.put("carownername", request.getParameter("carownername"));
		mapParam.put("carbrand", request.getParameter("carbrand"));
		mapParam.put("carproducername", request.getParameter("carproducername"));
		mapParam.put("carmodelyear", request.getParameter("carmodelyear"));
		DataGrid dg = defectRecordCarService.queryDefectCarList(page, rows, mapParam);
		return dg;
	}
	
	
	/**
	 * 查看缺陷详情信息
	 * @param model
	 * @param productId
	 * @return
	 */
	@RequestMapping("/viewDefectRecord.action") 
	public String viewDefectRecord(Model model, @RequestParam(value = "mainId") Long mainId) {
		DefectRecordCardBean defectCarRecordBean = null;
		try {
			defectCarRecordBean = defectRecordCarService.getDefectRecordDetail(mainId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("vo", defectCarRecordBean);
		return PREFIX+"/detail"; // / ;
	}
	/**
	 * 
	 * @Description: 删除缺陷信息
	 * @param id
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(@RequestParam(value="id") Long id){
//		DefectRecord defectRecord = defectRecordCarService.get(id);
//		defectRecordCarService.delete(defectRecord);
		defectRecordCarService.deleteInfo(id);
		return success("删除成功");
	}
	
	/*************  汽车品牌管理  start  ***************/     
	/**
	 * 跳转至列表页
	 */
	@RequestMapping("/dmCarBrandManage") 
	public String dmCarBrandManage() {
		return PREFIX + "/dmCarBrandCategoryManage/manage"; // 
	}
	@RequestMapping("/dmCarBrandDatagrid.action") 
	@ResponseBody
	public DataGrid dmCarBrandDatagrid(RequestPage page) {
		return defectRecordCarService.dmCarBrandDatagrid(page);
	}
	@RequestMapping("todmCarBrandAdd.action")
	public String todmCarBrandAdd() {
		return PREFIX + "/dmCarBrandCategoryManage/add";
	}
	/**
	 * 添加
	 */
	@RequestMapping("/dmCarBrandInsert.action")
	@ResponseBody
	public Map<String, Object> dmCarBrandInsert(DmCarBrand dmCarBrand, HttpSession session) {
		dmCarBrand.setValid(SystemCommon_Constant.VALID_STATUS_1);
		boolean istry=defectRecordCarService.dmCarBrandInsert(dmCarBrand);
		if(istry){
			return success("添加成功！", dmCarBrand);
		}else{
			return error("添加失败！");
		}
	}
	
	/**
	 * 跳转至修改页
	 */
	@RequestMapping("todmCarBrandEdit.action")
	public String todmCarBrandEdit(Model model, @RequestParam(value = "id") Long id) {
		model.addAttribute("vo", defectRecordCarService.getdmCarBrandInfo(id));
		return PREFIX + "/dmCarBrandCategoryManage/edit";
	}

	/**
	 * 修改
	 */
	@RequestMapping("/dmCarBrandUpdate.action")
	@ResponseBody
	public Map dmCarBrandUpdate(DmCarBrand dmCarBrand, HttpSession session) {
		boolean istry=defectRecordCarService.dmCarBrandUpdate(dmCarBrand);
		if(istry){
			return success("修改成功！", dmCarBrand);
		}else{
			return error("修改失败！");
		}
	}

	/**
	 * 删除
	 */
	@RequestMapping("/dmCarBrandDelete.action")
	@ResponseBody
	public Map dmCarBrandDelete(String ids)
	{
		if (StringUtils.isNotEmpty(ids))
		{
			for (int i = 0; i < ids.split(",").length; i++) {
				defectRecordCarService.dmCarBrandDelete(Long.valueOf(ids.split(",")[i]));
			}
			return success("删除成功！");
		} else
		{
			return error("请选择要删除的类别");
		}
	}
	/*************  汽车品牌管理  end  ***************/  
	/*************  汽车品牌车型系列管理  start  ***************/     
	/**
	 * 跳转至列表页
	 */
	@RequestMapping("/dmCarBrandSeriesManage") 
	public String dmCarBrandSeriesManage() {
		return PREFIX + "/dmCarBrandSeriesManage/manage"; // 
	}
	@RequestMapping("/dmCarBrandSeriesDatagrid.action") 
	@ResponseBody
	public DataGrid dmCarBrandSeriesDatagrid(RequestPage page) {
		return defectRecordCarService.dmCarBrandSeriesDatagrid(page);
	}
	@RequestMapping("todmCarBrandSeriesAdd.action")
	public String todmCarBrandSeriesAdd() {
		return PREFIX + "/dmCarBrandSeriesManage/add";
	}
	/**
	 * 添加
	 */
	@RequestMapping("/dmCarBrandSeriesInsert.action")
	@ResponseBody
	public Map<String, Object> dmCarBrandSeriesInsert(DmCarBrandSeries dmCarBrandSeries, HttpSession session) {
		dmCarBrandSeries.setValid(SystemCommon_Constant.VALID_STATUS_1);
		boolean istry=defectRecordCarService.dmCarBrandSeriesInsert(dmCarBrandSeries);
		if(istry){
			return success("添加成功！", dmCarBrandSeries);
		}else{
			return error("添加失败！");
		}
	}
	
	/**
	 * 跳转至修改页
	 */
	@RequestMapping("todmCarBrandSeriesEdit.action")
	public String todmCarBrandSeriesEdit(Model model, @RequestParam(value = "id") Long id) {
		model.addAttribute("vo", defectRecordCarService.getdmCarBrandSeriesInfo(id));
		return PREFIX + "/dmCarBrandSeriesManage/edit";
	}

	/**
	 * 修改
	 */
	@RequestMapping("/dmCarBrandSeriesUpdate.action")
	@ResponseBody
	public Map dmCarBrandSeriesUpdate(DmCarBrandSeries dmCarBrandSeries, HttpSession session) {
		boolean istry=defectRecordCarService.dmCarBrandSeriesUpdate(dmCarBrandSeries);
		if(istry){
			return success("修改成功！", dmCarBrandSeries);
		}else{
			return error("修改失败！");
		}
	}

	/**
	 * 删除
	 */
	@RequestMapping("/dmCarBrandSeriesDelete.action")
	@ResponseBody
	public Map dmCarBrandSeriesDelete(String ids)
	{
		if (StringUtils.isNotEmpty(ids))
		{
			for (int i = 0; i < ids.split(",").length; i++) {
				defectRecordCarService.dmCarBrandSeriesDelete(Long.valueOf(ids.split(",")[i]));
			}
			return success("删除成功！");
		} else
		{
			return error("请选择要删除的类别");
		}
	}
	/*************  汽车品牌车型系列管理   end  ***************/  
	/*************  汽车品牌车型名称管理  start  ***************/     
	/**
	 * 跳转至列表页
	 */
	@RequestMapping("/dmCarBrandSeriesModelManage") 
	public String dmCarBrandSeriesModelManage() {
		return PREFIX + "/dmCarBrandSeriesModelManage/manage"; // 
	}
	@RequestMapping("/dmCarBrandSeriesModelDatagrid.action") 
	@ResponseBody
	public DataGrid dmCarBrandSeriesModelDatagrid(RequestPage page) {
		return defectRecordCarService.dmCarBrandSeriesModelDatagrid(page);
	}
	@RequestMapping("todmCarBrandSeriesModelAdd.action")
	public String todmCarBrandSeriesModelAdd() {
		return PREFIX + "/dmCarBrandSeriesModelManage/add";
	}
	/**
	 * 添加
	 */
	@RequestMapping("/dmCarBrandSeriesModelInsert.action")
	@ResponseBody
	public Map<String, Object> dmCarBrandSeriesModelInsert(DmCarBrandSeriesModel dmCarBrandSeriesModel, HttpSession session) {
		dmCarBrandSeriesModel.setValid(SystemCommon_Constant.VALID_STATUS_1);
		boolean istry=defectRecordCarService.dmCarBrandSeriesModelInsert(dmCarBrandSeriesModel);
		if(istry){
			return success("添加成功！", dmCarBrandSeriesModel);
		}else{
			return error("添加失败！");
		}
	}
	
	/**
	 * 跳转至修改页
	 */
	@RequestMapping("todmCarBrandSeriesModelEdit.action")
	public String todmCarBrandSeriesModelEdit(Model model, @RequestParam(value = "id") Long id) {
		model.addAttribute("vo", defectRecordCarService.getdmCarBrandSeriesModelInfo(id));
		return PREFIX + "/dmCarBrandSeriesModelManage/edit";
	}

	/**
	 * 修改
	 */
	@RequestMapping("/dmCarBrandSeriesModelUpdate.action")
	@ResponseBody
	public Map dmCarBrandSeriesModelUpdate(DmCarBrandSeriesModel dmCarBrandSeriesModel, HttpSession session) {
		boolean istry=defectRecordCarService.dmCarBrandSeriesModelUpdate(dmCarBrandSeriesModel);
		if(istry){
			return success("修改成功！", dmCarBrandSeriesModel);
		}else{
			return error("修改失败！");
		}
	}

	/**
	 * 删除
	 */
	@RequestMapping("/dmCarBrandSeriesModelDelete.action")
	@ResponseBody
	public Map dmCarBrandSeriesModelDelete(String ids)
	{
		if (StringUtils.isNotEmpty(ids))
		{
			for (int i = 0; i < ids.split(",").length; i++) {
				defectRecordCarService.dmCarBrandSeriesModelDelete(Long.valueOf(ids.split(",")[i]));
			}
			return success("删除成功！");
		} else
		{
			return error("请选择要删除的类别");
		}
	}
	/*************  汽车品牌车型名称管理   end  ***************/  
	/*************  汽车具体型号管理  start  ***************/     
	/**DmCarBrandSeriesModelDetail
	 * 跳转至列表页
	 */
	@RequestMapping("/dmCarBrandSeriesModelDetailManage") 
	public String dmCarBrandSeriesModelDetailManage() {
		return PREFIX + "/dmCarBrandSeriesModelDetailManage/manage"; // 
	}
	@RequestMapping("/dmCarBrandSeriesModelDetailDatagrid.action") 
	@ResponseBody
	public DataGrid dmCarBrandSeriesModelDetailDatagrid(RequestPage page) {
		return defectRecordCarService.dmCarBrandSeriesModelDetailDatagrid(page);
	}
	@RequestMapping("todmCarBrandSeriesModelDetailAdd.action")
	public String todmCarBrandSeriesModelDetailAdd() {
		return PREFIX + "/dmCarBrandSeriesModelDetailManage/add";
	}
	/**
	 * 添加
	 */
	@RequestMapping("/dmCarBrandSeriesModelDetailInsert.action")
	@ResponseBody
	public Map<String, Object> dmCarBrandSeriesModelDetailInsert(DmCarBrandSeriesModelDetail dmCarBrandSeriesModelDetail, HttpSession session) {
		dmCarBrandSeriesModelDetail.setValid(SystemCommon_Constant.VALID_STATUS_1);
		boolean istry=defectRecordCarService.dmCarBrandSeriesModelDetailInsert(dmCarBrandSeriesModelDetail);
		if(istry){
			return success("添加成功！", dmCarBrandSeriesModelDetail);
		}else{
			return error("添加失败！");
		}
	}
	
	/**
	 * 跳转至修改页
	 */
	@RequestMapping("todmCarBrandSeriesModelDetailEdit.action")
	public String todmCarBrandSeriesModelDetailEdit(Model model, @RequestParam(value = "id") Long id) {
		model.addAttribute("vo", defectRecordCarService.getdmCarBrandSeriesModelDetailInfo(id));
		return PREFIX + "/dmCarBrandSeriesModelDetailManage/edit";
	}

	/**
	 * 修改
	 */
	@RequestMapping("/dmCarBrandSeriesModelDetailUpdate.action")
	@ResponseBody
	public Map dmCarBrandSeriesModelDetailUpdate(DmCarBrandSeriesModelDetail dmCarBrandSeriesModelDetail, HttpSession session) {
		boolean istry=defectRecordCarService.dmCarBrandSeriesModelDetailUpdate(dmCarBrandSeriesModelDetail);
		if(istry){
			return success("修改成功！", dmCarBrandSeriesModelDetail);
		}else{
			return error("修改失败！");
		}
	}

	/**
	 * 删除
	 */
	@RequestMapping("/dmCarBrandSeriesModelDetailDelete.action")
	@ResponseBody
	public Map dmCarBrandSeriesModelDetailDelete(String ids)
	{
		if (StringUtils.isNotEmpty(ids))
		{
			for (int i = 0; i < ids.split(",").length; i++) {
				defectRecordCarService.dmCarBrandSeriesModelDetailDelete(Long.valueOf(ids.split(",")[i]));
			}
			return success("删除成功！");
		} else
		{
			return error("请选择要删除的类别");
		}
	}
	/*************  汽车具体型号管理   end  ***************/  
	/*************  总分成类别管理  start  ***************/     
	/**
	 * 跳转至列表页
	 */
	@RequestMapping("/dmAssemblyManage") 
	public String dmAssemblyManage() {
		return PREFIX + "/dmAssemblyCategoryManage/manage"; // 
	}
	@RequestMapping("/dmAssemblyDatagrid.action") 
	@ResponseBody
	public DataGrid dmAssemblyDatagrid(RequestPage page) {
		return defectRecordCarService.dmAssemblyDatagrid(page);
	}
	@RequestMapping("toDmAssemblyAdd.action")
	public String todmAssemblyAdd() {
		return PREFIX + "/dmAssemblyCategoryManage/add";
	}
	/**
	 * 添加
	 */
	@RequestMapping("/dmAssemblyInsert.action")
	@ResponseBody
	public Map<String, Object> dmAssemblyInsert(DmAssembly dmAssembly, HttpSession session) {
		dmAssembly.setValid(SystemCommon_Constant.VALID_STATUS_1);
		boolean istry=defectRecordCarService.dmAssemblyInsert(dmAssembly);
		if(istry){
			return success("添加成功！", dmAssembly);
		}else{
			return error("添加失败！");
		}
	}
	
	/**
	 * 跳转至修改页
	 */
	@RequestMapping("toDmAssemblyEdit.action")
	public String toDmAssemblyEdit(Model model, @RequestParam(value = "id") Long id) {
		model.addAttribute("vo", defectRecordCarService.getAssemblyInfo(id));
		return PREFIX + "/dmAssemblyCategoryManage/edit";
	}

	/**
	 * 修改
	 */
	@RequestMapping("/dmAssemblyUpdate.action")
	@ResponseBody
	public Map dmAssemblyUpdate(DmAssembly dmAssembly, HttpSession session) {
		boolean istry=defectRecordCarService.dmAssemblyUpdate(dmAssembly);
		if(istry){
			return success("修改成功！", dmAssembly);
		}else{
			return error("修改失败！");
		}
	}

	/**
	 * 删除
	 */
	@RequestMapping("/dmAssemblyDelete.action")
	@ResponseBody
	public Map dmAssemblyDelete(String ids)
	{
		if (StringUtils.isNotEmpty(ids))
		{
			for (int i = 0; i < ids.split(",").length; i++) {
				defectRecordCarService.dmAssemblyDelete(Long.valueOf(ids.split(",")[i]));
			}
			return success("删除成功！");
		} else
		{
			return error("请选择要删除的类别");
		}
	}
	/*************  总分成类别管理  end  ***************/  
	/*************  分总成类别管理  start  ***************/     
	/**
	 * 跳转至列表页
	 */
	@RequestMapping("/dmSubAssemblyManage") 
	public String dmSubAssemblyManage() {
		return PREFIX + "/dmSubAssemblyCategoryManage/manage"; // 
	}
	@RequestMapping("/dmSubAssemblyDatagrid.action") 
	@ResponseBody
	public DataGrid dmSubAssemblyDatagrid(RequestPage page) {
		return defectRecordCarService.dmSubAssemblyDatagrid(page);
	}
	@RequestMapping("toDmSubAssemblyAdd.action")
	public String todmSubAssemblyAdd() {
		return PREFIX + "/dmSubAssemblyCategoryManage/add";
	}
	/**
	 * 添加
	 */
	@RequestMapping("/dmSubAssemblyInsert.action")
	@ResponseBody
	public Map<String, Object> dmSubAssemblyInsert(DmSubAssembly dmSubAssembly, HttpSession session) {
		dmSubAssembly.setValid(SystemCommon_Constant.VALID_STATUS_1);
		boolean istry=defectRecordCarService.dmSubAssemblyInsert(dmSubAssembly);
		if(istry){
			return success("添加成功！", dmSubAssembly);
		}else{
			return error("添加失败！");
		}
	}
	
	/**
	 * 跳转至修改页
	 */
	@RequestMapping("toDmSubAssemblyEdit.action")
	public String toDmSubAssemblyEdit(Model model, @RequestParam(value = "id") Long id) {
		model.addAttribute("vo", defectRecordCarService.getSubAssemblyInfo(id));
		return PREFIX + "/dmSubAssemblyCategoryManage/edit";
	}

	/**
	 * 修改
	 */
	@RequestMapping("/dmSubAssemblyUpdate.action")
	@ResponseBody
	public Map dmSubAssemblyUpdate(DmSubAssembly dmSubAssembly, HttpSession session) {
		boolean istry=defectRecordCarService.dmSubAssemblyUpdate(dmSubAssembly);
		if(istry){
			return success("修改成功！", dmSubAssembly);
		}else{
			return error("修改失败！");
		}
	}

	/**
	 * 删除
	 */
	@RequestMapping("/dmSubAssemblyDelete.action")
	@ResponseBody
	public Map dmSubAssemblyDelete(String ids)
	{
		if (StringUtils.isNotEmpty(ids))
		{
			for (int i = 0; i < ids.split(",").length; i++) {
				defectRecordCarService.dmSubAssemblyDelete(Long.valueOf(ids.split(",")[i]));
			}
			return success("删除成功！");
		} else
		{
			return error("请选择要删除的类别");
		}
	}
	/************* 分总成类别管理  end  ***************/  
	/************* 三级分成类别管理  start  ***************/     
	/**
	 * 跳转至列表页
	 */
	@RequestMapping("/dmThreeLevelAssemblyManage") 
	public String dmThreeLevelAssemblyManage() {
		return PREFIX + "/dmThreeLevelAssemblyManage/manage"; // 
	}
	@RequestMapping("/dmThreeLevelAssemblyDatagrid.action") 
	@ResponseBody
	public DataGrid dmThreeLevelAssemblyDatagrid(RequestPage page) {
		return defectRecordCarService.dmThreeLevelAssemblyDatagrid(page);
	}
	@RequestMapping("toDmThreeLevelAssemblyAdd.action")
	public String todmThreeLevelAssemblyAdd() {
		return PREFIX + "/dmThreeLevelAssemblyManage/add";
	}
	/**
	 * 添加
	 */
	@RequestMapping("/dmThreeLevelAssemblyInsert.action")
	@ResponseBody
	public Map<String, Object> dmThreeLevelAssemblyInsert(DmThreeLevelAssembly dmThreeLevelAssembly, HttpSession session) {
		dmThreeLevelAssembly.setValid(SystemCommon_Constant.VALID_STATUS_1);
		boolean istry=defectRecordCarService.dmThreeLevelAssemblyInsert(dmThreeLevelAssembly);
		if(istry){
			return success("添加成功！", dmThreeLevelAssembly);
		}else{
			return error("添加失败！");
		}
	}
	
	/**
	 * 跳转至修改页
	 */
	@RequestMapping("toDmThreeLevelAssemblyEdit.action")
	public String toDmThreeLevelAssemblyEdit(Model model, @RequestParam(value = "id") Long id) {
		model.addAttribute("vo", defectRecordCarService.getThreeLevelAssemblyInfo(id));
		return PREFIX + "/dmThreeLevelAssemblyManage/edit";
	}

	/**
	 * 修改
	 */
	@RequestMapping("/dmThreeLevelAssemblyUpdate.action")
	@ResponseBody
	public Map dmThreeLevelAssemblyUpdate(DmThreeLevelAssembly dmThreeLevelAssembly, HttpSession session) {
		boolean istry=defectRecordCarService.dmThreeLevelAssemblyUpdate(dmThreeLevelAssembly);
		if(istry){
			return success("修改成功！", dmThreeLevelAssembly);
		}else{
			return error("修改失败！");
		}
	}

	/**
	 * 删除
	 */
	@RequestMapping("/dmThreeLevelAssemblyDelete.action")
	@ResponseBody
	public Map dmThreeLevelAssemblyDelete(String ids)
	{
		if (StringUtils.isNotEmpty(ids))
		{
			for (int i = 0; i < ids.split(",").length; i++) {
				defectRecordCarService.dmThreeLevelAssemblyDelete(Long.valueOf(ids.split(",")[i]));
			}
			return success("删除成功！");
		} else
		{
			return error("请选择要删除的类别");
		}
	}
	/************* 三级分成类别管理  end  ***************/ 
	
}
