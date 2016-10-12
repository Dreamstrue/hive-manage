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

import com.hive.intendmanage.entity.Intend;
import com.hive.intendmanage.entity.IntendRelPrize;
import com.hive.intendmanage.model.IntendBean;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

/**
 * Filename: IntendInterfaceService.java Description: Copyright:Copyright
 * (c)2014 Company: GuangFan
 * 
 * @author: yanghui
 * @version: 1.0
 * @Create: 2014-4-4 Modification History: Date Author Version
 *          ------------------------------------------------------------------
 *          2014-4-4 上午10:16:25 yanghui 1.0
 */

@Service
public class IntendInterfaceService extends BaseService<Intend> {

	@Resource
	private BaseDao<Intend> intendDao;

	@Override
	protected BaseDao<Intend> getDao() {
		return intendDao;
	}

	@Resource
	private PrizeInterfaceService prizeInterfaceService;

	/**
	 * 
	 * @Description: 用户兑换奖品列表
	 * @author yanghui
	 * @Created 2014-4-8
	 * @param userId
	 * @return
	 */
	public DataGrid getIntendList(RequestPage page, Long userId) {

		StringBuffer hql = new StringBuffer();
		hql.append(" select a,b from Intend a, IntendRelPrize b where a.intendNo = b.intendNo ");
		hql.append(" and a.applyPersonId = ?  order by a.applyTime desc");
		List list = getDao().find(hql.toString(), userId);
		List<IntendBean> beanList = new ArrayList<IntendBean>();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Object[] obj = (Object[]) it.next();
			Intend intend = (Intend) obj[0];
			IntendRelPrize irp = (IntendRelPrize) obj[1];
			IntendBean bean = new IntendBean();
			bean.setId(intend.getId());
			bean.setIntendNo(intend.getIntendNo()); // 订单号
			bean.setIntendStatus(intend.getIntendStatus()); // 状态
			bean.setApplyTime(intend.getApplyTime()); // 兑换时间
			bean.setPrizeId(irp.getPrizeId());
			bean.setPrizeName(prizeInterfaceService.get(irp.getPrizeId())
					.getPrizeName());
			beanList.add(bean);
		}
		int pageNo = page.getPage();
		int pageSize = page.getRows();
		int begin = (pageNo - 1) * pageSize;
		int end = (pageNo * pageSize >= beanList.size() ? beanList.size()
				: pageNo * pageSize);
		List childList = beanList.subList(begin, end);
		return new DataGrid(beanList.size(), childList);
	}
	
	
	
	
	public IntendBean getIntendInfoAndPrizeInfo(Long id) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select a,b from Intend a,IntendRelPrize b ");
		sb.append(" where a.intendNo = b.intendNo and a.id = ?");
		
		List list = getDao().find(sb.toString(), id);
		IntendBean bean = new IntendBean();
		for(Iterator it = list.iterator();it.hasNext();){
			Object[] obj = (Object[]) it.next();
			Intend intend = (Intend) obj[0];  //订单信息
			IntendRelPrize irp = (IntendRelPrize) obj[1]; //订单与奖品关联信息
			bean.setId(intend.getId());
			bean.setIntendNo(intend.getIntendNo());
			bean.setIntendStatus(intend.getIntendStatus()); // 状态
			bean.setApplyTime(intend.getApplyTime()); // 兑换时间
			bean.setConsignee(intend.getConsignee());//收货人
			bean.setMobilePhone(intend.getMobilePhone());
			bean.setAddress(intend.getAddress());
			bean.setRemark(intend.getRemark());
			
			bean.setPrizeCateId(irp.getPrizeCateId());
			bean.setPrizeName(prizeInterfaceService.get(irp.getPrizeId()).getPrizeName());
			bean.setPrizeNum(irp.getPrizeNum());
			bean.setExcIntegral(irp.getExcIntegral());
		}
		return bean;
	}

}
