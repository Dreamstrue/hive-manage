package com.hive.permissionmanage.model;

import javax.persistence.Column;

/**
 * @description 
 * @author 燕珂
 * @createtime 2013-11-4 下午04:56:47
 */
public class MenuBean {
	
	private Long id;

	private String text;

	private String url;

	private int seq;

	private String iconCls;

	private Long pid;
	
	// 上面的属性与 Menu.java 中的属性相同，以下是辅助字段
	
	private String viewCheckStr;
	
	private String actionCheckStr;

	public String getActionCheckStr() {
		return actionCheckStr;
	}

	public void setActionCheckStr(String actionCheckStr) {
		this.actionCheckStr = actionCheckStr;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getViewCheckStr() {
		return viewCheckStr;
	}

	public void setViewCheckStr(String viewCheckStr) {
		this.viewCheckStr = viewCheckStr;
	}
}
