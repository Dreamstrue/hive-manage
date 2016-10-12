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

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hive.common.AnnexFileUpLoad;
import com.hive.common.entity.Annex;
import com.hive.common.service.AnnexService;
import com.hive.contentmanage.entity.Emagazine;
import com.hive.contentmanage.model.EmagazineBean;
import com.hive.contentmanage.service.EmagazineService;
import com.hive.util.DateUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

@Controller
@RequestMapping("/magazine")
public class EmagazineController extends BaseController
{
  private static final String PREFIX = "contentmanage/emagazine";
  private static final String OBJECT_TABLE = "F_EMAGAZINE";
  private static final String BUSINESS_DIR_NEW = "[12]DZZZTP";
  private static final String BUSINESS_DIR_ANNEX = "[12]DZZZFJ";

  @Resource
  private EmagazineService magazineService;

  @Resource
  private AnnexService annexService;

  @RequestMapping("/manage")
  public String manage(Model model, @RequestParam("id") Long id)
  {
    model.addAttribute("menuId", id);
    return PREFIX+"/magazineManage";
  }
  @RequestMapping("/datagrid")
  @ResponseBody
  public DataGrid datagrid(RequestPage page, @RequestParam(value="keys", required=false, defaultValue="") String s, @RequestParam("id") Long id) {
    return this.magazineService.dataGrid(page, s, id);
  }

  @RequestMapping("/add")
  public String add(Model model, @RequestParam("menuId") Long menuId) {
    model.addAttribute("menuId", menuId);
    return PREFIX+"/magazineAdd";
  }

  @RequestMapping("/insert")
  @ResponseBody
  public Map<String, Object> insert(Emagazine picture, HttpSession session, @RequestParam(value="imgfile", required=false) MultipartFile file, @RequestParam(value="exeString", required=false) String exeString, @RequestParam(value="zipString", required=false) String zipString)
  {
    String title = picture.getTitle();
    int size = this.magazineService.gettitleByName(title);
    if (size > 0) {
      return error("杂志标题【" + title + "】已存在");
    }

    picture.setNcreateid(
      (Long)session
      .getAttribute("userId"));
    picture.setDcreatetime(DateUtil.getTimestamp());
    picture.setCauditstatus("0");
    picture.setCvalid("1");
    picture.setIviewcount(Long.valueOf(0L));
    picture.setChasannex("1");

    if (StringUtils.isEmpty(exeString)) {
      return error("本地杂志没选择");
    }

    if (StringUtils.isEmpty(zipString)) {
      return error("在线版杂志没选择");
    }

    if (file.getSize() > 0L) {
      List list = AnnexFileUpLoad.uploadImageFile(file, session, new Long(0L), OBJECT_TABLE, BUSINESS_DIR_NEW, "image");
      String picpath = ((Annex)list.get(0)).getCfilepath();
      picture.setPath(picpath);
      this.magazineService.save(picture);
      this.annexService.saveAnnexList(list, String.valueOf(picture.getNemagazineId())); } else {
      return error("请选择图片");
    }

    Long createId = (Long)session.getAttribute("userId");
    this.annexService.updateAnnex(createId, picture.getNemagazineId(), exeString);
    this.annexService.updateAnnex(createId, picture.getNemagazineId(), zipString);

    return success("保存成功", picture);
  }

  @RequestMapping("/edit")
  public String edit(Model model, @RequestParam("id") Long id, @RequestParam("opType") String opType, @RequestParam("menuId") Long menuId)
  {
    Emagazine in = (Emagazine)this.magazineService.get(id);
    model.addAttribute("vo", in);
    model.addAttribute("opType", opType);

    List imgList = this.annexService.getAnnexInfoByObjectIdAndAnnexType(id);
    model.addAttribute("img", imgList.get(0));
    List exeList = this.annexService.getAnnexInfoByObjectIdAndAnnexType(id, "EXE");
    model.addAttribute("exe", exeList.get(0));
    List zipList = this.annexService.getAnnexInfoByObjectIdAndAnnexType(id, "ZIP");
    model.addAttribute("zip", zipList.get(0));
    model.addAttribute("menuId", menuId);
    return PREFIX+"/magazineEdit";
  }

  @RequestMapping("/update")
  @ResponseBody
  public Map<String, Object> update(EmagazineBean bean, HttpSession session, @RequestParam(value="imgfile", required=false) MultipartFile file, @RequestParam(value="exeString", required=false) String exeString, @RequestParam(value="zipString", required=false) String zipString)
  {
    Emagazine picture = (Emagazine)this.magazineService.get(bean.getNemagazineId());
    picture.setTitle(bean.getTitle());
    picture.setContent(bean.getContent());
    picture.setNmodifyid(
      (Long)session
      .getAttribute("userId"));
    picture.setDmodifytime(DateUtil.getTimestamp());
    picture.setCauditstatus("0");

    String imageExist = bean.getImageExist();
    if (imageExist.equals("no"))
    {
      if (file.getSize() < 0L) {
        return error("请选择上传封面图片");
      }

    }

    String exeExist = bean.getExeExist();
    if (exeExist.equals("no"))
    {
      if (StringUtils.isEmpty(exeString)) {
        return error("本地杂志没选择");
      }

      Annex nex = (Annex)this.annexService.get(bean.getExeId());
      nex.setCvalid("0");
      this.annexService.update(nex);

      Long createId = (Long)session.getAttribute("userId");
      this.annexService.updateAnnex(createId, picture.getNemagazineId(), exeString);
    }

    String zipExist = bean.getZipExist();
    if (zipExist.equals("no"))
    {
      if (StringUtils.isEmpty(zipString)) {
        return error("在线版杂志没选择");
      }
      Annex nex1 = (Annex)this.annexService.get(bean.getZipId());
      nex1.setCvalid("0");
      this.annexService.update(nex1);

      Long createId = (Long)session.getAttribute("userId");
      this.annexService.updateAnnex(createId, picture.getNemagazineId(), zipString);
    }

    if (file.getSize() > 0L) {
      Annex img = (Annex)this.annexService.get(bean.getImgId());
      img.setCvalid("0");
      this.annexService.update(img);

      List list = AnnexFileUpLoad.uploadImageFile(file, session, bean.getNemagazineId(), OBJECT_TABLE, BUSINESS_DIR_NEW, "image");
      String picpath = ((Annex)list.get(0)).getCfilepath();
      picture.setPath(picpath);
      this.annexService.saveAnnexList(list, String.valueOf(bean.getNemagazineId()));
    }

    this.magazineService.update(picture);
    return success("修改成功");
  }

  @RequestMapping("detail")
  public String detail(Model model, @RequestParam("id") Long id, @RequestParam("menuId") Long menuId) {
    Emagazine in = (Emagazine)this.magazineService.get(id);
    model.addAttribute("vo", in);

    List imgList = this.annexService.getAnnexInfoByObjectIdAndAnnexType(id);
    model.addAttribute("img", imgList.get(0));
    List exeList = this.annexService.getAnnexInfoByObjectIdAndAnnexType(id, "EXE");
    model.addAttribute("exe", exeList.get(0));
    List zipList = this.annexService.getAnnexInfoByObjectIdAndAnnexType(id, "ZIP");
    model.addAttribute("zip", zipList.get(0));
    model.addAttribute("menuId", menuId);
    return PREFIX+"/magazineDetails";
  }

  @RequestMapping("/approve")
  public String approve(Model model, @RequestParam("id") Long id, @RequestParam("opType") String opType, @RequestParam("menuId") Long menuId)
  {
    Emagazine in = (Emagazine)this.magazineService.get(id);
    model.addAttribute("vo", in);
    model.addAttribute("opType", opType);

    List imgList = this.annexService.getAnnexInfoByObjectIdAndAnnexType(id);
    model.addAttribute("img", imgList.get(0));
    List exeList = this.annexService.getAnnexInfoByObjectIdAndAnnexType(id, "EXE");
    model.addAttribute("exe", exeList.get(0));
    List zipList = this.annexService.getAnnexInfoByObjectIdAndAnnexType(id, "ZIP");
    model.addAttribute("zip", zipList.get(0));
    model.addAttribute("menuId", menuId);
    return PREFIX+"/magazineApprove";
  }

  @RequestMapping("/approveAction")
  @ResponseBody
  public Map<String, Object> approveAction(EmagazineBean bean, HttpSession session) {
    Emagazine picture = (Emagazine)this.magazineService.get(bean.getNemagazineId());
    picture.setCauditopinion(bean.getCauditopinion());
    picture.setNauditid(
      (Long)session
      .getAttribute("userId"));
    picture.setDaudittime(DateUtil.getTimestamp());
    String msg = "";
    if (bean.getCauditstatus().equals("1"))
      msg = "审核通过";
    else if (bean.getCauditstatus().equals("2")) {
      msg = "审核不通过";
    }
    picture.setCauditstatus(bean.getCauditstatus());
    this.magazineService.update(picture);
    return success(msg);
  }
  @RequestMapping("/delete")
  @ResponseBody
  public Map<String, Object> delete(@RequestParam("id") String id) {
    if (StringUtils.isNotEmpty(id)) {
      Emagazine inte = (Emagazine)this.magazineService.get(Long.valueOf(id));
      inte.setCvalid("0");
      this.magazineService.update(inte);
      return success("删除成功！");
    }
    return error("请选择需要删除的对象");
  }

  @RequestMapping("/uploadExeFiles")
  public void uploadExeFiles(@RequestParam(value="file", required=false) MultipartFile file, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
    List list = new ArrayList();
    Long objectId = new Long(0L);
    session.setAttribute("userId", new Long(0L));
    List nexlist = AnnexFileUpLoad.uploadSingleFile(file, session, objectId, OBJECT_TABLE, BUSINESS_DIR_ANNEX, "EXE", "type_exe");
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

  @RequestMapping("/uploadZipFiles")
  public void uploadZipFiles(@RequestParam(value="file", required=false) MultipartFile file, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
    List list = new ArrayList();
    Long objectId = new Long(0L);
    session.setAttribute("userId", new Long(0L));
    List nexlist = AnnexFileUpLoad.uploadSingleFile(file, session, objectId, OBJECT_TABLE, BUSINESS_DIR_ANNEX, "ZIP", "type_zip");
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