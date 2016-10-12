<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div align="center" style="width: 100%">
	<form id="entityCategory_manage_add_form" method="post" style="width: 100%">
		<table class="tableForm" style="width: 100%;font-size: 13px;">
			<tr>
				<th>行业名称</th>
				<td>
					<input name="text" class="easyui-validatebox" data-options="required:true"  style="width: 170px;"/>
				</td>
			</tr>
			<tr>
				<th>上级行业</th>
				<td>
					<input id="entityCategory_manage_pid" name="pid" class="easyui-combotree" data-options="url:'${appPath}/entityCategoryManage/treegrid.action',parentField : 'pid',lines : true" style="width:175px;" />
				</td>
			</tr>
			<tr>
				<th>行业排序</th>
				<td>
					<input name="sort" class="easyui-numberspinner" data-options="min:1,max:999,editable:false,required:true,missingMessage:'请选择行业排序'" value="1" style="width: 175px;" />
				</td>
			</tr>
		</table>
	</form>
</div>