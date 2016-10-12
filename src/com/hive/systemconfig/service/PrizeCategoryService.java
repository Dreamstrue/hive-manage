package com.hive.systemconfig.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.systemconfig.entity.PrizeCategory;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;
@Service
public class PrizeCategoryService extends BaseService<PrizeCategory> {

	
	@Resource
	private BaseDao<PrizeCategory> prizeCategoryDao;
	@Override
	protected BaseDao<PrizeCategory> getDao() {
		return prizeCategoryDao;
	}
	
	
	
	public DataGrid dataGrid(RequestPage page, String keys) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from "+getEntityName()+" where 1=1 ");
		//查询记录总数
		StringBuffer counthql = new StringBuffer();
		counthql.append(" select count(*) from "+getEntityName()+" where 1=1 " );
		StringBuffer sb = new StringBuffer();
		
		sb.append(" and valid = '"+SystemCommon_Constant.VALID_STATUS_1+"'");
		sb.append(" order by createTime desc");
		List<PrizeCategory> list = getDao().find(page.getPage(), page.getRows(), hql.append(sb).toString(), new Object[0]);
		Long  count = getDao().count(counthql.append(sb).toString(),new Object[0]).longValue();
		return new DataGrid(count, list);
	}
	
	
	public boolean isExistInfoCategoryByName(String cateName) {
		boolean flag =  false;
		List list = getListByCateName(cateName);
		if(list.size()>0){
			flag = true;
		}
		return flag;
	}
	
	
	
	private List getListByCateName(String cateName) {
		return getDao().find(" from "+ getEntityName() + " where cateName=? and valid = ?", new Object[]{cateName,SystemCommon_Constant.VALID_STATUS_1});
		}



	public boolean isExistInfoCategoryByNameAndId(String cateName, Long id) {
		boolean flag = false;
		List list = getListByCateName(cateName);
		if(list.size()>0){
			PrizeCategory i = (PrizeCategory) list.get(0);
			if(!i.getId().equals(id)){
				flag = true;
			}
		}
		return flag;
	}



	public List<PrizeCategory> findPrizeCateValid() {
		return getDao().find(" from "+ getEntityName() + " where  valid = ? order by createTime desc", SystemCommon_Constant.VALID_STATUS_1);
	}



	public boolean isExistChildren(Long id) {
		boolean flag =  false;
		List<PrizeCategory> list = getDao().find(" from "+getEntityName()+" where parentId =? and valid= ?",new Object[]{id,SystemCommon_Constant.VALID_STATUS_1});
		if(list.size()>0)flag = true;
		return flag;
	}



	public List<PrizeCategory> treegrid() {
		return findPrizeCateValid();
	}



	public List<PrizeCategory> findPrizeCateValidNotRoot() {
		return getDao().find(" from "+ getEntityName() + " where  valid = ?  and parentId<>'0' ", SystemCommon_Constant.VALID_STATUS_1);
	}
	
	public String getPrizeCateNameById(Long id){
		List<PrizeCategory> l = getDao().find(" from "+getEntityName()+" where id=?", id);
		if(l!=null && l.size() > 0){
			return l.get(0).getText();
		}else{
			return "";
		}
	}

}
