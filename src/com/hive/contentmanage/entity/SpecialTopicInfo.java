package com.hive.contentmanage.entity;

import dk.util.JsonDateTimeSerializer;
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

@Entity
@Table(name="F_SPECIALTOPICINFO")
public class SpecialTopicInfo
{
  private Long id;
  private String text;
  private String cspetoppicpath;
  private Long pid;
  private Long isortorder;
  private Long ncreateid;
  private Date dcreatetime;
  private Long nmodifyid;
  private Date dmodifytime;
  private Long nauditid;
  private Date daudittime;
  private String cauditstatus;
  private String cvalid;
  private String cauditopinion;
  private Map<String, Object> attributes = new HashMap();

  @Transient
  public Map<String, Object> getAttributes() {
    return this.attributes;
  }

  public void setAttributes(Map<String, Object> attributes)
  {
    this.attributes = attributes;
  }

  	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "NSPETOPID", unique = true, nullable = true)
  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Column(name="CSPETOPNAME", nullable=false, length=100)
  public String getText() {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Column(name="CSPETOPPICPATH", length=500)
  public String getCspetoppicpath() {
    return this.cspetoppicpath;
  }

  public void setCspetoppicpath(String cspetoppicpath) {
    this.cspetoppicpath = cspetoppicpath;
  }

  @Column(name="NPID", precision=22, scale=0)
  public Long getPid() {
    return this.pid;
  }

  public void setPid(Long pid) {
    this.pid = pid;
  }

  @Column(name="ISORTORDER", nullable=false)
  public Long getIsortorder() {
    return this.isortorder;
  }

  public void setIsortorder(Long isortorder) {
    this.isortorder = isortorder;
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

  @Column(name="CAUDITOPINION", length=400)
  public String getCauditopinion() {
    return this.cauditopinion;
  }

  public void setCauditopinion(String cauditopinion) {
    this.cauditopinion = cauditopinion;
  }
}