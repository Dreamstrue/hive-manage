package com.hive.votemanage.controller;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.votemanage.entity.SurveyInfo;
import com.hive.votemanage.service.SurveyInfoService;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

@Controller
@RequestMapping("/survey")
public class SurveyInfoController extends BaseController
{
  private final String PREFIX = "votemanage";

  @Resource
  private SurveyInfoService surveyInfoService;

  @ModelAttribute
  public void getSurveyInfo(@RequestParam(value="id", defaultValue="-1") Long id, Model model)
  {
    if ((id != null) && (id.longValue() != -1L))
      model.addAttribute("surveyInfo", this.surveyInfoService.get(id));
  }

  @RequestMapping("/toSurveyInfoList")
  public String toSurveyInfoList()
  {
    return PREFIX+"/surveyInfoList";
  }

  @RequestMapping("/listSurveyInfo")
  @ResponseBody
  public DataGrid listSurveyInfo(RequestPage page)
  {
    return this.surveyInfoService.datagrid(page);
  }

  @RequestMapping("/toSurveyInfoAdd")
  public String toSurveyInfoAdd()
  {
    return PREFIX+"/surveyInfoAdd";
  }

  @RequestMapping("/saveSurveyInfo")
  @ResponseBody
  public Map<String, Object> saveSurveyInfo(SurveyInfo surveyInfo, HttpSession session)
  {
    Long curUserId = (Long)session.getAttribute("userId");
    surveyInfo.setCreateId(curUserId);
    surveyInfo.setCreateTime(new Date());
    this.surveyInfoService.save(surveyInfo);
    return success("保存成功", surveyInfo);
  }

  @RequestMapping("/toSurveyInfoView")
  public String toSurveyInfoView(@RequestParam("surveyInfoId") Long surveyInfoId, Model model)
  {
    SurveyInfo surveyInfo = (SurveyInfo)this.surveyInfoService.get(surveyInfoId);
    model.addAttribute("surveyInfo", surveyInfo);
    return PREFIX+"/surveyInfoView";
  }

  @RequestMapping("/toSurveyInfoEdit")
  public String toSurveyInfoEdit(@RequestParam("surveyInfoId") Long surveyInfoId, Model model)
  {
    SurveyInfo surveyInfo = (SurveyInfo)this.surveyInfoService.get(surveyInfoId);
    model.addAttribute("surveyInfo", surveyInfo);
    return PREFIX+"/surveyInfoEdit";
  }

  @RequestMapping("/updateSurveyInfo")
  @ResponseBody
  public Map<String, Object> updateSurveyInfo(@ModelAttribute("surveyInfo") SurveyInfo surveyInfo, HttpSession session)
  {
    this.surveyInfoService.saveOrUpdate(surveyInfo);
    return success("操作成功");
  }

  @RequestMapping("/delSurveyInfo")
  @ResponseBody
  public Map<String, Object> delSurveyInfo(@RequestParam("surveyInfoId") Long surveyInfoId)
  {
    SurveyInfo surveyInfo = (SurveyInfo)this.surveyInfoService.get(surveyInfoId);
    if (surveyInfo != null) {
      surveyInfo.setValid("0");
      this.surveyInfoService.update(surveyInfo);
      return success("删除成功");
    }
    return error("删除失败");
  }
}