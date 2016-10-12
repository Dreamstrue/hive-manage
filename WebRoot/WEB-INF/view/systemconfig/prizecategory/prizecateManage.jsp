<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息类别管理</title>
<jsp:include page="../../common/inc.jsp"></jsp:include>
</head>
<body>
	<table id="prizeCategory_treegrid"></table>
	<script type="text/javascript">
	
   		$(function(){
   			$('#prizeCategory_treegrid').treegrid({
   				url:'${appPath}/prizeCate/treegrid.action',
   				idField:'id',
   				treeField:'text',
   				parentField:'parentId',
   				fit:true,
   				fitColumns:true,
   				pagination:true,
   				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50 ],
   				rownumbers:true,
   				border:false,
   				animate:false,
   				frozenColumns:[[{
   					field:'text',
   					title:'类别名称',
   					width:200
   				}]],
   				columns:[[{
   					field:'id',
   					title:'id',
   					align:'center',
   					width:50,
   					hidden:true
   				},{
   					field:'parentId',
   					title:'parentId',
   					align:'center',
   					width:50,
   					hidden:true
   				},{
   					field:'createTime',
   					title:'创建时间',
   					align:'center',
   					width:100/* ,
   					formatter:function(value,row,index){
   						return formatDate(value);
   					} */
   				},/* {
   					field:'cauditstatus',
   					title:'审核状态',
   					width:80,
   					formatter:function(value,row,index){
   						return back_zh_CN(value);
   					}
   				}, */{
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
   				},{
   					field:'remark',
   					title:'说明',
   			//		align:'center',
   					width:300/* ,
   					formatter:function(value,row,index){
   						return formatDate(value);
   					} */
   				}
   				<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canView || requestScope.canEdit || requestScope.canDelete || requestScope.canAudit}">
   				,{
   					field:'action',
   					title:'动作',
   					width:60,
   					align:'center',
   					formatter:function(value,row,index){
   						if(row.parentId!='0'){
	   						var html = "";
	   						html += '<img title="修改" onclick="pcate_manage_edit_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/pencil.png"/>';
	   						html += '&nbsp;<img title="删除" onclick="pcate_manage_del_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/cancel.png"/>';
	   						/* if(row.cauditstatus =='1'){
	   							//审核通过
	   							if(row.cvalid=='1'){//可用
	   								<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
	   								html += '<img onclick="policy_manage_del_fn(' + row.nlawid + ');" src="${appPath}/resources/images/extjs_icons/cancel.png"/>';
	   								</c:if>
	   								<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canView}">
	   								html += '&nbsp;<img title="查看" onclick="policy_manage_detail_fn(' + row.nlawid + ');" src="${appPath}/resources/images/extjs_icons/table.png"/>';
	   								</c:if>
	   							}
	   							if(row.cvalid=='0'){//不可用
	   								return formatString('<img onclick="policy_manage_back_fn(\'{0}\');" src="{1}"/>', row.nlawid, '${appPath}/resources/images/extjs_icons/tab_go.png');
	   							}
	   						}else if(row.cauditstatus=='0'){
	   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
	   							html += '<img alt="修改" onclick="policy_manage_edit_fn(' + row.nlawid + ');" src="${appPath}/resources/images/extjs_icons/pencil.png"/>';
	   							</c:if>
	   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canAudit}">
	   							html += '&nbsp;<img onclick="policy_manage_approve_fn(' + row.nlawid + ');" src="${appPath}/resources/images/extjs_icons/book_open.png"/>';
	   							</c:if>
	   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
	   							html += '&nbsp;<img onclick="policy_manage_del_fn(' + row.nlawid + ');" src="${appPath}/resources/images/extjs_icons/cancel.png"/>';
	   							</c:if>
	   						}else {
	   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
	   							html += '<img alt="修改" onclick="policy_manage_edit_fn(' + row.nlawid + ');" src="${appPath}/resources/images/extjs_icons/pencil.png"/>';
	   							</c:if>
	   							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
	   							html += '&nbsp;<img onclick="policy_manage_del_fn(' + row.nlawid + ');" src="${appPath}/resources/images/extjs_icons/cancel.png"/>';
	   							</c:if>
	   						} */
	   						return html;	
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
   						pcate_manage_add_fn();
   					}
   				},'-',
   				</c:if>
   				{
				text : '展开',
				iconCls : 'icon-redo',
				handler : function() {
					var node = $('#prizeCategory_treegrid').treegrid('getSelected');
					if (node) {
						$('#prizeCategory_treegrid').treegrid('expandAll', node.cid);
					} else {
						$('#prizeCategory_treegrid').treegrid('expandAll');
					}
				}
			},'-',{
				text : '折叠',
				iconCls : 'icon-undo',
				handler : function() {
					var node = $('#prizeCategory_treegrid').treegrid('getSelected');
					if (node) {
						$('#prizeCategory_treegrid').treegrid('collapseAll', node.cid);
					} else {
						$('#prizeCategory_treegrid').treegrid('collapseAll');
					}
				}
			},'-',{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#prizeCategory_treegrid').treegrid('reload');
   					}
   				}]
   			});
   		});
   		
   		
   		function pcate_manage_add_fn(){
   			$('<div/>').dialog({
			href : '${appPath}/prizeCate/add.action',
			width : 350,
			height : 220,
			modal : true,
			title : '添加奖品类别',
			buttons : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#pcate_manage_add_form').form('submit', {
						url : '${appPath}/prizeCate/insert.action',
						success : function(result) {
							var r = $.parseJSON(result);
							if (r.status) {
								/* $('#prizeCategory_treegrid').datagrid('insertRow', {
									index : 0,
									row : r.data
								}); */
								$('#prizeCategory_treegrid').treegrid('reload');
								d.dialog('destroy');
							}
							$.messager.show({
								title : '提示',
								msg : r.msg
							});
						}
					});
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			}
		});
   	}
   		
   		//修改
   		function pcate_manage_edit_fn(id){
   			$('<div/>').dialog({
			href : '${appPath}/prizeCate/edit.action?id='+id,
			width : 350,
			height : 220,
			modal : true,
			title : '修改奖品类别',
			buttons : [ {
				text : '修改',
				iconCls : 'icon-add',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#pcate_manage_edit_form').form('submit', {
						url : '${appPath}/prizeCate/update.action',
						success : function(result) {
							var r = $.parseJSON(result);
							if (r.status) {
								/* $('#prizeCategory_treegrid').datagrid('updateRow', {
									index : $('#prizeCategory_treegrid').datagrid('getRowIndex', id),
									row : r.data
								}); */
								$('#prizeCategory_treegrid').treegrid('reload');
								d.dialog('destroy');
							}
							$.messager.show({
								title : '提示',
								msg : r.msg
							});
						}
					});
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			}
		});
   	}
   		
   		//删除
   		function pcate_manage_del_fn(id){
   			if(id!=undefined){
   			   $('#prizeCategory_treegrid').treegrid('selectRecord',id);
   				var record = $('#prizeCategory_treegrid').treegrid('getSelected');
   				$.messager.confirm('提示','您确定要删除【'+record.text+'】?',function(b){
   					if(b){
   						var url = '${appPath}/prizeCate/delete.action';
   						$.post(url,{id:id},function(data){
   							if(data.status){
   								$('#prizeCategory_treegrid').treegrid('reload');
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
	</script>
</body>
</html>
