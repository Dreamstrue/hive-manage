<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<link rel="stylesheet" href="${appPath}/resources/css/info.css" type="text/css"></link>
<link rel="stylesheet" href="${appPath}/resources/js/plugin/uploadify/uploadify.css" type="text/css"></link>
<script type="text/javascript" src="${appPath}/resources/js/plugin/uploadify/jquery.uploadify.v2.1.4.js"></script>
<script type="text/javascript" src="${appPath}/resources/js/plugin/uploadify/swfobject.js"></script>
<script type="text/javascript">
		$(function() {
			$('.np').change(function(){
				var value = $('.np').val();
				if(value=='0'){
					$('#checkFile').hide();
				}else if(value='1'){
					$('#checkFile').show();
				}			
			});
			
			var idStr = new Array;	
		$('#exefile').uploadify({
				'uploader':'${appPath}/resources/js/plugin/uploadify/uploadify.swf',
				'script':'${pageContext.request.contextPath}/magazine/uploadExeFiles.action', //上传文件的后台处理程序的相对路径
				'cancelImg':'${appPath}/resources/js/plugin/uploadify/cancel.png',
				'queueID':'showexefile',// 文件队列的ID，该ID与存放文件队列的div的ID一致。
				'queueSizeLimit':1, // 当允许多文件生成时，设置选择文件的个数，默认值：999 。 
 				'multi':true, // 设置为true时可以上传多个文件。
				'auto':false, // 设置为true当选择文件后就直接上传了，为false需要点击上传按钮才上传 。 
				'fileDataName':'file', // 和以下input的name属性一致
				'fileExt':'*.exe;',  // 设置可以选择的文件的类型，格式如：'*.doc;*.pdf;*.rar' 。 
				'fileDesc':'请选择exe文件',
				'sizeLimit':104857600,//上传文件的大小限制 。这里为100M
				'simUploadLimit':3,// 允许同时上传的个数 默认值：1 。
			//	'buttonText':'浏览附件',// 浏览按钮的文本，默认值：BROWSE 。
				'buttonImg':'${appPath}/resources/images/100.png',// 浏览按钮的图片的路径 。
				'hideButton':false,// 设置为true则隐藏浏览按钮的图片 。
				'rollover':false,// 值为true和false，设置为true时当鼠标移到浏览按钮上时有反转效果。
				'width':'100',// 设置浏览按钮的宽度 ，默认值：110。
				'height':'25',// 设置浏览按钮的高度 ，默认值：30。
				'displayData':'speed',//进度条的显示方式
				onComplete : function(event, queueID, fileObj, response, data) {
					var r = $.parseJSON(response);
					$('#showexefile').append("<div>"+r[0].fileName+"</div>");
					idStr.push(r[0].idString);
					$('#exeString').val(idStr);
				}
			});
			
			
		var idStr2 = new Array;	
		$('#zipfile').uploadify({
				'uploader':'${appPath}/resources/js/plugin/uploadify/uploadify.swf',
				'script':'${pageContext.request.contextPath}/magazine/uploadZipFiles.action', //上传文件的后台处理程序的相对路径
				'cancelImg':'${appPath}/resources/js/plugin/uploadify/cancel.png',
				'queueID':'showzipfile',// 文件队列的ID，该ID与存放文件队列的div的ID一致。
				'queueSizeLimit':1, // 当允许多文件生成时，设置选择文件的个数，默认值：999 。 
 				'multi':true, // 设置为true时可以上传多个文件。
				'auto':false, // 设置为true当选择文件后就直接上传了，为false需要点击上传按钮才上传 。 
				'fileDataName':'file', // 和以下input的name属性一致
				'fileExt':'*.zip;',  // 设置可以选择的文件的类型，格式如：'*.doc;*.pdf;*.rar' 。 
				'fileDesc':'请选择zip文件', //这个属性值必须设置fileExt属性后才有效，用来设置选择文件对话框中的提示文本，如设置fileDesc为“请选择rar doc pdf文件”
				'sizeLimit':104857600,//上传文件的大小限制 。这里为100M
				'simUploadLimit':3,// 允许同时上传的个数 默认值：1 。
			//	'buttonText':'浏览附件',// 浏览按钮的文本，默认值：BROWSE 。
				'buttonImg':'${appPath}/resources/images/100.png',// 浏览按钮的图片的路径 。
				'hideButton':false,// 设置为true则隐藏浏览按钮的图片 。
				'rollover':false,// 值为true和false，设置为true时当鼠标移到浏览按钮上时有反转效果。
				'width':'100',// 设置浏览按钮的宽度 ，默认值：110。
				'height':'25',// 设置浏览按钮的高度 ，默认值：30。
				'displayData':'speed',//进度条的显示方式
				onComplete : function(event, queueID, fileObj, response, data) {
					var r = $.parseJSON(response);
					$('#showzipfile').append("<div>"+r[0].fileName+"</div>");
					idStr2.push(r[0].idString);
					$('#zipString').val(idStr2);
				}
			});
	});
	
	function check(){
		if(checkPictureValue()){
			$('#form').form('submit',{
				url:'${pageContext.request.contextPath}/magazine/insert.action',
				success:function(result){
					var r = $.parseJSON(result);
					$.messager.show({
						title:'提示',
						msg:r.msg
					});
					if(r.status){
						
					setTimeout(function(){
						location.href='${pageContext.request.contextPath}/magazine/manage.action?id=${menuId}';
					},1000);
					}
				}
			});
		}
	}
	
	function reload(){
		location.href='${pageContext.request.contextPath}/magazine/add.action';
	}
	
	function checkPictureValue(){
		var title =  $('#title').val();
		if(!checkStr(title)){
			showMessage('杂志标题不能为空');
			return false;
		}
		return true;
	}
	
	
	function checkExt(id,type){
		var string = $('#'+id).val();
		var ext = string.substring(string.lastIndexOf('.')+1);
		if(ext!=type){
			alert('请上传扩展名为'+type+'的文件');
			document.getElementById(id).value='';
		}
	}
	
	function checkImg(){
		var string = $('#picture').val();
		var ext = string.substring(string.lastIndexOf('.')+1);
		if(ext!='jpg' && ext!='png' && ext!='gif' && ext!='jpeg' && ext!='bmp'){
			alert('请上传图片');
			document.getElementById('picture').value='';
		}
	}
</script>
<div align="left" style="width:70%; height: 100%;">
<div id="div1" >当前位置：电子杂志&nbsp;&gt;&nbsp;新增</div>
<div id="div2" align="right"><a id="btn" href="${pageContext.request.contextPath}/magazine/manage.action?id=${menuId}" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">返回列表</a> </div>
<div align="left" >
	<table class="integrity_table" style="width: 100%">
		<tr>
			<td>
				<div>
					<form  id="form" method="post" enctype="multipart/form-data">
						<input type="hidden" name="nmenuid" value="${menuId}" />
						<table >
							<tr>
								<th>杂志标题：</th>
								<td><input name="title"  id="title"  style="width: 640px;"/>&#160;<font color="#FF0000" size="2">*必填项</font></td>
							</tr>
							<tr>
								<th>封面图片：</th>
								<td><input  type="file" name="imgfile"  id="picture"  style="width: 640px;" onblur="checkImg()"/>&#160;<font color="#FF0000" size="2">*必填项</font></td>
							</tr>
							<tr>
								<th>概要内容：</th>
								<td>
									<textarea name="content" id="content"  rows="8" cols="60" style="width:680px;height:240px;visibility:hidden;"></textarea>
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
							<tr id="">
								<th>本地杂志：</th>
								<td>
									<div id="showexefile"></div>
									<!-- <input type="file" name="exefile" id="exefile" style="width: 440px;" onblur="checkExt('exefile','exe')">
									<span><font color="red" size="2">*(请上传可执行的.exe文件)</font></span> -->
									<input type="file"  name="exefile" id="exefile" />
									<input type="hidden" id="exeString" name="exeString"  />
									<div style="font-size: 12px;">
								      <a href="javascript:$('#exefile').uploadifyUpload()">上传</a>| 
								      <a href="javascript:$('#exefile').uploadifyClearQueue()">取消上传</a>
								    </div>
								</td>
							</tr>
							<tr id="">
								<th>在线杂志：</th>
								<td>
									<div id="showzipfile"></div>
									<!-- <input type="file" name="rarfile" id="rarfile" style="width: 440px;" onblur="checkExt('rarfile','zip')">
									<span><font color="red" size="2">*(请上传压缩ZIP文件)</font></span> -->
									<input type="file"  name="zipfile" id="zipfile" />
									<input type="hidden" id="zipString" name="zipString"  />
									<div style="font-size: 12px;">
								      <a href="javascript:$('#zipfile').uploadifyUpload()">上传</a>| 
								      <a href="javascript:$('#zipfile').uploadifyClearQueue()">取消上传</a>
								    </div>
								</td>
							</tr>
						</table>
						<p align="right" style="padding-right: 70px;">
								<input type="button" value="提交" onclick="check()"/>
								<input type="button" value="重置" onclick="reload()" />
				   			</p>
					</form>
				</div>
			</td>
		</tr>
	</table>
</div>
</div>
