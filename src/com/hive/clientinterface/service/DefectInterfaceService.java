/**
 * 
 */
package com.hive.clientinterface.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import com.hive.util.DataUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

/**  
 * Filename: DefectInterfaceService.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  YangHui
 * @version: 1.0  
 * @Create:  2014-6-3  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-6-3 下午3:03:50				YangHui 	1.0
 */
@Service
public class DefectInterfaceService extends BaseService<DefectRecord> {

	@Resource
	private AnnexService annexService;
	
	@Resource
	private BaseDao<DefectRecord> defectRecordDao; //产品缺陷记录表（儿童玩具或其他）
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
	
	
	@Resource
	private BaseDao<CommonContent> commonContentDao;
	@Override
	protected BaseDao<DefectRecord> getDao() {
		return defectRecordDao;
	}

	/**
	 * 保存缺陷采集信息-汽车
	 * @author 燕珂
	 * @date 2015-11-12
	 * @param defectCarInfo
	 * @param defectCarDescription
	 * @param defectCarContract
	 * @return 保存成功返回 true，失败返回 false
	 */
	public boolean saveDefectCarAllInfo(DefectRecordCarInfo defectCarInfo, DefectRecordCarDefect defectCarDescription, DefectRecordCarContacts defectCarContract, List<Annex> annexlist) {
		try {
			// 4个表的保存放到同一个事务中
			DefectRecordCar defectCar = new DefectRecordCar();
			defectCar.setReportdate(new Date());
			defectCar.setIsValid(SystemCommon_Constant.VALID_STATUS_1);
			saveDefectRecordCar(defectCar);
			
			Long defectId = defectCar.getId();
			
			annexService.saveAnnexList(annexlist, defectId.toString());
			
			defectCarInfo.setDefectId(defectId);
			saveDefectRecordCarInfo(defectCarInfo);
			
			defectCarDescription.setDefectId(defectId);
			saveDrCarDefect(defectCarDescription);
			
			defectCarContract.setDefectId(defectId);
			saveDefectRecordCarConstact(defectCarContract);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @Description: 保存缺陷采集表（儿童玩具类）
	 * @author YangHui 
	 * @Created 2014-6-3
	 * @param dr
	 */
	public void saveDefectRecord(DefectRecord dr) {
		defectRecordDao.save(dr);
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


	/**
	 * 
	 * @Description:汽车品牌车型系列表
	 * @author YangHui 
	 * @Created 2014-6-4
	 * @param brandId
	 * @return
	 */
	public List<DmCarBrandSeries> getCarBrandSeriesList(Long  brandId) {
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
	
	
	//保存汽车类缺陷采集信息
	public void saveDefectRecordCar(DefectRecordCar car) {
		defectRecordCarDao.save(car);
	}

	//保存汽车类缺陷采集---联系人信息
	public void saveDefectRecordCarConstact(DefectRecordCarContacts carContact) {
		defectRecordCarContactsDao.save(carContact);
	}

	//保存汽车类缺陷采集--车辆信息
	public void saveDefectRecordCarInfo(DefectRecordCarInfo carinfo) {
		defectRecordCarInfoDao.save(carinfo);
	}

	//保存汽车类缺陷采集--缺陷描述信息
	public void saveDrCarDefect(DefectRecordCarDefect carDefect) {
		defectRecordCarDefectDao.save(carDefect);
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


	public void updateDefectRecordCar(DefectRecordCar car) {
		defectRecordCarDao.update(car);
	}


	//保存企业备案信息
	public void saveProductKeepRecord(ProductKeepRecord record) {
		productKeepRecordDao.save(record);
	}


	public DataGrid getProductKeepRecord(RequestPage page, Long userId) {
		String hql =" from ProductKeepRecord where reportuserid=? order by keepRecordDate desc ";
		List<ProductKeepRecord> list = productKeepRecordDao.find(hql, new Object[]{userId});
		return new DataGrid(list.size(), pageList(page, list));
	}

	
	
	
	
	public List pageList(RequestPage page ,List beanList){
		List childList = new ArrayList();
		if(beanList.size()>0){
			int pageNo = page.getPage();
			int pageSize = page.getRows();
			int begin = (pageNo - 1) * pageSize;
			int end = (pageNo * pageSize >= beanList.size() ? beanList.size(): pageNo * pageSize);
			childList = beanList.subList(begin, end);
		}
		return childList;
	}


	/**
	 * 
	 * @Description: 缺陷信息查询
	 * @author YangHui 
	 * @Created 2014-6-11
	 * @param peporttype
	 * @param proName
	 * @param producerName
	 * @return
	 */
	public DataGrid getDefectRecordInfos(RequestPage page,String peporttype, String keyword) {
		
		List<DefectRecordBean> blist = new ArrayList<DefectRecordBean>();
		List<DefectRecord> drlist = new ArrayList<DefectRecord>();
		List carlist = new ArrayList();

		if("0".equals(peporttype)){
			//全部类别
			drlist = getDefectRecord(peporttype,keyword);
			carlist = getDefectRecordCarInfo(peporttype,keyword);
		}else if("1".equals(peporttype)){
			//儿童玩具类
			drlist = getDefectRecord(peporttype,keyword);
		}else if("2".equals(peporttype)){
			//汽车类
			carlist = getDefectRecordCarInfo(peporttype,keyword);
		}else if("3".equals(peporttype)){
			//其他类
			drlist = getDefectRecord(peporttype,keyword);
		}
		
		//1.查询儿童玩具、其他类缺陷信息
		for(Iterator it = drlist.iterator();it.hasNext();){
			DefectRecord dr = (DefectRecord) it.next();
			DefectRecordBean bean = new DefectRecordBean();
			bean.setId(dr.getId());
			bean.setPeporttype(dr.getPeporttype());
			bean.setProducerName(""); //由于此类别的暂时没有生产厂家，设为空
			bean.setProName(dr.getProdName()); //产品名称
			bean.setProType(dr.getProdType()); //产品类型
			bean.setReportdate(dr.getReportdate()); //报告时间
			bean.setDefectDescription(dr.getDefectDescription());//缺陷描述
			blist.add(bean);
		}
		
		//2.查询汽车类缺陷信息
		for(Iterator it = carlist.iterator();it.hasNext();){
			Object[] obj = (Object[]) it.next();
			DefectRecordCar car = (DefectRecordCar) obj[0];
			DefectRecordCarInfo info = (DefectRecordCarInfo) obj[1];
			String defectDescription = (String) obj[2];
			DefectRecordBean bean = new DefectRecordBean();
			
			bean.setId(info.getDefectId());
			DmCarBrand b = dmCarBrandDao.get(DmCarBrand.class, info.getCarbrand());
			String carBrand = b==null?"":b.getBrandname(); //品牌名称
			DmCarBrandSeries dmCarBrandSeries = dmCarBrandSeriesDao.get(DmCarBrandSeries.class, info.getCarseries());
			String seriesname = dmCarBrandSeries != null ? dmCarBrandSeries.getSeriesname() : ""; //车型系列名称
			DmCarBrandSeriesModel model = dmCarBrandSeriesModelDao.get(DmCarBrandSeriesModel.class,info.getCarmodelname());
			String seriesModelName = model==null?"":model.getModelname();//车型名称
			bean.setProName(carBrand+" "+seriesModelName); //把品牌+车型名称赋给产品名称
			bean.setProType(seriesname); //把车型系列赋给产品类型
			bean.setPeporttype("2");
			DmCarBrand dmCarBrand = dmCarBrandDao.get(DmCarBrand.class, info.getCarbrand());
			String producername = dmCarBrand != null ? dmCarBrand.getProducername() : "";
			bean.setProducerName(producername); //生产厂家
			bean.setReportdate(car.getReportdate());
			bean.setDefectDescription(defectDescription); //缺陷描述
			blist.add(bean);
 		}
		
		ComparatorDefect comparator = new ComparatorDefect();
		Collections.sort(blist, comparator); 
		
		List  childList = new ArrayList(); // 此处需要初始化，否则当查询不到数据时，返回到手机端的 data 为 null 会报错
		if(blist.size()>0){
			int pageNo = page.getPage();
			int pageSize = page.getRows();
			int begin = (pageNo-1)*pageSize;
			int end = (pageNo*pageSize>=blist.size()?blist.size():pageNo*pageSize);
			childList = blist.subList(begin, end);
			return new DataGrid(blist.size(), childList);
		}else{
			return new DataGrid(blist.size(), childList);
		}
	}

	/**
	 * 
	 * @Description: 汽车类
	 * @author YangHui 
	 * @Created 2014-6-11
	 * @param peporttype
	 * @param producerName
	 * @param proName
	 * @return
	 */
	private List getDefectRecordCarInfo(String peporttype,
			String keyword) {
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select a,b,c.defectDescription  from DefectRecordCar a, DefectRecordCarInfo b ,DefectRecordCarDefect c where a.id = b.defectId and a.id = c.defectId and a.isValid='"+SystemCommon_Constant.VALID_STATUS_1+"' ");
		if(!DataUtil.isEmpty(keyword)){
			hql.append(" and b.carproducername like '%"+keyword+"%' ");
		}
		List list = defectRecordCarInfoDao.find(hql.toString(), new Object[0]);
		return list;
	}


	/**
	 * 
	 * @Description: 儿童玩具或其他类
	 * @author YangHui 
	 * @Created 2014-6-11
	 * @param peporttype
	 * @param producerName
	 * @param proName
	 * @return
	 */
	private List<DefectRecord> getDefectRecord(String peporttype,
			String keyword) {
		List<DefectRecord> list = new ArrayList<DefectRecord>();
		StringBuffer hql = new StringBuffer();
		hql.append(" from DefectRecord where 1=1 ");
		if(!"0".equals(peporttype)){
			hql.append(" and peporttype='"+peporttype+"' ");
		}
		if(!DataUtil.isEmpty(keyword)){
			hql.append(" and prodName like '%"+keyword+"%' ");
		}
		
		list = defectRecordDao.find(hql.toString(), new Object[0]);
		
		return list;
	}


	/**
	 * 
	 * @Description: 查看缺陷的详细信息
	 * @author YangHui 
	 * @Created 2014-6-12
	 * @param peporttype
	 * @param id
	 * @return
	 */
	public Object getDefectRecordDetail(String peporttype, Long id) {
		Object obj = null;
		List list = new ArrayList();
		StringBuffer hql  = new StringBuffer();
		if("2".equals(peporttype)){
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
					obj = bean;
				} catch (Exception e) {
				}
			}
			
			
		}else{
			//儿童玩具或其他类缺陷
			hql.append("  from DefectRecord where id=? and peporttype=? ");
			list = defectRecordDao.find(hql.toString(), new Object[]{id,peporttype});
			DefectRecord dr = new DefectRecord();
			if(DataUtil.listIsNotNull(list)){
				dr = (DefectRecord) list.get(0);
				obj = dr;
			}
		}
		
		
		return obj;
	}


	/**
	 * 
	 * @Description: 召回公告列表
	 * @author YangHui 
	 * @Created 2014-6-17
	 * @return
	 */
	public DataGrid getRecallNoticeList(RequestPage page) {
		String hql = " from CommonContent where infoCateId=113 and valid='1' ";
		List<CommonContent> list = commonContentDao.find(page.getPage(),page.getRows(),hql, new Object[0]); //此处113为信息类别表里召回公告的ID
		Long total = commonContentDao.count("select count(*) from CommonContent where infoCateId=113 and valid='1'", new Object[0]);
		return new DataGrid(total, list);
	}


	public CommonContent getCommonContentById(Long id) {
		CommonContent cc = commonContentDao.get(CommonContent.class, id);
		byte[] b = AnnexFileUpLoad.getContentFromFile(cc.getContent());
		cc.setContent(new String(b));
		return cc;
	}
}
