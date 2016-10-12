<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
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
		//图片裁切器
		var cutter = new jQuery.UtrialAvatarCutter({
			//主图片所在容器ID
			content : "originalImgContainer",
			//缩略图配置,ID:所在容器ID;width,height:缩略图大小
			purviews : [{id:"preview",width:60,height:60}],
			//选择器默认大小
			selector : {width:180,height:180}
		});
		
		
		
		$(function() {
			var ids ='';
			var t = $('#infoCateId').combotree('tree');// 得到树对象
			$('#infoCateId').combotree({
				onCheck:function(node,checked){
					var nodes = t.tree('getChecked');
					if(nodes.length>1){ //说明有一个节点被选中了
						var node1 = t.tree('find', ids);
						t.tree('uncheck',node1.target);
					}
					ids = node.id;
					
					//针对消费资讯包括“头条”这个分类，当选择消费资讯下的分类时，显示是否作为头条选项
					var node2 = t.tree('find',node.id);
					var pnode = t.tree('getParent', node2.target);
					var div1 = document.getElementById("top");
					$('#pid').val(pnode.id);
					if(pnode.id =='42'){
						div1.style.display = "";
					}else{
						div1.style.display = "none";
					} 
					
				}
			});
			
		
			$('#uploadFile').hide();
			$('.np').change(function(){
				var value = $('.np').val();
				if(value=='0'){
					$('#uploadFile').hide();
				}else if(value='1'){
					$('#uploadFile').show();
				}			
			});	
			
			
		//上传附件
		var idStr = new Array;	
		$('#file').uploadify({
				'uploader':'${appPath}/resources/js/plugin/uploadify/uploadify.swf',
				'script':'${appPath}/commonContent/uploadFiles.action', //上传文件的后台处理程序的相对路径
				'cancelImg':'${appPath}/resources/js/plugin/uploadify/cancel.png',
	//			'queueSizeLimit':3, // 当允许多文件生成时，设置选择文件的个数，默认值：999 。 
				'queueID':'showfile',// 文件队列的ID，该ID与存放文件队列的div的ID一致。
 				'multi':true, // 设置为true时可以上传多个文件。
				'auto':false, // 设置为true当选择文件后就直接上传了，为false需要点击上传按钮才上传 。 
				'fileDataName':'file', // 和以下input的name属性一致
	//			'fileExt':'*.doc;*.pdf;*.zip;*.jpg;*.png;*.jpeg;',  // 设置可以选择的文件的类型，格式如：'*.doc;*.pdf;*.rar' 。 
	//			'fileDesc':'请选择zip,doc,pdf,jpg文件', //这个属性值必须设置fileExt属性后才有效，用来设置选择文件对话框中的提示文本，如设置fileDesc为“请选择rar doc pdf文件”
				'sizeLimit':104857600,//上传文件的大小限制 。这里为100M
				'simUploadLimit':3,// 允许同时上传的个数 默认值：1 。
			//	'buttonText':'浏览附件',// 浏览按钮的文本，默认值：BROWSE 。
				'buttonImg':'${appPath}/resources/images/50-1.png',// 浏览按钮的图片的路径 。
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
				} ,
				onError:function(event,queueId,fileObj,errorObj){//当上传过程中发生错误时触发。
					 var errorCode = errorObj.info;
		//			 alert(errorCode);
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
				} 
			});
	});
	
	function check(){
			// 设置表单中图标的裁切信息
	    	var cutterData = cutter.submit();
			if(cutterData.x!=undefined){ //说明提交了剪切图片
				if(cutterData){
					if(cutterData.x>0){
						$("#x").val(cutterData.x);
						$("#y").val(cutterData.y);
						$("#w").val(cutterData.w);
						$("#h").val(cutterData.h);
					}else{
						$.messager.show({
						title:'提示',
						msg:'图片横坐标应大于零，请重新截取'
					});
						return false;
					}
				}
		   	}
			
		$('#form').form('submit',{
			url:'${appPath}/commonContent/insert.action',
			success:function(result){
				var r = $.parseJSON(result);
				$.messager.show({
					title:'提示',
					msg:r.msg
				});
				if(r.status){
					
				setTimeout(function(){
					location.href='${appPath}/commonContent/manage.action';
				},1000);
				}
			}
		});
	}
	
	function reload(){
		location.href='${appPath}/commonContent/add.action';
	}
	
	
	function doUpload() {
	// 上传方法
	$.upload({
			// 上传地址
			url: '${appPath}/annex/uploadAvatar.action', 
			// 文件域名字
			fileName: 'picturefile', 
			// 其他表单数据
			params: {},
			// 上传完成后, 返回json, text
			dataType: 'json',
			// 上传之前回调,return true表示可继续上传
			onSend: function() {
					return true;
			},
			// 上传之后回调
			onComplate: function(data) {
				if(data[0].flag=='1'){
					alert(data[0].message);
				}else{
					var path = data[0].path;
					// 记录下原图路径
					$("#picturePath").val(path);
					// 原图重新加载
					cutter.reload("${appPath}/"+path);
					$("#preview").attr("src", "${appPath}/"+path);
				}
			}
	});
}
</script>
<div align="left" style="width:75%; height: 100%;">
<div id="div1" >当前位置：内容管理&nbsp;&gt;&nbsp;新增</div>
<div id="div2" align="right"><a id="btn" href="${appPath}/commonContent/manage.action" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">返回列表</a> </div>
<div align="left" >
	<table class="integrity_table" style="width: 100%">
		<tr>
			<td>
				<div>
					<form  id="form" method="post" enctype="multipart/form-data">
						<input type="hidden" name="pid" id="pid"/>
						<!-- 原新闻图标路径 -->
				        <input type="hidden" name="picturePath" id="picturePath"/>
				        <!-- 新闻图标裁切的数据 -->
				        <input type="hidden" name="x" id="x"/>
				        <input type="hidden" name="y" id="y"/>
				        <input type="hidden" name="w" id="w"/>
				        <input type="hidden" name="h" id="h"/>
						<table >
							<tr>
								<td class="tt">类别：</td>
								<td>
									<input  id="infoCateId" name="infoCateId" class="easyui-combotree" data-options="url:'${appPath}/infoCate/allInfoTree.action',parentField:'parentId',lines:true,cascadeCheck:false,checkbox:true,multiple:true,onlyLeafCheck:true,required:true" style="width:170px;" />
								</td>
							</tr>
							<tr>
								<td class="tt">标题：</td>
								<td><input name="title"  id="title"  class="easyui-validatebox" data-options="required:true" style="width: 680px;"/></td>
							</tr>
							<tr id="top" style="display: none;">
								<td class="tt">是否头条：</td>
								<td>
									<select class="" name="isTop" id="isTop">
										<option value="1">是</option>
										<option value="0" selected="selected">否</option>
									</select>
								</td>
							</tr>
							<tr>
								<td class="tt">图标：</td>
								<td>
									<!---缩略图-->
									<div id="preview">
								    	<img id="avatar" style="width: 60px; height: 60px;"
								    		src="${appPath}/avatar/avatar-small.png"
								    	 />&#160;<font color="#FF0000" size="2">(注意：请选择图片300×300像素)</font>
							    	</div>
									<input name="picturefile"  id="picturefile"  type="hidden"  style="width: 200px;"/>
									<input type="button" onclick="doUpload()" value="点击上传">
									<!--原始图-->
					                <div id="originalImgContainer" style="float:right;margin-right: 5px;">
					                	<!-- 文件上传列表 -->
					                	<div id="fileList" style="height:10px;"></div>
									</div>
								</td>
							</tr>
							<tr>
								<td class="tt">来源：</td>
								<td><input name="source"  id="source" style="width: 680px;"/></td>
							</tr>
							<tr>
								<td class="tt">摘要：</td>
								<td><input name="summary"  id="summary"  class="easyui-validatebox" data-options="required:true" style="width: 680px;"/></td>
							</tr>
							<tr>
								<td class="tt">内容：</td>
								<td>
									<textarea name="content" id="content"  rows="8" cols="60" style="width:680px;height:240px;visibility:hidden;"></textarea>
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
							<tr>
								<td class="tt">允许下载：</td>
								<td>
									<input  type="radio" class="cdown" name="cdownload" value="1" checked="checked"/>是&nbsp;&nbsp;&nbsp;<input  type="radio" class="cdown" name="cdownload" value="0" />否
								</td>
							</tr>
							<tr>
								<td class="tt">允许评论：</td>
								<td>
									<input  type="radio" class="cdown" name="isComment" value="1" checked="checked"/>是&nbsp;&nbsp;&nbsp;<input  type="radio" class="cdown" name="isComment" value="0" />否
								</td>
							</tr>
							<tr>
								<td class="tt">允许分享：</td>
								<td>
									<input  type="radio" class="cdown" name="isShare" value="1" checked="checked"/>是&nbsp;&nbsp;&nbsp;<input  type="radio" class="cdown" name="isShare" value="0" />否
								</td>
							</tr>
							<tr>
								<td class="tt">是否推送：</td>
								<td>
									<input  type="radio" class="cdown" name="isPush" value="1" checked="checked"/>是&nbsp;&nbsp;&nbsp;<input  type="radio" class="cdown" name="isPush" value="0" />否
								</td>
							</tr>
							<tr>
								<td class="tt">链接地址：</td>
								<td>
									<input name="href"   style="width: 680px;"/>
								</td>
							</tr>
							<tr>
								<td class="tt">存在附件：</td>
								<td>
									<select class="np" name="hasannex">
										<option value="1">是</option>
										<option value="0" selected="selected">否</option>
									</select>
								</td>
							</tr>
							<tr id="uploadFile">
								<th>选择附件：</th>
								<td><input type="file" name="annexFile"  id="annexFile"  style="width: 630px;"/></td>
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
