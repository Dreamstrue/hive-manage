<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>导航菜单管理</title>
<jsp:include page="../common/inc.jsp"></jsp:include>
</head>

<body>
	<table id="navMenu_treegrid"></table>
	<script type="text/javascript">
	
   		$(function(){
   			$('#navMenu_treegrid').treegrid({
   				url:'${pageContext.request.contextPath}/navMenu/allNavMenu.action',
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
   					title:'栏目名称',
   					width:140
   				}]],
   				columns:[[{
   					field:'id',
   					title:'菜单ID',
   					width:50,
   					hidden:true
   				},{
   					field:'leaf',
   					title:'叶子',
   					width:50,
   					hidden:true
   				},{
   					field:'url',
   					title:'action路径',
   					width:50,
   					hidden:true
   				},{
   					field:'pid',
   					title:'父级菜单ID',
   					width:60,
   					hidden:true
   				},{
   					field:'menuHref',
   					title:'栏目地址',
   					width:150
   				},{
   					field:'sortOrder',
   					title:'排序',
   					width:50
   				},{
   					field:'createUserId',
   					title:'创建人',
   					width:80,
   					hidden:true
   				},{
   					field:'createTime',
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
   					field:'valid',
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
   				<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canView || requestScope.canEdit || requestScope.canDelete}">
   				,{
   					field:'action',
   					title:'动作',
   					width:150,
   					formatter:function(value,row,index){
   						if(row.pid!='0'){
   							if(row.valid=='1'){//可用
   								<c:if test="${(sessionScope.isAdmin == '1') || (requestScope.canEdit && requestScope.canDelete)}"><%-- 同时拥有修改和删除权限 --%>
   								return formatString('<img alt="修改" onclick="navmenu_manage_edit_fn(\'{0}\');" src="{1}"/>&nbsp;<img onclick="navmenu_manage_del_fn(\'{2}\');" src="{3}"/>', row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png', row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png');
   								</c:if>
   								<c:if test="${(sessionScope.isAdmin == '1') || (requestScope.canEdit && !requestScope.canDelete)}"><%-- 只有修改权限 --%>
   								return formatString('<img alt="修改" onclick="navmenu_manage_edit_fn(\'{0}\');" src="{1}"/>', row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png');
   								</c:if>
   								<c:if test="${(sessionScope.isAdmin == '1') || (!requestScope.canEdit && requestScope.canDelete)}"><%-- 只有删除权限 --%>
   								return formatString('<img onclick="navmenu_manage_del_fn(\'{0}\');" src="{1}"/>', row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png');
   								</c:if>
   							}
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
   						navmenu_manage_add_fn();
   					}
   				},'-',
   				</c:if>
   				{
   					text:'展开',
   					iconCls:'icon-redo',
   					handler:function(){
   						var node = $('#navMenu_treegrid').treegrid('getSelected');
						if (node) {
							$('#navMenu_treegrid').treegrid('expandAll', node.cid);
						} else {
							$('#navMenu_treegrid').treegrid('expandAll');
						}
	   				}
   				},'-',{
   					text:'折叠',
   					iconCls:'icon-undo',
   					handler:function(){
   						var node = $('#navMenu_treegrid').treegrid('getSelected');
   						if(node){
   							$('#navMenu_treegrid').treegrid('collapseAll',node.cid);
   						}else $('#navMenu_treegrid').treegrid('collapseAll');
   					}
   				},'-',{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#navMenu_treegrid').treegrid('reload');
   					}
   				}],
   				onLoadSuccess:function(){
   					$('#navMenu_treegrid').treegrid('collapseAll');
   				}
   			});
   		});
   		
   		
   		function navmenu_manage_add_fn(){
   			$('<div/>').dialog({
   				id : 'add_dialog',
				href:'${pageContext.request.contextPath}/navMenu/add.action',
				width:350,
				height:330,
				modal:true,
				title:'新增菜单',
				buttons:[{
					text:'增加',
					iconCls:'icon-add',
					handler:function(){
						var d = $(this).closest('.window-body');
						$('#navmenu_manage_add_form').form('submit',{
							url:'${pageContext.request.contextPath}/navMenu/insert.action',
							success:function(result){
								var r = $.parseJSON(result);
								if(r.status){
									$("#add_dialog").dialog('destroy');
									$('#navMenu_treegrid').treegrid('reload');
									window.parent.reloadWestTree(); 
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
   		function navmenu_manage_edit_fn(id){
   			if(id!=undefined){
   				$('#navMenu_treegrid').treegrid('select',id);
   			}
   			var node = $('#navMenu_treegrid').treegrid('getSelected');
   			if(node){
   				$('<div/>').dialog({
   					id : 'update_dialog',
					href:'${pageContext.request.contextPath}/navMenu/edit.action?id='+node.id,
					width:350,
					height:300,
					modal:true,
					title:'修改菜单',
					buttons:[{
						text:'确定',
						iconCls:'icon-ok',
						handler:function(){
							var d = $(this).closest('.window-body');
							$('#navmenu_manage_edit_form').form('submit',{
								url:'${pageContext.request.contextPath}/navMenu/update.action',
								success:function(result){
									var r = $.parseJSON(result);
									if(r.status){
										$('#update_dialog').dialog('destroy');
										$('#navMenu_treegrid').treegrid('reload');
										window.parent.reloadWestTree(); 
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
   		}
   		
   		//审核
   		function navmenu_manage_approve_fn(id){
   			if(id!=undefined){
   				$('#navMenu_treegrid').treegrid('select',id);
   			}
   			var node = $('#navMenu_treegrid').treegrid('getSelected');
   			if(node){
   				$('<div/>').dialog({
					href:'${pageContext.request.contextPath}/navMenu/audit.action?id='+node.id,
					width:350,
					height:250,
					modal:true,
					title:'修改菜单',
					buttons:[{
						text:'通过',
						iconCls:'icon-ok',
						handler:function(){
							var d = $(this).closest('.window-body');
							$('#navmenu_manage_edit_form').form('submit',{
								url:'${pageContext.request.contextPath}/navMenu/approve.action?type=yes',
								success:function(result){
									var r = $.parseJSON(result);
									if(r.status){
										d.dialog('destroy');
										$('#navMenu_treegrid').treegrid('reload');
									}
									$.messager.show({
										title : '提示',
										msg : r.msg
									});
								}
							});
						}
					},{
						text:'不通过',
						iconCls:'icon-cancel',
						handler:function(){
							var d = $(this).closest('.window-body');
							$('#navmenu_manage_edit_form').form('submit',{
								url:'${pageContext.request.contextPath}/navMenu/approve.action?type=no',
								success:function(result){
									var r = $.parseJSON(result);
									if(r.status){
										d.dialog('destroy');
										$('#navMenu_treegrid').treegrid('reload');
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
   		}
   		
   		
   		
   		//删除
   		function navmenu_manage_del_fn(id){
   			if(id!=undefined){
   				$('#navMenu_treegrid').treegrid('select',id);
   			}
   			var node = $('#navMenu_treegrid').treegrid('getSelected');
   			if(node){
   				$.messager.confirm('提示','您确定要禁用【'+node.text+'】?',function(b){
   					if(b){
   						var url = '${pageContext.request.contextPath}/navMenu/deleteAll.action';
   						$.post(url,{id:node.id},function(data){
   							if(data.status){
   								$('#navMenu_treegrid').treegrid('reload');
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
   		function navmenu_manage_back_fn(id){
   			if(id!=undefined){
   				$('#navMenu_treegrid').treegrid('select',id);
   			}
   			var node = $('#navMenu_treegrid').treegrid('getSelected');
   			if(node){
   				$.messager.confirm('提示','您确定要恢复【'+node.text+'】?',function(b){
   					if(b){
   						var url = '${pageContext.request.contextPath}/navMenu/backAll.action';
   						$.post(url,{id:node.id},function(data){
   							if(data.status){
   								$('#navMenu_treegrid').treegrid('reload');
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
   		
   		
   		
   		
   		
   		//格式化日期样式
		function formatDate(value){
			var  t = new Date(value);
   			var m = t.getMonth()+1;
   			var d =(t.getDate()<10)?("0"+t.getDate()):t.getDate();
   			var hour = (t.getHours()<10)?("0"+t.getHours()):t.getHours();
   			var min = (t.getMinutes()<10)?("0"+t.getMinutes()):t.getMinutes();
   			var sec = (t.getSeconds()<10)?("0"+t.getSeconds()):t.getSeconds();
   			var s = t.getFullYear()+"-"+m+"-"+d+" "+hour+":"+min+":"+sec;
   			return s;
		}
	</script>
</body>
</html>
