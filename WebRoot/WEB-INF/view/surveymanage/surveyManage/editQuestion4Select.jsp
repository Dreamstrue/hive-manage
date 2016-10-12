<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>问题编辑</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<jsp:include page="../../common/inc.jsp" />
	</head>
	
	<body>
		<div align="center" style="width: 100%">
			<form id="vote_Equestion_update_fn" method="post" style="width: 100%" class="vote_question_update_fn">
				<input type="hidden" name="surveyid" value="${question.surveyid }">
				<input type="hidden" name="id" id="qId" value="${question.id }">
				<table class="tableForm" style="width: 100%;font-size: 12px;">
					<tr>
						<th>问题标题</th>
						<td colspan="3">
							<input name="question" value="${question.question }" style="width:300px;" class="easyui-validatebox" data-options="required:true,validType:'maxLength[100]'"/>
						</td>
						<td><span style="color:red;margin-right: 122px;">* 必填</span></td>
					</tr>
					<tr>
						<th>问题说明</th>
						<td colspan="3" style="text-align: left;width:50%;">
							<textarea name="description" id="q_description"  rows="3" cols="20" style="width:297px;height:50px;font-size: 12px;">${question.description }</textarea>
						</td>
						<td></td>
					</tr>
					<tr>
						<th>问题类型</th>
						<td colspan="5" >
							<c:if test="${question.questiontype == '11' }">
								<input type="radio" onclick="displaySelectMinMax(0)" name="questiontype" class="easyui-validatebox" data-options="required:true" value="11" checked="checked"/>单选
								<input type="radio" onclick="displaySelectMinMax(1)" name="questiontype" class="easyui-validatebox" data-options="required:true" value="12"/>多选
							</c:if>
							<c:if test="${question.questiontype == '12' }">
								<input type="radio" onclick="displaySelectMinMax(0)" name="questiontype" class="easyui-validatebox" data-options="required:true" value="11"/>单选
								<input type="radio" onclick="displaySelectMinMax(1)" name="questiontype" class="easyui-validatebox" data-options="required:true" value="12" checked="checked"/>多选
							</c:if>
							&nbsp;&nbsp;<b>必填 </b><input type="checkbox" <c:if test='${question.required == "1"}'>checked="checked"</c:if> id="ck_0" />
						</td>
					</tr>
					<tr id="selectMinMax" style="display: none;">
						<th>至少选择</th>
						<td colspan="3">
							&nbsp;<input name="selectmin" id="selectMin" class="easyui-numberspinner" data-options="min:0,max:2,editable:true,required:true,missingMessage:'请填写选项分值'" value="${question.selectmin }" style="width: 40px;" />&nbsp;<b>项</b>&nbsp;&nbsp;
							<b>最多选择</b>&nbsp;&nbsp;&nbsp;<input name="selectmax" id="selectMax" class="easyui-numberspinner" data-options="min:1,max:2,editable:true,required:true,missingMessage:'请填写选项分值'" value="${question.selectmax }" style="width: 40px;" />&nbsp;<b>项</b>
						</td>
						<td></td>
					</tr>
					<tr>
						<td  colspan="5" style="background-color: #F4F4F4;text-align: left;width:100%;"><span style="font-size:14px;font-weight:bold;">编辑选项↓ </span>&nbsp;&nbsp;<input type="button" class="easyui-linkbutton" value="增加选项" onclick="addOption()"></td>
						<!-- 
						<td colspan="3" style="background-color: #F4F4F4;text-align: right;width:50%;"><a id="saveSurveyBtn" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" >保存问卷</a></td>
						 -->
					</tr>
				</table>
				<table class="tableForm" style="width: 100%;font-size: 12px;">
					<c:forEach items="${optionList}" var="item" varStatus="status">
					<tr>
						<th>选项内容</th>
						<c:if test="${status.index <= 1}">
						<td><input name="optionText" value="${item.optionText}" id="optionText_${status.index + 1}" class="easyui-validatebox" data-options="required:true, validType:'maxLength[100]'" style="width: 300px;"/>&nbsp;&nbsp;<b>可填空 </b><input type="checkbox" <c:if test='${item.requireinput == "1"}'>checked="checked"</c:if> id="ck_${status.index + 1}" /></td>
						</c:if>
						<c:if test="${status.index > 1}">
						<td><input name="optionText" value="${item.optionText}" id="optionText_${status.index + 1}" class="easyui-validatebox" data-options="validType:'maxLength[100]'" style="width: 300px;"/>&nbsp;&nbsp;<b>可填空 </b><input type="checkbox" <c:if test='${item.requireinput == "1"}'>checked="checked"</c:if> id="ck_${status.index + 1}" />&nbsp;&nbsp;<input type='button' name="deleteBtn" title='删除' value='X'/></td>
						</c:if>
					</tr>
					</c:forEach>
				</table>
				<table id="t1" class="tableForm" style="width: 99%;font-size: 12px;margin-left:-5px;">
				</table>
			</form>
		</div>
	
	
		<script type="text/javascript">
			// 动态添加选项
			function addOption(){ 
				var ck_numMax = 0; // 当前最大编号
				$("input[type=checkbox]").each(function(){ 
						var checkboxId = $(this).attr("id");
						var index = checkboxId.substring(checkboxId.indexOf("_") + 1);
						index = parseInt(index); // 如果不进行转换，index 就是个字符串，字符串10 是没有数字9大的
						if (index > ck_numMax) 
							ck_numMax = index;
				});
				ck_numMax ++;
				//alert(ck_numMax);
				
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
				} else if (type == 2) { // 第二种情况：选项加减(与添加页面不同，这里不改变当前值)
					if ($("#ck_0").attr("checked") == "checked") {
						$('#selectMin').numberspinner({ 
							min:min,
							max:max
						}); 
					}
					$('#selectMax').numberspinner({ 
						min:min,
						max:max
					}); 
				} else if (type == 3) { // 第三种情况：初始化
					$('#selectMin').numberspinner({ 
						min:min,
						max:max
					}); 
					$('#selectMax').numberspinner({ 
						min:min,
						max:max
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
			
			// 选项列表控件标识
			var itemGridId = "vote_edit_questionItem";
			
			$(function(){
				// 初始化数据
				if (${question.questiontype == '12' }) {
					displaySelectMinMax(1);
				}
				var optionNum = getOptionNum();
				if ($("#ck_0").attr("checked") != "checked")
					setSelectMinMax(3, 0, optionNum, 1);
				else
					setSelectMinMax(3, 1, optionNum, 1);
				
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
			
				// 为删除按钮绑定事件（循环出来那几个删除按钮）
				$("input[name='deleteBtn']").bind('click', function() {
					$(this).parent().parent().remove();
					
					// 同时要设置最少最多选几个
	                var optionNum = getOptionNum();
					setSelectMinMax(2, 1, optionNum, 1);
				});
			
				$('#'+itemGridId).datagrid({
					url : '${pageContext.request.contextPath}/optionManage/listOption.action?questionid=${question.id}',
					fitColumns : true,
					rownumbers : true,
					height:180,
					border : false,
					singleSelect:true,
					nowrap : false,
					frozenColumns : [ [ {
						field : 'id',
						title : '编号',
						hidden : true
					},{
						field : 'sortOrder',
						title : '序号',
						width : 50,
						align:'center',
						hidden : true
					}] ],
					 columns : [ [ {
							field : 'optionText',
							title : '选项内容',
							width : 100,
							align:'left',
							formatter : function(value, row, index) {
								var html="";
								html+="<div style='width:350px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;'>"+value+"</div>";
								return html;
							}
						},{
							field : 'requireinput',
							title : '是否可填空',
							width : 50,
							hidden : false,
							formatter : function(value, row, index) {
								var html="";
								if (row.requireinput == 0) {
									return "否";
								} else if (row.requireinput == 1) {
									return "是";
								} 
								return html;
							}
						},{
							field : 'action',
							title : '操作',
							width : 40,
							align:'center',
							formatter : function(value, row, index) {
								var html="";
								html+="&nbsp;<img onclick=\"vote_questionItem_edit_fn('"+row.id+"');\" title='编辑选项' src='${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png'/>";
								html+="&nbsp;<img onclick=\"vote_questionItem_del_fn('"+row.id+"');\" title='删除选项' src='${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png'/>";
								return html;
							}
						}] ],
						toolbar : [ {
							text : '添加选项',
							iconCls : 'icon-add',
							handler : function() {
								vote_item_add_fn();
							}
						}]
				});
			});
			
			//添加选项
			function vote_item_add_fn(){
				$('<div/>').dialog({
					href : '${pageContext.request.contextPath}/voteManage/toItemAdd.action?questionId='+$('#qId').val(),
					width : 400,
					height : 200,
					iconCls : 'icon-table',
					modal : true,
					title : '添加选项',
					buttons : [{
						text : '保存',
						iconCls : 'icon-save',
						handler : function(){
							var d = $(this).closest('.window-body');
							$('#vote_item_add_form').form('submit', {
								url : '${pageContext.request.contextPath}/voteManage/saveItem.action',
								success : function(result){
									var r = $.parseJSON(result);
									$.messager.show({
										title : '提示',
										msg : r.msg
									});
									if(r.status){
										$('#'+itemGridId).datagrid('load');
									}
									d.dialog('destroy');
								}
							
							});
						}
					},{
						text : '关闭',
						iconCls : 'icon-cancel',
						handler : function(){
							var d = $(this).closest('.window-body');
							d.dialog('destroy');
						}
					}],
					onClose : function(){
						$(this).dialog('destroy');
					}
				});
			}
			
			//编辑选项
			function vote_questionItem_edit_fn(itemId){
				$('<div/>').dialog({
					href : '${pageContext.request.contextPath}/voteManage/toItemEdit.action?itemId='+itemId,
					width : 400,
					height : 200,
					iconCls : 'icon-table',
					modal : true,
					title : '修改选项',
					buttons :[{
						text : '保存',
						iconCls : 'icon-save',
						handler : function(){
							var d = $(this).closest('.window-body');
							$('#itemForm').form('submit', {
								url : '${pageContext.request.contextPath}/voteManage/updateItem.action',
								success : function(result) {
									var r = $.parseJSON(result);
									if (r.status) {
										d.dialog('destroy');
										$('#'+itemGridId).datagrid('reload');
									}
									$.messager.show({
										title : '提示',
										msg : r.msg
									});
								}
							}); 
						}
					},{
						text : '关闭',
						iconCls : 'icon-cancel',
						handler : function(){
							var d = $(this).closest('.window-body');
							d.dialog('destroy');
						}
					}],
					onClose : function(){
						var d = $(this).closest('.window-body');
						d.dialog('destroy');
					}
				});
			}
			
			//删除选项
			function vote_questionItem_del_fn(itemId) {
				$.messager.confirm('确认', '您是否要删除吗？', function(r) {
					if (r) {
						$.post("${pageContext.request.contextPath}/voteManage/delItem.action",{itemId : itemId},function(result){
							if (result.status) {
								$('#'+itemGridId).datagrid('load');
								$('#'+itemGridId).datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
								easyuiBox.show(result.msg);
							}else{
								easyuiBox.alert(result.msg);
							}
							
						},'json');
					}
				});
			}
			
		</script>
	</body>
</html>