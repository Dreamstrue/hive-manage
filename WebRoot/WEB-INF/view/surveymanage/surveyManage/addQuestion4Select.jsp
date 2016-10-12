<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	 <head>
	    <title>添加问题</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<jsp:include page="../../common/inc.jsp" />
	 </head>
	
	<body>
		<div align="center" style="width: 100%;">
			<form id="survey_question_add_form" method="post" style="width: 100%">
				<input type="hidden" name="surveyid" id="surveyid" value="${surveyid}"/>
				<table class="tableForm" style="width: 100%;font-size: 12px;">
					<!-- 
					<tr>
						<th>问题序号</th>
						<td colspan="3">
							<input class="easyui-validatebox" id="maxSort" name="sortOrder" readonly="readonly" value="${maxSort }" style="cursor:not-allowed;">
						</td>	
					</tr>
					 -->
					<tr>
						<th>问题标题</th>
						<td colspan="3">
							<input name="question" style="width:300px;" class="easyui-validatebox" data-options="required:true,validType:'maxLength[100]'"/>
						</td>
						<td><span style="color:red;margin-right: 98px;">* 必填</span></td><!-- 在这儿调节文字对齐 -->
					</tr>
					<tr>
						<th>问题说明</th>
						<td colspan="3" style="text-align: left;width:50%;">
							<textarea name="description" id="q_description"  rows="3" cols="20" style="width:297px;height:50px;font-size: 12px;"></textarea>
						</td>
						<td></td>
					</tr>
					<tr>
						<th>问题类型</th>
						<td colspan="3">
							<input type="radio" onclick="displaySelectMinMax(0)" name="questiontype" class="easyui-validatebox" data-options="required:true" value="11" checked="checked"/>单选
							<input type="radio" onclick="displaySelectMinMax(1)" name="questiontype" class="easyui-validatebox" data-options="required:true" value="12"/>多选
							&nbsp;&nbsp;<b>必填 </b><input type="checkbox" checked="checked" id="ck_0" />
						</td>
						<td></td>
					</tr>
					<tr id="selectMinMax" style="display: none;">
						<th>至少选择</th>
						<td colspan="3">
							&nbsp;<input name="selectmin" id="selectMin" class="easyui-numberspinner" data-options="min:1,max:2,editable:true,required:true,missingMessage:'请填写选项分值'" value="1" style="width: 40px;" />&nbsp;<b>项</b>&nbsp;&nbsp;
							<b>最多选择</b>&nbsp;&nbsp;&nbsp;<input name="selectmax" id="selectMax" class="easyui-numberspinner" data-options="min:1,max:2,editable:true,required:true,missingMessage:'请填写选项分值'" value="2" style="width: 40px;" />&nbsp;<b>项</b>
						</td>
						<td></td>
					</tr>
					<tr>
						<td  colspan="5" style="background-color: #F4F4F4;text-align: left;width:100%;"><span style="font-size:14px;font-weight:bold;">添加选项↓ </span>&nbsp;&nbsp;<input type="button" class="easyui-linkbutton" value="增加选项" onclick="addOption()"></td>
						<!-- 
						<td colspan="3" style="background-color: #F4F4F4;text-align: right;width:50%;"><a id="saveSurveyBtn" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" >保存问卷</a></td>
						 -->
					</tr>
				</table>
				<table class="tableForm" style="width: 100%;font-size: 12px;">
					<tr>
						<th>选项内容</th>
						<td><input name="optionText" id="optionText_1" class="easyui-validatebox" data-options="required:true, validType:'maxLength[100]'" style="width: 300px;"/>&nbsp;&nbsp;<b>可填空 </b><input type="checkbox" id="ck_1" /></td>
					</tr>
					<tr>
						<th>选项内容</th>
						<td><input name="optionText" id="optionText_2" class="easyui-validatebox" data-options="required:true, validType:'maxLength[100]'" style="width: 300px;"/>&nbsp;&nbsp;<b>可填空 </b><input type="checkbox" id="ck_2" /></td>
					</tr>
					<tr>
						<th>选项内容</th>
						<td><input name="optionText" id="optionText_3" class="easyui-validatebox" data-options="validType:'maxLength[100]'" style="width: 300px;"/>&nbsp;&nbsp;<b>可填空 </b><input type="checkbox" id="ck_3" /></td>
					</tr>
					<tr>
						<th>选项内容</th>
						<td><input name="optionText" id="optionText_4" class="easyui-validatebox" data-options="validType:'maxLength[100]'" style="width: 300px;"/>&nbsp;&nbsp;<b>可填空 </b><input type="checkbox" id="ck_4" /></td>
					</tr>
				</table>
				<table id="t1" class="tableForm" style="width: 99%;font-size: 12px;margin-left:6px;"><!-- 在这儿调节文字对齐 -->
				</table>
			</form>
		</div>

		<script type="text/javascript">
			// 动态添加选项
			function addOption(){ 
				var ck_numMax = 4; // 当前最大编号
				$("input[type=checkbox]").each(function(){ 
						var checkboxId = $(this).attr("id");
						var index = checkboxId.substring(checkboxId.indexOf("_") + 1);
						if (index > ck_numMax) 
							ck_numMax = index;
				});
				ck_numMax ++;
				
				var btndel = $("<input type='button' title='删除' value='X'/>"); //创建删除按钮
	            btndel.click(function () { //为删除按钮添加删除功能
	                $(this).parent().parent().remove();
	                
	                // 同时要设置最少最多选几个
	                var optionNum = getOptionNum();
					setSelectMinMax(2, 1, optionNum, 1);
	            });
	            var td = $('<td><input name="optionText" id="optionText_' + ck_numMax + '" onblur="clearCheckBox(this.id)" class="easyui-validatebox" data-options="validType:\'maxLength[100]\'" style="width: 300px;"/>&nbsp;&nbsp;<b>可填空 </b><input type="checkbox" id="ck_' + ck_numMax + '" onclick="checkOptionText(this.id)"/>&nbsp;&nbsp;</td>'); //创建td对象
	            td.append(btndel); //将按钮添加到td中
	            var tr = $("<tr><th>选项内容</th></tr>");  //创建tr
	            tr.append(td);
	            $("#t1").append(tr);
			} 
		
			// 勾选复选框时候判断选项内容是否为空
			function checkOptionText(checkboxId) {
				var index = checkboxId.substring(checkboxId.indexOf("_") + 1);
				if ($("#" + checkboxId).attr("checked") == "checked") {
					if ($("#optionText_" + index).val() == "") {
						alert("请输入选项内容后再勾选");
						$("#" + checkboxId).attr("checked", false);
					}
				} 
			}
			
			// 清空选项内容时，把后面复选框的勾去掉
			function clearCheckBox(optionTextId) {
				var index = optionTextId.substring(optionTextId.indexOf("_") + 1);
				if ($("#" + optionTextId).val() == "") {
					$("#ck_" + index).attr("checked", false);
				} 
				
				var optionNum = getOptionNum();
				setSelectMinMax(2, 1, optionNum, 1);
			}
		
			// 显示/隐藏最多最少设置
			function displaySelectMinMax(flag) {
				if (flag == 0)
					$("#selectMinMax").css("display","none");
				else if (flag == 1)
					$("#selectMinMax").css("display","");
			}
			
			// 设置最多最少选几个
			function setSelectMinMax(type, min, max, value) {
				// 第一种情况：去掉必填
				if (type == 1) {
					$('#selectMin').numberspinner({ 
						min:min,
						max:max,
						value:value
					}); 
				} else if (type == 2) { // 第二种情况：选项加减
					if ($("#ck_0").attr("checked") == "checked") {
						$('#selectMin').numberspinner({ 
							min:min,
							max:max,
							value:value
						}); 
					}
					$('#selectMax').numberspinner({ 
						min:min,
						max:max,
						value:max
					}); 
				}
			}
			
			// 获取当前选项个数
			function getOptionNum() {
				var optionNum = 0; // 当前最大编号
				$("input[name=optionText]").each(function(){ 
						var inputId = $(this).attr("id");
						if (inputId == "optionText_1" || inputId == "optionText_2" || $(this).val() != "")
							optionNum ++;
				});
				//alert(optionNum);
				return optionNum;
			}
		
			// 页面加载完后的初始化
			$(function(){
				// 非必填时，最少可选择0个选项
				$("#ck_0").click(function() {
					if ($(this).attr("checked") != "checked") {
						setSelectMinMax(1, 0, 0, 0);
					} else {
						var optionNum = getOptionNum();
						setSelectMinMax(1, 1, optionNum, 1);
					}
				});
			
				// 勾选复选框时候判断选项内容是否为空
				$("input[type=checkbox]").click(function(){
					var checkboxId = $(this).attr("id");
					checkOptionText(checkboxId);
				});
				
				// 清空选项内容时候要把复选框勾掉
				$("input[name='optionText']").blur(function(){ //由于复选框一般选中的是多个,所以可以循环输出 
					var optionTextId = $(this).attr("id");
					clearCheckBox(optionTextId);
				}); 
				
				// *****************************************************
				// "保存"按钮的单击事件的处理
				// *****************************************************
				$('#saveSurveyBtn').bind('click', function() {
						var requireInput = "";
						$("input[type=checkbox]").each(function() { 
							if ($(this).attr("checked") == "checked") {
								requireInput += "1,"
							} else {
								requireInput += "0,"
							}
						});
						requireInput = requireInput.substring(0, requireInput.length - 1);
						//alert(requireInput);
				
						$('#survey_question_add_form').form('submit',{
							url : '${pageContext.request.contextPath}/optionManage/saveOption.action?requireInput=' + requireInput,
							error : function(result){
								var r = $.parseJSON(result);
								easyuiBox.show(r.msg);
							},
							success : function(result){
								var r = $.parseJSON(result);
								if (r.status) {
									easyuiBox.show(r.msg);
									var questionid = r.data;
									var url = "${pageContext.request.contextPath}/optionManage/toOptionList.action?questionid="+questionid;
									var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
									parent.layout_center_addTabFun({title:'选项列表', content:content, closable:true});
									parent.layout_center_refreshTab('选项列表'); // 刷新一下（针对问题列表已经打开的情况）
									// 然后关闭添加页面
									parent.layout_center_closeTab('添加选项');
								}else{
									easyuiBox.show(r.msg);
								}
							}
						});
				});
				
				// *****************************************************
				// "保存并继续"按钮的单击事件的处理
				// *****************************************************
				$('#saveSurveyContinueBtn').bind('click', function(){
						$('#vote_add_form').form('submit',{
							url : '${pageContext.request.contextPath}/surveyManage/saveSurvey.action',
							error : function(result){
								var r = $.parseJSON(result);
								easyuiBox.show(r.msg);
							},
							success : function(result){
								var r = $.parseJSON(result);
								if (r.status) {
									// 如果添加成功后,则先打开其编辑页面
									var surveyObj = r.data;
									var surveyId = surveyObj.id;
									var url = "${pageContext.request.contextPath}/surveyManage/toSurveyEdit.action?surveyId="+surveyId;
									var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
									parent.layout_center_addTabFun({title:'编辑问卷', content:content, closable:true});
									// 然后关闭添加页面
									parent.layout_center_closeTab('添加问卷');
									
								}else{
									easyuiBox.show(r.msg);
								}
							}
							
						});
				});
				
				// *****************************************************
				// "关闭"按钮的单击事件的处理
				// *****************************************************
				$('#closeBtn').click(function(){
					parent.layout_center_closeTab('添加问卷');
				});
				
				
			});// End Of 页面加载完后的初始化
			
			
			// 投票开始时间选中后，投票结束时间控件显示选中面板，并且设置其值为开始时间
			function openEndDateCalendar(date){
				$("#endDate").combo('showPanel');
				$('#endDate').datebox('setValue',date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate());
			}
		
		</script>
		

		<script type="text/javascript">
		
			// 页面加载完后的初始化
			$(function(){
				// 加载问卷类别（我们返回的 json 格式不能直接使用，参考：http://www.lihuoqing.cn/code/848.html）
				var url = "${appPath}/surveyCategoryManage/datagrid.action";
				$.getJSON(url, function(json) {
					$('#categoryid').combobox({
						data : json.rows, // 实际上就是先从返回的 json 中取得一个属性值，这个属性值又是一个 json 对象，我们要用的是这个 json 的属性
						valueField:'id',
						textField:'categoryname'
					});
				});
				
				// *****************************************************
				// "保存"按钮的单击事件的处理
				// *****************************************************
				$('#saveSurveyBtn').bind('click', function(){
						$('#vote_add_form').form('submit',{
							url : '${pageContext.request.contextPath}/questionManage/saveQuestion.action',
							error : function(result){
								var r = $.parseJSON(result);
								easyuiBox.show(r.msg);
							},
							success : function(result){
								var r = $.parseJSON(result);
								if (r.status) {
									easyuiBox.show(r.msg);
									var questionObj = r.data;
									var surveyid = questionObj.surveyid;
									var url = "${pageContext.request.contextPath}/questionManage/toQuestionList.action?surveyid="+surveyid;
									var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
									parent.layout_center_addTabFun({title:'问题列表', content:content, closable:true});
									parent.layout_center_refreshTab('问题列表'); // 刷新一下（针对问题列表已经打开的情况）
									// 然后关闭添加页面
									parent.layout_center_closeTab('添加问题');
								}else{
									easyuiBox.show(r.msg);
								}
							}
						});
				});
				// *****************************************************
				// "保存并继续"按钮的单击事件的处理
				// *****************************************************
				$('#saveSurveyContinueBtn').bind('click', function(){
						$('#vote_add_form').form('submit',{
							url : '${pageContext.request.contextPath}/surveyManage/saveSurvey.action',
							error : function(result){
								var r = $.parseJSON(result);
								easyuiBox.show(r.msg);
							},
							success : function(result){
								var r = $.parseJSON(result);
								if (r.status) {
									// 如果添加成功后,则先打开其编辑页面
									var surveyObj = r.data;
									var surveyId = surveyObj.id;
									var url = "${pageContext.request.contextPath}/surveyManage/toSurveyEdit.action?surveyId="+surveyId;
									var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
									parent.layout_center_addTabFun({title:'编辑问卷', content:content, closable:true});
									// 然后关闭添加页面
									parent.layout_center_closeTab('添加问卷');
									
								}else{
									easyuiBox.show(r.msg);
								}
							}
							
						});
				});
				
				// *****************************************************
				// "关闭"按钮的单击事件的处理
				// *****************************************************
				$('#closeBtn').click(function(){
					parent.layout_center_closeTab('添加问卷');
				});
				
				
			});// End Of 页面加载完后的初始化
			
			
			// 投票开始时间选中后，投票结束时间控件显示选中面板，并且设置其值为开始时间
			function openEndDateCalendar(date){
				$("#endDate").combo('showPanel');
				$('#endDate').datebox('setValue',date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate());
			}
		
		</script>

	</body>
</html>