<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
function addqrcodeBatch(){

	$('#qrcodeBatch_manage_add_form').form('submit',{
	    url:"${pageContext.request.contextPath}/qrcodeBatch/qrcodeBatchAdd.action",
	    onSubmit: function(){
	    	$.messager.progress({title:'温馨提示',msg:'请稍后，数据生成中...'});
			return $(this).form('validate');
	    },
	    success:function(data){
	    	$.messager.progress('close');
			var r = $.parseJSON(data);
				if (r.status) {
					$("#add_dialog").dialog('destroy');
					$('#qrcode_batch_manage_datagrid').datagrid('reload');
				}
				$.messager.alert("提示",r.msg,"info");
	    }
	});
	
}
</script>

<div align="center" style="width: 100%">
	<form id="qrcodeBatch_manage_add_form" method="post" enctype="multipart/form-data" style="width: 100%">
		<table class="tableForm" style="width: 100%" id="tab">
			<tr>
				<th style="width: 100px;">批次编号</th>
				<td>
				    <input type="text" id="batchNumber" name="batchNumber" value="${batchNum }" class="easyui-validatebox" readonly="readonly" style="width:150px;"/>
					<input id="status" name="status" value="0" type="hidden" />
					<input id="digitNumber" name="digitNumber" value="000001" type="hidden" />
				</td>
			</tr>
			<tr>
				<th style="width: 100px;">生成数量</th>
				<td>
					<input id="amount" name="amount" class="easyui-numberbox" validType="maxValue[100000]" required="true" style="width: 75px;" /> 张
				</td>
			</tr>
			<tr>
				<th style="width: 100px;text-align:left;" colspan="2"><font color="red">&nbsp;提示：
					<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由于生产的数量比较大，页面等待的时间可能有点长，请耐心等待！！！</font>
				</th>
			</tr>
		</table>
	</form>
</div>