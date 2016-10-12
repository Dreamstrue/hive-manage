package com.hive.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.hive.common.SystemCommon_Constant;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：Ryu Zheng
 * <p/>创建日期：2013-11-3
 * <p/>创建时间：下午5:25:36
 * <p/>功能描述：登记注册类型代码表实体类
 * <p/>===========================================================
 */
@Entity
@Table(name = "D_COMPANYTYPECODE")
public class CompanyTypeCode implements Serializable{

	private static final long serialVersionUID = 6465724697051140412L;
	
	// Fields
	
	/** 编号 */
	private Long id;
	/** 注册类型代码 */
	private String comTypCode;
	/** 注册类型名字 */
	private String comTypName;
	/** 注册类型父类代码 */
	private String comTypParentCode;
	/** 注册类型父类名字 */
	private String comTypParentName;
	/** 排序值 */
	private Integer sortOrder;
	/** 有效标志 */
	private String cvalid = SystemCommon_Constant.VALID_STATUS_1;

	// Constructors

	/** default constructor */
	public CompanyTypeCode() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "NCOMTYPCODEID", unique = true, nullable = true)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "CCOMTYPCODE", nullable = false, length = 10)
	public String getComTypCode() {
		return comTypCode;
	}

	public void setComTypCode(String comTypCode) {
		this.comTypCode = comTypCode;
	}

	@Column(name = "CCOMTYPNAME", nullable = false, length = 50)
	public String getComTypName() {
		return comTypName;
	}

	public void setComTypName(String comTypName) {
		this.comTypName = comTypName;
	}
	
	@Column(name = "CCOMTYPPARENTCODE", nullable = false, length = 10)
	public String getComTypParentCode() {
		return comTypParentCode;
	}

	public void setComTypParentCode(String comTypParentCode) {
		this.comTypParentCode = comTypParentCode;
	}

	@Column(name = "CCOMTYPPARENTNAME", nullable = false, length = 50)
	public String getComTypParentName() {
		return comTypParentName;
	}

	public void setComTypParentName(String comTypParentName) {
		this.comTypParentName = comTypParentName;
	}

	@Column(name = "ISORTORDER", nullable = false)
	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Column(name = "CVALID", nullable = false, length = 1)
	public String getCvalid() {
		return this.cvalid;
	}

	public void setCvalid(String cvalid) {
		this.cvalid = cvalid;
	}

}