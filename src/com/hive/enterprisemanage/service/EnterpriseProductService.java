package com.hive.enterprisemanage.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hive.common.entity.Annex;
import com.hive.common.entity.ProductCategoryCode;
import com.hive.common.service.AnnexService;
import com.hive.common.service.ProductCategoryCodeService;
import com.hive.enterprisemanage.entity.EEnterpriseproduct;
import com.hive.enterprisemanage.model.EEnterpriseproductVo;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class EnterpriseProductService extends BaseService<EEnterpriseproduct>
{

  @Resource
  private BaseDao<EEnterpriseproduct> EEnterpriseproductDao;

  @Resource
  private ProductCategoryCodeService productCategoryCodeService;

  @Resource
  private AnnexService annexService;

  protected BaseDao<EEnterpriseproduct> getDao()
  {
    return this.EEnterpriseproductDao;
  }

  public DataGrid datagrid(RequestPage page, Long entInfoId)
    throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
  {
    StringBuilder hql = new StringBuilder();
    hql.append("FROM ").append(getEntityName()).append(" WHERE cvalid = ").append("1");
    hql.append(" AND nenterpriseid = ").append(entInfoId);
    if (!StringUtils.isEmpty(page.getSort())) {
      hql.append(" ORDER BY ").append(page.getSort()).append(" ").append(page.getOrder());
    }

    StringBuilder countHql = new StringBuilder();
    countHql.append("select count(*) FROM ").append(getEntityName())
      .append(" WHERE cvalid = ")
      .append("1")
      .append(" AND nenterpriseid = ").append(entInfoId);
    long count = getDao().count(countHql.toString(), new Object[0]).longValue();
    List<EEnterpriseproduct> productList = getDao().find(page.getPage(), page.getRows(), hql.toString(), new Object[0]);

    List newProductList = new ArrayList();
    for (EEnterpriseproduct product : productList) {
      EEnterpriseproductVo productVo = new EEnterpriseproductVo();
      PropertyUtils.copyProperties(productVo, product);

      Annex annex = this.annexService.getAnnexByType("E_ENTERPRISEPRODUCT", product.getNentproid().toString());
      productVo.setAnnex(annex);

      String proCatCode = product.getCprocatcode();
      if (StringUtils.isNotBlank(proCatCode))
      {
        String[] proCatCodeArray = proCatCode.split("_");
        if (proCatCodeArray.length > 0) {
          String realCode = proCatCodeArray[(proCatCodeArray.length - 1)];

          ProductCategoryCode productCategory = this.productCategoryCodeService.getProductCategoryByCode(realCode);
          if (productCategory != null) {
            productVo.setCprocatname(productCategory.getProCatName());
            newProductList.add(productVo);
          }
        }

      }

    }

    return new DataGrid(count, newProductList);
  }

  public DataGrid datagrid2(RequestPage page, String cproductids)
  {
    StringBuilder hql = new StringBuilder();
    hql.append("FROM ").append(getEntityName()).append(" WHERE cvalid = ").append("1");
    hql.append(" AND nentproid in (").append(cproductids).append(")");
    if (!StringUtils.isEmpty(page.getSort())) {
      hql.append(" ORDER BY ").append(page.getSort()).append(" ").append(page.getOrder());
    }

    StringBuilder countHql = new StringBuilder();
    countHql.append("select count(*) FROM ").append(getEntityName())
      .append(" WHERE cvalid = ")
      .append("1")
      .append(" AND nentproid in (").append(cproductids).append(")");
    long count = getDao().count(countHql.toString(), new Object[0]).longValue();
    List<EEnterpriseproduct> productList = getDao().find(page.getPage(), page.getRows(), hql.toString(), new Object[0]);

    List newProductList = new ArrayList();
    for (EEnterpriseproduct product : productList) {
      EEnterpriseproductVo productVo = new EEnterpriseproductVo();
      try {
        PropertyUtils.copyProperties(productVo, product);
      } catch (Exception e) {
        e.printStackTrace();
      }

      String proCatCode = product.getCprocatcode();
      if (StringUtils.isNotBlank(proCatCode))
      {
        String[] proCatCodeArray = proCatCode.split("_");
        if (proCatCodeArray.length > 0) {
          String realCode = proCatCodeArray[(proCatCodeArray.length - 1)];

          ProductCategoryCode productCategory = this.productCategoryCodeService.getProductCategoryByCode(realCode);
          if (productCategory != null) {
            productVo.setCprocatname(productCategory.getProCatName());
            newProductList.add(productVo);
          }
        }
      }

    }

    return new DataGrid(count, newProductList);
  }
}