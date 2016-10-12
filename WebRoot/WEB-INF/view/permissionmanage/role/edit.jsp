<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div align="center" style="width: 100%">
	<form id="role_manage_edit_form" method="post" style="width: 100%">
		<input type="hidden" name="nroleid" value="${vo.nroleid }">
		<table class="tableForm" style="width: 100%">
			<tr>
				<th>角色名称</th>
				<td><input name="crolename" value="${vo.crolename }" class="easyui-validatebox" data-options="required:true" /></td>
			</tr>
			<tr>
				<th>角色描述</th>
				<td colspan="3">
					<textarea name="croledescription" style="width:240px;height:72px;" class="easyui-validatebox" style="width:600px;" validType="maxLength[500]" invalidMessage="请输入不要超过500个字符">${vo.croledescription }</textarea>
				</td>
			</tr>
		</table>
	</form>
</div>