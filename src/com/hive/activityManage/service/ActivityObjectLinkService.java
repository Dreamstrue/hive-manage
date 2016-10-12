package com.hive.activityManage.service;




import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.activityManage.entity.ActivityObjectLink;

import dk.dao.BaseDao;
import dk.service.BaseService;
/**
 * yyf 20160616 add
 * 
 */
@Service
public class ActivityObjectLinkService extends BaseService<ActivityObjectLink> {

	@Resource
	private BaseDao<ActivityObjectLink> dao;
	@Override
	protected BaseDao<ActivityObjectLink> getDao() {
		return dao;
	}
	public ActivityObjectLink findByAnctionObjectId(Long actionObjectId) {
		String hql = "from " + getEntityName()+" where actionObjectId = "+actionObjectId+" and isValid <> '0' ";
		List<ActivityObjectLink> list = this.getDao().find(hql);
		if(list.size()==1&&list.size()>0){
			return list.get(0);
		}else{
			return new ActivityObjectLink();
		}
	}
	public List<ActivityObjectLink> findByActivityId(Long activityId) {
		String hql = "from " + getEntityName()+" where activityId = "+activityId+" and isValid <> '0' ";
		List<ActivityObjectLink> list = this.getDao().find(hql);
		return list;
	}

}
