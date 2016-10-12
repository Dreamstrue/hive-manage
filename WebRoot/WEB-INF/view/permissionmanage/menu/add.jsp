<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
	
	$(function() {
		$('#menu_manage_icon_combobox').combobox({
			data : iconData,
			formatter : function(v) {
				return formatString('<span class="{0}" style="display:inline-block;vertical-align:middle;width:16px;height:16px;"></span>{1}', v.value, v.value);
			}
		});
	});
	
</script>
<div align="center" style="width: 100%">
	<form id="menu_manage_add_form" method="post" style="width: 100%">
		<table class="tableForm" style="width: 100%">
			<tr>
				<th>菜单名称</th>
				<td>
					<input name="text" class="easyui-validatebox" data-options="required:true"  style="width: 170px;"/>
				</td>
			</tr>
			<tr>
				<th>菜单地址</th>
				<td>
					<input name="url"  style="width: 170px;"/>
				</td>
			</tr>
			<tr>
				<th>菜单排序</th>
				<td>
					<input name="seq" class="easyui-numberspinner" data-options="min:1,max:999,editable:false,required:true,missingMessage:'请选择菜单排序'" value="1" style="width: 175px;" />
				</td>
			</tr>
			<tr>
				<th>菜单图标</th>
				<td>
					<input id="menu_manage_icon_combobox" name="iconCls" style="width:175px;" />
				</td>
			</tr>
			<tr>
				<th>上级菜单</th>
				<td>
					<input id="menu_manage_pid" name="pid" class="easyui-combotree" data-options="url:'${appPath}/menu/treegrid.action',parentField : 'pid',lines : true" style="width:175px;" />
				</td>
			</tr>
		</table>
	</form>
</div>