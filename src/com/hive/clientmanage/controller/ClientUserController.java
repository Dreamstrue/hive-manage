package com.hive.clientmanage.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.clientmanage.model.ClientUserBean;
import com.hive.clientmanage.model.MMemberBean;
import com.hive.clientmanage.service.ClientUserService;
import com.hive.common.SystemCommon_Constant;
import com.hive.enterprisemanage.entity.EEnterpriseinfo;
import com.hive.enterprisemanage.service.EnterpriseInfoService;
import com.hive.membermanage.entity.MMember;
import com.hive.membermanage.model.MemberVO;
import com.hive.membermanage.service.MembermanageService;
import com.hive.systemconfig.entity.SentimentKeyWord;
import com.hive.systemconfig.service.SentimentKeyWordService;
import com.hive.util.DataUtil;
import com.hive.util.DateUtil;

import dk.controller.BaseController;
import dk.util.MD5;

@Controller
@RequestMapping("interface/user")
public class ClientUserController extends BaseController {

	private Logger logger = Logger.getLogger(ClientUserController.class);
	
	
	@Resource
	private ClientUserService clientUserService;
	@Resource
	private SentimentKeyWordService sentimentKeyWordService;
	@Resource
	private EnterpriseInfoService enterpriseInfoService;
	@Resource
	private MembermanageService membermanageService;
	/**
	 * 
	 * @Description:  用户登录
	 * @author yanghui 
	 * @Created 2014-4-22
	 * @param user
	 * @return
	 */
	@RequestMapping(value="login",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> login(MMember user,HttpServletRequest request){
		HttpSession session = request.getSession();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String username = user.getCusername(); //登录时填写的用户名
		String password = user.getCpassword(); //登录时填写的密码
		boolean flag = true;
		 
		 //1.校验用户是否存在
		user = clientUserService.checkUserName(user);
		if(user.getNmemberid()==null){
			resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_FAIL);
			resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "用户名或密码错误，请检查后重新输入！");
			logger.error("用户不存在");
		}else{
			if(SystemCommon_Constant.VALID_STATUS_0.equals(user.getCvalid())){
				resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_FAIL);
				resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "用户已被禁用，请联系管理员！");
			}else{
				if("1".equals(user.getCmembertype())){ //企业类型的用户
					//2.该用户是管理员用户还是非管理员用户
					if(SystemCommon_Constant.SIGN_YES_1.equals(user.getIsManager())){ //管理员用户
						if(!SystemCommon_Constant.AUDIT_STATUS_1.equals(user.getCmemberstatus())) { //未审核
							resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_FAIL);
			//				resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "系统对用户【"+user.getCusername()+"】审核中");
							resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "系统管理员还未审核您的注册信息，请耐心等待！");
							logger.error("系统还未对用户【"+user.getCusername()+"】进行审核");
							flag = false;
						}
					}else{
						//非管理员用户
						if(!SystemCommon_Constant.AUDIT_STATUS_1.equals(user.getCmemberstatus())) { //未审核
							String managerName = clientUserService.getManagerName(user.getOrganizationCode());
							resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_FAIL);
				//			resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "管理员对用户【"+user.getCusername()+"】审核中");
							resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "管理员（"+managerName+"）还未审核您的注册信息，请耐心等待！");
							logger.error("管理员还未对用户【"+user.getCusername()+"】进行审核");
							flag = false;
						}
					}
				}
				//3.判断登录密码是否正确
				if(flag){
					String md5Pwd = MD5.getMD5Code(password);
					if(!user.getCpassword().equals(md5Pwd)){
						resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_FAIL);
						resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "用户名或密码错误，请检查后重新输入！");
						logger.error("用户名或密码不正确");
					}else{
						MemberVO member=new MemberVO();
						resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
						resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "登录成功");
						if(null != user.getNenterpriseid() && !"".equals(user.getNenterpriseid().toString())){//添加非空校验  20160712 yf add
							EEnterpriseinfo enterprise = enterpriseInfoService.get(user.getNenterpriseid());
							
							try {
								PropertyUtils.copyProperties(member,user);
								if(enterprise!=null){
								  member.setSurveyIndustryId( enterprise.getCindcatcode());
								}
							} catch (Exception e) {
								e.printStackTrace();
							} 
						}
						resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, member);
						session.setAttribute("currentUser", user);
						session.setAttribute("userName", user.getCusername());
						session.setAttribute("userId", user.getNmemberid());
						session.setAttribute("nenterpriseid", user.getNenterpriseid());
						session.setAttribute("enterpriseName", user.getEnterpriseName());
						session.setAttribute("password", user.getCpassword());
					}
				}
			}
		}
		return resultMap;
	}
	
	/**
	 * 
	 * @Description:  用户注册（客户端注册分为两步：第一步只注册用户名和密码，用户类型（企业，个人）；第二步：填写用户的个人或企业基本信息）
	 * @author yanghui 
	 * @Created 2014-4-22
	 * @param user
	 * @return
	 */
	@RequestMapping(value="register",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> registerUserName(MMember user){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//校验用户是否存在
		user = clientUserService.checkUserIsExist(user);
		if(user.getNmemberid()==null){
			//用户名不存在，保存新用户
			user = clientUserService.saveNewUser(user);
			resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
			resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "注册成功");
		}else{
			resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_FAIL);
			resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "用户名已存在");
		}
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, user);
		return resultMap;
	}

	/**
	 * 
	 * @Description: 注册第二步，保存用户基本信息
	 * @author yanghui 
	 * @Created 2014-4-22
	 * @param user
	 * @return
	 */
	@RequestMapping(value="updateInfo",method=RequestMethod.POST) 
	@ResponseBody
	public Map<String,Object> updateInfo(MMemberBean bean){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		MMember oldUser = clientUserService.get(bean.getNmemberid());
		try {
			PropertyUtils.copyProperties(oldUser, bean);
			resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
			resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "信息保存成功");
		} catch (Exception e) {
			resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_FAIL);
			resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "保存失败");
			e.printStackTrace();
		}
		
		clientUserService.saveOrUpdate(oldUser);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, oldUser);
		return resultMap;
	}
	
	
	
	@RequestMapping("queryKeyWord")
	@ResponseBody
	public Map<String,Object> queryKeyWord(@RequestParam(value="userId") Long userId){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<SentimentKeyWord> list = clientUserService.getSentimentKeyWord(userId);
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, list);
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 用户设置自己的关键字
	 * @author YangHui 
	 * @Created 2014-6-17
	 * @param keyword
	 * @param userId
	 * @return
	 */
	@RequestMapping("editKeyWord")
	@ResponseBody
	public Map<String,Object> editKeyWord(@RequestParam(value="keyword") String keyword,@RequestParam(value="userId") Long userId){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//不管新增或修改，首先删除该用户的关键字，然后新增
		List<SentimentKeyWord> list = clientUserService.getSentimentKeyWord(userId);
		for(int i=0;i<list.size();i++){
			SentimentKeyWord kw = list.get(i);
			sentimentKeyWordService.delete(kw.getId());
		}
		String[] s = keyword.split(",");
		for(int i=0;i<s.length;i++){
			String kw = s[i];
			SentimentKeyWord skw = new SentimentKeyWord();
			skw.setKeyWord(kw);
			skw.setUserId(userId);
			sentimentKeyWordService.save(skw);
		}
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "关键字添加成功");
		return resultMap;
	}
	
	
	
	/**
	 * 
	 * @Description: 企业用户注册
	 * @author YangHui 
	 * @Created 2014-6-18
	 * @param user
	 * @return
	 */
	@RequestMapping(value="registerEnter",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> registerEnter(MMember user){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String organizationCode = user.getOrganizationCode();
		/*
		 * 当flag=true时，说明该组织机构下已经存在用户
		 * 当flag=false时，说明该组织机构下不存在用户
		 */
		boolean flag = membermanageService.checkEnterpriseIsRegistedByOrganCode(organizationCode);
		//校验用户是否存在
		user = clientUserService.checkUserIsExist(user);
		if(user.getNmemberid()==null){
			//根据组织机构代码查询是否存在相应的企业信息，如果存在，则把企业与注册用户关联，如果不存在，则先保存企业部分信息，然后再与用户关联
			EEnterpriseinfo einfo = null;
			List<EEnterpriseinfo> list = enterpriseInfoService.checkByOrganizationCode(organizationCode);
			if(DataUtil.listIsNotNull(list)){
				einfo = list.get(0);
				user.setNenterpriseid(einfo.getNenterpriseid()); //企业ID
			}else{
				einfo = new EEnterpriseinfo();
				einfo.setCorganizationcode(organizationCode);
				einfo.setCenterprisename(user.getEnterpriseName());
				einfo.setDcreatetime(DateUtil.getTimestamp());
				enterpriseInfoService.save(einfo);
				user.setNenterpriseid(einfo.getNenterpriseid()); //企业ID 
			}
			if(!flag){
				user.setIsManager(SystemCommon_Constant.SIGN_YES_1); //当该组织机构下不存在用户时，第一个注册用户即为管理员用户
			//	resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "注册成功，请等待系统审核！");
				resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "企业管理员注册成功，请等待系统管理员审核！");
				logger.error("注册成功，请等待系统审核！");
			}else{
				//管理员用户的用户名
				String managerName = clientUserService.getManagerName(organizationCode);
				user.setIsManager(SystemCommon_Constant.SIGN_YES_0); //非管理员用户
	//			resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "注册成功，请等待管理员审核！");
				resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "注册成功，请等待您所在企业管理员（"+managerName+"）审核！");
				logger.error("注册成功，请等待管理员审核！");
			}
			//用户名不存在，保存新用户
			user = clientUserService.saveNewUser(user);
			resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		}else{
			resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_FAIL);
			resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "用户名已存在");
		}
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, user);
		return resultMap;
	}
	
	
	
	/**
	 * 
	 * @Description: 找回密码 第一步：通过用户名找到用户基本信息
	 * @author YangHui 
	 * @Created 2014-8-27
	 * @param user
	 * @return
	 */
	@RequestMapping(value="getQuestionId",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getQuestionId(@RequestParam(value="cusername") String username){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		MMember m = clientUserService.getMemberByUserName(username);
		if(m!=null){
			resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
			resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, m.getNpasproqueid());
		}else{
			resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_FAIL);
			resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "用户名输入错误");
		}
		return resultMap;
	}
	/**
	 * 
	 * @Description: 找回密码 第二步：校验密保答案
	 * @author YangHui 
	 * @Created 2014-8-26
	 * @return
	 */
	@RequestMapping(value="findPassword",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> findPassword(ClientUserBean user){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		MMember m = clientUserService.getMemberByUserName(user.getCusername());
		if(m!=null){
			if(user.getCpassprotectanswer().equals(m.getCpassprotectanswer())){
				m.setCpassword(MD5.getMD5Code(user.getNewPassword()));
				clientUserService.update(m);
				resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
				resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "密码设置成功");
			}else{
				resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_FAIL);
				resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "密保回答错误"); 
			}
		}else{
			resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_FAIL);
			resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "用户名输入错误");
		}
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 修改密码
	 * @author YangHui 
	 * @Created 2014-8-26
	 * @return 
	 */
	@RequestMapping(value="updatePassword",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updatePassword(ClientUserBean user){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		MMember m = clientUserService.getMemberByUserName(user.getCusername());
		if(m!=null){
			if(m.getCpassword().equals(MD5.getMD5Code(user.getCpassword()))){
				m.setCpassword(MD5.getMD5Code(user.getNewPassword()));
				clientUserService.update(m);
				resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
				resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "密码修改成功");
			}else{
				resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_FAIL);
				resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "旧密码输入错误");
			}
		}
		return resultMap;
	}
	
}
