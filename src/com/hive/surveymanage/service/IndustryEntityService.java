package com.hive.surveymanage.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.enterprisemanage.entity.EEnterpriseinfo;
import com.hive.membermanage.entity.MMember;
import com.hive.surveymanage.entity.CObject;
import com.hive.surveymanage.entity.IndustryEntity;
import com.hive.surveymanage.entity.SurveyIndustry;
import com.hive.surveymanage.model.IndustryEntityBean;
import com.hive.util.DataUtil;
import com.sun.xml.internal.fastinfoset.sax.Properties;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class IndustryEntityService extends BaseService<IndustryEntity> {

	@Resource
	private BaseDao<IndustryEntity> industryEntityDao;
	@Resource
	private SurveyIndustryService surveyIndustryService;
	@Resource
	private EntityCategoryService entityCategoryService;
	@Resource
	private SurveyService surveyService;
	@Override
	protected BaseDao<IndustryEntity> getDao() {
		return industryEntityDao;
	}
	
	public List<IndustryEntity> allSurveyIndustry() {
		return getDao().find(
				" from " + getEntityName() + " where valid = " + SystemCommon_Constant.VALID_STATUS_1 + " order by sort asc");
	}
	
	public DataGrid datagrid(RequestPage page,String entityName,String entityType,String entityCategory,String cauditstatus)
	{
	    StringBuilder hql = new StringBuilder();

	    StringBuilder countHql = new StringBuilder();

	    hql.append("FROM ").append(getEntityName()).append(" WHERE valid = ").append("1");
	    countHql.append("select count(*) FROM ").append(getEntityName()).append(" WHERE valid = ").append("1");

	    if (StringUtils.isNotBlank(entityName)) {
	      hql.append(" AND entityName like '%").append(entityName).append("%'");
	      countHql.append(" AND entityName ='%").append(entityName).append("%'");
	       }
	    if (StringUtils.isNotBlank(entityType)) {
		      hql.append(" AND entityType ='").append(entityType).append("'");
		      countHql.append(" AND entityType ='").append(entityType).append("'");
		   }
	    if (StringUtils.isNotBlank(entityCategory)) {
		      hql.append(" AND entityCategory ='").append(entityCategory).append("'");
		      countHql.append(" AND entityCategory ='").append(entityCategory).append("'");
		   }
	    if (StringUtils.isNotBlank(cauditstatus)) {
		      hql.append(" AND cauditstatus ='").append(cauditstatus).append("'");
		      countHql.append(" AND cauditstatus ='").append(cauditstatus).append("'");
		    }
	    if (!StringUtils.isEmpty(page.getSort())) {
	        hql.append(" ORDER BY ").append(page.getSort()).append(" ").append(page.getOrder());
	      }else{
	    	hql.append(" ORDER BY createtime desc");
	      }
	    long count = getDao().count(countHql.toString(), new Object[0]).longValue();
	    List<IndustryEntity> entInfoList = getDao().find(page.getPage(), page.getRows(), hql.toString(), new Object[0]);
	    List<IndustryEntityBean> entityList = new ArrayList<IndustryEntityBean>();
	    for(IndustryEntity insty:entInfoList){
	    	IndustryEntityBean ieb=new IndustryEntityBean();
	    	String typeName=surveyIndustryService.getNamebyId(insty.getEntityType());
	    	String entityCategoryName=entityCategoryService.getNamebyId(insty.getEntityCategory());
	    	insty.setEntityCategory(entityCategoryName);
	    	try {
				PropertyUtils.copyProperties(ieb, insty);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	ieb.setEntityTypeName(typeName);
	    	Long surveyId=ieb.getSurveyId();
	    	if(surveyId!=null&&!surveyId.equals("")){
	    		String surveyTitle=surveyService.get(surveyId).getSubject();
	    		ieb.setSurveyTitle(surveyTitle);
	    	}else{
	    		ieb.setSurveyTitle("未绑定问卷");
	    	}
	    	entityList.add(ieb);
	    }
	    return new DataGrid(count, entityList);
	}
	
	public IndustryEntityBean getIndstryEntiyBeanForEntity(IndustryEntity ie){
		IndustryEntityBean ieb=new IndustryEntityBean();
		String typeName=surveyIndustryService.getNamebyId(ie.getEntityType());
    	String entityCategoryName=entityCategoryService.getNamebyId(ie.getEntityCategory());
    	try {
			PropertyUtils.copyProperties(ieb, ie);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	ieb.setEntityCategory(entityCategoryName);
    	ieb.setEntityTypeName(typeName);
    	Long surveyId=ieb.getSurveyId();
    	if(surveyId!=null&&!surveyId.equals("")){
    		String surveyTitle=surveyService.get(surveyId).getSubject();
    		ieb.setSurveyTitle(surveyTitle);
    	}else{
    		ieb.setSurveyTitle("未绑定问卷");
    	}
    	String queryPath = SystemCommon_Constant.QRCODE_PATH;
    	ieb.setQueryPath(queryPath+ie.getId());
    	return ieb;
	}
	
	
	/**
	 * 逻辑删除
	 */
	public void delete_logic(Long id) {
		industryEntityDao.execute("UPDATE " + getEntityName() + " SET valid = ? WHERE id=? ", new Object[]{SystemCommon_Constant.VALID_STATUS_0,id});
	}
	/**
	 * 逻辑删除实体信息
	 */
    public void delEntInfo(IndustryEntity induEntity)
    {
	 induEntity.setValid("0");
     getDao().update(induEntity);
    }
    
    /**
	 * 实体审核操作
	 * @param ids
	 * @param status
	 */
	public void checkEntity(String ids,String status){
		String[] idArray = ids.split(",");
		for(String id : idArray){
			IndustryEntity induEntity = this.get(Long.valueOf(id));
			induEntity.setCauditstatus(status);
			if(induEntity!=null){
				this.update(induEntity);
			}
		}
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
	  * 方法名称：checkEntity
	  * 功能描述：查看是否实体名称重复
	  * 创建时间:2015年12月24日上午11:48:42
	  * 创建人: pengfei Zhao
	  * @return boolean
	 */
	public boolean checkEntity(String entityName) {
		String hql =" from "+getEntityName()+" where entityName=? and valid=? ";
		List list = getDao().find(hql, new Object[]{entityName,SystemCommon_Constant.VALID_STATUS_1});
		if(list.size()>0){
			return true;
		}else{
			return false;
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
	 * @Description:查询所有行业实体
	 * @author YYF 
	 * @Created 2016-1-18
	 * @return
	 */
	public List<IndustryEntity> getAllIndustryEntityList(String entityName) {
		String hql =" from "+getEntityName()+" where valid='1' ";
		if(StringUtils.isNotBlank(entityName)){
			hql += " and entityName like '%"+entityName+"%'";
		}
		return getDao().find(hql, new Object[0]);
	}
	/**
	 * 
	 * @Description:查询行业实体根据外系统ID
	 * @author zpf
	 * @Created 2016-06-24
	 * @return
	 */
	public List<IndustryEntity> getAllIndustryEntityListForOther(String otherId) {
		String hql =" from "+getEntityName()+" where valid='1' and otherId in ("+otherId+")";
		return getDao().find(hql, new Object[0]);
	}
	
	public List<IndustryEntity> getIndustryEntityInfo() {
		String hql = " from "+ getEntityName()+" where valid =? and cauditstatus =?  order by createtime desc ";
		List<IndustryEntity> list = this.getDao().find(hql, new Object[]{SystemCommon_Constant.VALID_STATUS_1,SystemCommon_Constant.AUDIT_STATUS_1});
		return list;
		}
	/**
	 * 根据外部系统的objectid获取在本系统对应的实体信息
	 * @param objectId
	 */
	public List<IndustryEntity> findEntityByOutSysObjectId(Long objectId,String objectType) {
		String hql = "select sie from "+ getEntityName()+" sie,CObject cob where sie.objectId=cob.id and sie.valid = "+SystemCommon_Constant.VALID_STATUS_1+" and cob.objectId = "+objectId+
				" and cob.objectType='"+objectType+"' order by sie.entityName ";
		List<IndustryEntity> list = this.getDao().find(hql);
		return list;
		
	}
	/**
	 * 获取所有绑定过问卷的行业实体 20160616 yyf add
	 * @return
	 */
	public List<IndustryEntity> findAllBindedSurveyEntity() {
		String hql = "from "+ getEntityName()+" sie where sie.surveyId is not null ";
		List<IndustryEntity> list = this.getDao().find(hql);
		return list;
	}
	}