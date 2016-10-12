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
   			$('#subscription_detail_datagrid').datagrid({
   				url:'${appPath}/subscription/subdetailDataGrid.action?userId='+${userId},
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
				title : '编号',
				field : 'id',
				width : 10,
				checkbox : true
			},{
   					field:'userName',
   					title:'用户名',
   					width:150,
   					align:'center'
   				},{
   					field:'infoCateName',
   					title:'分类名称',
   					width:120,
   					align:'center'
   				}]],
   				columns:[[{
   					field:'subTime',
   					title:'订阅时间',
   					width:40,
   					align:'center'
   				},{
   					field:'action',
   					title:'操作',
   					width:20,
   					align:'center',
   					formatter:function(value,row,index){
   						var html = "";
   						html += '<a href="javascript:unsubscript('+ row.id +')">退订</a>';
   						return html;
   					}
   				}]],
   				toolbar:[{
   					text:'批量退订',
   					iconCls:'icon-remove',
   					handler:function(){
   						manage_unsubAll_fn();
   					}
   				},{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#subscription_detail_datagrid').datagrid('reload');
   					}
   				}]
   			});
   		});
   		
   		function manage_unsubAll_fn(){
   			var rows = $('#subscription_detail_datagrid').datagrid('getChecked');
   			var ids = [];
   			if(rows.length>0){
   				$.messager.confirm('提示','您确认要退订当前选择的记录吗？',function(b){
   					if(b){
   						for(var i=0;i<rows.length;i++){
   							ids.push(rows[i].id);
   						}
   						var url='${appPath}/subscription/unsubInfo.action';
   						$.post(url,{ids:ids.join(',')},function(data){ //注意：这里的ids.join(',')方法是为了把数组变成字符串，否则会报参数类型不匹配错误
   							if(data.status){
   								$('#subscription_detail_datagrid').datagrid('reload');
   								$('#subscription_detail_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
   							}
   							$.messager.show({
   								msg:data.msg,
   								title:'提示'
   							});
   						},"json");
   					}
   				});
   			}else{
   				$.messager.show({
					title : '提示',
					msg : '请勾选要退订的记录！'
				});
   			}
   		}
   		
   		
   		function unsubscript(id){
   			$('#subscription_detail_datagrid').datagrid('selectRecord',id);
   				var record = $('#subscription_detail_datagrid').datagrid('getSelected');
   				$.messager.confirm('提示','您确定要退订【'+record.infoCateName+'】类信息?',function(b){
   					if(b){
   						var url = '${appPath}/subscription/unsubInfo.action';
   						$.post(url,{ids:id},function(data){
   							if(data.status){
   								$('#subscription_detail_datagrid').datagrid('reload');
   							}
   							$.messager.show({
   								msg:data.msg,
   								title:'提示'
   							});
   						},"json");
   					}
   				});
   		}
   		
   		
   		function detail_manage_search_fn() {
			$('#subscription_detail_datagrid').datagrid('load', serializeObject($('#detail_manage_search_form')));
		}
		function cleanForm() {
			$('#detail_manage_search_form input[class="inval"]').val('');
			$('#infoCateId').combobox('clear');
			$('#subscription_detail_datagrid').datagrid('load', {});
		}
	</script>
	<div class="searchDiv">
		<form action="javascript:void(0);" id="detail_manage_search_form" >
			<table class="form" style="width: 100%;height:60px;">
				<tr>
					<td class="tt">订阅分类</td>
					<td>
						<input  id="infoCateId" name="infoCateId" class="easyui-combotree" data-options="url:'${appPath}/infoCate/allInfoTree.action',parentField:'parentId',lines:true " style="width:150px;" />
					</td>
					<td class="tt" width="80">订阅日期</td>
					<td width="160">
						<input class="inval" type='text' name=subTime id='subTime' style='width:150px'  class='intxt' readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  />
					</td>
					<td>
						<input type="button" class="easyui-linkbutton" value="查询" onclick="detail_manage_search_fn();">
						<input type="button" class="easyui-linkbutton" value="重置" onclick="cleanForm();">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div style="height:250px;width:100%;">
		<table id="subscription_detail_datagrid"></table>
	</div>
</body>
</html>
