<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div align="center" style="width: 100%">
	<form id="action_manage_add_form" method="post" style="width: 100%">
		<table class="tableForm" style="width: 100%">
			<tr>
				<th>动作名称</th>
				<td><input name="cactionname" class="easyui-validatebox" data-options="required:true" /></td>
			</tr>
			<tr>
				<th>动作字符串</th>
				<td><input name="cactionstr" class="easyui-validatebox" data-options="required:true" /></td>
			</tr>
			<tr>
				<th>动作描述</th>
				<td colspan="3">
					<textarea name="cactiondescription" style="width:240px;height:72px;" class="easyui-validatebox" style="width:600px;" validType="maxLength[500]" invalidMessage="请输入不要超过500个字符"></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>