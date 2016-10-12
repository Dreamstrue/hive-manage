<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div align="center" style="width: 100%">
	<form id="category_manage_add_form" method="post" style="width: 100%">
		<table class="tableForm" style="width: 100%;font-size: 13px;">
		     <tr>
				<th>总成名称</th>
				<td><input name="assemblyId" class="easyui-combobox" data-options="url:'${appPath}/defectCar/getAssemblyList.action',valueField:'id',textField:'assemblyName',required:true "   /></td>
			</tr>
			<tr>
				<th>分总成名称</th>
				<td><input name="subAssemblyName"  class="easyui-validatebox" data-options="required:true" /></td>
			</tr>
			<tr>
				<th>排序值</th>
				<td >
					<input name="ordernum"  value="1"  class="easyui-numberspinner" data-options="min:1,max:999,required:true"/>
				</td>
			</tr>
		</table>
	</form>
</div>