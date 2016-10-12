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

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import com.hive.common.SystemCommon_Constant;

import dk.util.JsonDateTimeSerializer;

/**  
 * Filename: DiscloseInfoReply.java  
 * Description: 爆料信息评论表
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  YangHui
 * @version: 1.0  
 * @Create:  2014-7-11  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-7-11 上午10:13:19				YangHui 	1.0
 */
@Entity
@Table(name = "M_DISCLOSE_REPLY")
public class DiscloseInfoReply {
	
	/**
	 * 评论表主键
	 */
	private Long id;
	/**
	 * 爆料信息主键
	 */
	private Long discloseId;
	/**
	 * 评论内容
	 */
	private String content;
	/**
	 * 评论时间
	 */
	private Date replyTime;
	/**
	 * 是否存在图片（默认0-无，1-有）
	 */
	private String isPic = SystemCommon_Constant.SIGN_YES_0;
	/**
	 * 评论人的ID
	 */
	private Long replyUserId;
	
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
	
	@Column(name="DISCLOSEID", nullable=false)
	public Long getDiscloseId() {
		return discloseId;
	}
	public void setDiscloseId(Long discloseId) {
		this.discloseId = discloseId;
	}
	@Column(name="CONTENT", nullable=false,length=4000)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="REPLYTIME",nullable=false)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}
	@Column(name="ISPICTURE", nullable=true,length=1)
	public String getIsPic() {
		return isPic;
	}
	public void setIsPic(String isPic) {
		this.isPic = isPic;
	}
	@Column(name="REPLYUSERID", nullable=true)
	public Long getReplyUserId() {
		return replyUserId;
	}
	public void setReplyUserId(Long replyUserId) {
		this.replyUserId = replyUserId;
	} 
	

	
}
