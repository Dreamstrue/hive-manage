package com.hive.interviewmanage.entity;

import dk.util.JsonDateTimeSerializer;
import java.io.Serializable;
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
@Table(name="F_INTERVIEWONLINE")
public class Interview
  implements Serializable
{
  private Long nintonlid;
  private String csubject;
  private String csummary;
  private String csubjectpicpath;
  private Date dinterviewtime;
  private String cduration;
  private String cguests;
  private String caddress;
  private String cvideopath;
  private Long ncreateid;
  private Date dcreatetime;
  private Long nmodifyid;
  private Date dmodifytime;
  private Long nauditid;
  private Date daudittime;
  private String cauditstatus;
  private String cauditopinion;
  private String cvalid;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "NINTONLID", unique = true, nullable = true)
  public Long getNintonlid()
  {
    return this.nintonlid;
  }

  public void setNintonlid(Long nintonlid) {
    this.nintonlid = nintonlid;
  }

  @Column(name="CSUBJECT", nullable=false, length=100)
  public String getCsubject() {
    return this.csubject;
  }

  public void setCsubject(String csubject) {
    this.csubject = csubject;
  }

  @Column(name="CSUBJECTPICPATH", length=500)
  public String getCsubjectpicpath() {
    return this.csubjectpicpath;
  }

  public void setCsubjectpicpath(String csubjectpicpath) {
    this.csubjectpicpath = csubjectpicpath;
  }
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="DINTERVIEWTIME", nullable=false, length=7)
  @JsonSerialize(using=JsonDateTimeSerializer.class)
  public Date getDinterviewtime() { return this.dinterviewtime; }

  public void setDinterviewtime(Date dinterviewtime)
  {
    this.dinterviewtime = dinterviewtime;
  }

  @Column(name="CDURATION", nullable=false, length=100)
  public String getCduration() {
    return this.cduration;
  }

  public void setCduration(String cduration) {
    this.cduration = cduration;
  }

  @Column(name="CGUESTS", nullable=false, length=2000)
  public String getCguests() {
    return this.cguests;
  }

  public void setCguests(String cguests) {
    this.cguests = cguests;
  }

  @Column(name="CVIDEOPATH", length=500)
  public String getCvideopath() {
    return this.cvideopath;
  }

  public void setCvideopath(String cvideopath) {
    this.cvideopath = cvideopath;
  }

  @Column(name="NCREATEID", nullable=false, precision=22, scale=0)
  public Long getNcreateid() {
    return this.ncreateid;
  }

  public void setNcreateid(Long ncreateid) {
    this.ncreateid = ncreateid;
  }
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="DCREATETIME", nullable=false, length=7)
  @JsonSerialize(using=JsonDateTimeSerializer.class)
  public Date getDcreatetime() { return this.dcreatetime; }

  public void setDcreatetime(Date dcreatetime)
  {
    this.dcreatetime = dcreatetime;
  }

  @Column(name="NMODIFYID", precision=22, scale=0)
  public Long getNmodifyid() {
    return this.nmodifyid;
  }

  public void setNmodifyid(Long nmodifyid) {
    this.nmodifyid = nmodifyid;
  }
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="DMODIFYTIME", length=7)
  @JsonSerialize(using=JsonDateTimeSerializer.class)
  public Date getDmodifytime() { return this.dmodifytime; }

  public void setDmodifytime(Date dmodifytime)
  {
    this.dmodifytime = dmodifytime;
  }

  @Column(name="NAUDITID", precision=22, scale=0)
  public Long getNauditid() {
    return this.nauditid;
  }

  public void setNauditid(Long nauditid) {
    this.nauditid = nauditid;
  }
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="DAUDITTIME", length=7)
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

  @Column(name="CAUDITOPINION", length=400)
  public String getCauditopinion() {
    return this.cauditopinion;
  }

  public void setCauditopinion(String cauditopinion) {
    this.cauditopinion = cauditopinion;
  }

  @Column(name="CVALID", nullable=false, length=1)
  public String getCvalid() {
    return this.cvalid;
  }

  public void setCvalid(String cvalid) {
    this.cvalid = cvalid;
  }

  @Column(name="CSUMMARY", nullable=false, length=4000)
  public String getCsummary() {
    return this.csummary;
  }

  public void setCsummary(String csummary) {
    this.csummary = csummary;
  }

  @Column(name="CADDRESS", length=200)
  public String getCaddress() {
    return this.caddress;
  }

  public void setCaddress(String caddress) {
    this.caddress = caddress;
  }
}