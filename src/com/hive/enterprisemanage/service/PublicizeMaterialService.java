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
import com.hive.enterprisemanage.entity.EPublicizeMaterial;
import com.hive.enterprisemanage.model.EPublicizeMaterialVo;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：Ryu Zheng
 * <p/>创建日期：2013-10-24
 * <p/>创建时间：上午10:59:17
 * <p/>功能描述：企业宣传资料Service
 * <p/>===========================================================
 */
@Service
public class PublicizeMaterialService extends BaseService<EPublicizeMaterial>{
	
	@Resource
	private BaseDao<EPublicizeMaterial> publicizeMaterialDao;

	@Override
	protected BaseDao<EPublicizeMaterial> getDao() {
		return publicizeMaterialDao;
	}
	
	@Resource
	private AnnexService annexService;
	// ===========================================================
	
	/**
	 * 功能描述：获取某个企业的宣传资料信息列表
	 * 创建时间:2013-10-30下午2:50:31
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
		List<EPublicizeMaterial> publicizeMaterialList = getDao().find(page.getPage(), page.getRows(), hql.toString());
		
		List<EPublicizeMaterialVo> newPublicizeMaterialList = new ArrayList<EPublicizeMaterialVo>();
		for(EPublicizeMaterial publicizeMaterial : publicizeMaterialList){
			EPublicizeMaterialVo publicizeMaterialVo = new EPublicizeMaterialVo();
			PropertyUtils.copyProperties(publicizeMaterialVo, publicizeMaterial);
			
			// 获取附件信息
			Annex annex = annexService.getAnnexByType("E_PUBLICIZEMATERIAL", publicizeMaterial.getNpubmatid().toString());
			publicizeMaterialVo.setAnnex(annex);
			
			newPublicizeMaterialList.add(publicizeMaterialVo);
		}
		
		
		return new DataGrid(count, newPublicizeMaterialList);
	}

}
