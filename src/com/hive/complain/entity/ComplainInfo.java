package com.hive.complain.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import dk.util.JsonDateSerializer;
import dk.util.JsonDateTimeSerializer;

/**
 * 
* Filename: ComplainInfo.java  
* Description: 投诉信息实体类
* Copyright:Copyright (c)2013
* Company:  GuangFan 
* @author:  yanghui
* @version: 1.0  
* @Create:  2013-7-24  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2013-7-24 下午2:49:58				yanghui 	1.0
 */
@Entity
@Table(name = "COMPLAIN_INFO")
public class ComplainInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "complainId", unique = true, nullable = true)
	private Long id;
	
	@Column(name="title",length=200,nullable=true)
	private String title;
	
	@Column(name="tradeId",nullable=true)
	private String tradeId;
	
	@Column(name="tradeName",length=100,nullable=true)
	private String tradeName;
	
	@Column(name="productType",length=50,nullable=true)
	private String productType;
	
	@Column(name="productName",length=100,nullable=false)
	private String productName;
	
	@Column(name="productBrand",length=50,nullable=true)
	private String productBrand;
	
	@Column(name="productModel",length=100,nullable=true)
	private String productModel;
	
	@Column(name="comType",length=50,nullable=true)
	private String comType;
	
	@Column(name="proof",length=40,nullable=true)
	private String proof;
	
	@Column(name="buyDate",nullable=true)
	@Temporal(TemporalType.DATE)
	private Date buyDate;
	
	@Column(name="buyPrice",nullable=true)
	private BigDecimal buyPrice;
	
	@Column(name="producer",length=150,nullable=true)
	private String producer;
	
	@Column(name="flag",length=2,nullable=true)
	private String flag;
	
	@Column(name="dealState",length=5,nullable=false)
	private String dealState;
	
	@Column(name="comDate",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date comDate;
	
	@Column(name="searchCode",length=16,nullable=true)
	private String searchCode;
	
	@Column(name="wishResult",length=200,nullable=true)
	private String wishResult;
	
	@Column(name="content",length=1500,nullable=true)
	private String content;
	
	@Column(name="isValid",length=2,nullable=true)
	private String isValid;
	
	@Column(name="clickRate",length=3,nullable=false)
	private String clickRate;
	
	//企业名称
	@Column(name="enterprise",length=150,nullable=false)
	private String enterprise;
	
	//投诉回复
	@Column(name="reply",length=150,nullable=true)
	private String reply;

	//附件存放路径
	@Column(name="fileurl",length=300,nullable=true)
	private String fileurl;
	
	@Column(name="cate",length=100,nullable=true)
	private String group;
	
	//真实附件文件名
	@Column(name="filename",length=300,nullable=true)
	private String fileName;
	 
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}

	public String getProductModel() {
		return productModel;
	}

	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}

	public String getComType() {
		return comType;
	}

	public void setComType(String comType) {
		this.comType = comType;
	}

	public String getProof() {
		return proof;
	}

	public void setProof(String proof) {
		this.proof = proof;
	}

	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

	public BigDecimal getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getDealState() {
		return dealState;
	}

	public void setDealState(String dealState) {
		this.dealState = dealState;
	}




	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getComDate() {
		return comDate;
	}

	public void setComDate(Date comDate) {
		this.comDate = comDate;
	}

	public String getSearchCode() {
		return searchCode;
	}

	public void setSearchCode(String searchCode) {
		this.searchCode = searchCode;
	}

	public String getWishResult() {
		return wishResult;
	}

	public void setWishResult(String wishResult) {
		this.wishResult = wishResult;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public String getClickRate() {
		return clickRate;
	}

	public void setClickRate(String clickRate) {
		this.clickRate = clickRate;
	}

	public String getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(String enterprise) {
		this.enterprise = enterprise;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getFileurl() {
		return fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}
	
	
	/**
	 * 新增字段
	 */
	
	/**
	 * 实名注册时的用户ID
	 */
	private Long userId;
	/**
	 * 生产企业地址
	 */
	private String enterpriseAddress;
	/**
	 * 销售企业名称
	 */
	private String sellEnterprise;
	
	/**
	 * 销售企业地址
	 */
	private String sellEnterpriseAddress;
	
	/**
	 * 联系人
	 */
	private String contactPerson;
	/**
	 * 联系电话
	 */
	private String contactPhone;
	
	/**
	 * 产品数量
	 */
	private int productNum;
	/**
	 * 产品生产日期
	 */
	private Date produceDate;

	@Column(name="ENTERPRISEADDRESS",length=150,nullable=true)
	public String getEnterpriseAddress() {
		return enterpriseAddress;
	}

	public void setEnterpriseAddress(String enterpriseAddress) {
		this.enterpriseAddress = enterpriseAddress;
	}

	@Column(name="SELLENTERPRISE",length=150,nullable=true)
	public String getSellEnterprise() {
		return sellEnterprise;
	}

	public void setSellEnterprise(String sellEnterprise) {
		this.sellEnterprise = sellEnterprise;
	}

	@Column(name="SELLENTERPRISEADDRESS",length=150,nullable=true)
	public String getSellEnterpriseAddress() {
		return sellEnterpriseAddress;
	}

	public void setSellEnterpriseAddress(String sellEnterpriseAddress) {
		this.sellEnterpriseAddress = sellEnterpriseAddress;
	}

	@Column(name="CONTACTPERSON",length=20,nullable=true)
	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	@Column(name="CONTACTPHONE",length=11,nullable=true)
	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	@Column(name="PRODUCTNUM",nullable=true)
	public int getProductNum() {
		return productNum;
	}

	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}

	@Column(name="PRODUCEDATE",nullable=true)
	@JsonSerialize(using=JsonDateSerializer.class)
	@Temporal(TemporalType.DATE)
	public Date getProduceDate() {
		return produceDate;
	}

	public void setProduceDate(Date produceDate) {
		this.produceDate = produceDate;
	}

	@Column(name="USERID",nullable=true)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	
}
