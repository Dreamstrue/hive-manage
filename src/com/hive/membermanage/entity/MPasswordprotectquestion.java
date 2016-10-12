package com.hive.membermanage.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="M_PASSWORDPROTECTQUESTION")
public class MPasswordprotectquestion
  implements Serializable
{
  private Long npasproqueid;
  private String cquession;

  public MPasswordprotectquestion()
  {
  }

  public MPasswordprotectquestion(Long npasproqueid, String cquession)
  {
    this.npasproqueid = npasproqueid;
    this.cquession = cquession;
  }

  	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "NPASPROQUEID", unique = true, nullable = true)
  public Long getNpasproqueid() {
    return this.npasproqueid;
  }

  public void setNpasproqueid(Long npasproqueid) {
    this.npasproqueid = npasproqueid;
  }

  @Column(name="CQUESSION", nullable=false, length=200)
  public String getCquession() {
    return this.cquession;
  }

  public void setCquession(String cquession) {
    this.cquession = cquession;
  }
}