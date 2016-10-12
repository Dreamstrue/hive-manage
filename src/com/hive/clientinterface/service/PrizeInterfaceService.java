/**
 * 
 */
package com.hive.clientinterface.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.prizemanage.entity.PrizeInfo;
import com.hive.util.DateUtil;

import dk.dao.BaseDao;
import dk.service.BaseService;

/**  
 * Filename: PrizeInterfaceService.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-4-3  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-4-3 上午9:08:54				yanghui 	1.0
 */
@Service
public class PrizeInterfaceService extends BaseService<PrizeInfo> {

	
	@Resource
	private BaseDao<PrizeInfo> prizeInfoDao;
	@Override
	protected BaseDao<PrizeInfo> getDao() {
		return prizeInfoDao;
	}
	
	
	
	public List<PrizeInfo> getPrizeList() {
		StringBuffer hql = new StringBuffer();
		StringBuffer sb = new StringBuffer();
		hql.append(" select a.id,a.prizeName,a.picturePath,a.excIntegral,a.prizeCateId");
		hql.append(" from "+getEntityName()+" a where ");
		hql.append(" a.valid ='"+SystemCommon_Constant.VALID_STATUS_1+"' ");
		String currentDate = DateUtil.dateToString(new Date());
		hql.append(" and (STR_TO_DATE(validDate,'%Y-%m-%d')>='"+currentDate+"' or validDate is null)");
		sb.append(" order by createTime desc " );
		List list = getDao().find(hql.append(sb).toString(),new Object[0]);
		List<PrizeInfo> inList = new ArrayList<PrizeInfo>();
		for(int i=0;i<list.size();i++){
			Object[] o = (Object[]) list.get(i);
			PrizeInfo info = new PrizeInfo();
			info.setId((Long) o[0]);
			info.setPrizeName((String) o[1]);
			info.setPicturePath((String) o[2]);
			info.setExcIntegral((Long) o[3]);
			info.setPrizeCateId((Long) o[4]);
			inList.add(info);
		}
		return inList;
	}

}
