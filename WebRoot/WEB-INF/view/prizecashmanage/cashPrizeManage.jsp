<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>兑奖台账管理</title>
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
   				url:'${appPath}/cashPrizeInfo/dataGrid.action',
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
   				columns:[[{
   					field:'prizeSN',
   					title:'中奖SN码',
   					width:50,
   					align:'center'
   				},{
   					field:'prizeUser',
   					title:'中奖人',
   					width:30,
   					align:'center'
   				},{
   					field:'prizePhone',
   					title:'中奖人电话',
   					width:30,
   					align:'center'
   				},{
   					field:'prizeName',
   					title:'奖品名称',
   					width:70,
   					align:'center'
   				},{
   					field:'prizeNum',
   					title:'领取数量',
   					width:20,
   					align:'center'
   				},{
   					field:'createTime',
   					title:'领奖时间',
   					width:40,
   					align:'center'
   				},{
   					field:'createName',
   					title:'创建人',
   					width:40,
   					align:'center'
   				},{
   					field:'createOrgName',
   					title:'创建人部门',
   					width:40,
   					align:'center'
   				},{
   					field:'remark',
   					title:'备注',
   					width:40,
   					align:'center',
   					hidden:true
   				},{
   					field:'action',
   					title:'操作',
   					width:30,
   					align:'center',
   					formatter:function(value,row,index){
   						var html = "";
   						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
   						html += '<img title="修改" onclick="prize_manage_update_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/pencil.png"/>';
   						html += '&nbsp;<img title="删除" onclick="prize_manage_delete_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/cancel.png"/>';
   						</c:if>
   						return html;
   					}
   				}]],
   				toolbar:[{
   					text:'领取奖品',
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
   			location.href = '${appPath}/cashPrizeInfo/add.action?createId=${createId}&createName=${createName}&createOrgId=${createOrgId}&createOrgName=${createOrgName}';
   		}
   		
   		//修改奖品信息
   		function prize_manage_update_fn(id){
   			location.href='${appPath}/cashPrizeInfo/edit.action?id='+id;
   		}
   		
   		//删除奖品信息
   		function prize_manage_delete_fn(id){
   			if(id!=undefined){
   				$('#prizeInfo_datagrid').datagrid('selectRecord',id);
   				var record = $('#prizeInfo_datagrid').datagrid('getSelected');
   				$.messager.confirm('提示','确定删除奖品【'+record.prizeName+'】?',function(b){
   					if(b){
   						var url = '${appPath}/cashPrizeInfo/delete.action';
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
   		
   		function prize_manage_search_fn() {
			$('#prizeInfo_datagrid').datagrid('load', serializeObject($('#prize_manage_search_form')));
		}
		function cleanForm() {
			$('#prize_manage_search_form input[class="inval"]').val('');
			$('#prizeInfo_datagrid').datagrid('load', {});
		}
	</script>
	<div class="searchDiv">
		<form action="javascript:void(0);" id="prize_manage_search_form" >
			<table class="form" style="height:60px;">
				<tr>
					<td class="tt" width="80">奖品名称</td>
					<td width="160">
						<input class="inval" id="prizeName"  name="prizeName" style="width:150px;">
					</td>
					<td class="tt" width="80">中奖SN编号</td>
					<td width="160">
						<input  class="inval" id="prizeSN" name="prizeSN"  style="width:150px;" />
					</td>
				</tr>
				<tr>
					<td class="tt" >中奖人姓名</td>
					<td>
						<input  class="inval" id="prizeUser" name="prizeUser"  style="width:150px;" />
					</td>
					<td class="tt" >中奖人电话</td>
					<td>
						<input  class="inval" id="prizePhone" name="prizePhone"  style="width:150px;" />
					</td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="prize_manage_search_fn();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="cleanForm();">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div style="height:500px;width:100%;">
		<table id="prizeInfo_datagrid"></table>
	</div>
</body>
</html>
