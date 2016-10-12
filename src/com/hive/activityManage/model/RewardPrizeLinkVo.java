package com.hive.activityManage.model;



/**
 * @description 
 * @author yyf 20160608
 */
public class RewardPrizeLinkVo {
	private Long id;//中间表id
	private Integer prizeNum;//活动序号，从1开始的自然数
	private Long rewardId;//奖励活动实例id
	private Long prizeId;//活动奖品id
	private String prizeName;//奖品名称
	private Integer prizeCount;//奖品数量
	private Integer remainCount;//奖品剩余数量
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRewardId() {
		return rewardId;
	}
	public void setRewardId(Long rewardId) {
		this.rewardId = rewardId;
	}
	public Long getPrizeId() {
		return prizeId;
	}
	public void setPrizeId(Long prizeId) {
		this.prizeId = prizeId;
	}
	public String getPrizeName() {
		return prizeName;
	}
	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	public Integer getPrizeCount() {
		return prizeCount;
	}
	public void setPrizeCount(Integer prizeCount) {
		this.prizeCount = prizeCount;
	}
	public Integer getRemainCount() {
		return remainCount;
	}
	public void setRemainCount(Integer remainCount) {
		this.remainCount = remainCount;
	}
	public Integer getPrizeNum() {
		return prizeNum;
	}
	public void setPrizeNum(Integer prizeNum) {
		this.prizeNum = prizeNum;
	}
	
	
}
