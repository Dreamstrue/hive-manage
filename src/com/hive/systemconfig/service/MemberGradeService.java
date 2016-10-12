/**
 * 
 */
package com.hive.systemconfig.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.systemconfig.entity.MemberGrade;

import dk.dao.BaseDao;
import dk.service.BaseService;

/**  
 * Filename: MemberGradeService.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-3-31  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-3-31 下午5:33:20				yanghui 	1.0
 */
@Service
public class MemberGradeService extends BaseService<MemberGrade> {

	@Resource
	private BaseDao<MemberGrade> memberGradeDao;

	@Override
	protected BaseDao<MemberGrade> getDao() {
		return memberGradeDao;
	}

	/**
	 * 
	 * @Description: 通过用户的当前累计积分，判断所属的等级
	 * @author yanghui 
	 * @Created 2014-4-1
	 * @param totalIntegral
	 * @return
	 */
	public Long getUserGradeByTotalIntegral(Long totalIntegral) {
		List<MemberGrade> list = getDao().find(" from "+ getEntityName(), new Object[0]);
		Long gradeId = null;
		for(int i=0;i<list.size();i++){
			MemberGrade mg = list.get(i);
			gradeId = mg.getId();  //循环当前记录的ID值
			Long gradeIntegral = mg.getGradeIntegral();
			if(totalIntegral<gradeIntegral){
				//如果用户目前的累计总积分小于被选择记录的等级积分,则取得上一条记录的等级，然后跳出循环
				gradeId = list.get(i-1).getId();
				break;
			}else if(totalIntegral==gradeIntegral){
				break;
			}
		}
		return gradeId;
	}
	
}
