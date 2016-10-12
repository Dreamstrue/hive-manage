<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息订阅</title>
<jsp:include page="../common/inc.jsp"></jsp:include>
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
   			$('#subscription_datagrid').datagrid({
   				url:'${appPath}/subscription/subDataGrid.action',
   				idField:'id',
   				fit:true,
   				fitColumns:true,
   				pagination:true,
   				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50 ],
   				rownumbers:true,
   				border:false,
   				animate:false,
   				singleSelect:true,
   				frozenColumns:[[{
   					field:'userName',
   					title:'用户名',
   					width:400
   				},{
   					field:'count',
   					title:'数量',
   					width:400
   				}]],
   				columns:[[{
   					field:'userId',
   					title:'USERID',
   					width:50,
   					hidden:true
   				},{
   					field:'action',
   					title:'操作',
   					width:20,
   					align:'center',
   					formatter:function(value,row,index){
   						var html = "";
   						html += '<a href="javascript:showDetail('+ row.userId +')">查看</a>';
   						return html;
   					}
   				}]],
   				toolbar:[
   				{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#subscription_datagrid').datagrid('reload');
   					}
   				}]
   			});
   		});
   		
   		
   		function showDetail(userId){
   			$('<div/>').dialog({
				href : '${appPath}/subscription/subDetail.action?userId='+userId,
				width :700,
				height : 350,
				modal : true,
				title : '订阅明细',
				onClose : function() {
					$(this).dialog('destroy');
				}
			});
   		}
   		
   		function sub_manage_search_fn() {
			$('#subscription_datagrid').datagrid('load', serializeObject($('#sub_manage_search_form')));
		}
		function cleanForm() {
			$('#sub_manage_search_form input[class="inval"]').val('');
			$('#subscription_datagrid').datagrid('load', {});
		}
	</script>
	<div class="searchDiv">
		<form action="javascript:void(0);" id="sub_manage_search_form" >
			<table class="form" style="width: 100%;height:60px;">
				<tr>
					<td class="tt" width="80">用户名</td>
					<td width="160">
						<input class="inval" name="userName" style="width:150px;">
					</td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="sub_manage_search_fn();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="cleanForm();">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div style="height:393px;width:100%;">
		<table id="subscription_datagrid"></table>
	</div>
</body>
</html>
