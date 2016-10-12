/**
 * 
 */
package com.hive.subscription.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.subscription.entity.InfoSubscription;
import com.hive.subscription.model.InfoSubscriptionBean;
import com.hive.subscription.model.SubscriptionSearchBean;
import com.hive.systemconfig.service.InfoCategoryService;
import com.hive.util.DataUtil;
import com.hive.util.DateUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

/**  
 * Filename: InfoSubscriptionService.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-2-27  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-2-27 上午11:40:50				yanghui 	1.0
 */
@Service
public class InfoSubscriptionService extends BaseService<InfoSubscription> {

	@Resource
	private InfoCategoryService infoCategoryService;
	@Resource
	private BaseDao<InfoSubscription> subscriptionDao;
	@Override
	protected BaseDao<InfoSubscription> getDao() {
		return subscriptionDao;
	}
	
	/**
	 * 
	 * @Description:  信息订阅
	 * @author yanghui 
	 * @Created 2014-2-27
	 * @param page
	 * @param bean
	 * @return
	 */
	public DataGrid dataGrid(RequestPage page, SubscriptionSearchBean bean) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select userId,userName,count(*) from " + getEntityName() +" where subStatus='"+SystemCommon_Constant.INFO_SIGN_YES+"' ");
		if(!StringUtils.isEmpty(bean.getUserName())){
			hql.append(" and userName like '%"+bean.getUserName()+"%' ");
		}
		hql.append(" group by userId,userName");
		List list = getDao().find(hql.toString(), new Object[0]);
		List beanList = new ArrayList();
		
		for(Iterator it = list.iterator();it.hasNext();){
			Object[] obj = (Object[]) it.next();
			InfoSubscriptionBean sub = new InfoSubscriptionBean();
			sub.setUserId((Long) obj[0]);
			sub.setUserName((String) obj[1]);
			sub.setCount((Long) obj[2]);
			
			beanList.add(sub);
		}
		int pageNo = page.getPage();
		int pageSize = page.getRows();
		int begin = (pageNo-1)*pageSize;
		int end = (pageNo*pageSize>=beanList.size()?beanList.size():pageNo*pageSize);
		
		List subList = beanList.subList(begin, end);
		
		return new DataGrid(beanList.size(), subList);
	}
	
	/**
	 * 
	 * @Description:  某个用户订阅的明细
	 * @author yanghui 
	 * @Created 2014-2-27
	 * @param page
	 * @param bean
	 * @return
	 */
	public DataGrid detailDataGrid(RequestPage page, SubscriptionSearchBean bean,Long userId) {
		StringBuffer hql  = new StringBuffer();
		hql.append(" from "+ getEntityName() + " where userId =? ");
		
		StringBuffer counthql = new StringBuffer();
		counthql.append(" select count(*) from " + getEntityName() + " where userId =? " );
		
		StringBuffer sb = new StringBuffer();
		if(!DataUtil.isNull(bean.getInfoCateId())){
			sb.append(" and infoCateId='"+bean.getInfoCateId()+"' ");
		}
		if(!DataUtil.isNull(bean.getSubTime())){
			sb.append(" and STR_TO_DATE(subTime,'%Y-%m-%d') = '" + DateUtil.dateToString(bean.getSubTime())+"'");
		}
		sb.append(" and subStatus='"+SystemCommon_Constant.INFO_SIGN_YES+"' order by subTime desc ");
		
		List<InfoSubscription> list = getDao().find(page.getPage(), page.getRows(), hql.append(sb).toString(), userId);
		List<InfoSubscriptionBean> olist = new ArrayList<InfoSubscriptionBean>();
		for(int i=0;i<list.size();i++){
			InfoSubscription isub = list.get(i);
			InfoSubscriptionBean infoBean = new InfoSubscriptionBean();
			infoBean.setId(isub.getId());
			infoBean.setUserName(isub.getUserName());
			infoBean.setSubTime(isub.getSubTime());
			infoBean.setInfoCateName(infoCategoryService.get(isub.getInfoCateId()) != null ? infoCategoryService.get(isub.getInfoCateId()).getText() : "");
			olist.add(infoBean);
		}
		Long count = getDao().count(counthql.append(sb).toString(), userId);
		
		return new DataGrid(count, olist);
	}

	/**
	 * 
	 * @Description: 根据用户ID和信息类别ID取得订阅信息
	 * @author yanghui 
	 * @Created 2014-2-27
	 * @param userId
	 * @param cateId
	 * @return
	 */
	public InfoSubscription getInfoByUserIdAndCateId(Long userId, Long cateId) {
		String hql = " from "+ getEntityName() +" where userId =? and infoCateId=?";
		List list =  getDao().find(hql, new Object[]{userId,cateId});
		InfoSubscription info = new InfoSubscription();
		if(list!=null){
			info =(InfoSubscription) list.get(0);
		}
		return info;
	}

	/**
	 * 
	 * @Description: 信息退订
	 * @author yanghui 
	 * @Created 2014-2-28
	 * @param page
	 * @param bean
	 * @return
	 */
	public DataGrid unsubDataGrid(RequestPage page, SubscriptionSearchBean bean) {
		StringBuffer hql = new StringBuffer();
		StringBuffer counthql = new StringBuffer();
		StringBuffer sb = new StringBuffer();
		
		hql.append(" from " + getEntityName() + " where subStatus=? ");
		if(!StringUtils.isEmpty(bean.getUserName())){
			sb.append(" and userName='"+bean.getUserName()+"' ");
		}
		if(!DataUtil.isNull(bean.getInfoCateId())){
			sb.append(" and infoCateId='"+bean.getInfoCateId()+"' ");
		}
		if(!DataUtil.isNull(bean.getUnSubTime())){
			sb.append(" and STR_TO_DATE(unSubTime,'%Y-%m-%d')='"+DateUtil.dateToString(bean.getUnSubTime())+"'");
		}
		sb.append(" order by userName,unSubTime desc ");
		List<InfoSubscription> list = getDao().find(page.getPage(), page.getRows(), hql.append(sb).toString(), SystemCommon_Constant.INFO_SIGN_NO);
		
		counthql.append(" select count(*) from "+ getEntityName()+" where subStatus=? ");
		Long count = getDao().count(counthql.append(sb).toString(), SystemCommon_Constant.INFO_SIGN_NO);
		return new DataGrid(count, list);
	}

	
	/**
	 * 
	 * @Description: 订阅统计
	 * @author yanghui 
	 * @Created 2014-3-3
	 * @param page
	 * @param bean
	 * @return
	 */
	public DataGrid staDataGrid(RequestPage page, SubscriptionSearchBean bean) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select distinct infoCateId  from " + getEntityName() +" where 1=1 ");
		if(!DataUtil.isNull(bean.getInfoCateId())){
			hql.append(" and infoCateId = '"+bean.getInfoCateId()+"' ");
		}
		if(!DataUtil.isNull(bean.getBeginDate())){
			hql.append(" and STR_TO_DATE(subTime,'%Y-%m-%d') >= '"+DateUtil.dateToString(bean.getBeginDate())+"'");
		}
		
		if(!DataUtil.isNull(bean.getEndDate())){
			hql.append(" and STR_TO_DATE(subTime,'%Y-%m-%d') <= '"+DateUtil.dateToString(bean.getEndDate())+"'");
		}
		hql.append(" order by infoCateId asc ");
		List list = getDao().find(hql.toString(), new Object[0]);  //不重复的分类ID列表
		List beanList = new ArrayList();
		for(int i=0;i<list.size();i++){
			InfoSubscriptionBean infoBean = new InfoSubscriptionBean();
			Long infoCateId = (Long) list.get(i);
			StringBuffer sb = new StringBuffer();
			sb.append(" select count(*) from "+getEntityName()+" where infoCateId =? and subStatus=?");
			List dylist = getDao().find(sb.toString(), new Object[]{infoCateId,SystemCommon_Constant.INFO_SIGN_YES});
			Long subCount = (Long) dylist.get(0); //某类信息当前被订阅的次数
			
			sb.delete(0, sb.length());  //清空字符串缓存
			sb.append(" select count(*) from "+getEntityName()+" where infoCateId =? and subStatus=?");
			List dtlist = getDao().find(sb.toString(), new Object[]{infoCateId,SystemCommon_Constant.INFO_SIGN_NO});
			Long unsubCount = (Long) dtlist.get(0); //某类信息当前被退订的次数
			
			
			//由于某类信息被退订了，说明它原来必须被订阅过，所以此时的订阅次数为退订次数加上当前被订阅的次数
			subCount = subCount+unsubCount;
			String cateName = infoCategoryService.getNameById(infoCateId);
			
			infoBean.setSubCount(subCount);
			infoBean.setUnsubCount(unsubCount);
			infoBean.setInfoCateName(cateName);
			
			beanList.add(infoBean);
		}
		int pageNo = page.getPage();
		int pageSize = page.getRows();
		int begin = (pageNo-1)*pageSize;
		int end = (pageNo*pageSize>=beanList.size()?beanList.size():pageNo*pageSize);
		
		List subList = beanList.subList(begin, end);
		
		return new DataGrid(beanList.size(), subList);
	}


}
