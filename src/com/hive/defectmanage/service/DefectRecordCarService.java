/**
 * 
 */
package com.hive.defectmanage.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hive.common.AnnexFileUpLoad;
import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Annex;
import com.hive.common.service.AnnexService;
import com.hive.common.service.CityService;
import com.hive.common.service.DistrictService;
import com.hive.common.service.ProvinceService;
import com.hive.contentmanage.entity.CommonContent;
import com.hive.defectmanage.entity.DefectRecord;
import com.hive.defectmanage.entity.DefectRecordCar;
import com.hive.defectmanage.entity.DefectRecordCarContacts;
import com.hive.defectmanage.entity.DefectRecordCarDefect;
import com.hive.defectmanage.entity.DefectRecordCarInfo;
import com.hive.defectmanage.entity.DmAssembly;
import com.hive.defectmanage.entity.DmCarBrand;
import com.hive.defectmanage.entity.DmCarBrandSeries;
import com.hive.defectmanage.entity.DmCarBrandSeriesModel;
import com.hive.defectmanage.entity.DmCarBrandSeriesModelDetail;
import com.hive.defectmanage.entity.DmCertificateStype;
import com.hive.defectmanage.entity.DmGearbox;
import com.hive.defectmanage.entity.DmSubAssembly;
import com.hive.defectmanage.entity.DmThreeLevelAssembly;
import com.hive.defectmanage.entity.ProductKeepRecord;
import com.hive.defectmanage.model.ComparatorDefect;
import com.hive.defectmanage.model.DefectRecordBean;
import com.hive.defectmanage.model.DefectRecordCardBean;
import com.hive.defectmanage.model.DmCarBrandSeriesBean;
import com.hive.defectmanage.model.DmCarBrandSeriesModelBean;
import com.hive.defectmanage.model.DmCarBrandSeriesModelDetailBean;
import com.hive.defectmanage.model.DmSubAssemblyBean;
import com.hive.defectmanage.model.DmThreeLevelAssemblyBean;
import com.hive.util.DataUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

/**  
 * Filename: DefectRecordCarService.java  
 * Description:汽车产品缺陷service
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  ZhaopPengfei
 * @version: 1.0  
 * @Create:  2016-02-3  
 * ------------------------------------------------------------------  
 */
@Service
public class DefectRecordCarService extends BaseService<DefectRecordCar> {

	@Resource
	private AnnexService annexService;
	@Resource
	private BaseDao<DefectRecordCar> defectRecordCarDao; //产品缺陷记录表-汽车类
	@Resource
	private BaseDao<DefectRecordCarContacts> defectRecordCarContactsDao; //产品缺陷记录表-联系人子表
	@Resource
	private BaseDao<DefectRecordCarDefect> defectRecordCarDefectDao; //产品缺陷记录表-缺陷详情子表
	@Resource
	private BaseDao<DefectRecordCarInfo> defectRecordCarInfoDao; //产品缺陷记录表-车辆信息子表

	@Resource
	private BaseDao<DmAssembly> dmAssemblyDao; //总成代码表
	@Resource
	private BaseDao<DmCarBrand> dmCarBrandDao; //汽车品牌表
	@Resource
	private BaseDao<DmCarBrandSeries> dmCarBrandSeriesDao; //品牌系列表
	@Resource
	private BaseDao<DmCarBrandSeriesModel> dmCarBrandSeriesModelDao; //车型名称表
	@Resource
	private BaseDao<DmCarBrandSeriesModelDetail> dmCarBrandSeriesModelDetailDao; //具体车型名称表
	@Resource
	private BaseDao<DmCertificateStype> dmCertificateStypeDao; //证件类型
	@Resource
	private BaseDao<DmGearbox> dmGearboxDao; //变速箱类型代码表
	
	@Resource
	private BaseDao<DmSubAssembly> dmSubAssemblyDao; //分总成代码表
	@Resource
	private BaseDao<DmThreeLevelAssembly> dmThreeLevelAssemblyDao; //三级总成代码表
	
	@Resource
	private BaseDao<ProductKeepRecord> productKeepRecordDao; //产品标准备案表
	/** 省份代码表Service */
	@Resource
	private ProvinceService provinceService;
	/** 地市代码表Service */
	@Resource
	private CityService cityService;
	/** 县区代码表Service */
	@Resource
	private DistrictService districtService;
	@Resource
	private BaseDao<CommonContent> commonContentDao;
	@Override
	protected BaseDao<DefectRecordCar> getDao() {
		return defectRecordCarDao;
	}

	/**
	 * 
	 * @Description: 证件类型列表
	 * @author YangHui 
	 * @Created 2014-6-3
	 * @return
	 */
	public List<DmCertificateStype> getCertificatesTypeList() {
		List<DmCertificateStype>  list = dmCertificateStypeDao.find(" from DmCertificateStype where valid='1' order by ordernum asc ", new Object[0]);
		return list;
	}


	/**
	 * 
	 * @Description: 汽车品牌列表
	 * @author YangHui 
	 * @Created 2014-6-4
	 * @return
	 */
	public List<DmCarBrand> getCarBrandList(String brandName) {
		
		String hql = " from DmCarBrand where valid=? "
			+ (StringUtils.isNotBlank(brandName) ? (" and brandname like '%" + brandName.replaceAll("'", "") + "%'") : "") + // 若关键词中含有单引号会导致 sql 报错，所以需要替换掉
			" order by ordernum asc ";
		List<DmCarBrand> list = dmCarBrandDao.find(hql, SystemCommon_Constant.VALID_STATUS_1);
		return list;
	}
   public List<DmCarBrand> getCarBrandList() {
		String hql = " from DmCarBrand where valid=? order by ordernum asc";
		List<DmCarBrand> list = dmCarBrandDao.find(hql, SystemCommon_Constant.VALID_STATUS_1);
		return list;
	}
   public List<DmCarBrandSeries> getCarBrandSeriesList() {
		String hql = " from DmCarBrandSeries where valid=? order by ordernum asc";
		List<DmCarBrandSeries> list = dmCarBrandSeriesDao.find(hql, SystemCommon_Constant.VALID_STATUS_1);
		return list;
	}

	/**
	 * 
	 * @Description:汽车品牌车型系列表
	 * @author YangHui 
	 * @Created 2014-6-4
	 * @param brandId
	 * @return
	 */
	public List<DmCarBrandSeries> getCarBrandSeriesListForId(Long  brandId) {
		String hql = " from DmCarBrandSeries where valid=?  and brandid=? order by ordernum asc ";
		List<DmCarBrandSeries> list = dmCarBrandSeriesDao.find(hql, new Object[]{SystemCommon_Constant.VALID_STATUS_1,brandId});
		return list;
	}

	//车型名称列表
	public List<DmCarBrandSeriesModel> getCarBrandSeriesModelList(Long brandId,
			Long seriesId) {
		String hql = " from DmCarBrandSeriesModel where  brandid=?  and seriesid=?  and valid='"+SystemCommon_Constant.VALID_STATUS_1+"' order by ordernum asc ";
		List<DmCarBrandSeriesModel> list = dmCarBrandSeriesModelDao.find(hql, new Object[]{brandId,seriesId});
		return list;
	}
	
	//车型具体型号列表
	public List<DmCarBrandSeriesModelDetail> getCarBrandSeriesModelDetailList(Long brandId,
			Long seriesId,Long modelId) {
		String hql = " from DmCarBrandSeriesModelDetail where  brandid=?  and seriesid=? and modelid=?  and valid='"+SystemCommon_Constant.VALID_STATUS_1+"' order by ordernum asc ";
		List<DmCarBrandSeriesModelDetail> list = dmCarBrandSeriesModelDetailDao.find(hql, new Object[]{brandId,seriesId,modelId});
		return list;
	}

	//变速器类型列表
	public List<DmGearbox> getGearBoxList() {
		String hql = " from DmGearbox where valid=?   order by ordernum asc ";
		List<DmGearbox> list = dmGearboxDao.find(hql, SystemCommon_Constant.VALID_STATUS_1);
		return list;
	}

	//总成代码列表
	public List<DmAssembly> getAssemblyList() {
		String hql = " from DmAssembly where valid=?   order by ordernum asc ";
		List<DmAssembly> list = dmAssemblyDao.find(hql, SystemCommon_Constant.VALID_STATUS_1);
		return list;
	}
	//分总成代码列表
	public List<DmSubAssembly> getSubAssemblyList(Long assemblyId) {
		String hql = " from DmSubAssembly where valid=?  and assemblyId=?  order by ordernum asc ";
		List<DmSubAssembly> list = dmSubAssemblyDao.find(hql, new Object[]{SystemCommon_Constant.VALID_STATUS_1,assemblyId});
		return list;
	}

	//三级分总成代码表列表
	public List<DmThreeLevelAssembly> getThreeAssemblyList(Long assemblyId,
			Long subAssemblyId) {
		String hql = " from DmThreeLevelAssembly where assemblyId=? and subAssemblyId=? and valid='"+SystemCommon_Constant.VALID_STATUS_1+"'  order by ordernum asc ";
		List<DmThreeLevelAssembly> list = dmThreeLevelAssemblyDao.find(hql, new Object[]{assemblyId,subAssemblyId});
		return list;
	}


	public DefectRecordCar getDefectRecordCar(Long defectId) {
		DefectRecordCar car = null;
		car  = defectRecordCarDao.get(DefectRecordCar.class, defectId);
		return car;
	}

	//根据总成代码获取总成
	public String getAssembly(Long id) {
		DmAssembly dmas=null;
		if(id!=null&&!id.equals("")){
		dmas=dmAssemblyDao.get(DmAssembly.class, id);
		}
		String dmasName="";
		if(dmas==null){
			dmasName="";
		}else{
			dmasName=dmas.getAssemblyName();
		}
		return dmasName;
	}

	//根据分总成代码获取分总成
	public String getDmSubAssembly(Long id) {
		DmSubAssembly dmas=null;
		if(id!=null&&!id.equals("")){
		dmas=dmSubAssemblyDao.get(DmSubAssembly.class, id);
		}
		String dmasName="";
		if(dmas==null){
			dmasName="";
		}else{
			dmasName=dmas.getSubAssemblyName();
		}
		return dmasName;
	}
	//根据三级总成ID获取三级总称
	public String getDmThreeLevelAssembly(Long id) {
		DmThreeLevelAssembly dmas=null;
		if(id!=null&&!id.equals("")){
		dmas=dmThreeLevelAssemblyDao.get(DmThreeLevelAssembly.class, id);
		}
		String dmasName="";
		if(dmas==null){
			dmasName="";
		}else{
			dmasName=dmas.getThreeLevelAssemblyName();
		}
		return dmasName;
	}
	//根据车辆品牌ID获得品牌
	public String getDmCarBrand(Long id) {
		DmCarBrand dmas=null;
		if(id!=null&&!id.equals("")){
		dmas=dmCarBrandDao.get(DmCarBrand.class, id);
		}
		String dmasName="";
		if(dmas==null){
			dmasName="";
		}else{
			dmasName=dmas.getBrandname();
		}
		return dmasName;
	}
	//根据证件ID获得证件信息
	public String getDmCertificateStype(Long id) {
		DmCertificateStype dmas=null;
		if(id!=null&&!id.equals("")){
		dmas=dmCertificateStypeDao.get(DmCertificateStype.class, id);
		}
		String dmasName="";
		if(dmas==null){
			dmasName="";
		}else{
			dmasName=dmas.getCertificatesname();
		}
		return dmasName;
	}
	
	//根据ID获得汽车品牌车型系列表
	public String getDmCarBrandSeries(Long id) {
		DmCarBrandSeries dmas=null;
		if(id!=null&&!id.equals("")){
		dmas=dmCarBrandSeriesDao.get(DmCarBrandSeries.class, id);
		}
		String dmasName="";
		if(dmas==null){
			dmasName="";
		}else{
			dmasName=dmas.getSeriesname();
		}
		return dmasName;
	}
	//根据ID获得车型名称
	public String getDmCarBrandSeriesModel(Long id) {
		DmCarBrandSeriesModel dmas=null;
		if(id!=null&&!id.equals("")){
		dmas=dmCarBrandSeriesModelDao.get(DmCarBrandSeriesModel.class, id);
		}
		String dmasName="";
		if(dmas==null){
			dmasName="";
		}else{
			dmasName=dmas.getModelname();
		}
		return dmasName;
	}
	//根据ID获得具体型号
	public String getDmCarBrandSeriesModelDetail(Long id) {
		DmCarBrandSeriesModelDetail dmas=null;
		if(id!=null&&!id.equals("")){
		dmas=dmCarBrandSeriesModelDetailDao.get(DmCarBrandSeriesModelDetail.class, id);
		}
		String dmasName="";
		if(dmas==null){
			dmasName="";
		}else{
			dmasName=dmas.getModeldetailname();
		}
		return dmasName;
	}
	//根据ID获得变速器类型
	public String getDmGearbox(String id) {
		DmGearbox dmas=null;
		if(id!=null&&!id.equals("")){
		dmas=dmGearboxDao.get(DmGearbox.class, Long.valueOf(id));
		}
		String dmasName="";
		if(dmas==null){
			dmasName="";
		}else{
			dmasName=dmas.getGearboxName();
		}
		return dmasName;
	}


	
	/**
	 * 
	* @Title: queryDefectCarList 
	* @Description: TODO(查询汽车缺陷列表) 
	* @param @return    设定文件 
	* @return List<SNBase>    返回类型 
	* @throws
	 */
	public DataGrid queryDefectCarList(int page, int rows, Map<String, Object> mapParam){
		StringBuffer hql = new StringBuffer();
		hql.append(" select a ,b, c, d from DefectRecordCar a,DefectRecordCarInfo b,DefectRecordCarContacts c,DefectRecordCarDefect d ");
		hql.append(" where b.defectId = a.id and b.defectId = c.defectId and b.defectId = d.defectId ");
		if(mapParam.get("carownername")!=null && mapParam.get("carownername").toString().length()>0){
			hql.append(" and c.carownername ='"+mapParam.get("carownername")+"'");
		}
		if(mapParam.get("carbrand")!=null && mapParam.get("carbrand").toString().length()>0){
			hql.append(" and b.carbrand ="+mapParam.get("carbrand"));
		}
		if(mapParam.get("carproducername")!=null && mapParam.get("carproducername").toString().length()>0){
			hql.append(" and b.carproducername ='"+mapParam.get("carproducername")+"'");
		}
		if(mapParam.get("carmodelyear")!=null && mapParam.get("carmodelyear").toString().length()>0){
			hql.append(" and carmodelyear ='"+mapParam.get("carmodelyear")+"'");
		}
		hql.append(" order by a.reportdate desc");
		List list = defectRecordCarInfoDao.find(page,rows,hql.toString(),new Object[0]);
		List<DefectRecordCardBean> beanList=new ArrayList<DefectRecordCardBean>();
		for(int i=0;i<list.size();i++){
			Object[] object = (Object[]) list.get(i);
			DefectRecordCar car = (DefectRecordCar)object[0];
			DefectRecordCarInfo info = (DefectRecordCarInfo) object[1];
			DefectRecordCarContacts con = (DefectRecordCarContacts)object[2];
			DefectRecordCarDefect defect = (DefectRecordCarDefect) object[3];
			DefectRecordCardBean bean = new DefectRecordCardBean();
			try {
				PropertyUtils.copyProperties(bean, info);
				PropertyUtils.copyProperties(bean, con);
				PropertyUtils.copyProperties(bean, defect);
				bean.setReportdate(car.getReportdate());
				bean.setMainId(car.getId());
				String asseblyName=getAssembly(defect.getAssembly());
				String dmSubAssemblyName=getDmSubAssembly(defect.getSubAssembly());
				String carbrandName=getDmCarBrand(info.getCarbrand());
				String carmodelForName=getDmCarBrandSeriesModel(info.getCarmodelname());
				bean.setAssemblyName(asseblyName);
				bean.setSubAssemblyName(dmSubAssemblyName);
				bean.setCarbrandName(carbrandName);
				bean.setCarmodelForName(carmodelForName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			beanList.add(bean);
		}
	
		return new DataGrid(beanList.size(),beanList);
	}

	
	/**
	 * 
	 * @Description: 查看缺陷的详细信息
	 * @author zhaopengfei
	 * @Created 2016-03-02
	 * @param id
	 * @return
	 */
	public DefectRecordCardBean getDefectRecordDetail(Long id) {
		DefectRecordCardBean obj = null;
		List list = new ArrayList();
		StringBuffer hql  = new StringBuffer();
		//汽车类缺陷
		hql.append(" select a ,b, c, d from DefectRecordCar a,DefectRecordCarInfo b,DefectRecordCarContacts c,DefectRecordCarDefect d ");
		hql.append(" where a.id=? and b.defectId = a.id and b.defectId = c.defectId and b.defectId = d.defectId ");
		list = defectRecordCarInfoDao.find(hql.toString(), new Object[]{id});
		if(DataUtil.listIsNotNull(list)){
			Object[] object = (Object[]) list.get(0);
			DefectRecordCar car = (DefectRecordCar)object[0];
			DefectRecordCarInfo info = (DefectRecordCarInfo) object[1];
			DefectRecordCarContacts con = (DefectRecordCarContacts)object[2];
			DefectRecordCarDefect defect = (DefectRecordCarDefect) object[3];
			DefectRecordCardBean bean = new DefectRecordCardBean();
			try {
				PropertyUtils.copyProperties(bean, info);
				PropertyUtils.copyProperties(bean, con);
				PropertyUtils.copyProperties(bean, defect);
				bean.setReportdate(car.getReportdate());
				bean.setMainId(car.getId());
				String asseblyName=getAssembly(defect.getAssembly());
				String dmSubAssemblyName=getDmSubAssembly(defect.getSubAssembly());
				String threeLevelAssemblyName=getDmThreeLevelAssembly(defect.getThreeLevelAssembly());
				String carbrandName=getDmCarBrand(info.getCarbrand());
				String carmodelForName=getDmCarBrandSeriesModel(info.getCarmodelname());
				String carseriesName=getDmCarBrandSeries(info.getCarseries());
				String carmodeldetailedName=getDmCarBrandSeriesModelDetail(info.getCarmodeldetailed());
				String carTransmissionName=getDmGearbox(info.getCarTransmission());
				String certificatestypeName=getDmCertificateStype(con.getCertificatestype());
				String cprovincecodeName=provinceService.getProvinceNameByCode(con.getCprovincecode());
				String ccitycodeName=cityService.getCityNameCityNo(con.getCcitycode());
				String cdistrictcodeName=districtService.getDistrictsOfcode(con.getCdistrictcode());
				bean.setAssemblyName(asseblyName);
				bean.setSubAssemblyName(dmSubAssemblyName);
				bean.setThreeLevelAssemblyName(threeLevelAssemblyName);
				bean.setCarbrandName(carbrandName);
				bean.setCarmodelForName(carmodelForName);
				bean.setCarseriesName(carseriesName);
				bean.setCarmodeldetailedName(carmodeldetailedName);
				bean.setCarTransmissionName(carTransmissionName);
				bean.setCertificatestypeName(certificatestypeName);
				bean.setCprovincecodeName(cprovincecodeName);
				bean.setCcitycodeName(ccitycodeName);
				bean.setCdistrictcodeName(cdistrictcodeName);
				obj = bean;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		return obj;
	}

      public void deleteInfo(Long id){
		StringBuffer hql  = new StringBuffer();
		List list = new ArrayList();
		//汽车类缺陷
		hql.append(" select a ,b, c, d from DefectRecordCar a,DefectRecordCarInfo b,DefectRecordCarContacts c,DefectRecordCarDefect d ");
		hql.append(" where a.id=? and b.defectId = a.id and b.defectId = c.defectId and b.defectId = d.defectId ");
		list = defectRecordCarInfoDao.find(hql.toString(), new Object[]{id});
		if(DataUtil.listIsNotNull(list)){
			Object[] object = (Object[]) list.get(0);
			DefectRecordCar car = (DefectRecordCar)object[0];
			DefectRecordCarInfo info = (DefectRecordCarInfo) object[1];
			DefectRecordCarContacts con = (DefectRecordCarContacts)object[2];
			DefectRecordCarDefect defect = (DefectRecordCarDefect) object[3];
			defectRecordCarDao.delete(car);
			defectRecordCarContactsDao.delete(con);
			defectRecordCarDefectDao.delete(defect);
			defectRecordCarInfoDao.delete(info);
		}
  }

      /*************  汽车品牌管理  start  ***************/   
    	public DmCarBrand getdmCarBrandInfo(Long id) {
    		DmCarBrand dmas=null;
    		if(id!=null&&!id.equals("")){
    		dmas=dmCarBrandDao.get(DmCarBrand.class, id);
    		}
    		return dmas;
    	}
    	public DataGrid dmCarBrandDatagrid(RequestPage page){
    		StringBuffer hql  = new StringBuffer();
    		StringBuffer counthql  = new StringBuffer();
    	    hql.append("from DmCarBrand where valid=1   order by id desc ,ordernum asc");
    	  counthql.append("select count(*) from DmCarBrand where valid=1   order by id desc ,ordernum asc");
    		long count = dmCarBrandDao.count(counthql.toString(), new Object[0]).longValue();
    		java.util.List rolelist = getDao().find(page.getPage(), page.getRows(), hql.toString(), new Object[0]);
    		return new DataGrid(count, rolelist);
    	}
    	public boolean dmCarBrandInsert(DmCarBrand dmCarBrand){
    		boolean isyes=false;
    		try{
    		 dmCarBrandDao.save(dmCarBrand);
    		 isyes=true;
    		}catch(Exception e){
    			e.printStackTrace();
    			isyes=false;
    		}
    		return isyes;
    	}
    	public boolean dmCarBrandUpdate(DmCarBrand dmCarBrand){
    		boolean isyes=false;
    		try{
    		 dmCarBrandDao.update(dmCarBrand);
    		 isyes=true;
    		}catch(Exception e){
    			e.printStackTrace();
    			isyes=false;
    		}
    		return isyes;
    	}
    	public boolean dmCarBrandDelete(Long id){
    		boolean isyes=false;
    		try{
    			 DmCarBrand dmCarBrand = getdmCarBrandInfo(id);
    		 dmCarBrandDao.delete(dmCarBrand);
    		 isyes=true;
    		}catch(Exception e){
    			e.printStackTrace();
    			isyes=false;
    		}
    		return isyes;
    	}
    /*************    汽车品牌管理  end  ***************/  
    /*************  汽车品牌车型系列管理  start  ***************/   
    	public DmCarBrandSeries getdmCarBrandSeriesInfo(Long id) {
    		DmCarBrandSeries dmas=null;
    		if(id!=null&&!id.equals("")){
    		dmas=dmCarBrandSeriesDao.get(DmCarBrandSeries.class, id);
    		}
    		return dmas;
    	}
    	public DataGrid dmCarBrandSeriesDatagrid(RequestPage page){
    		StringBuffer hql  = new StringBuffer();
    		StringBuffer counthql  = new StringBuffer();
    	    hql.append("from DmCarBrandSeries where valid=1   order by id desc ,ordernum asc");
    	  counthql.append("select count(*) from DmCarBrandSeries where valid=1   order by id desc ,ordernum asc");
    		long count = dmCarBrandSeriesDao.count(counthql.toString(), new Object[0]).longValue();
    		List<DmCarBrandSeries> rolelist = dmCarBrandSeriesDao.find(page.getPage(), page.getRows(), hql.toString(), new Object[0]);
    		List<DmCarBrandSeriesBean> beanList=new ArrayList<DmCarBrandSeriesBean>();
    		try {
    		for(DmCarBrandSeries dcbs:rolelist){
    			DmCarBrandSeriesBean dmasBean=new DmCarBrandSeriesBean();
    			PropertyUtils.copyProperties(dmasBean, dcbs);
    			String brandname=getDmCarBrand(dmasBean.getBrandid());
				dmasBean.setBrandname(brandname);
				beanList.add(dmasBean);
    		}
    		} catch (Exception e) {
				e.printStackTrace();
			} 
    		return new DataGrid(count, beanList);
    	}
    	public boolean dmCarBrandSeriesInsert(DmCarBrandSeries dmCarBrandSeries){
    		boolean isyes=false;
    		try{
    		 dmCarBrandSeriesDao.save(dmCarBrandSeries);
    		 isyes=true;
    		}catch(Exception e){
    			e.printStackTrace();
    			isyes=false;
    		}
    		return isyes;
    	}
    	public boolean dmCarBrandSeriesUpdate(DmCarBrandSeries dmCarBrandSeries){
    		boolean isyes=false;
    		try{
    		 dmCarBrandSeriesDao.update(dmCarBrandSeries);
    		 isyes=true;
    		}catch(Exception e){
    			e.printStackTrace();
    			isyes=false;
    		}
    		return isyes;
    	}
    	public boolean dmCarBrandSeriesDelete(Long id){
    		boolean isyes=false;
    		try{
    			 DmCarBrandSeries dmCarBrandSeries = getdmCarBrandSeriesInfo(id);
    		 dmCarBrandSeriesDao.delete(dmCarBrandSeries);
    		 isyes=true;
    		}catch(Exception e){
    			e.printStackTrace();
    			isyes=false;
    		}
    		return isyes;
    	}
    	
    	
    /*************    汽车品牌车型系列管理  end  ***************/  	
    /*************   汽车品牌车型名称管理  start  ***************/   
    	public DmCarBrandSeriesModel getdmCarBrandSeriesModelInfo(Long id) {
    		DmCarBrandSeriesModel dmas=null;
    		if(id!=null&&!id.equals("")){
    		dmas=dmCarBrandSeriesModelDao.get(DmCarBrandSeriesModel.class, id);
    		}
    		return dmas;
    	}
    	public DataGrid dmCarBrandSeriesModelDatagrid(RequestPage page){
    		StringBuffer hql  = new StringBuffer();
    		StringBuffer counthql  = new StringBuffer();
    	    hql.append("from DmCarBrandSeriesModel where valid=1   order by id desc ,ordernum asc");
    	  counthql.append("select count(*) from DmCarBrandSeriesModel where valid=1   order by id desc ,ordernum asc");
    		long count = dmCarBrandSeriesModelDao.count(counthql.toString(), new Object[0]).longValue();
    		List<DmCarBrandSeriesModel> rolelist = dmCarBrandSeriesModelDao.find(page.getPage(), page.getRows(), hql.toString(), new Object[0]);
    		List<DmCarBrandSeriesModelBean> beanList=new ArrayList<DmCarBrandSeriesModelBean>();
    		try {
    		for(DmCarBrandSeriesModel dcbs:rolelist){
    			DmCarBrandSeriesModelBean dmasBean=new DmCarBrandSeriesModelBean();
    			PropertyUtils.copyProperties(dmasBean, dcbs);
    			String brandname=getDmCarBrand(dmasBean.getBrandid());
				dmasBean.setBrandname(brandname);
				String seriesname=getDmCarBrandSeries(dmasBean.getSeriesid());
				dmasBean.setSeriesname(seriesname);
				beanList.add(dmasBean);
    		}
    		} catch (Exception e) {
				e.printStackTrace();
			} 
    		return new DataGrid(count, beanList);
    	}
    	public boolean dmCarBrandSeriesModelInsert(DmCarBrandSeriesModel dmCarBrandSeriesModel){
    		boolean isyes=false;
    		try{
    		 dmCarBrandSeriesModelDao.save(dmCarBrandSeriesModel);
    		 isyes=true;
    		}catch(Exception e){
    			e.printStackTrace();
    			isyes=false;
    		}
    		return isyes;
    	}
    	public boolean dmCarBrandSeriesModelUpdate(DmCarBrandSeriesModel dmCarBrandSeriesModel){
    		boolean isyes=false;
    		try{
    		 dmCarBrandSeriesModelDao.update(dmCarBrandSeriesModel);
    		 isyes=true;
    		}catch(Exception e){
    			e.printStackTrace();
    			isyes=false;
    		}
    		return isyes;
    	}
    	public boolean dmCarBrandSeriesModelDelete(Long id){
    		boolean isyes=false;
    		try{
    			 DmCarBrandSeriesModel dmCarBrandSeriesModel = getdmCarBrandSeriesModelInfo(id);
    		 dmCarBrandSeriesModelDao.delete(dmCarBrandSeriesModel);
    		 isyes=true;
    		}catch(Exception e){
    			e.printStackTrace();
    			isyes=false;
    		}
    		return isyes;
    	}
    	
    	
    /*************    汽车品牌车型名称管理  end  ***************/ 
    	/*************   汽车具体型号管理  start  ***************/   
    	public DmCarBrandSeriesModelDetail getdmCarBrandSeriesModelDetailInfo(Long id) {
    		DmCarBrandSeriesModelDetail dmas=null;
    		if(id!=null&&!id.equals("")){
    		dmas=dmCarBrandSeriesModelDetailDao.get(DmCarBrandSeriesModelDetail.class, id);
    		}
    		return dmas;
    	}
    	public DataGrid dmCarBrandSeriesModelDetailDatagrid(RequestPage page){
    		StringBuffer hql  = new StringBuffer();
    		StringBuffer counthql  = new StringBuffer();
    	    hql.append("from DmCarBrandSeriesModelDetail where valid=1   order by id desc,ordernum asc");
    	  counthql.append("select count(*) from DmCarBrandSeriesModelDetail where valid=1   order by id desc,ordernum asc");
    		long count = dmCarBrandSeriesModelDetailDao.count(counthql.toString(), new Object[0]).longValue();
    		List<DmCarBrandSeriesModelDetail> rolelist = dmCarBrandSeriesModelDetailDao.find(page.getPage(), page.getRows(), hql.toString(), new Object[0]);
    		List<DmCarBrandSeriesModelDetailBean> beanList=new ArrayList<DmCarBrandSeriesModelDetailBean>();
    		try {
    		for(DmCarBrandSeriesModelDetail dcbs:rolelist){
    			DmCarBrandSeriesModelDetailBean dmasBean=new DmCarBrandSeriesModelDetailBean();
    			PropertyUtils.copyProperties(dmasBean, dcbs);
    			String brandname=getDmCarBrand(dmasBean.getBrandid());
				dmasBean.setBrandname(brandname);
				String seriesname=getDmCarBrandSeries(dmasBean.getSeriesid());
				dmasBean.setSeriesname(seriesname);
				String modelname=getDmCarBrandSeriesModel(dmasBean.getModelid());
				dmasBean.setModelname(modelname);
				beanList.add(dmasBean);
    		}
    		} catch (Exception e) {
				e.printStackTrace();
			} 
    		return new DataGrid(count, beanList);
    	}
    	public boolean dmCarBrandSeriesModelDetailInsert(DmCarBrandSeriesModelDetail dmCarBrandSeriesModelDetail){
    		boolean isyes=false;
    		try{
    		 dmCarBrandSeriesModelDetailDao.save(dmCarBrandSeriesModelDetail);
    		 isyes=true;
    		}catch(Exception e){
    			e.printStackTrace();
    			isyes=false;
    		}
    		return isyes;
    	}
    	public boolean dmCarBrandSeriesModelDetailUpdate(DmCarBrandSeriesModelDetail dmCarBrandSeriesModelDetail){
    		boolean isyes=false;
    		try{
    		 dmCarBrandSeriesModelDetailDao.update(dmCarBrandSeriesModelDetail);
    		 isyes=true;
    		}catch(Exception e){
    			e.printStackTrace();
    			isyes=false;
    		}
    		return isyes;
    	}
    	public boolean dmCarBrandSeriesModelDetailDelete(Long id){
    		boolean isyes=false;
    		try{
    			 DmCarBrandSeriesModelDetail dmCarBrandSeriesModelDetail = getdmCarBrandSeriesModelDetailInfo(id);
    		 dmCarBrandSeriesModelDetailDao.delete(dmCarBrandSeriesModelDetail);
    		 isyes=true;
    		}catch(Exception e){
    			e.printStackTrace();
    			isyes=false;
    		}
    		return isyes;
    	}
    	
    	
    /*************    汽车具体型号名称管理  end  ***************/ 
    	
    /*************  总成类别管理  start  ***************/   
    //根据总成代码获取总成
  	public DmAssembly getAssemblyInfo(Long id) {
  		DmAssembly dmas=null;
  		if(id!=null&&!id.equals("")){
  		dmas=dmAssemblyDao.get(DmAssembly.class, id);
  		}
  		return dmas;
  	}
  	public DataGrid dmAssemblyDatagrid(RequestPage page){
  		StringBuffer hql  = new StringBuffer();
  		StringBuffer counthql  = new StringBuffer();
  	    hql.append("from DmAssembly where valid=1   order by id desc ,ordernum asc");
  	  counthql.append("select count(*) from DmAssembly where valid=1   order by id desc ,ordernum asc");
  		long count = dmAssemblyDao.count(counthql.toString(), new Object[0]).longValue();
  		java.util.List rolelist = getDao().find(page.getPage(), page.getRows(), hql.toString(), new Object[0]);
  		return new DataGrid(count, rolelist);
  	}
  	public boolean dmAssemblyInsert(DmAssembly dmAssembly){
  		boolean isyes=false;
  		try{
  		 dmAssemblyDao.save(dmAssembly);
  		 isyes=true;
  		}catch(Exception e){
  			e.printStackTrace();
  			isyes=false;
  		}
  		return isyes;
  	}
  	public boolean dmAssemblyUpdate(DmAssembly dmAssembly){
  		boolean isyes=false;
  		try{
  		 dmAssemblyDao.update(dmAssembly);
  		 isyes=true;
  		}catch(Exception e){
  			e.printStackTrace();
  			isyes=false;
  		}
  		return isyes;
  	}
  	public boolean dmAssemblyDelete(Long id){
  		boolean isyes=false;
  		try{
  			 DmAssembly dmAssembly = getAssemblyInfo(id);
  		 dmAssemblyDao.delete(dmAssembly);
  		 isyes=true;
  		}catch(Exception e){
  			e.printStackTrace();
  			isyes=false;
  		}
  		return isyes;
  	}
  	/*************  总成类别管理  end  ***************/  
  	
  	/*************  分总成类别管理  start  ***************/   
    //根据分总成代码获取分总成
  	public DmSubAssembly getSubAssemblyInfo(Long id) {
  		DmSubAssembly dmas=null;
  		if(id!=null&&!id.equals("")){
  		dmas=dmSubAssemblyDao.get(DmSubAssembly.class, id);
  		}
  		return dmas;
  	}
  	public DataGrid dmSubAssemblyDatagrid(RequestPage page){
  		StringBuffer hql  = new StringBuffer();
		StringBuffer counthql  = new StringBuffer();
  	    hql.append("from DmSubAssembly where valid=1   order by id desc ,ordernum asc");
  	    counthql.append("select count(*) from DmSubAssembly where valid=1   order by id desc ,ordernum asc");
  	    long count = dmSubAssemblyDao.count(counthql.toString(), new Object[0]).longValue();
		List<DmSubAssembly> rolelist = dmSubAssemblyDao.find(page.getPage(), page.getRows(), hql.toString(), new Object[0]);
		List<DmSubAssemblyBean> beanList=new ArrayList<DmSubAssemblyBean>();
		try {
		for(DmSubAssembly dcbs:rolelist){
			DmSubAssemblyBean dmasBean=new DmSubAssemblyBean();
			PropertyUtils.copyProperties(dmasBean, dcbs);
			String assemblyName=getAssembly(dmasBean.getAssemblyId());
			dmasBean.setAssemblyName(assemblyName);
			beanList.add(dmasBean);
		}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return new DataGrid(count, beanList);
  	}
  	public boolean dmSubAssemblyInsert(DmSubAssembly dmSubAssembly){
  		boolean isyes=false;
  		try{
  		 dmSubAssemblyDao.save(dmSubAssembly);
  		 isyes=true;
  		}catch(Exception e){
  			e.printStackTrace();
  			isyes=false;
  		}
  		return isyes;
  	}
  	public boolean dmSubAssemblyUpdate(DmSubAssembly dmSubAssembly){
  		boolean isyes=false;
  		try{
  		 dmSubAssemblyDao.update(dmSubAssembly);
  		 isyes=true;
  		}catch(Exception e){
  			e.printStackTrace();
  			isyes=false;
  		}
  		return isyes;
  	}
  	public boolean dmSubAssemblyDelete(Long id){
  		boolean isyes=false;
  		try{
  			 DmSubAssembly dmSubAssembly = getSubAssemblyInfo(id);
  		 dmSubAssemblyDao.delete(dmSubAssembly);
  		 isyes=true;
  		}catch(Exception e){
  			e.printStackTrace();
  			isyes=false;
  		}
  		return isyes;
  	}
  	/*************  分总成类别管理  end  ***************/  
	/*************  三级分成类别管理  start  ***************/   
  	public DmThreeLevelAssembly getThreeLevelAssemblyInfo(Long id) {
  		DmThreeLevelAssembly dmas=null;
  		if(id!=null&&!id.equals("")){
  		dmas=dmThreeLevelAssemblyDao.get(DmThreeLevelAssembly.class, id);
  		}
  		return dmas;
  	}
  	public DataGrid dmThreeLevelAssemblyDatagrid(RequestPage page){
  		StringBuffer hql  = new StringBuffer();
		StringBuffer counthql  = new StringBuffer();
  	    hql.append("from DmThreeLevelAssembly where valid=1   order by id desc ,ordernum asc");
  	    counthql.append("select count(*) from DmThreeLevelAssembly where valid=1   order by id desc ,ordernum asc");
  	    long count = dmThreeLevelAssemblyDao.count(counthql.toString(), new Object[0]).longValue();
		List<DmThreeLevelAssembly> rolelist = dmThreeLevelAssemblyDao.find(page.getPage(), page.getRows(), hql.toString(), new Object[0]);
		List<DmThreeLevelAssemblyBean> beanList=new ArrayList<DmThreeLevelAssemblyBean>();
		try {
		for(DmThreeLevelAssembly dcbs:rolelist){
			DmThreeLevelAssemblyBean dmasBean=new DmThreeLevelAssemblyBean();
			PropertyUtils.copyProperties(dmasBean, dcbs);
			String assemblyName=getAssembly(dmasBean.getAssemblyId());
			dmasBean.setAssemblyName(assemblyName);
			String subAssemblyName=getDmSubAssembly(dmasBean.getSubAssemblyId());
			dmasBean.setSubAssemblyName(subAssemblyName);
			beanList.add(dmasBean);
		}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return new DataGrid(count, beanList);
  	}
  	public boolean dmThreeLevelAssemblyInsert(DmThreeLevelAssembly dmThreeLevelAssembly){
  		boolean isyes=false;
  		try{
  		 dmThreeLevelAssemblyDao.save(dmThreeLevelAssembly);
  		 isyes=true;
  		}catch(Exception e){
  			e.printStackTrace();
  			isyes=false;
  		}
  		return isyes;
  	}
  	public boolean dmThreeLevelAssemblyUpdate(DmThreeLevelAssembly dmThreeLevelAssembly){
  		boolean isyes=false;
  		try{
  		 dmThreeLevelAssemblyDao.update(dmThreeLevelAssembly);
  		 isyes=true;
  		}catch(Exception e){
  			e.printStackTrace();
  			isyes=false;
  		}
  		return isyes;
  	}
  	public boolean dmThreeLevelAssemblyDelete(Long id){
  		boolean isyes=false;
  		try{
  			 DmThreeLevelAssembly dmThreeLevelAssembly = getThreeLevelAssemblyInfo(id);
  		 dmThreeLevelAssemblyDao.delete(dmThreeLevelAssembly);
  		 isyes=true;
  		}catch(Exception e){
  			e.printStackTrace();
  			isyes=false;
  		}
  		return isyes;
  	}
  	/*************  三级分成类别管理  end  ***************/  
}
