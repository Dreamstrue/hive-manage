package com.hive.enterprisemanage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.hive.enterprisemanage.entity.EEnterpriseinfo;
import com.hive.enterprisemanage.entity.EnterpriseCoordinate;
import com.hive.enterprisemanage.model.CoorDinate;
import com.hive.enterprisemanage.model.ResultContent;
import com.hive.enterprisemanage.service.EnterpriseCoordinateService;
import com.hive.enterprisemanage.service.EnterpriseInfoService;
import com.hive.util.DataUtil;

/**
 * 
 */

/**
 * Filename: EnterpriseCoordinateTimeTask.java Description: Copyright:Copyright
 * (c)2014 Company: GuangFan
 * 
 * @author: yanghui
 * @version: 1.0
 * @Create: 2014-5-16 Modification History: Date Author Version
 *          ------------------------------------------------------------------
 *          2014-5-16 下午4:26:54 yanghui 1.0
 */
@Component
public class EnterpriseCoordinateTimeTask {
	
	@Resource
	private EnterpriseCoordinateService enterpriseCoordinateService;
	@Resource
	private EnterpriseInfoService enterpriseInfoService;
	
	private static final String strUrl = "http://api.map.baidu.com/place/v2/search?ak=vgcxTSA6iwaxPlAD9cnW76sZ&output=json&page_size=10&page_num=0&scope=1&region=河南省"; 

	public void execute() {
		List<EEnterpriseinfo> enterList = enterpriseInfoService.getAllEnterpriseInfo();
		if(DataUtil.listIsNotNull(enterList)){
			URL url = null;
			for(int i=0;i<enterList.size();i++){
				EEnterpriseinfo info = enterList.get(i);
				String enterName = info.getCenterprisename();
				try {
					url = new URL(strUrl+"&query="+enterName);
					URLConnection conn = url.openConnection();
					
					BufferedReader rd = new BufferedReader(new InputStreamReader(
							conn.getInputStream(),"UTF-8"));
					String ss = "";
					StringBuffer sb = new StringBuffer();
					while ((ss = rd.readLine()) != null){
						sb.append(ss);
					}
	//				System.out.println(sb);
					Gson gson = new Gson();
					ResultContent c = gson.fromJson(sb.toString(), ResultContent.class);
					if(c!=null){
						List list = c.getResults();
						if(list.size()>0 && list!=null){
							CoorDinate d = (CoorDinate) list.get(0);
							Object location = d.getLocation();
							String lat = "";
							String lng="";
							if(location!=null){
								int length = d.getLocation().toString().length();
								String string = d.getLocation().toString().substring(1, length - 1);
								String[] str = string.split(",");
								lat = str[0].split("=")[1]; //纬度
								lng = str[1].split("=")[1]; //经度
							}
							
							EnterpriseCoordinate coor = new EnterpriseCoordinate();
							
							//先判断是否已经存在企业的地理坐标信息，如存在，则修改
							coor = enterpriseCoordinateService.checkCoordinateByEnterId(info.getNenterpriseid());
							if(!"".equals(lat) && !"".equals(lng)){
								if(!DataUtil.isNull(coor.getId())){
									coor = enterpriseCoordinateService.get(coor.getId());
									coor.setLongitude(lng);
									coor.setLatitude(lat);
									enterpriseCoordinateService.update(coor);
								}else{
									coor.setEnterpriseId(info.getNenterpriseid());
									coor.setEnterpriseName(info.getCenterprisename());
									coor.setLongitude(lng);
									coor.setLatitude(lat);
									enterpriseCoordinateService.save(coor);
								}
							}
						}
					}
					
					rd.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
