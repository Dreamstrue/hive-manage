<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<link rel="stylesheet" href="${appPath}/resources/css/info.css" type="text/css"></link>
<link rel="stylesheet" href="${appPath}/resources/js/plugin/uploadify/uploadify.css" type="text/css"></link>
<script type="text/javascript" src="${appPath}/resources/js/plugin/uploadify/jquery.uploadify.v2.1.4.js"></script>
<script type="text/javascript" src="${appPath}/resources/js/plugin/uploadify/swfobject.js"></script>
<script type="text/javascript">
		$(function() {
			$('#fileExist').val('yes'); 
			var chasannex = $('.np').val();
			if(chasannex == '0'){
				$('#uploadFile').hide();
			}else {
				$('#fileHide').hide();
			}
		
			$('.np').change(function(){
				var value = $('.np').val();
				if(value=='0'){
					$.messager.confirm('提示','该操作将删除您已选择的所有附件，是否继续?',function(b){
						if(b){
							$('#uploadFile').hide();
						}else $('.np').val('1');
					});
				}else if(value='1'){
					$('#uploadFile').show();
				}			
			});
			
			
			var ctop = $('.top').val();
			if(ctop == '0'){
				$('#opend').hide();
			}
			
			$('.top').change(function(){
				var value = $('.top').val();
				if(value=='0'){
					$('#opend').hide();
				}else if(value='1'){
					$('#opend').show();
				}			
			});
			
			
			var sendObject = ${vo.sendObject};
			$('#sendObject').val(sendObject);
			
		var idStr = new Array;	
		$('#file').uploadify({
				'uploader':'${appPath}/resources/js/plugin/uploadify/uploadify.swf',
				'script':'${appPath}/notice/uploadFiles.action', //上传文件的后台处理程序的相对路径
				'cancelImg':'${appPath}/resources/js/plugin/uploadify/cancel.png',
	//			'queueSizeLimit':3, // 当允许多文件生成时，设置选择文件的个数，默认值：999 。 
				'queueID':'showfile',// 文件队列的ID，该ID与存放文件队列的div的ID一致。
 				'multi':true, // 设置为true时可以上传多个文件。
				'auto':false, // 设置为true当选择文件后就直接上传了，为false需要点击上传按钮才上传 。 
				'fileDataName':'file', // 和以下input的name属性一致
				'fileExt':'*.doc;*.pdf;*.zip;*.jpg;*.png;*.jpeg;',  // 设置可以选择的文件的类型，格式如：'*.doc;*.pdf;*.rar' 。 
				'fileDesc':'请选择zip,doc,pdf,jpg文件', //这个属性值必须设置fileExt属性后才有效，用来设置选择文件对话框中的提示文本，如设置fileDesc为“请选择rar doc pdf文件”
				'sizeLimit':104857600,//上传文件的大小限制 。这里为100M
				'simUploadLimit':3,// 允许同时上传的个数 默认值：1 。
				'buttonText':'选择附件',// 浏览按钮的文本，默认值：BROWSE 。
			//	'buttonImg':'',// 浏览按钮的图片的路径 。
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
			$('#ids').val(ids);
			$('#form').form('submit',{
				url:'${appPath}/notice/update.action',
				success:function(result){
					var r = $.parseJSON(result);
					$.messager.show({
						title:'提示',
						msg:r.msg
					});
					setTimeout(function(){
						location.href='${appPath}/notice/manage.action';
					},1000);
				}
			});
	}
	
	
	
	function approveAction(){
			$('#ids').val(ids);
			$('#form').form('submit',{
				url:'${appPath}/notice/approveAction.action',
				success:function(result){
					var r = $.parseJSON(result);
					$.messager.show({
						title:'提示',
						msg:r.msg
					});
					setTimeout(function(){
						location.href='${appPath}/notice/manage.action';
					},1000);
				}
			});
	}
	
	
	
	
	
	//删除附件的公共方法
	function delAnnex(id){
		$.messager.confirm('提示','您确定要删除该附件?',function(b){
			if(b){
				$('#'+id).hide();
				$('#fileHide').show();
				$('#fileExist').val('no');
				$('#ids').val(id);
				ids.push(id);
			}
		});
	}

	function download(id){
	//	location.href='${appPath}/annex/download.action?id='+id;
		window.open("${appPath}/annex/download.action?id="+id,"_blank");
	}
	
</script>
<div align="left" style="width:75%; height: 100%;">
<c:if test="${opType=='update' }">
<div id="div1" >当前位置：通知公告&nbsp;&gt;&nbsp;修改</div>
</c:if>
<c:if test="${opType=='approve' }">
<div id="div1" >当前位置：通知公告&nbsp;&gt;&nbsp;审核</div>
</c:if>
<div id="div2" align="right"><a id="btn" href="${appPath}/notice/manage.action" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">返回列表</a> </div>
<div align="left" style="width:100%;">
	<table class="integrity_table" style="width:100%; float: left;">
		<tr>
			<td>
				<div>
					<form  id="form" method="post" enctype="multipart/form-data">
						<input type="hidden" name="id" id="id" value="${vo.id }">
						<!-- 此处定义的ids用来保存 被删除的附件的id -->
						<input type="hidden" name="ids" id="ids">
						<!-- 自定义一个字段，方便后台操作附件修改  -->
						<input type="hidden" name="fileExist" id="fileExist"/>
						<table >
							<tr>
								<td class="tt">公告标题：</td>
								<td><input name="title"  id="title" value="${vo.title }" class="easyui-validatebox" data-options="required:true" style="width: 680px;"/></td>
							</tr>
							<tr>
								<td class="tt">发布对象：</td>
								<td>
									<select name="sendObject" id="sendObject">
										<option value="0">全部</option>
										<option value="1">个人</option>
										<option value="2">企业</option>
									</select>
								</td>
							</tr>
							<tr>
								<td class="tt">信息来源：</td>
								<td><input name="source"  id="source" value="${vo.source}" style="width: 680px;"/></td>
							</tr>
							<tr>
								<td class="tt">是否置顶：</td>
								<td>
									<select class="top" name="top">
										<c:if test="${vo.top=='1'}">
											<option value="1" selected="selected">是</option>
											<option value="0">否</option>
										</c:if>
										<c:if test="${vo.top=='0'}">
											<option value="1">是</option>
											<option value="0"  selected="selected">否</option>
										</c:if>
									</select>
								</td>
							</tr>
							<tr id="topend">
								<td class="tt">置顶有效期：</td>
								<td>
									<input type='text' name='topend' id='topend' style='width:230px' value="${vo.topend }" class="easyui-validatebox" readonly="readonly" onfocus="WdatePicker({minDate:'%y-%M-{%d}'})"  />
								</td>
							</tr>
							<tr>
								<td class="tt">公告内容：</td>
								<td>
									<textarea name="content" id="content"  rows="8" cols="60" style="width:680px;height:240px;visibility:hidden;">${vo.content }</textarea>
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
								<td class="tt">有效期起：</td>
								<td>
									<input type='text' name='validbegin' id='validbegin' value="${vo.validbegin }" style='width:230px'  class='easyui-validatebox' data-options="required:true" readonly="readonly"  onFocus="WdatePicker({minDate:'%y-%M-{%d}'})"/>
								</td>
							</tr>
							<tr>
								<td class="tt">有效期止：</td>
								<td>
									<input type='text' name='validend' id='validend' value="${vo.validend }" style='width:230px'  class='easyui-validatebox' readonly="readonly"  onFocus="WdatePicker({minDate:'#F{$dp.$D(\'validbegin\')}'})"  />
								</td>
							</tr>
							<tr>
								<td class="tt">链接地址：</td>
								<td>
									<input name="href"   value="${vo.href}"  style="width: 680px;"/>
								</td>
							</tr>
							<tr>
								<td class="tt">允许评论：</td>
								<td>
									<c:if test="${vo.isComment=='1'}">
										<input  type="radio" class="cdown" name="isComment" value="1" checked="checked" />是&nbsp;&nbsp;&nbsp;<input  type="radio" class="cdown" name="isComment" value="0" />否
									</c:if>
									<c:if test="${vo.isComment=='0'}">
										<input  type="radio" class="cdown" name="isComment" value="1"/>是&nbsp;&nbsp;&nbsp;<input  type="radio" class="cdown" name="isComment" value="0" checked="checked" />否
									</c:if>
								</td>
							</tr>
							<tr>
								<td class="tt">允许分享：</td>
								<td>
									<c:if test="${vo.isShare=='1'}">
										<input  type="radio" class="cdown" name="isShare" value="1" checked="checked"/>是&nbsp;&nbsp;&nbsp;<input  type="radio" class="cdown" name="isShare" value="0" />否
									</c:if>
									<c:if test="${vo.isShare=='0'}">
										<input  type="radio" class="cdown" name="isShare" value="1"/>是&nbsp;&nbsp;&nbsp;<input  type="radio" class="cdown" name="isShare" value="0" checked="checked" />否
									</c:if>
								</td>
							</tr>
							<tr>
								<td class="tt">是否推送：</td>
								<td>
									<c:if test="${vo.isPush=='1'}">
										<input  type="radio" class="cdown" name="isPush" value="1" checked="checked"/>是&nbsp;&nbsp;&nbsp;<input  type="radio" class="cdown" name="isPush" value="0" />否
									</c:if>
									<c:if test="${vo.isPush=='0'}">
										<input  type="radio" class="cdown" name="isPush" value="1"/>是&nbsp;&nbsp;&nbsp;<input  type="radio" class="cdown" name="isPush" value="0" checked="checked" />否
									</c:if>
								</td>
							</tr>
							<tr>
								<td class="tt">存在附件：</td>
								<td>
									<select class="np" name="hasannex">
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
								<td class="tt">选择附件：</td>
								<td>
									<span id="fileExistHide">
										<c:if test="${!empty annex}">
											<c:forEach items="${annex}" var="t" varStatus="status">
											<div id="${t.id}">
											<a href="${appPath}/annex/download.action?id=${t.id}">${t.cfilename}</a>&nbsp;&nbsp;<input type="button" value="删除" onclick="delAnnex('${t.id}')">
											</div>
											</c:forEach>
										</c:if>
									</span >
									<span id="fileHide">
										<input  type="file" name="files"  id="picFile"  style="width: 400px;"/>
									</span>
								</td>
							</tr>
							<c:if test="${opType=='approve' }">
							<tr>
								<td class="tt">审核结果：</td>
								<td>
									<select class="re" name="auditStatus">
										<option value="1" selected="selected">通过</option>
										<option value="2" >不通过</option>
									</select>
								</td>
							</tr>
							<tr>
								<td class="tt">审核意见：</td>
								<td>
									<textarea name="auditOpinion" id="auditOpinion"  style="width:680px;"></textarea>
								</td>
							</tr>
							</c:if>
							<c:if test="${opType=='update' }">
							<c:if test="${vo.auditStatus=='2' }">
							<tr>
								<td><font color="red">审核意见：</font></td>
								<td>
									<textarea name="auditOpinion" id="auditOpinion"  style="width:400px;" readonly="readonly">${vo.auditOpinion}</textarea>
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
