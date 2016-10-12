/**
 * 
 */
package com.hive.clientinterface.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.City;
import com.hive.common.entity.District;
import com.hive.common.entity.Province;
import com.hive.enterprisemanage.entity.EnterpriseProductCategory;
import com.hive.enterprisemanage.model.ProductCategoryBean;
import com.hive.surveymanage.entity.SurveyCategory;
import com.hive.surveymanage.entity.SurveyIndustry;
import com.hive.systemconfig.entity.InfoCategory;
import com.hive.util.DataUtil;

import dk.dao.BaseDao;
import dk.service.BaseService;

/**  
 * Filename: InfoCategoryInterfaceService.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-4-8  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-4-8 上午9:55:27				yanghui 	1.0
 */
@Service
public class InfoCategoryInterfaceService extends BaseService<InfoCategory> {

	@Resource
	private BaseDao<InfoCategory> infoCategoryDao;
	@Resource
	private BaseDao<EnterpriseProductCategory> enterpriseProductCategoryDao;
	@Resource
	private BaseDao<Province> provinceDao;
	@Resource
	private BaseDao<City> cityDao;
	@Resource
	private BaseDao<District> districtDao;
	@Resource
	private BaseDao<SurveyCategory> surveyCategoryDao;
	@Resource
	private BaseDao<SurveyIndustry> surveyIndustryDao;
	
	@Override
	protected BaseDao<InfoCategory> getDao() {
		return infoCategoryDao;
	}
	
	
	public List<InfoCategory> getAllInfoCategory() {
		String hql = " from "+getEntityName()+" where valid='"+SystemCommon_Constant.VALID_STATUS_1+"' and parentId<> '0' ";
		List<InfoCategory> list = getDao().find(hql, new Object[0]);
		return list;
	}


	/**
	 * 
	 * @Description:  消费资讯下的类别列表
	 * @author yanghui 
	 * @Created 2014-5-6
	 * @return
	 */
	public List<InfoCategory> getCateList(Long pid) {
		String hql = " from "+getEntityName()+" where valid='"+SystemCommon_Constant.VALID_STATUS_1+"' and clientShow='1' and parentId=? ";
		List<InfoCategory> list = getDao().find(hql,pid);
		return list;
	}


	/**
	 * 
	 * @Description:  产品类别列表
	 * @author yanghui 
	 * @Created 2014-5-14
	 * @return
	 */
	public List<ProductCategoryBean> getProductCategoryList() {
		List<ProductCategoryBean> blist = new ArrayList<ProductCategoryBean>();
		String hql = " from EnterpriseProductCategory  where valid='"+SystemCommon_Constant.VALID_STATUS_1+"'";
		List<EnterpriseProductCategory> list = enterpriseProductCategoryDao.find(hql, new Object[0]);
		if(DataUtil.listIsNotNull(list)){
			for(int i=0;i<list.size();i++){
				EnterpriseProductCategory e = list.get(i);
				ProductCategoryBean bean = new ProductCategoryBean();
				bean.setId(e.getId());
				bean.setPid(e.getParentId());
				bean.setText(e.getText());
				blist.add(bean);
			}
		}
		return blist;
	}


	/**
	 * 
	 * @Description: 省份代码
	 * @author YangHui 
	 * @Created 2014-6-3
	 * @return
	 */
	public List<Province> getProvinceList() {
		String hql = " from Province where cvalid ='1' order by sortOrder asc ";
		List<Province> list = provinceDao.find(hql, new Object[0]);
		return list;
	}


	/**
	 * 
	 * @Description: 某一省份下的城市代码
	 * @author YangHui 
	 * @Created 2014-6-3
	 * @param pcode
	 * @return
	 */
	public List<City> getCityList(String pcode) {
		String hql = " from City where cvalid ='1' and provinceCode=? order by sortOrder asc ";
		List<City> list = cityDao.find(hql, pcode);
		return list;
	}

	/**
	 * 
	 * @Description: 某一城市下的县区代码
	 * @author YangHui 
	 * @Created 2014-6-3
	 * @param ccode
	 * @return
	 */
	public List<District> getDistrictList(String ccode) {
		String hql = " from District where cvalid ='1' and cityCode=? order by sortOrder asc ";
		List<District> list = districtDao.find(hql, ccode);
		return list;
	}


	/**
	 * 
	 * @Description: 问卷类别列表
	 * @author YangHui 
	 * @Created 2014-9-4
	 * @return
	 */
	public List<SurveyCategory> getSurveyCategoryList() {
		String hql = " from SurveyCategory where valid='"+SystemCommon_Constant.VALID_STATUS_1+"' order by categoryname asc";
		List<SurveyCategory> list = surveyCategoryDao.find(hql, new Object[0]);
		return list;
	}
	
	/**
	 * 
	 * @Description:问卷行业类别列表
	 * @author YangHui 
	 * @Created 2014-9-10
	 * @return
	 */
	public List<SurveyIndustry> getSurveyIndustryList(){
		String hql = " from SurveyIndustry where id in (select id from SurveyIndustry where pid='0' and valid='"+SystemCommon_Constant.VALID_STATUS_1+"') and valid='"+SystemCommon_Constant.VALID_STATUS_1+"'";
		List<SurveyIndustry> list = surveyIndustryDao.find(hql, new Object[0]);
		return list;
	}

}
