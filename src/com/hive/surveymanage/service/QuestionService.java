package com.hive.surveymanage.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.surveymanage.entity.Question;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.service.BaseService;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：Ryu Zheng
 * <p/>创建日期：2013-11-7
 * <p/>创建时间：下午5:20:00
 * <p/>功能描述：问卷题目表Service
 * <p/>===========================================================
 */
@Service
public class QuestionService extends BaseService<Question>{

	@Resource
	private BaseDao<Question> questionDao;
	
	@Override
	protected BaseDao<Question> getDao() {
		return questionDao;
	}

	/**
	 * 功能描述：根据某投票问卷记录ID获取其问卷题目列表
	 * 创建时间:2013-11-7下午5:54:50
	 * 创建人: Ryu Zheng
	 * 
	 * @param surveyid
	 * @return
	 */
	public DataGrid datagrid(String surveyid){
		StringBuilder hql = new StringBuilder();
		hql.append("FROM ").append(getEntityName()).append(" WHERE valid = ")
				.append(SystemCommon_Constant.VALID_STATUS_1)
				.append(" AND surveyid = ").append(surveyid)
				.append(" order by sort asc, id asc"); // 排序值越大越靠后，比如排序值为1的就放在第一位，排序值为2的放第二位
		
		List<Question> questionList = getDao().find(hql.toString());
		return new DataGrid(questionList.size(), questionList);
	}
	
	/**
	 * 功能描述：获取某个问卷的问题列表
	 * 创建时间:2013-11-11上午10:51:34
	 * 创建人: Ryu Zheng
	 * 
	 * @param surveyid 问卷Id
	 * @return
	 */
	public List<Question> listQuestion(Long surveyid){
		List<Question> list =  getDao().find("from "+getEntityName()+" where surveyid = ? and valid = " + SystemCommon_Constant.VALID_STATUS_1 + " order by sort", surveyid);
		return list;
	}
	
	/**
	 * 功能描述：投票时更新问题的累计答题次数
	 * 创建时间:2013-11-13下午3:10:05
	 * 创建人: Ryu Zheng
	 * 
	 * @param questionId
	 * @param num
	 */
	public void updateQuestionNum(Long questionId,int num){
		getDao().execute("update "+getEntityName()+" set num=num+? where id=?", num, questionId);
	}
	
}
