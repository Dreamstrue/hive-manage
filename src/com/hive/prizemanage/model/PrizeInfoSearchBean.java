package com.hive.prizemanage.model;

import java.util.Date;

public class PrizeInfoSearchBean {
	
	private Long prizeId;
	private String prizeName;
	private Long prizeCateId;
	private Date beginDate; //
	private Date endDate;
	
	
	public Long getPrizeId() {
		return prizeId;
	}
	public void setPrizeId(Long prizeId) {
		this.prizeId = prizeId;
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
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	

}
