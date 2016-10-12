package com.hive.membermanage.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import com.hive.common.SystemCommon_Constant;

import dk.util.JsonDateTimeSerializer;

@Entity
@Table(name="M_MEMBER")
public class MMember {

	// Fields

	/**会员表数据ID，自动生产*/
	private Long nmemberid;
	/**用户级别*/
	private String cmemberlevel;
	/**用户名*/
	private String cusername=StringUtils.EMPTY;
	/**密码*/
	private String cpassword=StringUtils.EMPTY;
	/**密保问题 */
	private Long npasproqueid;
	/**密保问题答案*/
	private String cpassprotectanswer;
	/**昵称*/
	private String cnickname;
	/**中文名称（真实姓名）*/
	private String chinesename;
	/**身份证号*/
	private String cardno;
	/**性别*/
	private String csex;
	/**年龄*/
	private Long iage;
	/**学历*/
	private String ceducation;
	/**职业*/
	private String occupation;
	/**兴趣爱好*/
	private String interest;
	/**个人说明*/
	private String personalnote; 
	/**所在地的省份代码*/
	private String cprovincecode;
	/**所在地的城市代码*/
	private String ccitycode;
	 /**所在地的县区代码*/
	private String cdistrictcode;
	/**详细地址*/
	private String caddress;
	/**邮政编码*/
	private String zipcode;
	/**电子邮箱*/
	private String cemail;
	/**手机号*/
	private String cmobilephone;
	/**会员类型*/
	private String cmembertype;
	/**头像*/
	private String cavatarpath = StringUtils.EMPTY;
	/**注册时间*/
	private Date dcreatetime;
	/**是否有效*/
	private String cvalid = SystemCommon_Constant.VALID_STATUS_1;
	/**最后登录时间*/
	private Date dlastlogintime;
	/**会员审核状态*/
	private String cmemberstatus;
	/**审核人*/
	private Long nauditid;
	/**审核时间*/
	private Date daudittime;
	/**
	 * 企业简称（针对企业注册时使用）
	 */
	private String conciseName;
	
	//以下字段当会员类型为企业的时候用到
	/**企业的ID*/
	private Long nenterpriseid;
	/**企业名称*/
	private String enterpriseName;
	/**组织机构代码*/
	private String organizationCode;
	/**
 	 * 是否为管理员（企业用户注册时允许有多个用户，只有第一个注册的为管理员用户）
 	 */
	private String isManager = SystemCommon_Constant.SIGN_YES_0;

	/**行业实体ID*/
	private Long industryEntityId;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "NMEMBERID", unique = true, nullable = true)
	public Long getNmemberid() {
		return this.nmemberid;
	}

	public void setNmemberid(Long nmemberid) {
		this.nmemberid = nmemberid;
	}

	@Column(name = "CUSERNAME", nullable = false, length = 100)
	public String getCusername() {
		return this.cusername;
	}

	public void setCusername(String cusername) {
		this.cusername = cusername;
	}

	@Column(name = "CPASSWORD", nullable = false, length = 200)
	public String getCpassword() {
		return this.cpassword;
	}

	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}

	@Column(name = "NPASPROQUEID", precision = 22, scale = 0)
	public Long getNpasproqueid() {
		return this.npasproqueid;
	}

	public void setNpasproqueid(Long npasproqueid) {
		this.npasproqueid = npasproqueid;
	}

	@Column(name = "CPASSPROTECTANSWER", length = 100)
	public String getCpassprotectanswer() {
		return this.cpassprotectanswer;
	}

	public void setCpassprotectanswer(String cpassprotectanswer) {
		this.cpassprotectanswer = cpassprotectanswer;
	}

	@Column(name = "CNICKNAME",  length = 50)
	public String getCnickname() {
		return this.cnickname;
	}

	public void setCnickname(String cnickname) {
		this.cnickname = cnickname;
	}

	@Column(name = "CSEX",  length = 2)
	public String getCsex() {
		return this.csex;
	}

	public void setCsex(String csex) {
		this.csex = csex;
	}

	@Column(name = "IAGE",  precision = 22, scale = 0)
	public Long getIage() {
		return this.iage;
	}

	public void setIage(Long iage) {
		this.iage = iage;
	}

	@Column(name = "CEDUCATION", length = 20)
	public String getCeducation() {
		return this.ceducation;
	}

	public void setCeducation(String ceducation) {
		this.ceducation = ceducation;
	}

	@Column(name = "CADDRESS", length = 200)
	public String getCaddress() {
		return this.caddress;
	}

	public void setCaddress(String caddress) {
		this.caddress = caddress;
	}

	@Column(name = "CEMAIL", length = 100)
	public String getCemail() {
		return this.cemail;
	}

	public void setCemail(String cemail) {
		this.cemail = cemail;
	}

	@Column(name = "CMOBILEPHONE",length = 11)
	public String getCmobilephone() {
		return this.cmobilephone;
	}

	public void setCmobilephone(String cmobilephone) {
		this.cmobilephone = cmobilephone;
	}


	@Column(name = "CMEMBERTYPE", nullable = false, length = 1)
	public String getCmembertype() {
		return this.cmembertype;
	}

	public void setCmembertype(String cmembertype) {
		this.cmembertype = cmembertype;
	}

	@Column(name = "NENTERPRISEID", precision = 22, scale = 0)
	public Long getNenterpriseid() {
		return this.nenterpriseid;
	}

	public void setNenterpriseid(Long nenterpriseid) {
		this.nenterpriseid = nenterpriseid;
	}

	@Column(name = "CMEMBERLEVEL", length = 2)
	public String getCmemberlevel() {
		return this.cmemberlevel;
	}

	public void setCmemberlevel(String cmemberlevel) {
		this.cmemberlevel = cmemberlevel;
	}

	
	@Temporal(TemporalType.DATE)
	@Column(name = "DLASTLOGINTIME", length = 7)
	public Date getDlastlogintime() {
		return this.dlastlogintime;
	}

	public void setDlastlogintime(Date dlastlogintime) {
		this.dlastlogintime = dlastlogintime;
	}

	@Column(name = "CVALID", nullable = false, length = 1)
	public String getCvalid() {
		return this.cvalid;
	}

	public void setCvalid(String cvalid) {
		this.cvalid = cvalid;
	}

	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="DCREATETIME", nullable=false)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getDcreatetime() {
		return dcreatetime;
	}

	public void setDcreatetime(Date dcreatetime) {
		this.dcreatetime = dcreatetime;
	}


	@Column(name = "CPROVINCECODE",length = 50)
	public String getCprovincecode() {
		return cprovincecode;
	}

	public void setCprovincecode(String cprovincecode) {
		this.cprovincecode = cprovincecode;
	}

	@Column(name = "CCITYCODE",length = 50)
	public String getCcitycode() {
		return ccitycode;
	}

	public void setCcitycode(String ccitycode) {
		this.ccitycode = ccitycode;
	}

	@Column(name = "CDISTRICTCODE", length = 50)
	public String getCdistrictcode() {
		return cdistrictcode;
	}

	public void setCdistrictcode(String cdistrictcode) {
		this.cdistrictcode = cdistrictcode;
	}

	@Column(name = "CAVATARPATH", nullable = true, length = 100)
	public String getCavatarpath() {
		return cavatarpath;
	}

	public void setCavatarpath(String cavatarpath) {
		this.cavatarpath = cavatarpath;
	}

	@Column(name = "CHINESENAME", nullable = true, length = 20)
	public String getChinesename() {
		return chinesename;
	}

	public void setChinesename(String chinesename) {
		this.chinesename = chinesename;
	}

	@Column(name = "CARDNO", nullable = true, length = 18)
	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	@Column(name = "OCCUPATION", nullable = true, length = 100)
	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	@Column(name = "INTEREST", nullable = true, length = 100)
	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	@Column(name = "PERSONALNOTE", nullable = true, length = 500)
	public String getPersonalnote() {
		return personalnote;
	}

	public void setPersonalnote(String personalnote) {
		this.personalnote = personalnote;
	}

	@Column(name = "ENTERPRISENAME", nullable = true, length = 100)
	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	@Column(name = "ORGANIZATIONCODE", nullable = true, length = 100)
	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	@Column(name = "ZIPCODE", nullable = true, length = 6)
	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	@Column(name="CMEMBERSTATUS", nullable=true,length=1)
	public String getCmemberstatus() {
		return cmemberstatus;
	}

	public void setCmemberstatus(String cmemberstatus) {
		this.cmemberstatus = cmemberstatus;
	}

	@Column(name="NAUDITID")
	public Long getNauditid() {
		return nauditid;
	}

	public void setNauditid(Long nauditid) {
		this.nauditid = nauditid;
	}

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="DAUDITTIME")
    @JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getDaudittime() {
		return daudittime;
	}

	public void setDaudittime(Date daudittime) {
		this.daudittime = daudittime;
	}

	@Column(name="CONCISENAME", nullable=true,length=50)
	public String getConciseName() {
		return conciseName;
	}

	public void setConciseName(String conciseName) {
		this.conciseName = conciseName;
	}
	
	@Column(name="ISMANAGER", nullable=true,length=1)
	public String getIsManager() {
		return isManager;
	}

	public void setIsManager(String isManager) {
		this.isManager = isManager;
	}
	@Column(name = "INDUSTRYENTITYID")
	public Long getIndustryEntityId() {
		return industryEntityId;
	}
	public void setIndustryEntityId(Long industryEntityId) {
		this.industryEntityId = industryEntityId;
	}
	
}
