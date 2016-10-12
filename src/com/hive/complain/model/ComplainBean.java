package com.hive.complain.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Annex;

/**
 * 
* Filename: ComplainBean.java  
* Description:
* Copyright:Copyright (c)2013
* Company:  GuangFan 
* @author:  yanghui
* @version: 1.0  
* @Create:  2013-7-24  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2013-7-24 下午3:12:52				yanghui 	1.0
 */
public class ComplainBean {

	private Long id;
	/**
	 * 投诉人姓名
	 */
	private String name;

	/**
	 * 身份证号
	 */
	private String cardNo;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 联系电话
	 */
	private String phone;
	/**
	 * 职业
	 */
	private String occupation;
	/**
	 * 工作单位
	 */
	private String workUnit;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 省份代码
	 */
	private String province;
	/**
	 * 城市代码
	 */
	private String city;
	/**
	 * 县区代码
	 */
	private String district;
	/**
	 * 投诉人详细地址
	 */
	private String address;
	/**
	 * 邮政编码
	 */
	private String postCode;
	
	
	/**
	 * 投诉标题
	 */
	private String title;
	/**
	 *  行业ID
	 */
	private String tradeId;
	/**
	 * 行业名称
	 */
	private String tradeName;
	/**
	 * 生产企业名称
	 */
	private String enterprise;
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
	 * 产品类型
	 */
	private String productType;
	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 产品品牌
	 */
	private String productBrand;
	/**
	 * 产品型号
	 */
	private String productModel;
	/**
	 * 投诉类型
	 */
	private String comType;
	/**
	 * 相关凭证
	 */
	private String proof;
	/**
	 * 购买日期
	 */
	
	private Date buyDate;
	/**
	 * 产品数量
	 */
	private int productNum;
	/**
	 * 购买价格
	 */
	private BigDecimal buyPrice;
	/**
	 * 产品生产日期
	 */
	private Date produceDate;
	/**
	 * 生产厂家（产地）
	 */
	private String producer;
	/**
	 * 投诉内容
	 */
	private String content;
	
	/**需要后台处理的字段    start */
	private String flag = SystemCommon_Constant.SIGN_YES_1;
	/**
	 * 投诉处理状态
	 */
	private String dealState = SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_00;
	/**
	 * 投诉日期
	 */
	private Date comDate;
	
	private String searchCode;
	
	private Long complainId;
	/**
	 * 实名投诉时的用户ID
	 */
	private Long userId;
	
	/**需要后台处理的字段    end */
	/**
	 * 期望处理结果
	 */
	private String wishResult;
	
	/**
	 * 验证码
	 */
	private String vdcode;
	/**
	 * 是否有效（默认1-有效，0-无效）
	 */
	private String isValid = SystemCommon_Constant.VALID_STATUS_1;
	/**
	 * 点击率 默认为1
	 */
	private String clickRate = "1";
	/**
	 * 附件列表
	 */
	private List<Annex> annexList;
	
	private String firstName;
	private String lastName;
	
	private String reply;
	/**
	 * 附件的路径
	 */
	private String fileurl;
	/**
	 * 附件名称
	 */
	private String fileName;
	
	private String group;
	
	
	private String username;
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	//用户后台管理  投诉处理步骤的统一操作
	private String role; //岗位角色
	private String type; //针对审核岗和办结岗下有两种处理方式 而设置的类型
	

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(String enterprise) {
		this.enterprise = enterprise;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getComplainId() {
		return complainId;
	}

	public void setComplainId(Long complainId) {
		this.complainId = complainId;
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

	

	public Date getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}


	

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

	public String getVdcode() {
		return vdcode;
	}

	public void setVdcode(String vdcode) {
		this.vdcode = vdcode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getFileurl() {
		return fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getEnterpriseAddress() {
		return enterpriseAddress;
	}

	public void setEnterpriseAddress(String enterpriseAddress) {
		this.enterpriseAddress = enterpriseAddress;
	}

	public String getSellEnterprise() {
		return sellEnterprise;
	}

	public void setSellEnterprise(String sellEnterprise) {
		this.sellEnterprise = sellEnterprise;
	}

	public String getSellEnterpriseAddress() {
		return sellEnterpriseAddress;
	}

	public void setSellEnterpriseAddress(String sellEnterpriseAddress) {
		this.sellEnterpriseAddress = sellEnterpriseAddress;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public int getProductNum() {
		return productNum;
	}

	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}

	public Date getProduceDate() {
		return produceDate;
	}

	public void setProduceDate(Date produceDate) {
		this.produceDate = produceDate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<Annex> getAnnexList() {
		return annexList;
	}

	public void setAnnexList(List<Annex> annexList) {
		this.annexList = annexList;
	}
	
	
	
	
}
