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
@Table(name="D_PRODUCTCATEGORYCODE")
public class ProductCategoryCode
  implements Serializable
{
  private static final long serialVersionUID = 6465724697051140412L;
  private Long id;
  private String proCatCode;
  private String proCatName;
  private String proCatCodeP;
  private String proCatNameP;
  private Integer sortOrder;
  private String cvalid = "1";

  @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "NPROCATCODEID", unique = true, nullable = true)
  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Column(name="CPROCATCODE", nullable=false, length=10)
  public String getProCatCode() {
    return this.proCatCode;
  }

  public void setProCatCode(String proCatCode) {
    this.proCatCode = proCatCode;
  }

  @Column(name="CPROCATNAME", nullable=false, length=50)
  public String getProCatName() {
    return this.proCatName;
  }

  public void setProCatName(String proCatName) {
    this.proCatName = proCatName;
  }

  @Column(name="CPROCATCODE_P", nullable=true, length=10)
  public String getProCatCodeP() {
    return this.proCatCodeP;
  }

  public void setProCatCodeP(String proCatCodeP) {
    this.proCatCodeP = proCatCodeP;
  }

  @Column(name="CPROCATNAME_P", nullable=true, length=50)
  public String getProCatNameP() {
    return this.proCatNameP;
  }

  public void setProCatNameP(String proCatNameP) {
    this.proCatNameP = proCatNameP;
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