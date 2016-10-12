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
 * SN标签库
 * 项目名称：zbt-manage    
 * 类名称：SNBase    
 * 类描述：存放所有的SN信息 
 * 创建人：WLxing    
 * 创建时间：2015-2-2 下午3:15:41    
 * 修改人：WLxing_    
 * 修改时间：2015-2-2 下午3:15:41    
 * 修改备注：    
 * @version     
 */
@Entity
@Table(name = "TAG_SNBASE")
public class SNBase {

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid") // 通过注解方式生成一个generator
	@GeneratedValue(generator = "idGenerator") // 使用generator
	private String id;	//标识编号
	
	/**
	 * 对应批次标识ID
	 */
	@Column(name = "SNBATCHID", length = 32)
	private String snBatchId;
	
	/**
	 * SN序列号
	 */
	@Column(name = "SN", length = 32)
	private String sn;	
	
	/**
	 * SN标签序号
	 */
	@Column(name = "SEQUENCENUM", length = 32)
	private Integer sequenceNum;
	
	/**
	 * 是否已分配(1：未分配   2：已分配)
	 */
	@Column(name = "ISALLOT", length = 32)
	private Integer isAllot;//	
	
	
	/**
	 * 分配时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	@Column(name ="ALLOTTIME",nullable=true, length = 17)
	private Date allotTime;
	
	/**
	 * 分配操作人
	 */
	@Column(name = "ALLOTER", length = 32)
	private String alloter;

	/**
	 * 查询路径
	 */
	@Column(name = "QUERYPATH")
	private String queryPath;
	
	/**
	 * 查询次数
	 */
	@Column(name = "QUERYNUM")
	private long queryNum;
	
	/**
	 * 黑名单状态
	 */
	@Column(name = "BLACKLIST")
	private String blackList;
	/**
	 * 标签状态
	 * 0.未印刷，1.入库(已经印刷入库)，2.出库(厂家已经提货)，3.绑定产品(厂家已经将标签绑定到产品上)，4.作废(印刷失败的标签，此状态保留使用)
	 * 5   印刷中   6    已印刷
	 */
	@Column(name = "STATUS", length = 2)
	private String status;
	
	/**
	 * 标签编号
	 */
	@Transient
	private String bqbh;
	/**
	 * SN父节点序列号
	 */
	@Column(name = "PSN", length = 32)
	private String psn;	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSnBatchId() {
		return snBatchId;
	}

	public void setSnBatchId(String snBatchId) {
		this.snBatchId = snBatchId;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Integer getSequenceNum() {
		return sequenceNum;
	}

	public void setSequenceNum(Integer sequenceNum) {
		this.sequenceNum = sequenceNum;
	}

	public Integer getIsAllot() {
		return isAllot;
	}

	public void setIsAllot(Integer isAllot) {
		this.isAllot = isAllot;
	}

	public Date getAllotTime() {
		return allotTime;
	}

	public void setAllotTime(Date allotTime) {
		this.allotTime = allotTime;
	}

	public String getAlloter() {
		return alloter;
	}

	public void setAlloter(String alloter) {
		this.alloter = alloter;
	}

	public String getQueryPath() {
		return queryPath;
	}

	public void setQueryPath(String queryPath) {
		this.queryPath = queryPath;
	}

	public String getBqbh() {
		return bqbh;
	}

	public void setBqbh(String bqbh) {
		this.bqbh = bqbh;
	}


	public long getQueryNum() {
		return queryNum;
	}

	public void setQueryNum(long queryNum) {
		this.queryNum = queryNum;
	}

	public String getBlackList() {
		return blackList;
	}

	public void setBlackList(String blackList) {
		this.blackList = blackList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPsn() {
		return psn;
	}

	public void setPsn(String psn) {
		this.psn = psn;
	}
	
}
