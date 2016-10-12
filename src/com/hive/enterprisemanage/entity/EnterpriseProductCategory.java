/**
 * 
 */
package com.hive.enterprisemanage.entity;

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
 * Filename: EnterpriseProductCategory.java  
 * Description:  企业产品类别表（新增）
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-5-13  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-5-13 下午7:03:02				yanghui 	1.0
 */
@Entity
@Table(name="EN_PRODUCT_CATEGORY")
public class EnterpriseProductCategory {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 父级ID
	 */
	private Long parentId;
	/**
	 * 类别名称
	 */
	private String text;
	/**
	 * 类别概要信息
	 */
	private String summary;
	/**
	 * 图片路径
	 */
	private String picPath;
	/**
	 * 排序
	 */
	private Long orderNum;
	private Long createId;
	private Date createTime;
	private Long modifyId;
	private Date modifyTime;
	private Long auditId;
	private Date auditTime;
	/**
	 * 审核意见
	 */
	private String auditOpinion;
	/**
	 * 审核状态
	 */
	private String auditStatus;
	/**
	 * 是否有效
	 */
	private String valid;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "idGenerator", strategy = "native")
    @Column(name = "ID", unique = true, nullable = true)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "PARENTID", nullable = false)
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	@Column(name = "CATEGORYNAME", nullable = false,length=100)
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@Column(name = "CATEGORYSUMMARY",length=500)
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	@Column(name = "CATEGORYPICPATH",length=255)
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	
	@Column(name = "ORDERNUM")
	public Long getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}
	
	@Column(name = "CREATEID", nullable = false)
	public Long getCreateId() {
		return createId;
	}
	public void setCreateId(Long createId) {
		this.createId = createId;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME", nullable = false)
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "MODIFYID")
	public Long getModifyId() {
		return modifyId;
	}
	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFYTIME")
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	@Column(name = "AUDITID")
	public Long getAuditId() {
		return auditId;
	}
	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AUDITTIME")
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	
	@Column(name = "AUDITOPINION", length=200)
	public String getAuditOpinion() {
		return auditOpinion;
	}
	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
	
	@Column(name = "AUDITSTATUS", nullable = false,length=1)
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	@Column(name = "VALID", nullable = false,length=1)
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	
	
	
	
	

}
