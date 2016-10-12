package com.hive.common.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.District;

import dk.dao.BaseDao;
import dk.service.BaseService;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：Ryu Zheng
 * <p/>创建日期：2013-11-3
 * <p/>创建时间：下午2:13:06
 * <p/>功能描述：县区代码表Service
 * <p/>===========================================================
 */
@Service
public class DistrictService extends BaseService<District> {

	@Resource
	private BaseDao<District> districtDao;
	@Override
	protected BaseDao<District> getDao() {
		return districtDao;
	}
	
	
	/**
	 * 功能描述：获取县区代码表中的所有数据
	 * 创建时间:2013-11-3下午2:12:28
	 * 创建人: Ryu Zheng
	 * 
	 * @return
	 */
	public List<District> getDistrict() {
		StringBuffer sb = new StringBuffer(" FROM "+ getEntityName());
		sb.append(" WHERE 1=1 AND cvalid = '"+SystemCommon_Constant.VALID_STATUS_1+"' ORDER BY isortorder ASC ");
		List<District> list = districtDao.find(sb.toString());
		return list;
	}
	
	/**
	 * 功能描述：根据市区代码获取其下县区的相应数据
	 * 创建时间:2013-11-3下午2:50:06
	 * 创建人: Ryu Zheng
	 * 
	 * @param code 市区代码
	 * @return
	 */
	public List<District> getDistrictsOfCity(String cityCode) {
		StringBuffer sb = new StringBuffer(" FROM "+ getEntityName());
		sb.append(" WHERE cvalid = '").append(SystemCommon_Constant.VALID_STATUS_1).append("'");
		sb.append(" AND cityCode=? ");
		List<District> list = districtDao.find(sb.toString(), cityCode);
		return list;
	}
	/**
	 * 功能描述：根据县代码获取其下县区的相应数据
	 * 创建时间:2013-11-3下午2:50:06
	 * 创建人: Ryu Zheng
	 * 
	 * @param code 市区代码
	 * @return
	 */
	public String getDistrictsOfcode(String disCode) {
		StringBuffer sb = new StringBuffer(" FROM "+ getEntityName());
		sb.append(" WHERE cvalid = '").append(SystemCommon_Constant.VALID_STATUS_1).append("'");
		sb.append(" AND districtCode=? ");
		List<District> list = districtDao.find(sb.toString(), disCode);
		if(list.size()>0){
			return list.get(0).getDistrictName();
		}else{
			return "其他";
		}
	}
}
