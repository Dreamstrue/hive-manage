package com.hive.tagManage.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 批次信息
 * 项目名称：zbt-manage    
 * 类名称：SNBatch    
 * 类描述：每次生成SN的批次信息    
 * 创建人：WLxing    
 * 创建时间：2015-2-2 下午3:16:12    
 * 修改人：WLxing_    
 * 修改时间：2015-2-2 下午3:16:12    
 * 修改备注：    
 * @version     
 */
@Entity
@Table(name = "TAG_SNBATCH")
public class SNBatch {
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid") // 通过注解方式生成一个generator
	@GeneratedValue(generator = "idGenerator") // 使用generator
	private String id;	//标识编号
	
	/**
	 * 批次名称
	 */
	@Column(name = "BATCH", length = 10)
	private String batch;
	
	/**
	 * 数量
	 */
	@Column(name = "AMOUNT")
	private Integer amount;
	
	/**
	 * 状态
	 * 0:未印刷   1:印刷中   2：已印刷
	 */
	@Column(name = "STATUS", length = 1)
	private Integer status;
	
	/**
	 * 创建者
	 */
	@Column(name = "CREATER", length = 32)
	private String creater;
	
	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	@Column(name ="CREATETIME",nullable=true, length = 17)
	private Date createTime;
	
	/**
	 * 创建者姓名
	 */
	@Transient
	private String createrName;
	
	/**
	 * 单位信息（印刷的标签的单位信息）
	 */
	@Column(name = "INDUSTRYENTITYID", length = 64)
	private String industryEntityId;
	
	@Column(name = "ENTITYNAME", length = 64)
	private String entityName;

	/**
	 * 可用数量（没有分配的数量）
	 */
	@Transient
	private Long validAmount;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public Long getValidAmount() {
		return validAmount;
	}

	public void setValidAmount(Long validAmount) {
		this.validAmount = validAmount;
	}

	public String getIndustryEntityId() {
		return industryEntityId;
	}

	public void setIndustryEntityId(String industryEntityId) {
		this.industryEntityId = industryEntityId;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
}
