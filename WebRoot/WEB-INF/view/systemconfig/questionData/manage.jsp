<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>问卷问题数据源管理</title>
<jsp:include page="../../common/inc.jsp"></jsp:include>
</head>

<body>
	<table id="questionData_treegrid"></table>
	<script type="text/javascript">
	
   		$(function(){
   			$('#questionData_treegrid').treegrid({
   				url:'${pageContext.request.contextPath}/questionData/allquestionData.action',
   				idField:'id',
   				treeField:'text',
   				parentField:'parentId',
   				fit:true,
   				fitColumns:true,
   				rownumbers:true,
   				border:false,
   				animate:false,
   				frozenColumns:[[{
   					field:'text',
   					title:'名称',
   					width:160
   				}]],
   				columns:[[{
   					field:'id',
   					title:'菜单ID',
   					width:50,
   					hidden:true
   				},{
   					field:'parentId',
   					title:'父级菜单ID',
   					width:60,
   					hidden:true
   				},{
   					field:'sort',
   					title:'排序',
   					width:50
   				},{
   					field:'valid',
   					title:'是否可用',
   					width:50
   				},{
   					field:'action',
   					title:'动作',
   					width:150,
   					formatter:function(value,row,index){
   							return formatString('<img alt="修改" onclick="questionData_manage_edit_fn(\'{0}\');" src="{1}"/>&nbsp;<img onclick="questionData_manage_del_fn(\'{2}\');" src="{3}"/>', row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png', row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png');
   					}
   				}
   				]],
   				toolbar:[{
   					text:'新增',
   					iconCls:'icon-add',
   					handler:function(){
   						questionData_manage_add_fn();
   					}
   				},'-',{
   					text:'展开',
   					iconCls:'icon-redo',
   					handler:function(){
   						var node = $('#questionData_treegrid').treegrid('getSelected');
						if (node) {
							$('#questionData_treegrid').treegrid('expandAll', node.cid);
						} else {
							$('#questionData_treegrid').treegrid('expandAll');
						}
	   				}
   				},'-',{
   					text:'折叠',
   					iconCls:'icon-undo',
   					handler:function(){
   						var node = $('#questionData_treegrid').treegrid('getSelected');
   						if(node){
   							$('#questionData_treegrid').treegrid('collapseAll',node.cid);
   						}else $('#questionData_treegrid').treegrid('collapseAll');
   					}
   				},'-',{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#questionData_treegrid').treegrid('reload');
   					}
   				}],
   				onLoadSuccess:function(){
   					$('#questionData_treegrid').treegrid('collapseAll');
   				}
   			});
   		});
   		
   		
   		function questionData_manage_add_fn(){
   			$('<div/>').dialog({
				href:'${pageContext.request.contextPath}/questionData/add.action',
				width:350,
				height:300,
				modal:true,
				title:'新增',
				buttons:[{
					text:'增加',
					iconCls:'icon-add',
					handler:function(){
						var d = $(this).closest('.window-body');
						$('#questionData_manage_add_form').form('submit',{
							url:'${pageContext.request.contextPath}/questionData/insert.action',
							success:function(result){
								var r = $.parseJSON(result);
								if(r.status){
									d.dialog('destroy');
									$('#questionData_treegrid').treegrid('reload');
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
   		function questionData_manage_edit_fn(id){
   			if(id!=undefined){
   				$('#questionData_treegrid').treegrid('select',id);
   			}
   			var node = $('#questionData_treegrid').treegrid('getSelected');
   			if(node){
   				$('<div/>').dialog({
					href:'${pageContext.request.contextPath}/questionData/edit.action?id='+node.id,
					width:350,
					height:300,
					modal:true,
					title:'修改',
					buttons:[{
						text:'确定',
						iconCls:'icon-ok',
						handler:function(){
							var d = $(this).closest('.window-body');
							$('#questionData_manage_edit_form').form('submit',{
								url:'${pageContext.request.contextPath}/questionData/update.action',
								success:function(result){
									var r = $.parseJSON(result);
									if(r.status){
										d.dialog('destroy');
										$('#questionData_treegrid').treegrid('reload');
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
   		

   		
   		
   		
   		//删除
   		function questionData_manage_del_fn(id){
   			if(id!=undefined){
   				$('#questionData_treegrid').treegrid('select',id);
   			}
   			var node = $('#questionData_treegrid').treegrid('getSelected');
   			if(node){
   				$.messager.confirm('提示','您确定要删除【'+node.text+'】?',function(b){
   					if(b){
   						var url = '${pageContext.request.contextPath}/questionData/delete.action';
   						$.post(url,{id:node.id},function(data){
   							if(data.status){
   								$('#questionData_treegrid').treegrid('reload');
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
