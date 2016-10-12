package com.hive.enterprisemanage.entity;

import dk.util.JsonDateSerializer;
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
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="E_ENTERPRISEINFOMODIFYTEMP")
public class EEnterpriseinfomodifytemp
  implements Serializable
{
  private Long nenterpriseid;
  private String centerprisename;
  private String corganizationcode;
  private String cregaddress;
  private Double nregcapital;
  private Date destablishdate;
  private String cprovincecode;
  private String ccitycode;
  private String cdistrictcode;
  private String cbusiness;
  private String clegal;
  private String clegalphone;
  private String cindcatcode;
  private String ccomtypcode;
  private String cbusinesslicenseno;
  private Date dbusinesslicenseend;
  private Date dapprove;
  private String ccontractperson;
  private String ccontractpersonphone;
  private String cofficetelephone;
  private String ccontractaddress;
  private String czipcode;
  private String csite;
  private String ctrademark;
  private Long ipersonnum;
  private Long itechnicalpersonnumlow;
  private Long itechnicalpersonnummiddle;
  private Long itechnicalpersonnumhigh;
  private String csummary;
  private String ccountrytaxno;
  private String clocalgovtaxno;
  private String caccountbank;
  private String caccountno;
  private String chasannex = "0";
  private Long ncreateid;
  private Date dcreatetime;
  private Long nmodifyid;
  private Date dmodifytime;
  private Long nauditid;
  private Date daudittime;
  private String cauditstatus = "0";

  private String auditOpinion = "";

  private String cvalid = "1";

  public EEnterpriseinfomodifytemp()
  {
  }

  public EEnterpriseinfomodifytemp(Long nenterpriseid, String centerprisename, String corganizationcode, String cregaddress, Double nregcapital, Date destablishdate, String cprovincecode, String ccitycode, String cdistrictcode, String cbusiness, String clegal, String clegalphone, String cindcatcode, String ccomtypcode, String cbusinesslicenseno, Date dbusinesslicenseend, Date dapprove, String ccontractperson, String ccontractpersonphone, String cofficetelephone, String ccontractaddress, String czipcode, String csummary, String chasannex, Long ncreateid, Date dcreatetime, String cauditstatus, String cvalid)
  {
    this.nenterpriseid = nenterpriseid;
    this.centerprisename = centerprisename;
    this.corganizationcode = corganizationcode;
    this.cregaddress = cregaddress;
    this.nregcapital = nregcapital;
    this.destablishdate = destablishdate;
    this.cprovincecode = cprovincecode;
    this.ccitycode = ccitycode;
    this.cdistrictcode = cdistrictcode;
    this.cbusiness = cbusiness;
    this.clegal = clegal;
    this.clegalphone = clegalphone;
    this.cindcatcode = cindcatcode;
    this.ccomtypcode = ccomtypcode;
    this.cbusinesslicenseno = cbusinesslicenseno;
    this.dbusinesslicenseend = dbusinesslicenseend;
    this.dapprove = dapprove;
    this.ccontractperson = ccontractperson;
    this.ccontractpersonphone = ccontractpersonphone;
    this.cofficetelephone = cofficetelephone;
    this.ccontractaddress = ccontractaddress;
    this.czipcode = czipcode;
    this.csummary = csummary;
    this.chasannex = chasannex;
    this.ncreateid = ncreateid;
    this.dcreatetime = dcreatetime;
    this.cauditstatus = cauditstatus;
    this.cvalid = cvalid;
  }

  public EEnterpriseinfomodifytemp(Long nenterpriseid, String centerprisename, String corganizationcode, String cregaddress, Double nregcapital, Date destablishdate, String cprovincecode, String ccitycode, String cdistrictcode, String cbusiness, String clegal, String clegalphone, String cindcatcode, String ccomtypcode, String cbusinesslicenseno, Date dbusinesslicenseend, Date dapprove, String ccontractperson, String ccontractpersonphone, String cofficetelephone, String ccontractaddress, String czipcode, String csite, String ctrademark, Long ipersonnum, Long itechnicalpersonnumlow, Long itechnicalpersonnummiddle, Long itechnicalpersonnumhigh, String csummary, String ccountrytaxno, String clocalgovtaxno, String caccountbank, String caccountno, String chasannex, Long ncreateid, Date dcreatetime, Long nmodifyid, Date dmodifytime, Long nauditid, Date daudittime, String cauditstatus, String auditOpinion, String cvalid)
  {
    this.nenterpriseid = nenterpriseid;
    this.centerprisename = centerprisename;
    this.corganizationcode = corganizationcode;
    this.cregaddress = cregaddress;
    this.nregcapital = nregcapital;
    this.destablishdate = destablishdate;
    this.cprovincecode = cprovincecode;
    this.ccitycode = ccitycode;
    this.cdistrictcode = cdistrictcode;
    this.cbusiness = cbusiness;
    this.clegal = clegal;
    this.clegalphone = clegalphone;
    this.cindcatcode = cindcatcode;
    this.ccomtypcode = ccomtypcode;
    this.cbusinesslicenseno = cbusinesslicenseno;
    this.dbusinesslicenseend = dbusinesslicenseend;
    this.dapprove = dapprove;
    this.ccontractperson = ccontractperson;
    this.ccontractpersonphone = ccontractpersonphone;
    this.cofficetelephone = cofficetelephone;
    this.ccontractaddress = ccontractaddress;
    this.czipcode = czipcode;
    this.csite = csite;
    this.ctrademark = ctrademark;
    this.ipersonnum = ipersonnum;
    this.itechnicalpersonnumlow = itechnicalpersonnumlow;
    this.itechnicalpersonnummiddle = itechnicalpersonnummiddle;
    this.itechnicalpersonnumhigh = itechnicalpersonnumhigh;
    this.csummary = csummary;
    this.ccountrytaxno = ccountrytaxno;
    this.clocalgovtaxno = clocalgovtaxno;
    this.caccountbank = caccountbank;
    this.caccountno = caccountno;
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
  @Column(name = "NENTERPRISEID", unique = true, nullable = true)
  public Long getNenterpriseid() { return this.nenterpriseid; }

  public void setNenterpriseid(Long nenterpriseid)
  {
    this.nenterpriseid = nenterpriseid;
  }

  @Column(name="CENTERPRISENAME", nullable=false, length=100)
  public String getCenterprisename() {
    return this.centerprisename;
  }

  public void setCenterprisename(String centerprisename) {
    if (StringUtils.isNotBlank(centerprisename))
      this.centerprisename = centerprisename.trim();
  }

  @Column(name="CORGANIZATIONCODE", nullable=false, length=50)
  public String getCorganizationcode()
  {
    return this.corganizationcode;
  }

  public void setCorganizationcode(String corganizationcode) {
    if (StringUtils.isNotBlank(corganizationcode))
      this.corganizationcode = corganizationcode.trim();
  }

  @Column(name="CREGADDRESS", nullable=true, length=200)
  public String getCregaddress()
  {
    return this.cregaddress;
  }

  public void setCregaddress(String cregaddress) {
    this.cregaddress = cregaddress;
  }

  @Column(name="NREGCAPITAL", nullable=true, precision=10)
  public Double getNregcapital() {
    return this.nregcapital;
  }

  public void setNregcapital(Double nregcapital) {
    this.nregcapital = nregcapital;
  }
  @Temporal(TemporalType.DATE)
  @Column(name="DESTABLISHDATE", nullable=true, length=7)
  public Date getDestablishdate() {
    return this.destablishdate;
  }

  public void setDestablishdate(Date destablishdate) {
    this.destablishdate = destablishdate;
  }

  @Column(name="CPROVINCECODE", nullable=true, length=50)
  public String getCprovincecode() {
    return this.cprovincecode;
  }

  public void setCprovincecode(String cprovincecode) {
    this.cprovincecode = cprovincecode;
  }

  @Column(name="CCITYCODE", nullable=true, length=50)
  public String getCcitycode() {
    return this.ccitycode;
  }

  public void setCcitycode(String ccitycode) {
    this.ccitycode = ccitycode;
  }

  @Column(name="CDISTRICTCODE", nullable=true, length=50)
  public String getCdistrictcode() {
    return this.cdistrictcode;
  }

  public void setCdistrictcode(String cdistrictcode) {
    this.cdistrictcode = cdistrictcode;
  }

  @Column(name="CBUSINESS", nullable=true, length=2000)
  public String getCbusiness() {
    return this.cbusiness;
  }

  public void setCbusiness(String cbusiness) {
    this.cbusiness = cbusiness;
  }

  @Column(name="CLEGAL", nullable=true, length=40)
  public String getClegal() {
    return this.clegal;
  }

  public void setClegal(String clegal) {
    this.clegal = clegal;
  }

  @Column(name="CLEGALPHONE", nullable=true, length=13)
  public String getClegalphone() {
    return this.clegalphone;
  }

  public void setClegalphone(String clegalphone) {
    this.clegalphone = clegalphone;
  }

  @Column(name="CINDCATCODE", nullable=true, length=50)
  public String getCindcatcode() {
    return this.cindcatcode;
  }

  public void setCindcatcode(String cindcatcode) {
    this.cindcatcode = cindcatcode;
  }

  @Column(name="CCOMTYPCODE", nullable=false, length=50)
  public String getCcomtypcode() {
    return this.ccomtypcode;
  }

  public void setCcomtypcode(String ccomtypcode) {
    this.ccomtypcode = ccomtypcode;
  }

  @Column(name="CBUSINESSLICENSENO", nullable=false, length=50)
  public String getCbusinesslicenseno() {
    return this.cbusinesslicenseno;
  }

  public void setCbusinesslicenseno(String cbusinesslicenseno) {
    this.cbusinesslicenseno = cbusinesslicenseno;
  }
  @Temporal(TemporalType.DATE)
  @Column(name="DBUSINESSLICENSEEND", nullable=true, length=7)
  @JsonSerialize(using=JsonDateSerializer.class)
  public Date getDbusinesslicenseend() { return this.dbusinesslicenseend; }

  public void setDbusinesslicenseend(Date dbusinesslicenseend)
  {
    this.dbusinesslicenseend = dbusinesslicenseend;
  }
  @Temporal(TemporalType.DATE)
  @Column(name="DAPPROVE", nullable=true, length=7)
  @JsonSerialize(using=JsonDateTimeSerializer.class)
  public Date getDapprove() { return this.dapprove; }

  public void setDapprove(Date dapprove)
  {
    this.dapprove = dapprove;
  }

  @Column(name="CCONTRACTPERSON", nullable=true, length=40)
  public String getCcontractperson() {
    return this.ccontractperson;
  }

  public void setCcontractperson(String ccontractperson) {
    this.ccontractperson = ccontractperson;
  }

  @Column(name="CCONTRACTPERSONPHONE", nullable=true, length=13)
  public String getCcontractpersonphone() {
    return this.ccontractpersonphone;
  }

  public void setCcontractpersonphone(String ccontractpersonphone) {
    this.ccontractpersonphone = ccontractpersonphone;
  }

  @Column(name="COFFICETELEPHONE", nullable=true, length=50)
  public String getCofficetelephone() {
    return this.cofficetelephone;
  }

  public void setCofficetelephone(String cofficetelephone) {
    this.cofficetelephone = cofficetelephone;
  }

  @Column(name="CCONTRACTADDRESS", nullable=true, length=200)
  public String getCcontractaddress() {
    return this.ccontractaddress;
  }

  public void setCcontractaddress(String ccontractaddress) {
    this.ccontractaddress = ccontractaddress;
  }

  @Column(name="CZIPCODE", nullable=true, length=6)
  public String getCzipcode() {
    return this.czipcode;
  }

  public void setCzipcode(String czipcode) {
    this.czipcode = czipcode;
  }

  @Column(name="CSITE", length=100)
  public String getCsite() {
    return this.csite;
  }

  public void setCsite(String csite) {
    this.csite = csite;
  }

  @Column(name="CTRADEMARK", length=200)
  public String getCtrademark() {
    return this.ctrademark;
  }

  public void setCtrademark(String ctrademark) {
    this.ctrademark = ctrademark;
  }

  @Column(name="IPERSONNUM", precision=22, scale=0)
  public Long getIpersonnum() {
    return this.ipersonnum;
  }

  public void setIpersonnum(Long ipersonnum) {
    this.ipersonnum = ipersonnum;
  }

  @Column(name="ITECHNICALPERSONNUMLOW", precision=22, scale=0)
  public Long getItechnicalpersonnumlow() {
    return this.itechnicalpersonnumlow;
  }

  public void setItechnicalpersonnumlow(Long itechnicalpersonnumlow) {
    this.itechnicalpersonnumlow = itechnicalpersonnumlow;
  }

  @Column(name="ITECHNICALPERSONNUMMIDDLE", precision=22, scale=0)
  public Long getItechnicalpersonnummiddle() {
    return this.itechnicalpersonnummiddle;
  }

  public void setItechnicalpersonnummiddle(Long itechnicalpersonnummiddle)
  {
    this.itechnicalpersonnummiddle = itechnicalpersonnummiddle;
  }

  @Column(name="ITECHNICALPERSONNUMHIGH", precision=22, scale=0)
  public Long getItechnicalpersonnumhigh() {
    return this.itechnicalpersonnumhigh;
  }

  public void setItechnicalpersonnumhigh(Long itechnicalpersonnumhigh) {
    this.itechnicalpersonnumhigh = itechnicalpersonnumhigh;
  }

  @Column(name="CSUMMARY", nullable=false, length=500)
  public String getCsummary() {
    return this.csummary;
  }

  public void setCsummary(String csummary) {
    this.csummary = csummary;
  }

  @Column(name="CCOUNTRYTAXNO", length=100)
  public String getCcountrytaxno() {
    return this.ccountrytaxno;
  }

  public void setCcountrytaxno(String ccountrytaxno) {
    this.ccountrytaxno = ccountrytaxno;
  }

  @Column(name="CLOCALGOVTAXNO", length=100)
  public String getClocalgovtaxno() {
    return this.clocalgovtaxno;
  }

  public void setClocalgovtaxno(String clocalgovtaxno) {
    this.clocalgovtaxno = clocalgovtaxno;
  }

  @Column(name="CACCOUNTBANK", length=20)
  public String getCaccountbank() {
    return this.caccountbank;
  }

  public void setCaccountbank(String caccountbank) {
    this.caccountbank = caccountbank;
  }

  @Column(name="CACCOUNTNO", length=50)
  public String getCaccountno() {
    return this.caccountno;
  }

  public void setCaccountno(String caccountno) {
    this.caccountno = caccountno;
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
  @JsonSerialize(using=JsonDateSerializer.class)
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

  @Column(name="cAuditOpinion", length=200)
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