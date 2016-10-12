<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../common/inc.jsp"></jsp:include>
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
		$('#entityCategory_manage_treegrid').treegrid({
			url : '${appPath}/entityCategoryManage/treegrid.action',
			idField : 'id',
			treeField : 'text',
			parentField : 'pid',
			fit : true,
			fitColumns : false,  // 想让下面指定的列宽起作用，fitColumns 要设置为 false（不让自适应）
			border : false,
			frozenColumns : [ [ {
				title : '编号',
				field : 'id',
				width : 150,
				hidden : true
			}, {
				field : 'text',
				title : '行业名称',
				width : 250
			}, 
		    {
				field : 'sort',
				title : '排序值',
				align:'center',
				width : 50
			} ] ],
			columns : [ [ {
				field : 'pid',
				title : '上级行业ID',
				width : 50,
				hidden : true
			}
			<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit || requestScope.canDelete}">
			,{
				field : 'action',
				title : '动作',
				align:'center',
				width : 100,
				formatter : function(value, row, index) {
					//return formatString('<img onclick="entityCategory_manage_edit_fn(\'{0}\');" src="{1}" title="修改"/>&nbsp;<img onclick="entityCategory_manage_del_fn(\'{2}\');" src="{3}" title="删除"/>', row.id, '${appPath}/resources/images/extjs_icons/pencil.png', row.id, '${appPath}/resources/images/extjs_icons/cancel.png');
					var html = "";
					<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
					html += '<img onclick="entityCategory_manage_edit_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/pencil.png" title="修改"/>';
					</c:if>
					<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
					html += '&nbsp;<img onclick="entityCategory_manage_del_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/cancel.png" title="删除"/>';
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
					entityCategory_manage_add_fn();
				}
			}, '-', 
			</c:if>
			{
				text : '展开',
				iconCls : 'icon-redo',
				handler : function() {
					var node = $('#entityCategory_manage_treegrid').treegrid('getSelected');
					if (node) {
						$('#entityCategory_manage_treegrid').treegrid('expandAll', node.cid);
					} else {
						$('#entityCategory_manage_treegrid').treegrid('expandAll');
					}
				}
			}, '-', {
				text : '折叠',
				iconCls : 'icon-undo',
				handler : function() {
					var node = $('#entityCategory_manage_treegrid').treegrid('getSelected');
					if (node) {
						$('#entityCategory_manage_treegrid').treegrid('collapseAll', node.cid);
					} else {
						$('#entityCategory_manage_treegrid').treegrid('collapseAll');
					}
				}
			}, '-', {
				text : '刷新',
				iconCls : 'icon-reload',
				handler : function() {
					$('#entityCategory_manage_treegrid').treegrid('reload');
				}
			} ],
			onContextMenu : function(e, row) {
				e.preventDefault();
				$(this).treegrid('unselectAll');
				$(this).treegrid('select', row.id);
				$('#entityCategory_manage_contextMenu').entityCategoryManage('show', {
					left : e.pageX,
					top : e.pageY
				});
			}
		});
	});

	
	
	function entityCategory_manage_add_fn() {
		$('<div/>').dialog({
			id:'add_dialog',
			href : '${appPath}/entityCategoryManage/add.action',
			width : 340,
			height : 188,
			modal : true,
			title : '行业添加',
			buttons : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#entityCategory_manage_add_form').form('submit', {
						url : '${appPath}/entityCategoryManage/insert.action',
						success : function(result) {
							var r = $.parseJSON(result);
							if (r.status) {
								$("#add_dialog").dialog('destroy');
								$('#entityCategory_manage_treegrid').treegrid('reload');
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
	
	
	function entityCategory_manage_edit_fn(id) {
		if (id != undefined) {
			$('#entityCategory_manage_treegrid').treegrid('select', id);
		}
		var node = $('#entityCategory_manage_treegrid').treegrid('getSelected');
		$('<div/>').dialog({
			id:'update_dialog',
			href : '${appPath}/entityCategoryManage/edit.action?entityCategoryId='+node.id,
			width : 340,
			height : 188,
			modal : true,
			title : '编辑行业',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var d = $(this).closest('.window-body');
					  //if (formIsDirty(document.forms["entityCategory_manage_edit_form"])) { //有表单项存在修改
						  $('#entityCategory_manage_edit_form').form('submit', {
								url : '${appPath}/entityCategoryManage/update.action',
								success : function(result) {
									var r = $.parseJSON(result);
									if (r.status) {
										$("#update_dialog").dialog('destroy');
										$('#entityCategory_manage_treegrid').treegrid('reload');
									}
									$.messager.show({
										title : '提示',
										msg : r.msg
									});
								}
							});
					// }else{
					//	 d.dialog('destroy');
					//	 return ;
					// }
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	
	function entityCategory_manage_del_fn(id) {
		if (id != undefined) {
			$('#entityCategory_manage_treegrid').treegrid('select', id);
		}
		var node = $('#entityCategory_manage_treegrid').treegrid('getSelected');
		if (node) {
			$.messager.confirm('询问', '您确定要删除【' + node.text + '】？', function(b) {
				if (b) {
					$.post('${appPath}/entityCategoryManage/delete.action',{id:node.id},function(data){
						if (data.status) {
							$('#entityCategory_manage_treegrid').treegrid('remove', node.id);
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
<table id="entityCategory_manage_treegrid"></table>
<div id="entityCategory_manage_contextMenu" class="easyui-entityCategoryManage" style="width:120px;display: none;">
	<div onclick="entityCategory_manage_add_fn();" data-options="iconCls:'icon-add'">增加</div>
	<div onclick="entityCategory_manage_edit_fn();" data-options="iconCls:'icon-edit'">编辑</div>
	<div onclick="entityCategory_manage_del_fn();" data-options="iconCls:'icon-remove'">删除</div>
</div>