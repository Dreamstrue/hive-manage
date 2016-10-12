<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>导航菜单管理</title>
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
		<form action="javascript:void(0);" id="integrity_manage_search_form" style="width: 100%">
			<table class="form" style="width: 100%;height:30px;">
				<tr>
					<td class="tt" width="80">标题</td>
					<td>
						<input class="inval" name="keys" style="width: 215px;" />
						<input type="button" class="easyui-linkbutton" value="查询" onclick="integrity_manage_search_fn()">
						<input type="button" class="easyui-linkbutton" value="重置" onclick="integrity_manage_clean_form_fn()">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div style="height:85%;width:100%;">
		<table id="IntegrityNews_datagrid"></table>
	</div>
	<script type="text/javascript">
	
   		$(function(){
   			$('#IntegrityNews_datagrid').datagrid({
   				url:'${pageContext.request.contextPath}/inteNews/dataGrid.action',
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
   					field:'auditstatus',
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
   						if(row.auditstatus =='1'){
   							//审核通过
   							if(row.cvalid=='1'){//可用
   								<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
   								html += '<img onclick="inte_manage_del_fn(' + row.id + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png"/>';
   								</c:if>
   								<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canView}">
   								html += '&nbsp;<img title="查看" onclick="inte_manage_detail_fn(' + row.id + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/table/table.png"/>';
   								</c:if>
   							}
   							if(row.cvalid=='0'){//不可用
   								return formatString('<img onclick="inte_manage_back_fn(\'{0}\');" src="{1}"/>', row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/tab_go.png');
   							}
   						}else if(row.auditstatus=='0'){
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
   							html += '<img alt="修改" onclick="inte_manage_edit_fn(' + row.id + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png"/>';
   							</c:if>
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canAudit}">
   							html += '&nbsp;<img onclick="inte_manage_approve_fn(' + row.id + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/book_open.png"/>';
   							</c:if>
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
   							html += '&nbsp;<img onclick="inte_manage_del_fn(' + row.id + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png"/>';
   							</c:if>
   						}else {
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
   							html += '<img alt="修改" onclick="inte_manage_edit_fn(' + row.id + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png"/>';
   							</c:if>
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
   							html += '&nbsp;<img onclick="inte_manage_del_fn(' + row.id + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png"/>';
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
   						inte_manage_add_fn();
   					}
   				},'-',
   				</c:if>
   				{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#IntegrityNews_datagrid').datagrid('reload');
   					}
   				}]
   			});
   		});
   		
   		
   		function inte_manage_add_fn(){
   			location.href = '${pageContext.request.contextPath}/inteNews/add.action';
   		}
   		
   		//修改
   		function inte_manage_edit_fn(id){
   			if(id!=undefined){
   				location.href = '${pageContext.request.contextPath}/inteNews/edit.action?id='+id+'&opType=update';
   			}
   		}
   		
   		function inte_manage_detail_fn(id){
   			location.href = '${pageContext.request.contextPath}/inteNews/detail.action?id='+id;
   		}
   		
   		//审核
   		function inte_manage_approve_fn(id){
   			if(id!=undefined){
   				location.href = '${pageContext.request.contextPath}/inteNews/approve.action?id='+id+'&opType=approve';
   			}
   		}
   		
   		
   		
   		//删除
   		function inte_manage_del_fn(id){
   			if(id!=undefined){
   			   $('#IntegrityNews_datagrid').datagrid('selectRecord',id);
   				var record = $('#IntegrityNews_datagrid').datagrid('getSelected');
   				$.messager.confirm('提示','您确定要删除【'+record.title+'】?',function(b){
   					if(b){
   						var url = '${pageContext.request.contextPath}/inteNews/delete.action';
   						$.post(url,{id:id},function(data){
   							if(data.status){
   								$('#IntegrityNews_datagrid').datagrid('reload');
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
   		function inte_manage_back_fn(id){
   			if(id!=undefined){
   			   $('#IntegrityNews_datagrid').datagrid('selectRecord',id);
   				var record = $('#IntegrityNews_datagrid').datagrid('getSelected');
   				$.messager.confirm('提示','您确定恢复【'+record.title+'】?',function(b){
   					if(b){
   						var url = '${pageContext.request.contextPath}/inteNews/back.action';
   						$.post(url,{id:id},function(data){
   							if(data.status){
   								$('#IntegrityNews_datagrid').datagrid('reload');
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
   		
   		
   		function integrity_manage_search_fn() {
			$('#IntegrityNews_datagrid').datagrid('load', serializeObject($('#integrity_manage_search_form')));
		}
		function integrity_manage_clean_form_fn() {
			$('#integrity_manage_search_form input[class="inval"]').val('');
			$('#IntegrityNews_datagrid').datagrid('load', {});
		}
   		
   		
   	//格式化日期样式
	
		
		
	</script>
</body>
</html>
