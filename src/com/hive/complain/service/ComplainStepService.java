package com.hive.complain.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.complain.entity.ComplainStep;

import dk.dao.BaseDao;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class ComplainStepService extends BaseService<ComplainStep> {
	
	@Resource
	private BaseDao<ComplainStep> stepDao;

	
	@Override
	protected BaseDao<ComplainStep> getDao() {
		return stepDao;
	}

	/**
	 * 
	 * @Description: 通过投诉信息Id和岗位节点查询处理该节点的用户的信息
	 * @author yanghui 
	 * @Created 2013-9-4
	 * @param id
	 * @param type
	 * @return
	 */
	public ComplainStep getComplainStep(Long id, String type) {
		String sql = "from "+ getEntityName()+" where complainId='"+id+"' and step='"+type+"' order by dealTime desc";
		List<ComplainStep> list = getDao().find(sql);
		ComplainStep step = new ComplainStep();
		if(list.size()>0){
			step = list.get(0);
		}
		return step;
	}

	
	public ComplainStep getByIdAndStep(Long id, String node) {
		//可能某条投诉信息被驳回重新处理，另一用户确认签办了，此时步骤表中就存在两条由不同用户签办的信息，此时查询步骤信息按降序排列，取得第一条，就是最后一次签收人
		List<ComplainStep> list = getDao().find(" from "+getEntityName()+" where complainId='"+id+"' and step = '"+node+"' order by dealTime desc");
		ComplainStep step =  new ComplainStep();
		if(list.size()>0){
			step = list.get(0);
		}
		
		return step;
	}

	/**
	 * 
	 * @Description: 提供给已办理信息查询时使用的方法
	 * @author yanghui 
	 * @Created 2013-9-17
	 * @param page
	 * @param userName
	 * @return
	 */
	public  List getComplainStepByUsername(RequestPage page, String userName) {
		//所谓已办结　　不管当前用户是否参与某条投诉信息，只要该投诉信息办结，他都可以查看详情　
//		String sql = "from "+getEntityName()+" where  username ='"+userName+"'";
		String sql = "from "+getEntityName()+" where  step = '"+SystemCommon_Constant.COMPLAIN_DEAL_NODE_JIEAN+"'";
		List<ComplainStep> list = getDao().find(page.getPage(),page.getRows(),sql);
		return list;
	}
	
	
	/**
	 * 
	 * @Description: 提供给办理中信息查询时使用的方法
	 * @author yanghui 
	 * @Created 2013-9-17
	 * @param page
	 * @param userName
	 * @return
	 */
	public  List getWorkComplainStepByUsername(RequestPage page, String userName) {
		//所谓办理中  是指该用户参与过的投诉信息的处理，还没有结案的信息   所以这里查询该用户所参与的所有的处理节点但不处于结案岗
		String sql = "from "+getEntityName()+" where  username ='"+userName+"'";
		List<ComplainStep> list = getDao().find(sql);
		List subList = new ArrayList();
		for(int i=0;i<list.size();i++){
			if(!subList.contains(list.get(i).getComplainId())){
				subList.add(list.get(i).getComplainId());
			}
		}
		
		return subList;
	}

}
