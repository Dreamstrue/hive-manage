/**
 * 
 */
package com.hive.integralmanage.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hive.clientmanage.service.ClientUserService;
import com.hive.common.SystemCommon_Constant;
import com.hive.integralmanage.entity.Integral;
import com.hive.integralmanage.entity.IntegralOil;
import com.hive.integralmanage.entity.IntegralOilSub;
import com.hive.integralmanage.entity.IntegralSub;
import com.hive.integralmanage.model.IntegralBean;
import com.hive.integralmanage.model.IntegralCategoryBean;
import com.hive.integralmanage.model.IntegralDetailBean;
import com.hive.intendmanage.entity.IntendRelPrize;
import com.hive.intendmanage.model.IntendBean;
import com.hive.membermanage.entity.MMember;
import com.hive.membermanage.service.MembermanageService;
import com.hive.prizemanage.service.PrizeInfoService;
import com.hive.surveymanage.service.EntityCategoryService;
import com.hive.surveymanage.service.IndustryEntityService;
import com.hive.surveymanage.service.SurveyService;
import com.hive.systemconfig.entity.SystemConfig;
import com.hive.systemconfig.service.MemberGradeService;
import com.hive.systemconfig.service.SystemConfigService;
import com.hive.util.Comparator;
import com.hive.util.DataUtil;
import com.hive.util.DateUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

/**  
 * Filename: IntegralService.java  
 * Description:  积分管理  service类
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-11  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-11 上午9:33:07				yanghui 	1.0
 */
@Service
public class IntegralOilService extends BaseService<IntegralOil> {

	@Resource
	private ClientUserService clientUserService;
	@Resource
	private PrizeInfoService prizeInfoService;
	@Resource
	private IntegralSubService integralSubService;
	@Resource
	private IntegralOilSubService integralOilSubService;
	@Resource
	private BaseDao<Integral> integralDao;
	@Resource
	private MembermanageService membermanageService;
	@Resource
	private IndustryEntityService industryEntityService;
	@Resource
	private EntityCategoryService entityCategoryService;
	@Resource
	private SurveyService surveyService;
	@Resource
	private BaseDao<IntegralOil> integralOliDao;
	@Resource
	private SystemConfigService systemConfigService;
	@Resource
	private MemberGradeService memberGradeService;
	@Override
	protected BaseDao<IntegralOil> getDao() {
		return integralOliDao;
	}


	/**
	 * 
	 * @Description:  通过用户ID得到该用户加油站积分的记录信息
	 * @author yanghui 
	 * @Created 2014-3-11
	 * @param userId
	 * @return
	 */
	public IntegralOil getIntegralOilByUserId(Long userId) {
		 List<IntegralOil> list = getDao().find(" from "+ getEntityName()+" where userId=?", userId);
		 IntegralOil integral  = null;
		 if( list!=null && list.size()==1){
			 integral = list.get(0);
		 }
		return integral;
	}

	
	
	/**
	 * 
	 * @Description: 加油站积分管理 统计
	 * @param page
	 * @param userName
	 * @return
	 */
	public DataGrid integralOilDataGrid(RequestPage page, MMember mmember) {
		List<MMember> list = null;
		StringBuffer hql = new StringBuffer();
		StringBuffer counthql = new StringBuffer(" select count(*) from "+getEntityName()+" where 1=1 ");
		StringBuffer sb = new StringBuffer();
		hql.append(" from "+getEntityName()+" where 1=1 ");
		if(mmember!=null){
			list = clientUserService.getAllByParams(mmember);
			String ids = "";
			for(MMember m:list){
				ids = ids +m.getNmemberid()+",";
			}
			if(ids.length()>0){
				ids = ids.substring(0, ids.length()-1);
				sb.append(" and userId in (" + ids+")");
			}
//			if(list.size()>0){
//				String idsStr ="";
//				idsStr = list.toString();  // 形如：[000010000500001, 00001000050000100004, 00001000050000100002, 00001000050000100003]     
//				idsStr = idsStr.replace("[", "");  // 把前后的中括号去掉
//				idsStr = idsStr.replace("]", "");
//				idsStr = idsStr.replaceAll(" ", "");  // 把中间的空格去掉
//				sb.append(" and userId in (" + ids+")");
//			}
		}
		sb.append(" order by userId desc");
		List<IntegralOil> integraList = getDao().find(page.getPage(), page.getRows(), hql.append(sb).toString(), new Object[0]);
		List<IntegralBean> blist = new ArrayList<IntegralBean>();
		for(int i=0;i<integraList.size();i++){
			IntegralOil in = integraList.get(i);
			IntegralBean bean = new IntegralBean();
			bean.setCurrentIntegral(in.getCurrentValue());
			bean.setUsedIntegral(in.getUsedValue());
			bean.setTotalIntegral(in.getCurrentValue()+in.getUsedValue());
			bean.setUserId(in.getUserId());
			MMember mm = clientUserService.get(in.getUserId());
			if(mm!=null){
				bean.setUserName(mm.getCusername());
				bean.setChineseName(mm.getChinesename());
				bean.setIdCard(mm.getCardno());
				bean.setMobilePhone(mm.getCmobilephone());
				String anonymousFlag = mm.getCmembertype();
				if(StringUtils.isNotBlank(anonymousFlag)&&anonymousFlag.equals("2")){
					anonymousFlag = "匿名";
				}else{
					anonymousFlag = "会员";
				}
				bean.setAnonymousFlag(anonymousFlag);
				bean.setUserName(mm.getCusername());

			}
			blist.add(bean);
		}
		
		Long count = getDao().count(counthql.append(sb).toString(), new Object[0]);
		return new DataGrid(count,blist);
	}
	/**
	 * 
	 * @Description: 针对某个用户已使用的积分统计
	 * @author yanghui 
	 * @Created 2014-3-11
	 * @param page
	 * @param userId
	 * @return
	 */
	public DataGrid usedIntegralDataGrid(RequestPage page, Long userId) {
		
		StringBuffer hql = new StringBuffer();
		StringBuffer sb = new StringBuffer();
		StringBuffer counthql =  new StringBuffer();
		hql.append(" select a.applyTime,b from Intend a,IntendRelPrize b  ");
		counthql.append(" select count(*) from Intend a,IntendRelPrize b  ");
		sb.append(" where a.intendNo = b.intendNo and a.applyPersonId=? ");
//		hql.append(" and a.intendStatus= ?");
		
		List list = getDao().find(page.getPage(),page.getRows(), hql.append(sb).toString(), userId);
		List<IntendBean> beanList = new ArrayList<IntendBean>();
		for(Iterator it=list.iterator();it.hasNext();){
			Object[] obj = (Object[]) it.next();
			Date applyTime = DateUtil.format(DateUtil.Time14ToString((Timestamp) obj[0]));
			IntendRelPrize irp = (IntendRelPrize) obj[1];
			IntendBean bean =  new IntendBean();
			bean.setIntendNo(irp.getIntendNo());
			bean.setPrizeNum(irp.getPrizeNum());
			bean.setExcIntegral(irp.getExcIntegral()*irp.getPrizeNum());
			bean.setPrizeName(prizeInfoService.get(irp.getPrizeId()).getPrizeName());
			bean.setUserName(clientUserService.get(userId).getCusername());
			bean.setApplyTime(applyTime);
			beanList.add(bean);
		}
		
		Long count = getDao().count(counthql.append(sb).toString(), userId);
		
		
		return new DataGrid(count,beanList);
	}

	/**
	 * 	
	 * @Description: 积分明细列表
	 * @author yanghui 
	 * @Created 2014-3-27
	 * @param page
	 * @param userId
	 * @return
	 */
	public DataGrid integralDetailDataGrid(RequestPage page, Long userId) {
		List<IntegralDetailBean> beanList = new ArrayList<IntegralDetailBean>();
		beanList = getIntegralDetailBeanList(page,userId);
		//对beanList进行排序，按时间的从高到底
		Comparator comparator = new Comparator();
		Collections.sort(beanList, comparator);
		/*for(int i=0;i<beanList.size();i++){
			IntegralDetailBean b = beanList.get(i);
			System.out.println(b.getIntegralDate()+">>>>"+b.getRemark());
		}*/
		
		int pageNo = page.getPage();
		int pageSize = page.getRows();
		int begin = (pageNo-1)*pageSize;
		int end = (pageNo*pageSize>=beanList.size()?beanList.size():pageNo*pageSize);
		List  childList = beanList.subList(begin, end);
		return new DataGrid(beanList.size(), childList);
	}
	/**
	 * 	
	 * @Description: 某个类别下积分明细列表
	 * @author yanghui 
	 * @Created 2014-3-27
	 * @param page
	 * @param userId
	 * @return
	 */
	public DataGrid integralDetailCateDataGrid(RequestPage page, Long userId,Long entityCategory) {
		List<IntegralDetailBean> beanList = new ArrayList<IntegralDetailBean>();
		//第一步  得到该用户获得积分的明细  
		List<IntegralOilSub> subList = integralOilSubService.getInteCateListByUserId(userId,entityCategory);
		//处理得到的两种积分明细
		for(Iterator sit = subList.iterator();sit.hasNext();){
			IntegralDetailBean bean = new IntegralDetailBean();
			IntegralOilSub sub = (IntegralOilSub) sit.next();
			bean.setIntegralDate(sub.getGainDate());
			bean.setIntegralType(SystemCommon_Constant.INTEGRAL_DETAIL_INTEGRAL_TYPE_1); //该类型分为获取和消费，这里默认为获取
			bean.setIntegralValue(sub.getBasicValue()); //一条记录的总积分包括基本机会和会员奖励积分
			String entityName=industryEntityService.get(sub.getIndustryEntity()).getEntityName();
			String entityCate=entityCategoryService.get(sub.getEntityCategory()).getText();
			bean.setRemark("通过"+entityName+"评价获取积分");  //可以通过积分类别来判断（系统配置表中设置积分类别：分享、评论、投票等）,这里暂时赋值积分的类别，然后通过前台判断中文类型
			bean.setEntityCategory(entityCate);
			bean.setIndustryEntity(entityName);
			bean.setSummary("获取积分:"+sub.getBasicValue());
			beanList.add(bean);
		}
		//对beanList进行排序，按时间的从高到底
		Comparator comparator = new Comparator();
		Collections.sort(beanList, comparator);
		int pageNo = page.getPage();
		int pageSize = page.getRows();
		int begin = (pageNo-1)*pageSize;
		int end = (pageNo*pageSize>=beanList.size()?beanList.size():pageNo*pageSize);
		List  childList = beanList.subList(begin, end);
		return new DataGrid(beanList.size(), childList);
	}
	
	/**
	 * 	
	 * @Description: 积分类别明细列表(中石化中石油)
	 * @author yanghui 
	 * @Created 2014-3-27
	 * @param page
	 * @param userId
	 * @return
	 */
	public List<IntegralCategoryBean> integralCategory(RequestPage page, Long userId) {
		List<IntegralCategoryBean> beanList = new ArrayList<IntegralCategoryBean>();
		List<IntegralOilSub> subList = integralOilSubService.getIntegralCateByUserId(userId);
		for(Iterator sit = subList.iterator();sit.hasNext();){
			IntegralCategoryBean bean = new IntegralCategoryBean();
			IntegralOilSub sub = (IntegralOilSub) sit.next();
			Long count=integralOilSubService.getIntCateByUserId(userId, sub.getEntityCategory());
			Long totalIntegral=integralOilSubService.getTotalIntegralByUserId(userId, sub.getEntityCategory());
			bean.setEntityCategory(String.valueOf(sub.getEntityCategory()));
			bean.setCounts(String.valueOf(count)); //一条记录的总积分包括基本机会和会员奖励积分
			bean.setTotalIntegral(totalIntegral);
			bean.setUsedIntegral(0L);
			bean.setCurrentIntegral(totalIntegral);//已消费积分后续添加
			String entityName=industryEntityService.get(sub.getIndustryEntity()).getEntityName();
			String entityCate=entityCategoryService.get(sub.getEntityCategory()).getText();
			bean.setEntityCategoryName(entityCate);
			beanList.add(bean);
		}
//		beanList = getIntegralDetailBeanList(page,userId);
	
		/*for(int i=0;i<beanList.size();i++){
			IntegralDetailBean b = beanList.get(i);
			System.out.println(b.getIntegralDate()+">>>>"+b.getRemark());
		}*/
		return beanList;
	}
	
	private List<IntegralDetailBean> getIntegralDetailBeanList(
			RequestPage page, Long userId) {
		//第一步  得到该用户获得积分的明细  
		List<IntegralOilSub> subList = integralOilSubService.getIntegralListByUserId(userId);
		//第二步  得到该用户消费的积分明细
		StringBuffer hql = new StringBuffer();
		StringBuffer sb = new StringBuffer();
		hql.append(" select a.applyTime,b from Intend a,IntendRelPrize b  ");
		sb.append(" where a.intendNo = b.intendNo and a.applyPersonId=? ");
		sb.append(" and a.intendStatus not in('"+SystemCommon_Constant.INTEND_STATUS_3+"','"+SystemCommon_Constant.INTEND_STATUS_6+"')");
		List list = getDao().find(hql.append(sb).toString(), userId);
		//处理得到的两种积分明细
		List<IntegralDetailBean> beanList = new ArrayList<IntegralDetailBean>();
		for(Iterator sit = subList.iterator();sit.hasNext();){
			IntegralDetailBean bean = new IntegralDetailBean();
			IntegralOilSub sub = (IntegralOilSub) sit.next();
			bean.setIntegralDate(sub.getGainDate());
			bean.setIntegralType(SystemCommon_Constant.INTEGRAL_DETAIL_INTEGRAL_TYPE_1); //该类型分为获取和消费，这里默认为获取
			bean.setIntegralValue(sub.getBasicValue()); //一条记录的总积分包括基本机会和会员奖励积分
			String entityName=industryEntityService.get(sub.getIndustryEntity()).getEntityName();
			String entityCate=entityCategoryService.get(sub.getEntityCategory()).getText();
			bean.setRemark("通过"+entityName+"评价获取积分");  //可以通过积分类别来判断（系统配置表中设置积分类别：分享、评论、投票等）,这里暂时赋值积分的类别，然后通过前台判断中文类型
			bean.setEntityCategory(entityCate);
			bean.setIndustryEntity(entityName);
			bean.setSummary("获取积分:"+sub.getBasicValue());
			beanList.add(bean);
		}
		
//		for(Iterator it = list.iterator();it.hasNext();){
//			Object[] obj = (Object[]) it.next();
//			Date applyTime = DateUtil.format(DateUtil.Time14ToString((Timestamp) obj[0]));  //订单申请时间也就积分消费时间
//			IntendRelPrize irp = (IntendRelPrize) obj[1];
//			IntegralDetailBean bean = new IntegralDetailBean();
//			bean.setIntegralDate(applyTime);
//			bean.setIntegralType(SystemCommon_Constant.INTEGRAL_DETAIL_INTEGRAL_TYPE_2); //该类型分为获取和消费，这里默认为消费
//			bean.setIntegralValue(irp.getExcIntegral()*irp.getPrizeNum()); //一条记录的消费积分=奖品数量乘以单个奖品的积分
//			bean.setRemark("订单"+irp.getIntendNo());  // 消费的积分说明为产生的订单号
//			//消费积分的详情为兑换的商品及数量
//			String prizeName = prizeInfoService.get(irp.getPrizeId()).getPrizeName(); //奖品名称
//			bean.setSummary("兑换奖品["+prizeName+"],数量"+irp.getPrizeNum()+"个");
//			beanList.add(bean);
//		}

		return beanList;
	}
	

}
