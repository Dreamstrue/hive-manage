<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>图片新闻管理</title>
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
	<form action="javascript:void(0);" id="picture_manage_search_form" style="width: 100%">
		<table class="form" style="width: 100%">
			<tr>
				<th class="tt" width="80">标题</th>
				<td>
					<input class="inval" name="keys" style="width: 215px;" />
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="picture_manage_search_fn();">查询</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="picture_manage_clean_form_fn();">重置</a>
				</td>
			</tr>
		</table>
	</form>
</div>
	<div style="height:80%;width:100%;">
		<table id="picture_datagrid"></table>
	</div>
	<script type="text/javascript">
	
   		$(function(){
   			$('#picture_datagrid').datagrid({
   				url:'${pageContext.request.contextPath}/picture/dataGrid.action',
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
   					title:'新闻标题',
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
   					field:'createTime',
   					title:'创建时间',
   					width:100/* ,
   					formatter:function(value,row,index){
   						return formatDate(value);
   					} */
   				},{
   					field:'auditStatus',
   					title:'审核状态',
   					width:80,
   					formatter:function(value,row,index){
   						return back_zh_CN(value);
   					}
   				},{
   					field:'valid',
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
   						if(row.auditStatus =='1'){
   							//审核通过
   							if(row.valid=='1'){//可用
   								<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
   								html += '<img onclick="picture_manage_del_fn(' + row.id + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png"/>';
   								</c:if>
   								<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canView}">
   								html += '&nbsp;<img title="查看" onclick="picture_manage_detail_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/table/table.png"/>';
   								</c:if>
   							}
   							if(row.valid=='0'){//不可用
   								return formatString('<img onclick="picture_manage_back_fn(\'{0}\');" src="{1}"/>', row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/tab_go.png');
   							}
   						}else if(row.auditStatus=='0'){
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
   							html += '<img title="修改" onclick="picture_manage_edit_fn(' + row.id + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png"/>';
   							</c:if>
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canAudit}">
   							html += '&nbsp;<img onclick="picture_manage_approve_fn(' + row.id + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/book_open.png"/>';
   							</c:if>
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
   							html += '&nbsp;<img onclick="picture_manage_del_fn(' + row.id + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png"/>';
   							</c:if>
   						}else {
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
   							html += '<img title="修改" onclick="picture_manage_edit_fn(' + row.id + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png"/>';
   							</c:if>
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
   							html += '&nbsp;<img onclick="picture_manage_del_fn(' + row.id + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png"/>';
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
   						picture_manage_add_fn();
   					}
   				},'-',
   				</c:if>
   				{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#picture_datagrid').datagrid('reload');
   					}
   				}]
   			});
   		});
   		
   		
   		function picture_manage_add_fn(){
   			location.href = '${pageContext.request.contextPath}/picture/add.action';
   		}
   		
   		//修改
   		function picture_manage_edit_fn(id){
   			if(id!=undefined){
   				location.href = '${pageContext.request.contextPath}/picture/edit.action?id='+id+'&opType=update';
   			}
   		}
   		
   		function picture_manage_detail_fn(id){
   			location.href = '${pageContext.request.contextPath}/picture/detail.action?id='+id;
   		}
   		
   		//审核
   		function picture_manage_approve_fn(id){
   			if(id!=undefined){
   				location.href = '${pageContext.request.contextPath}/picture/approve.action?id='+id+'&opType=approve';
   			}
   		}
   		
   		
   		
   		//删除
   		function picture_manage_del_fn(id){
   			if(id!=undefined){
   			   $('#picture_datagrid').datagrid('selectRecord',id);
   				var record = $('#picture_datagrid').datagrid('getSelected');
   				$.messager.confirm('提示','您确定要删除【'+record.title+'】?',function(b){
   					if(b){
   						var url = '${pageContext.request.contextPath}/picture/delete.action';
   						$.post(url,{id:id},function(data){
   							if(data.status){
   								$('#picture_datagrid').datagrid('reload');
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
   		function picture_manage_back_fn(id){
   			if(id!=undefined){
   			   $('#picture_datagrid').datagrid('selectRecord',id);
   				var record = $('#picture_datagrid').datagrid('getSelected');
   				$.messager.confirm('提示','您确定恢复【'+record.title+'】?',function(b){
   					if(b){
   						var url = '${pageContext.request.contextPath}/picture/back.action';
   						$.post(url,{id:id},function(data){
   							if(data.status){
   								$('#picture_datagrid').datagrid('reload');
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
   		
   		
   		function picture_manage_search_fn() {
			$('#picture_datagrid').datagrid('load', serializeObject($('#picture_manage_search_form')));
		}
		function picture_manage_clean_form_fn() {
			$('#picture_manage_search_form input[class="inval"]').val('');
			$('#picture_datagrid').datagrid('load', {});
		}
   		
   		
   	//格式化日期样式
	
		
		
	</script>
</body>
</html>
