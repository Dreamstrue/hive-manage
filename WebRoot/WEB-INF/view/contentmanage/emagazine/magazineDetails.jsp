<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/integrity.css" type="text/css"></link>
<script type="text/javascript">
	
	
</script>
<div align="left" style="width:70%; height: 100%;">
<div id="div1" >当前位置：电子杂志&nbsp;&gt;&nbsp;查看</div>
<div id="div2" align="right"><a id="btn" href="${pageContext.request.contextPath}/magazine/manage.action?id=${menuId}" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">返回列表</a> </div>
<hr style="color: silver;border-style: solid;"/>
<div align="left" style="width: 100%">
	<table class="integrity_table">
		<tr>
			<td>
				<div>
					<form  id="form" method="post" enctype="multipart/form-data">
					<input type="hidden" name="nemagazineId" id="nemagazineId" value="${vo.nemagazineId}">
						<table >
							<tr>
								<th>杂志标题：</th>
								<td><input name="title"  id="title" value="${vo.title}" style="width: 680px;" disabled="disabled"/></td>
							</tr>
							<tr>
								<th>封面图片：</th>
								<td>
									<div id="${img.id}">
											<a href="${appPath}/annex/download.action?id=${img.id}">${img.cfilename}</a>
									</div>
								</td>
							</tr>
							<tr>
								<th>概要内容：</th>
								<td>
									<textarea name="content" id="content"  rows="8" cols="60" style="width:680px;height:240px;visibility:hidden;">${vo.content }</textarea>
									<script type="text/javascript">
										KindEditor.ready(function(K) {
											var editor1 = K.create('textarea[name="content"]', {
												cssPath : '${pageContext.request.contextPath}/resources/js/kindeditor/plugins/code/prettify.css',
												uploadJson : '${pageContext.request.contextPath}/resources/js/kindeditor/jsp/upload_json.jsp',
												fileManagerJson : '${pageContext.request.contextPath}/resources/js/kindeditor/jsp/file_manager_json.jsp',
												allowFileManager : true,
												afterCreate : function() { 
      											   this.sync(); 
										        }, 
										        afterBlur:function(){ 
										            this.sync(); 
										        }      
											});
											editor1.readonly(true);
										});
									</script>
								</td>
							</tr>
							<tr id="">
								<th>本地杂志：</th>
								<td>
									<div id="${exe.id}">
											<a href="${appPath}/annex/download.action?id=${exe.id}">${exe.cfilename}</a>
									</div>
								</td>
							</tr>
							<tr id="">
								<th>在线杂志：</th>
								<td>
									<div id="${zip.id}">
											<a href="${appPath}/annex/download.action?id=${zip.id}">${zip.cfilename}</a>
									</div>
								</td>
							</tr>
							<tr>
								<th>审核结果：</th>
								<td>
									<select class="re" name="cauditstatus" disabled="disabled">
										<option value="1" selected="selected">通过</option>
										<option value="2" >不通过</option>
									</select>
								</td>
							</tr>
							<tr>
								<th>审核意见：</th>
								<td>
									<textarea name="cauditopinion" id="cauditopinion"  style="width:680px;" disabled="disabled">${vo.cauditopinion}</textarea>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</td>
		</tr>
	</table>
</div>
</div>
