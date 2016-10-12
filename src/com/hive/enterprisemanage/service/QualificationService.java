package com.hive.enterprisemanage.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Annex;
import com.hive.common.service.AnnexService;
import com.hive.enterprisemanage.entity.EQualification;
import com.hive.enterprisemanage.model.EQualificationVo;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：Ryu Zheng
 * <p/>创建日期：2013-10-24
 * <p/>创建时间：上午11:00:24
 * <p/>功能描述：企业资质信息Service
 * <p/>===========================================================
 */
@Service
public class QualificationService extends BaseService<EQualification>{

	@Resource
	private BaseDao<EQualification> eQualificationDao;
	
	@Override
	protected BaseDao<EQualification> getDao() {
		return eQualificationDao;
	}
	
	@Resource
	private AnnexService annexService;
	//===============================================
	
	/**
	 * 功能描述：获取某个企业的资质信息列表
	 * 创建时间:2013-10-30上午10:17:49
	 * 创建人: Ryu Zheng
	 * 
	 * @param page
	 * @param entInfoId
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public DataGrid datagrid(RequestPage page, Long entInfoId) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		StringBuilder hql = new StringBuilder();
		hql.append("FROM ").append(getEntityName()).append(" WHERE cvalid = ").append(SystemCommon_Constant.VALID_STATUS_1);
		hql.append(" AND nenterpriseid = ").append(entInfoId);
		if(!StringUtils.isEmpty(page.getSort())){
			hql.append(" ORDER BY ").append(page.getSort()).append(" ").append(page.getOrder());
		}
		
		StringBuilder countHql = new StringBuilder();
		countHql.append("select count(*) FROM ").append(getEntityName())
				.append(" WHERE cvalid = ")
				.append(SystemCommon_Constant.VALID_STATUS_1)
				.append(" AND nenterpriseid = ").append(entInfoId);
		long count=getDao().count(countHql.toString());
		List<EQualification> qualificationList = getDao().find(page.getPage(), page.getRows(), hql.toString());
		
		List<EQualificationVo> qualificationVoList = new ArrayList<EQualificationVo>();
		for(EQualification qualification : qualificationList){
			EQualificationVo qualificationVo = new EQualificationVo();
			PropertyUtils.copyProperties(qualificationVo, qualification);
			
			// 获取附件信息
			Annex annex = annexService.getAnnexByType("E_QUALIFICATION", qualification.getNquaid().toString());
			qualificationVo.setAnnex(annex);
			
			qualificationVoList.add(qualificationVo);
		}
		
		return new DataGrid(count, qualificationVoList);
	}
	
	//此方法用于单个字段的查询,且返回值为一条数据(字段名、int型的值、String型的值)
	public EQualification findEqfByCustom(String columnName,Long iVal,String sVal)
	{
		EQualification eqf;
		if(sVal == null)
		{
			List<EQualification> leqf = eQualificationDao.find("from "+getEntityName()+" where "+columnName+"=?", iVal);
			if(leqf.size()>0)
			{
				eqf = leqf.get(0);
			}
			else{
				eqf = null;
			}
		}
		else{
			List<EQualification> leqf = eQualificationDao.find("from "+getEntityName()+" where "+columnName+"=?", sVal);
			if(leqf.size()>0)
			{
				eqf = leqf.get(0);
			}
			else{
				eqf = null;
			}
		}
		return eqf;
	}

}
