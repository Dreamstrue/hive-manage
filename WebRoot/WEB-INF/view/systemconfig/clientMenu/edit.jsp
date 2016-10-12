<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
	$('#sort').numberspinner({
		onSpinUp:function(){
			var value = $('#sort').numberspinner('getValue');
			$("#currentValue").val(value);
		},
		onSpinDown:function(){
			var value = $('#sort').numberspinner('getValue');
			$("#currentValue").val(value);
		}
	});
</script>
<div align="center" style="width: 100%">
	<form id="clientMenu_manage_edit_form" name="clientMenu_manage_edit_form" method="post" style="width: 100%">
		<input type="hidden" name="id" value="${vo.id}">
		<input type="hidden" name="valid" value="${vo.valid}">
		<input type="hidden" name="menuFlag" value="${vo.menuFlag}">
		<!-- 定义该隐藏变量是为了方便校验用户对表单项是否进行任何修改，保存的值为上级ID 由于document元素对jqueryEasyui中的combotree不识别，因此这样定义 -->
		<input type="hidden" name="defaultValue" id="defaultValue" value="${vo.sort}">
		<input type="hidden" name="currentValue" id="currentValue" value="${vo.sort}">
		<table class="tableForm" style="width: 100%">
			<tr>
				<th>菜单名称</th>
				<td><input name="menuName" class="easyui-validatebox" data-options="required:true" value="${vo.menuName}" /></td>
			</tr>
			<tr>
				<th>菜单排序</th>
				<td>
					<input name="sort" id="sort" class="easyui-numberspinner" data-options="min:1,max:999,editable:true,required:true,missingMessage:'请选择菜单排序'" value="${vo.sort}" style="width: 150px;" />
				</td>
			</tr>
			<tr>
				<th>版本类型</th>
				<td>
					<c:if test="${vo.versionType=='1'}">
						<input  type="radio" class="cdown" name="versionType" value="1" checked="checked"/>企业版&nbsp;&nbsp;<input  type="radio" class="cdown" name="versionType" value="2"/>大众版
					</c:if>
					<c:if test="${vo.versionType=='2'}">
						<input  type="radio" class="cdown" name="versionType" value="1"/>企业版&nbsp;&nbsp;<input  type="radio" class="cdown" name="versionType" value="2" checked="checked"/>大众版
					</c:if>
				</td>	
			</tr>
		</table>
	</form>
</div>