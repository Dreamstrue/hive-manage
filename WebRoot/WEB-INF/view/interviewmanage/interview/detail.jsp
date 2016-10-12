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
		if(checkReportValue()){
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
	
	function approveAction(opType){
		if(checkReportValue()){
			$('#form').form('submit',{
				url:'${pageContext.request.contextPath}/interview/approveAction.action?id=${vo.nintonlid }&opType='+opType,
				success:function(result){
					var r = $.parseJSON(result);
					if (r.status) {
						setTimeout(function(){
							location.href='${pageContext.request.contextPath}/interview/manage.action';
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
</script>
<div align="left" style="width:70%; height: 100%;">
<c:if test="${opType=='detail' }">
<div id="div1" >当前位置：名家访谈&nbsp;&gt;&nbsp;${vo.csubject}&nbsp;&gt;&nbsp;详情</div>
</c:if>
<c:if test="${opType=='approve' }">
<div id="div1" >当前位置：名家访谈&nbsp;&gt;&nbsp;${vo.csubject}&nbsp;&gt;&nbsp;审核</div>
</c:if>
<div id="div2" align="right"><a id="btn" href="${pageContext.request.contextPath}/interview/manage.action" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true">返回访谈列表</a> </div>
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
								<td><input name="csubject"  id="title" value="${vo.csubject }" readonly="readonly" style="width: 400px;"/></td>
							</tr>
							<tr id="topend">
								<th>访谈时间：</th>
								<td>
									<input type='text' name='cinterviewtime' id='cinterviewtime' value="${vo.dinterviewtime }" style='width:230px'  class='intxt' readonly="readonly"   />
							</tr>
							<tr>
								<th>预计时长：</th>
								<td><input name="cduration"  id="content" value="${vo.cduration }" readonly="readonly"  style="width: 400px;"/></td>
							</tr>
							<tr>
								<th>访谈地点：</th>
								<td><input name="caddress"  id="content" value="${vo.caddress }" readonly="readonly"  style="width: 400px;"/></td>
							</tr>
							<tr>
								<th>访谈嘉宾：</th>
								<td><input name="cguests"  id="content" value="${vo.cguests }" readonly="readonly"  style="width: 400px;"/></td>
							</tr>
							<tr>
								<th>概要内容：</th>
								<td>
									<div style="width:680px;height:240px;border:1px solid silver;">${vo.csummary }</div>
								</td>
							</tr>
							<tr>
								<th>视频地址：</th>
								<td><input name="cvideopath"  id="cfrom" value="${vo.cvideopath }" readonly="readonly" style="width: 400px;"/></td>
							</tr>
							<tr>
								<th>专题图片：</th>
								<td>
									<span>
										<c:if test="${!empty annex}">
											<c:forEach items="${annex}" var="t" varStatus="status">
											<div id="${t.id}">
											<a href="${appPath}/annex/download.action?id=${t.id}">${t.cfilename}</a>
											</div>
											</c:forEach>
										</c:if>
									</span>
								</td>
							</tr>
						<c:if test="${vo.cauditstatus != '0' }">
							<tr>
								<th>审核意见：</th>
								<td>
									<c:if test="${opType=='detail' }">
										<div style="width:400px;height:60px;border:1px solid silver;">${vo.cauditopinion }</div>
									</c:if>
								</td>
							</tr>
						</c:if>
						<c:if test="${opType=='approve' }">
							<tr>
							<th>审核意见：</th>
								<td>
									<textarea name="auditOpinion" id="auditOpinion"  style="width:400px;"></textarea>
								</td>
							</tr>
						</c:if>
						<c:if test="${vo.cauditstatus == '1' && liveContent != ''}">
							<tr>
								<th style="width:75px;">直播内容：</th>
								<td>
									<div style="width:700px;border:1px solid silver;word-wrap: break-word; word-break: normal;">${liveContent }</div>
								</td>
							</tr>
						</c:if>
					</table>
						<c:if test="${opType=='approve' }">
							<p align="right" style="padding-right: 70px;">
								<input type="button" value="通过" onclick="approveAction('success')"/>
								<input type="reset" value="不通过" onclick="approveAction('error')" />
				   			</p>
						</c:if>
					</form>
				</div>
			</td>
		</tr>
	</table>
</div>
</div>
