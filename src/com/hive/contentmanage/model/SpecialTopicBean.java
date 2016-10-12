package com.hive.contentmanage.model;

import com.hive.contentmanage.entity.SpecialTopicInfo;

public class SpecialTopicBean extends SpecialTopicInfo
{
  private String imageExist;
  private String virtualPid;
  private Long isortorder;
  private String text;

  public String getText()
  {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Long getIsortorder() {
    return this.isortorder;
  }

  public void setIsortorder(Long isortorder) {
    this.isortorder = isortorder;
  }

  public String getVirtualPid() {
    return this.virtualPid;
  }

  public void setVirtualPid(String virtualPid) {
    this.virtualPid = virtualPid;
  }

  public String getImageExist() {
    return this.imageExist;
  }

  public void setImageExist(String imageExist) {
    this.imageExist = imageExist;
  }
}