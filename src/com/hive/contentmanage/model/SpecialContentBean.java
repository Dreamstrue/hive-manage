package com.hive.contentmanage.model;

public class SpecialContentBean
{
  private Long id;
  private Long pid;
  private String title;
  private String content;
  private String chref;
  private String chasannex;
  private String cauditopinion;
  private String cauditstatus;

  public String getCauditstatus()
  {
    return this.cauditstatus;
  }
  public void setCauditstatus(String cauditstatus) {
    this.cauditstatus = cauditstatus;
  }
  public Long getId() {
    return this.id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public Long getPid() {
    return this.pid;
  }
  public void setPid(Long pid) {
    this.pid = pid;
  }
  public String getTitle() {
    return this.title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getContent() {
    return this.content;
  }
  public void setContent(String content) {
    this.content = content;
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
  public String getCauditopinion() {
    return this.cauditopinion;
  }
  public void setCauditopinion(String cauditopinion) {
    this.cauditopinion = cauditopinion;
  }
}