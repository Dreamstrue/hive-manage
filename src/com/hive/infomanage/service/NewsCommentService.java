/**
 * 
 */
package com.hive.infomanage.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.clientinterface.controller.NewsInterfaceController;
import com.hive.clientmanage.service.ClientUserService;
import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Annex;
import com.hive.infomanage.entity.NewsComment;
import com.hive.infomanage.model.InfoSearchBean;
import com.hive.infomanage.model.NewsCommentBean;
import com.hive.membermanage.entity.MMember;
import com.hive.util.DataUtil;
import com.hive.util.DateUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

/**  
 * Filename: NewsCommentService.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-6  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-6 下午3:40:02				yanghui 	1.0
 */
@Service
public class NewsCommentService extends BaseService<NewsComment> {
	
	@Resource
	private ClientUserService clientUserService;

	@Resource
	private BaseDao<NewsComment> newsCommentDao;
	@Resource
	private BaseDao<Annex> annexDao;
	
	@Override
	protected BaseDao<NewsComment> getDao() {
		return newsCommentDao;
	}
	
	/**
	 * 
	 * @Description: 统计评论次数
	 * @author yanghui 
	 * @Created 2014-3-10
	 * @param id
	 * @return
	 */
	public int getCommentNumByNewsId(Long newsId) {
		int num = 0;
		List list = newsCommentDao.find(" from "+getEntityName()+" where newsId=?", newsId);
		num = list.size();
		return num;
	}

	/**
	 * 
	 * @Description: 评论明细列表
	 * @author yanghui 
	 * @Created 2014-3-10
	 * @param page
	 * @param bean
	 * @return
	 */
	public DataGrid commentDataGrid(RequestPage page, InfoSearchBean bean,Long newsId) {
		StringBuffer hql = new StringBuffer();
		StringBuffer sb = new StringBuffer();
		StringBuffer counthql = new StringBuffer();
		
		hql.append(" from " + getEntityName() + " where newsId = ? ");
		if(!DataUtil.isNull(bean.getBeginDate())){
			sb.append(" and STR_TO_DATE(commentDate,'%Y-%m-%d') >='" + DateUtil.dateToString(bean.getBeginDate())+"' ");
		}
		
		if(!DataUtil.isNull(bean.getEndDate())){
			sb.append(" and STR_TO_DATE(commentDate,'%Y-%m-%d') <='" + DateUtil.dateToString(bean.getEndDate())+"' ");
		}
		
		sb.append(" order by userId,commentDate desc ");
		List<NewsComment> list = getDao().find(page.getPage(), page.getRows(), hql.append(sb).toString(), newsId);
		List<NewsCommentBean> blist = new ArrayList<NewsCommentBean>();
		for(int i=0;i<list.size();i++){
			NewsComment nc = list.get(i);
			String userName  = clientUserService.get(nc.getUserId()).getCusername();
			NewsCommentBean ncb = new NewsCommentBean();
			ncb.setId(nc.getId());
			ncb.setUserName(userName);
			ncb.setCommentDate(nc.getCommentDate());
			ncb.setContent(nc.getContent());
			blist.add(ncb);
		}
		counthql.append(" select count(*) from "+getEntityName()+" where newsId=? ");
		counthql.append(sb);
		Long count = getDao().count(counthql.toString(), newsId);
		return new DataGrid(count, blist);
	}

	/**
	 * 
	 * @Description: 根据新闻资讯ID获得评论内容列表
	 * @author yanghui 
	 * @Created 2014-3-25
	 * @param newsId
	 * @return
	 */
	public DataGrid getCommentContentByNewsId(RequestPage page,Long newsId) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select a,b from NewsComment a,MMember b ");
		hql.append(" where a.userId=b.id and newsId = ? ");
		hql.append(" order by commentDate desc ");
		List list = getDao().find(hql.toString(), newsId);
		List<NewsCommentBean> beanlist = new ArrayList<NewsCommentBean>();
		for(Iterator it = list.iterator();it.hasNext();){
			Object[] obj = (Object[]) it.next();
			NewsComment nc = (NewsComment) obj[0];
			MMember user = (MMember) obj[1];
			NewsCommentBean bean = new NewsCommentBean();
			bean.setCommentDate(nc.getCommentDate());
			bean.setContent(nc.getContent());
			if("1".equals(user.getCmembertype())){
				bean.setUserName(user.getConciseName());
			}else{
				bean.setUserName(user.getCnickname());
			}
			
			//取得评论图片
			String imghql = " from Annex where objectId=? and objectTable=? and cvalid = '"+SystemCommon_Constant.VALID_STATUS_1+"'";
			List<Annex> annexList = annexDao.find(imghql, new Object[]{nc.getId(),NewsInterfaceController.COMMENT_TABLE_NAME});
			if(DataUtil.listIsNotNull(annexList)){
				bean.setAnnexList(annexList);
			}
			
			
			beanlist.add(bean);
		}
		
		List childList = new ArrayList();
		if(beanlist.size()>0){
			int pageNo = page.getPage();
			int pageSize = page.getRows();
			int begin = (pageNo - 1) * pageSize;
			int end = (pageNo * pageSize >= beanlist.size() ? beanlist.size(): pageNo * pageSize);
			childList = beanlist.subList(begin, end);
		}
		
		
		return new DataGrid(beanlist.size(),childList);
	}
	

}
