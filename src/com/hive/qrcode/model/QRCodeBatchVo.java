package com.hive.qrcode.model;

import java.util.Date;

/**
 * 批次信息vo对象
 * 项目名称：zxt-manage    
 * 创建人：yyf   
 */
public class QRCodeBatchVo {
	private String id;	//标识编号
	private String batchNumber;//批次编号
	private Integer amount;//批次数量
	private Integer status;//批次状态，0:未印刷   1:印刷中   2：已印刷
	private String creater;//创建者id
	private Date createTime;//创建时间
	
	private String createrName;
	private Integer validAmount;//有效数量
	
	private String validCount;//最小有效数量，作为条件查询使用
	
	private String digitNumber;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDigitNumber() {
		return digitNumber;
	}
	public void setDigitNumber(String digitNumber) {
		this.digitNumber = digitNumber;
	}
	public String getCreaterName() {
		return createrName;
	}
	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}
	public Integer getValidAmount() {
		return validAmount;
	}
	public void setValidAmount(Integer validAmount) {
		this.validAmount = validAmount;
	}
	public String getValidCount() {
		return validCount;
	}
	public void setValidCount(String validCount) {
		this.validCount = validCount;
	}
	
	
	
}
