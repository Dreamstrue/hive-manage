<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../../common/inc.jsp"></jsp:include>
<script type="text/javascript">
	$(function() {
		$('#category_manage_datagrid').datagrid({
			url : '${appPath}/defectCar/dmCarBrandSeriesDatagrid.action',
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
			{
				title : '编号',
				field : 'id',
				width : 150,
				checkbox : true
			}, 
			{
				title : '品牌名称',
				field : 'brandname',
				width : 150,
				sortable : true
			}, 
			{
				title : '系列名称',
				field : 'seriesname',
				width : 150,
				sortable : true
			}] ],
			columns : [ [
				{
					title : '排序值',
					field : 'ordernum',
					width : 150,
					sortable : true
				},
			{
				title : '动作',
				field : 'action',
				align :'center',
				width : 100,
				formatter : function(value, row, index) {
						//return formatString('<img onclick="category_manage_edit_fn(\'{0}\');" src="{1}" title="修改"/>&nbsp;<img onclick="category_manage_del_fn(\'{2}\');" src="{3}" title="删除"/>', row.id, '${appPath}/resources/images/extjs_icons/pencil.png', row.id, '${appPath}/resources/images/extjs_icons/cancel.png');
						var html = "";
						html += '<img onclick="category_manage_edit_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/pencil.png" title="修改"/>';
						html += '&nbsp;<img onclick="category_manage_del_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/cancel.png" title="删除"/>';
						return html;
				}
			} 
			] ],
			toolbar : [ 
			{
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					category_manage_add_fn();
				}
			}, '-', 
			{
				text : '批量删除',
				iconCls : 'icon-remove',
				handler : function() {
					category_manage_delAll_fn();
				}
			}
			]
		});
	});

	
	function category_manage_add_fn() {
		$('<div/>').dialog({
			id : 'add_dialog',
			href : '${appPath}/defectCar/todmCarBrandSeriesAdd.action',
			width : 350,
			height : 200,
			modal : true,
			title : '添加类别',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#category_manage_add_form').form('submit', {
						url : '${appPath}/defectCar/dmCarBrandSeriesInsert.action',
						success : function(result) {
							try {
								var r = $.parseJSON(result);
								if (r.status) {
									//$('#category_manage_datagrid').datagrid('insertRow', {
									//	index : 0,
									//	row : r.data
									//});
									$("#add_dialog").dialog('destroy');
									$('#category_manage_datagrid').datagrid('reload');
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
	function category_manage_del_fn(id) {
		$.messager.confirm('确认', '您确定要删除当前选中的类别吗？', function(r) {
			if (r) {
				$.post("${pageContext.request.contextPath}/defectCar/dmCarBrandSeriesDelete.action",{ids : id},function(result){
					if (result.status) {
						$('#category_manage_datagrid').datagrid('load');
						$('#category_manage_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
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
	function category_manage_delAll_fn() {
		var rows = $('#category_manage_datagrid').datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			$.messager.confirm('确认', '您确定要删除当前选中的这些类别吗？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					//alert(ids);
					//return false;
					$.ajax({
						url : '${pageContext.request.contextPath}/defectCar/dmCarBrandSeriesDelete.action',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(result) {
							if (result.status) {
								$('#category_manage_datagrid').datagrid('load');
								$('#category_manage_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
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
				msg : '请勾选要删除的类别！'
			});
		}
	}
	
	
	
	function category_manage_edit_fn(id) {
		$('#category_manage_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		//alert(id);
		$('<div/>').dialog({
			id : 'update_dialog',
			href : '${pageContext.request.contextPath}/defectCar/todmCarBrandSeriesEdit.action?id=' + id,
			width : 350,
			height : 200,
			modal : true,
			title : '修改类别',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#category_manage_edit_form').form('submit', {
						url : '${pageContext.request.contextPath}/defectCar/dmCarBrandSeriesUpdate.action',
						success : function(result) {
							var r = $.parseJSON(result);
							if (r.status) {
								$('#update_dialog').dialog('destroy');
								$('#category_manage_datagrid').datagrid('reload');
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
				var index = $('#category_manage_datagrid').datagrid('getRowIndex', id);
				var rows = $('#category_manage_datagrid').datagrid('getRows');
				$('#category_manage_edit_form').form('load', {
										id:rows[index].id,
										name:rows[index].name,
										menu:stringToList(rows[index].menu),
										permission:stringToList(rows[index].permission)
										});
			}
		});
	}
	
	
	
</script>
<table id="category_manage_datagrid"></table>