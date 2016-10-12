package com.hive.surveymanage.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.surveymanage.entity.Option;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.service.BaseService;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：Ryu Zheng
 * <p/>创建日期：2013-11-11
 * <p/>创建时间：下午3:26:25
 * <p/>功能描述：问卷选项表Service
 * <p/>===========================================================
 */
@Service
public class OptionService extends BaseService<Option>{

	@Resource
	private BaseDao<Option> optionDao;
	
	@Override
	protected BaseDao<Option> getDao() {
		return optionDao;
	}

	
	/**
	 * 功能描述：根据某问题记录ID获取其问卷选项列表
	 * 创建时间:2013-11-11下午3:27:59
	 * 创建人: Ryu Zheng
	 * 
	 * @param questionId
	 * @return
	 */
	public DataGrid datagrid(Long questionId){
		StringBuilder hql = new StringBuilder();
		hql.append("FROM ").append(getEntityName()).append(" WHERE 1 = 1")
				.append(" AND questionid = ").append(questionId);
		hql.append(" ORDER BY sort ASC ");
		
		List<Option> itemList = getDao().find(hql.toString());
		return new DataGrid(itemList.size(), itemList);
	}
	
	/**
	 * 功能描述：获取某个问题的选项列表
	 * 创建时间:2013-11-11下午3:29:39
	 * 创建人: Ryu Zheng
	 * 
	 * @param questionId 问题Id
	 * @return
	 */
	public List<Option> listOption(Long questionid){
		List<Option> list =  getDao().find("from "+getEntityName()+" where questionid = ? order by sort", questionid);
		return list;
	}
	
	//根据已知条件修改数据
	public void updateWhere(String str){
		getDao().execute("update "+getEntityName()+" set num=num+1 where "+str);
	}
	
	public void deleteOldRoleOptionByQuestionId(long questionid) {
		optionDao.execute("DELETE FROM " + getEntityName() + " WHERE questionid = " + questionid); // 先删除旧的 再保存新的
	}
}
