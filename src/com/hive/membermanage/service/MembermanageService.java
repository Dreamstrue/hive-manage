package com.hive.membermanage.service;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.membermanage.entity.MMember;
import com.hive.membermanage.entity.MPaymentrecords;
import com.hive.membermanage.model.Paymentrecords;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class MembermanageService extends BaseService<MMember>{
	private static final Logger logger = Logger.getLogger(MembermanageService.class);
	@Resource
	private BaseDao<MMember> memberDao;//会员信息表
	
	@Override
	protected BaseDao<MMember> getDao() {
		return memberDao;
	}
	
	@Resource
	private BaseDao<MPaymentrecords> paymentrecordsDao;//缴费记录表
	@Resource
	private SessionFactory sessionFactory;
	
	//-----------------以下会员审核代码-----------------------	
	/**
	 * 功能描述：查询待审核注册用户
	 * 创建时间:2013-11-28下午4:40:01
	 * 创建人: Ryu Zheng
	 * 
	 * @param page
	 * @param userName
	 * @return
	 */
	public DataGrid findUncheckUser(RequestPage page, String userName,String status){
		//
		StringBuffer hql = new StringBuffer(" FROM ").append(getEntityName()).append(" WHERE cmemberstatus='"+status+"' AND cvalid='"+SystemCommon_Constant.VALID_STATUS_1+"'");
		StringBuffer countHql = new StringBuffer("select count(*) FROM ").append(getEntityName()).append(" WHERE cmemberstatus='"+status+"' AND cvalid='"+SystemCommon_Constant.VALID_STATUS_1+"'");
		/**  modify by YangHui 2014-08-21  
		 * 由于一个企业下可以注册多个用户，且第一个注册的为管理员级用户，其他为非管理员用户    后台管理审核用户时只审核管理员级的用户 ，非管理员用户由管理员通过手机客户端进行审核    
		 * begin  
		 
		hql.append(" and isManager='"+SystemCommon_Constant.SIGN_YES_1+"'");
		countHql.append(" and isManager='"+SystemCommon_Constant.SIGN_YES_1+"'");
		
		/**  modify by YangHui 2014-08-21 end  */
		// 用户名
		if(StringUtils.isNotBlank(userName)){
			hql.append(" AND cusername LIKE '%").append(userName).append("%'");
			countHql.append(" AND cusername LIKE '%").append(userName).append("%'");
		}
		
		
		hql.append(" order by dcreatetime desc");
		
		
		long count=getDao().count(countHql.toString(),new Object[0]).longValue();
		List<MMember> list = getDao().find(page.getPage(), page.getRows(), hql.toString(),new Object[0]);
		return new DataGrid(count,list);
	}
	/**
	 * 功能描述：注册用户管理
	 * 创建时间:2013-11-28下午4:40:01
	 * 创建人: Ryu Zheng
	 * 
	 * @param page
	 * @param userName
	 * @return
	 */
	public DataGrid findUser(RequestPage page,String chinesename, String userName,String status,String cmobilephone,String cmemberstatus){
		//
		StringBuffer hql = new StringBuffer(" FROM ").append(getEntityName()).append(" WHERE 1=1 AND cvalid='"+SystemCommon_Constant.VALID_STATUS_1+"'");
		StringBuffer countHql = new StringBuffer("select count(*) FROM ").append(getEntityName()).append(" WHERE 1=1 AND cvalid='"+SystemCommon_Constant.VALID_STATUS_1+"'");
		// 姓名
		if(StringUtils.isNotBlank(chinesename)){
			hql.append(" AND chinesename LIKE '%").append(chinesename).append("%'");
			countHql.append(" AND chinesename LIKE '%").append(chinesename).append("%'");
		}
		// 用户名
		if(StringUtils.isNotBlank(userName)){
			hql.append(" AND cusername LIKE '%").append(userName).append("%'");
			countHql.append(" AND cusername LIKE '%").append(userName).append("%'");
		}
		// 手机号
		if(StringUtils.isNotBlank(cmobilephone)){
			hql.append(" AND cmobilephone = '").append(cmobilephone).append("'");
			countHql.append(" AND cmobilephone = '").append(cmobilephone).append("'");
		}
		// 手机号
		if(StringUtils.isNotBlank(cmemberstatus)){
			hql.append(" AND cmemberstatus = '").append(cmemberstatus).append("'");
			countHql.append(" AND cmemberstatus = '").append(cmemberstatus).append("'");
		}
		
		hql.append(" order by dcreatetime desc");
		
		
		long count=getDao().count(countHql.toString(),new Object[0]).longValue();
		List<MMember> list = getDao().find(page.getPage(), page.getRows(), hql.toString(),new Object[0]);
		return new DataGrid(count,list);
	}
	/**
	 * 会员审核操作
	 * @param ids
	 * @param status
	 */
	public void checkMember(String ids,String status){
//		memberDao.execute("UPDATE MMember SET cmemberstatus=? WHERE nmemberid IN("+ids+")", status);
		/*
		 * 20150915 yanyanfei add 会员信息审核不能使用，故做一下修改，原来的注释掉(以上代码)
		 */
		String[] idArray = ids.split(",");
		for(String id : idArray){
			MMember m = this.get(Long.valueOf(id));
			m.setCmemberstatus(status);
			if(m!=null){
				this.update(m);
			}
		}
	}
	
	//-----------------以下缴费管理代码-----------------------	
	/**
	 * 功能描述：查询缴费记录(按人统计)
	 * 创建时间:2013-11-30上午11:23:25
	 * 创建人: Ryu Zheng
	 * 
	 * @param page
	 * @param userName 用户名
	 * @param memberType 会员类型
	 * @return
	 */
	public DataGrid listPaymentRecords(RequestPage page, String userName, String memberType){
		List<Paymentrecords> list=null;
		Long count=0L;
		Session session=sessionFactory.getCurrentSession();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT cast(M.nmemberid as char(50)) NMEMBERID, ");
		sql.append(" M.cusername CUSERNAME, ");
		sql.append(" cast(M.cmembertype as char(50)) CMEMBERTYPE, ");
		sql.append(" M.cmemberlevel CMEMBERLEVEL, ");
		sql.append(" cast(M.cmemberstatus as char(50)) CMEMBERSTATUS, ");
		sql.append(" M.cmobilephone CMOBILEPHONE, ");
		sql.append(" IFNULL(T.paysum ,0.00) PAYSUM, ");
		sql.append(" IFNULL(T.paytimes, 0) PAYTIMES");
		sql.append(" FROM m_member M ");
		sql.append(" left join (SELECT P.nmemberid, ");
		sql.append(" SUM(P.npaysum) PAYSUM, ");
		sql.append(" Count(P.nmemberid) PAYTIMES ");
		sql.append(" FROM m_paymentrecords P ");
		sql.append(" GROUP BY P.nmemberid) T ");
		sql.append(" ON T.nmemberid = M.nmemberid ");
		sql.append(" WHERE 1=1 ");
		
		// 用户名
		if(StringUtils.isNotBlank(userName)){
			sql.append(" AND M.cusername LIKE '%").append(userName).append("%' ");
		}
		
		// 会员类型
		if(StringUtils.isNotBlank(memberType)){
			sql.append(" AND M.cmembertype ='").append(memberType).append("' ");
		}
		
		// 排序
		if(StringUtils.isNotBlank(page.getOrder())){
			sql.append(" ORDER BY ");
			sql.append(page.getSort()).append(" ").append(page.getOrder());
		}
		StringBuffer sqlStrCnt = new StringBuffer(sql);
		if (page != null) {
			sqlStrCnt.replace(7, sqlStrCnt.indexOf("FROM") - 1, "COUNT(*)");
			BigInteger cnt = (BigInteger) session.createSQLQuery(
					sqlStrCnt.toString()).uniqueResult();
			count=cnt.longValue();
			logger.info("total count: "+count);
			list = session
					.createSQLQuery(sql.toString())
					.setFirstResult((page.getPage() - 1) * page.getRows())
					.setMaxResults( page.getRows())
					.setResultTransformer(
							Transformers.aliasToBean(Paymentrecords.class))
					.list();
		}
		return new DataGrid(count,list);
	}
	
	/**
	 * 根据员工ID查询缴费记录
	 * @param page
	 * @param recordid
	 * @return
	 */
	public DataGrid listPaymentRecordsByMember(RequestPage page,Long memberid){
		StringBuffer hql=new StringBuffer("FROM MPaymentrecords ");
		if(StringUtils.isNotBlank(page.getOrder())){
			hql.append(" WHERE nmemberid=? ORDER BY ");
			hql.append(page.getSort()).append(" ").append(page.getOrder());
		}
		String counthql="select count(*) from MPaymentrecords where nmemberid=?";
		long count=getDao().count(counthql,memberid);
		logger.debug("find all size : "+count);
		List<MMember> list = getDao().find(page.getPage(), page.getRows(), hql.toString(),memberid);
		logger.debug("list size is : "+list.size());
		return new DataGrid(count,list);
	}
	
	/**
	 * 功能描述： 获取某会员的缴费记录<br>
	 * 创建时间:2013-12-24下午4:39:54<br>
	 * 创建人: Ryu Zheng<br>
	 * 
	 * @param memberId
	 * @return
	 */
	public List<MPaymentrecords> listPaymentRecordsOf(Long memberId){
		List<MPaymentrecords> resultList = new ArrayList<MPaymentrecords>();
		if(memberId == null){
			return resultList;
		}
		
		StringBuffer hql=new StringBuffer("FROM MPaymentrecords WHERE cvalid = '1' ");
		hql.append(" AND nmemberid = ").append(memberId);
		resultList = paymentrecordsDao.find(hql.toString());
		return resultList;
	}
	
	/**
	 * 添加缴费记录
	 * @param payment
	 */
	public void addPaymentRecord(MPaymentrecords payment){
		paymentrecordsDao.save(payment);
	}
	
	/**
	 * 功能描述： 删除缴费记录列表<br>
	 * 创建时间:2013-12-24下午4:48:44<br>
	 * 创建人: Ryu Zheng<br>
	 * 
	 * @param paymentRecordsList
	 * @return
	 */
	public int delPaymentRecords(List<MPaymentrecords> paymentRecordsList){
		if(paymentRecordsList == null || paymentRecordsList.size() < 1){
			return 0;
		}
		
		String ids = StringUtils.EMPTY;
		int listSize = paymentRecordsList.size();
		for(int i=0; i < listSize; i++){
			MPaymentrecords record = paymentRecordsList.get(i);
			Long payrecId = record.getNpayrecid();
			if(i == (listSize - 1)){
				ids += payrecId;
			}else{
				ids += payrecId+",";
			}
			
		}
		int count = paymentrecordsDao.execute("UPDATE MPaymentrecords SET cvalid = '0' WHERE npayrecid IN("+ids+")");
		return count;
	}
	
	/**
	 * 功能描述： 根据企业ID查询相应的会员信息<br>
	 * 创建时间:2013-12-24下午2:59:43<br>
	 * 创建人: Ryu Zheng<br>
	 * 
	 * @param enterpriseId
	 * @return
	 */
	public List<MMember> findByEnterpriseId(Long enterpriseId){
		List<MMember> resultList = new ArrayList<MMember>();
		if(enterpriseId == null){
			return resultList;
		}
		
		StringBuffer hql = new StringBuffer("FROM MMember WHERE cvalid = '1' ");
		hql.append(" AND nenterpriseid = ").append(enterpriseId);
		
		resultList = getDao().find(hql.toString());
		return resultList;
	}
	
	/**
	 * 功能描述：  根据企业ID获取相应的会员信息记录<br>
	 * 创建时间:2013-12-24下午3:47:50<br>
	 * 创建人: Ryu Zheng<br>
	 * 
	 * @param enterpriseId
	 * @return
	 */
	public MMember getByEnterpriseId(Long enterpriseId){
		List<MMember> memberList = findByEnterpriseId(enterpriseId);
		MMember member = null;
		if(memberList != null && memberList.size() > 0){
			member = memberList.get(0);
		}
		
		return member;
	}
	/**
	 * 功能描述：判断是否存在此userName<br>
	 * @param enterpriseId
	 * @return
	 */
	public MMember getByuserName(String userName){
		StringBuffer hql = new StringBuffer("FROM MMember WHERE cvalid = '1' AND cusername =?");
		MMember member=memberDao.get(hql.toString(), userName);
		return member;
	}
	
	/**
	 * 功能描述： 删除会员列表<br>
	 * 创建时间:2013-12-24下午3:12:46<br>
	 * 创建人: Ryu Zheng<br>
	 * 
	 * @param memberList
	 * @return
	 */
	public int delMembers(List<MMember> memberList){
		if(memberList == null || memberList.size() < 1){
			return 0;
		}
		
		String ids = StringUtils.EMPTY;
		int listSize = memberList.size();
		for(int i=0; i < listSize; i++){
			MMember member = memberList.get(i);
			Long memberId = member.getNmemberid();
			if(i == (listSize - 1)){
				ids += memberId;
			}else{
				ids += memberId+",";
			}
			
		}
		int count = memberDao.execute("UPDATE MMember SET cvalid = '0' WHERE nmemberid IN("+ids+")");
		return count;
	}
	
	/**
	 * 功能描述： 删除会员 <br>
	 * 创建时间:2013-12-24下午3:50:07<br>
	 * 创建人: Ryu Zheng<br>
	 * 
	 * @param member
	 */
	public void delMember(MMember member){
		if(member != null){
			member.setCvalid(SystemCommon_Constant.VALID_STATUS_0);
			memberDao.update(member);
		}
	}

	
	
	/**
	 * 
	 * @Description:  根据组织机构代码判断是否 已经注册过
	 * @author yanghui 
	 * @Created 2014-4-18
	 * @param code
	 * @return
	 */
	public boolean checkEnterpriseIsRegistedByOrganCode(String code) {
		String hql = " from "+getEntityName()+" where organizationCode=?";
		List<MMember> list = getDao().find(hql, code);
		if(list.size()>0){
			return true;
		}
		return false;
	}
	public MMember findByPhone(String phone,String userName) {
		String hql = " from "+getEntityName()+" where cmobilephone=? and chinesename = ?";
		List<MMember> list = getDao().find(hql, phone,userName);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
}
