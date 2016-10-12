package com.hive.permissionmanage.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.permissionmanage.entity.RoleMenu;
import com.hive.permissionmanage.entity.RoleMenuAction;

import dk.dao.BaseDao;
import dk.service.BaseService;

/**
 * @description 
 * @author 燕珂
 * @createtime 2013-11-5 下午05:46:49
 */
@Service
public class RoleMenuActionService extends BaseService<RoleMenuAction> {
	@Resource
	private BaseDao<RoleMenuAction> roleMenuActionDao;

	@Override
	protected BaseDao<RoleMenuAction> getDao() {
		return roleMenuActionDao;
	}
	
	@Resource
	private RoleMenuService roleMenuService;
	
	/**
	 * 往关联表插入数据。注意：由于事务配置关系，方法名必须以 insert 开头，否则存不到数据库里面
	 */
	public boolean insertRoleMenuAction(long roleId, List<RoleMenu> roleMenuList, List<RoleMenuAction> roleMenuActionList) {
		boolean flag = true;
		try {
			roleMenuService.deleteOldRoleMenuBind(roleId);
			if (roleMenuList != null && roleMenuList.size() > 0) { // 等于 null 表示前台取消了之前的绑定
				for (int i = 0; i < roleMenuList.size(); i++) {
					RoleMenu roleMenu = roleMenuList.get(i);
					roleMenuService.insert(roleMenu);
				}
			}
			
			roleMenuActionDao.execute("DELETE FROM " + getEntityName() + " WHERE nroleid = " + roleId); // 先删除旧的 再保存新的
			if (roleMenuActionList != null && roleMenuActionList.size() > 0) { // 等于 null 表示前台取消了之前的绑定
				for (int i = 0; i < roleMenuActionList.size(); i++) {
					RoleMenuAction roleMenuAction = roleMenuActionList.get(i);
					insert(roleMenuAction);
				}
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 根据角色 id 及菜单 id 获取它们对应的动作集合值
	 * @param roleId
	 * @param menuId
	 * @return
	 */
	public int getActionValuesByRoleIdAndMenuId(long roleId, long menuId) {
		int actionValues = 0;
		List list = getDao().find("select iactionvalues from " + getEntityName() + " where nroleid = " + roleId + " and nmenuId = " + menuId);
		if (list.size() > 0)
			actionValues = (Integer)list.get(0);
		return actionValues;
	}
	
	/**
	 * 根据角色 id 获取它对应的菜单动作值
	 * @param roleId
	 * @return
	 */
	public List<RoleMenuAction> getRoleMenuActionListByRoleId(long roleId) {
		List<RoleMenuAction> roleMenuActionList = new ArrayList<RoleMenuAction>();
		String hql = "from " + getEntityName() + " where nroleid = " + roleId;
		roleMenuActionList = getDao().find(hql);
		return roleMenuActionList;
	}
}
