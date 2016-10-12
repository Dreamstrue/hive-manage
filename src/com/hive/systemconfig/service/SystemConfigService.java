/**
 * 
 */
package com.hive.systemconfig.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.systemconfig.entity.SystemConfig;
import com.hive.util.DataUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

/**  
 * Filename: SystemConfigService.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-31  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-31 下午2:25:17				yanghui 	1.0
 */
@Service
public class SystemConfigService extends BaseService<SystemConfig> {

	private Logger logger = Logger.getLogger(SysObjectParameconfigService.class);
	
	@Resource
	private BaseDao<SystemConfig> systemConfigDao;
	@Override
	protected BaseDao<SystemConfig> getDao() {
		return systemConfigDao;
	}
	
	@Resource
	private SessionFactory sessionFactory;
	
	/**
	 * 
	 * @Description:  配置列表
	 * @author yanghui 
	 * @Created 2014-3-31
	 * @param page
	 * @return
	 */
	public DataGrid dataGrid(RequestPage page) {
		
		StringBuffer hql = new StringBuffer();
		StringBuffer counthql = new StringBuffer();
		hql.append(" from "+getEntityName()+" where valid ='"+SystemCommon_Constant.VALID_STATUS_1+"' and parameDefaultValue != null order by parameCategory");
		counthql.append(" select count(*) ").append(hql);
		List<SystemConfig> list = getDao().find(hql.toString(), new Object[0]);
		Long total = getDao().count(counthql.toString(), new Object[0]).longValue();
		return new DataGrid(total, list);
	}


	/**
	 * 
	 * @Description: 验证重复信息
	 * @author yanghui 
	 * @Created 2014-3-31
	 * @param parameCode
	 * @return
	 */
	public boolean isExist(String parameCode,String text) {
		boolean flag = false;
		List list = getDao().find(" from "+getEntityName()+" where "+text+"=?", parameCode);
		if(list.size()>0){
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * @Description:  修改的时验证参数名称重复
	 * @author yanghui 
	 * @Created 2014-3-31
	 * @param parameName
	 * @param id
	 * @return
	 */
	public boolean isExistWhenUpdate(String parameName, Long id) {
		boolean flag = false;
		List list = getDao().find(" from "+getEntityName()+" where parameName=?", parameName);
		if(list.size()>0){
			SystemConfig sc = (SystemConfig) list.get(0);
			if(!id.equals(sc.getId())){
				flag = true;
			}
		}
		return flag;
	}


	/**
	 * 
	 * @Description: 根据参数代码查询数据
	 * @author yanghui 
	 * @Created 2014-3-31
	 * @param systemConfigCommentIntegral
	 * @return
	 */
	public SystemConfig getIdByParameCode(String parameCode) {
		SystemConfig sc = new SystemConfig();
		List list = getDao().find(" from "+ getEntityName()+" where parameCode=?", parameCode);
		if(list.size()>0){
			sc = (SystemConfig) list.get(0);
		}
		return sc;
	}
	
	/**
	 * 
	 * @Description: 根据参数代码查询数据
	 * @author yanghui 
	 * @Created 2014-3-31
	 * @param systemConfigCommentIntegral
	 * @return
	 */
	public void deleteOldSystemConfig(String objectType, Long objectId) {
		String sql = "select t.configid from sys_object_parameconfig t WHERE t.objecttype = '" + objectType + "' and t.objectid = " + objectId;
		String systemConfigIds = this.getIdsStringBySql(sql);
		if (!DataUtil.isEmpty(systemConfigIds))
			systemConfigDao.execute("delete from " + getEntityName() + " WHERE id in (" + systemConfigIds + ")");
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

	public List<SystemConfig> getSystemConfigListByParamCategory(String paramcategory) {
		List<SystemConfig> systemConfigList = getDao().find(" from SystemConfig where parameCategory=? and parameDefaultValue != null and valid=? order by id asc ", paramcategory, SystemCommon_Constant.VALID_STATUS_1);
		return systemConfigList;
	}
	/**
	 * 
	 * @Description: 取得系统参数配置默认表对于的问卷设置的ID值
	 * @author YangHui 
	 * @Created 2014-10-17
	 * @param paramcategory
	 * @return
	 */
	public List getSystemConfigIdsByParamCategory(String paramcategory) {
		List idList = getDao().find(" select id from SystemConfig where parameCategory=? and parameDefaultValue != null and valid=? order by id asc ", paramcategory, SystemCommon_Constant.VALID_STATUS_1);
		return idList;
	}
}
