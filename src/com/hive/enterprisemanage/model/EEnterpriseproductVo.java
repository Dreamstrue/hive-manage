package com.hive.enterprisemanage.model;

import java.io.Serializable;
import java.util.Date;

import com.hive.common.entity.Annex;

public class EEnterpriseproductVo
  implements Serializable
{
  private Long nentproid;
  private Long nenterpriseid;
  private String cproductname;
  private String cimplementstandard;
  private String cprocatcode;
  private String cprocatname = "";
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
  private Annex annex;

  public Long getNentproid()
  {
    return this.nentproid;
  }

  public void setNentproid(Long nentproid) {
    this.nentproid = nentproid;
  }

  public Long getNenterpriseid() {
    return this.nenterpriseid;
  }

  public void setNenterpriseid(Long nenterpriseid) {
    this.nenterpriseid = nenterpriseid;
  }

  public String getCproductname() {
    return this.cproductname;
  }

  public void setCproductname(String cproductname) {
    this.cproductname = cproductname;
  }

  public String getCimplementstandard() {
    return this.cimplementstandard;
  }

  public void setCimplementstandard(String cimplementstandard) {
    this.cimplementstandard = cimplementstandard;
  }

  public String getCprocatname() {
    return this.cprocatname;
  }

  public void setCprocatname(String cprocatname) {
    this.cprocatname = cprocatname;
  }

  public String getCprocatcode() {
    return this.cprocatcode;
  }

  public void setCprocatcode(String cprocatcode) {
    this.cprocatcode = cprocatcode;
  }

  public String getCadministrativelicensecategory() {
    return this.cadministrativelicensecategory;
  }

  public void setCadministrativelicensecategory(String cadministrativelicensecategory)
  {
    this.cadministrativelicensecategory = cadministrativelicensecategory;
  }

  public String getCcertificateno() {
    return this.ccertificateno;
  }

  public void setCcertificateno(String ccertificateno) {
    this.ccertificateno = ccertificateno;
  }

  public String getCcertificationunit() {
    return this.ccertificationunit;
  }

  public void setCcertificationunit(String ccertificationunit) {
    this.ccertificationunit = ccertificationunit;
  }

  public Date getDgetcertificate() {
    return this.dgetcertificate;
  }

  public void setDgetcertificate(Date dgetcertificate) {
    this.dgetcertificate = dgetcertificate;
  }

  public Date getDcertificateend() {
    return this.dcertificateend;
  }

  public void setDcertificateend(Date dcertificateend) {
    this.dcertificateend = dcertificateend;
  }

  public String getCyearexamine() {
    return this.cyearexamine;
  }

  public void setCyearexamine(String cyearexamine) {
    this.cyearexamine = cyearexamine;
  }

  public String getCrisklevel() {
    return this.crisklevel;
  }

  public void setCrisklevel(String crisklevel) {
    this.crisklevel = crisklevel;
  }

  public Double getNsalenum() {
    return this.nsalenum;
  }

  public void setNsalenum(Double nsalenum) {
    this.nsalenum = nsalenum;
  }

  public String getChasannex() {
    return this.chasannex;
  }

  public void setChasannex(String chasannex) {
    this.chasannex = chasannex;
  }

  public Long getNcreateid() {
    return this.ncreateid;
  }

  public void setNcreateid(Long ncreateid) {
    this.ncreateid = ncreateid;
  }

  public Date getDcreatetime() {
    return this.dcreatetime;
  }

  public void setDcreatetime(Date dcreatetime) {
    this.dcreatetime = dcreatetime;
  }

  public Long getNmodifyid() {
    return this.nmodifyid;
  }

  public void setNmodifyid(Long nmodifyid) {
    this.nmodifyid = nmodifyid;
  }

  public Date getDmodifytime() {
    return this.dmodifytime;
  }

  public void setDmodifytime(Date dmodifytime) {
    this.dmodifytime = dmodifytime;
  }

  public Long getNauditid() {
    return this.nauditid;
  }

  public void setNauditid(Long nauditid) {
    this.nauditid = nauditid;
  }

  public Date getDaudittime() {
    return this.daudittime;
  }

  public void setDaudittime(Date daudittime) {
    this.daudittime = daudittime;
  }

  public String getCauditstatus() {
    return this.cauditstatus;
  }

  public void setCauditstatus(String cauditstatus) {
    this.cauditstatus = cauditstatus;
  }

  public String getAuditOpinion() {
    return this.auditOpinion;
  }

  public void setAuditOpinion(String auditOpinion) {
    this.auditOpinion = auditOpinion;
  }

  public String getCvalid() {
    return this.cvalid;
  }

  public void setCvalid(String cvalid) {
    this.cvalid = cvalid;
  }

  public Annex getAnnex() {
    return this.annex;
  }

  public void setAnnex(Annex annex) {
    this.annex = annex;
  }
}