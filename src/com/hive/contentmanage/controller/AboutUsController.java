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
import com.hive.common.service.AnnexService;
import com.hive.contentmanage.entity.Aboutus;
import com.hive.contentmanage.model.AboutUsBean;
import com.hive.contentmanage.service.AboutUsService;
import com.hive.util.DateUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

@Controller
@RequestMapping("/aboutus")
public class AboutUsController extends BaseController
{
  private static final String PREFIX = "contentmanage/aboutus";
  public static final String OBJECT_TABLE = "F_ABOUTUS";
  public static final String BUSINESS_DIR = "[11]GYWM";

  @Resource
  private AboutUsService aboutusService;

  @Resource
  private AnnexService annexService;

  @RequestMapping("/introduce")
  public String introduce(Model model, @RequestParam(value="id",required=false) Long id)
  {
    Aboutus us = this.aboutusService.getAboutUsInfoByMenuId(id);
    byte[] bt = AnnexFileUpLoad.getContentFromFile(us.getCcontent());
    us.setCcontent(new String(bt));
    model.addAttribute("vo", us);

    model.addAttribute("menuid", id);

    List list = new ArrayList();
    if (us.getNaboutusid() != null) {
      list = this.annexService.getAnnexInfoByObjectId(us.getNaboutusid(),OBJECT_TABLE);
    }
    model.addAttribute("annex", list);
    return PREFIX+"/introduce";
  }

  @RequestMapping("/constitution")
  public String constitution(Model model, @RequestParam("id") Long id) {
    Aboutus us = this.aboutusService.getAboutUsInfoByMenuId(id);
    byte[] bt = AnnexFileUpLoad.getContentFromFile(us.getCcontent());
    us.setCcontent(new String(bt));
    model.addAttribute("vo", us);
    model.addAttribute("menuid", id);

    List list = new ArrayList();
    if (us.getNaboutusid() != null) {
      list = this.annexService.getAnnexInfoByObjectId(us.getNaboutusid(),OBJECT_TABLE);
    }
    model.addAttribute("annex", list);
    return PREFIX+"/constitution";
  }

  @RequestMapping("/applyflow")
  public String applyflow(Model model, @RequestParam("id") Long id)
  {
    Aboutus us = this.aboutusService.getAboutUsInfoByMenuId(id);
    byte[] bt = AnnexFileUpLoad.getContentFromFile(us.getCcontent());
    us.setCcontent(new String(bt));
    model.addAttribute("vo", us);
    model.addAttribute("menuid", id);

    List list = new ArrayList();
    if (us.getNaboutusid() != null) {
      list = this.annexService.getAnnexInfoByObjectId(us.getNaboutusid(),OBJECT_TABLE);
    }
    model.addAttribute("annex", list);
    return PREFIX+"/applyflow";
  }

  @RequestMapping("/insert")
  @ResponseBody
  public Map<String, Object> insert(Aboutus us, @RequestParam(value="file", required=false) MultipartFile[] files, HttpSession session, @RequestParam(value="idString", required=false) String idString)
  {
    String filePath = AnnexFileUpLoad.writeContentToFile(us.getCcontent(), BUSINESS_DIR, "","");
    us.setCcontent(filePath);

    us.setNcreateid((Long)session.getAttribute("userId"));
    us.setDcreatetime(DateUtil.getTimestamp());
    us.setCauditstatus("0");
    us.setCvalid("1");
    us.setIviewcount(Long.valueOf(0L));

    this.aboutusService.save(us);

    Long createId = (Long)session.getAttribute("userId");
    this.annexService.updateAnnex(createId, us.getNaboutusid(), idString);

    return success("保存成功", us);
  }
  @RequestMapping("/update")
  @ResponseBody
  public Map<String, Object> update(AboutUsBean us, @RequestParam(value="file", required=false) MultipartFile[] files, HttpSession session, @RequestParam(value="ids", required=false, defaultValue="") String ids, @RequestParam(value="idString", required=false) String idString) {
    Aboutus aution = (Aboutus)this.aboutusService.get(us.getNaboutusid());
    String oldPath = aution.getCcontent();
    String filePath = AnnexFileUpLoad.writeContentToFile(us.getCcontent(), BUSINESS_DIR, oldPath,"");
    aution.setCcontent(filePath);

    aution.setCtitle(us.getCtitle());
    aution.setChref(us.getChref());
    aution.setCfrom(us.getCfrom());
    aution.setCauditstatus("0");
    aution.setCauditopinion(us.getCauditopinion());

    int size = this.annexService.getAllValidAnnexSize(us.getNaboutusid(), "");

    boolean flag = AnnexFileUpLoad.checkHasAnnex(us.getChasannex(), idString, size);
    if (!flag)
      aution.setChasannex("0");
    else aution.setChasannex(us.getChasannex());

    aution.setNmodifyid((Long)session.getAttribute("userId"));
    aution.setDmodifytime(DateUtil.getTimestamp());

    if (us.getChasannex().equals("0")) {
      this.annexService.setAnnexIsValidByObjectId(us.getNaboutusid(), "");
    }
    if (!ids.equals("")) {
      String[] arr = ids.split(",");

      if ((arr.length == size) && (StringUtils.isEmpty(idString))) {
        aution.setChasannex("0");
      }
      for (int i = 0; i < arr.length; i++) {
        this.annexService.updateAnnexIsValidById(Long.valueOf(arr[i]), "");
      }
    }

    Long createId = (Long)session.getAttribute("userId");
    this.annexService.updateAnnex(createId, us.getNaboutusid(), idString);

    this.aboutusService.update(aution);

    return success("修改成功", aution);
  }

  @RequestMapping("/manage")
  public String manage(Model model, @RequestParam("id") Long id)
  {
    model.addAttribute("menuId", id);
    return PREFIX+"/associationNewsManage";
  }
  @RequestMapping("/dataGrid")
  @ResponseBody
  public DataGrid dataGrid(RequestPage page, @RequestParam(value="keys", required=false, defaultValue="") String keys, @RequestParam("id") Long id) {
    return this.aboutusService.dataGrid(page, keys, id);
  }

  @RequestMapping("/add")
  public String add(Model model, @RequestParam("menuId") Long menuId) {
    model.addAttribute("menuId", menuId);
    return PREFIX+"/associationNewsAdd";
  }

  @RequestMapping("/edit")
  public String edit(Model model, @RequestParam("id") Long id, @RequestParam("opType") String opType)
  {
    Aboutus in = (Aboutus)this.aboutusService.get(id);
    byte[] bt = AnnexFileUpLoad.getContentFromFile(in.getCcontent());
    in.setCcontent(new String(bt));
    model.addAttribute("vo", in);
    model.addAttribute("opType", opType);

    List list = this.annexService.getAnnexInfoByObjectId(id,OBJECT_TABLE);
    model.addAttribute("annex", list);
    String index = "";
    if (opType.equals("update"))
      index = PREFIX+"/associationNewsEdit";
    else index = PREFIX+"/associationNewsApprove";
    return index;
  }

  @RequestMapping("detail")
  public String detail(Model model, @RequestParam("id") Long id)
  {
    Aboutus in = (Aboutus)this.aboutusService.get(id);
    byte[] bt = AnnexFileUpLoad.getContentFromFile(in.getCcontent());
    in.setCcontent(new String(bt));
    model.addAttribute("vo", in);

    List list = this.annexService.getAnnexInfoByObjectId(id,OBJECT_TABLE);
    model.addAttribute("annex", list);
    return PREFIX+"/associationNewsDetail";
  }

  @RequestMapping("/approveAction")
  @ResponseBody
  public Map<String, Object> approveAction(AboutUsBean bean, HttpSession session, @RequestParam(value="file", required=false) MultipartFile[] files, @RequestParam(value="ids", required=false, defaultValue="") String ids, @RequestParam(value="type", required=false, defaultValue="") String type, @RequestParam(value="idString", required=false) String idString)
  {
    Aboutus aution = (Aboutus)this.aboutusService.get(bean.getNaboutusid());

    int size = this.annexService.getAllValidAnnexSize(bean.getNaboutusid(), "");

    boolean flag = AnnexFileUpLoad.checkHasAnnex(bean.getChasannex(), idString, size);
    if (!flag) {
      bean.setChasannex("0");
    }

    String oldPath = aution.getCcontent();
    String filePath = AnnexFileUpLoad.writeContentToFile(bean.getCcontent(), BUSINESS_DIR, oldPath,"approve");
    try
    {
      PropertyUtils.copyProperties(aution, bean);
      aution.setCcontent(filePath);
    } catch (Exception e) {
      e.printStackTrace();
    }

    aution.setNauditid((Long)session.getAttribute("userId"));
    aution.setDaudittime(DateUtil.getTimestamp());

    String msg = "";
    if (bean.getCauditstatus().equals("1"))
      msg = "审核通过";
    else if (bean.getCauditstatus().equals("2")) {
      msg = "审核不通过";
    }
    aution.setCauditstatus(bean.getCauditstatus());

    if (bean.getChasannex().equals("0")) {
      this.annexService.setAnnexIsValidByObjectId(bean.getNaboutusid(), "");
    }
    if (!ids.equals("")) {
      String[] arr = ids.split(",");

      if ((arr.length == size) && (StringUtils.isEmpty(idString))) {
        aution.setChasannex("0");
      }
      for (int i = 0; i < arr.length; i++) {
        this.annexService.updateAnnexIsValidById(Long.valueOf(arr[i]), "");
      }
    }

    Long createId = (Long)session.getAttribute("userId");
    this.annexService.updateAnnex(createId, bean.getNaboutusid(), idString);

    this.aboutusService.update(aution);

    return success(msg, aution);
  }

  @RequestMapping("/delete")
  @ResponseBody
  public Map<String, Object> delete(@RequestParam("id") String id) {
    if (StringUtils.isNotEmpty(id)) {
      Aboutus inte = (Aboutus)this.aboutusService.get(Long.valueOf(id));
      inte.setCvalid("0");
      this.aboutusService.update(inte);
      return success("删除成功！");
    }
    return error("请选择需要删除的对象");
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