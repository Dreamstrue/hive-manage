package com.hive.surveymanage.entity;

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

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import dk.util.JsonDateSerializer;

/**
 * 实体信息列表实体
 */
@Entity
@Table(name = "S_INDUSTRYENTITY", schema = "ZXT")
public class IndustryEntity implements java.io.Serializable {

	// Fields

	private Long id;
	private String entityName;//实体名称
	private String address; // 地址
	private String linkMan;//联系人
	private String linkPhone;//联系人
	private Date foundtime;//成立时间
	private String objectId;
	private String otherId;//实体对应外部系统ID（如：某个加油站ID）
	private Long createid;
	private Date createtime;
	private Long modifyid;
	private Date modifytime;
	private String valid;
	private String entityType;//实体类别
	private String entityCategory;//实体类型（中石油、中石化）
	private String cauditstatus;//0未审核 1审核
	private String creatUserstatus= "0";//0未分配1已分配账户
	private Date caudittime;
	private Long surveyId;//绑定的问卷ID
	// Constructors
	  /**
	   * 组织机构代码
	   */
	  private String cprovincecode;//sheng
	  private String ccitycode;//市级
	  private String cdistrictcode;//县级
	  private String cbusiness;//经营范围
	  private String csummary;//企业简介
	  private String cindcatcode;//登记类型
	  private String ccomtypcode;
	  private String cbusinesslicenseno;
	  
	/** default constructor */
	public IndustryEntity() {
	}


	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "ID", unique = true, nullable = true)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "ENTITYNAME", nullable = false, length = 100)
	public String getEntityName() {
		return entityName;
	}


	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}


	@Column(name = "ADDRESS", length = 100)
	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "LINKMAN",  length = 100)
	public String getLinkMan() {
		return linkMan;
	}


	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	@Column(name = "LINKPHONE",  length = 100)
	public String getLinkPhone() {
		return linkPhone;
	}


	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FOUNDTIME", nullable = false, length = 7)
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getFoundtime() {
		return foundtime;
	}


	public void setFoundtime(Date foundtime) {
		this.foundtime = foundtime;
	}

	@Column(name = "ENTITYTYPE", nullable = false, length = 10)
	public String getEntityType() {
		return entityType;
	}


	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	@Column(name = "ENTITYCATEGORY", nullable = false, length = 10)
	public String getEntityCategory() {
		return entityCategory;
	}
	public void setEntityCategory(String entityCategory) {
		this.entityCategory = entityCategory;
	}
	@Column(name = "OBJECTID",length = 100)
	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	@Column(name = "OTHERID",length = 100)
	public String getOtherId() {
		return otherId;
	}


	public void setOtherId(String otherId) {
		this.otherId = otherId;
	}


	@Column(name = "CREATEID", nullable = false, precision = 22, scale = 0)
	public Long getCreateid() {
		return this.createid;
	}

	public void setCreateid(Long createid) {
		this.createid = createid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME", nullable = false, length = 7)
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Column(name = "MODIFYID", precision = 22, scale = 0)
	public Long getModifyid() {
		return this.modifyid;
	}

	public void setModifyid(Long modifyid) {
		this.modifyid = modifyid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFYTIME",  length = 7)
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getModifytime() {
		return this.modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	@Column(name = "VALID", nullable = false, length = 1)
	public String getValid() {
		return this.valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	@Column(name = "CAUDITSTATUS", nullable = false, length = 10)
	public String getCauditstatus() {
		return cauditstatus;
	}


	public void setCauditstatus(String cauditstatus) {
		this.cauditstatus = cauditstatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CAUDITTIME", length = 7)
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getCaudittime() {
		return caudittime;
	}


	public void setCaudittime(Date caudittime) {
		this.caudittime = caudittime;
	}

	  @Column(name="CPROVINCECODE", nullable=true, length=50)
	  public String getCprovincecode() {
	    return this.cprovincecode;
	  }

	  public void setCprovincecode(String cprovincecode) {
	    this.cprovincecode = cprovincecode;
	  }

	  @Column(name="CCITYCODE", nullable=true, length=50)
	  public String getCcitycode() {
	    return this.ccitycode;
	  }

	  public void setCcitycode(String ccitycode) {
	    this.ccitycode = ccitycode;
	  }
	  @Column(name="CDISTRICTCODE", nullable=true, length=50)
	  public String getCdistrictcode() {
	    return this.cdistrictcode;
	  }

	  public void setCdistrictcode(String cdistrictcode) {
	    this.cdistrictcode = cdistrictcode;
	  }
	  @Column(name="CBUSINESS", nullable=true, length=2000)
	  public String getCbusiness() {
	    return this.cbusiness;
	  }

	  public void setCbusiness(String cbusiness) {
	    this.cbusiness = cbusiness;
	  }
	  @Column(name="CSUMMARY", nullable=true, length=500)
	  public String getCsummary() {
	    return this.csummary;
	  }

	  public void setCsummary(String csummary) {
	    this.csummary = csummary;
	  }
	  @Column(name="CINDCATCODE", nullable=true, length=50)
	  public String getCindcatcode() {
	    return this.cindcatcode;
	  }

	  public void setCindcatcode(String cindcatcode) {
	    this.cindcatcode = cindcatcode;
	  }

	  @Column(name="CCOMTYPCODE", nullable=true, length=50)
	  public String getCcomtypcode() {
	    return this.ccomtypcode;
	  }

	  public void setCcomtypcode(String ccomtypcode) {
	    this.ccomtypcode = ccomtypcode;
	  }
	  @Column(name="CBUSINESSLICENSENO", nullable=true, length=50)
	  public String getCbusinesslicenseno() {
	    return this.cbusinesslicenseno;
	  }

	  public void setCbusinesslicenseno(String cbusinesslicenseno) {
	    this.cbusinesslicenseno = cbusinesslicenseno;
	  }

	    @Column(name = "SURVEYID",length = 100)
		public Long getSurveyId() {
			return surveyId;
		}
		public void setSurveyId(Long surveyId) {
			this.surveyId = surveyId;
		}

		@Column(name = "creatUserstatus",  length = 10)
		public String getCreatUserstatus() {
			return creatUserstatus;
		}

		public void setCreatUserstatus(String creatUserstatus) {
			this.creatUserstatus = creatUserstatus;
		}
	  
}