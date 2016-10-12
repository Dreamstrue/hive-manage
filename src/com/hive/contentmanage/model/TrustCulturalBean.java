package com.hive.contentmanage.model;

public class TrustCulturalBean
{
  private Long id;
  private String title;
  private String content;
  private String chref;
  private String chasannex;
  private String cfrom;
  private Long nmenuid;
  private String auditopinion;
  private String cauditstatus;

 
  public String getCauditstatus() {
	return cauditstatus;
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
  public String getCfrom() {
    return this.cfrom;
  }
  public void setCfrom(String cfrom) {
    this.cfrom = cfrom;
  }
  public Long getNmenuid() {
    return this.nmenuid;
  }
  public void setNmenuid(Long nmenuid) {
    this.nmenuid = nmenuid;
  }
public String getAuditopinion() {
	return auditopinion;
}
public void setAuditopinion(String auditopinion) {
	this.auditopinion = auditopinion;
}

}