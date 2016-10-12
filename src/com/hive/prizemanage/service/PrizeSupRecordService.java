/**
 * 
 */
package com.hive.prizemanage.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.hive.permissionmanage.service.UserService;
import com.hive.prizemanage.entity.PrizeSupRecord;
import com.hive.prizemanage.model.PrizeInfoSearchBean;
import com.hive.prizemanage.model.PrizeSubRecordBean;
import com.hive.util.DataUtil;
import com.hive.util.DateUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

/**  
 * Filename: PrizeSupRecordService.java  
 * Description:  奖品补货service类
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-13  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-13 下午2:10:45				yanghui 	1.0
 */
@Service
public class PrizeSupRecordService extends BaseService<PrizeSupRecord> {

	
	@Resource
	private BaseDao<PrizeSupRecord> prizeSupRecordDao;
	@Override
	protected BaseDao<PrizeSupRecord> getDao() {
		return prizeSupRecordDao;
	}
	
	@Resource
	private PrizeInfoService prizeInfoService;
	
	@Resource
	private UserService userService;

	/**
	 * 
	 * @Description:  查询奖品补货记录
	 * @author yanghui 
	 * @Created 2014-3-13
	 * @param page
	 * @param bean
	 * @return
	 */
	public DataGrid recordDataGrid(RequestPage page, PrizeInfoSearchBean bean) {
		
		StringBuffer sb = new StringBuffer();
		StringBuffer counthql = new StringBuffer();
		sb.append(" from "+getEntityName()+" where 1=1 and prizeId = ?");
		if(!DataUtil.isNull(bean.getBeginDate())){
			sb.append(" and STR_TO_DATE(createTime,'%Y-%m-%d') >= '"+DateUtil.dateToString(bean.getBeginDate())+"'");
		}
		if(!DataUtil.isNull(bean.getEndDate())){
			sb.append(" and STR_TO_DATE(createTime,'%Y-%m-%d') <= '"+DateUtil.dateToString(bean.getEndDate())+"'");
		}
		sb.append(" order by createTime desc");
		List<PrizeSupRecord>  list = getDao().find(page.getPage(), page.getRows(), sb.toString(), bean.getPrizeId());
		List<PrizeSubRecordBean> beanList = new ArrayList<PrizeSubRecordBean>();
		for(int i=0;i<list.size();i++){
			PrizeSupRecord record = list.get(i);
			PrizeSubRecordBean rBean = new PrizeSubRecordBean();
			rBean.setPrizeName(prizeInfoService.get(bean.getPrizeId()).getPrizeName());
			rBean.setPrizeId(bean.getPrizeId());
			rBean.setId(record.getId());
			rBean.setPrizeNum(record.getPrizeNum());
			rBean.setCreateTime(record.getCreateTime());
			rBean.setUserName(userService.get(record.getCreateId()).getFullname());
			beanList.add(rBean);
		}
		
		counthql.append(" select count(*) ").append(sb);
		Long total = getDao().count(counthql.toString(), bean.getPrizeId());
		return new DataGrid(total,beanList);
	}
	
	
	/**
	 * 
	 * @Description:  根据奖品ID得到所有的补货记录
	 * @author yanghui 
	 * @Created 2014-3-24
	 * @param id
	 * @return
	 */
	public List getPrizeSupRecordByPrizeId(Long id) {
		List list = getDao().find(" from "+getEntityName()+" where prizeId=?", id);
		
		return list;
	}
	public void updatePrizeSubRecord(Long id) {
		getDao().execute("update "+getEntityName()+" set valid='0' where prizeId=?",id);
	}

}
