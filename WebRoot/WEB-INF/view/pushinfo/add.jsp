<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
	
</script>
<div align="center" style="width: 100%">
	<form id="pushinfo_manage_add_form" method="post" style="width: 100%">
		<input type="hidden" name="objectType" value="${objectType }"/>
		<input type="hidden" name="objectId" value="${objectId }"/>
		<table class="tableForm" style="width: 100%">
			<tr>
				<th>标题</th>
				<td>
					<input name="title" class="easyui-validatebox" data-options="required:true"  style="width: 170px;"/>
				</td>
			</tr>
			<tr>
				<th>描述</th>
				<td>
					<input name="description" class="easyui-validatebox" data-options="required:true"  style="width: 170px;"/>
				</td>
			</tr>
			<tr>
				<th>链接地址</th>
				<td>
					<input name="url"  style="width: 170px;"/>
				</td>
			</tr>
			<tr>
				<th>消息打开方式</th>
				<td>
					<select name="openType" style="width: 170px;">
						<option value="0" selected="selected">在应用中打开</option>
						<option value="1">在浏览器中打开</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>接收对象</th>
				<td>
					<select name="receiveType" id="sendObject" style="width: 170px;">
						<option value="0" selected="selected">全部</option>
						<option value="1">个人版</option>
						<option value="2">企业版</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>是否定时推送</th>
				<td style="font-size: 12px;">
					<input type="radio" name="isTime" onclick="displayPushTime(1)" class="easyui-validatebox" data-options="required:true" value="1"/>是
					<input type="radio" name="isTime" onclick="displayPushTime(0)" class="easyui-validatebox" data-options="required:true" value="0" checked="checked"/>否
				</td>
			</tr>
			<tr id="pushTime" style="display:none;">
				<th>推送时间</th>
				<td>
					<input class="inval" type='text' name='pushTimeStr' id='pushTimeStr' style='width:168px'  class='intxt' readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d %H:%m:%s'})"  />
				</td>
			</tr>
		</table>
	</form>
	
	<script type="text/javascript">
		// 显示/隐藏最多最少设置
		function displayPushTime(flag) {
			if (flag == 0)
				$("#pushTime").css("display","none");
			else if (flag == 1)
				$("#pushTime").css("display","");
		}
	</script>
</div>