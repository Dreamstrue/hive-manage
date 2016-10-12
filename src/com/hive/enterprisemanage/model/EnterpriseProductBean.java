/**
 * 
 */
package com.hive.enterprisemanage.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hive.common.entity.Annex;
import com.hive.enterprisemanage.entity.EnterpriseRoomPicture;
import com.hive.enterprisemanage.entity.EnterpriseRoomProduct;

/**  
 * Filename: EnterpriseProductBean.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-5-13  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-5-13 下午3:55:36				yanghui 	1.0
 */
public class EnterpriseProductBean extends EnterpriseRoomProduct {
	
	/**
	 * 产品的图片路径
	 */
	private String productPicPath = StringUtils.EMPTY;
	
	private String discountInfo = StringUtils.EMPTY;
	
	private List<EnterpriseRoomPicture> picList;
	
	/**
	 * 附件列表
	 */
	private List<Annex> annexList;

	public String getDiscountInfo() {
		return discountInfo;
	}

	public void setDiscountInfo(String discountInfo) {
		this.discountInfo = discountInfo;
	}

	public List<EnterpriseRoomPicture> getPicList() {
		return picList;
	}

	public void setPicList(List<EnterpriseRoomPicture> picList) {
		this.picList = picList;
	}

	public List<Annex> getAnnexList() {
		return annexList;
	}

	public void setAnnexList(List<Annex> annexList) {
		this.annexList = annexList;
	}

	public String getProductPicPath() {
		return productPicPath;
	}

	public void setProductPicPath(String productPicPath) {
		this.productPicPath = productPicPath;
	}
	
	
	
	

}
