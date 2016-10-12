<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<style>
.tableForm th{
	font-size: 13px;
}
</style>
<script type="text/javascript">

	
</script>
<div align="center" style="width: 100%">
	<form id="sysconfig_manage_edit_form" method="post" style="width: 100%">
		<input type="hidden" id="id" name="id" value="${vo.id }">
		<input type="hidden" id="valid" name="valid" value="${vo.valid }">
		<input type="hidden" id="parameCode" name="parameCode" value="${vo.parameCode }">
		<table class="tableForm" style="width: 100%">
			<tr>
				<th>参数类别</th>
				<td>
					<input name="parameCategory" value="${vo.parameCategory }" class="easyui-validatebox" data-options="required:true"  style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<th>参数名称</th>
				<td>
					<input name="parameName"  value="${vo.parameName }" class="easyui-validatebox" data-options="required:true"  style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<th>参数代码</th>
				<td>
					<input name="parameCode" value="${vo.parameCode }"  class="easyui-validatebox"   style="width: 200px;"  disabled="disabled"/>
				</td>
			</tr>
			<tr>
				<th>参数默认值</th>
				<td>
					<input name="parameDefaultValue" value="${vo.parameDefaultValue }"  class="easyui-validatebox" data-options="required:true"  style="width: 200px;" />
				</td>
			</tr>
			<%--<tr>
				<th>参数当前值</th>
				<td>
					<input name="parameCurrentValue"  value="${vo.parameCurrentValue }" class="easyui-validatebox" data-options=""  style="width: 200px;" />
				</td>
			</tr>
		--%></table>
	</form>
</div>