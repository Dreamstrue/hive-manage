<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/integrity.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/uploadify/uploadify.css" type="text/css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/uploadify/jquery.uploadify.v2.1.4.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/uploadify/swfobject.js"></script>
<script type="text/javascript">
		$(function() {
			/* $('#checkFile').hide();
			$('.np').change(function(){
				var value = $('.np').val();
				if(value=='0'){
					$('#checkFile').hide();
				}else if(value='1'){
					$('#checkFile').show();
				}			
			}); */
			
			$('#uploadFile').hide();
			$('.np').change(function(){
				var value = $('.np').val();
				if(value=='0'){
					$('#uploadFile').hide();
				}else if(value='1'){
					$('#uploadFile').show();
				}			
			});	
			
			$('.top').change(function(){
				var value = $('.top').val();
				if(value=='0'){
					$('#topend').hide();
				}else if(value='1'){
					$('#topend').show();
				}			
			});
			
			
			//获取当前日期
			var s = format_Date();
			$('#dvalidbegin').val(s);
			
			
		var idStr = new Array;	
		$('#file').uploadify({
				'uploader':'${pageContext.request.contextPath}/resources/js/uploadify/uploadify.swf',
				'script':'${pageContext.request.contextPath}/notice/uploadFiles.action', //上传文件的后台处理程序的相对路径
				'cancelImg':'${pageContext.request.contextPath}/resources/js/uploadify/cancel.png',
	//			'queueSizeLimit':3, // 当允许多文件生成时，设置选择文件的个数，默认值：999 。 
				'queueID':'showfile',// 文件队列的ID，该ID与存放文件队列的div的ID一致。
 				'multi':true, // 设置为true时可以上传多个文件。
				'auto':false, // 设置为true当选择文件后就直接上传了，为false需要点击上传按钮才上传 。 
				'fileDataName':'file', // 和以下input的name属性一致
	//			'fileExt':'*.doc;*.pdf;*.zip;*.jpg;*.png;*.jpeg;',  // 设置可以选择的文件的类型，格式如：'*.doc;*.pdf;*.rar' 。 
	//			'fileDesc':'请选择zip,doc,pdf,jpg文件', //这个属性值必须设置fileExt属性后才有效，用来设置选择文件对话框中的提示文本，如设置fileDesc为“请选择rar doc pdf文件”
				'sizeLimit':104857600,//上传文件的大小限制 。这里为100M
				'simUploadLimit':3,// 允许同时上传的个数 默认值：1 。
				'buttonText':'浏览附件',// 浏览按钮的文本，默认值：BROWSE 。
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
				}/* ,
				onError:function(event,queueId,fileObj,errorObj){//当上传过程中发生错误时触发。
					 var errorCode = errorObj.info;
					 switch(errorCode){
					 	case 500:
						 	$.messager.show({
								title:'提示',
								msg:'上传附件失败(应小于50M)'
							});
							break;
						case 404:
							$.messager.show({
								title:'提示',
								msg:'没有对应上传附件的方法'
							});
							break;
					 }
				} */
			});
	});
	
	function check(){
		if(checkValue()){
			$('#form').form('submit',{
				url:'${pageContext.request.contextPath}/notice/insert.action',
				success:function(result){
					var r = $.parseJSON(result);
					$.messager.show({
						title:'提示',
						msg:r.msg
					});
					if(r.status){
						
					setTimeout(function(){
						location.href='${pageContext.request.contextPath}/notice/manage.action';
					},1000);
					}
				}
			});
		}
	}
	
	function reload(){
		location.href='${pageContext.request.contextPath}/notice/add.action';
	}
	
	function checkValue(){
		var title =  $('#title').val();
		if(!checkStr(title)){
			showMessage('公告主题不能为空');
			return false;
		}
		
		var begin =  $('#dvalidbegin').val();
		if(!checkStr(begin)){
			showMessage('有效期起不能为空');
			return false;
		}
		
		return true;
	}
</script>
<div align="left" style="width:70%; height: 100%;">
<div id="div1" >当前位置：通知公告&nbsp;&gt;&nbsp;新增</div>
<div id="div2" align="right"><a id="btn" href="${pageContext.request.contextPath}/notice/manage.action" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">返回列表</a> </div>
<hr style="color: silver;border-style: solid;"/>
<div align="left" style="width: 100%">
	<table class="integrity_table">
		<tr>
			<td>
				<div>
					<form  id="form" method="post" enctype="multipart/form-data">
						<table >
							<tr>
								<th>公告主题：</th>
								<td><input name="title"  id="title"  style="width: 620px;"/>&#160;<font color="#FF0000" size="2">*必填项</font></td>
							</tr>
							<tr>
								<th>公告概要：</th>
								<td><input name="content"  id="content"  style="width: 680px;"/></td>
							</tr>
							<tr>
								<th>公告类别：</th>
								<td>
									<select class="cate" name="ccategory">
										<option value="1" selected="selected">列表展示</option>
										<option value="0" >浮动窗口</option>
									</select>
								</td>
							</tr>
							<tr>
								<th>重要标志：</th>
								<td>
									<input type="radio" class="sign" name="cimportant" value="1">是&nbsp;&nbsp;<input type="radio" class="sign" name="cimportant" value="0" checked="checked">否
									<!-- <select class="sign" name="cimportant">
										<option value="1">是</option>
										<option value="0"  selected="selected">否</option>
									</select> -->
								</td>
							</tr>
							<tr>
								<th>是否置顶：</th>
								<td>
									<select class="top" name="ctop">
										<option value="1" selected="selected">是</option>
										<option value="0" >否</option>
									</select>
								</td>
							</tr>
							<tr id="topend">
								<th>置顶有效期止：</th>
								<td>
									<input type='text' name='dtopend' id='dtopend' style='width:230px'  class='intxt' readonly="readonly" onfocus="WdatePicker({minDate:'%y-%M-{%d}'})"  />
								</td>
							</tr>
							<tr>
								<th>信息来源：</th>
								<td><input name="cfrom"  id="cfrom" style="width: 680px;"/></td>
							</tr>
							<tr>
								<th>公告内容：</th>
								<td>
									<textarea name="cdetail" id="cdetail"  rows="8" cols="60" style="width:680px;height:240px;visibility:hidden;"></textarea>
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
										});
									</script>
								</td>
							</tr>
							<tr>
								<th>有效期起：</th>
								<td>
									<input type='text' name='dvalidbegin' id='dvalidbegin' style='width:230px'  class='intxt' readonly="readonly"  onFocus="WdatePicker({minDate:'%y-%M-{%d}'})"/>&#160;<font color="#FF0000" size="2">*必填项</font>
								</td>
							</tr>
							<tr>
								<th>有效期止：</th>
								<td>
									<input type='text' name='dvalidend' id='dvalidend' style='width:230px'  class='intxt' readonly="readonly"  onFocus="WdatePicker({minDate:'#F{$dp.$D(\'dvalidbegin\')}'})"  />
								</td>
							</tr>
							<tr>
								<th>链接地址：</th>
								<td>
									<input name="chref"   style="width: 680px;"/>
								</td>
							</tr>
							<tr>
								<th>存在附件：</th>
								<td>
									<select class="np" name="chasannex">
										<option value="1">是</option>
										<option value="0" selected="selected">否</option>
									</select>
								</td>
							</tr>
							<tr id="uploadFile">
								<th>选择附件：</th>
								<td>
									<div id="showfile">
									</div>
									<input type="file"  name="file" id="file" />
									<input type="hidden" id="idString" name="idString"  />
									<div style="font-size: 12px;">
								      <a href="javascript:$('#file').uploadifyUpload()">上传</a>| 
								      <a href="javascript:$('#file').uploadifyClearQueue()">取消上传</a>
								    </div>
								</td>
							</tr>
							<!-- <tr id="checkFile">
								<th>选择附件：</th>
								<td>
									<input type="button" name="button" value="添加附件" onclick="addInput()">
									<input type="button" name="button" value="删除附件" onclick="deleteInput()"><br/>
									<span id="upload"></span>
								</td>
							</tr> -->
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
