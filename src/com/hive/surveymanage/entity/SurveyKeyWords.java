package com.hive.surveymanage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
* Filename: SurveyKeyWords.java  
* Description: 问卷调查行业类别关键字（方便通过百度地图查找商户时使用）
* Copyright:Copyright (c)2014
* Company:  GuangFan 
* @author:  YangHui
* @version: 1.0  
* @Create:  2014-10-15  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2014-10-15 下午6:15:01				YangHui 	1.0
 */

@Entity
@Table(name="S_SURVEYKEYWORDS")
public class SurveyKeyWords {
	
	private Long id;
	private String industryName;
	private String keywords;
	
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
	@Column(name = "INDUSTRYNAME", nullable=false,length=20)
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	@Column(name = "KEYWORDS", nullable=false,length=20)
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	
	

}
