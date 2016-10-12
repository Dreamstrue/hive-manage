<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息公告管理</title>
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
   			$('#statistics_datagrid').datagrid({
   				url:'${appPath}/subscription/staDataGrid.action',
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
   					field:'infoCateName',
   					title:'分类名称',
   					width:400
   				},{
   					field:'subCount',
   					title:'订阅次数',
   					width:400
   				},{
   					field:'unsubCount',
   					title:'退订次数',
   					width:400
   				}]],
   				toolbar:[
   				{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#statistics_datagrid').datagrid('reload');
   					}
   				}]
   			});
   		});
   		
   		
   		
   		
   		
   		function statistics_manage_search_fn() {
			$('#statistics_datagrid').datagrid('load', serializeObject($('#statistics_manage_search_form')));
		}
		function cleanForm() {
			$('#statistics_manage_search_form input[class="inval"]').val('');
			$('#infoCateId').combotree('clear');
			$('#statistics_datagrid').datagrid('load', {});
		}
	</script>
	<div class="searchDiv">
		<form action="javascript:void(0);" id="statistics_manage_search_form" >
			<table class="form" style="height:60px;">
			<tr>
					<td class="tt" width="80">开始日期</td>
					<td width="160">
						<input class="inval" type='text' name="beginDate" id='beginDate' style='width:150px'  class='intxt' readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  />
					</td>
					<td class="tt" width="80">结束日期</td>
					<td width="160">
						<input class="inval" type='text' name='endDate' id='endDate' style='width:150px'  class='intxt' readonly="readonly" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'beginDate\')}'})"  />
					</td>
				</tr>
				<tr>
					<td class="tt">订阅分类</td>
					<td>
						<input  id="infoCateId" name="infoCateId" class="easyui-combotree" data-options="url:'${appPath}/infoCate/allInfoTree.action',parentField:'parentId',lines:true" style="width:150px;" />
					</td>
					<td></td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="statistics_manage_search_fn();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="cleanForm();">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div style="height:393px;width:100%;">
		<table id="statistics_datagrid"></table>
	</div>
</body>
</html>
