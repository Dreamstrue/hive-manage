<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息公告管理</title>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<style type="text/css">
body{margin:0px;padding:0px;}
.searchDiv{
	overflow: hidden;
	height: 60px; 
	border-bottom: 1px solid #99CCFF;
}
.tt{
	font-size: 12px;
	text-align: right;
}
</style>
</head>

<body>
	<script type="text/javascript">
   		$(function(){
   			$('#news_datagrid').datagrid({
   				url:'${appPath}/news/dataGrid.action',
   				idField:'id',
   				fit:true,
   				fitColumns:true,
   				pagination:true,
   				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50 ],
   				rownumbers:true,
   				border:false,
   				animate:false,
   				frozenColumns:[[{
   					field:'title',
   					title:'标题',
   					width:400
   				}]],
   				columns:[[{
   					field:'id',
   					title:'ID',
   					width:50,
   					hidden:true
   				},{
   					field:'createId',
   					title:'创建人',
   					width:80,
   					hidden:true
   				},{
   					field:'infoCateName',
   					title:'信息类别',
   					width:120,
   					align:'center',
   					hidden:true
   				},{
   					field:'createTime',
   					title:'创建时间',
   					align:'center',
   					width:100/* ,
   					formatter:function(value,row,index){
   						return formatDate(value);
   					} */
   				},{
   					field:'auditStatus',
   					title:'审核状态',
   					width:80,
   					align:'center',
   					formatter:function(value,row,index){
   						return back_zh_CN(value);
   					}
   				},{
   					field:'valid',
   					title:'是否可用',
   					align:'center',
   					width:50,
   					formatter:function(value,row,index){
   						if(value=='1'){
   							return '<font>是</font>';
   						}else if(value=='0'){
							return '<font color="red">否</font>';					
   						}
   					}
   				}
   				<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit || requestScope.canDelete || requestScope.canView || requestScope.canAudit}">
   				,{
   					field:'action',
   					title:'动作',
   					width:60,
   					align:'center',
   					formatter:function(value,row,index){
   						var html = "";
   						if(row.auditStatus =='1'){
   							//审核通过
   							if(row.valid=='1'){//可用
   								<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canView}">
   								html += '<img title="查看" onclick="news_manage_detail_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/table/table.png"/>';
   								</c:if>
   								<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
   								html += '&nbsp;<img title="删除" onclick="news_manage_del_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/cancel.png"/>';
   								html += '&nbsp;<img title="手机推送" onclick="pushinfo_manage_add_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/email/email_go.png"/>';
   								</c:if>
   							}
   							if(row.valid=='0'){//不可用
   								return formatString('<img onclick="news_manage_back_fn(\'{0}\');" src="{1}"/>', row.id, '${appPath}/resources/images/extjs_icons/tab_go.png');
   							}
   						}else if(row.auditStatus=='0'){
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
   							html += '<img title="修改" onclick="news_manage_edit_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/pencil.png"/>';
   							</c:if>
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canAudit}">
   							html += '&nbsp;<img title="审核" onclick="news_manage_approve_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/book_open.png"/>';
   							</c:if>
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
   							html += '&nbsp;<img title="删除" onclick="news_manage_del_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/cancel.png"/>';
   							</c:if>
   						}else {
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
   							html += '<img title="修改" onclick="news_manage_edit_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/pencil.png"/>';
   							</c:if>
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
   							html += '&nbsp;<img title="删除" onclick="news_manage_del_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/cancel.png"/>';
   							</c:if>
   						}
   						return html;
   					}
   				}
   				</c:if>
   				]],
   				toolbar:[
   				<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canAdd}">
   				{
   					text:'新增',
   					iconCls:'icon-add',
   					handler:function(){
   						news_manage_add_fn();
   					}
   				},'-',
   				</c:if>
   				{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#news_datagrid').datagrid('reload');
   					}
   				}]
   			});
   		});
   		
   		
   		function news_manage_add_fn(){
   			location.href = '${appPath}/news/add.action';
   		}
   		
   		//修改
   		function news_manage_edit_fn(id){
   			if(id!=undefined){
   				location.href = '${appPath}/news/edit.action?id='+id+'&opType=update';
   			}
   		}
   		
   		
   		function news_manage_detail_fn(id){
   			location.href = '${appPath}/news/detail.action?id='+id;
   		}
   		
   		//审核
   		function news_manage_approve_fn(id){
   			if(id!=undefined){
   				location.href = '${appPath}/news/approve.action?id='+id+'&opType=approve';
   			}
   		}
   		
   		
   		
   		//删除
   		function news_manage_del_fn(id){
   			if(id!=undefined){
   			   $('#news_datagrid').datagrid('selectRecord',id);
   				var record = $('#news_datagrid').datagrid('getSelected');
   				$.messager.confirm('提示','您确定要删除【'+record.title+'】?',function(b){
   					if(b){
   						var url = '${appPath}/news/delete.action';
   						$.post(url,{id:id},function(data){
   							if(data.status){
   								$('#news_datagrid').datagrid('reload');
   							}
   							$.messager.show({
   								msg:data.msg,
   								title:'提示'
   							});
   						},"json");
   					}
   				});
   			}
   				
   		}
   		
   		function news_manage_search_fn() {
			$('#news_datagrid').datagrid('load', serializeObject($('#news_manage_search_form')));
		}
		function cleanForm() {
			$('#news_manage_search_form input[class="inval"]').val('');
			$('#infoCateId').combobox('clear');
			$('#auditStatus').combobox('clear');
			$('#news_datagrid').datagrid('load', {});
		}
		
		// 打开添加推送弹出框
		function pushinfo_manage_add_fn(id) {
			$('<div/>').dialog({
				href : '${pageContext.request.contextPath}/pushinfo/add.action?objectType=info_news&objectId='+id,
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
	</script>
	<div class="searchDiv">
		<form action="javascript:void(0);" id="news_manage_search_form" >
			<table class="form" style="width: 100%;height:60px;">
				<tr>
					<td class="tt" width="80">标题</td>
					<td width="160"><input class="inval" name="title" style="width:150px;"></td>
					<td class="tt" width="80">创建日期</td>
					<td width="160">
						<input class="inval" type='text' name='createTime' id='createTime' style='width:150px'  class='intxt' readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  />
					</td>
				</tr>
				<tr>
					<td class="tt" >审核状态</td>
					<td>
						<select id="auditStatus" name="auditStatus" class="easyui-combobox" style="width:150px;">
							<option value=""></option>
							<option value="0">0-未审核</option>
							<option value="1">1-审核通过</option>
							<option value="2">2-审核不通过</option>
						</select>
					</td>
					<td class="tt">信息类别</td>
					<td>
						<input  id="infoCateId" name="infoCateId" class="easyui-combotree" data-options="url:'${appPath}/infoCate/allInfoTree.action',parentField:'parentId',lines:true " style="width:150px;" />
					</td>
					<td>
						<input type="button" class="easyui-linkbutton" value="查询" onclick="news_manage_search_fn();">
						<input type="button" class="easyui-linkbutton" value="重置" onclick="cleanForm();">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div style="height:86%;width:100%;">
		<table id="news_datagrid"></table>
	</div>
</body>
</html>
