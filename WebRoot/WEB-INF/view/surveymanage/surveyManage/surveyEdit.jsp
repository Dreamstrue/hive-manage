<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	 <head>
	    <title>问卷编辑</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<jsp:include page="../../common/inc.jsp" />
		<link rel="stylesheet" href="${appPath}/resources/js/plugin/Jcrop/css/jquery.Jcrop.min.css" type="text/css"></link>
		<script type="text/javascript" src="${appPath}/resources/js/plugin/Jcrop/js/jquery.Jcrop.min.js"></script>
		<script type="text/javascript" src="${appPath}/resources/js/common/jQuery.UtrialAvatarCutter.js"></script>
		<script type="text/javascript" src="${appPath}/resources/js/defined/jquery.upload.js"></script>
	 </head>
	
	<body>
		<div align="center" style="width: 100%;">
			<form id="vote_add_form" method="post" style="width: 100%">
				<input type="hidden" name="id" id="surveyId" value="${survey.id}"/>
				<!-- 原新闻图标路径 -->
		        <input type="hidden" name="picturePath" id="picturePath" value="${survey.picturePath}"/>
		        <!-- 新闻图标裁切的数据 -->
		        <input type="hidden" name="x" id="x"/>
		        <input type="hidden" name="y" id="y"/>
		        <input type="hidden" name="w" id="w"/>
		        <input type="hidden" name="h" id="h"/>
				<table class="tableForm" style="width: 100%;font-size: 12px;">
					<!-- 
					<tr>
						<td colspan="4" style="background-color: #F4F4F4;text-align: left;width:50%;">
							<input type="button" class="easyui-linkbutton" value="增加选项" onclick="addOption()">
							<a id="surveySet" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" title="对问卷相关属性进行设置">设置</a>
							<a id="saveSurveyContinueBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" title="保存当前问卷后跳转至添加问题页面">改背景</a>
							<a id="closeBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" title="关闭当前页面">删除</a>
						</td>
					</tr>
					-->
					<tr>
						<th>问卷标题</th>
						<td colspan="3">
							<input name="subject" value="${survey.subject }" style="width:550px;" class="easyui-validatebox" data-options="required:true,validType:'maxLength[100]'"/>
							<span style="color:red;margin-left:5px;">* 必填</span>
						</td>
					</tr>
					<tr>
						<th>问卷图标</th>
						<td>
							<!---缩略图-->
							<div id="preview">
						    	<img id="avatar" style="width: 60px; height: 60px;"
						    		<c:choose>
						    			<c:when test="${empty survey.picturePath}"> src="${appPath}/avatar/avatar-small.png" </c:when>
						    			<c:otherwise> src="${appPath}/news/loadPic.action?picPath=${survey.picturePath}"</c:otherwise>  
						    		</c:choose>
						    	 />&#160;<font color="#FF0000" size="2">(注意：选择图片不能超过200×200像素)</font>
					    	</div>
					    	<!-- 为了操作的方便，在修改的过程中可以重新上传图片，而审核的时候就不用了 -->
					    	<c:if test="${opType=='update' }">  
								<input name="picturefile"  id="picturefile"  type="hidden"  style="width: 200px;"/>
					    	</c:if>
					    	<input type="button" onclick="doUpload()" value="浏览...">&nbsp;<input type="button" id="deleteBtn" style="display:none;" title="删除图标" onclick="deleteUpload()" value="X">
							<!--原始图-->
			                <div id="originalImgContainer" style="float:right;margin-right: 5px;">
			                	<!-- 文件上传列表 -->
			                	<div id="fileList" style="height:10px;"></div>
							</div>
						</td>
						<td colspan="2"></td>
					</tr>
					<tr>
						<th>问卷说明</th>
						<td colspan="3">
							<textarea name="description" id="description"  rows="2" cols="58" style="width:666px;height:40px;visibility:hidden;">${survey.description }</textarea>
									<script type="text/javascript">
										KindEditor.ready(function(K) {
											var editor1 = K.create('textarea[name="description"]', {
												cssPath : '${appPath}/resources/js/plugin/kindeditor/plugins/code/prettify.css',
												uploadJson : '${appPath}/resources/js/plugin/kindeditor/jsp/upload_json.jsp',
												fileManagerJson : '${appPath}/resources/js/plugin/kindeditor/jsp/file_manager_json.jsp',
												allowFileManager : true,
												afterCreate : function() { 
      											   this.sync(); 
										        }, 
										        afterBlur:function(){ 
										            this.sync(); 
										        }      
											});
										});
									</script>
						</td>
					</tr>
					<tr>
						<th>结束语</th>
						<td colspan="3">
							<textarea name="enddescription" id="enddescription"  rows="2" cols="58" style="width:666px;height:70px;visibility:hidden;">${survey.enddescription }</textarea>
									<script type="text/javascript">
										KindEditor.ready(function(K) {
											var editor1 = K.create('textarea[name="enddescription"]', {
												cssPath : '${appPath}/resources/js/plugin/kindeditor/plugins/code/prettify.css',
												uploadJson : '${appPath}/resources/js/plugin/kindeditor/jsp/upload_json.jsp',
												fileManagerJson : '${appPath}/resources/js/plugin/kindeditor/jsp/file_manager_json.jsp',
												allowFileManager : true,
												afterCreate : function() { 
      											   this.sync(); 
										        }, 
										        afterBlur:function(){ 
										            this.sync(); 
										        }      
											});
										});
									</script>
						</td>
					</tr>
					<tr>
						<th>所属行业</th>
						<td>
							<div id="typeDiv">
								<input  id="industryid" name="industryid" value="${survey.industryid }" class="easyui-combotree" data-options="url:'${appPath}/surveyIndustryManage/treegrid.action',parentField:'pid',lines:true,required:true " style="width:170px;" />
								<span style="color:red;margin-left:5px;">* 必填</span>
							</div>
						</td>
						<th>问卷类别</th>
						<td>
							<div id="typeDiv">
								<input  id="categoryid" name="categoryid" value="${survey.categoryid }" class="easyui-combobox" data-options="lines:true,required:true" style="width:170px;" />
								<span style="color:red;margin-left:5px;">* 必填</span>
							</div>
						</td>	
					</tr>
					<tr>
						<th>开始时间</th>
						<td>
							<input id="startDate" name="begintime" value="${survey.begintime }" class="easyui-datebox" data-options="required:true, editable:false, onSelect:openEndDateCalendar"/>
							<span style="color:red;margin-left:5px;">* 必填</span>
						</td>
						<th>结束时间</th>
						<td>
							<input id="endDate" name="endtime" value="${survey.endtime }" class="easyui-datebox" data-options="required:true, editable:false, validType:'dateGE[\'startDate\']', invalidMessage: '投票结束时间不能早于投票开始时间'"/>
							<span style="color:red;margin-left:5px;">* 必填</span>
						</td>
					</tr>
					<tr>
						<th>数量限制</th>
						<td>
							<input name="numlimit" value="${survey.numlimit }" style="width:149px;" class="easyui-numberbox" min="1"/>
							<span style="color:red;margin-left:5px;">不填表示无数量限制</span>
							<!--<span style="color:red;margin-left:5px;">不填表示无数量限制</span> -->
						</td>
						<th>奖励积分</th>
						<td>
							<input name="integral" value="${survey.integral }" style="width:150px;" class="easyui-numberbox" min="1"/>
							<span style="color:red;margin-left:5px;">不填表示不奖励积分</span>
						</td>	
					</tr>
					<tr>
						<th>问卷标签</th>
						<td colspan="3">
							<input name="tags" value="${survey.tags }" style="width:149px;" class="easyui-validatebox" data-options="validType:'maxLength[100]'"/>
							<span style="color:red;margin-left:5px;">多个标签请用空格隔开</span>
						</td>
					</tr>
					<tr>
						<td colspan="2" style="background-color: #F4F4F4;text-align: left;width:58%;">
							<span style="font-size:14px;font-weight:bold;">快速添加问题→ </span>
							<a id="addQuestion4Select" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:''" title="单项选择或者多项选择">选择题</a>
							<a id="addQuestion4CombinationSelect" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:''" title="组合选择题">组合选择题</a>
							<a id="addQuestion4Score" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:''" title="打分题">打分题</a>
							<a id="addQuestion4CombinationScore" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:''" title="组合打分题">组合打分题</a>
							<a id="addQuestion4Sort" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:''" title="排序题">排序题</a>
							<a id="addQuestion4Open" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:''" title="开放题">开放题</a>
						</td>
						<td colspan="2" style="background-color: #F4F4F4;text-align: right;width:42%;"><!--<a id="surveySet" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" title="对问卷相关属性进行设置">设置</a>&nbsp;&nbsp;--><a id="saveSurveyBtn" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" >保存问卷</a></td>
					</tr>
				</table>
				<table id="vote_question_datagrid"></table>
				<!-- ======================================================== -->
				<!--
				<hr>
				<div>
					<table style="font-size:12px;">
						<tr>
							<td align="right" style="line-height:1">嵌入界面:</td>
							<td><a target="_blank" href="<%=basePath%>voteManage/getWj.action?id=${questionnaireId}"><%=basePath%>voteManage/getWj.action?id=${questionnaireId}</a></td>
							<td><a href="#" onclick="copyToClipBoardAdd();">复制</a></td>
						</tr>
						<tr>
							<td align="right">主界面:</td>
							<td><a target="_blank" href="<%=basePath%>voteManage/getWjVote.action?id=${questionnaireId}"><%=basePath%>voteManage/getWjVote.action?id=${questionnaireId}</a></td>
							<td><a href="#" onclick="copyToClipBoardAdd2();">复制</a></td>
						</tr>
					</table>
				</div>
				<span style="color:red;">若使用火狐、谷歌浏览器,请手动复制</span>
				-->
			</form>
		</div>

		<script type="text/javascript">
			//图片裁切器
			var cutter = new jQuery.UtrialAvatarCutter({
				//主图片所在容器ID
				content : "originalImgContainer",
				//缩略图配置,ID:所在容器ID;width,height:缩略图大小
				purviews : [{id:"preview",width:60,height:60}],
				//选择器默认大小
				selector : {width:120,height:120}
			});
			
			function doUpload() {
				// 上传方法
				$.upload({
						// 上传地址
						url: '${appPath}/annex/uploadAvatar.action', 
						// 文件域名字
						fileName: 'picturefile', 
						// 其他表单数据
						params: {},
						// 上传完成后, 返回json, text
						dataType: 'json',
						// 上传之前回调,return true表示可继续上传
						onSend: function() {
								return true;
						},
						// 上传之后回调
						onComplate: function(data) {
							if(data[0].flag=='1'){
								alert(data[0].message);
							}else{
								var path = data[0].path;
								//alert(path);
								// 记录下原图路径
								$("#picturePath").val(path);
								// 原图重新加载
								cutter.reload("${appPath}/"+path);
								$("#preview").attr("src", "${appPath}/"+path);
								
								// 把图片框和删除按钮放出来
								$("#originalImgContainer").css("display", "");
								$("#deleteBtn").css("display", "");
							}
						}
				});
			}
		
			// 删除图标
			function deleteUpload() {
				// 记录下原图路径
				$("#picturePath").val("");
				$("#avatar").attr("src", "${appPath}/avatar/avatar-small.png");
				$("#originalImgContainer").css("display", "none");
				
				$("#deleteBtn").css("display", "none"); // 隐藏删除按钮
			}
		
		//===================================================
		//提交投票问卷
		function vote_add_form_submit(){
			$('#vote_add_form').form('submit', {
				url : '${pageContext.request.contextPath}/questionnaire/save.action',
				success : function(result) {
					var r = $.parseJSON(result);
					$.messager.alert('提示','数据信息录入成功！','info');
					
					$.messager.show({
						title : '提示',
						msg : r.msg
					});
					
					if (r.status) {
						$('#layout_center_tabs').tabs('getSelected').panel('refresh');
					}
				}
			});
		}// End Of vote_add_form_submit()
		
		// *****************************
		// 页面加载完后的初始化
		// *****************************
		$(function(){
			// 初始化数据
			if (${!empty survey.picturePath}) {
				$("#deleteBtn").css("display", "");
			}
			
			// 加载问卷类别（我们返回的 json 格式不能直接使用，参考：http://www.lihuoqing.cn/code/848.html）
			var url = "${appPath}/surveyCategoryManage/datagrid.action";
			$.getJSON(url, function(json) {
				$('#categoryid').combobox({
					data : json.rows, // 实际上就是先从返回的 json 中取得一个属性值，这个属性值又是一个 json 对象，我们要用的是这个 json 的属性
					valueField:'id',
					textField:'categoryname'
				});
			});
			
			// 问卷题目列表的加载
			$('#vote_question_datagrid').datagrid({
				url : '${pageContext.request.contextPath}/questionManage/listQuestion.action?surveyid='+$('#surveyId').val(), 
				fitColumns : true,
				height:160,
				border : false,
				rownumbers : true,
				singleSelect:true,
				nowrap : false,
				frozenColumns : [ [ {
					field : 'id',
					title : '问卷编号',
					hidden : true
				}] ],
				 columns : [[{
					field : 'id',
					title : '投票问卷ID',
					hidden : true
				},{
					field : 'question',
					title : '问题内容',
					width : 150,
					sortable : true,
					formatter : function(value, row, index) {
						var html="";
						html+="<div style='width:400px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;'>"+value+"</div>";
						//if(value == '请点击操作栏的铅笔图标,继续您上次未完成的问卷信息')
						/* if(row.qnDataInte == 1){
							return "<span style='color:red'>"+html+"</span>";
						}else{
							return html;
						} */
						return html;
					}
				},{
					field : 'description',
					title : '问题描述',
					width : 50,
					hidden : false
				},{
					field : 'questiontype',
					title : '问题类型',
					width : 50,
					hidden : false,
					formatter : function(value, row, index) {
						var html="";
						if (row.questiontype.substring(0,1) == "1") {
							return "选择题";
						} else if (row.questiontype.substring(0,1) == "2") {
							return "组合选择题";
						} else if (row.questiontype.substring(0,1) == "3") {
							return "打分题";
						} else if (row.questiontype.substring(0,1) == "4") {
							return "组合打分题";
						} else if (row.questiontype.substring(0,1) == "5") {
							return "排序题";
						} else if (row.questiontype.substring(0,1) == "6") {
							return "开放题";
						}
						return html;
					}
				}
				,{
				field : 'action',
				title : '操作',
						width : 80,
						align:'center',
						formatter : function(value, row, index) {
							var html="";
							html+="<img onclick=\"questionView('"+row.id+"', '" + row.questiontype + "');\" title='查看问题/选项' src='${pageContext.request.contextPath}/resources/images/extjs_icons/table/table.png'/>";
							html+="&nbsp;<img onclick=\"questionEdit('"+row.id+"', '" + row.questiontype + "');\" title='编辑问题/选项' src='${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png'/>";
							html+="&nbsp;<img onclick=\"questionDel('"+row.id+"');\" title='删除问题' src='${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png'/>";
							return html;
						}
					} ] ]
			});
			
			// *****************************************************
			// "选择题"按钮的单击事件的处理
			// *****************************************************
			$('#addQuestion4Select').bind('click', function(){
				//var w = getWindowWidth() - 100;
				//var h = getWindowHeight() -50;
				$('<div/>').dialog({
					href : '${pageContext.request.contextPath}/questionManage/toQuestionAdd.action?questiontype=1&surveyid='+$('#surveyId').val(),
					width : 600,
					height : 396,
					iconCls : 'icon-table',
					modal : true,
					title : '添加选择题',
					buttons : [{
						text : '保存',
						iconCls : 'icon-save',
						handler : function(){
							if (!checkQuestionDescription()) return; // 校验问题说明长度
						
							var d = $(this).closest('.window-body');
							
							var optionRequireInput = "";
							$("input[type=checkbox]").each(function() { 
								if ($(this).attr("id") != "ck_0") { // 排除“必填”复选框
									if ($(this).attr("checked") == "checked") {
										optionRequireInput += "1,"
									} else {
										optionRequireInput += "0,"
									}
								}
							});
							optionRequireInput = optionRequireInput.substring(0, optionRequireInput.length - 1);
							//alert(optionRequireInput);return;
							
							if ($("#selectMin").val() > $("#selectMax").val()) {
								easyuiBox.show("最多选择选项数应大于至少选择选项数！");
								return;
							}
								
							var required = $("#ck_0").attr("checked") == "checked" ? 1 : 0; 
							
							$('#survey_question_add_form').form('submit', {
								url : '${pageContext.request.contextPath}/questionManage/saveQuestion4Select.action?required=' + required + '&optionRequireInput=' + optionRequireInput,
								success : function(result) {
									var r = $.parseJSON(result);
									if (r.status) {
										$('#vote_question_datagrid').datagrid('reload');
										d.dialog('destroy');
										
										easyuiBox.show(r.msg);
									}else{
										easyuiBox.alert(r.msg);
									}
									
								}
							});
						}
					},{
						text : '取消',
						iconCls : 'icon-cancel',
						handler : function(){
							var d = $(this).closest('.window-body');
							d.dialog('destroy');
						}
					}],
					onLoad : function(){
						/* if($('#checkQn').val() == 0){
							$(this).dialog('destroy');
							alert("请先添加问卷再添加问题！");
						} */
					},
					onClose : function(){
						$(this).dialog('destroy');
					}
				});
			});
			
			// *****************************************************
			// "组合选择题"按钮的单击事件的处理
			// *****************************************************
			$('#addQuestion4CombinationSelect').bind('click', function(){
				//var w = getWindowWidth() - 100;
				//var h = getWindowHeight() -50;
				$('<div/>').dialog({
					href : '${pageContext.request.contextPath}/questionManage/toQuestionAdd.action?questiontype=2&surveyid='+$('#surveyId').val(),
					width : 600,
					height : 396,
					iconCls : 'icon-table',
					modal : true,
					title : '添加组合选择题',
					buttons : [{
						text : '保存',
						iconCls : 'icon-save',
						handler : function(){
							if (!checkQuestionDescription()) return; // 校验问题说明长度
						
							var d = $(this).closest('.window-body');
						
							if ($("#selectMin").val() > $("#selectMax").val()) {
								easyuiBox.show("最多选择选项数应大于至少选择选项数！");
								return;
							}
									
							var required = $("#ck_0").attr("checked") == "checked" ? 1 : 0;
							
							$('#survey_question_add_form').form('submit', {
								url : '${pageContext.request.contextPath}/questionManage/saveQuestion4CombinationSelect.action?required=' + required,
								success : function(result) {
									var r = $.parseJSON(result);
									if (r.status) {
										$('#vote_question_datagrid').datagrid('reload');
										d.dialog('destroy');
										
										easyuiBox.show(r.msg);
									}else{
										easyuiBox.alert(r.msg);
									}
									
								}
							});
						}
					},{
						text : '取消',
						iconCls : 'icon-cancel',
						handler : function(){
							var d = $(this).closest('.window-body');
							d.dialog('destroy');
						}
					}],
					onLoad : function(){
						/* if($('#checkQn').val() == 0){
							$(this).dialog('destroy');
							alert("请先添加问卷再添加问题！");
						} */
					},
					onClose : function(){
						$(this).dialog('destroy');
					}
				});
			});
			
			// *****************************************************
			// "打分题"按钮的单击事件的处理
			// *****************************************************
			$('#addQuestion4Score').bind('click', function(){
				//var w = getWindowWidth() - 100;
				//var h = getWindowHeight() -50;
				$('<div/>').dialog({
					href : '${pageContext.request.contextPath}/questionManage/toQuestionAdd.action?questiontype=3&surveyid='+$('#surveyId').val(),
					width : 628,
					height : 396,
					iconCls : 'icon-table',
					modal : true,
					title : '添加打分题',
					buttons : [{
						text : '保存',
						iconCls : 'icon-save',
						handler : function(){
							if (!checkQuestionDescription()) return; // 校验问题说明长度
							
							var d = $(this).closest('.window-body');
							
							var optionRequireInput = "";
							$("input[type=checkbox]").each(function() { 
								if ($(this).attr("id") != "ck_0") { // 排除“必填”复选框
									if ($(this).attr("checked") == "checked") {
										optionRequireInput += "1,"
									} else {
										optionRequireInput += "0,"
									}
								}
							});
							optionRequireInput = optionRequireInput.substring(0, optionRequireInput.length - 1);
							//alert(optionRequireInput);
							
							if ($("#selectMin").val() > $("#selectMax").val()) {
								easyuiBox.show("最多选择选项数应大于至少选择选项数！");
								return;
							}
									
							var required = $("#ck_0").attr("checked") == "checked" ? 1 : 0;
							
							$('#survey_question_add_form').form('submit', {
								url : '${pageContext.request.contextPath}/questionManage/saveQuestion4Score.action?required=' + required + '&optionRequireInput=' + optionRequireInput + '&questiontype=3',
								success : function(result) {
									var r = $.parseJSON(result);
									if (r.status) {
										$('#vote_question_datagrid').datagrid('reload');
										d.dialog('destroy');
										
										easyuiBox.show(r.msg);
									}else{
										easyuiBox.alert(r.msg);
									}
									
								}
							});
						}
					},{
						text : '取消',
						iconCls : 'icon-cancel',
						handler : function(){
							var d = $(this).closest('.window-body');
							d.dialog('destroy');
						}
					}],
					onLoad : function(){
						/* if($('#checkQn').val() == 0){
							$(this).dialog('destroy');
							alert("请先添加问卷再添加问题！");
						} */
					},
					onClose : function(){
						$(this).dialog('destroy');
					}
				});
			});
			
			// *****************************************************
			// "组合打分题"按钮的单击事件的处理
			// *****************************************************
			$('#addQuestion4CombinationScore').bind('click', function(){
				//var w = getWindowWidth() - 100;
				//var h = getWindowHeight() -50;
				$('<div/>').dialog({
					href : '${pageContext.request.contextPath}/questionManage/toQuestionAdd.action?questiontype=4&surveyid='+$('#surveyId').val(),
					width : 628,
					height : 396,
					iconCls : 'icon-table',
					modal : true,
					title : '添加组合打分题',
					buttons : [{
						text : '保存',
						iconCls : 'icon-save',
						handler : function(){
							if (!checkQuestionDescription()) return; // 校验问题说明长度
							
							var d = $(this).closest('.window-body');
							
							if ($("#selectMin").val() > $("#selectMax").val()) {
								easyuiBox.show("最多选择选项数应大于至少选择选项数！");
								return;
							}
									
							var required = $("#ck_0").attr("checked") == "checked" ? 1 : 0;
							
							$('#survey_question_add_form').form('submit', {
								url : '${pageContext.request.contextPath}/questionManage/saveQuestion4CombinationScore.action?required=' + required + '&questiontype=4',
								success : function(result) {
									var r = $.parseJSON(result);
									if (r.status) {
										$('#vote_question_datagrid').datagrid('reload');
										d.dialog('destroy');
										
										easyuiBox.show(r.msg);
									}else{
										easyuiBox.alert(r.msg);
									}
									
								}
							});
						}
					},{
						text : '取消',
						iconCls : 'icon-cancel',
						handler : function(){
							var d = $(this).closest('.window-body');
							d.dialog('destroy');
						}
					}],
					onLoad : function(){
						/* if($('#checkQn').val() == 0){
							$(this).dialog('destroy');
							alert("请先添加问卷再添加问题！");
						} */
					},
					onClose : function(){
						$(this).dialog('destroy');
					}
				});
			});
			
			// *****************************************************
			// "排序题"按钮的单击事件的处理
			// *****************************************************
			$('#addQuestion4Sort').bind('click', function(){
				//var w = getWindowWidth() - 100;
				//var h = getWindowHeight() -50;
				$('<div/>').dialog({
					href : '${pageContext.request.contextPath}/questionManage/toQuestionAdd.action?questiontype=5&surveyid='+$('#surveyId').val(),
					width : 619,
					height : 366,
					iconCls : 'icon-table',
					modal : true,
					title : '添加排序题',
					buttons : [{
						text : '保存',
						iconCls : 'icon-save',
						handler : function(){
							if (!checkQuestionDescription()) return; // 校验问题说明长度
							
							var d = $(this).closest('.window-body');
							
							var required = $("#ck_0").attr("checked") == "checked" ? 1 : 0;
							
							$('#survey_question_add_form').form('submit', {
								url : '${pageContext.request.contextPath}/questionManage/saveQuestion4Sort.action?required=' + required + '&questiontype=5',
								success : function(result) {
									var r = $.parseJSON(result);
									if (r.status) {
										$('#vote_question_datagrid').datagrid('reload');
										d.dialog('destroy');
										
										easyuiBox.show(r.msg);
									}else{
										easyuiBox.alert(r.msg);
									}
									
								}
							});
						}
					},{
						text : '取消',
						iconCls : 'icon-cancel',
						handler : function(){
							var d = $(this).closest('.window-body');
							d.dialog('destroy');
						}
					}],
					onLoad : function(){
						/* if($('#checkQn').val() == 0){
							$(this).dialog('destroy');
							alert("请先添加问卷再添加问题！");
						} */
					},
					onClose : function(){
						$(this).dialog('destroy');
					}
				});
			});
			
			// *****************************************************
			// "开放题"按钮的单击事件的处理
			// *****************************************************
			$('#addQuestion4Open').bind('click', function(){
				//var w = getWindowWidth() - 100;
				//var h = getWindowHeight() -50;
				$('<div/>').dialog({
					href : '${pageContext.request.contextPath}/questionManage/toQuestionAdd.action?questiontype=6&surveyid='+$('#surveyId').val(),
					width : 536,
					height : 300,
					iconCls : 'icon-table',
					modal : true,
					title : '添加开放题',
					buttons : [{
						text : '保存',
						iconCls : 'icon-save',
						handler : function(){
							if (!checkQuestionDescription()) return; // 校验问题说明长度
							
							var d = $(this).closest('.window-body');
							
							var required = $("#ck_0").attr("checked") == "checked" ? 1 : 0;
							
							$('#survey_question_add_form').form('submit', {
								url : '${pageContext.request.contextPath}/questionManage/saveQuestion4Open.action?required=' + required,
								success : function(result) {
									var r = $.parseJSON(result);
									if (r.status) {
										$('#vote_question_datagrid').datagrid('reload');
										d.dialog('destroy');
										
										easyuiBox.show(r.msg);
									}else{
										easyuiBox.alert(r.msg);
									}
									
								}
							});
						}
					},{
						text : '取消',
						iconCls : 'icon-cancel',
						handler : function(){
							var d = $(this).closest('.window-body');
							d.dialog('destroy');
						}
					}],
					onLoad : function(){
						/* if($('#checkQn').val() == 0){
							$(this).dialog('destroy');
							alert("请先添加问卷再添加问题！");
						} */
					},
					onClose : function(){
						$(this).dialog('destroy');
					}
				});
			});
			
			// *****************************************************
			// "保存问卷"按钮的单击事件的处理
			// *****************************************************
			$('#saveSurveyBtn').bind('click', function(){
				var description =  $('#description').val();
				//if (KindEditor.instances[0].isEmpty()) {
				//	ZENG.msgbox.show('问卷说明不能为空', 1, 3000);
				//	return false;
				//} else 
				if (description.length > 200) {
					easyuiBox.show('问卷说明长度超过最大限制（200字）');
					return false;
				}
				
				var enddescription =  $('#enddescription').val();
				if (enddescription.length > 200) {
					easyuiBox.show('结束语长度超过最大限制（200字）');
					return false;
				}
				
				// 设置表单中图标的裁切信息
		    	var cutterData = cutter.submit();
				if(cutterData.x!=undefined){ //说明提交了剪切图片
					if(cutterData){
						if(cutterData.x>0){
							$("#x").val(cutterData.x);
							$("#y").val(cutterData.y);
							$("#w").val(cutterData.w);
							$("#h").val(cutterData.h);
						}else{
							$.messager.show({
							title:'提示',
							msg:'图片横坐标应大于零，请重新截取'
						});
							return false;
						}
					}
			   	}
				
				$('#vote_add_form').form('submit',{
					url : '${pageContext.request.contextPath}/surveyManage/updateSurvey.action',
					error : function(result){
						var r = $.parseJSON(result);
						easyuiBox.show(r.msg);
					},
					success : function(result){
						var r = $.parseJSON(result);
						if (r.status) {
							easyuiBox.show(r.msg);
						}else{
							easyuiBox.show(r.msg);
						}
						parent.layout_center_refreshTab('问卷列表');
						setTimeout(function(){
							window.location.href = window.location.href;
						}, 2000);
					}
				});
			});
			
		});// End Of 页面加载完后的初始化
		
		
		// *****************************************************
		// 页面中所用的函数方法
		// *****************************************************
		
		function copyToClipBoardAdd(){
			var url = "<%=basePath%>voteManage/getWj.action?id=${questionnaireId}";
			window.clipboardData.setData("Text",url);
		}
		function copyToClipBoardAdd2(){
			var url = "<%=basePath%>voteManage/getWjVote.action?id=${questionnaireId}";
			window.clipboardData.setData("Text",url);
		}
		
		// 投票开始时间选中后，投票结束时间控件显示选中面板，并且设置其值为开始时间
		function openEndDateCalendar(date){
			$("#endDate").combo('showPanel');
			$('#endDate').datebox('setValue',date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate());
		
		}
		
		// *****************************************************
		// 问题相关操作的处理
		// *****************************************************
		
		//修改问题
		function questionEdit(questionid, questiontype){
			if (questiontype.substring(0,1) == "1")
				editQuestion4Select(questionid);
			else if (questiontype.substring(0,1) == "2")
				editQuestion4CombinationSelect(questionid);
			else if (questiontype.substring(0,1) == "3")
				editQuestion4Score(questionid);
			else if (questiontype.substring(0,1) == "4")
				editQuestion4CombinationScore(questionid);
			else if (questiontype.substring(0,1) == "5")
				editQuestion4Sort(questionid);
			else if (questiontype.substring(0,1) == "6")
				editQuestion4Open(questionid);
		}
		
		function editQuestion4Select(questionid) {
			$('<div/>').dialog({
				href : '${pageContext.request.contextPath}/questionManage/toQuestionEdit.action?questiontype=1&questionid='+questionid,
				width : 600,
				height : 396,
				iconCls : 'icon-table',
				modal : true,
				title : '修改问题',
				buttons :[{
					text : '保存',
					iconCls : 'icon-save',
					handler : function(){
						if (!checkQuestionDescription()) return; // 校验问题说明长度
					
						var d = $(this).closest('.window-body');
						
						var optionRequireInput = "";
						$("input[type=checkbox]").each(function() { 
							if ($(this).attr("id") != "ck_0") { // 排除“必填”复选框
								if ($(this).attr("checked") == "checked") {
									optionRequireInput += "1,"
								} else {
									optionRequireInput += "0,"
								}
							}
						});
						optionRequireInput = optionRequireInput.substring(0, optionRequireInput.length - 1);
						//alert(optionRequireInput);
						
						if ($("#selectMin").val() > $("#selectMax").val()) {
							easyuiBox.show("最多选择选项数应大于至少选择选项数！");
							return;
						}
								
						var required = $("#ck_0").attr("checked") == "checked" ? 1 : 0;
						
						$('.vote_question_update_fn').form('submit', {
							url : '${pageContext.request.contextPath}/questionManage/updateQuestion4Select.action?required=' + required + '&optionRequireInput=' + optionRequireInput,
							success : function(result) {
								var r = $.parseJSON(result);
								if (r.status) {
									d.dialog('destroy');
									$('#vote_question_datagrid').datagrid('reload');
									easyuiBox.show(r.msg);
								}else{
									easyuiBox.alert(r.msg);
								}
							},
							error : function(result) {
								var r = $.parseJSON(result);
								easyuiBox.alert(r.msg);
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
		
		function editQuestion4CombinationSelect(questionid) {
			$('<div/>').dialog({
				href : '${pageContext.request.contextPath}/questionManage/toQuestionEdit.action?questiontype=2&questionid='+questionid,
				width : 600,
				height : 484,
				iconCls : 'icon-table',
				modal : true,
				title : '修改问题',
				buttons :[{
					text : '保存',
					iconCls : 'icon-save',
					handler : function(){
						if (!checkQuestionDescription()) return; // 校验问题说明长度
					
						var d = $(this).closest('.window-body');
						
						if ($("#selectMin").val() > $("#selectMax").val()) {
							easyuiBox.show("最多选择选项数应大于至少选择选项数！");
							return;
						}
								
						var required = $("#ck_0").attr("checked") == "checked" ? 1 : 0;
						$('#editQuestion4CombinationSelect_form').form('submit', {
							url : '${pageContext.request.contextPath}/questionManage/updateQuestion4CombinationSelect.action?required=' + required,
							success : function(result) {
								var r = $.parseJSON(result);
								if (r.status) {
									d.dialog('destroy');
									$('#vote_question_datagrid').datagrid('reload');
									easyuiBox.show(r.msg);
								}else{
									easyuiBox.alert(r.msg);
								}
							},
							error : function(result) {
								var r = $.parseJSON(result);
								easyuiBox.alert(r.msg);
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
		
		function editQuestion4Score(questionid) {
			$('<div/>').dialog({
				href : '${pageContext.request.contextPath}/questionManage/toQuestionEdit.action?questiontype=3&questionid='+questionid,
				width : 628,
				height : 408,
				iconCls : 'icon-table',
				modal : true,
				title : '修改问题',
				buttons :[{
					text : '保存',
					iconCls : 'icon-save',
					handler : function(){
						if (!checkQuestionDescription()) return; // 校验问题说明长度
						
						var d = $(this).closest('.window-body');
						
						var optionRequireInput = "";
						$("input[type=checkbox]").each(function() { 
							if ($(this).attr("id") != "ck_0") { // 排除“必填”复选框
								if ($(this).attr("checked") == "checked") {
									optionRequireInput += "1,"
								} else {
									optionRequireInput += "0,"
								}
							}
						});
						optionRequireInput = optionRequireInput.substring(0, optionRequireInput.length - 1);
						//alert(optionRequireInput);
						
						if ($("#selectMin").val() > $("#selectMax").val()) {
							easyuiBox.show("最多选择选项数应大于至少选择选项数！");
							return;
						}
								
						var required = $("#ck_0").attr("checked") == "checked" ? 1 : 0;
						
						$('#editQuestion4Score_form').form('submit', {
							url : '${pageContext.request.contextPath}/questionManage/updateQuestion4Score.action?required=' + required + '&optionRequireInput=' + optionRequireInput + '&questiontype=3',
							success : function(result) {
								var r = $.parseJSON(result);
								if (r.status) {
									d.dialog('destroy');
									$('#vote_question_datagrid').datagrid('reload');
									easyuiBox.show(r.msg);
								}else{
									easyuiBox.alert(r.msg);
								}
							},
							error : function(result) {
								var r = $.parseJSON(result);
								easyuiBox.alert(r.msg);
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
		
		function editQuestion4CombinationScore(questionid) {
			$('<div/>').dialog({
				href : '${pageContext.request.contextPath}/questionManage/toQuestionEdit.action?questiontype=4&questionid='+questionid,
				width : 628,
				height : 484,
				iconCls : 'icon-table',
				modal : true,
				title : '修改问题',
				buttons :[{
					text : '保存',
					iconCls : 'icon-save',
					handler : function(){
						if (!checkQuestionDescription()) return; // 校验问题说明长度
						
						var d = $(this).closest('.window-body');
						
						if ($("#selectMin").val() > $("#selectMax").val()) {
							easyuiBox.show("最多选择选项数应大于至少选择选项数！");
							return;
						}
								
						var required = $("#ck_0").attr("checked") == "checked" ? 1 : 0;
						
						$('#editQuestion4CombinationScore_form').form('submit', {
							url : '${pageContext.request.contextPath}/questionManage/updateQuestion4CombinationScore.action?required=' + required + '&questiontype=4',
							success : function(result) {
								var r = $.parseJSON(result);
								if (r.status) {
									d.dialog('destroy');
									$('#vote_question_datagrid').datagrid('reload');
									easyuiBox.show(r.msg);
								}else{
									easyuiBox.alert(r.msg);
								}
							},
							error : function(result) {
								var r = $.parseJSON(result);
								easyuiBox.alert(r.msg);
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
		
		function editQuestion4Sort(questionid) {
			$('<div/>').dialog({
				href : '${pageContext.request.contextPath}/questionManage/toQuestionEdit.action?questiontype=5&questionid='+questionid,
				width : 600,
				height : 366,
				iconCls : 'icon-table',
				modal : true,
				title : '修改问题',
				buttons :[{
					text : '保存',
					iconCls : 'icon-save',
					handler : function(){
						if (!checkQuestionDescription()) return; // 校验问题说明长度
						
						var d = $(this).closest('.window-body');
								
						var required = $("#ck_0").attr("checked") == "checked" ? 1 : 0;
						
						$('.vote_question_update_fn').form('submit', {
							url : '${pageContext.request.contextPath}/questionManage/updateQuestion4Sort.action?required=' + required + '&questiontype=5',
							success : function(result) {
								var r = $.parseJSON(result);
								if (r.status) {
									d.dialog('destroy');
									$('#vote_question_datagrid').datagrid('reload');
									easyuiBox.show(r.msg);
								}else{
									easyuiBox.alert(r.msg);
								}
							},
							error : function(result) {
								var r = $.parseJSON(result);
								easyuiBox.alert(r.msg);
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
		
		function editQuestion4Open(questionid) {
			$('<div/>').dialog({
				href : '${pageContext.request.contextPath}/questionManage/toQuestionEdit.action?questiontype=6&questionid='+questionid,
				width : 566,
				height : 288,
				iconCls : 'icon-table',
				modal : true,
				title : '修改问题',
				buttons :[{
					text : '保存',
					iconCls : 'icon-save',
					handler : function(){
						if (!checkQuestionDescription()) return; // 校验问题说明长度
						
						var d = $(this).closest('.window-body');
								
						var required = $("#ck_0").attr("checked") == "checked" ? 1 : 0;
						
						$('.vote_question_update_fn').form('submit', {
							url : '${pageContext.request.contextPath}/questionManage/updateQuestion4Open.action?required=' + required,
							success : function(result) {
								var r = $.parseJSON(result);
								if (r.status) {
									d.dialog('destroy');
									$('#vote_question_datagrid').datagrid('reload');
									easyuiBox.show(r.msg);
								}else{
									easyuiBox.alert(r.msg);
								}
							},
							error : function(result) {
								var r = $.parseJSON(result);
								easyuiBox.alert(r.msg);
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
		
		//删除问题
		function questionDel(qId) {
			$.messager.confirm('确认', '将删除该问题及其所属选项，您确定要删除吗？', function(r) {
				if (r) {
					$.post("${pageContext.request.contextPath}/questionManage/deleteQuestion.action",{questionId : qId},function(result){
						if (result.status) {
							$('#vote_question_datagrid').datagrid('load');
							$('#vote_question_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
							easyuiBox.show(result.msg);
						}else{
							easyuiBox.alert(result.msg);
						}
					},'json');
				}
			});
		}// End Of questionDel()
		
		// *****************************************************
		// "选择题"按钮的单击事件的处理
		// *****************************************************
		$('#surveySet').bind('click', function(){
			//var w = getWindowWidth() - 100;
			//var h = getWindowHeight() -50;
			$('<div/>').dialog({
				href : '${pageContext.request.contextPath}/surveyManage/toSurveySet.action?surveyId='+$('#surveyId').val(),
				width : 400,
				height : 240,
				iconCls : 'icon-table',
				modal : true,
				title : '问卷设置',
				buttons : [{
					text : '保存',
					iconCls : 'icon-save',
					handler : function(){
						var d = $(this).closest('.window-body');
						$('#survey_set_form').form('submit', {
							url : '${pageContext.request.contextPath}/surveyManage/setSurvey.action?surveyId='+$('#surveyId').val(),
							success : function(result) {
								var r = $.parseJSON(result);
								if (r.status) {
									d.dialog('destroy');
									easyuiBox.show(r.msg);
								}else{
									easyuiBox.alert(r.msg);
								}
								
							}
						});
					}
				},{
					text : '取消',
					iconCls : 'icon-cancel',
					handler : function(){
						var d = $(this).closest('.window-body');
						d.dialog('destroy');
					}
				}],
				onLoad : function(){
					/* if($('#checkQn').val() == 0){
						$(this).dialog('destroy');
						alert("请先添加问卷再添加问题！");
					} */
				},
				onClose : function(){
					$(this).dialog('destroy');
				}
			});
		});
		
		// 校验问题说明是否过长
		function checkQuestionDescription() {
			var q_description =  $('#q_description').val();
			if (q_description.length > 100) {
				easyuiBox.show('问题说明超过最大限制（100字）');
				return false;
			} else {
				return true;
			}
		}
		
		// *****************************************************
		// 选项相关操作的处理
		// *****************************************************
		
		//添加选项
		function vote_item_add_fn(qId){
			$('<div/>').dialog({
				href : '${pageContext.request.contextPath}/questionnaire/add_item.action?qId='+qId,
				width : 400,
				height : 200,
				iconCls : 'icon-table',
				modal : true,
				title : '添加问题',
				buttons : [{
					text : '保存',
					iconCls : 'icon-save',
					handler : function(){
						var d = $(this).closest('.window-body');
						$('#vote_item_add_form').form('submit', {
							url : '${pageContext.request.contextPath}/questionnaire/save_item.action',
							success : function(result){
								var r = $.parseJSON(result);
								$.messager.show({
									title : '提示',
									msg : r.msg
								});
								d.dialog('destroy');
							}
						
						});
					}
				}],
				onClose : function(){
					$(this).dialog('destroy');
				}
			});
		}
		
		//查看选项
		function questionView(questionid, questiontype){ 
			if (questiontype.substring(0,1) == "1")
				questiontype = 1;
			else if (questiontype.substring(0,1) == "2")
				questiontype = 2;
			else if (questiontype.substring(0,1) == "6")
				questiontype = 6;
		
			$('<div/>').dialog({
				href : '${pageContext.request.contextPath}/questionManage/toQuestionView.action?questionid='+questionid + '&questiontype=' + questiontype,
				width : 588,
				height : 444,
				iconCls : 'icon-table',
				modal : true,
				title : '查看问题',
				buttons : [{
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
		
		function div_display() {
			 $('.window-shadow').hide(); 
			// $('.window').css("top","400px");
		}
		window.setInterval("div_display()", 200);
		</script>

	</body>
</html>