package com.hive.qrcode.model;


import java.util.Date;



public class QRCodeIssueDetailVo {
	
	private String id;	//标识编号
	private String pid;
	private String batchNumber;
	private String qrcodeType;
	private String creater;
	private Date createTime;
	private String valid;
	private Integer amount;
	
	private QRCodeIssueVo qrcodeIssueVo;
	private String detailQrcodeIds;//用来承载二维码ids数据的
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public String getQrcodeType() {
		return qrcodeType;
	}
	public void setQrcodeType(String qrcodeType) {
		this.qrcodeType = qrcodeType;
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
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public QRCodeIssueVo getQrcodeIssueVo() {
		return qrcodeIssueVo;
	}
	public void setQrcodeIssueVo(QRCodeIssueVo qrcodeIssueVo) {
		this.qrcodeIssueVo = qrcodeIssueVo;
	}
	public String getDetailQrcodeIds() {
		return detailQrcodeIds;
	}
	public void setDetailQrcodeIds(String detailQrcodeIds) {
		this.detailQrcodeIds = detailQrcodeIds;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

}
