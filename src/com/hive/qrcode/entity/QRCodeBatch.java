package com.hive.qrcode.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 批次信息
 * 项目名称：zxt-manage    
 * 类名称：QrcodeBatch    
 * 类描述：生成二维码批次信息    
 * 创建人：yyf   
 */
@Entity
@Table(name = "QRCODE_BATCH")
public class QRCodeBatch {
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid") // 通过注解方式生成一个generator
	@GeneratedValue(generator = "idGenerator") // 使用generator
	private String id;	//标识编号
	
	/**
	 * 批次编号
	 */
	@Column(name = "BATCHNUMBER", length = 100)
	private String batchNumber;
	
	/**
	 * 数量
	 */
	@Column(name = "AMOUNT")
	private Integer amount;
	/**
	 * 有效数量
	 */
	@Column(name = "VALIDAMOUNT")
	private Integer validAmount;
	
	/**
	 * 状态
	 * 0:未印刷(未生效)   1:印刷中（预占用）   2：已印刷(占用)
	 */
	@Column(name = "STATUS", length = 1)
	private Integer status;
	
	/**
	 * 创建者
	 */
	@Column(name = "CREATER", length = 32)
	private String creater;
	
	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	@Column(name ="CREATETIME",nullable=true, length = 17)
	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getValidAmount() {
		return validAmount;
	}

	public void setValidAmount(Integer validAmount) {
		this.validAmount = validAmount;
	}
	

	
}
