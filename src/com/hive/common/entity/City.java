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
@Table(name="D_CITY")
public class City
  implements Serializable
{
  private static final long serialVersionUID = 6465724697051140412L;
  private Long id;
  private String provinceCode;
  private String cityCode;
  private String cityName;
  private Integer sortOrder;
  private String cvalid = "1";

  @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "NCITYID", unique = true, nullable = true)
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

  public void setProvinceCode(String provinceCode) {
    this.provinceCode = provinceCode;
  }

  @Column(name="CCITYCODE", nullable=false, length=20)
  public String getCityCode() {
    return this.cityCode;
  }

  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
  }

  @Column(name="CCITYNAME", nullable=false, length=50)
  public String getCityName() {
    return this.cityName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
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