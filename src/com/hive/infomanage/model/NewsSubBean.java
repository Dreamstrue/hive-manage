/**
 * 
 */
package com.hive.infomanage.model;

/**  
 * Filename: NewsSubBean.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-2-27  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-2-27 上午10:44:34				yanghui 	1.0
 */
public class NewsSubBean {
	
	private Long id;
	private String title;
	private String source;
	private Long infoCateId;
	private String content;
	private String hasannex;
	private String href;
	private String auditStatus;
	private String auditOpinion;
	private String summary;
	private String picturePath;
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
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	
	
}
