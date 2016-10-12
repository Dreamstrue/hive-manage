<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div align="center" style="width: 100%">
	<form id="allocationForm" method="post" style="width: 100%">
		<table class="tableForm" style="width: 100%" id="tab">
		<input type="hidden" name="activityId" value="${activityVo.id }"/>
			<tr>
				<th style="width: 150px;">活动编号：</th>
				<td>
					<input class="easyui-validatebox" readonly="readonly" value="${activityVo.orderNum }" style="width: 250px;"/> 
				</td>
			</tr>
			<tr>
				<th>活动主题：</th>
				<td>
					<input class="easyui-validatebox" readonly="readonly" value="${activityVo.theme }" style="width: 250px;"/> 
				</td>
			</tr>
			<tr>
				<th>对象类型：</th>
				<td>
					<input class="easyui-combobox" id="actionObjectType" name="actionObjectType" data-options="prompt:'请选择',editable:false,required:true,panelHeight:'auto',width:'250px',
						valueField: 'id',
						textField: 'value',
						data: [
							{
								id: '1',
								value: '行业实体'
							},{
								id: '2',
								value: '标签批次'
							}
						],
						onSelect:function(rec){
						var url = '';
							if(rec.id=='1'){
								$('#cypc').hide();
							    $('#objectBatch').combobox({required: false});
							    //获取所有绑定过问卷的行业实体
								url = '${pageContext.request.contextPath}/industryEntityManage/findAllBindedSurveyEntity.action';
							}else if(rec.id=='2'){
							    //获取所有审批通过的行业实体
								url = '${pageContext.request.contextPath}/industryEntityManage/allindustryEntityInfo.action';
								$('#cypc').show();
							    $('#objectBatch').combobox({required: true});
							}
						    $('#objectId').combobox('reload', url);
						},
						onChange:function(){
							    $('#activityFormId').combobox('setValue', '');
							    $('#activityFormName').val('');
							    $('#objectBatch').combobox('setValue', '');
							    $('#objectId').combobox('setValue', '');
							    $('#objectName').val('');
							    $('#linkId').val('');
							    $('#batch').val('');
						}
						" />
				</td>
			</tr>
			<tr>
				<th>参与对象：</th>
				<td>
					<input id="objectId" name="objectId" class="easyui-combobox" style="width:250px;" 
					data-options="prompt:'请选择',required:true,editable:false,
						valueField:'id',
						textField:'entityName',
						onSelect:function(rec){
							var cbbv = $('#actionObjectType').combobox('getValue');
							$('#objectName').val(rec.entityName);
							
							if(cbbv=='1'){//获取此行业实体绑定的问卷信息
							 	var url = '${pageContext.request.contextPath}/industryEntityManage/findSurveyListById.action?objectId='+rec.id;
							    $('#activityFormId').combobox({
								    valueField:'id',
									textField:'subject'
							    });
							    $('#activityFormId').combobox('reload', url);
							}else if(cbbv=='2'){//获取此行业实体所有绑定过问卷的批次
							 	var url_ = '${pageContext.request.contextPath}/tagSNBatchController/getAllBindedSurveyBatchByIndustryEntityId.action?industryEntityId='+rec.id;
							    $('#objectBatch').combobox({
								    valueField:'id',
									textField:'batch',
									required: true
							    });
							    $('#objectBatch').combobox('reload', url_);
							}
						},
						onChange:function(){
							    $('#activityFormId').combobox('setValue', '');
							    $('#activityFormName').val('');
							    $('#objectBatch').combobox('setValue', '');
							    $('#linkId').val('');
							    $('#batch').val('');
						}"/>
					<input type="hidden" id="objectName" name="objectName"/>
				</td>
			</tr>
			<tr  id="cypc" hidden="true">
				<th>对象批次：</th>
				<td>
				<input class="easyui-combobox" id="objectBatch" name="objectBatch" data-options="prompt:'请选择',editable:false,width:'250px',
						onSelect:function(rec){
								$('#batch').val(rec.batch);
							 	var url_ = '${pageContext.request.contextPath}/tagSNBatchController/getBindedSurveyByBatchId.action?batchId='+rec.id;
							    $('#activityFormId').combobox({
								    valueField:'id',
									textField:'subject'
							    });
							    $('#activityFormId').combobox('reload', url_);
						}
						" />
					<input type="hidden" id="batch"/>
						
				</td>
			</tr>
			<tr>
				<th>问卷主题：</th>
				<td>
					<input id="activityFormId" name="activityFormId" class="easyui-combobox" style="width:250px;" 
					data-options="prompt:'请选择',required:true,editable:false,panelHeight:'auto',
						onSelect:function(rec){
							$('#activityFormName').val(rec.subject);
							var cbbv = $('#actionObjectType').combobox('getValue');
							var batchId = $('#objectBatch').combobox('getValue');
							var	objectId = $('#objectId').combobox('getValue');
							if(cbbv=='1'){//行业实体问卷
							 	var url_ = '${pageContext.request.contextPath}/industryEntityManage/getLinkIdByParam.action?surveyId='+rec.id+'&objectId='+objectId;
							    $.post(url_,
									function(result){
										$('#linkId').val(result.id);
									},'json');
							}else if(cbbv=='2'){
								var url_ = '${pageContext.request.contextPath}/tagSNBatchController/getLinkIdByParam.action?surveyId='+rec.id+'&batchId='+batchId;
								    $.post(url_,
										function(result){
											$('#linkId').val(result.id);
										},'json');
							}
						}
					"/>
					<input type="hidden" id="activityFormName" name="activityFormName"/>
					<input type="hidden" id="linkId" name="linkId"/>
				</td>
			</tr>
		</table>
	</form>
</div>