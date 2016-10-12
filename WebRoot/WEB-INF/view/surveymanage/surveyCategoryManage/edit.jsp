<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div align="center" style="width: 100%">
	<form id="category_manage_edit_form" method="post" style="width: 100%">
		<input type="hidden" name="id" value="${vo.id }">
		<table class="tableForm" style="width: 100%;font-size: 13px;">
			<tr>
				<th>类别名称</th>
				<td><input name="categoryname" value="${vo.categoryname }" class="easyui-validatebox" data-options="required:true" /></td>
			</tr>
			<!-- 
			<tr>
				<th>类别描述</th>
				<td colspan="3">
					<textarea name="cactiondescription" style="width:240px;height:72px;" class="easyui-validatebox" style="width:600px;" validType="maxLength[500]" invalidMessage="请输入不要超过500个字符"></textarea>
				</td>
			</tr>
			 -->
		</table>
	</form>
</div>