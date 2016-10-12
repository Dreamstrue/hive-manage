<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息公告管理</title>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<style type="text/css">
body{margin:0px;padding:0px;}
.searchDiv{
	overflow: hidden;
	height: 60px; 
	border-bottom: 1px solid #99CCFF;
}
.tt{
	font-size: 12px;
	text-align: right;
}
</style>
</head>

<body>
	<script type="text/javascript">
   		$(function(){
   			$('#notiecread_detail_datagrid').datagrid({
   				url:'${appPath}/notice/readDetailDataGrid.action?id='+${id},
   				idField:'id',
   				fit:true,
   				fitColumns:true,
   				pagination:true,
   				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50 ],
   				rownumbers:true,
   				border:false,
   				animate:false,
   				frozenColumns:[[{
   					field:'userName',
   					title:'用户名',
   					width:150,
   					align:'center'
   				}]],
   				columns:[[{
   					field:'userId',
   					title:'USERID',
   					width:50,
   					hidden:true
   				},{
   					field:'receiveDate',
   					title:'阅读时间',
   					width:40,
   					align:'center',
   					formatter:function(value,row,index){
   						return formatDate(value);
   					}
   				}]],
   				toolbar:[{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#notiecread_detail_datagrid').datagrid('reload');
   					}
   				}]
   			});
   		});
   		
	</script>
	<div style="height:250px;width:100%;">
		<table id="notiecread_detail_datagrid"></table>
	</div>
</body>
</html>
