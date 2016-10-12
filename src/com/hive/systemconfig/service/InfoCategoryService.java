/**
 * 
 */
package com.hive.systemconfig.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.systemconfig.entity.InfoCategory;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

/**  
 * Filename: InfoCategoryService.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-2-21  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-2-21 下午4:15:51				yanghui 	1.0
 */
@Service
public class InfoCategoryService extends BaseService<InfoCategory> {

	@Resource
	private BaseDao<InfoCategory> infoCateDao;
	@Override
	protected BaseDao<InfoCategory> getDao() {
		return infoCateDao;
	}
	
	/**
	 * 
	 * @Description: 判断信息类别是否已存在
	 * @author yanghui 
	 * @Created 2014-2-21
	 * @param cateName
	 * @return
	 */
	public boolean isExistInfoCategoryByName(String cateName) {
		boolean flag =  false;
		List list = getListByInfoCateName(cateName);
		if(list.size()>0){
			flag = true;
		}
		return flag;
	}
	
	
	public List<InfoCategory> getListByInfoCateName(String cateName){
		return getDao().find(" from "+ getEntityName() + " where text=? and valid = ?", new Object[]{cateName,SystemCommon_Constant.VALID_STATUS_1});
	}

	public DataGrid dataGrid(RequestPage page, String keys) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from "+getEntityName()+" where 1=1 ");
		//查询记录总数
		StringBuffer counthql = new StringBuffer();
		counthql.append(" select count(*) from "+getEntityName()+" where 1=1 " );
		StringBuffer sb = new StringBuffer();
		
		sb.append(" and valid = '"+SystemCommon_Constant.VALID_STATUS_1+"' and parentId <> '0'");
		sb.append(" order by createTime desc");
		List<InfoCategory> list = getDao().find(page.getPage(), page.getRows(), hql.append(sb).toString(), new Object[0]);
		Long  count = getDao().count(counthql.append(sb).toString(),new Object[0]).longValue();
		return new DataGrid(count, list);
	}

	/**
	 * 
	 * @Description:修改时判断是否重名
	 * @author yanghui 
	 * @Created 2014-2-24
	 * @param cateName
	 * @param id
	 * @return
	 */
	public boolean isExistInfoCategoryByNameAndId(String cateName, Long id) {
		boolean flag = false;
		List list = getListByInfoCateName(cateName);
		if(list.size()>0){
			InfoCategory i = (InfoCategory) list.get(0);
			if(!i.getId().equals(id)){
				flag = true;
			}
		}
		return flag;
	}

	public List<InfoCategory> findInfoCateValid() {
		String hql = " from "+ getEntityName() + " where  valid=? order by createTime desc";
		return getDao().find(hql, SystemCommon_Constant.VALID_STATUS_1);
	}
	
	
	public String getNameById(Long id){
		List<InfoCategory> l = getDao().find(" from "+ getEntityName() + " where id=?", id);
		if (l != null && l.size() > 0)
			return l.get(0).getText();
		else
			return "";
	}

	
	
	public List<InfoCategory> treegrid() {
		return getDao().find(" from "+ getEntityName() + " where  valid = ? and  parentId<>'0'", SystemCommon_Constant.VALID_STATUS_1);
	}

	/**
	 *  是否存在孩子节点
	 * @param id
	 * @return
	 */
	public boolean isExistChildren(Long id) {
		boolean flag = false;
		List<InfoCategory> list = getDao().find(" from "+ getEntityName() + " where parentId = ? and valid = ?",new Object[]{id,SystemCommon_Constant.VALID_STATUS_1});
		if(list.size()>0){
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * @Description: 没有root节点的信息分类树形列表
	 * @author yanghui 
	 * @Created 2014-4-10
	 * @return
	 */
	public List<InfoCategory> findInfoCateValidNotRoot() {
		return getDao().find(" from "+ getEntityName() + " where  valid = ? and isShow=? and parentId<>'0' order by id asc ", new Object[]{SystemCommon_Constant.VALID_STATUS_1,SystemCommon_Constant.IS_SHOW_1});
	}
	
	

	/**
	 * 
	 * @Description: 通过父节点查找孩子节点
	 * @author yanghui 
	 * @Created 2014-4-10
	 * @param id
	 * @return
	 */
	public List getChildByParentId(Long id) {
		String hql = " from "+getEntityName()+" where parentId=?";
		List list = getDao().find(hql, id);
		return list;
	}

}
