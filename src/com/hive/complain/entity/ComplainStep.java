package com.hive.complain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import dk.util.JsonDateTimeSerializer;

@Entity
@Table(name="complain_step")
public class ComplainStep {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "ID", unique = true, nullable = true)
	private Long id;
	
	
	
	@Column(name="complainId",length=32,nullable=false)
	private Long complainId;
	
	
	@Column(name="step",length=6,nullable=false)
	private String step;
	
	@Column(name="content",length=1000,nullable=false)
	private String content;
	
	
	@Column(name="dealTime",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dealTime;

	
	@Column(name="realname",length=50,nullable=true)
	private String realname;
	
	@Column(name="username",length=50,nullable=true)
	private String username;

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	


	public Long getComplainId() {
		return complainId;
	}


	public void setComplainId(Long complainId) {
		this.complainId = complainId;
	}


	public String getStep() {
		return step;
	}


	public void setStep(String step) {
		this.step = step;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}

	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getDealTime() {
		return dealTime;
	}


	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}


	public String getRealname() {
		return realname;
	}


	public void setRealname(String realname) {
		this.realname = realname;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	
	
	
}
