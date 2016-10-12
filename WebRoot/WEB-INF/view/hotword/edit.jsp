<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
	
</script>
<div align="center" style="width: 100%">
	<form id="hotword_manage_edit_form" method="post" style="width: 100%">
		<input type="hidden" name="id" value="${vo.id }">
		<table class="tableForm" style="width: 100%">
			<tr>
				<th>关键词名称</th>
				<td>
					<input name="name" value="${vo.name }" class="easyui-validatebox" data-options="required:true"  style="width: 170px;"/>
				</td>
			</tr>
			<tr>
				<th>链接地址</th>
				<td>
					<input name="href" value="${vo.href }" style="width: 170px;"/>
				</td>
			</tr>
			<tr>
				<th>有效期起</th>
				<td>
					<input class="inval" type='text' name='beginTime' value="${vo.beginTime }" id='createTime' style='width:168px'  class='intxt' readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  />
				</td>
			</tr>
			<tr>
				<th>有效期止</th>
				<td>
					<input class="inval" type='text' name='endTime' value="${vo.endTime }" id='createTime' style='width:168px'  class='intxt' readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  />
				</td>
			</tr>
			<tr>
				<th>排序值</th>
				<td>
					<input name="sortOrder" value="${vo.sortOrder }" class="easyui-numberspinner" data-options="min:1,max:999,editable:false,required:true,missingMessage:'请选择菜单排序'" value="1" style="width: 175px;" />
				</td>
			</tr>
		</table>
	</form>
</div>