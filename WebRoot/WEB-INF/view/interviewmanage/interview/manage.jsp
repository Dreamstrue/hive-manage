<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<style type="text/css">
body{margin:0px;padding:0px;}
.searchDiv{
	overflow: hidden;
	height: 30px; 
	border-bottom: 1px solid #99CCFF;
}
.tt{
	font-size: 12px;
	text-align: right;
}
</style>
</head>

<body>
<div class="searchDiv">
	<form action="javascript:void(0);" id="interview_manage_search_form" style="width: 100%">
		<table class="form" style="width: 100%">
			<tr>
				<th class="tt" width="80">标题</th>
				<td>
					<input class="inval" name="keys" style="width: 215px;" />
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="interview_manage_search_fn();">查询</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="interview_manage_clean_form_fn();">重置</a>
				</td>
			</tr>
		</table>
	</form>
</div>
<div style="height:80%;width:100%;">
	<table id="interview_datagrid"></table>
</div>
	<script type="text/javascript">
	
   		$(function(){
   			$('#interview_datagrid').datagrid({
   				url:'${pageContext.request.contextPath}/interview/dataGrid.action',
   				idField:'nintonlid',
   				fit:true,
   				fitColumns:true,
   				pagination:true,
   				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50 ],
   				rownumbers:true,
   				border:false,
   				singleSelect:true, // 列表页只能单选
   				animate:false,
   				frozenColumns:[[{
   					field:'csubject',
   					title:'主题',
   					width:400
   				}]],
   				columns:[[{
   					field:'nintonlid',
   					title:'ID',
   					width:50,
   					hidden:true
   				},{
   					field:'dinterviewtime',
   					title:'访谈时间',
   					width:100/* ,
   					formatter:function(value,row,index){
   						return formatDate(value);
   					} */
   				},{
   					field:'cguests',
   					title:'访谈嘉宾',
   					width:80
   				},{
   					field:'ncreateid',
   					title:'创建人',
   					width:80,
   					hidden:true
   				},{
   					field:'cduration',
   					title:'预计时长',
   					width:100/* ,
   					formatter:function(value,row,index){
   						return formatDate(value);
   					} */
   				},{
   					field:'cauditstatus',
   					title:'审核状态',
   					width:80,
   					formatter:function(value,row,index){
   						return back_zh_CN(value);
   					}
   				}
   				<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canView || requestScope.canEdit || requestScope.canDelete || requestScope.canAudit || requestScope.canLive}">
   				,{
   					field:'action',
   					title:'动作',
   					width:60,
   					formatter:function(value,row,index){
   						<%-- 原来这种采用占位符的方式，加权限判断非常麻烦（尤其是按钮较多时），采用下面那种拼接 html 的方式就很容易了。
   						if(row.cvalid=='1'){//可用
   							if (row.cauditstatus =='1'){
   								return formatString('<img title="删除" onclick="interview_manage_del_fn(\'{0}\');" src="{1}"/>&nbsp;<img title="查看" onclick="interview_manage_detail_fn(\'{2}\');" src="{3}"/>&nbsp;<img title="直播" onclick="interview_manage_live_fn(\'{4}\');" src="{5}"/>', row.nintonlid, '${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png', row.nintonlid, '${pageContext.request.contextPath}/resources/images/extjs_icons/book_open.png', row.nintonlid, '${pageContext.request.contextPath}/resources/images/extjs_icons/cd/cd.png');
   							} else if (row.cauditstatus=='0'){
   								return formatString('<img title="修改" onclick="interview_manage_edit_fn(\'{0}\');" src="{1}"/>&nbsp;<img title="删除" onclick="interview_manage_del_fn(\'{2}\');" src="{3}"/>&nbsp;<img title="审核" onclick="interview_manage_approve_fn(\'{4}\');" src="{5}"/>&nbsp;<img title="查看" onclick="interview_manage_detail_fn(\'{6}\');" src="{7}"/>', row.nintonlid, '${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png', row.nintonlid, '${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png', row.nintonlid, '${pageContext.request.contextPath}/resources/images/extjs_icons/comment_edit.png', row.nintonlid, '${pageContext.request.contextPath}/resources/images/extjs_icons/book_open.png');
		   					} else {
		   						return formatString('<img title="修改" onclick="interview_manage_edit_fn(\'{0}\');" src="{1}"/>&nbsp;<img title="删除" onclick="interview_manage_del_fn(\'{2}\');" src="{3}"/>', row.nintonlid, '${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png', row.nintonlid, '${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png');
		   					}
   						}
  						if(row.cvalid=='0'){//不可用
  							return formatString('<img onclick="interview_manage_back_fn(\'{0}\');" src="{1}"/>', row.nintonlid, '${pageContext.request.contextPath}/resources/images/extjs_icons/tab_go.png');
  						}
  						--%>
   						var html="";
   						if(row.cvalid=='1'){//可用
   							if (row.cauditstatus =='1') {
   								<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
   								html += '<img title="删除" onclick="interview_manage_del_fn(' + row.nintonlid + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png"/>';
   								</c:if>
   								<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canView}">
   								html += '&nbsp;<img title="查看" onclick="interview_manage_detail_fn(' + row.nintonlid + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/book_open.png"/>';
   								</c:if>
   								<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canLive}">
   								html += '&nbsp;<img title="直播" onclick="interview_manage_live_fn(' + row.nintonlid + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/cd/cd.png"/>';
   								</c:if>
   								return html;
   							} else if (row.cauditstatus=='0') {
   								<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
   								html += '<img title="修改" onclick="interview_manage_edit_fn(' + row.nintonlid + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png"/>';
   								</c:if>
   								<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
   								html += '&nbsp;<img title="删除" onclick="interview_manage_del_fn(' + row.nintonlid + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png"/>';
   								</c:if>
   								<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canAudit}">
   								html += '&nbsp;<img title="审核" onclick="interview_manage_approve_fn(' + row.nintonlid + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/comment_edit.png"/>';
   								</c:if>
   								<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canView}">
   								html += '&nbsp;<img title="查看" onclick="interview_manage_detail_fn(' + row.nintonlid + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/book_open.png"/>';
   								</c:if>
   								return html;
		   					} else {
		   						<%-- 这个还使用占位符的方式，加权限判断，以示对比（2个按钮还不算太麻烦，如果动作超过3个，用这种判断就要写很多 c:if 了 --> 主要就是为了应付那个占位符{0}、{1}） --%>
		   						<c:if test="${(sessionScope.isAdmin == '1') || (requestScope.canEdit && requestScope.canDelete)}"><%-- 同时拥有修改和删除权限 --%>
		   						return formatString('<img title="修改" onclick="interview_manage_edit_fn(\'{0}\');" src="{1}"/>&nbsp;<img title="删除" onclick="interview_manage_del_fn(\'{2}\');" src="{3}"/>', row.nintonlid, '${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png', row.nintonlid, '${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png');
		   						</c:if>
   								<c:if test="${(sessionScope.isAdmin == '1') || (requestScope.canEdit && !requestScope.canDelete)}"><%-- 只有修改权限 --%>
		   						return formatString('<img title="修改" onclick="interview_manage_edit_fn(\'{0}\');" src="{1}"/>', row.nintonlid, '${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png');
		   						</c:if>
   								<c:if test="${(sessionScope.isAdmin == '1') || (!requestScope.canEdit && requestScope.canDelete)}"><%-- 只有删除权限 --%>
		   						return formatString('<img title="删除" onclick="interview_manage_del_fn(\'{0}\');" src="{1}"/>', row.nintonlid, '${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png');
		   						</c:if>
		   					}
   						}
   						if(row.cvalid=='0'){//不可用
   							return formatString('<img onclick="interview_manage_back_fn(\'{0}\');" src="{1}"/>', row.nintonlid, '${pageContext.request.contextPath}/resources/images/extjs_icons/tab_go.png');
   						}
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
   						interview_manage_add_fn();
   					}
   				},'-',
   				</c:if>
   				{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#interview_datagrid').datagrid('reload');
   					}
   				}]
   			});
   		});
   		
   		
   		function interview_manage_add_fn(){
   			location.href = '${pageContext.request.contextPath}/interview/add.action';
   		}
   		
   		//修改
   		function interview_manage_edit_fn(id){
   			if(id!=undefined){
   				location.href = '${pageContext.request.contextPath}/interview/edit.action?id='+id
   			}
   		}
   		
   		function interview_manage_detail_fn(id){
   			location.href = '${pageContext.request.contextPath}/interview/detail.action?id='+id+'&opType=detail';
   		}
   		
   		//审核
   		function interview_manage_approve_fn(id){
   			if(id!=undefined){
   				location.href = '${pageContext.request.contextPath}/interview/detail.action?id='+id+'&opType=approve';
   			}
   		}
   		
   		//直播
   		function interview_manage_live_fn(id){
   			if(id!=undefined){
   				location.href = '${pageContext.request.contextPath}/interviewContent/manage.action?interviewId='+id;
   			}
   		}
   		
   		
   		//删除
   		function interview_manage_del_fn(id){
   			if(id!=undefined){
   			   $('#interview_datagrid').datagrid('selectRecord',id);
   				var record = $('#interview_datagrid').datagrid('getSelected');
   				$.messager.confirm('提示','您确定要删除【'+record.csubject+'】?',function(b){
   					if(b){
   						var url = '${pageContext.request.contextPath}/interview/delete.action';
   						$.post(url,{id:id},function(data){
   							if(data.status){
   								$('#interview_datagrid').datagrid('reload');
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
   		
   		
   		/*重新设置为可用状态*/
   		function interview_manage_back_fn(id){
   			if(id!=undefined){
   			   $('#interview_datagrid').datagrid('selectRecord',id);
   				var record = $('#interview_datagrid').datagrid('getSelected');
   				$.messager.confirm('提示','您确定恢复【'+record.title+'】?',function(b){
   					if(b){
   						var url = '${pageContext.request.contextPath}/interview/back.action';
   						$.post(url,{id:id},function(data){
   							if(data.status){
   								$('#interview_datagrid').datagrid('reload');
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
   		
   		
   		function interview_manage_search_fn() {
			$('#interview_datagrid').datagrid('load', serializeObject($('#interview_manage_search_form')));
		}
		function interview_manage_clean_form_fn() {
			$('#interview_manage_search_form input').val('');
			$('#interview_datagrid').datagrid('load', {});
		}
   		
	</script>
</body>
</html>
