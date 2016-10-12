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
import com.hive.common.entity.IndustryCategoryCode;
import com.hive.common.service.AnnexService;
import com.hive.common.service.IndustryCategoryCodeService;
import com.hive.enterprisemanage.entity.EQualitypromise;
import com.hive.enterprisemanage.model.EQualitypromiseVo;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：Ryu Zheng
 * <p/>创建日期：2013-10-24
 * <p/>创建时间：上午10:59:53
 * <p/>功能描述：企业质量承诺书Service
 * <p/>===========================================================
 */
@Service
public class QualityPromiseService extends BaseService<EQualitypromise>{
	
	@Resource
	private BaseDao<EQualitypromise> qualitypromiseDao;
	
	@Resource
	private AnnexService annexService;

	@Override
	protected BaseDao<EQualitypromise> getDao() {
		return qualitypromiseDao;
	}
	
	@Resource
	private IndustryCategoryCodeService industryCategoryCodeService;
	
	/**
	 * 功能描述：获取某个企业的质量承诺书信息列表
	 * 创建时间:2013-10-30下午1:50:42
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
		List<EQualitypromise> qualitypromiseList = getDao().find(page.getPage(), page.getRows(), hql.toString());
		
		// 将原来列表中的每个对象加工处理后放到新的列表中
		List<EQualitypromiseVo> newPromiseList = new ArrayList<EQualitypromiseVo>();
		for(EQualitypromise promise : qualitypromiseList){
			EQualitypromiseVo promiseVo = new EQualitypromiseVo();
			PropertyUtils.copyProperties(promiseVo, promise);
			
			// 获取附件信息
			Annex annex = annexService.getAnnexByType("E_QUALITYPROMISE", promise.getNquaproid().toString());
			promiseVo.setAnnex(annex);
			
			// 获取"行业分类"代码所对应的名称
			String indCatCode = promise.getCindcatcode();
			if(StringUtils.isNotBlank(indCatCode)){
				
				// 最后一个代码才是真正的代码
				String[] indCatCodeArray = indCatCode.split("_");
				if(indCatCodeArray.length>0){
					String realCode = indCatCodeArray[indCatCodeArray.length-1];
					
					IndustryCategoryCode industryCategory = industryCategoryCodeService.getIndustryCategoryByCode(realCode);
					if(industryCategory != null){
						promiseVo.setCindcatname(industryCategory.getIndCatName());
						newPromiseList.add(promiseVo);
					}
				}
				
			}
		}
		
		return new DataGrid(count, newPromiseList);
	}

}
