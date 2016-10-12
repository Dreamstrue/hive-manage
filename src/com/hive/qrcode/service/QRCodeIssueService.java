package com.hive.qrcode.service;



import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.permissionmanage.entity.User;
import com.hive.permissionmanage.service.UserService;
import com.hive.qrcode.entity.QRCode;
import com.hive.qrcode.entity.QRCodeBatch;
import com.hive.qrcode.entity.QRCodeIssue;
import com.hive.qrcode.entity.QRCodeIssueDetail;
import com.hive.qrcode.model.QRCodeIssueDetailVo;
import com.hive.qrcode.model.QRCodeIssueVo;
import com.hive.qrcode.model.QRCodeVo;



import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class QRCodeIssueService extends BaseService<QRCodeIssue> {
	
	private Logger logger = Logger.getLogger(QRCodeIssueService.class);
	
	@Resource
	private BaseDao<QRCodeIssue> qrcodeIssueDao;
	
	@Override
	protected BaseDao<QRCodeIssue> getDao() {
		return this.qrcodeIssueDao;
	}
	
	@Resource
	private UserService userService;
	@Resource
	private QRCodeIssueDetailService qrcodeIssueDetailService;
	@Resource
	private QRCodeService qrcodeService;
	@Resource
	private QRCodeBatchService qrcodeBatchService;
	/**
	 * 获取二维码发放列表数据
	 * @param page
	 * @param qrcodeIssueVo
	 * @return
	 */
	public DataGrid datagrid(RequestPage page,QRCodeIssueVo qrcodeIssueVo) {
		logger.info("获取二维码发放列表数据====");
		StringBuffer hql = new StringBuffer();
		StringBuffer countHql = new StringBuffer();
		hql.append("from QRCodeIssue ci where 1=1 ");
		countHql.append("select count(*) from QRCodeIssue ci where 1=1 ");
		if(qrcodeIssueVo!=null){
			//发放编号
			if(StringUtils.isNotBlank(qrcodeIssueVo.getNumber())) {
				hql.append(" and ci.number = '"+qrcodeIssueVo.getNumber()+"' ");
				countHql.append(" and ci.number = '"+qrcodeIssueVo.getNumber()+"' ");
			}
			//发放状态
			if(StringUtils.isNotBlank(qrcodeIssueVo.getStatus())) {
				hql.append(" and ci.status = '"+qrcodeIssueVo.getStatus()+"' ");
				countHql.append(" and ci.status = '"+qrcodeIssueVo.getStatus()+"' ");
			}
			//关联企业
			if(StringUtils.isNotBlank(qrcodeIssueVo.getEntityId())) {
				hql.append(" and ci.entityId = '"+qrcodeIssueVo.getEntityId()+"' ");
				countHql.append(" and ci.entityId = '"+qrcodeIssueVo.getEntityId()+"' ");
			}
		}
		hql.append(" order by ci.number asc");

		List<QRCodeIssue> list = this.qrcodeIssueDao.find(page.getPage(), page.getRows(), hql.toString());
		List<QRCodeIssueVo> listResult = new ArrayList<QRCodeIssueVo>();
		for(QRCodeIssue cb : list){
			QRCodeIssueVo vo = new QRCodeIssueVo();
			try {
				PropertyUtils.copyProperties(vo, cb);
				if(StringUtils.isNotBlank(cb.getCreater())){
					User us = userService.get(Long.parseLong(cb.getCreater()));
					vo.setCreater(us.getFullname());
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
		long cout = this.qrcodeIssueDao.count(countHql.toString());
		return new DataGrid(cout, listResult);
	}
	/**
	 * 增加发放明细
	 * @param cidVo
	 * @param userId
	 * @param count
	 * @param idsArr
	 */
	public void qrcodeIssueDetailAdd(QRCodeIssueDetailVo cidVo,Long userId, int count, String[] idsArr) throws Exception{
			//首先需要校验此发放记录中此批次是否已经存在
			QRCodeIssueDetail ci = qrcodeIssueDetailService.findIssueDetailByPidAndBatchNum(cidVo.getPid(),cidVo.getBatchNumber());
			if(ci==null){
				ci = new QRCodeIssueDetail();
				PropertyUtils.copyProperties(ci, cidVo);
				ci.setCreater(userId.toString());
				ci.setCreateTime(new Date());
				ci.setAmount(count);
				qrcodeIssueDetailService.save(ci);
			}else{
				//cid不为空，说明此发放记录下已经有了此批次，所以只需要对此发放明细进行修改即可
				ci.setAmount(ci.getAmount()+count);
				qrcodeIssueDetailService.update(ci);
			}
			//修改批次中的有效数量
			QRCodeBatch cbatch = qrcodeBatchService.findBatchByBatchNumber(cidVo.getBatchNumber());
			cbatch.setValidAmount(cbatch.getValidAmount()-count);
			qrcodeBatchService.update(cbatch);
			//更新二维码表
			for(String id : idsArr){
				QRCode cre = qrcodeService.get(id);
				QRCodeIssue  ciss = this.get(cidVo.getPid());
				if(ciss!=null){
					cre.setEntityId(ciss.getEntityId());
					cre.setContent(ciss.getQrcodeContent());
					cre.setQrcodeType(ciss.getQrcodeType());
				}
				cre.setIssueDetailId(ci.getId());
				cre.setQrcodeStatus(SystemCommon_Constant.QRCode_status_3);//待发放
				qrcodeService.update(cre);
			}
	}
	/**
	 * 执行发放命令
	 * @param cidVo
	 * @throws Throwable 
	 */
	public boolean issueQrcode(QRCodeIssueVo cidVo) throws Throwable {
		String id = cidVo.getId();
		if(StringUtils.isNotBlank(id)){
			QRCodeIssue ci = this.get(id);
			if(ci!=null){
				//获取所有的发放明细
				List<QRCodeIssueDetail> list = qrcodeIssueDetailService.findIssueDetailByPid(ci.getId());
				if(list.size()==0){
					return false;
				}
				//执行发放操作
				ci.setStatus("2");
				this.update(ci);
				for(QRCodeIssueDetail cid:list){
					//获取关联发放明细的所有二维码
					List<QRCode> listCre = qrcodeService.findQrcodeByIssueDetailId(cid.getId());
					for(QRCode cr : listCre){
						cr.setQrcodeStatus(SystemCommon_Constant.QRCode_status_4);
						//更新二维码状态
						qrcodeService.update(cr);
					}
				}
			}else{
				throw new Throwable("传递的QRCodeIssueVo参数中id为："+id+",此id参数有误！请核对！");
			}
		}else{
			throw new Throwable("传递的QRCodeIssueVo参数中id为空,请核对！");
		}
		return true;
	}
	/**
	 * 手动增加发放明细
	 * @param issueId
	 * @param userId
	 * @param qrcodeId
	 */
	public void qrcodeIssueDetailAdd_manual(QRCodeVo qrcodeVo, Long userId) {
		QRCode cre =qrcodeService.get(qrcodeVo.getId());
		QRCodeBatch cb =qrcodeBatchService.get(cre.getQrcodeBatchId());
		//先保存发放明细
		QRCodeIssueDetail ci = new QRCodeIssueDetail();
		ci.setAmount(1);
		ci.setBatchNumber(cb.getBatchNumber());
		ci.setCreater(userId==null?"":userId.toString());
		ci.setCreateTime(new Date());
		ci.setQrcodeType(qrcodeVo.getQrcodeType());
		ci.setPid(qrcodeVo.getIssueId());
		ci.setValid("1");
		qrcodeIssueDetailService.save(ci);
		//修改批次中的有效数量
		cb.setValidAmount(cb.getValidAmount()-1);
		qrcodeBatchService.update(cb);
		//更新二维码表
		QRCodeIssue  ciss = this.get(qrcodeVo.getIssueId());
		if(ciss!=null){
			cre.setEntityId(ciss.getEntityId());
		}
		cre.setContent(qrcodeVo.getContent());
		cre.setQrcodeType(qrcodeVo.getQrcodeType());
		cre.setIssueDetailId(ci.getId());
		cre.setQrcodeStatus(SystemCommon_Constant.QRCode_status_3);//待发放
		qrcodeService.update(cre);
	}
	
}
