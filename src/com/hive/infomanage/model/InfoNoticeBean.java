package com.hive.infomanage.model;

import com.hive.infomanage.entity.InfoNotice;

public class InfoNoticeBean extends InfoNotice {
	
	private String infoCateName; //信息类别名称
	private int readNum;// 已阅次数

	public String getInfoCateName() {
		return infoCateName;
	}

	public void setInfoCateName(String infoCateName) {
		this.infoCateName = infoCateName;
	}

	public int getReadNum() {
		return readNum;
	}

	public void setReadNum(int readNum) {
		this.readNum = readNum;
	}
	
	/** 追加字段  方便客户端处理 */
	private String readStatus;

	public String getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}
}
