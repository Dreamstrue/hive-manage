package com.hive.membermanage.model;

import com.hive.enterprisemanage.entity.EQualificationmodifytemp;

public class QualificationVO extends EQualificationmodifytemp
{
  private String enterpriseName;

  public String getEnterpriseName()
  {
    return this.enterpriseName;
  }
  public void setEnterpriseName(String enterpriseName) {
    this.enterpriseName = enterpriseName;
  }
}