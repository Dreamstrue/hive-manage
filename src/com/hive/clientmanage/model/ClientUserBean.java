package com.hive.clientmanage.model;

import org.apache.commons.lang3.StringUtils;

public class ClientUserBean {

	private Long nmemberid;
	/**用户名*/
	private String cusername=StringUtils.EMPTY;
	/**密码*/
	private String cpassword=StringUtils.EMPTY;
	/**新密码   针对修改或忘记密码时使用*/
	private String newPassword = StringUtils.EMPTY;
	/**密保问题 */
	private Long npasproqueid;
	/**密保问题答案*/
	private String cpassprotectanswer;
	
	
	
	
	
	public Long getNmemberid() {
		return nmemberid;
	}
	public void setNmemberid(Long nmemberid) {
		this.nmemberid = nmemberid;
	}
	public String getCusername() {
		return cusername;
	}
	public void setCusername(String cusername) {
		this.cusername = cusername;
	}
	public String getCpassword() {
		return cpassword;
	}
	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
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
	
	
}
