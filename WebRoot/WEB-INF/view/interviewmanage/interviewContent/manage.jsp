<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
<div class="searchDiv" >
	<form action="javascript:void(0);" id="interviewContent_manage_search_form" style="width: 100%">
		<table class="form" style="width: 100%">
			<tr>
				<th class="tt" width="80">标题</th>
				<td>
					<input class="inval" name="keys" style="width: 215px;" />
					<input type="button" class="easyui-linkbutton" value="查询" onclick="interviewContent_manage_search_fn()">
					<input type="button" class="easyui-linkbutton" value="重置" onclick="interviewContent_manage_clean_form_fn()">
				</td>
			</tr>
		</table>
	</form>
</div>
<div style="height:80%;width:100%;">
	<table id="interviewContent_datagrid"></table>
</div>
	<script type="text/javascript">
	
   		$(function(){
   			$('#interviewContent_datagrid').datagrid({
   				url:'${pageContext.request.contextPath}/interviewContent/dataGrid.action?interviewId=${interviewId}',
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
   					field:'cdialoguecontent',
   					title:'内容',
   					width:600,
   					formatter:function(value,row,index){
   						value = delHtmlTag(value); // 如果不删除里面的特殊字符，下面 substring 会出错
   						return (value.length > 40 ? value.substring(0, 40) + '...' : value);
   					}
   				}]],
   				columns:[[{
   					field:'nintonlid',
   					title:'ID',
   					width:50,
   					hidden:true
   				},{
   					field:'dcreatetime',
   					title:'创建时间',
   					width:100/* ,
   					formatter:function(value,row,index){
   						return formatDate(value);
   					} */
   				},{
   					field:'ncreateid',
   					title:'创建人',
   					width:80,
   					hidden:true
   				},{
   					field:'action',
   					title:'动作',
   					width:60,
   					formatter:function(value,row,index){
   									return formatString('<img title="删除" onclick="interviewContent_manage_del_fn(\'{0}\');" src="{1}"/>&nbsp;<img title="查看" onclick="interviewContent_manage_detail_fn(\'{2}\');" src="{3}"/>', row.nintconid, '${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png', row.nintconid, '${pageContext.request.contextPath}/resources/images/extjs_icons/book_open.png');
   							}
   				}]],
   				toolbar:[{
   					text:'新增',
   					iconCls:'icon-add',
   					handler:function(){
   						interviewContent_manage_add_fn();
   					}
   				},'-',{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#interviewContent_datagrid').datagrid('reload');
   					}
   				},'-',{
   					text:'返回访谈列表',
   					iconCls:'icon-undo',
   					handler:function(){
   						location.href = '${pageContext.request.contextPath}/interview/manage.action';
   					}
   				}]
   			});
   		});
   		
   		// 去掉一段文本中的所有 html 标签
		function delHtmlTag(str) 
		{ 
			return str.replace(/<[^>]+>/g,"");
			//return str.replace(/<[^>]+>|&nbsp;/g,"");  // 把空格也过滤掉
		} 
   		
   		function interviewContent_manage_add_fn(){
   			location.href = '${pageContext.request.contextPath}/interviewContent/add.action?interviewId=${interviewId}';
   		}
   		
   		//修改
   		function interviewContent_manage_edit_fn(id){
   			if(id!=undefined){
   				location.href = '${pageContext.request.contextPath}/interviewContent/edit.action?id='+id
   			}
   		}
   		
   		function interviewContent_manage_detail_fn(id){
   			location.href = '${pageContext.request.contextPath}/interviewContent/detail.action?interviewContentId='+id;
   		}
   		
   		//审核
   		function interviewContent_manage_approve_fn(id){
   			if(id!=undefined){
   				location.href = '${pageContext.request.contextPath}/interviewContent/detail.action?id='+id+'&opType=approve';
   			}
   		}
   		
   		//直播
   		function interviewContent_manage_live_fn(id){
   			if(id!=undefined){
   				location.href = '${pageContext.request.contextPath}/interviewContentContent/manage.action?interviewContentId='+id;
   			}
   		}
   		
   		
   		//删除
   		function interviewContent_manage_del_fn(id){
   			if(id!=undefined){
   				$.messager.confirm('提示','您确定要删除当前选项吗?',function(b){
   					if(b){
   						var url = '${pageContext.request.contextPath}/interviewContent/delete.action';
   						$.post(url,{id:id},function(data){
   							if(data.status){
   								$('#interviewContent_datagrid').datagrid('reload');
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
   		function interviewContent_manage_back_fn(id){
   			if(id!=undefined){
   			   $('#interviewContent_datagrid').datagrid('selectRecord',id);
   				var record = $('#interviewContent_datagrid').datagrid('getSelected');
   				$.messager.confirm('提示','您确定恢复【'+record.title+'】?',function(b){
   					if(b){
   						var url = '${pageContext.request.contextPath}/interviewContent/back.action';
   						$.post(url,{id:id},function(data){
   							if(data.status){
   								$('#interviewContent_datagrid').datagrid('reload');
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
   		
   		
   		function interviewContent_manage_search_fn() {
			$('#interviewContent_datagrid').datagrid('load', serializeObject($('#interviewContent_manage_search_form')));
		}
		function interviewContent_manage_clean_form_fn() {
			$('#interviewContent_manage_search_form input').val('');
			$('#interviewContent_datagrid').datagrid('load', {});
		}
   		
	</script>
</body>
</html>
