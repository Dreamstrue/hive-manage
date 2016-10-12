<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>积分管理</title>
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
   			$('#integral_manage_datagrid').datagrid({
   				url:'${appPath}/integral/integralDataGrid.action',
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
					field : 'chineseName',
					title : '姓名',
					width : 80
				}]],
   				columns:[[{
   					field:'userName',
   					title:'系统账号',
   					width:60,
   					align:'center'
   				},{
   					field:'userId',
   					title:'userId',
   					width:50,
   					hidden:true
   				},{
   					field:'mobilePhone',
   					title:'手机号',
   					width:50,
   					align:'center'
   				},{
   					field:'idCard',
   					title:'身份证',
   					width:60,
   					align:'center'
   				},{
   					field:'anonymousFlag',
   					title:'用户类型',
   					width:40,
   					align:'center'
   				},{
   					field:'totalIntegral',
   					title:'总积分',
   					width:50,
   					align:'center'
   				},{
   					field:'currentIntegral',
   					title:'当前积分',
   					align:'center',
   					width:50
   				},{
   					field:'usedIntegral',
   					title:'已消费积分',
   					width:50,
   					align:'center',
   					formatter:function(value,row,index){
   						if(value>0){
   							var html = "";
	   						html += '<a href="javascript:usedDetail('+ row.userId +')">'+value+'</a>';
	   						return html;
   						}else{
   							return value;
   						}
   					}
   				},{
   					field:'action',
   					title:'操作',
   					width:50,
   					align:'center',
   					formatter:function(value,row,index){
   							var html = "";
	   						html += '<a href="javascript:integralDetail('+ row.userId +')">积分明细</a>';
	   						return html;
   					}
   				}]],
   				toolbar:[{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#integral_manage_datagrid').datagrid('reload');
   					}
   				}]
   			});
   		});
   		
   		
   		//消费明细
   		function usedDetail(userId){
   			$('<div/>').dialog({
				href : '${appPath}/integral/usedDetail.action?userId='+userId,
				width :880,
				height : 400,
				modal : true,
				title : '消费明细',
				onClose : function() {
					$(this).dialog('destroy');
				}
			});
   		}
   		
   		//积分明细
   		function integralDetail(userId){
   			$('<div/>').dialog({
				href : '${appPath}/integral/integralDetail.action?userId='+userId,
				width :880,
				height : 400,
				modal : true,
				title : '积分明细',
				onClose : function() {
					$(this).dialog('destroy');
				}
			});
   		}
   		
   		
   		
   		
   		function integral_manage_search_fn() {
			$('#integral_manage_datagrid').datagrid('load', serializeObject($('#integral_manage_search_form')));
		}
		function cleanForm() {
			$('#integral_manage_search_form input[class="inval"]').val('');
			$('#integral_manage_datagrid').datagrid('load', {});
		}
	</script>
	<div class="searchDiv">
		<form action="javascript:void(0);" id="integral_manage_search_form" >
			<table class="form" style="height:10%;">
				<tr>
					<td class="tt" width="80">用户名</td>
					<td width="160"><input class="inval" name="cusername" style="width:150px;"></td>
					<td class="tt" width="80">手机号</td>
					<td width="160"><input class="inval" name="cmobilephone" style="width:150px;"></td>
					<td class="tt" width="80">身份证</td>
					<td width="160"><input class="inval" name="cardno" style="width:150px;"></td>
					<td class="tt" width="80">用户类型</td>
					<td width="160">
					<select id="cc" class="easyui-combobox" name="cmembertype" style="width:150px;">
					    <option value="2">匿名</option>
					    <!-- <option value="1">企业</option> -->
					    <option value="0">个人</option>
					</select>
					</td>
				</tr>
				<tr align="center">
					<td colspan="8">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="integral_manage_search_fn();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="cleanForm();">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div style="height:90%;">
		<table id="integral_manage_datagrid"></table>
	</div>
</body>
</html>
