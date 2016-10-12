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
* Filename: DmCarBrand.java  
* Description: 汽车品牌表
* Copyright:Copyright (c)2014
* Company:  GuangFan 
* @author:  YangHui
* @version: 1.0  
* @Create:  2014-5-30  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2014-5-30 下午5:39:03				YangHui 	1.0
 */

@Entity
@Table(name="DM_CARBRAND")

public class DmCarBrand  implements java.io.Serializable {


    // Fields    

	/**
	 * 主键
	 */
     private Long id;
     /**
 	 * 品牌名称
 	 */
     private String brandname;
     /**
 	 * 车辆生产厂家
 	 */
     private String producername;
     /**
 	 * 排序值
 	 */
     private Long ordernum;
     /**
 	 * 是否有效
 	 */
     private String valid = SystemCommon_Constant.VALID_STATUS_1;


    // Constructors

    /** default constructor */
    public DmCarBrand() {
    }

    
    /** full constructor */
    public DmCarBrand(String brandname, String producername, Long ordernum, String valid) {
        this.brandname = brandname;
        this.producername = producername;
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
    
    @Column(name="BRANDNAME", nullable=false, length=100)

    public String getBrandname() {
        return this.brandname;
    }
    
    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }
    
    @Column(name="PRODUCERNAME", nullable=false, length=100)

    public String getProducername() {
        return this.producername;
    }
    
    public void setProducername(String producername) {
        this.producername = producername;
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