package com.hive.enterprisemanage.entity;

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
* 
* Filename: EnterpriseRoomPicture.java  
* Description:  企业空间照片表
* Copyright:Copyright (c)2014
* Company:  GuangFan 
* @author:  yanghui
* @version: 1.0  
* @Create:  2014-5-9  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2014-5-9 上午9:37:41				yanghui 	1.0
 */
@Entity
@Table(name="E_ENT_ROOM_PIC")
public class EnterpriseRoomPicture {

	/**
	 * 企业空间图片表主键
	 */
	private Long id ;
	/**
	 * 企业秀表的主键
	 */
	private Long parentId;
	/**
	 * 图片路径
	 */
	private String picturePath;
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
	
	@Column(name="PARENTID", nullable=false)
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	@Column(name="PICTPATH", nullable=false,length=255)
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
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
	
	@Column(name="VALID",nullable=false,length=1)
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	
	
}
