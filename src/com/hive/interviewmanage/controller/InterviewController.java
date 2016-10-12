package com.hive.interviewmanage.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hive.common.AnnexFileUpLoad;
import com.hive.common.entity.Annex;
import com.hive.common.service.AnnexService;
import com.hive.interviewmanage.entity.Interview;
import com.hive.interviewmanage.service.InterviewContentService;
import com.hive.interviewmanage.service.InterviewService;
import com.hive.util.DateUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

@Controller
@RequestMapping("/interview")
public class InterviewController extends BaseController
{
  public static final String PREFIX = "interviewmanage/interview";
  public static final String OBJECT_TABLE = "F_INTERVIEWONLINE";
  public static final String BUSINESS_DIR = "[06]MJFT";

  @Resource
  private InterviewService interviewService;

  @Resource
  private AnnexService annexService;

  @Resource
  private InterviewContentService interviewContentService;

  @RequestMapping("/manage")
  public String manage()
  {
    return "interviewmanage/interview/manage";
  }

  @RequestMapping("/dataGrid")
  @ResponseBody
  public DataGrid dataGrid(RequestPage page, @RequestParam(value="keys", required=false, defaultValue="") String keys) {
    return this.interviewService.dataGrid(page, keys);
  }

  @RequestMapping("/add")
  public String add()
  {
    return "interviewmanage/interview/add";
  }

  @RequestMapping("/insert")
  @ResponseBody
  public Map<String, Object> insert(Interview interview, HttpSession session, @RequestParam("cinterviewtime") String cinterviewtime, @RequestParam(value="imgfile", required=false) MultipartFile file)
  {
    String subject = interview.getCsubject();
    int size = this.interviewService.getInterviewByName(subject);
    if (size > 0) {
      return error("主题【" + subject + "】已存在");
    }

    List anlist = new ArrayList();

    if (file.getSize() > 0L) {
      anlist = AnnexFileUpLoad.uploadImageFile(file, session, new Long(0L), "F_INTERVIEWONLINE", "[06]MJFT", "image");
      String picpath = ((Annex)anlist.get(0)).getCfilepath();
      interview.setCsubjectpicpath(picpath);
    }

    interview.setDinterviewtime(DateUtil.format(cinterviewtime, "yyyy-MM-dd HH:mm:ss"));
    interview.setNcreateid((Long)session.getAttribute("userId"));
    interview.setDcreatetime(DateUtil.getTimestamp());
    interview.setCauditstatus("0");
    interview.setCvalid("1");

    this.interviewService.save(interview);

    this.annexService.saveAnnexList(anlist, String.valueOf(interview.getNintonlid()));

    return success("保存成功");
  }

  @RequestMapping("/edit")
  public String edit(Model model, @RequestParam("id") Long id)
  {
    model.addAttribute("vo", this.interviewService.get(id));

    List imgList = this.annexService.getAnnexInfoByObjectIdAndAnnexType(id);
    if (imgList.size() > 0) {
      model.addAttribute("img", imgList.get(0));
    }
    return "interviewmanage/interview/edit";
  }
  @RequestMapping("/update")
  @ResponseBody
  public Map<String, Object> update(Interview interview, HttpServletRequest request, HttpSession session, @RequestParam("imgfile") MultipartFile file, @RequestParam(value="ids", required=false, defaultValue="") String ids) {
    Interview interviewTemp = (Interview)this.interviewService.get(interview.getNintonlid());

    interview.setDinterviewtime(DateUtil.format(request.getParameter("cinterviewtime"), "yyyy-MM-dd HH:mm:ss"));
    interview.setNcreateid(interviewTemp.getNcreateid());
    interview.setDcreatetime(interviewTemp.getDcreatetime());
    interview.setNmodifyid((Long)session.getAttribute("userId"));
    interview.setDmodifytime(DateUtil.getTimestamp());
    interview.setCauditstatus("0");
    interview.setCvalid(interviewTemp.getCvalid());

    String imageExist = request.getParameter("imageExist");
    if (!imageExist.equals("yes"))
    {
      if (imageExist.equals("no"))
      {
        if (file.getSize() > 0L)
        {
          List list = AnnexFileUpLoad.uploadImageFile(file, session, interview.getNintonlid(), "F_INTERVIEWONLINE", "[06]MJFT", "image");
          String picpath = ((Annex)list.get(0)).getCfilepath();
          interview.setCsubjectpicpath(picpath);
          this.annexService.saveAnnexList(list, String.valueOf(interview.getNintonlid()));

          String virtualPid = request.getParameter("virtualPid");
          if (StringUtils.isNotEmpty(virtualPid)) {
            Annex nex = (Annex)this.annexService.get(Long.valueOf(virtualPid));
            nex.setCvalid("0");
            this.annexService.update(nex);
          }
        } else { return error("请选择上传主题图片"); }
      }
    }
    this.interviewService.update(interview);

    return success("修改成功");
  }

  @RequestMapping("detail")
  public String detail(Model model, @RequestParam("id") Long id, @RequestParam("opType") String opType) {
    model.addAttribute("vo", this.interviewService.get(id));
    model.addAttribute("opType", opType);

    List list = this.annexService.getAnnexInfoByObjectId(id,OBJECT_TABLE);
    model.addAttribute("annex", list);

    String liveContent = this.interviewContentService.getLiveContent(id.longValue());
    model.addAttribute("liveContent", liveContent);

    return "interviewmanage/interview/detail";
  }

  @RequestMapping("/approveAction")
  @ResponseBody
  public Map approveAction(HttpSession session, @RequestParam("id") Long interviewId, @RequestParam("opType") String opType, @RequestParam(value="auditOpinion", required=false, defaultValue="") String auditOpinion)
  {
    if (this.interviewService.auditInterview(interviewId.longValue(), opType.equals("success"), auditOpinion, ((Long)session.getAttribute("userId")).longValue())) {
      return success("审核成功");
    }
    return error("审核失败");
  }
  @RequestMapping("/delete")
  @ResponseBody
  public Map<String, Object> delete(@RequestParam("id") String id) {
    if (StringUtils.isNotEmpty(id)) {
      this.interviewService.logicDelete(id);
      return success("删除成功！");
    }
    return error("请选择要删除的对象");
  }

  @RequestMapping("/back")
  @ResponseBody
  public Map<String, Object> back(@RequestParam("id") String id) {
    if (StringUtils.isNotEmpty(id)) {
      Interview inte = (Interview)this.interviewService.get(Long.valueOf(id));
      inte.setCvalid("1");
      this.interviewService.update(inte);
      return success("恢复成功！");
    }
    return error("请选择需要删除的对象");
  }
}