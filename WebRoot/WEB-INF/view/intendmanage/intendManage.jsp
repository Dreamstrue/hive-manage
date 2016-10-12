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
   			$('#intend_manage_datagrid').datagrid({
   				url:'${appPath}/intend/dataGrid.action',
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
   					field:'intendNo',
   					title:'订单号',
   					width:350,
   					formatter:function(value,row,index){
   						var html = "";
   						html += '<a href="javascript:showDetail('+row.id+')">'+value+'</a>';
   						return html;
   					}
   				}]],
   				columns:[[{
   					field:'id',
   					title:'ID',
   					width:50,
   					hidden:true
   				},{
   					field:'applyPersonId',
   					title:'申请人',
   					width:80,
   					hidden:true
   				},{
   					field:'consignee',
   					title:'收货人',
   					width:80,
   					align:'center'
   				},{
   					field:'applyTime',
   					title:'申请时间',
   					align:'center',
   					width:100/* ,
   					formatter:function(value,row,index){
   						return formatDate(value);
   					} */
   				},{
   					field:'intendStatus',
   					title:'订单状态',
   					width:80,
   					align:'center',
   					formatter:function(value,row,index){
   						return intend_zh_CN(value);
   					}
   				},{
   					field:'action',
   					title:'动作',
   					width:60,
   					align:'center',
   					formatter:function(value,row,index){
   						var html = "";
   						if(row.intendStatus =='1'){
   							//已申请
   							html += '&nbsp;<img title="审核" onclick="intend_manage_approve_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/book_open.png"/>';
   						}
   						if(row.intendStatus =='2'){
   							//审核通过
   							html += '&nbsp;<img title="发货" onclick="intend_manage_send_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/lorry.png"/>';
   						}
   						if(row.intendStatus =='4'){
   							//已发货
   							html += '&nbsp;<img title="确认收货" onclick="intend_manage_received_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/text_signature.png"/>';
   						}
   						return html;
   					}
   				}
   				]],
   				toolbar:[{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#intend_manage_datagrid').datagrid('reload');
   					}
   				}]
   			});
   		});
   		
   		
   		
   		
   		//审核
   		function intend_manage_approve_fn(id){
   			if(id!=undefined){
   				location.href = '${appPath}/intend/approve.action?id='+id+'&opType=approve';
   			}
   		}
   		
   		
   		//发货处理
   		function intend_manage_send_fn(id){
   			if(id!=undefined){
   				$.messager.confirm('提示','您确定进行发货操作?',function(b){
   					if(b){
   						var url = '${appPath}/intend/send.action';
						$.post(url,{id:id},function(data){
							if(data.status){
								$('#intend_manage_datagrid').datagrid('reload');
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
					msg:'请选择一条记录',
					title:'提示'
				});
   			}
   		}
   		
   		
   		//签收处理
   		function intend_manage_received_fn(id){
   			if(id!=undefined){
   				$.messager.confirm('提示','您确定进行签收操作?',function(b){
   					if(b){
   						var url = '${appPath}/intend/received.action';
						$.post(url,{id:id},function(data){
							if(data.status){
								$('#intend_manage_datagrid').datagrid('reload');
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
					msg:'请选择一条记录',
					title:'提示'
				});
   			}
   		}
   		
   		
   		//查看订单的详细情况
   		function showDetail(id){
   			location.href = '${appPath}/intend/detail.action?id='+id;
   		}
   		
   		
   		function intend_manage_search_fn() {
			$('#intend_manage_datagrid').datagrid('load', serializeObject($('#intend_manage_search_form')));
		}
		function cleanForm() {
			$('#intend_manage_search_form input[class="inval"]').val('');
			$('#intendStatus').combobox('clear');
			$('#intend_manage_datagrid').datagrid('load', {});
		}
		
		
		
	</script>
	<div class="searchDiv">
		<form action="javascript:void(0);" id="intend_manage_search_form" >
			<table class="form" style="height:60px;">
				<tr>
					<td class="tt" width="80">订单号</td>
					<td width="160"><input class="inval" name="intendNo" style="width:150px;"></td>
					<td class="tt" width="80">申请日期</td>
					<td width="160">
						<input class="inval" type='text' name='applyTime' id='applyTime' style='width:150px'  class='intxt' readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  />
					</td>
				</tr>
				<tr>
					<td class="tt" >订单状态</td>
					<td>
						<select id="intendStatus" name="intendStatus" class="easyui-combobox" style="width:150px;">
						<option value=""></option>
							<option value="1">1-已申请</option>
							<option value="2">2-审核通过</option>
							<option value="3">3-审核不通过</option>
							<option value="4">4-已发货</option>
							<option value="5">5-确认收货</option>
						</select>
					</td>
					<td>
					</td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="intend_manage_search_fn();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="cleanForm();">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div style="height:87%;width:100%;">
		<table id="intend_manage_datagrid"></table>
	</div>
</body>
</html>
