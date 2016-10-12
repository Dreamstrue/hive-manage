<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div align="center" style="width: 100%">
	<form id="versionCate_manage_edit_form" method="post" style="width: 100%">
		<input type="hidden" name="id" value="${vo.id}">
		<input type="hidden" name="valid" value="${vo.valid}">
		<input type="hidden" name="versionNo" value="${vo.versionNo}">
		<table class="tableForm" style="width: 100%">
			<tr>
				<th>版本名称</th>
				<td><input name="versionName" value="${vo.versionName}" class="easyui-validatebox" data-options="required:true" /></td>
			</tr>
			<tr>
				<th>版本类别</th>
				<td><input name="versionCate" value="${vo.versionCate}" class="easyui-validatebox" readonly="readonly" /></td>
			</tr>
		</table>
	</form>
</div>