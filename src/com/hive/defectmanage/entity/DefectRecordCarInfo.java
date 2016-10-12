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
* Filename: DefectRecordCarInfo.java  
* Description: 产品缺陷记录表-车辆信息子表
* Copyright:Copyright (c)2014
* Company:  GuangFan 
* @author:  YangHui
* @version: 1.0  
* @Create:  2014-5-30  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2014-5-30 下午5:08:50				YangHui 	1.0
 */
@Entity
@Table(name="DEFECT_RECORD_CAR_INFO")

public class DefectRecordCarInfo  implements java.io.Serializable {


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
 	 * 车辆品牌
 	 */
     private Long carbrand;
     /**
 	 * 车辆生产厂家
 	 */
     private String carproducername;
     /**
 	 * 生产年代
 	 */
     private Long carmodelyear;
     /**
 	 * 车型系列
 	 */
     private Long carseries;
     /**
 	 * 车型名称
 	 */
     private Long carmodelname;
     /**
 	 * 具体型号
 	 */
     private Long carmodeldetailed;
     /**
 	 * VIN码/车架号
 	 */
     private String carVin;
     /**
 	 * 变速器类型
 	 */
     private String carTransmission;
     /**
 	 * 发送机排量
 	 */
     private String carDischarge;
     /**
 	 * 发动机号码
 	 */
     private String carNumber;
     /**
 	 * 购买日期
 	 */
     private Date buyDate;
     /**
 	 * 行驶公里数
 	 */
     private Long runKilometers;
     /**
 	 * 购买店铺
 	 */
     private String buyshopName;
     /**
 	 * 车牌号
 	 */
     private String plateNumber;
     /**
 	 * 国产,进口
 	 */
     private String prodCountry;
     /**
 	 * 自主、合资、进口
 	 */
     private String prodModel;

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
    
    @Column(name="CARBRAND", nullable=false, precision=22, scale=0)

    public Long getCarbrand() {
        return this.carbrand;
    }
    
    public void setCarbrand(Long carbrand) {
        this.carbrand = carbrand;
    }
    
    @Column(name="CARPRODUCERNAME", nullable=false, length=200)

    public String getCarproducername() {
        return this.carproducername;
    }
    
    public void setCarproducername(String carproducername) {
        this.carproducername = carproducername;
    }
    
    @Column(name="CARMODELYEAR", nullable=false, precision=22, scale=0)

    public Long getCarmodelyear() {
        return this.carmodelyear;
    }
    
    public void setCarmodelyear(Long carmodelyear) {
        this.carmodelyear = carmodelyear;
    }
    
    @Column(name="CARSERIES", nullable=false, precision=22, scale=0)

    public Long getCarseries() {
        return this.carseries;
    }
    
    public void setCarseries(Long carseries) {
        this.carseries = carseries;
    }
    
    @Column(name="CARMODELNAME", nullable=false, precision=22, scale=0)

    public Long getCarmodelname() {
        return this.carmodelname;
    }
    
    public void setCarmodelname(Long carmodelname) {
        this.carmodelname = carmodelname;
    }
    
    @Column(name="CARMODELDETAILED", precision=22, scale=0)

    public Long getCarmodeldetailed() {
        return this.carmodeldetailed;
    }
    
    public void setCarmodeldetailed(Long carmodeldetailed) {
        this.carmodeldetailed = carmodeldetailed;
    }
    
    @Column(name="CAR_VIN", nullable=false, length=50)

    public String getCarVin() {
        return this.carVin;
    }
    
    public void setCarVin(String carVin) {
        this.carVin = carVin;
    }
    
    @Column(name="CAR_TRANSMISSION", length=50)

    public String getCarTransmission() {
        return this.carTransmission;
    }
    
    public void setCarTransmission(String carTransmission) {
        this.carTransmission = carTransmission;
    }
    
    @Column(name="CAR_DISCHARGE", precision=22, scale=0)

    public String getCarDischarge() {
        return this.carDischarge;
    }
    
    public void setCarDischarge(String carDischarge) {
        this.carDischarge = carDischarge;
    }
    
    @Column(name="CAR_NUMBER", length=50)

    public String getCarNumber() {
        return this.carNumber;
    }
    
    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }
    @Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonDateTimeSerializer.class)
    @Column(name="BUY_DATE", nullable=false, length=7)
    public Date getBuyDate() {
        return this.buyDate;
    }
    
    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }
    
    @Column(name="RUN_KILOMETERS", precision=22, scale=0)

    public Long getRunKilometers() {
        return this.runKilometers;
    }
    
    public void setRunKilometers(Long runKilometers) {
        this.runKilometers = runKilometers;
    }
    
    @Column(name="BUYSHOP_NAME", length=200)

    public String getBuyshopName() {
        return this.buyshopName;
    }
    
    public void setBuyshopName(String buyshopName) {
        this.buyshopName = buyshopName;
    }
    
    @Column(name="PLATE_NUMBER", length=20)

    public String getPlateNumber() {
        return this.plateNumber;
    }
    
    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }
    
    @Column(name="PROD_COUNTRY", nullable=false, length=1)

    public String getProdCountry() {
        return this.prodCountry;
    }
    
    public void setProdCountry(String prodCountry) {
        this.prodCountry = prodCountry;
    }
    
    @Column(name="PROD_MODEL", nullable=false, length=1)

    public String getProdModel() {
        return this.prodModel;
    }
    
    public void setProdModel(String prodModel) {
        this.prodModel = prodModel;
    }
   








}