<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<jsp:include page="../../common/inc_defact.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<title>${butShowStr}缺陷信息采集</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<script type="text/javascript">
		$(function() {
			/* $("#infoCollection").click(function() {
				var url ='toReminderPage.action?defactType=${defactType}';
				window.location.href= url;
			});
			
			$("#estimationOfQuality").click(function() {
				
				alert('${defactType}');
			}); */
			
		})
	</script>

  </head>
  
  <body>
  	<div class="main">
		<h2 class="main_title"></h2>
		<div class="list_ul">
			<ul>
				<li id="infoCollection"><a href="toReminderPage.action?defectType=${defectType}">${butShowStr}缺陷信息采集</a></li>
				<li id="estimationOfQuality"><a href="${zxt_url}/survey/list4Weixin.action?industryId=${industryId}">${butShowStr}质量评价</a></li>
			</ul>
		</div>
	</div>
    
  </body>
</html>
