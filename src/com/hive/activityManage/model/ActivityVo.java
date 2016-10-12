package com.hive.activityManage.model;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.hive.common.SystemCommon_Constant;
import com.hive.util.DateUtil;


/**
 * @description 
 * @author yyf 20160606
 */
public class ActivityVo {

	private Long id;//活动实例id
	private Long sonId;//子活动活动实例id
	private String orderNum;//活动序号
	private String theme;//活动主题
	private String activityType;//活动类型
	private String activityTypeStr;//活动类型名称
	private Date beginTime;//活动周期开始时间
	private Date endTime;//活动周期结束时间
	private String beginTimeStr;//活动周期开始时间
	private String endTimeStr;//活动周期结束时间
	private Date createTime;//活动创建时间
	private String createTimeStr;//活动创建时间
	private Integer joinTimes;//参与次数
	private String joinTimesPeriod;//参与次数周期，比如每天只能参加一次
	private String content;//活动详情
	private String oldContent;//旧活动详情
	private String status;//是否启动 0表示未启动  1表示已启动 2表示已结束
	private String isValid;//是否有效

	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
		if(StringUtils.isNotBlank(activityType)){
			if(activityType.equals(SystemCommon_Constant.ACTIVITY_TYPE_1)){
				this.setActivityTypeStr("抽奖活动");
			}else if(activityType.equals(SystemCommon_Constant.ACTIVITY_TYPE_2)){
				this.setActivityTypeStr("奖励活动");
			}
		}
	}
	
	public String getActivityTypeStr() {
		return activityTypeStr;
	}
	public void setActivityTypeStr(String activityTypeStr) {
		this.activityTypeStr = activityTypeStr;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		String beginTimeStr = DateUtil.format(beginTime, "yyyy-MM-dd HH:mm:ss");
		this.setBeginTimeStr(beginTimeStr);
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		String endTimeStr = DateUtil.format(endTime, "yyyy-MM-dd HH:mm:ss");
		this.setEndTimeStr(endTimeStr);
		this.endTime = endTime;
	}

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		String createTimeStr = DateUtil.format(createTime, "yyyy-MM-dd HH:mm:ss");
		this.setCreateTimeStr(createTimeStr);
		this.createTime = createTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public Integer getJoinTimes() {
		return joinTimes;
	}
	public void setJoinTimes(Integer joinTimes) {
		this.joinTimes = joinTimes;
	}

	public String getJoinTimesPeriod() {
		return joinTimesPeriod;
	}
	public void setJoinTimesPeriod(String joinTimesPeriod) {
		this.joinTimesPeriod = joinTimesPeriod;
	}
	public String getBeginTimeStr() {
		return beginTimeStr;
	}
	public void setBeginTimeStr(String beginTimeStr) {
		this.beginTimeStr = beginTimeStr;
	}
	public String getEndTimeStr() {
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public Long getSonId() {
		return sonId;
	}
	public void setSonId(Long sonId) {
		this.sonId = sonId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getOldContent() {
		return oldContent;
	}
	public void setOldContent(String oldContent) {
		this.oldContent = oldContent;
	}
	
}
