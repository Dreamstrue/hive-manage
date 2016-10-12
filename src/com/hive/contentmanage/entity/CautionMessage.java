package com.hive.contentmanage.entity;

import dk.util.JsonDateTimeSerializer;
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
@Table(name="F_CautionMsg")
public class CautionMessage
{

	private Long id;
	private String title;
	private String content;
	private Long nmenuid;
	private String chref;
	private String chasannex;
	private Long ncreateid;
	private Date dcreatetime;
	private Long nmodifyid;
	private Date dmodifytime;
	private Long nauditid;
	private Date daudittime;
	private String auditstatus;
	private String cvalid;
	private String cfrom;
	private Long iviewcount;
	private String auditopinion;

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "NINTECAUTIONID", unique = true, nullable = true)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CCAUTIONTITLE", nullable = false, length = 200)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "CCAUTIONCONTENT", length = 4000,nullable=false)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "NMENUID", nullable = false)
	public Long getNmenuid() {
		return this.nmenuid;
	}

	public void setNmenuid(Long nmenuid) {
		this.nmenuid = nmenuid;
	}

	@Column(name = "CHREF", length = 500)
	public String getChref() {
		return this.chref;
	}

	public void setChref(String chref) {
		this.chref = chref;
	}

	@Column(name = "CHASANNEX", nullable = false, length = 1)
	public String getChasannex() {
		return this.chasannex;
	}

	public void setChasannex(String chasannex) {
		this.chasannex = chasannex;
	}

	@Column(name = "NCREATEID", nullable = false)
	public Long getNcreateid() {
		return this.ncreateid;
	}

	public void setNcreateid(Long ncreateid) {
		this.ncreateid = ncreateid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DCREATETIME", nullable = false)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getDcreatetime() {
		return this.dcreatetime;
	}

	public void setDcreatetime(Date dcreatetime) {
		this.dcreatetime = dcreatetime;
	}

	@Column(name = "NMODIFYID")
	public Long getNmodifyid() {
		return this.nmodifyid;
	}

	public void setNmodifyid(Long nmodifyid) {
		this.nmodifyid = nmodifyid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DMODIFYTIME")
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getDmodifytime() {
		return this.dmodifytime;
	}

	public void setDmodifytime(Date dmodifytime) {
		this.dmodifytime = dmodifytime;
	}

	@Column(name = "NAUDITID")
	public Long getNauditid() {
		return this.nauditid;
	}

	public void setNauditid(Long nauditid) {
		this.nauditid = nauditid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DAUDITTIME")
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getDaudittime() {
		return this.daudittime;
	}

	public void setDaudittime(Date daudittime) {
		this.daudittime = daudittime;
	}

	@Column(name = "CAUDITSTATUS", nullable = false, length = 1)
	public String getAuditstatus() {
		return this.auditstatus;
	}

	public void setAuditstatus(String auditstatus) {
		this.auditstatus = auditstatus;
	}

	@Column(name = "CVALID", nullable = false, length = 1)
	public String getCvalid() {
		return this.cvalid;
	}

	public void setCvalid(String cvalid) {
		this.cvalid = cvalid;
	}

	@Column(name = "CFROM", nullable = false, length = 100)
	public String getCfrom() {
		return this.cfrom;
	}

	public void setCfrom(String cfrom) {
		this.cfrom = cfrom;
	}

	@Column(name = "IVIEWCOUNT")
	public Long getIviewcount() {
		return this.iviewcount;
	}

	public void setIviewcount(Long iviewcount) {
		this.iviewcount = iviewcount;
	}

	@Column(name = "CAUDITOPINION", length = 400)
	public String getAuditopinion() {
		return this.auditopinion;
	}

	public void setAuditopinion(String auditopinion) {
		this.auditopinion = auditopinion;
	}

}