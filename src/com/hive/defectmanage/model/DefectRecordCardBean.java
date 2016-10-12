/**
 * 
 */
package com.hive.defectmanage.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.hive.common.SystemCommon_Constant;

import dk.util.JsonDateTimeSerializer;

/**  
 * Filename: DefectRecordCardBean.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  YangHui
 * @version: 1.0  
 * @Create:  2014-6-10  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-6-10 下午3:20:43				YangHui 	1.0
 */
public class DefectRecordCardBean{
	//汽车类缺陷记录表
    /**
     * 主表Id
     */
	private Long mainId;
	/**
	 * 报告用户Id
	 */
	private Long reportuserid;
	/**
 	 * 报告日期
 	 */
     private Date reportdate;
	
	// 车辆信息表
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
 	 * 发送机号码
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
     
     
     
     //联系人信息表
     
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
      
      
      //缺陷描述信息表
      
      /**
   	 * 所在总成
   	 */
       private Long assembly;
       /**
   	 * 所在分总成
   	 */
       private Long subAssembly;
       /**
    	 * 三级总成
    	 */
       private Long threeLevelAssembly;
       /**
   	 * 具体部位
   	 */
       private String specificPosition;
       /**
   	 * 是否原厂配件
   	 */
       private String isOriginalAccessories = SystemCommon_Constant.SIGN_YES_1;
       /**
   	 * 缺陷描述
   	 */
       private String defectDescription;
       /**
   	 * 其他信息描述
   	 */
       private String otherDescription;
       /**
   	 * 是否存在附件
   	 */
       private String isAnnex = SystemCommon_Constant.SIGN_YES_0;
       /**
   	 * 是否发生交通事故
   	 */
       private String isTrafficAccident = SystemCommon_Constant.SIGN_YES_0;
       /**
   	 * 是否造成人员伤亡
   	 */
       private String isCasualtie = SystemCommon_Constant.SIGN_YES_0;
     
       //zhaopf add 添加名称方便展示 160302
       /**
      	 * 所在总成名称
      	 */
       private String assemblyName;
          /**
      	 * 所在分总成名称
      	 */
       private String subAssemblyName;
       /**
     	 * 所在三级总成名称
     	 *  */
       private String threeLevelAssemblyName;
       /**
     	 * 车辆品牌
     	 */
      private String carbrandName;
      /**
    	 * 车型系列
    	 */
      private String carseriesName;
      /**
   	 * 车型名称
   	 */
       private String carmodelForName;
       /**
	   	 * 具体型号
	   	 */
       private String carmodeldetailedName;
       /**
    	 * 变速器类型
    	 */
       private String carTransmissionName;
       /**
     	 * 证件类型
     	 */
       private String certificatestypeName;
       
       /**
     	 * 所属省份
     	 */
       private String cprovincecodeName;
     /**
 	 * 所属市
 	 */
       private String ccitycodeName;
     /**
 	 * 所属县区
 	 */
       private String cdistrictcodeName;
       
	public Long getMainId() {
		return mainId;
	}
	public void setMainId(Long mainId) {
		this.mainId = mainId;
	}
	public Long getReportuserid() {
		return reportuserid;
	}
	public void setReportuserid(Long reportuserid) {
		this.reportuserid = reportuserid;
	}
	

	public Date getReportdate() {
		return reportdate;
	}
	public void setReportdate(Date reportdate) {
		this.reportdate = reportdate;
	}
	public Long getCarbrand() {
		return carbrand;
	}
	public void setCarbrand(Long carbrand) {
		this.carbrand = carbrand;
	}
	public String getCarproducername() {
		return carproducername;
	}
	public void setCarproducername(String carproducername) {
		this.carproducername = carproducername;
	}
	public Long getCarmodelyear() {
		return carmodelyear;
	}
	public void setCarmodelyear(Long carmodelyear) {
		this.carmodelyear = carmodelyear;
	}
	public Long getCarseries() {
		return carseries;
	}
	public void setCarseries(Long carseries) {
		this.carseries = carseries;
	}
	public Long getCarmodelname() {
		return carmodelname;
	}
	public void setCarmodelname(Long carmodelname) {
		this.carmodelname = carmodelname;
	}
	public Long getCarmodeldetailed() {
		return carmodeldetailed;
	}
	public void setCarmodeldetailed(Long carmodeldetailed) {
		this.carmodeldetailed = carmodeldetailed;
	}
	public String getCarVin() {
		return carVin;
	}
	public void setCarVin(String carVin) {
		this.carVin = carVin;
	}
	public String getCarTransmission() {
		return carTransmission;
	}
	public void setCarTransmission(String carTransmission) {
		this.carTransmission = carTransmission;
	}
	public String getCarDischarge() {
		return carDischarge;
	}
	public void setCarDischarge(String carDischarge) {
		this.carDischarge = carDischarge;
	}
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getBuyDate() {
		return buyDate;
	}
	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}
	public Long getRunKilometers() {
		return runKilometers;
	}
	public void setRunKilometers(Long runKilometers) {
		this.runKilometers = runKilometers;
	}
	public String getBuyshopName() {
		return buyshopName;
	}
	public void setBuyshopName(String buyshopName) {
		this.buyshopName = buyshopName;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public String getProdCountry() {
		return prodCountry;
	}
	public void setProdCountry(String prodCountry) {
		this.prodCountry = prodCountry;
	}
	public String getProdModel() {
		return prodModel;
	}
	public void setProdModel(String prodModel) {
		this.prodModel = prodModel;
	}
	public String getCarownername() {
		return carownername;
	}
	public void setCarownername(String carownername) {
		this.carownername = carownername;
	}
	public String getCarownersex() {
		return carownersex;
	}
	public void setCarownersex(String carownersex) {
		this.carownersex = carownersex;
	}
	public Long getCertificatestype() {
		return certificatestype;
	}
	public void setCertificatestype(Long certificatestype) {
		this.certificatestype = certificatestype;
	}
	public String getCertificatescode() {
		return certificatescode;
	}
	public void setCertificatescode(String certificatescode) {
		this.certificatescode = certificatescode;
	}
	public String getContactsname() {
		return contactsname;
	}
	public void setContactsname(String contactsname) {
		this.contactsname = contactsname;
	}
	public String getContactsphone() {
		return contactsphone;
	}
	public void setContactsphone(String contactsphone) {
		this.contactsphone = contactsphone;
	}
	public String getContactstel() {
		return contactstel;
	}
	public void setContactstel(String contactstel) {
		this.contactstel = contactstel;
	}
	public String getContactsemail() {
		return contactsemail;
	}
	public void setContactsemail(String contactsemail) {
		this.contactsemail = contactsemail;
	}
	public String getContactsadress() {
		return contactsadress;
	}
	public void setContactsadress(String contactsadress) {
		this.contactsadress = contactsadress;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getCprovincecode() {
		return cprovincecode;
	}
	public void setCprovincecode(String cprovincecode) {
		this.cprovincecode = cprovincecode;
	}
	public String getCcitycode() {
		return ccitycode;
	}
	public void setCcitycode(String ccitycode) {
		this.ccitycode = ccitycode;
	}
	public String getCdistrictcode() {
		return cdistrictcode;
	}
	public void setCdistrictcode(String cdistrictcode) {
		this.cdistrictcode = cdistrictcode;
	}
	public Long getAssembly() {
		return assembly;
	}
	public void setAssembly(Long assembly) {
		this.assembly = assembly;
	}
	public Long getSubAssembly() {
		return subAssembly;
	}
	public void setSubAssembly(Long subAssembly) {
		this.subAssembly = subAssembly;
	}
	public Long getThreeLevelAssembly() {
		return threeLevelAssembly;
	}
	public void setThreeLevelAssembly(Long threeLevelAssembly) {
		this.threeLevelAssembly = threeLevelAssembly;
	}
	public String getSpecificPosition() {
		return specificPosition;
	}
	public void setSpecificPosition(String specificPosition) {
		this.specificPosition = specificPosition;
	}
	public String getIsOriginalAccessories() {
		return isOriginalAccessories;
	}
	public void setIsOriginalAccessories(String isOriginalAccessories) {
		this.isOriginalAccessories = isOriginalAccessories;
	}
	public String getDefectDescription() {
		return defectDescription;
	}
	public void setDefectDescription(String defectDescription) {
		this.defectDescription = defectDescription;
	}
	public String getOtherDescription() {
		return otherDescription;
	}
	public void setOtherDescription(String otherDescription) {
		this.otherDescription = otherDescription;
	}
	public String getIsAnnex() {
		return isAnnex;
	}
	public void setIsAnnex(String isAnnex) {
		this.isAnnex = isAnnex;
	}
	public String getIsTrafficAccident() {
		return isTrafficAccident;
	}
	public void setIsTrafficAccident(String isTrafficAccident) {
		this.isTrafficAccident = isTrafficAccident;
	}
	public String getIsCasualtie() {
		return isCasualtie;
	}
	public void setIsCasualtie(String isCasualtie) {
		this.isCasualtie = isCasualtie;
	}
	public String getAssemblyName() {
		return assemblyName;
	}
	public void setAssemblyName(String assemblyName) {
		this.assemblyName = assemblyName;
	}
	public String getSubAssemblyName() {
		return subAssemblyName;
	}
	public void setSubAssemblyName(String subAssemblyName) {
		this.subAssemblyName = subAssemblyName;
	}
	public String getThreeLevelAssemblyName() {
		return threeLevelAssemblyName;
	}
	public void setThreeLevelAssemblyName(String threeLevelAssemblyName) {
		this.threeLevelAssemblyName = threeLevelAssemblyName;
	}
	public String getCarbrandName() {
		return carbrandName;
	}
	public void setCarbrandName(String carbrandName) {
		this.carbrandName = carbrandName;
	}
	public String getCarseriesName() {
		return carseriesName;
	}
	public void setCarseriesName(String carseriesName) {
		this.carseriesName = carseriesName;
	}
	public String getCarmodelForName() {
		return carmodelForName;
	}
	public void setCarmodelForName(String carmodelForName) {
		this.carmodelForName = carmodelForName;
	}
	public String getCarmodeldetailedName() {
		return carmodeldetailedName;
	}
	public void setCarmodeldetailedName(String carmodeldetailedName) {
		this.carmodeldetailedName = carmodeldetailedName;
	}
	public String getCarTransmissionName() {
		return carTransmissionName;
	}
	public void setCarTransmissionName(String carTransmissionName) {
		this.carTransmissionName = carTransmissionName;
	}
	public String getCertificatestypeName() {
		return certificatestypeName;
	}
	public void setCertificatestypeName(String certificatestypeName) {
		this.certificatestypeName = certificatestypeName;
	}
	public String getCprovincecodeName() {
		return cprovincecodeName;
	}
	public void setCprovincecodeName(String cprovincecodeName) {
		this.cprovincecodeName = cprovincecodeName;
	}
	public String getCcitycodeName() {
		return ccitycodeName;
	}
	public void setCcitycodeName(String ccitycodeName) {
		this.ccitycodeName = ccitycodeName;
	}
	public String getCdistrictcodeName() {
		return cdistrictcodeName;
	}
	public void setCdistrictcodeName(String cdistrictcodeName) {
		this.cdistrictcodeName = cdistrictcodeName;
	}

}
