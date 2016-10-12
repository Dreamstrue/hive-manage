<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.form.js"></script>
    
<script type="text/javascript">    
	function onccSelect(){
		var industryEntityId = $('#industryEntityId').combobox('getValue');
		var url = '${pageContext.request.contextPath}/surveyManage/getSurveyByindustryEntity.action?industryEntityId='+industryEntityId;
		$('#subjectId').combobox('reload',url);
// 		$("#subjectId").combobox("setValue",'请选择');
	}
</script> 

<div align="center" style="width: 100%">
	<form id="sn_manage_bind_form" method="post" style="width: 100%">
	<table class="tableForm" style="width: 100%" id="tab">
		<tr>
			<th style="width: 100px;">生成批次</th>
			<td>${vo.batch}
			</td>
		</tr>
			<tr>
			<th>行业实体</th>
			<td>
				<input id="industryEntityId" name="industryEntityId" class="easyui-combobox" data-options="url:'${pageContext.request.contextPath}/industryEntityManage/getAllIndustryEntityList.action',mode:'remote',valueField:'id',textField:'entityName',required:true,onSelect:onccSelect" style="width:150px;" />
			</td>
		</tr>
		
		</tr>
			<tr>
			<th>评价问卷</th>
			<td>
				<input id="subjectId" name="subjectId" class="easyui-combobox" data-options="textField:'subject',valueField:'id',multiple:false,editable:false,required:true" style="width:150px;" />
			</td>
		</tr>
		
		<tr>
			<th style="width: 100px;">生成数量</th>
			<td>
				<input value="${vo.amount}" class="easyui-numberbox" data-options="required:true" style="width: 75px;" readonly="readonly"/> 张
			</td>
		</tr>
	</table>
	</form>
</div>