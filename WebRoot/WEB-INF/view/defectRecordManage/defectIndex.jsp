<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="../common/inc_defact.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<title>缺陷信息采集</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
  	<div class="main">
		<h2 class="main_title"></h2>
		<div class="list_ul">
			<ul>
				<li id="carDefect"><a href="${appPath}/defectCar/toDefectCarTips.action">汽车缺陷信息采集</a></li>
				<li id="childToyDefect"><a href="${appPath}/defectOfToyAndOthers/toReminderPage.action?defectType=toy">儿童玩具缺陷信息采集</a></li>
				<li id="otherDefect"><a href="${appPath}/defectOfToyAndOthers/toReminderPage.action?defectType=other">其他消费品缺陷信息采集</a></li>
			</ul>
		</div>
	</div>
    
  </body>
</html>
