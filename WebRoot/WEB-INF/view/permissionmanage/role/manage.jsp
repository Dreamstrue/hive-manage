<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<script type="text/javascript">
	$(function() {
		$('#role_manage_datagrid').datagrid({
			url : '${appPath}/role/datagrid.action',
			fit : true,
			fitColumns : false,
			border : false,
			pagination : true,
			rownumbers:true,
			idField : 'nroleid',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'nroleid',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			singleSelect:true,
			nowrap : false,
			frozenColumns : [ [ 
			<%-- 复选框就是为了批量删除，如果没有删除权限就不用显示了 --%>
			<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
			{
				title : '编号',
				field : 'nroleid',
				width : 150,
				checkbox : true
			}, 
			</c:if>
			{
				title : '角色名称',
				field : 'crolename',
				width : 150,
				sortable : true
			} , {
				title : '角色描述',
				field : 'croledescription',
				width : 250,
				sortable : true
			}] ],
			columns : [ [
			<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit || requestScope.canDelete || requestScope.canBind}">
			{
				title : '动作',
				field : 'action',
				align :'center',
				width : 100,
				formatter : function(value, row, index) {
					//alert(row.nroleid);
					if (row.nroleid == '0') {
						return '系统角色';
					} else {
						//return formatString('<img onclick="role_manage_edit_fn(\'{0}\');" src="{1}" title="修改"/>&nbsp;<img onclick="role_manage_del_fn(\'{2}\');" src="{3}" title="删除"/>&nbsp;<img onclick="role_menu_action_bind_fn(\'{4}\');" src="{5}" title="菜单动作绑定"/>', row.nroleid, '${appPath}/resources/images/extjs_icons/pencil.png', row.nroleid, '${appPath}/resources/images/extjs_icons/cancel.png', row.nroleid, '${appPath}/resources/images/extjs_icons/group/group_gear.png');
						var html = "";
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
						html += '<img onclick="role_manage_edit_fn(' + row.nroleid + ');" src="${appPath}/resources/images/extjs_icons/pencil.png" title="修改"/>';
						</c:if>
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
						html += '&nbsp;<img onclick="role_manage_del_fn(' + row.nroleid + ');" src="${appPath}/resources/images/extjs_icons/cancel.png" title="删除"/>';
						</c:if>
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canBind}">
						html += '&nbsp;<img onclick="role_menu_action_bind_fn(' + row.nroleid + ');" src="${appPath}/resources/images/extjs_icons/group/group_gear.png" title="绑定菜单及动作"/>';
						</c:if>
						return html;
					}
				}
			}
			</c:if>
			 ] ],
			toolbar : [
			<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canAdd}"> 
			{
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					role_manage_add_fn();
				}
			}, '-', 
			</c:if>
			<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
			{
				text : '批量删除',
				iconCls : 'icon-remove',
				handler : function() {
					role_manage_delAll_fn();
				}
			}
			</c:if>
			]
		});
	});

	
	function role_manage_add_fn() {
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/role/add.action',
			width : 360,
			height : 200,
			modal : true,
			title : '添加角色',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#role_manage_add_form').form('submit', {
						url : '${pageContext.request.contextPath}/role/insert.action',
						success : function(result) {
							try {
								var r = $.parseJSON(result);
								if (r.status) {
									//$('#role_manage_datagrid').datagrid('insertRow', {
									//	index : 0,
									//	row : r.data
									//});
									$('#role_manage_datagrid').datagrid('reload');
									d.dialog('destroy');
								}
								$.messager.show({
									title : '提示',
									msg : r.msg
								});
							} catch (e) {
								$.messager.alert('提示', result);
							}
						}
					});
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	//单个删除
	function role_manage_del_fn(id) {
		$.messager.confirm('确认', '您是否要删除当前选中的角色吗？', function(r) {
			if (r) {
				$.post("${pageContext.request.contextPath}/role/delete.action",{ids : id},function(result){
					if (result.status) {
						$('#role_manage_datagrid').datagrid('load');
						$('#role_manage_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
					}
					$.messager.show({
						title : '提示',
						msg : result.msg
					});
				},'json');
			}
		});
	}
	
	// 批量删除
	function role_manage_delAll_fn() {
		var rows = $('#role_manage_datagrid').datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			$.messager.confirm('确认', '您确定要删除当前选中的这些角色吗？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].nroleid);
					}
					//alert(ids);
					//return false;
					$.ajax({
						url : '${pageContext.request.contextPath}/role/delete.action',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(result) {
							if (result.status) {
								$('#role_manage_datagrid').datagrid('load');
								$('#role_manage_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
							}
							$.messager.show({
								title : '提示',
								msg : result.msg
							});
						}
					});
				}
			});
		} else {
			$.messager.show({
				title : '提示',
				msg : '请勾选要删除的记录！'
			});
		}
	}
	
	
	function role_menu_action_bind_fn(id){
  		location.href = '${pageContext.request.contextPath}/role/toRoleMenuActionBind.action?roleId=' + id;
  	}
	
	
	function role_manage_edit_fn(id) {
		$('#role_manage_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/role/edit.action?roleId=' + id,
			width : 360,
			height : 200,
			modal : true,
			title : '编辑角色',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#role_manage_edit_form').form('submit', {
						url : '${pageContext.request.contextPath}/role/update.action',
						success : function(result) {
							var r = $.parseJSON(result);
							if (r.status) {
								$('#role_manage_datagrid').datagrid('reload');
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
			},
			onLoad : function() {
				var index = $('#role_manage_datagrid').datagrid('getRowIndex', id);
				var rows = $('#role_manage_datagrid').datagrid('getRows');
				$('#role_manage_edit_form').form('load', {
										id:rows[index].id,
										name:rows[index].name,
										menu:stringToList(rows[index].menu),
										permission:stringToList(rows[index].permission)
										});
			}
		});
	}
	
	
	
</script>
<table id="role_manage_datagrid"></table>