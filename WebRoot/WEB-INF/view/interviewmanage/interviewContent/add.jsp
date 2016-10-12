<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<link rel="stylesheet" href="${appPath}/resources/css/info.css" type="text/css"></link>
<script type="text/javascript">
		$(function() {
			$('#checkFile').hide();
			$('.np').change(function(){
				var value = $('.np').val();
				if(value=='0'){
					$('#checkFile').hide();
				}else if(value='1'){
					$('#checkFile').show();
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
	});
	
	function check(){
		if(checkValue()){
			$('#form').form('submit',{
				url:'${pageContext.request.contextPath}/interviewContent/insert.action',
				success:function(result){
					var r = $.parseJSON(result);
					if (r.status) {
					/**
						setTimeout(function(){
							location.href='${pageContext.request.contextPath}/interviewContent/manage.action?interviewId=${vo.nintonlid}';
						},1000);
					*/
					KindEditor.instances[0].html(''); // 清空富文本编辑框内容
					}
					$.messager.show({
						title:'提示',
						msg:r.msg
					});
				}
			});
		}
	}
	
	function reload(){
		location.href='${pageContext.request.contextPath}/interview/add.action';
	}
	
	function checkValue(){
		/**
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
		*/
		
		return true;
	}
</script>
<div align="left" style="width:100%; height: 100%;">
<table>
<tr>
	<td style="width: 30%">
		
<div id="div1" >当前位置：名家访谈&nbsp;&gt;&nbsp;${vo.csubject}&nbsp;&gt;&nbsp;新增直播内容</div>
<div id="div2" align="right"><a id="btn" href="${pageContext.request.contextPath}/interviewContent/manage.action?interviewId=${vo.nintonlid}" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true">返回直播内容列表</a> </div>
<div align="left">
	<table class="integrity_table"  style="width: 100%">
		<tr>
			<td>
				<div>
					<form  id="form" method="post" enctype="multipart/form-data">
						<input type="hidden" name="nintonlid" id="id" value="${vo.nintonlid }">
						<table >
							<tr>
								<th style="width:5px;">直<br/>播<br/>内<br/>容<br/></th>
								<td>
									<textarea name="cdialoguecontent" id="cdetail"  rows="8" cols="10" style="width:670px;height:240px;visibility:hidden;"></textarea>
									<script type="text/javascript">
										KindEditor.ready(function(K) {
											var editor1 = K.create('textarea[name="cdialoguecontent"]', {
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
	</td>
	<td style="width: 70%">
		<table>
			<tr>
				<td>
					<iframe name="liveContent" src="${appPath}/interviewContent/liveContent.action?interviewId=${vo.nintonlid}" allowtransparency="true" style="background-color=transparent" title="test" frameborder="0" width="390" height="350" scrolling="true"></iframe>
				</td>
			</tr>
		</table>
	</td>
</tr>
	</table>
</div>
