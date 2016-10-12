/**
 * 
 */
package com.hive.enterprisemanage.model;

import java.util.List;

import com.hive.enterprisemanage.entity.EnterpriseRoomPicture;
import com.hive.enterprisemanage.entity.EnterpriseRoomReply;

/**  
 * Filename: EnterpriseRoomReplyBean.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-5-12  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-5-12 下午3:33:01				yanghui 	1.0
 */
public class EnterpriseRoomReplyBean extends EnterpriseRoomReply {
	
	private String userName;  //评论人用户名
	private String replyUserName; //回复人用户名
	
	private List<EnterpriseRoomPicture> picList;
	
	
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getReplyUserName() {
		return replyUserName;
	}

	public void setReplyUserName(String replyUserName) {
		this.replyUserName = replyUserName;
	}

	public List<EnterpriseRoomPicture> getPicList() {
		return picList;
	}

	public void setPicList(List<EnterpriseRoomPicture> picList) {
		this.picList = picList;
	}
	
	

}
