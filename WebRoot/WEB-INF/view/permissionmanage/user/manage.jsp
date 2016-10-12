<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<script type="text/javascript">
	$(function() {

		$('#user_manage_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/user/datagrid.action',
			fit : true,
			fitColumns : true,
			border : false,
			rownumbers:true,
			pagination : true,
			idField : 'id',
			pageSize : 20,
			pageList : [10, 20, 30, 40, 50 ],
			sortName : 'username',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			singleSelect:true,
			nowrap : false,
			frozenColumns : [ [ 
			<%-- 复选框就是为了批量删除，如果没有删除权限就不用显示了 --%>
			<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
			{
				field : 'id',
				title : '编号',
				width : 150,
				checkbox : true
			}, 
			</c:if>
			{
				field : 'username',
				title : '登录名称',
				align :'center',
				width : 120,
				sortable : true
			} , {
				field : 'fullname',
				title : '姓名',
				align :'center',
				width : 120,
				sortable : true
			} ] ],
			columns : [ [ {
				field : 'mobilephone',
				title : '电话',
				align :'center',
				width : 100
			}, {
				field : 'email',
				title : '邮箱',
				width : 100,
				align :'center',
				sortable : true
			}, {
				field : 'departmentName',
				title : '部门',
				width : 100,
				align :'center',
				sortable : true
			}, {
				field : 'admin',
				title : '是否管理员',
				width : 100,
				align :'center',
				sortable : true,
				formatter : function(value, row, index) {
					if (value == '1') {
						return '是';
					} else {
						return '否';
					}
				}
			}
			<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit || requestScope.canDelete || requestScope.canEditPassword || requestScope.canBind}">
			, {
				field : 'action',
				title : '动作',
				width : 100,
				align :'center',
				formatter : function(value, row, index) {
					if (row.id == '0') {
						return '系统用户';
					} else {
						var html = "";
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
						html += '<img onclick="user_manage_edit_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/pencil.png" title="修改"/>';
						</c:if>
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
						html += '&nbsp;<img onclick="user_manage_del_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/cancel.png" title="删除"/>';
						</c:if>
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEditPassword}">
						html += '&nbsp;<img onclick="user_manage_editPassword(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/lock/lock_edit.png" title="修改密码"/>';
						</c:if>
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canBind}">
						html += '&nbsp;<img onclick="user_role_bind_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/group/group_gear.png" title="绑定角色"/>';
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
					user_manage_add_fn();
				}
			}, '-', 
			</c:if>
			<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
			{
				text : '批量删除',
				iconCls : 'icon-remove',
				handler : function() {
					user_manage_delAll_fn();
				}
			}
			</c:if>
			]
		});

	});
	
	
	function user_manage_search_fn() {
		$('#user_manage_datagrid').datagrid('load', serializeObject($('#user_manage_search_form')));
	}
	function user_manage_clean_form_fn() {
		$('#user_manage_search_form input').val('');
		$('#user_manage_datagrid').datagrid('load', {});
	}
	//添加新用户
	function user_manage_add_fn(){
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/user/add.action',
			width : 350,
			height : 330,
			modal : true,
			title : '添加用户',
			buttons : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#user_manage_add_form').form('submit', {
						url : '${pageContext.request.contextPath}/user/insert.action',
						success : function(result) {
							var r = $.parseJSON(result);
							if (r.status) {
								$('#user_manage_datagrid').datagrid('insertRow', {
									index : 0,
									row : r.data
								});
								$('#user_manage_datagrid').datagrid('reload');
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
	
	//批量删除用户
	function user_manage_delAll_fn() {
		var rows = $('#user_manage_datagrid').datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			$.messager.confirm('确认', '您是否要删除当前选中的项吗？', function(r) {
				if (r) {
					var currentUserId = '${authInfo.id}';/*当前登录用户的ID*/
					var flag = false;
					var sysflag = false;
					for ( var i = 0; i < rows.length; i++) {
						if(rows[i].id!='0'){
							if (currentUserId != rows[i].id) {
								ids.push(rows[i].id);
							} else {
								flag = true;
							}
						}else{
							sysflag=true;
						}
					}
					
					if (flag) {
						$.messager.show({
							title : '提示',
							msg : '不可以删除自己！'
						});
					} else if(sysflag){
						$.messager.show({
							title : '提示',
							msg : '系统用户不可以删除！'
						});
					}else{
						$.ajax({
							url : '${pageContext.request.contextPath}/user/delete.action',
							data : {
								ids : ids.join(',')
							},
							dataType : 'json',
							success : function(result) {
								if (result.status) {
									$('#user_manage_datagrid').datagrid('load');
									$('#user_manage_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
								}
								$.messager.show({
									title : '提示',
									msg : result.msg
								});
							}
						});
					}
					
				}
			});
		} else {
			$.messager.show({
				title : '提示',
				msg : '请勾选要删除的记录！'
			});
		}
	}
	
	//单个删除
	function user_manage_del_fn(id) {
		$.messager.confirm('确认', '您是否要删除吗？', function(r) {
			if (r) {
				var currentUserId = '${authInfo.id}';/*当前登录用户的ID*/
				if(currentUserId==id){
					$.messager.show({
						title : '提示',
						msg : '不可以删除自己！'
					});
				}else{
					$.post("${pageContext.request.contextPath}/user/delete.action",{ids : id},function(result){
						if (result.status) {
							$('#user_manage_datagrid').datagrid('load');
							$('#user_manage_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
						}
						$.messager.show({
							title : '提示',
							msg : result.msg
						});
					},'json');
				}
			}
		});
		
	}
	
	//修改密码
	function user_manage_editPassword(id){
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/user/editPassword.action?id='+id,
			width : 300,
			height : 150,
			modal : true,
			title : '修改密码',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#user_manage_editPassword_form').form('submit', {
						url : '${pageContext.request.contextPath}/user/updatePassword.action',
						success : function(result) {
							var r = $.parseJSON(result);
							if (r.status) {
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
				$('#admin_yhglEditPwd_editForm').form('load',{
					id : id
				});
			}
		});
		
	}
	
	function user_role_bind_fn(id){
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/user/toUserRoleBind.action?userId='+id,
			width : 300,
			height : 150,
			modal : true,
			title : '绑定角色',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-edit',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#user_role_bind_form').form('submit', {
						url : '${pageContext.request.contextPath}/user/userRoleBind.action',
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
				var roleIds = '';
				// jQuery $.post 不支持同步（按顺序执行），必须用 $.ajax
				$.ajax({ 
		          type : "post", 
		          url : "${appPath}/user/getRoleIds.action", 
		          data : "id=" + id, 
		          dataType : "json", 
		          async : false, 
		          success : function(data){ 
		            roleIds = data.msg;
		          } 
	          	}); 
				// 有这个才能实现修改时，把选过的选项打上勾（主要就是这个 stringToList）
				$('#user_role_bind_form').form('load', {
										roleIds:stringToList(roleIds)
				});
			}
		});
	}
	
	function user_manage_edit_fn(id) {
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/user/edit.action?userId=' + id,
			width : 350,
			height : 270,
			modal : true,
			title : '编辑用户',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-edit',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#user_manage_edit_form').form('submit', {
						url : '${pageContext.request.contextPath}/user/update.action',
						success : function(result) {
							try {
								var r = $.parseJSON(result);
								if (r.status) {
									$('#user_manage_datagrid').datagrid('updateRow', {
										index : $('#user_manage_datagrid').datagrid('getRowIndex', id),
										row : r.data
									});
									$('#user_manage_datagrid').datagrid('reload');
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
			},
			onLoad : function() {
				var index = $('#user_manage_datagrid').datagrid('getRowIndex', id);
				var rows = $('#user_manage_datagrid').datagrid('getRows');
				$('#user_manage_edit_form').form('load', {
										id:rows[index].id,
										username:rows[index].username,
										name:rows[index].name,
										label:stringToList(rows[index].label)
				});
			}
		});
	}
	
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'north',title:'查询条件',border:false" style="height: 62px;overflow: hidden;">
		<form action="javascript:void(0);" id="user_manage_search_form" style="width: 100%">
			<table class="tableForm" style="width: 100%">
				<tr>
					<th style="width: 170px;"><span style="font-size:12px">检索用户名称(可模糊查询)</span></th>
					<td>
						<input name="keys" style="width: 215px;" />
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="user_manage_search_fn();">过滤条件</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="user_manage_clean_form_fn();">清空条件</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="user_manage_datagrid"></table>
	</div>
</div>