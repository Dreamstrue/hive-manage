package com.hive.permissionmanage.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "p_menu")
public class Menu {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "nmenuid", unique = true, nullable = true)
	private Long id;

	@Column(name = "cmenuname", length = 40, nullable = false)
	private String text;

	@Column(name = "cmenuhref", length = 500, nullable = false)
	private String url;

	@Column(name = "isortorder", length = 38, nullable = false)
	private int seq;

	@Column(name = "cmenuicon", length = 20, nullable = true)
	private String iconCls;

	@Column(name = "nparentmenuid", nullable = false)
	private Long pid;
	
	
	  @Transient
	  private Map<String, Object> attributes = new HashMap();

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}


	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	
	
}
