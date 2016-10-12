package com.hive.permissionmanage.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.permissionmanage.entity.RoleMenu;

import dk.dao.BaseDao;
import dk.service.BaseService;

/**
 * @description 
 * @author 燕珂
 * @createtime 2013-11-5 下午06:40:04
 */
@Service
public class RoleMenuService extends BaseService<RoleMenu> {
	@Resource
	private BaseDao<RoleMenu> roleMenuDao;

	@Override
	protected BaseDao<RoleMenu> getDao() {
		return roleMenuDao;
	}
	
	public void deleteOldRoleMenuBind(long roleId) {
		roleMenuDao.execute("DELETE FROM " + getEntityName() + " WHERE nroleid = " + roleId); // 先删除旧的 再保存新的
	}
	
	public boolean isBindByRoleIdAndMenuId(long roleId, long menuId) {
		List list = getDao().find(" from " + getEntityName() + " where nroleid = " + roleId + " and nmenuId = " + menuId);
		if (list.size() > 0)
			return true;
		else
			return false;
	}
}
