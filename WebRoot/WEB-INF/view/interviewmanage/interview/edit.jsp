<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<link rel="stylesheet" href="${appPath}/resources/css/info.css" type="text/css"></link>
<script type="text/javascript">
		$(function() {
			//修改时，新闻图片的选择框隐藏，当对应的图片被删除后，在出现。
			$('#picImg').hide();
			$('#imageExist').val('yes');
		
			// 把访谈时间最后面多出的 .0 截掉
			var cinterviewtime = $("#cinterviewtime").val(); // 形如：2013-11-07 19:41:46.0
			$("#cinterviewtime").val(cinterviewtime.substring(0, cinterviewtime.indexOf(".0")));
		
			var chasannex = $('.np').val();
			if(chasannex == '0'){
				$('#checkFile').hide();
			}
			
			$('.np').change(function(){
				var value = $('.np').val();
				if(value=='0'){
					$.messager.confirm('提示','该操作将删除您已选择的所有附件，是否继续?',function(b){
						if(b){
							$('#checkFile').hide();
						}else $('.np').val('1');
					});
				}else if(value='1'){
					$('#checkFile').show();
				}			
			});
			
	});
	var ids = new Array;
		
	function check(){
		if(checkValue()){
			$('#ids').val(ids);
			$('#form').form('submit',{
				url:'${pageContext.request.contextPath}/interview/update.action',
				success:function(result){
					var r = $.parseJSON(result);
					$.messager.show({
						title:'提示',
						msg:r.msg
					});
					if(r.status){
						setTimeout(function(){
							location.href='${pageContext.request.contextPath}/interview/manage.action';
						},1000);
					}
				}
			});
		}
	}
	
	function approveAction(type){
		if(checkReportValue()){
			$('#ids').val(ids);
			$('#form').form('submit',{
				url:'${pageContext.request.contextPath}/interview/approveAction.action?type='+type,
				success:function(result){
					var r = $.parseJSON(result);
					$.messager.show({
						title:'提示',
						msg:r.msg
					});
					setTimeout(function(){
						location.href='${pageContext.request.contextPath}/interview/manage.action';
					},1000);
				}
			});
		}
	}
	
	
	function errorAction(){
			var id = $('#id').val();
			$('#form').form('submit',{
				url:'${pageContext.request.contextPath}/interview/errorAction.action?id='+id,
				success:function(result){
					var r = $.parseJSON(result);
					$.messager.show({
						title:'提示',
						msg:r.msg
					});
					setTimeout(function(){
						location.href='${pageContext.request.contextPath}/interview/manage.action';
					},1000);
				}
			});
	}
	
	function checkReportValue(){
		var title =  $('#title').val();
		if(!checkStr(title)){
			showMessage('新闻标题不能为空');
			return false;
		}
		return true;
	}
	
	//删除附件的公共方法
	
	function delAnnex(id, type){
		$.messager.confirm('提示','您确定要删除该附件?',function(b){
			if(b){
				$('#'+id).hide();
				if(type=='image'){
					$('#picImg').show();
					$('#imageExist').val('no');
				}
				/* var url = '${appPath}/annex/delete.action';
							$.post(url,{id:id},function(data){
							},"json"); */
			}
		});
	}
	
	function checkValue(){
		var csubject =  $('#csubject').val();
		if(!checkStr(csubject)){
			showMessage('访谈主题不能为空');
			return false;
		}
		
		var cinterviewtime =  $('#cinterviewtime').val();
		if(!checkStr(cinterviewtime)){
			showMessage('访谈时间不能为空');
			return false;
		}
		
		var cguests =  $('#cguests').val();
		if(!checkStr(cguests)){
			showMessage('访谈嘉宾不能为空');
			return false;
		}
		
		var csummary =  $('#csummary').val();
		if(!checkStr(csummary)){
			showMessage('概要内容不能为空');
			return false;
		}
		
		var imgfile =  $('#imgfile').val();
		if(!checkStr(imgfile)){
			showMessage('主题图片不能为空');
			return false;
		}
		
		return true;
	}
</script>
<div align="left" style="width:70%; height: 100%;">
<div id="div1" >当前位置：名家访谈&nbsp;&gt;&nbsp;修改</div>
<div id="div2" align="right"><a id="btn" href="${pageContext.request.contextPath}/interview/manage.action" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">返回列表</a> </div>
<div align="left" >
	<table class="integrity_table" style="width: 100%">
		<tr>
			<td>
				<div>
					<form  id="form" method="post" enctype="multipart/form-data">
					<input type="hidden" name="nintonlid" id="id" value="${vo.nintonlid }">
					<!-- 此处定义的ids用来保存 被删除的附件的id -->
					<input type="hidden" name="ids" id="ids">
						<table >
							<tr>
								<th>访谈主题：</th>
								<td><input name="csubject"  id="csubject" value="${vo.csubject }"  style="width: 400px;"/>&#160;<font color="#FF0000" size="2">*必填项</font></td>
							</tr>
							<tr id="topend">
								<th>访谈时间：</th>
								<td>
									<input type='text' name='cinterviewtime' id='cinterviewtime' value="${vo.dinterviewtime }" style='width:230px'  class='intxt' readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
								<font color="#FF0000" size="2">*必填项</font></td>
							</tr>
							<tr>
								<th>预计时长：</th>
								<td><input name="cduration"  id="content" value="${vo.cduration }"  style="width: 400px;"/></td>
							</tr>
							<tr>
								<th>访谈地点：</th>
								<td><input name="caddress"  id="content" value="${vo.caddress }"  style="width: 400px;"/></td>
							</tr>
							<tr>
								<th>访谈嘉宾：</th>
								<td><input name="cguests"  id="cguests" value="${vo.cguests }"  style="width: 400px;"/><font color="#FF0000" size="2">*必填项</font></td>
							</tr>
							<tr>
								<th>概要内容：</th>
								<td>
									<textarea name="csummary" id="csummary"  rows="8" cols="60" style="width:680px;height:240px;visibility:hidden;">${vo.csummary }</textarea>
									<script type="text/javascript">
										KindEditor.ready(function(K) {
											var editor1 = K.create('textarea[name="csummary"]', {
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
								<th>视频地址：</th>
								<td><input name="cvideopath"  id="cfrom" value="${vo.cvideopath }" style="width: 400px;"/></td>
							</tr>
							<tr>
								<th>专题图片：</th>
								<td>
									<div id="picImg">
										<input  type="file" name="imgfile"  id="imgfile"  style="width: 250px;"/>&#160;<font color="#FF0000" size="2">*必填项</font>
									</div>
									<div id="${img.id}">
											<a href="${appPath}/annex/download.action?id=${img.id}">${img.cfilename}</a>&nbsp;&nbsp;<input type="button" value="删除" onclick="delAnnex(${img.id},'image')">
											<!-- 定义一个字段保存图片的ID,当新上传图片时，通过这个ID把原来的图片设置为不可用 -->
											<input type="hidden" name="virtualPid" id="virtualPid" value="${img.id}"/><font color="#FF0000" size="2">*必填项</font>
									</div>
									<!-- 自定义一个字段，方便后台操作新闻图片修改  -->
									<input type="hidden" name="imageExist" id="imageExist"/>
								</td>
							</tr>
						</table>
						<p align="right" style="padding-right: 70px;">
							<input type="button" value="提交" onclick="check()"/>
			   			</p>
					</form>
				</div>
			</td>
		</tr>
	</table>
</div>
</div>
