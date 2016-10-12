package com.hive.infomanage.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.infomanage.entity.InfoNotice;
import com.hive.infomanage.model.InfoNoticeBean;
import com.hive.infomanage.model.InfoSearchBean;
import com.hive.systemconfig.service.InfoCategoryService;
import com.hive.util.DataUtil;
import com.hive.util.DateUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;
@Service
public class InfoNoticeService extends BaseService<InfoNotice> {
	
	@Resource
	private BaseDao<InfoNotice> infoNoticeDao;

	@Resource
	private InfoCategoryService infoCategoryService;
	@Override
	protected BaseDao<InfoNotice> getDao() {
		return infoNoticeDao;
	}
	
	@Resource
	private NoticeReceiveService noticeReceiveService;

	public boolean isExistInfoNoticeByTitle(String title) {
		boolean flag = false;
		List list = getListByName(title);
		return false;
	}

	private List getListByName(String title) {
		return getDao().find(" from "+ getEntityName() + " where title=? and valid = ?", new Object[]{title,SystemCommon_Constant.VALID_STATUS_1});
	}
	
	public boolean isExistInfoNoticeByNameAndId(String title, Long id) {
		boolean flag = false;
		List list = getListByName(title);
		if(list.size()>0){
			InfoNotice i = (InfoNotice) list.get(0);
			if(!i.getId().equals(id)){
				flag = true;
			}
		}
		return flag;
	}

	
	
	public DataGrid dataGrid(RequestPage page, InfoSearchBean bean) {
		StringBuffer hql = new StringBuffer(); //分页查询语句
		StringBuffer counthql = new StringBuffer();//记录总条数语句
		StringBuffer sb = new StringBuffer();//查询条件
		
		hql.append(" from " + getEntityName() + " where 1=1 ");
		counthql.append(" select count(*) from " + getEntityName() + " where 1=1 " );
		if(!DataUtil.isEmpty(bean.getTitle())){
			sb.append(" and title like '%" + bean.getTitle() + "%'");
		}
		if(!DataUtil.isEmpty(bean.getAuditStatus())){
			sb.append(" and auditStatus = '" + bean.getAuditStatus() + "'");
		}
		if(!DataUtil.isEmpty(bean.getSendObject())){
			sb.append(" and sendObject = '" + bean.getSendObject() + "'");
		}
		if(!DataUtil.isNull(bean.getCreateTime())){
			sb.append(" and STR_TO_DATE(createTime,'%Y-%m-%d') = '" + DateUtil.dateToString(bean.getCreateTime()) + "'");
		}
		sb.append(" and valid ='"+SystemCommon_Constant.VALID_STATUS_1+"'");
		sb.append(" order by createTime desc ");
		List<InfoNotice> list = getDao().find(page.getPage(), page.getRows(),hql.append(sb).toString(),new Object[0]);
		List<InfoNoticeBean> blist = new ArrayList<InfoNoticeBean>();
		for(Iterator it = list.iterator();it.hasNext();){
			InfoNotice i = (InfoNotice) it.next();
			int readNum = 0;
			if(SystemCommon_Constant.AUDIT_STATUS_1.equals(i.getAuditStatus())){
				//统计通知公告已经被阅读的次数
				List recList = noticeReceiveService.getAllListByNoticeId(i.getId());
				if(recList.size()>0){
					readNum = recList.size();
				}
			}
			InfoNoticeBean infobean = new InfoNoticeBean();
			try{
				PropertyUtils.copyProperties(infobean, i);
				infobean.setReadNum(readNum);
				blist.add(infobean);
			}catch(Exception e){
				
			}
		}
		Long count = getDao().count(counthql.append(sb).toString(),new Object[0]).longValue();
		
		return new DataGrid(count, blist);
	}

}
