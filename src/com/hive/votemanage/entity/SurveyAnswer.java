package com.hive.votemanage.entity;

import dk.util.JsonDateTimeSerializer;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="S_SURVEYANSWER")
public class SurveyAnswer
  implements Serializable
{
  private Long id;
  private Long surInfoId;
  private Long memberId;
  private String answerValue;
  private String cvalid;
  private Date dcreatetime;

  	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "NSURANSID", unique = true, nullable = true)
  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Column(name="NSURINFOID", precision=22, scale=0)
  public Long getSurInfoId() {
    return this.surInfoId;
  }

  public void setSurInfoId(Long surInfoId) {
    this.surInfoId = surInfoId;
  }

  @Column(name="NMEMBERID", precision=22, scale=0)
  public Long getMemberId() {
    return this.memberId;
  }

  public void setMemberId(Long memberId) {
    this.memberId = memberId;
  }

  @Column(name="CANSWERVALUE", length=4000)
  public String getAnswerValue() {
    return this.answerValue;
  }

  public void setAnswerValue(String answerValue) {
    this.answerValue = answerValue;
  }

  @Column(name="CVALID", nullable=false, length=1)
  public String getCvalid() {
    return this.cvalid;
  }

  public void setCvalid(String cvalid) {
    this.cvalid = cvalid;
  }
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="DCREATETIME", nullable=false, length=7)
  @JsonSerialize(using=JsonDateTimeSerializer.class)
  public Date getDcreatetime() { return this.dcreatetime; }

  public void setDcreatetime(Date dcreatetime)
  {
    this.dcreatetime = dcreatetime;
  }
}