package com.hive.contentmanage.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hive.common.AnnexFileUpLoad;
import com.hive.common.SystemCommon_Constant;
import com.hive.common.service.AnnexService;
import com.hive.contentmanage.entity.TrustCultural;
import com.hive.contentmanage.model.TrustCulturalBean;
import com.hive.contentmanage.service.TrustCulturalService;
import com.hive.util.DateUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

@Controller
@RequestMapping("cultural")
public class TrustCulturalController extends BaseController
{
  private static final String PREFIX = "contentmanage/trustcultural";
  private static final String OBJECT_TABLE = "F_TrustCultural";
  private static final String BUSINESS_DIR = "[10]CXWH";

  @Resource
  private TrustCulturalService culturalService;

  @Resource
  private AnnexService annexService;

  @RequestMapping("manage")
  public String manage(Model model, @RequestParam("id") Long id, @RequestParam("pid") Long pid)
  {
    model.addAttribute("menuId", id);
    model.addAttribute("pid", pid);
    return PREFIX+"/trustCulturalManage";
  }
  @RequestMapping("dataGrid")
  @ResponseBody
  public DataGrid dataGrid(RequestPage page, @RequestParam(value="keys", required=false, defaultValue="") String keys, @RequestParam("id") Long id) {
    return this.culturalService.dataGrid(page, keys, id);
  }

  @RequestMapping("add")
  public String add(Model model, @RequestParam("menuId") Long menuId, @RequestParam("pid") Long pid)
  {
    model.addAttribute("menuId", menuId);
    model.addAttribute("pid", pid);
    return PREFIX+"/trustCulturalAdd";
  }

  @RequestMapping("insert")
  @ResponseBody
  public Map<String, Object> insert(TrustCultural cultural, HttpSession session, @RequestParam(value="file", required=false) MultipartFile[] files, @RequestParam(value="idString", required=false) String idString)
  {
    String title = cultural.getTitle();
    int size = this.culturalService.getCulturalByName(title);
    if (size > 0) {
      return error("标题【" + title + "】已存在");
    }

    String filePath = AnnexFileUpLoad.writeContentToFile(cultural.getContent(), BUSINESS_DIR, "","");
    cultural.setContent(filePath);

    cultural.setNcreateid((Long)session.getAttribute("userId"));
    cultural.setDcreatetime(DateUtil.getTimestamp());
    cultural.setCauditstatus(SystemCommon_Constant.AUDIT_STATUS_0);
    cultural.setCvalid("1");
    cultural.setIviewcount(Long.valueOf(0L));

    this.culturalService.save(cultural);

    Long createId = (Long)session.getAttribute("userId");
    this.annexService.updateAnnex(createId, cultural.getId(), idString);

    return success("保存成功", cultural);
  }

  @RequestMapping("edit")
  public String edit(Model model, @RequestParam("id") Long id, @RequestParam("opType") String opType, @RequestParam("pid") Long pid)
  {
    TrustCultural in = (TrustCultural)this.culturalService.get(id);
    byte[] bt = AnnexFileUpLoad.getContentFromFile(in.getContent());
    in.setContent(new String(bt));
    model.addAttribute("vo", in);
    model.addAttribute("opType", opType);
    model.addAttribute("pid", pid);

    List list = this.annexService.getAnnexInfoByObjectId(id,OBJECT_TABLE);
    model.addAttribute("annex", list);
    return PREFIX+"/trustCulturalEdit";
  }

  @RequestMapping("update")
  @ResponseBody
  public Map<String, Object> update(TrustCulturalBean bean, HttpSession session, @RequestParam(value="file", required=false) MultipartFile[] files, @RequestParam(value="ids", required=false, defaultValue="") String ids, @RequestParam(value="idString", required=false) String idString) {
    TrustCultural cultural = (TrustCultural)this.culturalService.get(bean.getId());

    int size = this.annexService.getAllValidAnnexSize(bean.getId(), "");

    boolean flag = AnnexFileUpLoad.checkHasAnnex(bean.getChasannex(), idString, size);
    if (!flag) {
      bean.setChasannex("0");
    }

    String oldPath = cultural.getContent();
    String filePath = AnnexFileUpLoad.writeContentToFile(bean.getContent(), BUSINESS_DIR, oldPath,"");
    try
    {
      PropertyUtils.copyProperties(cultural, bean);
      cultural.setContent(filePath);
    } catch (Exception e) {
      e.printStackTrace();
    }
    cultural.setNmodifyid((Long)session.getAttribute("userId"));
    cultural.setDmodifytime(DateUtil.getTimestamp());
    cultural.setCauditstatus(SystemCommon_Constant.AUDIT_STATUS_0);

    if (bean.getChasannex().equals("0")) {
      this.annexService.setAnnexIsValidByObjectId(bean.getId(), "");
    }
    if (!ids.equals("")) {
      String[] arr = ids.split(",");

      if ((arr.length == size) && (StringUtils.isEmpty(idString))) {
        cultural.setChasannex("0");
      }
      for (int i = 0; i < arr.length; i++) {
        this.annexService.updateAnnexIsValidById(Long.valueOf(arr[i]), "");
      }
    }

    Long createId = (Long)session.getAttribute("userId");
    this.annexService.updateAnnex(createId, cultural.getId(), idString);

    this.culturalService.update(cultural);

    return success("修改成功", cultural);
  }

  @RequestMapping("detail")
  public String detail(Model model, @RequestParam("id") Long id, @RequestParam("pid") Long pid) {
    TrustCultural in = (TrustCultural)this.culturalService.get(id);
    byte[] bt = AnnexFileUpLoad.getContentFromFile(in.getContent());
    in.setContent(new String(bt));
    model.addAttribute("vo", in);
    model.addAttribute("pid", pid);

    List list = this.annexService.getAnnexInfoByObjectId(id,OBJECT_TABLE);
    model.addAttribute("annex", list);
    return PREFIX+"/trustCulturalDetails";
  }

  @RequestMapping("approveAction")
  @ResponseBody
  public Map<String, Object> approveAction(TrustCulturalBean bean, HttpSession session, @RequestParam(value="file", required=false) MultipartFile[] files, @RequestParam(value="ids", required=false, defaultValue="") String ids, @RequestParam(value="type", required=false, defaultValue="") String type, @RequestParam(value="idString", required=false) String idString) {
    TrustCultural cultural = (TrustCultural)this.culturalService.get(bean.getId());

    int size = this.annexService.getAllValidAnnexSize(bean.getId(), "");

    boolean flag = AnnexFileUpLoad.checkHasAnnex(bean.getChasannex(), idString, size);
    if (!flag) {
      bean.setChasannex("0");
    }

    String oldPath = cultural.getContent();
    String filePath = AnnexFileUpLoad.writeContentToFile(bean.getContent(), BUSINESS_DIR, oldPath,"approve");
    try
    {
      PropertyUtils.copyProperties(cultural, bean);
      cultural.setContent(filePath);
    } catch (Exception e) {
      e.printStackTrace();
    }

    cultural.setNauditid((Long)session.getAttribute("userId"));
    cultural.setDaudittime(DateUtil.getTimestamp());

    String msg = "";
    if (bean.getCauditstatus().equals("1"))
      msg = "审核通过";
    else if (bean.getCauditstatus().equals("2")) {
      msg = "审核不通过";
    }
    cultural.setAuditopinion(bean.getAuditopinion());

    if (bean.getChasannex().equals("0")) {
      this.annexService.setAnnexIsValidByObjectId(bean.getId(), "");
    }
    if (!ids.equals("")) {
      String[] arr = ids.split(",");

      if ((arr.length == size) && (StringUtils.isEmpty(idString))) {
        cultural.setChasannex("0");
      }
      for (int i = 0; i < arr.length; i++) {
        this.annexService.updateAnnexIsValidById(Long.valueOf(arr[i]), "");
      }
    }

    Long createId = (Long)session.getAttribute("userId");
    this.annexService.updateAnnex(createId, cultural.getId(), idString);

    this.culturalService.update(cultural);

    return success(msg, cultural);
  }

  @RequestMapping("delete")
  @ResponseBody
  public Map<String, Object> delete(@RequestParam("id") String id)
  {
    if (StringUtils.isNotEmpty(id)) {
      TrustCultural inte = (TrustCultural)this.culturalService.get(Long.valueOf(id));
      inte.setCvalid("0");
      this.culturalService.update(inte);
      return success("删除成功！");
    }
    return error("请选择需要删除的对象");
  }

  @RequestMapping("uploadFiles")
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