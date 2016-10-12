package com.hive.enterprisemanage.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.enterprisemanage.entity.EEnterpriseinfo;
import com.hive.membermanage.entity.MMember;
import com.hive.membermanage.service.MembermanageService;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class EnterpriseInfoService extends BaseService<EEnterpriseinfo>
{

  @Resource
  private BaseDao<EEnterpriseinfo> eEnterpriseinfoDao;

  @Resource
  private MembermanageService membermanageService;

  protected BaseDao<EEnterpriseinfo> getDao()
  {
    return this.eEnterpriseinfoDao;
  }

  public DataGrid datagrid(RequestPage page, String entName, String orgCode, String province, String city, String district, String ccomtypcode, String cindcatcode, String legal, String contractAddress)
  {
    StringBuilder hql = new StringBuilder();

    StringBuilder countHql = new StringBuilder();

    hql.append("FROM ").append(getEntityName()).append(" WHERE cvalid = ").append("1");
    countHql.append("select count(*) FROM ").append(getEntityName()).append(" WHERE cvalid = ").append("1");

    if (StringUtils.isNotBlank(entName)) {
      hql.append(" AND centerprisename LIKE '%").append(entName).append("%'");
      countHql.append(" AND centerprisename LIKE '%").append(entName).append("%'");
    }

    if (StringUtils.isNotBlank(orgCode)) {
      hql.append(" AND corganizationcode LIKE '%").append(orgCode).append("%'");
      countHql.append(" AND corganizationcode LIKE '%").append(orgCode).append("%'");
    }

    if (StringUtils.isNotBlank(province)) {
      hql.append(" AND cprovincecode = '").append(province).append("'");
      countHql.append(" AND cprovincecode = '").append(province).append("'");
    }

    if (StringUtils.isNotBlank(city)) {
      hql.append(" AND ccitycode = '").append(city).append("'");
      countHql.append(" AND ccitycode = '").append(city).append("'");
    }

    if (StringUtils.isNotBlank(district)) {
      hql.append(" AND cdistrictcode = '").append(district).append("'");
      countHql.append(" AND cdistrictcode = '").append(district).append("'");
    }

    if (StringUtils.isNotBlank(ccomtypcode)) {
      hql.append(" AND ccomtypcode = '").append(ccomtypcode).append("'");
      countHql.append(" AND ccomtypcode = '").append(ccomtypcode).append("'");
    }

    if (StringUtils.isNotBlank(cindcatcode)) {
      hql.append(" AND cindcatcode = '").append(cindcatcode).append("'");
      countHql.append(" AND cindcatcode = '").append(cindcatcode).append("'");
    }

    if (StringUtils.isNotBlank(legal)) {
      hql.append(" AND clegal LIKE '%").append(legal).append("%'");
      countHql.append(" AND clegal LIKE '%").append(legal).append("%'");
    }

    if (StringUtils.isNotBlank(contractAddress)) {
      hql.append(" AND ccontractaddress LIKE '%").append(contractAddress).append("%'");
      countHql.append(" AND ccontractaddress LIKE '%").append(contractAddress).append("%'");
    }

    if (!StringUtils.isEmpty(page.getSort())) {
      hql.append(" ORDER BY ").append(page.getSort()).append(" ").append(page.getOrder());
    }

    long count = getDao().count(countHql.toString(), new Object[0]).longValue();
    List entInfoList = getDao().find(page.getPage(), page.getRows(), hql.toString(), new Object[0]);
    return new DataGrid(count, entInfoList);
  }

  public List<EEnterpriseinfo> checkByName(String enterpriseName)
  {
    return getDao().find("from " + getEntityName() + " WHERE cvalid = 1 AND centerprisename=?", new Object[] { enterpriseName });
  }

  public List<EEnterpriseinfo> checkByOrganizationCode(String organizationCode)
  {
    return getDao().find("FROM " + getEntityName() + " WHERE cvalid = 1 AND corganizationcode=?", new Object[] { organizationCode });
  }

  public void delEntInfo(EEnterpriseinfo entInfo)
  {
    entInfo.setCvalid("0");
    getDao().update(entInfo);

    MMember member = this.membermanageService.getByEnterpriseId(entInfo.getNenterpriseid());
    if (member != null) {
      this.membermanageService.delMember(member);

      List paymentRecordsList = this.membermanageService.listPaymentRecordsOf(member.getNmemberid());
      if ((paymentRecordsList != null) && (paymentRecordsList.size() > 0))
        this.membermanageService.delPaymentRecords(paymentRecordsList);
    }
  }

  /**
   * 
   * @Description: 
   * @author yanghui 
   * @Created 2014-5-19
   * @return
   */
public List<EEnterpriseinfo> getAllEnterpriseInfo() {
	String hql = " from "+ getEntityName()+" where cvalid =? and cauditstatus =?  order by dcreatetime desc ";
	List<EEnterpriseinfo> list = eEnterpriseinfoDao.find(hql, new Object[]{SystemCommon_Constant.VALID_STATUS_1,SystemCommon_Constant.AUDIT_STATUS_1});
	return list;
}

/**
 * 
 * @Description:根据企业名称判断是否存在此企业 
 * @author zhaopengfei 
 * @Created 2016-5-19
 * @return
 */
public EEnterpriseinfo getEnterpriseInfoByName(String centerprisename) {
	String hql = " from "+ getEntityName()+" where cvalid =? and centerprisename =?  ";
	EEnterpriseinfo eenter = eEnterpriseinfoDao.get(hql, new Object[]{SystemCommon_Constant.VALID_STATUS_1,centerprisename});
	return eenter;
}
}