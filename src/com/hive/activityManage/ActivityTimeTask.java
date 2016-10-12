package com.hive.activityManage;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hive.activityManage.entity.Activity;
import com.hive.activityManage.entity.ActivityObjectLink;
import com.hive.activityManage.service.ActivityObjectLinkService;
import com.hive.activityManage.service.ActivityService;
import com.hive.common.SystemCommon_Constant;

/**
 * 活动定时任务，即活动超时后更改活动状态
 * @author yyf 
 * 21600617
 *
 */
@Component
public class ActivityTimeTask {
	
	@Resource
	private ActivityService activityService;
	@Resource
	private ActivityObjectLinkService activityObjectLinkService;
	
	public void execute() {
		//获取所有超时的活动
		List<Activity> list = activityService.findAllTimeOutActivity();
		for(Activity act : list){
			act.setStatus(SystemCommon_Constant.ACTIVITY_STATUS_2);
			activityService.update(act);
			//获取所有关联此活动的作用对象中间表数据
			List<ActivityObjectLink> linkList = activityObjectLinkService.findByActivityId(act.getId());
			for(ActivityObjectLink aol : linkList){
				aol.setIsValid("0");
				//更新关联此活动的数据
				activityObjectLinkService.update(aol);
			}
			
		}
		System.out.println("刷新活动超时状态！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
	}
}
