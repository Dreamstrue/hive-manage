<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String zxtUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ "/zxt"; // 项目访问全路径，形如：http://localhost:8080/zyzlcxw
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
	<jsp:include page="../common/inc.jsp" />
  </head>
  
  <body>
   	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 60px;overflow: hidden;">
			<form action="javascript:void(0);" id="disclose_manage_search_form" style="width: 100%">
				<table class="tableForm" style="width: 100%">
				
					<tr>
						<td>
						    <b>内容</b> <input class="inval" type="text" name="content" id="content"  style='width:168px'  class='intxt' />&nbsp;&nbsp;
							<b>开始时间-起</b> <input class="inval" type='text' name='begintime' id='createTime' style='width:168px'  class='intxt' readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  />&nbsp;&nbsp;
							<b>开始时间-止</b> <input class="inval" type='text' name='endtime' id='createTime' style='width:166px'  class='intxt' readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  />&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="disclose_manage_search_fn();">查询</a>
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
	<script type="text/javascript">
		// 投票问卷列表元素Id
		var questionnaireGridId = "vote_questionnaire_datagrid";
		
		$(function(){
		
			$('#'+questionnaireGridId).datagrid({
				url : '${pageContext.request.contextPath}/discloseManage/listDisclose.action',
				fit : true,
				fitColumns : true,
				border : false,
				pagination : true,
				rownumbers : true,
				idField : 'surveyid',
				pageSize : 20,
				pageList : [10, 20, 30, 40, 50, 60],
				sortName : 'createtime',
				sortOrder : 'desc',
				checkOnSelect : false,
				selectOnCheck : false,
				singleSelect:true,
				nowrap : false,
				toolbar : [ 
				
				{
					text : '批量审核',
					iconCls : 'icon-add',
					handler : function() {
						checkDiscloseInfo();
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
			
				{
					text : '刷新',
					iconCls : 'icon-reload',
					handler : function() {
						$('#'+questionnaireGridId).datagrid('load');
					}
				}],
				columns : [[
				{
					field : 'title',
					title : '主题',
					width : 80
				},{
					field : 'id',
					title : '主键ID',
					hidden : true
				},{
					field : 'content',
					title : '内容',
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
					field : 'createTime',
					title : '爆料时间',
					width : 50
				},{
					field : 'auditStatus',
					title : '审核状态',
					width : 40,
					formatter:function(value,row,index){
   						return back_zh_CN(value);
   					}
				},{
					field : 'shieldStatus',
					title : '是否屏蔽',
					width : 40,
					formatter:function(value,row,index){
					  var html="";
   						if(row.shieldStatus == "0"){
   						    html="<font color='green'>未屏蔽</font>";
   						}else{
   						    html="<font color='red'>屏蔽</font>";
   						}
   						return html;
   					}
				}
				<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canView || requestScope.canEdit || requestScope.canDelete}">
				,{
				field : 'action',
				title : '操作',
				width : 40,
				align:'center',
				formatter : function(value, row, index) {
					var html="";
					if (row.auditStatus == '0') {
						<%-- 这种动作字符串拼接方法比较好，方便对每个操作加权限。示范页面！ --%>	
						//html+="<img onclick=\"vote_questionnaire_find_fn('"+row.surveyid+"');\" title='查看问卷' src='${pageContext.request.contextPath}/static/img/extjs_icons/table/table.png'/>";
						
	 					html += '&nbsp;<img title="审核" onclick="survey_manage_approve_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/book_open.png"/>';
						return html;
					} else if (row.auditStatus == '1') {
						<%-- 这种动作字符串拼接方法比较好，方便对每个操作加权限。示范页面！ --%>	
						//html+="<img onclick=\"vote_questionnaire_find_fn('"+row.surveyid+"');\" title='查看问卷' src='${pageContext.request.contextPath}/static/img/extjs_icons/table/table.png'/>";
						if(row.shieldStatus=='0'){
							html+="&nbsp;<img onclick=\"vote_question_del_fn('"+row.id+"');\" title='屏蔽' src='${pageContext.request.contextPath}/resources/images/extjs_icons/delete.png'/>";
						}else{
							html+="&nbsp;<img onclick=\"vote_question_back_fn('"+row.id+"');\" title='恢复' src='${pageContext.request.contextPath}/resources/images/extjs_icons/eye.png'/>";
						}
						return html;
					}
				}
				}
				</c:if>
				]]
			});
			
		});// End Of 页面加载完后初始化
		
		// 搜索
		function disclose_manage_search_fn() {
			$('#vote_questionnaire_datagrid').datagrid('load', serializeObject($('#disclose_manage_search_form')));
		}
		// 审核
		function survey_manage_approve_fn(surveyId) {
			location.href = '${pageContext.request.contextPath}/discloseManage/approve.action?id='+surveyId;
		//	Open_Dialog();
		}
		// 重置查询表单
		function resetSearchForm() {
			$('#disclose_manage_search_form input[class="inval"]').val('');
			$('#auditstatus').combobox('clear');
			$('#vote_questionnaire_datagrid').datagrid('load', {});
		}
		
			//屏蔽信息
		function vote_question_del_fn(discId){
			$.messager.confirm('确认', '您确定要屏蔽此信息吗？', function(r) {
				if (r) {
					$.post("${pageContext.request.contextPath}/discloseManage/deleteDiscloseInfo.action",{discId : discId},function(result){
						if (result.status) {
							$('#'+questionnaireGridId).datagrid('load');
						//	$('#'+questionnaireGridId).datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
							easyuiBox.show(result.msg);
						}else{
							easyuiBox.show(result.msg);
						}
					},'json');
				}
			});
		}
			
			//恢复信息
		function vote_question_back_fn(discId){
			$.messager.confirm('确认', '您确定要屏恢复此信息吗？', function(r) {
				if (r) {
					$.post("${pageContext.request.contextPath}/discloseManage/backDiscloseInfo.action",{discId : discId},function(result){
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
		function checkDiscloseInfo(){
		/*$.post('${pageContext.request.contextPath}/discloseManage/checkDiscloseInfo.action', {discId : "1001"},function (result) {
			        if (result.status) {
			        	easyuiBox.show(result.msg);
			        	$('#'+questionnaireGridId).datagrid('load',{});
			        }
			    })*/
			$.ajax({
						url : '${pageContext.request.contextPath}/discloseManage/checkDiscloseInfo.action',
						data : {},
						dataType : 'json',
						success : function(result) {
							if (result.status) {
								easyuiBox.show(result.msg);
			        	        $('#'+questionnaireGridId).datagrid('load',{});
							}
							
						}
					});	    
		}
	</script>
  </body>
</html>
