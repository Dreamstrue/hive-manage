package com.hive.defectmanage.model;
// default package

import com.hive.defectmanage.entity.DmCarBrandSeries;
import com.hive.defectmanage.entity.DmCarBrandSeriesModel;
import com.hive.defectmanage.entity.DmCarBrandSeriesModelDetail;

/**
* Description:  品牌系列表
 */
public class DmCarBrandSeriesModelDetailBean extends DmCarBrandSeriesModelDetail{
    
	/**
 	 * 品牌名称
 	 */
    private String brandname;
	/**
 	 * 系列名称
 	 */
    private String seriesname;
    /**
 	 * 车型名称
 	 */
    private String modelname;
    
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

	public String getModelname() {
		return modelname;
	}

	public void setModelname(String modelname) {
		this.modelname = modelname;
	}
    


    }