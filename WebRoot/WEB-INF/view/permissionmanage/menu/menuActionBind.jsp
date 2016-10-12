<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">

	
</script>
<div align="center" style="width: 100%">
	<form id="menu_manage_actionBind_form" method="post" style="width: 100%">
		<input type="hidden" name="menuId" value="${vo.id }">
		<table class="tableForm" style="width: 100%">
			<tr>
				<th style="width: 70px;">菜单名称</th>
				<td>
					<input name="menuname"  style="width称: 155px;" readonly="readonly" value="${vo.text }"/>
				</td>
			</tr>
			<tr>
				<th>绑定动作</th>
				<td>
					 <select id="aa" name="actionIds" class="easyui-combogrid" style="width:155px" data-options="
				            panelWidth: 300,
				            multiple: true,
				            idField: 'nactionid', // 往后台传的东西
				            textField: 'cactionname', // 要显示到选择框的东西
				            url: '${pageContext.request.contextPath}/action/datagrid.action',
				            method: 'get',
				            columns: [[
				                {field:'nactionid',checkbox:true},
				                {field:'cactionname',title:'动作名称',width:120,align:'center'},
				                {field:'cactiondescription',title:'动作描述',width:80,align:'center'}
				            ]],
				            fitColumns: true
				        ">
				    </select>
		    	</td>
			</tr>
		</table>
	</form>
</div>