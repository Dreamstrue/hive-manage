<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:include page="../common/inc.jsp"></jsp:include>
<link rel="stylesheet" href="${appPath}/resources/css/info.css" type="text/css"></link>
<link rel="stylesheet" href="${appPath}/resources/js/plugin/uploadify/uploadify.css" type="text/css"></link>
<link rel="stylesheet" href="${appPath}/resources/js/plugin/Jcrop/css/jquery.Jcrop.min.css" type="text/css"></link>
<script type="text/javascript" src="${appPath}/resources/js/plugin/uploadify/jquery.uploadify.v2.1.4.js"></script>
<script type="text/javascript" src="${appPath}/resources/js/plugin/uploadify/swfobject.js"></script>
<script type="text/javascript" src="${appPath}/resources/js/plugin/Jcrop/js/jquery.Jcrop.min.js"></script>
<script type="text/javascript" src="${appPath}/resources/js/common/jQuery.UtrialAvatarCutter.js"></script>
<script type="text/javascript" src="${appPath}/resources/js/defined/jquery.upload.js"></script>
<script type="text/javascript">
		$(function() {
			var chasannex = $('.np').val();
			if(chasannex == '0'){
				$('#uploadFile').hide();
			}
		});

	function download(id){
		window.open("${appPath}/annex/download.action?id="+id,"_blank");
	}
</script>
<div align="left" style="width:75%; height: 100%;">
<div id="div1" >当前位置：通知公告&nbsp;&gt;&nbsp;明细</div>
<div id="div2" align="right"><a id="btn" href="${appPath}/commonContent/manage.action" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">返回列表</a> </div>
<div align="left" style="width:100%;">
	<table class="integrity_table" style="width:100%;">
		<tr>
			<td>
				<div>
					<form  id="form" method="post" enctype="multipart/form-data">
						<input type="hidden" name="id" id="id" value="${vo.id }">
						<table >
							<tr>
								<td class="tt">类别：</td>
								<td>
									<input  id="infoCateId" name="infoCateId" value="${vo.infoCateId}" class="easyui-combotree" data-options="url:'${appPath}/infoCate/allInfoTree.action',parentField:'parentId',lines:true,cascadeCheck:false,checkbox:true,multiple:true,onlyLeafCheck:true,required:true" style="width:170px;" disabled="disabled"/>
								</td>
							</tr>
							<tr>
								<td class="tt">标题：</td>
								<td><input name="title"  id="title" value="${vo.title }" class="easyui-validatebox" data-options="required:true" style="width: 680px;" readonly="readonly"/></td>
							</tr>
							<tr>
								<td class="tt">图标：</td>
								<td>
									<div id="image">
										<c:if test="${empty vo.picturePath}">
											<img src="${appPath}/avatar/avatar-small.png" width="60px" height="60px"/>
										</c:if>
										<c:if test="${!empty vo.picturePath}">
											<img src="${appPath}/news/loadPic.action?picPath=${vo.picturePath}" width="60px" height="60px"/>
										</c:if>
									</div>	
								</td>
							</tr>
							<tr>
								<td class="tt">来源：</td>
								<td><input name="source"  id="source" value="${vo.source}" style="width: 680px;" readonly="readonly"/></td>
							</tr>
							<tr>
								<td class="tt">摘要：</td>
								<td><input name="summary"  id="summary"  value="${vo.summary}" class="easyui-validatebox" data-options="required:true" style="width: 680px;" readonly="readonly"/></td>
							</tr>
							<tr>
								<td class="tt">内容：</td>
								<td>
									<textarea name="content" id="content"  rows="8" cols="60" style="width:680px;height:240px;visibility:hidden;" readonly="readonly">${vo.content }</textarea>
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
								<td class="tt">允许下载：</td>
								<td>
									<c:if test="${vo.cdownload=='1'}">
										是
									</c:if>
									<c:if test="${vo.cdownload=='0'}">
										否
									</c:if>
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
								<td class="tt">是否推送：</td>
								<td>
									<c:if test="${vo.isPush=='1'}">
										是
									</c:if>
									<c:if test="${vo.isPush=='0'}">
										否
									</c:if>
								</td>
							</tr>
							<tr>
								<td class="tt">链接地址：</td>
								<td>
									<input name="href"   value="${vo.href}"  style="width: 680px;" readonly="readonly"/>
								</td>
							</tr>
							<tr>
								<td class="tt">存在附件：</td>
								<td>
									<select class="np" name="hasannex" disabled="disabled">
										<c:if test="${vo.hasannex=='1'}">
											<option value="1" selected="selected">是</option>
											<option value="0">否</option>
										</c:if>
										<c:if test="${vo.hasannex=='0'}">
											<option value="1">是</option>
											<option value="0"  selected="selected">否</option>
										</c:if>
									</select>
								</td>
							</tr>
							<tr id="uploadFile">
								<td class="tt">附件：</td>
								<td>
									<span>
										<c:if test="${!empty annex}">
											<c:forEach items="${annex}" var="t" varStatus="status">
											<div id="${t.id}">
											<a href="javascript:download(${t.id})">${t.cfilename}</a>
											</div>
											</c:forEach>
										</c:if>
									</span>
								</td>
							</tr>
							<tr>
								<td class="tt">审核结果：</td>
								<td>
									<select class="re" name="auditStatus" disabled="disabled">
										<option value="1" selected="selected">通过</option>
										<option value="2" >不通过</option>
									</select>
								</td>
							</tr>
							<tr>
								<td><font color="red">审核意见：</font></td>
								<td>
									<textarea name="auditOpinion" id="auditOpinion"  style="width:400px;" readonly="readonly">${vo.auditOpinion}</textarea>
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
