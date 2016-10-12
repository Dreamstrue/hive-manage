package com.hive.contentmanage.model;

public class PictureNewBean
{
  private Long id;
  private String title;
  private String content;
  private String chref;
  private Long isortorder;
  private String chasannex;
  private String cfrom;
  private String cauditopinion;
  private String cauditstatus;
  private String imageExist;
  private String fileExist;//是否附件更换
  /**
	 * 是否可以评论
	 */
	private String isComment;
	public String getFileExist() {
	return fileExist;
}
public void setFileExist(String fileExist) {
	this.fileExist = fileExist;
}
	/**
	 * 是否可以分享
	 */
	private String isShare;
  public Long getId()
  {
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
  public Long getIsortorder() {
    return this.isortorder;
  }
  public void setIsortorder(Long isortorder) {
    this.isortorder = isortorder;
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
public String getIsComment() {
	return isComment;
}
public void setIsComment(String isComment) {
	this.isComment = isComment;
}
public String getIsShare() {
	return isShare;
}
public void setIsShare(String isShare) {
	this.isShare = isShare;
}
  
  
}