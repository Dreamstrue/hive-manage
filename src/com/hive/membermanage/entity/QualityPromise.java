package com.hive.membermanage.entity;

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
@Table(name="E_QUALITYPROMISE")
public class QualityPromise
  implements Serializable
{
  private Long nquaproid;
  private Long nenterpriseid;
  private String cindcatcode;
  private String ctitle;
  private String ccontent;
  private String cpromisepicpath;
  private String chasannex;
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
	@Column(name = "NQUAPROID", unique = true, nullable = true)
  public Long getNquaproid()
  {
    return this.nquaproid;
  }

  public void setNquaproid(Long nquaproid) {
    this.nquaproid = nquaproid;
  }

  @Column(name="NENTERPRISEID", nullable=false, precision=22, scale=0)
  public Long getNenterpriseid() {
    return this.nenterpriseid;
  }

  public void setNenterpriseid(Long nenterpriseid) {
    this.nenterpriseid = nenterpriseid;
  }

  @Column(name="CINDCATCODE", nullable=false, length=50)
  public String getCindcatcode() {
    return this.cindcatcode;
  }

  public void setCindcatcode(String cindcatcode) {
    this.cindcatcode = cindcatcode;
  }

  @Column(name="CTITLE", length=100)
  public String getCtitle() {
    return this.ctitle;
  }

  public void setCtitle(String ctitle) {
    this.ctitle = ctitle;
  }

  @Column(name="CCONTENT", length=4000)
  public String getCcontent() {
    return this.ccontent;
  }

  public void setCcontent(String ccontent) {
    this.ccontent = ccontent;
  }

  @Column(name="CPROMISEPICPATH", length=500)
  public String getCpromisepicpath() {
    return this.cpromisepicpath;
  }

  public void setCpromisepicpath(String cpromisepicpath) {
    this.cpromisepicpath = cpromisepicpath;
  }

  @Column(name="CHASANNEX", nullable=false, length=1)
  public String getChasannex() {
    return this.chasannex;
  }

  public void setChasannex(String chasannex) {
    this.chasannex = chasannex;
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

  @Column(name="CVALID", nullable=false, length=1)
  public String getCvalid() {
    return this.cvalid;
  }

  public void setCvalid(String cvalid) {
    this.cvalid = cvalid;
  }

  @Column(name="CAUDITOPINION", length=400)
  public String getCauditopinion() {
    return this.cauditopinion;
  }

  public void setCauditopinion(String cauditopinion) {
    this.cauditopinion = cauditopinion;
  }
}