package com.hive.membermanage.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Annex;
import com.hive.common.service.AnnexService;
import com.hive.enterprisemanage.entity.EEnterpriseinfo;
import com.hive.enterprisemanage.entity.EEnterpriseinfomodifytemp;
import com.hive.enterprisemanage.entity.EnterpriseLinkPerson;
import com.hive.enterprisemanage.service.EnterpriseLinkPersonService;
import com.hive.enterprisemanage.service.EnterpriseProductService;
import com.hive.membermanage.entity.MMember;
import com.hive.membermanage.entity.MPaymentrecords;
import com.hive.membermanage.service.EnterpriseTempService;
import com.hive.membermanage.service.MembermanageService;
import com.hive.membermanage.service.QualityPromiseManageService;
import com.hive.surveymanage.entity.IndustryEntity;
import com.hive.util.DateUtil;
import com.hive.util.PropertiesFileUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.util.MD5;

@Controller
@RequestMapping({"/membermanage"})
public class MembermanageController extends BaseController
{
  private static final Logger logger = Logger.getLogger(MembermanageController.class);

  private final String PREFIX = "membermanage";

  @Resource
  private MembermanageService membermanageService;

  @Resource
  private EnterpriseTempService enterpriseTempService;

  @Resource
  private QualityPromiseManageService qualityPromiseManageService;

  @Resource
  private AnnexService annexService;

  @Resource
  private PropertiesFileUtil propertiesFileUtil;

  @Resource
  private EnterpriseProductService productService;
  @Resource
	private EnterpriseLinkPersonService enterpriseLinkPersonService;

  @RequestMapping({"/tocheckmember.action"})
  public String toCheckMember() { logger.info("tocheckmember.action");
    return "membermanage/membercheck";
  }

  @RequestMapping({"/listunchekmember.action"})
  @ResponseBody
  public DataGrid listUnchekMember(RequestPage page, HttpServletRequest request)
  {
    String userName = request.getParameter("cusername");
    String status = request.getParameter("status");
    status = StringUtils.isBlank(status) ? "0" : status;
    return this.membermanageService.findUncheckUser(page, userName, status);
  }
  @RequestMapping({"/findUser.action"})
  @ResponseBody
  public DataGrid findUser(RequestPage page, HttpServletRequest request)
  {
	String chinesename = request.getParameter("chinesename");
    String userName = request.getParameter("cusername");
    String status = request.getParameter("status");
    String cmobilephone = request.getParameter("cmobilephone");
    status = StringUtils.isBlank(status) ? "0" : status;
    String cmemberstatus = request.getParameter("cmemberstatus");
    return this.membermanageService.findUser(page,chinesename, userName, status,cmobilephone,cmemberstatus);
  }
  
  @RequestMapping({"/checkmember.action"})
  @ResponseBody
  public Object checkMember(HttpServletRequest request)
  {
    String ids = request.getParameter("ids");
    String status = request.getParameter("checktype");
    this.membermanageService.checkMember(ids, status);
    return success("审核成功！");
  }

  @RequestMapping({"/topaymentrecords.action"})
  public String toPaymentRecords()
  {
    return "membermanage/paymentrecords";
  }

  @RequestMapping({"/listpaymentrecords.action"})
  @ResponseBody
  public DataGrid listPaymentRecords(RequestPage page, HttpServletRequest request)
  {
    String userName = request.getParameter("userName");
    String memberType = request.getParameter("memberType");
    return this.membermanageService.listPaymentRecords(page, userName, memberType);
  }

  @RequestMapping({"/topaymentrecorddetails.action"})
  public String toPaymentRecordDetails(@RequestParam("memberid") String memberid, @RequestParam("membername") String membername)
  {
    return "membermanage/paymentrecorddetails";
  }

  @RequestMapping({"/listpaymentrecorddetails.action"})
  @ResponseBody
  public DataGrid listPaymentRecordDetails(RequestPage page, Model m, @RequestParam("memberid") Long memberid, @RequestParam("membername") String membername)
  {
    logger.info(memberid + ":" + membername);
    DataGrid dg = this.membermanageService.listPaymentRecordsByMember(page, memberid);
    return dg;
  }

  @RequestMapping({"/paymentadd.action"})
  @ResponseBody
  public Object paymentAdd(HttpServletRequest request)
  {
    String memberIds = request.getParameter("memberIds");
    String paysum = request.getParameter("paysum");
    String paytype = request.getParameter("paytype");
    String remark = request.getParameter("remark");
    MPaymentrecords payment = null;
    if (StringUtils.isNotBlank(memberIds)) {
      String[] ids = memberIds.split(",");
      Date d = new Date();
      for (String id : ids) {
        payment = new MPaymentrecords();
        payment.setCpaytype(paytype);
        payment.setCremark(remark);
        payment.setCvalid("1");
        payment.setDpaytime(d);
        payment.setNmemberid(Long.valueOf(Long.parseLong(id)));
        payment.setNoptuserid(Long.valueOf(0L));
        payment.setNpaysum(Double.valueOf(Double.parseDouble(paysum)));
        this.membermanageService.addPaymentRecord(payment);
      }
    }

    return success("缴费成功！");
  }

  @RequestMapping({"/touncheckenterprise.action"})
  public String toUncheckEnterprise()
  {
    return "membermanage/uncheckenterprise";
  }

  @RequestMapping({"/listuncheckenterprise.action"})
  @ResponseBody
  public DataGrid listUncheckEnterprise(RequestPage page, HttpServletRequest request)
  {
    String enterpriseName = request.getParameter("entName");
    String status = request.getParameter("status");
    status = StringUtils.isBlank(status) ? "0" : status;
    return this.enterpriseTempService.listUncheckEnterprise(page, enterpriseName, status);
  }

  @RequestMapping({"/toEntInfoEdit"})
  public String toEntInfoEdit(@RequestParam(value="entInfoId", required=false) Long entInfoId, Model model)
  {
    EEnterpriseinfomodifytemp enterpriseInfo = (EEnterpriseinfomodifytemp)this.enterpriseTempService.get(entInfoId);
    model.addAttribute("enterpriseInfo", enterpriseInfo);
    return "membermanage/entInfoEdit";
  }

  /**
   * 
   * @Description: 审核企业信息
   * @author yanghui 
   * @Created 2014-4-21
   * @param request
   * @param session
   * @return
   */
  @RequestMapping({"/checkenterprise.action"})
  @ResponseBody
  public Object checkEnterprise(HttpServletRequest request, HttpSession session)
  {
    String ids = request.getParameter("nenterpriseid");

    String status = request.getParameter("checktype");

    String remark = request.getParameter("remark");

    Long curUserId = (Long)session.getAttribute("userId");
    if (curUserId == null) {
      return error("登录之后才能审核，请先登录");
    }
    this.enterpriseTempService.updateCheckEnterprise(ids, status, remark, curUserId);
    return success("审核成功！");
  }

  @RequestMapping({"/toUncheckQualification.action"})
  public String toUncheckQualification()
  {
    return "membermanage/uncheckqualification";
  }

  @RequestMapping({"/listUncheckQualification.action"})
  @ResponseBody
  public DataGrid listUncheckQualification(RequestPage page, HttpServletRequest request)
  {
    String enterpriseName = request.getParameter("enterpriseName");
    String certificateName = request.getParameter("certificateName");
    String certificateNO = request.getParameter("certificateNO");
    String certificationUnit = request.getParameter("certificationUnit");
    String status = request.getParameter("status");
    status = StringUtils.isBlank(status) ? "0" : status;
    return this.enterpriseTempService.listUncheckQualification(page, enterpriseName, certificateName, certificateNO, certificationUnit, status);
  }

  @RequestMapping({"/checkQualification.action"})
  @ResponseBody
  public Object checkQualification(HttpServletRequest request)
  {
    String ids = request.getParameter("ids");
    String status = request.getParameter("checktype");
    String remark = request.getParameter("remark");
    this.enterpriseTempService.updateCheckQualification(ids, status, remark);
    String msg = "";
    if (status.equals("1"))
      msg = "审核通过!";
    else msg = "审核不通过!";
    return success(msg);
  }

  @RequestMapping({"/toUncheckProduct.action"})
  public String toUncheckProduct()
  {
    return "membermanage/uncheckproduct";
  }

  @RequestMapping({"/listUncheckProduct.action"})
  @ResponseBody
  public DataGrid listUncheckProduct(RequestPage page, HttpServletRequest request)
  {
    String enterpriseName = request.getParameter("enterpriseName");
    String productName = request.getParameter("productName");
    String certificateNO = request.getParameter("certificateNO");
    String certificationUnit = request.getParameter("certificationUnit");
    String status = request.getParameter("status");
    status = StringUtils.isBlank(status) ? "0" : status;
    return this.enterpriseTempService.listUncheckProduct(page, enterpriseName, productName, certificateNO, certificationUnit, status);
  }

  @RequestMapping({"/checkProduct.action"})
  @ResponseBody
  public Object checkProduct(HttpServletRequest request)
  {
    String ids = request.getParameter("ids");
    String status = request.getParameter("checktype");
    String remark = request.getParameter("remark");
    this.enterpriseTempService.updateCheckProduct(ids, status, remark);
    return success("审核成功！");
  }

  @RequestMapping({"/touncheckintegritystyle.action"})
  public String toUncheckIntegritystyle()
  {
    return "membermanage/uncheckintegritystyle";
  }

  @RequestMapping({"/listuncheckintegritystyle.action"})
  @ResponseBody
  public DataGrid listUncheckIntegritystyle(RequestPage page, HttpServletRequest request)
  {
    String enterpriseName = request.getParameter("enterpriseName");
    String userName = request.getParameter("userName");
    String status = request.getParameter("status");
    status = StringUtils.isBlank(status) ? "0" : status;
    return this.enterpriseTempService.listUncheckIntegritystyle(page, enterpriseName, userName, status);
  }

  @RequestMapping({"/toIntegrityStyle_Product.action"})
  public String toIntegrityStyle_Product(HttpServletRequest request, @RequestParam("cproductids") String cproductids)
  {
    request.setAttribute("cproductids", cproductids);
    return "membermanage/uncheckintegritystyle_product";
  }

  @RequestMapping({"/listuncheckintegritystyle_product.action"})
  @ResponseBody
  public DataGrid listUncheckIntegrityStyle_Product(RequestPage page, @RequestParam("cproductids") String cproductids)
  {
    return this.productService.datagrid2(page, cproductids);
  }

  /**
   * 
   * @Description: 审核企业基本信息
   * @author 
   * @Created 2014-4-21
   * @param session
   * @param request
   * @return
   */
  @RequestMapping({"/checkintegritystyle.action"})
  @ResponseBody
  public Object checkintegritystyle(HttpSession session, HttpServletRequest request)
  {
    String ids = request.getParameter("memberIds");
    String status = request.getParameter("checktype");
    String remark = request.getParameter("remark");
    this.enterpriseTempService.updateCheckIntegritystyle(ids, (Long)session.getAttribute("userId"), status, remark);
    return success("审核成功！");
  }

  @RequestMapping({"/toUncheckQualityPromise.action"})
  public String toUncheckQualityPromise()
  {
    return "membermanage/uncheckQualityPromise";
  }

  @RequestMapping({"/listUncheckQualityPromise.action"})
  @ResponseBody
  public DataGrid listUncheckQualityPromise(RequestPage page, HttpServletRequest request)
  {
    String enterpriseName = request.getParameter("enterpriseName");
    String status = request.getParameter("status");
    status = StringUtils.isBlank(status) ? "0" : status;
    return this.qualityPromiseManageService.listUncheckQualityPromise(page, enterpriseName, status);
  }

  @RequestMapping({"/previewPicture"})
  public void previewPicture(@RequestParam("qualityPromiseId") Long qualityPromiseId, HttpServletResponse response)
  {
    Annex annex = this.annexService.getAnnexByType("E_QualityPromise", qualityPromiseId.toString());
    ServletOutputStream os = null;
    FileInputStream fis = null;
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0L);
    response.setContentType("image/jpeg");
    if (annex == null) {
      return;
    }
    String previewPhotoPath = this.propertiesFileUtil.findValue("uploadPath") + annex.getCfilepath();
    File photoFile = new File(previewPhotoPath);
    try {
      if ((photoFile != null) && (photoFile.exists())) {
        os = response.getOutputStream();
        fis = new FileInputStream(photoFile);
        byte[] buffer = new byte[1024];
        int b;
        while ((b = fis.read(buffer)) != -1)
        {
          os.write(buffer, 0, b);
        }
        os.flush();
        os.close();
        fis.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @RequestMapping({"/checkQualityPromise.action"})
  @ResponseBody
  public Object checkQualityPromise(HttpSession session, HttpServletRequest request)
  {
    String ids = request.getParameter("ids");
    String status = request.getParameter("checktype");
    String remark = request.getParameter("remark");
    this.qualityPromiseManageService.updateCheckQualityPromise(ids, (Long)session.getAttribute("userId"), status, remark);
    return success("审核成功！");
  }
  
  
  
  @RequestMapping("getLinkPerson")
	@ResponseBody
	public List<EnterpriseLinkPerson> getLinkPerson(@RequestParam(value="entInfoId") Long enterId){
		List<EnterpriseLinkPerson> list = new ArrayList<EnterpriseLinkPerson>();
		list = enterpriseLinkPersonService.getLinkPersonsByEnterpriseId(enterId);
		return list;
		
	}
  
  /**
   * 方法名称：updatePassword
   * 功能描述：更改密码
   * 创建人: pengfei Zhao
  */
	@RequestMapping("/updatePassword.action") 
	public String updatePassword(Model model, @RequestParam(value = "nmemberid") Long nmemberid) {
		MMember member = membermanageService.get(nmemberid);
		if(member != null){
		   String existPwd = member.getCpassword();
		   model.addAttribute("vo", member);
		}
		return PREFIX + "/updatePassword";
	}
	
	
	 /**
     * 方法名称：creatLoginUser
     * 功能描述：创建用户
     * 创建时间:2016年4月15日下午4:41:33
     * 创建人: pengfei Zhao
     * @return Map<String,Object>
    */
   @RequestMapping("/savePassword")
	@ResponseBody
	public Map<String, Object> savePassword(HttpServletRequest request){
   	    String menbId=request.getParameter("id");
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		MMember menb=membermanageService.get(Long.valueOf(menbId));
		if(menb != null){
			if(menb.getCpassword().equals(MD5.getMD5Code(password))){
				return error("新密码不能与旧密码相同");
			} 
			String encryptedPassword = MD5.getMD5Code(password);
			menb.setCpassword(encryptedPassword);
		}else{
			return error("用户名不存在!");
		}
		membermanageService.update(menb);
		return success("密码修改成功");
	}
}