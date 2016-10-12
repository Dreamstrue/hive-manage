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

import com.hive.common.SystemCommon_Constant;

import dk.util.JsonDateTimeSerializer;

/**
 * 
 * Filename: DefectRecordCar.java 
 * Description: 产品缺陷记录表（汽车类） 
 * Copyright:Copyright * (c)2014 
 * Company: GuangFan
 * 
 * @author: YangHui
 * @version: 1.0
 * @Create: 2014-5-30 Modification History: Date Author Version
 *          ------------------------------------------------------------------
 *          2014-5-30 下午4:38:49 YangHui 1.0
 */
@Entity
@Table(name = "DEFECT_RECORD_CAR")
public class DefectRecordCar implements java.io.Serializable {


	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 报告用户Id
	 */
	private Long reportuserid;
	/**
	 * 报告日期
	 */
	private Date reportdate;
	/**
	 * 是否有效标识
	 */
	private String isValid = SystemCommon_Constant.VALID_STATUS_1;
	
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

	@Column(name = "REPORTUSERID", precision = 22, scale = 0)
	public Long getReportuserid() {
		return this.reportuserid;
	}

	public void setReportuserid(Long reportuserid) {
		this.reportuserid = reportuserid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	@Column(name = "REPORTDATE", nullable = false, precision = 22, scale = 0)
	public Date getReportdate() {
		return reportdate;
	}

	public void setReportdate(Date reportdate) {
		this.reportdate = reportdate;
	}

	@Column(name = "ISVALID", nullable=false,length=1)
	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	
	
}