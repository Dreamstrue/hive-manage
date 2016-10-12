/**
 * 
 */
package com.hive.clientmanage.model;

import org.apache.commons.lang3.StringUtils;

/**  
 * Filename: MMemberBean.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-4-22  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-4-22 下午5:08:55				yanghui 	1.0
 */
public class MMemberBean {
	
	/**会员表数据ID，自动生产*/
	private Long nmemberid;
	/**会员类型*/
	private String cmembertype = "0";
	/**密保问题 */
	private Long npasproqueid;
	/**密保问题答案*/
	private String cpassprotectanswer =StringUtils.EMPTY;
	/**昵称*/
	private String cnickname =StringUtils.EMPTY;
	/**中文名称（真实姓名）*/
	private String chinesename =StringUtils.EMPTY;
	/**身份证号*/
	private String cardno =StringUtils.EMPTY;
	/**性别*/
	private String csex =StringUtils.EMPTY;
	/**年龄*/
	private Long iage = 0l;
	/**学历*/
	private String ceducation =StringUtils.EMPTY;
	/**职业*/
	private String occupation =StringUtils.EMPTY;
	/**兴趣爱好*/
	private String interest =StringUtils.EMPTY;
	/**个人说明*/
	private String personalnote =StringUtils.EMPTY; 
	/**所在地的省份代码*/
	private String cprovincecode =StringUtils.EMPTY;
	/**所在地的城市代码*/
	private String ccitycode =StringUtils.EMPTY;
	 /**所在地的县区代码*/
	private String cdistrictcode =StringUtils.EMPTY;
	/**详细地址*/
	private String caddress =StringUtils.EMPTY;
	/**邮政编码*/
	private String zipcode =StringUtils.EMPTY;
	/**电子邮箱*/
	private String cemail =StringUtils.EMPTY;
	/**手机号*/
	private String cmobilephone =StringUtils.EMPTY;

	/**头像*/
	private String cavatarpath = StringUtils.EMPTY;

	public Long getNmemberid() {
		return nmemberid;
	}

	public void setNmemberid(Long nmemberid) {
		this.nmemberid = nmemberid;
	}

	public String getCmembertype() {
		return cmembertype;
	}

	public void setCmembertype(String cmembertype) {
		this.cmembertype = cmembertype;
	}

	public Long getNpasproqueid() {
		return npasproqueid;
	}

	public void setNpasproqueid(Long npasproqueid) {
		this.npasproqueid = npasproqueid;
	}

	public String getCpassprotectanswer() {
		return cpassprotectanswer;
	}

	public void setCpassprotectanswer(String cpassprotectanswer) {
		this.cpassprotectanswer = cpassprotectanswer;
	}

	public String getCnickname() {
		return cnickname;
	}

	public void setCnickname(String cnickname) {
		this.cnickname = cnickname;
	}

	public String getChinesename() {
		return chinesename;
	}

	public void setChinesename(String chinesename) {
		this.chinesename = chinesename;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getCsex() {
		return csex;
	}

	public void setCsex(String csex) {
		this.csex = csex;
	}

	public Long getIage() {
		return iage;
	}

	public void setIage(Long iage) {
		this.iage = iage;
	}

	public String getCeducation() {
		return ceducation;
	}

	public void setCeducation(String ceducation) {
		this.ceducation = ceducation;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getPersonalnote() {
		return personalnote;
	}

	public void setPersonalnote(String personalnote) {
		this.personalnote = personalnote;
	}

	public String getCprovincecode() {
		return cprovincecode;
	}

	public void setCprovincecode(String cprovincecode) {
		this.cprovincecode = cprovincecode;
	}

	public String getCcitycode() {
		return ccitycode;
	}

	public void setCcitycode(String ccitycode) {
		this.ccitycode = ccitycode;
	}

	public String getCdistrictcode() {
		return cdistrictcode;
	}

	public void setCdistrictcode(String cdistrictcode) {
		this.cdistrictcode = cdistrictcode;
	}

	public String getCaddress() {
		return caddress;
	}

	public void setCaddress(String caddress) {
		this.caddress = caddress;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getCemail() {
		return cemail;
	}

	public void setCemail(String cemail) {
		this.cemail = cemail;
	}

	public String getCmobilephone() {
		return cmobilephone;
	}

	public void setCmobilephone(String cmobilephone) {
		this.cmobilephone = cmobilephone;
	}

	public String getCavatarpath() {
		return cavatarpath;
	}

	public void setCavatarpath(String cavatarpath) {
		this.cavatarpath = cavatarpath;
	}

	

}
