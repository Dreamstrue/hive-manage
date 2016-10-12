package com.hive.discloseInfo.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.discloseInfo.entity.DiscloseInfo;
import com.hive.util.DataUtil;
import com.hive.util.DateUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class DiscloseService extends BaseService<DiscloseInfo>{
	private Logger logger = Logger.getLogger(DiscloseInfo.class);
	@Resource
	private BaseDao<DiscloseInfo> discloseInfo;
	@Override
	protected BaseDao<DiscloseInfo> getDao() {
		return discloseInfo;
	}
	
	public DataGrid datagrid(RequestPage page,String content,Date begintime,Date endtime){
		StringBuffer hql = new StringBuffer(); // 分页查询语句
		StringBuffer counthql = new StringBuffer(); // 记录总条数语句
		StringBuffer sb = new StringBuffer(); // 查询条件
		
		hql.append(" from " + getEntityName() + " where 1=1 ");
		counthql.append(" select count(*) from " + getEntityName() + " where 1=1 " );
		if(!DataUtil.isEmpty(content)){
			sb.append(" and content like '%" + content + "%'");
		}
		/*String auditstatus = searchBean.getAuditstatus();
		if(!DataUtil.isEmpty(auditstatus)){
			if (SystemCommon_Constant.SURVEY_STATUS_CLOSE.equals(auditstatus))
				sb.append(" and status = '" + auditstatus + "'");
			else
				sb.append(" and auditstatus = '" + auditstatus + "'");
		}
		if(!DataUtil.isNull(searchBean.getCreateName())){
			String sql = "select t.nuserid from p_user t WHERE t.cfullname like '%" + searchBean.getCreateName() + "%'";
			String createIds = this.getIdsStringBySql(sql);
			if (DataUtil.isEmpty(createIds))
				createIds = "''";
			sb.append(" and createid in (" + createIds + ")");
		}*/
		if(!DataUtil.isNull(begintime)){
			sb.append(" and STR_TO_DATE(createTime,'%Y-%m-%d') >= '" + DateUtil.dateToString(begintime) + "'");
		}
		if(!DataUtil.isNull(endtime)){
			sb.append(" and STR_TO_DATE(createTime,'%Y-%m-%d') <= '" + DateUtil.dateToString(endtime) + "'");
		}
		//sb.append(" and valid ='"+SystemCommon_Constant.VALID_STATUS_1+"'");
		sb.append(" order by createtime desc ");
		List<DiscloseInfo> discloseList=getDao().find(page.getPage(), page.getRows(), hql.append(sb).toString());
         Long count = getDao().count(counthql.append(sb).toString());
		
		return new DataGrid(count, discloseList);
		
	}
	public void updateDiscloseInfo(){
		String hql="update "+getEntityName()+" set auditStatus=?,auditTime=?  where auditStatus<>'1' ";
		this.getDao().execute(hql, SystemCommon_Constant.AUDIT_STATUS_1, new Date());
	}
}
