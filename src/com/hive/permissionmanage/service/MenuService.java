package com.hive.permissionmanage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.hive.permissionmanage.entity.Action;
import com.hive.permissionmanage.entity.Menu;
import com.hive.permissionmanage.model.MenuBean;

import dk.dao.BaseDao;
import dk.service.BaseService;

@Service
public class MenuService extends BaseService<Menu> {

	@Resource
	private BaseDao<Menu> menuDao;
	
	@Resource
	private MenuActionService menuActionService;
	@Resource
	private ActionService actionService;
	
	@Resource
	private RoleMenuActionService roleMenuActionService;
	
	@Resource
	private RoleMenuService roleMenuService;
	
	@Resource
	private SessionFactory sessionFactory;
//	@Resource
//	private NavMenuService navMenuService;
	
	@Override
	protected BaseDao<Menu> getDao() {
		return menuDao;
	}

	public List<Menu> allMenu(String menuIds) {
		List<Menu> list = new ArrayList<Menu>();
		if (StringUtils.isNotBlank(menuIds))
			list = getDao().find(" from " + getEntityName() + ("all".equals(menuIds) ? "" : " where id in (" + menuIds + ") ") + " order by isortorder asc");
		return list;
	}
	
	// 角色绑定菜单动作所需树
	public List<MenuBean> allMenuForBind(long roleId) {
		List<MenuBean> menuBeanList = new ArrayList<MenuBean>();
		List<Menu> menuList = getDao().find(" from " + getEntityName() + " order by isortorder asc");
		
		long neiRongGuanLiMenuId = getIdOfNeiRongGuanLiMenu();
		for (Iterator iterator1 = menuList.iterator(); iterator1.hasNext();) {
			Menu menu = (Menu)iterator1.next();
			MenuBean menuBean = new MenuBean();
			try {
				PropertyUtils.copyProperties(menuBean, menu); // 后者相同属性值会覆盖前者
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			long menuId = menu.getId();
			// 借用 checkbox 的 id 属性存放 pid（之前借用的是 value 属性，但这有可能会跟动作 checkbox 的 value 值冲突：例如父菜单 id 为2，而某个动作的 value 也为2，那么就会影响 jsp 中的判断）
			if (roleMenuService.isBindByRoleIdAndMenuId(roleId, menuId))
				menuBean.setViewCheckStr("<input type=\"checkbox\" checked=\"checked\" name=\"menu_" + menuId + "_view\" onclick=\"checkAction(this)\" id=\"" + menu.getPid() + "\" />"); 
			else
				menuBean.setViewCheckStr("<input type=\"checkbox\" name=\"menu_" + menuId + "_view\" onclick=\"checkAction(this)\" id=\"" + menu.getPid() + "\" />");
			
			// 给“内容管理”这个菜单绑定动作只是为了让其下面那些菜单用的，它本身作为一个目录不需要动作
			if (menu.getId() != neiRongGuanLiMenuId) {
				StringBuffer actionCheckStrSb = new StringBuffer();
				String actionIds = menuActionService.getActionIdsByMenuId(menu.getId());
				String[] actionIdsArray = StringUtils.split(actionIds, ",");
				int actionValues = roleMenuActionService.getActionValuesByRoleIdAndMenuId(roleId, menuId);	
				Action action = new Action();
				for (int i = 0; i < actionIdsArray.length; i++) {
					action = actionService.get(Long.parseLong(actionIdsArray[i]));
					if (action != null) { // 配置过权限后，如果某个动作被删除了，根据 id 就找不到这个动作了，下面取值会报空指针，所以此处需要判断下
						if ((actionValues & action.getIactionvalue()) > 0) {
							actionCheckStrSb.append("<input type=\"checkbox\" checked=\"checked\" name=\"menu_" + menuId + "\" value=\"" + action.getIactionvalue() + "\">" + action.getCactionname());
						} else {
							if (roleMenuService.isBindByRoleIdAndMenuId(roleId, menuId)) // 无访问权限，动作权限变成灰色
								actionCheckStrSb.append("<input type=\"checkbox\" name=\"menu_" + menu.getId() + "\" value=\"" + action.getIactionvalue() + "\">" + action.getCactionname());
							else
								actionCheckStrSb.append("<input type=\"checkbox\" disabled=\"disabled\" name=\"menu_" + menu.getId() + "\" value=\"" + action.getIactionvalue() + "\">" + action.getCactionname());
						}
					}
				}
				menuBean.setActionCheckStr(actionCheckStrSb.toString());
			}
			menuBeanList.add(menuBean);
		}
		
		// 内容管理下面的子菜单存在 F_NAVMENU 表中（实际上就是栏目列表，也即前台网站的导航） -- 等于把栏目列表“嫁接”到菜单树中
	/*	List<NavMenu> navMenuList = navMenuService.allNavMenu("all");
		for (Iterator iterator1 = navMenuList.iterator(); iterator1.hasNext();) {
			NavMenu navMenu = (NavMenu)iterator1.next();
			// 有些菜单只是前台网站用的，后台并不需要提供管理页面（这些菜单没有配置后台地址），那么就不把这些菜单（栏目）显示到内容管理下面了
	    	if (navMenu.getUrl() != null || (navMenu.getUrl() == null && SystemCommon_Constant.LEAF_NO.equals(navMenu.getLeaf()))) { // 文件夹的 url 也为 null，但是要显示（通过是否叶子节点区分）
				MenuBean menuBean = new MenuBean();
				try {
					PropertyUtils.copyProperties(menuBean, navMenu); // 后者相同属性值会覆盖前者
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				long menuId = navMenu.getId();
				// 借用 checkbox 的 id 属性存放 pid（之前借用的是 value 属性，但这有可能会跟动作 checkbox 的 value 值冲突：例如父菜单 id 为2，而某个动作的 value 也为2，那么就会影响 jsp 中的判断）
				if (roleMenuService.isBindByRoleIdAndMenuId(roleId, menuId))
					menuBean.setViewCheckStr("<input type=\"checkbox\" checked=\"checked\" name=\"menu_" + menuId + "_view\" onclick=\"checkAction(this)\" id=\"" + navMenu.getPid() + "\" />"); 
				else
					menuBean.setViewCheckStr("<input type=\"checkbox\" name=\"menu_" + menuId + "_view\" onclick=\"checkAction(this)\" id=\"" + navMenu.getPid() + "\" />");
				
				// 对于 url 为空的 navMenu，它实际上就是一个目录，不需要动作
				if (navMenu.getUrl() != null) {
					StringBuffer actionCheckStrSb = new StringBuffer();
					// 内容管理下的菜单动作都是一样的，就取“内容管理”这个菜单绑定的动作就行
					String actionIds = menuActionService.getActionIdsByMenuId(getIdOfNeiRongGuanLiMenu());
					String[] actionIdsArray = StringUtils.split(actionIds, ",");
					int actionValues = roleMenuActionService.getActionValuesByRoleIdAndMenuId(roleId, menuId);	
					Action action = new Action();
					for (int i = 0; i < actionIdsArray.length; i++) {
						action = actionService.get(Long.parseLong(actionIdsArray[i]));
						if (action != null) { // 配置过权限后，如果某个动作被删除了，根据 id 就找不到这个动作了，下面取值会报空指针，所以此处需要判断下
							if ((actionValues & action.getIactionvalue()) > 0) {
								actionCheckStrSb.append("<input type=\"checkbox\" checked=\"checked\" name=\"menu_" + menuId + "\" value=\"" + action.getIactionvalue() + "\">" + action.getCactionname());
							} else {
								if (roleMenuService.isBindByRoleIdAndMenuId(roleId, menuId)) // 无访问权限，动作权限变成灰色
									actionCheckStrSb.append("<input type=\"checkbox\" name=\"menu_" + navMenu.getId() + "\" value=\"" + action.getIactionvalue() + "\">" + action.getCactionname());
								else
									actionCheckStrSb.append("<input type=\"checkbox\" disabled=\"disabled\" name=\"menu_" + navMenu.getId() + "\" value=\"" + action.getIactionvalue() + "\">" + action.getCactionname());
							}
						}
					}
					menuBean.setActionCheckStr(actionCheckStrSb.toString());
				}
				menuBeanList.add(menuBean);
	    	}
		}*/
		
		return menuBeanList;
	}

	public List<Menu> getMenuListByName(String menuName) {
		return getDao().find(" from " + getEntityName() + " where text = ?",
				menuName);
	}
	
	/**
	 * 查询待审核信息
	 * @return
	 */
	public Map<String,String> getUncheckNumber(){
		Map<String,String> map=new HashMap<String,String>();
		//未审核会员数
		/**modify by YangHui 2014-08-21 个人会员注册时默认审核通过，一个企业下可以有多个用户，且第一个注册的为管理员用户，后台只审核管理员用户  begin */
	//	String countUncheckMember = "SELECT COUNT(*) FROM MMember WHERE cmemberstatus=0 AND cvalid='1' AND isManager='1' ";
		String countUncheckMember = "SELECT COUNT(*) FROM MMember WHERE cmemberstatus=0 AND cvalid='1'  ";
		/**modify by YangHui 2014-08-21 个人会员注册时默认审核通过，一个企业下可以有多个用户，且第一个注册的为管理员用户，后台只审核管理员用户  end  */
		//未审核企业数
		String countUncheckEnterprise = "select count(*) from EEnterpriseinfomodifytemp where cauditstatus=0 AND cvalid=1";
		//未审核资质数
		String countUncheckQualification ="select count(*) from EQualificationmodifytemp where cauditstatus=0 AND cvalid=1";
		//未审核产品数
		String countUncheckProduct="select count(*) from EEnterpriseproductmodifytemp where cauditstatus=0 AND cvalid=1";
		//未审核承诺数
		String countUncheckQualityPromise="select count(*) from QualityPromise where cauditstatus=0 AND cvalid=1";
		//未审核诚信风采数(sql查询)
		String countUncheckIntegritystyle="SELECT COUNT(*) FROM f_integritystyle i join e_enterpriseinfo ei ON ei.nenterpriseid = i.nenterpriseid join m_member mb ON i.nenterpriseid = mb.nenterpriseid WHERE i.cvalid = 1 AND i.cauditopinion='0'";
		//未审核产品发布数
		String countUncheckTradeinfo="select count(*) from MTradeinfo where cvalid=1 AND cauditstatus=0 ";
		
		map.put("注册会员审核", getDao().count(countUncheckMember).toString());
		map.put("企业基本信息审核", getDao().count(countUncheckEnterprise).toString());
		map.put("企业资质信息审核", getDao().count(countUncheckQualification).toString());
		map.put("企业产品信息审核", getDao().count(countUncheckProduct).toString());
		map.put("企业承诺书审核", getDao().count(countUncheckQualityPromise).toString());
		map.put("产品发布审核", getDao().count(countUncheckTradeinfo).toString());
		String UncheckIntegritystyleNumber = sessionFactory.getCurrentSession().createSQLQuery(countUncheckIntegritystyle).uniqueResult().toString();
		map.put("企业诚信风采审核", UncheckIntegritystyleNumber);
		
		return map;
	}

	public int getMenuByName(String menuName) {
		int size = 0;
		List<Menu> list = getMenuListByName(menuName);
		if (list != null) {
			size = list.size();
		}
		return size;
	}

	public boolean getMenuByNameAndId(long id, String menuName) {
		boolean flag = true;
		List list = getDao().find("select id from "+getEntityName()+" where text = ?", menuName);
		if(list.size()>0){
			if((Long)list.get(0) != id) {
				flag = false;
			}
		}
		return flag;
	}
	
	/**
	 * 获取名为“内容管理”的菜单的 id
	 * @return
	 */
	public Long getIdOfNeiRongGuanLiMenu() {
		long id = 0l;
		List list = getMenuListByName("内容管理");
		if (list.size() > 0){
			Menu menu = (Menu)list.get(0);
			id = menu.getId();
		}
		return id;
	}

	/**
	 * 
	 * @Description: 通过ID获得菜单
	 * @author yanghui 
	 * @param menuId 
	 * @Created 2014-1-13
	 * @return
	 */
	public List<Menu> allMenuByMenuId(Long menuId) {
		
		return getDao().find(" from "+getEntityName()+" where id=? ", menuId);
	}

}
