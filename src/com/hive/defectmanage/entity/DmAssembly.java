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
* Filename: DmAssembly.java  
* Description:  总成代码表
* Copyright:Copyright (c)2014
* Company:  GuangFan 
* @author:  YangHui
* @version: 1.0  
* @Create:  2014-5-30  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2014-5-30 下午5:35:56				YangHui 	1.0
 */
@Entity
@Table(name="DM_ASSEMBLY")

public class DmAssembly  implements java.io.Serializable {


    // Fields    
	/**
	 * 主键
	 */
     private Long id;
     /**
 	 * 总成名称
 	 */
     private String assemblyName;
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
    public DmAssembly() {
    }

    
    /** full constructor */
    public DmAssembly(String assemblyName, Long ordernum, String valid) {
        this.assemblyName = assemblyName;
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
    
    @Column(name="ASSEMBLY_NAME", nullable=false, length=100)

    public String getAssemblyName() {
        return this.assemblyName;
    }
    
    public void setAssemblyName(String assemblyName) {
        this.assemblyName = assemblyName;
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