<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<link rel="stylesheet" href="${appPath}/resources/css/info.css" type="text/css"></link>
<link rel="stylesheet" href="${appPath}/resources/js/plugin/uploadify/uploadify.css" type="text/css"></link>
<script type="text/javascript" src="${appPath}/resources/js/plugin/uploadify/jquery.uploadify.v2.1.4.js"></script>
<script type="text/javascript" src="${appPath}/resources/js/plugin/uploadify/swfobject.js"></script>
<script type="text/javascript">
		$(function() {
			//修改时，新闻图片的选择框隐藏，当对应的图片被删除后，再出现。
			$('#picImg').hide(); 
		 	$('#imageExist').val('yes'); 
		 	$('#fileExist').val('yes'); 
			$('#fileExistHide').hide(); 
			$('#fileHide').hide(); 
			$('#ids').val("${ids.id }"); 
			var chasannex = $('.np').val();
			if(chasannex == '0'){
				$('#uploadFile').hide();
			}else{
				$('#fileExistHide').show(); 
				}
			
			$('.np').change(function(){
				var value = $('.np').val();
				$('#fileExist').val('no');
				if(value=='0'){
					$('#uploadFile').hide();
				}else if(value='1'){
					$('#uploadFile').show();
					$('#fileExistHide').hide(); 
					$('#fileHide').show(); 
					$('#fileExist').val('no');
				}			
			});
			
			var idStr = new Array;	
			/*
		$('#file').uploadify({
				'script':'${appPath}/picture/uploadFiles.action', //上传文件的后台处理程序的相对路径
				'uploader':'${appPath}/resources/js/plugin/uploadify/uploadify.swf',
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
			*/
	});
	
	var ids = new Array;	
	function check(){
		if(!ifChasannex()){
			return;
			}
		$('#form').form('submit',{
			url:'${appPath}/picture/update.action',
			success:function(result){
				var r = $.parseJSON(result);
				$.messager.show({
					title:'提示',
					msg:r.msg
				});
				if(r.status){
					setTimeout(function(){
						location.href='${appPath}/picture/manage.action';
					},1000);
				}
			}
		});
	}
	
	function approveAction(){
		if(!ifChasannex()){
			return;
			}
			$('#form').form('submit',{
				url:'${appPath}/picture/approveAction.action',
				success:function(result){
					var r = $.parseJSON(result);
					$.messager.show({
						title:'提示',
						msg:r.msg
					});
					setTimeout(function(){
						location.href='${appPath}/picture/manage.action';
					},1000);
				}
			});
	}
	//删除图片的方法
	function delAnnex(id,type){
		$.messager.confirm('提示','您确定要删除该附件?',function(b){
			if(b){
				if(type=='image'){
					$('#'+id).hide();
					$('#picImg').show();
					$('#imageExist').val('no');
					$('#imgId').val(id);
				}

				else{
					$('#'+id).hide();
					ids.push(id);
				}
			}
		});
	}
	//附件删除方法
	function delAnnex1(id,type){
		$.messager.confirm('提示','您确定要删除该附件?',function(b){
			if(b){
				if(type=='image'){
					$('#'+id).hide();
					$('#fileHide').show();
					$('#fileExist').val('no');
					$('#ids').val(id);
				}
				else{
					$('#'+id).hide();
					id.push(id);
				}
			}
		});
	}

	//是否选择附件验证
	function  ifChasannex(){
		var chasannex = $("#chasannex").val();
		var annexFile = $("#picFile").val();
		var msg = "";
		var fileExist = $('#fileExist').val();
		if(chasannex==1){
			if(((annexFile=="") || (annexFile == null)) && (fileExist != "yes")){
					$.messager.show({
						title:'提示',
						msg:"请选择附件"
					});
					return false;
				}else{
					return true;
					}
			}else{
				return true;
				}
	}
	
</script>
<div align="left" style="width:75%; height: 100%;">
<c:if test="${opType=='update' }">
<div id="div1" >当前位置：图片新闻&nbsp;&gt;&nbsp;修改</div>
</c:if>
<c:if test="${opType=='approve' }">
<div id="div1" >当前位置：图片新闻&nbsp;&gt;&nbsp;审核</div>
</c:if>
<div id="div2" align="right"><a id="btn" href="${appPath}/picture/manage.action" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">返回列表</a> </div>
<div align="left">
	<table class="integrity_table"  style="width: 100%">
		<tr>
			<td>
				<div>
					<form  id="form" method="post" enctype="multipart/form-data">
					<input type="hidden" name="id" id="id" value="${vo.id }">
					<!-- 此处定义的ids用来保存 被删除的附件的id -->
					<input type="hidden" name="ids" id="ids">
					<!-- 此处定义的imgId用来保存 被删除新闻图片的ID-->
					<input type="hidden" name="imgId" id="imgId"> 
					<!-- 自定义一个字段，方便后台操作附件修改  -->
					<input type="hidden" name="fileExist" id="fileExist"/>
						<table >
							<tr>
								<th>新闻标题：</th>
								<td><input name="title"  id="title"  value="${vo.title}" class="easyui-validatebox" data-options="required:true" style="width: 630px;"/></td>
							</tr>
					 		<tr>
								<th>新闻图片：</th>
								<td>
									<div id="picImg">
										<input  type="file" name="imgfile"  id="picture"  style="width: 400px;"/>&#160;<font color="#FF0000" size="2">*必填项</font>
									</div>
									<div id="${img.id}">
											<a href="${appPath}/annex/download.action?id=${img.id}">${img.cfilename}</a>&nbsp;&nbsp;<input type="button" value="删除" onclick="delAnnex('${img.id }' ,'image')">
									</div>
									<!-- 自定义一个字段，方便后台操作新闻图片修改  -->
									<input type="hidden" name="imageExist" id="imageExist"/>
								</td>
							</tr>
							<tr>
								<th>信息来源：</th>
								<td><input name="cfrom"  id="cfrom" value="${vo.cfrom}" class="easyui-validatebox" data-options="required:true" style="width: 680px;"/></td>
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
										});
									</script>
								</td>
							</tr>
							<tr >
								<th>排序</th>
								<td>
									<input name="isortorder" value="${vo.isortorder}" class="easyui-numberspinner" data-options="min:1,max:999,editable:false,required:true"  style="width: 200px;" />
								</td>
							</tr>
							<tr>
								<th>链接地址：</th>
								<td>
									<input name="chref"   value="${vo.chref}" style="width: 680px;"/>
								</td>
							</tr>
							<tr style="display:none">
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
							<tr style="display:none">
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
								<th>存在附件：</th>
								<td>
									<select class="np" name="chasannex" id="chasannex">
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
								<th>选择附件 ：</th>
								<td>
									<span id="fileExistHide">
											<c:forEach items="${annex}" var="t" varStatus="status">
												<div id="${t.id}">
												<a href="${appPath}/annex/download.action?id=${t.id}">${t.cfilename}</a>&nbsp;&nbsp;<input type="button" value="删除" onclick="delAnnex1('${t.id}','image')">
											</div>
											</c:forEach>
									</span>
									<span id="fileHide">
										<input  type="file" name="files"  id="picFile"  style="width: 400px;"/>
									</span>
								</td>
							</tr>
							<c:if test="${opType=='approve' }">
							<tr>
								<th>审核结果：</th>
								<td>
									<select class="re" name="cauditstatus">
										<option value="1" selected="selected">通过</option>
										<option value="2" >不通过</option>
									</select>
								</td>
							</tr>
							<tr>
								<th>审核意见：</th>
								<td>
									<textarea name="cauditopinion" id="cauditopinion"  style="width:680px;"></textarea>
								</td>
							</tr>
							</c:if>
							<c:if test="${opType=='update' }">
							<c:if test="${vo.auditStatus=='2' }">
							<tr>
								<th><font color="red">审核意见：</font></th>
								<td>
									<textarea name="auditOpinion" id="auditOpinion"  style="width:680px;" readonly="readonly">${vo.cauditopinion}</textarea>
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
						<c:if test="${opType=='approve' }">
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
