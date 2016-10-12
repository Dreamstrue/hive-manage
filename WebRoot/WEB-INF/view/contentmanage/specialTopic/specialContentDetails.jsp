<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/integrity.css" type="text/css"></link>
<script type="text/javascript">
		$(function() {
			var chasannex = $('.np').val();
			if(chasannex == '0'){
				$('#checkFile').hide();
			}
		
			
			
	});

</script>
<div align="left" style="width:70%; height: 100%;">
<div id="div1" >当前位置：专题内容&nbsp;&gt;&nbsp;查看</div>
<div id="div2" align="right"><a id="btn" href="${pageContext.request.contextPath}/specialContent/manage.action" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">返回列表</a> </div>
<hr style="color: silver;border-style: solid;"/>
<div align="left" style="width: 100%">
	<table class="integrity_table">
		<tr>
			<td>
				<div>
					<form  id="form" method="post" enctype="multipart/form-data">
						<input type="hidden" id="id" name="id" value="${vo.id}">
						<table >
							<tr>
								<th>所属专题：</th>
								<td>
									<input id="pid" name="pid" value="${vo.pid}" class="easyui-combotree" data-options="url:'${appPath}/special/treegrid.action',parentField : 'pid',lines : true" style="width:200px;"  disabled="disabled"/>
								</td> 
							</tr>
							<tr>
								<th>标题：</th>
								<td><input name="title"  id="title" value="${vo.title}" style="width: 680px;" disabled="disabled"/></td>
							</tr>
							<tr>
								<th>专题内容：</th>
								<td>
									<textarea name="content" id="content"  rows="8" cols="60" style="width:680px;height:240px;visibility:hidden;">${vo.content}</textarea>
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
							<tr>
								<th>链接地址：</th>
								<td>
									<input name="chref"  value="${vo.chref}" style="width: 680px;" disabled="disabled"/>
								</td>
							</tr>
							<tr>
								<th>存在附件：</th>
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
								<th>选择附件：</th>
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
									<span id="upload"></span>
								</td>
							</tr>
							<tr>
								<th><font color="red">审核意见：</font></th>
								<td>
									<textarea name="cauditopinion" id="auditOpinion"  style="width:680px;" readonly="readonly" disabled="disabled">${vo.cauditopinion}</textarea>
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
