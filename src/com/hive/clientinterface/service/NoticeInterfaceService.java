package com.hive.clientinterface.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.contentmanage.entity.CommonContent;
import com.hive.infomanage.entity.InfoNotice;
import com.hive.infomanage.model.InfoNoticeBean;
import com.hive.infomanage.service.NoticeReceiveService;
import com.hive.util.DataUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;
@Service
public class NoticeInterfaceService extends BaseService<InfoNotice> {

	@Resource
	private BaseDao<InfoNotice> infoNoticeDao;

	@Override
	protected BaseDao<InfoNotice> getDao() {
		return infoNoticeDao;
	}
	
	@Resource
	private NoticeReceiveService noticeReceiveService;

	/**
	 * 
	 * @Description: 客户端通知公告类别（判断用户是否登录，如登录后，返回公告的已阅标识）
	 * @author yanghui
	 * @Created 2014-3-24
	 * @param page
	 * @param userId  客户端用户的ID
	 * @return
	 */
	public DataGrid dataGrid(RequestPage page, Long userId) {
		StringBuffer hql = new StringBuffer(); // 分页查询语句
		StringBuffer counthql = new StringBuffer();// 记录总条数语句
		StringBuffer sb = new StringBuffer();// 查询条件
		counthql.append(" select count(*) from " + getEntityName()+ " where 1=1 ");
		// 审核通过 并且没有被删除
		sb.append(" and valid='" + SystemCommon_Constant.VALID_STATUS_1+ "' and auditStatus='" + SystemCommon_Constant.AUDIT_STATUS_1+ "'");
		sb.append(" order by createTime desc ");
		List<InfoNotice> list = new ArrayList<InfoNotice>();
		Long count = getDao().count(counthql.append(sb).toString(),new Object[0]).longValue(); // 总数
		
		List<InfoNoticeBean> blist = new ArrayList<InfoNoticeBean>();
		if (!DataUtil.isNull(userId)) { //说明客户端用户已登录    
			hql.append(" from "+getEntityName()+" where 1=1 ");
			list = getDao().find(page.getPage(), page.getRows(),hql.append(sb).toString(),new Object[0]);
			for (Iterator it = list.iterator(); it.hasNext();) {
				InfoNotice i = (InfoNotice) it.next();
				InfoNoticeBean infobean = new InfoNoticeBean();
				infobean.setId(i.getId());
				infobean.setTitle(i.getTitle()); //标题
				infobean.setSource(i.getSource()); //来源
				infobean.setHasannex(i.getHasannex());  //是否存在附件
				infobean.setHref(i.getHref()); //链接
				infobean.setCreateTime(i.getCreateTime());
				//获取该用户已经查看过的通知公告
				List l = noticeReceiveService.getNoticeIdByUserId(userId);
				if(l.size()>0){
					if(l.contains(i.getId())){
						//说明该通知公告被该用户阅读过
						infobean.setReadStatus(SystemCommon_Constant.READ_YES);
					}else{
						infobean.setReadStatus(SystemCommon_Constant.READ_NO);
					}
				}
				blist.add(infobean);
			}
			return new DataGrid(count, blist);
		} else {
			//客户端用户没有登录，默认分页查询列表信息不包含是否阅读标识
			hql.append(" from " + getEntityName() + " where 1=1 ");
			list = getDao().find(page.getPage(), page.getRows(),hql.append(sb).toString(), new Object[0]);
			return new DataGrid(count, list);
		}
	}

	public List<InfoNotice> getNoticeList() {
		String hql  = " from "+getEntityName()+" where auditStatus=? and valid=? ";
		List<InfoNotice> list = getDao().find(hql, new Object[]{SystemCommon_Constant.AUDIT_STATUS_1,SystemCommon_Constant.VALID_STATUS_1});
		return list;
	}
}