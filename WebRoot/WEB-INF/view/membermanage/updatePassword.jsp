<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.form.js"></script>
<div align="center" style="width: 100%">
	<form id="manage_create_form" method="post" style="width: 100%">
		<table class="tableForm" style="width: 100%" id="tab">
			<input type="hidden" id="id" name="id" value="${vo.nmemberid }">
			<tr>
				<th>用户名</th>
				<td>
				<input name="username" id="username" type="text" class="easyui-validatebox"  readonly="readonly"  value="${vo.cusername }" style="width: 155px;" />
				</td>
			</tr>
				<tr>
				<th>密码</th>
				<td>
					<input id="password" name="password" type="password" class="easyui-validatebox" data-options="required:true" style="width: 155px;" />
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