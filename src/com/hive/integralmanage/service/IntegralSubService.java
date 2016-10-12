/**
 * 
 */
package com.hive.integralmanage.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.clientmanage.service.ClientUserService;
import com.hive.common.SystemCommon_Constant;
import com.hive.infomanage.model.InfoSearchBean;
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
public class IntegralSubService extends BaseService<IntegralSub> {

	@Resource
	private BaseDao<IntegralSub> integralSubDao;
	@Override
	protected BaseDao<IntegralSub> getDao() {
		return integralSubDao;
	}
	
	@Resource
	private ClientUserService clientUserService;
	/**
	 * 
	 * @Description:  提供给新闻资讯统计分享次数的方法 
	 * @author yanghui 
	 * @Created 2014-3-11
	 * @param newsId  新闻的ID 
	 * @param integralCategory  分享的类别
	 * @return
	 */
	public int getShareNumBySourceAndCateGory(Long newsId,String integralCategory) {
		int num = 0;
		String hql = " from "+ getEntityName() + " where integralSource =? and inteCateId =? ";
		List<IntegralSub> list = getDao().find(hql, new Object[]{newsId,Long.valueOf(integralCategory)});
		num = list.size();
		return num;
	}

	
	/**
	 * 
	 * @Description:  针对新闻资讯的分享统计方法
	 * @author yanghui 
	 * @Created 2014-3-21
	 * @param page
	 * @param bean
	 * @param newsId
	 * @return
	 */
	public DataGrid shareDataGrid(RequestPage page, InfoSearchBean bean,
			Long newsId) {
		
		StringBuffer hql = new StringBuffer();
		StringBuffer sb = new StringBuffer();
		StringBuffer counthql = new StringBuffer();
		
		hql.append(" from "+getEntityName()+" where integralSource= ? ");  //注意：这里的积分来源指的是资源的ID值，如分享的资讯ID值或投票、问卷调查积分
		if(!DataUtil.isNull(bean.getCreateTime())){
			sb.append(" and STR_TO_DATE(gainDate,'%Y-%m-%d') ='"+DateUtil.dateToString(bean.getCreateTime())+"'");
		}
		hql.append(" and inteCateId='"+SystemCommon_Constant.INTEGRAL_CATEGORY_SHARE+"'");
		List<IntegralSub> list = getDao().find(page.getPage(),page.getRows(),hql.append(sb).toString(), newsId);
		List<IntegralBean> blist = new ArrayList<IntegralBean>();
		for(int i=0;i<list.size();i++){
			IntegralSub sub = list.get(i);
			String userName = clientUserService.get(sub.getUserId()).getCusername();
			IntegralBean b = new IntegralBean();
			b.setUserName(userName);
			b.setGainDate(sub.getGainDate());
			blist.add(b);
		}
		
		counthql.append(" select count(*) from "+getEntityName()+" where integralSource=? ");
		counthql.append(sb);
		Long count = getDao().count(counthql.toString(), newsId);
		return new DataGrid(count, blist);
	}


	
	public List<IntegralSub> getIntegralListByUserId(Long userId) {
		String hql = " from "+getEntityName()+" where userId=? order by gainDate desc";
		List<IntegralSub> list = getDao().find(hql, userId);
		return list;
	}

}
