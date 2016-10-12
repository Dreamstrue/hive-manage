package com.hive.complain.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.complain.entity.ComplainInfo;
import com.hive.complain.entity.ComplainPerson;
import com.hive.complain.model.ComplainBean;
import com.hive.complain.model.TradeBean;
import com.hive.util.DataUtil;
import com.hive.util.PageModel;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class ComplainInfoService extends BaseService<ComplainInfo> {

	private static Logger logger = Logger.getLogger(ComplainInfoService.class);
	
	@Resource
	private ComplainPersonService complainPersonService;
	@Resource
	private ComplainStepService complainStepService;
	@Resource
	private BaseDao<ComplainInfo> infoDao;


	@Override
	protected BaseDao<ComplainInfo> getDao() {
		return infoDao;
	}

	public ComplainInfo getComplainInfo() {
		ComplainInfo info = new ComplainInfo();
		List list = getDao().find("from " + getEntityName());
		if (list.size() > 0) {
			info = (ComplainInfo) list.get(0);
		}
		return info;
	}

	public List<ComplainInfo> getListInfo() {
		List<ComplainInfo> list = getDao().find("from " + getEntityName());
		return list;
	}
	
	public List<ComplainInfo> getComplainInfoListById(Long id) {
		List<ComplainInfo> list = getDao().find("from " + getEntityName()+" where id=?",id);
		return list;
	}
	
	
	public ComplainInfo getComplainInfoById(Long id) {
		ComplainInfo info =  new ComplainInfo();
		List list = getDao().find("from " + getEntityName()+" where id=?",id);
		if(list.size()>0){
			info = (ComplainInfo) list.get(0);			
		}
		return info;
	}
	
	/**
	 * 
	 * @Description:
	 * @author yanghui 
	 * @Created 2013-8-23
	 * @param page  分页
	 * @param keys  模糊查询关键字
	 * @param flag 	处理类型  1-投诉  2-举报
	 * @param level 处理级别  1-一级处理 	2-二级处理
	 * @return
	 */
	public DataGrid dataGrid(RequestPage page,String keys,String flag, String level) {
		String sql = "select count(*) from "+getEntityName()+" where 1=1 and flag = '"+flag+"'";
		String hql = "from " + getEntityName()+" where 1=1 and flag='"+flag+"'";
		if(!keys.equals("")){
			hql = hql+" and title like '%"+keys+"%'";
			sql = sql+" and title like '%"+keys+"%'";
		}
		if(level.equals("1")){
			//一级处理 
			sql += " and dealState ='"+SystemCommon_Constant.COMPLAIN_DEAL_STATE_00+"'";
			hql += " and dealState ='"+SystemCommon_Constant.COMPLAIN_DEAL_STATE_00+"'";
		}
		if(level.equals("2")){
			//二级处理 
			sql += " and dealState ='"+SystemCommon_Constant.COMPLAIN_DEAL_STATE_04 + "'";
			hql += " and dealState ='"+SystemCommon_Constant.COMPLAIN_DEAL_STATE_04 + "'";
		}
		hql=hql+" order by dealState,tradeId,comDate desc";
		long count = getDao().count(sql);
		List<ComplainInfo> list = getDao().find(page.getPage(), page.getRows(),
				hql);
		List<ComplainBean> beanList = new ArrayList<ComplainBean>();
		for (int i = 0; i < list.size(); i++) {
			ComplainInfo info = list.get(i);
			//处理标题长度，如果大于15个字符，就用点号代替
			String title = info.getTitle();
			if(title.length()>15){
				title = title.substring(0, 15)+".....";
			}
			info.setTitle(title);
			Long complainId = info.getId();
			Date comDate = info.getComDate();
//			String str = DateUtil.dateToString(comDate);
//			Date date = DateUtil.stringToDate(str);
			ComplainPerson person = complainPersonService
					.getPersonByComplainId(complainId);
			ComplainBean bean = new ComplainBean();
			try {
				PropertyUtils.copyProperties(bean, info);
			//	bean.setComDate(date);
				PropertyUtils.copyProperties(bean, person);
				beanList.add(bean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new DataGrid(count, beanList);
	}

	/***
	 *  根据行业ID获取投诉信息
	 * 
	 * @Description:
	 * @author yanghui
	 * @Created 2013-7-30
	 * @param id
	 * @return
	 */
	public List<ComplainBean> getComplainList(String tradeId) {
		StringBuffer sb = new StringBuffer("from " + getEntityName());
		sb.append("  where tradeId= '" + tradeId+"'");
		sb.append(" order by tradeId");
		List<ComplainInfo> list = getDao().find(sb.toString());
		List<ComplainBean> beanList = new ArrayList<ComplainBean>();
		beanList = getBeanList(list,beanList);
		return beanList;
	}
	
	
	public List<ComplainBean> getComplainListAll() {
		StringBuffer sb = new StringBuffer("from " + getEntityName());
		List<ComplainInfo> list = getDao().find(sb.toString());
		List<ComplainBean> beanList = new ArrayList<ComplainBean>();
		beanList = getBeanList(list,beanList);
		return beanList;
	}

	/**
	 * 
	 * @Description: 投诉举报查询 根据查询码或身份证号
	 * @author yanghui 
	 * @Created 2013-7-31
	 * @param cardNo
	 * @param searchCode
	 * @return
	 */
	public List<ComplainBean> queryComlainBean(String cardNo, String searchCode,String flag) {
		List<ComplainBean> beanList = new ArrayList<ComplainBean>();
		StringBuffer sb = new StringBuffer();
		if(!DataUtil.isEmpty(searchCode)){
			sb.append(" from " + getEntityName()+" where 1=1 ");
			sb.append(" and searchCode ='"+searchCode+"'");
			sb.append(" and flag='"+flag+"'");
			List<ComplainInfo> list = getDao().find(sb.toString());
			beanList = getList(list, beanList);
		}
		
		if(!DataUtil.isEmpty(cardNo)){
			beanList = complainPersonService.getComplainBean(cardNo);
		}
		return beanList;
	}

	
	public List<ComplainBean> searchBeanByWd(PageModel pageModel,String wd,String type) {
		List<ComplainBean> beanList = new ArrayList<ComplainBean>();
		String sql = " from "+getEntityName()+" where title like '%"+wd+"%' and flag='"+type+"' order by comDate,tradeId asc";
		List<ComplainInfo> list = getDao().find(pageModel.getPageNo(),pageModel.getPageSize(),sql);
		beanList = getBeanList(list, beanList);
		return beanList;
	}
	
	
	
	public List<ComplainBean> getBeanList(List<ComplainInfo> list, List<ComplainBean> beanList){
		for (int i = 0; i < list.size(); i++) {
			ComplainInfo info = list.get(i);
			String title = info.getTitle();
			if(title.length()>15){
				title = title.substring(0, 15)+".....";
			}
			info.setTitle(title);
			Long complainId = info.getId();
			ComplainPerson person = complainPersonService
					.getPersonByComplainId(complainId);
			ComplainBean bean = new ComplainBean();
			try {
				PropertyUtils.copyProperties(bean, info);
				//针对举报时 没有个人信息
				if(person!=null){
					PropertyUtils.copyProperties(bean, person);
				}
				beanList.add(bean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return beanList;
	}
	
	
	public List<ComplainBean> getList(List<ComplainInfo> list, List<ComplainBean> beanList){
		for (int i = 0; i < list.size(); i++) {
			ComplainInfo info = list.get(i);
			Long complainId = info.getId();
			ComplainPerson person = complainPersonService
					.getPersonByComplainId(complainId);
			ComplainBean bean = new ComplainBean();
			try {
				PropertyUtils.copyProperties(bean, info);
				//针对举报时 没有个人信息
				if(person!=null){
					PropertyUtils.copyProperties(bean, person);
				}
				beanList.add(bean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return beanList;
	}

	/**
	 * 
	 * @Description: 查询所有被投诉的行业信息
	 * @author yanghui 
	 * @Created 2013-8-2
	 * @return
	 */
	public List<TradeBean> getTradeInfo(String type) {
		List list = getDao().find(" select distinct tradeId,tradeName from "+getEntityName()+" where flag='"+type+"' order by tradeId ");
		List<TradeBean> beanList = new ArrayList<TradeBean>();
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) ((Object[]) list.get(i)==null?"":list.get(i));
			TradeBean bean = new TradeBean();
			bean.setTradeId(obj[0].toString());
			bean.setTradeName(obj[1].toString());
			beanList.add(bean);
		}
		return beanList;
	}

	
	/**
	 * 
	 * @Description: 获取所有投诉信息数量
	 * @author yanghui 
	 * @Created 2013-8-5
	 * @return
	 */
	public int getComplainInfoSize(String tradeId,String type){
		int size = 0;
		StringBuffer sb = new StringBuffer(" from "+getEntityName()+" where 1=1");
		if(!type.equals("")){
			sb.append(" and flag ='"+type+"'");
		}
		if(!tradeId.equals("")){
			sb.append(" and tradeId='"+tradeId+"'");
		}
		
		sb.append(" order by comDate,tradeId asc");
		List list = getDao().find(sb.toString());
		if(list.size()>0){
			size = list.size();
		}
		return size;
	}
	
	
	/**
	 * 
	 * @Description: 根据关键字 模糊查询 所有记录数
	 * @author yanghui 
	 * @Created 2013-8-5
	 * @return
	 */
	public int getComplainInfoSizeByKey(String wd,String type){
		int size = 0;
		String sql = " from "+getEntityName()+" where title like '%"+wd+"%' and flag='"+type+"' order by comDate,tradeId asc";
		List list = getDao().find(sql);
		if(list.size()>0){
			size = list.size();
		}
		return size;
	}
	
	
	
	/**
	 * 
	 * @Description: 分页查询方法
	 * @author yanghui 
	 * @Created 2013-8-5
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public List<ComplainBean> getComplainListByPageNo(PageModel pageModel,String tradeId,String type) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from "+getEntityName()+" where 1=1 ");
		int pageNo = pageModel.getPageNo();
		int pageSize = pageModel.getPageSize();
		if(!tradeId.equals("")){
			//说明此分页查询是根据行业性质来查询的  
			sb.append(" and tradeId='"+tradeId+"'");
		}
		if(!type.equals("")){
			//区别查询为投诉或者举报
			sb.append(" and flag='"+type+"'");
		}
		sb.append(" order by comDate,tradeId asc");
		List<ComplainInfo> list = getDao().find(pageNo, pageSize, sb.toString());
		List<ComplainBean> beanList = new ArrayList<ComplainBean>();
		beanList = getBeanList(list,beanList);
		return beanList;
	}

	/**
	 * 
	 * @Description: 统计每个行业的投诉总量
	 * @author yanghui 
	 * @Created 2013-8-13
	 * @return
	 */
	public List getTotalByTradeId(String flag) {

		List list = getDao().find(" select distinct tradeId from "+getEntityName()+" where flag='"+flag+"' order by tradeId ");
		List totalList = new ArrayList();
		int size  = 0;
		for(int i=0;i<list.size();i++){
			size = getNumberByTradeId(list.get(i).toString(),flag);
			totalList.add(size);
		}
		logger.error(totalList);
		return totalList;
	
	}
	
	public String getTradeName(String flag) {

		List list = getDao().find(" select distinct tradeName from "+getEntityName()+" where flag='"+flag+"' order by tradeId ");
		List totalList = new ArrayList();
		String s = "['";
		for(int i=0;i<list.size();i++){
			s = s+list.get(i).toString()+"','";
			totalList.add(list.get(i).toString());
		}
		String str = s.substring(0,s.length()-2);
		str = str +"]";
		logger.error(str);
		return str;
	
	}
	
	
	public List<TradeBean> getTradeBean(String flag) {

		List list = getDao().find(" select distinct tradeId, tradeName from "+getEntityName()+" where flag='"+flag+"' order by tradeId ");
		List<TradeBean> beanList = new ArrayList<TradeBean>();
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) ((Object[]) list.get(i)==null?"":list.get(i));
			TradeBean bean = new TradeBean();
			bean.setTradeId(obj[0].toString());
			bean.setTradeName(obj[1].toString());
			bean.setComNumber(getNumberByTradeId(obj[0].toString(), flag));
			beanList.add(bean);
		}
		return beanList;
	
	}

	private int getNumberByTradeId(String tradeId, String flag) {
		int size = 0;
		String sql = "select count(*) from "+getEntityName()+" where tradeId='"+tradeId+"' and flag='"+flag+"'";
		List list = getDao().find(sql);
		if(list.size()>0){
			size = Integer.valueOf(list.get(0).toString());
		}
		return size;
	}

	/**
	 * 
	 * @Description: 行业投诉比例
	 * @author yanghui 
	 * @Created 2013-8-13
	 * @param complainType1
	 * @return
	 */
	public List<TradeBean> getTradeBeanRate(String flag) {
		//首先取得投诉总量
		long total = getDao().count(" select count(*) from "+getEntityName()+" where flag='"+flag+"'");
		
		List list = getDao().find(" select distinct tradeId, tradeName from "+getEntityName()+" where flag='"+flag+"' order by tradeId ");
		List<TradeBean> beanList = new ArrayList<TradeBean>();
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) ((Object[]) list.get(i)==null?"":list.get(i));
			TradeBean bean = new TradeBean();
			bean.setTradeId(obj[0].toString());
			bean.setTradeName(obj[1].toString());
			int num = getNumberByTradeId(obj[0].toString(), flag);
			bean.setRate((float)num/(float)total);
			beanList.add(bean);
		}
		return beanList;
	}

	/**
	 * 
	 * @Description: 通过身份证号和行业ID查询信息列表
	 * @author yanghui 
	 * @Created 2013-8-21
	 * @param pageModel
	 * @param tradeId
	 * @param complainType1
	 * @return
	 */
	public List<ComplainBean> getComplainListByCardNo(PageModel pageModel,
			String tradeId,String cardNo, String complainType1) {
		List<ComplainBean> beanList = new ArrayList<ComplainBean>();
		List<ComplainPerson> list = complainPersonService.getpersonByCardNo(pageModel,cardNo);
		for(int i=0;i<list.size();i++){
			ComplainPerson person = list.get(i);
			List<ComplainInfo> infoList = getDao().find(" from "+getEntityName()+" where id=? order by tradeId,comDate", person.getComplainId());
			if(infoList.size()>0){
				ComplainInfo info = infoList.get(0);
				String title = info.getTitle();
				if(info.getTitle().length()>15){
					title = title.substring(0, 15)+".....";
					info.setTitle(title);
				}
				if(tradeId.equals(info.getTradeId())){
					ComplainBean bean = new ComplainBean();
					try {
						PropertyUtils.copyProperties(bean, person);
						PropertyUtils.copyProperties(bean, info);
					} catch (Exception e) {
						e.printStackTrace();
					} 
					beanList.add(bean);
				}
			}
		}
		return beanList;
	}

	public String getTradeNameByTradeId(String tradeId) {
		List<ComplainInfo> list = getDao().find(" from "+getEntityName()+" where tradeId =? ", tradeId);
		ComplainInfo info = list.get(0);
		return info.getTradeName();
	}

	
	
	
	/**
	 * 
	 * @Description:
	 * @author yanghui 
	 * @Created 2013-8-23
	 * @param page  分页
	 * @param keys  模糊查询关键字
	 * @param flag  业务功能类型 1-投诉 2-举报 
	 * @param role 	岗位角色   初步定义  1-审核岗  2-派发岗 3- 处理岗     4-结案岗 
	 * @return
	 */
	public DataGrid comDataGrid(RequestPage page,String keys,String role) {
		//首先默认操作为投诉业务   举报暂时不考虑
		String sql = "select count(*) from "+getEntityName()+" where 1=1 and flag = '1'"; //查询总数   
		StringBuffer sb = new StringBuffer("from " + getEntityName()+" where 1=1 and flag = 1");
		if(role.equals("1")){
			//审核岗
			sb.append(" and dealState ="+SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_00);
		}else if(role.equals("2")){
			//派发岗
			sb.append(" and dealState ="+SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_01);
		}else if(role.equals("3")){
			//处理岗
			sb.append(" and dealState in ('"+SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_02+"','"+SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_04+"')");
		}else if(role.equals("4")){
			//结案岗
			sb.append(" and dealState ="+SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_03);
		}
		if(!keys.equals("")){
			sb.append(" and title like '%"+keys+"%'");
			sql = sql+" and title like '%"+keys+"%'";
		}
		
		sb.append(" order by dealState,tradeId,comDate desc");
		long count = getDao().count(sql);
		List<ComplainInfo> list = getDao().find(page.getPage(), page.getRows(),
				sb.toString());
		List<ComplainBean> beanList = new ArrayList<ComplainBean>();
		for (int i = 0; i < list.size(); i++) {
			ComplainInfo info = list.get(i);
			//处理标题长度，如果大于15个字符，就用点号代替
			String title = info.getTitle();
			if(title.length()>15){
				title = title.substring(0, 15)+".....";
			}
			info.setTitle(title);
			Long complainId = info.getId();
			ComplainPerson person = complainPersonService
					.getPersonByComplainId(complainId);
			ComplainBean bean = new ComplainBean();
			try {
				PropertyUtils.copyProperties(bean, info);
				PropertyUtils.copyProperties(bean, person);
				beanList.add(bean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new DataGrid(count, beanList);
	}
	
	
	
	/**
	 * 
	 * @Description:
	 * @author yanghui 
	 * @param session 
	 * @Created 2013-8-23
	 * @param page  分页
	 * @param keys  模糊查询关键字
	 * @param labels 
	 * @param flag  业务功能类型 1-投诉 2-举报 
	 * @param role 	岗位角色   初步定义 1104-审核岗  1105-派发岗  1106-处理岗    1107-结案岗 
	 * @return
	 */
/*	public DataGrid tsDataGrid(HttpSession session, RequestPage page,String keys,String permission,String roleid, String labels) {
		//首先默认操作为投诉业务   举报暂时不考虑
		StringBuffer countBuf = new StringBuffer();//查询总数   
		StringBuffer sb = new StringBuffer();
		
		StringBuffer countHql = new StringBuffer("select count(*) from "+getEntityName()+" where 1=1 and flag = '1'");//查询总数   
		StringBuffer sql = new StringBuffer("from " + getEntityName()+" where 1=1 and flag = 1");
		
		
		List<ComplainBean> beanList = new ArrayList<ComplainBean>();
		long count = 0;
		
		if(!keys.equals("")){
			sql.append(" and title like '%"+keys+"%'");
			countHql.append(" and title like '%"+keys+"%'");
		}
		if(roleid.equals("0")){
			//说明是超级管理员，具有所有权限操作
			count += getDao().count(countHql.toString());
			sql.append(" order by dealState,tradeId,comDate desc");
			List<ComplainInfo> checklist = getDao().find(page.getPage(), page.getRows(),
					sb.toString());
			beanList = getComplainBean(checklist,beanList);
		}else {
			//普通 用户
			if(!StringUtils.isEmpty(permission)){
				String[] str = permission.split(",");
				for(int i=0;i<str.length;i++){
					countBuf.append(countHql);
					sb.append(sql);
					if(str[i].equals(SystemCommon_Constant.COMPLAIN_DEAL_NODE_01)){
						//具有审核权限
						countBuf.append(" and dealState='"+SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_00+"'");
						sb.append(" and dealState='"+SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_00+"'");
						
						
						count += getDao().count(countBuf.toString());
						beanList = commonMethod(countBuf,sb,page,beanList);
						
					}
					if(str[i].equals(SystemCommon_Constant.COMPLAIN_DEAL_NODE_02)){
						//具有派发权限
						countBuf.append(" and dealState='"+SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_01+"'");
						
						sb.append(" and dealState='"+SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_01+"'");
						
						count += getDao().count(countBuf.toString());
						beanList = commonMethod(countBuf,sb,page,beanList);
					}
					if(str[i].equals(SystemCommon_Constant.COMPLAIN_DEAL_NODE_03)){
						//具有处理权限
						
						//在处理岗时  由于某个用户属于多个组，而某一条投诉信息也会被分配给一个或多个组去处理，所以这里要查询出该用户组别下的所有投诉信息
						String[] label = labels.split(",");
						List list = new ArrayList();
						for(int k=0;k<label.length;k++){
							list.add(label[k]);
						}
						
						StringBuffer buf = new StringBuffer();
						buf.append(sql);
						buf.append(" and ( dealState='"+SystemCommon_Constant.COMPLAIN_DEAL_STATE_02+"' or dealState='"+SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_03+"' or dealState='"+SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_04+"' )");
						buf.append(" order by dealState,tradeId,comDate desc");
						
						//首先获取投诉处理状态满足的全部投诉信息，然后逐条获取它们被分配的组别
						List<ComplainInfo> comlist = getDao().find(page.getPage(), page.getRows(),
								buf.toString());
						List<ComplainInfo> infolist = new ArrayList<ComplainInfo>();
						
						for(int n =0 ;n<comlist.size();n++){
							ComplainInfo info = comlist.get(n);
							String groups = info.getGroup();
							String[] group = groups.split(",");
							for(int m=0;m<group.length;m++){
								if(list.contains(group[m])){
									count++;
									infolist.add(info);
									break;
								}
							}
						}
						
						beanList = getComplainBean(infolist,beanList);	
						
						buf.delete(0, buf.length());
						
						
						
//						countBuf.append(" and ( dealState='"+SystemCommon_Constant.COMPLAIN_DEAL_STATE_02+"' or dealState='"+SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_03+"' or dealState='"+SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_04+"' )");
//						sb.append(" and ( dealState='"+SystemCommon_Constant.COMPLAIN_DEAL_STATE_02+"' or dealState='"+SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_03+"' or dealState='"+SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_04+"' )");
						
						
					}
					if(str[i].equals(SystemCommon_Constant.COMPLAIN_DEAL_NODE_04)){
						//具有结案权限
						countBuf.append(" and dealState='"+SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_07+"'");
						sb.append(" and dealState='"+SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_07+"'");
						
						count += getDao().count(countBuf.toString());
						beanList = commonMethod(countBuf,sb,page,beanList);
					}
					
					
					
	//				count += getDao().count(countBuf.toString());
//					sb.append(" order by dealState,tradeId,comDate desc");
//					List<ComplainInfo> checklist = getDao().find(page.getPage(), page.getRows(),
//							sb.toString());
//					beanList = getComplainBean(checklist,beanList);
//					
					countBuf.delete(0, countBuf.length());
					sb.delete(0, sb.length());
				}
				
			}
			
		}
		
		return new DataGrid(count, beanList);
	}*/
	
	
	private List<ComplainBean> commonMethod(StringBuffer countBuf, StringBuffer sb, RequestPage page, List<ComplainBean> beanList){
		sb.append(" order by comDate desc");
		List<ComplainInfo> checklist = getDao().find(sb.toString());
		beanList = getComplainBean(checklist,beanList);
		return beanList;
	}
	
	

	private List<ComplainBean> getComplainBean(List<ComplainInfo> list, List<ComplainBean> beanList) {
		for (int i = 0; i < list.size(); i++) {
			ComplainInfo info = list.get(i);
			//处理标题长度，如果大于15个字符，就用点号代替
			String title = info.getTitle();
			if(title.length()>15){
				title = title.substring(0, 15)+".....";
			}
			info.setTitle(title);
			Long complainId = info.getId();
			ComplainPerson person = complainPersonService
					.getPersonByComplainId(complainId);
			ComplainBean bean = new ComplainBean();
			try {
				PropertyUtils.copyProperties(bean, info);
				PropertyUtils.copyProperties(bean, person);
				beanList.add(bean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return beanList;
	}

	/**
	 * 
	 * @Description: 查询已办结的信息
	 * @author yanghui 
	 * @Created 2013-9-16
	 * @param page
	 * @param keys
	 * @param userName 
	 * @return
	 */
	public DataGrid finishDataGrid(RequestPage page, String keys, String userName) {
		List<ComplainBean> beanList = new ArrayList<ComplainBean>();
		//获取所有已经结案的投诉信息
		List<ComplainInfo> list = new ArrayList<ComplainInfo>();
		String sql = " from "+getEntityName()+" where dealState='"+SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_05+"'";
		if(!keys.equals("")){
			sql += " and title like '%"+keys+"%'";
		}
		list = getDao().find(page.getPage(),page.getRows(),sql);
		beanList = getComplainBean(list, beanList);
		long count = list.size();
		return new DataGrid(count, beanList);
	}

	/**
	 * 
	 * @Description: 查询正在办理中的信息
	 * @author yanghui 
	 * @Created 2013-9-17
	 * @param page
	 * @param keys
	 * @param userName
	 * @return
	 */
	public DataGrid workDataGrid(RequestPage page, String keys, String userName) {
		List stepList = complainStepService.getWorkComplainStepByUsername(page,userName);
		List<ComplainInfo> list = new ArrayList<ComplainInfo>();
		List<ComplainBean> beanList = new ArrayList<ComplainBean>();
		long count = 0;
		for(int i=0;i<stepList.size();i++){
			Long complainId = (Long) stepList.get(i);
			String sql = " from "+getEntityName()+" where complainId='"+complainId+"'";
			if(!keys.equals("")){
				sql += " and title like '%"+keys+"%'";
			}
			list = getDao().find(sql);
			if(list.size()>0){
				String dealState = list.get(0).getDealState();
				//投诉的状态不为00-待审核  06-无效投诉 05-已办结   则表示均在处理中
				if(!dealState.equals(SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_05) && !dealState.equals(SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_00)&& !dealState.equals(SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_06)){
					count++;
					beanList = getComplainBean(list, beanList);
				}
			}
			
		}
		int pageNo = page.getPage();//当前第几页
		int pageSize = page.getRows();//每页显示的数据条数
		//当前页的开始行记录
		int begin = (pageNo-1)*pageSize;
		//结束行记录
		int end = (int) (pageNo*pageSize>=count?count:pageNo*pageSize);
		List l = beanList.subList(begin,end);
		return new DataGrid(count, l);
	}
	
	
	
	
	public DataGrid tsDataGrid(RequestPage page,String keys,String roleid,String departMentIds) {
		//首先默认操作为投诉业务   举报暂时不考虑
		StringBuffer countBuf = new StringBuffer();//查询总数   
		StringBuffer sb = new StringBuffer();
		
		StringBuffer countHql = new StringBuffer("select count(*) from "+getEntityName()+" where 1=1 and flag = '1'");//查询总数   
		StringBuffer sql = new StringBuffer("from " + getEntityName()+" where 1=1 and flag = 1");
		
		
		List<ComplainBean> beanList = new ArrayList<ComplainBean>();
		long count = 0;
		
		if(!keys.equals("")){
			sql.append(" and title like '%"+keys+"%'");
			countHql.append(" and title like '%"+keys+"%'");
		}
		
		//一个用户可能拥有多个关于投诉的角色或者没有任何角色
		if(StringUtils.isNotEmpty(roleid)){
			//拥有一个或多个关于投诉的角色
			
			String[] str = roleid.split(",");
			for(int i=0;i<str.length;i++){
				countBuf.append(countHql);
				sb.append(sql);
				if(str[i].equals(SystemCommon_Constant.COMPLAIN_DEAL_NODE_APPROVE)){
					//具有审核权限
					countBuf.append(" and dealState='"+SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_00+"'"); //00-投诉提交未审核
					sb.append(" and dealState='"+SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_00+"'");
					
					
					count += getDao().count(countBuf.toString());
					beanList = commonMethod(countBuf,sb,page,beanList);
					
				}
				if(str[i].equals(SystemCommon_Constant.COMPLAIN_DEAL_NODE_PAIFA)){
					//具有派发权限
					countBuf.append(" and dealState='"+SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_01+"'");  //01-审核通过待派发
					
					sb.append(" and dealState='"+SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_01+"'");
					
					count += getDao().count(countBuf.toString());
					beanList = commonMethod(countBuf,sb,page,beanList);
				}
				if(str[i].equals(SystemCommon_Constant.COMPLAIN_DEAL_NODE_BANLI)){
					//具有处理权限
					
					//在处理岗时  由于某个用户属于某个部门，而某一条投诉信息也会被分配给一个或多个部门去处理，所以这里要查询出该用户部门下的所有投诉信息
						//获取用户所在的部门信息
					
					
					
					String[] label = departMentIds.split(",");
					List list = new ArrayList();
					for(int k=0;k<label.length;k++){
						list.add(label[k]);
					}
					
					StringBuffer buf = new StringBuffer();
					buf.append(sql);
					buf.append(" and ( dealState='"+SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_02+"' or dealState='"+SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_03+"' or dealState='"+SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_04+"' )");
					buf.append(" order by comDate desc");
					
					//首先获取投诉处理状态满足的全部投诉信息，然后逐条获取它们被分配的组别
					List<ComplainInfo> comlist = getDao().find(page.getPage(), page.getRows(),
							buf.toString());
					List<ComplainInfo> infolist = new ArrayList<ComplainInfo>();
					
					for(int n =0 ;n<comlist.size();n++){
						ComplainInfo info = comlist.get(n);
						String groups = info.getGroup();
						if(StringUtils.isNotEmpty(groups)){
							//投诉信息被派发到某些部门，该用户只能查看属于他所在部门下的投诉信息
							String[] group = groups.split(",");
							for(int m=0;m<group.length;m++){
								if(list.contains(group[m])){
									count++;
									infolist.add(info);
									break;
								}
							}
						}else{
							//当投诉信息没有被派发到某些部门时，此时该用户可以查看所有需要办理的信息
							count++;
							infolist.add(info);
						}
					}
					
					beanList = getComplainBean(infolist,beanList);	
					
					buf.delete(0, buf.length());
					
					
					
				}
				if(str[i].equals(SystemCommon_Constant.COMPLAIN_DEAL_NODE_JIEAN)){
					//具有结案权限
					countBuf.append(" and dealState='"+SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_07+"'");
					sb.append(" and dealState='"+SystemCommon_Constant.BACK_COMPLAIN_DEAL_STATE_07+"'");
					
					count += getDao().count(countBuf.toString());
					beanList = commonMethod(countBuf,sb,page,beanList);
				}
				countBuf.delete(0, countBuf.length());
				sb.delete(0, sb.length());
			}
		}else{
			
			
		}
		
		int pageNo = page.getPage();//当前第几页
		int pageSize = page.getRows();//每页显示的数据条数
		//当前页的开始行记录
		int begin = (pageNo-1)*pageSize;
		//结束行记录
		int end = (int) (pageNo*pageSize>=count?count:pageNo*pageSize);
		List l = beanList.subList(begin,end);
		return new DataGrid(count, l);
	}

	
	/**
	 * 
	 * @Description: 校验查询码是否重复
	 * @author YangHui 
	 * @Created 2014-6-30
	 * @param searchCode
	 * @return
	 */
	public boolean checkSearchCode(String searchCode) {
		boolean flag = false;
		ComplainInfo info = queryBySearchCode(searchCode);
		if(info!=null){
			flag = true;
		}
		return flag;
	}
	
	
	public ComplainInfo queryBySearchCode(String searchCode){
		ComplainInfo info = null;
		String hql = " from "+getEntityName()+" where searchCode=?";
		List list = getDao().find(hql, searchCode);
		if(DataUtil.listIsNotNull(list)){
			info = (ComplainInfo) list.get(0);
		}
		return info;
	}

	/**
	 * 
	 * @Description:  实名投诉   根据登录的用户查询所进行的投诉信息
	 * @author YangHui 
	 * @Created 2014-7-1
	 * @param userId
	 * @return
	 */
	public List<ComplainInfo> getComplainInfoListByUserId(Long userId) {
		String hql = " from "+getEntityName()+" where userId=? and isValid=? order by comDate desc ";
		List<ComplainInfo> list = getDao().find(hql, new Object[]{userId,SystemCommon_Constant.VALID_STATUS_1});
		return list;
	}
	
}
