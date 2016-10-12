package com.hive.qrcode.service;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hive.permissionmanage.entity.User;
import com.hive.permissionmanage.service.UserService;
import com.hive.qrcode.entity.QRCodeBatch;
import com.hive.qrcode.model.QRCodeBatchVo;


import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class QRCodeBatchService extends BaseService<QRCodeBatch> {
	
	private Logger logger = Logger.getLogger(QRCodeBatchService.class);
	
	@Resource
	private BaseDao<QRCodeBatch> qrcodeBatchDao;
	@Override
	protected BaseDao<QRCodeBatch> getDao() {
		return this.qrcodeBatchDao;
	}
	
	@Resource
	private UserService userService;

	public DataGrid datagrid(RequestPage page,QRCodeBatchVo qrcodeBatchVo) {
		StringBuffer hql = new StringBuffer();
		StringBuffer countHql = new StringBuffer();
		hql.append(" from QRCodeBatch cb where 1=1 ");
		countHql.append("select count(*) from QRCodeBatch cb where 1=1");
		if(qrcodeBatchVo!=null){
			logger.info("二维码datagrid查询===================");
			//二维码批次
			if(StringUtils.isNotBlank(qrcodeBatchVo.getBatchNumber())) {
				hql.append(" and cb.batchNumber = '"+qrcodeBatchVo.getBatchNumber()+"' ");
				countHql.append(" and cb.batchNumber = '"+qrcodeBatchVo.getBatchNumber()+"' ");
			}
			//印刷情况
			if(qrcodeBatchVo.getStatus()!=null) {
				hql.append(" and cb.status = "+qrcodeBatchVo.getStatus()+" ");
				countHql.append(" and cb.status = "+qrcodeBatchVo.getStatus()+" ");
			}
			//最小有效数量
			if(StringUtils.isNotBlank(qrcodeBatchVo.getValidCount())) {
				hql.append(" and cb.validAmount > "+qrcodeBatchVo.getValidCount()+" ");
				countHql.append(" and cb.validAmount > "+qrcodeBatchVo.getValidCount()+" ");
			}
		}
		hql.append(" order by cb.createTime desc");

		List<QRCodeBatch> list = this.qrcodeBatchDao.find(page.getPage(), page.getRows(), hql.toString());
		List<QRCodeBatchVo> listResult = new ArrayList<QRCodeBatchVo>();
		for(QRCodeBatch cb : list){
			QRCodeBatchVo vo = new QRCodeBatchVo();
			try {
				PropertyUtils.copyProperties(vo, cb);
				User user = userService.get(Long.parseLong(cb.getCreater()));
				vo.setCreaterName(user.getFullname());
				listResult.add(vo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		long cout = this.qrcodeBatchDao.count(countHql.toString());
		return new DataGrid(cout, listResult);
	}

	/**
	 * 根据批次获取批次实体信息
	 * @param batchNum
	 * @return
	 */
	public QRCodeBatch findBatchByBatchNumber(String batchNum) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from QRCodeBatch cb where cb.batchNumber = '"+batchNum+"' ");
		List<QRCodeBatch> list = this.qrcodeBatchDao.find(hql.toString());
		if(list.size()==1){
			return list.get(0);
		}else{
			return null;
		}
	}
}
