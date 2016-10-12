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
   			var userId = ${userId};
   			$('#integral_detail_datagrid').datagrid({
   				url:'${appPath}/integralOil/integralDetailDataGrid.action?userId='+userId,
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
   					field:'integralDate',
   					title:'积分日期',
   					width:150,
   					align:'center',
   					formatter:function(value,row,index){
   						return formatDate(value);
   					}
   				}]],
   				columns:[[{
   					field:'id',
   					title:'ID',
   					width:50,
   					hidden:true
   				},{
   					field:'entityCategory',
   					title:'实体类别',
   					align:'center',
   					width:80
   				},{
   					field:'industryEntity',
   					title:'实体名称',
   					align:'center',
   					width:100
   				},{
   					field:'integralType',
   					title:'类型',
   					width:80,
   					align:'center',
   					formatter:function(value,row,index){
   						if(value==1){
   							return '获取';
   						}
   						if(value==2){
   							return '消费';
   						}
   					}
   				},{
   					field:'integralValue',
   					title:'积分',
   					align:'center',
   					width:50
   				},{
   					field:'remark',
   					title:'说明',
   					width:150,
   					align:'center',
   					formatter:function(value,row,index){
   						if(value==2){
   							return '评论';
   						}else if(value==3){
   							return '分享';
   						}else{
   							return value;
   						}
   					}
   				},{
   					field:'summary',
   					title:'详情',
   					align:'center',
   					width:80
   				}]],
   				toolbar:[{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#integral_detail_datagrid').datagrid('reload');
   					}
   				}]
   			});
   		});
   	
	</script>
	<div style="height:350px;width:100%;">
		<table id="integral_detail_datagrid"></table>
	</div>
</body>
</html>
