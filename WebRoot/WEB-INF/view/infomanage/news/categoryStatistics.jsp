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
.searchDiv1{
	overflow: hidden;
	height: 40px; 
	border-bottom: 1px solid #99CCFF;
	padding-left: 10px;
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
   		
   			//分类Grid
   			$('#news_statistics_datagrid').datagrid({
   				url:'${appPath}/news/categoryDataGrid.action',
   				idField:'id',
   				fit:true,
   				fitColumns:true,
   				pagination:true,
   				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50 ],
   				rownumbers:true,
   				border:false,
   				animate:false,
   				singleSelect:true,
   				frozenColumns:[[{
   					field:'infoCateName',
   					title:'信息类别',
   					width:220,
   					align:'center'
   				}]],
   				columns:[[{
   					field:'id',
   					title:'id',
   					width:50,
   					hidden:true
   				},{
   					field:'infoCateId',
   					title:'infoCateId',
   					width:50,
   					hidden:true
   				},{
   					field:'newsNum',
   					title:'新闻数量',
   					align:'center',
   					width:50,
   					formatter:function(value,row,index){
   						if(value>0){
   							var html = "";
	   						html += '<a href="javascript:shownewsList('+ row.infoCateId +')">'+value+'</a>';
	   						return html;
   						}else{
   							return value;
   						}
	   					}
   				}]],
   				toolbar:[{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#news_statistics_datagrid').datagrid('reload');
   					}
   				}]
   			});
   		});
   		
   		function shownewsList(infoCateId){
			var w = getWindowWidth();  //浏览器的内部窗口宽度
			var h = getWindowHeight();
			$('#news_statistics_datagrid').datagrid('selectRecord',infoCateId);
   			var record = $('#news_statistics_datagrid').datagrid('getSelected');
			$('<div/>').dialog({
				href : '${appPath}/news/newsStatistics.action?infoCateId='+infoCateId,
				width :w-30,
				height : h-30,
				top:8,
				left:8,
				modal : false,
				draggable:true,
				title : record.infoCateName+'_资讯列表',
				onClose : function() {
					$(this).dialog('destroy');
				}
			});
   		}
   		
   		
   		//分类面板查询
   		function news_manage_search_fn() {
			$('#news_statistics_datagrid').datagrid('load', serializeObject($('#news_manage_search_form')));
		}
		function cleanForm() {
			$('#infoCateId').combobox('clear');
			$('#news_statistics_datagrid').datagrid('load', {});
		}
		
		
	</script>
  	<div class="searchDiv1">
		<form action="javascript:void(0);" id="news_manage_search_form" >
			<table class="form" style="height:40px;">
				<tr>
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
  	<div style="height:87%;width:100%;">
		<table id="news_statistics_datagrid"></table>
	</div>
</body>
</html>
