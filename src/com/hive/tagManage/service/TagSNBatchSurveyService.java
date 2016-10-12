package com.hive.tagManage.service;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hive.permissionmanage.service.UserService;
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
public class TagSNBatchSurveyService extends BaseService<SNBatchSurvey> {
	
	private Logger logger = Logger.getLogger(TagSNBatchSurveyService.class);
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Resource
	private BaseDao<SNBatchSurvey> SNBatchSurvyDao;
	
	@Override
	protected BaseDao<SNBatchSurvey> getDao() {
		return this.SNBatchSurvyDao;
	}
	
	@Resource
	private UserService userService;
	
	@Resource
	private TagSNBaseService tagSNBaseService;
	
	

	/**
	 * yf 20160303 add
	 * 根据标签批次获取对应的  SNBatchSurvey  用来获取对应的问卷
	 * @Title: getSNByBatch   
	 * @param @param createBatch 生产批次
	 * @param @return    设定文件  
	 * @return List<SNBase>    返回类型  
	 * @throws  
	 */
	public List<SNBatchSurvey> getSNBatchSurveyByBatch(String snBatchId) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from SNBatchSurvey where 1=1 and snBatchId = '"+snBatchId+"' order by createTime asc");
		List<SNBatchSurvey> list = getDao().find(sb.toString());
		return list;
	}

	/**
	 * 获取批次问卷信息
	 * @param batchId
	 * @param surveyId
	 * @return
	 */
	public SNBatchSurvey getLinkIdByParam(String batchId, String surveyId) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from SNBatchSurvey where surveyId='"+surveyId+"' and snBatchId = '"+batchId+"' ");
		List<SNBatchSurvey> list = getDao().find(sb.toString());
		if(list.size()==1){
			return list.get(0);
		}else{
			return null;
		}
	} 
	
	
}
