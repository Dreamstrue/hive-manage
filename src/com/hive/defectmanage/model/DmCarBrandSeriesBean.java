package com.hive.defectmanage.model;
// default package

import com.hive.defectmanage.entity.DmCarBrandSeries;

/**
* Description:  品牌系列表
 */
public class DmCarBrandSeriesBean extends DmCarBrandSeries{
    
     /**
 	 * 品牌名称
 	 */
     private String brandname;

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}
    


    }