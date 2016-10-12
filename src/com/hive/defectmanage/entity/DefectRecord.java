package com.hive.defectmanage.entity;
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

import dk.util.JsonDateTimeSerializer;
/**
 * 
* Filename: DefectRecord.java  
* Description: 产品缺陷记录表（儿童玩具或其他）
* Copyright:Copyright (c)2014
* Company:  GuangFan 
* @author:  YangHui
* @version: 1.0  
* @Create:  2014-5-30  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2014-5-30 下午4:19:09				YangHui 	1.0
 */


@Entity
@Table(name="DEFECT_RECORD")
public class DefectRecord  implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2402233527821511945L;
	
	/**
	 * 主键
	 */
     private Long id;
     /**
 	 * 报告用户Id
 	 */
     private Long reportuserid;
     /**
 	 * 报告类别（1-儿童玩具类；3-其他类）
 	 */
     private String peporttype;
     /**
 	 * 报告人姓名
 	 */
     private String reportusername;
     /**
 	 * 报告人电话
 	 */
     private String reportuserphone;
     /**
 	 * 报告人邮箱
 	 */
     private String reportuseremail;
     /**
 	 * 报告人详细地址
 	 */
     private String reportadress;
     /**
      * 邮政编码
      */
     private String postCode;
     /**
 	 * 所属省份
 	 */
     private String cprovincecode;
     /**
 	 * 所属市
 	 */
     private String ccitycode;
     /**
 	 * 所属县区
 	 */
     private String cdistrictcode;
     /**
 	 * 使用者姓名
 	 */
     private String username;
     /**
 	 * 使用者性别(1-男，2-女)
 	 */
     private String usersex;
     /**
 	 * 使用者年龄
 	 */
     private Long userage;
     /**
 	 * 产品名称
 	 */
     private String prodName;
     /**
 	 * 产品型号
 	 */
     private String prodModel;
     /**
 	 * 商品生产地（1-国内，2-国外）
 	 */
     private String prodCountry;
     /**
 	 * 产品商标
 	 */
     private String prodTrademark;
     /**
 	 * 产品类别
 	 */
     private String prodType;
     /**
 	 * 购买商店名称
 	 */
     private String buyshopName;
     /**
 	 * 购买商店地址
 	 */
     private String buyshopAdress;
     /**
 	 * 缺陷描述
 	 */
     private String defectDescription;
     /**
 	 * 购买日期
 	 */
     private Date buyDate;
     /**
 	 * 报告日期
 	 */
     private Date reportdate;


    // Constructors

    /** default constructor */
    public DefectRecord() {
    }

	/** minimal constructor */
    public DefectRecord(String peporttype, String reportusername, String reportuserphone, String reportuseremail, String reportadress, String cprovincecode, String ccitycode, String cdistrictcode, String prodName, String prodCountry, String prodTrademark, String defectDescription, Date buyDate, Date reportdate) {
        this.peporttype = peporttype;
        this.reportusername = reportusername;
        this.reportuserphone = reportuserphone;
        this.reportuseremail = reportuseremail;
        this.reportadress = reportadress;
        this.cprovincecode = cprovincecode;
        this.ccitycode = ccitycode;
        this.cdistrictcode = cdistrictcode;
        this.prodName = prodName;
        this.prodCountry = prodCountry;
        this.prodTrademark = prodTrademark;
        this.defectDescription = defectDescription;
        this.buyDate = buyDate;
        this.reportdate = reportdate;
    }
    
    /** full constructor */
    public DefectRecord(Long reportuserid, String peporttype, String reportusername, String reportuserphone, String reportuseremail, String reportadress, String cprovincecode, String ccitycode, String cdistrictcode, String username, String usersex, Long userage, String prodName, String prodModel, String prodCountry, String prodTrademark, String prodType, String buyshopName, String buyshopAdress, String defectDescription, Date buyDate, Date reportdate) {
        this.reportuserid = reportuserid;
        this.peporttype = peporttype;
        this.reportusername = reportusername;
        this.reportuserphone = reportuserphone;
        this.reportuseremail = reportuseremail;
        this.reportadress = reportadress;
        this.cprovincecode = cprovincecode;
        this.ccitycode = ccitycode;
        this.cdistrictcode = cdistrictcode;
        this.username = username;
        this.usersex = usersex;
        this.userage = userage;
        this.prodName = prodName;
        this.prodModel = prodModel;
        this.prodCountry = prodCountry;
        this.prodTrademark = prodTrademark;
        this.prodType = prodType;
        this.buyshopName = buyshopName;
        this.buyshopAdress = buyshopAdress;
        this.defectDescription = defectDescription;
        this.buyDate = buyDate;
        this.reportdate = reportdate;
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
    
    @Column(name="REPORTUSERID", precision=22, scale=0)
    public Long getReportuserid() {
        return this.reportuserid;
    }
    
    public void setReportuserid(Long reportuserid) {
        this.reportuserid = reportuserid;
    }
    
    @Column(name="PEPORTTYPE", nullable=false, length=1)

    public String getPeporttype() {
        return this.peporttype;
    }
    
    public void setPeporttype(String peporttype) {
        this.peporttype = peporttype;
    }
    
    @Column(name="REPORTUSERNAME", nullable=false, length=50)

    public String getReportusername() {
        return this.reportusername;
    }
    
    public void setReportusername(String reportusername) {
        this.reportusername = reportusername;
    }
    
    @Column(name="REPORTUSERPHONE", nullable=false, length=50)

    public String getReportuserphone() {
        return this.reportuserphone;
    }
    
    public void setReportuserphone(String reportuserphone) {
        this.reportuserphone = reportuserphone;
    }
    
    @Column(name="REPORTUSEREMAIL", nullable=true, length=50)

    public String getReportuseremail() {
        return this.reportuseremail;
    }
    
    public void setReportuseremail(String reportuseremail) {
        this.reportuseremail = reportuseremail;
    }
    
    @Column(name="REPORTADRESS", nullable=false, length=200)

    public String getReportadress() {
        return this.reportadress;
    }
    
    public void setReportadress(String reportadress) {
        this.reportadress = reportadress;
    }
    
    @Column(name="POSTCODE", nullable=false, length=6)
    public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	@Column(name="CPROVINCECODE", nullable=false, length=10)

    public String getCprovincecode() {
        return this.cprovincecode;
    }
    
    public void setCprovincecode(String cprovincecode) {
        this.cprovincecode = cprovincecode;
    }
    
    @Column(name="CCITYCODE", nullable=false, length=10)

    public String getCcitycode() {
        return this.ccitycode;
    }
    
    public void setCcitycode(String ccitycode) {
        this.ccitycode = ccitycode;
    }
    
    @Column(name="CDISTRICTCODE", nullable=false, length=10)

    public String getCdistrictcode() {
        return this.cdistrictcode;
    }
    
    public void setCdistrictcode(String cdistrictcode) {
        this.cdistrictcode = cdistrictcode;
    }
    
    @Column(name="USERNAME", length=50)

    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    @Column(name="USERSEX", length=1)

    public String getUsersex() {
        return this.usersex;
    }
    
    public void setUsersex(String usersex) {
        this.usersex = usersex;
    }
    
    @Column(name="USERAGE", precision=22, scale=0)

    public Long getUserage() {
        return this.userage;
    }
    
    public void setUserage(Long userage) {
        this.userage = userage;
    }
    
    @Column(name="PROD_NAME", nullable=false, length=100)

    public String getProdName() {
        return this.prodName;
    }
    
    public void setProdName(String prodName) {
        this.prodName = prodName;
    }
    
    @Column(name="PROD_MODEL", length=100)

    public String getProdModel() {
        return this.prodModel;
    }
    
    public void setProdModel(String prodModel) {
        this.prodModel = prodModel;
    }
    
    @Column(name="PROD_COUNTRY", nullable=false, length=1)

    public String getProdCountry() {
        return this.prodCountry;
    }
    
    public void setProdCountry(String prodCountry) {
        this.prodCountry = prodCountry;
    }
    
    @Column(name="PROD_TRADEMARK", nullable=false, length=100)

    public String getProdTrademark() {
        return this.prodTrademark;
    }
    
    public void setProdTrademark(String prodTrademark) {
        this.prodTrademark = prodTrademark;
    }
    
    @Column(name="PROD_TYPE", length=100)

    public String getProdType() {
        return this.prodType;
    }
    
    public void setProdType(String prodType) {
        this.prodType = prodType;
    }
    
    @Column(name="BUYSHOP_NAME", length=100)

    public String getBuyshopName() {
        return this.buyshopName;
    }
    
    public void setBuyshopName(String buyshopName) {
        this.buyshopName = buyshopName;
    }
    
    @Column(name="BUYSHOP_ADRESS", length=200)

    public String getBuyshopAdress() {
        return this.buyshopAdress;
    }
    
    public void setBuyshopAdress(String buyshopAdress) {
        this.buyshopAdress = buyshopAdress;
    }
    
    @Column(name="DEFECT_DESCRIPTION", nullable=false, length=2000)

    public String getDefectDescription() {
        return this.defectDescription;
    }
    
    public void setDefectDescription(String defectDescription) {
        this.defectDescription = defectDescription;
    }
    @Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
    @Column(name="BUY_DATE",nullable=false)
    public Date getBuyDate() {
        return this.buyDate;
    }
    
    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="REPORTDATE", nullable=false, length=7)
    @JsonSerialize(using=JsonDateTimeSerializer.class)
    public Date getReportdate() {
        return this.reportdate;
    }
    
    public void setReportdate(Date reportdate) {
        this.reportdate = reportdate;
    }
   








}