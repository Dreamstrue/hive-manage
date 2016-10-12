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
 * Filename: NoticeReceive.java  
 * Description: 通过公告签收实体类
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-6  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-6 下午3:30:26				yanghui 	1.0
 */
@Entity
@Table(name="INFO_NOTICE_RECEIVE",schema="ZXT")
public class NoticeReceive {


    // Fields    

     private Long id; //通知公告签收编号
     private Long noticeId; //通知公告编号
     private Date receiveDate;//签收时间（阅读时间）
     private Long userId; //签收人编号（登录用户的ID值）


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
    
    @Column(name="NOTICEID", nullable=false)
    public Long getNoticeId() {
        return this.noticeId;
    }
    
    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="RECEIVEDATE", nullable=false)
    @JsonSerialize(using=JsonDateTimeSerializer.class)
    public Date getReceiveDate() {
        return this.receiveDate;
    }
    
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }
    
    @Column(name="USERID", nullable=false)
    public Long getUserId() {
        return this.userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
