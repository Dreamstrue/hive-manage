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

@Entity
@Table(name="CLIENT_MENU")
public class ClientMenu {

	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 菜单名称
	 */
	private String menuName;
	/**
	 * 排序
	 */
	private int sort;
	/**
	 * 版本类型   1-企业版，2-大众版
	 */
	private String versionType;
	/**
	 * 是否可用
	 */
	private String valid = SystemCommon_Constant.SIGN_YES_1;
	/**
	 * 菜单标识（方便客户端那边显示图标）
	 */
	private String menuFlag;
	
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
	
	@Column(name = "MENUNAME", nullable = false,length=20)
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	@Column(name = "SORT", nullable = false)
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	@Column(name = "VERSIONTYPE", nullable = true,length=1)
	public String getVersionType() {
		return versionType;
	}
	public void setVersionType(String versionType) {
		this.versionType = versionType;
	}
	@Column(name = "VALID", nullable = false,length=1)
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	@Column(name = "MENUFLAG", nullable = true,length=100)
	public String getMenuFlag() {
		return menuFlag;
	}
	public void setMenuFlag(String menuFlag) {
		this.menuFlag = menuFlag;
	}
	
	
	
	
	
}
