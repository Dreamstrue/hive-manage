package com.hive.common.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hive.common.entity.ProductCategoryCode;

import dk.dao.BaseDao;
import dk.service.BaseService;

@Service
public class ProductCategoryCodeService extends BaseService<ProductCategoryCode>
{

  @Resource
  private BaseDao<ProductCategoryCode> productCategoryDao;

  protected BaseDao<ProductCategoryCode> getDao()
  {
    return this.productCategoryDao;
  }

  public List<ProductCategoryCode> getProductCategory(String parentCode)
  {
    StringBuffer sb = new StringBuffer(" FROM " + getEntityName());
    sb.append(" WHERE 1=1 AND cvalid = '1' ");
    if (StringUtils.isNotBlank(parentCode))
      sb.append(" AND proCatCodeP = '").append(parentCode).append("' ");
    else {
      sb.append(" AND proCatCodeP = '999999999' ");
    }
    sb.append(" ORDER BY isortorder ASC ");
    List list = this.productCategoryDao.find(sb.toString(), new Object[0]);
    return list;
  }

  public ProductCategoryCode getProductCategoryByCode(String code)
  {
    StringBuffer sb = new StringBuffer(" FROM " + getEntityName());
    sb.append(" WHERE 1=1 AND cvalid = '1' ");
    if (StringUtils.isNotBlank(code)) {
      sb.append(" AND proCatCode = '").append(code).append("' ");
    }
    List list = this.productCategoryDao.find(sb.toString(), new Object[0]);
    if ((list != null) && (list.size() > 0)) {
      return (ProductCategoryCode)list.get(0);
    }
    return null;
  }
}