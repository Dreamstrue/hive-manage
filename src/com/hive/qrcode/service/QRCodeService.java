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
import com.hive.enterprisemanage.service.EnterpriseInfoService;
import com.hive.permissionmanage.entity.User;
import com.hive.permissionmanage.service.UserService;
import com.hive.qrcode.entity.QRCode;
import com.hive.qrcode.entity.QRCodeBatch;
import com.hive.qrcode.model.QRCodeBatchVo;
import com.hive.qrcode.model.QRCodeVo;
import com.hive.surveymanage.service.IndustryEntityService;
import com.hive.tagManage.service.TagSNBaseService;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class QRCodeService extends BaseService<QRCode> {
	
	private Logger logger = Logger.getLogger(QRCodeService.class);
	
	@Resource
	private BaseDao<QRCode> qrcodeDao;
	
	@Override
	protected BaseDao<QRCode> getDao() {
		return this.qrcodeDao;
	}
	
	
	@Resource
	private UserService userService;
	@Resource
	private QRCodeBatchService qrcodeBatchService;
	@Resource
	private TagSNBaseService snBaseService;
	@Resource
	private IndustryEntityService industryEntityService;
	/**
	 * 根据批次分页查询二维码信息
	 * @param page
	 * @param batchId
	 * @return
	 */
//	public DataGrid findFurnitureGrid(RequestPage page, String batchId) {
//		logger.info("根据批次分页查询二维码信息==================");
//		StringBuffer hql = new StringBuffer();
//		StringBuffer countHql = new StringBuffer();
//		hql.append("select cf from QRCode cb,QRCodeFurniture cf where cb.id=cf.parentQRCodeId  and cb.qrcodeStatus <> '"+SystemCommon_Constant.QRCode_status_6+"'");
//		countHql.append("select count(*) from QRCode cb,QRCodeFurniture cf where cb.id=cf.parentQRCodeId and cb.qrcodeStatus <> '"+SystemCommon_Constant.QRCode_status_6+"'");
//			//二维码批次
//			if(StringUtils.isNotBlank(batchId)) {
//				hql.append(" and cb.qrcodeBatchId = '"+batchId+"' ");
//				countHql.append(" and cb.qrcodeBatchId = '"+batchId+"' ");
//			}
//		hql.append(" order by cb.qrcodeNumber asc");
//
//		List<QRCodeFurniture> list = this.qrcodeFurnitureDao.find(page.getPage(), page.getRows(), hql.toString());
//		List<QRCodeFurnitureVo> listResult = new ArrayList<QRCodeFurnitureVo>();
//		for(QRCodeFurniture cb : list){
//			QRCodeFurnitureVo vo = new QRCodeFurnitureVo();
//			try {
//				PropertyUtils.copyProperties(vo, cb);
//				QRCode parentQrcode = this.get(cb.getParentQrcodeId());
//				vo.setParentQrcode(parentQrcode);
//				listResult.add(vo);
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			} catch (InvocationTargetException e) {
//				e.printStackTrace();
//			} catch (NoSuchMethodException e) {
//				e.printStackTrace();
//			}
//		}
//		long cout = this.qrcodeFurnitureDao.count(countHql.toString());
//		return new DataGrid(cout, listResult);
//	}

	/**
	 * 根据批次获取所有的二维码
	 * @param id
	 * @return
	 */
	public List<QRCode> findQrcodeByBatchId(String id) {
		StringBuffer hql = new StringBuffer();
		hql.append("from QRCode cb where cb.qrcodeBatchId = '"+id+"'  and cb.qrcodeStatus <> '"+SystemCommon_Constant.QRCode_status_6+"' order by cb.qrcodeNumber");
		
		return this.qrcodeDao.find(hql.toString());
	}
	/**
	 * 获取所有二维码
	 * @param page
	 * @param qrcodeVo
	 * @return
	 */
	public DataGrid datagrid(RequestPage page, QRCodeVo qrcodeVo) {
		StringBuffer hql = new StringBuffer();
		StringBuffer countHql = new StringBuffer();
		hql.append("from QRCode cb where cb.qrcodeStatus <> '"+SystemCommon_Constant.QRCode_status_6+"' ");
		countHql.append("select count(*) from QRCode cb where cb.qrcodeStatus <> '"+SystemCommon_Constant.QRCode_status_6+"' ");
		if(qrcodeVo!=null){
			//二维码编号
			if(StringUtils.isNotBlank(qrcodeVo.getQrcodeNumber())) {
				hql.append(" and cb.qrcodeNumber = '"+qrcodeVo.getQrcodeNumber()+"' ");
				countHql.append(" and cb.qrcodeNumber = '"+qrcodeVo.getQrcodeNumber()+"' ");
			}
			//二维码状态
			if(StringUtils.isNotBlank(qrcodeVo.getQrcodeStatus())) {
				hql.append(" and cb.qrcodeStatus = '"+qrcodeVo.getQrcodeStatus()+"' ");
				countHql.append(" and cb.qrcodeStatus = '"+qrcodeVo.getQrcodeStatus()+"' ");
			}
			//二维码类型
			if(StringUtils.isNotBlank(qrcodeVo.getQrcodeType())) {
				hql.append(" and cb.qrcodeType = '"+qrcodeVo.getQrcodeType()+"' ");
				countHql.append(" and cb.qrcodeType = '"+qrcodeVo.getQrcodeType()+"' ");
			}
			//二维码批次
			if(qrcodeVo.getQrcodeBatchVo()!=null){
				String batchNum = qrcodeVo.getQrcodeBatchVo().getBatchNumber();
				if(StringUtils.isNotBlank(batchNum)){
					QRCodeBatch creb = qrcodeBatchService.findBatchByBatchNumber(batchNum);
					if(creb!=null){
						hql.append(" and cb.qrcodeBatchId = '"+creb.getId()+"' ");
						countHql.append(" and cb.qrcodeBatchId = '"+creb.getId()+"' ");
					}
				}
			}
			//发放明细id
			if(StringUtils.isNotBlank(qrcodeVo.getIssueDetailId())) {
				hql.append(" and cb.issueDetailId = '"+qrcodeVo.getIssueDetailId()+"' ");
				countHql.append(" and cb.issueDetailId = '"+qrcodeVo.getIssueDetailId()+"' ");
			}
			//多个发放明细id
			if(StringUtils.isNotBlank(qrcodeVo.getIssueDetailIds())) {
				hql.append(" and cb.issueDetailId in ("+qrcodeVo.getIssueDetailIds()+") ");
				countHql.append(" and cb.issueDetailId in ("+qrcodeVo.getIssueDetailIds()+") ");
			}
			//批次id
			if(StringUtils.isNotBlank(qrcodeVo.getQrcodeBatchId())) {
				hql.append(" and cb.qrcodeBatchId = '"+qrcodeVo.getQrcodeBatchId()+"' ");
				countHql.append(" and cb.qrcodeBatchId = '"+qrcodeVo.getQrcodeBatchId()+"' ");
			}
			//企业id
			if(StringUtils.isNotBlank(qrcodeVo.getEntityId())) {
				hql.append(" and cb.entityId = '"+qrcodeVo.getEntityId()+"' ");
				countHql.append(" and cb.entityId = '"+qrcodeVo.getEntityId()+"' ");
			}
		}
		hql.append(" order by cb.qrcodeNumber asc");

		List<QRCode> list = this.qrcodeDao.find(page.getPage(), page.getRows(), hql.toString());
		List<QRCodeVo> listResult = new ArrayList<QRCodeVo>();
		for(QRCode cb : list){
			QRCodeVo vo = new QRCodeVo();
			try {
				PropertyUtils.copyProperties(vo, cb);
				QRCodeBatch batch = qrcodeBatchService.get(cb.getQrcodeBatchId());
				QRCodeBatchVo batchVo = new QRCodeBatchVo();
				PropertyUtils.copyProperties(batchVo, batch);
				User user = userService.get(Long.parseLong(batch.getCreater()));
				batchVo.setCreaterName(user.getFullname());
				if(StringUtils.isNotBlank(cb.getEntityId()))
				vo.setIndustryEntity(industryEntityService.get(Long.parseLong(cb.getEntityId())));
				if(StringUtils.isNotBlank(cb.getSnbaseId()))
				vo.setSnBaseInfo(snBaseService.get(cb.getSnbaseId()));
				vo.setQrcodeBatchVo(batchVo);
				listResult.add(vo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		long cout = this.qrcodeDao.count(countHql.toString());
		return new DataGrid(cout, listResult);
	}
	/**
	 * 根据发放明细id获取已发放的二维码
	 * @param id
	 * @return
	 */
	public List<QRCode> findQrcodeByIssueDetailId(String id) {
		StringBuffer hql = new StringBuffer();
		hql.append("from QRCode cb where cb.issueDetailId = '"+id+"' and cb.qrcodeStatus <> '"+SystemCommon_Constant.QRCode_status_6+"'");
		
		return this.qrcodeDao.find(hql.toString());
	}
	/**
	 * 根据二维码编号后去二维码信息
	 * @param num
	 * @return
	 */
	public QRCode findQRCodeByNum(String num,String status) {
		StringBuffer hql = new StringBuffer();
		hql.append("from QRCode cb where cb.qrcodeNumber = '"+num+"' and cb.qrcodeStatus = '"+status+"'");
		List<QRCode> list = this.qrcodeDao.find(hql.toString());
		if(list.size()==1){
			return list.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 根据批次id和二维码状态获取二维码信息
	 * @param id
	 * @param qrcodeStatus2
	 * @return
	 */
	public List<QRCode> findQrcodeByBatchIdAndStatus(String id,String qrcodeStatus2) {
		StringBuffer hql = new StringBuffer();
		hql.append("from QRCode cb where cb.qrcodeBatchId = '"+id+"'  and cb.qrcodeStatus = '"+qrcodeStatus2+"' order by cb.qrcodeNumber");
		
		return this.qrcodeDao.find(hql.toString());
	}
}
