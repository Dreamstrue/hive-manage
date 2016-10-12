package com.hive.sendMsg.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.sendMsg.entity.Checkcode;

import dk.dao.BaseDao;
import dk.service.BaseService;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：Pengfei Zhao
 * <p/>创建日期：2016年3月24日
 * <p/>创建时间：上午10:42:14
 * <p/>功能描述：[短信验证码]Service
 * <p/>===========================================================
 */
@Service
public class CheckCodeService extends BaseService<Checkcode> {

	@Resource
	private BaseDao<Checkcode> checkCodeDao;
	@Override
	protected BaseDao<Checkcode> getDao() {
		return checkCodeDao;
	}
	
	
	/**
	  * 方法名称：getCodoInfoByMobile
	  * 功能描述：根据手机号获取有效的验证码信息
	  * 创建时间:2016年3月24日上午10:50:39
	  * 创建人: pengfei Zhao
	  * @return List<Checkcode>
	 */
	public List<Checkcode> getCodoInfoByMobile(String mobile) {
		StringBuffer sb = new StringBuffer(" FROM "+ getEntityName());
		sb.append(" WHERE mobile='"+mobile+"' AND cvalid = '"+SystemCommon_Constant.VALID_STATUS_1+"' ORDER BY createTime desc ");
		List<Checkcode> list = checkCodeDao.find(sb.toString());
		return list;
	}
	

}
