package com.hive.surveymanage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @description 通用评论对象
 * @author 燕珂
 * @createtime 2015-8-31 上午11:51:06
 */
@Entity
@Table(name = "C_OBJECT")
public class CObject {

	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 对象类型（标识从哪个系统过来的）
	 */
	private String objectType;
	/**
	 * 对象编号（从其他系统过来的对象 id）
	 */
	private String objectId;
	/**
	 * 对象名称（从其他系统过来的对象名称）
	 */
	private String objectName;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 备用属性1
	 */
	private String attribute1;
	/**
	 * 备用属性2
	 */
	private String attribute2;
	/**
	 * 备用属性3
	 */
	private String attribute3;
	/**
	 * 备用属性4
	 */
	private String attribute4;
	
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
	@Column(name = "OBJECTTYPE", length = 100)
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	@Column(name = "OBJECTID", length = 100)
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	@Column(name = "OBJECTNAME", length = 1000)
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	@Column(name = "REMARK", length = 2000)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "ATTRIBUTE1", length = 1000)
	public String getAttribute1() {
		return attribute1;
	}
	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}
	@Column(name = "ATTRIBUTE2", length = 1000)
	public String getAttribute2() {
		return attribute2;
	}
	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}
	@Column(name = "ATTRIBUTE3", length = 1000)
	public String getAttribute3() {
		return attribute3;
	}
	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}
	@Column(name = "ATTRIBUTE4", length = 1000)
	public String getAttribute4() {
		return attribute4;
	}
	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}
}
