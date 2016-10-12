package com.hive.enterprisemanage.model;

import java.util.List;

import com.hive.enterprisemanage.entity.EnterpriseRoomConsult;

public class EnterpriseRoomConsultBean extends EnterpriseRoomConsult {
	
	private String userName;
	private String enterName;
	
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEnterName() {
		return enterName;
	}

	public void setEnterName(String enterName) {
		this.enterName = enterName;
	}

	public List<EnterpriseRoomConsult> replyList;

	public List<EnterpriseRoomConsult> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<EnterpriseRoomConsult> replyList) {
		this.replyList = replyList;
	}
	
	
}
