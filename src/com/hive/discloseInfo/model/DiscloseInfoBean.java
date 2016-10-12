package com.hive.discloseInfo.model;

import java.util.List;

import com.hive.common.entity.Annex;
import com.hive.discloseInfo.entity.DiscloseInfo;

public class DiscloseInfoBean extends DiscloseInfo {
	
	
	/**
	 * 列表图片路径
	 */
	public String picPath;
	/**
	 * 评论次数
	 */
	public Long replyNum = 0L;
	
	/**
	 * 图片列表
	 */
	public List<Annex> annexList;
	

	public List<Annex> getAnnexList() {
		return annexList;
	}

	public void setAnnexList(List<Annex> annexList) {
		this.annexList = annexList;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public Long getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(Long replyNum) {
		this.replyNum = replyNum;
	}

}
