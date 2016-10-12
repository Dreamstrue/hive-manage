package com.hive.prizemanage.model;

import java.util.Date;

public class PrizeSubRecordBean {
	
	private Long id; //补货记录的ID
	private String prizeName;
	private Long prizeId;
	
	private String userName;// 补货人
	private Long  prizeNum;
	private Date createTime;//补货时间
	public String getPrizeName() {
		return prizeName;
	}
	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	public Long getPrizeId() {
		return prizeId;
	}
	public void setPrizeId(Long prizeId) {
		this.prizeId = prizeId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getPrizeNum() {
		return prizeNum;
	}
	public void setPrizeNum(Long prizeNum) {
		this.prizeNum = prizeNum;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	
}
