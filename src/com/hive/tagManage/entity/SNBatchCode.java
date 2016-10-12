package com.hive.tagManage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 用于更新批次
 * 项目名称：zbt-manage    
 * 类名称：SNBatchCode    
 * 类描述：    
 * 创建人：WLxing    
 * 创建时间：2015-2-2 下午3:17:38    
 * 修改人：WLxing_    
 * 修改时间：2015-2-2 下午3:17:38    
 * 修改备注：    
 * @version     
 */
@Entity
@Table(name = "TAG_BATCHCODE")
public class SNBatchCode {
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid") // 通过注解方式生成一个generator
	@GeneratedValue(generator = "idGenerator") // 使用generator
	private String id;	//标识编号
	
	/**
	 * 批次编号的前半部分（如:批次编号1501231      “150123”为前半部分）
	 */
	@Column(name = "BATCHFIRST", length = 8)
	private String batchFirst;
	
	/**
	 * 批次编号的后半部分（如:批次编号1501231      “1”为后半部分）
	 */
	@Column(name = "BATCHLAST", length = 6)
	private String batchLast;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBatchFirst() {
		return batchFirst;
	}

	public void setBatchFirst(String batchFirst) {
		this.batchFirst = batchFirst;
	}

	public String getBatchLast() {
		return batchLast;
	}

	public void setBatchLast(String batchLast) {
		this.batchLast = batchLast;
	}	
	
	
}
