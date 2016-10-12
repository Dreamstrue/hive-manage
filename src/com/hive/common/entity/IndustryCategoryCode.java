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
@Table(name="D_INDUSTRYCATEGORYCODE")
public class IndustryCategoryCode
  implements Serializable
{
  private static final long serialVersionUID = 6465724697051140412L;
  private Long id;
  private String indCatCode;
  private String indCatName;
  private String indCatCodeP;
  private String indCatNameP;
  private Integer sortOrder;
  private String cvalid = "1";

  @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "NINDCATCODEID", unique = true, nullable = true)
  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Column(name="CINDCATCODE", nullable=false, length=10)
  public String getIndCatCode() {
    return this.indCatCode;
  }

  public void setIndCatCode(String indCatCode) {
    this.indCatCode = indCatCode;
  }

  @Column(name="CINDCATNAME", nullable=false, length=50)
  public String getIndCatName() {
    return this.indCatName;
  }

  public void setIndCatName(String indCatName) {
    this.indCatName = indCatName;
  }

  @Column(name="CINDCATCODE_P", nullable=true, length=10)
  public String getIndCatCodeP() {
    return this.indCatCodeP;
  }

  public void setIndCatCodeP(String indCatCodeP) {
    this.indCatCodeP = indCatCodeP;
  }

  @Column(name="CINDCATNAME_P", nullable=true, length=50)
  public String getIndCatNameP() {
    return this.indCatNameP;
  }

  public void setIndCatNameP(String indCatNameP) {
    this.indCatNameP = indCatNameP;
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