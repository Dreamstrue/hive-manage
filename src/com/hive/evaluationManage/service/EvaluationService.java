package com.hive.evaluationManage.service;


import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.evaluationManage.entity.SurveyEvaluation;
import com.hive.evaluationManage.model.EvaluationVo;
import com.hive.integralmanage.entity.CashPrizeInfo;
import com.hive.integralmanage.service.CashPrizeInfoService;



import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class EvaluationService extends BaseService<SurveyEvaluation> {

	@Resource
	private BaseDao<SurveyEvaluation> surveyEvaluationDao;
	@Resource
	private SessionFactory sessionFactory;
	@Override
	protected BaseDao<SurveyEvaluation> getDao() {
		return surveyEvaluationDao;
	}
	@Resource
	private CashPrizeInfoService cashprizeInfoService;
	/**
	 * 获取评价列表
	 * @param page
	 * @return
	 */
	public DataGrid datagrid(RequestPage page,EvaluationVo evaluationVo) {
		StringBuffer hql = new StringBuffer();
		StringBuffer countHql = new StringBuffer();
		hql.append("select se.id as id, snb.sn as snCode ,ss.id as surveyId,ss.subject as surveyName,ss.begintime as surveyBeginTime,ss.endtime as surveyEndTime,ssi.industryName as industryName,ssi.objectType as objectType,sie.entityName as entityName,ssptu.id as surveyPartakeUserId,ssptu.username as voteUserName,se.createTime "+
				",ssptu.phone as mobilePhone,ssptu.IDCard as idCard ,mw.winPrizeFlag ,mw.isCash as getPrizeFlag,if(mw.id,mw.id,'') as winPrizeId" +
				" from s_SurveyEvaluation se " +
				" left join m_winprizeinfo mw on mw.surveyevaluationid=se.id" +
				" left join s_IndustryEntity sie on se.industryEntityId=sie.id" +
				" left join s_Survey ss on se.surveyid=ss.id" +
				" left join s_SurveyIndustry ssi on ssi.id = ss.industryid" +
				" left join s_SurveyPartakeUser ssptu on se.surveyPartakeUserId  = ssptu.id" +
				" left join tag_SNBase snb on se.snBaseId=snb.id " +
				" where se.isValid="+SystemCommon_Constant.VALID_STATUS_1);
		countHql.append("select count(distinct se.id) " +
				" from s_SurveyEvaluation se " +
				" left join m_winprizeinfo mw on mw.surveyevaluationid=se.id" +
				" left join s_IndustryEntity sie on se.industryEntityId=sie.id" +
				" left join s_Survey ss on se.surveyid=ss.id" +
				" left join s_SurveyIndustry ssi on ssi.id = ss.industryid" +
				" left join s_SurveyPartakeUser ssptu on se.surveyPartakeUserId  = ssptu.id" +
				" left join tag_SNBase snb on se.snBaseId=snb.id " +
				" where se.isValid="+SystemCommon_Constant.VALID_STATUS_1);
		if(evaluationVo!=null){
			//实体名称
			if(StringUtils.isNotBlank(evaluationVo.getEntityName())) {
				hql.append(" and sie.entityName = '"+evaluationVo.getEntityName()+"' ");
				countHql.append(" and sie.entityName = '"+evaluationVo.getEntityName()+"' ");
			}
			//问卷标题
			if(StringUtils.isNotBlank(evaluationVo.getSurveyName())) {
				hql.append(" and ss.subject = '"+evaluationVo.getSurveyName()+"' ");
				countHql.append(" and ss.subject = '"+evaluationVo.getSurveyName()+"' ");
			}
			//问卷行业类别名称
			if(StringUtils.isNotBlank(evaluationVo.getIndustryName())) {
				hql.append(" and ssi.text = '"+evaluationVo.getIndustryName()+"' ");
				countHql.append(" and ssi.industryname = '"+evaluationVo.getIndustryName()+"' ");
			}
			//问卷行业类别id
			if(StringUtils.isNotBlank(evaluationVo.getIndustryId())) {
				hql.append(" and ssi.id = '"+evaluationVo.getIndustryId()+"' ");
				countHql.append(" and ssi.id = '"+evaluationVo.getIndustryId()+"' ");
			}
			//会员名称，即评论人
			if(StringUtils.isNotBlank(evaluationVo.getVoteUserName())) {
				hql.append(" and ssptu.username like '%"+evaluationVo.getVoteUserName()+"%' ");
				countHql.append(" and ssptu.username like '%"+evaluationVo.getVoteUserName()+"%' ");
			}
			//评论人电话
			if(StringUtils.isNotBlank(evaluationVo.getMobilePhone())) {
				hql.append(" and ssptu.phone = '"+evaluationVo.getMobilePhone()+"' ");
				countHql.append(" and ssptu.phone = '"+evaluationVo.getMobilePhone()+"' ");
			}
			//评论人身份证号
			if(StringUtils.isNotBlank(evaluationVo.getIdCard())) {
				hql.append(" and ssptu.IDCard = '"+evaluationVo.getIdCard()+"' ");
				countHql.append(" and ssptu.IDCard = '"+evaluationVo.getIdCard()+"' ");
			}
			//条件查询中的评论时间区间-开始时间
			if(StringUtils.isNotBlank(evaluationVo.getBeginTime())) {
				hql.append(" and se.createTime >= '"+evaluationVo.getBeginTime()+" 00:00:00' ");
				countHql.append(" and se.createTime >= '"+evaluationVo.getBeginTime()+" 00:00:00' ");
			}
			//条件查询中的评论时间区间-结束时间
			if(StringUtils.isNotBlank(evaluationVo.getEndTime())) {
				hql.append(" and se.createTime <= '"+evaluationVo.getEndTime()+" 23:59:59' ");
				countHql.append(" and se.createTime <= '"+evaluationVo.getEndTime()+" 23:59:59' ");
			}
			
			//能参与抽奖的有效评价标示
			if(StringUtils.isNotBlank(evaluationVo.getValidFlag())&&evaluationVo.getValidFlag().equals("true")) {
				if(StringUtils.isNotBlank(evaluationVo.getSnCode())){
					hql.append(" and snb.sn = '"+evaluationVo.getSnCode()+"'");
					countHql.append("  and snb.sn = '"+evaluationVo.getSnCode()+"'");
				}
				/*else{
					hql.append(" and se.snBaseId is not null ");
					countHql.append("  and se.snBaseId is not null ");
				} 注释掉 20160624 yyf add 为支持行业实体的评价也能查询到*/
			}
		}
		hql.append(" group by se.id order by se.createTime desc");
//		countHql.append(" group by svr.surveyid,svr.surveyPartakeUserId ) r");

		Query query  = sessionFactory.getCurrentSession().createSQLQuery(hql.toString());
		List<EvaluationVo> list = query.setFirstResult((page.getPage()-1)*page.getRows()).setMaxResults(page.getRows()).setResultTransformer(Transformers.aliasToBean(EvaluationVo.class)).list();
		for(EvaluationVo vo : list){
			if(StringUtils.isNotBlank(vo.getWinPrizeFlag())){
				if(vo.getWinPrizeFlag().equals("1")){
					if(StringUtils.isNotBlank(vo.getGetPrizeFlag())){
						if(vo.getGetPrizeFlag().equals("1")){
							vo.setGetPrizeFlag("已领");
						}else{
							vo.setGetPrizeFlag("未领");
						}
					}else{
						vo.setGetPrizeFlag("中奖信息中是否兑奖为空");
					}
				}else{
					vo.setGetPrizeFlag("未中奖");
				}
				
			}else{
				vo.setGetPrizeFlag("未中奖");//中奖信息中是否中奖为空
			}
			/*if(StringUtils.isNotBlank(vo.getSnCode())){
				
				List<CashPrizeInfo>  lr = cashprizeInfoService.queryBySN(vo.getSnCode());
				if(lr.size()>0){
					vo.setGetPrizeFlag("已领");
				}else{
					vo.setGetPrizeFlag("未领");
				}
			}20160628 yyf 注释掉*/
		}
		
		Query queryCount  = sessionFactory.getCurrentSession().createSQLQuery(countHql.toString());
		BigInteger bigCount = (BigInteger)queryCount.uniqueResult();
		long count = bigCount.longValue();
		return new DataGrid(count, list);
	}
	
	
	/**
	  * 方法名称：getEvalVobySn
	  * 功能描述：根据sn码获取评论信息
	  * 创建时间:2016年2月25日下午2:29:08
	  * 创建人: pengfei Zhao
	  * @param @param page
	  * @param @return 
	  * @return EvaluationVo
	 */
	public EvaluationVo getEvalVobySn(String snCode,String phone) {
		StringBuffer hql = new StringBuffer();
		hql.append("select snb.sn as snCode ,ss.id as surveyId,ss.subject as surveyName,ss.begintime as surveyBeginTime,ss.endtime as surveyEndTime,ssi.industryName as industryName,ssi.objectType as objectType,sie.entityName as entityName,ssptu.id as surveyPartakeUserId,ssptu.username as voteUserName,se.createTime "+
				",ssptu.phone as mobilePhone,ssptu.IDCard as idCard " +
				" from s_SurveyEvaluation se " +
				" left join s_IndustryEntity sie on se.industryEntityId=sie.id" +
				" left join s_Survey ss on se.surveyid=ss.id" +
				" left join s_SurveyIndustry ssi on ssi.id = ss.industryid" +
				" left join s_SurveyPartakeUser ssptu on se.surveyPartakeUserId  = ssptu.id" +
				" left join tag_SNBase snb on se.snBaseId=snb.id " +
				" where se.isValid="+SystemCommon_Constant.VALID_STATUS_1);
		//yanyanfei 20160415 edit and add
		if(StringUtils.isNotBlank(snCode)){
			hql.append(" and snb.SN="+snCode);
		}
		if(StringUtils.isNotBlank(phone)){
			hql.append(" and ssptu.phone="+phone);
		}
		hql.append(" order by se.createTime desc");
		@SuppressWarnings("unchecked")
		List<EvaluationVo> list  = sessionFactory.getCurrentSession().createSQLQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(EvaluationVo.class)).list();
		if(list.size()>0){
			EvaluationVo evalVo=list.get(0);
			return evalVo;
		}else{
			return null;
		}
	}
	public void findSurveyDetailList() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 获取统计评价列表
	 * @param page
	 * @return
	 */
	public DataGrid datagridforChart(RequestPage page,String countTime,String isMember,String entityId,String surveyId) {
		StringBuffer hql = new StringBuffer();
		StringBuffer countHql = new StringBuffer();
		hql.append("select snb.sn as snCode ,ss.id as surveyId,ss.subject as surveyName,ss.begintime as surveyBeginTime,ss.endtime as surveyEndTime,ssi.industryName as industryName,ssi.objectType as objectType,sie.entityName as entityName,ssptu.id as surveyPartakeUserId,ssptu.username as voteUserName,se.createTime "+
				",ssptu.phone as mobilePhone,ssptu.IDCard as idCard " +
				" from s_SurveyEvaluation se " +
				" left join s_IndustryEntity sie on se.industryEntityId=sie.id" +
				" left join s_Survey ss on se.surveyid=ss.id" +
				" left join s_SurveyIndustry ssi on ssi.id = ss.industryid" +
				" left join s_SurveyPartakeUser ssptu on se.surveyPartakeUserId  = ssptu.id" +
				" left join tag_SNBase snb on se.snBaseId=snb.id " +
				" where se.isValid="+SystemCommon_Constant.VALID_STATUS_1+
				" and se.createTime like '"+countTime+"%'");
				if(isMember.equals("1")){
					hql.append(" and ssptu.username<>'匿名'");
				}
				if(isMember.equals("2")){
					hql.append(" and ssptu.username='匿名'");
				}
				if(entityId!=null&&!entityId.equals("")&&!entityId.equals("null")){
					hql.append(" and sie.id = " +Long.valueOf(entityId));
				}
				if(surveyId!=null&&!surveyId.equals("")&&!surveyId.equals("null")){
					hql.append(" and ss.id = " + Long.valueOf(surveyId));
				}
		countHql.append("select count(*) " +
				" from s_SurveyEvaluation se " +
				" left join s_IndustryEntity sie on se.industryEntityId=sie.id" +
				" left join s_Survey ss on se.surveyid=ss.id" +
				" left join s_SurveyIndustry ssi on ssi.id = ss.industryid" +
				" left join s_SurveyPartakeUser ssptu on se.surveyPartakeUserId  = ssptu.id" +
				" left join tag_SNBase snb on se.snBaseId=snb.id " +
				" where se.isValid="+SystemCommon_Constant.VALID_STATUS_1+
				" and se.createTime like '"+countTime+"%'");
				if(isMember.equals("1")){
					countHql.append(" and ssptu.username<>'匿名'");
				}
				if(isMember.equals("2")){
					countHql.append(" and ssptu.username='匿名'");
				}
				if(entityId!=null&&!entityId.equals("")&&!entityId.equals("null")){
					countHql.append(" and sie.id = " +Long.valueOf(entityId));
				}
				if(surveyId!=null&&!surveyId.equals("")&&!surveyId.equals("null")){
					countHql.append(" and ss.id = " + Long.valueOf(surveyId));
				}
		hql.append(" order by se.createTime desc");

		Query query  = sessionFactory.getCurrentSession().createSQLQuery(hql.toString());
		List<EvaluationVo> list = query.setFirstResult((page.getPage()-1)*page.getRows()).setMaxResults(page.getRows()).setResultTransformer(Transformers.aliasToBean(EvaluationVo.class)).list();
		for(EvaluationVo vo : list){
			if(StringUtils.isNotBlank(vo.getSnCode())){
				
				List<CashPrizeInfo>  lr = cashprizeInfoService.queryBySN(vo.getSnCode());
				if(lr.size()>0){
					vo.setGetPrizeFlag("已领");
				}else{
					vo.setGetPrizeFlag("未领");
				}
			}
		}
		
		Query queryCount  = sessionFactory.getCurrentSession().createSQLQuery(countHql.toString());
		BigInteger bigCount = (BigInteger)queryCount.uniqueResult();
		long count = bigCount.longValue();
		return new DataGrid(count, list);
	}


	/**
	 * 根据中奖信息id获取到对应的评论信息
	 * 20160628 yyf add
	 */
	public EvaluationVo findByWinPrizeId(String winPrizeId) {
		StringBuffer hql = new StringBuffer();
		hql.append("select snb.sn as snCode ,ss.id as surveyId,ss.subject as surveyName,ss.begintime as surveyBeginTime,ss.endtime as surveyEndTime,ssi.industryName as industryName,ssi.objectType as objectType,sie.entityName as entityName,ssptu.id as surveyPartakeUserId,ssptu.username as voteUserName,se.createTime "+
				",ssptu.phone as mobilePhone,ssptu.IDCard as idCard ,mw.winPrizeFlag ,mw.isCash as getPrizeFlag" +
				" from s_SurveyEvaluation se " +
				" left join m_winprizeinfo mw on mw.surveyevaluationid=se.id" +
				" left join s_IndustryEntity sie on se.industryEntityId=sie.id" +
				" left join s_Survey ss on se.surveyid=ss.id" +
				" left join s_SurveyIndustry ssi on ssi.id = ss.industryid" +
				" left join s_SurveyPartakeUser ssptu on se.surveyPartakeUserId  = ssptu.id" +
				" left join tag_SNBase snb on se.snBaseId=snb.id " +
				" where se.isValid="+SystemCommon_Constant.VALID_STATUS_1);
		if(StringUtils.isNotBlank(winPrizeId)){
			hql.append(" and mw.id="+winPrizeId);
		}
		hql.append(" order by se.createTime desc");
		@SuppressWarnings("unchecked")
		List<EvaluationVo> list  = sessionFactory.getCurrentSession().createSQLQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(EvaluationVo.class)).list();
		if(list.size()>0){
			EvaluationVo evalVo=list.get(0);
				if(StringUtils.isNotBlank(evalVo.getWinPrizeFlag())){
					if(evalVo.getWinPrizeFlag().equals("1")){
						if(StringUtils.isNotBlank(evalVo.getGetPrizeFlag())){
							if(evalVo.getGetPrizeFlag().equals("1")){
								evalVo.setGetPrizeFlag("已领");
							}else{
								evalVo.setGetPrizeFlag("未领");
							}
						}else{
							evalVo.setGetPrizeFlag("中奖信息中是否兑奖为空");
						}
					}else{
						evalVo.setGetPrizeFlag("未中奖");
					}
					
				}else{
					evalVo.setGetPrizeFlag("未中奖");//中奖信息中是否中奖为空
				}
			return evalVo;
		}else{
			return null;
		}
	}
	

}
