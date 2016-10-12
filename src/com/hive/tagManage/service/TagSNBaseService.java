package com.hive.tagManage.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;

import com.hive.permissionmanage.service.UserService;
import com.hive.tagManage.entity.SNBase;
import com.hive.tagManage.entity.SNBatch;
import com.hive.tagManage.entity.SNBatchCode;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class TagSNBaseService extends BaseService<SNBase> {
	
	private Logger logger = Logger.getLogger(TagSNBaseService.class);
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Resource
	private BaseDao<SNBase> SNBaseDao;

	@Override
	protected BaseDao<SNBase> getDao() {
		return this.SNBaseDao;
	}
	
	@Resource
	private TagBatchCodeService tagBatchCodeService;
	
	@Resource
	private TagSNBatchService tagSNBatchService;
	
	@Resource
	private UserService userService;
	
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

	/**
	 * 生产SN
	 */
	/*start*/
	private static long[] ls = new long[1000000];
	private static int li = 0;

	public synchronized static long getPK(long i) {
		return getpk(i);//20190929 yyf edit 删除一百万的for循环
	}

	private static long getpk(long i) {
		String a = (String.valueOf(System.currentTimeMillis())).substring(3, 13);
		String d = (String.valueOf(Math.random())).substring(2, 8);
		return Long.parseLong(a + d)+i;
	}
	
	/*end*/
	
	/**
	 * 批量插入SN的信息
	 * 
	 * @param statementName
	 * @param list
	 * @throws Exception
	 */
	public int insertSN(final List<Object[]> list,SNBatchCode snBaseCode,SNBatch snBatch) {
		String snbatchId = tagSNBatchService.save(snBatch).toString();
		if(StringUtils.isNotBlank(snBaseCode.getId())) {
			tagBatchCodeService.update(snBaseCode);
		} else {
			tagBatchCodeService.save(snBaseCode);
		}
		
		for(int i = 0; i < list.size(); i++) {
			list.get(i)[1] =snbatchId;
		}
		
		String insertSql = "INSERT INTO TAG_SNBASE (ID,SNBATCHID,SN,PSN,SEQUENCENUM, ISALLOT,QUERYPATH,status,queryNum,BLACKLIST) " + "values(?,?,?,?,?,?,?,0,0,0)"; // 在数据库上初始化了
		//String insertSql = "INSERT INTO TAG_SNBASE (ID,SNBATCHID,SN,SEQUENCENUM, ISALLOT,QUERYPATH, QUERYNUM, BLACKLIST) " + "values(?,?,?,?,?,?, 0, \"0\")"; // 将查询次数和是否黑名单初始化为0
		int[] result = this.simpleJdbcTemplate.batchUpdate(insertSql, list);
		int len = result.length;
		return result.length;
	}
	/**
	 * 批量插入SN的信息
	 * 20160929 yyf add
	 * @param sql
	 */
	public void insertSN(StringBuffer sql) {
		String insertSql = "INSERT INTO TAG_SNBASE (ID,SNBATCHID,SN,PSN,SEQUENCENUM, ISALLOT,QUERYPATH,status,queryNum,BLACKLIST) " + "values "; // 在数据库上初始化了
		insertSql += sql.substring(0,sql.length()-1)+";";
		this.jdbcTemplate.execute(insertSql);
	}
	/**
	 * 批量插入SN的信息的新方法
	 * 20160927 yyf add
	 * @param list
	 * @throws Exception
	 */
	public int insertSN(final List<Object[]> list) {
		String insertSql = "INSERT INTO TAG_SNBASE (ID,SNBATCHID,SN,PSN,SEQUENCENUM, ISALLOT,QUERYPATH,status,queryNum,BLACKLIST) " + "values(?,?,?,?,?,?,?,0,0,0)"; // 在数据库上初始化了
		int[] result = this.jdbcTemplate.batchUpdate(insertSql, list);
		return result.length;
	}
	
	
	/**
	 * 根据批次ID获取SN信息列表
	 * @Title: getAllotedSNDataGrid   
	 * @param @param page
	 * @param @param dealerApplyId 订单ID
	 * @param @return    设定文件  
	 * @return DataGrid    返回类型  
	 * @throws  
	 */
	public DataGrid getSNGridBySNBatch(RequestPage page, String snBatchId) {
		StringBuffer sb = new StringBuffer();
		StringBuffer sbCount = new StringBuffer();
		sb.append(" from SNBase where 1=1 and snbatchId = '"+snBatchId+"'");
		sbCount.append(" select count(*) from SNBase where 1=1 and snbatchId = '"+snBatchId+"'");
		sb.append(" order by sequenceNum asc");
		sbCount.append(" order by sn desc");
		List<SNBase> list = getDao().find(page.getPage(), page.getRows(), sb.toString(), new Object[0]);
		Long count = getDao().count(sbCount.toString(),new Object[0]).longValue();
		
		// 20150921 yf add  添加 sn序号
		List<SNBase> resultList = new ArrayList<SNBase>();
		if(list != null && list.size() > 0) {
			for(SNBase base : list) {
				String batchId = base.getSnBatchId();
				String snBatch = tagSNBatchService.get(batchId).getBatch();
				//base.setBqbh(snBatch+String.format("%07d", Integer.parseInt(base.getSequenceNum())));
				base.setBqbh(snBatch+String.format("%07d", base.getSequenceNum()));//修改 sequencenum类型为int　　yf 20150804 add
				resultList.add(base);
			}
		}
		
		return new DataGrid(count, resultList);
	}
	
	/**
	 * 获取批次中有效的数量
	 * @Title: getIsAllotByOne   
	 * @param @param snBatchId 批次ID
	 * @param @return    设定文件  
	 * @return Long    返回类型  
	 * @throws  
	 */
	public Long getIsAllotByOne(String snBatchId) {
		StringBuffer validAmounthql = new StringBuffer();
		validAmounthql.append(" select count(*) from SNBase  where 1=1  and isAllot=1 and snBatchId= '"+snBatchId+"' and psn=null");
		Long count = getDao().count(validAmounthql.toString(),new Object[0]).longValue();
		return count;
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
		sb.append(" from SNBase where 1=1 and snBatchId = '"+snBatchId+"' order by sn asc");
		List<SNBase> list = getDao().find(sb.toString());
		return list;
	}
	/**
	 * 根据生产批次更新状态
	 * @Title: getSNByBatch   
	 * @param @param createBatch 生产批次
	 * @param @return    设定文件  
	 * @return List<SNBase>    返回类型  
	 * @throws  
	 */
	public void updateSNStaByBatch(String snBatch,String sta) {
		SNBatch batch=tagSNBatchService.getEntityBybatch(snBatch);
		StringBuffer sb = new StringBuffer();
		sb.append(" update SNBase set status='"+sta+"' where snBatchId = '"+batch.getId()+"' ");
		getDao().execute(sb.toString());
	}
		
	/**
	 * 
	* @Title: querySNBaseList 
	* @Description: TODO(查询SNBase列表) 
	* @param @return    设定文件 
	* @return List<SNBase>    返回类型 
	* @throws
	 */
	public List<SNBase> querySNBaseList(int page, int rows, Map<String, Object> mapParam){
		StringBuffer hql = new StringBuffer();
		hql.append("From SNBase WHERE 1=1 ");
		if(mapParam.get("batch")!=null && mapParam.get("batch").toString().length()>0){
			SNBatch snbath = tagSNBatchService.getEntityBybatch(mapParam.get("batch").toString());
			if(snbath!=null){
				hql.append(" and snBatchId ='"+snbath.getId()+"'");
			}
		}
		if(mapParam.get("sn")!=null && mapParam.get("sn").toString().length()>0){
			hql.append(" and sn ='"+mapParam.get("sn")+"'");
		}
		if(mapParam.get("industryEntityId")!=null && mapParam.get("industryEntityId").toString().length()>0){
			SNBatch snbath = tagSNBatchService.getEntityByEntityId(mapParam.get("industryEntityId").toString());
			if(snbath!=null){
			hql.append(" and snBatchId ='"+snbath.getId()+"'");
			}
		}
		if(mapParam.get("blackList")!=null && mapParam.get("blackList").toString().length()>0){
			hql.append(" and blackList ="+mapParam.get("blackList"));
		}
		if(mapParam.get("queryNum")!=null && mapParam.get("queryNum").toString().length()>0){
			hql.append(" and queryNum ="+mapParam.get("queryNum"));
		}
		if(mapParam.get("status")!=null && mapParam.get("status").toString().length()>0){
			hql.append(" and status ="+mapParam.get("status"));
		}
		
		List<SNBatch> batchlist = querySNBaseList(mapParam);
		String batchId="";
		if(batchlist!=null){
			if(batchlist.size()==0){
				 batchId="0000000";
			}
			for (int i=0;i<batchlist.size();i++) {
				if(i>0){
					batchId+=",'"+batchlist.get(i).getId()+"'";
				}else{
					batchId+="'"+batchlist.get(i).getId()+"'";
				}
			}
		}
		if(StringUtils.isNotBlank(batchId)){
			hql.append(" and snBatchId in("+batchId+")");
		}
		
		hql.append(" order by sn, psn asc");
		//List<SNBase> list = this.getDao().find(page,rows,hql.toString(),new Object[0]);
		
		List<SNBase> list = this.getDao().find(page,rows,hql.toString(),new Object[0]);
		return list;
	}
	
	
	public List<SNBatch> querySNBaseList(Map<String, Object> mapParam){
		StringBuffer batchhql = new StringBuffer();
		boolean isok=false;
		batchhql.append("From SNBatch WHERE 1=1 ");
		if(mapParam.get("batch")!=null && mapParam.get("batch").toString().length()>0){
			batchhql.append(" and batch="+mapParam.get("batch"));
			isok=true;
		}
		if(mapParam.get("enterpriseName")!=null && mapParam.get("enterpriseName").toString().length()>0){
			batchhql.append(" and enterpriseName like '%"+mapParam.get("enterpriseName")+"%'");
			isok=true;
		}
		if(mapParam.get("productName")!=null && mapParam.get("productName").toString().length()>0){
			batchhql.append(" and productName like '%"+mapParam.get("productName")+"%'");
			isok=true;
		}
		if(mapParam.get("specifiName")!=null && mapParam.get("specifiName").toString().length()>0){ 
			batchhql.append(" and specifiName like '%"+mapParam.get("specifiName")+"%'");
			isok=true;
		}
		List<SNBatch> list = null;
		if(isok){
			list = tagSNBatchService.getDao().find(batchhql.toString(), new Object[0]);
		}
		return list;
		
	}
	
	
	public Long querySNBaseListCnt(Map<String, Object> mapParam){
		StringBuffer hql = new StringBuffer();
		hql.append("select count(*) from SNBase where 1=1 ");
		
		
		if(mapParam.get("batch")!=null && mapParam.get("batch").toString().length()>0){
			SNBatch snbath = tagSNBatchService.getEntityBybatch(mapParam.get("batch").toString());
			if(snbath!=null){
				hql.append(" and snBatchId ='"+snbath.getId()+"'");
			}
		}
		if(mapParam.get("sn")!=null && mapParam.get("sn").toString().length()>0){
			hql.append(" and sn ='"+mapParam.get("sn")+"'");
		}
		if(mapParam.get("industryEntityId")!=null && mapParam.get("industryEntityId").toString().length()>0){
			SNBatch snbath = tagSNBatchService.getEntityByEntityId(mapParam.get("industryEntityId").toString());
			if(snbath!=null){
			hql.append(" and snBatchId ='"+snbath.getId()+"'");
			}
		}
		if(mapParam.get("blackList")!=null && mapParam.get("blackList").toString().length()>0){
			hql.append(" and blackList ="+mapParam.get("blackList"));
		}
		if(mapParam.get("queryNum")!=null && mapParam.get("queryNum").toString().length()>0){
			hql.append(" and queryNum ="+mapParam.get("queryNum"));
		}
		if(mapParam.get("status")!=null && mapParam.get("status").toString().length()>0){
			hql.append(" and status ="+mapParam.get("status"));
		}
		List<SNBatch> batchlist = querySNBaseList(mapParam);
		String batchId="";
		if(batchlist!=null){
			if(batchlist.size()==0){
				 batchId="0000000";
			}
			for (int i=0;i<batchlist.size();i++) {
				if(i>0){
					batchId+=",'"+batchlist.get(i).getId()+"'";
				}else{
					batchId+="'"+batchlist.get(i).getId()+"'";
				}
			}
		}
		if(StringUtils.isNotBlank(batchId)){
			hql.append(" and snBatchId in("+batchId+")");
		}
		Long cnt = this.getDao().count(hql.toString(), new Object[0]).longValue();
		return cnt;
	}
	
	/**
	 * 
	 * @Title: queryBySN
	 * @Description: TODO(由SN查询SNBASE)
	 * @param @param sn
	 * @param @return    设定文件
	 * @return SNBase    返回类型
	 * @throws
	 */
	public SNBase queryBySN(String sn){
		String hql = "From SNBase where sn=?";
		return this.getDao().get(hql, sn);
	}

	public void deleteSnBaseBySNBatchId(String batchId) {
		String insertSql = "DELETE from tag_snbase where snbatchid ='"+batchId+"'";
		this.jdbcTemplate.execute(insertSql);
	}
	public static void main(String[] args) {
		System.out.println(String.valueOf(Math.random()));
	}
}
