<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>热点专题管理</title>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<style type="text/css">
body{margin:0px;padding:0px;}
.searchDiv{
	overflow: hidden;
	height: 30px; 
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
		<form action="javascript:void(0);" id="special_manage_search_form" style="width: 100%">
			<table class="form" style="width: 100%;height:30px;">
				<tr>
					<td class="tt" width="80">专题栏目</td>
					<td width="130">
						<input id="specTopid_combotree" name="id" class="easyui-combotree" data-options="url:'${appPath}/special/treegrid.action',parentField : 'pid',lines : true" />
						<input id="pid" name="pid" type="hidden"/>
					</td>
					<td class="tt" width="80">标题</td>
					<td>
						<input class="inval" name="keys" style="width: 215px;" />
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="special_manage_search_fn();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="special_manage_clean_form_fn();">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div style="height:80%;width:100%;">
		<table id="specialContent_datagrid"></table>
	</div>
	<script type="text/javascript">
	
   		$(function(){
   			$('#specialContent_datagrid').datagrid({
   				url:'${pageContext.request.contextPath}/specialContent/dataGrid.action',
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
   					field:'specialInfoName',
   					title:'专题名称',
   					width:100
   				},{
   					field:'specialNavName',
   					title:'栏目名称',
   					width:100
   				},{
   					field:'dcreatetime',
   					title:'创建时间',
   					width:100,
   					formatter:function(value,row,index){
   						return formatDate(value);
   					} 
   				},{
   					field:'cauditstatus',
   					title:'审核状态',
   					width:80,
   					formatter:function(value,row,index){
   						return back_zh_CN(value);
   					}
   				},{
   					field:'cvalid',
   					title:'是否可用',
   					width:50,
   					formatter:function(value,row,index){
   						if(value=='1'){
   							return '<font>是</font>';
   						}else if(value=='0'){
							return '<font color="red">否</font>';					
   						}
   					}
   				}
   				<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canView || requestScope.canEdit || requestScope.canDelete}">
   				,{
   					field:'action',
   					title:'动作',
   					width:60,
   					formatter:function(value,row,index){
   						var html = "";
   							if(row.cvalid=='1'){//可用
   								if(row.cauditstatus =='1'){
   									<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
   									html += '<img onclick="specialContent_manage_del_fn(' + row.id + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png"/>';
   									</c:if>
   									<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canView}">
   									html += '&nbsp;<img onclick="specialContent_manage_detail_fn(' + row.id + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/table.png"/>';
   									</c:if>
   								}else if(row.cauditstatus=='0'){
   									<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
   									html += '<img alt="修改" onclick="specialContent_manage_edit_fn(' + row.id + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png"/>';
   									</c:if>
   									<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canAudit}">
   									html += '&nbsp;<img onclick="specialContent_manage_approve_fn(' + row.id + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/book_open.png"/>';
   									</c:if>
   									<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
   									html += '&nbsp;<img onclick="specialContent_manage_del_fn(' + row.id + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png"/>';
   									</c:if>
   								}else {
   									<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
   									html += '<img alt="修改" onclick="specialContent_manage_edit_fn(' + row.id + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png"/>';
   									</c:if>
   									<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
   									html += '&nbsp;<img onclick="specialContent_manage_del_fn(' + row.id + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png"/>';
   									</c:if>
   								}
   								return html;
   							}
   					}
   				}
   				</c:if>
   				]],
   				toolbar:[
   				<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canAdd}">
   				{
   					text:'新增',
   					iconCls:'icon-add',
   					handler:function(){
   						specialContent_manage_add_fn();
   					}
   				},'-',
   				</c:if>
   				{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#specialContent_datagrid').datagrid('reload');
   					}
   				}]
   			});
   			
   			
   			$('#specTopid_combotree').combotree({
   				onSelect:function(node){
   	   				$('#pid').val(node.attributes.pid);
   	   			}
   			});
   			
   		});
   		
   		
   		function specialContent_manage_add_fn(){
   			location.href = '${pageContext.request.contextPath}/specialContent/add.action';
   		}
   		
   		//修改
   		function specialContent_manage_edit_fn(id){
   			if(id!=undefined){
   				location.href = '${pageContext.request.contextPath}/specialContent/edit.action?id='+id+'&opType=update';
   			}
   		}
   		
   		function specialContent_manage_detail_fn(id){
   			if(id!=undefined){
   				location.href = '${pageContext.request.contextPath}/specialContent/detail.action?id='+id;
   			}
   		}
   		
   		
   		
   		//审核
   		function specialContent_manage_approve_fn(id){
   			if(id!=undefined){
   				location.href = '${pageContext.request.contextPath}/specialContent/edit.action?id='+id+'&opType=approve';
   			}
   		}
   		
   		
   		
   		//删除
   		function specialContent_manage_del_fn(id){
   			if(id!=undefined){
   			   $('#specialContent_datagrid').datagrid('selectRecord',id);
   				var record = $('#specialContent_datagrid').datagrid('getSelected');
   				$.messager.confirm('提示','您确定要删除【'+record.title+'】?',function(b){
   					if(b){
   						var url = '${pageContext.request.contextPath}/specialContent/delete.action';
   						$.post(url,{id:id},function(data){
   							if(data.status){
   								$('#specialContent_datagrid').datagrid('reload');
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
   		
   		
   		/*重新设置为可用状态*/
   		function specialContent_manage_back_fn(id){
   			if(id!=undefined){
   			   $('#specialContent_datagrid').datagrid('selectRecord',id);
   				var record = $('#specialContent_datagrid').datagrid('getSelected');
   				$.messager.confirm('提示','您确定恢复【'+record.title+'】?',function(b){
   					if(b){
   						var url = '${pageContext.request.contextPath}/specialContent/back.action';
   						$.post(url,{id:id},function(data){
   							if(data.status){
   								$('#specialContent_datagrid').datagrid('reload');
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
   		
   		
   		function special_manage_search_fn() {
			$('#specialContent_datagrid').datagrid('load', serializeObject($('#special_manage_search_form')));
		}
		function special_manage_clean_form_fn() {
			$('#special_manage_search_form input[class="inval"]').val('');
			$('#specTopid_combotree').combotree('clear');
			$('#pid').val();
			$('#specialContent_datagrid').datagrid('load', {});
		}
   		
   		
   	//格式化日期样式
	
		
		
	</script>
</body>
</html>
