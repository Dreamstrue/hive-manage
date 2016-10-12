package com.hive.complain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * Filename: ComplainPerson.java Description: 投诉人信息 Copyright:Copyright (c)2013
 * Company: GuangFan
 * 
 * @author: yanghui
 * @version: 1.0
 * @Create: 2013-7-24 Modification History: Date Author Version
 *          ------------------------------------------------------------------
 *          2013-7-24 下午2:50:13 yanghui 1.0
 */

@Entity
@Table(name = "complain_person")
public class ComplainPerson {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "complainId", unique = true, nullable = true)
	private Long complainId;
	
	@Column(name = "firstName", length = 40, nullable = true)
	private String firstName;
	
	@Column(name = "lastName", length = 40, nullable = true)
	private String lastName;
	
	@Column(name = "name", length = 40, nullable = true)
	private String name;

	@Column(name = "cardNo", length = 18, nullable = true)
	private String cardNo;

	@Column(name = "sex", length = 10, nullable = true)
	private String sex;

	@Column(name = "phone", length = 11, nullable = true)
	private String phone;

	@Column(name = "email", length = 50, nullable = true)
	private String email;

	@Column(name = "address", length = 200, nullable = true)
	private String address;

	public Long getComplainId() {
		return complainId;
	}

	public void setComplainId(Long complainId) {
		this.complainId = complainId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
	/**
	 * 职业
	 */
	private String occupation;
	/**
	 * 工作单位
	 */
	private String workUnit;
	/**
	 * 邮政编码
	 */
	private String postCode;

	@Column(name = "OCCUPATION", length = 50, nullable = true)
	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	@Column(name = "WORKUNIT", length = 100, nullable = true)
	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}
	@Column(name = "POSTCODE", length = 6, nullable = true)
	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
	
}
