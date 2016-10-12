package com.hive.infomanage.model;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.hive.common.entity.Annex;

import dk.util.JsonDateTimeSerializer;

/**
 * 
* Filename: newsCommentBean.java  
* Description:  新增资讯评论  Bean  
* Copyright:Copyright (c)2014
* Company:  GuangFan 
* @author:  yanghui
* @version: 1.0  
* @Create:  2014-3-10  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2014-3-10 下午3:47:30				yanghui 	1.0
 */
public class NewsCommentBean {

	
	/**
	 * 评论编号
	 */
	private Long id;  
	/**
	 * 新闻资讯的编号
	 */
    private Long newsId; 
    /**
     *评论人的编号
     */
    private Long userId; 
    /**
     * 评论时间
     */
    private Date commentDate;
    /**
     * 评论内容
     */
    private String content; 
    /**
     * 评论人(个人：昵称；企业：简称）
     */
    private String userName; 
    
    private Long inteCateId;  //积分类别ID
    /**
     * 由于图片新闻和通知公告的内容不属于通用内容管理部分，所以在保存评论内容时，需要
     * 通过这个字段区分    方便以后网站查询使用  
     * 如果是通过公告评论设为 INFO_NOTICE
     * 如果是图片新闻评论设为  F_PICTURENEWS
     * 其他的为通用内容评论设为  null
     */
    private String entityName;
    
    /**
     * 被评论新闻的标题
     */
    private String sourceTitle;
    /**
     * 用户头像地址
     */
    private String headPicPath;
    /**
     * 图片附件列表
     */
    private List<Annex> annexList;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNewsId() {
		return newsId;
	}

	public void setNewsId(Long newsId) {
		this.newsId = newsId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getInteCateId() {
		return inteCateId;
	}

	public void setInteCateId(Long inteCateId) {
		this.inteCateId = inteCateId;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getSourceTitle() {
		return sourceTitle;
	}

	public void setSourceTitle(String sourceTitle) {
		this.sourceTitle = sourceTitle;
	}

	public String getHeadPicPath() {
		return headPicPath;
	}

	public void setHeadPicPath(String headPicPath) {
		this.headPicPath = headPicPath;
	}

	public List<Annex> getAnnexList() {
		return annexList;
	}

	public void setAnnexList(List<Annex> annexList) {
		this.annexList = annexList;
	}
	
	
    
    
}
