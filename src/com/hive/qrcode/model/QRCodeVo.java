package com.hive.qrcode.model;

import org.apache.commons.lang3.StringUtils;

import com.hive.enterprisemanage.entity.EEnterpriseinfo;
import com.hive.surveymanage.entity.IndustryEntity;
import com.hive.tagManage.entity.SNBase;




/**
 * 二维码vo对象
 * 项目名称：zxt-manage    
 * 创建人：yyf   
 */
public class QRCodeVo {
	private String id;	//标识编号
	private String sn;
	private String qrcodeNumber;
	private String qrcodeType;
	private String qrcodeTypeStr;
	private String content;
	private String qrcodeValue;
	private String qrcodeBatchId;
	private QRCodeBatchVo qrcodeBatchVo;
	private String qrcodeStatus;
	private String issueDetailId;
	private String entityId;//关联id
	private IndustryEntity industryEntity;
	private String snbaseId;//关联的sn码
	private SNBase snBaseInfo;
	private String issueId;//发放记录id
	private String issueDetailIds;//多个发放明细id
	
	private String showSimpleQrcodeUrl;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQrcodeNumber() {
		return qrcodeNumber;
	}

	public void setQrcodeNumber(String qrcodeNumber) {
		this.qrcodeNumber = qrcodeNumber;
	}

	public String getQrcodeType() {
		return qrcodeType;
	}

	public void setQrcodeType(String qrcodeType) {
		this.qrcodeType = qrcodeType;
		setQrcodeTypeStr(qrcodeType);
	}

	public String getQrcodeBatchId() {
		return qrcodeBatchId;
	}

	public void setQrcodeBatchId(String qrcodeBatchId) {
		this.qrcodeBatchId = qrcodeBatchId;
	}

	public String getQrcodeStatus() {
		return qrcodeStatus;
	}

	public void setQrcodeStatus(String qrcodeStatus) {
		this.qrcodeStatus = qrcodeStatus;
	}

	public QRCodeBatchVo getQrcodeBatchVo() {
		return qrcodeBatchVo;
	}

	public void setQrcodeBatchVo(QRCodeBatchVo qrcodeBatchVo) {
		this.qrcodeBatchVo = qrcodeBatchVo;
	}

	public String getIssueDetailId() {
		return issueDetailId;
	}

	public void setIssueDetailId(String issueDetailId) {
		this.issueDetailId = issueDetailId;
	}

	public String getIssueDetailIds() {
		return issueDetailIds;
	}

	public void setIssueDetailIds(String issueDetailIds) {
		this.issueDetailIds = issueDetailIds;
	}

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public IndustryEntity getIndustryEntity() {
		return industryEntity;
	}

	public void setIndustryEntity(IndustryEntity industryEntity) {
		this.industryEntity = industryEntity;
	}

	public String getSnbaseId() {
		return snbaseId;
	}

	public void setSnbaseId(String snbaseId) {
		this.snbaseId = snbaseId;
	}

	public SNBase getSnBaseInfo() {
		return snBaseInfo;
	}

	public void setSnBaseInfo(SNBase snBaseInfo) {
		this.snBaseInfo = snBaseInfo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getQrcodeValue() {
		return qrcodeValue;
	}

	public void setQrcodeValue(String qrcodeValue) {
		this.qrcodeValue = qrcodeValue;
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

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getShowSimpleQrcodeUrl() {
		return showSimpleQrcodeUrl;
	}

	public void setShowSimpleQrcodeUrl(String showSimpleQrcodeUrl) {
		this.showSimpleQrcodeUrl = showSimpleQrcodeUrl;
	}
	
	
}
