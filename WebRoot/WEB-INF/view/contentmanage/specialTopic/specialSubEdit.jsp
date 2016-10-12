<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/integrity.css" type="text/css"></link>
<script type="text/javascript">
		$(function() {
			//修改时，新闻图片的选择框隐藏，当对应的图片被删除后，在出现。
			var img = '${img}';
			if(img!=''){
				$('#picImg1').hide();
				$('#imageExist1').val('yes');
			}else $('#imageExist1').val('no');
			
		});
		
   	function delAnnex(id,type){
		$.messager.confirm('提示','您确定要删除该附件?',function(b){
			if(b){
				$('#'+id).hide();
				if(type=='image'){
					$('#picImg1').show();
					$('#imageExist1').val('no');
				}
				/* var url = '${appPath}/annex/delete.action';
							$.post(url,{id:id},function(data){
							},"json"); */
			}
		});
	}

</script>
<div align="center" style="width: 100%">
	<table class="integrity_table">
		<tr>
			<td>
				<div>
					<form  id="special_info_manage_edit_form" method="post" enctype="multipart/form-data">
					<input id="id" name="id" type="hidden" value="${vo.id }">
						<table >
							<tr>
								<th>栏目名称：</th>
								<td>
									<input name="text"  id="text" value="${vo.text}" style="width: 250px;"/>&#160;<font color="#FF0000" size="2">*必填项</font>
								</td>
							</tr>
							<tr>
								<th>栏目图片：</th>
								<td>
									<div id="picImg1">
										<input  type="file" name="imgfile"  id="picture"  style="width: 250px;"/>
									</div>
									<c:if test="${img!=null}">
										<div id="${img.id}">
											<a href="${appPath}/annex/download.action?id=${img.id}">${img.cfilename}</a>&nbsp;&nbsp;<input type="button" value="删除" onclick="delAnnex(${img.id},'image')">
											<!-- 定义一个字段保存图片的ID,当新上传图片时，通过这个ID把原来的图片设置为不可用 -->
											<input type="hidden" name="virtualPid" id="virtualPid" value="${img.id}"/>
										</div>
									</c:if>
									
									<!-- 自定义一个字段，方便后台操作新闻图片修改  -->
									<input type="hidden" name="imageExist" id="imageExist1"/>
									
								</td>
							</tr>
							<tr>
								<th>排序：</th>
								<td>
									<input name="isortorder" class="easyui-numberspinner" data-options="min:1,max:999,editable:false,required:true" value="${vo.isortorder}" style="width: 250px;" />
								</td>
							</tr>
						</table>
					</form>
				</div>
			</td>
		</tr>
	</table>
</div>

