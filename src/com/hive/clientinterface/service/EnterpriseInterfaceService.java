/**
 * 
 */
package com.hive.clientinterface.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Service;

import com.hive.common.AnnexFileUpLoad;
import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Annex;
import com.hive.enterprisemanage.entity.EEnterpriseinfo;
import com.hive.enterprisemanage.entity.EEnterpriseproduct;
import com.hive.enterprisemanage.entity.EnterpriseCustomerGroup;
import com.hive.enterprisemanage.entity.EnterpriseGroupLink;
import com.hive.enterprisemanage.entity.EnterpriseRoomAttention;
import com.hive.enterprisemanage.entity.EnterpriseRoomConsult;
import com.hive.enterprisemanage.entity.EnterpriseRoomDiscount;
import com.hive.enterprisemanage.entity.EnterpriseRoomDynamic;
import com.hive.enterprisemanage.entity.EnterpriseRoomPicture;
import com.hive.enterprisemanage.entity.EnterpriseRoomProduct;
import com.hive.enterprisemanage.entity.EnterpriseRoomReply;
import com.hive.enterprisemanage.entity.EnterpriseRoomReview;
import com.hive.enterprisemanage.entity.EnterpriseRoomShow;
import com.hive.enterprisemanage.model.EnterpriseAndDynamicBean;
import com.hive.enterprisemanage.model.EnterpriseCostomer;
import com.hive.enterprisemanage.model.EnterpriseCustomerGroupBean;
import com.hive.enterprisemanage.model.EnterpriseProductBean;
import com.hive.enterprisemanage.model.EnterpriseRoomConsultBean;
import com.hive.enterprisemanage.model.EnterpriseRoomReplyBean;
import com.hive.enterprisemanage.model.EnterpriseRoomReviewBean;
import com.hive.enterprisemanage.model.EnterpriseSearchBean;
import com.hive.enterprisemanage.model.EnterpriseShowBean;
import com.hive.membermanage.entity.MMember;
import com.hive.util.DataUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

/**  
 * Filename: EnterpriseInterfaceService.java  
 * Description:
 * Copyright:Copyright (c)2014
 * Company:  GuangFan 
 * @author:  yanghui
 * @version: 1.0  
 * @Create:  2014-5-9  
 * Modification History:  
 * Date								Author			Version
 * ------------------------------------------------------------------  
 * 2014-5-9 上午9:50:45				yanghui 	1.0
 */
@Service
public class EnterpriseInterfaceService extends BaseService<EnterpriseRoomShow> {
	
	@Resource
	private BaseDao<EnterpriseRoomShow> enterpriseRoomShowDao;
	@Resource
	private BaseDao<EnterpriseRoomPicture> enterpriseRoomPictureDao;
	@Resource
	private BaseDao<EnterpriseRoomDynamic> enterpriseRoomDynamicDao;
	@Resource
	private BaseDao<EnterpriseRoomReview> enterpriseRoomReviewDao;
	@Resource
	private BaseDao<EnterpriseRoomReply> enterpriseRoomReplyDao;
	@Resource
	private BaseDao<EnterpriseRoomDiscount> enterpriseRoomDiscountDao;
	@Resource
	private BaseDao<EnterpriseRoomProduct> enterpriseRoomProductDao;
	@Resource
	private BaseDao<EEnterpriseinfo> enterpriseInfoDao;
	@Resource
	private BaseDao<EnterpriseRoomAttention> enterpriseRoomAttentionDao;
	@Resource
	private BaseDao<EnterpriseRoomConsult> enterpriseRoomConsultDao;
	@Resource
	private BaseDao<EnterpriseCustomerGroup> enterpriseCustomerGroupDao;
	@Resource
	private BaseDao<EnterpriseGroupLink> enterpriseGroupLinkDao;
	@Resource
	private BaseDao<EEnterpriseproduct> enterpriseProductDao;
	@Resource
	private BaseDao<MMember> mmemberDao;
	@Resource
	private BaseDao<Annex> annexDao;
	@Override
	protected BaseDao<EnterpriseRoomShow> getDao() {
		return enterpriseRoomShowDao;
	}

	/**
	 * 
	 * @Description: 企业空间照片列表
	 * @author yanghui 
	 * @Created 2014-5-9
	 * @param enterId
	 * @return
	 */
	public List<EnterpriseRoomPicture> getEnterpriseRoomPictureList(Long enterId) {
		List<EnterpriseRoomPicture> list = new ArrayList<EnterpriseRoomPicture>();
		String hql = " from EnterpriseRoomPicture where parentId =? and valid = '"+SystemCommon_Constant.VALID_STATUS_1+"'";
		list = enterpriseRoomPictureDao.find(hql, enterId);
		//取得图片路径后，需要判断该图片是否存在磁盘上面，如果不存在，则删除该条图片路径
		List<EnterpriseRoomPicture> subList = new ArrayList<EnterpriseRoomPicture>();
		subList = getPictureExistList(list);
		return subList;
	}

	
	private List<EnterpriseRoomPicture> getPictureExistList(List<EnterpriseRoomPicture> list) {
		List<EnterpriseRoomPicture> subList = new ArrayList<EnterpriseRoomPicture>();
		for(int i=0;i<list.size();i++){
			EnterpriseRoomPicture pic = list.get(i);
			String lj = pic.getPicturePath();
			boolean flag = AnnexFileUpLoad.checkFileIsExist(lj);
			if(flag){
				subList.add(pic);
			}
		}
		return subList;
	}

	/**
	 * 
	 * @Description:  企业秀信息
	 * @author yanghui 
	 * @Created 2014-5-9
	 * @param enterId
	 * @return
	 */
	public EnterpriseRoomShow getEnterpriseRoomShowInfo(Long enterId) {
		EnterpriseRoomShow show = null;
		String hql = " from EnterpriseRoomShow where enterpriseId =?";
		List<EnterpriseRoomShow>  list  = enterpriseRoomShowDao.find(hql, enterId);
		if(list!=null && list.size()>0){
			show =  list.get(0);
		}
		return show;
	}

	/**
	 * 
	 * @Description: 企业信息与企业动态
	 * @author yanghui 
	 * @Created 2014-5-12
	 * @return
	 */
	public DataGrid getEnterpriseAndDynamic(RequestPage page,Long userId) {
		List<EnterpriseAndDynamicBean> blist = new ArrayList<EnterpriseAndDynamicBean>();
		StringBuffer hql = new StringBuffer();
		hql.append(" select a.id,b.nenterpriseid,b.centerprisename,b.csummary ");
		hql.append(" from EnterpriseRoomDynamic a ,EEnterpriseinfo b ");
		hql.append(" where a.enterpriseId = b.nenterpriseid and a.valid= '1' ");
		if(!DataUtil.isNull(userId) && userId!=0L){
			//用户已登录
			hql.append(" and ( a.enterpriseId in ( select c.enterpriseId from EnterpriseRoomAttention c  where c.userId ='"+userId+"' ) or a.isSuerange = '"+SystemCommon_Constant.SEND_RANG_1+"' )");
		}else{
			//没有用户登录时，只能查看那些对全部用户可见的动态
			hql.append(" and a.isSuerange = '"+SystemCommon_Constant.SEND_RANG_1+"' ");
		}
		hql.append(" order by a.createTime desc ");
		
		List list  = enterpriseRoomDynamicDao.find(hql.toString(), new Object[0]);
		List idList = new ArrayList();
		for(Iterator it = list.iterator();it.hasNext();){
			Object[] obj = (Object[]) it.next();
			Long id = (Long) obj[0]; //企业动态ID
			Long enterpriseId = (Long) obj[1]; //企业ID
			String enterpriseName = (String) obj[2];//企业名称
			String summary = (String) obj[3]; //企业简介
			
			EnterpriseAndDynamicBean bean = new EnterpriseAndDynamicBean();
			if(!idList.contains(enterpriseId)){
				idList.add(enterpriseId);

				bean.setId(id);
				bean.setEnterpriseId(enterpriseId);
				bean.setEnterpriseName(enterpriseName);
				bean.setEnterpriseSummary(summary);
				
				//取得企业的LOGO
				
				List<Annex> alist = annexDao.find(" from Annex where objectId = ? and cannextype ='ENT_LOGO' and cvalid ='1' ", enterpriseId);
				if(DataUtil.listIsNotNull(alist)){
					String logoPath = alist.get(0).getCfilepath();
					bean.setLogoPath(logoPath);
				}
				blist.add(bean);
			}
 		}
		
		return new DataGrid(blist.size(),pageList(page,blist));
	}

	/**
	 * 
	 * @Description: 企业的动态列表以及评论与回复列表
	 * @author yanghui  
	 * @Created 2014-5-12
	 * @param page
	 * @param enterpriseId
	 * @return
	 */
	public DataGrid getDynamicList(RequestPage page, Long enterpriseId) {
		List<EnterpriseAndDynamicBean> beanList = new ArrayList<EnterpriseAndDynamicBean>();
		//1.根据企业ID取得企业发布动态
		List<EnterpriseRoomDynamic> dynamicList = getDynamicListByEnterpriseId(enterpriseId);
		if(DataUtil.listIsNotNull(dynamicList)){
			for(int i=0;i<dynamicList.size();i++){
				EnterpriseAndDynamicBean dyBean = new EnterpriseAndDynamicBean();
				EnterpriseRoomDynamic erd = dynamicList.get(i);
				dyBean.setEnterDynamic(erd.getEnterDynamic());//企业动态内容
				dyBean.setCreateTime(erd.getCreateTime());//动态发布时间
				dyBean.setId(erd.getId());//动态ID
				dyBean.setEnterpriseId(enterpriseId);//企业ID
				
				String publishUserName = getUserName(erd.getEnterUserId());
				dyBean.setPublishUserName(publishUserName); //发布人用户名
				
				//判断动态下是否存在图片
				if(SystemCommon_Constant.SIGN_YES_1.equals(erd.getIsPicture())){
					List<EnterpriseRoomPicture> picList = getEnterprisePictureListById(erd.getId());
					if(DataUtil.listIsNotNull(picList)){
						dyBean.setPicList(picList);
					}
				}
				
				//判断动态下是否存在附件
				if(SystemCommon_Constant.SIGN_YES_1.equals(erd.getHasannex())){
					List<Annex> annexList = getAnnexListById(erd.getId());
					if(DataUtil.listIsNotNull(annexList)){
						dyBean.setAnnexList(annexList);
					}
				}
				
				//2.取得每条动态下的评论信息列表
				List<EnterpriseRoomReviewBean> reviewList = getReviewListByDynamicId(erd.getId(),enterpriseId);
				if(DataUtil.listIsNotNull(reviewList)){
					dyBean.setReviewList(reviewList);
				}
				beanList.add(dyBean);
			}
		}
		return  new DataGrid(beanList.size(),pageList(page, beanList));
	}

	/**
	 * 
	 * @Description: 动态评论信息列表
	 * @author yanghui 
	 * @Created 2014-5-12
	 * @param id
	 * @return
	 */
	private List<EnterpriseRoomReviewBean> getReviewListByDynamicId(Long id,Long enterpriseId) {
		List<EnterpriseRoomReviewBean> blist = new ArrayList<EnterpriseRoomReviewBean>();
		StringBuffer hql = new StringBuffer();
		hql.append(" select a.nmemberid,b from MMember a, EnterpriseRoomReview b where b.dynamicId = ? "); 
		//剔除该企业已经屏蔽用户 的所有评论信息
		hql.append(" and b.userId not in ( select c.userId from EnterpriseRoomAttention c where c.enterpriseId=? and c.shieldStatus ='"+SystemCommon_Constant.REVIEW_SHIELD_STATUS_1+"') ");
		hql.append(" and b.shieldStatus = '"+SystemCommon_Constant.REVIEW_SHIELD_STATUS_0+"' ");
		hql.append(" and a.nmemberid = b.userId ");
		hql.append(" order by b.createTime desc ");
		
		List list = enterpriseRoomReviewDao.find(hql.toString(),new Object[]{id,enterpriseId});
		for(Iterator it = list.iterator();it.hasNext();){
			Object[] obj = (Object[]) it.next();
			EnterpriseRoomReviewBean bean = new EnterpriseRoomReviewBean();
			Long userId  = (Long) obj[0];
			EnterpriseRoomReview review  = (EnterpriseRoomReview) obj[1];
			
			bean.setCreateTime(review.getCreateTime()); //评论时间
			bean.setReview(review.getReview()); //评论内容
			bean.setUserId(review.getUserId()); //评论用户ID
			bean.setUserName(getUserName(userId));//评论人用户名
			bean.setId(review.getId()); //评论信息的ID
			bean.setDynamicId(review.getDynamicId());//动态的ID
			//是否存在图片
			if(SystemCommon_Constant.SIGN_YES_1.equals(review.getIsPicture())){
				List<EnterpriseRoomPicture> picList = getEnterprisePictureListById(review.getId());
				if(DataUtil.listIsNotNull(picList)){
					bean.setPicList(picList);
				}
			}
			
			//取得评论下的回复信息列表
			List<EnterpriseRoomReplyBean> replyList = getEnterpriseReplyList(review.getId());
			
			if(DataUtil.listIsNotNull(replyList)){
				bean.setReplyList(replyList);
			}
			
			blist.add(bean);
		}
		
		
		return blist;
	}

	/**
	 * 
	 * @Description: 取得回复信息列表
	 * @author yanghui 
	 * @Created 2014-5-12
	 * @param id
	 * @return
	 */
	private List<EnterpriseRoomReplyBean> getEnterpriseReplyList(Long id) {
		List<EnterpriseRoomReplyBean> blist = new ArrayList<EnterpriseRoomReplyBean>();
		StringBuffer hql = new StringBuffer();
		hql.append("  from  EnterpriseRoomReply  where reviewId = ? "); 
		hql.append(" and shieldStatus = '"+SystemCommon_Constant.REVIEW_SHIELD_STATUS_0+"' ");
		hql.append(" order by createTime desc ");
		
		List<EnterpriseRoomReply> replyList = enterpriseRoomReplyDao.find(hql.toString(), id);
		for(Iterator it = replyList.iterator();it.hasNext();){
			EnterpriseRoomReplyBean bean = new EnterpriseRoomReplyBean();
			EnterpriseRoomReply reply = (EnterpriseRoomReply) it.next();
			bean.setId(reply.getId());
			bean.setCreateTime(reply.getCreateTime()); //回复时间
			bean.setReply(reply.getReply());//回复内容
			bean.setUserId(reply.getUserId()); //评论人的ID
			bean.setReplyUserId(reply.getReplyUserId());//回复人的ID
			bean.setReviewId(reply.getReviewId());
			
			String userName = getUserName(reply.getUserId()); //评论人用户名
			String replyUserName = getUserName(reply.getReplyUserId()); //回复人用户名
			
			bean.setUserName(userName);
			bean.setReplyUserName(replyUserName);
			
			if(SystemCommon_Constant.SIGN_YES_1.equals(reply.getIsPicture())){
				List<EnterpriseRoomPicture> picList = getEnterprisePictureListById(reply.getId());
				if(DataUtil.listIsNotNull(picList)){
					bean.setPicList(picList);
				}
			}
			blist.add(bean);
		}
		return blist;
	}

	/**
	 * 
	 * @Description: 取得用户名
	 * @author yanghui 
	 * @Created 2014-5-12
	 * @param userId
	 * @return
	 */
	private String getUserName(Long userId) {
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
	
	
	private String getConciseName(Long enterId){
		String conciseName ="";
		List<MMember> list = mmemberDao.find(" from MMember where nenterpriseid = ?  and cmembertype ='1' and cvalid = '1' ", enterId);
		if(DataUtil.listIsNotNull(list)){
			MMember m = list.get(0);
			conciseName = m.getConciseName();
		}
		return conciseName;
	}

	/**
	 * 
	 * @Description: 取得附件列表
	 * @author yanghui 
	 * @Created 2014-5-12
	 * @param id
	 * @return
	 */
	private List<Annex> getAnnexListById(Long id) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from Annex where objectId = ? ");
		hql.append(" and cvalid = '"+SystemCommon_Constant.VALID_STATUS_1+"'");
		hql.append(" order by dcreatetime desc ");
		List<Annex> list = annexDao.find(hql.toString(), id);
		return list;
	}

	/**
	 * 
	 * @Description: 取得图片列表
	 * @author yanghui 
	 * @Created 2014-5-12
	 * @param id
	 * @return
	 */
	private List<EnterpriseRoomPicture> getEnterprisePictureListById(Long id) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from EnterpriseRoomPicture where parentId = ? ");
		hql.append(" and valid = '"+SystemCommon_Constant.VALID_STATUS_1+"'");
		hql.append(" order by createTime desc ");
		List<EnterpriseRoomPicture> list = enterpriseRoomPictureDao.find(hql.toString(), id);
		//取得图片路径后，需要判断该图片是否存在磁盘上面，如果不存在，则删除该条图片路径
		List<EnterpriseRoomPicture> subList = new ArrayList<EnterpriseRoomPicture>();
		subList = getPictureExistList(list);
		return subList;
	}

	/**
	 * 
	 * @Description: 通过企业ID取得企业发布的动态
	 * @author yanghui 
	 * @Created 2014-5-12
	 * @param enterpriseId
	 * @return
	 */
	private List<EnterpriseRoomDynamic> getDynamicListByEnterpriseId(
			Long enterpriseId) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from EnterpriseRoomDynamic where enterpriseId = ? ");
		hql.append(" and valid = '"+SystemCommon_Constant.VALID_STATUS_1+"'");
		hql.append(" order by createTime asc ");
		List<EnterpriseRoomDynamic> list = enterpriseRoomDynamicDao.find(hql.toString(), enterpriseId);
		return list;
	}
	
	
	
	/**
	 * 
	 * @Description: 分页信息
	 * @author yanghui 
	 * @Created 2014-5-12
	 * @param page
	 * @param beanList
	 * @return
	 */
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
	 * @Description:  企业下的产品信息列表
	 * @author yanghui 
	 * @Created 2014-5-13
	 * @param page
	 * @param enterpriseId
	 * @return
	 */
	public DataGrid getProductList(RequestPage page, Long enterpriseId) {
		List<EnterpriseProductBean> beanList = new ArrayList<EnterpriseProductBean>();
		StringBuffer hql =  new StringBuffer();
		if(!DataUtil.isNull(enterpriseId)){
			//产品列表信息
			hql.append(" from EnterpriseRoomProduct where valid = '"+SystemCommon_Constant.SIGN_YES_1+"' ");
			hql.append(" and enterpriseId = ? ");
			hql.append(" order by createTime desc ");
			
			List<EnterpriseRoomProduct> list = enterpriseRoomProductDao.find(hql.toString(), new Object[]{enterpriseId});
			if(DataUtil.listIsNotNull(list)){

				for(int i=0;i<list.size();i++){
					EnterpriseRoomProduct erd = list.get(i);
					EnterpriseProductBean bean = new EnterpriseProductBean();
					try {
						PropertyUtils.copyProperties(bean, erd);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					if(SystemCommon_Constant.SIGN_YES_1.equals(erd.getIsPicture())){
						List<EnterpriseRoomPicture> picList = getEnterprisePictureListById(erd.getId());
						if(DataUtil.listIsNotNull(picList)){
//							EnterpriseRoomPicture pic = picList.get(0);
//							bean.setProductPicPath(pic.getPicturePath()); //产品图片路径
							bean.setPicList(picList);
						}
					}
					
					
					beanList.add(bean);
				}
			}
		}
		return new DataGrid(beanList.size(),pageList(page, beanList));
	}

	/**
	 * 
	 * @Description:企业产品详情
	 * @author yanghui 
	 * @Created 2014-5-13
	 * @param productId
	 * @return
	 */
	public EnterpriseProductBean findProductDetail(Long id) {
		EnterpriseProductBean bean = new EnterpriseProductBean();
		String hql = " from EnterpriseRoomProduct where id = ? ";
		List<EnterpriseRoomProduct> list = enterpriseRoomProductDao.find(hql, id);
		if(DataUtil.listIsNotNull(list)){
			EnterpriseRoomProduct p = list.get(0);
			try {
				PropertyUtils.copyProperties(bean, p);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//是否存在图片
			if(SystemCommon_Constant.SIGN_YES_1.equals(p.getIsPicture())){
				List<EnterpriseRoomPicture> picList = getEnterprisePictureListById(p.getId());
				if(DataUtil.listIsNotNull(picList)){
					bean.setPicList(picList);
				}
			}
			
			//判断动态下是否存在附件
			if(SystemCommon_Constant.SIGN_YES_1.equals(p.getHasannex())){
				List<Annex> annexList = getAnnexListById(p.getId());
				if(DataUtil.listIsNotNull(annexList)){
					bean.setAnnexList(annexList);
				}
			}
		}
		return bean;
	}
	
	/**
	 * 
	 * @Description: 优惠打折详情
	 * @author yanghui 
	 * @Created 2014-5-13
	 * @param id
	 * @return
	 */
	public EnterpriseProductBean findDiscountDetail(Long id) {
		EnterpriseProductBean bean = new EnterpriseProductBean();
		String hql = " from EnterpriseRoomDiscount where id = ? ";
		List<EnterpriseRoomDiscount> list = enterpriseRoomDiscountDao.find(hql, id);
		if(DataUtil.listIsNotNull(list)){
			EnterpriseRoomDiscount p = list.get(0);
			try {
				PropertyUtils.copyProperties(bean, p);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//是否存在图片
			if(SystemCommon_Constant.SIGN_YES_1.equals(p.getIsPicture())){
				List<EnterpriseRoomPicture> picList = getEnterprisePictureListById(p.getId());
				if(DataUtil.listIsNotNull(picList)){
					bean.setPicList(picList);
				}
			}
			
			//判断动态下是否存在附件
			if(SystemCommon_Constant.SIGN_YES_1.equals(p.getHasannex())){
				List<Annex> annexList = getAnnexListById(p.getId());
				if(DataUtil.listIsNotNull(annexList)){
					bean.setAnnexList(annexList);
				}
			}
		}
		return bean;
	}

	public void saveReview(EnterpriseRoomReview review) {
		enterpriseRoomReviewDao.save(review);
		
	}

	public void saveReply(EnterpriseRoomReply reply) {
		enterpriseRoomReplyDao.save(reply);
	}

	/**
	 * 
	 * @Description: 企业优惠打折（）
	 * @author yanghui 
	 * @Created 2014-5-14
	 * @param page
	 * @param enterpriseId 
	 * @param enterpriseId（当enterpriseId 不为空时，查询某个具体企业下的打折信息，否则查询全部企业发布的打折信息）
	 * @param proCateId 产品类别id 
	 * @return
	 */
	public DataGrid getDiscountList(RequestPage page, Long enterpriseId, EnterpriseSearchBean bean) {
		List<EnterpriseProductBean> beanList = new ArrayList<EnterpriseProductBean>();
		StringBuffer hql =  new StringBuffer();
		
		
		List<EnterpriseRoomDiscount> list = null;
		//当企业的ID不为空时，说明是进入企业空间查看的打折信息
		if(!DataUtil.isNull(enterpriseId)){
			hql.append(" select a from EnterpriseRoomDiscount a  where a.valid = '"+SystemCommon_Constant.SIGN_YES_1+"' ");
			hql.append(" and a.enterpriseId = ? ");
			hql.append(" order by a.enterpriseId asc,a.createTime desc ");
			list = enterpriseRoomDiscountDao.find(hql.toString(),enterpriseId);
		}else{
			//客户端优惠打折模块的信息
			
			//按距离查找
			Integer mNum = bean.getmNum();//距离
			String lng = bean.getLongitude();//经度
			String lat = bean.getLatitude();//纬度
			Double p  = 3.14159265358979323;
			
			StringBuffer sb = new StringBuffer();
			sb.append(" Sqrt(power((longitude - ").append(lng).append(") *").append(p).append("* 6371229 * cos(((latitude + ").append(lat).append(")/2) * ").append(p).append("/180)/180,2)+");
			sb.append(" power((latitude - ").append(lat).append(") * ").append(p).append(" * 6371229 /180,2)) ");
			
			
			
			hql.append(" select a from EnterpriseRoomDiscount a where a.enterpriseId in ( ");
			hql.append(" select e.nenterpriseid ").append("  from EEnterpriseinfo e ");
			hql.append(" where ");
			hql.append(" e.nenterpriseid in ( ");
			String sql = " select distinct p.nenterpriseid from EEnterpriseproduct p ";
			hql.append(sql);
			
			
			if(0!=mNum){
				//按距离查找
				hql.delete(0, hql.length());
				hql.append(" select a from EnterpriseRoomDiscount a where a.enterpriseId in ( ");
				hql.append(" select e.nenterpriseid ").append(" from EEnterpriseinfo e , EnterpriseCoordinate c ");
				hql.append(" where e.nenterpriseid = c.enterpriseId and ").append(sb).append(" <= ").append(mNum).append(" and  e.nenterpriseid in ( ").append(sql);
			}
			
			
			if(!DataUtil.isNull(bean.getProductCategoryId()) && bean.getProductCategoryId()!=0L){ 
				//按产品类别查询优惠打折信息
				hql.append(" where  p.cprocatcode = '"+bean.getProductCategoryId()+"' ");
			}
			
			hql.append(" ) )");
			hql.append(" and a.valid = '"+SystemCommon_Constant.SIGN_YES_1+"' ");
			hql.append(" order by a.createTime desc ");
			
			
			
			list = enterpriseRoomDiscountDao.find(hql.toString(),new Object[0]);
		}

		if(DataUtil.listIsNotNull(list)){
			for(int i=0;i<list.size();i++){
				EnterpriseRoomDiscount erd = list.get(i);
				EnterpriseProductBean beanp = new EnterpriseProductBean();
				try {
					PropertyUtils.copyProperties(beanp, erd);
				} catch (Exception e) {
					e.printStackTrace();
				} 
				
				if(SystemCommon_Constant.SIGN_YES_1.equals(erd.getIsPicture())){
					List<EnterpriseRoomPicture> picList = getEnterprisePictureListById(erd.getId());
					if(DataUtil.listIsNotNull(picList)){
//						EnterpriseRoomPicture pic = picList.get(0);
//						bean.setProductPicPath(pic.getPicturePath()); //产品图片路径
						beanp.setPicList(picList);
					}
				}
				beanList.add(beanp);
			}
		}
		return new DataGrid(beanList.size(),pageList(page, beanList));
	}

	/**
	 * 
	 * @Description:  寻找商家
	 * @author yanghui 
	 * @Created 2014-5-14
	 * @param page
	 * @param cateId
	 * @param sort
	 * @return
	 */
	public DataGrid getEnterpriseInfo(RequestPage page, EnterpriseSearchBean entBean) {
		List<EnterpriseAndDynamicBean> blist = new ArrayList<EnterpriseAndDynamicBean>();
		StringBuffer hql = new StringBuffer();
		//按距离查找
		Integer mNum = entBean.getmNum();//距离
		String lng = entBean.getLongitude();//经度
		String lat = entBean.getLatitude();//纬度
		Double p  = 3.14159265358979323;
		
		StringBuffer sb = new StringBuffer();
		sb.append(" Sqrt(power((longitude - ").append(lng).append(") *").append(p).append("* 6371229 * cos(((latitude + ").append(lat).append(")/2) * ").append(p).append("/180)/180,2)+");
		sb.append(" power((latitude - ").append(lat).append(") * ").append(p).append(" * 6371229 /180,2)) ");
		
		
		hql.append("select e,").append(sb).append(" as distance").append("  from EEnterpriseinfo e , EnterpriseCoordinate c ");
		hql.append(" where e.nenterpriseid = c.enterpriseId and ");
		hql.append(" e.nenterpriseid in ( ");
		String sql = " select distinct p.nenterpriseid from EEnterpriseproduct p ";
		hql.append(sql);
		if(0!=mNum){
			//按距离查找
			hql.delete(0, hql.length());
			hql.append("select e, ").append(sb).append(" as distance ").append(" from EEnterpriseinfo e , EnterpriseCoordinate c ");
			hql.append(" where e.nenterpriseid = c.enterpriseId and ").append(sb).append(" <= ").append(mNum).append(" and  e.nenterpriseid in ( ").append(sql);
		}
		
		if(entBean.getProductCategoryId() != 0L){
			hql.append(" where p.cprocatcode = '"+entBean.getProductCategoryId()+"'");
		}
		hql.append(" ) ");
		
		
		List elist = enterpriseInfoDao.find(hql.toString(), new Object[0]);
		if(DataUtil.listIsNotNull(elist)){
			for(Iterator it = elist.iterator();it.hasNext();){
				EEnterpriseinfo einfo = new EEnterpriseinfo();
				Object[] obj = (Object[]) it.next();
				einfo = (EEnterpriseinfo) obj[0];
				Double distance = (Double) obj[1];
				
 				EnterpriseAndDynamicBean bean = new EnterpriseAndDynamicBean();
//				EEnterpriseinfo einfo = elist.get(i); 
				bean.setEnterpriseId(einfo.getNenterpriseid()); //企业ID
				bean.setEnterpriseName(einfo.getCenterprisename());//企业名称
				bean.setEnterpriseSummary(einfo.getCsummary());//企业简介
				bean.setDistance(distance);
				//取得企业的LOGO
				List<Annex> alist = annexDao.find(" from Annex where objectId = ? and cannextype ='ENT_LOGO' and cvalid ='1' ", einfo.getNenterpriseid());
				if(DataUtil.listIsNotNull(alist)){
					String logoPath = alist.get(0).getCfilepath();
					bean.setLogoPath(logoPath);
				}
				blist.add(bean);
			}
		}
		return new DataGrid(blist.size(),pageList(page, blist));
	}

	public void saveAttention(EnterpriseRoomAttention atten) {
		enterpriseRoomAttentionDao.save(atten);
	}

	/**
	 * 
	 * @Description:
	 * @author yanghui 
	 * @Created 2014-5-20
	 * @param enterpriseId
	 * @param userId
	 * @return
	 */
	public EnterpriseRoomAttention getAttenByEnterIdAndUserId(
			Long enterpriseId, Long userId) {
		EnterpriseRoomAttention atten = new EnterpriseRoomAttention();
		String hql = " from EnterpriseRoomAttention where enterpriseId =? and userId=? ";
		List<EnterpriseRoomAttention> alist = enterpriseRoomAttentionDao.find(hql, new Object[]{enterpriseId,userId});
		if(DataUtil.listIsNotNull(alist)){
			atten = alist.get(0);
		}
		return atten;
	}

	public void deleteAtten(EnterpriseRoomAttention atten) {
		enterpriseRoomAttentionDao.delete(atten);
	}

	/**
	 * 
	 * @Description: 校验用户是否已关注某个企业
	 * @author yanghui 
	 * @Created 2014-5-20
	 * @param enterId
	 * @param userId
	 * @return
	 */
	public boolean checkAttenStatus(Long enterId, Long userId) {
		boolean flag = false;
		EnterpriseRoomAttention atten = getAttenByEnterIdAndUserId(enterId, userId);
		if(!DataUtil.isNull(atten.getId())){
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * @Description: 保存用户咨询与企业回复信息
	 * @author yanghui 
	 * @Created 2014-5-20
	 * @param consult
	 */
	public void saveConsult(EnterpriseRoomConsult consult) {
		enterpriseRoomConsultDao.save(consult);
	}

	/**
	 * 
	 * @Description: 咨询与回复列表
	 * @author yanghui 
	 * @param page 
	 * @Created 2014-5-26
	 * @param enterId
	 * @return
	 */
	public DataGrid getConsultList(RequestPage page, Long enterId) {
		List<EnterpriseRoomConsultBean> beanList = new ArrayList<EnterpriseRoomConsultBean>();
		StringBuffer hql = new StringBuffer();
		hql.append("select a,b.nmemberid  from EnterpriseRoomConsult a ,MMember b,EEnterpriseinfo c ");
		hql.append(" where a.consultType = '1' and a.shieldStatus = '"+SystemCommon_Constant.REVIEW_SHIELD_STATUS_0+"' ");
		//剔除企业屏蔽用户的咨询信息
		hql.append(" and a.userId not in ( select d.userId from EnterpriseRoomAttention d where d.enterpriseId=? and d.shieldStatus ='"+SystemCommon_Constant.REVIEW_SHIELD_STATUS_1+"') ");
		hql.append(" and a.enterpriseId = c.nenterpriseid and a.userId = b.nmemberid ");
		hql.append(" and c.nenterpriseid=? ");
		hql.append(" order by a.consultTime desc ");
		List  list = enterpriseRoomConsultDao.find(hql.toString(), new Object[]{enterId,enterId}); //咨询列表
		for(Iterator it = list.iterator();it.hasNext();){
			Object[] obj = (Object[]) it.next();
			
			EnterpriseRoomConsult con = (EnterpriseRoomConsult) obj[0];
	//		String userName = (String) obj[1]; //用户名
			Long uid = (Long) obj[1]; //用户ID
		//  String enterName = (String) obj[2]; //企业名称
			
			EnterpriseRoomConsultBean bean = new EnterpriseRoomConsultBean();
			bean.setId(con.getId());
			bean.setEnterpriseId(con.getEnterpriseId());
			bean.setUserId(con.getUserId());
			bean.setConsultContent(con.getConsultContent());
			bean.setConsultTime(con.getConsultTime());
			bean.setConsultType(con.getConsultType());
			
			bean.setUserName(getUserName(uid));
			bean.setEnterName(getConciseName(enterId));
			
			//取得咨询信息下的回复信息列表
			List<EnterpriseRoomConsult> replyList = getConsultReplyList(con.getId());
			if(DataUtil.listIsNotNull(replyList)){
				bean.setReplyList(replyList);
			}
			beanList.add(bean);
		}
		return new DataGrid(beanList.size(),pageList(page, beanList));
	}

	/**
	 * 
	 * @Description:取得咨询信息下的回复信息列表
	 * @author yanghui 
	 * @Created 2014-5-26
	 * @param id
	 * @return
	 */
	private List<EnterpriseRoomConsult> getConsultReplyList(Long id) {
		List<EnterpriseRoomConsult> list = new ArrayList<EnterpriseRoomConsult>();
		StringBuffer hql = new StringBuffer();
		hql.append(" from EnterpriseRoomConsult where consultType = '2' and shieldStatus = '"+SystemCommon_Constant.REVIEW_SHIELD_STATUS_0+"' ");
		hql.append(" and parentId=? ");
		hql.append(" order by consultTime asc ");
		list = enterpriseRoomConsultDao.find(hql.toString(), id); //咨询的回复列表
		return list;
	}

	/**
	 * 
	 * @Description: 企业的客户列表
	 * @author yanghui 
	 * @Created 2014-5-28
	 * @param page
	 * @param enterId
	 * @return
	 */
	public DataGrid getCustomerList(RequestPage page, Long enterId) {
		List<EnterpriseCostomer> blist = new ArrayList<EnterpriseCostomer>();
		StringBuffer hql = new StringBuffer();
		hql.append(" select a,b from EnterpriseRoomAttention a , MMember b where a.enterpriseId = ? and a.userId = b.nmemberid  ");
		hql.append(" order by a.attentionTime desc ");
		List list = enterpriseRoomAttentionDao.find(hql.toString(), enterId);
		for(Iterator it = list.iterator();it.hasNext();){
			Object[] obj = (Object[]) it.next();
			EnterpriseRoomAttention att = (EnterpriseRoomAttention)obj[0];
			MMember m = (MMember)obj[1];
			
			
			EnterpriseCostomer ec = new EnterpriseCostomer();
			ec.setEnterpriseId(att.getEnterpriseId());
			ec.setAttentionTime(att.getAttentionTime());
			ec.setUserId(m.getNmemberid());
			ec.setUserName(m.getCusername());
			ec.setUserHeadImgPath(m.getCavatarpath());
			ec.setShieldStatus(att.getShieldStatus());
			//该用户对企业的评论次数
			int commentNum = getCommentNumByUserId(enterId,m.getNmemberid());
			//该用户对企业的咨询次数
			int consultNum = getConsultNumByUserId(enterId,m.getNmemberid());	
			
			ec.setCommentNum(commentNum);
			ec.setConsultNum(consultNum);
			
			
			EnterpriseGroupLink link = getLinkByCustomerId(m.getNmemberid());
			ec.setGroupId(link.getGroupId());
			blist.add(ec);
		}
		return new DataGrid(blist.size(),pageList(page, blist));
	}

	
	private EnterpriseGroupLink getLinkByCustomerId(Long nmemberid) {
		EnterpriseGroupLink link = new EnterpriseGroupLink();
		String hql =" from EnterpriseGroupLink where customerId=? ";
		List<EnterpriseGroupLink> list = enterpriseGroupLinkDao.find(hql, nmemberid);
		if(DataUtil.listIsNotNull(list)){
			link = list.get(0);
		}
		return link;
	}

	private int getConsultNumByUserId(Long enterId,Long nmemberid) {
		int num = 0;
		String hql = " from EnterpriseRoomConsult where userId = ? and enterpriseId=? and consultType ='1' and shieldStatus='"+SystemCommon_Constant.REVIEW_SHIELD_STATUS_0+"'";
		
		List list = enterpriseRoomConsultDao.find(hql, new Object[]{nmemberid,enterId});
		if(DataUtil.listIsNotNull(list)){
			num = list.size();
		}
		return num;
	}

	private int getCommentNumByUserId(Long enterId,Long nmemberid) {
		int num = 0;
		String hql = " from EnterpriseRoomDynamic a,EnterpriseRoomReview b where a.id = b.dynamicId  and a.enterpriseId =? and b.userId =?  and b.shieldStatus = '"+SystemCommon_Constant.REVIEW_SHIELD_STATUS_0+"'";
		List list = enterpriseRoomReviewDao.find(hql, new Object[]{enterId,nmemberid});
		if(DataUtil.listIsNotNull(list)){
			num = list.size();
		}
		return num;
	}

	/**
	 * 
	 * @Description: 屏蔽关注该企业的用户
	 * @author yanghui 
	 * @Created 2014-5-29
	 * @param enterId
	 * @param userId
	 * @param type 
	 */
	public void updateEnterpriseCustomer(Long enterId, Long userId, String type) {
		String hql = " from EnterpriseRoomAttention where enterpriseId=? and userId=?";
		List list = enterpriseRoomAttentionDao.find(hql, new Object[]{enterId,userId});
		if(DataUtil.listIsNotNull(list)){
			EnterpriseRoomAttention a = (EnterpriseRoomAttention) list.get(0);
			//type用来判断操作 1代表屏蔽操作  0代表恢复操作 
			if("1".equals(type)){
				a.setShieldStatus(SystemCommon_Constant.REVIEW_SHIELD_STATUS_1);
			}else if("0".equals(type)){
				a.setShieldStatus(SystemCommon_Constant.REVIEW_SHIELD_STATUS_0);
			}
			enterpriseRoomAttentionDao.update(a);
		}
	}

	/**
	 * 
	 * @Description: 企业的分组列表(每个组别下的用户)
	 * @author yanghui 
	 * @Created 2014-5-29
	 * @param page
	 * @param enterId
	 * @param type 当type=1时，查询分组及组别下的用户信息，否则只查询分组
	 * @return
	 */
	public DataGrid getGroupListByEnterId(RequestPage page, Long enterId,String type) {
		List<EnterpriseCustomerGroupBean> beanList = new ArrayList<EnterpriseCustomerGroupBean>();
		String hql =" from EnterpriseCustomerGroup where enterpriseId=?";
		List list = enterpriseCustomerGroupDao.find(hql, enterId);
		for(int i=0;i<list.size();i++){
			EnterpriseCustomerGroup g = (EnterpriseCustomerGroup) list.get(i);
			EnterpriseCustomerGroupBean bean = new EnterpriseCustomerGroupBean();
			//分组信息
			bean.setGroupName(g.getGroupName());
			bean.setId(g.getId());
			bean.setEnterpriseId(g.getEnterpriseId());
			bean.setRemark(g.getRemark());
			
			if("1".equals(type)){
				//分组下的客户信息
				List mList = getMemberListByGroupId(g.getId(),enterId);
				if(DataUtil.listIsNotNull(mList)){
					List<EnterpriseCostomer> blist = new ArrayList<EnterpriseCostomer>();
					for(Iterator it = mList.iterator();it.hasNext();){
						Object[] obj = (Object[]) it.next();
						MMember m = (MMember)obj[0];
						EnterpriseRoomAttention att = (EnterpriseRoomAttention)obj[1];
						EnterpriseCostomer ec = new EnterpriseCostomer();
						ec.setEnterpriseId(att.getEnterpriseId());
						ec.setAttentionTime(att.getAttentionTime());
						ec.setUserId(m.getNmemberid());
						ec.setUserName(m.getCusername());
						ec.setUserHeadImgPath(m.getCavatarpath());
						ec.setShieldStatus(att.getShieldStatus());
						//该用户对企业的评论次数
						int commentNum = getCommentNumByUserId(enterId,m.getNmemberid());
						//该用户对企业的咨询次数
						int consultNum = getConsultNumByUserId(enterId,m.getNmemberid());	
						ec.setCommentNum(commentNum);
						ec.setConsultNum(consultNum);
						EnterpriseGroupLink link = getLinkByCustomerId(m.getNmemberid());
						ec.setGroupId(link.getGroupId());
						blist.add(ec);
					}
					bean.setList(blist);
				}
			}
			
			beanList.add(bean);
		}
		return new DataGrid(beanList.size(), pageList(page, beanList));
	}

	
	private List getMemberListByGroupId(Long id,Long enterId ) {
		String hql = "select a,c from MMember a,EnterpriseGroupLink b,EnterpriseRoomAttention c   where a.nmemberid = b.customerId and a.nmemberid  = c.userId and  b.groupId=? and c.enterpriseId=? order by c.attentionTime desc  ";
		List mlist = mmemberDao.find(hql, new Object[]{id,enterId});
		return mlist;
	}

	/**
	 * 
	 * @Description: 新增/修改分组
	 * @author yanghui 
	 * @Created 2014-5-29
	 * @param g
	 */
	public void saveOrUpdateCustomerGroup(EnterpriseCustomerGroup g,String str) {
		if("add".equals(str)){
			enterpriseCustomerGroupDao.save(g);
		}else if("update".equals(str)){
			String hql = " update EnterpriseCustomerGroup set groupName=? , remark=? where id=?";
			enterpriseCustomerGroupDao.execute(hql, new Object[]{g.getGroupName(),g.getRemark(),g.getId()});
		}
	}

	/**
	 * 删除分组
	 * @Description:
	 * @author yanghui 
	 * @Created 2014-5-29
	 * @param id
	 */
	public void deleteCustomerGroup(Long id) {
		//删除分组信息
		enterpriseCustomerGroupDao.delete(EnterpriseCustomerGroup.class,id);
		//删除该组下的关联用户
		String hql =" delete  from EnterpriseGroupLink where groupId =?";
		enterpriseCustomerGroupDao.execute(hql, id);
	}
	/**
	 * 
	 * @Description: 移动用户分组
	 * @author YangHui 
	 * @Created 2014-6-3
	 * @param userId
	 * @param newGroupId 新组
	 * @param oldGroupId 旧组
	 */

	public void updateCustomerGroup(Long userId, Long newGroupId, Long oldGroupId) {
		if(DataUtil.isNull(oldGroupId) || 0== oldGroupId){
			EnterpriseGroupLink l = new EnterpriseGroupLink();
			l.setCustomerId(userId);
			l.setGroupId(newGroupId);
			enterpriseGroupLinkDao.save(l);
		}else{
			String hql =" from EnterpriseGroupLink where customerId=? and groupId=? ";
			List<EnterpriseGroupLink> list = enterpriseGroupLinkDao.find(hql, new Object[]{userId,oldGroupId});
			if(DataUtil.listIsNotNull(list)){
				EnterpriseGroupLink l = list.get(0);
				l.setGroupId(newGroupId);
				enterpriseGroupLinkDao.update(l);
			}
		}
	}

	/**
	 * 
	 * @Description: 修改企业秀描述信息
	 * @author YangHui 
	 * @Created 2014-6-5
	 * @param bean
	 * @return
	 */
	public void updateEnterpriseRoomShow(EnterpriseShowBean bean) {
		String hql = " update EnterpriseRoomShow set enterpriseInfo=? where enterpriseId=? ";
		enterpriseRoomShowDao.execute(hql, new Object[]{bean.getEnterpriseInfo(),bean.getEnterpriseId()});
	}
	public void saveOrUpdateEnterPriseRoomShow(EnterpriseRoomShow show) {
		enterpriseRoomShowDao.saveOrUpdate(show);
	}
	
	
	public void saveEnterpriseRoomDynamic(EnterpriseRoomDynamic bean) {
		enterpriseRoomDynamicDao.save(bean);
	}

	/**
	 * 
	 * @Description: 新增或修改企业空间的图片信息
	 * @author YangHui 
	 * @Created 2014-6-5
	 * @param picList
	 * @param ids 待修改或删除的图片信息，由于在修改时，把新图片重新保存，原来的就逻辑删除
	 */
	public void saveOrUpdateEnterpriseRoomPictureList(
			List<EnterpriseRoomPicture> picList,String ids) {
		for(int i=0;i<picList.size();i++){
			EnterpriseRoomPicture pic = picList.get(i);
			enterpriseRoomPictureDao.save(pic);
		}
		if(!"".equals(ids)){
			String[] s = ids.split(",");
			for(int i=0;i<s.length;i++){
				EnterpriseRoomPicture p = enterpriseRoomPictureDao.get(EnterpriseRoomPicture.class, Long.valueOf(s[i]));
				if(p!=null){
					p.setValid(SystemCommon_Constant.VALID_STATUS_0);
					enterpriseRoomPictureDao.update(p);
				}
			}
		}
	}

	
	
	public List<EEnterpriseproduct> getProductListNotInRoom(Long enterpriseId) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select a from  EEnterpriseproduct a where a.nenterpriseid=? ");
		hql.append(" and a.nentproid not in ( select b.productId  from EnterpriseRoomProduct b where b.enterpriseId='"+enterpriseId+"' ) ");
		List<EEnterpriseproduct> list = enterpriseProductDao.find(hql.toString(), new Object[]{enterpriseId});
		return list;
	}

	public void saveEnterpriseRoomProduct(EnterpriseRoomProduct pro) {
		enterpriseRoomProductDao.save(pro);
	}

	public List<EEnterpriseproduct> getProductListNotInRoomDiscount(
			Long enterpriseId) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select a from  EEnterpriseproduct a where a.nenterpriseid=? ");
		hql.append(" and a.nentproid not in ( select b.productId  from EnterpriseRoomDiscount b where b.enterpriseId='"+enterpriseId+"' ) ");
		List<EEnterpriseproduct> list = enterpriseProductDao.find(hql.toString(), new Object[]{enterpriseId});
		return list;
	}

	public void saveEnterpriseRoomDiscount(EnterpriseRoomDiscount pro) {
		enterpriseRoomDiscountDao.save(pro);
	}

	/**
	 * 
	 * @Description: 根据商家名称模糊查询
	 * @author YangHui 
	 * @Created 2014-6-16
	 * @param page
	 * @param bean
	 * @return
	 */
	public DataGrid getEnterpriseInfoByName(RequestPage page, EnterpriseSearchBean bean) {
		List<EnterpriseAndDynamicBean> blist = new ArrayList<EnterpriseAndDynamicBean>();
		
		String lng = bean.getLongitude();//经度
		String lat = bean.getLatitude();//纬度
		Double p  = 3.14159265358979323;
		
		StringBuffer hql = new StringBuffer();
		
		
		StringBuffer sb = new StringBuffer();
		sb.append(" Sqrt(power((longitude - ").append(lng).append(") *").append(p).append("* 6371229 * cos(((latitude + ").append(lat).append(")/2) * ").append(p).append("/180)/180,2)+");
		sb.append(" power((latitude - ").append(lat).append(") * ").append(p).append(" * 6371229 /180,2)) ");
		
		
		hql.append("select e,").append(sb).append(" as distance").append("  from EEnterpriseinfo e , EnterpriseCoordinate c ");
		hql.append(" where e.nenterpriseid = c.enterpriseId  ");
		hql.append(" and  e.cvalid='1' ");
		if(!DataUtil.isEmpty(bean.getEnterpriseName())){
			hql.append(" and e.centerprisename like '%"+bean.getEnterpriseName()+"%' ");
		}
		hql.append(" order by e.dcreatetime desc ");
		
		List elist = enterpriseInfoDao.find(hql.toString(), new Object[0]);
		if(DataUtil.listIsNotNull(elist)){
			for(Iterator it = elist.iterator();it.hasNext();){
				EEnterpriseinfo einfo = new EEnterpriseinfo();
				Object[] obj = (Object[]) it.next();
				einfo = (EEnterpriseinfo) obj[0];
				Double distance = (Double) obj[1];
				
 				EnterpriseAndDynamicBean ebean = new EnterpriseAndDynamicBean();
 				ebean.setEnterpriseId(einfo.getNenterpriseid()); //企业ID
 				ebean.setEnterpriseName(einfo.getCenterprisename());//企业名称
 				ebean.setEnterpriseSummary(einfo.getCsummary());//企业简介
 				ebean.setDistance(distance);
				//取得企业的LOGO
				List<Annex> alist = annexDao.find(" from Annex where objectId = ? and cannextype ='ENT_LOGO' and cvalid ='1' ", einfo.getNenterpriseid());
				if(DataUtil.listIsNotNull(alist)){
					String logoPath = alist.get(0).getCfilepath();
					ebean.setLogoPath(logoPath);
				}
				blist.add(ebean);
			}
		}
		return new DataGrid(blist.size(),pageList(page, blist));
	}

	public void updateEnterpriseRoomProduct(EnterpriseRoomProduct pro) {
		enterpriseRoomProductDao.update(pro);
	}

	public void updateEnterpriseRoomDiscount(EnterpriseRoomDiscount pro) {
		enterpriseRoomDiscountDao.update(pro);
		
	}

	/**
	 * 
	 * @Description: 判断企业是否存在未回复的咨询信息
	 * @author YangHui 
	 * @Created 2014-6-23
	 * @param enterId
	 * @return
	 */
	public  boolean checkEnterpriseExistNewConsult(Long enterId) {
		boolean flag = false;
		String hql =" from EnterpriseRoomConsult where enterpriseId =? and consultType ='1' and id not in (select distinct parentId from EnterpriseRoomConsult where enterpriseId =? and consultType ='2' )";
		List list = enterpriseRoomConsultDao.find(hql, new Object[]{enterId,enterId});
		if(DataUtil.listIsNotNull(list)){
			flag = true;
		}
		return flag;
	}

	
	/**
	 * 
	 * @Description:企业下的多用户列表
	 * @author YangHui 
	 * @Created 2014-8-14
	 * @param enterpriseId
	 * @return
	 */
	public List<MMember> getUserListByEnterpriseId(Long enterpriseId) {
		String hql = " from MMember WHERE nenterpriseid=? and isManager=?";
		List<MMember> list = mmemberDao.find(hql, new Object[]{enterpriseId,SystemCommon_Constant.SIGN_YES_0});
		return list;
	}

	/**
	 * 
	 * @Description: 企业用户管理员角色转让
	 * @author YangHui 
	 * @Created 2014-8-14
	 * @param userId 转让用户
	 * @param transUserId 被转让用户
	 */
	public void updateMemberIsManager(Long userId, Long transUserId) {
		MMember m2 = mmemberDao.get(MMember.class, userId);
		m2.setIsManager(SystemCommon_Constant.SIGN_YES_0);
		mmemberDao.update(m2);

		m2 = mmemberDao.get(MMember.class, transUserId);
		m2.setIsManager(SystemCommon_Constant.SIGN_YES_1);
		mmemberDao.update(m2);
	}

	/**
	 * 
	 * @Description: 管理员对其他企业用户的禁用与启用
	 * @author YangHui 
	 * @Created 2014-8-14
	 * @param userId
	 * @param type
	 */
	public void updateMemberIsValid(Long userId, String type) {
		//禁用
		if("0".equals(type)){
			MMember m2 = mmemberDao.get(MMember.class, userId);
			m2.setCvalid(SystemCommon_Constant.VALID_STATUS_0);
			mmemberDao.update(m2);
		}
		
		//启用
		if("1".equals(type)){
			MMember m2 = mmemberDao.get(MMember.class, userId);
			m2.setCvalid(SystemCommon_Constant.VALID_STATUS_1);
			mmemberDao.update(m2);
		}
	}

	/**
	 * 
	 * @Description: 企业下管理员用户审核非管理员用户
	 * @author YangHui 
	 * @Created 2014-8-18
	 * @param userId
	 */
	public void updateMemberStatus(Long userId) {
		MMember m2 = mmemberDao.get(MMember.class, userId);
		m2.setCmemberstatus(SystemCommon_Constant.AUDIT_STATUS_1);
		mmemberDao.update(m2);
	}
	
}
