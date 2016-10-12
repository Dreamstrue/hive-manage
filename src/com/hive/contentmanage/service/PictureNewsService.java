package com.hive.contentmanage.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.contentmanage.entity.CommonContent;
import com.hive.contentmanage.entity.Picturenews;
import com.hive.util.DataUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class PictureNewsService extends BaseService<Picturenews> {

	@Resource
	private BaseDao<Picturenews> pictureNewsDao;

	protected BaseDao<Picturenews> getDao() {
		return this.pictureNewsDao;
	}

	public List getListForIndex() {
		return getDao().find(
				" from " + getEntityName() + " where cvalid=" + "1"
						+ " and cauditstatus=" + "1"
						+ " order by dcreatetime asc", new Object[0]);
	}

	public DataGrid dataGrid(RequestPage page, String keys) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from " + getEntityName() + " where 1=1 ");

		StringBuffer counthql = new StringBuffer();
		counthql.append(" select count(*) from " + getEntityName()
				+ " where 1=1 ");
		StringBuffer sb = new StringBuffer();
		if (!DataUtil.isEmpty(keys)) {
			sb.append(" and title like '%" + keys + "%'");
		}
		sb.append(" and cvalid = '1'");
		sb.append(" order by dcreatetime desc");
		List list = getDao().find(page.getPage(), page.getRows(),
				hql.append(sb).toString(), new Object[0]);
		List newList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Picturenews inte = (Picturenews) list.get(i);
			String title = inte.getTitle();
			if (title.length() > Integer.valueOf("30").intValue()) {
				title = title.substring(0, 20) + "......";
			}
			inte.setTitle(title);
			newList.add(inte);
		}

		Long count = Long.valueOf(getDao().count(
				counthql.append(sb).toString(), new Object[0]).longValue());
		return new DataGrid(count.longValue(), newList);
	}

	public int getNavMenuByName(String title) {
		int size = 0;
		List list = getPictureNewsListByName(title);
		if (list != null) {
			size = list.size();
		}
		return size;
	}

	public List<Picturenews> getPictureNewsListByName(String title) {
		return getDao().find(" from " + getEntityName() + " where title = ?",
				new Object[] { title });
	}

	public List<Picturenews> getCommonList() {
		String hql  = " from "+getEntityName()+" where auditStatus=? and valid=? ";
		List<Picturenews> list = getDao().find(hql, new Object[]{SystemCommon_Constant.AUDIT_STATUS_1,SystemCommon_Constant.VALID_STATUS_1});
		return list;
	}
}