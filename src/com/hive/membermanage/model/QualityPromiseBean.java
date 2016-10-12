package com.hive.membermanage.model;

import dk.util.JsonDateTimeSerializer;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class QualityPromiseBean
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
  private String cvalid;
  private String enterpriseName;
  private String indCatName;

  public String getCauditstatus()
  {
    return this.cauditstatus;
  }

  public void setCauditstatus(String cauditstatus) {
    this.cauditstatus = cauditstatus;
  }

  public String getCcontent() {
    return this.ccontent;
  }

  public void setCcontent(String ccontent) {
    this.ccontent = ccontent;
  }

  public String getChasannex() {
    return this.chasannex;
  }

  public void setChasannex(String chasannex) {
    this.chasannex = chasannex;
  }

  public String getCindcatcode() {
    return this.cindcatcode;
  }

  public void setCindcatcode(String cindcatcode) {
    this.cindcatcode = cindcatcode;
  }

  public String getCpromisepicpath() {
    return this.cpromisepicpath;
  }

  public void setCpromisepicpath(String cpromisepicpath) {
    this.cpromisepicpath = cpromisepicpath;
  }

  public String getCtitle() {
    return this.ctitle;
  }

  public void setCtitle(String ctitle) {
    this.ctitle = ctitle;
  }

  public String getCvalid() {
    return this.cvalid;
  }

  public void setCvalid(String cvalid) {
    this.cvalid = cvalid;
  }
  @Temporal(TemporalType.TIMESTAMP)
  @JsonSerialize(using=JsonDateTimeSerializer.class)
  public Date getDaudittime() {
    return this.daudittime;
  }

  public void setDaudittime(Date daudittime) {
    this.daudittime = daudittime;
  }
  @Temporal(TemporalType.TIMESTAMP)
  @JsonSerialize(using=JsonDateTimeSerializer.class)
  public Date getDcreatetime() {
    return this.dcreatetime;
  }

  public void setDcreatetime(Date dcreatetime) {
    this.dcreatetime = dcreatetime;
  }
  @Temporal(TemporalType.TIMESTAMP)
  @JsonSerialize(using=JsonDateTimeSerializer.class)
  public Date getDmodifytime() {
    return this.dmodifytime;
  }

  public void setDmodifytime(Date dmodifytime) {
    this.dmodifytime = dmodifytime;
  }

  public String getIndCatName() {
    return this.indCatName;
  }

  public void setIndCatName(String indCatName) {
    this.indCatName = indCatName;
  }

  public Long getNauditid() {
    return this.nauditid;
  }

  public void setNauditid(Long nauditid) {
    this.nauditid = nauditid;
  }

  public Long getNcreateid() {
    return this.ncreateid;
  }

  public void setNcreateid(Long ncreateid) {
    this.ncreateid = ncreateid;
  }

  public Long getNenterpriseid() {
    return this.nenterpriseid;
  }

  public void setNenterpriseid(Long nenterpriseid) {
    this.nenterpriseid = nenterpriseid;
  }

  public Long getNmodifyid() {
    return this.nmodifyid;
  }

  public void setNmodifyid(Long nmodifyid) {
    this.nmodifyid = nmodifyid;
  }

  public Long getNquaproid() {
    return this.nquaproid;
  }

  public void setNquaproid(Long nquaproid) {
    this.nquaproid = nquaproid;
  }

  public String getEnterpriseName() {
    return this.enterpriseName;
  }

  public void setEnterpriseName(String enterpriseName) {
    this.enterpriseName = enterpriseName;
  }
}