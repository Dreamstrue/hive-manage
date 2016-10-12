<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div align="center" style="width: 100%">
	<form id="user_manage_editPassword_form" method="post" style="width: 100%">
		<input type="hidden" name="id" value="${vo.id}">
		<table class="tableForm" style="width: 100%">
			<tr>
				<th>用户名</th>
				<td><input name="username" readonly="readonly"  style="width: 155px;" value="${vo.username}"/></td>
			</tr>
			<tr>
				<th>新密码</th>
				<td><input name="password" type="password"class="easyui-validatebox" data-options="required:true"  style="width: 155px;"  />
				</td>
			</tr>
		</table>
	</form>
</div>