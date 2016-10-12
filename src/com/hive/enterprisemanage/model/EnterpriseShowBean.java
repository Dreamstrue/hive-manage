/**
 * 
 */
package com.hive.enterprisemanage.model;

import java.util.List;

import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Annex;
import com.hive.enterprisemanage.entity.EnterpriseRoomPicture;
import com.hive.enterprisemanage.entity.EnterpriseRoomShow;

/**  
 * Filename: EnterpriseShowBean.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-5-9  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-5-9 上午9:57:57				yanghui 	1.0
 */
public class EnterpriseShowBean extends EnterpriseRoomShow{
	
	/**
	 * 企业名称
	 */
	private String enterpriseName;
	/**
	 * 企业LOGO图片路径
	 */
	private String logoPath;
	
	/**
	 * 企业空间照片列表
	 */
	private List<EnterpriseRoomPicture> picList;
	/**
	 * 附件列表
	 */
	private List<Annex> annexList;
	
	/**
	 * 关注状态
	 */
	private String attenStatus = SystemCommon_Constant.SIGN_YES_0;
	/**
	 * 是否存在新的咨询信息（1-存在，0-不存在）
	 */
	private String isExistNewConsult;
	
	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
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

	public String getAttenStatus() {
		return attenStatus;
	}

	public void setAttenStatus(String attenStatus) {
		this.attenStatus = attenStatus;
	}

	public String getIsExistNewConsult() {
		return isExistNewConsult;
	}

	public void setIsExistNewConsult(String isExistNewConsult) {
		this.isExistNewConsult = isExistNewConsult;
	}
	
	
	

}
