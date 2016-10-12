<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div align="center" style="width: 100%">
	<form id="action_manage_edit_form" method="post" style="width: 100%">
		<input type="hidden" name="nactionid" value="${vo.nactionid }">
		<input type="hidden" name="iactionvalue" value="${vo.iactionvalue }">
		<table class="tableForm" style="width: 100%">
			<tr>
				<th>动作名称</th>
				<td><input name="cactionname" value="${vo.cactionname }" class="easyui-validatebox" data-options="required:true" /></td>
			</tr>
			<tr>
				<th>动作字符串</th>
				<td><input name="cactionstr" value="${vo.cactionstr }" class="easyui-validatebox" data-options="required:true" /></td>
			</tr>
			<tr>
				<th>动作描述</th>
				<td colspan="3">
					<textarea name="cactiondescription" style="width:240px;height:72px;" class="easyui-validatebox" style="width:600px;" validType="maxLength[500]" invalidMessage="请输入不要超过500个字符">${vo.cactiondescription }</textarea>
				</td>
			</tr>
		</table>
	</form>
</div>