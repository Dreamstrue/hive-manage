<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<link rel="stylesheet" href="${appPath}/resources/css/info.css" type="text/css"></link>
<link rel="stylesheet" href="${appPath}/resources/js/plugin/uploadify/uploadify.css" type="text/css"></link>
<script type="text/javascript" src="${appPath}/resources/js/plugin/uploadify/jquery.uploadify.v2.1.4.js"></script>
<script type="text/javascript" src="${appPath}/resources/js/plugin/uploadify/swfobject.js"></script>
<script type="text/javascript">
		$(function() {
			var id = '${vo.naboutusid}';
			if(id == null || id==''){
		//		$('#checkFile').hide();
				$('#uploadFile').hide();
			}
			
			var value = $('.np').val();
			if(value=='0'){
			//	$('#checkFile').hide();
				$('#uploadFile').hide();
			}
			
			$('.np').change(function(){
				var value = $('.np').val();
				if(value=='0'){
			//		$('#checkFile').hide();
					$('#uploadFile').hide();
				}else if(value='1'){
			//		$('#checkFile').show();
					$('#uploadFile').show();
				}			
			});
			
			var idStr = new Array;	
		$('#file').uploadify({
				'script':'${appPath}/aboutus/uploadFiles.action', //上传文件的后台处理程序的相对路径
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
	});
		
	var ids = new Array;	
	
	function check(){
		if(checkValue()){
			$('#form').form('submit',{
				url:'${appPath}/aboutus/insert.action',
				success:function(result){
					var r = $.parseJSON(result);
					$.messager.show({
						title:'提示',
						msg:r.msg
					});
					if(r.status){
						setTimeout(function(){
							location.href='${appPath}/aboutus/constitution.action?id=${menuid}';
						},1000);
					}
					
				}
			});
		}
	}
	
	function reload(){
		location.href='${appPath}/aboutus/constitution.action?id=${menuid}';
	}
	
	function checkValue(){
		var title =  $('#title').val();
		if(!checkStr(title)){
			showMessage('标题不能为空');
			return false;
		}
		return true;
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
	
	function update(){
		if(checkValue()){
			$('#ids').val(ids);
			$('#form').form('submit',{
				url:'${appPath}/aboutus/update.action',
				success:function(result){
					var r = $.parseJSON(result);
					$.messager.show({
						title:'提示',
						msg:r.msg
					});
					setTimeout(function(){
						location.href='${appPath}/aboutus/constitution.action?id=${menuid}';
					},1000);
				}
			});
		}
	}
</script>
<div align="left" style="width:70%; height: 100%;">
<div align="left" >
	<table class="integrity_table" style="width: 100%">
		<tr>
			<td>
				<div>
					<form  id="form" method="post" enctype="multipart/form-data">
					<c:if test="${vo.naboutusid!=null}">
						<input type="hidden" name="naboutusid" value="${vo.naboutusid}"/>
						<input type="hidden" name="ids" id="ids"/>
					</c:if>
					<input type="hidden" name="nmenuid" value="${menuid}"/>
						<table >
							<tr>
								<th>标题：</th>
								<td><input name="ctitle"  id="title"  value="${vo.ctitle }" style="width: 680px;" class="easyui-validatebox" data-options="required:true"/></td>
							</tr>
							<tr>
								<th>协会章程：</th>
								<td>
									<textarea name="ccontent" id="content"  rows="12" cols="60" style="width:680px;height:340px;visibility:hidden;">${vo.ccontent }</textarea>
									<script type="text/javascript">
										KindEditor.ready(function(K) {
											var editor1 = K.create('textarea[name="ccontent"]', {
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
								<th>存在附件：</th>
								<td>
									<select class="np" name="chasannex">
										<c:if test="${vo.chasannex=='1'}">
											<option value="1" selected="selected">是</option>
											<option value="0">否</option>
										</c:if>
										<c:if test="${vo.chasannex=='0' || vo.chasannex==null}">
											<option value="1">是</option>
											<option value="0"  selected="selected">否</option>
										</c:if>
									</select>
								</td>
							</tr>
							<tr id="uploadFile">
								<th>选择附件：</th>
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
									<input type="hidden" id="idString" name="idString"  />
									<div style="font-size: 12px;">
								      <a href="javascript:$('#file').uploadifyUpload()">上传</a>| 
								      <a href="javascript:$('#file').uploadifyClearQueue()">取消上传</a>
								    </div>
								</td>
							</tr>
							<%-- <tr id="checkFile">
								<th>选择附件：</th>
								<td>
									<input type="button" name="button" value="添加附件" onclick="addInput()">
									<input type="button" name="button" value="删除附件" onclick="deleteInput()"><br/>
									<span>
										<c:if test="${!empty annex}">
											<c:forEach items="${annex}" var="t" varStatus="status">
											<div id="${t.id}">
											<a href="${appPath}/annex/download.action?id=${t.id}">${t.cfilename}</a>&nbsp;&nbsp;<input type="button" value="删除" onclick="delAnnex(${t.id})">
											</div>
											</c:forEach>
										</c:if>
									</span>
									<span id="upload"></span>
								</td>
							</tr> --%>
						</table>
						<c:if test="${vo.naboutusid!=null }">
							<p align="right" style="padding-right: 70px;">
								<input type="button" value="修改" onclick="update(${menuid})"/>
				   			</p>
						</c:if>
						<c:if test="${vo.naboutusid==null }">
							<p align="right" style="padding-right: 70px;">
								<input type="button" value="提交" onclick="check()"/>
								<input type="button" value="重置" onclick="reload()" />
				   			</p>
						</c:if>
						
					</form>
				</div>
			</td>
		</tr>
	</table>
</div>
</div>
