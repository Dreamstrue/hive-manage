<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../common/inc_notice.jsp"></jsp:include>
<link rel="stylesheet" href="${appPath}/resources/css/info.css" type="text/css"></link>
<link rel="stylesheet" href="${appPath}/resources/js/plugin/uploadify/uploadify.css" type="text/css"></link>
<script type="text/javascript" src="${appPath}/resources/js/plugin/uploadify/jquery.uploadify.v2.1.4.js"></script>
<script type="text/javascript" src="${appPath}/resources/js/plugin/uploadify/swfobject.js"></script>
<script type="text/javascript">
		$(function() {
			var chasannex = $('.np').val();
			if(chasannex == '0'){
			//	$('#checkFile').hide();
				$('#uploadFile').hide();
			}
			
			$('.np').change(function(){
				var value = $('.np').val();
				if(value=='0'){
					$.messager.confirm('提示','该操作将删除您已选择的所有附件，是否继续?',function(b){
						if(b){
				//			$('#checkFile').hide();
							$('#uploadFile').hide();
						}else $('.np').val('1');
					});
				}else if(value='1'){
				//	$('#checkFile').show();
					$('#uploadFile').show();
				}			
			});
			
			var idStr = new Array;	
		$('#file').uploadify({
				'uploader':'${appPath}/resources/js/plugin/uploadify/uploadify.swf',
				'script':'${appPath}/caution/uploadFiles.action', //上传文件的后台处理程序的相对路径
				'cancelImg':'${appPath}/resources/js/plugin/uploadify/cancel.png',
				'queueID':'showfile',// 文件队列的ID，该ID与存放文件队列的div的ID一致。
 				'multi':true, // 设置为true时可以上传多个文件。
				'auto':false, // 设置为true当选择文件后就直接上传了，为false需要点击上传按钮才上传 。 
				'fileDataName':'file', // 和以下input的name属性一致
				'sizeLimit':104857600,//上传文件的大小限制 。这里为100M
				'simUploadLimit':3,// 允许同时上传的个数 默认值：1 。
				'buttonText':'浏览附件',// 浏览按钮的文本，默认值：BROWSE 。
				'hideButton':false,// 设置为true则隐藏浏览按钮的图片 。
				'rollover':false,// 值为true和false，设置为true时当鼠标移到浏览按钮上时有反转效果。
				'width':'100',// 设置浏览按钮的宽度 ，默认值：110。
				'height':'25',// 设置浏览按钮的高度 ，默认值：30。
				'displayData':'speed',//进度条的显示方式
				onComplete : function(event, queueID, fileObj, response, data) {
					var r = $.parseJSON(response);
					$('#showfile').append("<div>"+r[0].fileName+"</div>");
					idStr.push(r[0].idString);
					$('#idString').val(idStr);
				}
			});
	});
		var ids = new Array;
	function check(){
		if(checkCautionValue()){
			$('#ids').val(ids);
			$('#form').form('submit',{
				url:'${appPath}/caution/update.action',
				success:function(result){
					var r = $.parseJSON(result);
					$.messager.show({
						title:'提示',
						msg:r.msg
					});
					setTimeout(function(){
						location.href='${appPath}/caution/manage.action?id=${vo.nmenuid}&pid=${pid}';
					},1000);
				}
			});
		}
	}
	
	function approveAction(){
			$('#ids').val(ids);
			$('#form').form('submit',{
				url:'${appPath}/caution/approveAction.action',
				success:function(result){
					var r = $.parseJSON(result);
					$.messager.show({
						title:'提示',
						msg:r.msg
					});
					setTimeout(function(){
						location.href='${appPath}/caution/manage.action?id=${vo.nmenuid}&pid=${pid}';
					},1000);
				}
			});
	}
	
	//删除附件的公共方法
	function delAnnex(id){
		$.messager.confirm('提示','您确定要删除该附件?',function(b){
			if(b){
				$('#'+id).hide();
				ids.push(id);
			}
		});
	}
</script>
<div align="left" style="width:70%; height: 100%;">
<c:if test="${opType=='update' }">
<div id="div1" >当前位置：警示信息&nbsp;&gt;&nbsp;修改</div>
</c:if>
<c:if test="${opType=='approve' }">
<div id="div1" >当前位置：警示信息&nbsp;&gt;&nbsp;审核</div>
</c:if>
<div id="div2" align="right"><a id="btn" href="${appPath}/caution/manage.action?id=${vo.nmenuid}&pid=${pid}" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">返回列表</a> </div>
<div align="left">
	<table class="integrity_table" style="width: 100%">
		<tr>
			<td>
				<div>
					<form  id="form" method="post" enctype="multipart/form-data">
						<input type="hidden" name="id" id="id" value="${vo.id }">
						<!-- 此处定义的ids用来保存 被删除的附件的id -->
						<input type="hidden" name="ids" id="ids">
						<table >
							<tr>
								<td class="tt">栏目选择：</td>
								<td>
									<input id="nmenuid" name="nmenuid" class="easyui-combobox" data-options="url:'${appPath}/navMenu/subNavMenu.action?id=${pid}',valueField:'id',textField:'text',required:true " style="width:170px;" value="${vo.nmenuid}" />
								</td>
							</tr>
							<tr>
								<td class="tt">标题：</td>
								<td><input name="title"  id="title" value="${vo.title}" class="easyui-validatebox" data-options="required:true" style="width: 630px;"/></td>
							</tr>
							<tr>
								<td class="tt">所属来源：</td>
								<td><input name=cfrom  id="cfrom"  value="${vo.cfrom}" class="easyui-validatebox" data-options="required:true" style="width: 630px;"/></td>
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
										});
									</script>
								</td>
							</tr>
							<tr>
								<td class="tt">链接地址：</td>
								<td><input name=chref  id="chref"  value="${vo.chref}" style="width: 630px;"/></td>
							</tr>
							<tr>
								<td class="tt">存在附件：</td>
								<td>
									<select class="np" name="chasannex">
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
							<tr id="uploadFile">
								<td class="tt">选择附件：</td>
								<td>
									<span>
										<c:if test="${!empty annex}">
											<c:forEach items="${annex}" var="t" varStatus="status">
											<div id="${t.id}">
											<a href="${appPath}/annex/download.action?id=${t.id}">${t.cfilename}</a>&nbsp;&nbsp;<input type="button" value="删除" onclick="delAnnex(${t.id})">
											</div>
											</c:forEach>
										</c:if>
									</span>
									<div id="showfile">
									</div>
									<input type="file"  name="file" id="file" />
									<input type="hidden" id="idString" name="idString" />
									<div style="font-size: 12px;">
								      <a href="javascript:$('#file').uploadifyUpload()">上传</a>| 
								      <a href="javascript:$('#file').uploadifyClearQueue()">取消上传</a>
								    </div>
								</td>
							</tr>
							<c:if test="${opType=='approve' }">
							<tr>
								<td class="tt">审核结果：</td>
								<td>
									<select class="re" name="auditstatus">
										<option value="1" selected="selected">通过</option>
										<option value="2" >不通过</option>
									</select>
								</td>
							</tr>
							<tr>
								<td class="tt">审核意见：</td>
								<td>
									<textarea name="auditopinion" id="auditopinion"  style="width:630px;"></textarea>
								</td>
							</tr>
							</c:if>
							<c:if test="${opType=='update' }">
							<c:if test="${vo.auditstatus=='2' }">
							<tr>
								<td class="tt"><font color="red">审核意见：</font></td>
								<td>
									<textarea name="auditopinion" id="auditopinion"  style="width:630px;" readonly="readonly">${vo.auditopinion}</textarea>
								</td>
							</tr>
							</c:if>
							</c:if>
						</table>
						<c:if test="${opType=='update' }">
							<p align="right" style="padding-right: 70px;">
								<input type="button" value="提交" onclick="check()"/>
				   			</p>
						</c:if>
						<c:if test="${opType=='approve'}">
							<p align="right" style="padding-right: 70px;">
								<input type="button" value="提交" onclick="approveAction()"/>
				   			</p>
						</c:if>
					</form>
				</div>
			</td>
		</tr>
	</table>
</div>
</div>
