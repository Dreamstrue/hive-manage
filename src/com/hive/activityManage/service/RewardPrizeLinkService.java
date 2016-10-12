package com.hive.activityManage.service;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Service;

import com.hive.activityManage.entity.RewardPrizeLink;
import com.hive.activityManage.model.RewardPrizeLinkVo;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;
/**
 * yyf 20160621 add
 * 
 */
@Service
public class RewardPrizeLinkService extends BaseService<RewardPrizeLink> {

	@Resource
	private BaseDao<RewardPrizeLink> rewardPrizeLinkDao;
	@Override
	protected BaseDao<RewardPrizeLink> getDao() {
		return rewardPrizeLinkDao;
	}
	/**
	 * 获取指定活动的奖品数据列表
	 * @param page
	 * @param vo
	 * @return
	 */
	public DataGrid dataGrid(RequestPage page,Long activityId) {
		String hql = "from " + getEntityName()+" where rewardId = "+activityId+" order by prizeLevel ";
		String countsql = "select count(*) from "+getEntityName()+"  where rewardId = "+activityId+" ";
		List<RewardPrizeLink> list = getDao().find(page.getPage(), page.getRows(),hql);
		long count = getDao().count(countsql);
		List<RewardPrizeLinkVo> resultList = new ArrayList<RewardPrizeLinkVo>();
		for(RewardPrizeLink apl : list){
			RewardPrizeLinkVo vo = new RewardPrizeLinkVo();
			try {
				PropertyUtils.copyProperties(vo, apl);
				resultList.add(vo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		return new DataGrid(count, resultList);
	}
	/**
	 * 根据抽奖活动id获取到本次抽奖活动的所有奖品
	 * @param activityId
	 * @return
	 */
	public List<RewardPrizeLink> findRewardByActivityId(Long activityId) {
		String hql = "from " + getEntityName()+" where rewardId = "+activityId+" order by prizeNum";
		return this.getDao().find(hql);
	}
	/**
	 * 获取大于指定奖品序号的所有奖品
	 * @param prizeLevel
	 * @return
	 */
	public List<RewardPrizeLink> findRewardByGreaterThanPrizeNum(Integer prizeLevel, Long activityId) {
		
		String hql = "from " + getEntityName()+" where prizeNum > "+prizeLevel+" and rewardId= "+activityId+" order by prizeNum";
		return this.getDao().find(hql);
	}
	/**
	 * 根据活动id和奖品序号获取活动奖品数据
	 * @param result
	 * @param awardActivityId
	 * @return
	 */
//	public RewardPrizeLink findActivityPrizeByPrizeLevelAndId(Integer level,Long awardActivityId) {
//		
//		String hql = "from " + getEntityName()+" where prizeLevel = "+level+" and activityId = "+awardActivityId+" ";
//		List<RewardPrizeLink> list = this.getDao().find(hql);
//		if(list.size()==1){
//			return list.get(0);
//		}else{
//			return  null;
//		}
//	}

}
