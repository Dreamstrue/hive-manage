package com.hive.common.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hive.common.entity.CompanyTypeCode;

import dk.dao.BaseDao;
import dk.service.BaseService;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：Ryu Zheng
 * <p/>创建日期：2013-11-3
 * <p/>创建时间：下午2:13:06
 * <p/>功能描述：登记注册类型代码表Service
 * <p/>===========================================================
 */
@Service
public class CompanyTypeCodeService extends BaseService<CompanyTypeCode> {

	@Resource
	private BaseDao<CompanyTypeCode> companyTypeCodeDao;
	@Override
	protected BaseDao<CompanyTypeCode> getDao() {
		return companyTypeCodeDao;
	}
	
	/**
	 * 功能描述：加载登记注册类型代码表中的数据
	 * 创建时间:2013-11-5下午4:45:16
	 * 创建人: Ryu Zheng
	 * 
	 * @param parentCode 父分类代码
	 * @return
	 */
	public List<CompanyTypeCode> getCompanyType(String parentCode) {
		StringBuffer sb = new StringBuffer(" FROM "+ getEntityName());
		sb.append(" WHERE 1=1 AND cvalid = '1' ");
		// 如果父分类不为空，则加载该父节点下的代码数据；如果为空，则加载一级分类代码数据
		if(StringUtils.isNotBlank(parentCode)){
			sb.append(" AND comTypParentCode = '").append(parentCode).append("'");
		}else{
			sb.append(" AND comTypParentCode = '999999999'");
		}
		sb.append(" ORDER BY isortorder ASC ");
		List<CompanyTypeCode> list = companyTypeCodeDao.find(sb.toString());
		return list;
	}
	
}
