package com.hive.enterprisemanage.entity;

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
@Table(name="E_QUALIFICATIONMODIFYTEMP")
public class EQualificationmodifytemp
  implements Serializable
{
  private Long nquaid;
  private Long nenterpriseid;
  private String ccertificatename;
  private String ccertificationunit;
  private String ccertificateno;
  private Date dcertificateend;
  private String chasannex;
  private Long ncreateid;
  private Date dcreatetime;
  private Long nmodifyid;
  private Date dmodifytime;
  private Long nauditid;
  private Date daudittime;
  private String cauditstatus;
  private String cvalid;

  public EQualificationmodifytemp()
  {
  }

  public EQualificationmodifytemp(Long nquaid, Long nenterpriseid, String ccertificatename, String ccertificationunit, String ccertificateno, Date dcertificateend, String chasannex, Long ncreateid, Date dcreatetime, String cauditstatus, String cvalid)
  {
    this.nquaid = nquaid;
    this.nenterpriseid = nenterpriseid;
    this.ccertificatename = ccertificatename;
    this.ccertificationunit = ccertificationunit;
    this.ccertificateno = ccertificateno;
    this.dcertificateend = dcertificateend;
    this.chasannex = chasannex;
    this.ncreateid = ncreateid;
    this.dcreatetime = dcreatetime;
    this.cauditstatus = cauditstatus;
    this.cvalid = cvalid;
  }

  public EQualificationmodifytemp(Long nquaid, Long nenterpriseid, String ccertificatename, String ccertificationunit, String ccertificateno, Date dcertificateend, String chasannex, Long ncreateid, Date dcreatetime, Long nmodifyid, Date dmodifytime, Long nauditid, Date daudittime, String cauditstatus, String cvalid)
  {
    this.nquaid = nquaid;
    this.nenterpriseid = nenterpriseid;
    this.ccertificatename = ccertificatename;
    this.ccertificationunit = ccertificationunit;
    this.ccertificateno = ccertificateno;
    this.dcertificateend = dcertificateend;
    this.chasannex = chasannex;
    this.ncreateid = ncreateid;
    this.dcreatetime = dcreatetime;
    this.nmodifyid = nmodifyid;
    this.dmodifytime = dmodifytime;
    this.nauditid = nauditid;
    this.daudittime = daudittime;
    this.cauditstatus = cauditstatus;
    this.cvalid = cvalid;
  }

  	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "idGenerator", strategy = "native")
    @Column(name = "NQUAID", unique = true, nullable = true)
  public Long getNquaid() {
    return this.nquaid;
  }

  public void setNquaid(Long nquaid) {
    this.nquaid = nquaid;
  }

  @Column(name="NENTERPRISEID", nullable=false, precision=22, scale=0)
  public Long getNenterpriseid() {
    return this.nenterpriseid;
  }

  public void setNenterpriseid(Long nenterpriseid) {
    this.nenterpriseid = nenterpriseid;
  }

  @Column(name="CCERTIFICATENAME", nullable=false, length=200)
  public String getCcertificatename() {
    return this.ccertificatename;
  }

  public void setCcertificatename(String ccertificatename) {
    this.ccertificatename = ccertificatename;
  }

  @Column(name="CCERTIFICATIONUNIT", nullable=true, length=200)
  public String getCcertificationunit() {
    return this.ccertificationunit;
  }

  public void setCcertificationunit(String ccertificationunit) {
    this.ccertificationunit = ccertificationunit;
  }

  @Column(name="CCERTIFICATENO", nullable=true, length=100)
  public String getCcertificateno() {
    return this.ccertificateno;
  }

  public void setCcertificateno(String ccertificateno) {
    this.ccertificateno = ccertificateno;
  }
  @Temporal(TemporalType.DATE)
  @Column(name="DCERTIFICATEEND", nullable=false, length=7)
  public Date getDcertificateend() {
    return this.dcertificateend;
  }

  public void setDcertificateend(Date dcertificateend) {
    this.dcertificateend = dcertificateend;
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
  @Temporal(TemporalType.DATE)
  @Column(name="DCREATETIME", nullable=false, length=7)
  public Date getDcreatetime() {
    return this.dcreatetime;
  }

  public void setDcreatetime(Date dcreatetime) {
    this.dcreatetime = dcreatetime;
  }

  @Column(name="NMODIFYID", precision=22, scale=0)
  public Long getNmodifyid() {
    return this.nmodifyid;
  }

  public void setNmodifyid(Long nmodifyid) {
    this.nmodifyid = nmodifyid;
  }
  @Temporal(TemporalType.DATE)
  @Column(name="DMODIFYTIME", length=7)
  public Date getDmodifytime() {
    return this.dmodifytime;
  }

  public void setDmodifytime(Date dmodifytime) {
    this.dmodifytime = dmodifytime;
  }

  @Column(name="NAUDITID", precision=22, scale=0)
  public Long getNauditid() {
    return this.nauditid;
  }

  public void setNauditid(Long nauditid) {
    this.nauditid = nauditid;
  }
  @Temporal(TemporalType.DATE)
  @Column(name="DAUDITTIME", length=7)
  public Date getDaudittime() {
    return this.daudittime;
  }

  public void setDaudittime(Date daudittime) {
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
}