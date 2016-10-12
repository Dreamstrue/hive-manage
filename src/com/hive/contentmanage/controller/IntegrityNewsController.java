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
import com.hive.common.SystemCommon_Constant;
import com.hive.common.service.AnnexService;
import com.hive.contentmanage.entity.IntegrityNews;
import com.hive.contentmanage.service.IntegrityNewsService;
import com.hive.util.DateUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

@Controller
@RequestMapping("inteNews")
public class IntegrityNewsController extends BaseController
{
  private static final String PREFIX = "contentmanage/integrity";
  private static final String OBJECT_TABLE = "F_INTEGRITYNEWS";
  private static final String BUSINESS_DIR = SystemCommon_Constant.CXZX_04;

  @Resource
  private IntegrityNewsService integrityNewsService;

  @Resource
  private AnnexService annexService;

  @RequestMapping("manage")
  public String manage() { return "contentmanage/integrity/integrityManage"; }

  @RequestMapping("/allIntegrityNews")
  @ResponseBody
  public List<IntegrityNews> allIntegrityNews()
  {
    List list = this.integrityNewsService.allIntegrityNews();
    return list;
  }

  @RequestMapping("/dataGrid")
  @ResponseBody
  public DataGrid dataGrid(RequestPage page, @RequestParam(value="keys", required=false, defaultValue="") String keys) {
    return this.integrityNewsService.dataGrid(page, keys);
  }

  @RequestMapping("add")
  public String add()
  {
    return "contentmanage/integrity/integrityAdd";
  }

  @RequestMapping("/insert")
  @ResponseBody
  public Map<String, Object> insert(IntegrityNews integrity, HttpSession session, @RequestParam(value="file", required=false) MultipartFile[] files, @RequestParam(value="idString", required=false) String idString) {
    String title = integrity.getTitle();
    int size = this.integrityNewsService.getIntegrityNewsByName(title);
    if (size > 0) {
      return error("资讯标题【" + title + "】已存在");
    }

    String filePath = AnnexFileUpLoad.writeContentToFile(integrity.getContent(), BUSINESS_DIR, "","");
    integrity.setContent(filePath);

    integrity.setNcreateid((Long)session.getAttribute("userId"));
    Timestamp stamp = DateUtil.getTimestamp();
    integrity.setDcreatetime(stamp);

    integrity.setAuditstatus("0");
    integrity.setCvalid("1");

    integrity.setCount(Long.valueOf(0L));
    this.integrityNewsService.save(integrity);

    Long createId = (Long)session.getAttribute("userId");
    this.annexService.updateAnnex(createId, integrity.getId(), idString);

    return success("添加成功", integrity);
  }

  @RequestMapping("edit")
  public String edit(Model model, @RequestParam("id") Long id, @RequestParam("opType") String opType)
  {
    IntegrityNews in = (IntegrityNews)this.integrityNewsService.get(id);
    byte[] bt = AnnexFileUpLoad.getContentFromFile(in.getContent());
    in.setContent(new String(bt));

    model.addAttribute("vo", in);
    model.addAttribute("opType", opType);

    List list = this.annexService.getAnnexInfoByObjectId(id,OBJECT_TABLE);
    model.addAttribute("annex", list);
    return "contentmanage/integrity/integrityEdit";
  }

  @RequestMapping("/update")
  @ResponseBody
  public Map<String, Object> update(IntegrityNews integrity, HttpSession session, @RequestParam(value="file", required=false) MultipartFile[] files, @RequestParam(value="ids", required=false, defaultValue="") String ids, @RequestParam(value="idString", required=false) String idString)
  {
    IntegrityNews grity = (IntegrityNews)this.integrityNewsService.get(integrity.getId());

    int size = this.annexService.getAllValidAnnexSize(integrity.getId(), "");

    String chasannex = integrity.getChasannex();

    boolean flag1 = AnnexFileUpLoad.checkHasAnnex(chasannex, idString, size);

    if (!flag1) {
      integrity.setChasannex("0");
    }

    grity.setChasannex(integrity.getChasannex());

    Long id = integrity.getId();
    String title = integrity.getTitle();
    boolean flag = this.integrityNewsService.getIntegrityNewsByNameAndId(id, title);
    if (!flag) {
      return error("资讯标题【" + title + "】已存在");
    }
    grity.setTitle(integrity.getTitle());

    grity.setCfrom(integrity.getCfrom());

    String oldPath = grity.getContent();
    String filePath = AnnexFileUpLoad.writeContentToFile(integrity.getContent(), BUSINESS_DIR, oldPath,"");
    grity.setContent(filePath);

    grity.setNmodifyid((Long)session.getAttribute("userId"));
    Timestamp stamp = DateUtil.getTimestamp();
    grity.setDmodifytime(stamp);

    grity.setAuditstatus("0");

    if (integrity.getChasannex().equals("0")) {
      this.annexService.setAnnexIsValidByObjectId(integrity.getId(), "");
    }
    if (!ids.equals("")) {
      String[] arr = ids.split(",");

      if ((arr.length == size) && (StringUtils.isEmpty(idString)))
      {
        grity.setChasannex("0");
      }
      for (int i = 0; i < arr.length; i++) {
        this.annexService.updateAnnexIsValidById(Long.valueOf(arr[i]), "");
      }
    }
    this.integrityNewsService.update(grity);

    Long createId = (Long)session.getAttribute("userId");
    this.annexService.updateAnnex(createId, grity.getId(), idString);

    return success("修改成功", grity);
  }

  @RequestMapping("detail")
  public String detail(Model model, @RequestParam("id") Long id)
  {
    IntegrityNews in = (IntegrityNews)this.integrityNewsService.get(id);
    byte[] bt = AnnexFileUpLoad.getContentFromFile(in.getContent());
    in.setContent(new String(bt));
    model.addAttribute("vo", in);

    List list = this.annexService.getAnnexInfoByObjectId(id,OBJECT_TABLE);
    model.addAttribute("annex", list);
    return "contentmanage/integrity/integrityDetails";
  }

  @RequestMapping("approve")
  public String approve(Model model, @RequestParam("id") Long id, @RequestParam("opType") String opType) {
    IntegrityNews in = (IntegrityNews)this.integrityNewsService.get(id);
    byte[] bt = AnnexFileUpLoad.getContentFromFile(in.getContent());
    in.setContent(new String(bt));
    model.addAttribute("vo", in);

    model.addAttribute("opType", opType);

    List list = this.annexService.getAnnexInfoByObjectId(id,OBJECT_TABLE);
    model.addAttribute("annex", list);
    return "contentmanage/integrity/integrityEdit";
  }

  @RequestMapping("/successAction")
  @ResponseBody
  public Map<String, Object> successAction(IntegrityNews integrity, HttpSession session, @RequestParam(value="file", required=false) MultipartFile[] files, @RequestParam(value="ids", required=false, defaultValue="") String ids)
  {
    IntegrityNews grity = (IntegrityNews)this.integrityNewsService.get(integrity.getId());

    Long id = integrity.getId();
    String title = integrity.getTitle();
    boolean flag = this.integrityNewsService.getIntegrityNewsByNameAndId(id, title);
    if (!flag) {
      return error("资讯标题【" + title + "】已存在");
    }

    grity.setTitle(integrity.getTitle());
    grity.setContent(integrity.getContent());
    grity.setCfrom(integrity.getCfrom());

    grity.setNmodifyid((Long)session.getAttribute("userId"));
    Timestamp stamp = DateUtil.getTimestamp();
    grity.setDmodifytime(stamp);

    grity.setNauditid((Long)session.getAttribute("userId"));
    grity.setDaudittime(stamp);

    grity.setAuditstatus("1");
    grity.setChasannex(integrity.getChasannex());
    grity.setAuditOpinion(integrity.getAuditOpinion());

    if (integrity.getChasannex().equals("0")) {
      this.annexService.setAnnexIsValidByObjectId(integrity.getId(), "");
    }

    int size = this.annexService.getAllValidAnnexSize(integrity.getId(), "");
    if (!ids.equals("")) {
      String[] arr = ids.split(",");

      if (arr.length == size) {
        grity.setChasannex("0");
      }
      for (int i = 0; i < arr.length; i++) {
        this.annexService.updateAnnexIsValidById(Long.valueOf(arr[i]), "");
      }
    }
    this.integrityNewsService.update(grity);

    List nexlist = AnnexFileUpLoad.uploadFile(files, session, integrity.getId(), "F_INTEGRITYNEWS", BUSINESS_DIR, null);
    this.annexService.saveAnnexList(nexlist, null);
    return success("审核完成", grity);
  }

  @RequestMapping("/errorAction")
  @ResponseBody
  public Map<String, Object> errorAction(IntegrityNews integrity, HttpSession session, @RequestParam(value="file", required=false) MultipartFile[] files, @RequestParam(value="ids", required=false, defaultValue="") String ids)
  {
    IntegrityNews grity = (IntegrityNews)this.integrityNewsService.get(integrity.getId());

    Long id = integrity.getId();
    String title = integrity.getTitle();
    boolean flag = this.integrityNewsService.getIntegrityNewsByNameAndId(id, title);
    if (!flag) {
      return error("资讯标题【" + title + "】已存在");
    }

    grity.setTitle(integrity.getTitle());
    grity.setContent(integrity.getContent());
    grity.setCfrom(integrity.getCfrom());

    grity.setNmodifyid((Long)session.getAttribute("userId"));
    Timestamp stamp = DateUtil.getTimestamp();
    grity.setDmodifytime(stamp);

    grity.setNauditid((Long)session.getAttribute("userId"));
    grity.setDaudittime(stamp);

    grity.setAuditstatus("2");
    grity.setChasannex(integrity.getChasannex());
    grity.setAuditOpinion(integrity.getAuditOpinion());

    if (integrity.getChasannex().equals("0")) {
      this.annexService.setAnnexIsValidByObjectId(integrity.getId(), "");
    }

    int size = this.annexService.getAllValidAnnexSize(integrity.getId(), "");
    if (!ids.equals("")) {
      String[] arr = ids.split(",");

      if (arr.length == size) {
        grity.setChasannex("0");
      }
      for (int i = 0; i < arr.length; i++) {
        this.annexService.updateAnnexIsValidById(Long.valueOf(arr[i]), "");
      }
    }
    this.integrityNewsService.update(grity);

    List nexlist = AnnexFileUpLoad.uploadFile(files, session, integrity.getId(), "F_INTEGRITYNEWS", BUSINESS_DIR, null);
    this.annexService.saveAnnexList(nexlist, null);
    return success("审核完成", grity);
  }

  @RequestMapping("/approveAction")
  @ResponseBody
  public Map<String, Object> approveAction(IntegrityNews integrity, HttpSession session, @RequestParam(value="file", required=false) MultipartFile[] files, @RequestParam(value="ids", required=false, defaultValue="") String ids, @RequestParam(value="type", required=false, defaultValue="") String type, @RequestParam(value="idString", required=false) String idString) {
    IntegrityNews grity = (IntegrityNews)this.integrityNewsService.get(integrity.getId());

    int size = this.annexService.getAllValidAnnexSize(integrity.getId(), "");

    String chasannex = integrity.getChasannex();

    boolean flag1 = AnnexFileUpLoad.checkHasAnnex(chasannex, idString, size);

    if (!flag1) {
      integrity.setChasannex("0");
    }

    Long id = integrity.getId();
    String title = integrity.getTitle();
    boolean flag = this.integrityNewsService.getIntegrityNewsByNameAndId(id, title);
    if (!flag) {
      return error("资讯标题【" + title + "】已存在");
    }

    grity.setTitle(integrity.getTitle());

    grity.setCfrom(integrity.getCfrom());

    String oldPath = grity.getContent();
    String filePath = AnnexFileUpLoad.writeContentToFile(integrity.getContent(), BUSINESS_DIR, oldPath,"approve");
    grity.setContent(filePath);

    grity.setNmodifyid((Long)session.getAttribute("userId"));
    Timestamp stamp = DateUtil.getTimestamp();
    grity.setDmodifytime(stamp);

    grity.setNauditid((Long)session.getAttribute("userId"));
    grity.setDaudittime(stamp);

    grity.setAuditstatus(integrity.getAuditstatus());

    String msg = "";
    if (grity.getAuditstatus().equals("1"))
      msg = "审核通过";
    else if (grity.getAuditstatus().equals("2")) {
      msg = "审核不通过";
    }

    grity.setChasannex(integrity.getChasannex());
    grity.setAuditOpinion(integrity.getAuditOpinion());

    if (integrity.getChasannex().equals("0")) {
      this.annexService.setAnnexIsValidByObjectId(integrity.getId(), "");
    }
    if (!ids.equals("")) {
      String[] arr = ids.split(",");

      if ((arr.length == size) && (StringUtils.isEmpty(idString)))
      {
        grity.setChasannex("0");
      }
      for (int i = 0; i < arr.length; i++) {
        this.annexService.updateAnnexIsValidById(Long.valueOf(arr[i]), "");
      }
    }
    this.integrityNewsService.update(grity);

    Long createId = (Long)session.getAttribute("userId");
    this.annexService.updateAnnex(createId, grity.getId(), idString);

    return success(msg, grity);
  }

  @RequestMapping("/delete")
  @ResponseBody
  public Map<String, Object> delete(@RequestParam("id") String id) {
    if (StringUtils.isNotEmpty(id)) {
      IntegrityNews inte = (IntegrityNews)this.integrityNewsService.get(Long.valueOf(id));
      inte.setCvalid("0");
      this.integrityNewsService.update(inte);
      return success("删除成功！");
    }return error("请选择需要删除的对象");
  }

  @RequestMapping("/back")
  @ResponseBody
  public Map<String, Object> back(@RequestParam("id") String id) {
    if (StringUtils.isNotEmpty(id)) {
      IntegrityNews inte = (IntegrityNews)this.integrityNewsService.get(Long.valueOf(id));
      inte.setCvalid("1");
      this.integrityNewsService.update(inte);
      return success("恢复成功！");
    }return error("请选择需要删除的对象");
  }

  @RequestMapping("/uploadFiles")
  public void uploadFiles(@RequestParam(value="file", required=false) MultipartFile[] files, HttpServletRequest request, HttpServletResponse response, HttpSession session)
  {
    List list = new ArrayList();
    Long objectId = new Long(0L);
    session.setAttribute("userId", new Long(0L));
    List nexlist = AnnexFileUpLoad.uploadFile(files, session, objectId, "F_INTEGRITYNEWS", BUSINESS_DIR, null);
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