package com.hive.interviewmanage.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.interviewmanage.entity.Interview;
import com.hive.interviewmanage.entity.InterviewContent;
import com.hive.interviewmanage.service.InterviewContentService;
import com.hive.interviewmanage.service.InterviewService;
import com.hive.util.DateUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

@Controller
@RequestMapping("/interviewContent")
public class InterviewContentController extends BaseController
{
  public static final String PREFIX = "interviewmanage/interviewContent";

  @Resource
  private InterviewContentService interviewContentService;

  @Resource
  private InterviewService interviewService;

  @RequestMapping("/manage")
  public String manage(Model model, @RequestParam("interviewId") String interviewId)
  {
    model.addAttribute("interviewId", interviewId);
    return "interviewmanage/interviewContent/manage";
  }
  @RequestMapping("/dataGrid")
  @ResponseBody
  public DataGrid dataGrid(RequestPage page, @RequestParam(value="keys", required=false, defaultValue="") String keys, @RequestParam("interviewId") long interviewId) {
    return this.interviewContentService.dataGrid(page, keys, interviewId);
  }

  @RequestMapping("/add")
  public String add(Model model, @RequestParam("interviewId") long interviewId) {
    Interview interview = (Interview)this.interviewService.get(Long.valueOf(interviewId));
    model.addAttribute("vo", interview);
    return "interviewmanage/interviewContent/add";
  }

  @RequestMapping("/insert")
  @ResponseBody
  public Map<String, Object> insert(InterviewContent interviewContent, HttpSession session) {
    interviewContent.setNcreateid((Long)session.getAttribute("userId"));
    interviewContent.setDcreatetime(DateUtil.getTimestamp());
    interviewContent.setCvalid("1");

    this.interviewContentService.save(interviewContent);

    return success("保存成功");
  }

  @RequestMapping("liveContent")
  public String liveContent(Model model, @RequestParam("interviewId") long interviewId)
  {
    model.addAttribute("interviewId", Long.valueOf(interviewId));
    return "interviewmanage/interviewContent/liveContent";
  }
  @RequestMapping("/getLiveContent")
  @ResponseBody
  public Map getLiveContent(@RequestParam("interviewId") long interviewId) {
    return success(this.interviewContentService.getLiveContent(interviewId));
  }
  @RequestMapping("/delete")
  @ResponseBody
  public Map<String, Object> delete(@RequestParam("id") String id) {
    if (StringUtils.isNotEmpty(id)) {
      this.interviewContentService.logicDelete(id);
      return success("删除成功！");
    }
    return error("请选择要删除的对象");
  }

  @RequestMapping("detail")
  public String detail(Model model, @RequestParam("interviewContentId") Long interviewContentId)
  {
    InterviewContent interviewContent = (InterviewContent)this.interviewContentService.get(interviewContentId);
    model.addAttribute("vo", interviewContent);
    Interview interview = (Interview)this.interviewService.get(interviewContent.getNintonlid());
    model.addAttribute("interviewSubject", interview.getCsubject());
    return "interviewmanage/interviewContent/detail";
  }
}