<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
	$('#surveyIndustry_manage_pid').combotree({
		onChange:function(nValue,oValue){
			$("#currentValue").val(nValue);
		}
	});
</script>
<div align="center" style="width: 100%">
	<form id="surveyIndustry_manage_edit_form" name="surveyIndustry_manage_edit_form" method="post" style="width: 100%">
		<input type="hidden" name="id" value="${vo.id }">
		<!-- 定义该隐藏变量是为了方便校验用户对表单项是否进行任何修改，保存的值为上级ID 由于document元素对jqueryEasyui中的combotree不识别，因此这样定义 -->
		<input type="hidden" name="defaultValue" id="defaultValue" value="${vo.pid}">
		<input type="hidden" name="currentValue" id="currentValue" value="${vo.pid}">
		
		<table class="tableForm" style="width: 100%;font-size: 13px;">
			<tr>
				<th>行业名称</th>
				<td>
					<input name="text" value="${vo.text }" class="easyui-validatebox" data-options="required:true"  style="width: 170px;"/>
				</td>
			</tr>
			<tr>
				<th>行业类别</th>
				<td>
					<input name="objectType" value="${vo.objectType }" class="easyui-validatebox" data-options="required:true"  style="width: 170px;"/>
				</td>
			</tr>
			<tr>
				<th>上级行业</th>
				<td>
					<input id="surveyIndustry_manage_pid" name="pid" value="${vo.pid }" class="easyui-combotree" data-options="url:'${appPath}/surveyIndustryManage/treegrid.action',parentField : 'pid',lines : true" style="width:175px;" />
				</td>
			</tr>
			<tr>
				<th>行业排序</th>
				<td>
					<input name="sort" id="surveyIndustry_manage_sort" class="easyui-numberspinner" data-options="min:1,max:999,editable:false,required:true,missingMessage:'请选择行业排序'" value="${vo.sort }" style="width: 175px;" />
				</td>
			</tr>
		</table>
	</form>
</div>