/**
 * 
 */
package com.hive.clientinterface.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.core.NestedCheckedException;
import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.contentmanage.entity.CommonContent;
import com.hive.contentmanage.entity.Picturenews;
import com.hive.util.DataUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

/**  
 * Filename: NewsInterfaceService.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-25  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-25 上午11:06:26				yanghui 	1.0
 */
@Service
public class NewsInterfaceService extends BaseService<CommonContent> {

	
	@Resource
	private BaseDao<CommonContent> commonContentDao;
	@Resource
	private BaseDao<Picturenews> picturenewsDao;
	@Resource
	private SessionFactory sessionFactory;
	
	
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	
	@Override
	protected BaseDao<CommonContent> getDao() {
		return commonContentDao;
	}
	
	
	public DataGrid dataGrid(RequestPage page,Long infoCateId) {
		StringBuffer hql = new StringBuffer(); // 分页查询语句
		StringBuffer counthql = new StringBuffer();// 记录总条数语句
		StringBuffer sb = new StringBuffer();// 查询条件
		List list = null;
		Long count = 0l;
		
		if(!DataUtil.isNull(infoCateId)){
			if(infoCateId==45){
				//查询图片新闻列表
				hql.append(" from Picturenews where 1=1 ");
				counthql.append(" select count(*) from  Picturenews where 1=1 ");
				sb.append(" and valid='" + SystemCommon_Constant.VALID_STATUS_1+ "' and auditStatus='" + SystemCommon_Constant.AUDIT_STATUS_1+ "'");
				sb.append(" order by createTime desc ");
				list = picturenewsDao.find(page.getPage(),page.getRows(),hql.append(sb).toString(),new Object[0]);
				count = picturenewsDao.count(counthql.append(sb).toString(), new Object[0]).longValue();
			}else{
				hql.append(" from " + getEntityName() + " where 1=1 ");
				counthql.append(" select count(*) from " + getEntityName()+ " where 1=1 ");
				if(infoCateId==0){
					//查询为头条的数据
					sb.append(" and isTop='1' ");
				}else{
					sb.append(" and infoCateId='"+infoCateId+"'");
				}
				sb.append(" and valid='" + SystemCommon_Constant.VALID_STATUS_1+ "' and auditStatus='" + SystemCommon_Constant.AUDIT_STATUS_1+ "'");
				sb.append(" order by createTime desc ");
				count = getDao().count(counthql.append(sb).toString(),new Object[0]).longValue(); // 总数
				list = getDao().find(page.getPage(), page.getRows(),hql.append(sb).toString(), new Object[0]); 
			}
		}
		return new DataGrid(count, list);
	}


	public Object getObjectById(String tableName,Long newsId) {
		Object o = null;
		try {
			if("F_COMMONCONTENT".equals(tableName)){
				o = getSession().get(Class.forName("com.guangfan.zxt.contentmanage.entity.CommonContent"), newsId);
			}else if("F_PICTURENEWS".equals(tableName)){
				o = getSession().get(Class.forName("com.guangfan.zxt.contentmanage.entity.Picturenews"), newsId);
			}else if("INFO_NOTICE".equals(tableName)){
				o = getSession().get(Class.forName("com.guangfan.zxt.contentmanage.entity.InfoNotice"), newsId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return o;
	}

}
