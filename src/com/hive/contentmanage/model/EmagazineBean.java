package com.hive.contentmanage.model;

public class EmagazineBean
{
  private Long nemagazineId;
  private String title;
  private String content;
  private String cauditopinion;
  private String cauditstatus;
  private String imageExist;
  private String exeExist;
  private String zipExist;
  private Long imgId;
  private Long exeId;
  private Long zipId;

  public String getTitle()
  {
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

  public String getCauditopinion()
  {
    return this.cauditopinion;
  }
  public void setCauditopinion(String cauditopinion) {
    this.cauditopinion = cauditopinion;
  }
  public String getImageExist() {
    return this.imageExist;
  }
  public void setImageExist(String imageExist) {
    this.imageExist = imageExist;
  }
  public String getCauditstatus() {
    return this.cauditstatus;
  }
  public void setCauditstatus(String cauditstatus) {
    this.cauditstatus = cauditstatus;
  }
  public Long getNemagazineId() {
    return this.nemagazineId;
  }
  public void setNemagazineId(Long nemagazineId) {
    this.nemagazineId = nemagazineId;
  }
  public String getExeExist() {
    return this.exeExist;
  }
  public void setExeExist(String exeExist) {
    this.exeExist = exeExist;
  }
  public String getZipExist() {
    return this.zipExist;
  }
  public void setZipExist(String zipExist) {
    this.zipExist = zipExist;
  }
  public Long getImgId() {
    return this.imgId;
  }
  public void setImgId(Long imgId) {
    this.imgId = imgId;
  }
  public Long getExeId() {
    return this.exeId;
  }
  public void setExeId(Long exeId) {
    this.exeId = exeId;
  }
  public Long getZipId() {
    return this.zipId;
  }
  public void setZipId(Long zipId) {
    this.zipId = zipId;
  }
}