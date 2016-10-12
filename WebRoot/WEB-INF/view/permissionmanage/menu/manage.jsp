<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>菜单管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../common/inc.jsp" />
  </head>
  
  <body>
<script type="text/javascript">
	var iconData = [ {
		value : '',
		text : '默认'
	}, {
		value : 'icon-add',
		text : 'icon-add'
	}, {
		value : 'icon-edit',
		text : 'icon-edit'
	}, {
		value : 'icon-remove',
		text : 'icon-remove'
	}, {
		value : 'icon-save',
		text : 'icon-save'
	}, {
		value : 'icon-cut',
		text : 'icon-cut'
	}, {
		value : 'icon-ok',
		text : 'icon-ok'
	}, {
		value : 'icon-no',
		text : 'icon-no'
	}, {
		value : 'icon-cancel',
		text : 'icon-cancel'
	}, {
		value : 'icon-reload',
		text : 'icon-reload'
	}, {
		value : 'icon-search',
		text : 'icon-search'
	}, {
		value : 'icon-print',
		text : 'icon-print'
	}, {
		value : 'icon-help',
		text : 'icon-help'
	}, {
		value : 'icon-undo',
		text : 'icon-undo'
	}, {
		value : 'icon-redo',
		text : 'icon-redo'
	}, {
		value : 'icon-back',
		text : 'icon-back'
	}, {
		value : 'icon-sum',
		text : 'icon-sum'
	}, {
		value : 'icon-tip',
		text : 'icon-tip'
	}, {
		value : 'icon-pie',
		text : 'icon-pie'
	}, {
		value : 'icon-map',
		text : 'icon-map'
	}, {
		value : 'icon-tableErr',
		text : 'icon-tableErr'
	}, {
		value : 'icon-table',
		text : 'icon-table'
	} ];
	
	$(function() {
		$('#menu_manage_treegrid').treegrid({
			url : '${appPath}/menu/treegrid.action',
			idField : 'id',
			treeField : 'text',
			parentField : 'pid',
			fit : true,
			fitColumns : true,
			border : false,
			frozenColumns : [ [ { // 冻结这两列，当列太多出现横向滚动条时，往右拉这两列不动
				title : '编号',
				field : 'id',
				width : 150,
				hidden : true
			}, {
				field : 'text',
				title : '菜单名称',
				width : 180
			} ] ],
			columns : [ [ {
				field : 'url',
				title : '菜单路径',
				width : 200
			}, {
				field : 'seq',
				title : '排序',
				align:'center',
				width : 50
			}, {
				field : 'pid',
				title : '上级菜单ID',
				width : 150,
				hidden : true
			}
			<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit || requestScope.canDelete || requestScope.canBind}">
			,{
				field : 'action',
				title : '动作',
				align:'center',
				width : 50,
				formatter : function(value, row, index) {
					//return formatString('<img onclick="menu_manage_edit_fn(\'{0}\');" src="{1}" title="编辑"/>&nbsp;<img onclick="menu_manage_del_fn(\'{2}\');" src="{3}" title="删除"/>&nbsp;<img onclick="menu_action_bind_fn(\'{4}\');" src="{5}" title="绑定动作"/>', row.id, '${appPath}/resources/images/extjs_icons/pencil.png', row.id, '${appPath}/resources/images/extjs_icons/cancel.png', row.id, '${appPath}/resources/images/extjs_icons/group/group_gear.png');
					var html="";
					<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
					html += '<img onclick="menu_manage_edit_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/pencil.png" title="编辑"/>';
					</c:if>
					<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
					html += '&nbsp;<img onclick="menu_manage_del_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/cancel.png" title="删除"/>';
					</c:if>
					<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canBind}">
					html += '&nbsp;<img onclick="menu_action_bind_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/group/group_gear.png" title="绑定动作"/>';
					</c:if>
					return html;
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
					menu_manage_add_fn();
				}
			}, '-', 
			</c:if>
			{
				text : '展开',
				iconCls : 'icon-redo',
				handler : function() {
					var node = $('#menu_manage_treegrid').treegrid('getSelected');
					if (node) {
						$('#menu_manage_treegrid').treegrid('expandAll', node.cid);
					} else {
						$('#menu_manage_treegrid').treegrid('expandAll');
					}
				}
			}, '-', {
				text : '折叠',
				iconCls : 'icon-undo',
				handler : function() {
					var node = $('#menu_manage_treegrid').treegrid('getSelected');
					if (node) {
						$('#menu_manage_treegrid').treegrid('collapseAll', node.cid);
					} else {
						$('#menu_manage_treegrid').treegrid('collapseAll');
					}
				}
			}, '-', {
				text : '刷新',
				iconCls : 'icon-reload',
				handler : function() {
					$('#menu_manage_treegrid').treegrid('reload');
				}
			} ],
			onContextMenu : function(e, row) { // 列表上的右键菜单
				e.preventDefault();
				$(this).treegrid('unselectAll');
				$(this).treegrid('select', row.id);
				$('#menu_manage_contextMenu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			}
		});
	});

	function menu_action_bind_fn(id) {
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/menu/toMenuActionBind.action?menuId='+id,
			width : 300,
			height : 150,
			modal : true,
			title : '绑定动作',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-edit',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#menu_manage_actionBind_form').form('submit', {
						url : '${pageContext.request.contextPath}/menu/menuActionBind.action',
						success : function(result) {
							try {
								var r = $.parseJSON(result);
								/**
								if (r.status) {
									$('#menu_manage_treegrid').datagrid('updateRow', {
										index : $('#menu_manage_treegrid').datagrid('getRowIndex', id),
										row : r.data
									});
									$('#menu_manage_treegrid').datagrid('reload');
								}
								*/
								d.dialog('destroy');
								$.messager.show({ // 右下角的提示
									title : '提示',
									msg : r.msg
								});
							} catch (e) {
								$.messager.alert('提示', result); // 页面中间的提示
							}
						}
					});
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			},
			onLoad : function() {
				var actionIds = '';
				// jQuery $.post 不支持同步（按顺序执行），必须用 $.ajax
				$.ajax({ 
		          type : "post", 
		          url : "${appPath}/menu/getActionIds.action", 
		          data : "id=" + id, 
		          dataType : "json", 
		          async : false, 
		          success : function(data){ 
		            actionIds = data.msg;
		          } 
	          	}); 
				// 有这个才能实现修改时，把选过的选项打上勾（主要就是这个 stringToList）
				$('#menu_manage_actionBind_form').form('load', {
										actionIds:stringToList(actionIds)
				});
			}
		});
	}
	
	function menu_manage_add_fn() {
		$('<div/>').dialog({
			id : 'add_dialog',
			href : '${appPath}/menu/add.action',
			width : 300,
			height : 250,
			modal : true,
			title : '菜单添加',
			buttons : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#menu_manage_add_form').form('submit', {
						url : '${appPath}/menu/insert.action',
						success : function(result) {
							var r = $.parseJSON(result);
							if (r.status) {
								$("#add_dialog").dialog('destroy');
								$('#menu_manage_treegrid').treegrid('reload');
								//$('#layout_west_tree').tree('reload');  // 不知为何不执行，下面用了一种变通的方式
								window.parent.reloadWestTree();  
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
	
	
	function menu_manage_edit_fn(id) {
		if (id != undefined) {
			$('#menu_manage_treegrid').treegrid('select', id);
		}
		var node = $('#menu_manage_treegrid').treegrid('getSelected');
		$('<div/>').dialog({
			id : 'update_dialog',
			href : '${appPath}/menu/edit.action?id='+node.id,
			width : 300,
			height : 250,
			modal : true,
			title : '编辑菜单',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#menu_manage_edit_form').form('submit', {
						url : '${appPath}/menu/update.action',
						success : function(result) {
							var r = $.parseJSON(result);
							if (r.status) {
								$('#update_dialog').dialog('destroy');
								$('#menu_manage_treegrid').treegrid('reload');
								//$('#layout_west_tree').tree('reload');  // 不知为何不执行，下面用了一种变通的方式
								window.parent.reloadWestTree(); 
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
	
	
	function menu_manage_del_fn(id) {
		if (id != undefined) {
			$('#menu_manage_treegrid').treegrid('select', id);
		}
		var node = $('#menu_manage_treegrid').treegrid('getSelected');
		if (node) {
			$.messager.confirm('询问', '您确定要删除【' + node.text + '】？', function(b) {
				if (b) {
					$.post('${appPath}/menu/delete.action',{id:node.id},function(data){
						if (data.status) {
							$('#menu_manage_treegrid').treegrid('remove', node.id);
							//$('#layout_west_tree').tree('reload');  // 不知为何不执行，下面用了一种变通的方式
							window.parent.reloadWestTree();
						}
						$.messager.show({
							msg : data.msg,
							title : '提示'
						});
					},"json");
					
				}
			});
		}
	}
</script>
<table id="menu_manage_treegrid"></table>
<div id="menu_manage_contextMenu" class="easyui-menu" style="width:120px;display: none;">
	<div onclick="menu_manage_add_fn();" data-options="iconCls:'icon-add'">增加</div>
	<div onclick="menu_manage_edit_fn();" data-options="iconCls:'icon-edit'">编辑</div>
	<div onclick="menu_manage_del_fn();" data-options="iconCls:'icon-remove'">删除</div>
</div>
  </body>
  </html>