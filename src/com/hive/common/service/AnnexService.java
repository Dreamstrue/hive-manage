package com.hive.common.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Annex;
import com.hive.util.DataUtil;

import dk.dao.BaseDao;
import dk.service.BaseService;

@Service
public class AnnexService extends BaseService<Annex> {

	
	@Resource
	private BaseDao<Annex> annexDao;
	@Override
	protected BaseDao<Annex> getDao() {
		return annexDao;
	}
	
	/**
	 * 
	 * @Description: 
	 * @author yanghui 
	 * @Created 2013-10-24
	 * @param nexlist
	 * @param id  可以为空，主要是针对某些功能模块处理时使用，如图片新闻管理
	 */
	public void saveAnnexList(List<Annex> nexlist, String id) {
		if(nexlist!=null){
			for(Annex annex:nexlist){
				if(!DataUtil.isEmpty(id)){
					annex.setObjectId(Long.valueOf(id));
				}
				save(annex);
			}
		}
	}


	
	public List<Annex> getAnnexInfoByObjectId(Long id, String objectTable) {
		List<Annex> list = annexDao.find(" from "+getEntityName()+" where objectId=? and objectTable=? and cvalid = '"+SystemCommon_Constant.VALID_STATUS_1+"'",new Object[]{id,objectTable});
		return list;
	}
	
	/**
	 * 查找一个附件
	 * @param annexType
	 * @param coObjectID
	 * @return
	 */
	public Annex getAnnexByType(String annexType,String coObjectId){
		Annex cannex=null;
		List l=annexDao.find(" from "+getEntityName()+" WHERE crelationobjecttype=? AND crelationobjectid=? AND cvalid=1 ORDER BY nannexid DESC", annexType, coObjectId);
		if(l!=null&&l.size()>0){
			cannex=(Annex) l.get(0);
		}
		return cannex;
	}
	
	/**
	 * 
	 * @Description: 该方法只针对图片新闻模块使用
	 * @author yanghui 
	 * @Created 2013-10-24
	 * @param id
	 * @return
	 */
	public List<Annex> getAnnexInfoByObjectIdToPictureNew(Long id) {
		StringBuffer sb = new StringBuffer(" from "+ getEntityName());
		sb.append(" where objectId=? and cvalid = '"+SystemCommon_Constant.VALID_STATUS_1+"'");
		sb.append(" and cannextype is null");
		List<Annex> list = annexDao.find(sb.toString(), id);
		return list;
	}
	
	/**
	 * 修改日期2015-5-15
	 * 检测平台用代码
	 * @Description: 该方法只针对图片新闻模块使用
	 * @author yanghui 
	 * @Created 2013-10-24
	 * @param id
	 * @return
	 */
	public List<Annex> getAnnexInfoByObjectIdToPictureNew1(Long id, String Annextype) {
		StringBuffer sb = new StringBuffer(" from "+ getEntityName());
		sb.append(" where objectId=? and cvalid = '"+SystemCommon_Constant.VALID_STATUS_1+"'");
		sb.append(" and cannextype='"+Annextype+"'");
		List<Annex> list = annexDao.find(sb.toString(), id);
		return list;
	}
	
	public List<Annex> getAnnexInfoByObjectIdAndAnnexType(Long id) {
		StringBuffer sb = new StringBuffer(" from "+ getEntityName());
		sb.append(" where objectId=? and cvalid = '"+SystemCommon_Constant.VALID_STATUS_1+"'");
		sb.append(" and cannextype='"+SystemCommon_Constant.ANNEXT_TYPE_IMAGE+"'");
		List<Annex> list = annexDao.find(sb.toString(), id);
		return list;
	}
	
	public List<Annex> getAnnexInfoByObjectIdAndObjectType(Long id, String objectType) {
		StringBuffer sb = new StringBuffer(" from "+ getEntityName());
		sb.append(" where objectId=? and cvalid = '"+SystemCommon_Constant.VALID_STATUS_1+"'");
		sb.append(" and objectTable='"+objectType+"'");
		List<Annex> list = annexDao.find(sb.toString(), id);
		return list;
	}
	
	public int getAllValidAnnexSize(Long id,String sign) {
		int size = 0;
		List<Annex> list = new ArrayList<Annex>();
		if(sign.equals("pictureNew") || sign.equals("magazine")){
			list = getDao().find(" from "+getEntityName()+" where objectId=? and cvalid = '"+SystemCommon_Constant.VALID_STATUS_1+"' and cannextype is null", id);
		}else list = getDao().find(" from "+getEntityName()+" where objectId=? and cvalid = '"+SystemCommon_Constant.VALID_STATUS_1+"'", id);
		if(list.size()>0){
			size = list.size();
		}
		return size;
	}
	
	/**
	 * 根据对象ID删除所有的有效附件
	 * @param id
	 */
	public void setAnnexIsValidByObjectId(Long id,String sign){
		List<Annex> list = getDao().find(" from "+getEntityName()+" where objectId=? and cvalid='"+SystemCommon_Constant.VALID_STATUS_1+"'", id);
		if(list.size()>0){
			for(Annex nex:list){
				if(sign.equals("pictureNew")|| sign.equals("magazine")){
					//此处添加参数主要是为了处理新闻图片内容管理模块，当没有附件时，防止附件表里的图片附件也被重置为不可用
					getDao().execute("UPDATE " + getEntityName() + " SET cvalid = ? WHERE id=? and cannextype is null", SystemCommon_Constant.VALID_STATUS_0,nex.getId());
				}else getDao().execute("UPDATE " + getEntityName() + " SET cvalid = ? WHERE id=?", SystemCommon_Constant.VALID_STATUS_0,nex.getId());
			}
		}
	}
	
	
	public List<Annex> getAnnexListByObjectId(Long id, String objectTable){
		List<Annex> list = new ArrayList<Annex>(); 
		list = getDao().find(" from "+getEntityName()+" where objectId=? and objectTable=? and cvalid='"+SystemCommon_Constant.VALID_STATUS_1+"'", new Object[]{id,objectTable});
		return list;
	}

	
	/**
	 * 根据附件的ID删除该附件
	 * @param id
	 */
	public void updateAnnexIsValidById(Long id,String sign){
		if(sign.equals("pictureNew") || sign.equals("magazine")){
			//此处添加参数主要是为了处理新闻图片内容管理模块，当没有附件时，防止附件表里的图片附件也被重置为不可用
			getDao().execute("UPDATE " + getEntityName() + " SET cvalid = ? WHERE id=? and cannextype is null", SystemCommon_Constant.VALID_STATUS_0,id);
		}else getDao().execute("UPDATE " + getEntityName() + " SET cvalid = ? WHERE id=?", SystemCommon_Constant.VALID_STATUS_0,id);
	}
	
	/**
	 * 根据附件的ID恢复该附件
	 * @param id
	 */
	public void setAnnexValidById(Long id,String sign){
		getDao().execute("UPDATE " + getEntityName() + " SET cvalid = ? WHERE id=?", SystemCommon_Constant.VALID_STATUS_1,id);
	}
	
	/**
	 * 功能描述：根据表名和数据ID联合删除附件信息
	 * 创建时间:2013-11-1上午11:21:10
	 * 创建人: Ryu Zheng
	 * 
	 * @param tableName
	 * @param objectId
	 * @return
	 */
	public int deleteAnnexByTableAndObjectId(String tableName, Long objectId){
		return getDao().execute(
				"UPDATE " + getEntityName()
						+ " SET cvalid = ? WHERE objectTable =? AND id=?",
				SystemCommon_Constant.VALID_STATUS_0, tableName, objectId);
	}
	
	/**
	 * 功能描述：删除指定的附件
	 * 创建时间:2013-11-1上午11:30:38
	 * 创建人: Ryu Zheng
	 * 
	 * @param id 附件ID
	 * @return
	 */
	public int  deleteAnnexById(Long id){
		return getDao().execute(
				"UPDATE " + getEntityName() + " SET cvalid = 0 WHERE id=?", id);
	}
	
	/**
	 * 
	 * @Description: 该方法被重写了，加个参数方便处理电子杂志这块的操作
	 * @author yanghui 
	 * @Created 2013-12-7
	 * @param id
	 * @param annexType
	 * @return
	 */
	public List<Annex> getAnnexInfoByObjectIdAndAnnexType(Long id,String annexType) {
		StringBuffer sb = new StringBuffer(" from "+ getEntityName());
		sb.append(" where objectId=? and cvalid = '"+SystemCommon_Constant.VALID_STATUS_1+"'");
		sb.append(" and cannextype='"+annexType+"'");
		List<Annex> list = annexDao.find(sb.toString(), id);
		return list;
	}
	
	/**
	 * 
	 * @Description: 在内容信息时，首选保存所上传的附件信息，然后把附件信息的ID值全部保存后返回
	 * @author yanghui 
	 * @Created 2014-1-6
	 * @param nexlist
	 * @param id
	 * @return
	 */
	public String saveAnnexListTwo(List<Annex> nexlist, String id) {
		StringBuffer b = new StringBuffer();
		String idString = "";
		if(nexlist!=null){
			for(Annex annex:nexlist){
				if(StringUtils.isNotEmpty(id)){
					annex.setObjectId(Long.valueOf(id));
				}
				save(annex);
				Long nexid = annex.getId();
				b.append(nexid).append(",");
			}
		}
		idString = b.toString();// 12,23,22,
		idString = idString.substring(0,idString.lastIndexOf(","));
		return idString;
	}

	/**
	 * 
	 * @Description: 在某条内容信息保存成功后，修改该信息关联的附件信息
	 * @author yanghui 
	 * @Created 2014-1-6
	 * @param ncreateid
	 * @param id
	 * @param idString
	 */
	public void updateAnnex(Long ncreateid, Long id, String idString) {
		if(StringUtils.isNotEmpty(idString)){
			String[] str = idString.split(",");
			for(int i=0;i<str.length;i++){
				Annex n = get(Long.valueOf(str[i]));
				if(n!=null){
					n.setNcreateid(ncreateid);
					n.setObjectId(id);
					update(n);
				}
			}
		}
	}
	
	
	
	

}
