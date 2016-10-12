<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<link rel="stylesheet" href="${appPath}/resources/css/info.css" type="text/css"></link>
<script type="text/javascript">
	var liveContent = "";
	function myrefresh() { 
		$.ajax({ 
	        type : "post", 
	        url : "${appPath}/interviewContent/getLiveContent.action?interviewId=${interviewId}", 
	        data : "", 
	        dataType : "json", 
	        async : false, 
	        success : function(data){ 
	          liveContent = data.msg;
	        } 
	    }); 
		$("#liveContent").html(liveContent);
		window.scrollTo(0, $("#liveContent").height()); // 让滚动条到最下面，方便看到最新内容
		setTimeout("myrefresh()", 1000*5); // 循环调用自己才能实现不断定时请求
	} 
	myrefresh(); // 初始化
</script>
									<div id="liveContent" style="width:100%;border:1px solid silver;word-wrap: break-word; word-break: normal;">直播内容加载中……</div>
