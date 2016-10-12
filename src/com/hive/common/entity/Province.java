package com.hive.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="D_PROVINCE")
public class Province
  implements Serializable
{
  private static final long serialVersionUID = 6465724697051140412L;
  private Long id;
  private String provinceCode;
  private String provinceName;
  private Integer sortOrder;
  private String cvalid = "1";

  @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "NPROVINCEID", unique = true, nullable = true)
  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Column(name="CPROVINCECODE", nullable=false, length=20)
  public String getProvinceCode() {
    return this.provinceCode;
  }

  public void setProvinceCode(String provinceCode)
  {
    this.provinceCode = provinceCode;
  }

  @Column(name="CPROVINCENAME", nullable=false, length=50)
  public String getProvinceName() {
    return this.provinceName;
  }

  public void setProvinceName(String provinceName) {
    this.provinceName = provinceName;
  }

  @Column(name="ISORTORDER", nullable=false)
  public Integer getSortOrder() {
    return this.sortOrder;
  }

  public void setSortOrder(Integer sortOrder) {
    this.sortOrder = sortOrder;
  }

  @Column(name="CVALID", nullable=false, length=1)
  public String getCvalid() {
    return this.cvalid;
  }

  public void setCvalid(String cvalid) {
    this.cvalid = cvalid;
  }
}