<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:include page="../../common/inc.jsp"></jsp:include>
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
			selector : {width:120,height:120}
		});
		
		$(function() {
			$('#fileExist').val('yes'); 
			var chasannex = $('.np').val();
			if(chasannex == '0'){
				$('#uploadFile').hide();
			} else {
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
			
		var idStr = new Array;	
		$('#file').uploadify({
				'uploader':'${appPath}/resources/js/plugin/uploadify/uploadify.swf',
				'script':'${appPath}/news/uploadFiles.action', //上传文件的后台处理程序的相对路径
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
		   	//alert(ids);
			//$('#ids').val(ids);return false;
			$('#form').form('submit',{
				url:'${appPath}/news/update.action',
				success:function(result){
					var r = $.parseJSON(result);
					$.messager.show({
						title:'提示',
						msg:r.msg
					});
					setTimeout(function(){
						location.href='${appPath}/news/manage.action';
					},1000);
				}
			});
	}
	
	
	
	function approveAction(){
			$('#ids').val(ids);
			$('#form').form('submit',{
				url:'${appPath}/news/approveAction.action',
				success:function(result){
					var r = $.parseJSON(result);
					$.messager.show({
						title:'提示',
						msg:r.msg
					});
					setTimeout(function(){
						location.href='${appPath}/news/manage.action';
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
			}
		});
	}

	function download(id){
	//	location.href='${appPath}/annex/download.action?id='+id;
		window.open("${appPath}/annex/download.action?id="+id,"_blank");
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
<c:if test="${opType=='update' }">
<div id="div1" >当前位置：新闻资讯&nbsp;&gt;&nbsp;修改</div>
</c:if>
<c:if test="${opType=='approve' }">
<div id="div1" >当前位置：新闻资讯&nbsp;&gt;&nbsp;审核</div>
</c:if>
<div id="div2" align="right"><a id="btn" href="${appPath}/news/manage.action" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">返回列表</a> </div>
<div align="left" style="width:100%;">
	<table class="integrity_table" style="width:100%;">
		<tr>
			<td>
				<div>
					<form  id="form" method="post" enctype="multipart/form-data">
						<input type="hidden" name="id" id="id" value="${vo.id }">
						<!-- 此处定义的ids用来保存 被删除的附件的id -->
						<input type="hidden" name="ids" id="ids">
						<!-- 自定义一个字段，方便后台操作附件修改  -->
						<input type="hidden" name="fileExist" id="fileExist"/>
						<!-- 原新闻图标路径 -->
				        <input type="hidden" name="picturePath" id="picturePath" value="${vo.picturePath}"/>
				        <!-- 新闻图标裁切的数据 -->
				        <input type="hidden" name="x" id="x"/>
				        <input type="hidden" name="y" id="y"/>
				        <input type="hidden" name="w" id="w"/>
				        <input type="hidden" name="h" id="h"/>
						<table >
							<tr>
								<td class="tt">新闻标题：</td>
								<td><input name="title"  id="title" value="${vo.title }" class="easyui-validatebox" data-options="required:true" style="width: 680px;"/></td>
							</tr>
							<tr style="display: none;">
								<td class="tt">信息分类：</td>
								<td>
									<input  id="infoCateId" name="infoCateId" value="${vo.infoCateId}" class="easyui-combotree" data-options="url:'${appPath}/infoCate/allInfoTree.action',parentField:'parentId',lines:true,required:true " style="width:170px;" />
								</td>
							</tr>
							<tr>
								<td class="tt">新闻图标：</td>
								<td>
									<!---缩略图-->
									<div id="preview">
								    	<img id="avatar" style="width: 60px; height: 60px;"
								    		<c:choose>
								    			<c:when test="${empty vo.picturePath}"> src="${appPath}/avatar/avatar-small.png" </c:when>
								    			<c:otherwise> src="${appPath}/news/loadPic.action?picPath=${vo.picturePath}"</c:otherwise>  
								    		</c:choose>
								    	 />&#160;<font color="#FF0000" size="2">(注意：请选择图片200×200像素)</font>
							    	</div>
							    	<!-- 为了操作的方便，在修改的过程中可以重新上传图片，而审核的时候就不用了 -->
							    	<c:if test="${opType=='update' }">  
										<input name="picturefile"  id="picturefile"  type="hidden"  style="width: 200px;"/>
							    	</c:if>
							    	<input type="button" onclick="doUpload()" value="点击上传">
									<!--原始图-->
					                <div id="originalImgContainer" style="float:right;margin-right: 5px;">
					                	<!-- 文件上传列表 -->
					                	<div id="fileList" style="height:10px;"></div>
									</div>
								</td>
							</tr>
							<tr>
								<td class="tt">信息来源：</td>
								<td><input name="source"  id="source" value="${vo.source}" style="width: 680px;"/></td>
							</tr>
							<tr>
								<td class="tt">新闻摘要：</td>
								<td><input name="summary"  id="summary"  value="${vo.summary}" class="easyui-validatebox" data-options="required:true" style="width: 680px;"/></td>
							</tr>
							<tr>
								<td class="tt">新闻内容：</td>
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
								<td class="tt">链接地址：</td>
								<td>
									<input name="href"   value="${vo.href}"  style="width: 680px;"/>
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
