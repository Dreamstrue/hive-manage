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

/**  
 * Filename: MemberGrade.java  
 * Description:  会员等级表
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-31  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-31 下午5:28:45				yanghui 	1.0
 */
@Entity
@Table(name="C_MEMBERGRADE")
public class MemberGrade {
	private Long id;
	private String gradeName;//等级名称
	private Long gradeIntegral; //等级积分
	private String rewardMultiple; //奖励倍数
	
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
	@Column(name="GRADENAME",nullable=false,length=20)
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	
	@Column(name="GRADEINTEGRAL",nullable=false)
	public Long getGradeIntegral() {
		return gradeIntegral;
	}
	public void setGradeIntegral(Long gradeIntegral) {
		this.gradeIntegral = gradeIntegral;
	}
	
	@Column(name="REWARDMULTIPLE",nullable=true)
	public String getRewardMultiple() {
		return rewardMultiple;
	}
	public void setRewardMultiple(String rewardMultiple) {
		this.rewardMultiple = rewardMultiple;
	}
	
	
	
	
	

}
