<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../common/inc_notice.jsp"></jsp:include>
<link rel="stylesheet" href="${appPath}/resources/css/info.css" type="text/css"></link>
<script type="text/javascript">
		$(function() {
			var chasannex = $('.np').val();
			if(chasannex == '0'){
				$('#checkFile').hide();
			}
		
	});
	

</script>
<div align="left" style="width:70%;">
<div id="div1" >当前位置：行业标准&nbsp;&gt;&nbsp;查看</div>
<c:if test="${backId!='0' }">
	<div id="div2" align="right"><a id="btn" href="${appPath}/industry/allmanage.action?id=${backId}" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">返回列表</a> </div>
</c:if>
<c:if test="${backId=='0' }">
	<div id="div2" align="right"><a id="btn" href="${appPath}/industry/manage.action?id=${vo.nmenuid}" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">返回列表</a> </div>
</c:if>
<div align="left">
	<table class="integrity_table"  style="width: 100%">
		<tr>
			<td>
				<div>
					<form  id="form" method="post" enctype="multipart/form-data">
						<input type="hidden" name="id" id="id" value="${vo.id}">
						<table >
							<tr>
								<td class="tt">标准名称：</td>
								<td><input name="title"  id="title"  value="${vo.title}" style="width: 630px;" disabled="disabled"/></td>
							</tr>
							<tr>
								<td class="tt">概要内容：</td>
								<td>
									<textarea name="content" id="content"  rows="8" cols="60" style="width:630px;height:240px;visibility:hidden;">${vo.content}</textarea>
									<script type="text/javascript">
										KindEditor.ready(function(K) {
											var editor1 = K.create('textarea[name="content"]', {
												cssPath : '${appPath}/resources/js/plugin/kindeditor/plugins/code/prettify.css',
												uploadJson : '${appPath}/resources/js/plugin/kindeditor/jsp/upload_json.jsp',
												fileManagerJson : '${appPath}/resources/js/plugin/kindeditor/jsp/file_manager_json.jsp',
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
							<tr>
								<td class="tt">允许下载：</td>
								<td>
									<c:if test="${vo.cdownload=='1'}">
										<input  type="radio" class="cdown" name="cdownload" value="1" checked="checked"/>是&nbsp;&nbsp;&nbsp;<input  type="radio" class="cdown" name="cdownload" value="0" />否
									</c:if>
									<c:if test="${vo.cdownload=='0'}">
										<input  type="radio" class="cdown" name="cdownload" value="1"/>是&nbsp;&nbsp;&nbsp;<input  type="radio" class="cdown" name="cdownload" value="0" checked="checked"/>否
									</c:if>
								</td>
							</tr>
							<tr>
								<td class="tt">存在附件：</td>
								<td>
									<select class="np" name="chasannex" disabled="disabled">
										<c:if test="${vo.chasannex=='1'}">
											<option value="1" selected="selected">是</option>
											<option value="0">否</option>
										</c:if>
										<c:if test="${vo.chasannex=='0'}">
											<option value="1">是</option>
											<option value="0"  selected="selected">否</option>
										</c:if>
									</select>
								</td>
							</tr>
							<tr id="checkFile">
								<td class="tt">附件：</td>
								<td>
									<span>
										<c:if test="${!empty annex}">
											<c:forEach items="${annex}" var="t" varStatus="status">
											<div id="${t.id}">
											<a href="${appPath}/annex/download.action?id=${t.id}">${t.cfilename}</a>
											</div>
											</c:forEach>
										</c:if>
									</span>
								</td>
							</tr>
							<tr>
								<td class="tt"><font color="red">审核意见：</font></td>
								<td>
									<textarea name="auditOpinion" id="auditOpinion"  style="width:630px;" readonly="readonly" disabled="disabled">${vo.auditOpinion}</textarea>
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
