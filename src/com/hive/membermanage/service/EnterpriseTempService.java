package com.hive.membermanage.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import com.hive.common.entity.Annex;
import com.hive.enterprisemanage.entity.EEnterpriseinfo;
import com.hive.enterprisemanage.entity.EEnterpriseinfomodifytemp;
import com.hive.enterprisemanage.entity.EEnterpriseproduct;
import com.hive.enterprisemanage.entity.EEnterpriseproductmodifytemp;
import com.hive.enterprisemanage.entity.EQualification;
import com.hive.enterprisemanage.entity.EQualificationmodifytemp;
import com.hive.enterprisemanage.entity.EnterpriseLinkPerson;
import com.hive.membermanage.entity.MMember;
import com.hive.membermanage.model.IntegritystyleView;
import com.hive.membermanage.model.ProductVO;
import com.hive.membermanage.model.QualificationVO;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class EnterpriseTempService extends BaseService<EEnterpriseinfomodifytemp>
{
  private static final Logger logger = Logger.getLogger(EnterpriseTempService.class);

  @Resource
  private BaseDao<EEnterpriseinfomodifytemp> enterpriseinfomodifytempDao;

  @Resource
  private BaseDao<EEnterpriseinfo> enterpriseinfoDao;

  @Resource
  private BaseDao<EEnterpriseproductmodifytemp> enterpriseproductmodifytempDao;

  @Resource
  private BaseDao<EEnterpriseproduct> enterpriseproductDao;

  @Resource
  private BaseDao<EQualificationmodifytemp> qualificationmodifytempDao;

  @Resource
  private BaseDao<EQualification> qualificationDao;

  @Resource
  private BaseDao<MMember> memberDao;

  @Resource
  private BaseDao<Annex> annexDao;
  @Resource
  private BaseDao<EnterpriseLinkPerson> enterpriseLinkPersonDao;

  @Resource
  private SessionFactory sessionFactory;

  public List<Annex> findAnnexByTableAndObjId(String tableName, Long objectId) { List resultList = null;
    if (StringUtils.isBlank(tableName)) {
      return resultList;
    }
    if (objectId == null) {
      return resultList;
    }

    String hql = "FROM Annex WHERE objectTable=? AND objectId=? ";
    resultList = this.annexDao.find(hql, new Object[] { tableName, objectId });
    return resultList;
  }

  public Annex getAnnexByTableAndObjId(String tableName, Long objectId)
  {
    Annex annex = null;
    if (StringUtils.isBlank(tableName)) {
      return annex;
    }
    if (objectId == null) {
      return annex;
    }

    String hql = "FROM Annex WHERE objectTable=? AND objectId=? ";
    annex = (Annex)this.annexDao.get(hql, new Object[] { tableName, objectId });
    return annex;
  }

  public DataGrid listUncheckEnterprise(RequestPage page, String enterpriseName, String status)
  {
    StringBuffer hql = new StringBuffer("FROM EEnterpriseinfomodifytemp WHERE cauditstatus=" + status + " AND cvalid=1 ");
    StringBuffer countHql = new StringBuffer("select count(*) from EEnterpriseinfomodifytemp where cauditstatus=" + status + " AND cvalid=1");

    if (StringUtils.isNotBlank(enterpriseName)) {
      hql.append(" AND centerprisename LIKE '%").append(enterpriseName).append("%'");
      countHql.append(" AND centerprisename LIKE '%").append(enterpriseName).append("%'");
    }

    if (StringUtils.isNotBlank(page.getOrder())) {
      hql.append(" ORDER BY ");
      hql.append(page.getSort()).append(" ").append(page.getOrder());
    }

    long count = getDao().count(countHql.toString(), new Object[0]).longValue();

    List list = getDao().find(page.getPage(), page.getRows(), hql.toString(), new Object[0]);
    return new DataGrid(count, list);
  }

  public void updateCheckEnterprise(String ids, String status, String remark, Long curUserId)
  {
    String hql = "UPDATE EEnterpriseinfomodifytemp SET cauditstatus=?,cauditopinion=?,nauditid=? WHERE nenterpriseid in (" + ids + ")";
    getDao().execute(hql, new Object[] { status, remark, curUserId });

    if ("1".equals(status))
      for (String entId : ids.split(","))
        if (StringUtils.isNotBlank(entId)) {
          EEnterpriseinfomodifytemp entInfoTemp = (EEnterpriseinfomodifytemp)this.enterpriseinfomodifytempDao.get(EEnterpriseinfomodifytemp.class, Long.valueOf(Long.parseLong(entId)));
          EEnterpriseinfo entInfo = (EEnterpriseinfo)this.enterpriseinfoDao.get("FROM EEnterpriseinfo WHERE corganizationcode=?", new Object[] { entInfoTemp.getCorganizationcode() });
          if (entInfo == null) {
            entInfo = new EEnterpriseinfo();
          }
          try
          {
            Long entInfoId = entInfo.getNenterpriseid();
            PropertyUtils.copyProperties(entInfo, entInfoTemp);

            if (entInfoId != null){
              entInfo.setNenterpriseid(entInfoId);
              //正式表中的企业ID不为空，说明此处理为修改操作，此时企业的多联系人数量可能发生了变化，这时就把原来的联系人全部删除，把修改时新增的联系人添加过来
              List<EnterpriseLinkPerson> plist = enterpriseLinkPersonDao.find(" from EnterpriseLinkPerson where enterpriseId=?", entInfoId);
              if(plist!=null && plist.size()>0){
              	for(int i=0;i<plist.size();i++){
              		EnterpriseLinkPerson  p = plist.get(i);
              		enterpriseLinkPersonDao.delete(p);
              	}
              }
            }else {
              entInfo.setNenterpriseid(null);
            }

            entInfo.setDaudittime(new Date());
            this.enterpriseinfoDao.saveOrUpdate(entInfo);

            Long entInfoTempId = entInfoTemp.getNenterpriseid();
            entInfoId = entInfo.getNenterpriseid();
            List<Annex> entInfoTempAnnexList = findAnnexByTableAndObjId("E_ENTERPRISEINFOMODIFYTEMP", entInfoTempId);
            if ((entInfoTempAnnexList != null) && (entInfoTempAnnexList.size() > 0)) {
              for (Annex entInfoTempAnnex : entInfoTempAnnexList) {
                Annex entInfoAnnex = new Annex();
                PropertyUtils.copyProperties(entInfoAnnex, entInfoTempAnnex);
                entInfoAnnex.setId(null);
                entInfoAnnex.setObjectTable("E_ENTERPRISEINFO");
                
                entInfoAnnex.setObjectId(entInfoId);
                this.annexDao.save(entInfoAnnex);

                entInfoTempAnnex.setCvalid("0");
                this.annexDao.update(entInfoTempAnnex);
              }

            }

            MMember member = (MMember)this.memberDao.get("FROM MMember WHERE nenterpriseid=?", new Object[] { entInfoTempId });
            if (member != null) {
              member.setNenterpriseid(entInfoId);
              this.memberDao.update(member);
            }
            
            //审核通过后，通过临时表的企业ID得到联系人数组，然后把联系人的企业ID修改为正式表的企业ID
            List<EnterpriseLinkPerson> plist = enterpriseLinkPersonDao.find(" from EnterpriseLinkPerson where enterpriseId=?", entInfoTemp.getNenterpriseid());
            if(plist!=null && plist.size()>0){
            	for(int i=0;i<plist.size();i++){
            		EnterpriseLinkPerson  p = plist.get(i);
            		p.setEnterpriseId(entInfo.getNenterpriseid());
            		enterpriseLinkPersonDao.update(p);
            	}
            }
            

            entInfoTemp.setCvalid("0");
            getDao().update(entInfoTemp);
          } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("对象COPY出错:" + ex.getMessage(), ex);
          }
        }
  }

  public DataGrid listUncheckProduct(RequestPage page, String enterpriseName, String productName, String certificateNO, String certificationUnit, String status)
  {
    StringBuffer hql = new StringBuffer("FROM EEnterpriseproductmodifytemp where cauditstatus=" + status + " AND cvalid=1 ");
    StringBuffer countHql = new StringBuffer("select count(*) from EEnterpriseproductmodifytemp where cauditstatus=" + status + " AND cvalid=1");

    if (StringUtils.isNotBlank(enterpriseName)) {
      StringBuffer enterpriseSearchHql = new StringBuffer("FROM EEnterpriseinfomodifytemp where cvalid=1 ");
      enterpriseSearchHql.append("AND centerprisename LIKE '%").append(enterpriseName).append("%' ");

      List enterpriseList = getDao().find(enterpriseSearchHql.toString(), new Object[0]);
      if ((enterpriseList != null) && (!enterpriseList.isEmpty())) {
        int enterpriseListSize = enterpriseList.size();
        hql.append(" AND nenterpriseid IN(");
        countHql.append(" AND nenterpriseid IN(");
        for (int i = 0; i < enterpriseListSize; i++) {
          EEnterpriseinfomodifytemp entTemp = (EEnterpriseinfomodifytemp)enterpriseList.get(i);
          if (i == enterpriseListSize - 1) {
            hql.append(entTemp.getNenterpriseid());
            countHql.append(entTemp.getNenterpriseid());
          } else {
            hql.append(entTemp.getNenterpriseid()).append(",");
            countHql.append(entTemp.getNenterpriseid()).append(",");
          }
        }
        hql.append(") ");
        countHql.append(") ");
      } else {
        hql.append(" AND nenterpriseid IN(0)");
        countHql.append(" AND nenterpriseid IN(0)");
      }

    }

    if (StringUtils.isNotBlank(productName)) {
      hql.append(" AND cproductname LIKE '%").append(productName).append("%'");
      countHql.append(" AND cproductname LIKE '%").append(productName).append("%'");
    }

    if (StringUtils.isNotBlank(certificateNO)) {
      hql.append(" AND ccertificateno LIKE '%").append(certificateNO).append("%'");
      countHql.append(" AND ccertificateno LIKE '%").append(certificateNO).append("%'");
    }

    if (StringUtils.isNotBlank(certificationUnit)) {
      hql.append(" AND ccertificationunit LIKE '%").append(certificationUnit).append("%'");
      countHql.append(" AND ccertificationunit LIKE '%").append(certificationUnit).append("%'");
    }

    if (StringUtils.isNotBlank(page.getOrder())) {
      hql.append(" ORDER BY ");
      hql.append(page.getSort()).append(" ").append(page.getOrder());
    }

    long count = getDao().count(countHql.toString(), new Object[0]).longValue();

    List<EEnterpriseproductmodifytemp> list = this.sessionFactory.getCurrentSession().createQuery(hql.toString())
      .setFirstResult((page.getPage() - 1) * page.getRows())
      .setMaxResults(page.getRows()).list();

    List productInfoList = new ArrayList();
    for (EEnterpriseproductmodifytemp product : list) {
      ProductVO pvo = new ProductVO();
      try {
        PropertyUtils.copyProperties(pvo, product);
      } catch (Exception e) {
        logger.error("listUncheckProduct error!", e);
      }
      EEnterpriseinfo enterprise = (EEnterpriseinfo)this.enterpriseinfoDao.get(EEnterpriseinfo.class, product.getNenterpriseid());
      if (enterprise != null) {
        pvo.setEnterpriseName(enterprise.getCenterprisename());
      }
      productInfoList.add(pvo);
    }
    return new DataGrid(count, productInfoList);
  }

  public void updateCheckProduct(String ids, String status, String remark)
  {
    String hql = "UPDATE EEnterpriseproductmodifytemp SET cauditstatus=?,cauditopinion=? WHERE nentproid in (" + 
      ids + ")";
    getDao().execute(hql, new Object[] { status, remark });
    if ("1".equals(status))
      for (String etid : ids.split(","))
        if (StringUtils.isNotBlank(etid)) {
          EEnterpriseproductmodifytemp et = 
            (EEnterpriseproductmodifytemp)this.enterpriseproductmodifytempDao
            .get(EEnterpriseproductmodifytemp.class, 
            Long.valueOf(Long.parseLong(etid)));
          EEnterpriseproduct e = (EEnterpriseproduct)this.enterpriseproductDao.get(
            "FROM EEnterpriseproduct WHERE ccertificateno=? AND nenterpriseid=? ", new Object[] { 
            et.getCcertificateno(), et.getNenterpriseid() });
          Long eid = null;
          if (e == null) {
            e = new EEnterpriseproduct();
          }
          eid = e.getNentproid();
          try {
            PropertyUtils.copyProperties(e, et);
            e.setNentproid(eid);
            this.enterpriseproductDao.saveOrUpdate(e);
          } catch (Exception ec) {
            logger.error("对象COPY出错", ec);
          }

          this.enterpriseproductmodifytempDao.delete(et);
        }
  }

  public DataGrid listUncheckQualification(RequestPage page, String enterpriseName, String certificateName, String certificateNO, String certificationUnit, String status)
  {
    StringBuffer hql = new StringBuffer("FROM EQualificationmodifytemp where cauditstatus=" + status + " AND cvalid=1 ");
    StringBuffer countHql = new StringBuffer("select count(*) from EQualificationmodifytemp where cauditstatus=" + status + " AND cvalid=1");

    if (StringUtils.isNotBlank(enterpriseName)) {
      StringBuffer enterpriseSearchHql = new StringBuffer("FROM EEnterpriseinfomodifytemp where cvalid=1 ");
      enterpriseSearchHql.append("AND centerprisename LIKE '%").append(enterpriseName).append("%' ");

      List enterpriseList = getDao().find(enterpriseSearchHql.toString(), new Object[0]);
      if ((enterpriseList != null) && (!enterpriseList.isEmpty())) {
        int enterpriseListSize = enterpriseList.size();
        hql.append(" AND nenterpriseid IN(");
        countHql.append(" AND nenterpriseid IN(");
        for (int i = 0; i < enterpriseListSize; i++) {
          EEnterpriseinfomodifytemp entTemp = (EEnterpriseinfomodifytemp)enterpriseList.get(i);
          if (i == enterpriseListSize - 1) {
            hql.append(entTemp.getNenterpriseid());
            countHql.append(entTemp.getNenterpriseid());
          } else {
            hql.append(entTemp.getNenterpriseid()).append(",");
            countHql.append(entTemp.getNenterpriseid()).append(",");
          }
        }
        hql.append(") ");
        countHql.append(") ");
      } else {
        hql.append(" AND nenterpriseid IN(0)");
        countHql.append(" AND nenterpriseid IN(0)");
      }

    }

    if (StringUtils.isNotBlank(certificateName)) {
      hql.append(" AND ccertificatename LIKE '%").append(certificateName).append("%'");
      countHql.append(" AND ccertificatename LIKE '%").append(certificateName).append("%'");
    }

    if (StringUtils.isNotBlank(certificateNO)) {
      hql.append(" AND ccertificateno LIKE '%").append(certificateNO).append("%'");
      countHql.append(" AND ccertificateno LIKE '%").append(certificateNO).append("%'");
    }

    if (StringUtils.isNotBlank(certificationUnit)) {
      hql.append(" AND ccertificationunit LIKE '%").append(certificationUnit).append("%'");
      countHql.append(" AND ccertificationunit LIKE '%").append(certificationUnit).append("%'");
    }

    if (StringUtils.isNotBlank(page.getOrder())) {
      hql.append(" ORDER BY ");
      hql.append(page.getSort()).append(" ").append(page.getOrder());
    }

    long count = getDao().count(countHql.toString(), new Object[0]).longValue();

    List<EQualificationmodifytemp> list = this.sessionFactory.getCurrentSession().createQuery(hql.toString())
      .setFirstResult((page.getPage() - 1) * page.getRows()).setMaxResults(page.getRows()).list();

    List objList = new ArrayList();
    if ((list != null) && (!list.isEmpty())) {
      for (EQualificationmodifytemp obj : list) {
        QualificationVO pvo = new QualificationVO();
        try {
          PropertyUtils.copyProperties(pvo, obj);
        } catch (Exception e) {
          logger.error("listUncheckProduct error!", e);
        }
        EEnterpriseinfo enterprise = (EEnterpriseinfo)this.enterpriseinfoDao.get(EEnterpriseinfo.class, obj.getNenterpriseid());
        if (enterprise != null) {
          pvo.setEnterpriseName(enterprise.getCenterprisename());
        }
        objList.add(pvo);
      }
    }

    return new DataGrid(count, objList);
  }

  public void updateCheckQualification(String ids, String status, String remark)
  {
    String hql = "UPDATE EQualificationmodifytemp SET cauditstatus=?,cauditopinion=? WHERE nquaid in (" + 
      ids + ")";
    getDao().execute(hql, new Object[] { status, remark });
    if ("1".equals(status))
      for (String etid : ids.split(","))
        if (StringUtils.isNotBlank(etid)) {
          EQualificationmodifytemp et = 
            (EQualificationmodifytemp)this.qualificationmodifytempDao
            .get(EQualificationmodifytemp.class, 
            Long.valueOf(Long.parseLong(etid)));
          EQualification e = (EQualification)this.qualificationDao.get(
            "FROM EQualification WHERE ccertificateno=? AND nenterpriseid=? ", new Object[] { 
            et.getCcertificateno(), et.getNenterpriseid() });
          Long eid = null;
          if (e == null) {
            e = new EQualification();
          }
          eid = e.getNquaid();
          try {
            PropertyUtils.copyProperties(e, et);
            e.setNquaid(eid);
            this.qualificationDao.saveOrUpdate(e);

            Annex annex = (Annex)this.annexDao.get("FROM Annex WHERE objectTable=? AND objectId=? ", new Object[] { "E_QUALIFICATION", Long.valueOf(Long.parseLong(etid)) });
            if (annex != null) {
              annex.setObjectId(e.getNquaid());
              this.annexDao.update(annex);
            }

            this.qualificationmodifytempDao.delete(et);
          } catch (Exception ec) {
            logger.error("对象COPY出错", ec);
          }
        }
  }

  public DataGrid listUncheckIntegritystyle(RequestPage page, String enterpriseName, String userName, String status)
  {
    List list = null;
    Long count = Long.valueOf(0L);
    Session session = this.sessionFactory.getCurrentSession();
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT i.nintstyleid, i.cproductids, i.dcreatetime, ");
    sql.append(" cast(i.cauditstatus as char(50)) cauditstatus, ");
    sql.append(" cast(ei.centerprisename as char(50)) centerprisename, ");
    sql.append(" cast(mb.cusername as char(50)) cusername  ");
    sql.append("FROM f_integritystyle i ");
    sql.append(" join e_enterpriseinfo ei ");
    sql.append(" ON ei.nenterpriseid = i.nenterpriseid ");
    sql.append(" join m_member mb ");
    sql.append(" ON i.nenterpriseid = mb.nenterpriseid ");
    sql.append("WHERE i.cvalid = 1 AND i.cauditopinion='" + status + "' ");

    if (StringUtils.isNotBlank(enterpriseName)) {
      sql.append(" AND ei.centerprisename like '%").append(enterpriseName).append("%' ");
    }

    if (StringUtils.isNotBlank(userName)) {
      sql.append(" AND mb.cusername like '%").append(userName).append("%' ");
    }

    sql.append(" ORDER BY i.dcreatetime desc");

    if (StringUtils.isNotBlank(page.getOrder())) {
      sql.append(", ");
      sql.append(page.getSort()).append(" ").append(page.getOrder());
    }

    StringBuffer sqlStrCnt = new StringBuffer(sql);
    if (page != null) {
      sqlStrCnt.replace(7, sqlStrCnt.indexOf("FROM") - 1, "COUNT(*)");
      BigInteger cnt = (BigInteger)session.createSQLQuery(
        sqlStrCnt.toString()).uniqueResult();
      count = Long.valueOf(cnt.longValue());
      logger.info("total count: " + count);
      list = session
        .createSQLQuery(sql.toString())
        .setFirstResult((page.getPage() - 1) * page.getRows())
        .setMaxResults(page.getRows())
        .setResultTransformer(
        Transformers.aliasToBean(IntegritystyleView.class))
        .list();
    }
    return new DataGrid(count.longValue(), list);
  }

  public void updateCheckIntegritystyle(String ids, Long nauditid, String status, String remark)
  {
    String hql = "UPDATE FIntegritystyle SET nauditid =?,daudittime=?,cauditstatus=?,cauditopinion=? WHERE nintstyleid in (" + ids + ")";
    getDao().execute(hql, new Object[] { nauditid, new Date(), status, remark });
  }

  protected BaseDao<EEnterpriseinfomodifytemp> getDao()
  {
    return this.enterpriseinfomodifytempDao;
  }
}