<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>热点专题管理</title>
<jsp:include page="../../common/inc.jsp"></jsp:include>
</head>

<body>
	<table id="special_treegrid"></table>
	<script type="text/javascript">
	
   		$(function(){
   			$('#special_treegrid').treegrid({
   				url:'${pageContext.request.contextPath}/special/treegrid.action',
   				idField:'id',
   				treeField:'text',
   				parentField:'pid',
   				fit:true,
   				fitColumns:true,
   			//	pagination:true,
   			//	pageSize : 10,
		//		pageList : [ 10, 20, 30, 40, 50 ],
   				rownumbers:true,
   		//		nowrap:false,
   				border:false,
   				animate:false,
   				frozenColumns:[[{
   					field:'text',
   					title:'专题名称',
   					width:260
   				}]],
   				columns:[[{
   					field:'id',
   					title:'菜单ID',
   					width:50,
   					hidden:true
   				},{
   					field:'pid',
   					title:'父级菜单ID',
   					width:60,
   					hidden:false
   				},{
   					field:'isortorder',
   					title:'排序',
   					width:50
   				},{
   					field:'ncreateid',
   					title:'创建人',
   					width:80,
   					hidden:true
   				},{
   					field:'dcreatetime',
   					title:'创建时间',
   					width:100
   				}/* ,{
   					field:'auditStatus',
   					title:'审核状态',
   					width:80,
   					formatter:function(value,row,index){
   						return back_zh_CN(value);
   					}
   				} */,{
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
   				<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit || requestScope.canDelete}"><%-- 拥有修改或删除权限，则显示动作列 --%>
   				,{
   					field:'action',
   					title:'动作',
   					width:150,
   					formatter:function(value,row,index){
   							if(row.pid=='0'){
   								if(row.cvalid=='1'){//可用
   									<%-- 动作少的页面可以采用这种方式判断，如果动作超过3个，用这种判断就要写很多 if 了（主要就是为了应付那个占位符{0}、{1}）。这就需要改变原来的字符串拼接方式，参考：/view/votemanage/questionnaireList.jsp --%>
   									<c:if test="${(sessionScope.isAdmin == '1') || (requestScope.canEdit && requestScope.canDelete)}"><%-- 同时拥有修改和删除权限 --%>
   									return formatString('<img alt="修改" onclick="special_manage_edit_fn(\'{0}\');" src="{1}"/>&nbsp;<img onclick="special_manage_del_fn(\'{2}\');" src="{3}"/>', row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png', row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png');
   									</c:if>
   									<c:if test="${(sessionScope.isAdmin == '1') || (requestScope.canEdit && !requestScope.canDelete)}"><%-- 只有修改权限 --%>
   									return formatString('<img alt="修改" onclick="special_manage_edit_fn(\'{0}\');" src="{1}"/>', row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png');
   									</c:if>
   									<c:if test="${(sessionScope.isAdmin == '1') || (!requestScope.canEdit && requestScope.canDelete)}"><%-- 只有删除权限 --%>
   									return formatString('<img onclick="special_manage_del_fn(\'{0}\');" src="{1}"/>', row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png');
   									</c:if>
   								}
   							}else {
   								if(row.cvalid=='1'){//可用
   									<c:if test="${(sessionScope.isAdmin == '1') || (requestScope.canEdit && requestScope.canDelete)}"><%-- 同时拥有修改和删除权限 --%>
   									return formatString('<img alt="修改" onclick="special_sub_manage_edit_fn(\'{0}\');" src="{1}"/>&nbsp;<img onclick="special_manage_del_fn(\'{2}\');" src="{3}"/>', row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png', row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png');
   									</c:if>
   									<c:if test="${(sessionScope.isAdmin == '1') || (requestScope.canEdit && !requestScope.canDelete)}"><%-- 只有修改权限 --%>
   									return formatString('<img alt="修改" onclick="special_sub_manage_edit_fn(\'{0}\');" src="{1}"/>', row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png');
   									</c:if>
   									<c:if test="${(sessionScope.isAdmin == '1') || (!requestScope.canEdit && requestScope.canDelete)}"><%-- 只有删除权限 --%>
   									return formatString('<img onclick="special_manage_del_fn(\'{0}\');" src="{1}"/>', row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png');
   									</c:if>
   								}
   							}
   							if(row.cvalid=='0'){//不可用
   								return formatString('<img onclick="special_manage_back_fn(\'{0}\');" src="{1}"/>', row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/tab_go.png');
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
   						special_manage_add_fn();
   					}
   				},'-',
   				</c:if>
   				{
   					text:'展开',
   					iconCls:'icon-redo',
   					handler:function(){
   						var node = $('#special_treegrid').treegrid('getSelected');
						if (node) {
							$('#special_treegrid').treegrid('expandAll', node.cid);
						} else {
							$('#special_treegrid').treegrid('expandAll');
						}
	   				}
   				},'-',{
   					text:'折叠',
   					iconCls:'icon-undo',
   					handler:function(){
   						var node = $('#special_treegrid').treegrid('getSelected');
   						if(node){
   							$('#special_treegrid').treegrid('collapseAll',node.cid);
   						}else $('#special_treegrid').treegrid('collapseAll');
   					}
   				},'-',{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#special_treegrid').treegrid('reload');
   					}
   				}],
   				onLoadSuccess:function(){
   					$('#special_treegrid').treegrid('collapseAll');
   				}
   			});
   		});
   		
   		
   		/* function special_manage_add_fn(){
   			location.href = '${pageContext.request.contextPath}/special/add.action';
   		} */
   		
   		
   		function special_manage_add_fn(){
   			$('<div/>').dialog({
				href:'${pageContext.request.contextPath}/special/add.action',
				width:390,
				height:190,
				modal:true,
				title:'新增专题',
				buttons:[{
					text:'增加',
					iconCls:'icon-add',
					handler:function(){
						var d = $(this).closest('.window-body');
						$('#special_info_form').form('submit',{
							url:'${pageContext.request.contextPath}/special/insert.action',
							success:function(result){
								var r = $.parseJSON(result);
								if(r.status){
									d.dialog('destroy');
									$('#special_treegrid').treegrid('reload');
								}
								$.messager.show({
									title : '提示',
									msg : r.msg
								});
							}
						});
					}
				}],
				onClose:function(){
					$(this).dialog('destroy');
				}
			});
   		}
			
   		//修改
  /*  		function special_manage_edit_fn(id){
   			location.href = '${pageContext.request.contextPath}/special/edit.action?id='+id;
   		}
   	 */	
   		
   		function special_manage_edit_fn(id){
   			$('<div/>').dialog({
				href:'${pageContext.request.contextPath}/special/edit.action?id='+id,
				width:700,
				height:480,
				modal:true,
				title:'修改专题',
				buttons:[{
					text:'提交',
					iconCls:'icon-ok',
					handler:function(){
						var d = $(this).closest('.window-body');
						$('#special_info_edit_form').form('submit',{
							url:'${pageContext.request.contextPath}/special/update.action',
							success:function(result){
								var r = $.parseJSON(result);
								if(r.status){
									d.dialog('destroy');
									$('#special_treegrid').treegrid('reload');
								}
								$.messager.show({
									title : '提示',
									msg : r.msg
								});
							}
						});
					}
				}],
				onClose:function(){
					$(this).dialog('destroy');
					$('#special_treegrid').treegrid('reload');
				}
			});
   		}
   		
   		
   		function special_sub_manage_edit_fn(id){
   			$('<div/>').dialog({
				href:'${pageContext.request.contextPath}/special/editSub.action?id='+id,
				width:490,
				height:220,
				modal:true,
				title:'修改栏目',
				buttons:[{
					text:'提交',
					iconCls:'icon-ok',
					handler:function(){
						var d = $(this).closest('.window-body');
						$('#special_info_manage_edit_form').form('submit',{
							url:'${pageContext.request.contextPath}/special/updateSub.action',
							success:function(result){
								var r = $.parseJSON(result);
								if(r.status){
									d.dialog('destroy');
									$('#special_treegrid').treegrid('reload');
								}
								$.messager.show({
									title : '提示',
									msg : r.msg
								});
							}
						});
					}
				}],
				onClose:function(){
					$(this).dialog('destroy');
				}
			});
   		}
   		
   		//删除
   		function special_manage_del_fn(id){
   			if(id!=undefined){
   				$('#special_treegrid').treegrid('select',id);
   			}
   			var node = $('#special_treegrid').treegrid('getSelected');
   			if(node){
   				$.messager.confirm('提示','您确定要禁用【'+node.text+'】?',function(b){
   					if(b){
   						var url = '${pageContext.request.contextPath}/special/deleteAll.action';
   						$.post(url,{id:node.id},function(data){
   							if(data.status){
   								$('#special_treegrid').treegrid('reload');
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
   		function special_manage_back_fn(id){
   			if(id!=undefined){
   				$('#special_treegrid').treegrid('select',id);
   			}
   			var node = $('#special_treegrid').treegrid('getSelected');
   			if(node){
   				$.messager.confirm('提示','您确定要恢复【'+node.text+'】?',function(b){
   					if(b){
   						var url = '${pageContext.request.contextPath}/special/backAll.action';
   						$.post(url,{id:node.id},function(data){
   							if(data.status){
   								$('#special_treegrid').treegrid('reload');
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
