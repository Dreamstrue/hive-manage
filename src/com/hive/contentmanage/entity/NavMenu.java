/**
 * 
 */
package com.hive.contentmanage.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import dk.util.JsonDateTimeSerializer;

/**  
 * Filename: NavMenu.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-25  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-25 下午2:28:53				yanghui 	1.0
 */
@Entity
@Table(name="f_navmenu")
public class NavMenu
{

  	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "nNavMenuId", unique = true, nullable = true)
  private Long id;

  @Column(name="cMenuName", length=40, nullable=false)
  private String text;

  @Column(name="cMenuHref", length=500, nullable=false)
  private String menuHref;

  @Column(name="iSortOrder", nullable=false)
  private Long sortOrder;

  @Column(name="nParentMenuId", nullable=false)
  private Long pid;

  @Column(name="nCreateId", nullable=false)
  private Long createUserId;

  @Column(name="dCreateTime", nullable=false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createTime;

  @Column(name="nModifyId", nullable=true)
  private Long modifUserId;

  @Column(name="dModifyTime", nullable=true)
  @Temporal(TemporalType.TIMESTAMP)
  private Date modifyTime;

  @Column(name="nAuditId", nullable=true)
  private Long auditUserId;

  @Column(name="dAuditTime", nullable=true)
  @Temporal(TemporalType.TIMESTAMP)
  private Date auditTime;

  @Column(name="cAuditStatus", length=1, nullable=false)
  private String auditStatus;

  @Column(name="cValid", length=1, nullable=false)
  private String valid;

  @Column(name="curl", length=500, nullable=true)
  private String url;

  @Column(name="CAUDITOPINION", length=400, nullable=true)
  private String auditOpinion;

  @Column(name="CCOENTITY", length=100, nullable=true)
  private String conentity;

  @Column(name="CLEAF", length=1)
  private String leaf;
  
  @Column(name="ISSHOW", length=1)
  private String isShow;  //用来判断该栏目是否可属于通用内容管理，如果可以，就不需要显示在菜单内容管理下面了，否则需要显示

  @Transient
  private Map<String, Object> attributes = new HashMap();

  public String getLeaf()
  {
    return this.leaf;
  }

  public void setLeaf(String leaf) {
    this.leaf = leaf;
  }

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getText() {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getMenuHref() {
    return this.menuHref;
  }

  public void setMenuHref(String menuHref) {
    this.menuHref = menuHref;
  }

  public Long getSortOrder() {
    return this.sortOrder;
  }

  public void setSortOrder(Long sortOrder) {
    this.sortOrder = sortOrder;
  }

  public Long getPid() {
    return this.pid;
  }

  public void setPid(Long pid) {
    this.pid = pid;
  }

  public Long getCreateUserId() {
    return this.createUserId;
  }

  public void setCreateUserId(Long createUserId) {
    this.createUserId = createUserId;
  }

  @JsonSerialize(using=JsonDateTimeSerializer.class)
  public Date getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Long getModifUserId() {
    return this.modifUserId;
  }

  public void setModifUserId(Long modifUserId) {
    this.modifUserId = modifUserId;
  }

  @JsonSerialize(using=JsonDateTimeSerializer.class)
  public Date getModifyTime() {
    return this.modifyTime;
  }

  public void setModifyTime(Date modifyTime) {
    this.modifyTime = modifyTime;
  }

  public Long getAuditUserId() {
    return this.auditUserId;
  }

  public void setAuditUserId(Long auditUserId) {
    this.auditUserId = auditUserId;
  }

  @JsonSerialize(using=JsonDateTimeSerializer.class)
  public Date getAuditTime() {
    return this.auditTime;
  }

  public void setAuditTime(Date auditTime) {
    this.auditTime = auditTime;
  }

  public String getAuditStatus() {
    return this.auditStatus;
  }

  public void setAuditStatus(String auditStatus) {
    this.auditStatus = auditStatus;
  }

  public String getValid() {
    return this.valid;
  }

  public void setValid(String valid) {
    this.valid = valid;
  }

  public Map<String, Object> getAttributes() {
    return this.attributes;
  }

  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getAuditOpinion() {
    return this.auditOpinion;
  }

  public void setAuditOpinion(String auditOpinion) {
    this.auditOpinion = auditOpinion;
  }

  public String getConentity() {
    return this.conentity;
  }

  public void setConentity(String conentity) {
    this.conentity = conentity;
  }

public String getIsShow() {
	return isShow;
}

public void setIsShow(String isShow) {
	this.isShow = isShow;
}
  
  
}
