<%@ page language="java" import="java.util.Map;" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%
	// 在jsp中使用${appPath}取出应用上下文路径，形如：/zyzlcxw
	String appPath = request.getContextPath();
	session.setAttribute("appPath", appPath);
%>
<script type="text/javascript" src="${appPath}/resources/js/plugin/jquery-easyui/jquery-1.8.0.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${appPath}/resources/js/plugin/jquery-easyui/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${appPath}/resources/js/plugin/jquery-easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<script type="text/javascript" src="${appPath}/resources/defect/js/defectToyOtherValidate.js" charset="utf-8"></script>
<link rel="stylesheet" href="${appPath}/resources/defect/css/common.css" type="text/css"></link>
<link rel="stylesheet" href="${appPath}/resources/defect/css/index.css" type="text/css"></link>
