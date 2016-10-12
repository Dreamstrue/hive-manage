package com.hive.membermanage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.membermanage.model.MemberSearchBean;
import com.hive.membermanage.service.MemberStatisticsService;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

@Controller
@RequestMapping({"/memberStat"})
public class MemberStatisticsController extends BaseController
{
  private static final String PREFIX = "membermanage/memberStatistics";

  @Resource
  private MemberStatisticsService mberService;

  @RequestMapping({"/manage"})
  public String manage()
  {
    return "membermanage/memberStatistics/memberStatistics";
  }
  @RequestMapping({"/datagrid"})
  @ResponseBody
  public DataGrid dataGrid(RequestPage page, MemberSearchBean bean) {
    return this.mberService.dataGrid(page, bean);
  }

  @RequestMapping({"/memberlist"})
  public String memberlist(Model model, @RequestParam("parames") String parames)
  {
    String[] s = parames.split(",");
    MemberSearchBean bean = new MemberSearchBean();
    bean.setAreacode(s[0]);
    bean.setCmembertype(s[1]);
    bean.setLevel(s[2]);
    if (s.length >= 4)
    {
      bean.setBegintime(s[3] == "" ? "" : s[3]);
    }
    else bean.setBegintime("");

    if (s.length >= 5)
    {
      bean.setEndtime(s[4] == "" ? "" : s[4]);
    }
    else bean.setEndtime("");

    model.addAttribute("vo", bean);
    return "membermanage/memberStatistics/memberlist";
  }

  @RequestMapping({"/memberDatagrid"})
  @ResponseBody
  public DataGrid memberDatagrid(RequestPage page, @RequestParam("areacode") String areacode, @RequestParam("cmembertype") String cmembertype, @RequestParam("level") String level, @RequestParam("begintime") String begintime, @RequestParam("endtime") String endtime) {
    return this.mberService.memberDatagrid(page, areacode, cmembertype, level, begintime, endtime);
  }
}