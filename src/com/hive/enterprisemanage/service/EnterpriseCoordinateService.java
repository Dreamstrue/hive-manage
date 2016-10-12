/**
 * 
 */
package com.hive.enterprisemanage.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.hive.enterprisemanage.entity.EEnterpriseinfo;
import com.hive.enterprisemanage.entity.EnterpriseCoordinate;
import com.hive.enterprisemanage.model.CoorDinate;
import com.hive.enterprisemanage.model.ResultContent;
import com.hive.util.DataUtil;

import dk.dao.BaseDao;
import dk.service.BaseService;

/**  
 * Filename: EnterpriseCoordinateService.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-5-19  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-5-19 下午2:16:22				yanghui 	1.0
 */
@Service
public class EnterpriseCoordinateService extends BaseService<EnterpriseCoordinate> {

	@Resource
	private BaseDao<EnterpriseCoordinate> enterpriseCoordinateDao;
	@Resource
	private EnterpriseInfoService enterpriseInfoService;
	@Override
	protected BaseDao<EnterpriseCoordinate> getDao() {
		return enterpriseCoordinateDao;
	}
	
	/**
	 * 
	 * @Description: 判断企业的地理坐标是否已经存在
	 * @author yanghui 
	 * @Created 2014-5-19
	 * @param nenterpriseid
	 * @return
	 */
	public EnterpriseCoordinate checkCoordinateByEnterId(Long nenterpriseid) {
		String hql = " from "+ getEntityName()+" where enterpriseId = ? ";
		List list = enterpriseCoordinateDao.find(hql, nenterpriseid);
		EnterpriseCoordinate coor = new EnterpriseCoordinate();
		if(DataUtil.listIsNotNull(list)){
			coor = (EnterpriseCoordinate) list.get(0);
		}
		return coor;
	}

	/**
	 * 
	 * @Description:
	 * @author yanghui 
	 * @Created 2014-5-19
	 * @param strUrl
	 */
	public void updateOrSaveCoordinate(String strUrl) {
		List<EEnterpriseinfo> enterList = enterpriseInfoService.getAllEnterpriseInfo();
		if(DataUtil.listIsNotNull(enterList)){
			URL url = null;
			for(int i=0;i<enterList.size();i++){
				EEnterpriseinfo info = enterList.get(i);
				String enterName = info.getCenterprisename();
				strUrl = strUrl+"&query="+enterName;
				try {
					url = new URL(strUrl);
					URLConnection conn = url.openConnection();
					
					BufferedReader rd = new BufferedReader(new InputStreamReader(
							conn.getInputStream()));
					String ss = "";
					StringBuffer sb = new StringBuffer();
					while ((ss = rd.readLine()) != null){
						sb.append(ss);
					}
					Gson gson = new Gson();
					ResultContent c = gson.fromJson(sb.toString(), ResultContent.class);
					List list = c.getResults();
					CoorDinate d = (CoorDinate) list.get(0);
					int length = d.getLocation().toString().length();
					String string = d.getLocation().toString().substring(1, length - 1);
					String[] str = string.split(",");
					String lat = str[0].split("=")[1]; //纬度
					String lng = str[1].split("=")[1]; //经度
					
					
					
					
					EnterpriseCoordinate coor = new EnterpriseCoordinate();
					
					//先判断是否已经存在企业的地理坐标信息，如存在，则修改
					coor = checkCoordinateByEnterId(info.getNenterpriseid());
					if(!DataUtil.isNull(coor.getId())){
						coor = this.get(coor.getId());
						coor.setLongitude(lng);
						coor.setLatitude(lat);
						update(coor);
					}else{
						coor.setEnterpriseId(info.getNenterpriseid());
						coor.setEnterpriseName(info.getCenterprisename());
						coor.setLongitude(lng);
						coor.setLatitude(lat);
						save(coor);
					}
					
					rd.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
