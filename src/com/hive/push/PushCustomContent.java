package com.hive.push;

/**
 * @Description 推送消息中的自定义内容对象
 * @Created 2014-6-10
 * @author Ryu Zheng
 * 
 */
public class PushCustomContent {

	/** 行为(用于表示打开哪个功能模块) */
	private String behavior;
	/** 标识 (用于表示打开该功能模块中哪个明细页面)*/
	private String id;

	public String getBehavior() {
		return behavior;
	}

	public void setBehavior(String behavior) {
		this.behavior = behavior;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
