package com.hive.subscription.model;

import com.hive.subscription.entity.InfoSubscription;

public class InfoSubscriptionBean extends InfoSubscription{
	
	private Long count;   //某个用户订阅的数量
	private Long subCount; //针对统计时，某类分类信息的总共订阅次数
	private Long unsubCount;//针对统计时，某类分类信息的总共退订次数
	private String infoCateName; //类别名称

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getSubCount() {
		return subCount;
	}

	public void setSubCount(Long subCount) {
		this.subCount = subCount;
	}

	public Long getUnsubCount() {
		return unsubCount;
	}

	public void setUnsubCount(Long unsubCount) {
		this.unsubCount = unsubCount;
	}

	public String getInfoCateName() {
		return infoCateName;
	}

	public void setInfoCateName(String infoCateName) {
		this.infoCateName = infoCateName;
	}
	
	

	
}
