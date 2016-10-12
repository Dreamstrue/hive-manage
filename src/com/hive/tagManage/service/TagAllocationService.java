package com.hive.tagManage.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;






import com.hive.membermanage.entity.MMember;
import com.hive.permissionmanage.entity.User;
import com.hive.tagManage.entity.SNAllocation;
import com.hive.tagManage.entity.SNBase;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class TagAllocationService extends BaseService<SNBase> {
	
	private Logger logger = Logger.getLogger(TagAllocationService.class);
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	@Resource
	private BaseDao<SNBase> snBaseDao;

	@Override
	protected BaseDao<SNBase> getDao() {
		return this.snBaseDao;
	}
	
	@Resource
	private BaseDao<SNAllocation> snAllocationDao;
	
	public BaseDao<SNAllocation> getSnAllocationDao() {
		return snAllocationDao;
	}
	
	
	/**
	 * 申请用户dao
	 */
	@Resource
	public BaseDao<MMember> memberDao;

	public BaseDao<MMember> getMemberDao() {
		return memberDao;
	}
	
	@Resource
	public BaseDao<User> userDao;

	public BaseDao<User> getUserDao() {
		return userDao;
	}

	
	@Resource
	private DataSource dataSource;

	private SimpleJdbcTemplate simpleJdbcTemplate;
	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	public void setSimpleJdbcTemplate() {
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}
	
	@PostConstruct
	public void setJdbcTemplate() {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	
	@Resource
	private TagSNBatchService tagSNBatchService;
	
	public List<SNAllocation> queryByApplyId(String applyId){
		String hql = "From SNAllocation where dealerapplyId=?";
		return this.getSnAllocationDao().find(hql, applyId);
	}
	
	/**
	 * 根据订单 显示已经分配的SN信息列表
	 * @Title: getAllotedSNDataGrid   
	 * @param @param page
	 * @param @param dealerApplyId 订单ID
	 * @param @return    设定文件  
	 * @return DataGrid    返回类型  
	 * @throws  
	 */
	public DataGrid getSNListDataGridByApplyId(RequestPage page, String dealerApplyId, String dealerApplyInfoId) {
		String snIdArray = getSNByDealerApplyId(dealerApplyId,dealerApplyInfoId);
		StringBuffer sb = new StringBuffer();
		String arr = String.valueOf(snIdArray);
		sb.append(" from SNBase where 1=1 and id in (" + arr + ")");
		List<SNBase> list = getDao().find(page.getPage(), page.getRows(), sb.toString(), new Object[0]);
		List<SNBase> resultList = new ArrayList<SNBase>();
		if(list != null && list.size() > 0) {
			for(SNBase base : list) {
				String batchId = base.getSnBatchId();
				String snBatch = tagSNBatchService.get(batchId).getBatch();
				base.setBqbh(snBatch+String.format("%07d", base.getSequenceNum()));
				
				resultList.add(base);
			}
		}
		
		return new DataGrid(resultList.size(), resultList);
	}
	
	
	public List<SNBase> getSNListByApplyId(String dealerApplyId, String dealerApplyInfoId) {
		String snIdArray = getSNByDealerApplyId(dealerApplyId,dealerApplyInfoId);
		StringBuffer sb = new StringBuffer();
		String arr = String.valueOf(snIdArray);
		sb.append(" from SNBase where 1=1 and id in ("+arr+")");
		List<SNBase> list = getDao().find(sb.toString(), new Object[0]);
		List<SNBase> resultList = new ArrayList<SNBase>();
		if(list != null && list.size() > 0) {
			for(SNBase base : list) {
				String batchId = base.getSnBatchId();
				String snBatch = tagSNBatchService.get(batchId).getBatch();
				base.setBqbh(snBatch+String.format("%07d", base.getSequenceNum()));
				
				resultList.add(base);
			}
		}
		
		return resultList;
	}
	

	/**
	 * 根据订单ID（DealerApply的ID）获取所分配的SN的数组
	 * @Title: getSNByDealerApplyId   
	 * @param @param dealerApplyId 订单ID
	 * @param @return    设定文件  
	 * @return List    返回类型  订单所包含 的集合
	 * @throws  
	 */
	public String getSNByDealerApplyId(String dealerApplyId,String dealerApplyInfoId) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from SNAllocation where dealerapplyId='"+dealerApplyId+"'");
		sb.append(" and applyInfoId='"+dealerApplyInfoId+"'");
		List<SNAllocation> list = this.snAllocationDao.find(sb.toString());
		List<String> idList = null;
		if(list != null && list.size() > 0) {
			idList = new ArrayList<String>();
			SNAllocation allocation = null;
			for(int i = 0;i < list.size();i++) {
				allocation = list.get(i);
				idList.add("'"+allocation.getSnBaseId()+"'");
			}
			return org.apache.commons.lang3.StringUtils.join(idList.toArray(),",");  
		}
		return "";
	}

	
	
	
	
	
	/**
	* @Title: queryBySNBaseId 
	* @Description: TODO(根据SN标签ID查询标签分配实体类6) 
	* @param @param snBaseId
	* @param @return    设定文件 
	* @return SNAllocation    返回类型 
	* @throws
	 */
	public SNAllocation queryBySNBaseId(String snBaseId){
		String hql = "From SNAllocation where snBaseId=?";
		return this.getSnAllocationDao().get(hql, snBaseId);
	}
	/**
	 * 根据sncode获取到标签信息
	 * @param snCode
	 */
	public SNBase getSnBaseBySn(String snCode) {
		String hql = "FROM SNBase WHERE sn = '"+snCode+"' ";
		List<SNBase> list = this.snBaseDao.find(hql);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 根据Psn获取到所有子标签的信息
	 * @param psn
	 */
	public List<SNBase> getSnBaseByPsn(String psn) {
		String hql = "FROM SNBase WHERE psn = '"+psn+"' ";
		List<SNBase> list = this.snBaseDao.find(hql);
			return list;
	}
	/**
	 * 根据生产批次获取SN集合
	 * @Title: getSNByBatch   
	 * @param @param createBatch 生产批次
	 * @param @return    设定文件  
	 * @return List<SNBase>    返回类型  
	 * @throws  
	 */
	public List<SNBase> getSNByBatch(String snBatchId) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from SNBase where 1=1 and snBatchId = '"+snBatchId+"'");
		List<SNBase> list = getDao().find(sb.toString());
		return list;
	}
}
