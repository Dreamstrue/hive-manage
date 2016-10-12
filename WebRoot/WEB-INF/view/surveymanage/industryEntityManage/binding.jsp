<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
	$(function() {   
		 $("#cleanBtn").click(function () {
			 $("#surveyId").combobox("unselect",'${vo.surveyId}');
		 });
	});
</script>
<div align="center" style="width: 100%">
	<form id="sn_manage_add_form" method="post" style="width: 100%">
		<table class="tableForm" style="width: 100%" id="tab">
			<input type="hidden" id="id" name="id" value="${vo.id }">
			<tr>
				<th>绑定问卷</th>
				<td>
					<input id="surveyId" name="surveyId"  value="${vo.surveyId}" class="easyui-combobox" data-options="url:'${pageContext.request.contextPath}/surveyManage/getSurveyByindustryEntity.action?industryEntityId=${vo.id}',textField:'subject',valueField:'id',editable:false" style="width:230px;" />
					<a id="cleanBtn" class="easyui-linkbutton" data-options="plain: true,iconCls:'icon-clear'"></a>
				</td>
			</tr>
			<tr>
				<th style="width: 100px;text-align:left;" colspan="2"><font color="#00CED1">提示：
					<br>&nbsp;&nbsp;绑定或者修改绑定问卷为该实体绑定一个问卷信息！
				</th>
			</tr>
		</table>
	</form>
</div>