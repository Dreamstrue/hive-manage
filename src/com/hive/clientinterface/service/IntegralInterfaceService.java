package com.hive.clientinterface.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.hive.clientmanage.service.ClientUserService;
import com.hive.common.SystemCommon_Constant;
import com.hive.infomanage.model.NewsCommentBean;
import com.hive.integralmanage.entity.Integral;
import com.hive.integralmanage.entity.IntegralSub;
import com.hive.integralmanage.model.IntegralDetailBean;
import com.hive.integralmanage.service.IntegralSubService;
import com.hive.intendmanage.entity.IntendRelPrize;
import com.hive.membermanage.entity.MMember;
import com.hive.prizemanage.service.PrizeInfoService;
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

@Service
public class IntegralInterfaceService extends BaseService<Integral> {

	@Resource
	private BaseDao<Integral> integralDao;
	@Override
	protected BaseDao<Integral> getDao() {
		return integralDao;
	}
	
	
	@Resource
	private ClientUserService clientUserService;
	@Resource
	private PrizeInfoService prizeInfoService;
	@Resource
	private IntegralSubService integralSubService;
	@Resource
	private SystemConfigService systemConfigService;
	@Resource
	private MemberGradeService memberGradeService;
	@Resource
	private SessionFactory sessionFactory;
	
	
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
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
		sb.append(" where a.intendNo = b.intendNo and a.applyPersonId=? and intendStatus not in('"+SystemCommon_Constant.INTEND_STATUS_3+"','"+SystemCommon_Constant.INTEND_STATUS_6+"')");
		List list = getDao().find(hql.append(sb).toString(), userId);
		//处理得到的两种积分明细
		List<IntegralDetailBean> beanList = new ArrayList<IntegralDetailBean>();
		for(Iterator sit = subList.iterator();sit.hasNext();){
			IntegralDetailBean bean = new IntegralDetailBean();
			IntegralSub sub = (IntegralSub) sit.next();
			bean.setId(sub.getId());
			bean.setSourceId(sub.getIntegralSource()); //积分来源Id 如评论的资讯ID,分享的资讯ID，
			bean.setIntegralCateId(sub.getInteCateId());//积分类别Id(在客户端查看积分明细的时候，想知道积分到底是从评论或分享哪些具体的信息而来时，就需要通过积分来源ID和积分类别ID联合处理   
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
			bean.setId(irp.getId());
			bean.setSourceId(irp.getPrizeId()); //积分消费的去向，如兑换奖品的ID
			bean.setIntendNo(irp.getIntendNo());//订单号
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



	public List getIntegralList(Long userId) {
		List integarList = getDao().find(" from "+getEntityName()+" where userId=?",userId);
		return integarList;
	}



	/**
	 * 通过用户ID获得积分总表信息
	 * @param userId
	 * @return
	 */
	public Integral getIntegralInfoByUserId(Long userId) {
		Integral integral = null;
		String hql = " from "+getEntityName()+" where userId=?";
		List list = getDao().find(hql, userId);
		if(list.size()>0){
			integral = (Integral) list.get(0);
		}
		return integral;
	}



	/**
	 * 
	 * @Description:  修改用户积分的公共方法（新闻评论、分享获得积分；投票获得积分等）
	 * @author yanghui 
	 * @Created 2014-4-1
	 * @param bean
	 * @param integralCategory  积分类别ID值
	 * @return
	 */
	public Integral updateUserIntegral(NewsCommentBean bean, String integralCategory) {
		//处理该用户获得的评论积分
		  //1.操作积分子表
		IntegralSub intergalSub = new IntegralSub();
		intergalSub.setUserId(bean.getUserId()); //客户端用户
		intergalSub.setIntegralSource(bean.getNewsId()); //积分来源  即 新闻资讯的ID
		intergalSub.setInteCateId(Long.valueOf(integralCategory)); //积分类别Id
		Long basicVlaue = new Long(0); //基本积分
		Long rewardValue = new Long(0); //奖励积分（基本积分乘以奖励倍数）
		//根据参数代码得到评论的积分配置
		SystemConfig  config = new SystemConfig();
		config = systemConfigService.getIdByParameCode(SystemCommon_Constant.SYSTEM_CONFIG_COMMENT_INTEGRAL);
		if(config!=null){
			/*if(!DataUtil.isNull(config.getParameCurrentValue())){
				//当前值不为空，则基本积分等于当前值
				basicVlaue = config.getParameCurrentValue();
			}else*/ basicVlaue = config.getParameDefaultValue();
		}
		//客户端用户
		MMember cuser = clientUserService.get(bean.getUserId());
		String level = cuser.getCmemberlevel(); //用户的级别
		//通过用户的级别从会员等级表中查询到该用户所能够用户的奖励倍数（此时的用户级别即为基本表的ID值）
		String times = memberGradeService.get(Long.valueOf(level)).getRewardMultiple();
		if(!DataUtil.isEmpty(times)){
			BigDecimal b = new BigDecimal(basicVlaue);
			BigDecimal t = new BigDecimal(times);
			rewardValue = b.multiply(t).longValue();
		}
		intergalSub.setBasicValue(basicVlaue); 
		intergalSub.setRewardValue(rewardValue); 
		intergalSub.setValid(SystemCommon_Constant.VALID_STATUS_1); //默认有效
		intergalSub.setTotalValue(basicVlaue+rewardValue); //本次获得的总积分
		intergalSub.setGainDate(DateUtil.getTimestamp());//积分获得日期
//		intergalSub.setSourceTitle(bean.getSourceTitle());
		integralSubService.save(intergalSub);
		
		
		 //2.操作用户的积分总表
		Integral integral = getIntegralInfoByUserId(bean.getUserId());
		Integral in = new Integral();
		if(integral==null){ //说明该用户还没有存在积分信息
			in.setCurrentValue(intergalSub.getTotalValue());
			in.setUsedValue(new Long(0));
			in.setUserId(bean.getUserId());
			save(in);
			return in;
		}else{
		//	integral = new Integral();
			integral.setCurrentValue(integral.getCurrentValue()+ intergalSub.getTotalValue());
			update(integral);
			return integral;
		}
		
	}


	/**
	 * 
	 * @Description:  用户当前可用积分
	 * @author yanghui 
	 * @Created 2014-4-1
	 * @param userId
	 * @return
	 */
	public Long getCurrentIntegralByUserId(Long userId) {
		Long value = new Long(0);
		List list = getDao().find(" from "+getEntityName()+" where userId=?", userId);
		if(list.size()>0){
			Integral i = (Integral) list.get(0);
			value = i.getCurrentValue();
		}
		return value;
	}


	/**
	 * 
	 * @Description:  通过用户ID得到该用户当前的积分信息
	 * @author yanghui 
	 * @Created 2014-4-4
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
	
	
	
	

}
