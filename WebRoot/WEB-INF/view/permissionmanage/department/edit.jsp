<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
	
	$(function() {
		$('#department_manage_icon_combobox').combobox({
			data : iconData,
			formatter : function(v) {
				return formatString('<span class="{0}" style="display:inline-block;vertical-align:middle;width:16px;height:16px;"></span>{1}', v.value, v.value);
			}
		});
	});
	
</script>
<div align="center" style="width: 100%">
	<form id="department_manage_edit_form" method="post" style="width: 100%">
		<input type="hidden" name="id" value="${vo.id }">
		<table class="tableForm" style="width: 100%">
			<tr>
				<th>部门名称</th>
				<td>
					<input name="text" value="${vo.text }" class="easyui-validatebox" data-options="required:true"  style="width: 170px;"/>
				</td>
			</tr>
			<tr>
				<th>部门描述</th>
				<td colspan="3">
					<textarea name="cdepartmentdescription" style="width:240px;height:72px;" class="easyui-validatebox" style="width:600px;" validType="maxLength[500]" invalidMessage="请输入不要超过500个字符">${vo.cdepartmentdescription }</textarea>
				</td>
			</tr>
			<tr>
				<th>上级部门</th>
				<td>
					<input id="department_manage_pid" name="pid" value="${vo.pid }" class="easyui-combotree" data-options="url:'${appPath}/department/treegrid.action',parentField : 'pid',lines : true" style="width:175px;" />
				</td>
			</tr>
		</table>
	</form>
</div>