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
	function approveAction(id){
			$('#form').form('submit',{
				url:'${appPath}/discloseManage/approveAction.action?id='+id,
				success:function(result){
					var r = $.parseJSON(result);
					$.messager.show({
						title:'提示',
						msg:r.msg
					});
					setTimeout(function(){
						location.href='${appPath}/discloseManage/toDiscloseList.action';
					},1000);
				}
			});
	}
</script>
<div align="left" style="width:75%; height: 100%;">
<div id="div1" >当前位置：爆料信息&nbsp;&gt;&nbsp;审核</div>
<div id="div2" align="right"><a id="btn" href="${appPath}/discloseManage/toDiscloseList.action" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">返回列表</a> </div>
<div align="left" style="width:100%;">
	<table class="integrity_table" style="width:100%;">
		<tr>
			<td>
				<div>
					<form  id="form" method="post" enctype="multipart/form-data">
						<table >
							<tr>
								<td class="tt">标题：</td>
								<td>
									<input name="title" id="title" style="width:680px;" value="${vo.title }"/>
								</td>
							</tr>
							<tr>
								<td class="tt">内容：</td>
								<td>
									<textarea name="content" id="content"  rows="8" cols="60" style="width:680px;height:240px;">${vo.content }</textarea>
								</td>
							</tr>
							<c:if test="${!empty list}">
							<tr>
								<td class="tt">图片</td>
								<td>
									<div>
										<c:forEach items="${list}" var="a">
											<div style="float: left; margin-right: 15px;">
												<img src="${appPath}/annex/loadPic.action?picPath=${a.cfilepath}" width="120" height="120" />
											</div>
										</c:forEach>
									</div>
								</td>
							
							</tr>
							</c:if>
							<tr>
								<td class="tt">审核结果：</td>
								<td>
									<select class="re" name="auditStatus">
										<option value="1" selected="selected">通过</option>
										<option value="2" >不通过</option>
									</select>
								</td>
							</tr>
						</table>
							<p align="right" style="padding-right: 70px;">
								<input type="button" value="提交" onclick="approveAction(${vo.id})"/>
				   			</p>
						
					</form>
				</div>
			</td>
		</tr>
	</table>
</div>
</div>
