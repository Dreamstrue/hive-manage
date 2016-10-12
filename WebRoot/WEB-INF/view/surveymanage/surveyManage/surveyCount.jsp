<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>问卷统计</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../common/inc.jsp" />
  </head>
  
  <body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 94px;overflow: hidden;">
			<form action="javascript:void(0);" id="survey_manage_search_form" style="width: 100%">
				<table class="tableForm" style="width: 100%">
				<tr>
					<td style="text-align: left;margin-left:20px;">
						<b>问卷名称</b> <input name="subject" class="inval" style="width: 188px;" />&nbsp;&nbsp;
						<b>问卷状态</b>
						<select id="auditstatus" name="auditstatus" class="easyui-combobox" style="width:188px;">
							<option value="">全部</option>
							<option value="0">未审核</option>
							<option value="1">审核通过/投票进行中</option>
							<option value="2">审核未通过</option>
							<option value="4">已关闭</option>
						</select>&nbsp;&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						<b>开始时间-起</b> <input class="inval" type='text' name='begintime' id='createTime' style='width:168px'  class='intxt' readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  />&nbsp;&nbsp;
						<b>开始时间-止</b> <input class="inval" type='text' name='endtime' id='createTime' style='width:166px'  class='intxt' readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  />&nbsp;
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
				pageSize : 20,
				pageList : [10, 20, 30, 40, 50, 60],
				sortName : 'qnCTime',
				sortOrder : 'desc',
				checkOnSelect : false,
				selectOnCheck : false,
				singleSelect:true,
				nowrap : false,
				columns : [[{
					field : 'id',
					title : '投票问卷ID',
					hidden : true
				},{
					field : 'subject',
					title : '标题',
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
					field : 'industryName',
					title : '所属行业',
					width : 50,
					hidden : false
				},{
					field : 'categoryName',
					title : '所属类别',
					width : 50,
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
					width : 80
				},{
					field : 'participatenum',
					title : '参与人数',
					width : 35
				},{
					field : 'createName',
					title : '发布人',
					width : 35
				},{
					field : 'auditstatus',
					title : '审核状态',
					width : 40,
					formatter:function(value,row,index){
   						return back_zh_CN(value);
   					}
				},{
					field : 'status',
					title : '当前状态',
					width : 40,
					formatter : function(value, row, index) {
						var html="";
						if (row.auditstatus == "0") {
							html = "未审核";
						} else if (row.auditstatus == "1") {
							if (row.status == "1") {
								html = "<span style='color:green'>投票进行中</span>";
							} else {
								html = "<span style='color:blue'>已终止</span>";
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
				width : 70,
				align:'center',
				formatter : function(value, row, index) {
					var html="";
					if (row.auditstatus == '0') {
						<%-- 这种动作字符串拼接方法比较好，方便对每个操作加权限。示范页面！ --%>	
						//html+="<img onclick=\"vote_questionnaire_find_fn('"+row.surveyid+"');\" title='查看问卷' src='${pageContext.request.contextPath}/static/img/extjs_icons/table/table.png'/>";
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
						html+="&nbsp;<img onclick=\"vote_questionnaire_edit_fn('"+row.id+"');\" title='查看/编辑问卷' src='${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png'/>";
						</c:if>
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canAudit}">
	 					html += '&nbsp;<img title="审核" onclick="survey_manage_approve_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/book_open.png"/>';
	 					</c:if>
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
						html+="&nbsp;<img onclick=\"vote_question_del_fn('"+row.id+"');\" title='删除问卷' src='${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png'/>";
						</c:if>
						return html;
					} else if (row.auditstatus == '1') {
						<%-- 这种动作字符串拼接方法比较好，方便对每个操作加权限。示范页面！ --%>	
						//html+="<img onclick=\"vote_questionnaire_find_fn('"+row.surveyid+"');\" title='查看问卷' src='${pageContext.request.contextPath}/static/img/extjs_icons/table/table.png'/>";
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canView}">
						html+="&nbsp;<img onclick=\"vote_poll_find_fn('"+row.id+"');\" title='查看投票结果' src='${pageContext.request.contextPath}/resources/images/extjs_icons/table/table.png'/>";
						</c:if>
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
						html+="&nbsp;<img onclick=\"survey_preview_fn('"+row.id+"');\" title='预览问卷' src='${pageContext.request.contextPath}/resources/images/preview.png'/>";
						</c:if>
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
						html+="&nbsp;<img onclick=\"vote_question_del_fn('"+row.id+"');\" title='删除问卷' src='${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png'/>";
						</c:if>
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
	function vote_poll_find_fn(surveyid){
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/surveyManage/toShowVoteResult.action?surveyid='+surveyid,
			width : 600,
			height : 510,
			modal : true,
			title : '统计表格',
			buttons : [{
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


		
	//==================================================
	//修改问卷
	function vote_questionnaire_edit_fn(surveyId) {
		
		var url = "${pageContext.request.contextPath}/surveyManage/toSurveyEdit.action?surveyId="+surveyId;
		var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
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
				d.dialog('destroy');
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

	// 审核
	function survey_manage_approve_fn(surveyId) {
		$('#ids').val(surveyId);
		Open_Dialog();
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
		
		function survey_preview_fn(surveyId) {
			var url = "${basePath}/surveyManage/toVotePage.action?id=" + surveyId;
			// 固定窗口大小
			var winWidth = 600;
			var winHeight = 800;
			var moveLeft = (screen.width - winWidth)/2 -10; // screen.width 表示屏幕宽度，差值为弹出窗口左上角距离屏幕左边距离
			var moveTop = (screen.height - winHeight)/2 - 36; // 上面的 -10 是减去浏览器右侧滚动条的宽度，这儿的 -36是减去弹出窗口地址栏及标题的高度
			//打开一个空白窗口，并初始化大小 
		 	var imgwin=window.open(url ,'img','left=' + moveLeft + ',top=' + moveTop + ',width=' + winWidth + ',height=' + winHeight + ',location=no') 
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
  </script>
</html>