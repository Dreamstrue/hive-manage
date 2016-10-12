<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
function addqrcodeIssue(){
	$('#issue_manage_add_form').form('submit',{
	    url:"${pageContext.request.contextPath}/qrcodeIssue/qrcodeIssueAdd.action",
	    onSubmit: function(){
	    	var en = $("#entityName").val();
	    	if(en==''){
	    		$.messager.alert("提示","请选择一个实体名称","error");
	    		return false;
	    	}
			return $(this).form('validate');
	    },
	    success:function(data){
			var r = $.parseJSON(data);
				if (r.status) {
					$("#add_dialog").dialog('destroy');
					$('#qrcode_issue_manage_datagrid').datagrid('reload');
				}
				$.messager.alert("提示",r.msg,"info");
	    }
	});
}
function autoSetValues(id){
// alert(id);
	$.ajax({ 
		type: 'POST', 
		url: '${pageContext.request.contextPath}/industryEntityManage/getIndustryEntityById.action?id='+id, 
		success: function(robj){ 
			var r = $.parseJSON(robj);
				$("#linkPerson").val(r.linkMan);
				$("#linkPhone").val(r.linkPhone);
				$("#linkAddress").text(r.address);
		},
		error: function(robj) {
			$.messager.show({
				title : '提示',
				msg : '数据获取失败！'
			});
        }

	});
}
</script>
<div align="center" style="width: 100%">
	<form id="issue_manage_add_form"  method="post" enctype="multipart/form-data">
	<input type="hidden" id="status" name="status" value="1"/>
		<table class="tableForm" style="width: 100%">
			<tr>
				<th style="width: 120px;">实体名称：</th>
				<td>
				<input id="cc1" name="entityId" class="easyui-combobox" data-options="editable:true,
							mode:'remote',
				    		valueField: 'id',
						    textField: 'entityName',
						    url: '${pageContext.request.contextPath}/industryEntityManage/getAllIndustryEntityList.action',
						    onSelect: function(rec){
						     	autoSetValues(rec.id);
						     	$('#entityName').val(rec.entityName);
						    }"  required="true">
				    <input type="hidden" id="entityName" name="entityName"/>
				</td>
			</tr>
			<tr>
				<th style="width: 120px;">联系人：</th>
				<td>
					<input type="text" id="linkPerson" name="linkPerson" class="easyui-validatebox" required="true" missingMessage="联系人必须填写"/>
                </td>
			</tr>
			<tr>
				<th style="width: 120px;">联系电话：</th>
				<td>
					<input type="text" id="linkPhone" name="linkPhone" class="easyui-validatebox" required="true" missingMessage="联系电话必须填写"/>
                </td>
			</tr>
			<tr>
				<th style="width: 120px;">联系地址：</th>
				<td><textarea rows="4" cols="30" id="linkAddress" name="linkAddress"  class="easyui-validatebox" required="true" missingMessage="联系地址必须填写"></textarea> </td>
			</tr>
			<tr>
				<th style="width: 120px;">二维码类别：</th>
				<td>
		            <select class="easyui-combobox" name="qrcodeType" data-options="prompt:'二维码类别',editable:false,panelHeight:'auto'" style="width:90px;" required="true">
						<option value=1>url</option>
<!-- 						<option value=2>文本</option> -->
<!-- 						<option value=3>图片</option> -->
					</select>
<!-- 					<input type="text" id="qrcodeType" name="qrcodeType" class="easyui-validatebox" required="true"/> -->
                </td>
			</tr>
			<tr>
				<th style="width: 120px;">二维码内容：</th>
				<td><textarea rows="4" cols="30" id="qrcodeContent" name="qrcodeContent"  class="easyui-validatebox" required="true" missingMessage="内容必须填写"></textarea> </td>
			</tr>
		</table>
	</form>
</div>