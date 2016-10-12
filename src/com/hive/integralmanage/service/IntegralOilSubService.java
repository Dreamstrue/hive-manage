/**
 * 
 */
package com.hive.integralmanage.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.integralmanage.entity.IntegralOilSub;
import com.hive.integralmanage.entity.IntegralSub;
import com.hive.integralmanage.model.IntegralBean;
import com.hive.util.DataUtil;
import com.hive.util.DateUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

/**  
 * Filename: IntegralSubService.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-11  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-11 上午9:34:11				yanghui 	1.0
 */
@Service
public class IntegralOilSubService extends BaseService<IntegralOilSub> {

	@Resource
	private BaseDao<IntegralOilSub> integralSubDao;
	@Override
	protected BaseDao<IntegralOilSub> getDao() {
		return integralSubDao;
	}
	/**
	  * 方法名称：getIntegralCateByUserId
	  * 功能描述：积分类别（中石油中石化）
	  * 创建时间:2016年2月19日下午3:16:12
	  * 创建人: pengfei Zhao
	  * @param @param userId
	  * @param @return 
	  * @return List<IntegralOilSub>
	 */
	public List<IntegralOilSub> getIntegralCateByUserId(Long userId) {
		String hql = " from "+getEntityName()+" where userId=? group by entityCategory";
		List<IntegralOilSub> list = getDao().find(hql, userId);
		return list;
	}
	/**
	  * 方法名称：getIntCateByUserId
	  * 功能描述：获取某个类型加油站下面的总次数
	  * 创建时间:2016年2月19日下午3:18:44
	  * 创建人: pengfei Zhao
	  * @param @param userId
	  * @param @return 
	  * @return Long
	 */
	public Long getIntCateByUserId(Long userId,Long entityCategory) {
		String hql = " select count(*) from "+getEntityName()+" where userId="+userId+" and entityCategory="+entityCategory;
		Long count = getDao().count(hql, new Object[]{});
		return count;
	}
	/**
	  * 方法名称：getTotalIntegralByUserId
	  * 功能描述：获取某个类型加油站下面的总积分
	  * 创建时间:2016年2月19日下午3:30:56
	  * 创建人: pengfei Zhao
	  * @param @param userId
	  * @param @param entityCategory
	  * @param @return 
	  * @return Long
	 */
	public Long getTotalIntegralByUserId(Long userId,Long entityCategory) {
		String hql = "from "+getEntityName()+" where userId="+userId+" and entityCategory="+entityCategory;
		List<IntegralOilSub> list = getDao().find(hql, new Object[]{});
		Long totalIntegral=0L;
		for(IntegralOilSub integralos:list){
			totalIntegral+=integralos.getBasicValue();
		}
		return totalIntegral;
	}
	
	public List<IntegralOilSub> getIntegralListByUserId(Long userId) {
		String hql = " from "+getEntityName()+" where userId=? order by gainDate desc";
		List<IntegralOilSub> list = getDao().find(hql, userId);
		return list;
	}
	public List<IntegralOilSub> getInteCateListByUserId(Long userId,Long entityCategory) {
		String hql = " from "+getEntityName()+" where userId=? and entityCategory=? order by gainDate desc";
		List<IntegralOilSub> list = getDao().find(hql, new Object[]{userId,entityCategory});
		return list;
	}
}
