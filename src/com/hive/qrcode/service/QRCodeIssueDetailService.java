package com.hive.qrcode.service;



import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.qrcode.entity.QRCodeIssueDetail;
import com.hive.qrcode.model.QRCodeIssueDetailVo;



import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class QRCodeIssueDetailService extends BaseService<QRCodeIssueDetail> {
	
	private Logger logger = Logger.getLogger(QRCodeIssueDetailService.class);
	
	@Resource
	private BaseDao<QRCodeIssueDetail> qrcodeIssueDetailDao;
	
	@Override
	protected BaseDao<QRCodeIssueDetail> getDao() {
		return this.qrcodeIssueDetailDao;
	}
	
//	@Resource
//	private UserService userService;
	/**
	 * 获取二维码发放列表数据
	 * @param page
	 * @param qrcodeIssueVo
	 * @return
	 */
	public DataGrid datagrid(RequestPage page,QRCodeIssueDetailVo cidVo) {
		logger.info("获取二维码发放列表数据======");
		StringBuffer hql = new StringBuffer();
		StringBuffer countHql = new StringBuffer();
		hql.append("from QRCodeIssueDetail ci where 1=1 ");
		countHql.append("select count(*) from QRCodeIssueDetail ci where 1=1 ");
		if(cidVo!=null){
			if(StringUtils.isNotBlank(cidVo.getPid())){
				hql.append(" and ci.pid = '"+cidVo.getPid()+"'");
				countHql.append(" and ci.pid = '"+cidVo.getPid()+"'");
			}
		}
		hql.append(" order by ci.batchNumber asc");

		List<QRCodeIssueDetail> list = this.qrcodeIssueDetailDao.find(page.getPage(), page.getRows(), hql.toString());
		List<QRCodeIssueDetailVo> listResult = new ArrayList<QRCodeIssueDetailVo>();
		for(QRCodeIssueDetail cb : list){
			QRCodeIssueDetailVo vo = new QRCodeIssueDetailVo();
			try {
				PropertyUtils.copyProperties(vo, cb);
				if(StringUtils.isNotBlank(cb.getCreater())){
//					User us = userService.get(Long.parseLong(cb.getCreater()));
//					vo.setCreater(us.getFullname());
				}
				listResult.add(vo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		long cout = this.qrcodeIssueDetailDao.count(countHql.toString());
		return new DataGrid(cout, listResult);
	}
	/**
	 * 
	 * @param pid
	 * @param batchNumber
	 * @return
	 */
	public QRCodeIssueDetail findIssueDetailByPidAndBatchNum(String pid,String batchNumber) {
		StringBuffer hql = new StringBuffer();
		hql.append("from QRCodeIssueDetail ci where ci.pid = '"+pid+"' and ci.batchNumber = '"+batchNumber+"' and ci.valid = '"+SystemCommon_Constant.VALID_STATUS_1+"'");

		List<QRCodeIssueDetail> list = this.qrcodeIssueDetailDao.find(hql.toString());
		if(list.size()==1){
			return list.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 根据pid获取所有的发放明细
	 * @param id
	 * @return
	 */
	public List<QRCodeIssueDetail> findIssueDetailByPid(String id) {
		StringBuffer hql = new StringBuffer();
		hql.append("from QRCodeIssueDetail ci where ci.pid = '"+id+"' and ci.valid = '"+SystemCommon_Constant.VALID_STATUS_1+"'");

		List<QRCodeIssueDetail> list = this.qrcodeIssueDetailDao.find(hql.toString());
		return list;
	}
	
//	@Resource
//	private BaseDao<QRCodeFurniture> qrcodeFurnitureDao;
}
