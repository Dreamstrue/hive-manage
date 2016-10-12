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
* Filename: DmThreeLevelAssembly.java  
* Description:  三级总成代码表
* Copyright:Copyright (c)2014
* Company:  GuangFan 
* @author:  YangHui
* @version: 1.0  
* @Create:  2014-5-30  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2014-5-30 下午5:42:13				YangHui 	1.0
 */
@Entity
@Table(name="DM_THREE_LEVEL_ASSEMBLY")

public class DmThreeLevelAssembly  implements java.io.Serializable {


    // Fields    

	/**
	 * 主键
	 */
     private Long id;
     /**
 	 * 总成ID
 	 */
     private Long assemblyId;
     /**
 	 * 分总成ID
 	 */
     private Long subAssemblyId;
     /**
 	 * 三级总成名称
 	 */
     private String threeLevelAssemblyName;
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
    public DmThreeLevelAssembly() {
    }

    
    /** full constructor */
    public DmThreeLevelAssembly(Long assemblyId, Long subAssemblyId, String threeLevelAssemblyName, Long ordernum, String valid) {
        this.assemblyId = assemblyId;
        this.subAssemblyId = subAssemblyId;
        this.threeLevelAssemblyName = threeLevelAssemblyName;
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
    
    @Column(name="ASSEMBLY_ID", nullable=false, precision=22, scale=0)

    public Long getAssemblyId() {
        return this.assemblyId;
    }
    
    public void setAssemblyId(Long assemblyId) {
        this.assemblyId = assemblyId;
    }
    
    @Column(name="SUB_ASSEMBLY_ID", nullable=false, precision=22, scale=0)

    public Long getSubAssemblyId() {
        return this.subAssemblyId;
    }
    
    public void setSubAssemblyId(Long subAssemblyId) {
        this.subAssemblyId = subAssemblyId;
    }
    
    @Column(name="THREE_LEVEL_ASSEMBLY_NAME", nullable=false, length=100)

    public String getThreeLevelAssemblyName() {
        return this.threeLevelAssemblyName;
    }
    
    public void setThreeLevelAssemblyName(String threeLevelAssemblyName) {
        this.threeLevelAssemblyName = threeLevelAssemblyName;
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