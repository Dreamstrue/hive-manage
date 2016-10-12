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
				url:'${appPath}/interview/insert.action',
				success:function(result){
					var r = $.parseJSON(result);
					if (r.status) {
						setTimeout(function(){
							location.href='${appPath}/interview/manage.action';
						},1000);
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
		location.href='${appPath}/interview/add.action';
	}
	
	function checkValue(){
		
		var csummary =  $('#csummary').val();
		if(!checkStr(csummary)){
			showMessage('概要内容不能为空');
			return false;
		}
		
		return true;
	}
</script>
<div align="left" style="width:70%; height: 100%;">
<div id="div1" >当前位置：名家访谈&nbsp;&gt;&nbsp;新增</div>
<div id="div2" align="right"><a id="btn" href="${appPath}/interview/manage.action" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true">返回访谈列表</a> </div>
<div align="left" >
	<table class="integrity_table" style="width: 100%">
		<tr>
			<td>
				<div>
					<form  id="form" method="post" enctype="multipart/form-data">
						<table >
							<tr>
								<th>访谈主题：</th>
								<td><input name="csubject"  id="csubject" class="easyui-validatebox" data-options="required:true"  style="width: 400px;"/></td>
							</tr>
							<tr id="topend">
								<th>访谈时间：</th>
								<td>
									<input type='text' name='cinterviewtime' id='cinterviewtime' class="easyui-validatebox" data-options="required:true" style='width:230px'  class='intxt' readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
								</td>
							</tr>
							<tr>
								<th>预计时长：</th>
								<td><input name="cduration"  id="content"  style="width: 400px;"/></td>
							</tr>
							<tr>
								<th>访谈地点：</th>
								<td><input name="caddress"  id="content"  style="width: 400px;"/></td>
							</tr>
							<tr>
								<th>访谈嘉宾：</th>
								<td><input name="cguests"  id="cguests" class="easyui-validatebox" data-options="required:true" style="width: 400px;"/></td>
							</tr>
							<tr>
								<th>概要内容：</th>
								<td>
									<textarea name="csummary" id="csummary"  rows="8" cols="60" style="width:680px;height:240px;visibility:hidden;"></textarea>
									<script type="text/javascript">
										KindEditor.ready(function(K) {
											var editor1 = K.create('textarea[name="csummary"]', {
												cssPath : '${appPath}/resources/js/kindeditor/plugins/code/prettify.css',
												uploadJson : '${appPath}/resources/js/kindeditor/jsp/upload_json_zxt.jsp',
												fileManagerJson : '${appPath}/resources/js/kindeditor/jsp/file_manager_json.jsp',
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
								<th>视频地址：</th>
								<td><input name="cvideopath"  id="cfrom" style="width: 400px;"/></td>
							</tr>
							<tr>
								<th>主题图片：</th>
								<td><input  type="file" name="imgfile"  id="imgfile" class="easyui-validatebox" data-options="required:true" style="width: 220px;"/>&#160;<font color="#FF0000" size="2">*必填项</font></td>
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
