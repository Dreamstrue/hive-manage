/**
 * 
 */
package com.hive.infomanage.entity;

import static javax.persistence.GenerationType.SEQUENCE;

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
 * Filename: NewsComment.java  
 * Description: 新闻资讯评论实体类
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-6  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-6 下午3:26:01				yanghui 	1.0
 */
@Entity
@Table(name="INFO_NEWS_COMMENT",schema="ZXT")
public class NewsComment {


    // Fields    

     private Long id;  //评论编号
     private Long newsId; //新闻资讯的编号
     private Long userId; //评论人的编号
     private Date commentDate; //评论时间
     private String content; //评论内容
     /**
      * 由于图片新闻和通知公告的内容不属于通用内容管理部分，所以在保存评论内容时，需要
      * 通过这个字段区分    方便以后网站查询使用  
      * 如果是通过公告评论设为 INFO_NOTICE
      * 如果是图片新闻评论设为  F_PICTURENEWS
      * 其他的为通用内容评论设为  null
      */
     private String entityName;


   
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
    
    @Column(name="newsId", nullable=false)
    public Long getNewsId() {
        return this.newsId;
    }
    
    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }
    
    @Column(name="userId", nullable=false)
    public Long getUserId() {
        return this.userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="commentDate")
    @JsonSerialize(using=JsonDateTimeSerializer.class)
    public Date getCommentDate() {
        return this.commentDate;
    }
    
    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }
    
    @Column(name="content", nullable=false, length=500)
    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    @Column(name="ENTITYNAME", length=100)
	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
   








}
