<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>奖品管理</title>
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
   			$('#prizeInfo_datagrid').datagrid({
   				url:'${appPath}/prizeInfo/dataGrid.action',
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
   					width:200,
   					align:'center'
   				},{
   					field:'prizeCateName',
   					title:'奖品类别',
   					width:130,
   					align:'center'
   				},{
   					field:'entityCategory',
   					title:'提供方',
   					width:130,
   					align:'center'
   				}]],
   				columns:[[{
   					field:'id',
   					title:'ID',
   					width:30,
   					hidden:true
   				},{
   					field:'prizeNum',
   					title:'总数量',
   					width:20,
   					align:'center'
   				},{
   					field:'excNum',
   					title:'已兑换数量',
   					width:20,
   					align:'center'
   				},{
   					field:'excIntegral',
   					title:'所需积分',
   					width:30,
   					align:'center'
   				},{
   					field:'validDate',
   					title:'有效期',
   					width:40,
   					align:'center'
   				},{
   					field:'action',
   					title:'操作',
   					width:20,
   					align:'center',
   					formatter:function(value,row,index){
   						var html = "";
   						html += '<img title="修改" onclick="prize_manage_update_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/pencil.png"/>';
   						html += '&nbsp;<img title="补货" onclick="prize_manage_addNum_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/cart.jpg"/>';
   						if(row.excNum==0){ //说明该奖品还没有发生兑换
   							html += '&nbsp;<img title="删除" onclick="prize_manage_delete_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/cancel.png"/>';
   						}
   						return html;
   					}
   				}]],
   				toolbar:[{
   					text:'增加新奖品',
   					iconCls:'icon-add',
   					handler:function(){
   						prize_manage_add_fn();
   					}
   				},{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#prizeInfo_datagrid').datagrid('reload');
   					}
   				}]
   			});
   		});
   		
   		
   		//新增奖品
   		function prize_manage_add_fn(){
   			location.href = '${appPath}/prizeInfo/add.action';
   		}
   		
   		//修改奖品信息
   		function prize_manage_update_fn(id){
   			location.href='${appPath}/prizeInfo/edit.action?id='+id;
   		}
   		
   		//删除奖品信息
   		function prize_manage_delete_fn(id){
   			if(id!=undefined){
   				$('#prizeInfo_datagrid').datagrid('selectRecord',id);
   				var record = $('#prizeInfo_datagrid').datagrid('getSelected');
   				$.messager.confirm('提示','确定删除奖品【'+record.prizeName+'】?',function(b){
   					if(b){
   						var url = '${appPath}/prizeInfo/delete.action';
   						$.post(url,{id:id},function(data){
   							if(data.status){
   								$('#prizeInfo_datagrid').datagrid('reload');
   							}
   							$.messager.show({
   								msg:data.msg,
   								title:'提示'
   							});
   						},"json");
   					}
   				});
   			}
   		}
   		
   		function prize_manage_addNum_fn(id){
   			$('<div/>').dialog({
				href : '${appPath}/prizeInfo/subRecord.action?id='+id,
				width :700,
				height : 400,
				modal : true,
				title : '补货明细',
				onClose : function() {
					$(this).dialog('destroy');
				}
			});
   		}
   		
   		function prize_manage_search_fn() {
			$('#prizeInfo_datagrid').datagrid('load', serializeObject($('#prize_manage_search_form')));
		}
		function cleanForm() {
			$('#prize_manage_search_form input[class="inval"]').val('');
			$('#prizeCateId').combotree('clear');
			$('#prizeInfo_datagrid').datagrid('load', {});
		}
	</script>
	<div class="searchDiv">
		<form action="javascript:void(0);" id="prize_manage_search_form" >
			<table class="form" style="height:60px;">
				<tr>
					<td class="tt" width="80">奖品名称</td>
					<td width="160">
						<input class="inval" name="prizeName" style="width:150px;">
					</td>
					<td class="tt" width="80">奖品类别</td>
					<td width="160">
						<input  id="prizeCateId" name="prizeCateId" class="easyui-combotree" data-options="url:'${appPath}/prizeCate/allPrizeTree.action',parentField:'parentId',lines:true  " style="width:150px;" />
					</td>
				</tr>
				<tr>
					<td class="tt" >有效期起</td>
					<td>
						<input name='beginDate' id='beginDate1' style='width:150px'  class="inval"  readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  />
					</td>
					<td class="tt" >有效期止</td>
					<td>
						<input name='endDate' id='endDate1' style='width:150px'  class="inval"  readonly="readonly" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'beginDate1\')}'})"  />
					</td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="prize_manage_search_fn();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="cleanForm();">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div style="height:393px;width:100%;">
		<table id="prizeInfo_datagrid"></table>
	</div>
</body>
</html>
