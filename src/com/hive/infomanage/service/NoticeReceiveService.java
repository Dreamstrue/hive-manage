/**
 * 
 */
package com.hive.infomanage.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.clientmanage.service.ClientUserService;
import com.hive.infomanage.entity.NoticeReceive;
import com.hive.infomanage.model.NoticeReadBean;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

/**  
 * Filename: NoticeReceiveService.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-6  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-6 下午3:39:01				yanghui 	1.0
 */
@Service
public class NoticeReceiveService extends BaseService<NoticeReceive> {
	
	@Resource
	private BaseDao<NoticeReceive> noticeReceiveDao;
	@Resource
	private ClientUserService clientUserService;
	@Override
	protected BaseDao<NoticeReceive> getDao() {
		return noticeReceiveDao;
	}

	public List getAllListByNoticeId(Long id) {
		List list  = getDao().find(" from "+getEntityName()+" where noticeId=?",id);
		return list;
	}

	
	public DataGrid readDetailDataGrid(RequestPage page, Long id) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from "+getEntityName()+" where noticeId=?");
		List<NoticeReceive> list = getDao().find(page.getPage(), page.getRows(), hql.toString(), id);
		List l = new ArrayList();
		for(int i=0;i<list.size();i++){
			NoticeReceive n= list.get(i);
			NoticeReadBean bean = new NoticeReadBean();
			bean.setId(n.getId());
			bean.setUserId(n.getUserId());
			bean.setReceiveDate(n.getReceiveDate());
			bean.setUserName(clientUserService.get(n.getUserId()).getCusername());
			l.add(bean);
		}
		return new DataGrid(l.size(), l);
	}

	/**
	 * 
	 * @Description:
	 * @author yanghui 
	 * @Created 2014-3-25
	 * @param userId
	 * @return
	 */
	public List getNoticeIdByUserId(Long userId) {
		List list = getDao().find(" select noticeId from "+getEntityName()+" where userId=?", userId);
		return list;
	}

	/**
	 * 
	 * @Description:  判断通知公告是否已读
	 * @author yanghui 
	 * @Created 2014-4-1
	 * @param noticeId
	 * @param userId
	 * @return
	 */
	public boolean isReadByUser(Long noticeId, Long userId) {
		boolean flag = false;
		List list = getDao().find(" from "+getEntityName()+" where noticeId=? and userId=?", new Object[]{noticeId,userId});
		if(list.size()>0){
			flag = true;
		}
		return flag;
	}

}
