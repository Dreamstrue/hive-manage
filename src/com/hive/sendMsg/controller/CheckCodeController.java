package com.hive.sendMsg.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.common.SystemCommon_Constant;
import com.hive.sendMsg.entity.Checkcode;
import com.hive.sendMsg.service.CheckCodeService;
import com.hive.sendMsg.service.PlatformSMSService;
import com.hive.sendMsg.service.PlatformSMSServiceLexinImpl;
import com.hive.util.DataUtil;
import com.hive.util.GetIPAddr;
import com.hive.util.PropertiesFileUtil;

import dk.controller.BaseController;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：pengfei Zhao
 * <p/>创建日期：2016-03-24
 * <p/>创建时间：下午2:54:53
 * <p/>功能描述：验证码Controller
 * <p/>===========================================================
 */
@Controller
@RequestMapping("/checkCode")
public class CheckCodeController extends BaseController {
	
	/** 验证码Service */
	@Resource
	private CheckCodeService checkCodeService;
	
	
	@RequestMapping("/checkMoblieCode")
	@ResponseBody
	public Map<String, Object> checkMoblieCode(@RequestParam(value = "phone", required = true) String phone,
			HttpServletRequest request,HttpSession session){
	     	String checkCode=DataUtil.getSix();//生成随机码
		    String ipAdress=GetIPAddr.getIpAddr(request);//获取IP地址
		    List<Checkcode> codeInfoList=checkCodeService.getCodoInfoByMobile(phone);
		    if(codeInfoList.size()>0){//如果数据库已存在该记录，就拿最新的一条跟现在时间做对比
		    	Checkcode codeInfo=codeInfoList.get(0);
		    	Long newtime=new Date().getTime();
		    	Long diffSeconds = (newtime - codeInfo.getCreateTime().getTime())/1000;
		    	int intsecond=diffSeconds.intValue();
		    	if(intsecond<SystemCommon_Constant.REPEAT_TIME){
		    		session.setAttribute("checkCode", codeInfo.getCheckCode());
		    		return success("2", SystemCommon_Constant.REPEAT_TIME-intsecond);
		    	}else{
		    		codeInfo.setCvalid(SystemCommon_Constant.VALID_STATUS_0);
		    		checkCodeService.saveOrUpdate(codeInfo);
			    	//调用接口生成验证码
			    	boolean isTrue = false;
					PropertiesFileUtil propertiesFileUtil = new PropertiesFileUtil();
					String check = propertiesFileUtil.findValue("sendMessageFlag");
					if(check.equals("run")){
				    	isTrue=sendMsg(checkCode,phone);//接口返回布尔值 
					}else if(check.equals("debug")){
						isTrue= true;
					}
			    	if(isTrue){
			    		System.out.println("手机验证码为##非第一次发送############################"+checkCode);
				    	session.setAttribute("checkCode", checkCode);
				    	saveEntityInfo(checkCode,phone,ipAdress);
				    	return success("1", "已发送成功");
			    	}else{
			    		return error("失败", "失败！请重新发送");
			    	}
		    	}
		    }else{
		    	//调用接口生成验证码
		    	boolean isTrue = false;
				PropertiesFileUtil propertiesFileUtil = new PropertiesFileUtil();
				String check = propertiesFileUtil.findValue("sendMessageFlag");
				if(check.equals("run")){
			    	isTrue=sendMsg(checkCode,phone);//接口返回布尔值 
				}else if(check.equals("debug")){
					isTrue= true;
				}
		    	if(isTrue){
		    		System.out.println("手机验证码为##第一次发送############################"+checkCode);
			    	session.setAttribute("checkCode", checkCode);
			    	saveEntityInfo(checkCode,phone,ipAdress);
		    	}else{
		    		System.out.println("失败！请重新发送");
		    		return error("失败", "失败！请重新发送");
		    	}
		    	
		    }
		return success("1", "已发送成功");
	}
	/**
	 * 校验手机验证码是否正确
	 * @param phone
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/checkCode.action")
	@ResponseBody
	public Map<String, Object> checkCode(@RequestParam(value = "phone", required = true) String phone,
			@RequestParam(value = "code", required = true) String code,HttpServletRequest request){
		    List<Checkcode> codeInfoList=checkCodeService.getCodoInfoByMobile(phone);
		    if(codeInfoList.size()>0){//如果数据库已存在该记录，就拿最新的一条跟现在时间做对比
		    	Checkcode codeInfo=codeInfoList.get(0);
		    	if(code.equals(codeInfo.getCheckCode())){
		    		return success();
		    	}else{
		    		return error("您输入的验证码有误，请核对后重新输入，验证码有效时间为十分钟！");
		    	}
		    }else{
	    		return error("您输入的验证码有误，请核对后重新输入！");
		    }
	}
	
	
	 public void saveEntityInfo(String checkCode,String phone,String ipAdress){
		try{
		//写入数据到数据库
	    	Checkcode codeInfo=new Checkcode();
	    	codeInfo.setCheckCode(checkCode);
	    	codeInfo.setCreateTime(new Date());
	    	codeInfo.setMobile(phone);
	    	codeInfo.setIpAdress(ipAdress);
	    	codeInfo.setCvalid(SystemCommon_Constant.VALID_STATUS_1);
	    	Long time=new Date().getTime()+(SystemCommon_Constant.INVALID_TIME*1000);
	    	Date expireTime=new Date(time);
	    	codeInfo.setExpireTime(expireTime);
	    	checkCodeService.save(codeInfo);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	 }
	public boolean sendMsg(String checkCode,String phone){
		String strSms = "质量评价短信验证码："+checkCode+",短信验证码10分钟失效【质讯通】";
		PlatformSMSService smsService = new PlatformSMSServiceLexinImpl();
		return smsService.sendSms(new Long(1),phone, strSms,"1","IPAS");
	}

}
