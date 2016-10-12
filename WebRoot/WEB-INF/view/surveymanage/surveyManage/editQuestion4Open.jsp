<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>修改开放题</title>
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
				<!-- 此处定义一个隐藏域，当手动选择项没有被选择时，为了处理方便，把值设为no -->
				<input type="hidden" name="myCheck" id="myCheck"/>
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
							<c:if test="${question.questiontype == '61' }">
								<input type="radio" onclick="displaySelectMinMax(0)" name="questiontype" class="easyui-validatebox" data-options="required:true" value="61" checked="checked"/>单行填空题
								<input type="radio" onclick="displaySelectMinMax(1)" name="questiontype" class="easyui-validatebox" data-options="required:true" value="62"/>多行问答题
							</c:if>
							<c:if test="${question.questiontype == '62' }">
								<input type="radio" onclick="displaySelectMinMax(0)" name="questiontype" class="easyui-validatebox" data-options="required:true" value="61"/>单行填空题
								<input type="radio" onclick="displaySelectMinMax(1)" name="questiontype" class="easyui-validatebox" data-options="required:true" value="62" checked="checked"/>多行问答题
							</c:if>
							&nbsp;&nbsp;<b>必填 </b><input type="checkbox" <c:if test='${question.required == "1"}'>checked="checked"</c:if> id="ck_0" />
						</td>
					</tr>
					<tr id="selectMinMax" style="display: none;">
						<th>至少</th>
						<td colspan="3">
							&nbsp;<input name="selectmin" id="selectMin" class="easyui-numberspinner" data-options="min:0,max:1000,editable:true,required:true,missingMessage:'请填写至少字数'" value="${question.selectmin }" style="width: 50px;" />&nbsp;<b>字</b>&nbsp;&nbsp;
							<b>最多</b>&nbsp;&nbsp;&nbsp;<input name="selectmax" id="selectMax" class="easyui-numberspinner" data-options="min:1,max:1000,editable:true,required:true,missingMessage:'请填写最多字数'" value="${question.selectmax }" style="width: 50px;" />&nbsp;<b>字</b>
						</td>
						<td></td>
					</tr>
					<tr>
						<th>手动选择</th>
						<td colspan="4">
							是<input type="checkbox"  id="selectOption"  name="isSelect" <c:if test="${question.isSelect == '1'}">checked="checked"</c:if> value="${question.isSelect}"/>
							<span id="datasource" style="display:none;margin-left: 50px;margin-top: -20px;">
								<input id="questionCateId" name="questionCateId"  <c:if test="${question.isSelect == '1'}">value="${question.questionCateId}"</c:if> class="easyui-combotree" data-options="url:'${appPath}/questionData/questionCateList.action',parentField : 'parentId',lines:true,cascadeCheck:false,checkbox:true,multiple:true,onlyLeafCheck:true" style="width:200px;" />
							</span>
						</td>
					</tr>
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
				if (${question.questiontype == '62' }) {
					displaySelectMinMax(1);
				}
				
				// 非必填时，最少可选择0个选项
				$("#ck_0").click(function() {
					if ($(this).attr("checked") != "checked") {
						setSelectMinMax(1, 0, 0, 0);
					} else {
						setSelectMinMax(1, 1, 1000, 1);
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
				
				
				//监听是否手动选择复选框
				var select_value = $('#selectOption').val();
				if(select_value=='1'){ //复选框被选中
					var obj = document.getElementById("datasource");
					obj.style.display = "block";
					$('#selectOption').val(1);
				}
				
				$('#selectOption').click(function(){
					var obj = document.getElementById("datasource");
					if($(this).attr("checked")=="checked"){
						obj.style.display = "block";
						$('#selectOption').val(1);
					}else{
						$('#myCheck').val('no');
						obj.style.display = "none";
					}
				});
				
				
				//控制数据源类别只可以选择一项
				var ids = '';
				$('#questionCateId').combotree({
					onCheck:function(node,checked){
						var t = $('#questionCateId').combotree('tree');// 得到树对象	
						var nodes = t.tree('getChecked');
						if(nodes.length>1){ //说明有一个节点被选中了
							var node1 = t.tree('find', ids);
							t.tree('uncheck',node1.target);
						}
						ids = node.id;
					}
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
					id:'save_dialog',
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
									$("#save_dialog").dialog('destroy');
								}
							
							});
						}
					},{
						text : '关闭',
						iconCls : 'icon-cancel',
						handler : function(){
							var d = $(this).closest('.window-body');
							$("#save_dialog").dialog('destroy');
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
					id:'update_dialog',
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
										$("#update_dialog").dialog('destroy');
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
							$("#update_dialog").dialog('destroy');
						}
					}],
					onClose : function(){
						var d = $(this).closest('.window-body');
						$("#update_dialog").dialog('destroy');
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