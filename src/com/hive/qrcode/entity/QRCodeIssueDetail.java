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
 * 发放二维码明细表
 * 项目名称：zxt-manage    
 * 类名称：QrcodeIssue   
 * 类描述：发放二维码明细表    
 * 创建人：yyf   
 */
@Entity
@Table(name = "QRCODE_ISSUE_DETAIL")
public class QRCodeIssueDetail {
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid") // 通过注解方式生成一个generator
	@GeneratedValue(generator = "idGenerator") // 使用generator
	private String id;	//标识编号
	/**
	 * Pid
	 */
	@Column(name = "PID",nullable=false, length = 100)
	private String pid;
	/**
	 * 批次编号
	 */
	@Column(name = "BATCHNUMBER", length = 100)
	private String batchNumber;
	
	/**
	 * 二维码类别
	 * 1：url,2:文本,3：图片
	 */
	@Column(name = "QRCODETYPE", length = 5)
	private String qrcodeType;
	/**
	 * 创建者
	 */
	@Column(name = "CREATER",nullable=false, length = 32)
	private String creater;
	
	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	@Column(name ="CREATETIME",nullable=false, length = 17)
	private Date createTime;
	/**
	 * 数量
	 */
	@Column(name = "AMOUNT", length = 5)
	private Integer amount;
	/**
	 * 是否有效
	 * 0：无效  1：有效
	 */
	@Column(name = "VALID", length = 5)
	private String valid;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public String getQrcodeType() {
		return qrcodeType;
	}
	public void setQrcodeType(String qrcodeType) {
		this.qrcodeType = qrcodeType;
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
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	

}
