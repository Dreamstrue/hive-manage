package com.hive.contentmanage.entity;

import dk.util.JsonDateTimeSerializer;
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

@Entity
@Table(name = "F_PICTURENEWS")
public class Picturenews {
	private Long id;
	private String title;
	private String cpicpath;
	private String content;
	private String chref;
	private Long isortorder;
	private String chasannex;
	private Long ncreateid;
	private Date createTime;
	private Long nmodifyid;
	private Date dmodifytime;
	private Long nauditid;
	private Date daudittime;
	private String auditStatus;
	private String valid;
	private String cfrom;
	private Long iviewcount;
	private String cauditopinion;
	/**
	 * 评论次数
	 */
	private int commentNum;
	/**
	 * 是否可以评论
	 */
	private String isComment;
	/**
	 * 是否可以分享
	 */
	private String isShare;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "NPICNEWSID", unique = true, nullable = true)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CNEWSTITLE", nullable = false, length = 200)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "CPICPATH", nullable = true, length = 500)
	public String getCpicpath() {
		return this.cpicpath;
	}

	public void setCpicpath(String cpicpath) {
		this.cpicpath = cpicpath;
	}

	@Column(name = "CNEWSCONTENT", nullable = false, length = 4000)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "CHREF", length = 500)
	public String getChref() {
		return this.chref;
	}

	public void setChref(String chref) {
		this.chref = chref;
	}

	@Column(name = "ISORTORDER", nullable = false)
	public Long getIsortorder() {
		return this.isortorder;
	}

	public void setIsortorder(Long isortorder) {
		this.isortorder = isortorder;
	}

	@Column(name = "CHASANNEX", nullable = false, length = 1)
	public String getChasannex() {
		return this.chasannex;
	}

	public void setChasannex(String chasannex) {
		this.chasannex = chasannex;
	}

	@Column(name = "NCREATEID", nullable = false)
	public Long getNcreateid() {
		return this.ncreateid;
	}

	public void setNcreateid(Long ncreateid) {
		this.ncreateid = ncreateid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DCREATETIME", nullable = false)
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "NMODIFYID")
	public Long getNmodifyid() {
		return this.nmodifyid;
	}

	public void setNmodifyid(Long nmodifyid) {
		this.nmodifyid = nmodifyid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DMODIFYTIME")
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getDmodifytime() {
		return this.dmodifytime;
	}

	public void setDmodifytime(Date dmodifytime) {
		this.dmodifytime = dmodifytime;
	}

	@Column(name = "NAUDITID")
	public Long getNauditid() {
		return this.nauditid;
	}

	public void setNauditid(Long nauditid) {
		this.nauditid = nauditid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DAUDITTIME")
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getDaudittime() {
		return this.daudittime;
	}

	public void setDaudittime(Date daudittime) {
		this.daudittime = daudittime;
	}

	@Column(name = "CAUDITSTATUS", nullable = false, length = 1)
	public String getAuditStatus() {
		return this.auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	@Column(name = "CVALID", nullable = false, length = 1)
	public String getValid() {
		return this.valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	@Column(name = "CFROM", nullable = false, length = 100)
	public String getCfrom() {
		return this.cfrom;
	}

	public void setCfrom(String cfrom) {
		this.cfrom = cfrom;
	}

	@Column(name = "IVIEWCOUNT", precision = 22, scale = 0)
	public Long getIviewcount() {
		return this.iviewcount;
	}

	public void setIviewcount(Long iviewcount) {
		this.iviewcount = iviewcount;
	}

	@Column(name = "CAUDITOPINION", length = 400)
	public String getCauditopinion() {
		return this.cauditopinion;
	}

	public void setCauditopinion(String cauditopinion) {
		this.cauditopinion = cauditopinion;
	}

	@Column(name = "COMMENTNUM", length = 4)
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
	
	
}