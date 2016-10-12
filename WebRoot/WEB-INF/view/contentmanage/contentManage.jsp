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
	<div class="searchDiv">
		<form action="javascript:void(0);" id="commonContent_manage_search_form" >
			<table class="form" style="width: 100%;height:60px;">
				<tr>
					<td class="tt" width="80">标题</td>
					<td width="160"><input class="inval" name="title" style="width:150px;"></td>
					<td class="tt" width="80">创建日期</td>
					<td width="160">
						<input class="inval" type="text" name="createTime" id="createTime" style="width:150px" class="intxt" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  />
					</td>
				</tr>
				<tr>
					<td class="tt" >审核状态</td>
					<td>
						<select id="auditStatus" name="auditStatus" class="easyui-combobox" style="width:150px;">
							<option value=""></option>
							<option value="0">0-未审核</option>
							<option value="1">1-审核通过</option>
							<option value="2">2-审核不通过</option>
						</select>
					</td>
					<td class="tt">信息类别</td>
					<td>
						<input  id="infoCateId" name="infoCateId" class="easyui-combotree" data-options="url:'${appPath}/infoCate/allInfoTree.action',parentField:'parentId',lines:true,cascadeCheck:false,checkbox:true,multiple:true,onlyLeafCheck:true" style="width:150px;" />					
					</td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="commonContent_manage_search_fn();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="cleanForm();">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div style="height:86%;width:100%;">
		<table id="commonContent_datagrid"></table>
	</div>
	
	<script type="text/javascript">
   		$(function(){
   			$('#commonContent_datagrid').datagrid({
   				url:'${appPath}/commonContent/dataGrid.action',
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
   					field:'title',
   					title:'标题',
   					width:400
   				}]],
   				columns:[[{
   					field:'id',
   					title:'ID',
   					width:50,
   					hidden:true
   				},{
   					field:'createId',
   					title:'创建人',
   					width:80,
   					hidden:true
   				},{
   					field:'infoCateName',
   					title:'信息类别',
   					width:120,
   					align:'center'
   				},{
   					field:'createTime',
   					title:'创建时间',
   					align:'center',
   					width:100
   				},{
   					field:'auditStatus',
   					title:'审核状态',
   					width:80,
   					align:'center',
   					formatter:function(value,row,index){
   						return back_zh_CN(value);
   					}
   				},{
   					field:'valid',
   					title:'是否可用',
   					align:'center',
   					width:50,
   					formatter:function(value,row,index){
   						if(value=='1'){
   							return '<font>是</font>';
   						}else if(value=='0'){
							return '<font color="red">否</font>';					
   						}
   					}
   				},{
   					field:'action',
   					title:'动作',
   					width:60,
   					align:'center',
   					formatter:function(value,row,index){
   						var html = "";
   						if(row.auditStatus =='1'){
   							//审核通过
							html += '<img title="查看" onclick="commonContent_manage_detail_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/table/table.png"/>';
							html += '&nbsp;<img title="删除" onclick="commonContent_manage_del_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/cancel.png"/>';
   						}else if(row.auditStatus=='0'){
   							html += '<img title="修改" onclick="commonContent_manage_edit_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/pencil.png"/>';
   							html += '&nbsp;<img title="审核" onclick="commonContent_manage_approve_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/book_open.png"/>';
   							html += '&nbsp;<img title="删除" onclick="commonContent_manage_del_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/cancel.png"/>';
   						}else {
   							html += '<img title="修改" onclick="commonContent_manage_edit_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/pencil.png"/>';
   							html += '&nbsp;<img title="删除" onclick="commonContent_manage_del_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/cancel.png"/>';
   						}
   						return html;
   					}
   				}
   				]],
   				toolbar:[
   				{
   					text:'新增',
   					iconCls:'icon-add',
   					handler:function(){
   						commonContent_manage_add_fn();
   					}
   				},'-',
   				{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#commonContent_datagrid').datagrid('reload');
   					}
   				}]
   			});
   			
   			//控制信息类型下拉框
   			var ids ='';
			var t = $('#infoCateId').combotree('tree');// 得到树对象
			$('#infoCateId').combotree({
				onCheck:function(node,checked){
					var nodes = t.tree('getChecked');	
					if(nodes.length>1){ //说明有一个节点被选中了
						var node1 = t.tree('find', ids);
						t.tree('uncheck',node1.target);
					}
					ids = node.id;
				}
			});
   			
   			
   		});
   		
   		
   		function commonContent_manage_add_fn(){
   			location.href = '${appPath}/commonContent/add.action';
   		}
   		
   		//修改
   		function commonContent_manage_edit_fn(id){
   			if(id!=undefined){
   				location.href = '${appPath}/commonContent/edit.action?id='+id+'&opType=update';
   			}
   		}
   		
   		
   		function commonContent_manage_detail_fn(id){
   			location.href = '${appPath}/commonContent/detail.action?id='+id;
   		}
   		
   		//审核
   		function commonContent_manage_approve_fn(id){
   			if(id!=undefined){
   				location.href = '${appPath}/commonContent/approve.action?id='+id+'&opType=approve';
   			}
   		}
   		
   		
   		
   		//删除
   		function commonContent_manage_del_fn(id){
   			if(id!=undefined){
   			   $('#commonContent_datagrid').datagrid('selectRecord',id);
   				var record = $('#commonContent_datagrid').datagrid('getSelected');
   				$.messager.confirm('提示','您确定要删除【'+record.title+'】?',function(b){
   					if(b){
   						var url = '${appPath}/commonContent/delete.action';
   						$.post(url,{id:id},function(data){
   							if(data.status){
   								$('#commonContent_datagrid').datagrid('reload');
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
   		
   		function commonContent_manage_search_fn() {
			$('#commonContent_datagrid').datagrid('load', serializeObject($('#commonContent_manage_search_form')));
		}
		function cleanForm() {
			$('#commonContent_manage_search_form input[class="inval"]').val('');
			$('#infoCateId').combobox('clear');
			$('#auditStatus').combobox('clear');
			$('#commonContent_datagrid').datagrid('load', {});
		}
	</script>
</body>
</html>
