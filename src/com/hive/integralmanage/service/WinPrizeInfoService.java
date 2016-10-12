package com.hive.integralmanage.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hive.integralmanage.entity.WinPrizeInfo;

import dk.dao.BaseDao;
import dk.service.BaseService;

/**
 * 中奖纪录 服务类
 * yf 20160329 add
 * @author Administrator
 *
 */
@Service
public class WinPrizeInfoService extends BaseService<WinPrizeInfo> {

	
	@Resource
	private BaseDao<WinPrizeInfo> winPrizeInfoDao;
	
	@Override
	protected BaseDao<WinPrizeInfo> getDao() {
		return winPrizeInfoDao;
	}
	
	/**
	 * 根据 sn 和 手机号 获取中奖信息
	 * 20160329 yf add
	 * @param snNum
	 * @param prizePhone
	 * @return
	 */
	public WinPrizeInfo getWinPrizeInfoBySnVsPhone(String snNum, String prizePhone) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from WinPrizeInfo where 1=1 and snNum = '"+snNum+"' ");
		//yyf 20160415 add and edit
		if(StringUtils.isNotBlank(prizePhone)){
			sb.append(" and prizePhone='"+ prizePhone +"'");
		}
		sb.append(" order by createTime desc");
		List<WinPrizeInfo> list = getDao().find(sb.toString());
		if(null != list && list.size()>0){
			return list.get(0);
		}
		return new WinPrizeInfo();
	}

	/**
	 * 获取此人参与此活动的所有抽奖情况
	 * @param anwserUserId
	 * @param activityId
	 * @return
	 */
	public List<WinPrizeInfo> findWinPrizeInfoByPhoneAndActivityId(
			String anwserUserId, Long activityId) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from WinPrizeInfo w ,SurveyPartakeUser sp where sp.phone=w.prizePhone and sp.id= "+anwserUserId+" and w.activityId="+activityId+" ");
		List<WinPrizeInfo> list = getDao().find(sb.toString());
		return list;
	}
	/**
	 * 校验当前评论是否进行过抽奖
	 * @param surveyEvaluationId
	 * @return
	 */
	public boolean checkWinPrizeByEvaluationId(String surveyEvaluationId) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from WinPrizeInfo w where w.surveyEvaluationId='"+surveyEvaluationId+"' ");
		List<WinPrizeInfo> list = getDao().find(sb.toString());
		if(list.size()>0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 校验当前sn是否有中奖纪录
	 * @param prizeSN
	 * @return
	 */
	public WinPrizeInfo findWinPrizeInfoBySn(String prizeSN) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from WinPrizeInfo where 1=1 and snNum = '"+prizeSN+"' and  winPrizeFlag='1' ");
		sb.append(" order by createTime desc");
		List<WinPrizeInfo> list = getDao().find(sb.toString());
		if(null != list && list.size()>0){
			return list.get(0);
		}
		return new WinPrizeInfo();
	} 
	
}
