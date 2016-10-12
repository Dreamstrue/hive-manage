<%@ page language="java" import="java.util.Map;" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%
	// 在jsp中使用${appPath}取出应用上下文路径，形如：/zyzlcxw
	String appPath = request.getContextPath();
	session.setAttribute("appPath", appPath);
	
	//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path; // 项目访问全路径，形如：http://localhost:8080/zyzlcxw
	boolean isLinux = "/".equals(java.io.File.separator); // 根据分隔符判断操作系统（windows是\，unix是/）
	String basePath = request.getScheme()+"://"+request.getServerName()+(isLinux ? "" : ":"+request.getServerPort())+appPath; // 前台项目直接放在 tomcat 的 ROOT 目录下了，就不需要端口号了
	session.setAttribute("basePath", basePath);
%>

<meta http-equiv="content-type" content="text/html;charset=UTF-8"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>    
<!-- my97日期控件 -->
<script type="text/javascript" src="${appPath}/resources/js/plugin/My97DatePicker/WdatePicker.js" charset="utf-8"></script>
<!-- easyui控件 -->
<!-- <link id="easyuiTheme" rel="stylesheet" href="${appPath}/resources/js/plugin/jquery-easyui/themes/<c:out value="${cookie.easyuiThemeName.value}" default="default"/>/easyui.css" type="text/css"></link> -->
<!-- <link rel="stylesheet" href="${appPath}/resources/js/plugin/jquery-easyui/themes/icon.css" type="text/css"></link> -->
<!-- <script type="text/javascript" src="${appPath}/resources/js/plugin/jquery-easyui/jquery-1.8.0.min.js" charset="utf-8"></script> -->
<!-- <script type="text/javascript" src="${appPath}/resources/js/plugin/jquery-easyui/jquery.easyui.min.js" charset="utf-8"></script> -->
<!-- <script type="text/javascript" src="${appPath}/resources/js/plugin/jquery-easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script> -->
<link id="easyuiTheme" rel="stylesheet" href="${appPath}/resources/js/jquery-easyui/themes/<c:out value="${cookie.easyuiThemeName.value}" default="default"/>/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="${appPath}/resources/js/jquery-easyui/themes/icon.css" type="text/css"></link>
<script type="text/javascript" src="${appPath}/resources/js/jquery-easyui/jquery-1.11.2.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${appPath}/resources/js/jquery-easyui/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${appPath}/resources/js/jquery-easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<script type="text/javascript" src="${appPath}/resources/js/jquery-easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="${appPath}/resources/js/jquery-easyui/jquery.edatagrid.js"></script>
<!-- easyui portal插件 -->
<link rel="stylesheet" href="${appPath}/resources/js/plugin/jquery-easyui-portal/portal.css" type="text/css"></link>
<script type="text/javascript" src="${appPath}/resources/js/plugin/jquery-easyui-portal/jquery.portal.js" charset="utf-8"></script>

<!-- textarea 中的富编辑 -->
<link rel="stylesheet" href="${appPath}/resources/js/plugin/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="${appPath}/resources/js/plugin/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="${appPath}/resources/js/plugin/kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="${appPath}/resources/js/plugin/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="${appPath}/resources/js/plugin/kindeditor/plugins/code/prettify.js"></script>
<!-- 自己定义的样式和JS扩展 -->
<link rel="stylesheet" href="${appPath}/resources/css/syCss.css" type="text/css"></link>
<link rel="stylesheet" href="${appPath}/resources/css/icon.css" type="text/css"></link>

<script type="text/javascript" src="${appPath}/resources/js/common/common.js" charset="utf-8"></script>
<script type="text/javascript" src="${appPath}/resources/js/common/jquery.cookie.js" charset="utf-8"></script>
<script type="text/javascript" src="${appPath}/resources/js/common/util.js" charset="utf-8"></script>
<script type="text/javascript" src="${appPath}/resources/js/common/DateUtil.js"></script>
<script type="text/javascript" src="${appPath}/resources/js/jquery.form.js"></script>
<script type="text/javascript" src="${appPath}/resources/js/common/industryEntity.js"></script><!-- 20160822 yyf add -->
<!-- <script type="text/javascript" src="${appPath}/resources/js/plugin/jquery-easyui/jquery.edatagrid.js"></script> -->
<!-- <script type="text/javascript" src="${appPath}/resources/js/plugin/jquery-easyui/datagrid-detailview.js"></script> -->
<%-- 页面按钮级权限判断（由于 el 表达式不支持 “按位与 & 运算”，只能在 java 里完成判断了） --%>
<%
	// 登录页面也引用的有这个 jsp，执行下面的代码会报空指针，所以需要判断下
	if (session.getAttribute("user") != null) {
		// 只有非管理员才需要判断这些权限
		if (!"1".equals(session.getAttribute("isAdmin"))) {
			String menuId = request.getParameter("id"); // 获取页面参数中的菜单 id（Jsp 中可通过 ${param.id} 获得）
			// 登录成功后进来的首页，没有点击任何菜单，是没有 menuId 的，执行下面的代码会报空指针，所以需要判断下
			if (menuId != null && !"".equals(menuId)) {
				// 获取当前用户对该菜单的权限集合
				Integer actionValues = (Integer)((Map)session.getAttribute("userMenuActionMap")).get(menuId);
				// 如果用户对某个菜单没有权限，那么 map 中就没有存放该菜单，取出来的 actionValues 为 null，下面进行位运算会报空指针，所以需要判断下
				if (actionValues != null) {
					// 获取动作值
					Integer action_add = (Integer)((Map)session.getAttribute("actionTypeMap")).get("ACTION_ADD");
					Integer action_edit = (Integer)((Map)session.getAttribute("actionTypeMap")).get("ACTION_EDIT");
					Integer action_delete = (Integer)((Map)session.getAttribute("actionTypeMap")).get("ACTION_DELETE");
					Integer action_audit = (Integer)((Map)session.getAttribute("actionTypeMap")).get("ACTION_AUDIT");
					Integer action_view = (Integer)((Map)session.getAttribute("actionTypeMap")).get("ACTION_VIEW");
					Integer action_live = (Integer)((Map)session.getAttribute("actionTypeMap")).get("ACTION_LIVE");
					Integer action_bind = (Integer)((Map)session.getAttribute("actionTypeMap")).get("ACTION_BIND");
					Integer action_editPassword = (Integer)((Map)session.getAttribute("actionTypeMap")).get("ACTION_EDITPASSWORD");
					
					// 计算权限，并放到 request 中，供下面 el 表达式判断用
					// 如果数据库表 P_ACTION 中不存在某个动作或者被删除了，那么上面取出来的动作值就为 null，下面进行位运算会报空指针，所以需要判断下
					if (action_add != null)
						request.setAttribute("canAdd", (actionValues & action_add) > 0);
					if (action_edit != null)
						request.setAttribute("canEdit", (actionValues & action_edit) > 0);
					if (action_delete != null)
						request.setAttribute("canDelete", (actionValues & action_delete) > 0);
					if (action_audit != null)
						request.setAttribute("canAudit", (actionValues & action_audit) > 0);
					if (action_view != null)
						request.setAttribute("canView", (actionValues & action_view) > 0);
					if (action_live != null)
						request.setAttribute("canLive", (actionValues & action_live) > 0);
					if (action_live != null)
						request.setAttribute("canBind", (actionValues & action_bind) > 0);
					if (action_live != null)
						request.setAttribute("canEditPassword", (actionValues & action_editPassword) > 0);
				}
			}
		}
	}
%>