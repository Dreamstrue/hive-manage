/**
 * 
 */
package com.hive.systemconfig.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.systemconfig.entity.ClientMenu;
import com.hive.util.DataUtil;

import dk.dao.BaseDao;
import dk.service.BaseService;

/**  
 * Filename: ClientMenuService.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  YangHui
 * @version: 1.0  
 * @Create:  2014-8-29  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-8-29 下午4:12:12				YangHui 	1.0
 */
@Service
public class ClientMenuService extends BaseService<ClientMenu> {

	
	@Resource
	private BaseDao<ClientMenu> clientMenuDao;
	@Override
	protected BaseDao<ClientMenu> getDao() {
		return clientMenuDao;
	}
	
	
	
	public List getList() {
		String hql = " from "+getEntityName()+" order by versionType asc , sort asc ";
		List<ClientMenu> list = getDao().find(hql, new Object[0]);
		return list;
	}
	
	
	public List getValidList(String flag) {
		String hql = " from "+getEntityName()+" where valid=? and versionType=?  order by sort asc";
		List<ClientMenu> list = getDao().find(hql, new Object[]{SystemCommon_Constant.SIGN_YES_1,flag});
		return list;
	}


/**
 * 
 * @Description: 根据菜单名和版本类型 判断是否菜单名重复
 * @author YangHui 
 * @Created 2014-9-4
 * @param menuName
 * @param versionType
 * @return
 */
	public boolean checkMenuNameByVersionType(String menuName,
			String versionType) {
		String hql = " from "+getEntityName()+" where menuName=? and versionType=? ";
		List list = getDao().find(hql, new Object[]{menuName,versionType});
		if(DataUtil.listIsNotNull(list)){
			return true;
		}
		return false;
	}

}
