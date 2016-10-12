package com.hive.qrcode.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 发放二维码表
 * 项目名称：zxt-manage    
 * 类名称：QRCodeIssue   
 * 类描述：发放二维码表    
 * 创建人：yyf   
 */
@Entity
@Table(name = "QRCODE_ISSUE")
public class QRCodeIssue {
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid") // 通过注解方式生成一个generator
	@GeneratedValue(generator = "idGenerator") // 使用generator
	private String id;	//标识编号
	
	/**
	 * 发放编号
	 */
	@Column(name = "number",nullable=false, length = 100)
	private String number;
	/**
	 * 发放的二维码类型
	 */
	@Column(name = "QRCODETYPE",nullable=false, length = 5)
	private String qrcodeType;
	/**
	 * 发放的二维码内容
	 */
	@Column(name = "QRCODECONTENT",nullable=false, length = 2000)
	private String qrcodeContent;
	/**
	 * 领取实体id
	 */
	@Column(name = "ENTITYID",nullable=false, length = 100)
	private String entityId;
	/**
	 * 领取实体名称
	 */
	@Column(name = "ENTITYNAME",nullable=true, length = 100)
	private String entityName;
	/**
	 * 领取企业联系人
	 */
	@Column(name = "LINKPERSON",nullable=true, length = 100)
	private String linkPerson;
	/**
	 * 领取企业联系电话
	 */
	@Column(name = "LINKPHONE",nullable=true, length = 100)
	private String linkPhone;
	/**
	 * 领取企业地址
	 */
	@Column(name = "LINKADDRESS",nullable=true, length = 100)
	private String linkAddress;
	
	/**
	 * 发放记录状态
	 * 0：删除 1:待发放  2：已发放
	 */
	@Column(name = "STATUS",nullable=false, length = 5)
	private String status;
	/**
	 * 创建者
	 */
	@Column(name = "CREATER",nullable=false, length = 32)
	private String creater;
	
	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	@Column(name ="CREATETIME",nullable=false, length = 17)
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
	}

	public String getQrcodeContent() {
		return qrcodeContent;
	}

	public void setQrcodeContent(String qrcodeContent) {
		this.qrcodeContent = qrcodeContent;
	}


}
