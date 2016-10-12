<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<style>
.tableForm th{
	font-size: 13px;
}
</style>
<script type="text/javascript">

	
</script>
<div align="center" style="width: 100%">
	<form id="prize_number_add_form" method="post" style="width: 100%">
		<input type="hidden" id="prizeId" name="prizeId" value="${prizeId}">
		<table class="tableForm" style="width: 100%">
			<tr>
				<th>奖品名称</th>
				<td>
					<input name="prizeName" class="easyui-validatebox"  value="${prizeName}" style="width: 200px;"  readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<th>奖品数量</th>
				<td>
					<input  id="prizeNum" name="prizeNum" class="easyui-validatebox" data-options="required:true,validType:'number' " style="width:200px;" />
				</td>
			</tr>
		</table>
	</form>
</div>