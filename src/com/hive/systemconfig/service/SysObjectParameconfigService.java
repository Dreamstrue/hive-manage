/**
 * 
 */
package com.hive.systemconfig.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hive.systemconfig.entity.SysObjectParameconfig;
import com.hive.systemconfig.entity.SystemConfig;
import com.hive.util.DataUtil;

import dk.dao.BaseDao;
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
public class SysObjectParameconfigService extends BaseService<SysObjectParameconfig> {
	private Logger logger = Logger.getLogger(SysObjectParameconfigService.class);
	
	@Resource
	private BaseDao<SysObjectParameconfig> sysObjectParameconfigDao;
	@Override
	protected BaseDao<SysObjectParameconfig> getDao() {
		return sysObjectParameconfigDao;
	}
	
	@Resource
	private SystemConfigService systemConfigService;
	
	/**
	 * 
	 * @Description: 根据参数代码查询数据
	 * @author yanghui 
	 * @Created 2014-3-31
	 * @param systemConfigCommentIntegral
	 * @return
	 */
	public void deleteOldObjectConfig(String objectType, Long objectId) {
		sysObjectParameconfigDao.execute("delete from " + getEntityName() + " WHERE objecttype = ? and objectid = ? )", objectType, objectId);
	}
	
	/**
	 * 
	 * @param objectType
	 * @param paramcategory
	 * @param objectId
	 * @return
	 */
	public Map<String, Long> getObjectSetById(String objectType, String paramcategory, Long objectId) {
		Map<String, Long> objectSetMap = new HashMap<String, Long>();
		// 先去对象配置表看是否对该问卷进行过单独设置，如果没有去系统设置表取默认值
		List<SysObjectParameconfig> objectConfigList = getDao().find(" from "+ getEntityName()+" where objecttype=? and objectid=?", objectType, objectId);
		if (objectConfigList.size() > 0) {
			for (int i = 0; i < objectConfigList.size(); i++) {
				SysObjectParameconfig objectConfig = objectConfigList.get(i);
				SystemConfig systemConfig = systemConfigService.get(objectConfig.getConfigid());
				objectSetMap.put(systemConfig.getParameCode(), systemConfig.getParameCurrentValue());
			}
		} else {
			List<SystemConfig> systemConfigList = systemConfigService.getSystemConfigListByParamCategory(paramcategory);
			for (int i = 0; i < systemConfigList.size(); i++) {
				SystemConfig systemConfig = systemConfigList.get(i);
				objectSetMap.put(systemConfig.getParameCode(), systemConfig.getParameDefaultValue());
			}
		}
		return objectSetMap;
	}

	public List getConfigId(String string, Long id) {
		List list = sysObjectParameconfigDao.find(" select configid from " + getEntityName() + " WHERE objecttype = ? and objectid = ? )", new Object[]{string,id});
		return list;
	}
	
	
	public SysObjectParameconfig getSysObjectParameconfig(String string, Long id) {
		SysObjectParameconfig sc = new SysObjectParameconfig();
		List list = sysObjectParameconfigDao.find(" from " + getEntityName() + " WHERE objecttype = ? and objectid = ? )", new Object[]{string,id});
		if(DataUtil.listIsNotNull(list)){
			sc = (SysObjectParameconfig) list.get(0);
		}
		return sc;
	}
	
	
	public SysObjectParameconfig getSysObjectParameconfig(String string, Long objectId,Long configId) {
		SysObjectParameconfig sc = new SysObjectParameconfig();
		List list = sysObjectParameconfigDao.find(" from " + getEntityName() + " WHERE objecttype = ? and objectid = ? and configid=?)", new Object[]{string,objectId,configId});
		if(DataUtil.listIsNotNull(list)){
			sc = (SysObjectParameconfig) list.get(0);
		}
		return sc;
	}
	
	public List<SysObjectParameconfig>  getSysObjectParameconfigByObjecttype(String objectType, String paramcategory, Long objectId){
		List<SysObjectParameconfig> objectConfigList = getDao().find(" from "+ getEntityName()+" where objecttype=? and objectid=? order by configid asc ", objectType, objectId);
		return objectConfigList;
	}
	
	/**
	 * 
	 * @Description: 取得对象配置表中的对象ID值
	 * @author YangHui 
	 * @Created 2014-10-17
	 * @param objectType
	 * @param paramcategory
	 * @param objectId
	 * @return
	 */
	public List  getSysObjectParameconfigIdsByObjecttype(String objectType, String paramcategory, Long objectId){
		List idList = getDao().find("select configid from "+ getEntityName()+" where objecttype=? and objectid=? order by configid asc ", objectType, objectId);
		return idList;
	}

	public SysObjectParameconfig getSysObjectParame(String objectType,
			Long surveyId, Long configId) {
		SysObjectParameconfig conf = null;
		List idList = getDao().find(" from "+ getEntityName()+" where objecttype=? and objectid=? and configid=?  ", objectType, surveyId,configId);
		if(idList!=null){
			conf = (SysObjectParameconfig) idList.get(0);
		}
		return conf;
	}
}
