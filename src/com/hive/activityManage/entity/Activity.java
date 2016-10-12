package com.hive.activityManage.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import dk.util.JsonDateTimeSerializer;

/**
 * 活动表
 */
@Entity
@Table(name = "A_Activity", schema = "ZXT")
public class Activity implements java.io.Serializable {

	// Fields

	private Long id;//活动实例id
	private String orderNum;//活动序号
	private String theme;//活动主题
	private String activityType;//活动类型
	private Date beginTime;//活动周期开始时间
	private Date endTime;//活动周期结束时间
	private Date createTime;//活动创建时间
	private Integer joinTimes;//参与次数
	private String joinTimesPeriod;//参与次数周期，比如每天只能参加一次
	private String content;//活动详情
	private String status;//是否启动 0表示未启动  1表示已启动 2表示已结束
	private String isValid;//是否有效

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "ID", unique = true, nullable = true)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "ACTIVITYTYPE", nullable = false, length = 10)
	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	@Column(name = "ORDERNUM", nullable = false, length = 32)
	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	@Column(name = "THEME", nullable = false, length = 100)
	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "BEGINTIME", nullable = true, length = 32)
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENDTIME", nullable = false, length = 7)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME", nullable = true, length = 7)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "STATUS", length=10)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "ISVALID", length=10)
	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	@Column(name = "JOINTIMES" ,nullable = true)
	public Integer getJoinTimes() {
		return joinTimes;
	}
	public void setJoinTimes(Integer joinTimes) {
		this.joinTimes = joinTimes;
	}
	@Column(name = "JOINTIMESPERIOD", length=10)
	public String getJoinTimesPeriod() {
		return joinTimesPeriod;
	}

	public void setJoinTimesPeriod(String joinTimesPeriod) {
		this.joinTimesPeriod = joinTimesPeriod;
	}
	@Column(name = "content", nullable = true, length = 500)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}