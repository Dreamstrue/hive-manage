package com.hive.tagManage.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;










import com.hive.common.SystemCommon_Constant;
import com.hive.permissionmanage.entity.User;
import com.hive.permissionmanage.service.UserService;
import com.hive.tagManage.entity.SNBatch;
import com.hive.tagManage.entity.SNBatchCode;
import com.hive.tagManage.entity.SNBatchSurvey;
import com.hive.tagManage.model.SNBatchVo;
import com.hive.util.DateUtil;
import com.hive.util.IdFactory;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class TagSNBatchService extends BaseService<SNBatch> {
	
	private Logger logger = Logger.getLogger(TagSNBatchService.class);
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Resource
	private BaseDao<SNBatch> SNBatchDao;
	
	@Resource
	private BaseDao<SNBatchSurvey> sNBatchSurveyDao;
	@Resource
	private TagBatchCodeService tagBatchCodeService;
	@Resource
	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;	
	@PostConstruct
	public void setJdbcTemplate() {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	@Override
	protected BaseDao<SNBatch> getDao() {
		return this.SNBatchDao;
	}
	
	@Resource
	private UserService userService;
	
	@Resource
	private TagSNBaseService tagSNBaseService;
	
	@Resource
	private TagSNBatchSurveyService tagSNBatchSurveyService;
	
	
	
	/**
	 * SNBatch列表
	 * @Title: datagrid   
	 * @param @param page 分页对象
	 * @throws  
	 */
	public DataGrid datagrid(RequestPage page, SNBatch snBatch) {
		String batch = snBatch.getBatch()==null?"":snBatch.getBatch();
		String entityId = snBatch.getIndustryEntityId()==null?"":snBatch.getIndustryEntityId();
		Integer status = snBatch.getStatus()==null?3:snBatch.getStatus();
		
		StringBuffer hql = new StringBuffer();
		StringBuffer counthql = new StringBuffer();//记录总条数语句
		hql.append(" from " + getEntityName() +" where 1=1 ");
		counthql.append(" select count(*) from " + getEntityName() + " a  where 1=1 " );
		
		if(batch!="" && !"".equals(batch)) {
			hql.append(" and batch like '%"+batch+"%'");
			counthql.append(" and batch like '%"+batch+"%'");
		}
		if(entityId!="" && !"".equals(entityId)) {
			hql.append(" and industryEntityId="+entityId);
			counthql.append(" and industryEntityId="+entityId);
		}
		if(status != 3) {
			hql.append(" and status = "+status);
			counthql.append(" and status = "+status);
		}
		hql.append(" order by batch desc ");
		
		List<SNBatch> list = getDao().find(page.getPage(), page.getRows(), hql.toString(), new Object[0]);
		List<SNBatch> resultList = new ArrayList<SNBatch>();
		List<SNBatchVo> resultList2 = new ArrayList<SNBatchVo>();
		for (SNBatch snb : list ) {
			//获取创建人姓名
			User user = userService.get(Long.parseLong(snb.getCreater()));
			snb.setCreaterName(user.getFullname());
			int validAmount=0;
			snb.setValidAmount(Long.valueOf(validAmount));
			resultList.add(snb);
			
			//yf 20160304 add 
			SNBatchVo sNBatchVo = new SNBatchVo();
			sNBatchVo.setId(snb.getId());
			sNBatchVo.setBatch(snb.getBatch());
			sNBatchVo.setCreater(snb.getCreater());
			sNBatchVo.setCreaterName(snb.getCreaterName());
			sNBatchVo.setCreateTime(snb.getCreateTime());
			sNBatchVo.setEntityName(snb.getEntityName());
			sNBatchVo.setIndustryEntityId(snb.getIndustryEntityId());
			sNBatchVo.setStatus(snb.getStatus());
			sNBatchVo.setValidAmount(snb.getValidAmount());
			sNBatchVo.setAmount(snb.getAmount());
			
			 String surveyId = "";
			 String surveyTitle= "";
			List<SNBatchSurvey> res = tagSNBatchSurveyService.getSNBatchSurveyByBatch(snb.getId());
			if(null != res && res.size() >= 1){
				int i = 1;
				for(SNBatchSurvey sNBatchSurvey:res){
					surveyId += sNBatchSurvey.getSurveyId();
					if(i < res.size()){
						surveyId += ",";
					}
					surveyTitle += sNBatchSurvey.getSurveyTitle();
					if(i < res.size()){
						surveyTitle += ",";
					}
					i++;
				}
			}
			sNBatchVo.setSurveyId(surveyId);
			sNBatchVo.setSurveyTitle(surveyTitle);
			resultList2.add(sNBatchVo);
		}
		
		Long count = getDao().count(counthql.toString(),new Object[0]).longValue();

		return new DataGrid(count, resultList2);
	}
	
	
	
		
		public SNBatch getEntityBybatch(String batch) {
			SNBatch snBatch=new SNBatch();
			String hql = " from "+ getEntityName()+" where 1=1 and batch=? order by createtime desc ";
			snBatch=SNBatchDao.get(hql, batch);
			return snBatch;
		}
		
		public SNBatch getEntityByEntityId(String batch) {
			SNBatch snBatch=new SNBatch();
			String hql = " from "+ getEntityName()+" where 1=1 and industryEntityId=? order by createtime desc ";
			snBatch=SNBatchDao.get(hql, batch);
			return snBatch;
		}
	/**
	 * 根据行业实体id获取其所有绑定过问卷的批次
	 * yyf 20160615 add
	 * @return
	 */
	public List<SNBatch> getAllBindedSurveyBatchByIndustryEntityId(Long industryEntityId) {
		String hql = "select snb from "+ getEntityName()+" snb , SNBatchSurvey sns  where snb.id = sns.snBatchId and snb.industryEntityId="+industryEntityId+"  order by snb.createTime desc ";
		return SNBatchDao.find(hql);
	}
	/**
	 * 批量插入标签批次
	 * 20160927 yyf add
	 * @param list
	 * @throws Exception
	 */
	public int insertSNBatch(final List<Object[]> list) {
		String insertSql = "INSERT INTO TAG_SNBATCH (ID,BATCH,AMOUNT,CREATER,CREATETIME, STATUS,ENTITYNAME,INDUSTRYENTITYID) " + "values(?,?,?,?,?,?,?,?)"; // 在数据库上初始化了
		int[] result = this.jdbcTemplate.batchUpdate(insertSql, list);
		return result.length;
	}




	public List<SNBatchSurvey> getBindedSurveyByBatchId(String batchId) {
		 String hql = "select sns from "+ getEntityName()+" snb , SNBatchSurvey sns  where snb.id = sns.snBatchId and snb.id='"+batchId+"'  order by sns.createTime desc ";
		 return sNBatchSurveyDao.find(hql);
	}
	/**
	 * 批量生成后处理标签的方法
	 * @param curUserId
	 * @param numbers
	 * @param batchCount
	 * @param nyr
	 * @return
	 * @throws Throwable 
	 */
	public void createSNAfter(String curUserId, String numbers, Integer batchCount, String nyr) throws Throwable{
		try {
			synchronized(this) {
				long beginTime = new Date().getTime();
				long codeSumTime = 0;
				long zzSumTime = 0;
				long snSumTime = 0;
				long insertSumTime = 0;
				long uuidSumTime = 0;
				long getsnSumTime = 0;
				for (int j = 0; j < batchCount; j++) {
					StringBuffer sql = new StringBuffer();
					long codebeginTime = new Date().getTime();
					//判断在标签批次戳里有无记录（Tag_BatchCode）
					SNBatchCode snBaseCode = tagBatchCodeService.getBatchBybfirst(nyr);
					if(snBaseCode != null) {
						snBaseCode.setBatchLast((Integer.parseInt(snBaseCode.getBatchLast())+1)+"");
						tagBatchCodeService.update(snBaseCode);
					} else {
						snBaseCode = new SNBatchCode();
						snBaseCode.setBatchFirst(nyr);
						snBaseCode.setBatchLast("1");
						tagBatchCodeService.save(snBaseCode);
					}
					String batchCode = snBaseCode.getBatchFirst()+(snBaseCode.getBatchLast()+"");
					long codeendTime = new Date().getTime();
					codeSumTime += (codeendTime-codebeginTime);
					long zzbeginTime = new Date().getTime();
					/***** 组装批次信息  START *****/
					SNBatch snBatch = new SNBatch();
					snBatch.setBatch(batchCode);
					snBatch.setAmount(Integer.parseInt(numbers));
					snBatch.setValidAmount(Long.valueOf(numbers));
					snBatch.setCreater(curUserId);
					snBatch.setCreateTime(new Date());
					snBatch.setStatus(0);
					snBatch.setEntityName("通用型");
					snBatch.setIndustryEntityId("");
					this.getDao().save(snBatch);
					/***** 组装批次信息  END *****/
					long zzendTime = new Date().getTime();
					zzSumTime += (zzendTime-zzbeginTime);
					long snbeginTime = new Date().getTime();
					/***** 组装SN信息  START *****/
					int num=Integer.parseInt(numbers);
					String queryPath = SystemCommon_Constant.QRCODE_PATH;
					queryPath+="&SNId=";
					for(int i = 0; i <num; i ++) {
						long sn=0;
						long uuidbeginTime = new Date().getTime();
						String id = IdFactory.getUuid();
						long uuidendTime = new Date().getTime();
						uuidSumTime += (uuidendTime-uuidbeginTime);
						long getsnbeginTime = new Date().getTime();
						sn = tagSNBaseService.getPK(i);
						long getsnendTime = new Date().getTime();
						getsnSumTime += (getsnendTime-getsnbeginTime);
						sql.append("('"+id+"','"+snBatch.getId()+"','"+sn+"',null,"+i+",1,'"+queryPath+id+"',0,0,0),");
					}
					long snendTime = new Date().getTime();
					snSumTime += (snendTime-snbeginTime);
					/***** 组装SN信息  END *****/
					long insertbeginTime = new Date().getTime();
					tagSNBaseService.insertSN(sql);
					long insertendTime = new Date().getTime();
					insertSumTime += (insertendTime-insertbeginTime);
				}
				long endTime = new Date().getTime();
				System.out.println("保存和更新批次总时间："+DateUtil.formatTime(codeSumTime));
				System.out.println("组装批次信息总时间："+DateUtil.formatTime(zzSumTime));
				System.out.println("获取uuid总时间："+DateUtil.formatTime(uuidSumTime));
				System.out.println("获取sn码总时间："+DateUtil.formatTime(getsnSumTime));
				System.out.println("组装SN信息总时间："+DateUtil.formatTime(snSumTime));
				System.out.println("SN插入总时间："+DateUtil.formatTime(insertSumTime));
				System.out.println("总时间："+DateUtil.formatTime(endTime - beginTime));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Throwable("批量生成数据异常！");
		}
	}
}
