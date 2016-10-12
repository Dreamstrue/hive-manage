package com.hive.contentmanage.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hive.common.AnnexFileUpLoad;
import com.hive.common.service.AnnexService;
import com.hive.contentmanage.entity.IndustryStandard;
import com.hive.contentmanage.service.IndustryStandardService;
import com.hive.util.DateUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

@Controller
@RequestMapping("industry")
public class IndustryStandardController extends BaseController
{
  private static final String PREFIX = "contentmanage/industrystandard";
  private static final String OBJECT_TABLE = "F_INDUSTRYSTANDARD";
  private static final String BUSINESS_DIR = "[04]HYBZ";

  @Resource
  private IndustryStandardService industryStandardService;

  @Resource
  private AnnexService annexService;

  @RequestMapping("manage")
  public String manage(Model model, @RequestParam("id") int id)
  {
    model.addAttribute("pid", Integer.valueOf(id));
    return PREFIX+"/industryStandardManage";
  }

  @RequestMapping("allmanage")
  public String allmanage(Model model, @RequestParam("id") int id)
  {
    model.addAttribute("backId", Integer.valueOf(id));
    return PREFIX+"/industryStandardAllManage";
  }

  @RequestMapping("/dataGrid")
  @ResponseBody
  public DataGrid dataGrid(RequestPage page, @RequestParam(value="keys", required=false, defaultValue="") String keys, @RequestParam(value="menuid", required=false, defaultValue="") String menuid)
  {
    return this.industryStandardService.dataGrid(page, keys, menuid);
  }

  @RequestMapping("alladd")
  public String alladd(Model model, @RequestParam("id") int id)
  {
    model.addAttribute("pid", Integer.valueOf(id));
    return PREFIX+"/industryStandardAllAdd";
  }

  @RequestMapping("add")
  public String add(Model model, @RequestParam("id") int id) {
    model.addAttribute("pid", Integer.valueOf(id));
    return PREFIX+"/industryStandardAdd";
  }

  @RequestMapping("/insert")
  @ResponseBody
  public Map<String, Object> insert(IndustryStandard industry, HttpSession session, @RequestParam(value="file", required=false) MultipartFile[] files, @RequestParam(value="idString", required=false) String idString) {
    String title = industry.getTitle();
    int size = this.industryStandardService.getIndustryByName(title);
    if (size > 0) {
      return error("标准名称【" + title + "】已存在");
    }

    String filePath = AnnexFileUpLoad.writeContentToFile(industry.getContent(), BUSINESS_DIR, "","");
    industry.setContent(filePath);

    industry.setNcreateid((Long)session.getAttribute("userId"));
    Timestamp stamp = DateUtil.getTimestamp();
    industry.setDcreatetime(stamp);

    industry.setCauditstatus("0");
    industry.setCvalid("1");

    industry.setIdowncount(Long.valueOf(0L));
    this.industryStandardService.save(industry);

    Long createId = (Long)session.getAttribute("userId");
    this.annexService.updateAnnex(createId, industry.getId(), idString);

    return success("添加成功", industry);
  }

  @RequestMapping("edit")
  public String edit(Model model, @RequestParam("id") Long id, @RequestParam("opType") String opType, @RequestParam(value="backId", required=false, defaultValue="") String backId)
  {
    IndustryStandard in = (IndustryStandard)this.industryStandardService.get(id);
    byte[] bt = AnnexFileUpLoad.getContentFromFile(in.getContent());
    in.setContent(new String(bt));
    model.addAttribute("vo", in);
    model.addAttribute("opType", opType);
    if (backId.equals(""))
      model.addAttribute("backId", Integer.valueOf(0));
    else model.addAttribute("backId", backId);

    List list = this.annexService.getAnnexInfoByObjectId(id,OBJECT_TABLE);
    model.addAttribute("annex", list);
    return PREFIX+"/industryStandardEdit";
  }

  @RequestMapping("/update")
  @ResponseBody
  public Map<String, Object> update(IndustryStandard industry, HttpSession session, @RequestParam(value="file", required=false) MultipartFile[] files, @RequestParam(value="ids", required=false, defaultValue="") String ids, @RequestParam(value="idString", required=false) String idString)
  {
    IndustryStandard policy = (IndustryStandard)this.industryStandardService.get(industry.getId());

    int size = this.annexService.getAllValidAnnexSize(industry.getId(), "");

    boolean sign = AnnexFileUpLoad.checkHasAnnex(industry.getChasannex(), idString, size);
    if (!sign)
      policy.setChasannex("0");
    else policy.setChasannex(industry.getChasannex());

    Long id = industry.getId();
    String title = industry.getTitle();
    boolean flag = this.industryStandardService.getIndustryByNameAndId(id, title);
    if (!flag) {
      return error("标准名称【" + title + "】已存在");
    }

    policy.setNmodifyid((Long)session.getAttribute("userId"));
    Timestamp stamp = DateUtil.getTimestamp();
    policy.setNmodifytime(stamp);

    policy.setTitle(industry.getTitle());

    policy.setCdownload(industry.getCdownload());

    String oldPath = policy.getContent();
    String filePath = AnnexFileUpLoad.writeContentToFile(industry.getContent(), BUSINESS_DIR, oldPath,"");
    policy.setContent(filePath);

    policy.setCauditstatus("0");

    if (industry.getChasannex().equals("0")) {
      this.annexService.setAnnexIsValidByObjectId(industry.getId(), "");
    }
    if (!ids.equals("")) {
      String[] arr = ids.split(",");

      if ((arr.length == size) && (StringUtils.isEmpty(idString))) {
        policy.setChasannex("0");
      }
      for (int i = 0; i < arr.length; i++) {
        this.annexService.updateAnnexIsValidById(Long.valueOf(arr[i]), "");
      }
    }

    Long createId = (Long)session.getAttribute("userId");
    this.annexService.updateAnnex(createId, industry.getId(), idString);

    this.industryStandardService.update(policy);

    return success("修改成功", policy);
  }

  @RequestMapping("detail")
  public String detail(Model model, @RequestParam("id") Long id, @RequestParam(value="backId", required=false, defaultValue="") String backId)
  {
    IndustryStandard in = (IndustryStandard)this.industryStandardService.get(id);
    byte[] bt = AnnexFileUpLoad.getContentFromFile(in.getContent());
    in.setContent(new String(bt));
    model.addAttribute("vo", in);
    if (backId.equals(""))
      model.addAttribute("backId", Integer.valueOf(0));
    else model.addAttribute("backId", backId);

    List list = this.annexService.getAnnexInfoByObjectId(id,OBJECT_TABLE);
    model.addAttribute("annex", list);
    return PREFIX+"/industryStandardDetails";
  }

  @RequestMapping("approve")
  public String approve(Model model, @RequestParam("id") Long id, @RequestParam("opType") String opType, @RequestParam(value="backId", required=false, defaultValue="") String backId)
  {
    IndustryStandard in = (IndustryStandard)this.industryStandardService.get(id);
    byte[] bt = AnnexFileUpLoad.getContentFromFile(in.getContent());
    in.setContent(new String(bt));
    model.addAttribute("vo", in);
    model.addAttribute("opType", opType);
    if (backId.equals(""))
      model.addAttribute("backId", Integer.valueOf(0));
    else model.addAttribute("backId", backId);

    List list = this.annexService.getAnnexInfoByObjectId(id,OBJECT_TABLE);
    model.addAttribute("annex", list);
    return PREFIX+"/industryStandardEdit";
  }

  @RequestMapping("/successAction")
  @ResponseBody
  public Map<String, Object> successAction(IndustryStandard policy, HttpSession session, @RequestParam(value="file", required=false) MultipartFile[] files)
  {
    IndustryStandard bean = (IndustryStandard)this.industryStandardService.get(policy.getId());

    Long id = policy.getId();
    String title = policy.getTitle();
    boolean flag = this.industryStandardService.getIndustryByNameAndId(id, title);
    if (!flag) {
      return error("标准名称【" + title + "】已存在");
    }

    bean.setNmodifyid((Long)session.getAttribute("userId"));
    Timestamp stamp = DateUtil.getTimestamp();
    bean.setNmodifytime(stamp);

    bean.setNauditid((Long)session.getAttribute("userId"));
    bean.setDaudittime(stamp);

    bean.setTitle(policy.getTitle());
    bean.setContent(policy.getContent());
    bean.setCdownload(policy.getCdownload());

    bean.setCauditstatus("1");
    bean.setCvalid("1");

    bean.setChasannex(policy.getChasannex());

    bean.setIdowncount(Long.valueOf(0L));
    bean.setAuditOpinion(policy.getAuditOpinion());
    this.industryStandardService.update(bean);
    List nexlist = AnnexFileUpLoad.uploadFile(files, session, policy.getId(), OBJECT_TABLE, BUSINESS_DIR, null);
    this.annexService.saveAnnexList(nexlist, null);
    return success("审核完成", bean);
  }

  @RequestMapping("/errorAction")
  @ResponseBody
  public Map<String, Object> errorAction(HttpSession session, @RequestParam(value="id", required=false) int id, @RequestParam(value="auditOpinion", required=false) String auditOpinion)
  {
    IndustryStandard in = (IndustryStandard)this.industryStandardService.get(Integer.valueOf(id));
    in.setCauditstatus("2");
    in.setCvalid("0");
    in.setIdowncount(Long.valueOf(0L));
    in.setAuditOpinion(auditOpinion);
    in.setNauditid((Long)session.getAttribute("userId"));
    in.setDaudittime(DateUtil.getTimestamp());
    this.industryStandardService.update(in);
    return success("审核完成", in);
  }

  @RequestMapping("/approveAction")
  @ResponseBody
  public Map<String, Object> approveAction(IndustryStandard industry, HttpSession session, @RequestParam(value="file", required=false) MultipartFile[] files, @RequestParam(value="ids", required=false, defaultValue="") String ids, @RequestParam(value="type", required=false, defaultValue="") String type, @RequestParam(value="idString", required=false) String idString)
  {
    IndustryStandard policy = (IndustryStandard)this.industryStandardService.get(industry.getId());

    int size = this.annexService.getAllValidAnnexSize(industry.getId(), "");

    boolean sign = AnnexFileUpLoad.checkHasAnnex(industry.getChasannex(), idString, size);
    if (!sign)
      policy.setChasannex("0");
    else policy.setChasannex(industry.getChasannex());

    Long id = industry.getId();
    String title = industry.getTitle();
    boolean flag = this.industryStandardService.getIndustryByNameAndId(id, title);
    if (!flag) {
      return error("标准名称【" + title + "】已存在");
    }

    policy.setNmodifyid((Long)session.getAttribute("userId"));
    Timestamp stamp = DateUtil.getTimestamp();
    policy.setNmodifytime(stamp);

    policy.setTitle(industry.getTitle());

    policy.setCdownload(industry.getCdownload());

    String oldPath = policy.getContent();
    String filePath = AnnexFileUpLoad.writeContentToFile(industry.getContent(), BUSINESS_DIR, oldPath,"approve");
    policy.setContent(filePath);

    String msg = "";
    if (industry.getCauditstatus().equals("1"))
      msg = "审核通过";
    else if (industry.getCauditstatus().equals("2")) {
      msg = "审核不通过";
    }
    policy.setAuditOpinion(industry.getAuditOpinion());
    policy.setCauditstatus(industry.getCauditstatus());

    if (industry.getChasannex().equals("0")) {
      this.annexService.setAnnexIsValidByObjectId(industry.getId(), "");
    }
    if (!ids.equals("")) {
      String[] arr = ids.split(",");

      if ((arr.length == size) && (StringUtils.isEmpty(idString))) {
        policy.setChasannex("0");
      }
      for (int i = 0; i < arr.length; i++) {
        this.annexService.updateAnnexIsValidById(Long.valueOf(arr[i]), "");
      }
    }

    this.industryStandardService.update(policy);

    Long createId = (Long)session.getAttribute("userId");
    this.annexService.updateAnnex(createId, industry.getId(), idString);

    return success(msg, policy);
  }

  @RequestMapping("/delete")
  @ResponseBody
  public Map<String, Object> delete(@RequestParam("id") String id)
  {
    if (StringUtils.isNotEmpty(id)) {
      IndustryStandard inte = (IndustryStandard)this.industryStandardService.get(Long.valueOf(id));
      inte.setCvalid("0");
      this.industryStandardService.update(inte);
      return success("删除成功！");
    }return error("请选择需要删除的对象");
  }

  @RequestMapping("/back")
  @ResponseBody
  public Map<String, Object> back(@RequestParam("id") String id) {
    if (StringUtils.isNotEmpty(id)) {
      IndustryStandard inte = (IndustryStandard)this.industryStandardService.get(Long.valueOf(id));
      inte.setCvalid("1");
      this.industryStandardService.update(inte);
      return success("恢复成功！");
    }return error("请选择需要删除的对象");
  }

  @RequestMapping("/uploadFiles")
  public void uploadFiles(@RequestParam(value="file", required=false) MultipartFile[] files, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
    List list = new ArrayList();
    Long objectId = new Long(0L);
    session.setAttribute("userId", new Long(0L));
    List nexlist = AnnexFileUpLoad.uploadFile(files, session, objectId, OBJECT_TABLE, BUSINESS_DIR, null);
    String idString = this.annexService.saveAnnexListTwo(nexlist, null);
    list = AnnexFileUpLoad.commonUploadFiles(nexlist, idString);
    String jsonArray = JSONArray.toJSONString(list);
    PrintWriter out = null;
    try {
      out = response.getWriter();
    } catch (IOException e) {
      e.printStackTrace();
    }
    out.print(jsonArray);
  }
}