package com.hive.activityManage.service;



import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hive.activityManage.entity.Activity;
import com.hive.activityManage.model.ActivityVo;
import com.hive.activityManage.model.AwardActivityVo;
import com.hive.util.DateUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;
/**
 * yyf 20160606 add
 * 
 */
@Service
public class ActivityService extends BaseService<Activity> {

	@Resource
	private BaseDao<Activity> activityDao;
	@Override
	protected BaseDao<Activity> getDao() {
		return activityDao;
	}
	
//	@Resource
//	private MemberGradeService memberGradeService;
	/**
	 * 获取活动列表数据
	 * @param page
	 * @param vo
	 * @return
	 */
	public DataGrid dataGrid(RequestPage page,ActivityVo vo) {
		String hql = "from " + getEntityName()+" where  isValid='1' ";
		String countsql = "select count(*) from "+getEntityName()+" where isValid='1' ";
		if(StringUtils.isNotBlank(vo.getOrderNum())){
			hql = hql + " and orderNum = '"+vo.getOrderNum()+"'";
			countsql = countsql + " and orderNum = '"+vo.getOrderNum()+"'";
		}
		if(StringUtils.isNotBlank(vo.getTheme())){
			hql = hql + " and theme like '%"+vo.getTheme()+"%'";
			countsql = countsql + " and theme like '%"+vo.getTheme()+"%'";
		}
		if(StringUtils.isNotBlank(vo.getActivityType())){
			hql = hql + " and activityType = '"+vo.getActivityType()+"'";
			countsql = countsql + " and activityType = '"+vo.getActivityType()+"'";
		}
		if(StringUtils.isNotBlank(vo.getStatus())){
			hql = hql + " and status = '"+vo.getStatus()+"'";
			countsql = countsql + " and status = '"+vo.getStatus()+"'";
		}
		if(vo.getBeginTime()!=null){
			String beginTimeStr = DateUtil.format(vo.getBeginTime(), "yyyy-MM-dd");
			hql = hql + " and beginTime >= '"+beginTimeStr+" 00:00:00' and beginTime <= '"+beginTimeStr+" 23:59:59'";
			countsql = countsql + " and beginTime >= '"+beginTimeStr+" 00:00:00' and beginTime <= '"+beginTimeStr+" 23:59:59'";
		}
		if(vo.getEndTime()!=null){
			String endTimeStr = DateUtil.format(vo.getEndTime(), "yyyy-MM-dd");
			hql = hql + " and endTime >= '"+endTimeStr+" 00:00:00' and beginTime <= '"+endTimeStr+" 23:59:59'";
			countsql = countsql + " and endTime >= '"+endTimeStr+" 00:00:00' and beginTime <= '"+endTimeStr+" 23:59:59'";
		}
		
		List<Activity> list = getDao().find(page.getPage(), page.getRows(),hql);
		long count = getDao().count(countsql);
		List<ActivityVo> voList = new ArrayList<ActivityVo>();
		for(Activity a : list){
			ActivityVo avo = new ActivityVo();
			try {
				PropertyUtils.copyProperties(avo, a);
				voList.add(avo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		return new DataGrid(count, voList);
	}
	/**
	 * 获取已经启动活动列表数据用于分配给作用对象
	 * @param page
	 * @param vo
	 * @return
	 */
	public DataGrid dataGridStartedActivity(RequestPage page,ActivityVo vo) {
		String hql = "from " + getEntityName()+" where  isValid='1' and status='1' ";
		String countsql = "select count(*) from "+getEntityName()+" where isValid='1'  and status='1' ";
		if(StringUtils.isNotBlank(vo.getOrderNum())){
			hql = hql + " and orderNum = '"+vo.getOrderNum()+"'";
			countsql = countsql + " and orderNum = '"+vo.getOrderNum()+"'";
		}
		if(StringUtils.isNotBlank(vo.getTheme())){
			hql = hql + " and theme like '%"+vo.getTheme()+"%'";
			countsql = countsql + " and theme like '%"+vo.getTheme()+"%'";
		}
		if(StringUtils.isNotBlank(vo.getActivityType())){
			hql = hql + " and activityType = '"+vo.getActivityType()+"'";
			countsql = countsql + " and activityType = '"+vo.getActivityType()+"'";
		}
		if(StringUtils.isNotBlank(vo.getStatus())){
			hql = hql + " and status = '"+vo.getStatus()+"'";
			countsql = countsql + " and status = '"+vo.getStatus()+"'";
		}
		if(vo.getBeginTime()!=null){
			String beginTimeStr = DateUtil.format(vo.getBeginTime(), "yyyy-MM-dd");
			hql = hql + " and beginTime >= '"+beginTimeStr+" 00:00:00' and beginTime <= '"+beginTimeStr+" 23:59:59'";
			countsql = countsql + " and beginTime >= '"+beginTimeStr+" 00:00:00' and beginTime <= '"+beginTimeStr+" 23:59:59'";
		}
		if(vo.getEndTime()!=null){
			String endTimeStr = DateUtil.format(vo.getEndTime(), "yyyy-MM-dd");
			hql = hql + " and endTime >= '"+endTimeStr+" 00:00:00' and beginTime <= '"+endTimeStr+" 23:59:59'";
			countsql = countsql + " and endTime >= '"+endTimeStr+" 00:00:00' and beginTime <= '"+endTimeStr+" 23:59:59'";
		}
		
		List<Activity> list = getDao().find(page.getPage(), page.getRows(),hql);
		long count = getDao().count(countsql);
		List<ActivityVo> voList = new ArrayList<ActivityVo>();
		for(Activity a : list){
			ActivityVo avo = new ActivityVo();
			try {
				PropertyUtils.copyProperties(avo, a);
				voList.add(avo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		return new DataGrid(count, voList);
	}

	public Activity findNewestActivity() {
		String hql = "from " + getEntityName()+" where  isValid='1' order by orderNum desc ";
		List<Activity> list = getDao().find(hql);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 获取所有超时活动
	 * @return
	 */
	public List<Activity> findAllTimeOutActivity() {
		String currentTime = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		String hql = "from " + getEntityName()+" where  isValid='1' and endTime<='"+currentTime+"'";
		List<Activity> list = getDao().find(hql);
		System.out.println("超时刷新的sql=="+hql);
		return list;
	}
	/**
	 * 获取活动已经开始的时间
	 * @param awardActivityId
	 * @return
	 */
	public Long checkActivityBeginTime(Long awardActivityId) {
		String hql = "select at from " + getEntityName()+" at, AwardActivity aa where aa.pid = at.id and  aa.id = "+awardActivityId+"";
		List<Activity> list = this.getDao().find(hql);
		if(list.size()>0){
			Long beginTime = list.get(0).getBeginTime().getTime();
			Long nowTime = new Date().getTime();
			Long resultTime = nowTime - beginTime;
			return resultTime;
		}else{
			return null;
		}
	}
}
