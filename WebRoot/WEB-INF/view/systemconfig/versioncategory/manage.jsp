<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<script type="text/javascript">
	$(function() {
		$('#version_manage_datagrid').datagrid({
			url : '${appPath}/versionCate/list.action',
			fit : true,
			fitColumns : false,
			border : false,
			pagination : true,
			rownumbers:true,
			idField : 'id',
	//		pageSize : 10,
	//		pageList : [ 10, 20, 30, 40, 50 ],
	//		sortName : 'nroleid',
//			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			singleSelect:true,
			nowrap : false,
			frozenColumns : [ [ 
			{
				title : '编号',
				field : 'id',
				width : 150,
				hidden:true
			}, 
			{
				title : '版本名称',
				field : 'versionName',
				width : 150,
				sortable : true
			} , {
				title : '版本类别',
				field : 'versionCate',
				width : 150,
				sortable : true
			} , {
				title : '版本号',
				field : 'versionNo',
				width : 100,
				sortable : true
			}, {
				title : '状态',
				field : 'valid',
				width : 50,
				formatter : function(value, row, index) {
					if(value=='1'){
						return "正常";
					}else{
						return "禁用";
					}
					
				}
			}] ],
			columns : [ [
			{
				title : '动作',
				field : 'action',
				align :'center',
				width : 100,
				formatter : function(value, row, index) {
						var html = "";
						if(row.valid=="1"){
							html += '<img onclick="versionCate_manage_edit_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/pencil.png" title="修改"/>';
							html += '&nbsp;<img onclick="versionCate_manage_del_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/delete.png" title="禁用"/>';
						}else{
							html += '<img onclick="versionCate_manage_back_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/eye.png" title="恢复" />';
						}
						return html;
				}
			}
			 ] ],
			toolbar : [{
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					versionCate_manage_add_fn();
				}
			},'-',{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#version_manage_datagrid').datagrid('reload');
   					}
   				}]
		});
	});

	
	function versionCate_manage_add_fn() {
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/versionCate/add.action',
			width : 360,
			height : 180,
			modal : true,
			title : '添加版本',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#versionCate_manage_add_form').form('submit', {
						url : '${pageContext.request.contextPath}/versionCate/insert.action',
						success : function(result) {
							try {
								var r = $.parseJSON(result);
								if (r.status) {
									$('#version_manage_datagrid').datagrid('reload');
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
	
	//单个禁用
	function versionCate_manage_del_fn(id) {
		$.messager.confirm('确认', '您是否要禁用当前选中的版本吗？', function(r) {
			if (r) {
				$.post("${pageContext.request.contextPath}/versionCate/delete.action",{id : id},function(result){
					if (result.status) {
						$('#version_manage_datagrid').datagrid('load');
					}
					$.messager.show({
						title : '提示',
						msg : result.msg
					});
				},'json');
			}
		});
	}
	
	//恢复
	function versionCate_manage_back_fn(id) {
		$.messager.confirm('确认', '您是否要恢复当前选中的版本吗？', function(r) {
			if (r) {
				$.post("${pageContext.request.contextPath}/versionCate/back.action",{id : id},function(result){
					if (result.status) {
						$('#version_manage_datagrid').datagrid('load');
					}
					$.messager.show({
						title : '提示',
						msg : result.msg
					});
				},'json');
			}
		});
	}

	
	
	
	function versionCate_manage_edit_fn(id) {
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/versionCate/edit.action?id=' + id,
			width : 360,
			height : 180,
			modal : true,
			title : '编辑版本',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#versionCate_manage_edit_form').form('submit', {
						url : '${pageContext.request.contextPath}/versionCate/update.action',
						success : function(result) {
							var r = $.parseJSON(result);
							if (r.status) {
								$('#version_manage_datagrid').datagrid('reload');
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
	
	
	
</script>
<table id="version_manage_datagrid"></table>