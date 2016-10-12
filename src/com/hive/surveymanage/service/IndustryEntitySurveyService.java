package com.hive.surveymanage.service;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hive.permissionmanage.service.UserService;
import com.hive.surveymanage.entity.IndustryEntitySurvey;
import com.hive.tagManage.entity.SNBase;
import com.hive.tagManage.entity.SNBatchSurvey;

import dk.dao.BaseDao;
import dk.service.BaseService;

/**
 * yf 20160303 add
 * @author Administrator
 *
 */
@Service
public class IndustryEntitySurveyService extends BaseService<IndustryEntitySurvey> {
	
	private Logger logger = Logger.getLogger(IndustryEntitySurveyService.class);
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Resource
	private BaseDao<IndustryEntitySurvey> industryEntitySurveyDao;
	
	@Override
	protected BaseDao<IndustryEntitySurvey> getDao() {
		return this.industryEntitySurveyDao;
	}
	
	@Resource
	private UserService userService;
	
	@Resource
	private IndustryEntitySurveyService industryEntitySurveyService;
	
	
    /**
      * 方法名称：getSurveyByIndustryEntityId
      * 功能描述：根据实体ID 获取绑定的问卷信息
      * 创建时间:2016年6月6日下午2:39:57
      * 创建人: pengfei Zhao
      * @return List<IndustryEntitySurvey>
     */
	public List<IndustryEntitySurvey> getSurveyByIndustryEntityId(String industryEntityId) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from IndustryEntitySurvey where 1=1 and industryEntityId = '"+industryEntityId+"' order by createTime asc");
		List<IndustryEntitySurvey> list = getDao().find(sb.toString());
		return list;
	}

	/**
	 * 根据行业实体id和问卷id获取行业实体问卷的信息
	 * @param objectId
	 * @param surveyId
	 * @return
	 */
	public IndustryEntitySurvey getLinkIdByParam(Long objectId, Long surveyId) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from IndustryEntitySurvey where surveyId='"+surveyId+"' and industryEntityId = '"+objectId+"' order by createTime asc");
		List<IndustryEntitySurvey> list = getDao().find(sb.toString());
		if(list.size()==1){
			return list.get(0);
		}else{
			return null;
		}
	} 
	/**
     * 方法名称：getSurveyByIndustryEntityId
     * 功能描述：根据实体ID和问卷Id判断是否存在
     * 创建时间:2016年6月6日下午2:39:57
     * 创建人: pengfei Zhao
     * @return List<IndustryEntitySurvey>
    */
	public List<IndustryEntitySurvey> exitEntityByIndustryandEntityId(String entityId,String surveyId) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from IndustryEntitySurvey where 1=1 and industryEntityId = '"+entityId+"'");
		List<IndustryEntitySurvey> list = getDao().find(sb.toString());
		return list;
	} 
	
}
