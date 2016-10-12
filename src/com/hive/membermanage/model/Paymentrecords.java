package com.hive.membermanage.model;

import java.math.BigInteger;


public class Paymentrecords
{
  private String NMEMBERID;
  private String CUSERNAME;
  private String CMEMBERTYPE;
  private String CMEMBERLEVEL;
  private String CMEMBERSTATUS;
  private String CMOBILEPHONE;
  private Double PAYSUM;
  private BigInteger PAYTIMES;
  private BigInteger ROWNUM_;

  public String getNMEMBERID()
  {
    return this.NMEMBERID;
  }

  public void setNMEMBERID(String nmemberid) {
    this.NMEMBERID = nmemberid;
  }

  public String getCUSERNAME() {
    return this.CUSERNAME;
  }

  public void setCUSERNAME(String cusername) {
    this.CUSERNAME = cusername;
  }

  public String getCMEMBERTYPE() {
    return this.CMEMBERTYPE;
  }

  public void setCMEMBERTYPE(String cmembertype) {
    this.CMEMBERTYPE = cmembertype;
  }

  public String getCMEMBERLEVEL() {
    return this.CMEMBERLEVEL;
  }

  public void setCMEMBERLEVEL(String cmemberlevel) {
    this.CMEMBERLEVEL = cmemberlevel;
  }

  public String getCMEMBERSTATUS() {
    return this.CMEMBERSTATUS;
  }

  public void setCMEMBERSTATUS(String cmemberstatus) {
    this.CMEMBERSTATUS = cmemberstatus;
  }

  public String getCMOBILEPHONE() {
    return this.CMOBILEPHONE;
  }

  public void setCMOBILEPHONE(String cmobilephone) {
    this.CMOBILEPHONE = cmobilephone;
  }

  public Double getPAYSUM() {
    return this.PAYSUM;
  }

  public void setPAYSUM(Double paysum) {
    this.PAYSUM = paysum;
  }

  public BigInteger getPAYTIMES() {
    return this.PAYTIMES;
  }

  public void setPAYTIMES(BigInteger paytimes) {
    this.PAYTIMES = paytimes;
  }

  public BigInteger getROWNUM_() {
    return this.ROWNUM_;
  }

  public void setROWNUM_(BigInteger rownum_) {
    this.ROWNUM_ = rownum_;
  }
}