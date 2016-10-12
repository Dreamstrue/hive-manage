/**
 * 
 */
package com.hive.infomanage.model;

import java.util.Date;

/**  
 * Filename: NoticeSubBean.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-2-27  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-2-27 上午9:43:59				yanghui 	1.0
 */
public class NoticeSubBean {
	
	private Long id;
	private String title;
	private String source;
	private Long infoCateId;
	private String content;
	private String hasannex;
	private String href;
	private String auditStatus;
	private String auditOpinion;
	/**
	 * 是否可以评论
	 */
	private String isComment;
	/**
	 * 是否可以分享
	 */
	private String isShare;
	private String isPush;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Long getInfoCateId() {
		return infoCateId;
	}
	public void setInfoCateId(Long infoCateId) {
		this.infoCateId = infoCateId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getHasannex() {
		return hasannex;
	}
	public void setHasannex(String hasannex) {
		this.hasannex = hasannex;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getAuditOpinion() {
		return auditOpinion;
	}
	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
	public String getIsComment() {
		return isComment;
	}
	public void setIsComment(String isComment) {
		this.isComment = isComment;
	}
	public String getIsShare() {
		return isShare;
	}
	public void setIsShare(String isShare) {
		this.isShare = isShare;
	}
	public String getIsPush() {
		return isPush;
	}
	public void setIsPush(String isPush) {
		this.isPush = isPush;
	}

	
	
}
