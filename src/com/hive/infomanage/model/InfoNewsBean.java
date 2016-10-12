/**
 * 
 */
package com.hive.infomanage.model;

import com.hive.infomanage.entity.InfoNews;

/**  
 * Filename: InfoNewsBean.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-2-27  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-2-27 上午10:43:54				yanghui 	1.0
 */
public class InfoNewsBean extends InfoNews {
	
	private String infoCateName; //信息类别名称
	private int commentNum; //评论次数
	private int shareNum; //分享次数
	private Long newsNum;  //某信息类别下的新闻数量

	public String getInfoCateName() {
		return infoCateName;
	}

	public void setInfoCateName(String infoCateName) {
		this.infoCateName = infoCateName;
	}

	public int getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}

	public int getShareNum() {
		return shareNum;
	}

	public void setShareNum(int shareNum) {
		this.shareNum = shareNum;
	}

	public Long getNewsNum() {
		return newsNum;
	}

	public void setNewsNum(Long newsNum) {
		this.newsNum = newsNum;
	}
	
	
	
	
	

}
