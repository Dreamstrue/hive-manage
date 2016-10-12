package com.hive.surveymanage.model;

import java.util.Date;

/**
 * @description 
 * @author 燕珂
 * @createtime 2014-3-15 下午06:11:53
 */
public class SurveyVo {

	private Long id;
	private String subject;
	private Long industryid;
	private Long categoryid;
	private String tags;
	private Integer numlimit;
	private Date begintime;
	private Date endtime;
	private String status;
	private Integer integral;
	private int participatenum;
	private Long createid;
	private Date createtime;
	private Long modifyid;
	private Date modifytime;
	private String auditstatus;
	private String isShow;
	private String picturePath;
	
	// 上面的属性与 User.java 中的属性相同，以下是辅助字段
	private String industryName;
	private String categoryName;
	private String createName;
	/**
	 * 是否允许匿名投票
	 */
	private String anonymous;
	/**
	 *是否需要关联具体商铺
	 */
	private String isRelShop;
	/**
	 * 二维码 标识
	 */
	private String hasCode;
	
	public Date getBegintime() {
		return begintime;
	}
	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}
	public Long getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Long getCreateid() {
		return createid;
	}
	public void setCreateid(Long createid) {
		this.createid = createid;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIndustryid() {
		return industryid;
	}
	public void setIndustryid(Long industryid) {
		this.industryid = industryid;
	}
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	public Long getModifyid() {
		return modifyid;
	}
	public void setModifyid(Long modifyid) {
		this.modifyid = modifyid;
	}
	public Date getModifytime() {
		return modifytime;
	}
	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}
	public Integer getNumlimit() {
		return numlimit;
	}
	public void setNumlimit(Integer numlimit) {
		this.numlimit = numlimit;
	}
	public int getParticipatenum() {
		return participatenum;
	}
	public void setParticipatenum(int participatenum) {
		this.participatenum = participatenum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getAuditstatus() {
		return auditstatus;
	}
	public void setAuditstatus(String auditstatus) {
		this.auditstatus = auditstatus;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public String getAnonymous() {
		return anonymous;
	}
	public void setAnonymous(String anonymous) {
		this.anonymous = anonymous;
	}
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	public String getIsRelShop() {
		return isRelShop;
	}
	public void setIsRelShop(String isRelShop) {
		this.isRelShop = isRelShop;
	}
	public String getHasCode() {
		return hasCode;
	}
	public void setHasCode(String hasCode) {
		this.hasCode = hasCode;
	}
	
	
}
