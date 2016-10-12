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

import com.hive.common.SystemCommon_Constant;


/**
 * 
* Filename: DmCertificateStype.java  
* Description:  证件类型
* Copyright:Copyright (c)2014
* Company:  GuangFan 
* @author:  YangHui
* @version: 1.0  
* @Create:  2014-5-30  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2014-5-30 下午5:44:38				YangHui 	1.0
 */
@Entity
@Table(name="DM_CERTIFICATESTYPE")

public class DmCertificateStype  implements java.io.Serializable {


    // Fields    

	/**
	 * 主键
	 */
     private Long id;
     /**
 	 * 证件名称
 	 */
     private String certificatesname;
     /**
 	 * 排序
 	 */
     private Long ordernum;
     /**
 	 * 是否有效
 	 */
     private String valid = SystemCommon_Constant.VALID_STATUS_1;


    // Constructors

    /** default constructor */
    public DmCertificateStype() {
    }

    
    /** full constructor */
    public DmCertificateStype(String certificatesname, Long ordernum, String valid) {
        this.certificatesname = certificatesname;
        this.ordernum = ordernum;
        this.valid = valid;
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
    
    @Column(name="CERTIFICATESNAME", nullable=false, length=50)

    public String getCertificatesname() {
        return this.certificatesname;
    }
    
    public void setCertificatesname(String certificatesname) {
        this.certificatesname = certificatesname;
    }
    
    @Column(name="ORDERNUM", nullable=false, precision=22, scale=0)

    public Long getOrdernum() {
        return this.ordernum;
    }
    
    public void setOrdernum(Long ordernum) {
        this.ordernum = ordernum;
    }
    
    @Column(name="VALID", nullable=false, length=1)

    public String getValid() {
        return this.valid;
    }
    
    public void setValid(String valid) {
        this.valid = valid;
    }
   








}