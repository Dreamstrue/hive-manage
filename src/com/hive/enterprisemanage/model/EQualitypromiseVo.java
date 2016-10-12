package com.hive.enterprisemanage.model;

import java.util.Date;

import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Annex;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：Ryu Zheng
 * <p/>创建日期：2013-10-24
 * <p/>创建时间：上午10:56:50
 * <p/>功能描述：企业质量承诺书表
 * <p/>===========================================================
 */
@SuppressWarnings("serial")
public class EQualitypromiseVo implements java.io.Serializable {

	/** 编号 */
	private Long nquaproid;
	/** 企业 id */
	private Long nenterpriseid;
	/** 行业分类代码 */
	private String cindcatcode;
	/** 行业分类名称 */
	private String cindcatname;
	/** 标题 */
	private String ctitle;
	/** 内容 */
	private String ccontent;
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
	public EQualitypromiseVo() {
	}

	public Long getNquaproid() {
		return nquaproid;
	}

	public void setNquaproid(Long nquaproid) {
		this.nquaproid = nquaproid;
	}

	public Long getNenterpriseid() {
		return nenterpriseid;
	}

	public void setNenterpriseid(Long nenterpriseid) {
		this.nenterpriseid = nenterpriseid;
	}

	public String getCindcatcode() {
		return cindcatcode;
	}

	public void setCindcatcode(String cindcatcode) {
		this.cindcatcode = cindcatcode;
	}

	public String getCindcatname() {
		return cindcatname;
	}

	public void setCindcatname(String cindcatname) {
		this.cindcatname = cindcatname;
	}

	public String getCtitle() {
		return ctitle;
	}

	public void setCtitle(String ctitle) {
		this.ctitle = ctitle;
	}

	public String getCcontent() {
		return ccontent;
	}

	public void setCcontent(String ccontent) {
		this.ccontent = ccontent;
	}

	public String getChasannex() {
		return chasannex;
	}

	public void setChasannex(String chasannex) {
		this.chasannex = chasannex;
	}

	public Long getNcreateid() {
		return ncreateid;
	}

	public void setNcreateid(Long ncreateid) {
		this.ncreateid = ncreateid;
	}

	public Date getDcreatetime() {
		return dcreatetime;
	}

	public void setDcreatetime(Date dcreatetime) {
		this.dcreatetime = dcreatetime;
	}

	public Long getNmodifyid() {
		return nmodifyid;
	}

	public void setNmodifyid(Long nmodifyid) {
		this.nmodifyid = nmodifyid;
	}

	public Date getDmodifytime() {
		return dmodifytime;
	}

	public void setDmodifytime(Date dmodifytime) {
		this.dmodifytime = dmodifytime;
	}

	public Long getNauditid() {
		return nauditid;
	}

	public void setNauditid(Long nauditid) {
		this.nauditid = nauditid;
	}

	public Date getDaudittime() {
		return daudittime;
	}

	public void setDaudittime(Date daudittime) {
		this.daudittime = daudittime;
	}

	public String getCauditstatus() {
		return cauditstatus;
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
		return cvalid;
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