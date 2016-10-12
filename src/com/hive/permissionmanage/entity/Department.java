package com.hive.permissionmanage.entity;

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

import dk.util.JsonDateTimeSerializer;

/**
 * PDepartment entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "P_DEPARTMENT")
public class Department implements java.io.Serializable {

	// Fields
	/**
	 * 需要展示成树的，id 必须叫 id，不能叫别的，比如 ndepartmentid，否则树展示不出来，好像是从 json 转成树那个地方写死了。还有 text、pid 都必须叫这个名字。
	 */
	//private int ndepartmentid;
	private Long id;
	private String text;  // 部门名称
	private Long pid;  // 父 id
	private String cdepartmentdescription;
	private Long ncreateid;
	private Date dcreatetime;
	private Long nmodifyid;
	private Date dmodifytime;
	private String cvalid;

	// Constructors

	/** default constructor */
	public Department() {
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "NDEPARTMENTID", unique = true, nullable = true)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "CDEPARTMENTNAME", nullable = false, length = 200)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@Column(name = "NPARENTDEPARTMENTID", nullable = false, precision = 22, scale = 0)
	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}
	
	@Column(name = "CDEPARTMENTDESCRIPTION", length = 200)
	public String getCdepartmentdescription() {
		return this.cdepartmentdescription;
	}

	public void setCdepartmentdescription(String cdepartmentdescription) {
		this.cdepartmentdescription = cdepartmentdescription;
	}

	@Column(name = "NCREATEID", nullable = false, precision = 22, scale = 0)
	public Long getNcreateid() {
		return this.ncreateid;
	}

	public void setNcreateid(Long ncreateid) {
		this.ncreateid = ncreateid;
	}

	@Temporal(TemporalType.TIMESTAMP) // 默认生成的是 TemporalType.DATE，想存时间的话就应该改成 TIMESTAMP
	@Column(name = "DCREATETIME", nullable = false, length = 7)
	@JsonSerialize(using = JsonDateTimeSerializer.class) // 这一行是手动加上，不加的话存到数据库中只有日期没有时间
	public Date getDcreatetime() {
		return this.dcreatetime;
	}

	public void setDcreatetime(Date dcreatetime) {
		this.dcreatetime = dcreatetime;
	}

	@Column(name = "NMODIFYID", precision = 22, scale = 0)
	public Long getNmodifyid() {
		return this.nmodifyid;
	}

	public void setNmodifyid(Long nmodifyid) {
		this.nmodifyid = nmodifyid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DMODIFYTIME", length = 7)
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getDmodifytime() {
		return this.dmodifytime;
	}

	public void setDmodifytime(Date dmodifytime) {
		this.dmodifytime = dmodifytime;
	}

	@Column(name = "CVALID", nullable = false, length = 1)
	public String getCvalid() {
		return this.cvalid;
	}

	public void setCvalid(String cvalid) {
		this.cvalid = cvalid;
	}




}