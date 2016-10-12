<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
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
	<form action="javascript:void(0);" id="ass_manage_search_form" style="width: 100%">
		<table class="form" style="width: 100%">
			<tr>
				<th class="tt" width="80">标题</th>
				<td>
					<input class="inval" name="keys" style="width: 215px;" />
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="ass_manage_search_fn();">查询</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="ass_manage_clean_form_fn();">重置</a>
				</td>
			</tr>
		</table>
	</form>
</div>
	<div style="height:420px;width:100%;">
		<table id="ass_datagrid"></table>
	</div>
	<script type="text/javascript">
	
   		$(function(){
   			$('#ass_datagrid').datagrid({
   				url:'${appPath}/aboutus/dataGrid.action?id=${menuId}',
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
   					field:'ctitle',
   					title:'标题',
   					width:400
   				}]],
   				columns:[[{
   					field:'naboutusid',
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
   							if(row.cvalid=='1'){//可用
   								var html = "";
   								if(row.cauditstatus =='1'){
   									<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
   									html += '<img onclick="ass_manage_del_fn(' + row.naboutusid + ');" src="${appPath}/resources/images/extjs_icons/cancel.png"/>';
   									</c:if>
   									<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canView}">
   									html += '&nbsp;<img title="查看" onclick="ass_manage_detail_fn(' + row.naboutusid + ');" src="${appPath}/resources/images/extjs_icons/table.png"/>';
   									</c:if>
   								}else if(row.cauditstatus=='0'){
   									<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
   									html += '<img alt="修改" onclick="ass_manage_edit_fn(' + row.naboutusid + ');" src="${appPath}/resources/images/extjs_icons/pencil.png"/>';
   									</c:if>
   									<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canAudit}">
   									html += '&nbsp;<img onclick="ass_manage_approve_fn(' + row.naboutusid + ');" src="${appPath}/resources/images/extjs_icons/book_open.png"/>';
   									</c:if>
   									<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
   									html += '&nbsp;<img onclick="ass_manage_del_fn(' + row.naboutusid + ');" src="${appPath}/resources/images/extjs_icons/cancel.png"/>';
   									</c:if>
		   						}else {
		   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
		   							html += '<img alt="修改" onclick="ass_manage_edit_fn(' + row.naboutusid + ');" src="${appPath}/resources/images/extjs_icons/pencil.png"/>';
		   							</c:if>
   									<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
		   							html += '&nbsp;<img onclick="ass_manage_del_fn(' + row.naboutusid + ');" src="${appPath}/resources/images/extjs_icons/cancel.png"/>';
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
   						ass_manage_add_fn();
   					}
   				},'-',
   				</c:if>
   				{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#ass_datagrid').datagrid('reload');
   					}
   				}]
   			});
   		});
   		
   		
   		function ass_manage_add_fn(){
   			location.href = '${appPath}/aboutus/add.action?menuId=${menuId}';
   		}
   		
   		//修改    这里的ID为该条记录的ID，而pid为该条记录对应的导航的父节点的ID
   		function ass_manage_edit_fn(id){
   			if(id!=undefined){
   				location.href = '${appPath}/aboutus/edit.action?id='+id+'&opType=update';
   			}
   		}
   		
   		function ass_manage_detail_fn(id){
   			location.href = '${appPath}/aboutus/detail.action?id='+id;
   		}
   		
   		//审核
   		function ass_manage_approve_fn(id){
   			if(id!=undefined){
   				location.href = '${appPath}/aboutus/edit.action?id='+id+'&opType=approve';
   			}
   		}
   		
   		
   		
   		//删除
   		function ass_manage_del_fn(id){
   			if(id!=undefined){
   			   $('#ass_datagrid').datagrid('selectRecord',id);
   				var record = $('#ass_datagrid').datagrid('getSelected');
   				$.messager.confirm('提示','您确定要删除【'+record.ctitle+'】?',function(b){
   					if(b){
   						var url = '${appPath}/aboutus/delete.action';
   						$.post(url,{id:id},function(data){
   							if(data.status){
   								$('#ass_datagrid').datagrid('reload');
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
   		
   		
   		function ass_manage_search_fn() {
			$('#ass_datagrid').datagrid('load', serializeObject($('#ass_manage_search_form')));
		}
		function ass_manage_clean_form_fn() {
			$('#ass_manage_search_form input[class="inval"]').val('');
			$('#ass_datagrid').datagrid('load', {});
		}
   		
	</script>
</body>
</html>
