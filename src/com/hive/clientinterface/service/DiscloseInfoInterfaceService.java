/**
 * 
 */
package com.hive.clientinterface.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Annex;
import com.hive.discloseInfo.entity.DiscloseInfo;
import com.hive.discloseInfo.entity.DiscloseInfoReply;
import com.hive.discloseInfo.model.DiscloseInfoBean;
import com.hive.discloseInfo.model.DiscloseInfoReplyBean;
import com.hive.membermanage.entity.MMember;
import com.hive.util.DataUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

/**  
 * Filename: DiscloseInfoInterfaceService.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  YangHui
 * @version: 1.0  
 * @Create:  2014-7-11  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-7-11 上午10:42:08				YangHui 	1.0
 */
@Service
public class DiscloseInfoInterfaceService extends BaseService<DiscloseInfo> {

	@Resource
	private BaseDao<DiscloseInfo> discloseInfoDao;
	@Resource
	private BaseDao<DiscloseInfoReply> discloseInfoReplyDao;
	@Resource
	private BaseDao<MMember> mmemberDao;
	@Resource
	private BaseDao<Annex> annexDao;
	
	
	@Override
	protected BaseDao<DiscloseInfo> getDao() {
		return discloseInfoDao;
		
		
	}
	
	/**
	 * 
	 * @Description: 爆料列表
	 * @author YangHui 
	 * @Created 2014-7-11
	 * @return
	 */
	public DataGrid getDiscloseInfoList(RequestPage page) {
		
		StringBuffer hql = new StringBuffer();
		hql.append("  from ").append(getEntityName()).append("  where ");
		hql.append(" auditStatus=? and shieldStatus=? ");
		hql.append(" order by createTime desc ");
		
		List list = getDao().find(hql.toString(), new Object[]{SystemCommon_Constant.AUDIT_STATUS_1,SystemCommon_Constant.REVIEW_SHIELD_STATUS_0});
		List<DiscloseInfoBean> blist = new ArrayList<DiscloseInfoBean>();
		for(Iterator it = list.iterator();it.hasNext();){
			 DiscloseInfo info = (DiscloseInfo) it.next();
			 DiscloseInfoBean bean = new DiscloseInfoBean();
			 Long replyNum = getReplyNumByDiscloseId(info.getId());
			 try {
				PropertyUtils.copyProperties(bean, info);
				bean.setReplyNum(replyNum);
				bean.setContent(DataUtil.getText(bean.getContent()));
				
				if(SystemCommon_Constant.SIGN_YES_1.equals(info.getIsPic())){
					//爆料的信息上传的图片信息
					String ahql = " from Annex where objectId=? and objectTable=? and cvalid = '"+SystemCommon_Constant.VALID_STATUS_1+"'";
					List<Annex> alist = annexDao.find(ahql, new Object[]{info.getId(),"M_DISCLOSE"});
					//存在上传的图片时，就取第一张图片作为列表图标展示
					if(DataUtil.listIsNotNull(alist)){
						bean.setPicPath(alist.get(0).getCfilepath()); 
						bean.setAnnexList(alist);
					}  
				}
				 
				 blist.add(bean);
			} catch (Exception e) {
				
			}
		}
		return new DataGrid(blist.size(),pageList(page, blist));
	}
	
	public Long getReplyNumByDiscloseId(Long id) {
		String hql = " select count(*) from DiscloseInfoReply where discloseId=? ";
		Long count = discloseInfoReplyDao.count(hql, new Object[]{id});
		return count;
	}

	public List pageList(RequestPage page ,List beanList){
		List childList = new ArrayList();
		if(beanList.size()>0){
			int pageNo = page.getPage();
			int pageSize = page.getRows();
			int begin = (pageNo - 1) * pageSize;
			int end = (pageNo * pageSize >= beanList.size() ? beanList.size(): pageNo * pageSize);
			childList = beanList.subList(begin, end);
		}
		return childList;
		
	}

	/**
	 * 
	 * @Description: 保存爆料信息
	 * @author YangHui 
	 * @Created 2014-7-11
	 * @param info
	 */
	public void saveDiscloseInfo(DiscloseInfo info) {
		discloseInfoDao.save(info);
	}

	
	public void saveDiscloseReply(DiscloseInfoReply reply) {
		discloseInfoReplyDao.save(reply);
	}

	/**
	 * 
	 * @Description: 爆料信息的评论列表
	 * @author YangHui 
	 * @Created 2014-7-11
	 * @param id
	 * @param tableNameReply
	 * @return
	 */
	public List<DiscloseInfoReplyBean> getReplyList(Long id,
			String tableNameReply) {
		List<DiscloseInfoReplyBean> beanlist = new ArrayList<DiscloseInfoReplyBean>();
		String hql = " from DiscloseInfoReply where discloseId=? order by replyTime desc ";
		List<DiscloseInfoReply> list = discloseInfoReplyDao.find(hql, new Object[]{id});
		for(int i=0;i<list.size();i++){
			DiscloseInfoReply reply = list.get(i);
			DiscloseInfoReplyBean  bean = new DiscloseInfoReplyBean();
			try {
				PropertyUtils.copyProperties(bean, reply);
				if(SystemCommon_Constant.SIGN_YES_1.equals(reply.getIsPic())){
					//爆料的信息上传的图片信息
					String ahql = " from Annex where objectId=? and objectTable=? and cvalid = '"+SystemCommon_Constant.VALID_STATUS_1+"'";
					List<Annex> alist = annexDao.find(ahql, new Object[]{reply.getId(),tableNameReply});
					bean.setAnnexList(alist);
				}
				Long replyUserId = reply.getReplyUserId();
				if(replyUserId!=null){
					bean.setReplyUserName(getUserName(replyUserId));
				}
				beanlist.add(bean);
			} catch (Exception e) {
			}
		}
		return beanlist;
	}

	/**
	 * 
	 * @Description: 爆料信息下的评论列表信息
	 * @author YangHui 
	 * @Created 2014-7-14
	 * @param page
	 * @param id
	 * @param tableNameReply 
	 * @return
	 */
	public DataGrid getReplyInfoList(RequestPage page, Long id, String tableNameReply) {
		List<DiscloseInfoReplyBean> blist = getReplyList(id, tableNameReply);
		return new DataGrid(blist.size(),pageList(page, blist));
	}
	
	
	
	
	public String getUserName(Long userId) {
		String username ="" ;
		List<MMember> list = mmemberDao.find(" from MMember where nmemberid = ?  and cvalid = '1' ", userId);
		if(DataUtil.listIsNotNull(list)){
			MMember m = list.get(0);
			if("1".equals(m.getCmembertype())){
				username = m.getConciseName();
			}else{
				username = m.getCnickname();
			}
		}
		return username;
	}
}
