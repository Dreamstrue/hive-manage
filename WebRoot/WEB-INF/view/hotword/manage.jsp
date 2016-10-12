<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/inc.jsp"></jsp:include>
<script type="text/javascript">
	$(function() {
		$('#hotword_manage_datagrid').datagrid({
			url : '${appPath}/hotWord/datagrid.action',
			fit : true,
			fitColumns : false,
			border : false,
			pagination : true,
			rownumbers:true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'id',
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
				field : 'id',
				width : 150,
				checkbox : true
			}, 
			</c:if>
			{
				title : '关键词名称',
				field : 'name',
				width : 150,
				sortable : true
			} , {
				title : '链接地址',
				field : 'href',
				width : 350,
				sortable : true
			} , {
				title : '有效期起',
				field : 'beginTime',
				width : 100,
				sortable : true
			} , {
				title : '有效期止',
				field : 'endTime',
				width : 100,
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
					//alert(row.id);
					if (row.id == '0') {
						return '系统动作';
					} else {
						//return formatString('<img onclick="hotword_manage_edit_fn(\'{0}\');" src="{1}" title="修改"/>&nbsp;<img onclick="hotword_manage_del_fn(\'{2}\');" src="{3}" title="删除"/>', row.id, '${appPath}/resources/images/extjs_icons/pencil.png', row.id, '${appPath}/resources/images/extjs_icons/cancel.png');
						var html = "";
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
						html += '<img onclick="hotword_manage_edit_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/pencil.png" title="修改"/>';
						</c:if>
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
						html += '&nbsp;<img onclick="hotword_manage_del_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/cancel.png" title="删除"/>';
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
					hotword_manage_add_fn();
				}
			}, '-', 
			</c:if>
			<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
			{
				text : '批量删除',
				iconCls : 'icon-remove',
				handler : function() {
					hotword_manage_delAll_fn();
				}
			}
			</c:if>
			]
		});
	});

	
	function hotword_manage_add_fn() {
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/hotWord/add.action',
			width : 400,
			height : 230,
			modal : true,
			title : '添加热门推荐',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#hotword_manage_add_form').form('submit', {
						url : '${pageContext.request.contextPath}/hotWord/insert.action',
						success : function(result) {
							try {
								var r = $.parseJSON(result);
								if (r.status) {
									//$('#hotword_manage_datagrid').datagrid('insertRow', {
									//	index : 0,
									//	row : r.data
									//});
									$('#hotword_manage_datagrid').datagrid('reload');
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
	function hotword_manage_del_fn(id) {
		$.messager.confirm('确认', '您确定要删除当前选中的记录吗？', function(r) {
			if (r) {
				$.post("${pageContext.request.contextPath}/hotWord/delete.action",{ids : id},function(result){
					if (result.status) {
						$('#hotword_manage_datagrid').datagrid('load');
						$('#hotword_manage_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
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
	function hotword_manage_delAll_fn() {
		var rows = $('#hotword_manage_datagrid').datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			$.messager.confirm('确认', '您确定要删除当前选中的记录吗？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					//alert(ids);
					//return false;
					$.ajax({
						url : '${pageContext.request.contextPath}/hotWord/delete.action',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(result) {
							if (result.status) {
								$('#hotword_manage_datagrid').datagrid('load');
								$('#hotword_manage_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
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
				msg : '请选择要删除的记录！'
			});
		}
	}
	
	
	
	function hotword_manage_edit_fn(id) {
		$('#hotword_manage_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		//alert(id);
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/hotWord/edit.action?id=' + id,
			width : 400,
			height : 240,
			modal : true,
			title : '编辑热门推荐',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#hotword_manage_edit_form').form('submit', {
						url : '${pageContext.request.contextPath}/hotWord/update.action',
						success : function(result) {
							var r = $.parseJSON(result);
							if (r.status) {
								$('#hotword_manage_datagrid').datagrid('reload');
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
				var index = $('#hotword_manage_datagrid').datagrid('getRowIndex', id);
				var rows = $('#hotword_manage_datagrid').datagrid('getRows');
				$('#hotword_manage_edit_form').form('load', {
										id:rows[index].id,
										name:rows[index].name,
										menu:stringToList(rows[index].menu),
										permission:stringToList(rows[index].permission)
										});
			}
		});
	}
	
	
	
</script>
<table id="hotword_manage_datagrid"></table>