<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>行业标准管理</title>
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
	<form action="javascript:void(0);" id="industry_manage_search_form" style="width: 100%">
		<table class="form" style="width: 100%">
			<tr>
				<th class="tt" width="80">标题</th>
				<td>
					<input class="inval" name="keys" style="width: 215px;" />
					<input type="button" class="easyui-linkbutton" value="查询" onclick="industry_manage_search_fn()">
					<input type="button" class="easyui-linkbutton" value="重置" onclick="industry_manage_clean_form_fn()">
				</td>
			</tr>
		</table>
	</form>
</div>
	<div style="height:420px;width:100%;">
		<table id="industry_datagrid"></table>
	</div>
	<script type="text/javascript">
	
   		$(function(){
   			$('#industry_datagrid').datagrid({
   				url:'${pageContext.request.contextPath}/industry/dataGrid.action?menuid='+'${pid}',
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
   					title:'标准名称',
   					width:400
   				}]],
   				columns:[[{
   					field:'id',
   					title:'ID',
   					width:50,
   					hidden:true
   				},{
   					field:'ncreateid',
   					title:'创建人',
   					width:80,
   					hidden:true
   				},{
   					field:'dcreatetime',
   					title:'创建时间',
   					width:100/* ,
   					formatter:function(value,row,index){
   						return formatDate(value);
   					} */
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
   				<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit || requestScope.canDelete || requestScope.canView || requestScope.canAudit}">
   				,{
   					field:'action',
   					title:'动作',
   					width:60,
   					formatter:function(value,row,index){
   						var html = "";
   						if(row.cauditstatus =='1'){
   							//审核通过
   							if(row.cvalid=='1'){//可用
   								<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
   								html += '<img title="删除" onclick="industry_manage_del_fn(' + row.id + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png"/>';
   								</c:if>
   								<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canView}">
   								html += '&nbsp;<img title="查看" onclick="industry_manage_detail_fn(' + row.id + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/table/table.png"/>';
   								</c:if>
   							}
   						}else if(row.cauditstatus=='0'){
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
   							html += '<img title="修改" onclick="industry_manage_edit_fn(' + row.id + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png"/>';
   							</c:if>
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canAudit}">
   							html += '&nbsp;<img title="审核" onclick="industry_manage_approve_fn(' + row.id + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/book_open.png"/>';
   							</c:if>
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
   							html += '&nbsp;<img title="删除" onclick="industry_manage_del_fn(' + row.id + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png"/>';
   							</c:if>
   						}else {
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
   							html += '<img title="修改" onclick="industry_manage_edit_fn(' + row.id + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png"/>';
   							</c:if>
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
   							html += '&nbsp;<img title="删除" onclick="industry_manage_del_fn(' + row.id + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png"/>';
   							</c:if>
   						}
   						return html;
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
   						industry_manage_add_fn();
   					}
   				},'-',
   				</c:if>
   				{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#industry_datagrid').datagrid('reload');
   					}
   				}]
   			});
   		});
   		
   		
   		function industry_manage_add_fn(){
   			location.href = '${pageContext.request.contextPath}/industry/add.action?id='+${pid};
   		}
   		
   		//修改
   		function industry_manage_edit_fn(id){
   			if(id!=undefined){
   				location.href = '${pageContext.request.contextPath}/industry/edit.action?id='+id+'&opType=update&backId=';
   			}
   		}
   		
   	//修改
   		function industry_manage_detail_fn(id){
   			if(id!=undefined){
   				location.href = '${pageContext.request.contextPath}/industry/detail.action?id='+id+'&backId=';
   			}
   		}
   		
   		//审核
   		function industry_manage_approve_fn(id){
   			if(id!=undefined){
   				location.href = '${pageContext.request.contextPath}/industry/approve.action?id='+id+'&opType=approve';
   			}
   		}
   		
   		
   		
   		//删除
   		function industry_manage_del_fn(id){
   			if(id!=undefined){
   			   $('#industry_datagrid').datagrid('selectRecord',id);
   				var record = $('#industry_datagrid').datagrid('getSelected');
   				$.messager.confirm('提示','您确定要删除【'+record.title+'】?',function(b){
   					if(b){
   						var url = '${pageContext.request.contextPath}/industry/delete.action';
   						$.post(url,{id:id},function(data){
   							if(data.status){
   								$('#industry_datagrid').datagrid('reload');
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
   		function industry_manage_back_fn(id){
   			if(id!=undefined){
   			   $('#industry_datagrid').datagrid('selectRecord',id);
   				var record = $('#industry_datagrid').datagrid('getSelected');
   				$.messager.confirm('提示','您确定恢复【'+record.title+'】?',function(b){
   					if(b){
   						var url = '${pageContext.request.contextPath}/industry/back.action';
   						$.post(url,{id:id},function(data){
   							if(data.status){
   								$('#industry_datagrid').datagrid('reload');
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
   		
   		
   		function industry_manage_search_fn() {
			$('#industry_datagrid').datagrid('load', serializeObject($('#industry_manage_search_form')));
		}
		function industry_manage_clean_form_fn() {
			$('#industry_manage_search_form input[class="inval"]').val('');
			$('#industry_datagrid').datagrid('load', {});
		}
   		
   		
   	//格式化日期样式
	
		
		
	</script>
</body>
</html>
