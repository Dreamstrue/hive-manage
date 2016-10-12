package com.hive.evaluationManage.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.evaluationManage.entity.SurveyPartakeUser;
import com.hive.util.DataUtil;

import dk.dao.BaseDao;
import dk.service.BaseService;

@Service
public class SurveyPartakeUserService extends BaseService<SurveyPartakeUser> {

	@Resource
	private BaseDao<SurveyPartakeUser> surveyPartakeDao;
	@Override
	protected BaseDao<SurveyPartakeUser> getDao() {
		return surveyPartakeDao;
	}
	/**
	  * 功能描述：判断此发票信息是否已作废（已经参与过）
	  * 创建时间:2015年12月23日上午11:57:05
	  * 创建人: pengfei Zhao
	  * @param @param objectId
	  * @param @return 
	  * @return boolean
	 */
	public boolean checkOilCardNo(String oilcardNo) {
		String hql =" from "+getEntityName()+" where oilcardNo=? and valid=? ";
		List list = getDao().find(hql, new Object[]{oilcardNo,SystemCommon_Constant.VALID_STATUS_1});
		if(DataUtil.listIsNotNull(list)){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	  * 方法名称：checkEntity
	  * 功能描述：判断是否存在第三方（如加油站）问卷评价的实体信息
	  * 创建时间:2015年12月23日上午11:57:05
	  * 创建人: pengfei Zhao
	  * @param @param objectId
	  * @param @return 
	  * @return boolean
	 */
	public boolean checkEntity(String objectId) {
		String hql =" from "+getEntityName()+" where objectId=? and valid=? ";
		List list = getDao().find(hql, new Object[]{objectId,SystemCommon_Constant.VALID_STATUS_1});
		if(DataUtil.listIsNotNull(list)){
			return true;
		}else{
			return false;
		}
	}

	/**
	  * 功能描述：根据实体ID获取姓名
	  * 创建时间:2015年12月23日上午11:57:05
	  * 创建人: pengfei Zhao
	  * @param @param objectId
	  * @param @return 
	  * @return boolean
	 */
	public SurveyPartakeUser getNameById(Long Id) {
		String hql =" from "+getEntityName()+" where id=? and valid=? ";
		SurveyPartakeUser surveyPu  = getDao().get(hql, new Object[]{Id,SystemCommon_Constant.VALID_STATUS_1});
		return surveyPu;
	}
	
	
}
