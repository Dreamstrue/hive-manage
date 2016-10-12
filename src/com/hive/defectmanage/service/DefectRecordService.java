package com.hive.defectmanage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hive.defectmanage.entity.DefectRecord;
import com.hive.defectmanage.entity.DefectRecordCar;
import com.hive.defectmanage.entity.DefectRecordCarContacts;
import com.hive.defectmanage.entity.DefectRecordCarDefect;
import com.hive.defectmanage.entity.DefectRecordCarInfo;
import com.hive.defectmanage.model.DefectRecordCardBean;
import com.hive.tagManage.entity.SNBase;
import com.hive.tagManage.entity.SNBatch;
import com.hive.util.DataUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.service.BaseService;

@Service
public class DefectRecordService extends BaseService<DefectRecord>   {

	private Logger logger = Logger.getLogger(DefectRecordService.class);
	
	@Resource
	private BaseDao<DefectRecord> defectRecordDao;
	@Override
	protected BaseDao<DefectRecord> getDao() {
		return this.defectRecordDao;
	}
	/**
	 * 
	* @Title: queryDefectList 
	* @Description: TODO(查询玩具和其他消费品缺陷列表) 
	* @param @return    设定文件 
	* @return List<SNBase>    返回类型 
	* @throws
	 */
	public DataGrid queryDefectList(int page, int rows, Map<String, Object> mapParam){
		StringBuffer hql = new StringBuffer();
		StringBuffer conthql = new StringBuffer();
		hql.append("From " + getEntityName() + " WHERE 1=1 ");
		if(mapParam.get("peporttype")!=null && mapParam.get("peporttype").toString().length()>0){
			hql.append(" and peporttype ="+mapParam.get("peporttype"));
		}
		if(mapParam.get("reportusername")!=null && mapParam.get("reportusername").toString().length()>0){
			hql.append(" and reportusername ='"+mapParam.get("reportusername")+"'");
		}
		if(mapParam.get("reportuserphone")!=null && mapParam.get("reportuserphone").toString().length()>0){
			hql.append(" and reportuserphone ='"+mapParam.get("reportuserphone")+"'");
		}
		if(mapParam.get("prodName")!=null && mapParam.get("prodName").toString().length()>0){
			hql.append(" and prodName ='"+mapParam.get("prodName")+"'");
		}
		hql.append(" order by reportdate desc");
		List<DefectRecord> list = this.getDao().find(page,rows,hql.toString(),new Object[0]);
		return new DataGrid(list.size(),list);
	}
	
}
