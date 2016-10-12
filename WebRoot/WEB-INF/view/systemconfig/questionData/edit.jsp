<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<script type="text/javascript">
	$(function() {
		var ids = '';
		$('#industryId').combotree({
			onCheck:function(node,checked){
				var t = $('#industryId').combotree('tree');// 得到树对象	
				var nodes = t.tree('getChecked');
				if(nodes.length>1){ //说明有一个节点被选中了
					var node1 = t.tree('find', ids);
					t.tree('uncheck',node1.target);
				}
				ids = node.id;
			}
		});
	});
</script>
<div align="center" style="width: 100%">
	<form id="questionData_manage_edit_form" method="post" style="width: 100%">
		<input type="hidden" name="id" value="${vo.id}"/>
		<input type="hidden" name="valid" value="${vo.valid}"/>
		<table class="tableForm" style="width: 100%">
			<tr>
				<th>名称</th>
				<td>
					<input name="text" class="easyui-validatebox" value="${vo.text}" data-options="required:true"  style="width: 170px;"/>
				</td> 
			</tr>
			<tr>
				<th>上级菜单</th>
				<td>
					<input id="parentId" name="parentId" value="${vo.parentId}" class="easyui-combotree" data-options="url:'${appPath}/questionData/allquestionData.action',parentField : 'parentId',lines : true,required:false,cascadeCheck:false" style="width:170px;" />
				</td>
			</tr>
			<tr>
				<th>排序</th>
				<td>
					<input name="sort" value="${vo.sort}" class="easyui-numberspinner" data-options="min:1,max:999,editable:false,required:true" value="1" style="width: 170px;" />
				</td>
			</tr>
			<tr>
				<th>问题类别</th>
				<td>
					<input id="industryId" name="industryId" value="${vo.industryId}" class="easyui-combotree" data-options="url:'${appPath}/questionData/questionCateList.action',parentField : 'parentId',lines:true,cascadeCheck:false,checkbox:true,multiple:true,onlyLeafCheck:true,required:true" style="width:170px;" />
				</td>
			</tr>
		</table>
	</form>
</div>
