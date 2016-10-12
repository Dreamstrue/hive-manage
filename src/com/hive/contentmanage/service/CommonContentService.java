package com.hive.contentmanage.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.contentmanage.entity.CommonContent;
import com.hive.contentmanage.model.CommonContentBean;
import com.hive.infomanage.model.InfoNewsBean;
import com.hive.infomanage.model.InfoSearchBean;
import com.hive.systemconfig.service.InfoCategoryService;
import com.hive.util.DataUtil;
import com.hive.util.DateUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;
@Service
public class CommonContentService extends BaseService<CommonContent> {

	@Resource
	private BaseDao<CommonContent> commonContentDao;
	@Override
	protected BaseDao<CommonContent> getDao() {
		return commonContentDao;
	}
	@Resource
	private InfoCategoryService infoCategoryService;
	
	/**
	 * 
	 * @Description:  通用内容管理列表
	 * @author yanghui 
	 * @Created 2014-4-10
	 * @param page
	 * @param bean
	 * @return
	 */
	public DataGrid dataGrid(RequestPage page, InfoSearchBean bean) {
		StringBuffer hql = new StringBuffer(); //分页查询语句
		StringBuffer counthql = new StringBuffer();//记录总条数语句
		StringBuffer sb = new StringBuffer();//查询条件
		
		hql.append(" select a,b.text from " + getEntityName() + " a,InfoCategory b where 1=1 and a.infoCateId = b.id ");
		counthql.append(" select count(*) from " + getEntityName() + " a  where 1=1 " );
		if(!DataUtil.isEmpty(bean.getTitle())){
			sb.append(" and a.title like '%" + bean.getTitle() + "%'");
		}
		if(!DataUtil.isEmpty(bean.getAuditStatus())){
			sb.append(" and a.auditStatus = '" + bean.getAuditStatus() + "'");
		}
		if(!DataUtil.isNull(bean.getInfoCateId())){
			sb.append(" and a.infoCateId = '" + bean.getInfoCateId() + "'");
		}
		if(!DataUtil.isNull(bean.getCreateTime())){
			sb.append(" and STR_TO_DATE(a.createTime,'%Y-%m-%d') = '" + DateUtil.dateToString(bean.getCreateTime()) + "'");
		}
		sb.append(" and a.valid = '"+SystemCommon_Constant.VALID_STATUS_1+"' "); // 查询可用的 
		sb.append(" order by a.auditStatus asc, a.infoCateId asc,a.createTime desc ");
		List list = getDao().find(page.getPage(), page.getRows(),hql.append(sb).toString(),new Object[0]);
		List<CommonContentBean> blist = new ArrayList<CommonContentBean>();
		for(Iterator it = list.iterator();it.hasNext();){
			Object[] obj = (Object[]) it.next();
			CommonContent i = (CommonContent) obj[0];
			String cateName = (String) obj[1];
			CommonContentBean infobean = new CommonContentBean();
			try{
				PropertyUtils.copyProperties(infobean, i);
				infobean.setInfoCateName(cateName);
				blist.add(infobean);
			}catch(Exception e){
				
			}
		}
		Long count = getDao().count(counthql.append(sb).toString(),new Object[0]).longValue();
		
		return new DataGrid(count, blist);
	}

	/**
	 * 
	 * @Description:  新增时是否存在标题重复
	 * @author yanghui 
	 * @Created 2014-4-10
	 * @param title
	 * @return
	 */
	public boolean isExistTitle(String title) {
		boolean flag = false;
		List list = getListByTitle(title);
		if(list.size()>0){
			flag = true;
		}
		return flag;
	}

	private List getListByTitle(String title) {
		return getDao().find(" from "+ getEntityName() + " where title=? and valid = ?", new Object[]{title,SystemCommon_Constant.VALID_STATUS_1});
	}

	public List<CommonContent> getCommonList() {

		String hql  = " from "+getEntityName()+" where auditStatus=? and valid=? ";
		List<CommonContent> list = getDao().find(hql, new Object[]{SystemCommon_Constant.AUDIT_STATUS_1,SystemCommon_Constant.VALID_STATUS_1});
		
		
		return list;
	}

}
