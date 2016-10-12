package com.hive.membermanage.model;

public class MemberStatistics
{
  private String cprovincecode;
  private String ccitycode;
  private String cdistrictcode;
  private String areacode;
  private String areaname;
  private String cmembertype;
  private int amount;

  public String getAreacode()
  {
    return this.areacode;
  }
  public void setAreacode(String areacode) {
    this.areacode = areacode;
  }
  public String getCmembertype() {
    return this.cmembertype;
  }
  public void setCmembertype(String cmembertype) {
    this.cmembertype = cmembertype;
  }
  public int getAmount() {
    return this.amount;
  }
  public void setAmount(int amount) {
    this.amount = amount;
  }
  public String getCprovincecode() {
    return this.cprovincecode;
  }
  public void setCprovincecode(String cprovincecode) {
    this.cprovincecode = cprovincecode;
  }
  public String getCcitycode() {
    return this.ccitycode;
  }
  public void setCcitycode(String ccitycode) {
    this.ccitycode = ccitycode;
  }
  public String getCdistrictcode() {
    return this.cdistrictcode;
  }
  public void setCdistrictcode(String cdistrictcode) {
    this.cdistrictcode = cdistrictcode;
  }
  public String getAreaname() {
    return this.areaname;
  }
  public void setAreaname(String areaname) {
    this.areaname = areaname;
  }
}