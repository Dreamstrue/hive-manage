package com.hive.tagManage.entity;

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

@Entity
@Table(name = "TAG_ALLOCATION")
public class SNAllocation {

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid") // 通过注解方式生成一个generator
	@GeneratedValue(generator = "idGenerator") // 使用generator
	private String id;	//标识编号
	
	/**
	 * 申请单ID
	 */
	@Column(name = "DEALERAPPLYID", length = 32)
	private String dealerapplyId;
	
	/**
	 * 申请清单ID
	 */
	@Column(name = "DEALERAPPLYINFOID", length = 32)
	private String applyInfoId;
	
	/**
	 * snID
	 */
	@Column(name = "SNBASEID", length = 32)
	private String snBaseId;	
	
	/**
	 * 申请时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	@Column(name ="OPERATIONTIME",nullable=true, length = 17)
	private Date operationTime;	
	
	/**
	 * 申请人
	 */
	@Column(name = "OPERATOR", length = 32)
	private String operator;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDealerapplyId() {
		return dealerapplyId;
	}

	public void setDealerapplyId(String dealerapplyId) {
		this.dealerapplyId = dealerapplyId;
	}

	public String getSnBaseId() {
		return snBaseId;
	}

	public void setSnBaseId(String snBaseId) {
		this.snBaseId = snBaseId;
	}

	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getApplyInfoId() {
		return applyInfoId;
	}

	public void setApplyInfoId(String applyInfoId) {
		this.applyInfoId = applyInfoId;
	}
	
}
