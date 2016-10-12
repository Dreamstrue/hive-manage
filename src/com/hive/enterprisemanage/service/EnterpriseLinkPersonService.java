package com.hive.enterprisemanage.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.enterprisemanage.entity.EnterpriseLinkPerson;

import dk.dao.BaseDao;
import dk.service.BaseService;
@Service
public class EnterpriseLinkPersonService extends
		BaseService<EnterpriseLinkPerson> {

	@Resource
	private BaseDao<EnterpriseLinkPerson> enterpriseLinkPersonDao;

	@Override
	protected BaseDao<EnterpriseLinkPerson> getDao() {
		return enterpriseLinkPersonDao;
	}

	 /**
	  * 
	  * @Description:  保存企业联系人信息
	  * @author yanghui 
	  * @Created 2014-4-21
	  * @param persons
	  * @param phones
	 * @param entInfoId 
	 * @param memberId 
	  */
	public void saveLinkPerson(String[] persons, String[] phones, Long memberId, Long entInfoId) {
		for(int i=0;i<persons.length;i++){
			EnterpriseLinkPerson elp = new EnterpriseLinkPerson();
			elp.setMemberId(memberId);
			elp.setEnterpriseId(entInfoId);
			elp.setLinkPersonName(persons[i]);
			elp.setLinkPhone(phones[i]);
			this.save(elp);
		}
		
	}

	
	/**
	 * 
	 * @Description: 查询企业的联系人
	 * @author yanghui 
	 * @Created 2014-4-21
	 * @param enterId
	 * @return
	 */
	public List<EnterpriseLinkPerson> getLinkPersonsByEnterpriseId(Long enterId) {
		List<EnterpriseLinkPerson> list = new ArrayList<EnterpriseLinkPerson>();
		list = enterpriseLinkPersonDao.find(" from "+getEntityName()+" where enterpriseId=?", enterId);
		return list;
	}

}
