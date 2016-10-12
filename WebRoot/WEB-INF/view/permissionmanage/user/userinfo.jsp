<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div align="center" style="width: 100%">
	<form id="edit_currentUser_form" method="post" style="width: 100%">
		<input type="hidden" name="id" value="${vo.id }">
		<table class="tableForm" style="width: 100%">
			<tr>
				<th style="width: 70px;">用户名</th>
				<td>
					<input name="username" value="${vo.username }" class="easyui-validatebox" data-options="required:true"  style="width: 155px;" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<th>姓名</th>
				<td>
					<input name="fullname" value="${vo.fullname }" style="width: 155px;" />
				</td>
			</tr>
			<tr>
				<th>电话</th>
				<td>
					<input name="mobilephone" value="${vo.mobilephone }" style="width: 155px;" />
				</td>
			</tr>
			<tr>
				<th>邮箱</th>
				<td>
					<input name="email" value="${vo.email }" style="width: 155px;" />
				</td>
			</tr>
		</table>
	</form>
</div>