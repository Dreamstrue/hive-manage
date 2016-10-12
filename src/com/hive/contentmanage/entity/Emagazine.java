package com.hive.contentmanage.entity;

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
@Table(name="F_EMAGAZINE")
public class Emagazine
{
  private Long nemagazineId;
  private String title;
  private String content;
  private Long nmenuid;
  private String chasannex;
  private String path;
  private Long ncreateid;
  private Date dcreatetime;
  private Long nmodifyid;
  private Date dmodifytime;
  private Long nauditid;
  private Date daudittime;
  private String cauditstatus;
  private String cvalid;
  private Long iviewcount;
  private String cauditopinion;

  	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "NEMAGAZINEID", unique = true, nullable = true)
  public Long getNemagazineId()
  {
    return this.nemagazineId;
  }

  public void setNemagazineId(Long nemagazineId) {
    this.nemagazineId = nemagazineId;
  }

  @Column(name="CEMAGAZINETITLE", nullable=false, length=200)
  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  @Column(name="CEMAGAZINECONTENT", length=4000)
  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Column(name="NMENUID", nullable=true, precision=22, scale=0)
  public Long getNmenuid() {
    return this.nmenuid;
  }

  public void setNmenuid(Long nmenuid) {
    this.nmenuid = nmenuid;
  }

  @Column(name="CHASANNEX", nullable=false, length=1)
  public String getChasannex()
  {
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
  @Column(name="DCREATETIME", nullable=false)
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
  @Column(name="DMODIFYTIME")
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

  @Column(name="IVIEWCOUNT", precision=38, scale=0)
  public Long getIviewcount()
  {
    return this.iviewcount;
  }

  public void setIviewcount(Long iviewcount) {
    this.iviewcount = iviewcount;
  }

  @Column(name="CAUDITOPINION", length=400)
  public String getCauditopinion() {
    return this.cauditopinion;
  }

  public void setCauditopinion(String cauditopinion) {
    this.cauditopinion = cauditopinion;
  }

  @Column(name="CCOVERPATH", length=200, nullable=false)
  public String getPath() {
    return this.path;
  }

  public void setPath(String path) {
    this.path = path;
  }
}