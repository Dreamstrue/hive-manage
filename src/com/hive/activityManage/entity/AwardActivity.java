package com.hive.activityManage.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;


/**
 * 抽奖活动表
 */
@Entity
@Table(name = "A_AwardActivity", schema = "ZXT")
public class AwardActivity implements java.io.Serializable {

	// Fields

	private Long id;//抽奖活动实例id
	private Long pid;//活动实例id
//	private String isEmptyAward;//是否有空奖

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
	@Column(name = "PID", nullable = false, precision = 22, scale = 0)
	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}
}