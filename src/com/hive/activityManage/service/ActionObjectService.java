package com.hive.activityManage.service;




import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hive.activityManage.entity.ActionObject;
import com.hive.activityManage.entity.Activity;
import com.hive.activityManage.entity.ActivityObjectLink;
import com.hive.activityManage.model.ActionObjectVo;
import com.hive.activityManage.model.ActivityVo;
import com.hive.tagManage.entity.SNBatch;
import com.hive.tagManage.entity.SNBatchSurvey;
import com.hive.tagManage.service.TagSNBatchService;
import com.hive.tagManage.service.TagSNBatchSurveyService;
import com.hive.util.DateUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;
/**
 * yyf 20160616 add
 * 
 */
@Service
public class ActionObjectService extends BaseService<ActionObject> {

	@Resource
	private BaseDao<ActionObject> actionObjectDao;
	@Override
	protected BaseDao<ActionObject> getDao() {
		return actionObjectDao;
	}
	@Resource
	private TagSNBatchSurveyService tagSNBatchSurveyService;
	@Resource
	private TagSNBatchService tagSNBatchService;
	@Resource
	private ActivityObjectLinkService activityObjectLinkService;
	
	/**
	 * 根据活动id获取此活动下所有的作用对象
	 * @param page
	 * @param activityId
	 * @return
	 */
	public DataGrid dataGrid(RequestPage page,Long activityId) {
		String hql = "select ao from " + getEntityName()+" ao,ActivityObjectLink aol where ao.id = aol.actionObjectId and aol.activityId="+activityId+" and aol.isValid <> '0'  ";
		String countsql = "select count(*) from "+getEntityName()+" ao,ActivityObjectLink aol where ao.id = aol.actionObjectId and aol.activityId="+activityId+"  and aol.isValid <> '0'  ";
		
		List<ActionObject> list = getDao().find(page.getPage(), page.getRows(),hql);
		long count = getDao().count(countsql);
		List<ActionObjectVo> voList = new ArrayList<ActionObjectVo>();
		for(ActionObject a : list){
			ActionObjectVo avo = new ActionObjectVo();
			try {
				PropertyUtils.copyProperties(avo, a);
				//活动对象中间表
				ActivityObjectLink activityObjectLink = activityObjectLinkService.findByAnctionObjectId(a.getId());
				if(activityObjectLink.getIsValid().equals("0")){
					avo.setAostatus("0");
				}else if(activityObjectLink.getIsValid().equals("1")){
					avo.setAostatus("1");
				}else if(activityObjectLink.getIsValid().equals("2")){
					avo.setAostatus("2");
				}
				String actionObjectName="";
				if(a.getActionObjectType().equals("2")){
					String linkId = a.getLinkId();
					SNBatchSurvey  sns = tagSNBatchSurveyService.get(linkId);
					if(sns!=null){
						String batchId = sns.getSnBatchId();
						SNBatch  snb = tagSNBatchService.get(batchId);
						if(snb!=null){
							actionObjectName = snb.getBatch();
						}
					}
				}else if(a.getActionObjectType().equals("1")){
					actionObjectName = a.getObjectName();
				}
				avo.setActionObjectName(actionObjectName);
				voList.add(avo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		return new DataGrid(count, voList);
	}
	/**
	 * 校验是否存在
	 * @param actionObjectType
	 * @param linkId
	 * @return
	 */
	public ActionObject findByLinkIdAndactionObjectType(String actionObjectType, String linkId) {
		String hql = "from " + getEntityName()+" where actionObjectType = '"+actionObjectType+"' and linkId = '"+linkId+"' ";
		List<ActionObject> list = this.getDao().find(hql);
		if(list.size()==1&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

}
