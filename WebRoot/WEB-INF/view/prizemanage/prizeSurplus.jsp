<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>奖品库存查询</title>
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
   			$('#prize_surplus_datagrid').datagrid({
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
   					width:130,
   					align:'center'
   				},{
   					field:'prizeCateName',
   					title:'奖品类别',
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
   					width:30,
   					align:'center'
   				},{
   					field:'excNum',
   					title:'已兑换数量',
   					width:30,
   					align:'center',
   					formatter:function(value,row,index){
   						if(value>0){
	   						var html = "";
	   						html = '<a href="javascript:showExcDetail('+row.id+')">'+value+'</a>';
	   						return html;
   						}else return value;
   					}
   				},{
   					field:'surplusNum',
   					title:'库存数量',
   					width:30,
   					align:'center'
   				}]],
   				toolbar:[{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#prize_surplus_datagrid').datagrid('reload');
   					}
   				}]
   			});
   		});
   		
   		
   		function showExcDetail(id){
   			$('<div/>').dialog({
   				href:'${appPath}/prizeInfo/excDetail.action?id='+id, //id为奖品的ID值
   				width:850,
   				height:400,
   				modal:true,
   				title:'奖品兑换明细',
   				onClose:function(){
   					$(this).dialog('destroy');
   				}
   			});
   		}
   		
   		
   		
   		
   		function prize_manage_search_fn() {
			$('#prize_surplus_datagrid').datagrid('load', serializeObject($('#prize_manage_search_form')));
		}
		function cleanForm() {
			$('#prize_manage_search_form input[class="inval"]').val('');
			$('#prizeCateId').combotree('clear');
			$('#prize_surplus_datagrid').datagrid('load', {});
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
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="prize_manage_search_fn();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="cleanForm();">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div style="height:393px;width:100%;">
		<table id="prize_surplus_datagrid"></table>
	</div>
</body>
</html>
