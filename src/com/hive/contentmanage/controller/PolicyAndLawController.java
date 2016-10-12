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
import com.hive.contentmanage.entity.Policyandlaw;
import com.hive.contentmanage.service.PolicyAndLawService;
import com.hive.util.DateUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;
/**
 * 
* Filename: PolicyAndLawController.java  
* Description:  政策法规  Controller 类
* Copyright:Copyright (c)2014
* Company:  GuangFan 
* @author:  yanghui
* @version: 1.0  
* @Create:  2014-3-25  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2014-3-25 下午3:07:37				yanghui 	1.0
 */
@Controller
@RequestMapping("policy")
public class PolicyAndLawController extends BaseController
{
  private static final String PREFIX = "contentmanage/policy";
  private static final String OBJECT_TABLE = "F_POLICYANDLAW";
  private static final String BUSINESS_DIR = "[03]ZCFG";

  @Resource
  private PolicyAndLawService policyAndLawService;

  @Resource
  private AnnexService annexService;

  @RequestMapping("manage")
  public String manage(Model model, @RequestParam("id") int id)
  {
    model.addAttribute("pid", Integer.valueOf(id));
    return PREFIX+"/policyManage";
  }

  @RequestMapping("allmanage")
  public String allmanage(Model model, @RequestParam("id") int id)
  {
    model.addAttribute("backId", Integer.valueOf(id));
    return PREFIX+"/policyAllManage";
  }

  @RequestMapping("/allPolicyNews")
  @ResponseBody
  public List<Policyandlaw> allIntegrityNews()
  {
    List list = this.policyAndLawService.allPolicyandlaw();
    return list;
  }

  @RequestMapping("/dataGrid")
  @ResponseBody
  public DataGrid dataGrid(RequestPage page, @RequestParam(value="keys", required=false, defaultValue="") String keys, @RequestParam(value="menuid", required=false, defaultValue="") String menuid)
  {
    return this.policyAndLawService.dataGrid(page, keys, menuid);
  }

  @RequestMapping("alladd")
  public String alladd(Model model, @RequestParam("id") int id)
  {
    model.addAttribute("pid", Integer.valueOf(id));
    return PREFIX+"/policyAllAdd";
  }

  @RequestMapping("add")
  public String add(Model model, @RequestParam("id") int id) {
    model.addAttribute("pid", Integer.valueOf(id));
    return PREFIX+"/policyAdd";
  }

  @RequestMapping("/insert")
  @ResponseBody
  public Map<String, Object> insert(Policyandlaw policy, HttpSession session, @RequestParam(value="file", required=false) MultipartFile[] files, @RequestParam(value="idString", required=false) String idString) {
    String title = policy.getClawname();
    int size = this.policyAndLawService.getPolicyandlawByName(title);
    if (size > 0) {
      return error("标题【" + title + "】已存在");
    }

    String filePath = AnnexFileUpLoad.writeContentToFile(policy.getClawsummary(), BUSINESS_DIR, "","");
    policy.setClawsummary(filePath);

    policy.setNcreateid((Long)session.getAttribute("userId"));
    Timestamp stamp = DateUtil.getTimestamp();
    policy.setDcreatetime(stamp);

    policy.setCauditstatus("0");
    policy.setCvalid("1");

    policy.setIdowncount(Long.valueOf(0L));
    this.policyAndLawService.save(policy);

    Long createId = (Long)session.getAttribute("userId");
    this.annexService.updateAnnex(createId, policy.getNlawid(), idString);

    return success("添加成功", policy);
  }

  @RequestMapping("edit")
  public String edit(Model model, @RequestParam("id") Long id, @RequestParam("opType") String opType, @RequestParam(value="backId", required=false, defaultValue="") String backId)
  {
    Policyandlaw in = (Policyandlaw)this.policyAndLawService.get(id);
    byte[] bt = AnnexFileUpLoad.getContentFromFile(in.getClawsummary());
    in.setClawsummary(new String(bt));
    model.addAttribute("vo", in);
    model.addAttribute("opType", opType);
    if (backId.equals(""))
      model.addAttribute("backId", Integer.valueOf(0));
    else model.addAttribute("backId", backId);

    List list = this.annexService.getAnnexInfoByObjectId(id,OBJECT_TABLE);
    model.addAttribute("annex", list);
    return PREFIX+"/policyEdit";
  }

  @RequestMapping("/update")
  @ResponseBody
  public Map<String, Object> update(Policyandlaw integrity, HttpSession session, @RequestParam(value="file", required=false) MultipartFile[] files, @RequestParam(value="ids", required=false, defaultValue="") String ids, @RequestParam(value="idString", required=false) String idString)
  {
    Policyandlaw policy = (Policyandlaw)this.policyAndLawService.get(integrity.getNlawid());

    int size = this.annexService.getAllValidAnnexSize(integrity.getNlawid(), "");
    String chasannex = integrity.getChasannex();

    boolean sign = AnnexFileUpLoad.checkHasAnnex(chasannex, idString, size);
    if (!sign)
      policy.setChasannex("0");
    else policy.setChasannex(integrity.getChasannex());

    Long id = integrity.getNlawid();
    String title = integrity.getClawname();
    boolean flag = this.policyAndLawService.getPolicyandlawByNameAndId(id, title);
    if (!flag) {
      return error("标题【" + title + "】已存在");
    }

    String oldPath = policy.getClawsummary();
    String filePath = AnnexFileUpLoad.writeContentToFile(integrity.getClawsummary(), BUSINESS_DIR, oldPath,"");
    policy.setClawsummary(filePath);

    policy.setNmodifyid((Long)session.getAttribute("userId"));
    Timestamp stamp = DateUtil.getTimestamp();
    policy.setDmodifytime(stamp);

    policy.setClawname(integrity.getClawname());

    policy.setCdownload(integrity.getCdownload());

    policy.setCauditstatus("0");

    if (integrity.getChasannex().equals("0")) {
      this.annexService.setAnnexIsValidByObjectId(integrity.getNlawid(), "");
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

    this.policyAndLawService.update(policy);

    Long createId = (Long)session.getAttribute("userId");
    this.annexService.updateAnnex(createId, policy.getNlawid(), idString);

    return success("修改成功", policy);
  }

  @RequestMapping("detail")
  public String detail(Model model, @RequestParam("id") Long id, @RequestParam(value="backId", required=false, defaultValue="") String backId)
  {
    Policyandlaw in = (Policyandlaw)this.policyAndLawService.get(id);
    byte[] bt = AnnexFileUpLoad.getContentFromFile(in.getClawsummary());
    in.setClawsummary(new String(bt));
    model.addAttribute("vo", in);
    if (backId.equals(""))
      model.addAttribute("backId", Integer.valueOf(0));
    else model.addAttribute("backId", backId);

    List list = this.annexService.getAnnexInfoByObjectId(id,OBJECT_TABLE);
    model.addAttribute("annex", list);
    return PREFIX+"/policyDetails";
  }

  @RequestMapping("approve")
  public String approve(Model model, @RequestParam("id") Long id, @RequestParam("opType") String opType, @RequestParam(value="backId", required=false, defaultValue="") String backId) {
    Policyandlaw in = (Policyandlaw)this.policyAndLawService.get(id);
    byte[] bt = AnnexFileUpLoad.getContentFromFile(in.getClawsummary());
    in.setClawsummary(new String(bt));
    model.addAttribute("vo", in);
    model.addAttribute("opType", opType);
    if (backId.equals(""))
      model.addAttribute("backId", Integer.valueOf(0));
    else model.addAttribute("backId", backId);

    List list = this.annexService.getAnnexInfoByObjectId(id,OBJECT_TABLE);
    model.addAttribute("annex", list);
    return PREFIX+"/policyEdit";
  }

  @RequestMapping("/successAction")
  @ResponseBody
  public Map<String, Object> successAction(Policyandlaw policy, HttpSession session, @RequestParam(value="file", required=false) MultipartFile[] files)
  {
    Policyandlaw bean = (Policyandlaw)this.policyAndLawService.get(policy.getNlawid());

    Long id = policy.getNlawid();
    String title = policy.getClawname();
    boolean flag = this.policyAndLawService.getPolicyandlawByNameAndId(id, title);
    if (!flag) {
      return error("标题【" + title + "】已存在");
    }

    bean.setClawname(policy.getClawname());
    bean.setClawsummary(policy.getClawsummary());
    bean.setCdownload(policy.getCdownload());

    bean.setNmodifyid((Long)session.getAttribute("userId"));
    Timestamp stamp = DateUtil.getTimestamp();
    bean.setDmodifytime(stamp);

    bean.setNauditerid((Long)session.getAttribute("userId"));
    bean.setDaudittime(stamp);

    bean.setCauditstatus("1");
    bean.setCvalid("1");

    bean.setChasannex(policy.getChasannex());

    bean.setAuditOpinion(policy.getAuditOpinion());
    this.policyAndLawService.update(bean);

    List nexlist = AnnexFileUpLoad.uploadFile(files, session, policy.getNlawid(), OBJECT_TABLE, BUSINESS_DIR, null);
    this.annexService.saveAnnexList(nexlist, null);
    return success("审核完成", bean);
  }

  @RequestMapping("/errorAction")
  @ResponseBody
  public Map<String, Object> errorAction(HttpSession session, @RequestParam(value="id", required=false) Long id, @RequestParam(value="auditOpinion", required=false) String auditOpinion)
  {
    Policyandlaw in = (Policyandlaw)this.policyAndLawService.get(id);
    in.setCauditstatus("2");
    in.setIdowncount(Long.valueOf(0L));

    in.setAuditOpinion(auditOpinion);
    in.setNauditerid((Long)session.getAttribute("userId"));
    in.setDaudittime(DateUtil.getTimestamp());
    this.policyAndLawService.update(in);
    return success("审核完成", in);
  }

  @RequestMapping("/approveAction")
  @ResponseBody
  public Map<String, Object> approveAction(Policyandlaw integrity, HttpSession session, @RequestParam(value="file", required=false) MultipartFile[] files, @RequestParam(value="ids", required=false, defaultValue="") String ids, @RequestParam(value="type", required=false, defaultValue="") String type, @RequestParam(value="idString", required=false) String idString)
  {
    Policyandlaw policy = (Policyandlaw)this.policyAndLawService.get(integrity.getNlawid());

    int size = this.annexService.getAllValidAnnexSize(integrity.getNlawid(), "");
    String chasannex = integrity.getChasannex();

    boolean sign = AnnexFileUpLoad.checkHasAnnex(chasannex, idString, size);
    if (!sign)
      policy.setChasannex("0");
    else policy.setChasannex(integrity.getChasannex());

    Long id = integrity.getNlawid();
    String title = integrity.getClawname();
    boolean flag = this.policyAndLawService.getPolicyandlawByNameAndId(id, title);
    if (!flag) {
      return error("标题【" + title + "】已存在");
    }

    policy.setNmodifyid((Long)session.getAttribute("userId"));
    Timestamp stamp = DateUtil.getTimestamp();
    policy.setDmodifytime(stamp);

    policy.setClawname(integrity.getClawname());

    policy.setCdownload(integrity.getCdownload());

    String oldPath = policy.getClawsummary();
    String filePath = AnnexFileUpLoad.writeContentToFile(integrity.getClawsummary(), BUSINESS_DIR, oldPath,"approve");
    policy.setClawsummary(filePath);

    String msg = "";
    if (integrity.getCauditstatus().equals("1"))
      msg = "审核通过";
    else if (integrity.getCauditstatus().equals("2")) {
      msg = "审核不通过";
    }
    policy.setAuditOpinion(integrity.getAuditOpinion());
    policy.setCauditstatus(integrity.getCauditstatus());

    if (integrity.getChasannex().equals("0")) {
      this.annexService.setAnnexIsValidByObjectId(integrity.getNlawid(), "");
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
    this.annexService.updateAnnex(createId, policy.getNlawid(), idString);

    this.policyAndLawService.update(policy);

    return success(msg, policy);
  }

  @RequestMapping("/delete")
  @ResponseBody
  public Map<String, Object> delete(@RequestParam("id") String id)
  {
    if (StringUtils.isNotEmpty(id)) {
      Policyandlaw inte = (Policyandlaw)this.policyAndLawService.get(Long.valueOf(id));
      inte.setCvalid("0");
      this.policyAndLawService.update(inte);
      return success("删除成功！");
    }return error("请选择需要删除的对象");
  }

  @RequestMapping("/back")
  @ResponseBody
  public Map<String, Object> back(@RequestParam("id") String id) {
    if (StringUtils.isNotEmpty(id)) {
      Policyandlaw inte = (Policyandlaw)this.policyAndLawService.get(Long.valueOf(id));
      inte.setCvalid("1");
      this.policyAndLawService.update(inte);
      return success("恢复成功！");
    }return error("请选择需要删除的对象");
  }

  @RequestMapping("/uploadFiles")
  public void uploadFiles(@RequestParam(value="file", required=false) MultipartFile[] files, HttpServletRequest request, HttpServletResponse response, HttpSession session)
  {
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