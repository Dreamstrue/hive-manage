<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
function addqrcodeIssueByManual(){
	$('#manual_manage_add_form').form('submit',{
	    url:"${pageContext.request.contextPath}/qrcodeIssue/qrcodeIssueAdd_manual.action",
	    onSubmit: function(){
			return $(this).form('validate');
	    },
	    success:function(data){
			var r = $.parseJSON(data);
				if (r.status) {
					$("#manual_dialog").dialog('destroy');
					$("#qrcode_issue_detail_manage_datagrid").datagrid("load");
// 					$('#qrcode_issue_manage_datagrid').datagrid('reload');
				}
				$.messager.alert("提示",r.msg,"info");
	    }
	});
}
$(function(){
	$.extend($.fn.validatebox.defaults.rules, {
		validqrcodeNum:{
			validator: function (value) {
				return isExistqrcode(value);
			},
			message: '请输入有效的二维码编号！'
		}
	});
})
function isExistqrcode(param){
	var b =true;
	$.ajax({
				type: 'POST', 
				async: false,
				url: '${pageContext.request.contextPath}/qrcode/checkQrcode.action?qrcodeNumber='+param,
				success: function(robj){ 
					var r = $.parseJSON(robj);
// 					alert(r.status);
					b = r.status;
					if(b){
						$("#qrcodeBatchId").val(r.data.qrcodeBatchVo.batchNumber);
						$("#qrcodeType").combobox("setValue",2);
// 						$("#content").val(r.data.content);
						$("#qrcodeStatus").val(r.data.qrcodeStatus);
						$("#qrcodeId").val(r.data.id);
					}
// 					else{
// 						$.messager.alert("提示",r.msg,"info");
// 					}
				}
			});
	return b;
}
</script>
<div align="center" style="width: 100%">
	<form id="manual_manage_add_form"  method="post" enctype="multipart/form-data">
	<input type="hidden" id="qrcodeId" name="id"/>
	<input type="hidden" id="issueId" name="issueId" value="${issueId }"/>
		<table class="tableForm" style="width: 100%">
			<tr>
				<th style="width: 120px;">二维码编号：</th>
				<td>
					<input type="text" id="qrcodeNumber" name="qrcodeNumber" class="easyui-validatebox" validType="validqrcodeNum" required="required"/>
                </td>
			</tr>
			<tr>
				<th style="width: 120px;">批次编号：</th>
				<td>
					<input type="text" id="qrcodeBatchId" name="qrcodeBatchId" class="easyui-validatebox" readonly="readonly"/>
                </td>
			</tr>
			<tr>
				<th style="width: 120px;">二维码类别：</th>
				<td>
					<select class="easyui-combobox" name="qrcodeType" data-options="prompt:'二维码类型',editable:false,panelHeight:'auto'" style="width:90px;" required="true">
						<option value=1>url</option>
						<option value=2>文本</option>
<!-- 						<option value=3>图片</option> -->
					</select>
<!-- 					<input type="text" id="qrcodeType" name="qrcodeType" class="easyui-validatebox" required="true"/> -->
                </td>
			</tr>
			<tr>
				<th style="width: 120px;">二维码状态：</th>
				<td>
					<input type="text" id="qrcodeStatus" name="qrcodeStatus" class="easyui-validatebox" readonly="readonly"/>
                </td>
			</tr>
			<tr>
				<th style="width: 120px;">二维码内容：</th>
				<td><textarea rows="4" cols="30" id="content" name="content"  class="easyui-validatebox" required="required"></textarea> </td>
			</tr>
			<!-- <tr>
				<th style="width: 120px;">创建人：</th>
				<td>
					<input type="text" id="creater" name="creater" class="easyui-validatebox" required="true" />
                </td>
			</tr>
			<tr>
				<th style="width: 120px;">创建时间：</th>
				<td>
					<input type="text" id="createTime" name="createTime" class="easyui-validatebox" required="true" />
                </td>
			</tr> -->
		</table>
	</form>
</div>