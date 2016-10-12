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
	<table id="questionCate_treegrid"></table>
	<script type="text/javascript">
	
   		$(function(){
   			$('#questionCate_treegrid').treegrid({
   				url:'${pageContext.request.contextPath}/questionCate/allquestionCate.action',
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
   					width:200
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
   					field:'action',
   					title:'动作',
   					width:150,
   					formatter:function(value,row,index){
   							return formatString('<img alt="修改" onclick="questionCate_manage_edit_fn(\'{0}\');" src="{1}"/>&nbsp;<img onclick="questionCate_manage_del_fn(\'{2}\');" src="{3}"/>', row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png', row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png');
   					}
   				}
   				]],
   				toolbar:[{
   					text:'新增',
   					iconCls:'icon-add',
   					handler:function(){
   						questionCate_manage_add_fn();
   					}
   				},'-',{
   					text:'展开',
   					iconCls:'icon-redo',
   					handler:function(){
   						var node = $('#questionCate_treegrid').treegrid('getSelected');
						if (node) {
							$('#questionCate_treegrid').treegrid('expandAll', node.cid);
						} else {
							$('#questionCate_treegrid').treegrid('expandAll');
						}
	   				}
   				},'-',{
   					text:'折叠',
   					iconCls:'icon-undo',
   					handler:function(){
   						var node = $('#questionCate_treegrid').treegrid('getSelected');
   						if(node){
   							$('#questionCate_treegrid').treegrid('collapseAll',node.cid);
   						}else $('#questionCate_treegrid').treegrid('collapseAll');
   					}
   				},'-',{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#questionCate_treegrid').treegrid('reload');
   					}
   				}],
   				onLoadSuccess:function(){
   					$('#questionCate_treegrid').treegrid('collapseAll');
   				}
   			});
   		});
   		
   		
   		function questionCate_manage_add_fn(){
   			$('<div/>').dialog({
				href:'${pageContext.request.contextPath}/questionCate/add.action',
				width:350,
				height:300,
				modal:true,
				title:'新增',
				buttons:[{
					text:'增加',
					iconCls:'icon-add',
					handler:function(){
						var d = $(this).closest('.window-body');
						$('#questionCate_manage_add_form').form('submit',{
							url:'${pageContext.request.contextPath}/questionCate/insert.action',
							success:function(result){
								var r = $.parseJSON(result);
								if(r.status){
									d.dialog('destroy');
									$('#questionCate_treegrid').treegrid('reload');
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
   		function questionCate_manage_edit_fn(id){
   			if(id!=undefined){
   				$('#questionCate_treegrid').treegrid('select',id);
   			}
   			var node = $('#questionCate_treegrid').treegrid('getSelected');
   			if(node){
   				$('<div/>').dialog({
					href:'${pageContext.request.contextPath}/questionCate/edit.action?id='+node.id,
					width:350,
					height:300,
					modal:true,
					title:'修改',
					buttons:[{
						text:'确定',
						iconCls:'icon-ok',
						handler:function(){
							var d = $(this).closest('.window-body');
							$('#questionCate_manage_edit_form').form('submit',{
								url:'${pageContext.request.contextPath}/questionCate/update.action',
								success:function(result){
									var r = $.parseJSON(result);
									if(r.status){
										d.dialog('destroy');
										$('#questionCate_treegrid').treegrid('reload');
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
   		function questionCate_manage_del_fn(id){
   			if(id!=undefined){
   				$('#questionCate_treegrid').treegrid('select',id);
   			}
   			var node = $('#questionCate_treegrid').treegrid('getSelected');
   			if(node){
   				$.messager.confirm('提示','您确定要删除【'+node.text+'】?',function(b){
   					if(b){
   						var url = '${pageContext.request.contextPath}/questionCate/delete.action';
   						$.post(url,{id:node.id},function(data){
   							if(data.status){
   								$('#questionCate_treegrid').treegrid('reload');
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
