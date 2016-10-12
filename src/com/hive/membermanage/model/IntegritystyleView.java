package com.hive.membermanage.model;

import dk.util.JsonDateTimeSerializer;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class IntegritystyleView
{
  BigDecimal NINTSTYLEID;
  String CAUDITSTATUS;
  String CENTERPRISENAME;
  String CUSERNAME;
  String CPRODUCTIDS;
  Date DCREATETIME;

  public BigDecimal getNINTSTYLEID()
  {
    return this.NINTSTYLEID;
  }

  public void setNINTSTYLEID(BigDecimal nintstyleid) {
    this.NINTSTYLEID = nintstyleid;
  }

  public String getCAUDITSTATUS() {
    return this.CAUDITSTATUS;
  }

  public void setCAUDITSTATUS(String cauditstatus) {
    this.CAUDITSTATUS = cauditstatus;
  }

  public String getCENTERPRISENAME() {
    return this.CENTERPRISENAME;
  }

  public void setCENTERPRISENAME(String centerprisename) {
    this.CENTERPRISENAME = centerprisename;
  }

  public String getCUSERNAME() {
    return this.CUSERNAME;
  }

  public void setCUSERNAME(String cusername) {
    this.CUSERNAME = cusername;
  }

  public String getCPRODUCTIDS() {
    return this.CPRODUCTIDS;
  }

  public void setCPRODUCTIDS(String cproductids) {
    this.CPRODUCTIDS = cproductids;
  }
  @Temporal(TemporalType.TIMESTAMP)
  @JsonSerialize(using=JsonDateTimeSerializer.class)
  public Date getDCREATETIME() {
    return this.DCREATETIME;
  }

  public void setDCREATETIME(Date dcreatetime) {
    this.DCREATETIME = dcreatetime;
  }
}