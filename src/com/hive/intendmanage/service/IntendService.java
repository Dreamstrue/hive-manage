/**
 * 
 */
package com.hive.intendmanage.service;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.intendmanage.entity.Intend;
import com.hive.intendmanage.entity.IntendRelPrize;
import com.hive.intendmanage.model.IntendBean;
import com.hive.intendmanage.model.IntendSearchBean;
import com.hive.prizemanage.service.PrizeInfoService;
import com.hive.util.DataUtil;
import com.hive.util.DateUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

/**  
 * Filename: IntendService.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-11  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-11 上午11:00:02				yanghui 	1.0
 */
@Service
public class IntendService extends BaseService<Intend> {
	
	@Resource
	private BaseDao<Intend> intendDao;

	@Override
	protected BaseDao<Intend> getDao() {
		return intendDao;
	}

	@Resource
	private PrizeInfoService prizeInfoService;
	/**
	 * 
	 * @Description: 订单列表
	 * @author yanghui 
	 * @Created 2014-3-11
	 * @param page
	 * @param bean
	 * @return
	 */
	public DataGrid DataGrid(RequestPage page, IntendSearchBean bean) {
		StringBuffer counthql = new StringBuffer();
		StringBuffer sb = new StringBuffer();
		sb.append(" from "+getEntityName()+" where 1=1 ");
		if(!DataUtil.isEmpty(bean.getIntendNo())){
			sb.append(" and intendNo like '%"+bean.getIntendNo()+"%' ");
		}
		if(!DataUtil.isEmpty(bean.getIntendStatus())){
			sb.append(" and intendStatus='"+bean.getIntendStatus()+"' ");
		}
		if(!DataUtil.isNull(bean.getApplyTime())){
			sb.append(" and STR_TO_DATE(applyTime,'%Y-%m-%d')='"+DateUtil.dateToString(bean.getApplyTime())+"' ");
		}
		sb.append(" and intendStatus <> '"+SystemCommon_Constant.INTEND_STATUS_6+"'");
		List<Intend> list = getDao().find(page.getPage(), page.getRows(), sb.toString(), new Object[0]);
		counthql.append(" select count(*) ").append(sb);
		Long total = getDao().count(counthql.toString(), new Object[0]);
		return new DataGrid(total, list);
	}

	/**
	 * 
	 * @Description: 通过订单的ID获得订单以及订单对应的奖品信息
	 * @author yanghui 
	 * @Created 2014-3-13
	 * @param id
	 * @return
	 */
	public IntendBean getIntendInfoAndPrizeInfo(Long id) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select a,b from Intend a,IntendRelPrize b ");
		sb.append(" where a.intendNo = b.intendNo and a.id = ?");
		
		List list = getDao().find(sb.toString(), id);
		IntendBean bean = new IntendBean();
		for(Iterator it = list.iterator();it.hasNext();){
			Object[] obj = (Object[]) it.next();
			Intend intend = (Intend) obj[0];  //订单信息
			IntendRelPrize irp = (IntendRelPrize) obj[1]; //订单与奖品关联信息
			try {
				PropertyUtils.copyProperties(bean, intend);
				bean.setPrizeCateId(irp.getPrizeCateId());
				bean.setPrizeName(prizeInfoService.get(irp.getPrizeId()).getPrizeName());
				bean.setPrizeNum(irp.getPrizeNum());
				bean.setExcIntegral(irp.getExcIntegral());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bean;
	}

}
