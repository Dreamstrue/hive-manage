package com.hive.defectmanage.model;
// default package

import com.hive.defectmanage.entity.DmCarBrandSeries;
import com.hive.defectmanage.entity.DmCarBrandSeriesModel;

/**
* Description:  品牌系列表
 */
public class DmCarBrandSeriesModelBean extends DmCarBrandSeriesModel{
    
	/**
 	 * 品牌名称
 	 */
    private String brandname;;
	/**
 	 * 系列名称
 	 */
    private String seriesname;;

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public String getSeriesname() {
		return seriesname;
	}

	public void setSeriesname(String seriesname) {
		this.seriesname = seriesname;
	}
    


    }