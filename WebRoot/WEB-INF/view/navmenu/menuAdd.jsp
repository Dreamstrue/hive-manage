<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<script type="text/javascript">
		$(function() {
			
	});
</script>
<div align="center" style="width: 100%">
	<form id="navmenu_manage_add_form" method="post" style="width: 100%">
		<table class="tableForm" style="width: 100%">
			<tr>
				<th>栏目名称</th>
				<td>
					<input name="text" class="easyui-validatebox" data-options="required:true,validType:'CHECK_D'"  style="width: 170px;"/>
				</td> 
			</tr>
			<tr>
				<th>栏目路径</th>
				<td>
					<input name="menuHref" class="easyui-validatebox" data-options="required:true"  style="width: 170px;"/>
				</td>
			</tr>
			<tr> 
				<th>菜单路径</th>
				<td>
					<input name="url" class="easyui-validatebox" data-options="required:false"  style="width: 170px;"/>
				</td> 
			</tr>
			<tr>
				<th>上级菜单</th>
				<td>
					<input id="pid" name="pid" class="easyui-combotree" data-options="url:'${appPath}/navMenu/myNavMenu.action',parentField : 'pid',lines : true,required:false,cascadeCheck:false" style="width:170px;" />
				</td>
			</tr>
			<tr>
				<th>排序</th>
				<td>
					<input name="sortOrder" class="easyui-numberspinner" data-options="min:1,max:999,editable:false,required:true" value="1" style="width: 170px;" />
				</td>
			</tr>
			<tr> 
				<th>对应实体</th>
				<td>
					<input name="conentity" class="easyui-validatebox" data-options="required:false"  style="width: 170px;"/>
				</td> 
			</tr>
		</table>
	</form>
</div>
