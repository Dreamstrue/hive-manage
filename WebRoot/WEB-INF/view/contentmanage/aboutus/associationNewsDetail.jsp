<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<link rel="stylesheet" href="${appPath}/resources/css/info.css" type="text/css"></link>
<script type="text/javascript">
$(function() {
	var chasannex = $('.np').val();
	if(chasannex == '0'){
		$('#checkFile').hide();
	}
	
});
	
	
</script>
<div align="left" style="width:70%; height: 100%;">
<div id="div1" >当前位置：协会动态&nbsp;&gt;&nbsp;明细</div>
<div id="div2" align="right"><a id="btn" href="${pageContext.request.contextPath}/aboutus/manage.action?id=${vo.nmenuid}" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">返回列表</a> </div>
<hr style="color: silver;border-style: solid;"/>
<div align="left" style="width: 100%">
	<table class="integrity_table">
		<tr>
			<td>
				<div>
					<form  id="form" method="post" enctype="multipart/form-data">
					<input type="hidden" name="id" id="id" value="${vo.naboutusid}">
						<table >
							<tr>
								<th>标题：</th>
								<td><input name="ctitle"  id="ctitle"  value="${vo.ctitle}" style="width: 680px;"/></td>
							</tr>
							<tr>
								<th>所属来源：</th>
								<td><input name="cfrom"  id="cfrom" value="${vo.cfrom}"  style="width: 680px;"/></td>
							</tr>
							<tr>
								<th>动态内容：</th>
								<td>
									<textarea name="ccontent" id="ccontent"  rows="8" cols="60" style="width:680px;height:340px;visibility:hidden;">${vo.ccontent}</textarea>
									<script type="text/javascript">
										KindEditor.ready(function(K) {
											var editor1 = K.create('textarea[name="ccontent"]', {
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
								<th>链接地址：</th>
								<td><input name="chref"  id="chref" value="${vo.chref}"  style="width: 680px;"/></td>
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
