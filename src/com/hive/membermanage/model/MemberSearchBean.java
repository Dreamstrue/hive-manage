package com.hive.membermanage.model;

public class MemberSearchBean
{
  private String begintime;
  private String endtime;
  private String cprovincecode;
  private String ccitycode;
  private String cdistrictcode;
  private String cmembertype;
  private String level;
  private String areacode;

  public String getBegintime()
  {
    return this.begintime;
  }
  public void setBegintime(String begintime) {
    this.begintime = begintime;
  }
  public String getEndtime() {
    return this.endtime;
  }
  public void setEndtime(String endtime) {
    this.endtime = endtime;
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
  public String getCmembertype() {
    return this.cmembertype;
  }
  public void setCmembertype(String cmembertype) {
    this.cmembertype = cmembertype;
  }
  public String getLevel() {
    return this.level;
  }
  public void setLevel(String level) {
    this.level = level;
  }
  public String getAreacode() {
    return this.areacode;
  }
  public void setAreacode(String areacode) {
    this.areacode = areacode;
  }
}