<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/integrity.css" type="text/css"></link>
<script type="text/javascript">
		$(function() {
			
		});
	
	function check(){
		if(checkSpecialValue()){
			$('#form').form('submit',{
				url:'${pageContext.request.contextPath}/special/insert.action',
				success:function(result){
					var r = $.parseJSON(result);
					$.messager.show({
						title:'提示',
						msg:r.msg
					});
					if(r.status){
						
					setTimeout(function(){
						location.href='${pageContext.request.contextPath}/special/manage.action';
					},1000);
					}
				}
			});
		}
	}
	
	function reload(){
		location.href='${pageContext.request.contextPath}/special/add.action';
	}
	
	function checkSpecialValue(){
		var title =  $('#text').val();
		if(!checkStr(title)){
			showMessage('专题名称不能为空');
			return false;
		}
		return true;
	}
</script>
<div align="center" style="width: 100%">
	<table  class="integrity_table">
		<tr>
			<td>
				<div>
					<form  id="special_info_form" method="post" enctype="multipart/form-data">
					<input id="pid" name="pid" type="hidden" value="0">
						<table >
							<tr>
								<th>专题名称：</th>
								<td>
									<input name="text"  id="text" class="easyui-validatebox" data-options="required:true" style="width: 220px;"/>&#160;<font color="#FF0000" size="2">*必填项</font>
								</td>
							</tr>
							<tr>
								<th>专题图片：</th>
								<td><input  type="file" name="imgfile"  id="picture"  style="width: 220px;"/>&#160;<font color="#FF0000" size="2">*必填项</font></td>
							</tr>
							<tr>
								<th>排序：</th>
								<td>
									<input name="isortorder" class="easyui-numberspinner" data-options="min:1,max:999,editable:false,required:true" value="1" style="width: 220px;" />
								</td>
							</tr>
						</table>
					</form>
				</div>
			</td>
		</tr>
	</table>
</div>
