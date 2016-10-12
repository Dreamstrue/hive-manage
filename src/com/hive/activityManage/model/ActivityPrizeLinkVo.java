package com.hive.activityManage.model;

import com.hive.prizemanage.entity.PrizeInfo;


/**
 * @description 
 * @author yyf 20160608
 */
public class ActivityPrizeLinkVo {

	private Long id;//中间表id
	private Long awardActivityId;//抽奖活动实例id
	private Long prizeId;//活动奖品id
	private String prizeInfoName;//奖品名称
	private PrizeInfo prizeVo;//活动奖品实例
//	private Long winPrizeRulesId;//中奖规则id
	private Integer prizeLevel;//活动奖品序号或等级，从1开始的自然数
	private String prizeName;//奖品等级名称
	private Integer prizeCount;//奖品数量
	private Integer remainCount;//奖品剩余数量
	private Integer prizeProbability;//中奖概率
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getAwardActivityId() {
		return awardActivityId;
	}
	public void setAwardActivityId(Long awardActivityId) {
		this.awardActivityId = awardActivityId;
	}
	public Long getPrizeId() {
		return prizeId;
	}
	public void setPrizeId(Long prizeId) {
		this.prizeId = prizeId;
	}
	public Integer getPrizeLevel() {
		return prizeLevel;
	}
	public void setPrizeLevel(Integer prizeLevel) {
		this.prizeLevel = prizeLevel;
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
	public Integer getPrizeProbability() {
		return prizeProbability;
	}
	public void setPrizeProbability(Integer prizeProbability) {
		this.prizeProbability = prizeProbability;
	}
	public PrizeInfo getPrizeVo() {
		return prizeVo;
	}
	public void setPrizeVo(PrizeInfo prizeVo) {
		this.prizeVo = prizeVo;
	}
	public String getPrizeInfoName() {
		return prizeInfoName;
	}
	public void setPrizeInfoName(String prizeInfoName) {
		this.prizeInfoName = prizeInfoName;
	}
	public Integer getRemainCount() {
		return remainCount;
	}
	public void setRemainCount(Integer remainCount) {
		this.remainCount = remainCount;
	}
	
	
	
}
