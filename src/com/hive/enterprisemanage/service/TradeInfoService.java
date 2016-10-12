package com.hive.enterprisemanage.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.enterprisemanage.entity.MTradeinfo;
import com.hive.util.DataUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;
/**
 * 
* Filename: MTradeInfoService.java  
* Description: 供求关系服务类
* Copyright:Copyright (c)2013
* Company:  GuangFan 
* @author:  yanghui
* @version: 1.0  
* @Create:  2013-10-30  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2013-10-30 下午2:59:45				yanghui 	1.0
 */
@Service
public class TradeInfoService extends BaseService<MTradeinfo> {

	@Resource
	private BaseDao<MTradeinfo> tradeInfoDao;
	
	@Override
	protected BaseDao<MTradeinfo> getDao() {
		return tradeInfoDao;
	}

	public DataGrid dataGrid(RequestPage page, String keys,String status) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from "+getEntityName()+" where 1=1 AND cauditstatus='"+status+"' ");
		//查询记录总数
		StringBuffer counthql = new StringBuffer();
		counthql.append(" select count(*) from "+getEntityName()+" where 1=1 AND cauditstatus='"+status+"' ");
		
		StringBuffer sb = new StringBuffer();
		if(!DataUtil.isEmpty(keys)){
			sb.append(" and title like '%"+keys+"%'");
		}
		sb.append(" and cvalid = '"+SystemCommon_Constant.VALID_STATUS_1+"'");
		sb.append(" order by dcreatetime desc ,cbs,cauditstatus  asc");
		List<MTradeinfo> list = getDao().find(page.getPage(), page.getRows(), hql.append(sb).toString(), new Object[0]);
		List<MTradeinfo> newList = new ArrayList<MTradeinfo>();
		for(int i=0;i<list.size();i++){
			MTradeinfo caution = list.get(i);
			String title = caution.getCtitle();
			if(title.length()>Integer.valueOf(SystemCommon_Constant.TITLE_SHOW_LENGTH)){
				title = title.substring(0,20)+"......";
			}
			caution.setCtitle(title);
			newList.add(caution);
		}
		Long  count = getDao().count(counthql.append(sb).toString(),new Object[0]).longValue();
		return new DataGrid(count, newList);
	}

}
