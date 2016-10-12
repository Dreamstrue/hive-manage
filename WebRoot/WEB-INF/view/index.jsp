<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
  <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico" media="screen" />
 	<title>质讯通-综合管理平台</title>
	<jsp:include page="common/inc.jsp" />
  </head>
  <body class="easyui-layout">
		<div data-options="region:'north',href:'${pageContext.request.contextPath}/layout/north.action'" style="height: 60px;overflow: hidden;" class="logo"></div>
		<div data-options="region:'west',title:'功能导航',href:'${pageContext.request.contextPath}/layout/west.action'" style="width: 200px;overflow: hidden;"></div>
		<div data-options="region:'center',href:'${pageContext.request.contextPath}/layout/center.action'" style="overflow: hidden;"></div>
		<div data-options="region:'south',href:'${pageContext.request.contextPath}/layout/south.action'" style="height: 27px;overflow: hidden;"></div>
   </body>
</html>
