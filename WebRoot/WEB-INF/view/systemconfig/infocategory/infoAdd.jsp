<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<style>
.tableForm th{
	font-size: 13px;
}
</style>
<script type="text/javascript">

	
</script>
<div align="center" style="width: 100%">
	<form id="info_manage_add_form" method="post" style="width: 100%">
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
					<input  id="parentId" name="parentId" class="easyui-combotree" data-options="url:'${appPath}/infoCate/allInfoCate.action',parentField:'parentId',lines:true" style="width:200px;" />
				</td>
			</tr>
			<!--此处的 是否展示  表示在通用内容管理中的类别选择时使用    选择是，则展示在下拉列表中，否则没有 -->
			<tr>
				<th>是否展示</th>
				<td>
					<input  type="radio" class="cdown" name="isShow" value="1" checked="checked"/>是&nbsp;&nbsp;&nbsp;<input  type="radio" class="cdown" name="isShow" value="0" />否
				</td>
			</tr>
			<!--此处的 客户端显示  表示在客户端APP中是否作为栏目展示 -->
			<tr>
				<th>客户端显示</th>
				<td>
					<input  type="radio" class="cdown" name="clientShow" value="1" checked="checked"/>是&nbsp;&nbsp;&nbsp;<input  type="radio" class="cdown" name="clientShow" value="0" />否
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