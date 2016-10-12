package com.hive.contentmanage.entity;

/**
 * 
* Filename: Policyandlaw.java  
* Description:  政策法规实体类
* Copyright:Copyright (c)2014
* Company:  GuangFan 
* @author:  yanghui
* @version: 1.0  
* @Create:  2014-3-25  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2014-3-25 下午3:03:56				yanghui 	1.0
 */
import dk.util.JsonDateTimeSerializer;
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

@Entity
@Table(name="F_POLICYANDLAW")
public class Policyandlaw
{
  private Long nlawid;
  private String clawname;
  private String clawsummary;
  private String chasannex;
  private String cdownload;
  private Long ncreateid;
  private Date dcreatetime;
  private Long nmodifyid;
  private Date dmodifytime;
  private Long nauditerid;
  private Date daudittime;
  private String cauditstatus;
  private String cvalid;
  private Long nmenuid;
  private Long idowncount;
  private String auditOpinion;

  	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "NLAWID", unique = true, nullable = true)
  public Long getNlawid()
  {
    return this.nlawid;
  }

  public void setNlawid(Long nlawid) {
    this.nlawid = nlawid;
  }

  @Column(name="CLAWNAME", nullable=false, length=200)
  public String getClawname() {
    return this.clawname;
  }

  public void setClawname(String clawname) {
    this.clawname = clawname;
  }

  @Column(name="CLAWSUMMARY", nullable=false, length=4000)
  public String getClawsummary() {
    return this.clawsummary;
  }

  public void setClawsummary(String clawsummary) {
    this.clawsummary = clawsummary;
  }

  @Column(name="CHASANNEX", nullable=false, length=1)
  public String getChasannex() {
    return this.chasannex;
  }

  public void setChasannex(String chasannex) {
    this.chasannex = chasannex;
  }

  @Column(name="CDOWNLOAD", nullable=false, length=1)
  public String getCdownload() {
    return this.cdownload;
  }

  public void setCdownload(String cdownload) {
    this.cdownload = cdownload;
  }

  @Column(name="NCREATEID", nullable=false)
  public Long getNcreateid() {
    return this.ncreateid;
  }

  public void setNcreateid(Long ncreateid) {
    this.ncreateid = ncreateid;
  }
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="DCREATETIME", nullable=false)
  @JsonSerialize(using=JsonDateTimeSerializer.class)
  public Date getDcreatetime() { return this.dcreatetime; }

  public void setDcreatetime(Date dcreatetime)
  {
    this.dcreatetime = dcreatetime;
  }

  @Column(name="NMODIFYID", nullable=true)
  public Long getNmodifyid() {
    return this.nmodifyid;
  }

  public void setNmodifyid(Long nmodifyid) {
    this.nmodifyid = nmodifyid;
  }
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="DMODIFYTIME")
  @JsonSerialize(using=JsonDateTimeSerializer.class)
  public Date getDmodifytime() { return this.dmodifytime; }

  public void setDmodifytime(Date dmodifytime)
  {
    this.dmodifytime = dmodifytime;
  }

  @Column(name="NAUDITERID")
  public Long getNauditerid() {
    return this.nauditerid;
  }

  public void setNauditerid(Long nauditerid) {
    this.nauditerid = nauditerid;
  }
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="DAUDITTIME")
  @JsonSerialize(using=JsonDateTimeSerializer.class)
  public Date getDaudittime() { return this.daudittime; }

  public void setDaudittime(Date daudittime)
  {
    this.daudittime = daudittime;
  }

  @Column(name="CAUDITSTATUS", nullable=false, length=1)
  public String getCauditstatus() {
    return this.cauditstatus;
  }

  public void setCauditstatus(String cauditstatus) {
    this.cauditstatus = cauditstatus;
  }

  @Column(name="CVALID", nullable=false, length=1)
  public String getCvalid() {
    return this.cvalid;
  }

  public void setCvalid(String cvalid) {
    this.cvalid = cvalid;
  }

  @Column(name="NMENUID", nullable=false)
  public Long getNmenuid() {
    return this.nmenuid;
  }

  public void setNmenuid(Long nmenuid) {
    this.nmenuid = nmenuid;
  }

  @Column(name="IDOWNCOUNT", nullable=false, precision=22, scale=0)
  public Long getIdowncount()
  {
    return this.idowncount;
  }

  public void setIdowncount(Long idowncount)
  {
    this.idowncount = idowncount;
  }

  @Column(name="CAUDITOPINION", length=400, nullable=true)
  public String getAuditOpinion()
  {
    return this.auditOpinion;
  }

  public void setAuditOpinion(String auditOpinion)
  {
    this.auditOpinion = auditOpinion;
  }
}