package com.hive.permissionmanage.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hive.permissionmanage.entity.UserRole;

import dk.dao.BaseDao;
import dk.service.BaseService;

/**
 * @description 
 * @author 燕珂
 * @createtime 2013-11-1 下午12:29:49
 */
@Service
public class UserRoleService extends BaseService<UserRole> {
	@Resource
	private BaseDao<UserRole> userRoleDao;

	@Override
	protected BaseDao<UserRole> getDao() {
		return userRoleDao;
	}
	
	/**
	 * 往关联表插入数据。注意：由于事务配置关系，方法名必须以 insert 开头，否则存不到数据库里面
	 */
	public boolean insertUserRole(long userId, String[] roleIds) {
		boolean flag = true;
		try {
			userRoleDao.execute("DELETE FROM " + getEntityName() + " WHERE nuserid = " + userId); // 先删除旧的 再保存新的
			if (roleIds != null) { // 等于 null 表示前台取消了之前的绑定
				for (int i = 0; i < roleIds.length; i++) {
					UserRole UserRole = new UserRole();
					UserRole.setNuserid(userId);
					UserRole.setNroleid(Long.valueOf(roleIds[i]));
					insert(UserRole);
				}
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 根据菜单 id 获取其拥有的动作 id 集合（逗号分隔）
	 * @param userId
	 * @return
	 */
	public String getRoleIdsByUserId(long userId) {
		String roleIds = "";
		List<UserRole> userRoleList = getDao().find(" from " + getEntityName() + " where nuserId = ?", userId);
		if (userRoleList != null && userRoleList.size() > 0) {
			for (int i = 0; i < userRoleList.size(); i++) {
				roleIds += userRoleList.get(i).getNroleid() + ",";
			}
			if (!StringUtils.isNotBlank(roleIds))
				roleIds = roleIds.substring(0, roleIds.length() - 1);
		}
		return roleIds;
	}
}
