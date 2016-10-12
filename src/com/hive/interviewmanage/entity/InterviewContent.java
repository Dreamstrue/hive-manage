package com.hive.interviewmanage.entity;

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

import dk.util.JsonDateTimeSerializer;

@Entity
@Table(name="F_INTERVIEWCONTENT")
public class InterviewContent
  implements Serializable
{
  private Long nintconid;
  private Long nintonlid;
  private String cdialoguecontent;
  private Long ncreateid;
  private Date dcreatetime;
  private String cvalid;

  public InterviewContent()
  {
  }

  public InterviewContent(Long nintconid, Long nintonlid, String cdialoguecontent, Long ncreateid, Date dcreatetime, String cvalid)
  {
    this.nintconid = nintconid;
    this.nintonlid = nintonlid;
    this.cdialoguecontent = cdialoguecontent;
    this.ncreateid = ncreateid;
    this.dcreatetime = dcreatetime;
    this.cvalid = cvalid;
  }
  
  	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "NINTCONID", unique = true, nullable = true)
  public Long getNintconid() { return this.nintconid; }

  public void setNintconid(Long nintconid)
  {
    this.nintconid = nintconid;
  }

  @Column(name="NINTONLID", nullable=false, precision=22, scale=0)
  public Long getNintonlid() {
    return this.nintonlid;
  }

  public void setNintonlid(Long nintonlid) {
    this.nintonlid = nintonlid;
  }

  @Column(name="CDIALOGUECONTENT", nullable=false, length=2000)
  public String getCdialoguecontent() {
    return this.cdialoguecontent;
  }

  public void setCdialoguecontent(String cdialoguecontent) {
    this.cdialoguecontent = cdialoguecontent;
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

  @Column(name="CVALID", nullable=false, length=1)
  public String getCvalid() {
    return this.cvalid;
  }

  public void setCvalid(String cvalid) {
    this.cvalid = cvalid;
  }
}