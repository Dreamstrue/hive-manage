/**
 * 
 */
package com.hive.integralmanage.service;

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
import com.hive.integralmanage.entity.IntegralSub;
import com.hive.integralmanage.model.IntegralBean;
import com.hive.integralmanage.model.IntegralDetailBean;
import com.hive.intendmanage.entity.IntendRelPrize;
import com.hive.intendmanage.model.IntendBean;
import com.hive.membermanage.entity.MMember;
import com.hive.prizemanage.service.PrizeInfoService;
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
public class IntegralService extends BaseService<Integral> {

	
	@Resource
	private ClientUserService clientUserService;
	@Resource
	private PrizeInfoService prizeInfoService;
	@Resource
	private IntegralSubService integralSubService;
	@Resource
	private BaseDao<Integral> integralDao;
	
	@Override
	protected BaseDao<Integral> getDao() {
		return integralDao;
	}

	/**
	 * 
	 * @Description:  通过用户ID得到该用户积分的记录信息
	 * @author yanghui 
	 * @Created 2014-3-11
	 * @param userId
	 * @return
	 */
	public Integral getIntegralByUserId(Long userId) {
		 List<Integral> list = getDao().find(" from "+ getEntityName()+" where userId=?", userId);
		 Integral integral  = new Integral();
		 if(list.size()==1){
			 integral = list.get(0);
		 }
		return integral;
	}

	/**
	 * 
	 * @Description: 积分管理 统计
	 * @author yanghui 
	 * @Created 2014-3-11
	 * @param page
	 * @param userName
	 * @return
	 */
	public DataGrid integralDataGrid(RequestPage page, MMember mmember) {
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
		List<Integral> integraList = getDao().find(page.getPage(), page.getRows(), hql.append(sb).toString(), new Object[0]);
		List<IntegralBean> blist = new ArrayList<IntegralBean>();
		for(int i=0;i<integraList.size();i++){
			Integral in = integraList.get(i);
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

	
	
	private List<IntegralDetailBean> getIntegralDetailBeanList(
			RequestPage page, Long userId) {
		//第一步  得到该用户获得积分的明细  
		List<IntegralSub> subList = integralSubService.getIntegralListByUserId(userId);
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
			IntegralSub sub = (IntegralSub) sit.next();
			bean.setIntegralDate(sub.getGainDate());
			bean.setIntegralType(SystemCommon_Constant.INTEGRAL_DETAIL_INTEGRAL_TYPE_1); //该类型分为获取和消费，这里默认为获取
			bean.setIntegralValue(sub.getTotalValue()); //一条记录的总积分包括基本机会和会员奖励积分
			bean.setRemark(String.valueOf(sub.getInteCateId()));  //可以通过积分类别来判断（系统配置表中设置积分类别：分享、评论、投票等）,这里暂时赋值积分的类别，然后通过前台判断中文类型
			if(!DataUtil.isNull(sub.getRewardValue())){
				bean.setSummary("基本积分:"+sub.getBasicValue()+",会员赠分:"+sub.getRewardValue());
			}else {
				bean.setSummary("基本积分:"+sub.getBasicValue());
			}
			beanList.add(bean);
		}
		
		for(Iterator it = list.iterator();it.hasNext();){
			Object[] obj = (Object[]) it.next();
			Date applyTime = DateUtil.format(DateUtil.Time14ToString((Timestamp) obj[0]));  //订单申请时间也就积分消费时间
			IntendRelPrize irp = (IntendRelPrize) obj[1];
			IntegralDetailBean bean = new IntegralDetailBean();
			bean.setIntegralDate(applyTime);
			bean.setIntegralType(SystemCommon_Constant.INTEGRAL_DETAIL_INTEGRAL_TYPE_2); //该类型分为获取和消费，这里默认为消费
			bean.setIntegralValue(irp.getExcIntegral()*irp.getPrizeNum()); //一条记录的消费积分=奖品数量乘以单个奖品的积分
			bean.setRemark("订单"+irp.getIntendNo());  // 消费的积分说明为产生的订单号
			//消费积分的详情为兑换的商品及数量
			String prizeName = prizeInfoService.get(irp.getPrizeId()).getPrizeName(); //奖品名称
			bean.setSummary("兑换奖品["+prizeName+"],数量"+irp.getPrizeNum()+"个");
			beanList.add(bean);
		}

		return beanList;
	}

	

}
