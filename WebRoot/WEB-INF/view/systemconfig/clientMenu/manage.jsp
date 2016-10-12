<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<script type="text/javascript">
	$(function() {
		$('#cmenu_manage_datagrid').datagrid({
			url : '${appPath}/clientMenu/list.action',
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
				title : '菜单名称',
				field : 'menuName',
				width : 150,
				sortable : true
			} , {
				title : '排序',
				field : 'sort',
				width : 30,
				sortable : true
			}, {
				title : '版本类型',
				field : 'versionType',
				width : 100,
				formatter : function(value, row, index) {
					if(value=='1'){
						return "<font color='red'>企业版</font>";
					}else{
						return "大众版";
					}
					
				}
			}, {
				title : '状态',
				field : 'valid',
				width : 50,
				formatter : function(value, row, index) {
					if(value=='1'){
						return "正常";
					}else{
						return "被屏蔽";
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
							html += '<img onclick="clientMenu_manage_edit_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/pencil.png" title="修改"/>';
							html += '&nbsp;<img onclick="clientMenu_manage_del_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/delete.png" title="禁用"/>';
						}else{
							html += '<img onclick="clientMenu_manage_back_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/eye.png" title="恢复" />';
						}
						return html;
				}
			}
			 ] ],
			toolbar : [{
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					clientMenu_manage_add_fn();
				}
			},'-',{
				text : '刷新',
				iconCls : 'icon-reload',
				handler : function() {
					$('#cmenu_manage_datagrid').datagrid('load');
				}
			}]
		});
	});

	
	function clientMenu_manage_add_fn() {
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/clientMenu/add.action',
			width : 360,
			height : 200,
			modal : true,
			title : '添加菜单',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#clientMenu_manage_add_form').form('submit', {
						url : '${pageContext.request.contextPath}/clientMenu/insert.action',
						success : function(result) {
							try {
								var r = $.parseJSON(result);
								if (r.status) {
									$('#cmenu_manage_datagrid').datagrid('reload');
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
	function clientMenu_manage_del_fn(id) {
		$.messager.confirm('确认', '您是否要禁用当前选中的菜单吗？', function(r) {
			if (r) {
				$.post("${pageContext.request.contextPath}/clientMenu/delete.action",{id : id},function(result){
					if (result.status) {
						$('#cmenu_manage_datagrid').datagrid('load');
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
	function clientMenu_manage_back_fn(id) {
		$.messager.confirm('确认', '您是否要恢复当前选中的菜单吗？', function(r) {
			if (r) {
				$.post("${pageContext.request.contextPath}/clientMenu/back.action",{id : id},function(result){
					if (result.status) {
						$('#cmenu_manage_datagrid').datagrid('load');
					}
					$.messager.show({
						title : '提示',
						msg : result.msg
					});
				},'json');
			}
		});
	}

	
	
	
	function clientMenu_manage_edit_fn(id) {
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/clientMenu/edit.action?id=' + id,
			width : 360,
			height : 200,
			modal : true,
			title : '编辑菜单',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var d = $(this).closest('.window-body');
					if (formIsDirty(document.forms["clientMenu_manage_edit_form"])) { //有表单项存在修改
						$('#clientMenu_manage_edit_form').form('submit', {
							url : '${pageContext.request.contextPath}/clientMenu/update.action',
							success : function(result) {
								var r = $.parseJSON(result);
								if (r.status) {
									$('#cmenu_manage_datagrid').datagrid('reload');
									d.dialog('destroy');
								}
								$.messager.show({
									title : '提示',
									msg : r.msg
								});
							}
						});
					}else{
						d.dialog('destroy');
						return ;
					}
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	
	
</script>
<table id="cmenu_manage_datagrid"></table>