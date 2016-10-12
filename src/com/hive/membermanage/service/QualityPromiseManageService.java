package com.hive.membermanage.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.hive.common.entity.IndustryCategoryCode;
import com.hive.common.service.IndustryCategoryCodeService;
import com.hive.enterprisemanage.entity.EEnterpriseinfo;
import com.hive.enterprisemanage.entity.EEnterpriseinfomodifytemp;
import com.hive.enterprisemanage.service.EnterpriseInfoService;
import com.hive.membermanage.entity.QualityPromise;
import com.hive.membermanage.model.QualityPromiseBean;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class QualityPromiseManageService extends BaseService<QualityPromise>
{
  private static final Logger logger = Logger.getLogger(QualityPromiseManageService.class);

  @Resource
  private BaseDao<QualityPromise> qualityPromiseDao;

  @Resource
  private SessionFactory sessionFactory;

  @Resource
  private IndustryCategoryCodeService industryCategoryCodeService;

  @Resource
  private EnterpriseInfoService enterpriseInfoService;

  protected BaseDao<QualityPromise> getDao() { return this.qualityPromiseDao; }


  public DataGrid listUncheckQualityPromise(RequestPage page, String enterpriseName, String status)
  {
    StringBuffer hql = new StringBuffer("FROM QualityPromise where cauditstatus=" + status + " AND cvalid=1 ");
    StringBuffer countHql = new StringBuffer("select count(*) from QualityPromise where cauditstatus=" + status + " AND cvalid=1");

    if (StringUtils.isNotBlank(enterpriseName)) {
      StringBuffer enterpriseSearchHql = new StringBuffer("FROM EEnterpriseinfomodifytemp where cvalid=1 ");
      enterpriseSearchHql.append("AND centerprisename LIKE '%").append(enterpriseName).append("%' ");

      List enterpriseList = this.sessionFactory.getCurrentSession().createQuery(enterpriseSearchHql.toString()).list();
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

    if (StringUtils.isNotBlank(page.getOrder())) {
      hql.append(" ORDER BY ");
      hql.append(page.getSort()).append(" ").append(page.getOrder());
    }
    long count = getDao().count(countHql.toString(), new Object[0]).longValue();

    List<QualityPromise> list = this.sessionFactory.getCurrentSession()
      .createQuery(hql.toString()).setFirstResult(
      (page.getPage() - 1) * page.getRows()).setMaxResults(
      page.getRows()).list();
    List objList = new ArrayList();
    for (QualityPromise obj : list) {
      QualityPromiseBean pvo = new QualityPromiseBean();
      try {
        PropertyUtils.copyProperties(pvo, obj);
      } catch (Exception e) {
        logger.error("listUncheckProduct error!", e);
      }
      EEnterpriseinfo enterprise = (EEnterpriseinfo)this.enterpriseInfoService.get(obj.getNenterpriseid());
      IndustryCategoryCode industryCategoryCode = this.industryCategoryCodeService.getIndustryCategoryByCode(obj.getCindcatcode());
      if (industryCategoryCode != null) {
        pvo.setEnterpriseName(enterprise.getCenterprisename());
        pvo.setIndCatName(industryCategoryCode.getIndCatName());
      }
      objList.add(pvo);
    }
    return new DataGrid(count, objList);
  }

  public void updateCheckQualityPromise(String ids, Long auditId, String status, String remark)
  {
    String hql = "UPDATE " + getEntityName() + " SET nauditid=?,daudittime=?,cauditstatus=?,cauditopinion=? WHERE nquaproid in (" + ids + ")";
    getDao().execute(hql, new Object[] { auditId, new Date(), status, remark });
  }
}