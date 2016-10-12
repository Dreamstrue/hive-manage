/**
 * 
 */
package com.hive.subscription.entity;

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
 * @description 热门推荐（关键词）实体
 * @author 燕珂
 * @date 2015-08-04
 */
@Entity
@Table(name = "Info_hotword")
public class HotWord implements java.io.Serializable {

	/**
	 * 编号（主键）
	 */
	private Long id;
	/**
	 * 关键词名称
	 */
	private String name;
	/**
	 * 关键词链接地址
	 */
	private String href;
	/**
	 * 有效期开始
	 */
	private Date beginTime;
	/**
	 * 有效期结束
	 */
	private Date endTime;
	/**
	 * 排序值
	 */
	private int sortOrder;
	/**
	 * 有效状态
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
	@Temporal(TemporalType.DATE) // 默认生成的是 TemporalType.DATE，想存时间的话就应该改成 TIMESTAMP
	@Column(name = "BEGINTIME", length = 7)
	//@JsonSerialize(using = JsonDateTimeSerializer.class) // 这一行是手动加上，不加的话存到数据库中只有日期没有时间
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	@Temporal(TemporalType.DATE) // 默认生成的是 TemporalType.DATE，想存时间的话就应该改成 TIMESTAMP
	@Column(name = "ENDTIME", length = 7)
	//@JsonSerialize(using = JsonDateTimeSerializer.class) // 这一行是手动加上，不加的话存到数据库中只有日期没有时间
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@Column(name = "HREF", length = 400)
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	@Column(name = "NAME", length = 100)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "SORTORDER", length = 3)
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	@Column(name = "VALID", length = 1)
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
}
