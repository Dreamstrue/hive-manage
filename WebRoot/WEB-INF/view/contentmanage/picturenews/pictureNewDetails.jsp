<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<link rel="stylesheet" href="${appPath}/resources/css/info.css" type="text/css"></link>
<script type="text/javascript">
		$(function() {
			//修改时，新闻图片的选择框隐藏，当对应的图片被删除后，在出现。
			$('#picImg').hide();
		
			var chasannex = $('.np').val();
			if(chasannex == '0'){
				$('#checkFile').hide();
			}
	});
</script>
<div align="left" style="width:70%; height: 100%;">
<div id="div1" >当前位置：图片新闻&nbsp;&gt;&nbsp;查看</div>
<div id="div2" align="right"><a id="btn" href="${pageContext.request.contextPath}/picture/manage.action" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">返回列表</a> </div>
<div align="left">
	<table class="integrity_table" style="width: 100%">
		<tr>
			<td>
				<div>
					<form  id="form" method="post" enctype="multipart/form-data">
					<input type="hidden" name="id" id="id" value="${vo.id }">
						<table >
							<tr>
								<th>新闻标题：</th>
								<td><input name="title"  id="title"  value="${vo.title}" style="width: 680px;" disabled="disabled"/>
							</tr>
							<tr>
								<th>新闻图片：</th>
								<td>
									<div id="picImg">
										<input  type="file" name="imgfile"  id="picture"  style="width: 680px;"/>
									</div>
									<div id="${img.id}">
											<a href="${appPath}/annex/download.action?id=${img.id}">${img.cfilename}</a>
									</div>
								</td>
							</tr>
							<tr>
								<th>信息来源：</th>
								<td><input name="cfrom"  id="cfrom" value="${vo.cfrom}" style="width: 680px;" disabled="disabled"/></td>
							</tr>
							<tr>
								<th>新闻内容：</th>
								<td>
									<textarea name="content" id="content"  rows="8" cols="60" style="width:680px;height:300px;visibility:hidden;">${vo.content }</textarea>
									<script type="text/javascript">
										KindEditor.ready(function(K) {
											var editor1 = K.create('textarea[name="content"]', {
												cssPath : '${appPath}/resources/js/plugin/kindeditor/plugins/code/prettify.css',
												uploadJson : '${appPath}/resources/js/plugin/kindeditor/jsp/upload_json_zxt.jsp',
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
								<th>排序</th>
								<td>
									<input name="isortorder" value="${vo.isortorder}" class="easyui-numberspinner" data-options="min:1,max:999,editable:false,required:true"  style="width: 200px;"  disabled="disabled"/>
								</td>
							</tr>
							<tr>
								<th>链接地址：</th>
								<td>
									<input name="chref"   value="${vo.chref}" style="width: 680px;" disabled="disabled"/>
								</td>
							</tr>
							<tr>
								<td class="tt">允许评论：</td>
								<td>
									<c:if test="${vo.isComment=='1'}">
										是
									</c:if>
									<c:if test="${vo.isComment=='0'}">
										否
									</c:if>
								</td>
							</tr>
							<tr>
								<td class="tt">允许分享：</td>
								<td>
									<c:if test="${vo.isShare=='1'}">
										是
									</c:if>
									<c:if test="${vo.isShare=='0'}">
										否
									</c:if>
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
								</td>
							</tr>
							<tr>
								<th><font color="red">审核意见：</font></th>
								<td>
									<textarea name="auditOpinion" id="auditOpinion"  style="width:680px;" readonly="readonly" disabled="disabled">${vo.cauditopinion}</textarea>
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
