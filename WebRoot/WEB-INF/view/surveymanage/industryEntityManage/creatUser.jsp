<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.form.js"></script>
<div align="center" style="width: 100%">
	<form id="manage_create_form" method="post" style="width: 100%">
		<table class="tableForm" style="width: 100%" id="tab">
			<input type="hidden" id="id" name="id" value="${vo.id }">
			<tr>
				<th>用户名</th>
				<td>
				<input name="username" id="username" type="text" class="easyui-validatebox" required="true" value=""/><label class="red">*</label>
				</td>
			</tr>
			<tr>
				<th>密码</th>
				<td>
				<input name="password" id="password" type="text" class="easyui-validatebox" required="true" value="123456"/><label class="red">*</label>
				</td>
			</tr>
			<tr>
				<th style="width: 100px;text-align:left;" colspan="2"><font color="#00CED1">提示：
					<br>&nbsp;&nbsp;创建用户需要填写登陆用户名和密码
				</th>
			</tr>
		</table>
	</form>
</div>