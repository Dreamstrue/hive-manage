<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div align="center" style="width: 100%">
	<form id="category_manage_edit_form" method="post" style="width: 100%">
		<input type="hidden" name="id" value="${vo.id }">
		<table class="tableForm" style="width: 100%;font-size: 13px;">
			<tr>
				<th>品牌名称</th>
				<td><input name="assemblyId" class="easyui-combobox" value="${vo.assemblyId}" data-options="url:'${appPath}/defectCar/getAssemblyList.action',valueField:'id',textField:'assemblyName',required:true " /></td>
			</tr>
			<tr>
				<th>系列名称</th>
				<td><input name="subAssemblyName" value="${vo.subAssemblyName }" class="easyui-validatebox" data-options="required:true" /></td>
			</tr>
			<tr>
				<th>排序值</th>
				<td >
					<input name="ordernum"  value="${vo.ordernum }"  class="easyui-numberspinner" data-options="min:0,max:999,editable:false,required:true"/>
				</td>
			</tr>
		</table>
	</form>
</div>