package com.hive.tagManage.service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;



import com.hive.tagManage.entity.SNBatchCode;

import dk.dao.BaseDao;
import dk.service.BaseService;

@Service
public class TagBatchCodeService extends BaseService<SNBatchCode> {
	
	private Logger logger = Logger.getLogger(TagBatchCodeService.class);
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Resource
	private BaseDao<SNBatchCode> snBatchCodeDao;
	
	@Override
	protected BaseDao<SNBatchCode> getDao() {
		return this.snBatchCodeDao;
	}
	
	/**
	 *  更批次的前半部分判断批次是否存在
	 * @Title: getBatchBybfirst   
	 * @param @param batchFrist
	 * @param @return    设定文件  
	 * @return SNBatchCode    返回类型  
	 * @throws  
	 */
	public SNBatchCode getBatchBybfirst(String batchFrist) {
		SNBatchCode bc = getDao().get("FROM SNBatchCode WHERE BATCHFIRST=?", batchFrist);
		if(bc != null) {
			return bc;
		} else {
			return null;
		}
	}
	
	public int updateBatchBybfirst(String batchFirst,String batchLast) {
		Map map = new HashMap();
		map.put("batchLast", batchLast);
		map.put("batchFirst", batchFirst);
		return getDao().execute("UPDATE SNBatchCode SET batchLast=:batchLast WHERE batchFirst=:batchFirst",map);
	
	}

}
