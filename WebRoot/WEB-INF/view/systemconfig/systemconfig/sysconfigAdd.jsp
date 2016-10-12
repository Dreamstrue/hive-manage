<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<style>
.tableForm th{
	font-size: 13px;
}
</style>
<script type="text/javascript">

	
</script>
<div align="center" style="width: 100%">
	<form id="sysconfig_manage_add_form" method="post" style="width: 100%">
		<table class="tableForm" style="width: 100%">
			<tr>
				<th>参数类别</th>
				<td>
					<input name="parameCategory" class="easyui-validatebox" data-options="required:true"  style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<th>参数名称</th>
				<td>
					<input name="parameName" class="easyui-validatebox" data-options="required:true"  style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<th>参数代码</th>
				<td>
					<input name="parameCode" class="easyui-validatebox" data-options="required:true"  style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<th>参数默认值</th>
				<td>
					<input name="parameDefaultValue" class="easyui-validatebox" data-options="required:true"  style="width: 200px;" />
				</td>
			</tr>
		</table>
	</form>
</div>