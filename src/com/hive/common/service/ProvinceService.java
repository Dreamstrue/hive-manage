package com.hive.common.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Province;

import dk.dao.BaseDao;
import dk.service.BaseService;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：Ryu Zheng
 * <p/>创建日期：2013-11-3
 * <p/>创建时间：下午2:13:06
 * <p/>功能描述：省份代码表Service
 * <p/>===========================================================
 */
@Service
public class ProvinceService extends BaseService<Province> {

	@Resource
	private BaseDao<Province> provinceDao;
	@Override
	protected BaseDao<Province> getDao() {
		return provinceDao;
	}
	
	
	/**
	 * 功能描述：获取省份代码表中的所有数据
	 * 创建时间:2013-11-3下午2:12:28
	 * 创建人: Ryu Zheng
	 * 
	 * @return
	 */
	public List<Province> getProvince() {
		StringBuffer sb = new StringBuffer(" FROM "+ getEntityName());
		sb.append(" WHERE 1=1 AND cvalid = '"+SystemCommon_Constant.VALID_STATUS_1+"' ORDER BY isortorder ASC ");
		List<Province> list = provinceDao.find(sb.toString());
		return list;
	}
	
	/**
	 * 功能描述：根据代码获取省份代码表中的相应数据
	 * 创建时间:2013-11-3下午2:50:06
	 * 创建人: Ryu Zheng
	 * 
	 * @param code
	 * @return
	 */
	public List<Province> getProvinceByCode(String code) {
		StringBuffer sb = new StringBuffer(" FROM "+ getEntityName());
		sb.append(" WHERE 1=1 AND provinceCode=? AND cvalid = '"+SystemCommon_Constant.VALID_STATUS_1+"'");
		List<Province> list = provinceDao.find(sb.toString(), code);
		return list;
	}
	/**
	 * 功能描述：根据代码获取省份代码表中的相应数据
	 * @param code
	 * @return
	 */
	public String getProvinceNameByCode(String code) {
		StringBuffer sb = new StringBuffer(" FROM "+ getEntityName());
		sb.append(" WHERE 1=1 AND provinceCode=? AND cvalid = '"+SystemCommon_Constant.VALID_STATUS_1+"'");
		List<Province> list = provinceDao.find(sb.toString(), code);
		if(list.size()>0){
			return list.get(0).getProvinceName();
		}else{
			return "其他";
		}
	}
}
