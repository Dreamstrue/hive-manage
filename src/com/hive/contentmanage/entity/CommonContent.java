/**
 * 
 */
package com.hive.contentmanage.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import dk.util.JsonDateTimeSerializer;

/**
 * Filename: CommonContent.java 
 * Description:   内容管理的公共数据表，通过信息类别区分
 * Copyright:Copyright (c)2014
 * Company: GuangFan
 * 
 * @author: yanghui
 * @version: 1.0
 * @Create: 2014-4-10 
 * Modification History: Date Author Version
 *------------------------------------------------------------------
 *				2014-4-10 上午10:42:09 yanghui 1.0
 */
@Entity
@Table(name = "F_COMMONCONTENT")
public class CommonContent {


	private Long id;
	private String title;
	private String source;
	private Long infoCateId;
	private String content;
	private String hasannex;
	private Long createId;
	private Date createTime;
	private Long modifyId;
	private Date modifyTime;
	private Long auditId;
	private Date auditTime;
	private String auditStatus;
	private String auditOpinion;
	private String valid;
	private String href;
	private Long commentIntegral;
	private Long shareIntegral;
	private Long idowncount;  //下载次数
	private String cdownload;// 是否允许下载
	private String picturePath; // 图标路径
	private String summary; // 概要信息
	private int commentNum; // 评论次数  (可以为空，针对新闻资讯，红黑榜，打折优惠，案例的时候需要保存值)
	private int shareNum; // 分享次数 (可以为空，针对新闻资讯，红黑榜，打折优惠，案例的时候需要保存值)
	/**
	 * 是否作为头条显示
	 */
	private String isTop;
	/**
	 * 是否可以评论
	 */
	private String isComment;
	/**
	 * 是否可以分享
	 */
	private String isShare;
	/**
	 * 是否进行推送
	 */
	private String isPush;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "id", unique = true, nullable = true)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "title", nullable = false, length = 200)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "source", length = 100)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "infoCateId", nullable = false)
	public Long getInfoCateId() {
		return this.infoCateId;
	}

	public void setInfoCateId(Long infoCateId) {
		this.infoCateId = infoCateId;
	}

	@Column(name = "content", nullable = false, length = 500)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "hasannex", nullable = false, length = 1)
	public String getHasannex() {
		return this.hasannex;
	}

	public void setHasannex(String hasannex) {
		this.hasannex = hasannex;
	}

	@Column(name = "createId", nullable = false)
	public Long getCreateId() {
		return this.createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createTime", nullable = false)
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "modifyId")
	public Long getModifyId() {
		return this.modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modifyTime")
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "auditId")
	public Long getAuditId() {
		return this.auditId;
	}

	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "auditTime")
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getAuditTime() {
		return this.auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	@Column(name = "auditStatus", nullable = false, length = 1)
	public String getAuditStatus() {
		return this.auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	@Column(name = "auditOpinion", length = 200)
	public String getAuditOpinion() {
		return this.auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	@Column(name = "valid", nullable = false, length = 1)
	public String getValid() {
		return this.valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	@Column(name = "href", length = 300)
	public String getHref() {
		return this.href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	@Column(name = "commentIntegral")
	public Long getCommentIntegral() {
		return this.commentIntegral;
	}

	public void setCommentIntegral(Long commentIntegral) {
		this.commentIntegral = commentIntegral;
	}

	@Column(name = "shareIntegral")
	public Long getShareIntegral() {
		return this.shareIntegral;
	}

	public void setShareIntegral(Long shareIntegral) {
		this.shareIntegral = shareIntegral;
	}

	@Column(name = "IDOWNCOUNT", nullable = false)
	public Long getIdowncount() {
		return this.idowncount;
	}

	public void setIdowncount(Long idowncount) {
		this.idowncount = idowncount;
	}
	
	
	@Column(name = "CDOWNLOAD", nullable = false,length=1)
	public String getCdownload() {
		return cdownload;
	}

	public void setCdownload(String cdownload) {
		this.cdownload = cdownload;
	}

	@Column(name = "PICTUREPATH", length = 200, nullable = true)
	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	@Column(name = "SUMMARY", length = 1000, nullable = false)
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name = "COMMENTNUM", length = 4)
	public int getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}

	@Column(name = "SHARENUM", length = 4)
	public int getShareNum() {
		return shareNum;
	}

	public void setShareNum(int shareNum) {
		this.shareNum = shareNum;
	}

	@Column(name = "ISTOP", length = 1)
	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}
	
	@Column(name = "ISCOMMENT", length = 1,nullable=true)
	public String getIsComment() {
		return isComment;
	}

	public void setIsComment(String isComment) {
		this.isComment = isComment;
	}

	@Column(name = "ISSHARE", length = 1,nullable=true)
	public String getIsShare() {
		return isShare;
	}

	public void setIsShare(String isShare) {
		this.isShare = isShare;
	}

	@Column(name = "ISPUSH", length = 1,nullable=true)
	public String getIsPush() {
		return isPush;
	}

	public void setIsPush(String isPush) {
		this.isPush = isPush;
	}
	
	

}
