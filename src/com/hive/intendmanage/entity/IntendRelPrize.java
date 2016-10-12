/**
 * 
 */
package com.hive.intendmanage.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**  
 * Filename: IntendRelPrize.java  
 * Description: 订单与奖品关联表 
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-11  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-11 上午10:55:24				yanghui 	1.0
 */
@Entity
@Table(name = "M_intendRelPrize")
public class IntendRelPrize {

	// Fields

	private Long id;
	private String intendNo;
	private Long prizeId;
	private Long prizeNum;
	private Long prizeCateId;
	private Long excIntegral;

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

	@Column(name = "intendNo", nullable = false, length = 30)
	public String getIntendNo() {
		return this.intendNo;
	}

	public void setIntendNo(String intendNo) {
		this.intendNo = intendNo;
	}

	@Column(name = "prizeId", nullable = false)
	public Long getPrizeId() {
		return this.prizeId;
	}

	public void setPrizeId(Long prizeId) {
		this.prizeId = prizeId;
	}

	@Column(name = "prizeNum", nullable = false)
	public Long getPrizeNum() {
		return this.prizeNum;
	}

	public void setPrizeNum(Long prizeNum) {
		this.prizeNum = prizeNum;
	}

	@Column(name = "prizeCateId", nullable = false)
	public Long getPrizeCateId() {
		return this.prizeCateId;
	}

	public void setPrizeCateId(Long prizeCateId) {
		this.prizeCateId = prizeCateId;
	}

	@Column(name = "excIntegral", nullable = false)
	public Long getExcIntegral() {
		return this.excIntegral;
	}

	public void setExcIntegral(Long excIntegral) {
		this.excIntegral = excIntegral;
	}

}
