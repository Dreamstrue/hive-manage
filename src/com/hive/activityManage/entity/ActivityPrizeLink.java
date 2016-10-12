package com.hive.activityManage.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * 抽奖活动奖品关联中间表
 */
@Entity
@Table(name = "A_Activity_Prize_Link", schema = "ZXT")
public class ActivityPrizeLink implements java.io.Serializable {

	// Fields

	private Long id;//中间表id
	private Long awardActivityId;//抽奖活动实例id
	private Long prizeId;//活动奖品id
	private String prizeInfoName;//奖品名称
	private Integer prizeLevel;//活动奖品序号或等级，从1开始的自然数
	private String prizeName;//奖品等级名称
	private Integer prizeCount;//奖品数量
	private Integer remainCount;//奖品剩余数量
	private Integer prizeProbability;//中奖概率

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
	
	@Column(name = "AWARDACTIVITYID", nullable = false, precision = 22, scale = 0)
	public Long getAwardActivityId() {
		return awardActivityId;
	}

	public void setAwardActivityId(Long awardActivityId) {
		this.awardActivityId = awardActivityId;
	}
	@Column(name = "PRIZEID", nullable = true, precision = 22, scale = 0)
	public Long getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(Long prizeId) {
		this.prizeId = prizeId;
	}

	@Column(name = "prizeLevel" ,nullable = true)
	public Integer getPrizeLevel() {
		return prizeLevel;
	}

	public void setPrizeLevel(Integer prizeLevel) {
		this.prizeLevel = prizeLevel;
	}
	@Column(name = "PRIZENAME", nullable = false, length = 150)
	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	@Column(name = "PRIZEINFONAME", nullable = true, length = 150)
	public String getPrizeInfoName() {
		return prizeInfoName;
	}

	public void setPrizeInfoName(String prizeInfoName) {
		this.prizeInfoName = prizeInfoName;
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

	@Column(name = "PRIZEPROBABILITY" ,nullable = true)
	public Integer getPrizeProbability() {
		return prizeProbability;
	}

	public void setPrizeProbability(Integer prizeProbability) {
		this.prizeProbability = prizeProbability;
	}
}