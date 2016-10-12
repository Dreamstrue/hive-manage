<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/integrity.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/uploadify/uploadify.css" type="text/css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/uploadify/jquery.uploadify.v2.1.4.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/uploadify/swfobject.js"></script>
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
			
			var ctop = $('.top').val();
			if(ctop == '0'){
				$('#topend').hide();
			}
			
			$('.top').change(function(){
				var value = $('.top').val();
				if(value=='0'){
					$('#topend').hide();
				}else if(value='1'){
					$('#topend').show();
				}			
			});
			
			
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
		if(checkValue()){
			$('#ids').val(ids);
			$('#form').form('submit',{
				url:'${pageContext.request.contextPath}/notice/update.action',
				success:function(result){
					var r = $.parseJSON(result);
					$.messager.show({
						title:'提示',
						msg:r.msg
					});
					setTimeout(function(){
						location.href='${pageContext.request.contextPath}/notice/manage.action';
					},1000);
				}
			});
		}
	}
	
	
	
	function approveAction(){
		if(checkValue()){
			$('#ids').val(ids);
			$('#form').form('submit',{
				url:'${pageContext.request.contextPath}/notice/approveAction.action',
				success:function(result){
					var r = $.parseJSON(result);
					$.messager.show({
						title:'提示',
						msg:r.msg
					});
					setTimeout(function(){
						location.href='${pageContext.request.contextPath}/notice/manage.action';
					},1000);
				}
			});
		}
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
<div id="div1" >当前位置：通知公告&nbsp;&gt;&nbsp;修改</div>
</c:if>
<c:if test="${opType=='approve' }">
<div id="div1" >当前位置：通知公告&nbsp;&gt;&nbsp;审核</div>
</c:if>
<div id="div2" align="right"><a id="btn" href="${pageContext.request.contextPath}/notice/manage.action" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">返回列表</a> </div>
<hr style="color: silver;border-style: solid;"/>
<div align="left" style="width: 100%">
	<table class="integrity_table">
		<tr>
			<td>
				<div>
					<form  id="form" method="post" enctype="multipart/form-data">
						<input type="hidden" name="id" id="id" value="${vo.id }">
						<!-- 此处定义的ids用来保存 被删除的附件的id -->
						<input type="hidden" name="ids" id="ids">
						<table >
							<tr>
								<th>公告主题：</th>
								<td><input name="title"  id="title" value="${vo.title }" style="width: 620px;"/>&#160;<font color="#FF0000" size="2">*必填项</font></td>
							</tr>
							<tr>
								<th>公告概要：</th>
								<td><input name="content"  id="content" value="${vo.content }" style="width: 680px;"/></td>
							</tr>
							<tr>
								<th>公告类别：</th>
								<td>
									<select class="cate" name="ccategory">
										<c:if test="${vo.ccategory=='1'}">
											<option value="1" selected="selected">列表展示</option>
											<option value="0">否</option>
										</c:if>
										<c:if test="${vo.ccategory=='0'}">
											<option value="1">是</option>
											<option value="0"  selected="selected">浮动窗口</option>
										</c:if>
									</select>
								</td>
							</tr>
							<tr>
								<th>重要标志：</th>
								<td>
									<c:if test="${vo.cimportant=='1'}">
									<input type="radio" class="sign" name="cimportant" value="1"  checked="checked">是&nbsp;&nbsp;<input type="radio" class="sign" name="cimportant" value="0">否
									</c:if>
									<c:if test="${vo.cimportant=='0'}">
									<input type="radio" class="sign" name="cimportant" value="1">是&nbsp;&nbsp;<input type="radio" class="sign" name="cimportant" value="0" checked="checked">否
									</c:if>
									<%-- <select class="sign" name="cimportant">
										<c:if test="${vo.cimportant=='1'}">
											<option value="1" selected="selected">是</option>
											<option value="0">否</option>
										</c:if>
										<c:if test="${vo.cimportant=='0'}">
											<option value="1">是</option>
											<option value="0"  selected="selected">否</option>
										</c:if>
									</select> --%>
								</td>
							</tr>
							<tr>
								<th>是否置顶：</th>
								<td>
									<select class="top" name="ctop">
										<c:if test="${vo.ctop=='1'}">
											<option value="1" selected="selected">是</option>
											<option value="0">否</option>
										</c:if>
										<c:if test="${vo.ctop=='0'}">
											<option value="1">是</option>
											<option value="0"  selected="selected">否</option>
										</c:if>
									</select>
								</td>
							</tr>
							<tr id="topend">
								<th>置顶有效期止：</th>
								<td>
									<input type='text' name='dtopend' id='dtopend' value="${vo.dtopend }" style='width:230px'  class='intxt' readonly="readonly" onfocus="WdatePicker({minDate:'%y-%M-{%d}'})"  />
								</td>
							</tr>
							<tr>
								<th>信息来源：</th>
								<td><input name="cfrom"  id="cfrom" value="${vo.cfrom }" style="width: 680px;"/></td>
							</tr>
							<tr>
								<th>公告内容：</th>
								<td>
									<textarea name="cdetail" id="cdetail"  rows="8" cols="60" style="width:680px;height:240px;visibility:hidden;">${vo.cdetail }</textarea>
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
									<input type='text' name='dvalidbegin' id='dvalidbegin' value="${vo.dvalidbegin }"  style='width:230px'  class='intxt' readonly="readonly" onFocus="WdatePicker({minDate:'%y-%M-{%d}'})"/>&#160;<font color="#FF0000" size="2">*必填项</font>
								</td>
							</tr>
							<tr>
								<th>有效期止：</th>
								<td>
									<input type='text' name='dvalidend' id='dvalidend' value="${vo.dvalidend }" style='width:230px'  class='intxt' readonly="readonly"  onFocus="WdatePicker({minDate:'#F{$dp.$D(\'dvalidbegin\')}'})"  />
								</td>
							</tr>
							<tr>
								<th>链接地址：</th>
								<td>
									<input name="chref"   value="${vo.chref}"  style="width: 680px;"/>
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
										<c:if test="${vo.chasannex=='0'}">
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
									<input type="hidden" id="idString" name="idString" />
									<div style="font-size: 12px;">
								      <a href="javascript:$('#file').uploadifyUpload()">上传</a>| 
								      <a href="javascript:$('#file').uploadifyClearQueue()">取消上传</a>
								    </div>
								</td>
							</tr>
							<%-- <tr id="checkFile">
								<th>附件：</th>
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
									<textarea name="auditOpinion" id="auditOpinion"  style="width:680px;"></textarea>
								</td>
							</tr>
							</c:if>
							<c:if test="${opType=='update' }">
							<c:if test="${vo.cauditstatus=='2' }">
							<tr>
								<th><font color="red">审核意见：</font></th>
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
