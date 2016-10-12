<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>会员统计</title>
	<jsp:include page="../../common/inc.jsp"/>
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
		<form action="javascript:void(0);" id="magazine_manage_search_form" style="width: 100%">
			<table class="form" style="width: 100%;height:30px;">
				<tr>
					<td class="tt" width="80">标题</td>
					<td>
						<input class="inval" name="keys" style="width: 215px;" />
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="magazine_manage_search_fn();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="magazine_manage_clean_form_fn();">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div style="height:600px;width:100%;">
		<table id="magazine_grid"></table>
	</div>
	<script type="text/javascript">
		
		var searchFormName = "searchForm";
  		var gridName = "magazine_grid";
		
		$(function() {
		
			$('#'+gridName).datagrid({
					url : '${appPath}/magazine/datagrid.action?id=${menuId}',
					fit : true,
					fitColumns : true,
					border : false,
					pagination : true,
					striped:true,
					rownumbers : true,
					idField : 'nemagazineId',
					pageSize : 10,
					pageList : [10, 20, 30, 40, 50],
					sortName : 'nmemberid',
					sortOrder : 'desc',
					checkOnSelect : false,
					selectOnCheck : false,
					singleSelect:true,
					nowrap : false,
					columns : [[{
						field : 'nemagazineId',
						title : '',
						width : 50,
						hidden:true
					},{
						field : 'title',
						title : '杂志标题',
						width : 100
					},{
	   					field:'dcreatetime',
	   					title:'创建时间',
	   					width:100
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
	   								html += '<img onclick="magazine_manage_del_fn(' + row.nemagazineId + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png"/>';
	   								</c:if>
   									<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canView}">
	   								html += '&nbsp;<img title="查看" onclick="magazine_manage_detail_fn(' + row.nemagazineId + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/table.png"/>';
	   								</c:if>
	   							}
	   						}else if(row.cauditstatus=='0'){
	   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
	   							html += '<img alt="修改" onclick="magazine_manage_edit_fn(' + row.nemagazineId + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png"/>';
	   							</c:if>
   								<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canAudit}">
	   							html += '&nbsp;<img onclick="magazine_manage_approve_fn(' + row.nemagazineId + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/book_open.png"/>';
	   							</c:if>
   								<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
	   							html += '&nbsp;<img onclick="magazine_manage_del_fn(' + row.nemagazineId + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png"/>';
	   							</c:if>
	   						}else {
	   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
	   							html += '<img alt="修改" onclick="magazine_manage_edit_fn(' + row.nemagazineId + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png"/>';
	   							</c:if>
   								<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
	   							html += '&nbsp;<img onclick="magazine_manage_del_fn(' + row.nemagazineId + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png"/>';
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
	   						magazine_manage_add_fn();
	   					}
   					},'-',
   					</c:if>
   				{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#'+gridName).datagrid('reload');
   					}
   				}]
				});
			});// 页面加载完后的初始化完毕
		
		
		
		
		function magazine_manage_add_fn(){
   			location.href = '${pageContext.request.contextPath}/magazine/add.action?menuId=${menuId}';
   		}
   		
   		//修改
   		function magazine_manage_edit_fn(id){
   			if(id!=undefined){
   				location.href = '${pageContext.request.contextPath}/magazine/edit.action?id='+id+'&opType=update&menuId=${menuId}';
   			}
   		}
   		
   		//审核
   		function magazine_manage_approve_fn(id){
   			if(id!=undefined){
   				location.href = '${pageContext.request.contextPath}/magazine/approve.action?id='+id+'&opType=approve&menuId=${menuId}';
   			}
   		}
   		
   		//修改
   		function magazine_manage_detail_fn(id){
   			if(id!=undefined){
   				location.href = '${pageContext.request.contextPath}/magazine/detail.action?id='+id+'&menuId=${menuId}';
   			}
   		}
   		
   		//删除
   		function magazine_manage_del_fn(id){
   			if(id!=undefined){
   			   $('#magazine_grid').datagrid('selectRecord',id);
   				var record = $('#magazine_grid').datagrid('getSelected');
   				$.messager.confirm('提示','您确定要删除【'+record.title+'】?',function(b){
   					if(b){
   						var url = '${pageContext.request.contextPath}/magazine/delete.action';
   						$.post(url,{id:id},function(data){
   							if(data.status){
   								$('#magazine_grid').datagrid('reload');
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
	function magazine_manage_search_fn() {
			$('#magazine_grid').datagrid('load', serializeObject($('#magazine_manage_search_form')));
		}
	function magazine_manage_clean_form_fn() {
			$('#magazine_manage_search_form input[class="inval"]').val('');
			$('#magazine_grid').datagrid('load', {});
		}
		
		
	</script>
	
</body>
</html>