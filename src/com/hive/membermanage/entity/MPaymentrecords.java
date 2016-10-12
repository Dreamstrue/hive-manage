package com.hive.membermanage.entity;

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

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="M_PAYMENTRECORDS")
public class MPaymentrecords
  implements Serializable
{
  private Long npayrecid;
  private Long nmemberid;
  private Date dpaytime;
  private Double npaysum;
  private String cpaytype;
  private Long noptuserid;
  private String cremark;
  private String cvalid = "1";

  public MPaymentrecords()
  {
  }

  public MPaymentrecords(Long npayrecid, Long nmemberid, Date dpaytime, Double npaysum, String cpaytype, Long noptuserid, String cvalid)
  {
    this.npayrecid = npayrecid;
    this.nmemberid = nmemberid;
    this.dpaytime = dpaytime;
    this.npaysum = npaysum;
    this.cpaytype = cpaytype;
    this.noptuserid = noptuserid;
    this.cvalid = cvalid;
  }

  public MPaymentrecords(Long npayrecid, Long nmemberid, Date dpaytime, Double npaysum, String cpaytype, Long noptuserid, String cremark, String cvalid)
  {
    this.npayrecid = npayrecid;
    this.nmemberid = nmemberid;
    this.dpaytime = dpaytime;
    this.npaysum = npaysum;
    this.cpaytype = cpaytype;
    this.noptuserid = noptuserid;
    this.cremark = cremark;
    this.cvalid = cvalid;
  }
  
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "NPAYRECID", unique = true, nullable = true)
  public Long getNpayrecid() { return this.npayrecid; }

  public void setNpayrecid(Long npayrecid)
  {
    this.npayrecid = npayrecid;
  }

  @Column(name="NMEMBERID", nullable=false, precision=22, scale=0)
  public Long getNmemberid() {
    return this.nmemberid;
  }

  public void setNmemberid(Long nmemberid) {
    this.nmemberid = nmemberid;
  }
  @Temporal(TemporalType.DATE)
  @Column(name="DPAYTIME", nullable=false, length=7)
  public Date getDpaytime() {
    return this.dpaytime;
  }

  public void setDpaytime(Date dpaytime) {
    this.dpaytime = dpaytime;
  }

  @Column(name="NPAYSUM", nullable=false, precision=9)
  public Double getNpaysum() {
    return this.npaysum;
  }

  public void setNpaysum(Double npaysum) {
    this.npaysum = npaysum;
  }

  @Column(name="CPAYTYPE", nullable=false, length=1)
  public String getCpaytype() {
    return this.cpaytype;
  }

  public void setCpaytype(String cpaytype) {
    this.cpaytype = cpaytype;
  }

  @Column(name="NOPTUSERID", nullable=false, precision=22, scale=0)
  public Long getNoptuserid() {
    return this.noptuserid;
  }

  public void setNoptuserid(Long noptuserid) {
    this.noptuserid = noptuserid;
  }

  @Column(name="CREMARK", length=500)
  public String getCremark() {
    return this.cremark;
  }

  public void setCremark(String cremark) {
    this.cremark = cremark;
  }

  @Column(name="CVALID", nullable=false, length=1)
  public String getCvalid() {
    return this.cvalid;
  }

  public void setCvalid(String cvalid) {
    this.cvalid = cvalid;
  }
}