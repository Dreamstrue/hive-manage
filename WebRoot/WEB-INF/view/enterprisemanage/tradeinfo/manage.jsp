<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>产品供求</title>
<jsp:include page="../../common/inc.jsp"></jsp:include>
</head>

<body>
	<div  style="height: 35px;overflow: hidden; margin-top: -10px;">
					<form action="javascript:void(0);" id="trade_manage_search_form" style="width: 100%">
						<table class="form" style="width: 100%">
							<tr>
								<th style="width: 200px; font-size: 12px;">标题关键字(可模糊查询)</th>
								<td>
									<input name="keys" style="width: 215px;" />
									<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="trade_manage_search_fn();">过滤条件</a>
									<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="trade_manage_clean_form_fn();">清空条件</a>
								</td>
							</tr>
						</table>
					</form>
	</div>
	<div style="height:420px;width:100%;">
		<table id="trade_datagrid"></table>
	</div>
	<script type="text/javascript">
	
   		$(function(){
   			$('#trade_datagrid').datagrid({
   				url:'${pageContext.request.contextPath}/tradeInfo/dataGrid.action',
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
   					field:'ctitle',
   					title:'供求标题',
   					width:400
   				}]],
   				columns:[[{
   					field:'id',
   					title:'ID',
   					width:50,
   					hidden:true
   				},{
   					field:'cproductname',
   					title:'产品名称',
   					width:80
   				},{
   					field:'cproductnum',
   					title:'产品数量',
   					width:40
   				},{
   					field:'cbs',
   					title:'供求类别',
   					width:80,
   					formatter:function(value,row,index){
   						if(value=='1'){
   							return '供应';
   						}
   						if(value=='2'){
   							return '求购';
   						}
   					}
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
   				},{
   					field:'action',
   					title:'动作',
   					width:60,
   					formatter:function(value,row,index){
   						if(row.cauditstatus =='1'){
   							//审核通过
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}"><%-- 拥有删除权限 --%>
   							return formatString('<img title="查看" onclick="trade_manage_detail_fn(\'{0}\');" src="{1}"/>&nbsp;<img onclick="trade_manage_del_fn(\'{2}\');" src="{3}"/>',row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/table.png', row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png');
   							</c:if>
   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}"><%-- 没有删除权限 --%>
   							return formatString('<img title="查看" onclick="trade_manage_detail_fn(\'{0}\');" src="{1}"/>',row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/table.png');
   							</c:if>
   						}else if(row.cauditstatus=='0'){
   							<c:if test="${(sessionScope.isAdmin == '1') || (requestScope.canAudit && requestScope.canDelete)}"><%-- 同时拥有审核和删除权限 --%>
   							return formatString('<img alt="审核" onclick="trade_manage_approve_fn(\'{0}\');" src="{1}"/>&nbsp;<img onclick="trade_manage_del_fn(\'{2}\');" src="{3}"/>', row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/book_open.png', row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png');
   							</c:if>
   							<c:if test="${(sessionScope.isAdmin == '1') || (requestScope.canAudit && !requestScope.canDelete)}"><%-- 只有审核权限 --%>
   							return formatString('<img alt="审核" onclick="trade_manage_approve_fn(\'{0}\');" src="{1}"/>', row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/book_open.png');
   							</c:if>
   							<c:if test="${(sessionScope.isAdmin == '1') || (!requestScope.canAudit && requestScope.canDelete)}"><%-- 只有删除权限 --%>
   							return formatString('<img onclick="trade_manage_del_fn(\'{0}\');" src="{1}"/>', row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png');
   							</c:if>
   						}else {
	   						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}"><%-- 拥有删除权限 --%>
	   						return formatString('<img title="查看" onclick="trade_manage_detail_fn(\'{0}\');" src="{1}"/>&nbsp;<img onclick="trade_manage_del_fn(\'{2}\');" src="{3}"/>',row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/table.png', row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png');
	   						</c:if>
	   						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}"><%-- 没有删除权限 --%>
	   						return formatString('<img title="查看" onclick="trade_manage_detail_fn(\'{0}\');" src="{1}"/>',row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/table.png');
	   						</c:if>	
   						}	
   					}
   				}]],
   				toolbar:[{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#trade_datagrid').datagrid('reload');
   					}
   				}]
   			});
   		});
   		
   		
   		function trade_manage_detail_fn(id){
   			location.href = '${pageContext.request.contextPath}/tradeInfo/detail.action?id='+id;
   		}
   		
   		//审核
   		function trade_manage_approve_fn(id){
   			if(id!=undefined){
   				location.href = '${pageContext.request.contextPath}/tradeInfo/approve.action?id='+id;
   			}
   		}
   		
   		
   		
   		//删除
   		function trade_manage_del_fn(id){
   			if(id!=undefined){
   			   $('#trade_datagrid').datagrid('selectRecord',id);
   				var record = $('#trade_datagrid').datagrid('getSelected');
   				$.messager.confirm('提示','您确定要删除【'+record.title+'】?',function(b){
   					if(b){
   						var url = '${pageContext.request.contextPath}/tradeInfo/delete.action';
   						$.post(url,{id:id},function(data){
   							if(data.status){
   								$('#trade_datagrid').datagrid('reload');
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
   		
   		
   		
   		
   		function trade_manage_search_fn() {
			$('#trade_datagrid').datagrid('load', serializeObject($('#trade_manage_search_form')));
		}
		function trade_manage_clean_form_fn() {
			$('#trade_manage_search_form input').val('');
			$('#trade_datagrid').datagrid('load', {});
		}
   		
   		
   	//格式化日期样式
	
		
		
	</script>
</body>
</html>
