package com.hive.permissionmanage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.permissionmanage.entity.Department;
import com.hive.permissionmanage.entity.RoleMenuAction;
import com.hive.permissionmanage.entity.User;
import com.hive.permissionmanage.model.UserBean;
import com.hive.util.DataUtil;
import com.hive.util.MD5EncrpytUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class UserService extends BaseService<User> {
	private Logger logger = Logger.getLogger(UserService.class);
	
  @Resource
  private BaseDao<User> userDao;
  
  @Resource
  private DepartmentService departmentService;
  @Resource
  private RoleMenuActionService roleMenuActionService;
  
  @Resource
  private SessionFactory sessionFactory;

  protected BaseDao<User> getDao()
  {
    return this.userDao;
  }
  
  public User check(String username, String password)
  {
	  String mdPass = MD5EncrpytUtil.md5Encrypt(password, "md5");
    return (User)getDao().get("from " + getEntityName() + " where cusername=? and cpassword=?", new Object[] { username, mdPass });
  }

  public DataGrid datagrid(RequestPage page, String keys) {
	  StringBuffer sb = new StringBuffer();
		//sb.append(" from " + getEntityName() + " where 1=1 ");
		sb.append(" from " + getEntityName() + " where cvalid = " + SystemCommon_Constant.VALID_STATUS_1);
		if(!DataUtil.isEmpty(keys)){
			sb.append(" and username like '%" + keys + "%' or fullname like '%" + keys + "%'");
		}
		sb.append(" order by id asc");
		List<User> list = getDao().find(page.getPage(), page.getRows(), sb.toString(), new Object[0]);
		
		List<UserBean> userBeanList = new ArrayList<UserBean>(); 
		for (Iterator iterator1 = list.iterator(); iterator1.hasNext();)
		{
			User user = (User)iterator1.next();
			UserBean userBean = new UserBean();
			try {
				PropertyUtils.copyProperties(userBean, user); // 后者相同属性值会覆盖前者
			} catch (Exception e) {
				e.printStackTrace();
			}
			String departmentName = "";
			if (user.getDepartmentId() > 0) {
				try {
					Department department = (Department)departmentService.get(user.getDepartmentId());
					departmentName = department.getText();
				} catch (Exception e) {
					departmentName = "所属部门已被删除！";
					logger.error("所属部门已被删除：userId = " + user.getId() + ", departmentId = " + user.getDepartmentId());
				}
			}
			userBean.setDepartmentName(departmentName);
			userBeanList.add(userBean);
		}
		
		return new DataGrid(userBeanList.size(), userBeanList);
  }
  
	/**
	 * 逻辑删除
	 */
	public void logicDelete(String ids) {
		for (int i = 0; i < ids.split(",").length; i++) {
			userDao.execute("UPDATE " + getEntityName() + " SET cvalid = ? WHERE nuserid IN (" + ids + ")", SystemCommon_Constant.VALID_STATUS_0);
		}
	}
	
	public void updatePassword(long id, String password)
	{
		String mdPass = MD5EncrpytUtil.md5Encrypt(password, "md5");
		getDao().execute("update " + getEntityName() + " set password = ? where id = ?", new Object[] {mdPass, id});
	}
	
	/**
	 * 根据用户 id 获取其所属角色 id 集合
	 * @author 燕珂
	 * @param userId
	 * @return
	 */
	public String getRoleIdsByUserId(long userId) {
		String roleIds = "";
		String sql = "select t.nroleid from p_userrole t WHERE t.nuserid = " + userId;
		roleIds = this.getIdsStringBySql(sql);
		return roleIds;
	}
	
	/**
	 * 根据角色 id 获取其能访问的菜单 id 集合
	 * @author 燕珂
	 * @param roleId
	 * @return
	 */
	public String getMenuIdsByRoleId(long roleId) {
		String menuIds = "";
		String sql = "select t.nmenuid from p_rolemenu t WHERE t.nroleid = " + roleId;
		menuIds = this.getIdsStringBySql(sql);
		return menuIds;
	}
	
	/**
	 * 根据用户 id 获取其能访问的菜单 id 集合
	 * @author 燕珂
	 * @param userId
	 * @return
	 */
	public String getMenuIdsByUserId(long userId) {
		String menuIds = "";
		StringBuffer sb = new StringBuffer();
		String roleIds = getRoleIdsByUserId(userId);
		if (StringUtils.isNotBlank(roleIds)) {
			for (int i = 0; i < roleIds.split(",").length; i++) {
				String menuIdsTemp = getMenuIdsByRoleId(Long.parseLong(roleIds.split(",")[i]));
				if (StringUtils.isNotBlank(menuIdsTemp)) {
					sb.append(menuIdsTemp);
					sb.append(",");
				}
			}
			menuIds = sb.toString();
			menuIds = noRepeat(menuIds, ",");
		}
		return menuIds;
	}
	
	/**
	 * 根据用户 id ，获取其权限集合 map(key 为菜单 id，value 为菜单权限值 --> 一个用户可以同时拥有多个角色，多个角色同一菜单权限用“|”进行运算后放入 map)
	 * 本来 key 放的是 long 型的菜单 id，但是前台读取时会出现各种问题，因为前台通过 request 获取到的值都是字符串，所以就把 key 换成 String 型的菜单 id 了
	 * 受启发自：http://dhzhen2003.blog.163.com/blog/static/68980174201102724511677/
	 * @author 燕珂
	 * @param userId
	 * @return 最终返回的 map 形如：("143", 6),("188", 15)，如果没有权限集，返回 null
	 * 特别注意：对于某个菜单，如果用户对该菜单没有任何操作权限，那么该菜单值直接不存储到 map 中的，也即权限 map 中不存在 {"166", 0} 这样的元素
	 */
	public Map<String, Integer> getUserMenuActionMapByUserId(long userId) {
		Map<String, Integer> userMenuActionMap = new HashMap<String, Integer>();
		String roleIds = getRoleIdsByUserId(userId);
		if (StringUtils.isNotBlank(roleIds)) {
			List<RoleMenuAction> roleMenuActionList = new ArrayList<RoleMenuAction>();
			RoleMenuAction roleMenuAction = new RoleMenuAction();
			if (roleIds.split(",").length == 1) { // 只有一个角色
				roleMenuActionList = roleMenuActionService.getRoleMenuActionListByRoleId(Long.parseLong(roleIds));
				for (int j = 0; j < roleMenuActionList.size(); j ++) {
					roleMenuAction = roleMenuActionList.get(j);
					userMenuActionMap.put(roleMenuAction.getNmenuid().toString(), roleMenuAction.getIactionvalues());
				}
			} else {
				long roleId = 0l;
				for (int i = 0; i < roleIds.split(",").length; i ++) {
					roleId = Long.parseLong(roleIds.split(",")[i]);
					roleMenuActionList = roleMenuActionService.getRoleMenuActionListByRoleId(roleId);
					for (int j = 0; j < roleMenuActionList.size(); j ++) {
						roleMenuAction = roleMenuActionList.get(j);
						Integer actionValue = userMenuActionMap.get(roleMenuAction.getNmenuid().toString());
						if (actionValue == null)
							userMenuActionMap.put(roleMenuAction.getNmenuid().toString(), roleMenuAction.getIactionvalues());
						else
							userMenuActionMap.put(roleMenuAction.getNmenuid().toString(), actionValue | roleMenuAction.getIactionvalues());  // 模块权限已存在，进行“|”运算后覆盖
					}
				}
			}
		}
		
		/**
		 * 遍历 map
		if (userMenuActionMap.size() > 0) {
			Iterator iter = userMenuActionMap.entrySet().iterator(); 
			while (iter.hasNext()) {      
	    		Map.Entry entry = (Map.Entry) iter.next();      
	    		Long key = (Long)entry.getKey();  // key 是 String 类型      
	    		Integer value = (Integer)entry.getValue();  // value 是 String 数组类型
	    		System.out.println("key = " + key + ", value = " + value);
			}
		}
		 */
		
		return userMenuActionMap;
	}
	
	/**
	 * 通过一条 sql 得到数据库的一列，然后转成一个字符串
	 * @param sql
	 * @return
	 */
	public String getIdsStringBySql(String sql) {
		String idsStr = "";
		List list = null;  // 查询结果只有一列，放到一个 list 里（无需泛型）
		Session session = sessionFactory.getCurrentSession();
		try {
			list = session.createSQLQuery(sql).list();
		} catch (Exception e) {
			logger.error("getIdsStringBySql ERROR!", e);
		} finally {
			//this.releaseSession(session);
		}
		if (list != null && list.size() > 0) {
			idsStr = list.toString();  // 形如：[000010000500001, 00001000050000100004, 00001000050000100002, 00001000050000100003]     
			idsStr = idsStr.replace("[", "");  // 把前后的中括号去掉
			idsStr = idsStr.replace("]", "");
			idsStr = idsStr.replaceAll(" ", "");  // 把中间的空格去掉
		}
		return idsStr;
	}
	
    /**
     * 去除字符串中的重复项
     * @param str 形如：人、药物、疾病、药物、疾病、人、药物 --> 最后面多个分隔符也没事，输出结果时会截掉，但如果最前面多了个分隔符，那就不会截掉了
     * @param splitChar 分隔符，形如：、
     * @return 形如：人、药物、疾病
     */
    public static String noRepeat(String str, String splitChar) {
		if (StringUtils.isNotBlank(str)) {
			String strArray[] = str.split(splitChar); 
			StringBuilder sb = new StringBuilder(strArray[0]); 
			for(int i = 1; i < strArray.length; i ++) { 
				if((splitChar + sb.toString() + splitChar).indexOf((splitChar + strArray[i] + splitChar)) < 0) { 
					sb.append(splitChar).append(strArray[i]); 
				} 
			} 
			return sb.toString();
		} else {
			return "";
		}
	}

	public List getIdsByUserName(String userName) {
		List list = getDao().find(" select id from "+getEntityName()+" where username like '%"+userName+"%' ");
		return list;
	}
	
}
