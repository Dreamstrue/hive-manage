/**
 * 
 */
package com.hive.prizemanage.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hive.clientmanage.service.ClientUserService;
import com.hive.common.SystemCommon_Constant;
import com.hive.intendmanage.entity.Intend;
import com.hive.intendmanage.entity.IntendRelPrize;
import com.hive.intendmanage.model.IntendBean;
import com.hive.prizemanage.entity.PrizeInfo;
import com.hive.prizemanage.model.PrizeInfoBean;
import com.hive.prizemanage.model.PrizeInfoSearchBean;
import com.hive.surveymanage.service.EntityCategoryService;
import com.hive.systemconfig.service.PrizeCategoryService;
import com.hive.util.DataUtil;
import com.hive.util.DateUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

/**  
 * Filename: PrizeInfoService.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-6  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-6 下午4:15:46				yanghui 	1.0
 */
@Service
public class PrizeInfoService extends BaseService<PrizeInfo> {

	@Resource
	private PrizeCategoryService prizeCategoryService;
	@Resource
	private EntityCategoryService entityCategoryService;
	@Resource
	private BaseDao<PrizeInfo> prizeInfoDao;
	@Override
	protected BaseDao<PrizeInfo> getDao() {
		return prizeInfoDao;
	}
	@Resource
	private ClientUserService clientUserService;
	
	/**
	 * 
	 * @Description: 奖品列表查询
	 * @author yanghui 
	 * @Created 2014-3-7
	 * @param page
	 * @param bean
	 * @return
	 */
	public DataGrid dataGrid(RequestPage page, PrizeInfoSearchBean bean) {
		StringBuffer hql = new StringBuffer();
		StringBuffer counthql = new StringBuffer();
		StringBuffer sb = new StringBuffer();
		
		hql.append(" from "+ getEntityName() + " where 1=1 ");
		if(!DataUtil.isEmpty(bean.getPrizeName())){
			sb.append(" and prizeName like '%"+bean.getPrizeName()+"%' ");
		}
		if(!DataUtil.isNull(bean.getPrizeCateId())){
			sb.append(" and prizeCateId = '"+bean.getPrizeCateId()+"' ");
		}
		if(!DataUtil.isNull(bean.getBeginDate())){
			sb.append(" and STR_TO_DATE(validDate,'%Y-%m-%d') >= '"+DateUtil.dateToString(bean.getBeginDate())+"' ");
		}
		if(!DataUtil.isNull(bean.getEndDate())){
			sb.append(" and STR_TO_DATE(validDate,'%Y-%m-%d') <= '"+DateUtil.dateToString(bean.getEndDate())+"' ");
		}
		sb.append(" and valid=? order by createTime desc");
		List<PrizeInfo> infoList = getDao().find(page.getPage(), page.getRows(), hql.append(sb).toString(), SystemCommon_Constant.VALID_STATUS_1);
		List<PrizeInfoBean> beanList = new ArrayList<PrizeInfoBean>();
		for(Iterator it=infoList.iterator();it.hasNext();){
			PrizeInfo info = (PrizeInfo) it.next();
			String prizeCateName = prizeCategoryService.getPrizeCateNameById(info.getPrizeCateId());
			String entitucate=info.getEntityCategory();
			String entityCategoryName="";
			if(StringUtils.isNotBlank(entitucate)){
				entityCategoryName=entityCategoryService.getNamebyId(entitucate);
			}else{
				entityCategoryName="暂未选择提供方";
			}
			PrizeInfoBean infoBean = new PrizeInfoBean();
			try {
				PropertyUtils.copyProperties(infoBean, info);
				infoBean.setPrizeCateName(prizeCateName); //奖品类别名称
				infoBean.setSurplusNum(infoBean.getPrizeNum()-infoBean.getExcNum()); //剩余数量
				infoBean.setEntityCategory(entityCategoryName);
				beanList.add(infoBean);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("属性拷贝错误");
			} 
		}
		
		counthql.append(" select count(*) from " + getEntityName()+" where 1=1 ");
		Long count = getDao().count(counthql.append(sb).toString(), SystemCommon_Constant.VALID_STATUS_1);
		
		return new DataGrid(count, beanList);
	}


	/**
	 * 
	 * @Description: 新增奖品重名判断
	 * @author yanghui 
	 * @Created 2014-3-7
	 * @param prizeName
	 * @return
	 */
	public boolean isExistPrizeName(String prizeName) {
		boolean flag = false;
		List<PrizeInfo> list = getDao().find(" from "+getEntityName()+" where prizeName=?", prizeName);
		if(list.size()>0){ //存在
			flag = true; 
		}
		return flag;
	}


	/**
	 * 
	 * @Description:奖品库存查询  列表中针对已兑换数量查看明细
	 * @author yanghui 
	 * @Created 2014-3-17
	 * @param page
	 * @param infobean
	 * @return
	 */
	public DataGrid excDetailDataGrid(RequestPage page, PrizeInfoSearchBean infobean) {
		
		StringBuffer hql = new StringBuffer();
		StringBuffer sb = new StringBuffer();
		StringBuffer counthql =  new StringBuffer();
		hql.append(" select a,b from Intend a,IntendRelPrize b  ");
		counthql.append(" select count(*) from Intend a,IntendRelPrize b  ");
		sb.append(" where a.intendNo = b.intendNo and b.prizeId=? ");
//		hql.append(" and a.intendStatus= ?");
		if(!DataUtil.isNull(infobean.getBeginDate())){
			sb.append(" and STR_TO_DATE(applyTime,'%Y-%m-%d') >= '"+DateUtil.dateToString(infobean.getBeginDate())+"' ");
		}
		if(!DataUtil.isNull(infobean.getEndDate())){
			sb.append(" and STR_TO_DATE(applyTime,'%Y-%m-%d') <= '"+DateUtil.dateToString(infobean.getEndDate())+"' ");
		}
		sb.append(" order by applyPersonId asc");
		
		List list = getDao().find(page.getPage(),page.getRows(), hql.append(sb).toString(), infobean.getPrizeId());
		List<IntendBean> beanList = new ArrayList<IntendBean>();
		for(Iterator it=list.iterator();it.hasNext();){
			Object[] obj = (Object[]) it.next();
			Intend intend = (Intend) obj[0];
			IntendRelPrize irp = (IntendRelPrize) obj[1];
			IntendBean bean =  new IntendBean();
//			bean.setIntendNo(irp.getIntendNo());
			bean.setPrizeNum(irp.getPrizeNum());
	//		bean.setExcIntegral(irp.getExcIntegral()*irp.getPrizeNum());
			bean.setPrizeName(get(irp.getPrizeId()).getPrizeName());
			bean.setUserName(clientUserService.get(intend.getApplyPersonId()).getCusername());
			bean.setApplyTime(intend.getApplyTime());
			beanList.add(bean);
		}
		
		Long count = getDao().count(counthql.append(sb).toString(), infobean.getPrizeId());
		
		
		return new DataGrid(count,beanList);
	}
	public List<PrizeInfo> getAllPrizeInfo(Long prizeCateId) {
		if(prizeCateId!=null){
			
			String hql = " from "+ getEntityName()+" where valid =? and auditStatus =? and prizeCateId=?  order by createTime desc ";
			List<PrizeInfo> list = getDao().find(hql, new Object[]{SystemCommon_Constant.VALID_STATUS_1,SystemCommon_Constant.AUDIT_STATUS_1,prizeCateId});
			return list;
		}else{//20160608 yyf add
			String hql = " from "+ getEntityName()+" where valid =? and auditStatus =?  order by createTime desc ";
			List<PrizeInfo> list = getDao().find(hql, new Object[]{SystemCommon_Constant.VALID_STATUS_1,SystemCommon_Constant.AUDIT_STATUS_1});
			return list;
		}
	}

	/**
	 * 根据活动id和中奖序号获取奖品信息
	 * 20160620 yyf add
	 * @param result
	 * @param awardActivityId
	 * @return
	 */
	public PrizeInfo findPrizeInfoByPrizeLevelAndActivityId(Integer level,Long awardActivityId) {
		String hql = "select pi from "+ getEntityName()+" pi,ActivityPrizeLink apl where pi.id = apl.prizeId and pi.valid =? and pi.auditStatus =? and apl.prizeLevel=?  and apl.awardActivityId=? and apl.remainCount>0 order by createTime desc ";
		List<PrizeInfo> list = getDao().find(hql, new Object[]{SystemCommon_Constant.VALID_STATUS_1,SystemCommon_Constant.AUDIT_STATUS_1,level,awardActivityId});
		if(list.size()==1){
			return list.get(0);
		}else{
			return null;
		}
	}
}
