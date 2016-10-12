<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息公告管理</title>
<jsp:include page="../../common/inc.jsp"></jsp:include>
</head>

<body>
	<script type="text/javascript">
   		$(function(){
   			$('#systemconfig_datagrid').datagrid({
   				url:'${appPath}/sysconfig/dataGrid.action',
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
   					field:'parameName',
   					title:'参数名称',
   					width:400
   				}]],
   				columns:[[{
   					field:'id',
   					title:'ID',
   					width:50,
   					hidden:true
   				},{
   					field:'parameDefaultValue',
   					title:'参数默认值',
   					width:50,
   					align:'center'
   				},{
   					field:'parameCategory',
   					title:'参数类别',
   					width:50,
   					align:'center'
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
   				}
   				<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit || requestScope.canDelete || requestScope.canView || requestScope.canAudit}">
   				,{
   					field:'action',
   					title:'动作',
   					width:60,
   					align:'center',
   					formatter:function(value,row,index){
   						var html = "";
   						if(row.auditStatus =='1'){
   							//审核通过
   							if(row.valid=='1'){//可用
   								<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
   								html += '&nbsp;<img title="删除" onclick="sysconfig_manage_del_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/cancel.png"/>';
   								</c:if>
   							}
   						}else if(row.auditStatus=='0'){
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
   							html += '<img title="修改" onclick="sysconfig_manage_edit_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/pencil.png"/>';
   							</c:if>
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
   							html += '&nbsp;<img title="删除" onclick="sysconfig_manage_del_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/cancel.png"/>';
   							</c:if>
   						}else {
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
   							html += '<img title="修改" onclick="sysconfig_manage_edit_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/pencil.png"/>';
   							</c:if>
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
   							html += '&nbsp;<img title="删除" onclick="sysconfig_manage_del_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/cancel.png"/>';
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
   						sysconfig_manage_add_fn();
   					}
   				},'-',
   				</c:if>
   				{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#systemconfig_datagrid').datagrid('reload');
   					}
   				}]
   			});
   		});
   		
   		
   		function sysconfig_manage_add_fn(){
   			$('<div/>').dialog({
				href : '${appPath}/sysconfig/add.action',
				width : 400,
				height : 240,
				modal : true,
				title : '系统参数新增',
				buttons : [ {
					text : '增加',
					iconCls : 'icon-add',
					handler : function() {
						var d = $(this).closest('.window-body');
						$('#sysconfig_manage_add_form').form('submit', {
							url : '${appPath}/sysconfig/insert.action',
							success : function(result) {
								var r = $.parseJSON(result);
								if (r.status) {
								$('#systemconfig_datagrid').datagrid('reload');
									d.dialog('destroy');
								}
								$.messager.show({
									title : '提示',
									msg : r.msg
								});
							}
						});
					}
				} ],
				onClose : function() {
					$(this).dialog('destroy');
				}
			});
   		}
   		
   		//修改
   		function sysconfig_manage_edit_fn(id){
   			if(id!=undefined){
   				$('<div/>').dialog({
					href : '${appPath}/sysconfig/edit.action?id='+id,
					width : 400,
					height : 260,
					modal : true,
					title : '系统参数修改',
					buttons : [ {
						text : '修改',
						iconCls : 'icon-ok',
						handler : function() {
							var d = $(this).closest('.window-body');
							$('#sysconfig_manage_edit_form').form('submit', {
								url : '${appPath}/sysconfig/update.action',
								success : function(result) {
									var r = $.parseJSON(result);
									if (r.status) {
									$('#systemconfig_datagrid').datagrid('reload');
										d.dialog('destroy');
									}
									$.messager.show({
										title : '提示',
										msg : r.msg
									});
								}
							});
						}
					} ],
					onClose : function() {
						$(this).dialog('destroy');
					}
				});
   			}
   		}
   		
   		//删除
   		function sysconfig_manage_del_fn(id){
   			if(id!=undefined){
   			   $('#systemconfig_datagrid').datagrid('selectRecord',id);
   				var record = $('#systemconfig_datagrid').datagrid('getSelected');
   				$.messager.confirm('提示','您确定要删除【'+record.parameName+'】?',function(b){
   					if(b){
   						var url = '${appPath}/sysconfig/delete.action';
   						$.post(url,{id:id},function(data){
   							if(data.status){
   								$('#systemconfig_datagrid').datagrid('reload');
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
   		
	</script>
	<table id="systemconfig_datagrid"></table>
</body>
</html>
