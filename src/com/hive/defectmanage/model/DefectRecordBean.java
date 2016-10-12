package com.hive.defectmanage.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import dk.util.JsonDateTimeSerializer;

/**
 * 
* Filename: DefectRecordBean.java  
* Description: 缺陷采集查询Bean
* Copyright:Copyright (c)2014
* Company:  GuangFan 
* @author:  YangHui
* @version: 1.0  
* @Create:  2014-6-11  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2014-6-11 下午2:31:34				YangHui 	1.0
 */

public class DefectRecordBean {

	private Long id;
	/**
	 * 产品名称
	 */
	private String proName;
	/**
	 * 产品类型
	 */
	private String proType;
	/**
	 * 生产厂家
	 */
	private String producerName;
	/**
	 * 报告时间
	 */
	private Date reportdate;
	/**
 	 * 报告类别（1-儿童玩具类；2-汽车类；3-其他类）
 	 */
    private String peporttype;
    /**
     * 缺陷描述
     */
    private String defectDescription;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getProType() {
		return proType;
	}
	public void setProType(String proType) {
		this.proType = proType;
	}
	public String getProducerName() {
		return producerName;
	}
	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getReportdate() {
		return reportdate;
	}
	public void setReportdate(Date reportdate) {
		this.reportdate = reportdate;
	}
	public String getPeporttype() {
		return peporttype;
	}
	public void setPeporttype(String peporttype) {
		this.peporttype = peporttype;
	}
	public String getDefectDescription() {
		return defectDescription;
	}
	public void setDefectDescription(String defectDescription) {
		this.defectDescription = defectDescription;
	}
    
    
    
	
}
