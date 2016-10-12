package com.hive.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.permissionmanage.entity.Action;
import com.hive.permissionmanage.entity.User;
import com.hive.permissionmanage.service.ActionService;
import com.hive.permissionmanage.service.UserService;
import com.hive.util.DateUtil;
import com.hive.util.MD5EncrpytUtil;

import dk.controller.BaseController;

@Controller
@RequestMapping({"/security"})
public class SecurityController extends BaseController
{

	private static final String PREFIX = "permissionmanage/user";
  @Resource
  private UserService userService;
  @Resource
  private ActionService actionService;

 

  @RequestMapping({"/login"})
  @ResponseBody
  public Map<String, Object> login(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("userInputCheckCode") String userInputCheckCode, HttpServletRequest request, HttpSession session)
  {
    String code = (String)session.getAttribute("KAPTCHA_SESSION_KEY");
//    if ((userInputCheckCode == null) || (userInputCheckCode.trim().equals(""))) {
//      return error("请输入验证码");
//    }
//    if (!code.equals(userInputCheckCode)) {
//      return error("验证码错误！");
//    }
    User user = this.userService.check(username, password);
    if (user != null) {
//      Map map = new HashMap();
//      map.put("username", user.getUsername());
//      map.put("realname", user.getName());
//      map.put("roleid", user.getRoleid());
//      map.put("group", user.getLabel());
//      Role role = (Role)this.roleService.get(user.getRoleid());
//      map.put("rolename", role.getName());
//
//      List permissionList = this.permissionService.findAll();
//      Map permissionMap = new HashMap();
//      for (Permission permission : permissionList) {
//        permissionMap.put(permission.getId(), permission.getCode());
//      }
//      String[] permissions = StringUtils.split(role.getPermission(), ",");
//      Object codelist = new ArrayList();
//      if (permissions != null) {
//        for (String permissionid : permissions) {
//          ((List)codelist).add((String)permissionMap.get(permissionid));
//        }
//      }
//
//      map.put("permission", StringUtils.join((Iterable)codelist, ","));
//      String loginFlagName = ResourceUtil.getString("config", "loginFlagName");
//      session.setAttribute(loginFlagName, map);
    	session.setAttribute("userId", user.getId());
    	session.setAttribute("user", user);
    	session.setAttribute("loginTime", DateUtil.getTimestamp());
    	/** add by YangHui  2014-01-08  为了投诉项目共享session  begin */
    	ServletContext ContextA =session.getServletContext();
   // 	System.out.println("sessionId="+session.getId()+"----- ContextA="+ContextA.getContextPath());
    	ContextA.setAttribute("session", session); 
    	/** add by YangHui  2014-01-08  为了投诉项目共享session  end  */
    	// 把各种动作的值放到 Map 中（由于动作对应的值是动态从数据库中读取的，而不是定义的常量，所以如果系统权限配置好之后，删除了某个动作，然后又添加了新的动作，有可能会导致已配置的权限错乱而需要重新配置）
    	Map<String, Integer> actionTypeMap = new HashMap<String, Integer>();
    	List<Action> actionList = actionService.getActionList();
    	if (actionList != null && actionList.size() > 0) {
    		Action action = new Action();
    		for (int i = 0; i < actionList.size(); i++) {
    			action = actionList.get(i);
    			if ("add".equals(action.getCactionstr())) { // 注意：这里的 add 是写死的，是需要与数据库 P_ACTION 表中动作字符串一致的。将来添加新的动作，需要在这儿加一个 else if，然后在 inc.jsp 中加两行代码
    				actionTypeMap.put("ACTION_ADD", action.getIactionvalue()); // jsp 中“添加”的值都用 actionTypeMap.get("ACTION_ADD") 运算
    				//continue; // 对于 if... else if ... 语句块，只要进入了某个 if，后面的 if 就都不会再进行判断了，直接跳到整个语句块末尾，所以就不需要在每个 if 里面写 continue 了
				} else if ("edit".equals(action.getCactionstr())) { 
    				actionTypeMap.put("ACTION_EDIT", action.getIactionvalue());
				} else if ("delete".equals(action.getCactionstr())) { 
    				actionTypeMap.put("ACTION_DELETE", action.getIactionvalue()); 
				} else if ("audit".equals(action.getCactionstr())) { 
    				actionTypeMap.put("ACTION_AUDIT", action.getIactionvalue()); 
				} else if ("view".equals(action.getCactionstr())) { 
    				actionTypeMap.put("ACTION_VIEW", action.getIactionvalue()); 
				} else if ("live".equals(action.getCactionstr())) { 
    				actionTypeMap.put("ACTION_LIVE", action.getIactionvalue()); 
				} else if ("bind".equals(action.getCactionstr())) { 
    				actionTypeMap.put("ACTION_BIND", action.getIactionvalue()); 
				} else if ("editPassword".equals(action.getCactionstr())) { 
    				actionTypeMap.put("ACTION_EDITPASSWORD", action.getIactionvalue()); 
				}
			}
		}
    	session.setAttribute("actionTypeMap", actionTypeMap);
    	
    	session.setAttribute("isAdmin", user.getAdmin());
    	
    	Map<String, Integer> userMenuActionMap = userService.getUserMenuActionMapByUserId(user.getId());
    	session.setAttribute("userMenuActionMap", userMenuActionMap);
    	
      return success("登录成功");
    }
    return error("登录失败,用户名或密码错误！");
  }

  @RequestMapping({"/editUser"})
  public String editUser(Model model, @RequestParam("id") Long id)
  {
    model.addAttribute("vo", this.userService.get(id));
    return PREFIX+"/userinfo";
  }
  @RequestMapping({"/updateUser"})
  @ResponseBody
  public Map<String, Object> updateUser(User user) {
    User u = (User)this.userService.get(user.getId());
    u.setFullname(user.getFullname());
    u.setMobilephone(user.getMobilephone());
    u.setEmail(user.getEmail());
    this.userService.update(u);
    return success("个人信息更新成功！", u);
  }

  @RequestMapping({"/editPassword"})
  public String editPassword(Model model, @RequestParam("id") Long id)
  {
    model.addAttribute("vo", this.userService.get(id));
    return PREFIX+"/editUserPassword";
  }
  @RequestMapping({"/updatePassword"})
  @ResponseBody
  public Map<String, Object> updatePassword(User user, @RequestParam("oldpassword") String oldpassword) {
    User u = (User)this.userService.get(user.getId());
    oldpassword = MD5EncrpytUtil.md5Encrypt(oldpassword, "md5");
    if (oldpassword.equals(u.getPassword())) {
    	String newPwd = MD5EncrpytUtil.md5Encrypt(user.getPassword(), "md5");
      u.setPassword(newPwd);
      this.userService.update(u);
      return success("密码修改成功！", u);
    }
    return error("旧密码错误！");
  }

  @RequestMapping({"/logout"})
  public String logout(HttpSession session)
  {
    if (session != null) {
      session.invalidate();
    }
    return "redirect:/";
  }
//
//  @RequestMapping({"/noSecurity"})
//  public String noSecurity()
//  {
//    return "error/noSecurity";
//  }
//
//  @RequestMapping({"/noSession"})
//  public String noSession()
//  {
//    return "error/noSession";
//  }
//
//  @RequestMapping({"/blank"})
//  public String blank()
//  {
//    return "error/blank";
//  }
  

  
}