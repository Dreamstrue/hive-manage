<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">

	$(function() {
		$('#user_manage_role_datagrid').combogrid({
			multiple : false,
			nowrap : false,
			url : '${appPath}/menu/treegrid.action',
			panelWidth : 450,
			panelHeight : 200,
			idField : 'nroleid',
			textField : 'crolename',
			pagination : true,
			fitColumns : true,
			rownumbers : true,
			editable : true,
			mode : 'remote',
			delay : 500,
			pageSize : 5,
			pageList : [ 5, 10 ],
			columns : [ [ {
				field : 'nroleid',
				title : '编号',
				width : 150,
				hidden : true
			}, {
				field : 'text',
				title : '角色名称',
				width : 150
			}] ]
		});
	});
	
</script>
<div align="center" style="width: 100%">
	<form id="user_role_bind_form" method="post" style="width: 100%">
		<input type="hidden" name="userId" value="${vo.id }">
		<table class="tableForm" style="width: 100%">
			<tr>
				<th style="width: 70px;">用户名</th>
				<td>
					<input name="username"  style="width: 155px;" readonly="readonly" value="${vo.username }"/>
				</td>
			</tr>
			<tr>
				<th>绑定角色</th>
				<td>
					 <select id="aa" name="roleIds" class="easyui-combogrid" style="width:155px" data-options="
				            panelWidth: 300,
				            multiple: true,
				            idField: 'nroleid', // 往后台传的东西
				            textField: 'crolename', // 要显示到选择框的东西
				            url: '${pageContext.request.contextPath}/role/datagrid.action',
				            method: 'get',
				            columns: [[
				                {field:'nroleid',checkbox:true},
				                {field:'crolename',title:'角色名称',width:120,align:'center'},
				                {field:'croledescription',title:'角色描述',width:80,align:'center'}
				            ]],
				            fitColumns: true
				        ">
				    </select>
		    	</td>
			</tr>
		</table>
	</form>
</div>