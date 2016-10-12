package com.hive.defectmanage.entity;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
* Filename: DefectRecordCarContacts.java  
* Description: 产品缺陷记录表-联系人子表
* Copyright:Copyright (c)2014
* Company:  GuangFan 
* @author:  YangHui
* @version: 1.0  
* @Create:  2014-5-30  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2014-5-30 下午4:56:03				YangHui 	1.0
 */
@Entity
@Table(name="DEFECT_RECORD_CAR_CONTACTS")

public class DefectRecordCarContacts  implements java.io.Serializable {


    // Fields    

	/**
	 * 主键
	 */
     private Long id;
     /**
 	 * 缺陷登记表主ID
 	 */
     private Long defectId;
     /**
 	 * 车主姓名
 	 */
     private String carownername;
     /**
 	 * 车主性别
 	 */
     private String carownersex;
     /**
 	 * 证件类型
 	 */
     private Long certificatestype;
     /**
 	 * 证件号码
 	 */
     private String certificatescode;
     /**
 	 * 联系人名
 	 */
     private String contactsname;
     /**
 	 * 联系手机
 	 */
     private String contactsphone;
     /**
 	 * 联系人电话
 	 */
     private String contactstel;
     /**
 	 * 联系人邮箱
 	 */
     private String contactsemail;
     /**
 	 * 联系人详细地址
 	 */
     private String contactsadress;
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


    // Constructors

    /** default constructor */
    public DefectRecordCarContacts() {
    }

	/** minimal constructor */
    public DefectRecordCarContacts(Long defectId, String carownername, String carownersex, Long certificatestype, String certificatescode, String contactsname, String contactsphone) {
        this.defectId = defectId;
        this.carownername = carownername;
        this.carownersex = carownersex;
        this.certificatestype = certificatestype;
        this.certificatescode = certificatescode;
        this.contactsname = contactsname;
        this.contactsphone = contactsphone;
    }
    
    /** full constructor */
    public DefectRecordCarContacts(Long defectId, String carownername, String carownersex, Long certificatestype, String certificatescode, String contactsname, String contactsphone, String contactstel, String contactsemail, String contactsadress, String cprovincecode, String ccitycode, String cdistrictcode) {
        this.defectId = defectId;
        this.carownername = carownername;
        this.carownersex = carownersex;
        this.certificatestype = certificatestype;
        this.certificatescode = certificatescode;
        this.contactsname = contactsname;
        this.contactsphone = contactsphone;
        this.contactstel = contactstel;
        this.contactsemail = contactsemail;
        this.contactsadress = contactsadress;
        this.cprovincecode = cprovincecode;
        this.ccitycode = ccitycode;
        this.cdistrictcode = cdistrictcode;
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
    
    @Column(name="DEFECT_ID", nullable=false, precision=22, scale=0)

    public Long getDefectId() {
        return this.defectId;
    }
    
    public void setDefectId(Long defectId) {
        this.defectId = defectId;
    }
    
    @Column(name="CAROWNERNAME", nullable=false, length=50)

    public String getCarownername() {
        return this.carownername;
    }
    
    public void setCarownername(String carownername) {
        this.carownername = carownername;
    }
    
    @Column(name="CAROWNERSEX", nullable=false, length=1)

    public String getCarownersex() {
        return this.carownersex;
    }
    
    public void setCarownersex(String carownersex) {
        this.carownersex = carownersex;
    }
    
    @Column(name="CERTIFICATESTYPE", nullable=false, precision=22, scale=0)

    public Long getCertificatestype() {
        return this.certificatestype;
    }
    
    public void setCertificatestype(Long certificatestype) {
        this.certificatestype = certificatestype;
    }
    
    @Column(name="CERTIFICATESCODE", nullable=false, length=50)

    public String getCertificatescode() {
        return this.certificatescode;
    }
    
    public void setCertificatescode(String certificatescode) {
        this.certificatescode = certificatescode;
    }
    
    @Column(name="CONTACTSNAME", nullable=false, length=50)

    public String getContactsname() {
        return this.contactsname;
    }
    
    public void setContactsname(String contactsname) {
        this.contactsname = contactsname;
    }
    
    @Column(name="CONTACTSPHONE", nullable=false, length=20)

    public String getContactsphone() {
        return this.contactsphone;
    }
    
    public void setContactsphone(String contactsphone) {
        this.contactsphone = contactsphone;
    }
    
    @Column(name="CONTACTSTEL", length=20)

    public String getContactstel() {
        return this.contactstel;
    }
    
    public void setContactstel(String contactstel) {
        this.contactstel = contactstel;
    }
    
    @Column(name="CONTACTSEMAIL", length=100)

    public String getContactsemail() {
        return this.contactsemail;
    }
    
    public void setContactsemail(String contactsemail) {
        this.contactsemail = contactsemail;
    }
    
    @Column(name="CONTACTSADRESS", length=400)

    public String getContactsadress() {
        return this.contactsadress;
    }
    
    public void setContactsadress(String contactsadress) {
        this.contactsadress = contactsadress;
    }
    
    @Column(name="POSTCODE", length=400)
    public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	@Column(name="CPROVINCECODE", length=20)

    public String getCprovincecode() {
        return this.cprovincecode;
    }
    
    public void setCprovincecode(String cprovincecode) {
        this.cprovincecode = cprovincecode;
    }
    
    @Column(name="CCITYCODE", length=20)

    public String getCcitycode() {
        return this.ccitycode;
    }
    
    public void setCcitycode(String ccitycode) {
        this.ccitycode = ccitycode;
    }
    
    @Column(name="CDISTRICTCODE", length=20)

    public String getCdistrictcode() {
        return this.cdistrictcode;
    }
    
    public void setCdistrictcode(String cdistrictcode) {
        this.cdistrictcode = cdistrictcode;
    }
   








}