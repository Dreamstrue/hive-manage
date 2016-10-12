package com.hive.activityManage.service;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Service;

import com.hive.activityManage.entity.ActivityPrizeLink;
import com.hive.activityManage.model.ActivityPrizeLinkVo;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;
/**
 * yyf 20160612 add
 * 
 */
@Service
public class ActivityPrizeLinkService extends BaseService<ActivityPrizeLink> {

	@Resource
	private BaseDao<ActivityPrizeLink> awardActivityDao;
	@Override
	protected BaseDao<ActivityPrizeLink> getDao() {
		return awardActivityDao;
	}
	/**
	 * 获取指定活动的奖品数据列表
	 * @param page
	 * @param vo
	 * @return
	 */
	public DataGrid dataGrid(RequestPage page,Long awardActivityId) {
		String hql = "from " + getEntityName()+" where awardActivityId = "+awardActivityId+" order by prizeLevel ";
		String countsql = "select count(*) from "+getEntityName()+"  where awardActivityId = "+awardActivityId+" ";
		List<ActivityPrizeLink> list = getDao().find(page.getPage(), page.getRows(),hql);
		long count = getDao().count(countsql);
		List<ActivityPrizeLinkVo> resultList = new ArrayList<ActivityPrizeLinkVo>();
		for(ActivityPrizeLink apl : list){
			ActivityPrizeLinkVo vo = new ActivityPrizeLinkVo();
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
	public List<ActivityPrizeLink> findAwardByActivityId(Long awardActivityId) {
		String hql = "from " + getEntityName()+" where awardActivityId = "+awardActivityId+" order by prizeLevel";
		return this.getDao().find(hql);
	}
	/**
	 * 获取大于指定奖品等级的所有奖品
	 * @param prizeLevel
	 * @return
	 */
	public List<ActivityPrizeLink> findAwardByGreaterThanPrizeLevel(Integer prizeLevel, Long awardActivityId) {
		
		String hql = "from " + getEntityName()+" where prizeLevel > "+prizeLevel+" and awardActivityId="+awardActivityId+" order by prizeLevel";
		return this.getDao().find(hql);
	}
	/**
	 * 根据活动id和奖品序号获取活动奖品数据
	 * @param result
	 * @param awardActivityId
	 * @return
	 */
	public ActivityPrizeLink findActivityPrizeByPrizeLevelAndId(Integer level,Long awardActivityId) {
		
		String hql = "from " + getEntityName()+" where prizeLevel = "+level+" and awardActivityId = "+awardActivityId+" ";
		List<ActivityPrizeLink> list = this.getDao().find(hql);
		if(list.size()==1){
			return list.get(0);
		}else{
			return  null;
		}
	}

}
