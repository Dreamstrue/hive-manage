package com.hive.contentmanage.model;

import java.util.Date;

public class SpecialContentInfoBean
{
  private Long id;
  private Long pid;
  private String title;
  private String specialInfoName;
  private String specialNavName;
  private Date dcreatetime;
  private String cauditstatus;
  private String cvalid;

  public String getSpecialInfoName()
  {
    return this.specialInfoName;
  }
  public void setSpecialInfoName(String specialInfoName) {
    this.specialInfoName = specialInfoName;
  }
  public String getSpecialNavName() {
    return this.specialNavName;
  }
  public void setSpecialNavName(String specialNavName) {
    this.specialNavName = specialNavName;
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
  public Date getDcreatetime() {
    return this.dcreatetime;
  }
  public void setDcreatetime(Date dcreatetime) {
    this.dcreatetime = dcreatetime;
  }
  public String getCauditstatus() {
    return this.cauditstatus;
  }
  public void setCauditstatus(String cauditstatus) {
    this.cauditstatus = cauditstatus;
  }
  public String getCvalid() {
    return this.cvalid;
  }
  public void setCvalid(String cvalid) {
    this.cvalid = cvalid;
  }
}