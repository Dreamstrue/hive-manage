package com.hive.sendMsg.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import com.hive.common.SystemCommon_Constant;

import dk.util.JsonDateTimeSerializer;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：pengfei Zhao
 * <p/>创建日期：2016-03-24
 * <p/>创建时间：下午5:25:36
 * <p/>功能描述：验证码的验证
 * <p/>===========================================================
 */
@Entity
@Table(name = "C_CheckCode")
public class Checkcode {

	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 手机号（标识发送验证码的手机号）
	 */
	private String mobile;
	/**
	 * 验证码
	 */
	private String checkCode;
	/**
	 * ip地址
	 */
	private String ipAdress;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 失效时间
	 */
	private Date expireTime;
	/**
	 * 验证时间
	 */
	private Date validate;
	
	/** 有效标志 */
	private String cvalid = SystemCommon_Constant.VALID_STATUS_1;
	
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
	@Column(name = "MOBILE", length = 100)
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Column(name = "CHECKCODE", length = 100)
	public String getCheckCode() {
		return checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	@Column(name = "IPADRESS", length = 100)
	public String getIpAdress() {
		return ipAdress;
	}
	public void setIpAdress(String ipAdress) {
		this.ipAdress = ipAdress;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATETIME",nullable=false)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="EXPIRETIME")
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="VALIDATE")
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getValidate() {
		return validate;
	}
	public void setValidate(Date validate) {
		this.validate = validate;
	}
	@Column(name = "CVALID", nullable = false, length = 1)
	public String getCvalid() {
		return cvalid;
	}
	public void setCvalid(String cvalid) {
		this.cvalid = cvalid;
	}
}
