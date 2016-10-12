/**
 * 
 */
package com.hive.systemconfig.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.hive.common.SystemCommon_Constant;

/**  
 * Filename: QuestionCatagory.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  YangHui
 * @version: 1.0  
 * @Create:  2014-10-23  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-10-23 下午3:07:01				YangHui 	1.0
 */
@Entity
@Table(name="S_QUESTIONCATEGORY")
public class QuestionCategory {

	
	private Long id;
	private Long parentId;
	private String text;
	private int sort;
	private String valid = SystemCommon_Constant.VALID_STATUS_1;
	
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
	@Column(name = "PARENTID", nullable = false)
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	@Column(name = "TEXT", nullable = false,length=100)
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "SORT", nullable = false,length=4)
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	
	@Column(name = "VALID", nullable = false,length=1)
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	
	
}
