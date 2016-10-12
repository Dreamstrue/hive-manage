<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/integrity.css" type="text/css"></link>
<script type="text/javascript">
		$(function() {
			var chasannex = $('.np').val();
			if(chasannex == '0'){
				$('#checkFile').hide();
			}
			
			
			var ctop = $('.top').val();
			if(ctop == '0'){
				$('#topend').hide();
			}
	});
		

</script>
<div align="left" style="width:70%; height: 100%;">
<div id="div1" >当前位置：通知公告&nbsp;&gt;&nbsp;查看</div>
<div id="div2" align="right"><a id="btn" href="${pageContext.request.contextPath}/notice/manage.action" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">返回列表</a> </div>
<hr style="color: silver;border-style: solid;"/>
<div align="left" style="width: 100%">
	<table class="integrity_table">
		<tr>
			<td>
				<div>
					<form  id="form" method="post" enctype="multipart/form-data">
						<input type="hidden" name="id" id="id" value="${vo.id }">
						<table >
							<tr>
								<th>公告主题：</th>
								<td><input name="title"  id="title" value="${vo.title }" style="width: 680px;" disabled="disabled" />
							</tr>
							<tr>
								<th>公告概要：</th>
								<td><input name="content"  id="content" value="${vo.content }" style="width: 680px;" disabled="disabled" /></td>
							</tr>
							<tr>
								<th>公告类别：</th>
								<td>
									<select class="cate" name="ccategory" disabled="disabled">
										<c:if test="${vo.ccategory=='1'}">
											<option value="1" selected="selected">列表展示</option>
											<option value="0">否</option>
										</c:if>
										<c:if test="${vo.ccategory=='0'}">
											<option value="1">是</option>
											<option value="0"  selected="selected">浮动窗口</option>
										</c:if>
									</select>
								</td>
							</tr>
							<tr>
								<th>重要标志：</th>
								<td>
									<c:if test="${vo.cimportant=='1'}">
									<input type="radio" class="sign" name="cimportant" value="1"  checked="checked">是&nbsp;&nbsp;<input type="radio" class="sign" name="cimportant" value="0">否
									</c:if>
									<c:if test="${vo.cimportant=='0'}">
									<input type="radio" class="sign" name="cimportant" value="1">是&nbsp;&nbsp;<input type="radio" class="sign" name="cimportant" value="0" checked="checked">否
									</c:if>
								</td>
							</tr>
							<tr>
								<th>是否置顶：</th>
								<td>
									<select class="top" name="ctop" disabled="disabled">
										<c:if test="${vo.ctop=='1'}">
											<option value="1" selected="selected">是</option>
											<option value="0">否</option>
										</c:if>
										<c:if test="${vo.ctop=='0'}">
											<option value="1">是</option>
											<option value="0"  selected="selected">否</option>
										</c:if>
									</select>
								</td>
							</tr>
							<tr id="topend">
								<th>置顶有效期止：</th>
								<td>
									<input type='text' name='dtopend' id='dtopend' value="${vo.dtopend }" style='width:230px'  class='intxt' readonly="readonly" onfocus="WdatePicker({minDate:'%y-%M-{%d}'})"  disabled="disabled" />
								</td>
							</tr>
							<tr>
								<th>信息来源：</th>
								<td><input name="cfrom"  id="cfrom" value="${vo.cfrom }" style="width: 680px;" disabled="disabled" /></td>
							</tr>
							<tr>
								<th>公告内容：</th>
								<td>
									<textarea name="cdetail" id="cdetail"  rows="8" cols="60" style="width:680px;height:240px;visibility:hidden;">${vo.cdetail }</textarea>
									<script type="text/javascript">
										KindEditor.ready(function(K) {
											var editor1 = K.create('textarea[name="cdetail"]', {
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
								<th>有效期起：</th>
								<td>
									<input type='text' name='dvalidbegin' id='dvalidbegin' value="${vo.dvalidbegin }"  style='width:230px'  class='intxt' readonly="readonly" onFocus="WdatePicker({minDate:'%y-%M-{%d}'})" disabled="disabled" />
								</td>
							</tr>
							<tr>
								<th>有效期止：</th>
								<td>
									<input type='text' name='dvalidend' id='dvalidend' value="${vo.dvalidend }" style='width:230px'  class='intxt' readonly="readonly"  onFocus="WdatePicker({minDate:'#F{$dp.$D(\'dvalidbegin\')}'})"  disabled="disabled"/>
								</td>
							</tr>
							<tr>
								<th>链接地址：</th>
								<td>
									<input name="chref"   value="${vo.chref}"  style="width: 680px;" disabled="disabled"/>
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
								<th>附件：</th>
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
									<textarea name="auditOpinion" id="auditOpinion"  style="width:680px;" readonly="readonly" disabled="disabled">${vo.auditOpinion}</textarea>
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
