package com.hive.activityManage.service;



import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.activityManage.entity.RewardActivity;

import dk.dao.BaseDao;
import dk.service.BaseService;
/**
 * yyf 20160606 add
 * 
 */
@Service
public class RewardActivityService extends BaseService<RewardActivity> {

	@Resource
	private BaseDao<RewardActivity> rewardActivityDao;
	@Override
	protected BaseDao<RewardActivity> getDao() {
		return rewardActivityDao;
	}
	/**
	 * 根据父id获取抽奖活动id
	 * @param pid
	 * @return
	 */
	public RewardActivity findRewardActivityIdByPid(Long pid) {
		String hql = "from " + getEntityName()+" where pid = "+pid+"";
		List<RewardActivity> list = this.getDao().find(hql);
		if(list.size()==1){
			return list.get(0);
		}else{
			return null;
		}
	}

}
