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
import com.hive.enterprisemanage.entity.EAwardenterprise;
import com.hive.enterprisemanage.model.EAwardenterpriseVo;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：Ryu Zheng
 * <p/>创建日期：2013-10-24
 * <p/>创建时间：上午10:59:17
 * <p/>功能描述：企业获奖信息Service
 * <p/>===========================================================
 */
@Service
public class AwardEnterpriseService extends BaseService<EAwardenterprise>{
	
	@Resource
	private BaseDao<EAwardenterprise> awardEnterpriseDao;

	@Override
	protected BaseDao<EAwardenterprise> getDao() {
		return awardEnterpriseDao;
	}
	
	@Resource
	private AnnexService annexService;
	//===============================================
	
	/**
	 * 功能描述：获取某个企业的获奖信息列表
	 * 创建时间:2013-10-28下午3:23:18
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
		List<EAwardenterprise> awardEnterpriseList = getDao().find(page.getPage(), page.getRows(), hql.toString());
		
		List<EAwardenterpriseVo> newAwardEnterpriseList = new ArrayList<EAwardenterpriseVo>();
		for(EAwardenterprise award : awardEnterpriseList){
			EAwardenterpriseVo awardVo = new EAwardenterpriseVo();
			PropertyUtils.copyProperties(awardVo, award);
			
			// 获取附件信息
			Annex annex = annexService.getAnnexByType("E_AWARDENTERPRISE", award.getNawaentid().toString());
			awardVo.setAnnex(annex);
			
			newAwardEnterpriseList.add(awardVo);
		}
		
		return new DataGrid(count, newAwardEnterpriseList);
	}

}
