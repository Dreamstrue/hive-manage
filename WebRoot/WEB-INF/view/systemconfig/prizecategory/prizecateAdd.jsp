<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<style>
.tableForm th{
	font-size: 13px;
}
</style>
<script type="text/javascript">

	
</script>
<div align="center" style="width: 100%">
	<form id="pcate_manage_add_form" method="post" style="width: 100%">
		<table class="tableForm" style="width: 100%">
			<tr>
				<th>类别名称</th>
				<td>
					<input name="text" class="easyui-validatebox" data-options="required:true"  style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<th>父级类别</th>
				<td>
					<input  id="parentId" name="parentId" class="easyui-combotree" data-options="url:'${appPath}/prizeCate/allPrizeCate.action',parentField:'parentId',lines:true" style="width:200px;" />
				</td>
			</tr>
			<tr>
				<th>类别说明</th>
				<td>
					<textarea id="remark" name="remark"  class="easyui-validatebox" data-options="required:false"  style="width: 200px;height: 50px;" ></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>