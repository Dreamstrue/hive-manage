<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div align="center" style="width: 100%">
	<form id="clientMenu_manage_add_form" method="post" style="width: 100%">
		<table class="tableForm" style="width: 100%">
			<tr>
				<th>菜单名称</th>
				<td><input name="menuName" class="easyui-validatebox" data-options="required:true" /></td>
			</tr>
			<tr>
				<th>菜单排序</th>
				<td>
					<input name="sort" class="easyui-numberspinner" data-options="min:1,max:999,editable:true,required:true,missingMessage:'请选择菜单排序'" value="1" style="width: 150px;" />
				</td>
			</tr>
			<tr>
				<th>版本类型</th>
				<td><input  type="radio" class="cdown" name="versionType" value="1" checked="checked"/>企业版&nbsp;&nbsp;<input  type="radio" class="cdown" name="versionType" value="2"/>大众版</td>
			</tr>
		</table>
	</form>
</div>