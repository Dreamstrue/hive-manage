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
* Filename: DmCarBrandSeriesModel.java  
* Description: 车型名称表
* Copyright:Copyright (c)2014
* Company:  GuangFan 
* @author:  YangHui
* @version: 1.0  
* @Create:  2014-5-30  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2014-5-30 下午5:47:11				YangHui 	1.0
 */
@Entity
@Table(name="DM_CARBRAND_SERIES_MODEL")

public class DmCarBrandSeriesModel  implements java.io.Serializable {


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
 	 * 系列ID
 	 */
     private Long seriesid;
     /**
 	 * 车型名称
 	 */
     private String modelname;
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
    public DmCarBrandSeriesModel() {
    }

	/** minimal constructor */
    public DmCarBrandSeriesModel(Long brandid, Long seriesid, String modelname, Long ordernum) {
        this.brandid = brandid;
        this.seriesid = seriesid;
        this.modelname = modelname;
        this.ordernum = ordernum;
    }
    
    /** full constructor */
    public DmCarBrandSeriesModel(Long brandid, Long seriesid, String modelname, Long ordernum, String valid) {
        this.brandid = brandid;
        this.seriesid = seriesid;
        this.modelname = modelname;
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
    
    @Column(name="SERIESID", nullable=false, precision=22, scale=0)

    public Long getSeriesid() {
        return this.seriesid;
    }
    
    public void setSeriesid(Long seriesid) {
        this.seriesid = seriesid;
    }
    
    @Column(name="MODELNAME", nullable=false, length=100)

    public String getModelname() {
        return this.modelname;
    }
    
    public void setModelname(String modelname) {
        this.modelname = modelname;
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