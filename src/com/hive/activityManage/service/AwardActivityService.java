package com.hive.activityManage.service;



import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.activityManage.entity.ActivityPrizeLink;
import com.hive.activityManage.entity.AwardActivity;

import dk.dao.BaseDao;
import dk.service.BaseService;
/**
 * yyf 20160606 add
 * 
 */
@Service
public class AwardActivityService extends BaseService<AwardActivity> {

	@Resource
	private BaseDao<AwardActivity> awardActivityDao;
	@Override
	protected BaseDao<AwardActivity> getDao() {
		return awardActivityDao;
	}
	/**
	 * 根据父id获取抽奖活动id
	 * @param pid
	 * @return
	 */
	public AwardActivity findAwardActivityIdByPid(Long pid) {
		String hql = "from " + getEntityName()+" where pid = "+pid+"";
		List<AwardActivity> list = this.getDao().find(hql);
		if(list.size()==1){
			return list.get(0);
		}else{
			return null;
		}
	}

}
