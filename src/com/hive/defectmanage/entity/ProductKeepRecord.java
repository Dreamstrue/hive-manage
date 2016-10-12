package com.hive.defectmanage.entity;
// default package

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import dk.util.JsonDateTimeSerializer;


/**
 * 
* Filename: ProductKeepRecord.java  
* Description: 产品标准备案表
* Copyright:Copyright (c)2014
* Company:  GuangFan 
* @author:  YangHui
* @version: 1.0  
* @Create:  2014-5-30  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2014-5-30 下午5:49:19				YangHui 	1.0
 */
@Entity
@Table(name="PRODUCT_KEEP_RECORD")

public class ProductKeepRecord  implements java.io.Serializable {


    // Fields    

	/**
	 * 主键
	 */
     private Long id;
     /**
 	 * 备案用户ID（必须是企业用户登录后备案）
 	 */
     private Long reportuserid;
     /**
 	 * 单位名称
 	 */
     private String entName;
     /**
 	 * 组织机构代码
 	 */
     private String entOracode;
     /**
 	 * 法定代表人姓名
 	 */
     private String entCorporate;
     /**
 	 * 法人注册地址
 	 */
     private String entAddress;
     /**
 	 * 联系人
 	 */
     private String entContacts;
     /**
 	 * 联系电话
 	 */
     private String entPhone;
     /**
 	 * 邮箱
 	 */
     private String entEmail;
     /**
 	 * 邮政编码
 	 */
     private String entPostalCode;
     /**
 	 * 经济类型
 	 */
     private String entEconomicType;
     /**
 	 * 所属行业
 	 */
     private String entIndustrycode;
     /**
 	 * 企业标准名称
 	 */
     private String standardName;
     /**
 	 * 企业标准编号
 	 */
     private String standardCode;
     /**
 	 * 原备案号
 	 */
     private String oldKeeprecordCode;
     /**
 	 * 代替标准号
 	 */
     private String replaceStandardCode;
     /**
 	 * 产品名称
 	 */
     private String productName;
     /**
 	 * 标准文献分类号
 	 */
     private String standardLiteratureCode;
     /**
 	 * 采用的国际标准编号及名称
 	 */
     private String internationalCodename;
     /**
 	 * 采标程度 （等同、修改）
 	 */
     private String adoptingDegree;
     /**
 	 * 产品生产者名称
 	 */
     private String producerName;
     /**
 	 * 产品生产者地址
 	 */
     private String produceAddress;
     /**
 	 * 产品生产者联系人
 	 */
     private String produceContacts;
     /**
 	 * 产品生产者联系电话
 	 */
     private String producePhone;
     /**
 	 * 企业上级主管部门意见
 	 */
     private String superiorEntSuggestion;
     /**
 	 * 备案时间
 	 */
     private Date keepRecordDate;


    // Constructors

    /** default constructor */
    public ProductKeepRecord() {
    }

	/** minimal constructor */
    public ProductKeepRecord(Long reportuserid, String entName, String entOracode, String entCorporate, String entAddress, String entContacts, String entPhone, String entEmail, String entPostalCode, String entEconomicType, String entIndustrycode, String standardName, String standardCode, String productName, String standardLiteratureCode) {
        this.reportuserid = reportuserid;
        this.entName = entName;
        this.entOracode = entOracode;
        this.entCorporate = entCorporate;
        this.entAddress = entAddress;
        this.entContacts = entContacts;
        this.entPhone = entPhone;
        this.entEmail = entEmail;
        this.entPostalCode = entPostalCode;
        this.entEconomicType = entEconomicType;
        this.entIndustrycode = entIndustrycode;
        this.standardName = standardName;
        this.standardCode = standardCode;
        this.productName = productName;
        this.standardLiteratureCode = standardLiteratureCode;
    }
    
    /** full constructor */
    public ProductKeepRecord(Long reportuserid, String entName, String entOracode, String entCorporate, String entAddress, String entContacts, String entPhone, String entEmail, String entPostalCode, String entEconomicType, String entIndustrycode, String standardName, String standardCode, String oldKeeprecordCode, String replaceStandardCode, String productName, String standardLiteratureCode, String internationalCodename, String adoptingDegree, String producerName, String produceAddress, String produceContacts, String producePhone, String superiorEntSuggestion, Date keepRecordDate) {
        this.reportuserid = reportuserid;
        this.entName = entName;
        this.entOracode = entOracode;
        this.entCorporate = entCorporate;
        this.entAddress = entAddress;
        this.entContacts = entContacts;
        this.entPhone = entPhone;
        this.entEmail = entEmail;
        this.entPostalCode = entPostalCode;
        this.entEconomicType = entEconomicType;
        this.entIndustrycode = entIndustrycode;
        this.standardName = standardName;
        this.standardCode = standardCode;
        this.oldKeeprecordCode = oldKeeprecordCode;
        this.replaceStandardCode = replaceStandardCode;
        this.productName = productName;
        this.standardLiteratureCode = standardLiteratureCode;
        this.internationalCodename = internationalCodename;
        this.adoptingDegree = adoptingDegree;
        this.producerName = producerName;
        this.produceAddress = produceAddress;
        this.produceContacts = produceContacts;
        this.producePhone = producePhone;
        this.superiorEntSuggestion = superiorEntSuggestion;
        this.keepRecordDate = keepRecordDate;
    }

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "ID", unique = true, nullable = true)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="REPORTUSERID", nullable=false, precision=22, scale=0)

    public Long getReportuserid() {
        return this.reportuserid;
    }
    
    public void setReportuserid(Long reportuserid) {
        this.reportuserid = reportuserid;
    }
    
    @Column(name="ENT_NAME", nullable=false, length=100)

    public String getEntName() {
        return this.entName;
    }
    
    public void setEntName(String entName) {
        this.entName = entName;
    }
    
    @Column(name="ENT_ORACODE", nullable=false, length=30)

    public String getEntOracode() {
        return this.entOracode;
    }
    
    public void setEntOracode(String entOracode) {
        this.entOracode = entOracode;
    }
    
    @Column(name="ENT_CORPORATE", nullable=false, length=50)

    public String getEntCorporate() {
        return this.entCorporate;
    }
    
    public void setEntCorporate(String entCorporate) {
        this.entCorporate = entCorporate;
    }
    
    @Column(name="ENT_ADDRESS", nullable=false, length=200)

    public String getEntAddress() {
        return this.entAddress;
    }
    
    public void setEntAddress(String entAddress) {
        this.entAddress = entAddress;
    }
    
    @Column(name="ENT_CONTACTS", nullable=false, length=50)

    public String getEntContacts() {
        return this.entContacts;
    }
    
    public void setEntContacts(String entContacts) {
        this.entContacts = entContacts;
    }
    
    @Column(name="ENT_PHONE", nullable=false, length=20)

    public String getEntPhone() {
        return this.entPhone;
    }
    
    public void setEntPhone(String entPhone) {
        this.entPhone = entPhone;
    }
    
    @Column(name="ENT_EMAIL", nullable=false, length=50)

    public String getEntEmail() {
        return this.entEmail;
    }
    
    public void setEntEmail(String entEmail) {
        this.entEmail = entEmail;
    }
    
    @Column(name="ENT_POSTAL_CODE", nullable=false, length=6)

    public String getEntPostalCode() {
        return this.entPostalCode;
    }
    
    public void setEntPostalCode(String entPostalCode) {
        this.entPostalCode = entPostalCode;
    }
    
    @Column(name="ENT_ECONOMIC_TYPE", nullable=false, length=20)

    public String getEntEconomicType() {
        return this.entEconomicType;
    }
    
    public void setEntEconomicType(String entEconomicType) {
        this.entEconomicType = entEconomicType;
    }
    
    @Column(name="ENT_INDUSTRYCODE", nullable=false, length=20)

    public String getEntIndustrycode() {
        return this.entIndustrycode;
    }
    
    public void setEntIndustrycode(String entIndustrycode) {
        this.entIndustrycode = entIndustrycode;
    }
    
    @Column(name="STANDARD_NAME", nullable=false, length=100)

    public String getStandardName() {
        return this.standardName;
    }
    
    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }
    
    @Column(name="STANDARD_CODE", nullable=false, length=50)

    public String getStandardCode() {
        return this.standardCode;
    }
    
    public void setStandardCode(String standardCode) {
        this.standardCode = standardCode;
    }
    
    @Column(name="OLD_KEEPRECORD_CODE", length=50)

    public String getOldKeeprecordCode() {
        return this.oldKeeprecordCode;
    }
    
    public void setOldKeeprecordCode(String oldKeeprecordCode) {
        this.oldKeeprecordCode = oldKeeprecordCode;
    }
    
    @Column(name="REPLACE_STANDARD_CODE", length=50)

    public String getReplaceStandardCode() {
        return this.replaceStandardCode;
    }
    
    public void setReplaceStandardCode(String replaceStandardCode) {
        this.replaceStandardCode = replaceStandardCode;
    }
    
    @Column(name="PRODUCT_NAME", nullable=false, length=100)

    public String getProductName() {
        return this.productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    @Column(name="STANDARD_LITERATURE_CODE", nullable=false, length=50)

    public String getStandardLiteratureCode() {
        return this.standardLiteratureCode;
    }
    
    public void setStandardLiteratureCode(String standardLiteratureCode) {
        this.standardLiteratureCode = standardLiteratureCode;
    }
    
    @Column(name="INTERNATIONAL_CODENAME", length=100)

    public String getInternationalCodename() {
        return this.internationalCodename;
    }
    
    public void setInternationalCodename(String internationalCodename) {
        this.internationalCodename = internationalCodename;
    }
    
    @Column(name="ADOPTING_DEGREE", length=1)

    public String getAdoptingDegree() {
        return this.adoptingDegree;
    }
    
    public void setAdoptingDegree(String adoptingDegree) {
        this.adoptingDegree = adoptingDegree;
    }
    
    @Column(name="PRODUCER_NAME", length=50)

    public String getProducerName() {
        return this.producerName;
    }
    
    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }
    
    @Column(name="PRODUCE_ADDRESS", length=200)

    public String getProduceAddress() {
        return this.produceAddress;
    }
    
    public void setProduceAddress(String produceAddress) {
        this.produceAddress = produceAddress;
    }
    
    @Column(name="PRODUCE_CONTACTS", length=50)

    public String getProduceContacts() {
        return this.produceContacts;
    }
    
    public void setProduceContacts(String produceContacts) {
        this.produceContacts = produceContacts;
    }
    
    @Column(name="PRODUCE_PHONE", length=20)

    public String getProducePhone() {
        return this.producePhone;
    }
    
    public void setProducePhone(String producePhone) {
        this.producePhone = producePhone;
    }
    
    @Column(name="SUPERIOR_ENT_SUGGESTION", length=200)

    public String getSuperiorEntSuggestion() {
        return this.superiorEntSuggestion;
    }
    
    public void setSuperiorEntSuggestion(String superiorEntSuggestion) {
        this.superiorEntSuggestion = superiorEntSuggestion;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonDateTimeSerializer.class)
    @Column(name="KEEP_RECORD_DATE", length=7)
    public Date getKeepRecordDate() {
        return this.keepRecordDate;
    }
    
    public void setKeepRecordDate(Date keepRecordDate) {
        this.keepRecordDate = keepRecordDate;
    }
   








}