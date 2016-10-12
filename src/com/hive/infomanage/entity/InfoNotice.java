package com.hive.infomanage.entity;

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
import javax.persistence.Transient;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import dk.util.JsonDateSerializer;
import dk.util.JsonDateTimeSerializer;


@Entity
@Table(name = "INFO_NOTICE")
public class InfoNotice implements java.io.Serializable {

	// Fields

	private Long id;
	private Long auditId;
	private String auditOpinion;
	private String auditStatus;
	private Date auditTime;
	private String content;
	private Long createId;
	private Date createTime;
	private String hasannex;
	private String href;
	private String sendObject;
	private Long modifyId;
	private Date modifyTime;
	private String source;
	private String title;
	private String valid;
	/**
	 * 评论次数
	 */
	private int commentNum;
	//新增字段
	private Date validbegin; // 公告有效期起
    private Date validend;   //公告有效期止
    private String top;      //是否置顶
    private Date topend;	//置顶有效期
    /**
	 * 是否可以评论
	 */
	private String isComment;
	/**
	 * 是否可以分享
	 */
	private String isShare;
	/**
	 * 是否推送
	 */
	private String isPush;
    @Transient
    private NoticeReceive noticeReceive;   
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "ID", unique = true, nullable = true)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "AUDITID")
	public Long getAuditId() {
		return this.auditId;
	}

	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}

	@Column(name = "AUDITOPINION", length = 200)
	public String getAuditOpinion() {
		return this.auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	@Column(name = "AUDITSTATUS", nullable = false, length = 1)
	public String getAuditStatus() {
		return this.auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AUDITTIME")
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getAuditTime() {
		return this.auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	@Column(name = "CONTENT", nullable = false, length = 500)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "CREATEID", nullable = false)
	public Long getCreateId() {
		return this.createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME", nullable = false)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "HASANNEX", nullable = false)
	public String getHasannex() {
		return this.hasannex;
	}

	public void setHasannex(String hasannex) {
		this.hasannex = hasannex;
	}

	@Column(name = "HREF", length = 300)
	public String getHref() {
		return this.href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	
	@Column(name = "SENDOBJECT", length = 1,nullable=false)
	public String getSendObject() {
		return sendObject;
	}

	public void setSendObject(String sendObject) {
		this.sendObject = sendObject;
	}

	@Column(name = "MODIFYID")
	public Long getModifyId() {
		return this.modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFYTIME")
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "SOURCE", length = 100)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "TITLE", nullable = false, length = 200)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "VALID", nullable = false, length = 1)
	public String getValid() {
		return this.valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "VALIDBEGIN",nullable=false)
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getValidbegin() {
		return validbegin;
	}

	public void setValidbegin(Date validbegin) {
		this.validbegin = validbegin;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "VALIDEND")
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getValidend() {
		return validend;
	}

	
	public void setValidend(Date validend) {
		this.validend = validend;
	}

	@Column(name = "TOP", nullable = false, length = 1)
	public String getTop() {
		return top;
	}

	public void setTop(String top) {
		this.top = top;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "TOPEND")
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getTopend() {
		return topend;
	}

	public void setTopend(Date topend) {
		this.topend = topend;
	}

	@Column(name = "COMMENTNUM",  length = 4)
	public int getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
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