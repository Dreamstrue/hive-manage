<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.form.js"></script>

<div align="center" style="width: 100%">
	<form id="sn_manage_add_form" method="post" style="width: 100%">
		<table class="tableForm" style="width: 100%" id="tab">
			<tr>
				<th style="width: 100px;">生成批次</th>
				<td>${nyr}**
					<input id="nyr" name="nyr" value="${nyr}" type="hidden" />
					<input id="status" name="status" value="0" type="hidden" />
				</td>
			</tr>
			<tr>
				<th style="width: 100px;">批次数量</th>
				<td>
					<input id="batchCount" name="batchCount" class="easyui-numberbox" data-options="required:true" style="width: 75px;" /> 批
				</td>
			</tr>
			<tr>
				<th style="width: 100px;">生成数量</th>
				<td>
					<input id="nNumber" name="nNumber" class="easyui-numberbox" data-options="required:true" style="width: 75px;" /> 张（每批的数量）
				</td>
			</tr>
			<tr><th>&nbsp;</th><td>&nbsp;</td></tr>
			<tr>
				<th style="width: 100px;text-align:left;" colspan="2"><font color="red">&nbsp;提示：
					<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由于生产的数量比较大，页面等待的时间可能有点长，请耐心等待！！！
					<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;建议SN生成数量尽量保持在100000之内！</font>
				</th>
			</tr>
		</table>
	</form>
</div>