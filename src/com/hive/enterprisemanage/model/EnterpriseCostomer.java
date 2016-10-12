/**
 * 
 */
package com.hive.enterprisemanage.model;

import com.hive.enterprisemanage.entity.EnterpriseRoomAttention;

/**  
 * Filename: EnterpriseCostomer.java  
 * Description: 企业客户管理
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-5-28  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-5-28 下午3:02:03				yanghui 	1.0
 */
public class EnterpriseCostomer extends EnterpriseRoomAttention{

	
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 用户头像路径
	 */
	private String userHeadImgPath;
	
	/**
	 * 评论次数
	 */
	private int commentNum = 0;
	/**
	 * 咨询次数
	 */
	private int consultNum = 0;
	/**
	 * 浏览次数
	 */
	private int browseNum = 0;
	/**
	 * 消息数量
	 */
	private int messageNum = 0;
	
	private String shieldStatus;
	
	/**
	 * 组名
	 */
	private String groupName;
	/**
	 * 组的备注
	 */
	private String remark;
	/**
	 * 分组的ID
	 */
	private Long groupId;
	
	
	public String getShieldStatus() {
		return shieldStatus;
	}
	public void setShieldStatus(String shieldStatus) {
		this.shieldStatus = shieldStatus;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserHeadImgPath() {
		return userHeadImgPath;
	}
	public void setUserHeadImgPath(String userHeadImgPath) {
		this.userHeadImgPath = userHeadImgPath;
	}
	
	public int getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}
	
	public int getConsultNum() {
		return consultNum;
	}
	public void setConsultNum(int consultNum) {
		this.consultNum = consultNum;
	}
	public int getBrowseNum() {
		return browseNum;
	}
	public void setBrowseNum(int browseNum) {
		this.browseNum = browseNum;
	}
	public int getMessageNum() {
		return messageNum;
	}
	public void setMessageNum(int messageNum) {
		this.messageNum = messageNum;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	
	
	
	
	
}
