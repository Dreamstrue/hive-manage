package com.hive.contentmanage.model;

public class AboutUsBean
{
  private Long naboutusid;
  private String ctitle;
  private String ccontent;
  private String chref;
  private String chasannex;
  private String cfrom;
  private String cauditopinion;
  private String cauditstatus;

  public String getCauditstatus()
  {
    return this.cauditstatus;
  }
  public void setCauditstatus(String cauditstatus) {
    this.cauditstatus = cauditstatus;
  }
  public Long getNaboutusid() {
    return this.naboutusid;
  }
  public void setNaboutusid(Long naboutusid) {
    this.naboutusid = naboutusid;
  }
  public String getCtitle() {
    return this.ctitle;
  }
  public void setCtitle(String ctitle) {
    this.ctitle = ctitle;
  }
  public String getCcontent() {
    return this.ccontent;
  }
  public void setCcontent(String ccontent) {
    this.ccontent = ccontent;
  }
  public String getChref() {
    return this.chref;
  }
  public void setChref(String chref) {
    this.chref = chref;
  }
  public String getChasannex() {
    return this.chasannex;
  }
  public void setChasannex(String chasannex) {
    this.chasannex = chasannex;
  }
  public String getCfrom() {
    return this.cfrom;
  }
  public void setCfrom(String cfrom) {
    this.cfrom = cfrom;
  }
  public String getCauditopinion() {
    return this.cauditopinion;
  }
  public void setCauditopinion(String cauditopinion) {
    this.cauditopinion = cauditopinion;
  }
}