package com.hive.membermanage.model;

import com.hive.enterprisemanage.entity.EEnterpriseproductmodifytemp;

public class ProductVO extends EEnterpriseproductmodifytemp
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