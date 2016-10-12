package com.hive.surveymanage.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.defectmanage.entity.DmCarBrand;
import com.hive.infomanage.entity.InfoNotice;
import com.hive.infomanage.model.InfoNoticeBean;
import com.hive.permissionmanage.service.UserService;
import com.hive.surveymanage.entity.Survey;
import com.hive.surveymanage.model.SurveySearchBean;
import com.hive.surveymanage.model.SurveyVo;
import com.hive.util.DataUtil;
import com.hive.util.DateUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：Ryu Zheng
 * <p/>创建日期：2013-11-14
 * <p/>创建时间：下午3:07:56
 * <p/>功能描述：问卷基本信息表Service
 * <p/>===========================================================
 */
@Service
public class SurveyService extends BaseService<Survey>{
	private Logger logger = Logger.getLogger(SurveyService.class);
	
	@Resource
	private BaseDao<Survey> surveyDao;
	
	@Override
	protected BaseDao<Survey> getDao() {
		return surveyDao;
	}
	
	@Resource
	private SurveyIndustryService surveyIndustryService;
	@Resource
	private SurveyCategoryService surveyCategoryService;
	@Resource
	private UserService userService;
	@Resource
	private SessionFactory sessionFactory;

	/**
	 * 功能描述：获取问卷基本信息列表
	 * 创建时间:2013-11-26上午10:02:56
	 * 创建人: Ryu Zheng
	 * 
	 * @param page
	 * @return
	 */
	public DataGrid datagrid(RequestPage page, SurveySearchBean searchBean){
		StringBuffer hql = new StringBuffer(); // 分页查询语句
		StringBuffer counthql = new StringBuffer(); // 记录总条数语句
		StringBuffer sb = new StringBuffer(); // 查询条件
		
		hql.append(" from " + getEntityName() + " where 1=1 ");
		counthql.append(" select count(*) from " + getEntityName() + " where 1=1 " );
		if(!DataUtil.isEmpty(searchBean.getSubject())){
			sb.append(" and subject like '%" + searchBean.getSubject() + "%'");
		}
		String auditstatus = searchBean.getAuditstatus();
		if(!DataUtil.isEmpty(auditstatus)){
			if (SystemCommon_Constant.SURVEY_STATUS_CLOSE.equals(auditstatus))
				sb.append(" and status = '" + auditstatus + "'");
			else
				sb.append(" and auditstatus = '" + auditstatus + "'");
		}
		if(!DataUtil.isEmpty(searchBean.getCreateName())){
			String sql = "select t.nuserid from p_user t WHERE t.cfullname like '%" + searchBean.getCreateName() + "%'";
			String createIds = this.getIdsStringBySql(sql);
			if (DataUtil.isEmpty(createIds))
				createIds = "''";
			sb.append(" and createid in (" + createIds + ")");
		}
		if(!DataUtil.isNull(searchBean.getBegintime())){
			sb.append(" and STR_TO_DATE(begintime,'%Y-%m-%d') >= '" + DateUtil.dateToString(searchBean.getBegintime()) + "'");
		}
		if(!DataUtil.isNull(searchBean.getEndtime())){
			sb.append(" and STR_TO_DATE(endtime,'%Y-%m-%d') <= '" + DateUtil.dateToString(searchBean.getEndtime()) + "'");
		}
		sb.append(" and valid ='"+SystemCommon_Constant.VALID_STATUS_1+"'");
		sb.append(" order by createtime desc ");
		
		List<Survey> surveyList = getDao().find(page.getPage(), page.getRows(), hql.append(sb).toString());
		
		List<SurveyVo> surveyVoList = new ArrayList<SurveyVo>(); 
		for (Iterator iterator1 = surveyList.iterator(); iterator1.hasNext();) {
			Survey survey = (Survey)iterator1.next();
			SurveyVo surveyVo = new SurveyVo();
			try {
				PropertyUtils.copyProperties(surveyVo, survey); // 后者相同属性值会覆盖前者
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (survey.getIndustryid() > 0) 
				surveyVo.setIndustryName(surveyIndustryService.get(survey.getIndustryid()) != null ? surveyIndustryService.get(survey.getIndustryid()).getText() : "");
			if (survey.getCategoryid() > 0) 
				surveyVo.setCategoryName(surveyCategoryService.get(survey.getCategoryid()) != null ? surveyCategoryService.get(survey.getCategoryid()).getCategoryname() : "");
			if (survey.getCreateid() > 0)
				surveyVo.setCreateName(userService.get(survey.getCreateid()) != null ? userService.get(survey.getCreateid()).getFullname() : "");
			surveyVoList.add(surveyVo);
		}
		
		Long count = getDao().count(counthql.append(sb).toString());
		
		return new DataGrid(count, surveyVoList);
	}
	
	/**
	 * 通过一条 sql 得到数据库的一列，然后转成一个字符串
	 * @param sql
	 * @return
	 */
	public String getIdsStringBySql(String sql) {
		String idsStr = "";
		List list = null;  // 查询结果只有一列，放到一个 list 里（无需泛型）
		Session session = sessionFactory.getCurrentSession();
		try {
			list = session.createSQLQuery(sql).list();
		} catch (Exception e) {
			logger.error("getIdsStringBySql ERROR!", e);
		} finally {
			//this.releaseSession(session);
		}
		if (list != null && list.size() > 0) {
			idsStr = list.toString();  // 形如：[000010000500001, 00001000050000100004, 00001000050000100002, 00001000050000100003]     
			idsStr = idsStr.replace("[", "");  // 把前后的中括号去掉
			idsStr = idsStr.replace("]", "");
			idsStr = idsStr.replaceAll(" ", "");  // 把中间的空格去掉
		}
		return idsStr;
	}
	
	/**
	 * 审核问卷
	 * @param ids
	 * @param status
	 * @param remark
	 */
	public void updateAuditSurvey(String ids, Long auditId, String auditstatus, String auditopinion){
		String hql="UPDATE " + getEntityName() + " SET auditid=?,audittime=?,auditstatus=?,auditopinion=?, status=? WHERE id in ("+ids+")"; // 审核通过后即把问卷状态改为“投票进行中”
		this.getDao().execute(hql, auditId, new Date(), auditstatus, auditopinion, SystemCommon_Constant.SURVEY_STATUS_ON);
	}
	/**
	 * 获取所有的有效问卷信息
	 */
	public List<Survey> findAllSurvey(String param){
		StringBuffer hql = new StringBuffer(); 
		hql.append(" from " + getEntityName() + " surv where surv.valid ="+SystemCommon_Constant.VALID_STATUS_1);
		if(StringUtils.isNotBlank(param)){
			hql.append(" and surv.subject like '%"+param+"%'");
		}
		hql.append(" order by surv.subject");
		List<Survey> list = this.getDao().find(hql.toString());
		return list;
	}
	
	
	/**
	 * yf 20160303 add
	 * 根据 行业     获取该行业   的所有的有效问卷信息
	 * @param  industryid
	 * @return
	 */
	public List<Survey> findAllSurveyByIndustryid(Long  industryid){
		List<Survey> list = new ArrayList<Survey>();
		if(null != industryid && StringUtils.isNotBlank(industryid.toString())){
			StringBuffer hql = new StringBuffer(); 
			hql.append(" from " + getEntityName() + " surv where surv.valid ="+SystemCommon_Constant.VALID_STATUS_1);
			hql.append(" and surv.industryid = '"+industryid+"'");
			hql.append(" order by surv.subject");
			list = this.getDao().find(hql.toString());
		}
		return list;
	}

	/**
	 * yyf 20160616 add
	 * 获取指定id的问卷
	 * @param surId
	 * @return
	 */
	public List<Survey> findSurveyById(Long surId) {
		StringBuffer hql = new StringBuffer(); 
		hql.append(" from " + getEntityName() + " surv where surv.valid ="+SystemCommon_Constant.VALID_STATUS_1+" and surv.id ="+surId);
		return this.getDao().find(hql.toString());
	}
}
