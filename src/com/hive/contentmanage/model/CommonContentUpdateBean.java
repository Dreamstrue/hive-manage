package com.hive.contentmanage.model;


public class CommonContentUpdateBean {

	
	private Long id;
	private String title;
	private Long infoCateId;
	private String picturePath; // 图标路径
	private String source;
	private String summary; // 概要信息
	private String content;
	private String cdownload; // 是否允许下载
	private String href;
	private String hasannex;
	private String auditStatus;
	private String auditOpinion;
	private String isTop;
	private String isPush;
	/**
	 * 是否可以评论
	 */
	private String isComment;
	/**
	 * 是否可以分享
	 */
	private String isShare;
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
	public Long getInfoCateId() {
		return infoCateId;
	}
	public void setInfoCateId(Long infoCateId) {
		this.infoCateId = infoCateId;
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getCdownload() {
		return cdownload;
	}
	public void setCdownload(String cdownload) {
		this.cdownload = cdownload;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getHasannex() {
		return hasannex;
	}
	public void setHasannex(String hasannex) {
		this.hasannex = hasannex;
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
	public String getIsTop() {
		return isTop;
	}
	public void setIsTop(String isTop) {
		this.isTop = isTop;
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
