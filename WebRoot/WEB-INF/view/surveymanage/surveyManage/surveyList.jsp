<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String zxtUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();//+ "/zxt"部署项目时候屏蔽;; // 项目访问全路径，形如：http://localhost:8080/zyzlcxw
	//String zxtUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ "/zxt";
	request.setAttribute("zxtUrl", zxtUrl);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>问卷列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../common/inc.jsp" />
  </head>
  
  <body>
	<div class="easyui-layout" data-options="fit : true,border : false" >
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 94px;overflow: hidden; >
			<form action="javascript:void(0);" id="survey_manage_search_form" style="width: 100%">
				<table class="tableForm" style="width: 100%">
				<tr>
					<td style="text-align: left;margin-left:20px;">
						<b>问卷名称</b> <input name="subject" class="inval" style="width: 188px;" />&nbsp;&nbsp;
						<b>审核状态</b>
						<select id="auditstatus" name="auditstatus" class="easyui-combobox" style="width:188px;">
							<option value="">全部</option>
							<option value="0">未审核</option>
							<option value="1">审核通过</option>
							<option value="2">审核未通过</option>
						</select>&nbsp;&nbsp;
						<b>发布人</b> <input name="createName" class="inval" style="width: 188px;" />
					</td>
				</tr>
				<tr>
					<td>
						<b>开始时间-起</b> <input class="inval" type='text' name='begintime' id='createTime' style='width:168px'  class='intxt' readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  />&nbsp;&nbsp;
						<b>结束时间-止</b> <input class="inval" type='text' name='endtime' id='createTime' style='width:166px'  class='intxt' readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  />&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="survey_manage_search_fn();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="resetSearchForm()">重置</a>
					</td>
				</tr>
			</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id=vote_questionnaire_datagrid></table>
		</div>
	</div>
	
	<!-- 弹出窗口：审核问卷 --> 
	<div id="mydialog" style="display:none;padding:5px;width:400px;height:200px;" title="问卷审核"> 
		<form id="form1" action="">
		<input type="hidden" id="ids" name="ids" value="" />
			<table>
			<tr>
			<td>审核结果：</td><td><select id="auditstatus" name="auditstatus">
					  <option value ="1">通过</option>
					  <option value ="2">不通过</option>
					</select></td>
			</tr>
			<tr>
			<td>审核意见：</td><td><textarea rows="5" cols="30" id="auditopinion" style="width:297px;height:80px;font-size: 12px;" name="auditopinion"></textarea> </td>
			</tr>
			</table>
		</form>
	</div>
	
	<!-- 弹出窗口：延长问卷 --> 
	<div id="mydialog2" style="display:none;padding:5px;width:300px;height:110px;" title="延长问卷"> 
		<form id="form2" action="">
		<input type="hidden" id="surveyId" name="surveyId" value="" />
			<table>
			<tr>
			<td><b>问卷结束时间：</b></td>
			<td>
			<input class="inval" type='text' name='endTimeStr' id='endTimeStr' style='width:168px' class="easyui-validatebox" data-options="required:true" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  />
			</td>
			</tr>
			</table>
		</form>
	</div>
	
  </body>
  
  
  <script type="text/javascript">
		// 投票问卷列表元素Id
		var questionnaireGridId = "vote_questionnaire_datagrid";
		
		$(function(){
		
			$('#'+questionnaireGridId).datagrid({
				url : '${pageContext.request.contextPath}/surveyManage/listSurvey.action',
				fit : true,
				fitColumns : true,
				border : false,
				pagination : true,
				rownumbers : true,
				idField : 'surveyid',
				pageSize : 10,
				pageList : [10, 20, 30, 40, 50, 60],
				sortName : 'qnCTime',
				sortOrder : 'desc',
				checkOnSelect : false,
				selectOnCheck : false,
				singleSelect:true,
				nowrap : false,
				toolbar : [ 
				<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canAdd}">
				{
					text : '添加问卷',
					iconCls : 'icon-add',
					handler : function() {
						addSurvey();
					}
				},'-',
				/**
				{
					text : '批量审核',
					iconCls : 'icon-add',
					handler : function() {
						addSurvey();
					}
				},'-',
				*/
				</c:if>
				{
					text : '刷新',
					iconCls : 'icon-reload',
					handler : function() {
						$('#'+questionnaireGridId).datagrid('load');
					}
				}],
				columns : [[{
					field : 'id',
					title : '投票问卷ID',
					hidden : true
				},{
					field : 'subject',
					title : '标题',
					width : 110,
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
					field : 'industryName',
					title : '所属行业',
					width : 50,
					hidden : false
				},{
					field : 'categoryName',
					title : '所属类别',
					width : 30,
					hidden : false
				},{
					field : 'begintime',
					title : '开始时间',
					width : 40
				},{
					field : 'endtime',
					title : '结束时间',
					width : 40
				},{
					field : 'tags',
					title : '标签',
					width : 40
				},{
					field : 'participatenum',
					title : '参与人数',
					width : 35
				},{
					field : 'createName',
					title : '发布人',
					width : 35,
					hidden:true
				},{
					field : 'hasCode',
					title : '二维码标识',
					width : 35,
					hidden:true
				},{
					field : 'isShow',
					title : '允许公示',
					width : 30,
					formatter:function(value,row,index){
   						if(value=='1'){
   							return '是';
   						}else if(value=='0'){
   							return '否';
   						}
   					}
				},{
					field : 'auditstatus',
					title : '审核状态',
					width : 35,
					formatter:function(value,row,index){
   						return back_zh_CN(value);
   					}
				},{
					field : 'status',
					title : '当前状态',
					width : 35,
					formatter : function(value, row, index) {
						var html="";
						if (row.auditstatus == "0") {
							html = "未审核";
						} else if (row.auditstatus == "1") {
							if (row.status == "1") {
								if (ParseDate(row.begintime + " 00:00:00") < new Date() && ParseDate(row.endtime + " 23:59:59") > new Date())
									html = "<span id='statusText' style='color:green'>投票进行中</span>";
								else if (ParseDate(row.begintime + " 00:00:00") > new Date())
									html = "<span style='color:black'>投票未开始</span>";
								else
									html = "<span style='color:#6B006B' title='投票已经过了结束时间'>投票已过期</span>";
							} else {
								html = "<span style='color:blue' title='投票被手动关闭'>投票已关闭</span>";
							}
							return html;
						} else if (row.auditstatus == "2") {
							html = "<span style='color:red'>审核未通过</span>";
						} 
						return html;
					}
				}
				<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canView || requestScope.canEdit || requestScope.canDelete}">
				,{
				field : 'action',
				title : '操作',
				width : 80,
				align:'center',
				formatter : function(value, row, index) {
					var html="";
					html+="<img onclick=\"vote_setting_find_fn('"+row.id+"');\" title='问卷设置' src='${pageContext.request.contextPath}/resources/images/extjs_icons/cog_edit.png'/>";
					if (row.auditstatus == '0') {
						<%-- 这种动作字符串拼接方法比较好，方便对每个操作加权限。示范页面！ --%>	
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
						html+="&nbsp;<img onclick=\"vote_questionnaire_edit_fn('"+row.id+"');\" title='查看/编辑问卷' src='${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png'/>";
						</c:if>
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canAudit}">
	 					html += '&nbsp;<img title="审核" onclick="survey_manage_approve_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/book_open.png"/>';
	 					</c:if>
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
						html+="&nbsp;<img onclick=\"vote_question_del_fn('"+row.id+"');\" title='删除问卷' src='${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png'/>";
						</c:if>
						if(row.isShow=='0'){
							html+="&nbsp;<img onclick=\"vote_show_true_fn('"+row.id+"');\" title='公示问卷' src='${pageContext.request.contextPath}/resources/images/extjs_icons/eye.png'/>";
						}else if(row.isShow=='1'){
							html+="&nbsp;<img onclick=\"vote_show_false_fn('"+row.id+"');\" title='屏蔽公示问卷' src='${pageContext.request.contextPath}/resources/images/extjs_icons/delete.png'/>";
						}
						return html;
					} else if (row.auditstatus == '1') {
						<%-- 这种动作字符串拼接方法比较好，方便对每个操作加权限。示范页面！ --%>	
						//html+="<img onclick=\"vote_questionnaire_find_fn('"+row.surveyid+"');\" title='查看问卷' src='${pageContext.request.contextPath}/static/img/extjs_icons/table/table.png'/>";
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canView}">
						html+="&nbsp;<img onclick=\"survey_count_fn('"+row.id+"');\" title='查看投票结果' src='${pageContext.request.contextPath}/resources/images/extjs_icons/table/table.png'/>";
						</c:if>
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
						html+="&nbsp;<img onclick=\"survey_user_fn('"+row.id+"');\" title='评价记录' src='${pageContext.request.contextPath}/resources/images/extjs_icons/book_go.png'/>";
						</c:if>
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
						html+="&nbsp;<img onclick=\"survey_preview_fn('"+row.id+"');\" title='预览问卷' src='${pageContext.request.contextPath}/resources/images/preview.png'/>";
						</c:if>
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
						html+="&nbsp;<img onclick=\"vote_question_del_fn('"+row.id+"');\" title='删除问卷' src='${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png'/>";
						</c:if>
						if(row.isShow=='0'){
							html+="&nbsp;<img onclick=\"vote_show_true_fn('"+row.id+"');\" title='公示问卷' src='${pageContext.request.contextPath}/resources/images/extjs_icons/eye.png'/>";
						}else if(row.isShow=='1'){
							html+="&nbsp;<img onclick=\"vote_show_false_fn('"+row.id+"');\" title='屏蔽公示问卷' src='${pageContext.request.contextPath}/resources/images/extjs_icons/delete.png'/>";
						}
						
						if (row.status == "1") {
							if (ParseDate(row.begintime + " 00:00:00") < new Date() && ParseDate(row.endtime + " 23:59:59") > new Date())
								html+="&nbsp;<img onclick=\"survey_manage_stop_fn('"+row.id+"');\" title='关闭问卷' src='${pageContext.request.contextPath}/resources/images/extjs_icons/control/control_stop.png'/>";
							else if (ParseDate(row.endtime + " 00:00:00") < new Date())
								html+="&nbsp;<img onclick=\"survey_manage_delay_fn('"+row.id+"');\" title='延长问卷' src='${pageContext.request.contextPath}/resources/images/extjs_icons/time_add.png'/>";
						} else {
							html+="&nbsp;<img onclick=\"survey_manage_start_fn('"+row.id+"');\" title='开启问卷' src='${pageContext.request.contextPath}/resources/images/extjs_icons/control/control_play.png'/>";
						}
						html+="&nbsp;<img onclick=\"create_twocode_fn('"+row.id+"','"+row.hasCode+"');\" title='创建二维码' src='${pageContext.request.contextPath}/resources/images/extjs_icons/barcode.png'/>";
						html += '&nbsp;<img title="手机推送" onclick="pushinfo_manage_add_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/email/email_go.png"/>';
						return html;
					} else if (row.auditstatus == '2') {
						<%-- 这种动作字符串拼接方法比较好，方便对每个操作加权限。示范页面！ --%>	
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
						html+="&nbsp;<img onclick=\"vote_questionnaire_edit_fn('"+row.id+"');\" title='查看/编辑问卷' src='${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png'/>";
						</c:if>
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
						html+="&nbsp;<img onclick=\"vote_question_del_fn('"+row.id+"');\" title='删除问卷' src='${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png'/>";
						</c:if>
						return html;
					}
				}
				}
				</c:if>
				]]
			});
			
		});// End Of 页面加载完后初始化
		
	//==================================================
	
	/*
		添加投票问卷
	*/
	function addSurvey(){
	
		var url = "${pageContext.request.contextPath}/surveyManage/toSurveyAdd.action";
		var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
		parent.layout_center_addTabFun({title:'添加问卷', content:content, closable:true});
		
		/* $('<div/>').dialog({
			href : '${pageContext.request.contextPath}/surveyManage/toSurveyAdd.action',
			width :830,
			height : 500,
			modal : true,
			title : '增加问卷',
			buttons : [{
				text : '关闭',
				handler : function(){
					var d = $(this).closest('.window-body');
					$('#'+questionnaireGridId).datagrid('load');
					d.dialog('destroy');
				}
			}],
			onClose : function(){
				$('#'+questionnaireGridId).datagrid('load');
				$(this).dialog('destroy');
			},
			onLoad:function(){
				//鼠标经过,触发日期选择框弹出
				$(".datebox").mouseover(function(){
					$(this).prev().combo('showPanel');
				});
			}
		}); */
	}// End Of addSurvey()
	
	/*
		添加问题
	*/
	function addQuestion(surveyid){
	
		var url = "${pageContext.request.contextPath}/questionManage/toQuestionAdd.action?surveyid=" + surveyid;
		var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
		parent.layout_center_addTabFun({title:'添加问题', content:content, closable:true});
		
		/* $('<div/>').dialog({
			href : '${pageContext.request.contextPath}/surveyManage/toSurveyAdd.action',
			width :830,
			height : 500,
			modal : true,
			title : '增加问卷',
			buttons : [{
				text : '关闭',
				handler : function(){
					var d = $(this).closest('.window-body');
					$('#'+questionnaireGridId).datagrid('load');
					d.dialog('destroy');
				}
			}],
			onClose : function(){
				$('#'+questionnaireGridId).datagrid('load');
				$(this).dialog('destroy');
			},
			onLoad:function(){
				//鼠标经过,触发日期选择框弹出
				$(".datebox").mouseover(function(){
					$(this).prev().combo('showPanel');
				});
			}
		}); */
	}// End Of addSurvey()
	
	
	function toQuestionList(surveyid) {
		var url = "${pageContext.request.contextPath}/questionManage/toQuestionList.action?surveyid="+surveyid;
		var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
		parent.layout_center_addTabFun({title:'问题列表', content:content, closable:true});
	}
	
	//==================================================
	//展示统计结果
	function survey_count_fn(surveyid){
		var url = "${zxt_url}/surveyCountManage/toSurveyCount.action?surveyId=" + surveyid;
		//alert(url);
			// 固定窗口大小
			var winWidth = 700;
			var winHeight = 800;
			var moveLeft = (screen.width - winWidth)/2 -10; // screen.width 表示屏幕宽度，差值为弹出窗口左上角距离屏幕左边距离
			var moveTop = (screen.height - winHeight)/2 - 36; // 上面的 -10 是减去浏览器右侧滚动条的宽度，这儿的 -36是减去弹出窗口地址栏及标题的高度
			//打开一个空白窗口，并初始化大小 
		 	var imgwin=window.open(url ,'img','left=' + moveLeft + ',top=' + moveTop + ',width=' + winWidth + ',height=' + winHeight + ',location=no,scrollbars=yes') 
			imgwin.focus() //使窗口聚焦，成为当前窗口 
	}
	//展示评价列表
	function survey_user_fn(surveyid){
		var url = "${zxt_url}/surveyCountManage/toSurveyAnswer.action?surveyid=" + surveyid;
		//alert(url);
			// 固定窗口大小
			var winWidth = 700;
			var winHeight = 800;
			var moveLeft = (screen.width - winWidth)/2 -10; // screen.width 表示屏幕宽度，差值为弹出窗口左上角距离屏幕左边距离
			var moveTop = (screen.height - winHeight)/2 - 36; // 上面的 -10 是减去浏览器右侧滚动条的宽度，这儿的 -36是减去弹出窗口地址栏及标题的高度
			//打开一个空白窗口，并初始化大小 
		 	var imgwin=window.open(url ,'img','left=' + moveLeft + ',top=' + moveTop + ',width=' + winWidth + ',height=' + winHeight + ',location=no,scrollbars=yes') 
			imgwin.focus() //使窗口聚焦，成为当前窗口 
	}
	
	//==================================================
	//修改问卷
	function vote_questionnaire_edit_fn(surveyId) {
		
		var url = "${pageContext.request.contextPath}/surveyManage/toSurveyEdit.action?surveyId="+surveyId;
		var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
		parent.layout_center_closeTab('编辑问卷'); // 先关闭再打开，否则如果之前有别的问卷处于编辑状态，就不会刷新了
		parent.layout_center_addTabFun({title:'编辑问卷', content:content, closable:true});
		
		/* $('<div/>').dialog({
			href : '${pageContext.request.contextPath}/surveyManage/editSurvey.action?surveyid='+surveyid,
			width : 830,
			height : 535,
			modal : true,
			title : '编辑问卷信息',
			buttons : [ {
				text : '关闭',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#'+questionnaireGridId).datagrid('reload');
					d.dialog('destroy');
				}
			} ],
			onClose : function() {
				$('#'+questionnaireGridId).datagrid('load');
				$(this).dialog('destroy');
			},
			onLoad:function(){
				//鼠标经过,触发日期选择框弹出
				$(".datebox").mouseover(function(){
					$(this).prev().combo('showPanel');
				});
			}
		}); */
	}
	
	//查看问卷
	function vote_questionnaire_find_fn(surveyid2){
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/questionnaire/findQn.action?surveyid='+surveyid2,
			width : 800,
			height : 510,
			iconCls : 'icon-table',
			modal : true,
			title : '查看问卷',
			buttons :[{
				text : '关闭',
				iconCls : 'icon-save',
				handler : function(){
					var d = $(this).closest('.window-body');
					d.dialog('destroy');
				}
			}],
			onClose : function(){
				var d = $(this).closest('.window-body');
				$("#view_dialog").dialog('destroy');
			}
		});
	}
	
	//删除问卷
	function vote_question_del_fn(surveyid){
		$.messager.confirm('确认', '您确定要删除该问卷吗？', function(r) {
			if (r) {
				$.post("${pageContext.request.contextPath}/surveyManage/deleteSurvey.action",{surveyId : surveyid},function(result){
					if (result.status) {
						$('#'+questionnaireGridId).datagrid('load');
						$('#'+questionnaireGridId).datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
						easyuiBox.show(result.msg);
					}else{
						easyuiBox.show(result.msg);
					}
				},'json');
			}
		});
	}
	
	//公示问卷
	function vote_show_true_fn(surveyid){
		$.messager.confirm('确认', '您确定要公示该问卷吗？', function(r) {
			if (r) {
				$.post("${pageContext.request.contextPath}/surveyManage/showSurvey.action",{surveyId : surveyid},function(result){
					if (result.status) {
						$('#'+questionnaireGridId).datagrid('load');
						easyuiBox.show(result.msg);
					}else{
						easyuiBox.show(result.msg);
					}
				},'json');
			}
		});
	}
	//屏蔽问卷公示
	function vote_show_false_fn(surveyid){
		$.messager.confirm('确认', '您确定要屏蔽公示该问卷吗？', function(r) {
			if (r) {
				$.post("${pageContext.request.contextPath}/surveyManage/shieldSurvey.action",{surveyId : surveyid},function(result){
					if (result.status) {
						$('#'+questionnaireGridId).datagrid('load');
						easyuiBox.show(result.msg);
					}else{
						easyuiBox.show(result.msg);
					}
				},'json');
			}
		});
	}
	//开启问卷
	function survey_manage_start_fn(surveyid){
		$.messager.confirm('确认', '您确定要开启该问卷吗？', function(r) {
			if (r) {
				$.post("${pageContext.request.contextPath}/surveyManage/startSurvey.action",{surveyId : surveyid},function(result){
					if (result.status) {
						$('#'+questionnaireGridId).datagrid('load');
						easyuiBox.show(result.msg);
					}else{
						easyuiBox.show(result.msg);
					}
				},'json');
			}
		});
	}
	//关闭问卷
	function survey_manage_stop_fn(surveyid){
		$.messager.confirm('确认', '您确定要关闭该问卷吗？', function(r) {
			if (r) {
				$.post("${pageContext.request.contextPath}/surveyManage/stopSurvey.action",{surveyId : surveyid},function(result){
					if (result.status) {
						$('#'+questionnaireGridId).datagrid('load');
						easyuiBox.show(result.msg);
					}else{
						easyuiBox.show(result.msg);
					}
				},'json');
			}
		});
	}
	// 审核
	function survey_manage_approve_fn(surveyId) {
		$('#ids').val(surveyId);
		Open_Dialog();
	}
	// 延期
	function survey_manage_delay_fn(surveyId) {
		$('#surveyId').val(surveyId);
		Open_Dialog2();
	}

	function Open_Dialog() {
			$('#mydialog').show(); 
			$('#mydialog').dialog({ 
				collapsible: true, 
				maximizable: true, 
				modal:true,
				buttons: [
					{ 
					text: '提交', 
					iconCls: 'icon-ok', 
					handler: checkAdd 
					}, 
					{ 
					text: '取消', 
					handler: function() { 
					$('#mydialog').dialog('close'); 
					} 
					}
				] 
			}); 
		}
		
		function Open_Dialog2() {
			$("#endTimeStr").val("");
			$('#mydialog2').show(); 
			$('#mydialog2').dialog({ 
				modal:true,
				buttons: [
					{ 
					text: '提交', 
					iconCls: 'icon-ok', 
					handler: delayEndTime 
					}, 
					{ 
					text: '取消', 
					handler: function() { 
						$("#endTimeStr").val("");
					$('#mydialog2').dialog('close'); 
					} 
					}
				] 
			}); 
		}
		
		function checkAdd(){
			if($("#form1").form('validate')){
				var param=$('#form1').formSerialize();
				$.post('${appPath}/surveyManage/auditSurvey.action',param, function (re) {
			        if (re.status) {
			        	easyuiBox.show(re.msg);
			        	$('#mydialog').dialog('close');
			        	$('#'+questionnaireGridId).datagrid('load');
			        }
			    }, 'json');
		    }
		}
		
		function delayEndTime(){
			var param=$('#form2').formSerialize(); // 形如：surveyId=304&endTimeStr=2015-09-08
			if (param.substring(param.lastIndexOf("=") + 1) == "") {
				alert("请设置问卷结束时间！");
				return false;
			}
			$.post('${appPath}/surveyManage/delaySurvey.action',param, function (re) {
		        if (re.status) {
		        	easyuiBox.show(re.msg);
		        	$('#mydialog2').dialog('close');
		        	$('#'+questionnaireGridId).datagrid('load');
		        } else {
		        	easyuiBox.show(re.msg);
		        }
		    }, 'json');
		}
		
		
		//设置问卷
		function vote_setting_find_fn(id){
			$('<div/>').dialog({
			    id:'set_dialog',
				href : '${pageContext.request.contextPath}/surveyManage/toSurveySet.action?surveyId='+id,
				width : 400,
				height : 260,
				iconCls : 'icon-table',
				modal : true,
				title : '问卷设置',
				buttons : [{
					text : '保存',
					iconCls : 'icon-save',
					handler : function(){
						var d = $(this).closest('.window-body');
						$('#survey_set_form').form('submit', {
							url : '${pageContext.request.contextPath}/surveyManage/setSurvey.action?surveyId='+id,
							success : function(result) {
								var r = $.parseJSON(result);
								if (r.status) {
								    $("#set_dialog").dialog('destroy');
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
					    $("#set_dialog").dialog('destroy');
					}
				}],
				onLoad : function(){
				},
				onClose : function(){
					$(this).dialog('destroy');
				}
			});
		}
		
		
		
		
		
		function survey_preview_fn(surveyId) {
			var url = "${zxt_url}/survey/toVotePage.action?id=" + surveyId;
			// 固定窗口大小
			var winWidth = 700;
			var winHeight = 800;
			var moveLeft = (screen.width - winWidth)/2 -10; // screen.width 表示屏幕宽度，差值为弹出窗口左上角距离屏幕左边距离
			var moveTop = (screen.height - winHeight)/2 - 36; // 上面的 -10 是减去浏览器右侧滚动条的宽度，这儿的 -36是减去弹出窗口地址栏及标题的高度
			//打开一个空白窗口，并初始化大小 
		 	var imgwin=window.open(url ,'img','left=' + moveLeft + ',top=' + moveTop + ',width=' + winWidth + ',height=' + winHeight + ',location=no,scrollbars=yes') 
			imgwin.focus() //使窗口聚焦，成为当前窗口 
		}
		
		// 搜索
		function survey_manage_search_fn() {
			$('#vote_questionnaire_datagrid').datagrid('load', serializeObject($('#survey_manage_search_form')));
		}
		
		// 重置查询表单
		function resetSearchForm() {
			$('#survey_manage_search_form input[class="inval"]').val('');
			$('#auditstatus').combobox('clear');
			$('#vote_questionnaire_datagrid').datagrid('load', {});
		}
		
		
		//生产二维码图片
		function create_twocode_fn(id,hasCode){
			//已经生产二维码图片
			if(hasCode == '1'){
				$.messager.confirm('确认', '该问卷已存在二维码图片，是否重新生成？', function(b) {
					if (b) {
						$.post("${pageContext.request.contextPath}/surveyManage/createTwoCode.action",{id : id},function(r){
							if (r.status) {
								$('#'+questionnaireGridId).datagrid('reload');
								$.messager.show({
									title : '提示',
									msg : r.msg,
									timeout: 8000
								});
							}
						},'json');
					}
				});
			}else{
				$.post("${pageContext.request.contextPath}/surveyManage/createTwoCode.action",{id : id},function(r){
					if (r.status) {
						$('#'+questionnaireGridId).datagrid('reload');
						$.messager.show({
							title : '提示',
							msg : r.msg,
							timeout: 8000
						});
					}
				},'json');
			}
		}
		
		// 打开添加推送弹出框
		function pushinfo_manage_add_fn(id) {
			$('<div/>').dialog({
			    id:'view_dialog',
				href : '${pageContext.request.contextPath}/pushinfo/add.action?objectType=s_survey&objectId='+id,
				width : 400,
				height : 310,
				modal : true,
				title : '添加推送消息',
				buttons : [ {
					text : '确定',
					iconCls : 'icon-ok',
					handler : function() {
						var d = $(this).closest('.window-body');
						$('#pushinfo_manage_add_form').form('submit', {
							url : '${pageContext.request.contextPath}/pushinfo/insert.action',
							success : function(result) {
								try {
									var r = $.parseJSON(result);
									if (r.status) {
										//$('#pushinfo_manage_datagrid').datagrid('insertRow', {
										//	index : 0,
										//	row : r.data
										//});
										$('#pushinfo_manage_datagrid').datagrid('reload');
										d.dialog('destroy');
										$("#view_dialog").dialog('destroy');
									}
									$.messager.show({
										title : '提示',
										msg : r.msg
									});
									// 等待两秒后，跳转至推送管理页面
									setTimeout(function(){
										var surveyObj = r.data;
										var surveyId = surveyObj.id;
										var url = "${pageContext.request.contextPath}/pushinfo/manage.action?id=1005&pid=4";
										var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
										parent.layout_center_addTabFun({title:'推送管理', content:content, closable:true});
										parent.layout_center_refreshTab('推送管理'); // 可能推送管理页面已打开，那就刷新一下
										// 然后关闭添加页面
										//parent.layout_center_closeTab('添加问卷');
									}, 2000);  
								} catch (e) {
									$.messager.alert('提示', result);
								}
							}
						});
					}
				} ],
				onClose : function() {
					$(this).dialog('destroy');
				}
			});
		}
		
	// 将字符串（带时分秒）解析成日期值
	function ParseDate(s) {    
	    var dv, reg = /^\d\d\d\d-\d\d-\d\d \d\d:\d\d:\d\d$/gi;
	    if (!reg.test(s)) {
	        window.alert("日期值格式错误!");
	        return null;
	    } else {
	        dv = new Date(Date.parse(s.replace(/-/g, "/")));
	        /* 比较日期值的各部分是否相同, 防止输入错误日期值, 如2013-08-33这种/ */
	
	        if (dv.getFullYear() != eval(s.substring(0, 4)) || dv.getMonth() + 1 != eval(s.substring(5, 7)) || dv.getDate() != eval(s.substring(8, 10))
	
	            || dv.getHours() != eval(s.substring(11, 13)) || dv.getMinutes() != eval(s.substring(14, 16)) || dv.getSeconds() != eval(s.substring(17, 19))
	      ) {
	
	            window.alert("日期值错误!");
	            return null;
	        }
	    }
	    return dv;
	}
  </script>
</html>