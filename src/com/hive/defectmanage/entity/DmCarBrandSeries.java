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
* Filename: DmCarBrandSeries.java  
* Description:  品牌系列表
* Copyright:Copyright (c)2014
* Company:  GuangFan 
* @author:  YangHui
* @version: 1.0  
* @Create:  2014-5-30  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2014-5-30 下午5:37:43				YangHui 	1.0
 */
@Entity
@Table(name="DM_CARBRAND_SERIES")

public class DmCarBrandSeries  implements java.io.Serializable {


    // Fields    

	/**
	 * 主键
	 */
     private Long id;
     /**
 	 * 品牌ID
 	 */
     private Long brandid;
     /**
 	 * 系列名称
 	 */
     private String seriesname;
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
    public DmCarBrandSeries() {
    }

	/** minimal constructor */
    public DmCarBrandSeries(Long brandid, String seriesname, Long ordernum) {
        this.brandid = brandid;
        this.seriesname = seriesname;
        this.ordernum = ordernum;
    }
    
    /** full constructor */
    public DmCarBrandSeries(Long brandid, String seriesname, Long ordernum, String valid) {
        this.brandid = brandid;
        this.seriesname = seriesname;
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
    
    @Column(name="BRANDID", nullable=false, precision=22, scale=0)

    public Long getBrandid() {
        return this.brandid;
    }
    
    public void setBrandid(Long brandid) {
        this.brandid = brandid;
    }
    
    @Column(name="SERIESNAME", nullable=false, length=100)

    public String getSeriesname() {
        return this.seriesname;
    }
    
    public void setSeriesname(String seriesname) {
        this.seriesname = seriesname;
    }
    
    @Column(name="ORDERNUM", nullable=false, precision=22, scale=0)

    public Long getOrdernum() {
        return this.ordernum;
    }
    
    public void setOrdernum(Long ordernum) {
        this.ordernum = ordernum;
    }
    
    @Column(name="VALID", length=1)

    public String getValid() {
        return this.valid;
    }
    
    public void setValid(String valid) {
        this.valid = valid;
    }
   








}