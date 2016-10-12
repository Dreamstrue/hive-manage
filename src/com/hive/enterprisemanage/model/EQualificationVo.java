package com.hive.enterprisemanage.model;

import java.util.Date;

import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Annex;


/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：Ryu Zheng
 * <p/>创建日期：2013-10-24
 * <p/>创建时间：上午10:55:23
 * <p/>功能描述：企业资质信息
 * <p/>===========================================================
 */
@SuppressWarnings("serial")
public class EQualificationVo implements java.io.Serializable {

	// Fields

	/** 编号 */
	private Long nquaid;
	/** 企业 id */
	private Long nenterpriseid;
	/** 证书名称 */
	private String ccertificatename;
	/** 发证单位 */
	private String ccertificationunit;
	/** 证书编号 */
	private String ccertificateno;
	/** 有效截止日期 */
	private Date dcertificateend;
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
	private String cauditstatus = SystemCommon_Constant.AUDIT_STATUS_0;
	/** 审核意见 */
	private String auditOpinion = "";
	/** 是否有效 */
	private String cvalid = SystemCommon_Constant.VALID_STATUS_1;
	/** 附件对象 */
	private Annex annex;

	// Constructors

	/** default constructor */
	public EQualificationVo() {
	}

	// Property accessors

	public Long getNquaid() {
		return this.nquaid;
	}

	public void setNquaid(Long nquaid) {
		this.nquaid = nquaid;
	}

	public Long getNenterpriseid() {
		return this.nenterpriseid;
	}

	public void setNenterpriseid(Long nenterpriseid) {
		this.nenterpriseid = nenterpriseid;
	}

	public String getCcertificatename() {
		return this.ccertificatename;
	}

	public void setCcertificatename(String ccertificatename) {
		this.ccertificatename = ccertificatename;
	}

	public String getCcertificationunit() {
		return this.ccertificationunit;
	}

	public void setCcertificationunit(String ccertificationunit) {
		this.ccertificationunit = ccertificationunit;
	}

	public String getCcertificateno() {
		return this.ccertificateno;
	}

	public void setCcertificateno(String ccertificateno) {
		this.ccertificateno = ccertificateno;
	}

	public Date getDcertificateend() {
		return this.dcertificateend;
	}

	public void setDcertificateend(Date dcertificateend) {
		this.dcertificateend = dcertificateend;
	}

	public String getChasannex() {
		return this.chasannex;
	}

	public void setChasannex(String chasannex) {
		this.chasannex = chasannex;
	}

	public Long getNcreateid() {
		return this.ncreateid;
	}

	public void setNcreateid(Long ncreateid) {
		this.ncreateid = ncreateid;
	}

	public Date getDcreatetime() {
		return this.dcreatetime;
	}

	public void setDcreatetime(Date dcreatetime) {
		this.dcreatetime = dcreatetime;
	}

	public Long getNmodifyid() {
		return this.nmodifyid;
	}

	public void setNmodifyid(Long nmodifyid) {
		this.nmodifyid = nmodifyid;
	}

	public Date getDmodifytime() {
		return this.dmodifytime;
	}

	public void setDmodifytime(Date dmodifytime) {
		this.dmodifytime = dmodifytime;
	}

	public Long getNauditid() {
		return this.nauditid;
	}

	public void setNauditid(Long nauditid) {
		this.nauditid = nauditid;
	}

	public Date getDaudittime() {
		return this.daudittime;
	}

	public void setDaudittime(Date daudittime) {
		this.daudittime = daudittime;
	}

	public String getCauditstatus() {
		return this.cauditstatus;
	}

	public void setCauditstatus(String cauditstatus) {
		this.cauditstatus = cauditstatus;
	}
	
	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	public String getCvalid() {
		return this.cvalid;
	}

	public void setCvalid(String cvalid) {
		this.cvalid = cvalid;
	}

	public Annex getAnnex() {
		return annex;
	}

	public void setAnnex(Annex annex) {
		this.annex = annex;
	}

}