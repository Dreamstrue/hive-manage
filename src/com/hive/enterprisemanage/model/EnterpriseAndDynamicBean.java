/**
 * 
 */
package com.hive.enterprisemanage.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hive.common.entity.Annex;
import com.hive.enterprisemanage.entity.EnterpriseRoomDynamic;
import com.hive.enterprisemanage.entity.EnterpriseRoomPicture;

/**  
 * Filename: EnterpriseAndDynamicBean.java  
 * Description:		
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-5-12  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-5-12 上午10:50:19				yanghui 	1.0
 */
public class EnterpriseAndDynamicBean extends EnterpriseRoomDynamic{

	/**
	 * 企业动态发布人用户名
	 */
	private String publishUserName=StringUtils.EMPTY;
	/**
	 * 企业logo路径
	 */
	private String logoPath=StringUtils.EMPTY;
	/**
	 * 企业名称
	 */
	private String enterpriseName=StringUtils.EMPTY;
	/**
	 * 企业简介
	 */
	private String enterpriseSummary=StringUtils.EMPTY;
	
	/**
	 * 企业空间照片列表
	 */
	private List<EnterpriseRoomPicture> picList;
	/**
	 * 附件列表
	 */
	private List<Annex> annexList;
	/**
	 * 评论列表
	 */
	private List<EnterpriseRoomReviewBean> reviewList ;
	/**
	 * 企业距离某坐标的距离
	 */
	private Double distance;
	
	
	
	
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public String getPublishUserName() {
		return publishUserName;
	}
	public void setPublishUserName(String publishUserName) {
		this.publishUserName = publishUserName;
	}
	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	public String getEnterpriseSummary() {
		return enterpriseSummary;
	}
	public void setEnterpriseSummary(String enterpriseSummary) {
		this.enterpriseSummary = enterpriseSummary;
	}
	public List<EnterpriseRoomPicture> getPicList() {
		return picList;
	}
	public void setPicList(List<EnterpriseRoomPicture> picList) {
		this.picList = picList;
	}
	public List<Annex> getAnnexList() {
		return annexList;
	}
	public void setAnnexList(List<Annex> annexList) {
		this.annexList = annexList;
	}
	public List<EnterpriseRoomReviewBean> getReviewList() {
		return reviewList;
	}
	public void setReviewList(List<EnterpriseRoomReviewBean> reviewList) {
		this.reviewList = reviewList;
	}
	
	
	
	
}
