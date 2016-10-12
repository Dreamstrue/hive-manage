<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div align="center" style="width: 100%">
	<form id="edit_currentUser_password_form" method="post" style="width: 100%">
		<input type="hidden" name="id" value="${vo.id }">
		<table class="tableForm" style="width: 100%">
			<tr>
				<th style="width: 70px;">用户名</th>
				<td>
					<input name="username" value="${vo.username }" style="width: 155px;" readonly="readonly" />
				</td>
			</tr>
			
			<tr>
				<th>旧密码</th>
				<td>
					<input name="oldpassword" id="oldpassword" type="password" class="easyui-validatebox" data-options="required:true"  style="width: 155px;" />
				</td>
			</tr>
			
			<tr>
				<th>密码</th>
				<td>
					<input id="password" name="password" type="password" class="easyui-validatebox" data-options="required:true" validType="eqnewPwd['#oldpassword']" style="width: 155px;" />
				</td>
			</tr>
			<tr>
				<th>确认密码</th>
				<td>
					<input id="repassword" name="repassword" type="password" class="easyui-validatebox" required="required" validType="eqPwd['#password']" style="width: 155px;" />
				</td>
			</tr>
		</table>
	</form>
</div>