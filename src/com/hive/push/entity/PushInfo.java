package com.hive.push.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import dk.util.JsonDateTimeSerializer;

/**
 * @description 消息推送实体
 * @author 燕珂
 * @createtime 2015-8-13 下午07:42:46
 */
@Entity
@Table(name = "info_push")
public class PushInfo implements java.io.Serializable {

	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 消息主题类型（所属表名）
	 */
	private String objectType;
	/**
	 * 消息 id（所对应表记录 id）
	 */
	private Long objectId;
	/**
	 * 消息对应 url
	 */
	private String url;
	/**
	 * 消息打开方式
	 */
	private String openType;
	/**
	 * 消息接收对象
	 */
	private String receiveType;
	/**
	 * 是否定时推送
	 */
	private String isTime;
	/**
	 * 定时推送时间
	 */
	private Date pushTime;
	/**
	 * 创建人
	 */
	private Long createId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 是否有效
	 */
	private String valid;
	
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
	@Column(name = "CREATEID", precision = 22, scale = 0)
	public Long getCreateId() {
		return createId;
	}
	public void setCreateId(Long createId) {
		this.createId = createId;
	}
	@Temporal(TemporalType.TIMESTAMP) // 默认生成的是 TemporalType.DATE，想存时间的话就应该改成 TIMESTAMP
	@Column(name = "CREATETIME", length = 7)
	@JsonSerialize(using = JsonDateTimeSerializer.class) // 这一行是手动加上，不加的话存到数据库中只有日期没有时间
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "DESCRIPTION", length = 400)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name = "ISTIME", length = 1)
	public String getIsTime() {
		return isTime;
	}
	public void setIsTime(String isTime) {
		this.isTime = isTime;
	}
	@Column(name = "OBJECTID", precision = 22, scale = 0)
	public Long getObjectId() {
		return objectId;
	}
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	@Column(name = "OBJECTTYPE", length = 50)
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	@Column(name = "OPENTYPE", length = 1)
	public String getOpenType() {
		return openType;
	}
	public void setOpenType(String openType) {
		this.openType = openType;
	}
	@Temporal(TemporalType.TIMESTAMP) // 默认生成的是 TemporalType.DATE，想存时间的话就应该改成 TIMESTAMP
	@Column(name = "PUSHTIME", length = 7)
	@JsonSerialize(using = JsonDateTimeSerializer.class) // 这一行是手动加上，不加的话存到数据库中只有日期没有时间
	public Date getPushTime() {
		return pushTime;
	}
	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}
	@Column(name = "RECEIVETYPE", length = 1)
	public String getReceiveType() {
		return receiveType;
	}
	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}
	@Column(name = "TITLE", length = 200)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name = "URL", length = 400)
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Column(name = "VALID", length = 1)
	public String getValid() {
		return this.valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
}
