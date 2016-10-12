package com.hive.qrcode.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 二维码表
 * 项目名称：zxt-manage    
 * 类名称：Qrcode   
 * 类描述：二维码表    
 * 创建人：yyf   
 */
@Entity
@Table(name = "QRCODE")
public class QRCode {
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid") // 通过注解方式生成一个generator
	@GeneratedValue(generator = "idGenerator") // 使用generator
	private String id;	//标识编号
	
	/**
	 * 二维码编号
	 */
	@Column(name = "QRCODENUMBER", length = 100)
	private String qrcodeNumber;
	/**
	 * sn码
	 */
	@Column(name = "SN", length = 100)
	private String sn;
	
	/**
	 * 二维码类别
	 * 1：url,2:文本,3：图片
	 */
	@Column(name = "QRCODETYPE", length = 5)
	private String qrcodeType;
	
	/**
	 * 类别的具体内容
	 */
	@Column(name = "CONTENT", length = 2000)
	private String content;
	/**
	 * 二维码值，即本身的url
	 */
	@Column(name = "QRCODEVALUE", length = 1000)
	private String qrcodeValue;
	
	/**
	 * 二维码批次id
	 */
	@Column(name = "QRCODEBATCHID", length = 100)
	private String qrcodeBatchId;
	/**
	 * 发放明细id
	 */
	@Column(name = "ISSUEDETAILID", length = 100)
	private String issueDetailId;
	/**
	 * 领取二维码的实体id
	 */
	@Column(name = "ENTITYID", length = 100)
	private String entityId;
	
	/**
	 * 标签snid
	 * 保留，暂无用20160816 add yyf
	 */
	@Column(name = "SNBASEID", length = 100)
	private String snbaseId;
	/**
	 * 二维码状态
	 * 0：未印刷（未生效），1：印刷中(预占用)，2：已印刷（空闲），3：待发放，4：已发放，5：已绑定,6:作废
	 */
	@Column(name = "QRCODESTATUS", length = 5)
	private String qrcodeStatus;


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

	public String getIssueDetailId() {
		return issueDetailId;
	}

	public void setIssueDetailId(String issueDetailId) {
		this.issueDetailId = issueDetailId;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getSnbaseId() {
		return snbaseId;
	}

	public void setSnbaseId(String snbaseId) {
		this.snbaseId = snbaseId;
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

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}


}
