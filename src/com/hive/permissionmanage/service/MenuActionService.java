package com.hive.permissionmanage.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hive.permissionmanage.entity.MenuAction;

import dk.dao.BaseDao;
import dk.service.BaseService;

/**
 * @description 
 * @author 燕珂
 * @createtime 2013-11-1 下午12:29:49
 */
@Service
public class MenuActionService extends BaseService<MenuAction> {
	@Resource
	private BaseDao<MenuAction> menuActionDao;

	@Override
	protected BaseDao<MenuAction> getDao() {
		return menuActionDao;
	}
	
	/**
	 * 往关联表插入数据。注意：由于事务配置关系，方法名必须以 insert 开头，否则存不到数据库里面
	 */
	public boolean insertMenuAction(long menuId, String[] actionIds) {
		boolean flag = true;
		try {
			menuActionDao.execute("DELETE FROM " + getEntityName() + " WHERE nmenuid = " + menuId); // 先删除旧的 再保存新的
			if (actionIds != null) { // 等于 null 表示前台取消了之前的绑定
				for (int i = 0; i < actionIds.length; i++) {
					MenuAction menuAction = new MenuAction();
					menuAction.setNmenuid(menuId);
					menuAction.setNactionid(Long.valueOf(actionIds[i]));
					insert(menuAction);
				}
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 根据菜单 id 获取其拥有的动作 id 集合（逗号分隔）
	 * @param menuId
	 * @return
	 */
	public String getActionIdsByMenuId(long menuId) {
		String actionIds = "";
		List<MenuAction> menuActionList = getDao().find(" from " + getEntityName() + " where nmenuid = ?", menuId);
		if (menuActionList != null && menuActionList.size() > 0) {
			for (int i = 0; i < menuActionList.size(); i++) {
				actionIds += menuActionList.get(i).getNactionid() + ",";
			}
			if (!StringUtils.isNotBlank(actionIds))
				actionIds = actionIds.substring(0, actionIds.length() - 1);
		}
		return actionIds;
	}
}
