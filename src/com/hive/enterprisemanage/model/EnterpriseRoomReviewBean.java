package com.hive.enterprisemanage.model;

import java.util.List;

import com.hive.enterprisemanage.entity.EnterpriseRoomPicture;
import com.hive.enterprisemanage.entity.EnterpriseRoomReply;
import com.hive.enterprisemanage.entity.EnterpriseRoomReview;

public class EnterpriseRoomReviewBean extends EnterpriseRoomReview {
	
	private String userName;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	private List<EnterpriseRoomReplyBean> replyList ;

	public List<EnterpriseRoomReplyBean> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<EnterpriseRoomReplyBean> replyList) {
		this.replyList = replyList;
	}
	
	private List<EnterpriseRoomPicture> picList;

	public List<EnterpriseRoomPicture> getPicList() {
		return picList;
	}

	public void setPicList(List<EnterpriseRoomPicture> picList) {
		this.picList = picList;
	}
	
	
	
	

}
