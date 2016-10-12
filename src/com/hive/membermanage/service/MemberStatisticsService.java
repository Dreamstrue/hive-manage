package com.hive.membermanage.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.hive.common.entity.City;
import com.hive.common.entity.District;
import com.hive.common.entity.Province;
import com.hive.membermanage.entity.MMember;
import com.hive.membermanage.model.MemberSearchBean;
import com.hive.membermanage.model.MemberStatistics;
import com.hive.util.DataUtil;
import com.hive.util.DateUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

@Service
public class MemberStatisticsService extends BaseService<MMember>
{

  @Resource
  private SessionFactory sessionFactory;

  @Resource
  private BaseDao<MMember> mberDao;

  @Resource
  private BaseDao<Province> provinceDao;

  @Resource
  private BaseDao<City> cityDao;

  @Resource
  private BaseDao<District> districtDao;

  protected BaseDao<MMember> getDao()
  {
    return this.mberDao;
  }

  public DataGrid dataGrid(RequestPage page, MemberSearchBean bean) {
    Session session = this.sessionFactory.getCurrentSession();

    String begintime = bean.getBegintime();
    String endtime = bean.getEndtime();
    String pcode = bean.getCprovincecode();
    String ccode = bean.getCcitycode();
    String dcode = bean.getCdistrictcode();
    String membertype = bean.getCmembertype();

    StringBuffer sb = new StringBuffer();
    sb.append("select ");
    String hql1 = ",cmembertype,count(*) as amount from M_MEMBER ";
    String whereStr = " where cmemberstatus='1' ";
    String groupStr = "";

    if (!DataUtil.isEmpty(pcode)) {
      if (!DataUtil.isEmpty(ccode)) {
        if (!DataUtil.isEmpty(dcode)) {
          sb.append("cprovincecode,ccitycode,cdistrictcode").append(hql1).append(whereStr).append(" and cprovincecode ='" + pcode + "' ");
          sb.append(" and ccitycode ='" + ccode + "' ");
          sb.append(" and cdistrictcode='" + dcode + "' ");
          groupStr = " cprovincecode,ccitycode,cdistrictcode,cmembertype";
        } else {
          sb.append("cprovincecode,ccitycode,cdistrictcode").append(hql1).append(whereStr).append(" and cprovincecode ='" + pcode + "' ");
          sb.append(" and ccitycode ='" + ccode + "' ");
          groupStr = "cprovincecode,ccitycode,cdistrictcode,cmembertype";
        }
      }
      else {
        sb.append("cprovincecode,ccitycode").append(hql1).append(whereStr).append(" and cprovincecode ='" + pcode + "' ");
        groupStr = " cprovincecode,ccitycode,cmembertype";
      }
    } else {
      sb.append("cprovincecode").append(hql1).append(whereStr);
      groupStr = " cprovincecode,cmembertype";
    }

    if (!DataUtil.isEmpty(begintime))
    {
      sb.append(" and STR_TO_DATE(dcreatetime,'%Y-%m-%d')>='" + begintime + "'");
    }
    else sb.append(" and STR_TO_DATE(dcreatetime,'%Y-%m-%d')>='" + DateUtil.beforeFirstDate() + "'");

    if (!DataUtil.isEmpty(endtime))
    {
      sb.append(" and STR_TO_DATE(dcreatetime,'%Y-%m-%d')<='" + endtime + "'");
    }
    if ((!DataUtil.isEmpty(membertype)) && 
      (!membertype.equals("2"))) {
      sb.append(" and cmembertype = '" + membertype + "' ");
    }

    sb.append(" group by ").append(groupStr);
    sb.append(" order by ").append(groupStr);

    StringBuffer sql = new StringBuffer();
    int firstNo = (page.getPage() - 1) * page.getRows() + 1;
    int maxNo = page.getPage() * page.getRows();
    sql.append(" select * from ( select t.*,rownum as rn from ( ").append(sb).append(" ) t ) where rn <=" + maxNo + " and rn >=" + firstNo);

    List qList = null;
    List list = new ArrayList();

    qList = session.createSQLQuery(sql.toString()).list();

    for (Iterator it = qList.iterator(); it.hasNext(); ) {
      MemberStatistics ms = new MemberStatistics();
      Object[] obj = (Object[])it.next();
      int size = obj.length;

      if (size == 4) {
        ms.setAreacode((String)obj[0]);
        ms.setAreaname(getProvinceName((String)obj[0]));
        ms.setCmembertype(obj[1].toString());
        ms.setAmount(Integer.valueOf(obj[2].toString()).intValue());

        list.add(ms);
      }
      if (size == 5) {
        String p = (String)obj[0];
        String c = (String)obj[1];

        ms.setAreacode(p + "-" + c);
        ms.setAreaname(getProvinceName(p) + "-" + getCityName(c));
        ms.setCmembertype(obj[2].toString());
        ms.setAmount(Integer.valueOf(obj[3].toString()).intValue());

        list.add(ms);
      }
      if (size == 6) {
        String p = (String)obj[0];
        String c = (String)obj[1];
        String d = (String)obj[2];
        ms.setAreacode(p + "-" + c + "-" + d);
        ms.setAreaname(getProvinceName(p) + "-" + getCityName(c) + "-" + getDistrictName(d));
        ms.setCmembertype(obj[3].toString());
        ms.setAmount(Integer.valueOf(obj[4].toString()).intValue());

        list.add(ms);
      }

    }

    StringBuffer counthql = new StringBuffer(sb);
    int num = session.createSQLQuery(counthql.toString()).list().size();
    return new DataGrid(num, list);
  }

  public String getProvinceName(String pcode)
  {
    String hql = "from Province where provinceCode=?";
    String pname = ((Province)this.provinceDao.find(hql, new Object[] { pcode }).get(0)).getProvinceName();
    return pname;
  }

  public String getCityName(String pcode)
  {
    String hql = "from City where cityCode=?";
    String cname = ((City)this.cityDao.find(hql, new Object[] { pcode }).get(0)).getCityName();
    return cname;
  }

  public String getDistrictName(String pcode)
  {
    String hql = "from District where districtCode=?";
    String pname = ((District)this.districtDao.find(hql, new Object[] { pcode }).get(0)).getDistrictName();
    return pname;
  }

  public DataGrid memberDatagrid(RequestPage page, String areacode, String cmembertype, String level, String begintime, String endtime)
  {
    StringBuffer sb = new StringBuffer();
    sb.append(" from " + getEntityName() + " where cmemberstatus='1' ");
    if (level.equals("province")) {
      sb.append(" and cprovincecode = ?");
    }
    if (level.equals("city")) {
      sb.append(" and ccitycode = ?");
    }
    if (level.equals("district")) {
      sb.append(" and cdistrictcode = ?");
    }
    if (!DataUtil.isEmpty(begintime))
      sb.append(" and STR_TO_DATE(dcreatetime,'%Y-%m-%d')>='" + begintime + "'");
    else {
      sb.append(" and STR_TO_DATE(dcreatetime,'%Y-%m-%d')>='" + DateUtil.beforeFirstDate() + "'");
    }
    if (!DataUtil.isEmpty(endtime)) {
      sb.append(" and STR_TO_DATE(dcreatetime,'%Y-%m-%d')<='" + endtime + "'");
    }
    sb.append(" and cmembertype = '" + cmembertype + "'");
    List list = getDao().find(page.getPage(), page.getRows(), sb.toString(), new Object[] { areacode });
    StringBuffer hql = new StringBuffer("select count(*)").append(sb);
    Long count = Long.valueOf(getDao().count(hql.toString(), new Object[] { areacode }).longValue());
    return new DataGrid(count.longValue(), list);
  }
}