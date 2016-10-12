package com.hive.activityManage.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * 奖励活动奖品关联中间表
 */
@Entity
@Table(name = "A_Reward_Prize_Link", schema = "ZXT")
public class RewardPrizeLink implements java.io.Serializable {

	// Fields

	private Long id;//中间表id
	private Integer prizeNum;//活动序号，从1开始的自然数
	private Long rewardId;//奖励活动实例id
	private Long prizeId;//活动奖品id
	private String prizeName;//奖品名称
	private Integer prizeCount;//奖品数量
	private Integer remainCount;//奖品剩余数量

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@Column(name = "ID", unique = true, nullable = true)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "REWARDID", nullable = false, precision = 22, scale = 0)
	public Long getRewardId() {
		return rewardId;
	}

	public void setRewardId(Long rewardId) {
		this.rewardId = rewardId;
	}

	@Column(name = "PRIZEID", nullable = true, precision = 22, scale = 0)
	public Long getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(Long prizeId) {
		this.prizeId = prizeId;
	}
	@Column(name = "PRIZENAME", nullable = false, length = 150)
	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	@Column(name = "PRIZECOUNT" ,nullable = true)
	public Integer getPrizeCount() {
		return prizeCount;
	}

	public void setPrizeCount(Integer prizeCount) {
		this.prizeCount = prizeCount;
	}
	@Column(name = "REMAINCOUNT" ,nullable = true)
	public Integer getRemainCount() {
		return remainCount;
	}

	public void setRemainCount(Integer remainCount) {
		this.remainCount = remainCount;
	}
	@Column(name = "prizeLevel" ,nullable = true)
	public Integer getPrizeNum() {
		return prizeNum;
	}

	public void setPrizeNum(Integer prizeNum) {
		this.prizeNum = prizeNum;
	}
	
}