package com.hive.infomanage.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.infomanage.entity.InfoNews;
import com.hive.infomanage.model.InfoNewsBean;
import com.hive.infomanage.model.InfoSearchBean;
import com.hive.integralmanage.service.IntegralSubService;
import com.hive.systemconfig.service.InfoCategoryService;
import com.hive.util.DataUtil;
import com.hive.util.DateUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;
@Service
public class InfoNewsService extends BaseService<InfoNews> {
	
	@Resource
	private BaseDao<InfoNews> infoNewsDao;
	@Resource
	private NewsCommentService newsCommentService;
	@Resource
	private InfoCategoryService infoCategoryService;
	@Resource
	private IntegralSubService integralSubService;
	
	@Override
	protected BaseDao<InfoNews> getDao() {
		return infoNewsDao;
	}

	public boolean isExistInfoNewsByTitle(String title) {
		boolean flag = false;
		List list = getListByName(title);
		if(list.size()>0){
			flag = true;
		}
		return flag;
	}

	private List getListByName(String title) {
		return getDao().find(" from "+ getEntityName() + " where title=? and valid = ?", new Object[]{title,SystemCommon_Constant.VALID_STATUS_1});
	}
	
	public boolean isExistInfoNewsByNameAndId(String title, Long id) {
		boolean flag = false;
		List list = getListByName(title);
		if(list.size()>0){
			InfoNews i = (InfoNews) list.get(0);
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
		if(!DataUtil.isNull(bean.getInfoCateId())){
			sb.append(" and infoCateId = '" + bean.getInfoCateId() + "'");
		}
		if(!DataUtil.isNull(bean.getCreateTime())){
			sb.append(" and STR_TO_DATE(createTime,'%Y-%m-%d') = '" + DateUtil.dateToString(bean.getCreateTime()) + "'");
		}
		sb.append(" and valid = '"+SystemCommon_Constant.VALID_STATUS_1+"' "); // 查询可用的 
		sb.append(" order by createTime desc ");
		List<InfoNews> list = getDao().find(page.getPage(), page.getRows(),hql.append(sb).toString(),new Object[0]);
		List<InfoNewsBean> blist = new ArrayList<InfoNewsBean>();
		for(Iterator it = list.iterator();it.hasNext();){
			InfoNews i = (InfoNews) it.next();
			Long infoCateId = i.getInfoCateId();
			String cateName = infoCategoryService.getNameById(infoCateId);
			InfoNewsBean infobean = new InfoNewsBean();
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
	 * @Description: 新闻资讯统计（评论，分享）
	 * @author yanghui 
	 * @Created 2014-3-10
	 * @param page
	 * @param bean
	 * @return
	 */
	public DataGrid statisticsDataGrid(RequestPage page, InfoSearchBean bean) {
		List<InfoNewsBean> blist = new ArrayList<InfoNewsBean>();
		Long count = (long) 0;
		if(!DataUtil.isNull(bean.getInfoCateId())){
				StringBuffer hql = new StringBuffer(); //分页查询语句
				StringBuffer counthql = new StringBuffer();//记录总条数语句
				StringBuffer sb = new StringBuffer();//查询条件
				hql.append(" from " + getEntityName() + " where 1=1 and infoCateId='"+bean.getInfoCateId()+"' ");
				counthql.append(" select count(*) from " + getEntityName() + " where 1=1  and infoCateId='"+bean.getInfoCateId()+"' ");
				if(!DataUtil.isEmpty(bean.getTitle())){
					sb.append(" and title like '%" + bean.getTitle() + "%'");
				}
				if(!DataUtil.isNull(bean.getCreateTime())){
					sb.append(" and STR_TO_DATE(createTime,'%Y-%m-%d') = '" + DateUtil.dateToString(bean.getCreateTime()) + "'");
				}
				sb.append(" and valid = '"+SystemCommon_Constant.VALID_STATUS_1+"' "); // 查询可用的 
				sb.append(" and auditStatus = '" + SystemCommon_Constant.AUDIT_STATUS_1+ "' "); //查询审核通过的
				sb.append(" order by createTime desc ");
				List<InfoNews> list = getDao().find(page.getPage(), page.getRows(),hql.append(sb).toString(),new Object[0]);
				for(Iterator it = list.iterator();it.hasNext();){
					InfoNews i = (InfoNews) it.next();
					Long infoCateId = i.getInfoCateId();
					String cateName = infoCategoryService.getNameById(infoCateId);
					
					//统计评论次数
					int commentNum = i.getCommentNum();
					//统计分享次数
					int shareNum = i.getShareNum();
					
					InfoNewsBean infobean = new InfoNewsBean();
					infobean.setId(i.getId());
					infobean.setTitle(i.getTitle());
					infobean.setInfoCateName(cateName);
					infobean.setCommentNum(commentNum); //评论次数
					infobean.setShareNum(shareNum); //分享次数
					blist.add(infobean);
				}
				count = getDao().count(counthql.append(sb).toString(),new Object[0]).longValue();
		}
		return new DataGrid(count, blist);
	}

	
	/**
	 * 
	 * @Description:  按信息类别统计
	 * @author yanghui 
	 * @Created 2014-3-20
	 * @param page
	 * @param bean
	 * @return
	 */
	public DataGrid categoryDataGrid(RequestPage page, InfoSearchBean bean) {
		StringBuffer hql = new StringBuffer(); //分页查询语句
		StringBuffer sb = new StringBuffer();//查询条件
		hql.append(" select infoCateId,count(*) from " + getEntityName() + " where 1=1 ");
		if(!DataUtil.isNull(bean.getInfoCateId())){
			sb.append(" and infoCateId = '" + bean.getInfoCateId() + "'");
		}
		sb.append(" and valid = '"+SystemCommon_Constant.VALID_STATUS_1+"' "); // 查询可用的 
		sb.append(" and auditStatus = '" + SystemCommon_Constant.AUDIT_STATUS_1+ "' "); //查询审核通过的
		sb.append(" group by infoCateId order by infoCateId");
		
		List list = getDao().find(hql.append(sb).toString(),new Object[0]);
		List<InfoNewsBean> blist = new ArrayList<InfoNewsBean>();
		for(Iterator it = list.iterator();it.hasNext();){
			Object[] obj = (Object[]) it.next();
			Long infoCateId = (Long) obj[0];  //分类ID
			Long newsNum = (Long) obj[1];    //资讯数量
			String cateName = infoCategoryService.getNameById(infoCateId);
			InfoNewsBean infobean = new InfoNewsBean();
			infobean.setInfoCateId(infoCateId);
			infobean.setId(infoCateId);//这里设置是为了页面取值，方便操作。
			infobean.setInfoCateName(cateName);
			infobean.setNewsNum(newsNum);
			blist.add(infobean);
		}
		int pageNo = page.getPage();
		int pageSize = page.getRows();
		int begin = (pageNo-1)*pageSize;
		int end = (pageNo*pageSize>=blist.size()?blist.size():pageNo*pageSize);
		List subList = blist.subList(begin, end);
		return new DataGrid(blist.size(),subList);
	}

	/**
	 * 
	 * @Description:  新闻资讯分享列表
	 * @author yanghui 
	 * @Created 2014-3-21
	 * @param page
	 * @param bean
	 * @param newsId
	 * @return
	 */
	public DataGrid shareDataGrid(RequestPage page, InfoSearchBean bean,
			Long newsId) {
		return integralSubService.shareDataGrid(page,bean,newsId);
		
	
	}

}
