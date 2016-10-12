package com.hive.enterprisemanage.entity;

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

import com.hive.common.SystemCommon_Constant;

import dk.util.JsonDateTimeSerializer;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：Ryu Zheng
 * <p/>创建日期：2013-10-24
 * <p/>创建时间：上午10:56:17
 * <p/>功能描述：企业宣传资料表
 * <p/>===========================================================
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "E_PublicizeMaterial")
public class EPublicizeMaterial implements java.io.Serializable {

	// Fields

	/** 编号 */
	private Long npubmatid;
	/** 企业id */
	private Long nenterpriseid;
	/** 企业荣誉 */
	private String honor;
	/** 名牌产品 id 集合 */
	private String nameplateProductIds;
	/** 是否包含附件 */
	private String chasannex = SystemCommon_Constant.SIGN_YES_0;
	/** 创建人 */
	private Long ncreateid;
	/** 创建时间 */
	private Date dcreatetime;
	/** 修改人 */
	private Long nmodifyid;
	/** 修改时间 */
	private Date dmodifytime;
	/** 审核人 */
	private Long nauditid;
	/** 审核时间 */
	private Date daudittime;
	/** 审核状态 */
	private String cauditstatus = SystemCommon_Constant.AUDIT_STATUS_1;
	/** 审核意见 */
	private String auditOpinion = "";
	/** 是否有效 */
	private String cvalid = SystemCommon_Constant.VALID_STATUS_1;

	// Constructors

	/** default constructor */
	public EPublicizeMaterial() {
	}

	/** minimal constructor */
	public EPublicizeMaterial(Long npubmatid, Long nenterpriseid,
			String honor, String nameplateProductIds, String chasannex,
			Long ncreateid, Date dcreatetime, String cauditstatus,
			String cvalid) {
		this.npubmatid = npubmatid;
		this.nenterpriseid = nenterpriseid;
		this.honor = honor;
		this.nameplateProductIds = nameplateProductIds;
		this.chasannex = chasannex;
		this.ncreateid = ncreateid;
		this.dcreatetime = dcreatetime;
		this.cauditstatus = cauditstatus;
		this.cvalid = cvalid;
	}

	/** full constructor */
	public EPublicizeMaterial(Long npubmatid, Long nenterpriseid,
			String honor, String nameplateProductIds, String chasannex,
			Long ncreateid, Date dcreatetime, Long nmodifyid,
			Date dmodifytime, Long nauditid, Date daudittime,
			String cauditstatus, String auditOpinion, String cvalid) {
		this.npubmatid = npubmatid;
		this.nenterpriseid = nenterpriseid;
		this.honor = honor;
		this.nameplateProductIds = nameplateProductIds;
		this.chasannex = chasannex;
		this.ncreateid = ncreateid;
		this.dcreatetime = dcreatetime;
		this.nmodifyid = nmodifyid;
		this.dmodifytime = dmodifytime;
		this.nauditid = nauditid;
		this.daudittime = daudittime;
		this.cauditstatus = cauditstatus;
		this.auditOpinion = auditOpinion;
		this.cvalid = cvalid;
	}

	// Property accessors
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "idGenerator", strategy = "native")
    @Column(name = "npubmatId", unique = true, nullable = true)
	public Long getNpubmatid() {
		return this.npubmatid;
	}

	public void setNpubmatid(Long npubmatid) {
		this.npubmatid = npubmatid;
	}

	@Column(name = "NENTERPRISEID", nullable = false, precision = 22, scale = 0)
	public Long getNenterpriseid() {
		return this.nenterpriseid;
	}

	public void setNenterpriseid(Long nenterpriseid) {
		this.nenterpriseid = nenterpriseid;
	}

	@Column(name = "CHONOR", length = 600)
	public String getHonor() {
		return this.honor;
	}

	public void setHonor(String honor) {
		this.honor = honor;
	}

	@Column(name = "cNameplateProductIds", length = 200)
	public String getNameplateProductIds() {
		return nameplateProductIds;
	}

	public void setNameplateProductIds(String nameplateProductIds) {
		this.nameplateProductIds = nameplateProductIds;
	}

	@Column(name = "CHASANNEX", nullable = false, length = 1)
	public String getChasannex() {
		return this.chasannex;
	}

	public void setChasannex(String chasannex) {
		this.chasannex = chasannex;
	}

	@Column(name = "NCREATEID", nullable = false, precision = 22, scale = 0)
	public Long getNcreateid() {
		return this.ncreateid;
	}

	public void setNcreateid(Long ncreateid) {
		this.ncreateid = ncreateid;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DCREATETIME", nullable = false, length = 7)
	@JsonSerialize(using = JsonDateTimeSerializer.class)
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

	@Temporal(TemporalType.DATE)
	@Column(name = "DMODIFYTIME", length = 7)
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getDmodifytime() {
		return this.dmodifytime;
	}

	public void setDmodifytime(Date dmodifytime) {
		this.dmodifytime = dmodifytime;
	}

	@Column(name = "NAUDITID", precision = 22, scale = 0)
	public Long getNauditid() {
		return this.nauditid;
	}

	public void setNauditid(Long nauditid) {
		this.nauditid = nauditid;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DAUDITTIME", length = 7)
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getDaudittime() {
		return this.daudittime;
	}

	public void setDaudittime(Date daudittime) {
		this.daudittime = daudittime;
	}

	@Column(name = "CAUDITSTATUS", nullable = false, length = 1)
	public String getCauditstatus() {
		return this.cauditstatus;
	}

	public void setCauditstatus(String cauditstatus) {
		this.cauditstatus = cauditstatus;
	}
	
	@Column(name = "cAuditOpinion", length = 200)
	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	@Column(name = "CVALID", nullable = false, length = 1)
	public String getCvalid() {
		return this.cvalid;
	}

	public void setCvalid(String cvalid) {
		this.cvalid = cvalid;
	}

}