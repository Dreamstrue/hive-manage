/**
 * 
 */
package com.hive.discloseInfo.model;

import java.util.List;

import com.hive.common.entity.Annex;
import com.hive.discloseInfo.entity.DiscloseInfoReply;

/**  
 * Filename: DiscloseInfoReplyBean.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  YangHui
 * @version: 1.0  
 * @Create:  2014-7-11  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-7-11 下午3:18:39				YangHui 	1.0
 */
public class DiscloseInfoReplyBean extends DiscloseInfoReply {
	
	
	private String replyUserName;
	/**
	 * 图片列表
	 */
	public List<Annex> annexList;

	
	
	public String getReplyUserName() {
		return replyUserName;
	}

	public void setReplyUserName(String replyUserName) {
		this.replyUserName = replyUserName;
	}

	public List<Annex> getAnnexList() {
		return annexList;
	}

	public void setAnnexList(List<Annex> annexList) {
		this.annexList = annexList;
	}
	
	
	

}
