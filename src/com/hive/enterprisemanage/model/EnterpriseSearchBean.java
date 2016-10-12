/**
 * 
 */
package com.hive.enterprisemanage.model;

/**  
 * Filename: EnterpriseSearchBean.java  
 * Description:  寻找商家 参数 bean
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-5-26  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-5-26 下午3:24:10				yanghui 	1.0
 */
public class EnterpriseSearchBean {

	/**
	 * 距离
	 */
	private Integer mNum;
	/**
	 * 经度
	 */
	private String longitude;
	/**
	 *纬度
	 *
	 */
	private String latitude;
	/**
	 * 产品类别ID 
	 */
	private Long productCategoryId;
	/**
	 * 排序
	 */
	private String sortType;
	/**
	 * 商家名称（模糊查询使用）
	 */
	private String enterpriseName;
	
	
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	public Integer getmNum() {
		return mNum;
	}
	public void setmNum(Integer mNum) {
		this.mNum = mNum;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public Long getProductCategoryId() {
		return productCategoryId;
	}
	public void setProductCategoryId(Long productCategoryId) {
		this.productCategoryId = productCategoryId;
	}
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	
	
}
