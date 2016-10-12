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
	
		var prizeId = ${prizeId};
		
   		$(function(){
   			$('#prizeInfo_number_datagrid').datagrid({
   				url:'${appPath}/prizeInfo/recordDataGrid.action?prizeId='+prizeId,
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
   					field:'prizeName',
   					title:'奖品名称',
   					width:130,
   					align:'center'
   				},{
   					field:'prizeId',
   					title:'prizeId',
   					width:10,
   					hidden:true
   				}]],
   				columns:[[{
   					field:'id',
   					title:'ID',
   					width:30,
   					hidden:true
   				},{
   					field:'userName',
   					title:'补货人',
   					width:30,
   					align:'center'
   				},{
   					field:'prizeNum',
   					title:'补货数量',
   					width:30,
   					align:'center'
   				},{
   					field:'createTime',
   					title:'补货时间',
   					width:40,
   					align:'center',
   					formatter:function(value,row,index){
   						return formatDate(value);
   					}
   				}/* ,{
   					field:'action',
   					title:'操作',
   					width:20,
   					align:'center',
   					formatter:function(value,row,index){
   						var html = "";
   						html += '<img title="修改" onclick="prize_manage_update_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/pencil.png"/>';
   						html += '&nbsp;<img title="补货" onclick="prize_manage_addNum_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/cart.jpg"/>';
   						return html;
   					}
   				} */]],
   				toolbar:[{
   					text:'新增',
   					iconCls:'icon-add',
   					handler:function(){
   						prize_number_manage_add_fn(prizeId);
   					}
   				},{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#prizeInfo_number_datagrid').datagrid('reload');
   					}
   				}]
   			});
   		});
   		
   		
   		//新增奖品
   		function prize_number_manage_add_fn(prizeId){
   			$('<div/>').dialog({
				href : '${appPath}/prizeInfo/subRecordAdd.action?prizeId='+prizeId,
				width :350,
				height : 180,
				modal : true,
				title : '新增数量',
				buttons : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#prize_number_add_form').form('submit', {
						url : '${appPath}/prizeInfo/subRecordInsert.action',
						success : function(result) {
							var r = $.parseJSON(result);
							if (r.status) {
							$('#prizeInfo_number_datagrid').datagrid('reload');
								d.dialog('destroy');
							}
							$.messager.show({
								title : '提示',
								msg : r.msg
							});
						}
					});
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			}
			});
   		}
   		
   		
   		function prize_manage_update_fn(id){
   			location.href='${appPath}/prizeInfo/edit.action?id='+id;
   		}
   		
   		
   		function prize_num_manage_search_fn() {
			$('#prizeInfo_number_datagrid').datagrid('load', serializeObject($('#prize_number_manage_search_form')));
		}
		function cleanForm() {
			$('#prize_number_manage_search_form input[class="inval"]').val('');
			$('#prizeInfo_number_datagrid').datagrid('load', {});
		}
	</script>
	<div class="searchDiv">
		<form action="javascript:void(0);" id="prize_number_manage_search_form" >
			<table class="form" style="height:60px;">
				<tr>
					<td class="tt" >开始日期</td>
					<td>
						<input name='beginDate' id='beginDate' style='width:150px'  class="inval"  readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  />
					</td>
					<td class="tt" >结束日期</td>
					<td>
						<input name='endDate' id='endDate' style='width:150px'  class="inval"  readonly="readonly" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'beginDate\')}'})"  />
					</td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="prize_num_manage_search_fn();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="cleanForm();">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div style="height:82%;width:100%;">
		<table id="prizeInfo_number_datagrid"></table>
	</div>
</body>
</html>
