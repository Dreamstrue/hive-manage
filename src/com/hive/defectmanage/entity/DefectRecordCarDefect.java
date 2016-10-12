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
* Filename: DefectRecordCarDefect.java  
* Description:产品缺陷记录表-缺陷详情子表
* Copyright:Copyright (c)2014
* Company:  GuangFan 
* @author:  YangHui
* @version: 1.0  
* @Create:  2014-5-30  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2014-5-30 下午5:00:09				YangHui 	1.0
 */
@Entity
@Table(name="DEFECT_RECORD_CAR_DEFECT")

public class DefectRecordCarDefect  implements java.io.Serializable {


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


    // Constructors

    /** default constructor */
    public DefectRecordCarDefect() {
    }

	/** minimal constructor */
    public DefectRecordCarDefect(Long defectId, Long assembly, Long subAssembly, String isOriginalAccessories, String defectDescription, String isAnnex, String isTrafficAccident, String isCasualtie) {
        this.defectId = defectId;
        this.assembly = assembly;
        this.subAssembly = subAssembly;
        this.isOriginalAccessories = isOriginalAccessories;
        this.defectDescription = defectDescription;
        this.isAnnex = isAnnex;
        this.isTrafficAccident = isTrafficAccident;
        this.isCasualtie = isCasualtie;
    }
    
    /** full constructor */
    public DefectRecordCarDefect(Long defectId, Long assembly, Long subAssembly, Long threeLevelAssembly, String specificPosition, String isOriginalAccessories, String defectDescription, String otherDescription, String isAnnex, String isTrafficAccident, String isCasualtie) {
        this.defectId = defectId;
        this.assembly = assembly;
        this.subAssembly = subAssembly;
        this.threeLevelAssembly = threeLevelAssembly;
        this.specificPosition = specificPosition;
        this.isOriginalAccessories = isOriginalAccessories;
        this.defectDescription = defectDescription;
        this.otherDescription = otherDescription;
        this.isAnnex = isAnnex;
        this.isTrafficAccident = isTrafficAccident;
        this.isCasualtie = isCasualtie;
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
    
    @Column(name="ASSEMBLY", nullable=false, precision=22, scale=0)

    public Long getAssembly() {
        return this.assembly;
    }
    
    public void setAssembly(Long assembly) {
        this.assembly = assembly;
    }
    
    @Column(name="SUB_ASSEMBLY", nullable=false, precision=22, scale=0)

    public Long getSubAssembly() {
        return this.subAssembly;
    }
    
    public void setSubAssembly(Long subAssembly) {
        this.subAssembly = subAssembly;
    }
    
    @Column(name="THREE_LEVEL_ASSEMBLY", precision=22, scale=0)

    public Long getThreeLevelAssembly() {
        return this.threeLevelAssembly;
    }
    
    public void setThreeLevelAssembly(Long threeLevelAssembly) {
        this.threeLevelAssembly = threeLevelAssembly;
    }
    
    @Column(name="SPECIFIC_POSITION", length=400)

    public String getSpecificPosition() {
        return this.specificPosition;
    }
    
    public void setSpecificPosition(String specificPosition) {
        this.specificPosition = specificPosition;
    }
    
    @Column(name="IS_ORIGINAL_ACCESSORIES", nullable=false, length=1)

    public String getIsOriginalAccessories() {
        return this.isOriginalAccessories;
    }
    
    public void setIsOriginalAccessories(String isOriginalAccessories) {
        this.isOriginalAccessories = isOriginalAccessories;
    }
    
    @Column(name="DEFECT_DESCRIPTION", nullable=false, length=2000)

    public String getDefectDescription() {
        return this.defectDescription;
    }
    
    public void setDefectDescription(String defectDescription) {
        this.defectDescription = defectDescription;
    }
    
    @Column(name="OTHER_DESCRIPTION", length=2000)

    public String getOtherDescription() {
        return this.otherDescription;
    }
    
    public void setOtherDescription(String otherDescription) {
        this.otherDescription = otherDescription;
    }
    
    @Column(name="IS_ANNEX", nullable=false, length=1)

    public String getIsAnnex() {
        return this.isAnnex;
    }
    
    public void setIsAnnex(String isAnnex) {
        this.isAnnex = isAnnex;
    }
    
    @Column(name="IS_TRAFFIC_ACCIDENT", nullable=false, length=1)

    public String getIsTrafficAccident() {
        return this.isTrafficAccident;
    }
    
    public void setIsTrafficAccident(String isTrafficAccident) {
        this.isTrafficAccident = isTrafficAccident;
    }
    
    @Column(name="IS_CASUALTIE", nullable=false, length=1)

    public String getIsCasualtie() {
        return this.isCasualtie;
    }
    
    public void setIsCasualtie(String isCasualtie) {
        this.isCasualtie = isCasualtie;
    }
   








}