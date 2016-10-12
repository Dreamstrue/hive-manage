package com.hive.common.service;



import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.hive.common.entity.Sequence;


import dk.dao.BaseDao;
import dk.service.BaseService;

@Service
public class SequenceService extends BaseService<Sequence> {

	
	@Resource
	private BaseDao<Sequence> sequenceDao;
	@Override
	protected BaseDao<Sequence> getDao() {
		return sequenceDao;
	}
	@Resource
	private SessionFactory  sessionFactory;
	/**
	 * 根据seqType获取其最新的序列号
	 * @param seqType
	 * @return
	 */
	public String createSequenceBySeqType(String seqType){
		StringBuffer hql = new StringBuffer();
		hql.append("select nextval('"+seqType+"') from dual ");
		Query query  = sessionFactory.getCurrentSession().createSQLQuery(hql.toString());
		String batchNumber = (String)query.uniqueResult();
		return batchNumber;
	}
	/**
	 * 根据seqCode获取序列实体
	 * @param seqCode
	 * @return
	 */
	public Sequence findSequenceBySeqCode(String seqCode) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Sequence where seqCode = '"+seqCode+"'");
		List<Sequence> list = this.sequenceDao.find(hql.toString());
		if(list.size()==1){
			return list.get(0);
		}else{
			return null;
		}
	}
	

}
