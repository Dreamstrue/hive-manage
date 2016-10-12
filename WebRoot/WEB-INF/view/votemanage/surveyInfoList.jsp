<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>意见征集信息管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../common/inc.jsp" />
  </head>
  
  <body>
	  	<table id="vote_questionnaire_datagrid"></table>
	
		<div data-options="region:'center',border:false">
			<table id="vote_manage_datagrid"></table>
		</div>
  </body>
  
  
  <script type="text/javascript">
		// 投票问卷列表元素Id
		var dataGridId = "vote_questionnaire_datagrid";
		
		$(function(){
		
			$('#'+dataGridId).datagrid({
				url : '${pageContext.request.contextPath}/survey/listSurveyInfo.action',
				fit : true,
				fitColumns : true,
				border : false,
				pagination : true,
				rownumbers : true,
				idField : 'qnId',
				pageSize : 20,
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
					text : '增加意见征集',
					iconCls : 'icon-add',
					handler : function() {
						addSurveyInfo();
					}
				},
				</c:if>
				{
					text : '刷新页面',
					iconCls : 'icon-reload',
					handler : function() {
						$('#'+dataGridId).datagrid('load');
					}
				}],
				columns : [[{
					field : 'id',
					title : '问卷ID',
					hidden : true
				},{
					field : 'subject',
					title : '标题',
					width : 80,
					sortable : true,
					formatter : function(value, row, index) {
						var html="";
						html+="<div style='width:400px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;'>"+value+"</div>";
						return html;
					}
				},{
					field : 'preface',
					title : '前言 ',
					width : 100
				},{
					field : 'question',
					title : '问卷题目',
					width : 100
				},{
					field : 'validBegin',
					title : '有效期起',
					width : 40
				},{
					field : 'validEnd',
					title : '有效期止',
					width : 40
				},{
					field : 'createTime',
					title : '创建时间',
					width : 75
				}
				<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canView || requestScope.canEdit || requestScope.canDelete}">
				,{
				field : 'action',
				title : '操作',
				width : 70,
				align:'center',
				formatter : function(value, row, index) {
					var html="";
					<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canView}">
					html+="<img onclick=\"viewSurveyInfo('"+row.id+"');\" title='查看意见' src='${pageContext.request.contextPath}/resources/images/extjs_icons/table/table.png'/>";
					</c:if>
					<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
					html+="&nbsp;<img onclick=\"editSurveyInfo('"+row.id+"');\" title='编辑意见' src='${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png'/>";
					</c:if>
					<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
					html+="&nbsp;<img onclick=\"delSurveyInfo('"+row.id+"');\" title='删除意见' src='${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png'/>";
					</c:if>
					return html;
				}
				}
				</c:if>
				]]
			});
			
		});// End Of 页面加载完后初始化
		
	//==================================================
	
	/*
		添加意见征集
	*/
	function addSurveyInfo(){
		/* var url = "${pageContext.request.contextPath}/survey/toSurveyInfoAdd.action";
		var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
		parent.layout_center_addTabFun({title:'添加问卷信息', content:content, closable:true}); */
		
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/survey/toSurveyInfoAdd.action',
			width :750,
			height : 400,
			modal : true,
			title : '添加意见征集',
			buttons : [{
						text : '保存',
						iconCls : 'icon-save',
						handler : function(){
							var d = $(this).closest('.window-body');
							$('#dataForm').form('submit', {
								url : '${pageContext.request.contextPath}/survey/saveSurveyInfo.action',
								success : function(result){
									var r = $.parseJSON(result);
									if(r.status){
										$('#'+dataGridId).datagrid('load');
										d.dialog('destroy');
										easyuiBox.show(r.msg);
										
									}else{
										easyuiBox.alert(r.msg);
									}
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
	}// End Of addSurveyInfo()
	
	/*
		查看意见征集
	*/
	function viewSurveyInfo(id){
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/survey/toSurveyInfoView.action?surveyInfoId='+id,
			width :750,
			height : 400,
			modal : true,
			title : '查看意见征集',
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
		
	}// End Of viewSurveyInfo()
	
	/*
		编辑意见征集
	*/
	function editSurveyInfo(id){
		/* var url = "${pageContext.request.contextPath}/survey/toSurveyInfoEdit.action?surveyInfoId="+id;
		var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
		parent.layout_center_addTabFun({title:'编辑问卷信息', content:content, closable:true}); */
		
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/survey/toSurveyInfoEdit.action?surveyInfoId='+id,
			width :750,
			height : 400,
			modal : true,
			title : '编辑意见征集',
			buttons : [{
						text : '保存',
						iconCls : 'icon-save',
						handler : function(){
							var d = $(this).closest('.window-body');
							$('#dataForm').form('submit', {
								url : '${pageContext.request.contextPath}/survey/updateSurveyInfo.action',
								success : function(result){
									var r = $.parseJSON(result);
									if(r.status){
										$('#'+dataGridId).datagrid('load');
										d.dialog('destroy');
										easyuiBox.show(r.msg);
										
									}else{
										easyuiBox.alert(r.msg);
									}
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
		
	}// End Of editSurveyInfo()
	
	/*
		删除意见征集
	*/
	function delSurveyInfo(id){
		$.messager.confirm('确认', '您是否要删除吗？', function(r) {
			if (r) {
				$.post("${pageContext.request.contextPath}/survey/delSurveyInfo.action",{surveyInfoId : id},function(result){
					if (result.status) {
						$('#'+dataGridId).datagrid('load');
						$('#'+dataGridId).datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
						easyuiBox.show(result.msg);
					}else{
						easyuiBox.show(result.msg);
					}
				},'json');
			}
		});
	}// End Of delSurveyInfo()

  </script>
</html>