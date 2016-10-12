package com.hive.common.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.City;

import dk.dao.BaseDao;
import dk.service.BaseService;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：Ryu Zheng
 * <p/>创建日期：2013-11-3
 * <p/>创建时间：下午2:13:06
 * <p/>功能描述：地市代码表Service
 * <p/>===========================================================
 */
@Service
public class CityService extends BaseService<City> {

	@Resource
	private BaseDao<City> cityDao;
	@Override
	protected BaseDao<City> getDao() {
		return cityDao;
	}
	
	
	/**
	 * 功能描述：获取地市代码表中的所有数据
	 * 创建时间:2013-11-3下午2:12:28
	 * 创建人: Ryu Zheng
	 * 
	 * @return
	 */
	public List<City> getCity() {
		StringBuffer sb = new StringBuffer(" FROM "+ getEntityName());
		sb.append(" WHERE 1=1 AND cvalid = '"+SystemCommon_Constant.VALID_STATUS_1+"' ORDER BY isortorder ASC ");
		List<City> list = cityDao.find(sb.toString());
		return list;
	}
	
	/**
	 * 功能描述：根据省代码获取其下地市代码表中的数据
	 * 创建时间:2013-11-3下午2:50:06
	 * 创建人: Ryu Zheng
	 * 
	 * @param provinceCode
	 * @return
	 */
	public List<City> getCitysOfProvince(String provinceCode) {
		StringBuffer sb = new StringBuffer(" FROM "+ getEntityName());
		sb.append(" WHERE cvalid = '").append(SystemCommon_Constant.VALID_STATUS_1).append("'");
		sb.append(" AND provinceCode=? ");
		List<City> list = cityDao.find(sb.toString(), provinceCode);
		return list;
	}
	/**
	 * 功能描述：根据市代码获取其下地市代码表中的数据
	 * @param provinceCode
	 * @return
	 */
	public String getCityNameCityNo(String cityCode) {
		StringBuffer sb = new StringBuffer(" FROM "+ getEntityName());
		sb.append(" WHERE cvalid = '").append(SystemCommon_Constant.VALID_STATUS_1).append("'");
		sb.append(" AND cityCode=? ");
		List<City> list = cityDao.find(sb.toString(), cityCode);
		if(list.size()>0){
			return list.get(0).getCityName();
		}else{
			return "其他";
		}
	}
}
