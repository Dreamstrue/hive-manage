package com.hive.surveymanage.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.surveymanage.entity.SurveyIndustry;
import com.hive.util.DataUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class SurveyIndustryService extends BaseService<SurveyIndustry> {

	@Resource
	private BaseDao<SurveyIndustry> surveyIndustryDao;

	@Override
	protected BaseDao<SurveyIndustry> getDao() {
		return surveyIndustryDao;
	}
	
	public List<SurveyIndustry> allSurveyIndustry() {
		return getDao().find(
				" from " + getEntityName() + " where valid = " + SystemCommon_Constant.VALID_STATUS_1 + " order by sort asc");
	}
	
	public DataGrid datagrid(RequestPage page)
	{
		String hql = (new StringBuilder("from ")).append(getEntityName()).toString();
		if (!StringUtils.isEmpty(page.getSort()))
			hql = (new StringBuilder(String.valueOf(hql))).append(" order by ").append(page.getSort()).append(" ").append(page.getOrder()).toString();
		String counthql = (new StringBuilder("select count(*) from ")).append(getEntityName()).toString();
		long count = getDao().count(counthql, new Object[0]).longValue();
		java.util.List rolelist = getDao().find(page.getPage(), page.getRows(), hql, new Object[0]);
		return new DataGrid(count, rolelist);
	}
	
	/**
	 * 逻辑删除
	 */
	public void delete_logic(Long id) {
		surveyIndustryDao.execute("UPDATE " + getEntityName() + " SET valid = ? WHERE id=? ", new Object[]{SystemCommon_Constant.VALID_STATUS_0,id});
	}

	public boolean checkChild(Long id) {
		String hql =" from "+getEntityName()+" where pid=? and valid=? ";
		List list = getDao().find(hql, new Object[]{id,SystemCommon_Constant.VALID_STATUS_1});
		if(DataUtil.listIsNotNull(list)){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 
	 * @Description:只查询父级的行业类别
	 * @author YangHui 
	 * @Created 2014-10-23
	 * @return
	 */
	public List getParentIndustryList() {
		String hql =" from "+getEntityName()+" where pid=(select id from "+getEntityName()+" where pid='0') and valid='1' ";
		return getDao().find(hql, new Object[0]);
	}
	/**
	 * 
	 * @Description:查询所有的行业类别
	 * @author yyf
	 * @Created 2016-06-15 
	 * @return
	 */
	public List getAllIndustryList() {
		String hql =" from "+getEntityName()+" where valid='1' ";
		return getDao().find(hql, new Object[0]);
	}
	/**
	  * 方法名称：getNamebyId
	  * 功能描述：根据ID获取名称
	  * 创建时间:2015年12月21日下午5:31:56
	  * 创建人: pengfei Zhao
	  * @param @param id
	  * @param @return 
	  * @return String
	 */
	public String getNamebyId(String id) {
		SurveyIndustry survey=this.getDao().get(" from "+getEntityName()+" where id=? and valid='1'", Long.valueOf(id));
		if(survey==null){
			return "类别不存在";
		}
		return survey.getText();
	}
	/**
	  * 方法名称：getNamebyId
	  * 功能描述：根据ID获取名称
	  * 创建时间:2015年12月21日下午5:31:56
	  * 创建人: pengfei Zhao
	  * @param @param id
	  * @param @return 
	  * @return String
	 */
	public String getobjectTypebyId(String id) {
		SurveyIndustry survey=this.getDao().get(" from "+getEntityName()+" where id=? and valid='1'", Long.valueOf(id));
		if(survey==null){
			return "类别不存在";
		}
		return survey.getObjectType();
	}
}
