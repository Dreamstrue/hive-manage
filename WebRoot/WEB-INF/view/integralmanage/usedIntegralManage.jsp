<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>积分管理</title>
<jsp:include page="../common/inc.jsp"></jsp:include>
<style type="text/css">
body{margin:0px;padding:0px;}
</style>
</head>

<body>
	<script type="text/javascript">
   		$(function(){
   			$('#used_integral_manage_datagrid').datagrid({
   				url:'${appPath}/integral/usedIntegralDataGrid.action?userId='+${userId},
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
   					field:'id',
   					title:'ID',
   					width:50,
   					hidden:true
   				},{
   					field:'intendNo',
   					title:'订单号',
   					width:80,
   					align:'center'
   				},{
   					field:'prizeName',
   					title:'奖品名称',
   					align:'center',
   					width:70
   				},{
   					field:'prizeNum',
   					title:'奖品数量',
   					width:30,
   					align:'center'
   				},{
   					field:'excIntegral',
   					title:'消费积分',
   					align:'center',
   					width:30
   				},{
   					field:'applyTime',
   					title:'消费时间',
   					align:'center',
   					width:70
   				}]],
   				toolbar:[{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#used_integral_manage_datagrid').datagrid('reload');
   					}
   				}]
   			});
   		});
   	
	</script>
	<div style="height:350px;width:100%;">
		<table id="used_integral_manage_datagrid"></table>
	</div>
</body>
</html>
