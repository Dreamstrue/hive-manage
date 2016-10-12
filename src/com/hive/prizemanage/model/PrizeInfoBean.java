package com.hive.prizemanage.model;

import java.util.Date;

public class PrizeInfoBean{
	
	private Long id;
	private String prizeName;  //奖品名称
	private Long prizeCateId;  //奖品类别
	private String picturePath; //奖品图片路径
	private String pictureName; //图片名称
	private Long excIntegral; //兑换奖品所需积分
	private String prizeExplain; //奖品说明
	private Long prizeNum;   //奖品数量
	private Long excNum; //已兑换数量
	private Long surplusNum; //剩余数量（库存数量）
	private Date validDate; //奖品有效期（下线时间）
	private String prizeCateName;// 类别名称
	private String imageExist;   //用于判断奖品图片是否被修改过
	private String entityCategory;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPrizeName() {
		return prizeName;
	}
	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	public Long getPrizeCateId() {
		return prizeCateId;
	}
	public void setPrizeCateId(Long prizeCateId) {
		this.prizeCateId = prizeCateId;
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	public String getPictureName() {
		return pictureName;
	}
	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}
	public Long getExcIntegral() {
		return excIntegral;
	}
	public void setExcIntegral(Long excIntegral) {
		this.excIntegral = excIntegral;
	}
	public String getPrizeExplain() {
		return prizeExplain;
	}
	public void setPrizeExplain(String prizeExplain) {
		this.prizeExplain = prizeExplain;
	}
	public Long getPrizeNum() {
		return prizeNum;
	}
	public void setPrizeNum(Long prizeNum) {
		this.prizeNum = prizeNum;
	}
	public Long getExcNum() {
		return excNum;
	}
	public void setExcNum(Long excNum) {
		this.excNum = excNum;
	}
	
	
	public Long getSurplusNum() {
		return surplusNum;
	}
	public void setSurplusNum(Long surplusNum) {
		this.surplusNum = surplusNum;
	}
	public Date getValidDate() {
		return validDate;
	}
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}
	public String getPrizeCateName() {
		return prizeCateName;
	}
	public void setPrizeCateName(String prizeCateName) {
		this.prizeCateName = prizeCateName;
	}
	public String getImageExist() {
		return imageExist;
	}
	public void setImageExist(String imageExist) {
		this.imageExist = imageExist;
	}
	public String getEntityCategory() {
		return entityCategory;
	}
	public void setEntityCategory(String entityCategory) {
		this.entityCategory = entityCategory;
	}
	
	
	
}
