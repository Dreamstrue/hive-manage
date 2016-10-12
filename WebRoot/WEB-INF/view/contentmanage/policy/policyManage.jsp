<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>政策法规管理</title>
<jsp:include page="../../common/inc.jsp"></jsp:include>
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
		<form action="javascript:void(0);" id="policy_manage_search_form" style="width: 100%">
			<table class="form" style="width: 100%;height:30px;">
				<tr>
					<td class="tt" width="80">标题</td>
					<td>
						<input class="inval" name="keys" style="width: 215px;" />
						<input type="button" class="easyui-linkbutton" value="查询" onclick="policy_manage_search_fn()">
						<input type="button" class="easyui-linkbutton" value="重置" onclick="policy_manage_clean_form_fn()">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div style="height:80%;width:100%;">
		<table id="PolicyLaw_datagrid"></table>
	</div>
	<script type="text/javascript">
	
   		$(function(){
   			$('#PolicyLaw_datagrid').datagrid({
   				url:'${pageContext.request.contextPath}/policy/dataGrid.action?menuid='+'${pid}',
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
   					field:'clawname',
   					title:'标题',
   					width:400
   				}]],
   				columns:[[{
   					field:'nlawid',
   					title:'ID',
   					width:50,
   					hidden:true
   				},{
   					field:'ncreateid',
   					title:'创建人',
   					width:80,
   					hidden:true
   				},{
   					field:'dcreatetime',
   					title:'创建时间',
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
   				},{
   					field:'cvalid',
   					title:'是否可用',
   					width:50,
   					formatter:function(value,row,index){
   						if(value=='1'){
   							return '<font>是</font>';
   						}else if(value=='0'){
							return '<font color="red">否</font>';					
   						}
   					}
   				}
   				<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canView || requestScope.canEdit || requestScope.canDelete || requestScope.canAudit}">
   				,{
   					field:'action',
   					title:'动作',
   					width:60,
   					formatter:function(value,row,index){
   						var html = "";
   						if(row.cauditstatus =='1'){
   							//审核通过
   							if(row.cvalid=='1'){//可用
   								<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
   								html += '<img onclick="policy_manage_del_fn(' + row.nlawid + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png"/>';
   								</c:if>
   								<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canView}">
   								html += '&nbsp;<img title="查看" onclick="policy_manage_detail_fn(' + row.nlawid + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/table/table.png"/>';
   								</c:if>
   							}
   						}else if(row.cauditstatus=='0'){
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
   							html += '<img title="修改" onclick="policy_manage_edit_fn(' + row.nlawid + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png"/>';
   							</c:if>
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canAudit}">
   							html += '&nbsp;<img onclick="policy_manage_approve_fn(' + row.nlawid + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/book_open.png"/>';
   							</c:if>
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
   							html += '&nbsp;<img onclick="policy_manage_del_fn(' + row.nlawid + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png"/>';
   							</c:if>
   						}else {
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
   							html += '<img title="修改" onclick="policy_manage_edit_fn(' + row.nlawid + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png"/>';
   							</c:if>
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
   							html += '&nbsp;<img onclick="policy_manage_del_fn(' + row.nlawid + ');" src="${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png"/>';
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
   						policy_manage_add_fn();
   					}
   				},'-',
   				</c:if>
   				{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#PolicyLaw_datagrid').datagrid('reload');
   					}
   				}]
   			});
   		});
   		
   		
   		function policy_manage_add_fn(){
   			location.href = '${pageContext.request.contextPath}/policy/add.action?id='+${pid};
   		}
   		
   		//修改
   		function policy_manage_edit_fn(id){
   			if(id!=undefined){
   				location.href = '${pageContext.request.contextPath}/policy/edit.action?id='+id+'&opType=update&backId=';
   			}
   		}
   		
   		
   		function policy_manage_detail_fn(id){
   			if(id!=undefined){
   				location.href = '${pageContext.request.contextPath}/policy/detail.action?id='+id+'&backId=';
   			}
   		}
   		
   		//审核
   		function policy_manage_approve_fn(id){
   			if(id!=undefined){
   				location.href = '${pageContext.request.contextPath}/policy/approve.action?id='+id+'&opType=approve';
   			}
   		}
   		
   		
   		
   		//删除
   		function policy_manage_del_fn(id){
   			if(id!=undefined){
   			   $('#PolicyLaw_datagrid').datagrid('selectRecord',id);
   				var record = $('#PolicyLaw_datagrid').datagrid('getSelected');
   				$.messager.confirm('提示','您确定要删除【'+record.clawname+'】?',function(b){
   					if(b){
   						var url = '${pageContext.request.contextPath}/policy/delete.action';
   						$.post(url,{id:id},function(data){
   							if(data.status){
   								$('#PolicyLaw_datagrid').datagrid('reload');
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
   		function policy_manage_back_fn(id){
   			if(id!=undefined){
   			   $('#PolicyLaw_datagrid').datagrid('selectRecord',id);
   				var record = $('#PolicyLaw_datagrid').datagrid('getSelected');
   				$.messager.confirm('提示','您确定恢复【'+record.clawname+'】?',function(b){
   					if(b){
   						var url = '${pageContext.request.contextPath}/policy/back.action';
   						$.post(url,{id:id},function(data){
   							if(data.status){
   								$('#PolicyLaw_datagrid').datagrid('reload');
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
   		
   		
   		function policy_manage_search_fn() {
			$('#PolicyLaw_datagrid').datagrid('load', serializeObject($('#policy_manage_search_form')));
		}
		function policy_manage_clean_form_fn() {
			$('#policy_manage_search_form input[class="inval"]').val('');
			$('#PolicyLaw_datagrid').datagrid('load', {});
		}
	</script>
</body>
</html>
