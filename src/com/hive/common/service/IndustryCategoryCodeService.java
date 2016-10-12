package com.hive.common.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hive.common.entity.IndustryCategoryCode;

import dk.dao.BaseDao;
import dk.service.BaseService;

@Service
public class IndustryCategoryCodeService extends BaseService<IndustryCategoryCode>
{

  @Resource
  private BaseDao<IndustryCategoryCode> industryCategoryCodeDao;

  protected BaseDao<IndustryCategoryCode> getDao()
  {
    return this.industryCategoryCodeDao;
  }

  public List<IndustryCategoryCode> getIndustryCategory(String parentCode)
  {
    StringBuffer sb = new StringBuffer(" FROM " + getEntityName());
    sb.append(" WHERE 1=1 AND cvalid = '").append("1").append("'");

    if (StringUtils.isNotBlank(parentCode))
      sb.append(" AND indCatCodeP = '").append(parentCode).append("'");
    else {
      sb.append(" AND indCatCodeP = '999999999'");
    }
    sb.append(" ORDER BY isortorder ASC ");
    List list = this.industryCategoryCodeDao.find(sb.toString(), new Object[0]);
    return list;
  }

  public IndustryCategoryCode getIndustryCategoryByCode(String code)
  {
    StringBuffer sb = new StringBuffer(" FROM " + getEntityName());
    sb.append(" WHERE 1=1 AND cvalid = '").append("1").append("'");
    if (StringUtils.isNotBlank(code)) {
      sb.append(" AND indCatCode = '").append(code).append("'");
    }
    List list = this.industryCategoryCodeDao.find(sb.toString(), new Object[0]);
    if ((list != null) && (list.size() > 0)) {
      return (IndustryCategoryCode)list.get(0);
    }
    return null;
  }
}