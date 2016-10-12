<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div align="center" style="width: 100%">
	<form id="pushinfo_manage_edit_form" method="post" style="width: 100%">
		<input type="hidden" name="id" value="${vo.id }"/>
		<input type="hidden" name="createId" value="${vo.createId }"/>
		<input type="hidden" name="createTimeStr" value="${createTimeStr }"/>
		<input type="hidden" name="valid" value="${vo.valid }"/>
		<input type="hidden" name="objectType" value="${vo.objectType }"/>
		<input type="hidden" name="objectId" value="${vo.objectId }"/>
		<table class="tableForm" style="width: 100%">
			<tr>
				<th>标题</th>
				<td>
					<input name="title" value="${vo.title }" class="easyui-validatebox" data-options="required:true"  style="width: 170px;"/>
				</td>
			</tr>
			<tr>
				<th>描述</th>
				<td>
					<input name="description" value="${vo.description }" class="easyui-validatebox" data-options="required:true"  style="width: 170px;"/>
				</td>
			</tr>
			<tr>
				<th>链接地址</th>
				<td>
					<input name="url" value="${vo.url }" style="width: 170px;"/>
				</td>
			</tr>
			<tr>
				<th>消息打开方式</th>
				<td>
					<select name="openType" style="width: 170px;">
						<option value="0" <c:if test='${vo.openType == "0"}'>selected="selected"</c:if>>在应用中打开</option>
						<option value="1" <c:if test='${vo.openType == "1"}'>selected="selected"</c:if>>在浏览器中打开</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>接收对象</th>
				<td>
					<select name="receiveType" id="sendObject" style="width: 170px;">
						<option value="0" <c:if test='${vo.receiveType == "0"}'>selected="selected"</c:if>>全部</option>
						<option value="1" <c:if test='${vo.receiveType == "1"}'>selected="selected"</c:if>>个人版</option>
						<option value="2" <c:if test='${vo.receiveType == "2"}'>selected="selected"</c:if>>企业版</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>是否定时推送</th>
				<td style="font-size: 12px;">
					<input type="radio" name="isTime" onclick="displayPushTime(1)" class="easyui-validatebox" data-options="required:true" value="1" <c:if test='${vo.isTime == "1"}'>checked="checked"</c:if>/>是
					<input type="radio" name="isTime" onclick="displayPushTime(0)" class="easyui-validatebox" data-options="required:true" value="0" <c:if test='${vo.isTime == "0"}'>checked="checked"</c:if>/>否
				</td>
			</tr>
			<tr id="pushTime" <c:if test='${vo.isTime == "0"}'>style="display:none;"</c:if>>
				<th>推送时间</th>
				<td>
					<input class="inval" type='text' name='pushTimeStr' value="${vo.pushTime }" id='pushTimeStr' style='width:168px'  class='intxt' readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d %H:%m:%s'})"  />
				</td>
			</tr>
		</table>
	</form>
	
	<script type="text/javascript">
		// 去掉时间最后面的 .0
		var pushTimeStr = $("#pushTimeStr").val();
		$("#pushTimeStr").val(pushTimeStr.substring(0, pushTimeStr.length - 2));
		
		// 显示/隐藏最多最少设置
		function displayPushTime(flag) {
			if (flag == 0)
				$("#pushTime").css("display","none");
			else if (flag == 1)
				$("#pushTime").css("display","");
		}
	</script>
</div>