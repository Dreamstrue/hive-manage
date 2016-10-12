package com.hive.qrcode.model;


import java.util.Date;

import org.apache.commons.lang3.StringUtils;


public class QRCodeIssueVo {
	
	private String id;	//标识编号
	private String number;
	private String qrcodeType;
	private String qrcodeTypeStr;
	private String qrcodeContent;
	private String entityId;
	private String entityName;
	private String linkPerson;
	private String linkPhone;
	private String linkAddress;
	private String status;
	private String creater;
	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getLinkPerson() {
		return linkPerson;
	}

	public void setLinkPerson(String linkPerson) {
		this.linkPerson = linkPerson;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public String getLinkAddress() {
		return linkAddress;
	}

	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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

	public String getQrcodeType() {
		return qrcodeType;
	}

	public void setQrcodeType(String qrcodeType) {
		this.qrcodeType = qrcodeType;
		this.setQrcodeTypeStr(qrcodeType);
	}

	public String getQrcodeContent() {
		return qrcodeContent;
	}

	public void setQrcodeContent(String qrcodeContent) {
		this.qrcodeContent = qrcodeContent;
	}
	public String getQrcodeTypeStr() {
		return qrcodeTypeStr;
	}

	public void setQrcodeTypeStr(String qrcodeType) {
		if(StringUtils.isNotBlank(qrcodeType)){
			if(qrcodeType.equals("1")){
				this.qrcodeTypeStr = "url";
			}else if(qrcodeType.equals("2")){
				this.qrcodeTypeStr = "文本";
			}else if(qrcodeType.equals("3")){
				this.qrcodeTypeStr = "图片";
			}else{
				this.qrcodeTypeStr = "暂无";
			}
		}else{
			this.qrcodeTypeStr = "未知";
		}
	}

}
