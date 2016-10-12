package com.hive.clientmanage.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.infomanage.model.NewsCommentBean;
import com.hive.integralmanage.entity.Integral;
import com.hive.membermanage.entity.MMember;
import com.hive.systemconfig.entity.SentimentKeyWord;
import com.hive.systemconfig.service.MemberGradeService;
import com.hive.util.DateUtil;

import dk.dao.BaseDao;
import dk.service.BaseService;
import dk.util.MD5;
/**
 * 
* Filename: ClientUserService.java  
* Description: 客户端用户service类
* Copyright:Copyright (c)2014
* Company:  GuangFan 
* @author:  yanghui
* @version: 1.0  
* @Create:  2014-3-31  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2014-3-31 下午2:19:45				yanghui 	1.0
 */
@Service
public class ClientUserService extends BaseService<MMember> {

	@Resource
	private BaseDao<MMember> clientUserDao;
	@Resource
	private BaseDao<SentimentKeyWord> sentimentKeyWordDao;
	@Override
	protected BaseDao<MMember> getDao() {
		return clientUserDao;
	}
	
	@Resource
	private MemberGradeService memberGradeService;
	
	/**
	 * 
	 * @Description: 通过用户的总积分修改相应的等级
	 * @author yanghui 
	 * @Created 2014-4-1
	 * @param bean
	 * @param integral
	 */
	public void updateUserGradeByTotalIntegral(NewsCommentBean bean,
			Integral integral) {
		Long totalIntegral = integral.getCurrentValue()+integral.getUsedValue(); //用户的总积分
		Long gradeId = null;
		gradeId = memberGradeService.getUserGradeByTotalIntegral(totalIntegral);
		MMember cuser = this.get(bean.getUserId());
		String level = cuser.getCmemberlevel(); //用户的等级
		if(!gradeId.equals(Long.valueOf(level))){
			cuser.setCmemberlevel(gradeId.toString());
			this.update(cuser);
		}
		
	}

	public List getIdsByUserName(String userName) {
		List list = getDao().find(" select id from "+getEntityName()+" where username like '%"+userName+"%' ");
		return list;
	}

	/**
	 * 
	 * @Description:  登录时校验密码是否正确
	 * @author yanghui 
	 * @Created 2014-4-17
	 * @param user
	 * @return
	 */
	public MMember checkUser(MMember user) {
		//加密密码
		String password = MD5.getMD5Code(user.getCpassword());
		String hql = " from "+getEntityName()+" where cusername=? and cpassword=? ";
		List list = getDao().find(hql, new Object[]{user.getCusername(),password});
		if( list!=null && list.size()==1){
			user = (MMember) list.get(0);
		}
		return user;
	}
	
	
	/**
	 * 
	 * @Description: 校验用户名是否存在
	 * @author YangHui 
	 * @Created 2014-8-21
	 * @param user
	 * @return
	 */
	public MMember checkUserName(MMember user) {
		String hql = " from "+getEntityName()+" where cusername=?";
		List list = getDao().find(hql, new Object[]{user.getCusername()});
		if( list!=null && list.size()==1){
			user = (MMember) list.get(0);
		}
		return user;
	}
	/**
	 * 
	 * @Description: 校验密码是否正确
	 * @author YangHui 
	 * @Created 2014-8-21
	 * @param user
	 * @return
	 */
	public boolean checkUserPwd(String username,String password){
		//加密密码
		password = MD5.getMD5Code(password);
		String hql = " from "+getEntityName()+" where cusername=? and cpassword=? ";
		List list = getDao().find(hql, new Object[]{username,password});
		if( list!=null && list.size()==1){
			return true;
		}else{
			return false;
		}
	}
	
	
	
/**
 * 
 * 
 * @Description: 注册时校验是否存在该用户
 * @author yanghui 
 * @Created 2014-4-22
 * @param user
 * @return
 */
	public MMember checkUserIsExist(MMember user) {
		String hql = " from "+getEntityName()+" where cusername=? and cmembertype=? ";
		List list = getDao().find(hql, new Object[]{user.getCusername(),user.getCmembertype()});
		if( list!=null && list.size()==1){
			user = (MMember) list.get(0);
		}
		return user;
	}

	/**
	 * 
	 * @Description: 注册时保存新用户
	 * @author yanghui 
	 * @Created 2014-4-22
	 * @param user
	 * @return
	 */
	public MMember saveNewUser(MMember user) {
		String password = MD5.getMD5Code(user.getCpassword());
		user.setCpassword(password);
		user.setCmemberlevel(SystemCommon_Constant.MEMBER_LEVEL_1);//会员级别
		user.setCvalid(SystemCommon_Constant.VALID_STATUS_1);
		if("0".equals(user.getCmembertype())){ //个人会员默认不用审核
			user.setCmemberstatus(SystemCommon_Constant.AUDIT_STATUS_1);
		}else{
			user.setCmemberstatus(SystemCommon_Constant.AUDIT_STATUS_0);
		}
		user.setDcreatetime(DateUtil.getTimestamp());
		this.save(user);
		return user;
	}

	/**
	 * 
	 * @Description:
	 * @author YangHui 
	 * @Created 2014-6-17
	 * @param userId
	 * @return
	 */
	public List<SentimentKeyWord> getSentimentKeyWord(Long userId) {
		String hql = " from SentimentKeyWord where userId=? and valid='1' ";
		List<SentimentKeyWord> list = sentimentKeyWordDao.find(hql, new Object[]{userId});
		return list;
	}

	public String getManagerName(String organizationCode) {
		String hql = " from "+getEntityName()+" where organizationCode=? and isManager=? ";
		List<MMember> list = getDao().find(hql, new Object[]{organizationCode,SystemCommon_Constant.SIGN_YES_1});
		String name = "";
		if(list.size()>0){
			name = list.get(0).getCusername();
		}
		return name;
	}

	public MMember getMemberByUserName(String cusername) {
		String hql = " from "+getEntityName()+" where cusername=?";
		List list = getDao().find(hql, new Object[]{cusername});
		MMember user = null;
		if( list!=null && list.size()==1){
			user = (MMember) list.get(0);
		}
		return user;
	}

	/**
	 * 根据mmember实例参数获取满足条件的所有mmember对象
	 * @param mmember
	 * @return
	 */
	public List<MMember> getAllByParams(MMember mmember) {
		String hql = " from "+getEntityName()+" where 1 =1 ";
		if(StringUtils.isNotBlank(mmember.getCusername())){
			hql = hql + " and cusername like '%"+mmember.getCusername()+"%' ";
		}
		if(StringUtils.isNotBlank(mmember.getCmembertype())){
			hql = hql + " and cmembertype = '"+mmember.getCmembertype()+"' ";
		}
		if(StringUtils.isNotBlank(mmember.getCmobilephone())){
			hql = hql + " and cmobilephone = '"+mmember.getCmobilephone()+"' ";
		}
		if(StringUtils.isNotBlank(mmember.getCardno())){
			hql = hql + " and cardno = '"+mmember.getCardno()+"' ";
		}
		List<MMember> list = getDao().find(hql);
		return list;
	}

}
