package com.hive.enterprisemanage.model;

import java.util.Date;

import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Annex;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：Ryu Zheng
 * <p/>创建日期：2013-10-24
 * <p/>创建时间：上午10:56:17
 * <p/>功能描述：企业宣传资料
 * <p/>===========================================================
 */
@SuppressWarnings("serial")
public class EPublicizeMaterialVo implements java.io.Serializable {

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
	/** 附件对象 */
	private Annex annex;

	// Constructors

	/** default constructor */
	public EPublicizeMaterialVo() {
	}

	// Property accessors
	public Long getNpubmatid() {
		return this.npubmatid;
	}

	public void setNpubmatid(Long npubmatid) {
		this.npubmatid = npubmatid;
	}

	public Long getNenterpriseid() {
		return this.nenterpriseid;
	}

	public void setNenterpriseid(Long nenterpriseid) {
		this.nenterpriseid = nenterpriseid;
	}

	public String getHonor() {
		return this.honor;
	}

	public void setHonor(String honor) {
		this.honor = honor;
	}

	public String getNameplateProductIds() {
		return nameplateProductIds;
	}

	public void setNameplateProductIds(String nameplateProductIds) {
		this.nameplateProductIds = nameplateProductIds;
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