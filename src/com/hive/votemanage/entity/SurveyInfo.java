package com.hive.votemanage.entity;

import dk.util.JsonDateSerializer;
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
@Table(name="S_SurveyInfo")
public class SurveyInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String subject;
  private String preface;
  private String question;
  private Date validBegin;
  private Date validEnd;
  private Long createId;
  private Date createTime;
  private Long auditId;
  private Date auditTime;
  private String auditStatus = "0";
  private String auditOpinion;
  private String valid = "1";

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "NSURINFOID", unique = true, nullable = true)
  public Long getId() { return this.id; }

  public void setId(Long id) {
    this.id = id;
  }

  @Column(name="CSUBJECT", nullable=false, length=100)
  public String getSubject() {
    return this.subject;
  }
  public void setSubject(String subject) {
    this.subject = subject;
  }

  @Column(name="CPREFACE", nullable=false, length=2000)
  public String getPreface() {
    return this.preface;
  }
  public void setPreface(String preface) {
    this.preface = preface;
  }

  @Column(name="CQUESTION", nullable=false, length=2000)
  public String getQuestion() {
    return this.question;
  }
  public void setQuestion(String question) {
    this.question = question;
  }
  @Temporal(TemporalType.DATE)
  @Column(name="DVALIDBEGIN", nullable=false, length=7)
  @JsonSerialize(using=JsonDateSerializer.class)
  public Date getValidBegin() { return this.validBegin; }

  public void setValidBegin(Date validBegin) {
    this.validBegin = validBegin;
  }
  @Temporal(TemporalType.DATE)
  @Column(name="DVALIDEND", nullable=false, length=7)
  @JsonSerialize(using=JsonDateSerializer.class)
  public Date getValidEnd() { return this.validEnd; }

  public void setValidEnd(Date validEnd) {
    this.validEnd = validEnd;
  }

  @Column(name="NCREATEID", nullable=false, precision=22, scale=0)
  public Long getCreateId() {
    return this.createId;
  }
  public void setCreateId(Long createId) {
    this.createId = createId;
  }
  @Temporal(TemporalType.DATE)
  @Column(name="DCREATETIME", nullable=false, length=7)
  @JsonSerialize(using=JsonDateSerializer.class)
  public Date getCreateTime() { return this.createTime; }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  @Column(name="NAUDITID", nullable=false, precision=22, scale=0)
  public Long getAuditId() {
    return this.auditId;
  }
  public void setAuditId(Long auditId) {
    this.auditId = auditId;
  }
  @Temporal(TemporalType.DATE)
  @Column(name="DAUDITTIME", nullable=false, length=7)
  @JsonSerialize(using=JsonDateSerializer.class)
  public Date getAuditTime() { return this.auditTime; }

  public void setAuditTime(Date auditTime) {
    this.auditTime = auditTime;
  }

  @Column(name="CAUDITSTATUS", nullable=false, length=1)
  public String getAuditStatus() {
    return this.auditStatus;
  }
  public void setAuditStatus(String auditStatus) {
    this.auditStatus = auditStatus;
  }

  @Column(name="CAUDITOPINION", nullable=true, length=400)
  public String getAuditOpinion() {
    return this.auditOpinion;
  }
  public void setAuditOpinion(String auditOpinion) {
    this.auditOpinion = auditOpinion;
  }

  @Column(name="CVALID", nullable=false, length=1)
  public String getValid() {
    return this.valid;
  }
  public void setValid(String valid) {
    this.valid = valid;
  }
}