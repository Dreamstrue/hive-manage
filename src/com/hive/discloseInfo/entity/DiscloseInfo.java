/**
 * 
 */
package com.hive.discloseInfo.entity;

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

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import com.hive.common.SystemCommon_Constant;

import dk.util.JsonDateTimeSerializer;

/**  
 * Filename: DiscloseInfo.java  
 * Description:  爆料信息表
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  YangHui
 * @version: 1.0  
 * @Create:  2014-7-11  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-7-11 上午10:03:20				YangHui 	1.0
 */
@Entity
@Table(name="M_DISCLOSE")
public class DiscloseInfo {
	
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 爆料主题
	 */
	private String title;
	/**
	 * 爆料内容
	 */
	private String content;
	/**
	 * 爆料时间
	 */
	private Date createTime;
	/**
	 * 审核状态
	 */
	private String auditStatus = SystemCommon_Constant.AUDIT_STATUS_0;
	/**
	 * 审核时间
	 */
	private Date auditTime;
	/**
	 * 是否屏蔽（0未屏蔽；1-屏蔽；）
	 */
	private String shieldStatus = SystemCommon_Constant.REVIEW_SHIELD_STATUS_0;
	
	/**
	 * 爆料人的ID(登录或匿名)
	 */
	private Long userId;
	/**
	 * 是否上传图片（1-存在，0-不存在）
	 */
	private String isPic;
	
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "ID", unique = true, nullable = true)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="TITLE", nullable=true,length=2000)
	public String getTitle() {
		if (!StringUtils.isNotBlank(title))
			return "无主题";
		else
			return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name="CONTENT", nullable=false,length=4000)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATETIME",nullable=false)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name="AUDITSTATUS",nullable=true,length=1)
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="AUDITTIME",nullable=true)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	@Column(name="SHIELDSTATUS",nullable=true,length=1)
	public String getShieldStatus() {
		return shieldStatus;
	}
	public void setShieldStatus(String shieldStatus) {
		this.shieldStatus = shieldStatus;
	}
	
	@Column(name="USERID",nullable=true)
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Column(name="ISPICTURE",nullable=true,length=1)
	public String getIsPic() {
		return isPic;
	}
	public void setIsPic(String isPic) {
		this.isPic = isPic;
	}
	
	
	

}
