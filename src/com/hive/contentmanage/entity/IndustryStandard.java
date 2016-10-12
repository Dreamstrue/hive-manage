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
@Table(name="F_INDUSTRYSTANDARD")
public class IndustryStandard
{
  private Long id;
  private String title;
  private String content;
  private String chasannex;
  private String cdownload;
  private Long ncreateid;
  private Date dcreatetime;
  private Long nmodifyid;
  private Date nmodifytime;
  private Long nauditid;
  private Date daudittime;
  private String cauditstatus;
  private String cvalid;
  private Long nmenuid;
  private Long idowncount;
  private String auditOpinion;

  	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "NINDSTAID", unique = true, nullable = true)
  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Column(name="CINDSTANAME", nullable=false, length=200)
  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  @Column(name="CINDSTASUMMARY", nullable=false, length=4000)
  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
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
  @Column(name="DCREATETIME")
  @JsonSerialize(using=JsonDateTimeSerializer.class)
  public Date getDcreatetime() { return this.dcreatetime; }

  public void setDcreatetime(Date dcreatetime)
  {
    this.dcreatetime = dcreatetime;
  }

  @Column(name="NMODIFYID")
  public Long getNmodifyid() {
    return this.nmodifyid;
  }

  public void setNmodifyid(Long nmodifyid) {
    this.nmodifyid = nmodifyid;
  }
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="NMODIFYTIME")
  @JsonSerialize(using=JsonDateTimeSerializer.class)
  public Date getNmodifytime() { return this.nmodifytime; }

  public void setNmodifytime(Date nmodifytime)
  {
    this.nmodifytime = nmodifytime;
  }

  @Column(name="NAUDITID")
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

  @Column(name="NMENUID", nullable=false)
  public Long getNmenuid() {
    return this.nmenuid;
  }

  public void setNmenuid(Long nmenuid) {
    this.nmenuid = nmenuid;
  }

  @Column(name="IDOWNCOUNT", nullable=false)
  public Long getIdowncount() {
    return this.idowncount;
  }

  public void setIdowncount(Long idowncount) {
    this.idowncount = idowncount;
  }
  
  @Column(name="CAUDITOPINION", length=400, nullable=true)
  public String getAuditOpinion()
  {
    return this.auditOpinion;
  }

  public void setAuditOpinion(String auditOpinion) {
    this.auditOpinion = auditOpinion;
  }
}