package com.hive.complain.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Service;

import com.hive.complain.entity.ComplainInfo;
import com.hive.complain.entity.ComplainPerson;
import com.hive.complain.model.ComplainBean;
import com.hive.util.PageModel;

import dk.dao.BaseDao;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class ComplainPersonService extends BaseService<ComplainPerson> {

	@Resource
	private ComplainInfoService complainInfoService;
	
	@Resource
	private BaseDao<ComplainPerson> personDao;
	

	@Override
	protected BaseDao<ComplainPerson> getDao() {
		return personDao;
	}

	public ComplainPerson getPersonByComplainId(Long id) {
		ComplainPerson person = new ComplainPerson();
		List list = getDao().find("from "+getEntityName()+" where complainId = ?", id);
		if(list.size()>0){
			person = (ComplainPerson) list.get(0);
		}
		return person;
	}

	public List<ComplainPerson> getPerson(RequestPage page) {
		String hql = "from " + getEntityName();
		List<ComplainPerson> list = getDao().find(page.getPage(), page.getRows(),hql);
		return list;
	}
	
	/**
	 * 
	 * @Description: 通过身份证号查询投诉举报信息
	 * @author yanghui 
	 * @Created 2013-7-31
	 * @param cardNo
	 * @return
	 */
	public List<ComplainBean> getComplainBean(String cardNo){
		List<ComplainBean> beanList = new ArrayList<ComplainBean>();
		List<ComplainPerson> list = getDao().find(" from "+getEntityName()+" where cardNo=?", cardNo);
		for(int i=0;i<list.size();i++){
			ComplainPerson person = list.get(i);
			Long complainId = person.getComplainId();
			ComplainInfo info = complainInfoService.getComplainInfoById(complainId);
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
	 * @Description: 通过身份证号或身份证和行业ID查询总数
	 * @author yanghui 
	 * @Created 2013-8-9
	 * @param cardNo
	 * @return
	 */
	public int getTotalPersonByCardNo(String cardNo,String tradeId) {
		int size=0;
		List list = new ArrayList();
		if(tradeId.equals("")){
			list = getDao().find(" from "+getEntityName()+" where cardNo=?", cardNo);
		}else{
			List<ComplainPerson> plist = getDao().find("from "+getEntityName()+" where cardNo=?", cardNo);
			for(int i=0;i<plist.size();i++){
				ComplainPerson person = plist.get(i);
				List<ComplainInfo> ilist = complainInfoService.getComplainInfoListById(person.getComplainId());
				if(ilist.size()>0){
					if(tradeId.equals(ilist.get(0).getTradeId())){
						size++;
					}
				}
				
			}
		}
		
		if(list.size()>0){
			size = list.size();
		}
		return size;
	}
	
	
	/**
	 * 
	 * @Description:投诉按身份证号查询分页信息
	 * @author yanghui 
	 * @Created 2013-8-9
	 * @param pageModel
	 * @param cardNo
	 * @return
	 */
	public List<ComplainBean> getPageListByCardNo(PageModel pageModel,
			String cardNo,String tradeId) {
		List<ComplainBean> beanList = new ArrayList<ComplainBean>();
		if(tradeId.equals("")){
			String sql = " from "+getEntityName()+" where cardNo='"+cardNo+"'";
			List<ComplainPerson> list = getDao().find(pageModel.getPageNo(),pageModel.getPageSize(),sql);
			beanList = getBeanList(list, beanList);
		}else{
			beanList = complainInfoService.getComplainListByCardNo(pageModel, tradeId, cardNo, "");
		}
		
		
		return beanList;
	}
	
	
	public List<ComplainBean> getBeanList(List<ComplainPerson> list, List<ComplainBean> beanList){
		for (int i = 0; i < list.size(); i++) {
			ComplainPerson person = list.get(i);
			Long complainId = person.getComplainId();
			
			
			ComplainInfo info = complainInfoService
					.getComplainInfoById(complainId);
			String title = info.getTitle();
			if(title.length()>15){
				title = title.substring(0, 15)+".....";
			}
			info.setTitle(title);	
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

	public List<ComplainPerson> getpersonByCardNo(PageModel pageModel,String cardNo) {
		List<ComplainPerson> list = getDao().find(pageModel.getPageNo(),pageModel.getPageSize(),"from "+getEntityName()+" where cardNo = ? order by complainId", cardNo);
		return list;
	}

}
