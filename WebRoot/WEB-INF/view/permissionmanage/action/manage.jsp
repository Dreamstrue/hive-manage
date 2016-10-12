<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<script type="text/javascript">
	$(function() {
		$('#action_manage_datagrid').datagrid({
			url : '${appPath}/action/datagrid.action',
			fit : true,
			fitColumns : false,
			border : false,
			pagination : true,
			rownumbers:true,
			idField : 'nactionid',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'nactionid',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			singleSelect:true, // 列表页只能单选
			nowrap : false,
			frozenColumns : [ [ 
			<%-- 复选框就是为了批量删除，如果没有删除权限就不用显示了 --%>
			<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
			{
				title : '编号',
				field : 'nactionid',
				width : 150,
				checkbox : true
			}, 
			</c:if>
			{
				title : '动作名称',
				field : 'cactionname',
				width : 150,
				sortable : true
			} , {
				title : '动作字符串',
				field : 'cactionstr',
				width : 150,
				sortable : true
			} , {
				title : '动作描述',
				field : 'cactiondescription',
				width : 250,
				sortable : true
			}] ],
			columns : [ [
			<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit || requestScope.canDelete}">
			{
				title : '动作',
				field : 'action',
				align :'center',
				width : 100,
				formatter : function(value, row, index) {
					//alert(row.nactionid);
					if (row.nactionid == '0') {
						return '系统动作';
					} else {
						//return formatString('<img onclick="action_manage_edit_fn(\'{0}\');" src="{1}" title="修改"/>&nbsp;<img onclick="action_manage_del_fn(\'{2}\');" src="{3}" title="删除"/>', row.nactionid, '${appPath}/resources/images/extjs_icons/pencil.png', row.nactionid, '${appPath}/resources/images/extjs_icons/cancel.png');
						var html = "";
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
						html += '<img onclick="action_manage_edit_fn(' + row.nactionid + ');" src="${appPath}/resources/images/extjs_icons/pencil.png" title="修改"/>';
						</c:if>
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
						html += '&nbsp;<img onclick="action_manage_del_fn(' + row.nactionid + ');" src="${appPath}/resources/images/extjs_icons/cancel.png" title="删除"/>';
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
					action_manage_add_fn();
				}
			}, '-', 
			</c:if>
			<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
			{
				text : '批量删除',
				iconCls : 'icon-remove',
				handler : function() {
					action_manage_delAll_fn();
				}
			}
			</c:if>
			]
		});
	});

	
	function action_manage_add_fn() {
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/action/add.action',
			width : 400,
			height : 230,
			modal : true,
			title : '添加动作',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#action_manage_add_form').form('submit', {
						url : '${pageContext.request.contextPath}/action/insert.action',
						success : function(result) {
							try {
								var r = $.parseJSON(result);
								if (r.status) {
									//$('#action_manage_datagrid').datagrid('insertRow', {
									//	index : 0,
									//	row : r.data
									//});
									$('#action_manage_datagrid').datagrid('reload');
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
	function action_manage_del_fn(id) {
		$.messager.confirm('确认', '您是否要删除当前选中的动作吗？', function(r) {
			if (r) {
				$.post("${pageContext.request.contextPath}/action/delete.action",{ids : id},function(result){
					if (result.status) {
						$('#action_manage_datagrid').datagrid('load');
						$('#action_manage_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
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
	function action_manage_delAll_fn() {
		var rows = $('#action_manage_datagrid').datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			$.messager.confirm('确认', '您确定要删除当前选中的这些动作吗？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].nactionid);
					}
					//alert(ids);
					//return false;
					$.ajax({
						url : '${pageContext.request.contextPath}/action/delete.action',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(result) {
							if (result.status) {
								$('#action_manage_datagrid').datagrid('load');
								$('#action_manage_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
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
	
	
	
	function action_manage_edit_fn(id) {
		$('#action_manage_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		//alert(id);
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/action/edit.action?actionId=' + id,
			width : 400,
			height : 240,
			modal : true,
			title : '编辑动作',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#action_manage_edit_form').form('submit', {
						url : '${pageContext.request.contextPath}/action/update.action',
						success : function(result) {
							var r = $.parseJSON(result);
							if (r.status) {
								$('#action_manage_datagrid').datagrid('reload');
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
				var index = $('#action_manage_datagrid').datagrid('getRowIndex', id);
				var rows = $('#action_manage_datagrid').datagrid('getRows');
				$('#action_manage_edit_form').form('load', {
										id:rows[index].id,
										name:rows[index].name,
										menu:stringToList(rows[index].menu),
										permission:stringToList(rows[index].permission)
										});
			}
		});
	}
	
	
	
</script>
<table id="action_manage_datagrid"></table>