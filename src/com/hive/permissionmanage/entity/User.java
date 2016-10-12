package com.hive.permissionmanage.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="p_user")
public class User
{
  	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "nuserid", unique = true, nullable = true)
  private Long id;

  @Column(name="cusername", length=20, nullable=false, unique=true)
  private String username;

  @Column(name="cpassword", length=100, nullable=false)
  private String password;

  @Column(name="cfullname", length=20, nullable=true)
  private String fullname;

  @Column(name="cadmin", length=1, nullable=false)
  private String admin;

  @Column(name="cmobilephone", length=11, nullable=true)
  private String mobilephone ;
  
  @Column(name="cemail", length=100, nullable=true)
  private String email ;

  @Column(name="ndepartmentid", length=100, nullable=false)
  private Long departmentId ;
  
  @Column(name="cvalid", length=1, nullable=false)
  private String valid ;
  

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getFullname() {
	return fullname;
}

public void setFullname(String fullname) {
	this.fullname = fullname;
}

public String getAdmin() {
	return admin;
}

public void setAdmin(String admin) {
	this.admin = admin;
}

public String getMobilephone() {
	return mobilephone;
}

public void setMobilephone(String mobilephone) {
	this.mobilephone = mobilephone;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}



public Long getDepartmentId() {
	return departmentId;
}

public void setDepartmentId(Long departmentId) {
	this.departmentId = departmentId;
}

public String getValid() {
	return valid;
}

public void setValid(String valid) {
	this.valid = valid;
}

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}
  
}