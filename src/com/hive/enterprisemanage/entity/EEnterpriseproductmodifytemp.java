package com.hive.enterprisemanage.entity;

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
@Table(name="E_ENTERPRISEPRODUCTMODIFYTEMP")
public class EEnterpriseproductmodifytemp
  implements Serializable
{
  private Long nentproid;
  private Long nenterpriseid;
  private String cproductname;
  private String cimplementstandard;
  private String cprocatcode;
  private String cadministrativelicensecategory;
  private String ccertificateno;
  private String ccertificationunit;
  private Date dgetcertificate;
  private Date dcertificateend;
  private String cyearexamine;
  private String crisklevel;
  private Double nsalenum;
  private String chasannex = "0";
  private Long ncreateid;
  private Date dcreatetime;
  private Long nmodifyid;
  private Date dmodifytime;
  private Long nauditid;
  private Date daudittime;
  private String cauditstatus = "0";
  private String auditOpinion;
  private String cvalid = "1";

  public EEnterpriseproductmodifytemp()
  {
  }

  public EEnterpriseproductmodifytemp(Long nentproid, Long nenterpriseid, String cproductname, String cimplementstandard, String cprocatcode, String cadministrativelicensecategory, String ccertificateno, String ccertificationunit, Date dgetcertificate, Date dcertificateend, String cyearexamine, Double nsalenum, String chasannex, Long ncreateid, Date dcreatetime, String cauditstatus, String cvalid)
  {
    this.nentproid = nentproid;
    this.nenterpriseid = nenterpriseid;
    this.cproductname = cproductname;
    this.cimplementstandard = cimplementstandard;
    this.cprocatcode = cprocatcode;
    this.cadministrativelicensecategory = cadministrativelicensecategory;
    this.ccertificateno = ccertificateno;
    this.ccertificationunit = ccertificationunit;
    this.dgetcertificate = dgetcertificate;
    this.dcertificateend = dcertificateend;
    this.cyearexamine = cyearexamine;
    this.nsalenum = nsalenum;
    this.chasannex = chasannex;
    this.ncreateid = ncreateid;
    this.dcreatetime = dcreatetime;
    this.cauditstatus = cauditstatus;
    this.cvalid = cvalid;
  }

  public EEnterpriseproductmodifytemp(Long nentproid, Long nenterpriseid, String cproductname, String cimplementstandard, String cprocatcode, String cadministrativelicensecategory, String ccertificateno, String ccertificationunit, Date dgetcertificate, Date dcertificateend, String cyearexamine, String crisklevel, Double nsalenum, String chasannex, Long ncreateid, Date dcreatetime, Long nmodifyid, Date dmodifytime, Long nauditid, Date daudittime, String cauditstatus, String auditOpinion, String cvalid)
  {
    this.nentproid = nentproid;
    this.nenterpriseid = nenterpriseid;
    this.cproductname = cproductname;
    this.cimplementstandard = cimplementstandard;
    this.cprocatcode = cprocatcode;
    this.cadministrativelicensecategory = cadministrativelicensecategory;
    this.ccertificateno = ccertificateno;
    this.ccertificationunit = ccertificationunit;
    this.dgetcertificate = dgetcertificate;
    this.dcertificateend = dcertificateend;
    this.cyearexamine = cyearexamine;
    this.crisklevel = crisklevel;
    this.nsalenum = nsalenum;
    this.chasannex = chasannex;
    this.ncreateid = ncreateid;
    this.dcreatetime = dcreatetime;
    this.nmodifyid = nmodifyid;
    this.dmodifytime = dmodifytime;
    this.nauditid = nauditid;
    this.daudittime = daudittime;
    this.cauditstatus = cauditstatus;
    this.auditOpinion = auditOpinion;
    this.cvalid = cvalid;
  }
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @GenericGenerator(name = "idGenerator", strategy = "native")
  @Column(name = "NENTPROID", unique = true, nullable = true)
  public Long getNentproid() {
    return this.nentproid;
  }

  public void setNentproid(Long nentproid) {
    this.nentproid = nentproid;
  }

  @Column(name="NENTERPRISEID", nullable=false, precision=22, scale=0)
  public Long getNenterpriseid() {
    return this.nenterpriseid;
  }

  public void setNenterpriseid(Long nenterpriseid) {
    this.nenterpriseid = nenterpriseid;
  }

  @Column(name="CPRODUCTNAME", nullable=false, length=200)
  public String getCproductname() {
    return this.cproductname;
  }

  public void setCproductname(String cproductname) {
    this.cproductname = cproductname;
  }

  @Column(name="CIMPLEMENTSTANDARD", nullable=true, length=1000)
  public String getCimplementstandard() {
    return this.cimplementstandard;
  }

  public void setCimplementstandard(String cimplementstandard) {
    this.cimplementstandard = cimplementstandard;
  }

  @Column(name="CPROCATCODE", nullable=true, length=50)
  public String getCprocatcode() {
    return this.cprocatcode;
  }

  public void setCprocatcode(String cprocatcode) {
    this.cprocatcode = cprocatcode;
  }

  @Column(name="CADMINISTRATIVELICENSECATEGORY", nullable=true, length=50)
  public String getCadministrativelicensecategory() {
    return this.cadministrativelicensecategory;
  }

  public void setCadministrativelicensecategory(String cadministrativelicensecategory)
  {
    this.cadministrativelicensecategory = cadministrativelicensecategory;
  }

  @Column(name="CCERTIFICATENO", nullable=true, length=100)
  public String getCcertificateno() {
    return this.ccertificateno;
  }

  public void setCcertificateno(String ccertificateno) {
    this.ccertificateno = ccertificateno;
  }

  @Column(name="CCERTIFICATIONUNIT", nullable=true, length=50)
  public String getCcertificationunit() {
    return this.ccertificationunit;
  }

  public void setCcertificationunit(String ccertificationunit) {
    this.ccertificationunit = ccertificationunit;
  }
  @Temporal(TemporalType.DATE)
  @Column(name="DGETCERTIFICATE", nullable=true, length=7)
  public Date getDgetcertificate() {
    return this.dgetcertificate;
  }

  public void setDgetcertificate(Date dgetcertificate) {
    this.dgetcertificate = dgetcertificate;
  }
  @Temporal(TemporalType.DATE)
  @Column(name="DCERTIFICATEEND", nullable=true, length=7)
  public Date getDcertificateend() {
    return this.dcertificateend;
  }

  public void setDcertificateend(Date dcertificateend) {
    this.dcertificateend = dcertificateend;
  }

  @Column(name="CYEAREXAMINE", nullable=true, length=50)
  public String getCyearexamine() {
    return this.cyearexamine;
  }

  public void setCyearexamine(String cyearexamine) {
    this.cyearexamine = cyearexamine;
  }

  @Column(name="CRISKLEVEL", length=20)
  public String getCrisklevel() {
    return this.crisklevel;
  }

  public void setCrisklevel(String crisklevel) {
    this.crisklevel = crisklevel;
  }

  @Column(name="NSALENUM", nullable=false, precision=9, scale=2)
  public Double getNsalenum() {
    return this.nsalenum;
  }

  public void setNsalenum(Double nsalenum) {
    this.nsalenum = nsalenum;
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
  @Temporal(TemporalType.DATE)
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
  @Temporal(TemporalType.DATE)
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

  @Column(name="CAuditOpinion", length=200)
  public String getAuditOpinion() {
    return this.auditOpinion;
  }

  public void setAuditOpinion(String auditOpinion) {
    this.auditOpinion = auditOpinion;
  }

  @Column(name="CVALID", nullable=false, length=1)
  public String getCvalid() {
    return this.cvalid;
  }

  public void setCvalid(String cvalid) {
    this.cvalid = cvalid;
  }
}