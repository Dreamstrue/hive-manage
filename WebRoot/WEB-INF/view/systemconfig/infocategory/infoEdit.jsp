<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
.tableForm th{
	font-size: 13px;
}
</style>
<script type="text/javascript">
	$('#parentId').combotree({
		onChange:function(nValue,oValue){
			$('#currentValue').val(nValue);
		}
	});
	
</script>
<div align="center" style="width: 100%">
	<form id="info_manage_edit_form"  name="info_manage_edit_form" method="post" style="width: 100%">
		<input name="id" id="id" type="hidden" value="${vo.id}">
		<input name="defaultValue" id="defaultValue" type="hidden" value="${vo.parentId}">
		<input name="currentValue" id="currentValue" type="hidden" value="${vo.parentId}">
		<table class="tableForm" style="width: 100%">
			<tr>
				<th>类别名称</th>
				<td>
					<input name="text" value="${vo.text}" class="easyui-validatebox" data-options="required:true"  style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<th>父级类别</th>
				<td>
					<input  id="parentId" name="parentId" value="${vo.parentId}" class="easyui-combotree" data-options="url:'${appPath}/infoCate/allInfoCate.action',parentField:'parentId',lines:true" style="width:200px;" />
				</td>
			</tr>
			<tr>
				<th>是否展示</th>
				<td>
					<c:if test="${vo.isShow=='1'}">
						<input  type="radio" class="cdown" name="isShow" value="1" checked="checked"/>是&nbsp;&nbsp;&nbsp;<input  type="radio" class="cdown" name="isShow" value="0" />否
					</c:if>
					<c:if test="${vo.isShow=='0'}">
						<input  type="radio" class="cdown" name="isShow" value="1"/>是&nbsp;&nbsp;&nbsp;<input  type="radio" class="cdown" name="isShow" value="0" checked="checked"/>否
					</c:if>
				</td>
			</tr>
			<!--此处的 客户端显示  表示在客户端APP中是否作为栏目展示 -->
			<tr>
				<th>客户端显示</th>
				<td>
					<c:if test="${vo.clientShow=='1'}">
						<input  type="radio" class="cdown" name="clientShow" value="1" checked="checked"/>是&nbsp;&nbsp;&nbsp;<input  type="radio" class="cdown" name="clientShow" value="0" />否
					</c:if>
					<c:if test="${vo.clientShow=='0'}">
						<input  type="radio" class="cdown" name="clientShow" value="1"/>是&nbsp;&nbsp;&nbsp;<input  type="radio" class="cdown" name="clientShow" value="0" checked="checked"/>否
					</c:if>
					
				</td>
			</tr>
			<tr>
				<th>类别说明</th>
				<td>
					<textarea id="remark" name="remark"  class="easyui-validatebox" data-options="required:false"  style="width: 200px;height: 50px;" >${vo.remark}</textarea>
				</td>
			</tr>
		</table>
	</form>
</div>