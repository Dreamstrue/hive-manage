/**
 * 
 */
package com.hive.integralmanage.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**  
 * Filename: Integral.java  
 * Description:  积分总表  实体类
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-11  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-11 上午9:21:32				yanghui 	1.0
 */
@Entity
@Table(name = "M_integral")
public class Integral {

	// Fields

	private Long id;
	private Long userId;
	private Long currentValue;
	private Long usedValue;

	
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

	@Column(name = "userId", nullable = false)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "currentValue", nullable = false)
	public Long getCurrentValue() {
		return this.currentValue;
	}

	public void setCurrentValue(Long currentValue) {
		this.currentValue = currentValue;
	}

	@Column(name = "usedValue", nullable = false)
	public Long getUsedValue() {
		return this.usedValue;
	}

	public void setUsedValue(Long usedValue) {
		this.usedValue = usedValue;
	}

}
