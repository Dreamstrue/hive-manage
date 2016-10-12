<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.form.js"></script>
    
<script type="text/javascript">    
    
    
    //选择行业实体信息 时候 级联处 该实体  行业类别对应的 问卷列表  yf 20160303 add
	function onccSelect(){
		var industryEntityId = $('#industryEntityId').combobox('getValue');
		var url = '${pageContext.request.contextPath}/surveyManage/getSurveyByindustryEntity.action?industryEntityId='+industryEntityId;
		$('#subjectId').combobox('reload',url);
		$("#subjectId").combobox("setValue",'请选择');
	}
</script> 

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
				<th>行业实体</th>
				<td>
					<input id="industryEntityId" name="industryEntityId" class="easyui-combobox" data-options="url:'${pageContext.request.contextPath}/industryEntityManage/getAllIndustryEntityList.action',mode:'remote',valueField:'id',textField:'entityName',required:true,onSelect:onccSelect" style="width:300px;" />
				</td>
			</tr>
			
			</tr>
				<tr>
				<th>评价问卷</th>
				<td>
					<input id="subjectId" name="subjectId" class="easyui-combobox" data-options="textField:'subject',valueField:'id',multiple:false,editable:false" style="width:300px;" />
				</td>
			</tr>
			
			<tr>
				<th style="width: 100px;">生成数量</th>
				<td>
					<input id="nNumber" name="nNumber" class="easyui-numberbox" data-options="required:true" style="width: 100px;" /> 张
				</td>
			</tr>
			<tr><th>&nbsp;</th><td>&nbsp;</td></tr>
			<tr>
				<th style="width: 100px;text-align:left;" colspan="2"><font color="red">&nbsp;提示：
					<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由于生产的数量比较大，页面等待的时间可能有点长，请耐心等待！！！
					<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;试运行初期，建议SN生成数量尽量保持在100000之内！</font>
				</th>
			</tr>
		</table>
	</form>
</div>