/**
 * 
 */
package com.hive.integralmanage.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.clientmanage.service.ClientUserService;
import com.hive.common.SystemCommon_Constant;
import com.hive.integralmanage.entity.CashPrizeInfo;
import com.hive.systemconfig.service.PrizeCategoryService;
import com.hive.util.DataUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

/**  
 * Filename: PrizeInfoService.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-6  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-6 下午4:15:46				yanghui 	1.0
 */
@Service
public class CashPrizeInfoService extends BaseService<CashPrizeInfo> {

	@Resource
	private PrizeCategoryService prizeCategoryService;
	@Resource
	private BaseDao<CashPrizeInfo> prizeInfoDao;
	@Override
	protected BaseDao<CashPrizeInfo> getDao() {
		return prizeInfoDao;
	}
	@Resource
	private ClientUserService clientUserService;
	
	/**
	 * 
	 * @Description: 奖品列表查询
	 * @author yanghui 
	 * @Created 2014-3-7
	 * @param page
	 * @param bean
	 * @return
	 */
	public DataGrid dataGrid(RequestPage page, CashPrizeInfo cashPrinfo) {
		StringBuffer hql = new StringBuffer();
		StringBuffer counthql = new StringBuffer();
		StringBuffer sb = new StringBuffer();
		
		hql.append(" from "+ getEntityName() + " where 1=1 ");
		if(!DataUtil.isEmpty(cashPrinfo.getPrizeName())){
			sb.append(" and prizeName like '%"+cashPrinfo.getPrizeName()+"%' ");
		}
		if(!DataUtil.isEmpty(cashPrinfo.getPrizeSN())){
			sb.append(" and prizeSN = '"+cashPrinfo.getPrizeSN()+"' ");
		}
		if(!DataUtil.isEmpty(cashPrinfo.getPrizeUser())){
			sb.append(" and prizeUser = '"+cashPrinfo.getPrizeUser()+"' ");
		}
		if(!DataUtil.isEmpty(cashPrinfo.getPrizePhone())){
			sb.append(" and prizePhone = '"+cashPrinfo.getPrizePhone()+"' ");
		}
		sb.append(" and valid=? order by createTime desc");
		List<CashPrizeInfo> infoList = getDao().find(page.getPage(), page.getRows(), hql.append(sb).toString(), SystemCommon_Constant.VALID_STATUS_1);
	
		counthql.append(" select count(*) from " + getEntityName()+" where 1=1 ");
		Long count = getDao().count(counthql.append(sb).toString(), SystemCommon_Constant.VALID_STATUS_1);
		
		return new DataGrid(count, infoList);
	}


	/**
	 * 
	 * @Description: 新增奖品重名判断
	 * @author yanghui 
	 * @Created 2014-3-7
	 * @param prizeName
	 * @return
	 */
	public boolean isExistPrizeName(String prizeName) {
		boolean flag = false;
		List<CashPrizeInfo> list = getDao().find(" from "+getEntityName()+" where prizeName=?", prizeName);
		if(list.size()>0){ //存在
			flag = true; 
		}
		return flag;
	}
	/**
	 * 
	 * @Description: 新增领奖重复判断
	 * @author yanghui 
	 * @param prizeName
	 * @return
	 */
	public boolean isExistPrizeSN(String prizeSN) {
		boolean flag = false;
		List<CashPrizeInfo> list = getDao().find(" from "+getEntityName()+" where prizeSN=?", prizeSN);
		if(list.size()>0){ //存在
			flag = true; 
		}
		return flag;
	}

	public List<CashPrizeInfo> queryBySN(String prizeSN){
		List<CashPrizeInfo> list = getDao().find(" from "+getEntityName()+" where prizeSN=?", prizeSN);
		return list;
	}

	/**
	 * 根据中奖信息id获取兑奖信息
	 * yyf 20160629 add
	 * @param winPrizeId
	 * @return
	 */
	public List<CashPrizeInfo> queryByWinPrizeId(String winPrizeId) {
		List<CashPrizeInfo> list = getDao().find(" from "+getEntityName()+" where winPrizeInfoId=?", winPrizeId);
		return list;
	}
}
