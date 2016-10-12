package com.hive.surveymanage.service;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hive.permissionmanage.service.UserService;
import com.hive.surveymanage.entity.IndustryEntitySurvey;
import com.hive.surveymanage.entity.SurveyIndustrySurvey;
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
public class SurveyIndustrySurveyService extends BaseService<SurveyIndustrySurvey> {
	
	private Logger logger = Logger.getLogger(SurveyIndustrySurveyService.class);
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Resource
	private BaseDao<SurveyIndustrySurvey> surveyIndustrySurveyDao;
	
	@Override
	protected BaseDao<SurveyIndustrySurvey> getDao() {
		return this.surveyIndustrySurveyDao;
	}
	
	@Resource
	private UserService userService;
	
	
    /**
      * 方法名称：getSurveyByIndustryEntityId
      * 功能描述：根据实体ID 获取绑定的问卷信息
      * 创建时间:2016年6月6日下午2:39:57
      * 创建人: pengfei Zhao
      * @return List<IndustryEntitySurvey>
     */
	public List<SurveyIndustrySurvey> getSurveyByIndustryEntityId(String surveyIndustryId) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from SurveyIndustrySurvey where 1=1 and surveyIndustryId = '"+surveyIndustryId+"' order by createTime asc");
		List<SurveyIndustrySurvey> list = getDao().find(sb.toString());
		return list;
	} 
	
	/**
     * 方法名称：getSurveyByIndustryEntityId
     * 功能描述：根据实体ID和问卷Id判断是否存在
     * 创建时间:2016年6月6日下午2:39:57
     * 创建人: pengfei Zhao
     * @return List<IndustryEntitySurvey>
    */
	public List<SurveyIndustrySurvey> exitEntityByIndustryandsurveyId(String IndustryId,String surveyId) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from SurveyIndustrySurvey where 1=1 and surveyIndustryId = '"+IndustryId+"'");
		List<SurveyIndustrySurvey> list = getDao().find(sb.toString());
		return list;
	} 
}
